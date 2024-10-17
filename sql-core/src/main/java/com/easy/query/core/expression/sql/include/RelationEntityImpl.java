package com.easy.query.core.expression.sql.include;

import com.easy.query.core.expression.sql.include.multi.RelationValueFactory;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Objects;

/**
 * create time 2024/4/13 13:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationEntityImpl implements RelationExtraEntity {
    private final Object entity;
    private final EntityMetadata entityMetadata;
    private final RelationValueFactory relationValueFactory;

    public RelationEntityImpl(Object entity, EntityMetadata entityMetadata, RelationValueFactory relationValueFactory) {
        this.relationValueFactory = relationValueFactory;
        Objects.requireNonNull(entity, "entity is null");
        Objects.requireNonNull(entityMetadata, "entityMetadata is null");

        this.entity = entity;
        this.entityMetadata = entityMetadata;
    }

    public Object getEntity() {
        return entity;
    }

    public RelationValue getRelationExtraColumns(String[] propertyNames) {
        Object[] values = new Object[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            values[i] = entityMetadata.getColumnNotNull(propertyNames[i]).getGetterCaller().apply(entity);
        }
        return relationValueFactory.createRelationValue(values);
    }
}
