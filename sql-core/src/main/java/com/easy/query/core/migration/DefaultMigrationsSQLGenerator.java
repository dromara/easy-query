package com.easy.query.core.migration;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.ForeignKeyMigrationData;
import com.easy.query.core.migration.data.IndexMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2025/1/11 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMigrationsSQLGenerator implements MigrationsSQLGenerator {
    private final EntityMetadataManager entityMetadataManager;
    private final DatabaseMigrationProvider databaseMigrationProvider;
    private final ServiceProvider serviceProvider;

    public DefaultMigrationsSQLGenerator(EntityMetadataManager entityMetadataManager, DatabaseMigrationProvider databaseMigrationProvider, ServiceProvider serviceProvider) {
        this.entityMetadataManager = entityMetadataManager;
        this.databaseMigrationProvider = databaseMigrationProvider;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public List<MigrationCommand> generateMigrationSQL(MigrationContext migrationContext) {
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>();
//        if (!databaseMigrationProvider.databaseExists()) {
//            MigrationCommand databaseCommand = databaseMigrationProvider.createDatabaseCommand();
//            if (databaseCommand != null) {
//                migrationCommands.add(databaseCommand);
//            }
//        }
        QueryRuntimeContext runtimeContext = serviceProvider.getService(QueryRuntimeContext.class);
        for (TableMigrationData tableMigrationData : migrationContext.getTableMigrationDataList()) {
//            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity);
//            EntityMigrationMetadata entityMigrationMetadata = databaseMigrationProvider.createEntityMigrationMetadata(entityMetadata);
            if (EasyStringUtil.isBlank(tableMigrationData.getTableName())) {
                throw new EasyQueryInvalidOperationException("table migration data not found table name.");
            }
//            if (sql.length() > 0) {
//                sql.append(newLine);
//            }
//            String tableName = EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getTableName(), null, null);
//            String oldTableName = EasyStringUtil.isBlank(entityMetadata.getOldTableName()) ? null : EasyToSQLUtil.getTableName(sqlKeyword, entityMetadata, entityMetadata.getOldTableName(), null, null);
            boolean tableExists = databaseMigrationProvider.tableExists(tableMigrationData.getSchema(), tableMigrationData.getTableName());
            if (!tableExists) {
                //如果新旧表名不一致
                if (!Objects.equals(tableMigrationData.getTableName(), tableMigrationData.getOldTableName())) {
                    //判断旧表是否存在
                    boolean oldTableExists = databaseMigrationProvider.tableExists(tableMigrationData.getSchema(), tableMigrationData.getOldTableName());
                    if (oldTableExists) {
                        //存在就要修改表名
                        MigrationCommand migrationCommand = databaseMigrationProvider.renameTable(tableMigrationData);
                        if (migrationCommand != null) {
                            migrationCommands.add(migrationCommand);
                        }
                        //新增修改删除表信息
                        List<MigrationCommand> columns = databaseMigrationProvider.syncTable(tableMigrationData, false);
                        if (columns != null) {
                            migrationCommands.addAll(columns);
                        }
                        //判断是否要创建索引
                    } else {
                        //表不存在就创建表
                        MigrationCommand migrationCommand = databaseMigrationProvider.createTable(tableMigrationData);
                        if (migrationCommand != null) {
                            migrationCommands.add(migrationCommand);
                        }
                        //创建外键
                        List<MigrationCommand> tableForeignKeyCommands = databaseMigrationProvider.createTableForeignKey(tableMigrationData, runtimeContext);
                        migrationCommands.addAll(tableForeignKeyCommands);
                        //创建索引
                        List<MigrationCommand> tableIndexCommands = databaseMigrationProvider.createTableIndex(tableMigrationData);
                        migrationCommands.addAll(tableIndexCommands);
                    }
                } else {
                    //表不存在就创建表
                    MigrationCommand migrationCommand = databaseMigrationProvider.createTable(tableMigrationData);
                    if (migrationCommand != null) {
                        migrationCommands.add(migrationCommand);
                    }
                    //创建外键
                    List<MigrationCommand> tableForeignKeyCommands = databaseMigrationProvider.createTableForeignKey(tableMigrationData, runtimeContext);
                    migrationCommands.addAll(tableForeignKeyCommands);
                    //创建索引
                    List<MigrationCommand> tableIndexCommands = databaseMigrationProvider.createTableIndex(tableMigrationData);
                    migrationCommands.addAll(tableIndexCommands);
                }
            } else {
                List<MigrationCommand> columns = databaseMigrationProvider.syncTable(tableMigrationData, false);
                if (columns != null) {
                    migrationCommands.addAll(columns);
                }
                //创建外键
                List<MigrationCommand> tableForeignKeyCommands = databaseMigrationProvider.syncTableForeignKey(tableMigrationData, runtimeContext, false);
                migrationCommands.addAll(tableForeignKeyCommands);
                //创建索引
                List<MigrationCommand> tableIndexCommands = databaseMigrationProvider.syncTableIndex(tableMigrationData, false);
                migrationCommands.addAll(tableIndexCommands);
            }

        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> generateCreateTableMigrationSQL(MigrationContext migrationContext) {
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>(migrationContext.getTableMigrationDataList().size());
        for (TableMigrationData tableMigrationData : migrationContext.getTableMigrationDataList()) {
            MigrationCommand migrationCommand = databaseMigrationProvider.createTable(tableMigrationData);
            if (migrationCommand != null) {
                migrationCommands.add(migrationCommand);
            }
            List<MigrationCommand> tableIndexCommands = databaseMigrationProvider.createTableIndex(tableMigrationData);
            migrationCommands.addAll(tableIndexCommands);
        }
        return migrationCommands;
    }

    @Override
    public List<MigrationCommand> generateDropTableMigrationSQL(MigrationContext migrationContext, boolean checkTableExists) {
        ArrayList<MigrationCommand> migrationCommands = new ArrayList<>(migrationContext.getTableMigrationDataList().size());
        for (TableMigrationData tableMigrationData : migrationContext.getTableMigrationDataList()) {
            if (checkTableExists) {
                if (!tableExists(tableMigrationData.getSchema(), tableMigrationData.getTableName())) {
                    continue;
                }
            }
            MigrationCommand migrationCommand = databaseMigrationProvider.dropTable(tableMigrationData);
            if (migrationCommand != null) {
                migrationCommands.add(migrationCommand);
            }
        }
        return migrationCommands;
    }

    @Override
    public boolean tableExists(String schema, String tableName) {
        return databaseMigrationProvider.tableExists(schema, tableName);
    }


    @Override
    public TableMigrationData parseEntity(Class<?> entityClass) {
        TableMigrationData tableMigrationData = new TableMigrationData();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);

        EntityMigrationMetadata entityMigrationMetadata = databaseMigrationProvider.createEntityMigrationMetadata(entityMetadata);
        tableMigrationData.setSchema(entityMetadata.getSchemaOrNull());
        tableMigrationData.setTableName(entityMetadata.getTableName());
        tableMigrationData.setOldTableName(entityMetadata.getOldTableName());
        tableMigrationData.setComment(databaseMigrationProvider.getMigrationEntityParser().getTableComment(entityMigrationMetadata));
        List<ColumnMigrationData> columns = new ArrayList<>(entityMetadata.getColumns().size());
        for (ColumnMetadata column : entityMetadata.getColumns()) {

            boolean exist = databaseMigrationProvider.getMigrationEntityParser().columnExistInDb(entityMigrationMetadata, column);
            if (!exist) {
                continue;
            }

            ColumnDbTypeResult columnDbType = databaseMigrationProvider.getMigrationEntityParser().getColumnDbType(entityMigrationMetadata, column);
            ColumnMigrationData columnMigrationData = new ColumnMigrationData();
            columnMigrationData.setName(column.getName());
            String columnComment = databaseMigrationProvider.getMigrationEntityParser().getColumnComment(entityMigrationMetadata, column);
            columnMigrationData.setComment(columnComment);
            columnMigrationData.setDbType(columnDbType.columnType);
            columnMigrationData.setDefValue(columnDbType.defValue);
            columnMigrationData.setPrimary(column.isPrimary());
            columnMigrationData.setGeneratedKey(column.isGeneratedKey());
            boolean nullable = databaseMigrationProvider.getMigrationEntityParser().isNullable(entityMigrationMetadata, column);
            columnMigrationData.setNotNull(!nullable);
            columnMigrationData.setOldColumnName(column.getFieldName());
            String columnOldName = databaseMigrationProvider.getMigrationEntityParser().getColumnOldName(entityMigrationMetadata, column);
            columnMigrationData.setOldColumnName(columnOldName);
            columns.add(columnMigrationData);

        }
        tableMigrationData.setColumns(columns);
        //索引
        List<TableIndexResult> tableIndexes = databaseMigrationProvider.getMigrationEntityParser().getTableIndexes(entityMigrationMetadata);
        List<IndexMigrationData> indexes = new ArrayList<>(tableIndexes.size());
        for (TableIndexResult tableIndex : tableIndexes) {
            IndexMigrationData indexMigrationData = new IndexMigrationData();
            indexMigrationData.setIndexName(tableIndex.indexName);
            indexMigrationData.setUnique(tableIndex.unique);
            if (tableIndex.fields != null) {
                List<IndexMigrationData.EntityField> entityFields = new ArrayList<>();
                for (TableIndexResult.EntityField field : tableIndex.fields) {
                    IndexMigrationData.EntityField entityField = new IndexMigrationData.EntityField();
                    entityField.setColumnName(field.columnName);
                    entityField.setAsc(field.asc);
                    entityFields.add(entityField);
                }
                indexMigrationData.setFields(entityFields);
            }
            indexes.add(indexMigrationData);
        }
        tableMigrationData.setIndexes(indexes);

        //外键
        QueryRuntimeContext runtimeContext = serviceProvider.getService(QueryRuntimeContext.class);
        List<TableForeignKeyResult> tableForeignKeys = databaseMigrationProvider.getMigrationEntityParser().getTableForeignKeys(entityMigrationMetadata, runtimeContext);
        List<ForeignKeyMigrationData> foreignKeys = new ArrayList<>(tableForeignKeys.size());
        for (TableForeignKeyResult tableForeignKey : tableForeignKeys) {
            ForeignKeyMigrationData foreignKey = new ForeignKeyMigrationData();
            foreignKey.setName(tableForeignKey.name);
            foreignKey.setSelfTable(tableForeignKey.selfTable);
            foreignKey.setTargetTable(tableForeignKey.targetTable);
            foreignKey.setSelfColumn(tableForeignKey.selfColumn);
            foreignKey.setTargetColumn(tableForeignKey.targetColumn);
            foreignKeys.add(foreignKey);
        }
        tableMigrationData.setForeignKeys(foreignKeys);
        return tableMigrationData;
    }
}
