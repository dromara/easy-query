package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.crud.read.IAggregation;
import io.github.kiryu1223.expressionTree.delegate.Func3;
import io.github.kiryu1223.expressionTree.delegate.Func4;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public class SqlAggregation4<T1, T2, T3, T4> implements IAggregation
{
    public <R> long count(@Expr Func4<T1, T2, T3, T4, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal sum(@Expr Func4<T1, T2, T3, T4, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal avg(@Expr Func4<T1, T2, T3, T4, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> R max(@Expr Func4<T1, T2, T3, T4, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> R min(@Expr Func4<T1, T2, T3, T4, R> expr)
    {
        throw new RuntimeException();
    }

    public long count()
    {
        throw new RuntimeException();
    }

    public long count(int i)
    {
        throw new RuntimeException();
    }

    public <R> long count(ExprTree<Func4<T1, T2, T3, T4, R>> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal sum(ExprTree<Func4<T1, T2, T3, T4, R>> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal avg(ExprTree<Func4<T1, T2, T3, T4, R>> expr)
    {
        throw new RuntimeException();
    }

    public <R> R min(ExprTree<Func4<T1, T2, T3, T4, R>> expr)
    {
        throw new RuntimeException();
    }

    public <R> R max(ExprTree<Func4<T1, T2, T3, T4, R>> expr)
    {
        throw new RuntimeException();
    }
}
