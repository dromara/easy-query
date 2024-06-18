package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;
import io.github.kiryu1223.expressionTree.delegate.Func5;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation5<T1, T2, T3, T4, T5> implements IAggregation
{
    public <R> long count(Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

}
