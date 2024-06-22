package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.sqlext.SqlFunctionInvokeException;
import io.github.kiryu1223.expressionTree.delegate.Func2;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation2<T1, T2> implements IAggregation
{

    public <R> long count(Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal sum(Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> BigDecimal avg(Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R max(Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

    public <R> R min(Func2<T1, T2, R> expr)
    {
        throw new SqlFunctionInvokeException();
    }

}
