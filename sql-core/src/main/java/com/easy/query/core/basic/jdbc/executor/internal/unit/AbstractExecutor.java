package com.easy.query.core.basic.jdbc.executor.internal.unit;

import com.easy.query.core.basic.thread.EasyShardingExecutorService;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.executor.common.CommandExecuteUnit;
import com.easy.query.core.sharding.merge.executor.common.DataSourceSqlExecutorUnit;
import com.easy.query.core.sharding.merge.executor.common.SqlExecutorGroup;
import com.easy.query.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/4/13 22:43
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractExecutor<TResult> implements Executor<TResult> {
    private static final Log log= LogFactory.getLog(AbstractExecutor.class);
    protected final StreamMergeContext streamMergeContext;

    public AbstractExecutor(StreamMergeContext streamMergeContext){
        this.streamMergeContext = streamMergeContext;
    }

    @Override
    public List<TResult> execute(DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit) {
        return execute0(dataSourceSqlExecutorUnit);
    }
    private List<TResult> execute0(DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit){
        List<SqlExecutorGroup<CommandExecuteUnit>> executorGroups = dataSourceSqlExecutorUnit.getSqlExecutorGroups();
        int count = ArrayUtil.sum(executorGroups, o -> o.getGroups().size());
        List<TResult> result = new ArrayList<>(count);
        int executorGroupsSize = executorGroups.size();
        //同数据库下多组数据间采用串行
        for (SqlExecutorGroup<CommandExecuteUnit> executorGroup : executorGroups) {
            executorGroupsSize--;
            Collection<TResult> results = groupExecute(executorGroup.getGroups());
            result.addAll(results);
        }
        return result;
    }
    private Collection<TResult> groupExecute(List<CommandExecuteUnit> commandExecuteUnits){
        if(ArrayUtil.isEmpty(commandExecuteUnits)){
            return Collections.emptyList();
        }
        if(commandExecuteUnits.size()==1){
            TResult result = executeCommandUnit(commandExecuteUnits.get(0));
            return Collections.singletonList(result);
        }else{
            List<TResult> resultList = new ArrayList<>(commandExecuteUnits.size());
            ArrayList<Callable<TResult>> tasks = new ArrayList<>(commandExecuteUnits.size());
            for (CommandExecuteUnit commandExecuteUnit : commandExecuteUnits) {
                Callable<TResult> task=()->executeCommandUnit(commandExecuteUnit);
                tasks.add(task);
            }

            try {
                EasyShardingExecutorService easyShardingExecutorService = streamMergeContext.getRuntimeContext().getEasyShardingExecutorService();
                List<Future<TResult>> futures = easyShardingExecutorService.getExecutorService().invokeAll(tasks,60, TimeUnit.SECONDS);
                for (Future<TResult> future : futures) {
                    resultList.add(future.get());
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("group execute error.",e);
                throw new EasyQueryException(e);
            }
            return resultList;
        }
    }
    protected abstract TResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit);
}
