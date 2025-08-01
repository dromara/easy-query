package com.easy.query.pgsql.migration;

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
public class PgSQLMigrationEntityParser extends DefaultMigrationEntityParser {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("BOOL", "0"));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("BOOL", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("FLOAT4", "0"));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("FLOAT4", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("FLOAT8", "0"));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("FLOAT8", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("INT2", "0"));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("INT2", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("INT4", "0"));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("INT4", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("INT8", "0"));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("INT8", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("INT2", "0"));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("INT2", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("numeric(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("TIMESTAMP", null));
        columnTypeMap.put(LocalDate.class, new ColumnDbTypeResult("DATE", null));
        columnTypeMap.put(LocalTime.class, new ColumnDbTypeResult("TIME", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("VARCHAR(255)", null));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("UUID", null));
    }
    @Override
    protected Map<Class<?>, ColumnDbTypeResult> getColumnTypeMap() {
        return columnTypeMap;
    }
}
