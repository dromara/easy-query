package com.easy.query.core.basic.jdbc.executor.internal.unit.abstraction;

import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.CircuitBreaker;
import com.easy.query.core.basic.thread.EasyShardingExecutorService;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryTimeoutException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.DataSourceSqlExecutorUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SqlExecutorGroup;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
    public List<TResult> execute(DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit) throws SQLException {
        try {
            return execute0(dataSourceSqlExecutorUnit);

        } catch (Throwable throwable) {
            stop();
            throw throwable;
        }
    }

    private List<TResult> execute0(DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit) throws SQLException {
        List<SqlExecutorGroup<CommandExecuteUnit>> executorGroups = dataSourceSqlExecutorUnit.getSqlExecutorGroups();
        int count = EasyCollectionUtil.sum(executorGroups, o -> o.getGroups().size());
        CircuitBreaker circuitBreak = createCircuitBreak();
        List<TResult> result = new ArrayList<>(count);
        //同数据库下多组数据间采用串行
        Iterator<SqlExecutorGroup<CommandExecuteUnit>> iterator = executorGroups.iterator();
        while (iterator.hasNext()) {
            SqlExecutorGroup<CommandExecuteUnit> executorGroup = iterator.next();
            Collection<TResult> results = groupExecute(executorGroup.getGroups());
            if (Objects.equals(ConnectionModeEnum.CONNECTION_STRICTLY, dataSourceSqlExecutorUnit.getConnectionMode())) {
                getShardingMerger().inMemoryMerge(streamMergeContext, result, results);
            } else {
                result.addAll(results);
            }
            //是否还有下次循环
            if (iterator.hasNext()) {
                if (isStopped() || circuitBreak.terminated(streamMergeContext, result)) {
                    break;
                }
            }
        }
        return result;
    }

    private Collection<TResult> groupExecute(List<CommandExecuteUnit> commandExecuteUnits) {
        if (EasyCollectionUtil.isEmpty(commandExecuteUnits)) {
            return Collections.emptyList();
        }
        if (commandExecuteUnits.size() == 1) {
            TResult result = executeCommandUnit(commandExecuteUnits.get(0));
            return Collections.singletonList(result);
        } else {
            EasyShardingExecutorService easyShardingExecutorService = streamMergeContext.getRuntimeContext().getEasyShardingExecutorService();
            List<TResult> resultList = new ArrayList<>(commandExecuteUnits.size());
            ArrayList<Future<TResult>> tasks = new ArrayList<>(commandExecuteUnits.size());
            for (CommandExecuteUnit commandExecuteUnit : commandExecuteUnits) {
                Future<TResult> task = easyShardingExecutorService.getExecutorService().submit(() -> executeCommandUnit(commandExecuteUnit));
                tasks.add(task);
            }

            try {

                for (Future<TResult> task : tasks) {
                    resultList.add(task.get(60L, TimeUnit.SECONDS));
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("group execute error.", e);
                throw new EasyQueryException(e);
            } catch (TimeoutException e) {
                throw new EasyQueryTimeoutException(e);
            }
            return resultList;
        }
    }

    protected abstract TResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit);

    protected abstract CircuitBreaker createCircuitBreak();
}
