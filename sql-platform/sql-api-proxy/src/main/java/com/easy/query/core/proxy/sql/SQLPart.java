package com.easy.query.core.proxy.sql;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.partition.proxy.Part1Proxy;
import com.easy.query.core.proxy.partition.proxy.Part2Proxy;

/**
 * create time 2024/8/4 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLPart {
    public <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> Part1Proxy<TKey1Proxy,TKey1,TSourceProxy,TSource> of(TSourceProxy tSourceProxy, TKey1Proxy column1) {
        Part1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource> partitionBy1Proxy = new Part1Proxy<>(tSourceProxy);
        partitionBy1Proxy.fetch(0,column1,partitionBy1Proxy.partColumn1());
        return partitionBy1Proxy;
    }
    public <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> Part2Proxy<TKey1Proxy,TKey1,TKey2Proxy,TKey2,TSourceProxy,TSource> of(TSourceProxy tSourceProxy, TKey1Proxy column1, TKey2Proxy column2) {
        Part2Proxy<TKey1Proxy, TKey1,TKey2Proxy, TKey2, TSourceProxy, TSource> partitionBy2Proxy = new Part2Proxy<>(tSourceProxy);
        partitionBy2Proxy.fetch(0,column1,partitionBy2Proxy.partColumn1());
        partitionBy2Proxy.fetch(1,column2,partitionBy2Proxy.partColumn2());
        return partitionBy2Proxy;
    }
}
