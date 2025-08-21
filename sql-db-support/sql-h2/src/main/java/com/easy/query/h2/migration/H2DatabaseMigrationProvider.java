package com.easy.query.h2.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
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
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
                sql.append(" AUTO_INCREMENT");
            } else {

                if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
                    sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
                }
            }

            if (column.isPrimary()) {
                sql.append(" PRIMARY KEY ");
            }
            String columnComment = getColumnComment(entityMigrationMetadata, column, "'");
            if (EasyStringUtil.isNotBlank(columnComment)) {
                sql.append(" COMMENT ").append(columnComment);
            }
            sql.append(",");
        }
//        Collection<String> keyProperties = entityMetadata.getKeyProperties();
//        if (EasyCollectionUtil.isNotEmpty(keyProperties)) {
//            sql.append(" ").append(newLine).append(" PRIMARY KEY (");
//            int i = keyProperties.size();
//            for (String keyProperty : keyProperties) {
//                i--;
//                ColumnMetadata keyColumn = entityMetadata.getColumnNotNull(keyProperty);
//                sql.append(getQuoteSQLName(keyColumn.getName()));
//                if (i > 0) {
//                    sql.append(", ");
//                } else {
//                    sql.append(")");
//                }
//            }
//        } else {
//            sql.deleteCharAt(sql.length() - 1);
//        }
        sql.append(newLine).append(")");
        String tableComment = getTableComment(entityMigrationMetadata, "'");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            sql.append(" COMMENT=").append(tableComment);
        }
        sql.append(";");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }


    @Override
    protected MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column) {

        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()))
                .append(" CHANGE ").append(getQuoteSQLName(renameFrom))
                .append(" ")
                .append(getQuoteSQLName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (migrationEntityParser.isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }

        String columnComment = getColumnComment(entityMigrationMetadata, column, "'");
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(" COMMENT ").append(columnComment);
        }
        sql.append(";");
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
        if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
            sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
        }

        String columnComment = getColumnComment(entityMigrationMetadata, column, "'");
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(" COMMENT ").append(columnComment);
        }
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

    @Override
    protected MigrationCommand createTableForeignKey(EntityMigrationMetadata entityMigrationMetadata, TableForeignKeyResult tableForeignKeyResult) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ");
        sql.append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()));
        sql.append(" ADD CONSTRAINT ");
        sql.append(tableForeignKeyResult.name);
        sql.append(" FOREIGN KEY (");
        for (int i = 0; i < tableForeignKeyResult.selfColumn.length; i++) {
            if (i > 0) {
                sql.append(",");
            }
            sql.append(getQuoteSQLName(tableForeignKeyResult.selfColumn[i]));
        }
        sql.append(") REFERENCES ");
        sql.append(getQuoteSQLName(tableForeignKeyResult.targetTable));
        sql.append(" (");
        for (int i = 0; i < tableForeignKeyResult.targetColumn.length; i++) {
            if (i > 0) {
                sql.append(",");
            }
            sql.append(getQuoteSQLName(tableForeignKeyResult.targetColumn[i]));
        }
        sql.append(")");

        if (EasyStringUtil.isNotBlank(tableForeignKeyResult.action)) {
            sql.append(" ").append(tableForeignKeyResult.action).append(" ");
        }
        sql.append(";");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }
}
