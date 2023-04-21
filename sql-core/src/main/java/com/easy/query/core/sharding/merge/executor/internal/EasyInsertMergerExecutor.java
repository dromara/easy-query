package com.easy.query.core.sharding.merge.executor.internal;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.executor.common.CommandExecuteUnit;
import com.easy.query.core.sharding.merge.executor.common.SqlUnit;
import com.easy.query.core.sharding.merge.executor.merger.DefaultStreamShardingMerger;
import com.easy.query.core.util.JDBCExecutorUtil;

import java.util.List;

/**
 * create time 2023/4/21 16:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyInsertMergerExecutor extends AbstractExecutor<ExecuteResult>{
    public EasyInsertMergerExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected ExecuteResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit) {
        ExecutorContext executorContext = streamMergeContext.getExecutorContext();
        EasyConnection easyConnection = commandExecuteUnit.getEasyConnection();
        SqlUnit sqlUnit = commandExecuteUnit.getExecutionUnit().getSqlUnit();
        String sql = sqlUnit.getSql();
        List<Object> entities = sqlUnit.getEntities();
        List<SQLParameter> parameters = sqlUnit.getParameters();
        boolean fillAutoIncrement = sqlUnit.isFillAutoIncrement();
        return JDBCExecutorUtil.insert(executorContext,easyConnection,sql,entities,parameters,fillAutoIncrement);
    }
    @Override
    public ShardingMerger<ExecuteResult> getShardingMerger() {
        return DefaultStreamShardingMerger.getInstance();
    }
}
