package com.easy.query.api4j.select.extension.queryable2;

import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable2<T1,T2> extends ClientQueryable2Available<T1,T2>,Queryable2Available<T1,T2> {

    default Queryable2<T1, T2> where(SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> whereExpression) {
        getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return getQueryable2();
    }

    default Queryable2<T1, T2> where(boolean condition, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> whereExpression) {
        getClientQueryable2().where(condition, (wherePredicate1, wherePredicate2) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return getQueryable2();
    }

    default Queryable2<T1, T2> whereMerge(SQLExpression1<Tuple2<SQLWherePredicate<T1>, SQLWherePredicate<T2>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default Queryable2<T1, T2> whereMerge(boolean condition, SQLExpression1<Tuple2<SQLWherePredicate<T1>, SQLWherePredicate<T2>>> whereExpression) {
        return where(condition, (t, t1) -> {
            whereExpression.apply(new Tuple2<>(t, t1));
        });
    }
}
