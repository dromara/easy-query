package com.easy.query.core.expression.sql.include.multi;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.SingleRelationValue;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create time 2024/10/17 08:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class SingleRelationValueColumnMetadata implements RelationValueColumnMetadata {
    private final ColumnMetadata columnMetadata;
    private final RelationValue columnName;

    public SingleRelationValueColumnMetadata(EntityMetadata entityMetadata, String property) {
        this.columnMetadata = entityMetadata.getColumnNotNull(property);
        this.columnName = new SingleRelationValue(columnMetadata.getName());
    }

    @Override
    public RelationValue getRelationValue(Object entity) {
        if (entity == null) {
            throw new EasyQueryInvalidOperationException("current entity can not be null");
        }
        Object value = columnMetadata.getGetterCaller().apply(entity);
        return new SingleRelationValue(value);
    }

    @Override
    public RelationValue getRelationValue(Map<String, Object> mappingRow) {
        Object value = mappingRow.get(columnMetadata.getName());
        return new SingleRelationValue(value);
    }

    @Override
    public RelationValue getName() {
        return columnName;
    }
}
