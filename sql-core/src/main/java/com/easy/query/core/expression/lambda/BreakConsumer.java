package com.easy.query.core.expression.lambda;

/**
 * create time 2023/5/18 17:39
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface BreakConsumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    boolean accept(T t);
}
