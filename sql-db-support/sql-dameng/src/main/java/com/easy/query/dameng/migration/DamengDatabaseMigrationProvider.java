package com.easy.query.dameng.migration;

import com.easy.query.core.basic.api.database.Credentials;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.migration.AbstractDatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.migration.commands.MultiMigrationCommand;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.ForeignKeyMigrationData;
import com.easy.query.core.migration.data.IndexMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * create time 2025/1/19 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {
    public DamengDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword, migrationEntityParser);
    }

//    @Override
//    public boolean databaseExists() {
//        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, , Collections.singletonList(getDatabaseName()));
//        return EasyCollectionUtil.isNotEmpty(maps);
//    }
//
//    @Override
//    public MigrationCommand createDatabaseCommand() {
//        log.warn("dameng not support create database command.");
//        return null;


    @Override
    public void createDatabaseIfNotExists(Function<DataSource, Credentials> jdbcCredentialsByDataSourceFunction) {

    }

    @Override
    public String databaseExistSQL(String databaseName) {
       return String.format("select 1 from sys.dba_users where username='%s'",databaseName);
    }

    @Override
    public String createDatabaseSQL(String databaseName) {
        throw new UnsupportedOperationException("dameng not support create database command.");
    }

    ////        String databaseSQL = "CREATE SCHEMA IF NOT EXISTS " + getQuoteSQLName(databaseName) + ";";
////        return new DefaultMigrationCommand(null, databaseSQL);
//    }


    @Override
    public String getDatabaseName() {
        if (databaseName == null) {
            try (Connection connection = dataSource.getConnection()) {
                String user = connection.getMetaData().getUserName();
                if (EasyStringUtil.isBlank(user)) {
                    throw new EasyQueryInvalidOperationException("cant get database user name.");
                }
                databaseName = user.toUpperCase(Locale.ROOT);
            } catch (SQLException e) {
                throw new EasyQueryInvalidOperationException("cant get database user name: " + e.getMessage());
            }
        }
        return databaseName;
    }

    @Override
    public boolean tableExists(String schema,String tableName) {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        sqlParameters.add(EasyStringUtil.isBlank(schema) ? getDatabaseName() : schema);
        sqlParameters.add(tableName);
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select 1 from all_tab_comments where owner=? and table_name=?", sqlParameters);
        return EasyCollectionUtil.isNotEmpty(maps);
    }
    @Override
    public MigrationCommand renameTable(TableMigrationData tableMigrationData) {
        String sql = "ALTER TABLE " + getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getOldTableName())
                + " RENAME TO " + getQuoteSQLName(tableMigrationData.getTableName());
        return new DefaultMigrationCommand(sql);
    }

    @Override
    public MigrationCommand createTable(TableMigrationData tableMigrationData) {
        String tableName = getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName());
        List<ColumnMigrationData> primaryKeys = EasyCollectionUtil.filter(tableMigrationData.getColumns(), ColumnMigrationData::isPrimary);
        List<String> statements = new ArrayList<>();

        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(tableName).append(" ( ");
        for (ColumnMigrationData column : tableMigrationData.getColumns()) {
            sql.append(newLine).append(getQuoteSQLName(column.getName())).append(" ");
            if (column.isGeneratedKey()) {
                sql.append("BIGINT IDENTITY(1,1) NOT NULL,");
                continue;
            }
            sql.append(column.getDbType());
            if (column.isNotNull()) {
                sql.append(" NOT NULL ");
            }
            if (EasyStringUtil.isNotBlank(column.getDefValue())) {
                sql.append(" DEFAULT ").append(column.getDefValue());
            }
            sql.append(",");
        }
        if (EasyCollectionUtil.isNotEmpty(primaryKeys)) {
            sql.append(newLine).append("CONSTRAINT ")
                    .append(getQuoteSQLName(truncate(tableMigrationData.getTableName() + "_PK", 30)))
                    .append(" PRIMARY KEY (")
                    .append(primaryKeys.stream().map(key -> getQuoteSQLName(key.getName())).collect(Collectors.joining(",")))
                    .append("),");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(newLine).append(")");
        statements.add(sql.toString());

        if (EasyStringUtil.isNotBlank(tableMigrationData.getComment())) {
            statements.add("COMMENT ON TABLE " + tableName + " IS '" + escape(tableMigrationData.getComment()) + "'");
        }
        for (ColumnMigrationData column : tableMigrationData.getColumns()) {
            if (EasyStringUtil.isNotBlank(column.getComment())) {
                statements.add("COMMENT ON COLUMN " + tableName + "." + getQuoteSQLName(column.getName())
                        + " IS '" + escape(column.getComment()) + "'");
            }
        }
        return new MultiMigrationCommand(statements);
    }

    @Override
    protected MigrationCommand renameColumn(TableMigrationData table, String renameFrom, ColumnMigrationData column) {
        String sql = "ALTER TABLE " + getQuoteSQLName(table.getSchema(), table.getTableName())
                + " RENAME COLUMN " + getQuoteSQLName(renameFrom) + " TO " + getQuoteSQLName(column.getName());
        return new DefaultMigrationCommand(sql);
    }

    @Override
    protected MigrationCommand addColumn(TableMigrationData table, ColumnMigrationData column) {
        StringBuilder sql = new StringBuilder("ALTER TABLE ")
                .append(getQuoteSQLName(table.getSchema(), table.getTableName()))
                .append(" ADD ").append(getQuoteSQLName(column.getName())).append(" ").append(column.getDbType());
        boolean hasDefault = EasyStringUtil.isNotBlank(column.getDefValue());
        if (hasDefault) {
            sql.append(" DEFAULT ").append(column.getDefValue());
        }
        sql.append(column.isNotNull() && hasDefault ? " NOT NULL" : " NULL");
        return new DefaultMigrationCommand(sql.toString());
    }


    @Override
    public MigrationCommand dropTable(TableMigrationData table) {
        return new DefaultMigrationCommand("DROP TABLE " + getQuoteSQLName(table.getSchema(), table.getTableName()));
    }
    @Override
    protected MigrationCommand createIndex(TableMigrationData table, IndexMigrationData tableIndex) {
        StringBuilder sql = new StringBuilder("CREATE ");
        sql.append(tableIndex.isUnique() ? "UNIQUE INDEX " : "INDEX ");
        sql.append(getQuoteSQLName(tableIndex.getIndexName())).append(" ON ")
                .append(getQuoteSQLName(table.getSchema(), table.getTableName())).append(" (");
        StringJoiner joiner = new StringJoiner(",");
        for (IndexMigrationData.EntityField field : tableIndex.getFields()) {
            joiner.add(getQuoteSQLName(field.getColumnName()) + (field.isAsc() ? " ASC" : " DESC"));
        }
        sql.append(joiner).append(")");
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
        return new DefaultMigrationCommand(sql.toString());
    }

    private String truncate(String name, int maxLength) {
        return name.length() <= maxLength ? name : name.substring(0, maxLength);
    }

    private String escape(String text) {
        return text.replace("'", "''");
    }

    @Override
    protected String tableQuerySql() {
        return "SELECT owner AS table_schema, table_name FROM all_tables WHERE owner = ?";
    }
}
