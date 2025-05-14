package com.easy.query.core.expression.lambda;

/**
 * create time 2023/12/2 17:04
 * 2个参数入参的代码块
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLActionExpression2<T1, T2> {

    void apply(T1 p1, T2 p2);
}