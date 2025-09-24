//package com.easy.query.core.proxy.extension.functions.type.impl;
//
//import com.easy.query.core.expression.builder.AsSelector;
//import com.easy.query.core.expression.builder.GroupSelector;
//import com.easy.query.core.expression.builder.OrderSelector;
//import com.easy.query.core.expression.builder.Selector;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.func.SQLFunc;
//import com.easy.query.core.func.SQLFunction;
//import com.easy.query.core.func.def.enums.OrderByModeEnum;
//import com.easy.query.core.proxy.SQLFunctionExpressionUtil;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//import com.easy.query.core.proxy.extension.functions.type.JsonArrayTypeExpression;
//import com.easy.query.core.proxy.extension.functions.type.JsonMapTypeExpression;
//import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;
//
//import java.util.function.Function;
//
///**
// * create time 2023/12/2 23:22
// * 文件说明
// *
// * @author xuejiaming
// */
//public class JsonArrayTypeExpressionImpl<TProperty> implements JsonArrayTypeExpression<TProperty> {
//    private final EntitySQLContext entitySQLContext;
//    private final TableAvailable table;
//    private final String property;
//    private final Function<SQLFunc, SQLFunction> func;
//    private Class<?> propType;
//    public JsonArrayTypeExpressionImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
//        this.entitySQLContext = entitySQLContext;
//
//        this.table = table;
//        this.property = property;
//        this.func = func;
//        this.propType = propType;
//    }
//
//    @Override
//    public String getValue() {
//        return property;
//    }
//
//    @Override
//    public TableAvailable getTable() {
//        return table;
//    }
//
//    @Override
//    public void accept(Selector s) {
//        SQLFunctionExpressionUtil.accept(s, getTable(), func);
//    }
//
//    @Override
//    public void accept(AsSelector s) {
//        SQLFunctionExpressionUtil.accept(s, getTable(), func);
//    }
//
//    @Override
//    public void accept(GroupSelector s) {
//        SQLFunctionExpressionUtil.accept(s, getTable(), func);
//    }
//
//    @Override
//    public void accept(OrderSelector s) {
//        SQLFunctionExpressionUtil.accept(s, getTable(), func);
//    }
//
////    @Override
////    public void asc(boolean condition) {
////        if (condition) {
////            getCurrentEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
////                SQLFunctionExpressionUtil.accept(s, getTable(), func, true);
////            }));
////        }
////    }
//
//    @Override
//    public void asc(boolean condition, OrderByModeEnum nullsModeEnum) {
//        if (condition) {
//
//            getCurrentEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
//                s.setAsc(true);
//                SQLFunc fx = getEntitySQLContext().getRuntimeContext().fx();
//                SQLFunction sqlFunction = func.apply(fx);
//                if (nullsModeEnum != null) {
//                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(sqlFunction, true, nullsModeEnum);
//                    s.func(this.getTable(), orderByNullsModeFunction,false);
//                } else {
//                    s.func(this.getTable(), sqlFunction,true);
//                }
//            }));
//        }
//    }
////
////    @Override
////    public void desc(boolean condition) {
////        if (condition) {
////            getCurrentEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
////                SQLFunctionExpressionUtil.accept(s, getTable(), func, false);
////            }));
////        }
////    }
//    @Override
//    public void desc(boolean condition, OrderByModeEnum nullsModeEnum) {
//        if (condition) {
//
//            getCurrentEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
//                s.setAsc(false);
//                SQLFunc fx = getEntitySQLContext().getRuntimeContext().fx();
//                SQLFunction sqlFunction = func.apply(fx);
//                if (nullsModeEnum != null) {
//                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(sqlFunction, false, nullsModeEnum);
//                    s.func(this.getTable(), orderByNullsModeFunction,false);
//                } else {
//                    s.func(this.getTable(), sqlFunction,true);
//                }
//            }));
//        }
//    }
//
//    @Override
//    public Function<SQLFunc, SQLFunction> func() {
//        return this.func;
//    }
//
//    @Override
//    public EntitySQLContext getEntitySQLContext() {
//        return entitySQLContext;
//    }
//
//    @Override
//    public Class<?> getPropertyType() {
//        return propType;
//    }
//
//    @Override
//    public <TR> void _setPropertyType(Class<TR> clazz) {
//        this.propType = clazz;
//    }
//
//
////    @Override
////    public void eq(boolean condition,TProperty val) {
////        if(val!=null&& EasyClassUtil.isBooleanType(val.getClass())){
////            ColumnFunctionCompareComparableAnyChainExpression.super.eq(true,Expression.of(getEntitySQLContext()).sqlSegment("CAST(? AS JSON)",c->c.value(val)));;
////        }else{
////            ColumnFunctionCompareComparableAnyChainExpression.super.eq(val);
////        }
////    }
//}
////        if(condition){
////            return new SQLPredicateImpl(f->{
////                SQLFunc fx = f.getRuntimeContext().fx();
////                f.eq(this.table,func.apply(fx),val);
////            });
////        }
////        return SQLPredicate.empty;
////    }
////}
