package com.easy.query.core.expression.lambda;

import java.io.Serializable;

/**
 *
 * @FileName: SFunction.java
 * @Description: 文件说明
 * create time 2023/2/4 22:16
 * @author xuejiaming
 */
@FunctionalInterface
public interface PropertyVoidSetter<T1,T2> extends Serializable {
    void apply(T1 t,T2 value);
}

