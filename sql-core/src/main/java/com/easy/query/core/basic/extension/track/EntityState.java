package com.easy.query.core.basic.extension.track;

import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyBeanUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xuejiaming
 * @FileName: EntryState.java
 * @Description: 文件说明
 * create time 2023/3/18 21:22
 */
public class EntityState {
    private final EntityMetadata entityMetadata;
    private final Object originalValue;
    private final Object currentValue;
    private final String trackKey;

    private List<NavigateMetadata> includes;
    private Map<NavigateMetadata, Set<String>> includeWithTrackKeyMap;

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

    public List<NavigateMetadata> getIncludes() {
        return includes;
    }

    public void setAppendIncludes(List<NavigateMetadata> includes) {
        if (this.includes != null) {
            if (includes != null) {
                for (NavigateMetadata include : includes) {
                    addInclude(include);
                }
            }
        } else {
            this.includes = includes;
        }
    }

    public boolean addInclude(NavigateMetadata navigateMetadata) {
        if (includes == null) {
            includes = new ArrayList<>();
        }
        if (!includes.contains(navigateMetadata)) {
            includes.add(navigateMetadata);
            return true;
        }
        return false;
    }

    public void addTrackKey(NavigateMetadata navigateMetadata, String trackKey) {
        if (includeWithTrackKeyMap == null) {
            includeWithTrackKeyMap = new HashMap<>();
        }
        Set<String> trackKeys = includeWithTrackKeyMap.computeIfAbsent(navigateMetadata, o -> new LinkedHashSet<>());
        if (trackKey != null) {
            trackKeys.add(trackKey);
        }
    }

    public Set<String> getTrackKeys(NavigateMetadata navigateMetadata) {
        if (includeWithTrackKeyMap == null) {
            return null;
        }
        return includeWithTrackKeyMap.get(navigateMetadata);
    }

    public EntityValueState getEntityValueState(String propertyName) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
        return getEntityValueState(columnMetadata);
    }

    public EntityValueState getEntityValueState(ColumnMetadata columnMetadata) {
        Object originalPropertyValue = EasyBeanUtil.getPropertyValue(originalValue, entityMetadata, columnMetadata);
        Object currentPropertyValue = EasyBeanUtil.getPropertyValue(currentValue, entityMetadata, columnMetadata);
        return new EntityValueState(columnMetadata, originalPropertyValue, currentPropertyValue);
    }

}
