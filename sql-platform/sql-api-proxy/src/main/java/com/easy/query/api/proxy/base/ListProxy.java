package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.List;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ListProxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractBasicProxyEntity<ListProxy<TProxy,TEntity>, List<TEntity>> {

    private final SQLQueryable<TProxy, TEntity> sqlQueryable;

//    public static <TResultProxy extends ProxyEntity<TResultProxy, TResult>,TResult> ListProxy<TResultProxy,TResult> createTable() {
//        return new ListProxy<>();
//    }
    private static final Class<List> entityClass = List.class;

    public ListProxy(SQLQueryable<TProxy,TEntity> sqlQueryable) {
        this.sqlQueryable = sqlQueryable;
    }

    public SQLQueryable<TProxy, TEntity> getSqlQueryable() {
        return sqlQueryable;
    }
    //
//    public ListProxy(List<TEntity> val) {
//        set(val);
//    }
//
//
//    public ListProxy(SQLColumn<?,List<TEntity>> sqlColumn) {
//        set(sqlColumn);
//    }
////    public StringProxy(PropTypeColumn<String> sqlColumn) {
////        set(sqlColumn);
////    }
//
//
//    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<List<TEntity>>> ListProxy(TResult sqlFunctionAvailable) {
//        set(sqlFunctionAvailable);
//    }



    @Override
    public Class<List<TEntity>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }



}