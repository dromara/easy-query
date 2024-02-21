package com.easy.query.core.metadata;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression;

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
}
