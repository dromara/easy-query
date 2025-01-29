package com.easy.query.mssql.config;

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
public class MsSQLDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("BIT", false));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("BIT", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("REAL", 0f));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("REAL", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("FLOAT", 0d));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("FLOAT", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("SMALLINT", 0));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("SMALLINT", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("INT", 0));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("INT", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("BIGINT", 0L));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("BIGINT", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("TINYINT", 0));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("TINYINT", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("DECIMAL(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("DATETIME", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("NVARCHAR(255)", ""));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("uniqueidentifier", null));
    }

    public MsSQLDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword) {
        super(dataSource, sqlKeyword);
    }

//    @Override
//    public boolean databaseExists() {
//        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, " select 1 from sys.databases where name= ? ", Collections.singletonList(getDatabaseName()));
//        return EasyCollectionUtil.isNotEmpty(maps);
//    }
//
//    @Override
//    public MigrationCommand createDatabaseCommand() {
//        String databaseSQL = "if not exists(select 1 from sys.databases where name= '" + this.databaseName + "') " + newLine + " create database " + getQuoteSQLName(this.databaseName) + ";";
//        return new DefaultMigrationCommand(null, databaseSQL);
//    }

    @Override
    public String databaseExistSQL(String databaseName) {
        return String.format(" select 1 from sys.databases where name= '%s' ", databaseName);
    }
    @Override
    public String createDatabaseSQL(String databaseName) {
        return "if not exists(select 1 from sys.databases where name= '" + this.databaseName + "') " + newLine + " create database " + getQuoteSQLName(this.databaseName) + ";";
    }


    @Override
    public boolean tableExists(String schema, String tableName) {
        String querySchema = null;
        if (EasyStringUtil.isBlank(schema)) {
            querySchema = "dbo";
        } else {
            querySchema = schema;
        }
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, " select 1 from dbo.sysobjects where id = object_id(N'"+getQuoteSQLName(querySchema,tableName)+"') and OBJECTPROPERTY(id, N'IsUserTable') = 1", Collections.emptyList());
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public MigrationCommand renameTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();

        return new DefaultMigrationCommand(entityMetadata, "ALTER TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getOldTableName()) + " RENAME TO " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()) + ";");
    }

    @Override
    public MigrationCommand createTable(EntityMigrationMetadata entityMigrationMetadata) {

        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        StringBuilder columnCommentSQL = new StringBuilder();

//        String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null);
//        String schema = EasyToSQLUtil.getSchema(sqlKeyword, entityMetadata, entityMetadata.getSchemaOrNull(), null, null);
//        String schemaWithoutDatabaseName = EasyToSQLUtil.getSchemaWithoutDatabaseName(entityMetadata, entityMetadata.getSchemaOrNull(), null, "dbo");

        String tableComment = getTableComment(entityMigrationMetadata,"");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            String format = String.format("exec sp_addextendedproperty 'MS_Description', '%s', 'SCHEMA', '%s', 'TABLE', '%s'", tableComment, entityMetadata.getSchemaOrDefault("dbo"), entityMetadata.getTableName());
            columnCommentSQL.append(newLine)
                    .append(format)
                    .append(newLine).append("go");
        }

        sql.append("USE ").append(getQuoteSQLName(this.databaseName)).append(newLine)
                .append("go")
                .append(newLine);
        sql.append("CREATE TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName())).append(" ( ");
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
                sql.append(" IDENTITY(1,1)");
            }
            if (column.isPrimary()) {
                sql.append(" PRIMARY KEY ");
            }
            String columnComment = getColumnComment(entityMigrationMetadata, column,"");
//            exec sp_addextendedproperty 'MS_Description', '微信唯一识别码', 'SCHEMA', 'dbo', 'TABLE', 'Base_User', 'COLUMN', 'OpenId'
//            go
            if (EasyStringUtil.isNotBlank(columnComment)) {
                String format = String.format("exec sp_addextendedproperty 'MS_Description', '%s', 'SCHEMA', '%s', 'TABLE', '%s', 'COLUMN', '%s'", columnComment, entityMetadata.getSchemaOrDefault("dbo"), entityMetadata.getTableName(), column.getName());
                columnCommentSQL.append(newLine)
                        .append(format)
                        .append(newLine)
                        .append("go")
                        .append(newLine);
//                        .append("exec sp_addextendedproperty 'MS_Description', '").append(columnComment).append("', 'SCHEMA', '").append(schemaWithoutDatabaseName).append("', 'TABLE', '").append(entityMetadata.getTableName()).append("', 'COLUMN', '").append().append("'")
//                        .append(tableName).append(".").append(getQuoteSQLName(column.getName()))
//                        .append(" IS ").append(columnComment).append(";");
            }
            sql.append(",");
        }
        sql.append(newLine).append(")").append(newLine).append("go").append(newLine);
        if (columnCommentSQL.length() > 0) {
            sql.append(newLine).append(columnCommentSQL);
        }

        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }


    @Override
    protected MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
//        exec sp_rename 'Base_User.Domains', Domains2, 'COLUMN'
//        go

        String format = String.format("exec sp_rename '%s.%s', %s, 'COLUMN'", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()), getQuoteSQLName(renameFrom), getQuoteSQLName(column.getName()));
        sql.append(format).append(newLine)
                .append("go")
                .append(newLine);
//
//        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
//        sql.append(columnDbTypeResult.columnType);
//        if (isNullable(entityMigrationMetadata, column)) {
//            sql.append(" NULL");
//        } else {
//            sql.append(" NOT NULL");
//        }
//
//        String columnComment = getColumnComment(entityMigrationMetadata, column);
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(newLine);
//            sql.append(" COMMENT ON COLUMN ").append(tableName).append(" IS ").append(columnComment);
//            sql.append(";");
//        }
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    @Override
    protected MigrationCommand addColumn(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()))
                .append(" ADD ").append(getQuoteSQLName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }
        sql.append(newLine)
                .append("go")
                .append(newLine);


//        exec sp_addextendedproperty 'MS_Description', '123', 'SCHEMA', 'dbo', 'TABLE', 'Base_User', 'COLUMN', 'column_35'
//        go
        String columnComment = getColumnComment(entityMigrationMetadata, column,"");
        if (EasyStringUtil.isNotBlank(columnComment)) {

            String format = String.format("exec sp_addextendedproperty 'MS_Description', '%s', 'SCHEMA', '%s', 'TABLE', '%s', 'COLUMN', '%s'", columnComment, entityMetadata.getSchemaOrDefault("dbo"), entityMetadata.getTableName(), column.getName());
            sql.append(format).append(newLine)
                    .append("go")
                    .append(newLine);
        }
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }


    @Override
    public MigrationCommand dropTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        return new DefaultMigrationCommand(entityMetadata, "DROP TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()) + ";");
    }

    @Override
    protected ColumnDbTypeResult getColumnDbType0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        return columnTypeMap.get(columnMetadata.getPropertyType());
    }
}
