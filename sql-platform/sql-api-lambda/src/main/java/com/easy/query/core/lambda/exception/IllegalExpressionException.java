package com.easy.query.core.lambda.exception;

public class IllegalExpressionException extends RuntimeException
{
    public IllegalExpressionException()
    {
        super("不支持的表达式");
    }

    public IllegalExpressionException(Object mes)
    {
        super("不支持的表达式\n" + mes);
    }
}
