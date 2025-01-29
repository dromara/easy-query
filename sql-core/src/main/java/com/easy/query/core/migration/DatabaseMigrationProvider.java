package com.easy.query.core.migration;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

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
    void createDatabaseIfNotExists();

    boolean tableExists(String schema, String tableName);

    MigrationCommand renameTable(EntityMigrationMetadata entityMigrationMetadata);

    MigrationCommand createTable(EntityMigrationMetadata entityMigrationMetadata);

    List<MigrationCommand> syncTable(EntityMigrationMetadata entityMigrationMetadata, boolean oldTable);

    MigrationCommand dropTable(EntityMigrationMetadata entityMigrationMetadata);

    @Nullable
    ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata);

    String getColumnComment(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata, String quote);

    boolean isNullable(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata);

    boolean columnExistInDb(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata);

    String getTableComment(EntityMigrationMetadata entityMigrationMetadata, String quote);

    String getColumnRenameFrom(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata);

}
