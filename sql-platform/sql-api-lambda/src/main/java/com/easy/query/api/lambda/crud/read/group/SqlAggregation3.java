package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;
import io.github.kiryu1223.expressionTree.delegate.Func3;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation3<T1, T2, T3> implements IAggregation
{
    public <R> long count(Func3<T1, T2, T3, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(Func3<T1, T2, T3, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(Func3<T1, T2, T3, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(Func3<T1, T2, T3, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(Func3<T1, T2, T3, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

}
