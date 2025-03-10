//package com.easy.query.core.proxy.columns.impl;
//
//import com.easy.query.api.proxy.entity.select.EntityQueryable;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.proxy.columns.SQLQueryable;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//
///**
// * create time 2024/2/11 22:23
// * 文件说明
// *
// * @author xuejiaming
// */
//public class EasySQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLQueryable<T1Proxy, T1> {
//    private final EntitySQLContext entitySQLContext;
//    private final EntityQueryable<T1Proxy, T1> easyEntityQueryable;
//    private final TableAvailable originalTable;
//
//    public EasySQLQueryable(EntitySQLContext entitySQLContext, EntityQueryable<T1Proxy, T1> easyEntityQueryable, TableAvailable originalTable) {
//
//        this.entitySQLContext = entitySQLContext;
//        this.easyEntityQueryable = easyEntityQueryable;
//        this.originalTable = originalTable;
//    }
//
//    @Override
//    public EntitySQLContext getEntitySQLContext() {
//        return entitySQLContext;
//    }
//
//    @Override
//    public EntityQueryable<T1Proxy, T1> getQueryable() {
//        return easyEntityQueryable;
//    }
//
//    @Override
//    public TableAvailable getOriginalTable() {
//        return originalTable;
//    }
//
//    @Override
//    public String getNavValue() {
//        return easyEntityQueryable.get1Proxy().getNavValue();
//    }
//
//    @Override
//    public T1Proxy getProxy() {
//        return easyEntityQueryable.get1Proxy();
//    }
//
//    @Override
//    public SQLQueryable<T1Proxy, T1> useLogicDelete(boolean enable) {
//        easyEntityQueryable.useLogicDelete(enable);
//        return this;
//    }
//}
