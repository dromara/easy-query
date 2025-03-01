package com.easy.query.core.expression.sql.include.relation;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;

/**
 * create time 2025/2/20 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRelationValueColumnMetadataFactory implements RelationValueColumnMetadataFactory {
    private final RelationValueFactory relationValueFactory;
    private final EntityMetadataManager entityMetadataManager;

    public DefaultRelationValueColumnMetadataFactory(RelationValueFactory relationValueFactory, EntityMetadataManager entityMetadataManager) {
        this.relationValueFactory = relationValueFactory;
        this.entityMetadataManager = entityMetadataManager;
    }

    @Override
    public RelationValueColumnMetadata create(EntityMetadata entityMetadata, String[] properties) {
        return new DefaultRelationValueColumnMetadata(entityMetadata, properties, relationValueFactory);
    }

    @Override
    public RelationValueColumnMetadata createDirect(NavigateMetadata directNavigateMetadata, String[] properties) {
        return new DirectRelationValueColumnMetadata(entityMetadataManager, directNavigateMetadata, properties, relationValueFactory);
    }
}
