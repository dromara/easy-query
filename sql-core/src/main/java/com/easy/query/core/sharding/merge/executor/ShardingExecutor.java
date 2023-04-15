package com.easy.query.core.sharding.merge.executor;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryTimeoutException;
import com.easy.query.core.sharding.EasyShardingOption;
import com.easy.query.core.sharding.common.SqlRouteUnit;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.executor.common.CommandExecuteUnit;
import com.easy.query.core.sharding.merge.executor.common.DataSourceSqlExecutorUnit;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.common.Grouping;
import com.easy.query.core.sharding.merge.executor.common.SqlExecutorGroup;
import com.easy.query.core.sharding.merge.executor.common.SqlUnit;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;
import com.easy.query.core.sharding.merge.executor.internal.Executor;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.EasyUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
    private ShardingExecutor() {
    }

    public static <TResult> TResult execute(StreamMergeContext streamMergeContext, Executor<TResult> executor, Stream<SqlRouteUnit> sqlRouteUnits) {
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

    private static <TResult> List<Future<List<TResult>>> execute0(StreamMergeContext streamMergeContext, Executor<TResult> executor, Stream<SqlRouteUnit> sqlRouteUnits) {
        ExecutorService executorService = streamMergeContext.getRuntimeContext().getEasyShardingExecutorService().getExecutorService();
        Stream<SqlRouteUnit> sqlRouteUnitStream = reOrderTableTails(streamMergeContext, sqlRouteUnits);
        return EasyUtil.groupBy(sqlRouteUnitStream, SqlRouteUnit::getDataSourceName)
                .map(o -> getSqlExecutorGroups(streamMergeContext, o))
                .map(dataSourceSqlExecutorUnit -> {
                    return executorService.submit(() -> executor.execute(dataSourceSqlExecutorUnit));
                }).collect(Collectors.toList());
    }

    private static Stream<SqlRouteUnit> reOrderTableTails(StreamMergeContext streamMergeContext,
                                                          Stream<SqlRouteUnit> sqlRouteUnits) {
        if (streamMergeContext.isSeqQuery()) {
            throw new NotImplementedException();
//            return sqlRouteUnits.OrderByAscDescIf(o => o.TableRouteResult.ReplaceTables.First().Tail,
//            streamMergeContext.TailComparerNeedReverse, streamMergeContext.ShardingTailComparer);
        }

        return sqlRouteUnits;
    }

    private static DataSourceSqlExecutorUnit getSqlExecutorGroups(StreamMergeContext streamMergeContext, Grouping<String, SqlRouteUnit> sqlGroups) {
        boolean isSerialExecute = streamMergeContext.isSerialExecute();
        EasyShardingOption easyShardingOption = streamMergeContext.getRuntimeContext().getEasyShardingOption();
        int maxQueryConnectionsLimit = easyShardingOption.getMaxQueryConnectionsLimit();
        String dataSourceName = sqlGroups.key();
        List<SqlRouteUnit> sqlGroupExecutionUnits = sqlGroups.values().collect(Collectors.toList());
        int sqlCount = sqlGroupExecutionUnits.size();
        //串行执行insert update delete或者最大连接数大于每个数据源分库的执行数目
        ConnectionModeEnum connectionMode = (isSerialExecute || maxQueryConnectionsLimit >= sqlCount)
                ? ConnectionModeEnum.MEMORY_STRICTLY
                : ConnectionModeEnum.CONNECTION_STRICTLY;

        //如果是串行执行就是说每个组只有1个,如果是不是并行每个组有最大执行个数个
        int parallelCount = isSerialExecute ? 1 : maxQueryConnectionsLimit;

        List<List<SqlRouteUnit>> sqlUnitPartitions = ArrayUtil.partition(sqlGroupExecutionUnits, parallelCount);
        //由于分组后除了最后一个元素其余元素都满足parallelCount为最大,第一个元素的分组数将是实际的创建连接数
        int createDbConnectionCount = sqlUnitPartitions.get(0).size();
        List<EasyConnection> easyConnections = streamMergeContext.getEasyConnections(connectionMode, dataSourceName, createDbConnectionCount);
        //将SqlExecutorUnit进行分区,每个区maxQueryConnectionsLimit个
        //[1,2,3,4,5,6,7],maxQueryConnectionsLimit=3,结果就是[[1,2,3],[4,5,6],[7]]
        List<List<CommandExecuteUnit>> sqlExecutorUnitPartitions = ArrayUtil.select(sqlUnitPartitions, (executionUnits, index0) -> {
            return ArrayUtil.select(executionUnits, (executionUnit, index1) -> {
                SqlUnit sqlUnit = executionUnit.getSqlUnit();
                EasyConnection easyConnection = easyConnections.get(index1);
                PreparedStatement preparedStatement = createPreparedStatement(sqlUnit, easyConnection);
                return new CommandExecuteUnit(executionUnit, preparedStatement, connectionMode, CommandTypeEnum.QUERY);
            });
        });
        List<SqlExecutorGroup<CommandExecuteUnit>> sqlExecutorGroups = ArrayUtil.select(sqlExecutorUnitPartitions, (o, i) -> new SqlExecutorGroup<CommandExecuteUnit>(connectionMode, o));
        return new DataSourceSqlExecutorUnit(connectionMode, sqlExecutorGroups);

    }

    private static PreparedStatement createPreparedStatement(SqlUnit sqlUnit, EasyConnection easyConnection) {
        return null;
    }
}
