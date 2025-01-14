package com.easy.query.core.migration;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.jdbc.JdbcExecutor;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseNameUtil;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/1/14 13:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDatabaseMigrationInfoConverter implements MigrationInfoConverter {
    private static final Map<Class<?>, ColumnTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnTypeResult("TINYINT", "TINYINT(1) NOT NULL", false));
        columnTypeMap.put(Boolean.class, new ColumnTypeResult("TINYINT", "TINYINT(1) NOT NULL", null));
        columnTypeMap.put(float.class, new ColumnTypeResult("FLOAT", "FLOAT NOT NULL", 0f));
        columnTypeMap.put(Float.class, new ColumnTypeResult("FLOAT", "FLOAT NOT NULL", null));
        columnTypeMap.put(double.class, new ColumnTypeResult("DOUBLE", "DOUBLE NOT NULL", 0d));
        columnTypeMap.put(Double.class, new ColumnTypeResult("DOUBLE", "DOUBLE NOT NULL", null));
        columnTypeMap.put(short.class, new ColumnTypeResult("SMALLINT(6)", "SMALLINT(6) NOT NULL", 0));
        columnTypeMap.put(Short.class, new ColumnTypeResult("SMALLINT(6)", "SMALLINT(6) NOT NULL", null));
        columnTypeMap.put(int.class, new ColumnTypeResult("INT(11)", "INT(11) NOT NULL", 0));
        columnTypeMap.put(Integer.class, new ColumnTypeResult("INT(11)", "INT(11) NOT NULL", null));
        columnTypeMap.put(long.class, new ColumnTypeResult("BIGINT(20)", "BIGINT(20) NOT NULL", 0L));
        columnTypeMap.put(Long.class, new ColumnTypeResult("BIGINT(20)", "BIGINT(20) NOT NULL", null));
        columnTypeMap.put(byte.class, new ColumnTypeResult("TINYINT(3)", "TINYINT(3) NOT NULL", 0));
        columnTypeMap.put(Byte.class, new ColumnTypeResult("TINYINT(3)", "TINYINT(3) NOT NULL", null));
        columnTypeMap.put(BigDecimal.class, new ColumnTypeResult("DECIMAL(16,2)", "DECIMAL(18,4) NOT NULL", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnTypeResult("DATETIME(3)", "DATETIME(3) NOT NULL", null));
        columnTypeMap.put(String.class, new ColumnTypeResult("VARCHAR(255)", "VARCHAR(255) NOT NULL", ""));
    }

    private final QueryRuntimeContext runtimeContext;


    public DefaultDatabaseMigrationInfoConverter(QueryRuntimeContext runtimeContext){
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String getDatabaseName(DataSource dataSource) {
        return EasyDatabaseNameUtil.getDatabaseName(dataSource);
    }

    @Override
    public boolean databaseExists(DataSource dataSource, String databaseName) {
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        List<Map> maps = sqlClientApiFactory.createJdbcExecutor(runtimeContext).sqlQuery("select 1 from information_schema.schemata where schema_name=?", Map.class, Collections.singletonList(new EasyConstSQLParameter(null,null,databaseName)));
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public @Nullable ColumnTypeResult getColumnTypeResult(EntityMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return columnTypeMap.get(columnMetadata.getPropertyType());
    }

    @Override
    public String getColumnComment(EntityMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return "";
    }

    @Override
    public String getTableComment(EntityMetadata entityMetadata) {
        return "";
    }
}
