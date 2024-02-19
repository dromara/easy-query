//package com.easy.query.core.proxy.columns.proxy;
//
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//import com.easy.query.core.proxy.impl.SQLColumnImpl;
//import com.easy.query.core.util.EasyObjectUtil;
//
///**
// * create time 2024/2/19 22:56
// * 文件说明
// *
// * @author xuejiaming
// */
//public abstract class AbstractSQLColumnProxy<TProxy,TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty>
//extends SQLColumnImpl<TProxy,TProperty> implements ProxyEntity<TPropertyProxy,TProperty>, EntitySQLContextAvailable {
//    public AbstractSQLColumnProxy(EntitySQLContext entitySQLContext, TableAvailable table, String property, Class<TProperty> propType) {
//        super(entitySQLContext, table, property, propType);
//    }
//
//    @Override
//    public TableAvailable getTableOrNull() {
//        return table;
//    }
//
//    @Override
//    public Class<TProperty> getEntityClass() {
//        return EasyObjectUtil.typeCastNullable(getPropertyType());
//    }
//}
