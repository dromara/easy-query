package com.easy.query.core.expression.lambda;

/**
 * create time 2023/2/4 22:31
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLFuncExpression2<T1, T2, TR> {

    TR apply(T1 p1, T2 p2);
}