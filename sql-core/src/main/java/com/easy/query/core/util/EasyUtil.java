package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.segment.SqlEntityAliasSegment;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.abstraction.metadata.ColumnMetadata;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
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

    private static Map<Class<?>, Map<String, Property<?,?>>> CLASS_PROPERTY_LAMBDA_CACHE = new ConcurrentHashMap<>();
    public static Property<?,?> getLambdaProperty(Class<?> entityClass, String propertyName, Class<?> fieldType){
        Map<String, Property<?, ?>> propertyLambdaMap = CLASS_PROPERTY_LAMBDA_CACHE.computeIfAbsent(entityClass,key-> new ConcurrentHashMap<>());
       return propertyLambdaMap.computeIfAbsent(propertyName,key-> getLambdaProperty0(entityClass,propertyName,fieldType));
    }
    private static Property<?,?> getLambdaProperty0(Class<?> entityClass, String propertyName, Class<?> fieldType){

        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(fieldType, entityClass);
        final CallSite site;

        String getFunName="get"+StringUtil.toUpperCaseFirstOne(propertyName);
        try {
            site = LambdaMetafactory.altMetafactory(lookup,
                    "invoke",
                    MethodType.methodType(Property.class),
                    methodType,
                    lookup.findVirtual(entityClass, getFunName, MethodType.methodType(fieldType)),
                    methodType, FLAG_SERIALIZABLE);
            return (Property<?,?>) site.getTarget().invokeExact();
        }catch (Throwable e){
            throw new EasyQueryException(e);
        }
    }


}

