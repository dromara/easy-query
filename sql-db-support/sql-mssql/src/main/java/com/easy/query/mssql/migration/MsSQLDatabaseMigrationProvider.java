package com.easy.query.mssql.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.migration.AbstractDatabaseMigrationProvider;
import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.ForeignKeyMigrationData;
import com.easy.query.core.migration.data.IndexMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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
    public MigrationCommand renameTable(TableMigrationData tableMigrationData) {

        return new DefaultMigrationCommand("ALTER TABLE " + getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getOldTableName()) + " RENAME TO " + getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName()) + ";");

    }

    @Override
    public MigrationCommand createTable(TableMigrationData tableMigrationData) {

        StringBuilder sql = new StringBuilder();
        StringBuilder columnCommentSQL = new StringBuilder();

//        String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null);
//        String schema = EasyToSQLUtil.getSchema(sqlKeyword, entityMetadata, entityMetadata.getSchemaOrNull(), null, null);
//        String schemaWithoutDatabaseName = EasyToSQLUtil.getSchemaWithoutDatabaseName(entityMetadata, entityMetadata.getSchemaOrNull(), null, "dbo");

        String tableComment = getTableComment(tableMigrationData, "");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            String format = String.format("exec sp_addextendedproperty 'MS_Description', '%s', 'SCHEMA', '%s', 'TABLE', '%s'", tableComment, tableMigrationData.getSchemaOrDefault("dbo"), tableMigrationData.getTableName());
            columnCommentSQL.append(newLine)
                    .append(format)
                    .append(newLine);
        }

        sql.append("USE ").append(getQuoteSQLName(this.getDatabaseName()))
                .append(newLine);
        sql.append("CREATE TABLE ").append(getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName())).append(" ( ");
        int i = tableMigrationData.getColumns().size();
        for (ColumnMigrationData column : tableMigrationData.getColumns()) {
            i--;
            sql.append(newLine)
                    .append(getQuoteSQLName(column.getName()))
                    .append(" ");
            ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(column.getDbType(), column.getDefValue());
            sql.append(columnDbTypeResult.columnType);
            if (column.isNotNull()) {
                sql.append(" NOT NULL ");
            } else {
                sql.append(" NULL ");
            }
            if (column.isGeneratedKey()) {
                sql.append(" IDENTITY(1,1)");
            }else{
                if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
                    sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
                }
            }
            if (column.isPrimary()) {
                sql.append(" PRIMARY KEY ");
            }
            String columnComment = getColumnComment(column, "");
//            exec sp_addextendedproperty 'MS_Description', '微信唯一识别码', 'SCHEMA', 'dbo', 'TABLE', 'Base_User', 'COLUMN', 'OpenId'
//            go
            if (EasyStringUtil.isNotBlank(columnComment)) {
                String format = String.format("exec sp_addextendedproperty 'MS_Description', '%s', 'SCHEMA', '%s', 'TABLE', '%s', 'COLUMN', '%s'", columnComment, tableMigrationData.getSchemaOrDefault("dbo"), tableMigrationData.getTableName(), column.getName());
                columnCommentSQL.append(newLine)
                        .append(format)
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

        return new DefaultMigrationCommand(sql.toString());
    }

    @Override
    protected MigrationCommand renameColumn(TableMigrationData table, String renameFrom, ColumnMigrationData column) {
        StringBuilder sql = new StringBuilder();
//        exec sp_rename 'Base_User.Domains', Domains2, 'COLUMN'
//        go

        String format = String.format("exec sp_rename '%s.%s', %s, 'COLUMN'", getQuoteSQLName(table.getSchema(), table.getTableName()), getQuoteSQLName(renameFrom), getQuoteSQLName(column.getName()));
        sql.append(format).append(newLine);
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
        return new DefaultMigrationCommand(sql.toString());
    }

    @Override
    protected MigrationCommand addColumn(TableMigrationData table, ColumnMigrationData column) {
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(table.getSchema(), table.getTableName()))
                .append(" ADD ").append(getQuoteSQLName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(column.getDbType(), column.getDefValue());
        sql.append(columnDbTypeResult.columnType);
        if (column.isNotNull()) {
            sql.append(" NOT NULL");
        } else {
            sql.append(" NULL");
        }
        if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
            sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
        }
        sql.append(newLine);


//        exec sp_addextendedproperty 'MS_Description', '123', 'SCHEMA', 'dbo', 'TABLE', 'Base_User', 'COLUMN', 'column_35'
//        go
        String columnComment = getColumnComment(column, "");
        if (EasyStringUtil.isNotBlank(columnComment)) {

            String format = String.format("exec sp_addextendedproperty 'MS_Description', '%s', 'SCHEMA', '%s', 'TABLE', '%s', 'COLUMN', '%s'", columnComment, table.getSchemaOrDefault("dbo"), table.getTableName(), column.getName());
            sql.append(format).append(newLine);
        }
        return new DefaultMigrationCommand(sql.toString());
    }


    @Override
    public MigrationCommand dropTable(TableMigrationData table) {
        return new DefaultMigrationCommand("DROP TABLE " + getQuoteSQLName(table.getSchema(), table.getTableName()) + ";");
    }
    @Override
    protected MigrationCommand createIndex(TableMigrationData table, IndexMigrationData tableIndex) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE ");
        if (tableIndex.isUnique()) {
            sql.append("UNIQUE INDEX ");
        } else {
            sql.append("INDEX ");
        }
        sql.append(tableIndex.getIndexName());
        sql.append(" ON ").append(getQuoteSQLName(table.getSchema(), table.getTableName()));
        sql.append(" (");
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < tableIndex.getFields().size(); i++) {
            IndexMigrationData.EntityField entityField = tableIndex.getFields().get(i);
            String column = getQuoteSQLName(entityField.getColumnName()) + " " + (entityField.isAsc() ? "ASC" : "DESC");
            joiner.add(column);
        }
        sql.append(joiner);
        sql.append(");");
        return new DefaultMigrationCommand(sql.toString());
    }
    @Override
    protected MigrationCommand createTableForeignKey(TableMigrationData table, ForeignKeyMigrationData foreignKey) {
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ");
        sql.append(getQuoteSQLName(table.getSchema(), table.getTableName()));
        sql.append(" ADD CONSTRAINT ").append(foreignKey.getName());
        sql.append(" FOREIGN KEY (");

        String selfColumns = Arrays.stream(foreignKey.getSelfColumn()).map(self -> getQuoteSQLName(self)).collect(Collectors.joining(","));
        sql.append(selfColumns);
        sql.append(") REFERENCES ");
        sql.append(getQuoteSQLName(foreignKey.getTargetTable()));
        sql.append(" (");
        String targetColumns = Arrays.stream(foreignKey.getTargetColumn()).map(target -> getQuoteSQLName(target)).collect(Collectors.joining(","));
        sql.append(targetColumns);
        sql.append(")");

        if (EasyStringUtil.isNotBlank(foreignKey.getAction())) {
            sql.append(" ").append(foreignKey.getAction()).append(" ");
        }
        sql.append(";");
        return new DefaultMigrationCommand(sql.toString());
    }
}
