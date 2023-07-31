package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcResult;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/7/31 21:13
 * 文件说明
 *
 * @author xuejiaming
 */
public final class JdbcResultWrap<TR> {
    private final ExecuteMethodEnum executeMethod;
    private final ExpressionContext expressionContext;
    private final EntityMetadata entityMetadata;
    private final JdbcResult<TR> jdbcResult;

    public JdbcResultWrap(ExecuteMethodEnum executeMethod, ExpressionContext expressionContext, EntityMetadata entityMetadata, JdbcResult<TR> jdbcResult){
        this.executeMethod = executeMethod;
        this.expressionContext = expressionContext;
        this.entityMetadata = entityMetadata;

        this.jdbcResult = jdbcResult;
    }

    public ExecuteMethodEnum getExecuteMethod() {
        return executeMethod;
    }

    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public JdbcResult<TR> getJdbcResult() {
        return jdbcResult;
    }
}
