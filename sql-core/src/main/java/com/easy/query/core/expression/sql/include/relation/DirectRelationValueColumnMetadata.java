package com.easy.query.core.expression.sql.include.relation;

import com.easy.query.core.common.DirectMappingIterator;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2024/10/17 08:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class DirectRelationValueColumnMetadata implements RelationValueColumnMetadata {
    private final List<Property<Object, ?>> valueFunctionList;
    private final List<ColumnMetadata> columnMetadataList;
    private final RelationValueFactory relationValueFactory;

    public DirectRelationValueColumnMetadata(EntityMetadataManager entityMetadataManager, NavigateMetadata navigateMetadata, String[] properties, RelationValueFactory relationValueFactory) {
        this.relationValueFactory = relationValueFactory;
        if (EasyArrayUtil.isEmpty(navigateMetadata.getDirectMapping())) {
            throw new EasyQueryInvalidOperationException("direct mapping is empty");
        }
        this.valueFunctionList = new ArrayList<>();
        DirectMappingIterator directMappingIterator = new DirectMappingIterator(navigateMetadata.getDirectMapping(), 1);
        String firstProp = directMappingIterator.next();
        NavigateMetadata currentNavigateMetadata = navigateMetadata.getEntityMetadata().getNavigateNotNull(firstProp);
//        valueFunctionList.add(currentNavigateMetadata.getGetter());
        while (directMappingIterator.hasNext()) {
            String prop = directMappingIterator.next();
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
            currentNavigateMetadata = entityMetadata.getNavigateNotNull(prop);
            valueFunctionList.add(currentNavigateMetadata.getGetter());
        }
        EntityMetadata endEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
        ArrayList<ColumnMetadata> columnMetadataList = new ArrayList<>(properties.length);
        for (String property : properties) {
            ColumnMetadata columnMetadata = endEntityMetadata.getColumnNotNull(property);
            columnMetadataList.add(columnMetadata);
        }
        this.columnMetadataList = columnMetadataList;
    }

    @Override
    public RelationValue getRelationValue(Object entity) {
        if (entity == null) {
            throw new EasyQueryInvalidOperationException("current entity can not be null");
        }
        Object newEntity = entity;
        for (Property<Object, ?> objectProperty : valueFunctionList) {
            newEntity = objectProperty.apply(entity);
            if (newEntity == null) {
                return relationValueFactory.createCollectionRelationValue(null);
            }
        }
        Object finalNewEntity = newEntity;
        List<Object> values = EasyCollectionUtil.select(columnMetadataList, (columnMetadata, index) -> columnMetadata.getGetterCaller().apply(finalNewEntity));
        return relationValueFactory.createCollectionRelationValue(values);
    }

//    @Override
//    public RelationValue getRelationValue(Map<String, Object> mappingRow) {
//        List<Object> values = EasyCollectionUtil.select(columnMetadataList, (columnMetadata, index) -> mappingRow.get(columnMetadata.getName()));
//        return relationValueFactory.createRelationValue(values);
//    }
}
