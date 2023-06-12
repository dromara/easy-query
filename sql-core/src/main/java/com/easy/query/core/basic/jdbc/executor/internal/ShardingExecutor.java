package com.easy.query.core.basic.jdbc.executor.internal;

import com.easy.query.core.basic.jdbc.conn.EasyConnection;
import com.easy.query.core.basic.thread.FuturesInvoker;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.DataSourceSQLExecutorUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.Grouping;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLExecutorGroup;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.util.EasyShardingUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * create time 2023/4/15 14:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingExecutor {
//    private static final Log log = LogFactory.getLog(ShardingExecutor.class);

    private ShardingExecutor() {
    }

    public static <TResult> TResult execute(StreamMergeContext streamMergeContext, Executor<TResult> executor, List<ExecutionUnit> sqlRouteUnits) throws SQLException {

        List<TResult> results = execute0(streamMergeContext, executor, sqlRouteUnits);
        if (EasyCollectionUtil.isEmpty(results)) {
            throw new EasyQueryException("execute result empty");
        }
        return executor.getShardingMerger().streamMerge(streamMergeContext, results);
    }

    private static <TResult> List<TResult> execute0(StreamMergeContext streamMergeContext, Executor<TResult> executor, List<ExecutionUnit> executionUnits) throws SQLException {
        //如果只有单个执行那么直接创建不需要过多处理
        if (EasyCollectionUtil.isSingle(executionUnits)) {
            DataSourceSQLExecutorUnit dataSourceSQLExecutorUnit = getSingleSQLExecutorGroups(streamMergeContext, EasyCollectionUtil.first(executionUnits));
            return executor.execute(dataSourceSQLExecutorUnit);
        }
        //将数据以每个数据源进行聚合
        List<DataSourceSQLExecutorUnit> dataSourceSQLExecutorUnits = EasyUtil.groupBy(executionUnits.stream(), ExecutionUnit::getDataSourceName)
                .map(o -> getSQLExecutorGroups(streamMergeContext, o)).collect(Collectors.toList());
        //如果本身就只有一条要执行的sql那么就不需要另外开启线程并行执行,直接当前线程执行即可
        if (dataSourceSQLExecutorUnits.size() == 1) {
            DataSourceSQLExecutorUnit dataSourceSQLExecutorUnit = dataSourceSQLExecutorUnits.get(0);
            return executor.execute(dataSourceSQLExecutorUnit);
        }
        //如果是有多条sql要执行那么就进行并行处理以数据源DataSourceName多组 每组再以maxQueryConnectionsLimit为一组进行同数据源下顺序并行查询
        List<Future<List<TResult>>> futures = executeFuture0(streamMergeContext, executor, dataSourceSQLExecutorUnits);

        EasyQueryOption easyQueryOption = streamMergeContext.getEasyQueryOption();
        long timeoutMillis = easyQueryOption.getShardingExecuteTimeoutMillis();

        try(FuturesInvoker<List<TResult>> invoker = new FuturesInvoker<>(futures)){
            List<List<TResult>> lists = invoker.get(timeoutMillis);
            return lists.stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
    }

    private static <TResult> List<Future<List<TResult>>> executeFuture0(StreamMergeContext streamMergeContext, Executor<TResult> executor, List<DataSourceSQLExecutorUnit> dataSourceSQLExecutorUnits) {
        ExecutorService executorService = streamMergeContext.getRuntimeContext().getShardingExecutorService().getExecutorService();
        List<Future<List<TResult>>> futures = new ArrayList<>(dataSourceSQLExecutorUnits.size());
        for (DataSourceSQLExecutorUnit dataSourceSQLExecutorUnit : dataSourceSQLExecutorUnits) {
            Future<List<TResult>> future = executorService.submit(() -> executor.execute(dataSourceSQLExecutorUnit));
            futures.add(future);
        }
        return futures;
    }

    /**
     * 将多个按数据源分组的执行单元进行分组查询每组最大数目就是max query connections limit
     * 如果当前是顺序查询那么则为配置的值
     *
     * @param streamMergeContext
     * @param sqlGroups
     * @return
     */
    private static DataSourceSQLExecutorUnit getSQLExecutorGroups(StreamMergeContext streamMergeContext, Grouping<String, ExecutionUnit> sqlGroups) {
        boolean isSerialExecute = !streamMergeContext.isQuery();
        //如果是顺序查询应该使用顺序的connectionlimit或者表达式指定分片的connectionlimit
        int maxShardingQueryLimit = streamMergeContext.getMaxShardingQueryLimit();
        String dataSourceName = sqlGroups.key();
        List<ExecutionUnit> sqlGroupExecutionUnits = sqlGroups.values().collect(Collectors.toList());
        int groupUnitSize = sqlGroupExecutionUnits.size();
        ConnectionModeEnum useConnectionMode = streamMergeContext.getConnectionMode();
        //串行执行insert update delete或者最大连接数大于每个数据源分库的执行数目
        ConnectionModeEnum connectionMode = EasyShardingUtil.getActualConnectionMode(isSerialExecute, maxShardingQueryLimit, groupUnitSize, useConnectionMode);
//        ConnectionModeEnum connectionMode = (isSerialExecute || maxShardingQueryLimit >= groupUnitSize)
//                ? ConnectionModeEnum.MEMORY_STRICTLY
//                : ConnectionModeEnum.CONNECTION_STRICTLY;

        //如果是串行执行就是说每个组只有1个,如果是不是并行每个组有最大执行个数个
        int parallelCount = isSerialExecute ? 1 : Math.min(groupUnitSize,maxShardingQueryLimit);

        List<List<ExecutionUnit>> sqlUnitPartitions = EasyCollectionUtil.partition(sqlGroupExecutionUnits, parallelCount);
        //由于分组后除了最后一个元素其余元素都满足parallelCount为最大,第一个元素的分组数将是实际的创建连接数
        int createDbConnectionCount = EasyCollectionUtil.first(sqlUnitPartitions).size();
        List<EasyConnection> easyConnections = streamMergeContext.getEasyConnections(dataSourceName, createDbConnectionCount);
        //将SQLExecutorUnit进行分区,每个区parallelCount个
        //[1,2,3,4,5,6,7],parallelCount=3,结果就是[[1,2,3],[4,5,6],[7]]
        List<List<CommandExecuteUnit>> sqlExecutorUnitPartitions = EasyCollectionUtil.select(sqlUnitPartitions, (executionUnits, index0) -> {
            return EasyCollectionUtil.select(executionUnits, (executionUnit, index1) -> {
                EasyConnection easyConnection = easyConnections.get(index1);
                return new CommandExecuteUnit(executionUnit, easyConnection);
            });
        });
        List<SQLExecutorGroup<CommandExecuteUnit>> sqlExecutorGroups = EasyCollectionUtil.select(sqlExecutorUnitPartitions, (o, i) -> new SQLExecutorGroup<CommandExecuteUnit>(connectionMode, o));
        return new DataSourceSQLExecutorUnit(dataSourceName,connectionMode, sqlExecutorGroups);

    }

    /**
     * 单条sql执行时创建单个执行单元
     *
     * @param streamMergeContext
     * @param executionUnit
     * @return
     */
    private static DataSourceSQLExecutorUnit getSingleSQLExecutorGroups(StreamMergeContext streamMergeContext, ExecutionUnit executionUnit) {

        ConnectionModeEnum connectionMode = ConnectionModeEnum.MEMORY_STRICTLY;
        String dataSourceName = executionUnit.getDataSourceName();
        List<EasyConnection> easyConnections = streamMergeContext.getEasyConnections(dataSourceName, 1);
        EasyConnection easyConnection = EasyCollectionUtil.first(easyConnections);
        CommandExecuteUnit commandExecuteUnit = new CommandExecuteUnit(executionUnit, easyConnection);
        SQLExecutorGroup<CommandExecuteUnit> sqlExecutorGroup = new SQLExecutorGroup<>(connectionMode, Collections.singletonList(commandExecuteUnit));

        return new DataSourceSQLExecutorUnit(dataSourceName,connectionMode, Collections.singletonList(sqlExecutorGroup));

    }
}
