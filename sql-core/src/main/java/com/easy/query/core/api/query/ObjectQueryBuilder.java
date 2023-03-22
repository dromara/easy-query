package com.easy.query.core.api.query;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.LambdaUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: EntityQueryBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:15
 * @Created by xuejiaming
 */
public class ObjectQueryBuilder<TObject> {
    private final Map<String,EntityPropertyNode> propertyMap=new HashMap<>();
    public <TEntity> EntityPropertyBuilder<TEntity,TObject> EntityProperty(Class<TEntity> entityClass){
        return new EntityPropertyBuilder<>(entityClass,propertyMap);
    }

    public Map<String,EntityPropertyNode> getPropertyMapping(){
        return propertyMap;
    }
}
