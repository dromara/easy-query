package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;

public interface IAggregation
{
    default long count()
    {
        throw new SqlFunctionInvokeException();
    }

    default <R> long count(int i)
    {
        throw new SqlFunctionInvokeException();
    }
}
