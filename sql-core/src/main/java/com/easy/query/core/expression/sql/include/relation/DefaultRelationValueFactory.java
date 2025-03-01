package com.easy.query.core.expression.sql.include.relation;

import com.easy.query.core.expression.sql.include.MultiRelationValue;
import com.easy.query.core.expression.sql.include.NullRelationValue;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.SingleRelationValue;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2024/10/17 08:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRelationValueFactory implements RelationValueFactory {

    @Override
    public RelationValue createCollectionRelationValue(List<Object> values) {
        if(EasyCollectionUtil.isEmpty(values)){
            return new NullRelationValue();
        }
        if (values.size() == 1) {
            return new SingleRelationValue(values.get(0));
        }
        return new MultiRelationValue(values);
    }
}
