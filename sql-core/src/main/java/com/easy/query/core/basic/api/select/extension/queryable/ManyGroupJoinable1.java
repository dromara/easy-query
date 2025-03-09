package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

/**
 * create time 2025/3/8 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ManyGroupJoinable1<T1> {
    default ClientQueryable<T1> manyGroupJoin(SQLFuncExpression<String> manyPropColumnExpression) {
        return manyGroupJoin(true, manyPropColumnExpression);
    }

    default ClientQueryable<T1> manyGroupJoin(SQLFuncExpression<String> manyPropColumnExpression, SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> adapterExpression) {
        return manyGroupJoin(true, manyPropColumnExpression, adapterExpression);
    }

    default ClientQueryable<T1> manyGroupJoin(boolean condition, SQLFuncExpression<String> manyPropColumnExpression) {
        return manyGroupJoin(condition, manyPropColumnExpression, null);
    }

    ClientQueryable<T1> manyGroupJoin(boolean condition, SQLFuncExpression<String> manyPropColumnExpression, SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> adapterExpression);
}
