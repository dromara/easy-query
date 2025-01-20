package com.easy.query.core.basic.extension.conversion.arg;

/**
 * create time 2025/1/20 23:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ArgExpressionFactory {
    ArgExpression create(Class<? extends ArgExpression> argExpressionClass);
}
