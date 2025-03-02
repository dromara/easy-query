package com.easy.query.test.common;

import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.expression.sql.include.relation.RelationValueFactory;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.List;

/**
 * create time 2024/10/17 08:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyDefaultRelationValueFactory implements RelationValueFactory {

    @Override
    public RelationValue createCollectionRelationValue(List<Object> values) {
        if (values.size() == 1) {
            return new MySingleRelationValue(values.get(0));
        }
        return new MyMultiRelationValue(values);
    }
}
