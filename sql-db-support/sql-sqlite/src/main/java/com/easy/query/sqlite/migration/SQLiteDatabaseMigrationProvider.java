package com.easy.query.sqlite.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * create time 2025/1/14 13:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {

    public SQLiteDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword, migrationEntityParser);
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
    public MigrationCommand renameTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        String sql = "ALTER TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getOldTableName()) + " RENAME TO " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()) + ";";
        return new DefaultMigrationCommand(entityMetadata, sql);
    }

    @Override
    public MigrationCommand createTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(" ( ");
        for (ColumnMetadata column : entityMetadata.getColumns()) {
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
                sql.append(" PRIMARY KEY AUTOINCREMENT");
            }
//            String columnComment = getColumnComment(entityMigrationMetadata, column);
//            if (EasyStringUtil.isNotBlank(columnComment)) {
//                sql.append(" COMMENT ").append(columnComment);
//            }
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
        }else{
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(newLine).append(");");
//        String tableComment = getTableComment(entityMigrationMetadata);
//        if (EasyStringUtil.isNotBlank(tableComment)) {
//            sql.append(" COMMENT=").append(tableComment);
//        }
//        sql.append(";");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    @Override
    protected MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        //ALTER TABLE table_name RENAME COLUMN old_column_name TO new_column_name;
        sql.append("ALTER TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()))
                .append(" RENAME COLUMN ").append(getQuoteSQLName(renameFrom))
                .append(" TO ")
                .append(getQuoteSQLName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (migrationEntityParser.isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }

//        String columnComment = getColumnComment(entityMigrationMetadata, column);
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(" COMMENT").append(columnComment);
//        }
        sql.append(";");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    @Override
    protected MigrationCommand addColumn(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()))
                .append(" ADD COLUMN ").append(getQuoteSQLName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (migrationEntityParser.isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }

//        String columnComment = getColumnComment(entityMigrationMetadata, column);
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(" COMMENT").append(columnComment);
//        }
        sql.append(";");
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
