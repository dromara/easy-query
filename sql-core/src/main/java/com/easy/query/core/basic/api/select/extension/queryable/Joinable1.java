package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Joinable1<T1> {
    <T2> ClientQueryable2<T1, T2> leftJoin(Class<T2> joinClass, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> leftJoin(ClientQueryable<T2> joinQueryable, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> rightJoin(ClientQueryable<T2> joinQueryable, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> innerJoin(ClientQueryable<T2> joinQueryable, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

}
