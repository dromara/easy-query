package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.base.many.ManyColumn;
import com.easy.query.core.expression.parser.core.base.many.ManyJoinSelector;

/**
 * create time 2025/3/8 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ManyJoinable2<T1, T2> {
    default ClientQueryable2<T1,T2> manyJoin(SQLFuncExpression2<ManyJoinSelector<T1>,ManyJoinSelector<T2>, ManyColumn> manyPropColumnExpression) {
        return manyJoin(true, manyPropColumnExpression);
    }

    ClientQueryable2<T1, T2> manyJoin(boolean condition, SQLFuncExpression2<ManyJoinSelector<T1>,ManyJoinSelector<T2>, ManyColumn> manyPropColumnExpression);
}
