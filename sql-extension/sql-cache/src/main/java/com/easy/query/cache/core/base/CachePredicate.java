package com.easy.query.cache.core.base;

/**
 * create time 2024/1/25 13:46
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface CachePredicate<T> {
    boolean apply(T element);
}