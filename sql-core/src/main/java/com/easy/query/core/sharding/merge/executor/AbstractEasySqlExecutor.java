package com.easy.query.core.sharding.merge.executor;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryTimeoutException;
import com.easy.query.core.sharding.EasyShardingOption;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.executor.common.CommandExecuteUnit;
import com.easy.query.core.sharding.merge.executor.common.DataSourceSqlExecutorUnit;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.common.Grouping;
import com.easy.query.core.sharding.merge.executor.common.SqlExecutorGroup;
import com.easy.query.core.sharding.merge.executor.common.SqlUnit;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;
import com.easy.query.core.sharding.merge.executor.internal.ExecuteResult;
import com.easy.query.core.sharding.merge.executor.internal.Executor;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.EasyUtil;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * create time 2023/4/13 21:55
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEasySqlExecutor implements EasySqlExecutor {
    protected final StreamMergeContext streamMergeContext;
    protected final ExecutorService executorService;
    protected final EasyShardingOption easyShardingOption;

    public AbstractEasySqlExecutor(StreamMergeContext streamMergeContext) {
        this.streamMergeContext = streamMergeContext;
        this.executorService = streamMergeContext.getRuntimeContext().getEasyShardingExecutorService().getExecutorService();
        this.easyShardingOption=streamMergeContext.getRuntimeContext().getEasyShardingOption();
    }

    protected abstract ExecutorResult merge(ExecuteResult executeResult);

    @Override
    public ExecutorResult execute() {
        Executor executor = streamMergeContext.getRuntimeContext().getExecutor();
        ExecuteResult executeResult = execute(executor);
        return merge(executeResult);
    }

    private ExecuteResult execute(Executor executor) {

        List<Future<List<ExecuteResult>>> futures = execute0(executor);
        List<ExecuteResult> results = new ArrayList<>(futures.size()*easyShardingOption.getMaxQueryConnectionsLimit());
        for (Future<List<ExecuteResult>> future : futures) {
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

    private List<Future<List<ExecuteResult>>> execute0(Executor executor) {
        List<ExecutionUnit> executionUnits = streamMergeContext.getExecutionUnits();
        List<DataSourceSqlExecutorUnit> dataSourceSqlExecutorUnits = EasyUtil.groupBy(executionUnits, ExecutionUnit::getDataSourceName, this::getSqlExecutorGroups);
        return ArrayUtil.select(dataSourceSqlExecutorUnits, (o, i) -> executorService.submit(() -> executor.execute(o)));
    }

    private DataSourceSqlExecutorUnit getSqlExecutorGroups(Grouping<String, ExecutionUnit> sqlGroups) {
        boolean isSerialExecute = streamMergeContext.isSerialExecute();
        int maxQueryConnectionsLimit = easyShardingOption.getMaxQueryConnectionsLimit();
        String dataSourceName = sqlGroups.getKey();
        int sqlCount = sqlGroups.getValues().size();
        //串行执行insert update delete或者最大连接数大于每个数据源分库的执行数目
        ConnectionModeEnum connectionMode = (isSerialExecute || maxQueryConnectionsLimit >= sqlCount)
                ? ConnectionModeEnum.MEMORY_STRICTLY
                : ConnectionModeEnum.CONNECTION_STRICTLY;

        //如果是串行执行就是说每个组只有1个,如果是不是并行每个组有最大执行个数个
        int parallelCount = isSerialExecute ? 1 : maxQueryConnectionsLimit;

        List<List<ExecutionUnit>> sqlUnitPartitions = ArrayUtil.partition(sqlGroups.getValues(), parallelCount);
        //由于分组后除了最后一个元素其余元素都满足parallelCount为最大,第一个元素的分组数将是实际的创建连接数
        int createDbConnectionCount = sqlUnitPartitions.get(0).size();
        List<EasyConnection> easyConnections = dbConnections(connectionMode, dataSourceName, createDbConnectionCount);
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

    protected abstract PreparedStatement createPreparedStatement(SqlUnit sqlUnit,EasyConnection easyConnection);
    protected abstract List<EasyConnection> dbConnections(ConnectionModeEnum connectionMode, String dataSourceName, int connectionSize);
}
