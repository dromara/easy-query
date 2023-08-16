package com.easy.query.core.basic.api.select.extension.queryable4;

import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLWhere4Extension<T1, T2, T3,T4> {
    default ClientQueryable4<T1, T2, T3,T4> where(SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable4<T1, T2, T3,T4> where(boolean condition, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> whereExpression);


    default ClientQueryable4<T1, T2, T3,T4> whereMerge(SQLExpression1<Tuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable4<T1, T2, T3,T4> whereMerge(boolean condition, SQLExpression1<Tuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> whereExpression) {
        return where(condition, (t, t1, t2,t3) -> {
            whereExpression.apply(new Tuple4<>(t, t1, t2,t3));
        });
    }
}
