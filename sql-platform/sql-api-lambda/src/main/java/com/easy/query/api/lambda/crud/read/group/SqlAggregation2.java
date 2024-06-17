package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;
import io.github.kiryu1223.expressionTree.delegate.Func2;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation2<T1, T2> implements IAggregation
{

    public <R> long count(@Expr Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(@Expr Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(@Expr Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(@Expr Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(@Expr Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> long count(ExprTree<Func2<T1, T2, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(ExprTree<Func2<T1, T2, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(ExprTree<Func2<T1, T2, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(ExprTree<Func2<T1, T2, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(ExprTree<Func2<T1, T2, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }
}
