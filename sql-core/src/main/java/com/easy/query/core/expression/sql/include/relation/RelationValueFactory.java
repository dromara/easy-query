package com.easy.query.core.expression.sql.include.relation;

import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2024/10/17 08:54
 * 关联查询关系值工程用于创建可比较的关系值
 *
 * @author xuejiaming
 */
public interface RelationValueFactory {
    default RelationValue createRelationValue(Object[] values) {
        return createRelationValue(Arrays.asList(values));
    }

    RelationValue createRelationValue(List<Object> values);
}
