package com.easy.query.core.basic.jdbc.executor.internal.unit.impl;

import com.easy.query.core.basic.jdbc.conn.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRouteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.sharding.merger.ShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.sharding.merger.impl.AffectedRowsShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.unit.abstraction.AbstractExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.CircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.NoCircuitBreaker;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.sql.JdbcSQLExecutor;
import com.easy.query.core.util.EasyJdbcExecutorUtil;

import java.sql.SQLException;
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
    protected AffectedRowsExecuteResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit) throws SQLException {
        ExecutorContext executorContext = streamMergeContext.getExecutorContext();
        EasyConnection easyConnection = commandExecuteUnit.getEasyConnection();
        ExecutionUnit executionUnit = commandExecuteUnit.getExecutionUnit();
        SQLRouteUnit sqlRouteUnit = executionUnit.getSQLRouteUnit();
        String sql = sqlRouteUnit.getSQL();
        List<Object> entities = sqlRouteUnit.getEntities();
        List<SQLParameter> parameters = sqlRouteUnit.getParameters();
        boolean fillAutoIncrement = sqlRouteUnit.isFillAutoIncrement();
        boolean isSharding = streamMergeContext.isSharding();
        boolean configReplica = streamMergeContext.configReplica();
        JdbcSQLExecutor jdbcSQLExecutor = executorContext.getRuntimeContext().getJdbcSQLExecutor();
        int rows = jdbcSQLExecutor.insert(executorContext, easyConnection, sql, entities, parameters, fillAutoIncrement,isSharding,configReplica);
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
