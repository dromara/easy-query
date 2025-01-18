package com.easy.query.core.migration;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2025/1/18 20:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MigrationEntityParser {
    @Nullable
    ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    String getColumnComment(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    Boolean isNullable(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);
    Boolean columnExistInDb(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    String getTableComment(EntityMigrationMetadata entityMetadata);
    String getColumnRenameFrom(EntityMigrationMetadata entityMetadata,ColumnMetadata columnMetadata);
}
