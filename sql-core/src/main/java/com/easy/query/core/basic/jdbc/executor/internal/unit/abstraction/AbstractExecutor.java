package com.easy.query.core.basic.jdbc.executor.internal.unit.abstraction;

import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.DataSourceSQLExecutorUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLExecutorGroup;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.CircuitBreaker;
import com.easy.query.core.basic.thread.FuturesInvoker;
import com.easy.query.core.basic.thread.ShardingExecutorService;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryTimeoutSQLException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * create time 2023/4/13 22:43
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractExecutor<TResult> implements Executor<TResult> {
    private static final Log log = LogFactory.getLog(AbstractExecutor.class);
    protected final StreamMergeContext streamMergeContext;
    private volatile boolean stopped = false;

    public AbstractExecutor(StreamMergeContext streamMergeContext) {
        this.streamMergeContext = streamMergeContext;
    }

    private void stop() {
        stopped = true;
    }

    private boolean isStopped() {
        return stopped;
    }

    @Override
    public List<TResult> execute(DataSourceSQLExecutorUnit dataSourceSQLExecutorUnit) throws SQLException {
        try {
            return execute0(dataSourceSQLExecutorUnit);

        } catch (Throwable throwable) {
            stop();
            throw throwable;
        }
    }

    private List<TResult> execute0(DataSourceSQLExecutorUnit dataSourceSQLExecutorUnit) throws SQLException {
        List<SQLExecutorGroup<CommandExecuteUnit>> executorGroups = dataSourceSQLExecutorUnit.getSQLExecutorGroups();

        long timeoutMillis = streamMergeContext.getEasyQueryOption().getShardingExecuteTimeoutMillis();
        if(!streamMergeContext.isSharding()|| EasyCollectionUtil.isSingle(executorGroups)){
            ArrayList<TResult> tResults = new ArrayList<>();
            for (SQLExecutorGroup<CommandExecuteUnit> executorGroup : executorGroups) {
                List<TResult> r = executeSingle0(executorGroup, timeoutMillis);
                tResults.addAll(r);
            }
            return tResults;
        }
        return executeMulti0(dataSourceSQLExecutorUnit,timeoutMillis);
    }

    private List<TResult> executeSingle0(SQLExecutorGroup<CommandExecuteUnit> executorGroup, long timeoutMillis) throws SQLException {
        List<CommandExecuteUnit> groups = executorGroup.getGroups();
        Collection<TResult> tResults = groupExecute(groups, timeoutMillis);
        return new ArrayList<>(tResults);
    }
    private List<TResult> executeMulti0(DataSourceSQLExecutorUnit dataSourceSQLExecutorUnit,long timeoutMillis) throws SQLException {

        List<SQLExecutorGroup<CommandExecuteUnit>> executorGroups = dataSourceSQLExecutorUnit.getSQLExecutorGroups();
        int count = EasyCollectionUtil.sum(executorGroups, o -> o.getGroups().size());
        CircuitBreaker circuitBreak = createCircuitBreak();
        List<TResult> result = new ArrayList<>(count);
        //性能提升防止每次都获取当前时间
        long start = System.currentTimeMillis();
        long constTime = 0L;
        //同数据库下多组数据间采用串行
        Iterator<SQLExecutorGroup<CommandExecuteUnit>> iterator = executorGroups.iterator();
        while (iterator.hasNext()) {
            if (timeoutMillis < constTime) {
                throw new EasyQueryTimeoutSQLException("sharding execute time out:" + dataSourceSQLExecutorUnit.getDataSourceName());
            }
            SQLExecutorGroup<CommandExecuteUnit> executorGroup = iterator.next();
            Collection<TResult> results = groupExecute(executorGroup.getGroups(), timeoutMillis - constTime);
            if (Objects.equals(ConnectionModeEnum.CONNECTION_STRICTLY, dataSourceSQLExecutorUnit.getConnectionMode())) {
                getShardingMerger().inMemoryMerge(streamMergeContext, result, results);
            } else {
                result.addAll(results);
            }
            //是否还有下次循环
            if (iterator.hasNext()) {
                if (isStopped() || circuitBreak.terminated(streamMergeContext, result)) {
                    break;
                }
                constTime = System.currentTimeMillis() - start;
            }
        }
        return result;
    }

    private Collection<TResult> groupExecute(List<CommandExecuteUnit> commandExecuteUnits, long timeoutMillis) throws SQLException {
        if (EasyCollectionUtil.isEmpty(commandExecuteUnits)) {
            return Collections.emptyList();
        }
        if (EasyCollectionUtil.isSingle(commandExecuteUnits)) {
            TResult result = executeCommandUnit(EasyCollectionUtil.first(commandExecuteUnits));
            return Collections.singletonList(result);
        } else {
            ShardingExecutorService easyShardingExecutorService = streamMergeContext.getRuntimeContext().getShardingExecutorService();

            List<Future<TResult>> tasks = new ArrayList<>(commandExecuteUnits.size());
            for (CommandExecuteUnit commandExecuteUnit : commandExecuteUnits) {
                Future<TResult> task = easyShardingExecutorService.getExecutorService().submit(() -> executeCommandUnit(commandExecuteUnit));
                tasks.add(task);
            }

            try (FuturesInvoker<TResult> invoker = new FuturesInvoker<>(tasks)) {
                return invoker.get(timeoutMillis);
            }
        }
    }

    protected abstract TResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit) throws SQLException;

    protected abstract CircuitBreaker createCircuitBreak();
}
