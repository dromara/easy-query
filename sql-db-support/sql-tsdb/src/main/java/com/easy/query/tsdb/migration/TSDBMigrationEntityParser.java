package com.easy.query.tsdb.migration;

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
public class TSDBMigrationEntityParser extends DefaultMigrationEntityParser {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("BOOL", "0"));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("BOOL", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("FLOAT", "0"));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("FLOAT", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("DOUBLE", "0"));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("DOUBLE", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("SMALLINT", "0"));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("SMALLINT", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("INT", "0"));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("INT", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("BIGINT", "0"));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("BIGINT", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("TINYINT", "0"));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("TINYINT", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("DECIMAL(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("TIMESTAMP", null));
        columnTypeMap.put(LocalDate.class, new ColumnDbTypeResult("DATE", null));
        columnTypeMap.put(LocalTime.class, new ColumnDbTypeResult("TIME", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("VARCHAR(255)", null));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("VARCHAR(36)", null));
    }

    @Override
    protected Map<Class<?>, ColumnDbTypeResult> getColumnTypeMap() {
        return columnTypeMap;
    }

    @Override
    protected String replaceSqlTypeLength(String sqlType, int length, int scale) {
        if (sqlType == null || sqlType.isEmpty()) {
            return sqlType;
        }
        String upperType = sqlType.toUpperCase();
        if (upperType.startsWith("INT") || upperType.startsWith("TINYINT") || upperType.startsWith("SMALLINT") || upperType.startsWith("BIGINT") || upperType.startsWith("BOOL") || upperType.startsWith("TIMESTAMP") || upperType.startsWith("FLOAT") || upperType.startsWith("DOUBLE") || upperType.startsWith("DATE") || upperType.startsWith("TIME")) {
            return sqlType;
        }
        return super.replaceSqlTypeLength(sqlType, length, scale);
    }
}
