package com.easy.query.core.expression.lambda;

/**
 * create time 2023/12/2 17:04
 * 1个参数入参的代码块
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLActionExpression1<T1> {

    void apply(T1 p1);
}