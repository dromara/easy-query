package com.easy.query.test.common;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Map;

/**
 * create time 2024/10/17 08:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySingleRelationValueColumnMetadata implements RelationValueColumnMetadata {
    private final ColumnMetadata columnMetadata;

    public MySingleRelationValueColumnMetadata(EntityMetadata entityMetadata, String property) {
        this.columnMetadata = entityMetadata.getColumnNotNull(property);
    }

    @Override
    public RelationValue getRelationValue(Object entity) {
        if (entity == null) {
            throw new EasyQueryInvalidOperationException("current entity can not be null");
        }
        Object value = columnMetadata.getGetterCaller().apply(entity);
        return new MySingleRelationValue(value);
    }

    @Override
    public RelationValue getRelationValue(Map<String, Object> mappingRow) {
        Object value = mappingRow.get(columnMetadata.getName());
        return new MySingleRelationValue(value);
    }
}
