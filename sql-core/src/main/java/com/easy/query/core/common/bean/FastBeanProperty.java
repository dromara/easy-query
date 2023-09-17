package com.easy.query.core.common.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * create time 2023/9/17 13:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class FastBeanProperty {
    private final boolean isGeneric;
    private final PropertyDescriptor property;

    public FastBeanProperty(boolean isGeneric, PropertyDescriptor property){

        this.isGeneric = isGeneric;
        this.property = property;
    }
    public Class<?> getPropertyType(){
        if(isGeneric){
            return Object.class;
        }
        return property.getPropertyType();
    }
    public Method getWriteMethod(){
        return property.getWriteMethod();
    }
    public Method getReadMethod(){
        return property.getReadMethod();
    }
    public String getName(){
        return property.getName();
    }
}
