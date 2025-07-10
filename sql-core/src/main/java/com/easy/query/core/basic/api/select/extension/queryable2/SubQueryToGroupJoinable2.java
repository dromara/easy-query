package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.base.many.SubQueryProperty;
import com.easy.query.core.expression.parser.core.base.many.SubQueryPropertySelector;

/**
 * create time 2025/3/8 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryToGroupJoinable2<T1, T2> {
    default ClientQueryable2<T1,T2> subQueryToGroupJoin(SQLFuncExpression2<SubQueryPropertySelector, SubQueryPropertySelector, SubQueryProperty> manyPropColumnExpression) {
        return subQueryToGroupJoin(true, manyPropColumnExpression);
    }

    ClientQueryable2<T1, T2> subQueryToGroupJoin(boolean condition, SQLFuncExpression2<SubQueryPropertySelector, SubQueryPropertySelector, SubQueryProperty> manyPropColumnExpression);
}
