package com.easy.query.core.basic.extension.track;

/**
 * @FileName: EntryState.java
 * @Description: 文件说明
 * @Date: 2023/3/18 21:22
 * @author xuejiaming
 */
public class EntityState {
    private final Class<?> entityClass;
    private final Object originalValue;
    private final Object currentValue;
    private final String trackKey;

    public EntityState(Class<?> entityClass, String trackKey, Object originalValue, Object currentValue) {
        this.trackKey = trackKey;
        assert entityClass != null;
        assert originalValue != null;
        assert currentValue != null;
        this.entityClass = entityClass;
        this.originalValue = originalValue;
        this.currentValue = currentValue;
    }

    public Class<?> getEntityClass() {
        return entityClass;
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
}
