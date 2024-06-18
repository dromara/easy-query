package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;
import io.github.kiryu1223.expressionTree.delegate.Func10;
import io.github.kiryu1223.expressionTree.delegate.Func4;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> implements IAggregation
{
    public <R> long count(@Expr Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(@Expr Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(@Expr Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(@Expr Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(@Expr Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> long count(ExprTree<Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(ExprTree<Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(ExprTree<Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(ExprTree<Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(ExprTree<Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> expr)
    {
        throw new SqlFunctionInvokeException();
    }
}
