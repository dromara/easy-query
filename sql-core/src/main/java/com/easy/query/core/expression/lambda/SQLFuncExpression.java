package com.easy.query.core.expression.lambda;

/**
 * create time 2023/6/23 21:22
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLFuncExpression<TR> {

    TR apply();
}