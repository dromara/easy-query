package com.easy.query.core.basic.api.select.extension.queryable3;

import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable3<T1, T2, T3> {
    default ClientQueryable3<T1, T2, T3> where(SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable3<T1, T2, T3> where(boolean condition, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> whereExpression);


    default ClientQueryable3<T1, T2, T3> whereMerge(SQLExpression1<Tuple3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable3<T1, T2, T3> whereMerge(boolean condition, SQLExpression1<Tuple3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>>> whereExpression) {
        return where(condition, (t1, t2,t3) -> {
            whereExpression.apply(new Tuple3<>(t1, t2,t3));
        });
    }
}
