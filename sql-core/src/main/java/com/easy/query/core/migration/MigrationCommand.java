package com.easy.query.core.migration;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2025/1/11 13:51
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MigrationCommand {
    EntityMetadata getEntityMetadata();
    String toSQL();
}
