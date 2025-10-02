package com.easy.query.duckdb.migration;

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
public class DuckDBSQLMigrationEntityParser extends DefaultMigrationEntityParser {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("BOOLEAN", "0"));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("BOOLEAN", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("REAL", "0"));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("REAL", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("DOUBLE", "0"));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("DOUBLE", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("SMALLINT", "0"));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("SMALLINT", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("INTEGER", "0"));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("INTEGER", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("BIGINT", "0"));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("BIGINT", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("TINYINT", "0"));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("TINYINT", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("DECIMAL(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("TIMESTAMP", null));
        columnTypeMap.put(LocalDate.class, new ColumnDbTypeResult("DATE", null));
        columnTypeMap.put(LocalTime.class, new ColumnDbTypeResult("TIME", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("VARCHAR", null));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("UUID", null));
    }
    @Override
    protected Map<Class<?>, ColumnDbTypeResult> getColumnTypeMap() {
        return columnTypeMap;
    }
}
