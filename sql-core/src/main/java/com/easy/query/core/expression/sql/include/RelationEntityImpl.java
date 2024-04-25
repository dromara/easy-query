package com.easy.query.core.expression.sql.include;

import com.easy.query.core.metadata.EntityMetadata;

import java.util.Objects;

/**
 * create time 2024/4/13 13:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationEntityImpl implements RelationExtraEntity{
    private final Object entity;
    private final EntityMetadata entityMetadata;

    public RelationEntityImpl(Object entity, EntityMetadata entityMetadata){
        Objects.requireNonNull(entity,"entity is null");
        Objects.requireNonNull(entityMetadata,"entityMetadata is null");

        this.entity = entity;
        this.entityMetadata = entityMetadata;
    }

    public Object getEntity() {
        return entity;
    }

    public Object getRelationExtraColumn(String propertyName){
        return entityMetadata.getColumnNotNull(propertyName).getGetterCaller().apply(entity);
    }
}
