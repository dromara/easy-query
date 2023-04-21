package com.easy.query.core.sharding.merge.executor;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.exception.EasyQueryException;
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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * create time 2023/4/15 14:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingExecutor {
    private static final Log log= LogFactory.getLog(ShardingExecutor.class);
    private ShardingExecutor() {
    }

    public static <TResult> TResult execute(StreamMergeContext streamMergeContext, Executor<TResult> executor, Stream<ExecutionUnit> sqlRouteUnits) {
        List<Future<List<TResult>>> futures = execute0(streamMergeContext, executor, sqlRouteUnits);
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
        return executor.getShardingMerger().streamMerge(streamMergeContext, results);
    }

    private static <TResult> List<Future<List<TResult>>> execute0(StreamMergeContext streamMergeContext, Executor<TResult> executor, Stream<ExecutionUnit> executionUnits) {
        ExecutorService executorService = streamMergeContext.getRuntimeContext().getEasyShardingExecutorService().getExecutorService();
        Stream<ExecutionUnit> executionUnitStream = reOrderTableTails(streamMergeContext, executionUnits);
        return EasyUtil.groupBy(executionUnitStream, ExecutionUnit::getDataSourceName)
                .map(o -> getSqlExecutorGroups(streamMergeContext, o))
                .map(dataSourceSqlExecutorUnit -> {
                    return executorService.submit(() -> executor.execute(dataSourceSqlExecutorUnit));
                }).collect(Collectors.toList());
    }

    private static Stream<ExecutionUnit> reOrderTableTails(StreamMergeContext streamMergeContext,
                                                       Stream<ExecutionUnit> executionUnits) {
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
