package com.easy.query.core.migration;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.List;

/**
 * create time 2025/1/14 13:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DatabaseMigrationProvider {
    void setMigrationParser(MigrationEntityParser migrationParser);

    EntityMigrationMetadata createEntityMigrationMetadata(EntityMetadata entityMetadata);
    String getDatabaseName();

    boolean databaseExists();

    MigrationCommand createDatabaseCommand();

    boolean tableExists(String tableName);

    MigrationCommand renameTable(EntityMigrationMetadata entityMetadata);

    MigrationCommand createTable(EntityMigrationMetadata entityMetadata);

    List<MigrationCommand> syncTable(EntityMigrationMetadata entityMetadata, boolean oldTable);

    MigrationCommand dropTable(EntityMigrationMetadata entityMetadata);

    @Nullable
    ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    String getColumnComment(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    boolean isNullable(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);
    boolean columnExistInDb(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    String getTableComment(EntityMigrationMetadata entityMetadata);
    String getColumnRenameFrom(EntityMigrationMetadata entityMetadata,ColumnMetadata columnMetadata);

}
