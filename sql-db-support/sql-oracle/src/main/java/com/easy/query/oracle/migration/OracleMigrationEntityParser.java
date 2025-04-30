package com.easy.query.oracle.migration;

import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.DefaultMigrationEntityParser;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * create time 2025/1/18 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleMigrationEntityParser extends DefaultMigrationEntityParser {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("number(1)", false));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("number(1)", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("real", 0f));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("real", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("double", 0d));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("double", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("number(6)", 0));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("number(6)", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("number(11)", 0));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("number(11)", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("number(21)", 0L));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("number(21)", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("number(3)", 0));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("number(3)", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("number(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("timestamp(6)", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("nvarchar2(255)", ""));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("char(36)", null));
    }

    @Override
    protected Map<Class<?>, ColumnDbTypeResult> getColumnTypeMap() {
        return columnTypeMap;
    }
}
