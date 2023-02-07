package org.jdqc.sql.core.common;

import org.jdqc.sql.core.abstraction.lambda.Property;
import org.jdqc.sql.core.config.NameConversion;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: TableinFO.java
 * @Description: 文件说明
 * @Date: 2023/2/6 12:40
 * @Created by xuejiaming
 */
public class TableInfo {

    private final Class<?> tableType;
    private final Map<String,ColumnInfo> columnsMap;
    public TableInfo(Class<?> tableType,Map<String,ColumnInfo> columnsMap) {
        this.columnsMap=columnsMap;
        this.tableType = tableType;
    }

    public Class<?> getTableType() {
        return tableType;
    }



    private  <T> String getFunctionName(Property<T, ?> property) {
        try {
            Method declaredMethod = property.getClass().getDeclaredMethod("writeReplace");
            declaredMethod.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(property);
            String method = serializedLambda.getImplMethodName();


            String attr = null;
            if (method.startsWith("get")) {
                attr = method.substring(3);
            } else {
                attr = method.substring(2);
            }
            ColumnInfo columnInfo = columnsMap.get(attr);
            if(columnInfo==null){
                throw new RuntimeException();
            }
            return columnInfo.getColumnName();
//            return sqlManager.getNc().getColName(clazz, StringKit.toLowerCaseFirstOne(attr));
//            return nc.getColName(clazz, attr);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
}
