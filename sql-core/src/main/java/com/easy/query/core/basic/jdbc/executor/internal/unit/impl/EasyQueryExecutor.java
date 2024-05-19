package com.easy.query.core.basic.jdbc.executor.internal.unit.impl;

import com.easy.query.core.basic.jdbc.conn.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRouteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.result.impl.DefaultQueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.sharding.merger.ShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.sharding.merger.impl.QueryStreamShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.unit.abstraction.AbstractExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.AllCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.AnyCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.AnyElementCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.CircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.ListCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.NoCircuitBreaker;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.util.EasyJdbcExecutorUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/4/14 11:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryExecutor extends AbstractExecutor<QueryExecuteResult> {

    public EasyQueryExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected QueryExecuteResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit) throws SQLException {

        ExecutorContext executorContext = streamMergeContext.getExecutorContext();

        EasyConnection easyConnection = commandExecuteUnit.getEasyConnection();
        ExecutionUnit executionUnit = commandExecuteUnit.getExecutionUnit();
        SQLRouteUnit sqlRouteUnit = executionUnit.getSQLRouteUnit();
        String sql = sqlRouteUnit.getSQL();
        List<SQLParameter> parameters = sqlRouteUnit.getParameters();
        boolean isSharding = streamMergeContext.isSharding();
        boolean configReplica = streamMergeContext.configReplica();
        StreamResultSet streamResultSet = EasyJdbcExecutorUtil.query( executorContext, easyConnection, sql, parameters, isSharding,configReplica);

        return new DefaultQueryExecuteResult(streamResultSet);
    }

    @Override
    protected CircuitBreaker createCircuitBreak() {
        ExecuteMethodEnum executeMethod = streamMergeContext.getExecuteMethod();
        //todo single熔断
        switch (executeMethod){
            case FIRST:
            case MAX:
            case MIN:
                return AnyElementCircuitBreaker.INSTANCE;
            case LIST:return ListCircuitBreaker.INSTANCE;
            case ANY:return AnyCircuitBreaker.INSTANCE;
//            case ALL:return AllCircuitBreaker.INSTANCE;
            default:return NoCircuitBreaker.INSTANCE;
        }
    }


    @Override
    public ShardingMerger<QueryExecuteResult> getShardingMerger() {
        return QueryStreamShardingMerger.INSTANCE;
    }
}
