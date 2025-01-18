package com.easy.query.core.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.util.EasyClassUtil;
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

/**
 * create time 2025/1/14 13:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {
    private static final Map<Class<?>, ColumnDbTypeResult> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(boolean.class, new ColumnDbTypeResult("TINYINT(1)", false));
        columnTypeMap.put(Boolean.class, new ColumnDbTypeResult("TINYINT(1)", null));
        columnTypeMap.put(float.class, new ColumnDbTypeResult("FLOAT", 0f));
        columnTypeMap.put(Float.class, new ColumnDbTypeResult("FLOAT", null));
        columnTypeMap.put(double.class, new ColumnDbTypeResult("DOUBLE", 0d));
        columnTypeMap.put(Double.class, new ColumnDbTypeResult("DOUBLE", null));
        columnTypeMap.put(short.class, new ColumnDbTypeResult("SMALLINT(6)", 0));
        columnTypeMap.put(Short.class, new ColumnDbTypeResult("SMALLINT(6)", null));
        columnTypeMap.put(int.class, new ColumnDbTypeResult("INT(11)", 0));
        columnTypeMap.put(Integer.class, new ColumnDbTypeResult("INT(11)", null));
        columnTypeMap.put(long.class, new ColumnDbTypeResult("BIGINT(20)", 0L));
        columnTypeMap.put(Long.class, new ColumnDbTypeResult("BIGINT(20)", null));
        columnTypeMap.put(byte.class, new ColumnDbTypeResult("TINYINT(3)", 0));
        columnTypeMap.put(Byte.class, new ColumnDbTypeResult("TINYINT(3)", null));
        columnTypeMap.put(BigDecimal.class, new ColumnDbTypeResult("DECIMAL(16,2)", null));
        columnTypeMap.put(LocalDateTime.class, new ColumnDbTypeResult("DATETIME(3)", null));
        columnTypeMap.put(String.class, new ColumnDbTypeResult("VARCHAR(255)", ""));
    }


    public DefaultDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword) {
        super(dataSource, sqlKeyword);
    }


    @Override
    public boolean databaseExists() {
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select 1 from information_schema.schemata where schema_name=?", Collections.singletonList(getDatabaseName()));
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public MigrationCommand createDatabaseCommand() {
        String databaseSQL = "CREATE DATABASE IF NOT EXISTS " + sqlKeyword.getQuoteName(databaseName) + " default charset utf8mb4 COLLATE utf8mb4_0900_ai_ci;";
        return new DefaultMigrationCommand(null, databaseSQL);
    }

    @Override
    public boolean tableExists(String tableName) {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        sqlParameters.add(getDatabaseName());
        sqlParameters.add(tableName);
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select 1 from information_schema.TABLES where table_schema=? and table_name=?", sqlParameters);
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public MigrationCommand renameTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null, null);
        String oldTableName = EasyStringUtil.isBlank(entityMetadata.getOldTableName()) ? null : EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getOldTableName(), null, null);
        sql.append("ALTER TABLE ").append(oldTableName).append(" RENAME TO ").append(tableName).append(";");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    @Override
    public MigrationCommand createTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null, null);
        sql.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" ( ");
        for (ColumnMetadata column : entityMetadata.getColumns()) {
            sql.append(newLine)
                    .append(sqlKeyword.getQuoteName(column.getName()))
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
                sql.append(" AUTO_INCREMENT");
            }
            String columnComment = getColumnComment(entityMigrationMetadata, column);
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
                sql.append(sqlKeyword.getQuoteName(keyColumn.getName()));
                if (i > 0) {
                    sql.append(", ");
                } else {
                    sql.append("),");
                }
            }
        }
        sql.append(newLine).append(") Engine=InnoDB");
        String tableComment = getTableComment(entityMigrationMetadata);
        if (EasyStringUtil.isNotBlank(tableComment)) {
            sql.append(" COMMENT=").append(tableComment);
        }
        sql.append(";").append(newLine);
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    @Override
    protected ColumnDbTypeResult getColumnDbType0(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        return columnTypeMap.get(columnMetadata.getPropertyType());
    }

    @Override
    public List<MigrationCommand> syncTable(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {

        //比较差异
        Set<String> tableColumns = getColumnNames(entityMigrationMetadata, oldTable);

        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null, null);
        for (ColumnMetadata column : entityMetadata.getColumns()) {
            if (!columnExistInDb(entityMigrationMetadata, column)) {
                continue;
            }
            if (!tableColumns.contains(column.getName())) {

                String columnRenameFrom = getColumnRenameFrom(entityMigrationMetadata, column);
                if (EasyStringUtil.isNotBlank(columnRenameFrom) && tableColumns.contains(columnRenameFrom)) {
                    MigrationCommand migrationCommand = renameColumn(entityMigrationMetadata, tableName, columnRenameFrom, column);
                    migrationCommands.add(migrationCommand);
                } else {
                    MigrationCommand migrationCommand = addColumn(entityMigrationMetadata, tableName, column);
                    migrationCommands.add(migrationCommand);
                }
            }
        }
        return migrationCommands;
    }

    private MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String tableName, String renameFrom, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(tableName)
                .append(" CHANGE ").append(sqlKeyword.getQuoteName(renameFrom))
                .append(" ")
                .append(sqlKeyword.getQuoteName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }

        String columnComment = getColumnComment(entityMigrationMetadata, column);
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(" COMMENT").append(columnComment);
        }
        sql.append(";");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    private MigrationCommand addColumn(EntityMigrationMetadata entityMigrationMetadata, String tableName, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(tableName)
                .append(" ADD ").append(sqlKeyword.getQuoteName(column.getName())).append(" ");

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }

        String columnComment = getColumnComment(entityMigrationMetadata, column);
        if (EasyStringUtil.isNotBlank(columnComment)) {
            sql.append(" COMMENT").append(columnComment);
        }
        sql.append(";");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    @Override
    public MigrationCommand dropTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null, null);
        return new DefaultMigrationCommand(entityMetadata, "ALTER TABLE " + tableName + ";");
    }

}
