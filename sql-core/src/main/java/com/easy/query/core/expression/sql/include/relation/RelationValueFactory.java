package com.easy.query.core.expression.sql.include.relation;

import com.easy.query.core.expression.sql.include.RelationValue;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2024/10/17 08:54
 * 关联查询关系值工程用于创建可比较的关系值
 *
 * @author xuejiaming
 */
public interface RelationValueFactory {
    default RelationValue createArrayRelationValue(Object[] values) {
        return createCollectionRelationValue(Arrays.asList(values));
    }

    RelationValue createCollectionRelationValue(List<Object> values);
}
