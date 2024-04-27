package com.easy.query.processor.templates;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2023/12/24 14:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyColumn {
    private final String sqlColumnName;
    private final String propertyType;
    private String navigateProxyName;

    private static Map<String, String> IMPORT_MAPPING = new HashMap<>();

    static {
        IMPORT_MAPPING.put("SQLAnyTypeColumn", "com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn");
        IMPORT_MAPPING.put("SQLBigDecimalTypeColumn", "com.easy.query.core.proxy.columns.types.SQLBigDecimalTypeColumn");
        IMPORT_MAPPING.put("SQLBooleanTypeColumn", "com.easy.query.core.proxy.columns.types.SQLBooleanTypeColumn");
        IMPORT_MAPPING.put("SQLByteTypeColumn", "com.easy.query.core.proxy.columns.types.SQLByteTypeColumn");
        IMPORT_MAPPING.put("SQLDateTypeColumn", "com.easy.query.core.proxy.columns.types.SQLDateTypeColumn");
        IMPORT_MAPPING.put("SQLDoubleTypeColumn", "com.easy.query.core.proxy.columns.types.SQLDoubleTypeColumn");
        IMPORT_MAPPING.put("SQLFloatTypeColumn", "com.easy.query.core.proxy.columns.types.SQLFloatTypeColumn");
        IMPORT_MAPPING.put("SQLIntegerTypeColumn", "com.easy.query.core.proxy.columns.types.SQLIntegerTypeColumn");
        IMPORT_MAPPING.put("SQLLocalDateTimeTypeColumn", "com.easy.query.core.proxy.columns.types.SQLLocalDateTimeTypeColumn");
        IMPORT_MAPPING.put("SQLLocalDateTypeColumn", "com.easy.query.core.proxy.columns.types.SQLLocalDateTypeColumn");
        IMPORT_MAPPING.put("SQLLocalTimeTypeColumn", "com.easy.query.core.proxy.columns.types.SQLLocalTimeTypeColumn");
        IMPORT_MAPPING.put("SQLLongTypeColumn", "com.easy.query.core.proxy.columns.types.SQLLongTypeColumn");
        IMPORT_MAPPING.put("SQLShortTypeColumn", "com.easy.query.core.proxy.columns.types.SQLShortTypeColumn");
        IMPORT_MAPPING.put("SQLStringTypeColumn", "com.easy.query.core.proxy.columns.types.SQLStringTypeColumn");
        IMPORT_MAPPING.put("SQLTimestampTypeColumn", "com.easy.query.core.proxy.columns.types.SQLTimestampTypeColumn");
        IMPORT_MAPPING.put("SQLTimeTypeColumn", "com.easy.query.core.proxy.columns.types.SQLTimeTypeColumn");
        IMPORT_MAPPING.put("SQLUtilDateTypeColumn", "com.easy.query.core.proxy.columns.types.SQLUtilDateTypeColumn");
        IMPORT_MAPPING.put("SQLUUIDTypeColumn", "com.easy.query.core.proxy.columns.types.SQLUUIDTypeColumn");
    }

    public PropertyColumn(String sqlColumnName, String propertyType) {

        this.sqlColumnName = sqlColumnName;
        this.propertyType = propertyType;
    }

    public String getSqlColumnName() {
        return sqlColumnName;
    }

    public String getPropertyType() {
        return propertyType;
    }
    public String getPropertyTypeClass(boolean includeProperty) {
        if(!includeProperty){
            if(Objects.equals("SQLAnyTypeColumn",sqlColumnName)){
                return "__cast(Object.class)";
            }
        }
        return propertyType+".class";
    }

    public String getImport() {
        return IMPORT_MAPPING.get(sqlColumnName);
    }

    public String getSQLColumnMethod() {
        switch (sqlColumnName) {
            case "SQLBigDecimalTypeColumn":
                return "getBigDecimalTypeColumn";
            case "SQLBooleanTypeColumn":
                return "getBooleanTypeColumn";
            case "SQLByteTypeColumn":
                return "getByteTypeColumn";
            case "SQLDateTypeColumn":
                return "getSQLDateTypeColumn";
            case "SQLDoubleTypeColumn":
                return "getDoubleTypeColumn";
            case "SQLFloatTypeColumn":
                return "getFloatTypeColumn";
            case "SQLIntegerTypeColumn":
                return "getIntegerTypeColumn";
            case "SQLLocalDateTimeTypeColumn":
                return "getLocalDateTimeTypeColumn";
            case "SQLLocalDateTypeColumn":
                return "getLocalDateTypeColumn";
            case "SQLLocalTimeTypeColumn":
                return "getLocalTimeTypeColumn";
            case "SQLLongTypeColumn":
                return "getLongTypeColumn";
            case "SQLShortTypeColumn":
                return "getShortTypeColumn";
            case "SQLStringTypeColumn":
                return "getStringTypeColumn";
            case "SQLTimestampTypeColumn":
                return "getTimestampTypeColumn";
            case "SQLTimeTypeColumn":
                return "getTimeTypeColumn";
            case "SQLUtilDateTypeColumn":
                return "getUtilDateTypeColumn";
            case "SQLUUIDTypeColumn":
                return "getUUIDTypeColumn";
        }
        return "getAnyTypeColumn";
    }

    public String getNavigateProxyName() {
        return navigateProxyName;
    }

    public void setNavigateProxyName(String navigateProxyName) {
        this.navigateProxyName = navigateProxyName;
    }
    public boolean isAnyType(){
        return Objects.equals("SQLAnyTypeColumn",sqlColumnName);
    }
}
