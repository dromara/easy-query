package com.easy.query.core.api.dynamic.order.internal;

import com.easy.query.core.api.dynamic.order.EasyOrderByBuilder;
import com.easy.query.core.api.dynamic.order.EasyOrderByPropertyBuilder;
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
public class DefaultOrderByBuilder implements EasyOrderByBuilder, EasyOrderByPropertyGetter {
    private final Map<String,Class<?>> orderEntityMap=new HashMap<>();
    private final Map<String, DynamicOrderByPropertyNode> orderPropertyMap=new LinkedHashMap<>();
    private final DynamicModeEnum dynamicMode;

    public DefaultOrderByBuilder(DynamicModeEnum dynamicMode){

        this.dynamicMode = dynamicMode;
    }

    private boolean isStrictMode(){
        return Objects.equals(DynamicModeEnum.STRICT,dynamicMode);
    }
    public <TEntity> EasyOrderByPropertyBuilder<TEntity> mapTo(Class<TEntity> entityClass){
        return new DefaultEasyOrderByPropertyBuilder<>(orderEntityMap,entityClass);
    }

    @Override
    public EasyOrderByBuilder orderBy(String propertyName, boolean asc) {
        Class<?> entityClass = orderEntityMap.get(propertyName);
        if(entityClass==null){
            if(isStrictMode()){
                throw new EasyQueryOrderByInvalidOperationException(propertyName,"object order by unknown property "+propertyName);
            }
        }else{
            orderPropertyMap.put(propertyName,new DynamicOrderByPropertyNode(entityClass,asc));
        }
        return this;
    }

    @Override
    public Map<String, DynamicOrderByPropertyNode> getOrderProperties() {
        return orderPropertyMap;
    }
}
