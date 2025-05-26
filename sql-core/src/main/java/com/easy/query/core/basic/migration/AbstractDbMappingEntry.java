package com.easy.query.core.basic.migration;

import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/11/30 08:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDbMappingEntry implements MappingToDbColumn {
    protected final Class<?> propertyType;
    protected final String dbType;
    protected final String dbTypeFull;
    protected final Boolean unsigned;
    protected final Boolean nullable;
    protected final Object defaultValue;

    public AbstractDbMappingEntry(Class<?> propertyType, String dbType, String dbTypeFull, Boolean unsigned, Boolean nullable, Object defaultValue){

        this.propertyType = propertyType;
        this.dbType = dbType;
        this.dbTypeFull = dbTypeFull;
        this.unsigned = unsigned;
        this.nullable = nullable;
        this.defaultValue = defaultValue;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public String getDbType() {
        return dbType;
    }

    public String getDbTypeFull() {
        return dbTypeFull;
    }

    public Boolean getUnsigned() {
        return unsigned;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    protected @NotNull String getRealDbTypeFull(String dbTypeFull){
        if(dbTypeFull!=null){
            return dbTypeFull;
        }
        return this.dbTypeFull;
    }

    protected Boolean getRealUnsigned(Boolean unsigned){
        if(unsigned!=null){
            return unsigned;
        }
        return this.unsigned;
    }
    protected Boolean getRealNullable(Boolean nullable){
        if(nullable!=null){
            return nullable;
        }
        return this.nullable;
    }
    protected Object getRealDefaultValue(Object defaultValue){
        if(defaultValue!=null){
            return defaultValue;
        }
        return this.defaultValue;
    }


}
