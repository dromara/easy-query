package com.easy.query.core.expression.sql.include.multi;

import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * create time 2024/10/17 08:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationValueFactory {
    RelationValueColumnMetadata create(EntityMetadata entityMetadata, String[] properties);
   default RelationValue createRelationValue(Object[] values){
       return createRelationValue(Arrays.asList(values));
    }
    RelationValue createRelationValue(List<Object> values);;
}
