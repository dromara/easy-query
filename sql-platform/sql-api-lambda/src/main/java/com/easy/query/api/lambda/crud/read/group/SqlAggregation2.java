package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.crud.read.IAggregation;
import io.github.kiryu1223.expressionTree.delegate.Func2;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation2<T1, T2> implements IAggregation
{
    private long count;
    private BigDecimal sum;
    private BigDecimal avg;
    private Object max;
    private Object min;

    public <R> long count(@Expr Func2<T1, T2, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal sum(@Expr Func2<T1, T2, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal avg(@Expr Func2<T1, T2, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> R max(@Expr Func2<T1, T2, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> R min(@Expr Func2<T1, T2, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> long count()
    {
        return count;
    }

    public <R> long count(int i)
    {
        return count;
    }

    public <R> long count(ExprTree<Func2<T1, T2, R>> expr)
    {
        return count;
    }

    public <R> BigDecimal sum(ExprTree<Func2<T1, T2, R>> expr)
    {
        return sum;
    }

    public <R> BigDecimal avg(ExprTree<Func2<T1, T2, R>> expr)
    {
        return avg;
    }

    public <R> R min(ExprTree<Func2<T1, T2, R>> expr)
    {
        return (R) min;
    }

    public <R> R max(ExprTree<Func2<T1, T2, R>> expr)
    {
        return (R) max;
    }
}
