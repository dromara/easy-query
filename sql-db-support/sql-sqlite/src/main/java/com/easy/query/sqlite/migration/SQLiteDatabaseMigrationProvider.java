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
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * create time 2025/1/14 13:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();
    private static final Log log = LogFactory.getLog(SQLiteDatabaseMigrationProvider.class);

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("boolean", false));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("boolean", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("float", 0f));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("float", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("double", 0d));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("double", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("smallint", 0));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("smallint", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("integer", 0));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("integer", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("integer", 0L));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("integer", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("int2", 0));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("int2", null));
        columnTypeMap.put(byte[].class, new ColumnDbTypeResult("blob", new byte[0]));
        columnTypeMap.put(Byte[].class, new ColumnDbTypeResult("blob", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("decimal(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("datetime", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("nvarchar(255)", ""));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("character(36)", ""));
    }


    public SQLiteDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword) {
        super(dataSource, sqlKeyword);
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
            boolean nullable = isNullable(entityMigrationMetadata, column);
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
    protected ColumnDbTypeResult getColumnDbType0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        return columnTypeMap.get(columnMetadata.getPropertyType());
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
        if (isNullable(entityMigrationMetadata, column)) {
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
        if (isNullable(entityMigrationMetadata, column)) {
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

}
