package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.crud.read.LQuery;
import com.easy.query.api.lambda.crud.read.QueryBase;
import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.lambda.condition.having.Having;
import com.easy.query.core.lambda.condition.orderby.OrderBy;
import com.easy.query.core.lambda.condition.select.Select;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

public class GroupedQuery4<Key, T1, T2, T3, T4> extends QueryBase
{
    protected final ClientQueryable4<T1, T2, T3, T4> clientQueryable3;

    public GroupedQuery4(ClientQueryable4<T1, T2, T3, T4> clientQueryable3, QueryData queryData)
    {
        super(queryData);
        this.clientQueryable3 = clientQueryable3;
    }

    public GroupedQuery4<Key, T1, T2, T3, T4> having(@Expr Func1<Group4<Key, T1, T2, T3, T4>, Boolean> func)
    {
        throw new RuntimeException();
    }

    public GroupedQuery4<Key, T1, T2, T3, T4> having(ExprTree<Func1<Group4<Key, T1, T2, T3, T4>, Boolean>> expr)
    {
        Having having = new Having(expr.getTree());
        having.analysis(clientQueryable3, queryData);
        return this;
    }

    public <R> GroupedQuery4<Key, T1, T2, T3, T4> orderBy(@Expr Func1<Group4<Key, T1, T2, T3, T4>, R> expr, boolean asc)
    {
        throw new RuntimeException();
    }

    public <R> GroupedQuery4<Key, T1, T2, T3, T4> orderBy(ExprTree<Func1<Group4<Key, T1, T2, T3, T4>, R>> expr, boolean asc)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), asc);
        orderBy.analysis(clientQueryable3, queryData);
        return this;
    }

    public <R> GroupedQuery4<Key, T1, T2, T3, T4> orderBy(@Expr Func1<Group4<Key, T1, T2, T3, T4>, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> GroupedQuery4<Key, T1, T2, T3, T4> orderBy(ExprTree<Func1<Group4<Key, T1, T2, T3, T4>, R>> expr)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), true);
        orderBy.analysis(clientQueryable3, queryData);
        return this;
    }

    public <R> LQuery<R> select(@Expr Func1<Group4<Key, T1, T2, T3, T4>, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<R> select(ExprTree<Func1<Group4<Key, T1, T2, T3, T4>, R>> expr)
    {
        Select select = new Select(expr.getTree());
        return new LQuery<>(select.analysis(clientQueryable3, queryData), queryData.getDbType());
    }
}
