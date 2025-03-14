package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.many.ManyColumn;
import com.easy.query.core.expression.parser.core.base.many.ManyJoinSelector;

/**
 * create time 2025/3/8 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ManyJoinable1<T1> {
    default ClientQueryable<T1> manyJoin(SQLFuncExpression1<ManyJoinSelector<T1>, ManyColumn> manyPropColumnExpression) {
        return manyJoin(true, manyPropColumnExpression);
    }

    ClientQueryable<T1> manyJoin(boolean condition, SQLFuncExpression1<ManyJoinSelector<T1>, ManyColumn> manyPropColumnExpression);
}
