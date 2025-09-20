package com.easy.query.core.migration;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.api.database.Credentials;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.ForeignKeyMigrationData;
import com.easy.query.core.migration.data.IndexMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

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
        Objects.requireNonNull(migrationParser, "migrationParser can not be null");
        this.migrationEntityParser = migrationParser;
    }

    @Override
    public MigrationEntityParser getMigrationEntityParser() {
        return migrationEntityParser;
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
    public void createDatabaseIfNotExists(Function<DataSource, Credentials> jdbcCredentialsByDataSourceFunction) {
        EasyDatabaseUtil.checkAndCreateDatabase(dataSource, (databaseName) -> {
            this.databaseName = databaseName;
            return databaseExistSQL(databaseName);
        }, databaseName -> {
            return createDatabaseSQL(databaseName);
        },jdbcCredentialsByDataSourceFunction);
    }

    //    @Override
    public abstract String databaseExistSQL(String databaseName);

    public abstract String createDatabaseSQL(String databaseName);

    protected String getColumnComment(ColumnMigrationData columnMigrationData, String quote) {
        String columnComment = columnMigrationData.getComment();
        if (EasyStringUtil.isNotBlank(columnComment)) {
            return quote + columnComment + quote;
        }
        return null;
    }

    protected @Nullable String getTableComment(TableMigrationData tableMigrationData, String quote) {
        String tableComment = tableMigrationData.getComment();
        if (EasyStringUtil.isNotBlank(tableComment)) {
            return quote + tableComment + quote;
        }
        return tableComment;
    }

    protected Set<String> getColumnNames(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();

        String columnTableName = EasyToSQLUtil.getSchemaTableName(sqlKeyword, entityMetadata.getSchemaOrNull(), oldTable ? entityMetadata.getOldTableName() : entityMetadata.getTableName(), null, null);
        //比较差异
        return EasyDatabaseUtil.getColumns(dataSource, "select * from " + columnTableName + " where 1=2");
    }

    protected Set<String> getColumnNames(TableMigrationData tableMigrationData, boolean oldTable) {

        String columnTableName = EasyToSQLUtil.getSchemaTableName(sqlKeyword, tableMigrationData.getSchema(), oldTable ? tableMigrationData.getOldTableName() : tableMigrationData.getTableName(), null, null);
        //比较差异
        return EasyDatabaseUtil.getColumns(dataSource, "select * from " + columnTableName + " where 1=2");
    }

    protected Set<String> getTableIndexes(TableMigrationData tableMigrationData, boolean oldTable) {
        //比较差异
        return EasyDatabaseUtil.getTableIndexes(dataSource, oldTable ? tableMigrationData.getOldTableName() : tableMigrationData.getTableName());
    }
    protected Set<String> getTableForeignKeys(TableMigrationData tableMigrationData, boolean oldTable) {
        //比较差异
        return EasyDatabaseUtil.getTableForeignKeys(dataSource, oldTable ? tableMigrationData.getOldTableName() : tableMigrationData.getTableName());
    }
    @Override
    public List<MigrationCommand> syncTable(TableMigrationData tableMigrationData, boolean oldTable) {

        //比较差异
        Set<String> tableColumns = getColumnNames(tableMigrationData, oldTable);

        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
        for (ColumnMigrationData column : tableMigrationData.getColumns()) {
            if (!tableColumns.contains(column.getName())) {
                String oldColumnName = column.getOldColumnName();
                if (EasyStringUtil.isNotBlank(oldColumnName) && tableColumns.contains(oldColumnName)) {
                    MigrationCommand migrationCommand = renameColumn(tableMigrationData, oldColumnName, column);
                    migrationCommands.add(migrationCommand);
                } else {
                    MigrationCommand migrationCommand = addColumn(tableMigrationData, column);
                    migrationCommands.add(migrationCommand);
                }
            }
        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> createTableIndex(TableMigrationData tableMigrationData) {

        List<IndexMigrationData> tableIndexes = tableMigrationData.getIndexes();
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
        for (IndexMigrationData tableIndex : tableIndexes) {
            MigrationCommand migrationCommand = createIndex(tableMigrationData, tableIndex);
            if (migrationCommand != null) {
                migrationCommands.add(migrationCommand);
            }
        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> syncTableIndex(TableMigrationData tableMigrationData, boolean oldTable) {
        Set<String> dbTableIndexes = getTableIndexes(tableMigrationData, oldTable);
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
        List<IndexMigrationData> tableIndexes = tableMigrationData.getIndexes();
        for (IndexMigrationData tableIndex : tableIndexes) {
            if (!dbTableIndexes.contains(tableIndex.getIndexName())) {
                MigrationCommand migrationCommand = createIndex(tableMigrationData, tableIndex);
                migrationCommands.add(migrationCommand);
            }
        }
        return migrationCommands;
    }
    @Override
    public List<MigrationCommand> createTableForeignKey(TableMigrationData tableMigrationData, QueryRuntimeContext runtimeContext) {
        List<ForeignKeyMigrationData> foreignKeys = tableMigrationData.getForeignKeys();
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
        for (ForeignKeyMigrationData foreignKey : foreignKeys) {
            MigrationCommand migrationCommand = createTableForeignKey(tableMigrationData, foreignKey);
            if (migrationCommand != null) {
                migrationCommands.add(migrationCommand);
            }
        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> syncTableForeignKey(TableMigrationData tableMigrationData, QueryRuntimeContext runtimeContext, boolean oldTable) {
        Set<String> dbTableIForeignKeys = getTableForeignKeys(tableMigrationData, oldTable);

        List<ForeignKeyMigrationData> tableForeignKeys = tableMigrationData.getForeignKeys();
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>(tableForeignKeys.size());
        for (ForeignKeyMigrationData foreignKeyMigrationData : tableForeignKeys) {
            if (!dbTableIForeignKeys.contains(foreignKeyMigrationData.getName())) {
                MigrationCommand migrationCommand = createTableForeignKey(tableMigrationData, foreignKeyMigrationData);
                if (migrationCommand != null) {
                    migrationCommands.add(migrationCommand);
                }
            }
        }
        return migrationCommands;
    }


    protected abstract MigrationCommand renameColumn(TableMigrationData tableMigrationData, String oldColumnName, ColumnMigrationData columnMigrationData);


    protected abstract MigrationCommand addColumn(TableMigrationData tableMigrationData, ColumnMigrationData columnMigrationData);


    protected abstract MigrationCommand createIndex(TableMigrationData table, IndexMigrationData tableIndex);


    protected abstract @Nullable MigrationCommand createTableForeignKey(TableMigrationData tableMigrationData, ForeignKeyMigrationData foreignKeyMigrationData);

    public String getQuoteSQLName(String val) {
        return EasyToSQLUtil.getQuoteSQLName(sqlKeyword, val);
    }

    public String getQuoteSQLName(String val1, String val2) {
        return EasyToSQLUtil.getQuoteSQLName(sqlKeyword, val1, val2);
    }
}