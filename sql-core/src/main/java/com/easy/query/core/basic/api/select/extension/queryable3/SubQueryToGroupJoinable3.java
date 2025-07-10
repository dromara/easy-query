package com.easy.query.core.basic.api.select.extension.queryable3;

import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.expression.parser.core.base.many.SubQueryProperty;
import com.easy.query.core.expression.parser.core.base.many.SubQueryPropertySelector;

/**
 * create time 2025/3/8 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryToGroupJoinable3<T1, T2, T3> {
    default ClientQueryable3<T1,T2, T3> subQueryToGroupJoin(SQLFuncExpression3<SubQueryPropertySelector, SubQueryPropertySelector, SubQueryPropertySelector, SubQueryProperty> manyPropColumnExpression) {
        return subQueryToGroupJoin(true, manyPropColumnExpression);
    }

    ClientQueryable3<T1, T2, T3> subQueryToGroupJoin(boolean condition, SQLFuncExpression3<SubQueryPropertySelector, SubQueryPropertySelector, SubQueryPropertySelector, SubQueryProperty> manyPropColumnExpression);
}
