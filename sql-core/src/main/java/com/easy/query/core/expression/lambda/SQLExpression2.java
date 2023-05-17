package com.easy.query.core.expression.lambda;

/**
 *
 * @FileName: OnFunction.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:31
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLExpression2<T1,T2> {

    void apply(T1 p1,T2 p2);
}