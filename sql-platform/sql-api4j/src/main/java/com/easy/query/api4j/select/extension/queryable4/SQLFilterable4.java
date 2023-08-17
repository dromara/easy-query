package com.easy.query.api4j.select.extension.queryable4;

import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable4<T1,T2,T3,T4> extends ClientQueryable4Available<T1,T2,T3,T4>, Queryable4Available<T1,T2,T3,T4> {

    default Queryable4<T1, T2,T3,T4> where(SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> whereExpression) {
        getClientQueryable4().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4));
        });
        return getQueryable4();
    }

    default Queryable4<T1, T2,T3,T4> where(boolean condition, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> whereExpression) {
        getClientQueryable4().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4));
        });
        return getQueryable4();
    }

    default Queryable4<T1, T2,T3,T4> whereMerge(SQLExpression1<Tuple4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default Queryable4<T1, T2,T3,T4> whereMerge(boolean condition, SQLExpression1<Tuple4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>>> whereExpression) {
        return where(condition, (t, t1,t3,t4) -> {
            whereExpression.apply(new Tuple4<>(t, t1,t3,t4));
        });
    }
}
