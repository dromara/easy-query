//package com.easy.query.core.proxy.columns.proxy;
//
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.proxy.columns.SQLStringColumn;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//
///**
// * create time 2024/2/19 23:00
// * 文件说明
// *
// * @author xuejiaming
// */
//public class SQLStringColumnProxy<TProxy> extends AbstractSQLColumnProxy<TProxy, SQLStringColumnProxy<TProxy>,String> implements SQLStringColumn<TProxy,String> {
//    public SQLStringColumnProxy(String property,EntitySQLContext entitySQLContext, TableAvailable table) {
//        super(entitySQLContext, table, property, String.class);
//    }
//
//    @Override
//    public SQLStringColumnProxy<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
//        return new SQLStringColumnProxy<>(property,entitySQLContext,table);
//    }
//}
