package com.easy.query.core.expression.lambda;

/**
 * create time 2023/4/18 23:04
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface RouteFunction<T> {
    boolean apply(T t);
}
