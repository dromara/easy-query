package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.crud.read.IAggregation;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation<T> implements IAggregation
{
    private long count;
    private BigDecimal sum;
    private BigDecimal avg;
    private Object max;
    private Object min;

    public <R> BigDecimal sum(@Expr Func1<T, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal avg(@Expr Func1<T, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> R max(@Expr Func1<T, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> R min(@Expr Func1<T, R> expr)
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

    public <R> long count(@Expr Func1<T, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> long count(ExprTree<Func1<T, R>> expr)
    {
        return count;
    }

    public <R> BigDecimal sum(ExprTree<Func1<T, R>> expr)
    {
        return sum;
    }

    public <R> BigDecimal avg(ExprTree<Func1<T, R>> expr)
    {
        return avg;
    }

    public <R> R min(ExprTree<Func1<T, R>> expr)
    {
        return (R) min;
    }

    public <R> R max(ExprTree<Func1<T, R>> expr)
    {
        return (R) max;
    }
}
