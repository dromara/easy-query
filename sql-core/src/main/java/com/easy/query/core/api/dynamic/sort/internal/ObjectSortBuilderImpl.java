package com.easy.query.core.api.dynamic.sort.internal;

import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;
import com.easy.query.core.api.dynamic.sort.ObjectSortPropertyBuilder;
import com.easy.query.core.enums.dynamic.DynamicModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @FileName: ObjectOrderBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 17:24
 * @author xuejiaming
 */
public class ObjectSortBuilderImpl implements ObjectSortBuilder, ObjectSortPropertyGetter {
    private final Map<String,Class<?>> orderEntityMap=new HashMap<>();
    private final Map<String, ObjectSortPropertyNode> orderPropertyMap=new LinkedHashMap<>();
    private final DynamicModeEnum dynamicMode;

    public ObjectSortBuilderImpl(DynamicModeEnum dynamicMode){

        this.dynamicMode = dynamicMode;
    }

    private boolean isStrictMode(){
        return Objects.equals(DynamicModeEnum.STRICT,dynamicMode);
    }
    public <TEntity> ObjectSortPropertyBuilder<TEntity> mapTo(Class<TEntity> entityClass){
        return new ObjectSortPropertyBuilderImpl<>(orderEntityMap,entityClass);
    }

    @Override
    public ObjectSortBuilder orderBy(String propertyName, boolean asc) {
        Class<?> entityClass = orderEntityMap.get(propertyName);
        if(entityClass==null){
            if(isStrictMode()){
                throw new EasyQueryOrderByInvalidOperationException(propertyName,"object order by unknown property "+propertyName);
            }
        }else{
            orderPropertyMap.put(propertyName,new ObjectSortPropertyNode(entityClass,asc));
        }
        return this;
    }

    @Override
    public Map<String, ObjectSortPropertyNode> getProperties() {
        return orderPropertyMap;
    }
}
