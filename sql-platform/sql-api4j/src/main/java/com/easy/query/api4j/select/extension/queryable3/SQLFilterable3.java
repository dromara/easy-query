package com.easy.query.api4j.select.extension.queryable3;

import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable3<T1,T2,T3> extends ClientQueryable3Available<T1,T2,T3>, Queryable3Available<T1,T2,T3> {

    default Queryable3<T1, T2,T3> where(SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> whereExpression) {
        getClientQueryable3().where((wherePredicate1, wherePredicate2, wherePredicate3) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3));
        });
        return getQueryable3();
    }

    default Queryable3<T1, T2,T3> where(boolean condition, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> whereExpression) {
        getClientQueryable3().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3));
        });
        return getQueryable3();
    }

    default Queryable3<T1, T2,T3> whereMerge(SQLExpression1<Tuple3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default Queryable3<T1, T2,T3> whereMerge(boolean condition, SQLExpression1<Tuple3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>>> whereExpression) {
        return where(condition, (t1,t2,t3) -> {
            whereExpression.apply(new Tuple3<>(t1,t2,t3));
        });
    }
}
