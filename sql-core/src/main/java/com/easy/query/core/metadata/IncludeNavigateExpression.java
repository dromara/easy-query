package com.easy.query.core.metadata;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression;

import java.util.Objects;

/**
 * create time 2024/2/21 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeNavigateExpression {
    private final IncludeNavigateParams includeNavigateParams;
    private final SQLFuncExpression<ClientQueryable<?>> sqlFuncExpression;

    public IncludeNavigateExpression(IncludeNavigateParams includeNavigateParams, SQLFuncExpression<ClientQueryable<?>> sqlFuncExpression){

        this.includeNavigateParams = includeNavigateParams;
        this.sqlFuncExpression = sqlFuncExpression;
    }

    public IncludeNavigateParams getIncludeNavigateParams() {
        return includeNavigateParams;
    }

    public SQLFuncExpression<ClientQueryable<?>> getSqlFuncExpression() {
        return sqlFuncExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncludeNavigateExpression that = (IncludeNavigateExpression) o;
        return Objects.equals(includeNavigateParams, that.includeNavigateParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(includeNavigateParams);
    }
}
