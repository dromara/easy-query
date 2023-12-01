package com.easy.query.core.basic.migration.creator;

import com.easy.query.core.basic.migration.AbstractDbMappingEntry;

import java.util.Objects;

/**
 * create time 2023/11/30 09:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDbMappingEntry extends AbstractDbMappingEntry {

    public DefaultDbMappingEntry(Class<?> propertyType, String dbType, String dbTypeFull, Boolean unsigned, Boolean nullable, Object defaultValue) {
        super(propertyType, dbType, dbTypeFull, unsigned, nullable, defaultValue);
    }

    @Override
    public String toDbColumn(String dbTypeFull, Boolean unsigned, Boolean nullable, Object defaultValue) {
        String realDbTypeFull = getRealDbTypeFull(dbTypeFull);
        Boolean realUnsigned = getRealUnsigned(unsigned);
        Boolean realNullable = getRealNullable(nullable);
        Object realDefaultValue = getRealDefaultValue(defaultValue);
        StringBuilder sql = new StringBuilder();
        sql.append(realDbTypeFull);
        if (Objects.equals(Boolean.TRUE,realUnsigned)){
            sql.append(" unsigned");
        }
        if(realDefaultValue!=null){
            sql.append(" default ").append(realDefaultValue);
        }
        if (!Objects.equals(Boolean.TRUE,realNullable)){
            sql.append(" NOT NULL");
        }
        return sql.toString();
    }
}
