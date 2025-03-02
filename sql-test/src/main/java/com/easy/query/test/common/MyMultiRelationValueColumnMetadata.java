package com.easy.query.test.common;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
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
public class MyMultiRelationValueColumnMetadata implements RelationValueColumnMetadata {
    private final List<ColumnMetadata> columnMetadataList;

    public MyMultiRelationValueColumnMetadata(EntityMetadata entityMetadata, String[] properties) {
        ArrayList<ColumnMetadata> columnMetadataList = new ArrayList<>(properties.length);
        for (String property : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
            columnMetadataList.add(columnMetadata);
        }
        this.columnMetadataList = columnMetadataList;
    }

    @Override
    public RelationValue getRelationValue(Object entity) {
        if (entity == null) {
            throw new EasyQueryInvalidOperationException("current entity can not be null");
        }
        List<Object> values = EasyCollectionUtil.select(columnMetadataList, (columnMetadata, index) -> columnMetadata.getGetterCaller().apply(entity));
        return new MyMultiRelationValue(values);
    }

//    @Override
//    public RelationValue getRelationValue(Map<String, Object> mappingRow) {
//        List<Object> values = EasyCollectionUtil.select(columnMetadataList, (columnMetadata, index) -> mappingRow.get(columnMetadata.getName()));
//        return new MyMultiRelationValue(values);
//    }
}
