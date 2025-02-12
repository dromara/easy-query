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
    /**
     * 返回数据库应该有的类型比如varchar(100)
     * @param entityMetadata
     * @param columnMetadata
     * @return
     */
    @Nullable
    ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    /**
     * 生成当前列的备注信息
     * @param entityMetadata
     * @param columnMetadata
     * @return
     */
    String getColumnComment(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    /**
     * 当前列是否可为null
     * @param entityMetadata
     * @param columnMetadata
     * @return
     */
    Boolean isNullable(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    /**
     * 当前列是否存在于数据库
     * @param entityMetadata
     * @param columnMetadata
     * @return
     */
    Boolean columnExistInDb(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata);

    /**
     * 返回当前表的备注信息
     * @param entityMetadata
     * @return
     */
    String getTableComment(EntityMigrationMetadata entityMetadata);

    /**
     * 返回当前列重命名之前的名称
     * @param entityMetadata
     * @param columnMetadata
     * @return
     */
    String getColumnRenameFrom(EntityMigrationMetadata entityMetadata,ColumnMetadata columnMetadata);
}
