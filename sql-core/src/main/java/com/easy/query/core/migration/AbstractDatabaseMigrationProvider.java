package com.easy.query.core.migration;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * create time 2025/1/18 20:09
 * 抽象生成数据库迁移提供者
 *
 * @author xuejiaming
 */
public abstract class AbstractDatabaseMigrationProvider implements DatabaseMigrationProvider {
    public static final String newLine = System.lineSeparator();
    protected final DataSource dataSource;
    protected final SQLKeyword sqlKeyword;

    protected String databaseName;
    protected MigrationEntityParser migrationEntityParser;


    public AbstractDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        this.dataSource = dataSource;
        this.sqlKeyword = sqlKeyword;
        this.migrationEntityParser = migrationEntityParser;
    }

    @Override
    public void setMigrationParser(MigrationEntityParser migrationParser) {
        this.migrationEntityParser = migrationParser;
    }

    @Override
    public EntityMigrationMetadata createEntityMigrationMetadata(EntityMetadata entityMetadata) {
        return new EntityMigrationMetadata(entityMetadata);
    }

    @Override
    public String getDatabaseName() {
        if (databaseName == null) {
            this.databaseName = EasyDatabaseUtil.getDatabaseName(dataSource, null);
        }
        if (databaseName == null) {
            throw new EasyQueryInvalidOperationException("cant get database name, Please confirm that the database has been created and exists.");
        }
        return this.databaseName;
    }

    @Override
    public void createDatabaseIfNotExists() {
        EasyDatabaseUtil.checkAndCreateDatabase(dataSource, (databaseName) -> {
            this.databaseName = databaseName;
            return databaseExistSQL(databaseName);
        }, databaseName -> {
            return createDatabaseSQL(databaseName);
        });
    }

    //    @Override
    public abstract String databaseExistSQL(String databaseName);

    public abstract String createDatabaseSQL(String databaseName);
//
//    @Override
//    public MigrationCommand createDatabaseCommand() {
//        String databaseSQL = "CREATE DATABASE IF NOT EXISTS " + getQuoteSQLName(databaseName) + " ENGINE=Ordinary;";
//        return new DefaultMigrationCommand(null, databaseSQL);
//    }


    protected @NotNull ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
        ColumnDbTypeResult columnDbType = migrationEntityParser.getColumnDbType(entityMigrationMetadata, columnMetadata);
        if (columnDbType == null) {
            throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(entityMigrationMetadata.getEntityMetadata().getEntityClass()) + "] field name:" + columnMetadata.getFieldName() + " not found column db type.");
        }
        return columnDbType;
    }

    protected String getColumnComment(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata, String quote) {
        String columnComment = migrationEntityParser.getColumnComment(entityMigrationMetadata, columnMetadata);
        if (EasyStringUtil.isNotBlank(columnComment)) {
            return quote + columnComment + quote;
        }
        return null;
    }

    protected @Nullable String getTableComment(EntityMigrationMetadata entityMigrationMetadata, String quote) {
        String tableComment = migrationEntityParser.getTableComment(entityMigrationMetadata);
        if (EasyStringUtil.isNotBlank(tableComment)) {
            return quote + tableComment + quote;
        }
        return tableComment;
    }

    protected Set<String> getColumnNames(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();

        String columnTableName = EasyToSQLUtil.getSchemaTableName(sqlKeyword, entityMetadata, oldTable ? entityMetadata.getOldTableName() : entityMetadata.getTableName(), null, null);
        //比较差异
        return EasyDatabaseUtil.getColumns(dataSource, "select * from " + columnTableName + " where 1=2");
    }

    protected Set<String> getTableIndexes(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();

//        String columnTableName = EasyToSQLUtil.getSchemaTableName(sqlKeyword, entityMetadata, oldTable ? entityMetadata.getOldTableName() : entityMetadata.getTableName(), null, null);
        //比较差异
        return EasyDatabaseUtil.getTableIndexes(dataSource, oldTable ? entityMetadata.getOldTableName() : entityMetadata.getTableName());
    }

    protected Set<String> getTableForeignKeys(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();

//        String columnTableName = EasyToSQLUtil.getSchemaTableName(sqlKeyword, entityMetadata, oldTable ? entityMetadata.getOldTableName() : entityMetadata.getTableName(), null, null);
        //比较差异
        return EasyDatabaseUtil.getTableForeignKeys(dataSource, oldTable ? entityMetadata.getOldTableName() : entityMetadata.getTableName());
    }

    @Override
    public List<MigrationCommand> syncTable(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {

        //比较差异
        Set<String> tableColumns = getColumnNames(entityMigrationMetadata, oldTable);

        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        for (ColumnMetadata column : entityMetadata.getColumns()) {
            if (migrationEntityParser.columnExistInDb(entityMigrationMetadata, column)) {
                if (!tableColumns.contains(column.getName())) {
                    String columnRenameFrom = migrationEntityParser.getColumnRenameFrom(entityMigrationMetadata, column);
                    if (EasyStringUtil.isNotBlank(columnRenameFrom) && tableColumns.contains(columnRenameFrom)) {
                        MigrationCommand migrationCommand = renameColumn(entityMigrationMetadata, columnRenameFrom, column);
                        migrationCommands.add(migrationCommand);
                    } else {
                        MigrationCommand migrationCommand = addColumn(entityMigrationMetadata, column);
                        migrationCommands.add(migrationCommand);
                    }
                }
            }
        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> createTableIndex(EntityMigrationMetadata entityMigrationMetadata) {
        List<TableIndexResult> tableIndexes = migrationEntityParser.getTableIndexes(entityMigrationMetadata);

        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>(tableIndexes.size());
        for (TableIndexResult tableIndex : tableIndexes) {
            MigrationCommand migrationCommand = createIndex(entityMigrationMetadata, tableIndex);
            migrationCommands.add(migrationCommand);
        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> syncTableIndex(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {
        Set<String> dbTableIndexes = getTableIndexes(entityMigrationMetadata, oldTable);

        List<TableIndexResult> tableIndexes = migrationEntityParser.getTableIndexes(entityMigrationMetadata);

        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>(tableIndexes.size());
        for (TableIndexResult tableIndex : tableIndexes) {
            if (!dbTableIndexes.contains(tableIndex.indexName)) {
                MigrationCommand migrationCommand = createIndex(entityMigrationMetadata, tableIndex);
                migrationCommands.add(migrationCommand);
            }
        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> createTableForeignKey(EntityMigrationMetadata entityMigrationMetadata, QueryRuntimeContext runtimeContext) {
        List<TableForeignKeyResult> tableForeignKeys = migrationEntityParser.getTableForeignKeys(entityMigrationMetadata, runtimeContext);
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>(tableForeignKeys.size());
        for (TableForeignKeyResult tableForeignKeyResult : tableForeignKeys) {
            MigrationCommand migrationCommand = createTableForeignKey(entityMigrationMetadata, tableForeignKeyResult);
            if (migrationCommand != null) {
                migrationCommands.add(migrationCommand);
            }
        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> syncTableForeignKey(EntityMigrationMetadata entityMigrationMetadata, QueryRuntimeContext runtimeContext, boolean oldTable) {
        Set<String> dbTableIForeignKeys = getTableForeignKeys(entityMigrationMetadata, oldTable);
        List<TableForeignKeyResult> tableForeignKeys = migrationEntityParser.getTableForeignKeys(entityMigrationMetadata, runtimeContext);
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>(tableForeignKeys.size());
        for (TableForeignKeyResult tableForeignKeyResult : tableForeignKeys) {
            if (!dbTableIForeignKeys.contains(tableForeignKeyResult.name)) {
                MigrationCommand migrationCommand = createTableForeignKey(entityMigrationMetadata, tableForeignKeyResult);
                if (migrationCommand != null) {
                    migrationCommands.add(migrationCommand);
                }
            }
        }
        return migrationCommands;
    }

    protected abstract MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column);

    protected abstract MigrationCommand addColumn(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata column);

    protected abstract MigrationCommand createIndex(EntityMigrationMetadata entityMigrationMetadata, TableIndexResult tableIndex);

    protected abstract @Nullable MigrationCommand createTableForeignKey(EntityMigrationMetadata entityMigrationMetadata, TableForeignKeyResult tableForeignKeyResult);

    public String getQuoteSQLName(String val) {
        return EasyToSQLUtil.getQuoteSQLName(sqlKeyword, val);
    }

    public String getQuoteSQLName(String val1, String val2) {
        return EasyToSQLUtil.getQuoteSQLName(sqlKeyword, val1, val2);
    }
}