package com.easy.query.kingbase.es.config;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.AbstractDatabaseMigrationProvider;
import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.EntityMigrationMetadata;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * create time 2025/1/19 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("BOOL", false));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("BOOL", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("FLOAT4", 0f));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("FLOAT4", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("FLOAT8", 0d));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("FLOAT8", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("INT2", 0));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("INT2", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("INT4", 0));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("INT4", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("INT8", 0L));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("INT8", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("INT2", 0));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("INT2", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("numeric(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("TIMESTAMP", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("VARCHAR(255)", ""));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("UUID", null));
    }
    public KingbaseESDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword) {
        super(dataSource, sqlKeyword);
    }

    @Override
    public boolean databaseExists() {
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select 1 from pg_namespace where nspname = ?", Collections.singletonList(getDatabaseName()));
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public MigrationCommand createDatabaseCommand() {
        String databaseSQL = "CREATE SCHEMA IF NOT EXISTS " + getQuoteSQLName(databaseName) + ";";
        return new DefaultMigrationCommand(null, databaseSQL);
    }

    @Override
    public boolean tableExists(String schema, String tableName) {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        if (EasyStringUtil.isBlank(schema)) {
            sqlParameters.add("public");
        } else {
            sqlParameters.add(schema);
        }
        sqlParameters.add(tableName);
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select 1 from pg_tables a inner join pg_namespace b on b.nspname = a.schemaname where b.nspname =? and a.tablename = ?", sqlParameters);
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public MigrationCommand renameTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        String sql = "ALTER TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getOldTableName()) + " RENAME TO " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()) + ";";
        return new DefaultMigrationCommand(entityMetadata, sql);
    }

    @Override
    public MigrationCommand createTable(EntityMigrationMetadata entityMigrationMetadata) {

        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        StringBuilder columnCommentSQL = new StringBuilder();


        String tableComment = getTableComment(entityMigrationMetadata,"'");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            columnCommentSQL.append(newLine)
                    .append("COMMENT ON TABLE ")
                    .append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(" IS ").append(tableComment).append(";");
        }

        sql.append("CREATE TABLE IF NOT EXISTS ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(" ( ");
        for (ColumnMetadata column : entityMetadata.getColumns()) {
            sql.append(newLine)
                    .append(getQuoteSQLName(column.getName()))
                    .append(" ");
            ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
            sql.append(columnDbTypeResult.columnType);
            boolean nullable = isNullable(entityMigrationMetadata, column);
            if (nullable) {
                sql.append(" NULL ");
            } else {
                sql.append(" NOT NULL ");
            }
            if (column.isGeneratedKey()) {
                sql.append(" GENERATED BY DEFAULT AS IDENTITY");
            }
            String columnComment = getColumnComment(entityMigrationMetadata, column,"'");
            if (EasyStringUtil.isNotBlank(columnComment)) {
                columnCommentSQL.append(newLine)
                        .append("COMMENT ON COLUMN ")
                        .append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(".").append(getQuoteSQLName(column.getName()))
                        .append(" IS ").append(columnComment).append(";");
            }
            sql.append(",");
        }
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        if (EasyCollectionUtil.isNotEmpty(keyProperties)) {
            sql.append(" ").append(newLine).append(" PRIMARY KEY (");
            int i = keyProperties.size();
            for (String keyProperty : keyProperties) {
                i--;
                ColumnMetadata keyColumn = entityMetadata.getColumnNotNull(keyProperty);
                sql.append(getQuoteSQLName(keyColumn.getName()));
                if (i > 0) {
                    sql.append(", ");
                } else {
                    sql.append(")");
                }
            }
        }
        sql.append(newLine).append(");");
        if (columnCommentSQL.length() > 0) {
            sql.append(newLine).append(columnCommentSQL);
        }
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }


    @Override
    protected MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()))
                .append(" RENAME COLUMN ").append(getQuoteSQLName(renameFrom))
                .append(" TO ")
                .append(getQuoteSQLName(column.getName())).append(";");
//
//        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
//        sql.append(columnDbTypeResult.columnType);
//        if (isNullable(entityMigrationMetadata, column)) {
//            sql.append(" NULL");
//        } else {
//            sql.append(" NOT NULL");
//        }

        String columnComment = getColumnComment(entityMigrationMetadata, column,"'");
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(newLine);
            sql.append(" COMMENT ON COLUMN ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(" IS ").append(columnComment);
            sql.append(";");
        }
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    @Override
    protected MigrationCommand addColumn(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()))
                .append(" ADD ").append(getQuoteSQLName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }
        sql.append(";");

        String columnComment = getColumnComment(entityMigrationMetadata, column,"'");
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(newLine);
            sql.append(" COMMENT ON COLUMN ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(" IS ").append(columnComment);
            sql.append(";");
        }
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }


    @Override
    public MigrationCommand dropTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        return new DefaultMigrationCommand(entityMetadata, "DROP TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()) + ";");
    }

    @Override
    protected ColumnDbTypeResult getColumnDbType0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        return columnTypeMap.get(columnMetadata.getPropertyType());
    }
}
