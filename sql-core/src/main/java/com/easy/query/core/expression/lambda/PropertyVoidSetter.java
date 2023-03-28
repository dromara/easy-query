package com.easy.query.core.expression.lambda;

import java.io.Serializable;

/**
 *
 * @FileName: SFunction.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:16
 * @author xuejiaming
 */
@FunctionalInterface
public interface PropertyVoidSetter<T> extends Serializable {
    void apply(T t,Object value);
}

