package org.easy.query.core.metadata;

import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.util.StringUtil;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @FileName: TableInfo.java
 * @Description: 文件说明
 * @Date: 2023/2/10 23:34
 * @Created by xuejiaming
 */
public class TableInfo {

    private final Class<?> tableType;

    public String getName() {
        return name;
    }

    public String getSchema() {
        return schema;
    }

    private final String name;
    private final String schema;


    private final Map<String, ColumnInfo> columns;
    private final Map<String, Field> properties;

    public TableInfo(Class<?> tableType,String name) {
       this(tableType,name,null);
    }

    public TableInfo(Class<?> tableType,String name,String schema) {
        this.tableType = tableType;
        this.name = name;
        this.schema = schema;
        this.columns = new LinkedHashMap<>();
        properties=new LinkedHashMap<>();
    }

    public Class<?> getTableType() {
        return tableType;
    }


    public <T> String getColumnName(Property<T, ?> property) {
        ColumnInfo columnInfo = getColumnInfo(property);
        return columnInfo.getName();
    }
    private  <T> ColumnInfo getColumnInfo(Property<T, ?> property) {
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
            String propertyName = StringUtil.toLowerCaseFirstOne(attr);
            ColumnInfo columnInfo = columns.get(propertyName);
            if (columnInfo == null) {
                throw new JDQCException("not found column info,property name:"+propertyName);
            }
            return columnInfo;
//            return sqlManager.getNc().getColName(clazz, StringKit.toLowerCaseFirstOne(attr));
//            return nc.getColName(clazz, attr);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Field> getProperties() {
        return properties;
    }
    public Map<String, ColumnInfo> getColumns() {
        return columns;
    }
}
