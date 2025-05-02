package com.easy.query.kingbase.es.migration;

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
 * create time 2025/1/19 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {
    public KingbaseESDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword,migrationEntityParser);
    }

//    @Override
//    public boolean databaseExists() {
//        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select 1 from pg_namespace where nspname = ?", Collections.singletonList(getDatabaseName()));
//        return EasyCollectionUtil.isNotEmpty(maps);
//    }
//
//    @Override
//    public MigrationCommand createDatabaseCommand() {
//        String databaseSQL = "CREATE SCHEMA IF NOT EXISTS " + getQuoteSQLName(databaseName) + ";";
//        return new DefaultMigrationCommand(null, databaseSQL);
//    }

    @Override
    public String databaseExistSQL(String databaseName) {
        return String.format("select 1 from pg_namespace where nspname = '%s'", databaseName);
    }
    @Override
    public String createDatabaseSQL(String databaseName) {
        return "CREATE SCHEMA IF NOT EXISTS " + getQuoteSQLName(databaseName) + ";";
    }

    @Override
    public boolean tableExists(String schema, String tableName) {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        if (EasyStringUtil.isBlank(schema)) {
            sqlParameters.add("public");
        } else {
            sqlParameters.add(schema);
        }
        sqlParameters.add(tableName);
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select 1 from pg_tables a inner join pg_namespace b on b.nspname = a.schemaname where b.nspname =? and a.tablename = ?", sqlParameters);
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
        StringBuilder columnCommentSQL = new StringBuilder();


        String tableComment = getTableComment(entityMigrationMetadata,"'");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            columnCommentSQL.append(newLine)
                    .append("COMMENT ON TABLE ")
                    .append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(" IS ").append(tableComment).append(";");
        }

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
                sql.append(" GENERATED BY DEFAULT AS IDENTITY");
            }
            String columnComment = getColumnComment(entityMigrationMetadata, column,"'");
            if (EasyStringUtil.isNotBlank(columnComment)) {
                columnCommentSQL.append(newLine)
                        .append("COMMENT ON COLUMN ")
                        .append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(".").append(getQuoteSQLName(column.getName()))
                        .append(" IS ").append(columnComment).append(";");
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
        }else{
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(newLine).append(");");
        if (columnCommentSQL.length() > 0) {
            sql.append(newLine).append(columnCommentSQL);
        }
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }


    @Override
    protected MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()))
                .append(" RENAME COLUMN ").append(getQuoteSQLName(renameFrom))
                .append(" TO ")
                .append(getQuoteSQLName(column.getName())).append(";");
//
//        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
//        sql.append(columnDbTypeResult.columnType);
//        if (isNullable(entityMigrationMetadata, column)) {
//            sql.append(" NULL");
//        } else {
//            sql.append(" NOT NULL");
//        }

        String columnComment = getColumnComment(entityMigrationMetadata, column,"'");
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(newLine);
            sql.append(" COMMENT ON COLUMN ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(" IS ").append(columnComment);
            sql.append(";");
        }
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
        sql.append(";");

        String columnComment = getColumnComment(entityMigrationMetadata, column,"'");
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(newLine);
            sql.append(" COMMENT ON COLUMN ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName())).append(" IS ").append(columnComment);
            sql.append(";");
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

    @Override
    protected MigrationCommand createTableForeignKey(EntityMigrationMetadata entityMigrationMetadata, TableForeignKeyResult tableForeignKeyResult) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ");
        sql.append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()));
        sql.append(" ADD CONSTRAINT ").append(tableForeignKeyResult.name);
        sql.append(" FOREIGN KEY (");

        String selfColumns = Arrays.stream(tableForeignKeyResult.selfColumn).map(self -> getQuoteSQLName(self)).collect(Collectors.joining(","));
        sql.append(selfColumns);
        sql.append(") REFERENCES ");
        sql.append(getQuoteSQLName(tableForeignKeyResult.targetTable));
        sql.append(" (");
        String targetColumns = Arrays.stream(tableForeignKeyResult.targetColumn).map(target -> getQuoteSQLName(target)).collect(Collectors.joining(","));
        sql.append(targetColumns);
        sql.append(")");

        if (EasyStringUtil.isNotBlank(tableForeignKeyResult.action)) {
            sql.append(" ").append(tableForeignKeyResult.action).append(" ");
        }
        sql.append(";");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }
}
