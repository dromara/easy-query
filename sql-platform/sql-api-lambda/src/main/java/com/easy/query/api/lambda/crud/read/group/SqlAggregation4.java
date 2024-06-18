package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;
import io.github.kiryu1223.expressionTree.delegate.Func4;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation4<T1, T2, T3, T4> implements IAggregation
{
    public <R> long count(Func4<T1, T2, T3, T4, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(Func4<T1, T2, T3, T4, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(Func4<T1, T2, T3, T4, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(Func4<T1, T2, T3, T4, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(Func4<T1, T2, T3, T4, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

}
