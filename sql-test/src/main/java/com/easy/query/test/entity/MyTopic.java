//package com.easy.query.test.entity;
//
//import com.easy.query.core.annotation.EntityProxy;
//import com.easy.query.core.annotation.Table;
//import com.easy.query.core.proxy.ProxyEntityAvailable;
//import com.easy.query.test.entity.proxy.MyTopicProxy;
//import lombok.Data;
//
///**
// * create time 2024/5/25 20:12
// * 文件说明
// *
// * @author xuejiaming
// */
//@Data
//@Table("my_topic")
//@EntityProxy
//public class MyTopic extends Topic implements ProxyEntityAvailable<MyTopic , MyTopicProxy> {
//    private String customKey;
//
//    @Override
//    public Class<MyTopicProxy> proxyTableClass() {
//        return MyTopicProxy.class;
//    }
//}
