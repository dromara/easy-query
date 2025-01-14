package com.easy.query.core.migration;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

import javax.sql.DataSource;

/**
 * create time 2025/1/14 13:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MigrationInfoConverter {
    String getDatabaseName(DataSource dataSource);
    boolean databaseExists(DataSource dataSource,String databaseName);
    @Nullable
    ColumnTypeResult getColumnTypeResult(EntityMetadata entityMetadata, ColumnMetadata columnMetadata);

    String getColumnComment(EntityMetadata entityMetadata, ColumnMetadata columnMetadata);
    String getTableComment(EntityMetadata entityMetadata);
}
