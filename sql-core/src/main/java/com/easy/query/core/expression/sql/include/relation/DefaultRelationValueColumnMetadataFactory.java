package com.easy.query.core.expression.sql.include.relation;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2025/2/20 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRelationValueColumnMetadataFactory implements RelationValueColumnMetadataFactory {
    private final RelationValueFactory relationValueFactory;

    public DefaultRelationValueColumnMetadataFactory(RelationValueFactory relationValueFactory) {
        this.relationValueFactory = relationValueFactory;
    }

    @Override
    public RelationValueColumnMetadata create(EntityMetadata entityMetadata, String[] properties) {
        return new DefaultRelationValueColumnMetadata(entityMetadata, properties, relationValueFactory);
    }
}
