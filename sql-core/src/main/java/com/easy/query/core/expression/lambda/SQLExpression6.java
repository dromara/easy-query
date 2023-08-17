package com.easy.query.core.expression.lambda;

/**
 *
 * @FileName: OnFunction.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:31
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLExpression6<T1,T2,T3,T4,T5,T6> {

    void apply(T1 p1,T2 p2,T3 p3,T4 p4,T5 p5,T6 p6);
}