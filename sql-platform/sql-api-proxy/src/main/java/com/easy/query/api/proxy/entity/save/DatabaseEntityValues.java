package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.relation.RelationValueFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2025/9/20 21:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class DatabaseEntityValues {
    private final NavigateMetadata navigateMetadata;
    private final EntityMetadata entityMetadata;
    private final QueryRuntimeContext runtimeContext;
    private final RelationValueFactory relationValueFactory;
    private final Map<String, Object> dbEntityMap;
    private final Map<RelationValue, String> saveKeyIndexMap;
    private final Map<RelationValue, Object> saveKeyCheckMap;

    public DatabaseEntityValues(NavigateMetadata navigateMetadata, EntityMetadata entityMetadata, QueryRuntimeContext runtimeContext) {
        this.navigateMetadata = navigateMetadata;
        this.entityMetadata = entityMetadata;
        this.runtimeContext = runtimeContext;
        this.relationValueFactory = runtimeContext.getRelationValueFactory();
        this.dbEntityMap = new LinkedHashMap<>();
        this.saveKeyIndexMap = new HashMap<>();
        this.saveKeyCheckMap = new HashMap<>();
    }

    private boolean hasSaveKey() {
        return !entityMetadata.getSaveKeyProperties().isEmpty();
    }

    public void put(String trackKey, Object entity) {
        Objects.requireNonNull(trackKey, "trackKey cant be null");
        dbEntityMap.put(trackKey, entity);
        if (hasSaveKey()) {
            RelationValue relationValue = getRelationValue(entity);
            String oldTrackKey = saveKeyIndexMap.put(relationValue, trackKey);
            if (oldTrackKey != null) {
                //存在save key一样的对象
                throw new EasyQueryInvalidOperationException("In [" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] , there are objects with the same @SaveKey, but their TrackKey values are [" + oldTrackKey + "," + trackKey + "]. Please verify the data accuracy.");
            }
        }
    }

    private RelationValue getRelationValue(Object entity) {
        int size = entityMetadata.getSaveKeyProperties().size();
        List<Object> saveKeyValues = new ArrayList<>(size);
        for (String saveKeyProperty : entityMetadata.getSaveKeyProperties()) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(saveKeyProperty);
            Object saveKey = columnMetadata.getGetterCaller().apply(entity);
            if (saveKey == null) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getInstanceSimpleName(entity) + "] save key property:[" + saveKeyProperty + "] can not be null");
            }
            saveKeyValues.add(saveKey);
        }
        RelationValue relationValue = relationValueFactory.createCollectionRelationValue(saveKeyValues);
        if (relationValue.isNull()) {
            throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getInstanceSimpleName(entity) + "] save key property:[" + String.join(",", entityMetadata.getSaveKeyProperties()) + "] can not be null");
        }
        return relationValue;
    }

    public String getTrackKey(Object entity) {
        String newNavigateEntityKey = EasyTrackUtil.getTrackKey(entityMetadata, entity);
        if (newNavigateEntityKey == null) {
            if (hasSaveKey()) {
                RelationValue relationValue = getRelationValue(entity);
                return saveKeyIndexMap.get(relationValue);
            }
        }
        return newNavigateEntityKey;
    }

    public void checkSaveKeyRepeat(Object entity) {
        if (hasSaveKey()) {
            RelationValue relationValue = getRelationValue(entity);
            Object old = saveKeyCheckMap.put(relationValue, entity);
            if (old != null && old != entity) {
                throw new EasyQueryInvalidOperationException("In [" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] , there are objects with the same @SaveKey, but their entity values are [" + entity + "," + old + "]. Please verify the data accuracy.");
            }
        }
    }

    public void remove(String tracKey) {
        dbEntityMap.remove(tracKey);
    }

    public boolean containsKey(String tracKey) {
        return dbEntityMap.containsKey(tracKey);
    }

    public Collection<Object> values() {
        return dbEntityMap.values();
    }
}
