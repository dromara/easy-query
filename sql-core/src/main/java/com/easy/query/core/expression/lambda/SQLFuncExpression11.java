package com.easy.query.core.expression.lambda;

/**
 * create time 2023/8/17 17:26
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLFuncExpression11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, TR> {

    TR apply(T1 p1, T2 p2, T3 p3, T4 p4, T5 p5, T6 p6, T7 p7, T8 p8, T9 p9, T10 p10, T11 p11);
}