package com.easy.query.core.sharding.merge.executor;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.exception.EasyQueryTimeoutException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.EasyShardingOption;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.executor.common.CommandExecuteUnit;
import com.easy.query.core.sharding.merge.executor.common.DataSourceSqlExecutorUnit;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.common.Grouping;
import com.easy.query.core.sharding.merge.executor.common.SqlExecutorGroup;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.EasyUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * create time 2023/4/15 14:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingExecutor {
    private static final Log log = LogFactory.getLog(ShardingExecutor.class);

    private ShardingExecutor() {
    }

    public static <TResult> TResult execute(StreamMergeContext streamMergeContext, Executor<TResult> executor, Collection<ExecutionUnit> sqlRouteUnits) {

        List<TResult> results = execute0(streamMergeContext, executor, sqlRouteUnits);
        if (ArrayUtil.isEmpty(results)) {
            throw new EasyQueryException("execute result empty");
        }
        return executor.getShardingMerger().streamMerge(streamMergeContext, results);
    }

    private static <TResult> List<TResult> execute0(StreamMergeContext streamMergeContext, Executor<TResult> executor, Collection<ExecutionUnit> executionUnits) {

        //先进行顺序重排,可以让按顺序执行的提高性能,比如按时间分片,并且是时间倒序,那么重排后可以减少很多查询
        Collection<ExecutionUnit> reOrderExecutionUnits = reOrderExecutionUnits(streamMergeContext, executionUnits);
        //将数据以每个数据源进行聚合
        List<DataSourceSqlExecutorUnit> dataSourceSqlExecutorUnits = EasyUtil.groupBy(reOrderExecutionUnits.stream(), ExecutionUnit::getDataSourceName)
                .map(o -> getSqlExecutorGroups(streamMergeContext, o)).collect(Collectors.toList());
        //如果本身就只有一条要执行的sql那么就不需要另外开启线程并行执行,直接当前线程执行即可
        if (executionUnits.size() == 1) {
            DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit = dataSourceSqlExecutorUnits.get(0);
            return executor.execute(dataSourceSqlExecutorUnit);
        }
        //如果是有多条sql要执行那么就进行并行处理以数据源DataSourceName多组 每组再以maxQueryConnectionsLimit为一组进行同数据源下顺序并行查询
        List<Future<List<TResult>>> futures = executeFuture0(streamMergeContext, executor, dataSourceSqlExecutorUnits);

        EasyShardingOption easyShardingOption = streamMergeContext.getRuntimeContext().getEasyShardingOption();
        List<TResult> results = new ArrayList<>(futures.size() * easyShardingOption.getMaxQueryConnectionsLimit());
        for (Future<List<TResult>> future : futures) {
            try {
                results.addAll(future.get(60, TimeUnit.SECONDS));
            } catch (InterruptedException | ExecutionException e) {
                throw new EasyQueryException(e);
            } catch (TimeoutException e) {
                throw new EasyQueryTimeoutException(e);
            }
        }
        return results;


    }

    private static <TResult> List<Future<List<TResult>>> executeFuture0(StreamMergeContext streamMergeContext, Executor<TResult> executor, List<DataSourceSqlExecutorUnit> dataSourceSqlExecutorUnits) {
        ExecutorService executorService = streamMergeContext.getRuntimeContext().getEasyShardingExecutorService().getExecutorService();
        List<Future<List<TResult>>> futures = new ArrayList<>(dataSourceSqlExecutorUnits.size());
        for (DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit : dataSourceSqlExecutorUnits) {
            Future<List<TResult>> future = executorService.submit(() -> executor.execute(dataSourceSqlExecutorUnit));
            futures.add(future);
        }
        return futures;
    }

    private static Collection<ExecutionUnit> reOrderExecutionUnits(StreamMergeContext streamMergeContext,
                                                                   Collection<ExecutionUnit> executionUnits) {
        if (streamMergeContext.isSeqQuery()) {
            throw new NotImplementedException();
//            return sqlRouteUnits.OrderByAscDescIf(o => o.TableRouteResult.ReplaceTables.First().Tail,
//            streamMergeContext.TailComparerNeedReverse, streamMergeContext.ShardingTailComparer);
        }

        return executionUnits;
    }

    private static DataSourceSqlExecutorUnit getSqlExecutorGroups(StreamMergeContext streamMergeContext, Grouping<String, ExecutionUnit> sqlGroups) {
        boolean isSerialExecute = streamMergeContext.isSerialExecute();
        EasyShardingOption easyShardingOption = streamMergeContext.getRuntimeContext().getEasyShardingOption();
        int maxQueryConnectionsLimit = easyShardingOption.getMaxQueryConnectionsLimit();
        String dataSourceName = sqlGroups.key();
        List<ExecutionUnit> sqlGroupExecutionUnits = sqlGroups.values().collect(Collectors.toList());
        int sqlCount = sqlGroupExecutionUnits.size();
        //串行执行insert update delete或者最大连接数大于每个数据源分库的执行数目
        ConnectionModeEnum connectionMode = (isSerialExecute || maxQueryConnectionsLimit >= sqlCount)
                ? ConnectionModeEnum.MEMORY_STRICTLY
                : ConnectionModeEnum.CONNECTION_STRICTLY;

        //如果是串行执行就是说每个组只有1个,如果是不是并行每个组有最大执行个数个
        int parallelCount = isSerialExecute ? 1 : maxQueryConnectionsLimit;

        List<List<ExecutionUnit>> sqlUnitPartitions = ArrayUtil.partition(sqlGroupExecutionUnits, parallelCount);
        //由于分组后除了最后一个元素其余元素都满足parallelCount为最大,第一个元素的分组数将是实际的创建连接数
        int createDbConnectionCount = sqlUnitPartitions.get(0).size();
        List<EasyConnection> easyConnections = streamMergeContext.getEasyConnections(connectionMode, dataSourceName, createDbConnectionCount);
        //将SqlExecutorUnit进行分区,每个区maxQueryConnectionsLimit个
        //[1,2,3,4,5,6,7],maxQueryConnectionsLimit=3,结果就是[[1,2,3],[4,5,6],[7]]
        List<List<CommandExecuteUnit>> sqlExecutorUnitPartitions = ArrayUtil.select(sqlUnitPartitions, (executionUnits, index0) -> {
            return ArrayUtil.select(executionUnits, (executionUnit, index1) -> {
                EasyConnection easyConnection = easyConnections.get(index1);
                return new CommandExecuteUnit(executionUnit, easyConnection, connectionMode);
            });
        });
        List<SqlExecutorGroup<CommandExecuteUnit>> sqlExecutorGroups = ArrayUtil.select(sqlExecutorUnitPartitions, (o, i) -> new SqlExecutorGroup<CommandExecuteUnit>(connectionMode, o));
        return new DataSourceSqlExecutorUnit(connectionMode, sqlExecutorGroups);

    }
}
