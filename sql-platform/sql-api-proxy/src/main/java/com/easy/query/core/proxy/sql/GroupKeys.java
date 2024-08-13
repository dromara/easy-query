package com.easy.query.core.proxy.sql;

import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.grouping.proxy.Grouping1Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping2Proxy;

/**
 * create time 2023/12/29 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupKeys {
    /**
     * 请使用GroupKeys.TABLE1.of(...）
     *
     * @param key1Proxy
     * @param <TKey1Proxy>
     * @param <TKey1>
     * @param <T1Proxy>
     * @return
     */
    @Deprecated
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, T1Proxy> SQLFuncExpression1<T1Proxy, Grouping1Proxy<TKey1Proxy, TKey1, T1Proxy>> of(TKey1Proxy key1Proxy) {
        return o -> new Grouping1Proxy<>(key1Proxy, o);
    }

    /**
     * GroupKeys.TABLE2.of(...)
     *
     * @param key1Proxy
     * @param key2Proxy
     * @param <TKey1Proxy>
     * @param <TKey1>
     * @param <TKey2Proxy>
     * @param <TKey2>
     * @param <T1Proxy>
     * @param <T2Proxy>
     * @return
     */
    @Deprecated
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, T1Proxy, T2Proxy> SQLFuncExpression1<MergeTuple2<T1Proxy, T2Proxy>, Grouping2Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, MergeTuple2<T1Proxy, T2Proxy>>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy) {
        return o -> new Grouping2Proxy<>(key1Proxy, key2Proxy, o);
    }

    /**
     * 如果您的表达式只有一张表请用这个聚合
     */
    public static Group1Keys TABLE1 = new Group1Keys();

    /**
     * 如果您的表达式只有二张表请用这个聚合
     */
    public static Group2Keys TABLE2 = new Group2Keys();

    /**
     * 如果您的表达式只有三张表请用这个聚合
     */
    public static Group3Keys TABLE3 = new Group3Keys();
    /**
     * 如果您的表达式只有四张表请用这个聚合
     */
    public static Group4Keys TABLE4 = new Group4Keys();
    /**
     * 如果您的表达式只有五张表请用这个聚合
     */
    public static Group5Keys TABLE5 = new Group5Keys();
    /**
     * 如果您的表达式只有六张表请用这个聚合
     */
    public static Group6Keys TABLE6 = new Group6Keys();
    /**
     * 如果您的表达式只有七张表请用这个聚合
     */
    public static Group7Keys TABLE7 = new Group7Keys();
    /**
     * 如果您的表达式只有八张表请用这个聚合
     */
    public static Group8Keys TABLE8 = new Group8Keys();
    /**
     * 如果您的表达式只有九张表请用这个聚合
     */
    public static Group9Keys TABLE9 = new Group9Keys();
    /**
     * 如果您的表达式只有十张表请用这个聚合
     */
    public static Group10Keys TABLE10 = new Group10Keys();
}
