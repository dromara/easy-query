package com.easy.query.core.migration;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.util.List;

/**
 * create time 2025/1/18 20:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MigrationEntityParser {
    /**
     * 返回数据库应该有的类型比如varchar(100)
     * @param entityMigrationMetadata
     * @param columnMetadata
     * @return
     */
    @Nullable
    ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata);

    /**
     * 生成当前列的备注信息
     * @param entityMigrationMetadata
     * @param columnMetadata
     * @return
     */
    String getColumnComment(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata);

    /**
     * 当前列是否可为null
     * @param entityMigrationMetadata
     * @param columnMetadata
     * @return
     */
    boolean isNullable(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata);

    /**
     * 当前列是否存在于数据库
     * @param entityMigrationMetadata
     * @param columnMetadata
     * @return
     */
    Boolean columnExistInDb(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata);

    /**
     * 返回当前表的备注信息
     * @param entityMigrationMetadata
     * @return
     */
    String getTableComment(EntityMigrationMetadata entityMigrationMetadata);

    /**
     * 返回当前列重命名之前的名称
     * @param entityMigrationMetadata
     * @param columnMetadata
     * @return
     */
    String getColumnRenameFrom(EntityMigrationMetadata entityMigrationMetadata,ColumnMetadata columnMetadata);

    List<TableIndexResult> getTableIndexes(EntityMigrationMetadata entityMigrationMetadata);
    List<TableForeignKeyResult> getTableForeignKeys(EntityMigrationMetadata entityMigrationMetadata, QueryRuntimeContext runtimeContext);
}
