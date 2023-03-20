package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.segment.SqlEntityAliasSegment;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.abstraction.metadata.ColumnMetadata;

import java.lang.invoke.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: EasyUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:12
 * @Created by xuejiaming
 */
public class EasyUtil {


    private static final int FLAG_SERIALIZABLE=1;
    private EasyUtil(){}
    public static SqlEntityTableExpression getPredicateTableByOffset(SqlEntityQueryExpression sqlEntityExpression, int offsetForward) {
        List<SqlEntityTableExpression> tables = sqlEntityExpression.getTables();
        if (tables.isEmpty()) {
            throw new EasyQueryException("cant get current join table");
        }
        int i = getNextTableIndex(sqlEntityExpression) - 1 - offsetForward;
        return tables.get(i);
    }
    public static SqlEntityTableExpression getCurrentPredicateTable(SqlEntityQueryExpression sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression,0);
    }
    public static SqlEntityTableExpression getPreviewPredicateTable(SqlEntityQueryExpression sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression,1);
    }

    public static ColumnMetadata getColumnMetadata(SqlEntityTableExpression tableExpression,String propertyName){
        return tableExpression.getEntityMetadata().getColumnNotNull(propertyName);
    }

    public static int getNextTableIndex(SqlEntityQueryExpression sqlEntityExpression){
        return sqlEntityExpression.getTables().size();
    }
    public static String getAnonymousColumnName(SqlEntityAliasSegment sqlEntityProject){
        String alias = sqlEntityProject.getAlias();
        if(StringUtil.isBlank(alias)){
            return sqlEntityProject.getTable().getColumnName(sqlEntityProject.getPropertyName());
        }
        return alias;
    }

    private static Map<Class<?>, Map<String, Property<Object,?>>> CLASS_PROPERTY_LAMBDA_CACHE = new ConcurrentHashMap<>();
    public static Property<Object,?> getPropertyLambda(Class<?> entityClass, String propertyName, Class<?> fieldType){
        Map<String, Property<Object, ?>> propertyLambdaMap = CLASS_PROPERTY_LAMBDA_CACHE.computeIfAbsent(entityClass,key-> new ConcurrentHashMap<>());
       return propertyLambdaMap.computeIfAbsent(propertyName,key-> getLambdaProperty(entityClass,propertyName,fieldType));
    }
    private static Property<Object,?> getLambdaProperty(Class<?> entityClass, String propertyName, Class<?> fieldType){

        final MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(fieldType, entityClass);
        final CallSite site;

        String getFunName="get"+StringUtil.toUpperCaseFirstOne(propertyName);
        try {
            site = LambdaMetafactory.altMetafactory(caller,
                    "apply",
                    MethodType.methodType(Property.class),
                    methodType,
                    caller.findVirtual(entityClass, getFunName, MethodType.methodType(fieldType)),
                    methodType, FLAG_SERIALIZABLE);
            return (Property<Object,?>) site.getTarget().invokeExact();
        }catch (Throwable e){
            throw new EasyQueryException(e);
        }
    }
    private static Property<Object,?> getLambdaProperty03(Class<?> entityClass, String propertyName, Class<?> fieldType){

        final CallSite site;

        String getFunName="get"+StringUtil.toUpperCaseFirstOne(propertyName);
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(fieldType); // 目标方法的返回类型和参数类型
            MethodHandle methodHandle = lookup.findVirtual(entityClass, getFunName, methodType); // 目标方法的句柄
            MethodType instantiatedMethodType = MethodType.methodType(Property.class); // Lambda表达式的目标方法类型
            site = LambdaMetafactory.altMetafactory(
                    lookup,
                    "apply",
                    instantiatedMethodType,
                    methodType.generic(),
                    methodHandle,
                    methodType,
                    MethodType.methodType(fieldType));
            return (Property<Object,?>) site.getTarget().invokeExact();
        }catch (Throwable e){
            throw new EasyQueryException(e);
        }
    }
    private static Property<Object,?> getLambdaProperty02(Class<?> entityClass, String propertyName, Class<?> fieldType){

        final MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(fieldType, entityClass);
        final CallSite site;

        String getFunName="get"+StringUtil.toUpperCaseFirstOne(propertyName);
        try {
            site = LambdaMetafactory.altMetafactory(
                    caller,
                    "apply",
                    MethodType.methodType(Property.class),
                    methodType.erase(),
                    caller.findVirtual(entityClass, getFunName, MethodType.methodType(fieldType)),
                    methodType, FLAG_SERIALIZABLE);
            return (Property<Object,?>) site.getTarget().invokeExact();
        }catch (Throwable e){
            throw new EasyQueryException(e);
        }
    }
    private static Map<Class<?>, Map<String, Property<Object,?>>> CLASS_PROPERTY_LAMBDA_GETTER_CACHE = new ConcurrentHashMap<>();
    public static Property<Object,?> getPropertyLambdaGetter(Class<?> entityClass, String propertyName, Class<?> fieldType){
        Map<String, Property<Object, ?>> propertyLambdaMap = CLASS_PROPERTY_LAMBDA_GETTER_CACHE.computeIfAbsent(entityClass,key-> new ConcurrentHashMap<>());
       return propertyLambdaMap.computeIfAbsent(propertyName,key-> getGetterLambdaProperty(entityClass,propertyName,fieldType));
    }
    private static Property<Object,?> getGetterLambdaProperty(Class<?> entityClass, String propertyName, Class<?> fieldType){

        final MethodHandles.Lookup caller = MethodHandles.lookup();
       MethodType methodType = MethodType.methodType(fieldType, entityClass);
        final CallSite site;

        String getFunName="get"+StringUtil.toUpperCaseFirstOne(propertyName);
        try {
             site = LambdaMetafactory.metafactory(
                     caller,
                    "apply",
                    MethodType.methodType(Property.class),
                     methodType.erase(),
                     caller.findVirtual(entityClass, getFunName, MethodType.methodType(fieldType)),
                     methodType);
            return (Property<Object,?>) site.getTarget().invokeExact();
        }catch (Throwable e){
            throw new EasyQueryException(e);
        }
    }
//    private static Property<Object,?> getLambdaProperty01(Class<?> entityClass, String propertyName, Class<?> fieldType){
//
//        final MethodHandles.Lookup lookup = MethodHandles.lookup();
//       MethodType methodType = MethodType.methodType(fieldType, entityClass);
//        final CallSite site;
//
//        String getFunName="get"+StringUtil.toUpperCaseFirstOne(propertyName);
//        try {
//            MethodHandle target = lookup.findVirtual(entityClass, getFunName, MethodType.methodType(fieldType));
//            MethodType func = target.type();
//            site = LambdaMetafactory.metafactory(lookup,
//                    "apply",
//                    MethodType.methodType(Property.class),
//                    methodType.erase(),
//                    target,
//                    func);
//            return (Property<Object,?>) site.getTarget().invokeExact();
//        }catch (Throwable e){
//            throw new EasyQueryException(e);
//        }
//    }


}

