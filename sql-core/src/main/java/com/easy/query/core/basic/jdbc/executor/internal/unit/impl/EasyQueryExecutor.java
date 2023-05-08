package com.easy.query.core.basic.jdbc.executor.internal.unit.impl;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.result.impl.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.merger.ShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.unit.abstraction.AbstractExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.breaker.AnyCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.breaker.AnyElementCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.breaker.CircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.breaker.ListCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.breaker.NoCircuitBreaker;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SqlUnit;
import com.easy.query.core.basic.jdbc.executor.internal.merger.impl.QueryStreamShardingMerger;
import com.easy.query.core.util.JdbcExecutorUtil;

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
    protected QueryExecuteResult executeCommandUnit(CommandExecuteUnit commandExecuteUnit) {

        ExecutorContext executorContext = streamMergeContext.getExecutorContext();
        EasyConnection easyConnection = commandExecuteUnit.getEasyConnection();
        SqlUnit sqlUnit = commandExecuteUnit.getExecutionUnit().getSqlUnit();
        String sql = sqlUnit.getSql();
        List<SQLParameter> parameters = sqlUnit.getParameters();
        boolean isSharding = streamMergeContext.isSharding();
        return JdbcExecutorUtil.query(executorContext,easyConnection,sql,parameters,isSharding);
    }

    @Override
    protected CircuitBreaker createCircuitBreak() {
        ExecuteMethodEnum executeMethod = streamMergeContext.getExecuteMethod();
        switch (executeMethod){
            case FIRST:return new AnyElementCircuitBreaker(streamMergeContext);
            case LIST:return new ListCircuitBreaker(streamMergeContext);
            case ANY:return new AnyCircuitBreaker(streamMergeContext);
            default:return NoCircuitBreaker.getInstance();
        }
    }


    @Override
    public ShardingMerger<QueryExecuteResult> getShardingMerger() {
        return QueryStreamShardingMerger.getInstance();
    }
}
