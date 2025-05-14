package com.easy.query.core.expression.lambda;

/**
 * create time 2023/12/2 17:04
 * 3个参数入参的代码块
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLActionExpression3<T1, T2, T3> {

    void apply(T1 p1, T2 p2, T3 p3);
}