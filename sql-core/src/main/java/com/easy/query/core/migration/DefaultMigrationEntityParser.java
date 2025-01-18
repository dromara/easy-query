package com.easy.query.core.migration;

import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2025/1/18 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMigrationEntityParser implements MigrationEntityParser {
    @Override
    public ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return null;
    }

    @Override
    public String getColumnComment(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return "";
    }

    @Override
    public Boolean isNullable(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return null;
    }

    @Override
    public Boolean columnExistInDb(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return null;
    }

    @Override
    public String getTableComment(EntityMigrationMetadata entityMetadata) {
        return "";
    }

    @Override
    public String getColumnRenameFrom(EntityMigrationMetadata entityMetadata,ColumnMetadata columnMetadata) {
        return "";
    }
}
