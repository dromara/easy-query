package com.easy.query.core.proxy.sql;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.partition.proxy.Partition1Proxy;

/**
 * create time 2024/8/4 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLPartition {
    public <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> Partition1Proxy<TKey1Proxy,TKey1,TSourceProxy,TSource> of(TSourceProxy tSourceProxy, TKey1Proxy column1) {
        Partition1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource> partitionBy1Proxy = new Partition1Proxy<>(tSourceProxy);
        partitionBy1Proxy.fetch(0,column1,partitionBy1Proxy.partitionColumn1());
        return partitionBy1Proxy;
    }
}
