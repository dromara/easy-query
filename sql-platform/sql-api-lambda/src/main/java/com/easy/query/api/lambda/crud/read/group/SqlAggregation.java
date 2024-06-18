package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation<T> implements IAggregation
{

    public <R> BigDecimal sum(Func1<T, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(Func1<T, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(Func1<T, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(Func1<T, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> long count(Func1<T, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

}
