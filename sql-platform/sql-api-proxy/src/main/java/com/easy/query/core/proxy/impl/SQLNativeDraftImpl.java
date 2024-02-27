//package com.easy.query.core.proxy.impl;
//
//import com.easy.query.core.expression.builder.AsSelector;
//import com.easy.query.core.expression.builder.GroupSelector;
//import com.easy.query.core.expression.builder.OnlySelector;
//import com.easy.query.core.expression.builder.Selector;
//import com.easy.query.core.expression.builder.core.SQLNativeAble;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.func.SQLFunc;
//import com.easy.query.core.func.SQLFunction;
//import com.easy.query.core.proxy.PropTypeColumn;
//import com.easy.query.core.proxy.SQLSelectAsExpression;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
//import com.easy.query.core.util.EasyObjectUtil;
//
//import java.util.function.BiConsumer;
//import java.util.function.Function;
//
///**
// * create time 2023/12/19 23:10
// * 文件说明
// *
// * @author xuejiaming
// */
//public class SQLNativeDraftImpl implements PropTypeColumn<Object>, DSLSQLFunctionAvailable {
//
//
//    private final EntitySQLContext entitySQLContext;
//    private final BiConsumer<String, SQLNativeAble> selectorConsumer;
//    private Class<?> propType;
//
//    public SQLNativeDraftImpl(EntitySQLContext entitySQLContext,BiConsumer<String, SQLNativeAble> selectorConsumer){
//        this.entitySQLContext = entitySQLContext;
//        this.selectorConsumer = selectorConsumer;
//
//        this.propType = Object.class;
//    }
//
//    @Override
//    public SQLSelectAsExpression as(String propertyAlias) {
//        return new SQLSelectAsImpl(s -> {
//            selectorConsumer.accept(propertyAlias,s);
//        }, s -> {
//            selectorConsumer.accept(propertyAlias,s);
//        }, s -> {
//            throw new UnsupportedOperationException();
//        });
//    }
//
//    @Override
//    public void accept(GroupSelector s) {
//        selectorConsumer.accept(null,s);
//    }
//
//    @Override
//    public void accept(AsSelector s) {
//        selectorConsumer.accept(null,s);
//    }
//
//    @Override
//    public void accept(Selector s) {
//        selectorConsumer.accept(null,s);
//    }
//
//    @Override
//    public void accept(OnlySelector s) {
//        selectorConsumer.accept(null,s);
//    }
//
//
//    @Override
//    public TableAvailable getTable() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String getValue() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Class<?> getPropertyType() {
//        return propType;
//    }
//
//    @Override
//    public EntitySQLContext getEntitySQLContext() {
//        return entitySQLContext;
//    }
//
//    @Override
//    public <TR> void _setPropertyType(Class<TR> clazz) {
//        this.propType=clazz;
//    }
//
//    @Override
//    public <TR> PropTypeColumn<TR> setPropertyType(Class<TR> clazz) {
//        _setPropertyType(clazz);
//        return EasyObjectUtil.typeCastNullable(this);
//    }
//
//    @Override
//    public Function<SQLFunc, SQLFunction> func() {
//        return null;
//    }
//}
