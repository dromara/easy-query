package com.easy.query.core.api.dynamic.sort.internal;

import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author xuejiaming
 * @FileName: ObjectOrderBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 17:24
 */
public class ObjectSortBuilderImpl implements ObjectSortBuilder {
    private final Map<String, ObjectSortEntry> orderPropertyMap = new LinkedHashMap<>();
    private final Set<String> allowedProperties = new HashSet<>();
    private final Set<String> notAllowedProperties = new HashSet<>();

    @Override
    public ObjectSortBuilder orderBy(String propertyName, boolean asc,int tableIndex) {
        orderPropertyMap.put(propertyName, new ObjectSortEntry(asc,tableIndex));
        return this;
    }

    @Override
    public ObjectSortBuilder allowed(String propertyName) {
        allowedProperties.add(propertyName);
        return this;
    }

    @Override
    public ObjectSortBuilder notAllowed(String propertyName) {
        notAllowedProperties.add(propertyName);
        return this;
    }

    @Override
    public Map<String, ObjectSortEntry> build() {
        LinkedHashMap<String, ObjectSortEntry> result = new LinkedHashMap<>();
        boolean allowedLimit = !allowedProperties.isEmpty();
        for (Map.Entry<String, ObjectSortEntry> orderProperty : orderPropertyMap.entrySet()) {
            if(!notAllowedProperties.contains(orderProperty.getKey())){
                if(allowedLimit){
                    if (allowedProperties.contains(orderProperty.getKey())) {
                        result.put(orderProperty.getKey(), orderProperty.getValue());
                    }
                }else{
                    result.put(orderProperty.getKey(), orderProperty.getValue());
                }
            }
        }
        return result;
    }

    public void clear(){
        orderPropertyMap.clear();
        allowedProperties.clear();
        notAllowedProperties.clear();
    }
}
