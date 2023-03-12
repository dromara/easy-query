package com.easy.query.core.expression.lambda;

import java.io.Serializable;

/**
 *
 * @FileName: SFunction.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:16
 * @Created by xuejiaming
 */
@FunctionalInterface
public interface Property<T, R> extends Serializable {
    R apply(T t);
}

