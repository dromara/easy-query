//package com.easy.query.api.proxy.base;
//
//import com.easy.query.core.proxy.PropTypeColumn;
//import com.easy.query.core.proxy.SQLColumn;
//import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
//
///**
// * create time 2023/6/29 09:22
// * 文件说明
// *
// * @author xuejiaming
// */
//public class ObjectProxy extends AbstractBasicProxyEntity<ObjectProxy, Object> {
//
//    public static ObjectProxy createTable() {
//        return new ObjectProxy();
//    }
//    private static final Class<Object> entityClass = Object.class;
//
//    private ObjectProxy() {
//    }
//
//
//    public ObjectProxy(Object val) {
//        set(val);
//    }
//
//
//    public ObjectProxy(SQLColumn<?,Object> sqlColumn) {
//        set(sqlColumn);
//    }
////    public StringProxy(PropTypeColumn<String> sqlColumn) {
////        set(sqlColumn);
////    }
//
//
//    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<Object>> ObjectProxy(TResult sqlFunctionAvailable) {
//        set(sqlFunctionAvailable);
//    }
//
//
//
//    @Override
//    public Class<Object> getEntityClass() {
//        return entityClass;
//    }
//
//
//
//}