package com.easy.query.core.expression.sql.include.multi;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.MultiRelationValue;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create time 2024/10/17 08:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiRelationValueColumnMetadata implements RelationValueColumnMetadata {
    private final List<ColumnMetadata> columnMetadataList;
    private final RelationValue columnName;

    public MultiRelationValueColumnMetadata(EntityMetadata entityMetadata, String[] properties) {
        ArrayList<ColumnMetadata> columnMetadataList = new ArrayList<>(properties.length);
        for (String property : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
            columnMetadataList.add(columnMetadata);
        }
        this.columnMetadataList = columnMetadataList;

        List<Object> columnNames = EasyCollectionUtil.select(columnMetadataList, (columnMetadata, index) -> columnMetadata.getName());
        this.columnName = new MultiRelationValue(columnNames);
    }

    @Override
    public RelationValue getRelationValue(Object entity) {
        if (entity == null) {
            throw new EasyQueryInvalidOperationException("current entity can not be null");
        }
        List<Object> values = EasyCollectionUtil.select(columnMetadataList, (columnMetadata, index) -> columnMetadata.getGetterCaller().apply(entity));
        return new MultiRelationValue(values);
    }

    @Override
    public RelationValue getRelationValue(Map<String, Object> mappingRow) {
        List<Object> values = EasyCollectionUtil.select(columnMetadataList, (columnMetadata, index) -> mappingRow.get(columnMetadata.getName()));
        return new MultiRelationValue(values);
    }

    @Override
    public RelationValue getName() {
        return columnName;
    }
}
