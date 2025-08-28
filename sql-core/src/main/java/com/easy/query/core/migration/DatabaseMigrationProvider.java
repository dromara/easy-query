package com.easy.query.core.migration;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.data.TableMigrationData;

import java.util.List;

/**
 * create time 2025/1/14 13:29
 * 生成数据库迁移提供者
 *
 * @author xuejiaming
 */
public interface DatabaseMigrationProvider {
    void setMigrationParser(MigrationEntityParser migrationParser);

    EntityMigrationMetadata createEntityMigrationMetadata(EntityMetadata entityMetadata);

    String getDatabaseName();

    //    boolean databaseExists();
//
//    MigrationCommand createDatabaseCommand();

    /**
     * 创建数据库如果数据库不存在
     */
    void createDatabaseIfNotExists();

    boolean tableExists(String schema, String tableName);

    MigrationCommand renameTable(EntityMigrationMetadata entityMigrationMetadata);
//    MigrationCommand renameTable(TableMigrationData tableMigrationData);

    MigrationCommand createTable(EntityMigrationMetadata entityMigrationMetadata);
//    MigrationCommand createTable(TableMigrationData tableMigrationData);
    List<MigrationCommand> createTableIndex(EntityMigrationMetadata entityMigrationMetadata);
    List<MigrationCommand> createTableForeignKey(EntityMigrationMetadata entityMigrationMetadata, QueryRuntimeContext runtimeContext);

    List<MigrationCommand> syncTable(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable);
//    List<MigrationCommand> syncTable(TableMigrationData tableMigrationData, boolean oldTable);
    List<MigrationCommand> syncTableIndex(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable);
    List<MigrationCommand> syncTableForeignKey(EntityMigrationMetadata entityMigrationMetadata, QueryRuntimeContext runtimeContext, boolean oldTable);

    MigrationCommand dropTable(EntityMigrationMetadata entityMigrationMetadata);

}
