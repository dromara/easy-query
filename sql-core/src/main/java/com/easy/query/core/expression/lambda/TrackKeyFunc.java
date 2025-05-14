package com.easy.query.core.expression.lambda;

/**
 * @FileName: TrackKeyFunc.java
 * @Description: 文件说明
 * create time 2023/3/19 17:09
 * @author xuejiaming
 */
@FunctionalInterface
public interface TrackKeyFunc<T> {
    String get(T entity);
}
