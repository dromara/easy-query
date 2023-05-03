package com.easy.query.core.basic.jdbc.executor.internal.unit.impl;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.internal.result.impl.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.merger.ShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.unit.abstraction.AbstractExecutor;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SqlUnit;
import com.easy.query.core.basic.jdbc.executor.internal.merger.impl.AffectedRowsShardingMerger;
import com.easy.query.core.util.JdbcExecutorUtil;

import java.util.List;

/**
 * create time 2023/4/21 16:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyInsertExecutor extends AbstractExecutor<AffectedRowsExecuteResult> {
    public EasyInsertExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected AffectedRowsExecuteResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit) {
        ExecutorContext executorContext = streamMergeContext.getExecutorContext();
        EasyConnection easyConnection = commandExecuteUnit.getEasyConnection();
        SqlUnit sqlUnit = commandExecuteUnit.getExecutionUnit().getSqlUnit();
        String sql = sqlUnit.getSql();
        List<Object> entities = sqlUnit.getEntities();
        List<SQLParameter> parameters = sqlUnit.getParameters();
        boolean fillAutoIncrement = sqlUnit.isFillAutoIncrement();
        return JdbcExecutorUtil.insert(executorContext,easyConnection,sql,entities,parameters,fillAutoIncrement);
    }
    @Override
    public ShardingMerger<AffectedRowsExecuteResult> getShardingMerger() {
        return AffectedRowsShardingMerger.getInstance();
    }
}
