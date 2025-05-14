package com.easy.query.core.basic.extension.track;

import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyBeanUtil;

/**
 * @FileName: EntryState.java
 * @Description: 文件说明
 * create time 2023/3/18 21:22
 * @author xuejiaming
 */
public class EntityState {
    private final EntityMetadata entityMetadata;
    private final Object originalValue;
    private final Object currentValue;
    private final String trackKey;

    public EntityState(EntityMetadata entityMetadata, String trackKey, Object originalValue, Object currentValue) {
        this.trackKey = trackKey;
        assert entityMetadata != null;
        assert originalValue != null;
        assert currentValue != null;
        this.entityMetadata = entityMetadata;
        this.originalValue = originalValue;
        this.currentValue = currentValue;
    }

    public Class<?> getEntityClass() {
        return entityMetadata.getEntityClass();
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public String getTrackKey() {
        return trackKey;
    }

    public Object getOriginalValue() {
        return originalValue;
    }

    public Object getCurrentValue() {
        return currentValue;
    }

    public EntityValueState getEntityValueState(String propertyName) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
        return getEntityValueState(columnMetadata);
    }
    public EntityValueState getEntityValueState(ColumnMetadata columnMetadata) {
        Object originalPropertyValue = EasyBeanUtil.getPropertyValue(originalValue,entityMetadata,columnMetadata);
        Object currentPropertyValue = EasyBeanUtil.getPropertyValue(currentValue,entityMetadata,columnMetadata);
        return new EntityValueState(columnMetadata, originalPropertyValue, currentPropertyValue);
    }
}
