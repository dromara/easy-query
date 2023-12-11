package com.easy.query.core.basic.extension.complex;

import java.lang.reflect.Type;

/**
 * create time 2023/9/29 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ComplexPropType {
    /**
     * 请使用接口complexType
     * 因为get接口会导致json序列化的时候将其生成为json属性
     * @return
     */
    Type complexType();
}
