package com.easy.query.core.proxy.sql;

import com.easy.query.core.common.tuple.MergeTuple10;
import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.common.tuple.MergeTuple4;
import com.easy.query.core.common.tuple.MergeTuple5;
import com.easy.query.core.common.tuple.MergeTuple6;
import com.easy.query.core.common.tuple.MergeTuple7;
import com.easy.query.core.common.tuple.MergeTuple8;
import com.easy.query.core.common.tuple.MergeTuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.grouping.proxy.Grouping10Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping1Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping2Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping3Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping4Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping5Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping6Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping7Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping8Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping9Proxy;
import com.easy.query.core.proxy.impl.SQLGroupSelectImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/12/2 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupBy {
    public static SQLGroupByExpression of(SQLGroupByExpression... groupByExpressions){

        if(EasyArrayUtil.isNotEmpty(groupByExpressions)){
            SQLGroupByExpression firstGroupByExpression = groupByExpressions[0];
            for (int i = 1; i < groupByExpressions.length; i++) {
                firstGroupByExpression = firstGroupByExpression._thenBy(groupByExpressions[i]);
            }
            return firstGroupByExpression;
        }
        return SQLGroupByExpression.empty;
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>,TKey1,T1Proxy> SQLFuncExpression1<T1Proxy,Grouping1Proxy<TKey1Proxy,TKey1,T1Proxy>> keys(TKey1Proxy key1Proxy){
        return o->new Grouping1Proxy<>(key1Proxy,o);
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>,TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>,TKey2,
            T1Proxy,T2Proxy> SQLFuncExpression1<MergeTuple2<T1Proxy,T2Proxy>, Grouping2Proxy<TKey1Proxy,TKey1,TKey2Proxy,TKey2,MergeTuple2<T1Proxy,T2Proxy>>> keys(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy){
        return o->new Grouping2Proxy<>(key1Proxy,key2Proxy,o);
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>,TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>,TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>,TKey3,
            T1Proxy,T2Proxy,T3Proxy> SQLFuncExpression1<MergeTuple3<T1Proxy,T2Proxy,T3Proxy>, Grouping3Proxy<TKey1Proxy,TKey1,TKey2Proxy,TKey2,TKey3Proxy,TKey3,MergeTuple3<T1Proxy,T2Proxy,T3Proxy>>> keys(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy){
        return o->new Grouping3Proxy<>(key1Proxy,key2Proxy, key3Proxy,o);
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            T1Proxy, T2Proxy, T3Proxy, T4Proxy> SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>,
            Grouping4Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>>> keys(
            TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy) {
        return o -> new Grouping4Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, o);
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> SQLFuncExpression1<MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>,
            Grouping5Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>>> keys(
            TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy) {
        return o -> new Grouping5Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, o);
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> SQLFuncExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>,
            Grouping6Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>>> keys(
            TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy) {
        return o -> new Grouping6Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, o);
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
            T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> SQLFuncExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>,
            Grouping7Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>>> keys(
            TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy) {
        return o -> new Grouping7Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, o);
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
            TKey8Proxy extends PropTypeColumn<TKey8>, TKey8,
            T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> SQLFuncExpression1<MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>,
            Grouping8Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>>> keys(
            TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy,
            TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy) {
        return o -> new Grouping8Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, o);
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
            TKey8Proxy extends PropTypeColumn<TKey8>, TKey8,
            TKey9Proxy extends PropTypeColumn<TKey9>, TKey9,
            T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> SQLFuncExpression1<MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>,
            Grouping9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>>> keys(
            TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy,
            TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy, TKey9Proxy key9Proxy) {
        return o -> new Grouping9Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, key9Proxy, o);
    }
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
            TKey8Proxy extends PropTypeColumn<TKey8>, TKey8,
            TKey9Proxy extends PropTypeColumn<TKey9>, TKey9,
            TKey10Proxy extends PropTypeColumn<TKey10>, TKey10,
            T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>,
            Grouping10Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, TKey10Proxy, TKey10, MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>>> keys(
            TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy,
            TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy,
            TKey9Proxy key9Proxy, TKey10Proxy key10Proxy) {
        return o -> new Grouping10Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, key9Proxy, key10Proxy, o);
    }
    public static SQLGroupByExpression sql(String sqlSegment) {
        return sql(true, sqlSegment, f -> {
        });
    }


    public static SQLGroupByExpression sql(boolean condition, String sqlSegment) {
        if (condition) {
            return sql(true, sqlSegment, f -> {
            });
        }
        return SQLGroupByExpression.empty;
    }

    public static SQLGroupByExpression sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return sql(true,sqlSegment,contextConsume);
    }

    public static SQLGroupByExpression sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            return new SQLGroupSelectImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            });
        }
        return SQLGroupByExpression.empty;
    }
}
