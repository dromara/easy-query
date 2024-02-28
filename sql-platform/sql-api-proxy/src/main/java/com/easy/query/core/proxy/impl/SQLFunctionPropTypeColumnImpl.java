//package com.easy.query.core.proxy.impl;
//
//import com.easy.query.core.expression.builder.AsSelector;
//import com.easy.query.core.expression.builder.GroupSelector;
//import com.easy.query.core.expression.builder.OnlySelector;
//import com.easy.query.core.expression.builder.Selector;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.expression.segment.CloneableSQLSegment;
//import com.easy.query.core.func.SQLFunc;
//import com.easy.query.core.func.SQLFunction;
//import com.easy.query.core.proxy.PropTypeColumn;
//import com.easy.query.core.proxy.SQLSelectAsExpression;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
//import com.easy.query.core.util.EasyObjectUtil;
//
//import java.util.function.Function;
//
///**
// * create time 2023/12/19 23:10
// * 文件说明
// *
// * @author xuejiaming
// */
//public class SQLFunctionPropTypeColumnImpl implements PropTypeColumn<Object>, DSLSQLFunctionAvailable {
//
//
//    private final EntitySQLContext entitySQLContext;
//    private final SQLFunction sqlFunction;
//    private Class<?> propType;
//
//    public SQLFunctionPropTypeColumnImpl(EntitySQLContext entitySQLContext, SQLFunction sqlFunction){
//        this.entitySQLContext = entitySQLContext;
//        this.sqlFunction = sqlFunction;
//
//        this.propType = Object.class;
//    }
//
//    @Override
//    public SQLSelectAsExpression as(String propertyAlias) {
//        return new SQLSelectAsImpl(s -> {
//            s.s
//        }, s -> {
//           s.sqlSegmentAs(sqlSegment,propertyAlias);
//        }, s -> {
//            s.sqlSegmentAs(sqlSegment);
//        });
//    }
//
//    @Override
//    public void accept(GroupSelector s) {
//        s.sqlSegmentAs(sqlSegment);
//    }
//
//    @Override
//    public void accept(AsSelector s) {
//        s.sqlSegmentAs(sqlSegment);
//    }
//
//    @Override
//    public void accept(Selector s) {
//        s.sqlSegmentAs(sqlSegment);
//    }
//
//    @Override
//    public void accept(OnlySelector s) {
//        s.sqlSegmentAs(sqlSegment);
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
//        return sqlFunc -> sqlFunction;
//    }
//}
