package com.easy.query.h2.migration;

import com.easy.query.core.basic.api.database.Credentials;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * create time 2025/1/14 13:31
 * 生成数据库迁移提供者
 *
 * @author xuejiaming
 */
public class H2DatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {

    public H2DatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword, migrationEntityParser);
    }

    @Override
    public String databaseExistSQL(String databaseName) {
        throw new UnsupportedOperationException("h2 database not support check database exists.");
    }

    @Override
    public String createDatabaseSQL(String databaseName) {
        throw new UnsupportedOperationException("h2 database not support create database command.");
    }
    @Override
    public void createDatabaseIfNotExists(Function<DataSource, Credentials> jdbcCredentialsByDataSourceFunction) {

    }

    @Override
    public boolean tableExists(String schema, String tableName) {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        if (EasyStringUtil.isBlank(schema)) {
            sqlParameters.add("PUBLIC");
        } else {
            sqlParameters.add(schema);
        }
        sqlParameters.add(tableName);
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?;", sqlParameters);
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public MigrationCommand renameTable(TableMigrationData tableMigrationData) {
        String sql = "ALTER TABLE " + getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getOldTableName()) + " RENAME TO " + getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName()) + ";";
        return new DefaultMigrationCommand(sql);
    }

    @Override
    public MigrationCommand createTable(TableMigrationData tableMigrationData) {

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ").append(getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName())).append(" ( ");
        for (ColumnMigrationData column : tableMigrationData.getColumns()) {
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
                sql.append(" AUTO_INCREMENT");
            } else {

                if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
                    sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
                }
            }

//            if (column.isPrimary()) {
//                sql.append(" PRIMARY KEY ");
//            }
            String columnComment = getColumnComment(column, "'");
            if (EasyStringUtil.isNotBlank(columnComment)) {
                sql.append(" COMMENT ").append(columnComment);
            }
            sql.append(",");
        }

        List<ColumnMigrationData> keys = EasyCollectionUtil.filter(tableMigrationData.getColumns(), s -> s.isPrimary());
        if (EasyCollectionUtil.isNotEmpty(keys)) {
            sql.append(" ").append(newLine).append(" PRIMARY KEY (");
            int i = keys.size();
            for (ColumnMigrationData keyColumn : keys) {
                i--;
                sql.append(getQuoteSQLName(keyColumn.getName()));
                if (i > 0) {
                    sql.append(", ");
                } else {
                    sql.append(")");
                }
            }
        }else{
            sql.deleteCharAt(sql.length() - 1);
        }
        String tableComment = getTableComment(tableMigrationData, "'");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            sql.append(" COMMENT=").append(tableComment);
        }
        sql.append(newLine).append(");");
        return new DefaultMigrationCommand(sql.toString());
    }

    @Override
    protected MigrationCommand renameColumn(TableMigrationData table, String renameFrom, ColumnMigrationData column) {

        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(table.getSchema(), table.getTableName()))
                .append(" CHANGE ").append(getQuoteSQLName(renameFrom))
                .append(" ")
                .append(getQuoteSQLName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(column.getDbType(), column.getDefValue());
        sql.append(columnDbTypeResult.columnType);
        if (column.isNotNull()) {
            sql.append(" NOT NULL");
        } else {
            sql.append(" NULL");
        }

        String columnComment = getColumnComment(column, "'");
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(" COMMENT ").append(columnComment);
        }
        sql.append(";");
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

        String columnComment = getColumnComment(column, "'");
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(" COMMENT ").append(columnComment);
        }
        sql.append(";");
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
        sql.append(" ADD CONSTRAINT ");
        sql.append(foreignKey.getName());
        sql.append(" FOREIGN KEY (");
        for (int i = 0; i < foreignKey.getSelfColumn().length; i++) {
            if (i > 0) {
                sql.append(",");
            }
            sql.append(getQuoteSQLName(foreignKey.getSelfColumn()[i]));
        }
        sql.append(") REFERENCES ");
        sql.append(getQuoteSQLName(foreignKey.getTargetTable()));
        sql.append(" (");
        for (int i = 0; i < foreignKey.getTargetColumn().length; i++) {
            if (i > 0) {
                sql.append(",");
            }
            sql.append(getQuoteSQLName(foreignKey.getTargetColumn()[i]));
        }
        sql.append(")");

        if (EasyStringUtil.isNotBlank(foreignKey.getAction())) {
            sql.append(" ").append(foreignKey.getAction()).append(" ");
        }
        sql.append(";");
        return new DefaultMigrationCommand(sql.toString());
    }
}
