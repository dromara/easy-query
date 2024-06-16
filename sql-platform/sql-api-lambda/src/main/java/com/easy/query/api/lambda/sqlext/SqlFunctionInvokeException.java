package com.easy.query.api.lambda.sqlext;

public class SqlFunctionInvokeException extends RuntimeException
{
    public SqlFunctionInvokeException()
    {
        super("SqlFunction不能在查询表达式以外的地方被调用");
    }
}
