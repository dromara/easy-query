package com.easy.query.tsdb.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.migration.DefaultDatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/1/18 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class TSDBDatabaseMigrationProvider extends DefaultDatabaseMigrationProvider {
    public TSDBDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword,migrationEntityParser);
    }

    @Override
    public String databaseExistSQL(String databaseName) {
        return "SHOW DATABASES LIKE '" + databaseName + "'";
    }

    @Override
    public String createDatabaseSQL(String databaseName) {
        return "CREATE DATABASE IF NOT EXISTS " + getQuoteSQLName(databaseName);
    }

    @Override
    public MigrationCommand createTable(TableMigrationData tableMigrationData) {
        List<ColumnMigrationData> tags = EasyCollectionUtil.filter(tableMigrationData.getColumns(), ColumnMigrationData::isTag);
        List<ColumnMigrationData> columns = EasyCollectionUtil.filter(tableMigrationData.getColumns(), s -> !s.isTag());

        StringBuilder sql = new StringBuilder();
        if (EasyCollectionUtil.isNotEmpty(tags)) {
            sql.append("CREATE STABLE IF NOT EXISTS ");
        } else {
            sql.append("CREATE TABLE IF NOT EXISTS ");
        }
        sql.append(getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName())).append(" ( ");
        
        for (int i = 0; i < columns.size(); i++) {
            ColumnMigrationData column = columns.get(i);
            sql.append(newLine)
                    .append(getQuoteSQLName(column.getName()))
                    .append(" ");
            
            ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(column.getDbType(), column.getDefValue());
            String columnType = columnDbTypeResult.columnType;
            // TDengine 3.0 首列必须是 TIMESTAMP
            if (i == 0 && columnType.toUpperCase().startsWith("DATETIME")) {
                sql.append("TIMESTAMP");
            } else {
                sql.append(columnType);
            }
            // TDengine 语法不支持显式的 NULL/NOT NULL 声明
            sql.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(newLine).append(" )");

        if (EasyCollectionUtil.isNotEmpty(tags)) {
            sql.append(" TAGS ( ");
            for (ColumnMigrationData tag : tags) {
                sql.append(newLine)
                        .append(getQuoteSQLName(tag.getName()))
                        .append(" ");
                ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(tag.getDbType(), tag.getDefValue());
                sql.append(columnDbTypeResult.columnType);
                // 标签列同样不需要 NULL 声明
                sql.append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(newLine).append(" )");
        }
        sql.append(";");
        return new DefaultMigrationCommand(sql.toString());
    }

    @Override
    public boolean tableExists(String schema, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet rs = metaData.getTables(schema, null, tableName, null)) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new com.easy.query.core.exception.EasyQuerySQLCommandException(e);
        }
    }

    @Override
    protected MigrationCommand renameColumn(TableMigrationData tableMigrationData, String oldColumnName, ColumnMigrationData columnMigrationData) {
        String sql = "ALTER TABLE " + getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName()) +
                " RENAME COLUMN " + getQuoteSQLName(oldColumnName) + " TO " + getQuoteSQLName(columnMigrationData.getName()) + ";";
        return new DefaultMigrationCommand(sql);
    }

    @Override
    protected MigrationCommand addColumn(TableMigrationData tableMigrationData, ColumnMigrationData columnMigrationData) {
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName()))
                .append(" ADD COLUMN ").append(getQuoteSQLName(columnMigrationData.getName())).append(" ");
        ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(columnMigrationData.getDbType(), columnMigrationData.getDefValue());
        sql.append(columnDbTypeResult.columnType);
        sql.append(";");
        return new DefaultMigrationCommand(sql.toString());
    }

    @Override
    protected MigrationCommand createIndex(TableMigrationData table, com.easy.query.core.migration.data.IndexMigrationData tableIndex) {
        return null; // TDengine 不支持传统索引
    }

    @Override
    protected MigrationCommand createTableForeignKey(TableMigrationData tableMigrationData, com.easy.query.core.migration.data.ForeignKeyMigrationData foreignKeyMigrationData) {
        return null; // TDengine 不支持外键
    }
}
