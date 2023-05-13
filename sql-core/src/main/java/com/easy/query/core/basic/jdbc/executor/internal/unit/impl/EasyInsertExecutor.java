package com.easy.query.core.basic.jdbc.executor.internal.unit.impl;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SqlUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.merger.ShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.unit.abstraction.AbstractExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.CircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.NoCircuitBreaker;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SqlRouteUnit;
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
        ExecutionUnit executionUnit = commandExecuteUnit.getExecutionUnit();
        String dataSourceName = executionUnit.getDataSourceName();
        SqlRouteUnit sqlRouteUnit = executionUnit.getSqlRouteUnit();
        SqlUnit sqlUnit = sqlRouteUnit.getSqlUnit();
        String sql = sqlUnit.getSql();
        List<Object> entities = sqlUnit.getEntities();
        List<SQLParameter> parameters = sqlUnit.getParameters();
        boolean fillAutoIncrement = sqlUnit.isFillAutoIncrement();
        int rows = JdbcExecutorUtil.insert(dataSourceName, executorContext, easyConnection, sql, entities, parameters, fillAutoIncrement);
        return new AffectedRowsExecuteResult(rows);
    }

    @Override
    protected CircuitBreaker createCircuitBreak() {
        return NoCircuitBreaker.getInstance();
    }

    @Override
    public ShardingMerger<AffectedRowsExecuteResult> getShardingMerger() {
        return AffectedRowsShardingMerger.getInstance();
    }
}
