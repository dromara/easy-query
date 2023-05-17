package com.easy.query.core.basic.jdbc.executor.internal.unit.impl;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.sharding.merger.ShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.unit.abstraction.AbstractExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.CircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.NoCircuitBreaker;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRouteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.sharding.merger.impl.AffectedRowsShardingMerger;
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
        SQLRouteUnit sqlRouteUnit = executionUnit.getSQLRouteUnit();
        SQLUnit sqlUnit = sqlRouteUnit.getSQLUnit();
        String sql = sqlUnit.getSQL();
        List<Object> entities = sqlUnit.getEntities();
        List<SQLParameter> parameters = sqlUnit.getParameters();
        boolean fillAutoIncrement = sqlUnit.isFillAutoIncrement();
        boolean isSharding = streamMergeContext.isSharding();
        boolean configReplica = streamMergeContext.configReplica();
        int rows = JdbcExecutorUtil.insert(executorContext, easyConnection, sql, entities, parameters, fillAutoIncrement,isSharding,configReplica);
        return new AffectedRowsExecuteResult(rows);
    }

    @Override
    protected CircuitBreaker createCircuitBreak() {
        return NoCircuitBreaker.INSTANCE;
    }

    @Override
    public ShardingMerger<AffectedRowsExecuteResult> getShardingMerger() {
        return AffectedRowsShardingMerger.getInstance();
    }
}
