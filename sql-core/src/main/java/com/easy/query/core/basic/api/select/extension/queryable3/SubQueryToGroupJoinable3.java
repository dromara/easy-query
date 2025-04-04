package com.easy.query.core.basic.api.select.extension.queryable3;

import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.expression.parser.core.base.many.ManyColumn;
import com.easy.query.core.expression.parser.core.base.many.ManyJoinSelector;

/**
 * create time 2025/3/8 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryToGroupJoinable3<T1, T2, T3> {
    default ClientQueryable3<T1,T2, T3> subQueryToGroupJoin(SQLFuncExpression3<ManyJoinSelector<T1>,ManyJoinSelector<T2>,ManyJoinSelector<T3>, ManyColumn> manyPropColumnExpression) {
        return subQueryToGroupJoin(true, manyPropColumnExpression);
    }

    ClientQueryable3<T1, T2, T3> subQueryToGroupJoin(boolean condition, SQLFuncExpression3<ManyJoinSelector<T1>,ManyJoinSelector<T2>,ManyJoinSelector<T3>, ManyColumn> manyPropColumnExpression);
}
