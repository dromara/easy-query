package com.easy.query.sqlite.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.AbstractDatabaseMigrationProvider;
import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.EntityMigrationMetadata;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.TableForeignKeyResult;
import com.easy.query.core.migration.TableIndexResult;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.ForeignKeyMigrationData;
import com.easy.query.core.migration.data.IndexMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyBoolUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * create time 2025/1/14 13:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {

    private final ServiceProvider serviceProvider;

    public SQLiteDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser, ServiceProvider serviceProvider) {
        super(dataSource, sqlKeyword, migrationEntityParser);
        this.serviceProvider = serviceProvider;
    }

    @Override
    public String databaseExistSQL(String databaseName) {
        throw new UnsupportedOperationException("sqlite not support check database exists.");
    }

    @Override
    public String createDatabaseSQL(String databaseName) {
        throw new UnsupportedOperationException("sqlite not support create database command.");
    }

    @Override
    public String getDatabaseName() {
        return "main";
    }
    @Override
    public boolean tableExists(String schema, String tableName) {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, String.format("select 1 from %s.sqlite_master where type='table' and name='%s'", getDatabaseName(), tableName), sqlParameters);
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
//            if(column.isPrimary()){
//                sql.append(" PRIMARY KEY");
//            }
            if (column.isGeneratedKey()) {
                sql.append(" AUTOINCREMENT");
            }else{
                if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
                    sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
                }
            }
//            String columnComment = getColumnComment(entityMigrationMetadata, column);
//            if (EasyStringUtil.isNotBlank(columnComment)) {
//                sql.append(" COMMENT ").append(columnComment);
//            }
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
        List<ForeignKeyMigrationData> foreignKeys = tableMigrationData.getForeignKeys();
        if(EasyCollectionUtil.isNotEmpty(foreignKeys)){
            for (ForeignKeyMigrationData foreignKeyMigrationData : foreignKeys) {
                String selfColumns = Arrays.stream(foreignKeyMigrationData.getSelfColumn()).map(self -> getQuoteSQLName(self)).collect(Collectors.joining(","));
                String targetColumns = Arrays.stream(foreignKeyMigrationData.getTargetColumn()).map(target -> getQuoteSQLName(target)).collect(Collectors.joining(","));
                sql.append(newLine).append(",");
                sql.append(" FOREIGN KEY (")
                        .append(selfColumns)
                        .append(") REFERENCES ")
                        .append(getQuoteSQLName(foreignKeyMigrationData.getTargetTable()))
                        .append("(")
                        .append(targetColumns)
                        .append(")");
                if (EasyStringUtil.isNotBlank(foreignKeyMigrationData.getAction())) {
                    sql.append(" ").append(foreignKeyMigrationData.getAction()).append(" ");
                }
            }
        }
        sql.append(newLine).append(");");
        return new DefaultMigrationCommand(sql.toString());
    }

    @Override
    protected MigrationCommand renameColumn(TableMigrationData table, String renameFrom, ColumnMigrationData column) {
        StringBuilder sql = new StringBuilder();
        //ALTER TABLE table_name RENAME COLUMN old_column_name TO new_column_name;
        sql.append("ALTER TABLE ").append(getQuoteSQLName(table.getSchema(), table.getTableName()))
                .append(" RENAME COLUMN ").append(getQuoteSQLName(renameFrom))
                .append(" TO ")
                .append(getQuoteSQLName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(column.getDbType(), column.getDefValue());
        sql.append(columnDbTypeResult.columnType);
        if (column.isNotNull()) {
            sql.append(" NOT NULL");
        } else {
            sql.append(" NULL");
        }

        sql.append(";");
        return new DefaultMigrationCommand(sql.toString());
    }

    @Override
    protected MigrationCommand addColumn(TableMigrationData table, ColumnMigrationData column) {
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(table.getSchema(), table.getTableName()))
                .append(" ADD COLUMN ").append(getQuoteSQLName(column.getName())).append(" ");

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
//        String columnComment = getColumnComment(entityMigrationMetadata, column);
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(" COMMENT").append(columnComment);
//        }
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

    /**
     * sqlite不支持alter table来额外增加外键必须在create table的时候直接写入外键
     * @param table
     * @param foreignKey
     * @return
     */
    @Override
    protected MigrationCommand createTableForeignKey(TableMigrationData table, ForeignKeyMigrationData foreignKey) {
        return null;
    }
}
