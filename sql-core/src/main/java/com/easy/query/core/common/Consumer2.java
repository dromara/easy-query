package com.easy.query.core.common;

/**
 * create time 2024/12/12 16:43
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface Consumer2<T1,T2> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t1 the input argument
     * @param t2 the input argument
     */
    void accept(T1 t1,T2 t2);
}