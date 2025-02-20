package com.easy.query.core.expression.sql.include.relation;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2025/2/20 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationValueColumnMetadataFactory {
    RelationValueColumnMetadata create(EntityMetadata entityMetadata, String[] properties);
}
