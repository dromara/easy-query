package com.easy.query.core.basic.jdbc.executor.internal.unit.impl;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.impl.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.merger.ShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.unit.abstraction.AbstractExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.AllCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.AnyCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.AnyElementCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.CircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.ListCircuitBreaker;
import com.easy.query.core.basic.jdbc.executor.internal.unit.breaker.NoCircuitBreaker;
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
        ExecutionUnit executionUnit = commandExecuteUnit.getExecutionUnit();
        String dataSourceName = executionUnit.getDataSourceName();
        SqlUnit sqlUnit = executionUnit.getSqlUnit();
        String sql = sqlUnit.getSql();
        List<SQLParameter> parameters = sqlUnit.getParameters();
        boolean isSharding = streamMergeContext.isSharding();
        return JdbcExecutorUtil.query(dataSourceName,executorContext,easyConnection,sql,parameters,isSharding);
    }

    @Override
    protected CircuitBreaker createCircuitBreak() {
        ExecuteMethodEnum executeMethod = streamMergeContext.getExecuteMethod();
        switch (executeMethod){
            case FIRST:
            case MAX:
            case MIN:
                return AnyElementCircuitBreaker.getInstance();
            case LIST:return ListCircuitBreaker.getInstance();
            case ANY:return AnyCircuitBreaker.getInstance();
            case ALL:return AllCircuitBreaker.getInstance();
            default:return NoCircuitBreaker.getInstance();
        }
    }


    @Override
    public ShardingMerger<QueryExecuteResult> getShardingMerger() {
        return QueryStreamShardingMerger.getInstance();
    }
}
