package com.easy.query.core.expression.lambda;

/**
 *
 * @FileName: OnFunction.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:31
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLExpression<T1> {

    void apply(T1 p1);
}