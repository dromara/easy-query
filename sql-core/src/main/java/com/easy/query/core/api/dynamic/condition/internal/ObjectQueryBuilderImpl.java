package com.easy.query.core.api.dynamic.condition.internal;

import com.easy.query.core.api.dynamic.condition.ObjectQueryBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: EntityQueryBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:15
 * @author xuejiaming
 */
public class ObjectQueryBuilderImpl implements ObjectQueryBuilder {
    private final Map<String/*query property*/, String/*entity property*/> propertyMap=new HashMap<>();

    @Override
    public ObjectQueryBuilder property(String entityPropertyName, String propertyName) {
        propertyMap.put(propertyName,entityPropertyName);
        return this;
    }

    @Override
    public ObjectQueryBuilder property(String entityPropertyName, String propertyName1, String propertyName2) {
        propertyMap.put(propertyName1,entityPropertyName);
        propertyMap.put(propertyName2,entityPropertyName);
        return this;
    }

    @Override
    public Map<String, String> build() {
        return propertyMap;
    }
}
