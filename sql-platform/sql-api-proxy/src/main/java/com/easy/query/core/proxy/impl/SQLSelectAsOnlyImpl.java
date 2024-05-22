//package com.easy.query.core.proxy.impl;
//
//import com.easy.query.core.expression.builder.AsSelector;
//import com.easy.query.core.expression.builder.GroupSelector;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.proxy.SQLSelectAsExpression;
//import com.easy.query.core.proxy.TablePropColumn;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//
//import java.util.function.Consumer;
//
///**
// * create time 2023/12/1 23:42
// * 文件说明
// *
// * @author xuejiaming
// */
//public class SQLSelectAsOnlyImpl implements SQLSelectAsExpression {
//
//    private final TableAvailable table;
//    private final EntitySQLContext entitySQLContext;
//    private final Consumer<AsSelector> asSelectConsumer;
//
//    public SQLSelectAsOnlyImpl(TableAvailable table,EntitySQLContext entitySQLContext,Consumer<AsSelector> asSelectConsumer) {
//        this.table = table;
//        this.entitySQLContext = entitySQLContext;
//        this.asSelectConsumer = asSelectConsumer;
//    }
//
//    @Override
//    public void accept(GroupSelector s) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void accept(AsSelector s) {
//        asSelectConsumer.accept(s);
//    }
//
//
//    @Override
//    public String getValue() {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public SQLSelectAsExpression as(TablePropColumn propColumn) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public SQLSelectAsExpression as(String propertyAlias) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public TableAvailable getTable() {
//        return table;
//    }
//
//    @Override
//    public EntitySQLContext getEntitySQLContext() {
//        return entitySQLContext;
//    }
//}
