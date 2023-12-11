package com.easy.query.core.basic.extension.complex;

import java.lang.reflect.Type;

/**
 * create time 2023/9/29 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultComplexPropType implements ComplexPropType {
    private final Class<?> propertyType;

    public DefaultComplexPropType(Class<?> propertyType){

        this.propertyType = propertyType;
    }
    @Override
    public Type complexType() {
        return propertyType;
    }
}
