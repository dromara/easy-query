package com.easy.query.core.expression.lambda;

/**
 * create time 2023/4/14 17:25
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface Selector<T,R> {
    R apply(T t,int index);
}
