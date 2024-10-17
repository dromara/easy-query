package com.easy.query.core.expression.sql.include.multi;

import com.easy.query.core.expression.sql.include.MultiRelationValue;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.SingleRelationValue;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;
import java.util.List;

/**
 * create time 2024/10/17 08:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRelationValueFactory implements RelationValueFactory{
    @Override
    public RelationValueColumnMetadata create(EntityMetadata entityMetadata, String[] properties) {
        if(properties.length==1){
            return new SingleRelationValueColumnMetadata(entityMetadata,properties[0]);
        }
        return new MultiRelationValueColumnMetadata(entityMetadata,properties);
    }

    @Override
    public RelationValue createRelationValue(List<Object> values) {
        if(values.size()==1){
            return new SingleRelationValue(values.get(0));
        }
        return new MultiRelationValue(values);
    }
}
