package com.easy.query.core.basic.api.flat.extension;

import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.flat.provider.MapFilter;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2024/3/26 15:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Joinable {
    MapQueryable join(MultiTableTypeEnum joinTable, SQLExpression1<MapFilter> on);
    MapQueryable join(MultiTableTypeEnum joinTable,MapQueryable mapQueryable, SQLExpression1<MapFilter> on);

//    <T2> ClientQueryable2<T1, T2> leftJoin(ClientQueryable<T2> joinQueryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);
//
//    <T2> ClientQueryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);
//
//    <T2> ClientQueryable2<T1, T2> rightJoin(ClientQueryable<T2> joinQueryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);
//
//    <T2> ClientQueryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);
//
//    <T2> ClientQueryable2<T1, T2> innerJoin(ClientQueryable<T2> joinQueryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);
}
