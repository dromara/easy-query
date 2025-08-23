package com.easy.query.sqlite.migration;

import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.DefaultMigrationEntityParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * create time 2025/1/18 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteMigrationEntityParser extends DefaultMigrationEntityParser {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("boolean", "0"));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("boolean", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("float", "0"));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("float", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("double", "0"));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("double", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("smallint", "0"));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("smallint", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("integer", "0"));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("integer", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("integer", "0"));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("integer", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("int2", "0"));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("int2", null));
        columnTypeMap.put(byte[].class, new ColumnDbTypeResult("blob", null));
        columnTypeMap.put(Byte[].class, new ColumnDbTypeResult("blob", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("decimal(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("TEXT", null));
        columnTypeMap.put(LocalDate.class, new ColumnDbTypeResult("TEXT", null));
        columnTypeMap.put(LocalTime.class, new ColumnDbTypeResult("TEXT", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("VARCHAR(255)", null));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("CHAR(36)", null));
    }

    @Override
    protected Map<Class<?>, ColumnDbTypeResult> getColumnTypeMap() {
        return columnTypeMap;
    }
}
