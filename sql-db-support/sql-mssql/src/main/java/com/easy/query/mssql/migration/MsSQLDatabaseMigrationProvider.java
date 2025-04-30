package com.easy.query.mssql.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.AbstractDatabaseMigrationProvider;
import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.EntityMigrationMetadata;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.TableIndexResult;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * create time 2025/1/19 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {

    public MsSQLDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword, migrationEntityParser);
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
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, " select 1 from dbo.sysobjects where id = object_id(N'" + getQuoteSQLName(querySchema, tableName) + "') and OBJECTPROPERTY(id, N'IsUserTable') = 1", Collections.emptyList());
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

        String tableComment = getTableComment(entityMigrationMetadata, "");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            String format = String.format("exec sp_addextendedproperty 'MS_Description', '%s', 'SCHEMA', '%s', 'TABLE', '%s'", tableComment, entityMetadata.getSchemaOrDefault("dbo"), entityMetadata.getTableName());
            columnCommentSQL.append(newLine)
                    .append(format)
                    .append(newLine).append("go");
        }

        sql.append("USE ").append(getQuoteSQLName(this.getDatabaseName()))
//                .append("go")
                .append(newLine);
        sql.append("CREATE TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(" ( ");
        int i = entityMetadata.getColumns().size();
        for (ColumnMetadata column : entityMetadata.getColumns()) {
            i--;
            sql.append(newLine)
                    .append(getQuoteSQLName(column.getName()))
                    .append(" ");
            ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
            sql.append(columnDbTypeResult.columnType);
            boolean nullable = migrationEntityParser.isNullable(entityMigrationMetadata, column);
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
            String columnComment = getColumnComment(entityMigrationMetadata, column, "");
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
            if (i > 0) {
                sql.append(",");
            }
        }
        sql.append(newLine).append(")");
        if (columnCommentSQL.length() > 0) {
            sql.append(newLine).append(columnCommentSQL);
        }
        sql.append(";");

        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }


    @Override
    protected MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
//        exec sp_rename 'Base_User.Domains', Domains2, 'COLUMN'
//        go

        String format = String.format("exec sp_rename '%s.%s', %s, 'COLUMN'", getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()), getQuoteSQLName(renameFrom), getQuoteSQLName(column.getName()));
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
        sql.append("ALTER TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()))
                .append(" ADD ").append(getQuoteSQLName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (migrationEntityParser.isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }
        sql.append(newLine)
                .append("go")
                .append(newLine);


//        exec sp_addextendedproperty 'MS_Description', '123', 'SCHEMA', 'dbo', 'TABLE', 'Base_User', 'COLUMN', 'column_35'
//        go
        String columnComment = getColumnComment(entityMigrationMetadata, column, "");
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
        return new DefaultMigrationCommand(entityMetadata, "DROP TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()) + ";");
    }
    @Override
    protected MigrationCommand createIndex(EntityMigrationMetadata entityMigrationMetadata, TableIndexResult tableIndex) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE ");
        if (tableIndex.unique) {
            sql.append("UNIQUE INDEX ");
        } else {
            sql.append("INDEX ");
        }
        sql.append(tableIndex.indexName);
        sql.append(" ON ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()));
        sql.append(" (");
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < tableIndex.fields.size(); i++) {
            TableIndexResult.EntityField entityField = tableIndex.fields.get(i);
            String column = getQuoteSQLName(entityField.columnName) + " " + (entityField.asc ? "ASC" : "DESC");
            joiner.add(column);
        }
        sql.append(joiner);
        sql.append(");");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }
}
