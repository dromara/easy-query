package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;
import io.github.kiryu1223.expressionTree.delegate.Func7;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation7<T1, T2, T3, T4, T5, T6, T7> implements IAggregation
{
    public <R> long count(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

}
