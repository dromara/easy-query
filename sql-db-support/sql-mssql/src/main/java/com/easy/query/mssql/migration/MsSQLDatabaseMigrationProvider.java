package com.easy.query.mssql.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.migration.AbstractDatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.commands.MultiMigrationCommand;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.ForeignKeyMigrationData;
import com.easy.query.core.migration.data.IndexMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
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
//        return new MultiMigrationCommand(databaseSQL);
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
        String oldTableName = getQuoteSQLName(tableMigrationData.getSchemaOrDefault("dbo"), tableMigrationData.getOldTableName()).replace("'", "''");
        String newTableName = tableMigrationData.getTableName().replace("'", "''");
        return new MultiMigrationCommand("EXEC sp_rename N'" + oldTableName + "', N'" + newTableName + "'");
    }

    @Override
    public MigrationCommand createTable(TableMigrationData tableMigrationData) {
        String schema = tableMigrationData.getSchemaOrDefault("dbo");
        String tableName = getQuoteSQLName(schema, tableMigrationData.getTableName());
        List<ColumnMigrationData> primaryKeys = EasyCollectionUtil.filter(tableMigrationData.getColumns(), ColumnMigrationData::isPrimary);
        List<String> statements = new ArrayList<>();

        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(tableName).append(" ( ");
        for (ColumnMigrationData column : tableMigrationData.getColumns()) {
            sql.append(newLine).append(getQuoteSQLName(column.getName())).append(" ").append(column.getDbType());
            if (column.isGeneratedKey()) {
                sql.append(" IDENTITY(1,1)");
            }
            sql.append(column.isNotNull() ? " NOT NULL " : " NULL ");
            if (!column.isGeneratedKey() && EasyStringUtil.isNotBlank(column.getDefValue())) {
                sql.append(" DEFAULT ").append(column.getDefValue());
            }
            sql.append(",");
        }
        if (EasyCollectionUtil.isNotEmpty(primaryKeys)) {
            sql.append(newLine).append("CONSTRAINT ")
                    .append(getQuoteSQLName(truncate(tableMigrationData.getTableName() + "_PK", 128)))
                    .append(" PRIMARY KEY (")
                    .append(primaryKeys.stream().map(key -> getQuoteSQLName(key.getName())).collect(Collectors.joining(",")))
                    .append("),");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(newLine).append(")");
        statements.add(sql.toString());

        if (EasyStringUtil.isNotBlank(tableMigrationData.getComment())) {
            statements.add(tableComment(schema, tableMigrationData.getTableName(), tableMigrationData.getComment()));
        }
        for (ColumnMigrationData column : tableMigrationData.getColumns()) {
            if (EasyStringUtil.isNotBlank(column.getComment())) {
                statements.add(columnComment(schema, tableMigrationData.getTableName(), column.getName(), column.getComment()));
            }
        }
        return new MultiMigrationCommand(statements);
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
        return new MultiMigrationCommand(sql.toString());
    }

    @Override
    protected MigrationCommand addColumn(TableMigrationData table, ColumnMigrationData column) {
        String schema = table.getSchemaOrDefault("dbo");
        StringBuilder sql = new StringBuilder("ALTER TABLE ")
                .append(getQuoteSQLName(schema, table.getTableName()))
                .append(" ADD ").append(getQuoteSQLName(column.getName())).append(" ").append(column.getDbType());
        boolean hasDefault = EasyStringUtil.isNotBlank(column.getDefValue());
        if (hasDefault) {
            sql.append(" DEFAULT ").append(column.getDefValue());
        }
        if (column.isNotNull() && hasDefault) {
            sql.append(" NOT NULL");
        }
        if (EasyStringUtil.isBlank(column.getComment())) {
            return new MultiMigrationCommand(sql.toString());
        }
        List<String> statements = new ArrayList<>();
        statements.add(sql.toString());
        statements.add(columnComment(schema, table.getTableName(), column.getName(), column.getComment()));
        return new MultiMigrationCommand(statements);
    }


    @Override
    public MigrationCommand dropTable(TableMigrationData table) {
        return new MultiMigrationCommand("DROP TABLE " + getQuoteSQLName(table.getSchema(), table.getTableName()) + ";");
    }

    @Override
    protected MigrationCommand createIndex(TableMigrationData table, IndexMigrationData tableIndex) {
        StringBuilder sql = new StringBuilder("CREATE ");
        sql.append(tableIndex.isUnique() ? "UNIQUE INDEX " : "INDEX ");
        sql.append(tableIndex.getIndexName()).append(" ON ")
                .append(getQuoteSQLName(table.getSchemaOrDefault("dbo"), table.getTableName())).append(" (");
        StringJoiner joiner = new StringJoiner(",");
        for (IndexMigrationData.EntityField field : tableIndex.getFields()) {
            joiner.add(getQuoteSQLName(field.getColumnName()) + (field.isAsc() ? " ASC" : " DESC"));
        }
        sql.append(joiner).append(")");
        if (EasyStringUtil.isBlank(tableIndex.getComment())) {
            return new MultiMigrationCommand(sql.toString());
        }
        List<String> statements = new ArrayList<>();
        statements.add(sql.toString());
        statements.add(indexComment(table.getSchemaOrDefault("dbo"), table.getTableName(), tableIndex.getIndexName(), tableIndex.getComment()));
        return new MultiMigrationCommand(statements);
    }

    private String indexComment(String schema, String tableName, String indexName, String comment) {
        return "EXEC sp_addextendedproperty 'MS_Description', N'" + escape(comment)
                + "', 'SCHEMA', N'" + schema + "', 'TABLE', N'" + tableName
                + "', 'INDEX', N'" + indexName + "'";
    }

    private String tableComment(String schema, String tableName, String comment) {
        return "EXEC sp_addextendedproperty 'MS_Description', N'" + escape(comment)
                + "', 'SCHEMA', N'" + schema + "', 'TABLE', N'" + tableName + "'";
    }

    private String columnComment(String schema, String tableName, String columnName, String comment) {
        return "EXEC sp_addextendedproperty 'MS_Description', N'" + escape(comment)
                + "', 'SCHEMA', N'" + schema + "', 'TABLE', N'" + tableName + "', 'COLUMN', N'" + columnName + "'";
    }

    private String truncate(String name, int maxLength) {
        return name.length() <= maxLength ? name : name.substring(0, maxLength);
    }

    private String escape(String text) {
        return text.replace("'", "''");
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
        return new MultiMigrationCommand(sql.toString());
    }

    @Override
    protected String tableQuerySql() {
        return "SELECT table_schema, table_name FROM information_schema.tables WHERE table_catalog = ? AND table_type = 'BASE TABLE'";
    }

    @Override
    protected List<Object> params() {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        String databaseName = getDatabaseName();
        sqlParameters.add(databaseName);
        return sqlParameters;
    }
}
