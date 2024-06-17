package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;
import io.github.kiryu1223.expressionTree.delegate.Func4;
import io.github.kiryu1223.expressionTree.delegate.Func5;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation5<T1, T2, T3, T4, T5> implements IAggregation
{
    public <R> long count(@Expr Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(@Expr Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(@Expr Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(@Expr Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(@Expr Func5<T1, T2, T3, T4, T5, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> long count(ExprTree<Func5<T1, T2, T3, T4, T5, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(ExprTree<Func5<T1, T2, T3, T4, T5, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(ExprTree<Func5<T1, T2, T3, T4, T5, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(ExprTree<Func5<T1, T2, T3, T4, T5, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(ExprTree<Func5<T1, T2, T3, T4, T5, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }
}
