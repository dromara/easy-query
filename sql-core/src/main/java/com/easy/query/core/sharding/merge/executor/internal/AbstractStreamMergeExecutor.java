package com.easy.query.core.sharding.merge.executor.internal;

import com.easy.query.core.basic.thread.EasyShardingExecutorService;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.merge.executor.common.CommandExecuteUnit;
import com.easy.query.core.sharding.merge.executor.common.DataSourceSqlExecutorUnit;
import com.easy.query.core.sharding.merge.executor.common.SqlExecutorGroup;
import com.easy.query.core.util.ArrayUtil;
import com.mysql.cj.protocol.Protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/4/13 22:43
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractStreamMergeExecutor implements Executor {
    private static final Log log= LogFactory.getLog(AbstractStreamMergeExecutor.class);
    protected final EasyShardingExecutorService easyShardingExecutorService;

    public AbstractStreamMergeExecutor(EasyShardingExecutorService easyShardingExecutorService){

        this.easyShardingExecutorService = easyShardingExecutorService;
    }

    @Override
    public List<ExecuteResult> execute(DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit) {
        return execute0(dataSourceSqlExecutorUnit);
    }
    private List<ExecuteResult> execute0(DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit){
        List<SqlExecutorGroup<CommandExecuteUnit>> sqlExecutorGroups = dataSourceSqlExecutorUnit.getSqlExecutorGroups();
        int count = ArrayUtil.sum(sqlExecutorGroups, o -> o.getGroups().size());
        List<ExecuteResult> result = new ArrayList<>(count);
        for (SqlExecutorGroup<CommandExecuteUnit> sqlExecutorGroup : sqlExecutorGroups) {
            Collection<ExecuteResult> results = groupExecute(sqlExecutorGroup.getGroups());
            result.addAll(results);
        }
        return result;
    }
    private Collection<ExecuteResult> groupExecute(List<CommandExecuteUnit> commandExecuteUnits){
        if(ArrayUtil.isEmpty(commandExecuteUnits)){
            return Collections.emptyList();
        }
        if(commandExecuteUnits.size()==1){
            ExecuteResult result = executeCommandUnit(commandExecuteUnits.get(0));
            return Collections.singletonList(result);
        }else{
            List<ExecuteResult> resultList = new ArrayList<>(commandExecuteUnits.size());
            ArrayList<Callable<ExecuteResult>> tasks = new ArrayList<>(commandExecuteUnits.size());
            for (CommandExecuteUnit commandExecuteUnit : commandExecuteUnits) {
                Callable<ExecuteResult> task=()->executeCommandUnit(commandExecuteUnit);
                tasks.add(task);
            }

            try {
                List<Future<ExecuteResult>> futures = easyShardingExecutorService.getExecutorService().invokeAll(tasks,60, TimeUnit.SECONDS);
                for (Future<ExecuteResult> future : futures) {
                    resultList.add(future.get());
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("group execute error.",e);
                throw new EasyQueryException(e);
            }
            return resultList;
        }
    }
    protected abstract ExecuteResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit);
}
