package com.easy.query.core.basic.api.select.extension.queryable4;

import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.EasyTuple4;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression4;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable4<T1, T2, T3,T4> {
    default ClientQueryable4<T1, T2, T3,T4> where(SQLActionExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable4<T1, T2, T3,T4> where(boolean condition, SQLActionExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> whereExpression);


    default ClientQueryable4<T1, T2, T3,T4> whereMerge(SQLActionExpression1<EasyTuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable4<T1, T2, T3,T4> whereMerge(boolean condition, SQLActionExpression1<EasyTuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> whereExpression) {
        return where(condition, (t1, t2,t3, t4) -> {
            whereExpression.apply(new EasyTuple4<>(t1, t2,t3, t4));
        });
    }
}
