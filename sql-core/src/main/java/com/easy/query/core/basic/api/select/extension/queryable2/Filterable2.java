package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.common.tuple.EasyTuple2;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable2<T1,T2> {
    default ClientQueryable2<T1, T2> where(SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable2<T1, T2> where(boolean condition, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> whereExpression);


    default ClientQueryable2<T1, T2> whereMerge(SQLActionExpression1<EasyTuple2<WherePredicate<T1>, WherePredicate<T2>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable2<T1, T2> whereMerge(boolean condition, SQLActionExpression1<EasyTuple2<WherePredicate<T1>, WherePredicate<T2>>> whereExpression) {
        return where(condition, (t1, t2) -> {
            whereExpression.apply(new EasyTuple2<>(t1, t2));
        });
    }
}
