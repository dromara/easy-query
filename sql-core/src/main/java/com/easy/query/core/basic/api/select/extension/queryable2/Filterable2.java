package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable2<T1,T2> {
    default ClientQueryable2<T1, T2> where(SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable2<T1, T2> where(boolean condition, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> whereExpression);


    default ClientQueryable2<T1, T2> whereMerge(SQLExpression1<Tuple2<WherePredicate<T1>, WherePredicate<T2>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable2<T1, T2> whereMerge(boolean condition, SQLExpression1<Tuple2<WherePredicate<T1>, WherePredicate<T2>>> whereExpression) {
        return where(condition, (t, t1) -> {
            whereExpression.apply(new Tuple2<>(t, t1));
        });
    }
}
