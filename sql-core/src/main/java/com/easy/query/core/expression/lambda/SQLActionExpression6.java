package com.easy.query.core.expression.lambda;

/**
 * create time 2023/12/2 17:04
 * 6个参数入参的代码块
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLActionExpression6<T1, T2, T3, T4, T5, T6> {

    void apply(T1 p1, T2 p2, T3 p3, T4 p4, T5 p5, T6 p6);
}