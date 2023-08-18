package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable2;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/17 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtJoinable1<T1> {

    <T2> KtQueryable2<T1, T2> leftJoin(Class<T2> joinClass, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> leftJoin(KtQueryable<T2> joinQueryable, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> rightJoin(KtQueryable<T2> joinQueryable, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> innerJoin(KtQueryable<T2> joinQueryable, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

}
