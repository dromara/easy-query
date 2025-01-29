package com.easy.query.dameng.config;

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
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * create time 2025/1/19 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();
    private static final Log log= LogFactory.getLog(DamengDatabaseMigrationProvider.class);

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("number(1)", false));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("number(1)", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("real", 0f));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("real", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("double", 0d));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("double", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("number(6)", 0));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("number(6)", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("number(11)", 0));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("number(11)", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("number(21)", 0L));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("number(21)", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("number(3)", 0));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("number(3)", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("number(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("timestamp(6)", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("nvarchar2(255)", ""));
        columnTypeMap.put(UUID.class, new ColumnDbTypeResult("char(36)", null));
    }
    public DamengDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword) {
        super(dataSource, sqlKeyword);
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
    public boolean tableExists(String schema,String tableName) {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        if(EasyStringUtil.isBlank(schema)){
            sqlParameters.add("public");
        }else{
            sqlParameters.add(schema);
        }
        sqlParameters.add(tableName);
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select 1 from all_tab_comments where owner=? and table_name=?", sqlParameters);
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public MigrationCommand renameTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        String sql = String.format("execute immediate 'ALTER TABLE %s RENAME TO  %s';", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getOldTableName()), getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()));
        return new DefaultMigrationCommand(entityMetadata, sql);
    }

    @Override
    public MigrationCommand createTable(EntityMigrationMetadata entityMigrationMetadata) {

        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("BEGIN")
                .append(newLine);
        StringBuilder columnCommentSQL = new StringBuilder();


        String tableComment = getTableComment(entityMigrationMetadata,"''");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            String format = String.format("execute immediate 'COMMENT ON TABLE %s IS %s';", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()), tableComment);
            columnCommentSQL.append(newLine)
                    .append(format)
                    .append(newLine);
        }

        sql.append("execute immediate 'CREATE TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName())).append(" ( ");
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
//            if (column.isGeneratedKey()) {
//                sql.append(" GENERATED BY DEFAULT AS IDENTITY");
//            }
            String columnComment = getColumnComment(entityMigrationMetadata, column,"''");
            if (EasyStringUtil.isNotBlank(columnComment)) {
                String format = String.format("execute immediate 'COMMENT ON COLUMN %s.%s IS %s';", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()), getQuoteSQLName(column.getName()), columnComment);
                columnCommentSQL.append(newLine)
                        .append(format);
            }
            sql.append(",");
        }
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        if (EasyCollectionUtil.isNotEmpty(keyProperties)) {
            sql.append(newLine)
                    .append(" CONSTRAINT ").append(getQuoteSQLName(entityMetadata.getTableName()+"_primary_key")).append(" ").append(" PRIMARY KEY (");
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
        }
//        sql.append(newLine).append(")");
        sql.append(newLine).append(") LOGGING ';");
        if(columnCommentSQL.length()>0){
            sql.append(newLine).append(columnCommentSQL);
        }

        sql.append(newLine).append("END;");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    @Override
    protected MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        String sql = String.format("execute immediate 'ALTER TABLE %s RENAME COLUMN %s TO  %s';", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()), getQuoteSQLName(renameFrom), getQuoteSQLName(column.getName()));
//
//        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
//        sql.append(columnDbTypeResult.columnType);
//        if (isNullable(entityMigrationMetadata, column)) {
//            sql.append(" NULL");
//        } else {
//            sql.append(" NOT NULL");
//        }

//        String columnComment = getColumnComment(entityMigrationMetadata, column);
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(newLine);
//            sql.append(" COMMENT ON COLUMN ").append(tableName).append(" IS ").append(columnComment);
//            sql.append(";");
//        }
        return new DefaultMigrationCommand(entityMetadata, sql);
    }

    @Override
    protected MigrationCommand addColumn(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        String format = String.format("execute immediate 'ALTER TABLE %s ADD (", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()));
        sql.append(format).append(getQuoteSQLName(column.getName()));

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }
        sql.append(")';");
//
//        String columnComment = getColumnComment(entityMigrationMetadata, column);
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(newLine);
//            sql.append(" COMMENT ON COLUMN ").append(tableName).append(" IS ").append(columnComment);
//            sql.append(";");
//        }
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }


    @Override
    public MigrationCommand dropTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        return new DefaultMigrationCommand(entityMetadata, "DROP TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()) + ";");
    }
    @Override
    protected ColumnDbTypeResult getColumnDbType0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        return columnTypeMap.get(columnMetadata.getPropertyType());
    }
}
