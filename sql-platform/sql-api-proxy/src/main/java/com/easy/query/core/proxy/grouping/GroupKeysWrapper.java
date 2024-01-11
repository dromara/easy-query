//package com.easy.query.core.proxy.grouping;
//
//import com.easy.query.core.proxy.PropTypeColumn;
//import com.easy.query.core.proxy.grouping.proxy.Grouping1Proxy;
//
///**
// * create time 2024/1/11 08:48
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface GroupKeysWrapper<TSourceProxy> {
//    TSourceProxy group();
//    default  <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1> Grouping1Proxy<TKey1Proxy, TKey1, TSourceProxy> of(TKey1Proxy key1Proxy) {
//        return new Grouping1Proxy<>(key1Proxy, group());
//    }
//}
