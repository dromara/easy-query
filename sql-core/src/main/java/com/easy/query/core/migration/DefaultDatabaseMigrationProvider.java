package com.easy.query.core.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.migration.data.ColumnMigrationData;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * create time 2025/1/14 13:31
 * 生成数据库迁移提供者
 *
 * @author xuejiaming
 */
public class DefaultDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {

    public DefaultDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword, migrationEntityParser);
    }

    @Override
    public String databaseExistSQL(String databaseName) {
        return String.format("select 1 from information_schema.schemata where schema_name='%s'", databaseName);
    }

    @Override
    public String createDatabaseSQL(String databaseName) {
        return "CREATE DATABASE IF NOT EXISTS " + getQuoteSQLName(databaseName) + " default charset utf8mb4 COLLATE utf8mb4_general_ci;";
    }

    @Override
    public boolean tableExists(String schema, String tableName) {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        sqlParameters.add(getDatabaseName());
        sqlParameters.add(tableName);
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select 1 from information_schema.TABLES where table_schema=? and table_name=?", sqlParameters);
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public MigrationCommand renameTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        String sql = "ALTER TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getOldTableName()) + " RENAME TO " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()) + ";";
        return new DefaultMigrationCommand(sql);
    }

//    @Override
//    public MigrationCommand renameTable(TableMigrationData tableMigrationData) {
//        String sql = "ALTER TABLE " + getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getOldTableName()) + " RENAME TO " + getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName()) + ";";
//        return new DefaultMigrationCommand(sql);
//    }

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
            String columnComment = getColumnComment(entityMigrationMetadata, column, "'");
            if (EasyStringUtil.isNotBlank(columnComment)) {
                sql.append(" COMMENT ").append(columnComment);
            }
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
        } else {
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(newLine).append(") Engine=InnoDB");
        String tableComment = getTableComment(entityMigrationMetadata, "'");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            sql.append(" COMMENT=").append(tableComment);
        }
        sql.append(";");
        return new DefaultMigrationCommand(sql.toString());
    }

//    @Override
//    public MigrationCommand createTable(TableMigrationData tableMigrationData) {
////        StringBuilder sql = new StringBuilder();
////        sql.append("CREATE TABLE IF NOT EXISTS ").append(getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName())).append(" ( ");
////        for (ColumnMigrationData column : tableMigrationData.getColumns()) {
////            sql.append(newLine)
////                    .append(getQuoteSQLName(column.getName()))
////                    .append(" ");
////            ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(column.getDbType(), column.getDefValue());
////            sql.append(columnDbTypeResult.columnType);
////            boolean nullable = EasyBoolUtil.isNotFalse(column.getNullable());
////            if (nullable) {
////                sql.append(" NULL ");
////            } else {
////                sql.append(" NOT NULL ");
////            }
////            if (column.isGeneratedKey()) {
////                sql.append(" AUTO_INCREMENT");
////            } else {
////
////                if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
////                    sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
////                }
////            }
////            String columnComment = getColumnComment(entityMigrationMetadata, column, "'");
////            if (EasyStringUtil.isNotBlank(columnComment)) {
////                sql.append(" COMMENT ").append(columnComment);
////            }
////            sql.append(",");
////        }
////        Collection<String> keyProperties = entityMetadata.getKeyProperties();
////        if (EasyCollectionUtil.isNotEmpty(keyProperties)) {
////            sql.append(" ").append(newLine).append(" PRIMARY KEY (");
////            int i = keyProperties.size();
////            for (String keyProperty : keyProperties) {
////                i--;
////                ColumnMetadata keyColumn = entityMetadata.getColumnNotNull(keyProperty);
////                sql.append(getQuoteSQLName(keyColumn.getName()));
////                if (i > 0) {
////                    sql.append(", ");
////                } else {
////                    sql.append(")");
////                }
////            }
////        } else {
////            sql.deleteCharAt(sql.length() - 1);
////        }
////        sql.append(newLine).append(") Engine=InnoDB");
////        String tableComment = getTableComment(entityMigrationMetadata, "'");
////        if (EasyStringUtil.isNotBlank(tableComment)) {
////            sql.append(" COMMENT=").append(tableComment);
////        }
////        sql.append(";");
////        return new DefaultMigrationCommand(sql.toString());
//        return null;
//    }

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
        return new DefaultMigrationCommand(sql.toString());
    }

//    @Override
//    protected MigrationCommand renameColumn(TableMigrationData tableMigrationData, String oldColumnName, ColumnMigrationData columnMigrationData) {
//
//        StringBuilder sql = new StringBuilder();
//        sql.append("ALTER TABLE ").append(getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName()))
//                .append(" CHANGE ").append(getQuoteSQLName(oldColumnName))
//                .append(" ")
//                .append(getQuoteSQLName(columnMigrationData.getName())).append(" ");
//
//        ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(columnMigrationData.getDbType(), columnMigrationData.getDefValue());
//        sql.append(columnDbTypeResult.columnType);
//        if (EasyBoolUtil.isNotFalse(columnMigrationData.getNullable())) {
//            sql.append(" NULL");
//        } else {
//            sql.append(" NOT NULL");
//        }
//
//        String columnComment = getColumnComment(columnMigrationData, "'");
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(" COMMENT ").append(columnComment);
//        }
//        sql.append(";");
//        return new DefaultMigrationCommand(sql.toString());
//    }

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
        return new DefaultMigrationCommand(sql.toString());
    }

//    @Override
//    protected MigrationCommand addColumn(TableMigrationData tableMigrationData, ColumnMigrationData columnMigrationData) {
//
//        StringBuilder sql = new StringBuilder();
//        sql.append("ALTER TABLE ").append(getQuoteSQLName(tableMigrationData.getSchema(), tableMigrationData.getTableName()))
//                .append(" ADD ").append(getQuoteSQLName(columnMigrationData.getName())).append(" ");
//
//        ColumnDbTypeResult columnDbTypeResult = new ColumnDbTypeResult(columnMigrationData.getDbType(), columnMigrationData.getDefValue());
//        sql.append(columnDbTypeResult.columnType);
//        if (EasyBoolUtil.isNotFalse(columnMigrationData.getNullable())) {
//            sql.append(" NULL");
//        } else {
//            sql.append(" NOT NULL");
//        }
//        if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
//            sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
//        }
//
//        String columnComment = getColumnComment(columnMigrationData, "'");
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(" COMMENT ").append(columnComment);
//        }
//        sql.append(";");
//        return new DefaultMigrationCommand(sql.toString());
//    }

    @Override
    public MigrationCommand dropTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        return new DefaultMigrationCommand("DROP TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()) + ";");
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
        return new DefaultMigrationCommand(sql.toString());
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
        return new DefaultMigrationCommand(sql.toString());
    }
}
