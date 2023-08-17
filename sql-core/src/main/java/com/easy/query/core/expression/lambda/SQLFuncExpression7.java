package com.easy.query.core.expression.lambda;

/**
 * create time 2023/8/17 17:26
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLFuncExpression7<T1, T2, T3, T4, T5, T6, T7, TR> {

    TR apply(T1 p1, T2 p2, T3 p3, T4 p4, T5 p5, T6 p6, T7 p7);
}
