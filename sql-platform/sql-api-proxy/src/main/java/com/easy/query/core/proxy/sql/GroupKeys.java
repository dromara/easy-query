package com.easy.query.core.proxy.sql;

import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.grouping.proxy.Grouping10Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping11Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping12Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping13Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping14Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping15Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping1Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping2Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping3Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping4Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping5Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping6Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping7Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping8Proxy;
import com.easy.query.core.proxy.grouping.proxy.Grouping9Proxy;

/**
 * create time 2023/12/29 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupKeys {
    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping1Proxy<TKey1Proxy, TKey1, TGroupTable>> of(TKey1Proxy key1Proxy) {
        return o -> new Grouping1Proxy<>(key1Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping2Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy) {
        return o -> new Grouping2Proxy<>(key1Proxy, key2Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping3Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy) {
        return o -> new Grouping3Proxy<>(key1Proxy, key2Proxy, key3Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping4Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy) {
        return o -> new Grouping4Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping5Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy) {
        return o -> new Grouping5Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping6Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy) {
        return o -> new Grouping6Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TKey7Proxy extends PropTypeColumn<TKey7>, TKey7, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping7Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy) {
        return o -> new Grouping7Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TKey7Proxy extends PropTypeColumn<TKey7>, TKey7, TKey8Proxy extends PropTypeColumn<TKey8>, TKey8, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping8Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy) {
        return o -> new Grouping8Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TKey7Proxy extends PropTypeColumn<TKey7>, TKey7, TKey8Proxy extends PropTypeColumn<TKey8>, TKey8, TKey9Proxy extends PropTypeColumn<TKey9>, TKey9, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy, TKey9Proxy key9Proxy) {
        return o -> new Grouping9Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, key9Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TKey7Proxy extends PropTypeColumn<TKey7>, TKey7, TKey8Proxy extends PropTypeColumn<TKey8>, TKey8, TKey9Proxy extends PropTypeColumn<TKey9>, TKey9, TKey10Proxy extends PropTypeColumn<TKey10>, TKey10, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping10Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, TKey10Proxy, TKey10, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy, TKey9Proxy key9Proxy, TKey10Proxy key10Proxy) {
        return o -> new Grouping10Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, key9Proxy, key10Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TKey7Proxy extends PropTypeColumn<TKey7>, TKey7, TKey8Proxy extends PropTypeColumn<TKey8>, TKey8, TKey9Proxy extends PropTypeColumn<TKey9>, TKey9, TKey10Proxy extends PropTypeColumn<TKey10>, TKey10, TKey11Proxy extends PropTypeColumn<TKey11>, TKey11, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping11Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, TKey10Proxy, TKey10, TKey11Proxy, TKey11, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy, TKey9Proxy key9Proxy, TKey10Proxy key10Proxy, TKey11Proxy key11Proxy) {
        return o -> new Grouping11Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, key9Proxy, key10Proxy, key11Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TKey7Proxy extends PropTypeColumn<TKey7>, TKey7, TKey8Proxy extends PropTypeColumn<TKey8>, TKey8, TKey9Proxy extends PropTypeColumn<TKey9>, TKey9, TKey10Proxy extends PropTypeColumn<TKey10>, TKey10, TKey11Proxy extends PropTypeColumn<TKey11>, TKey11, TKey12Proxy extends PropTypeColumn<TKey12>, TKey12, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping12Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, TKey10Proxy, TKey10, TKey11Proxy, TKey11, TKey12Proxy, TKey12, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy, TKey9Proxy key9Proxy, TKey10Proxy key10Proxy, TKey11Proxy key11Proxy, TKey12Proxy key12Proxy) {
        return o -> new Grouping12Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, key9Proxy, key10Proxy, key11Proxy, key12Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TKey7Proxy extends PropTypeColumn<TKey7>, TKey7, TKey8Proxy extends PropTypeColumn<TKey8>, TKey8, TKey9Proxy extends PropTypeColumn<TKey9>, TKey9, TKey10Proxy extends PropTypeColumn<TKey10>, TKey10, TKey11Proxy extends PropTypeColumn<TKey11>, TKey11, TKey12Proxy extends PropTypeColumn<TKey12>, TKey12, TKey13Proxy extends PropTypeColumn<TKey13>, TKey13, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping13Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, TKey10Proxy, TKey10, TKey11Proxy, TKey11, TKey12Proxy, TKey12, TKey13Proxy, TKey13, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy, TKey9Proxy key9Proxy, TKey10Proxy key10Proxy, TKey11Proxy key11Proxy, TKey12Proxy key12Proxy, TKey13Proxy key13Proxy) {
        return o -> new Grouping13Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, key9Proxy, key10Proxy, key11Proxy, key12Proxy, key13Proxy, o);
    }


    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TKey7Proxy extends PropTypeColumn<TKey7>, TKey7, TKey8Proxy extends PropTypeColumn<TKey8>, TKey8, TKey9Proxy extends PropTypeColumn<TKey9>, TKey9, TKey10Proxy extends PropTypeColumn<TKey10>, TKey10, TKey11Proxy extends PropTypeColumn<TKey11>, TKey11, TKey12Proxy extends PropTypeColumn<TKey12>, TKey12, TKey13Proxy extends PropTypeColumn<TKey13>, TKey13, TKey14Proxy extends PropTypeColumn<TKey14>, TKey14, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping14Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, TKey10Proxy, TKey10, TKey11Proxy, TKey11, TKey12Proxy, TKey12, TKey13Proxy, TKey13, TKey14Proxy, TKey14, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy, TKey9Proxy key9Proxy, TKey10Proxy key10Proxy, TKey11Proxy key11Proxy, TKey12Proxy key12Proxy, TKey13Proxy key13Proxy, TKey14Proxy key14Proxy) {
        return o -> new Grouping14Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, key9Proxy, key10Proxy, key11Proxy, key12Proxy, key13Proxy, key14Proxy, o);
    }

    public static <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TKey4Proxy extends PropTypeColumn<TKey4>, TKey4, TKey5Proxy extends PropTypeColumn<TKey5>, TKey5, TKey6Proxy extends PropTypeColumn<TKey6>, TKey6, TKey7Proxy extends PropTypeColumn<TKey7>, TKey7, TKey8Proxy extends PropTypeColumn<TKey8>, TKey8, TKey9Proxy extends PropTypeColumn<TKey9>, TKey9, TKey10Proxy extends PropTypeColumn<TKey10>, TKey10, TKey11Proxy extends PropTypeColumn<TKey11>, TKey11, TKey12Proxy extends PropTypeColumn<TKey12>, TKey12, TKey13Proxy extends PropTypeColumn<TKey13>, TKey13, TKey14Proxy extends PropTypeColumn<TKey14>, TKey14, TKey15Proxy extends PropTypeColumn<TKey15>, TKey15, TGroupTable> SQLFuncExpression1<TGroupTable, Grouping15Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, TKey10Proxy, TKey10, TKey11Proxy, TKey11, TKey12Proxy, TKey12, TKey13Proxy, TKey13, TKey14Proxy, TKey14, TKey15Proxy, TKey15, TGroupTable>> of(TKey1Proxy key1Proxy, TKey2Proxy key2Proxy, TKey3Proxy key3Proxy, TKey4Proxy key4Proxy, TKey5Proxy key5Proxy, TKey6Proxy key6Proxy, TKey7Proxy key7Proxy, TKey8Proxy key8Proxy, TKey9Proxy key9Proxy, TKey10Proxy key10Proxy, TKey11Proxy key11Proxy, TKey12Proxy key12Proxy, TKey13Proxy key13Proxy, TKey14Proxy key14Proxy, TKey15Proxy key15Proxy) {
        return o -> new Grouping15Proxy<>(key1Proxy, key2Proxy, key3Proxy, key4Proxy, key5Proxy, key6Proxy, key7Proxy, key8Proxy, key9Proxy, key10Proxy, key11Proxy, key12Proxy, key13Proxy, key14Proxy, key15Proxy, o);
    }
}
