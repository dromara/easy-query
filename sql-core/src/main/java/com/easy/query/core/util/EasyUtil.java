package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetter;
import com.easy.query.core.expression.segment.SqlEntityAliasSegment;
import com.easy.query.core.expression.sql.SqlEntityQueryExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.metadata.ColumnMetadata;

import java.beans.PropertyDescriptor;
import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: EasyUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:12
 * @author xuejiaming
 */
public class EasyUtil {


    private static final int FLAG_SERIALIZABLE = 1;

    private EasyUtil() {
    }

    public static SqlEntityTableExpression getPredicateTableByOffset(SqlEntityQueryExpression sqlEntityExpression, int offsetForward) {
        List<SqlEntityTableExpression> tables = sqlEntityExpression.getTables();
        if (tables.isEmpty()) {
            throw new EasyQueryException("cant get current join table");
        }
        int i = getNextTableIndex(sqlEntityExpression) - 1 - offsetForward;
        return tables.get(i);
    }

    public static SqlEntityTableExpression getCurrentPredicateTable(SqlEntityQueryExpression sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression, 0);
    }

    public static SqlEntityTableExpression getPreviewPredicateTable(SqlEntityQueryExpression sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression, 1);
    }

    public static ColumnMetadata getColumnMetadata(SqlEntityTableExpression tableExpression, String propertyName) {
        return tableExpression.getEntityMetadata().getColumnNotNull(propertyName);
    }

    public static int getNextTableIndex(SqlEntityQueryExpression sqlEntityExpression) {
        return sqlEntityExpression.getTables().size();
    }

    public static String getAnonymousColumnName(SqlEntityAliasSegment sqlEntityProject) {
        String alias = sqlEntityProject.getAlias();
        if (StringUtil.isBlank(alias)) {
            return sqlEntityProject.getTable().getColumnName(sqlEntityProject.getPropertyName());
        }
        return alias;
    }

    private static Map<Class<?>, Map<String, Property<Object, ?>>> CLASS_PROPERTY_LAMBDA_CACHE = new ConcurrentHashMap<>();

    public static Property<Object, ?> getPropertyLambda(Class<?> entityClass, String propertyName, Class<?> fieldType) {
        return getPropertyLambda(entityClass,propertyName,fieldType,true);
    }
    public static Property<Object, ?> getPropertyLambda(Class<?> entityClass, String propertyName, Class<?> fieldType,boolean cache) {
        if(cache){
            Map<String, Property<Object, ?>> propertyLambdaMap = CLASS_PROPERTY_LAMBDA_CACHE.computeIfAbsent(entityClass, key -> new ConcurrentHashMap<>());
            return propertyLambdaMap.computeIfAbsent(propertyName, key -> getLambdaProperty(entityClass, propertyName, fieldType));
        }
        return getLambdaProperty(entityClass, propertyName, fieldType);
    }

    private static Property<Object, ?> getLambdaProperty(Class<?> entityClass, String propertyName, Class<?> fieldType) {
        final MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(fieldType, entityClass);
        final CallSite site;

        String getFunName = "get" + StringUtil.toUpperCaseFirstOne(propertyName);
        try {
            site = LambdaMetafactory.altMetafactory(caller,
                    "apply",
                    MethodType.methodType(Property.class),
                    methodType.erase(),
                    caller.findVirtual(entityClass, getFunName, MethodType.methodType(fieldType)),
                    methodType, FLAG_SERIALIZABLE);
            return (Property<Object, ?>) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new EasyQueryException(e);
        }
    }



    private static Map<Class<?>, Map<String, PropertySetter<Object>>> CLASS_PROPERTY_SETTER_LAMBDA_CACHE = new ConcurrentHashMap<>();

    public static PropertySetter<Object> getPropertyLambdaSetter(Class<?> entityClass, PropertyDescriptor prop){
        return getPropertyLambdaSetter(entityClass,prop,true);
    }
    public static PropertySetter<Object> getPropertyLambdaSetter(Class<?> entityClass, PropertyDescriptor prop,boolean cache) {
        if(cache){
            Map<String, PropertySetter<Object>> propertyLambdaMap = CLASS_PROPERTY_SETTER_LAMBDA_CACHE.computeIfAbsent(entityClass, key -> new ConcurrentHashMap<>());
            return propertyLambdaMap.computeIfAbsent(prop.getName(), key -> getLambdaPropertySetter(entityClass, prop));
        }
        return getLambdaPropertySetter(entityClass, prop);
    }
    private static PropertySetter<Object> getLambdaPropertySetter(Class<?> entityClass, PropertyDescriptor prop) {
//        final MethodHandles.Lookup caller = MethodHandles.lookup();
//        MethodType methodType = MethodType.methodType(Void.class,entityClass);
//        final CallSite site;

        String propertyName = prop.getName();
        Class<?> propertyType = prop.getPropertyType();

        String getFunName = "set" + StringUtil.toUpperCaseFirstOne(propertyName);
        try {
//            site = LambdaMetafactory.altMetafactory(caller,
//                    "apply",
//                    MethodType.methodType(PropertySetter.class),
//                    methodType.erase(),
//                    caller.findVirtual(entityClass, getFunName, MethodType.methodType(Void.class,fieldType)),
//                    methodType, FLAG_SERIALIZABLE);
//            return (PropertySetter<Object>) site.getTarget().invokeExact();

            MethodHandles.Lookup caller = MethodHandles.lookup();
            Method writeMethod = ClassUtil.getWriteMethodNotNull(prop, entityClass);

            MethodType setter = MethodType.methodType(writeMethod.getReturnType(), propertyType);
            MethodHandle target = caller.findVirtual(entityClass, getFunName, setter);
            MethodType func = target.type();

            CallSite site = LambdaMetafactory.metafactory(
                    caller,
                    "apply",
                    MethodType.methodType(PropertySetter.class),
                    func.erase(),
                    target,
                    func
            );

            MethodHandle factory = site.getTarget();
           return (PropertySetter<Object>) factory.invoke();
        } catch (Throwable e) {
            throw new EasyQueryException(e);
        }
    }
}

