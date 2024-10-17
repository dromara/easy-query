package com.easy.query.core.expression.sql.include;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.multi.RelationValueFactory;
import com.easy.query.core.metadata.RelationExtraColumn;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Map;
import java.util.Objects;

/**
 * create time 2024/4/13 13:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationExtraEntityImpl implements RelationExtraEntity {
    private final Object entity;
    private final Map<String, Object> extraColumns;
    private final Map<String, RelationExtraColumn> relationExtraColumnMap;
    private final RelationValueFactory relationValueFactory;

    public RelationExtraEntityImpl(Object entity, Map<String, Object> extraColumns, Map<String, RelationExtraColumn> relationExtraColumnMap, RelationValueFactory relationValueFactory) {
        this.relationExtraColumnMap = relationExtraColumnMap;
        this.relationValueFactory = relationValueFactory;
        Objects.requireNonNull(entity, "entity is null");
        Objects.requireNonNull(extraColumns, "extraColumns is null");

        this.entity = entity;
        this.extraColumns = extraColumns;
    }

    public Object getEntity() {
        return entity;
    }

    @Override
    public RelationValue getRelationExtraColumns(String[] propertyNames) {
        Object[] values = new Object[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            values[i] = getRelationExtraColumn(propertyNames[i]);
        }
        return relationValueFactory.createRelationValue(values);
    }

    private Object getRelationExtraColumn(String propertyName) {

        RelationExtraColumn relationExtraColumn = relationExtraColumnMap.get(propertyName);
        if (relationExtraColumn == null) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getInstanceSimpleName(entity) + " cant get relation column:" + propertyName);
        }
        if (relationExtraColumn.isAppendRelationExtra()) {
            return extraColumns.get(propertyName);
        } else {
            return relationExtraColumn.getColumnMetadata().getGetterCaller().apply(entity);
        }
    }
}
