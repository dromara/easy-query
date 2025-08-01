package com.easy.query.clickhouse.migration;

import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
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
public class ClickHouseMigrationEntityParser extends DefaultMigrationEntityParser {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("Int8", "0"));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("Int8", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("Float32", "0"));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("Float32", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("Float64", "0"));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("Float64", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("Int16", "0"));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("Int16", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("Int32", "0"));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("Int32", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("Int64", "0"));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("Int64", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("Int8", "0"));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("Int8", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("Decimal(38, 19)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("DateTime('Asia/Shanghai')", null));
        columnTypeMap.put(LocalDate.class, new ColumnDbTypeResult("Date32", null));
        columnTypeMap.put(LocalTime.class, new ColumnDbTypeResult("String", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("String", ""));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("String", null));
    }
    @Override
    protected Map<Class<?>, ColumnDbTypeResult> getColumnTypeMap() {
        return columnTypeMap;
    }
}
