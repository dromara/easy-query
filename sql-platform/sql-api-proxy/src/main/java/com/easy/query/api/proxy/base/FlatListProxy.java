//package com.easy.query.api.proxy.base;
//
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.proxy.columns.SQLQueryable;
//
///**
// * create time 2023/6/29 09:22
// * 文件说明
// *
// * @author xuejiaming
// */
//public class FlatListProxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractBasicProxyEntity<FlatListProxy<TProxy,TEntity>, TEntity> {
//
//    private final SQLQueryable<TProxy, TEntity> sqlQueryable;
//
////    public static <TResultProxy extends ProxyEntity<TResultProxy, TResult>,TResult> ListProxy<TResultProxy,TResult> createTable() {
////        return new ListProxy<>();
////    }
//
//    public FlatListProxy(SQLQueryable<TProxy,TEntity> sqlQueryable) {
//        this.sqlQueryable = sqlQueryable;
//    }
//
//    public SQLQueryable<TProxy, TEntity> getSqlQueryable() {
//        return sqlQueryable;
//    }
//    //
////    public ListProxy(List<TEntity> val) {
////        set(val);
////    }
////
////
////    public ListProxy(SQLColumn<?,List<TEntity>> sqlColumn) {
////        set(sqlColumn);
////    }
//////    public StringProxy(PropTypeColumn<String> sqlColumn) {
//////        set(sqlColumn);
//////    }
////
////
////    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<List<TEntity>>> ListProxy(TResult sqlFunctionAvailable) {
////        set(sqlFunctionAvailable);
////    }
//
//
//
//    @Override
//    public Class<TEntity> getEntityClass() {
//        return sqlQueryable.getQueryable().queryClass();
//    }
//
//
//
//}