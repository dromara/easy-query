package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.crud.read.LQuery;
import com.easy.query.api.lambda.crud.read.QueryBase;
import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.lambda.condition.having.Having;
import com.easy.query.core.lambda.condition.orderby.OrderBy;
import com.easy.query.core.lambda.condition.select.Select;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

public class GroupedQuery<Key, T> extends QueryBase
{
    protected final ClientQueryable<T> clientQueryable;

    public GroupedQuery(ClientQueryable<T> entityQueryable, QueryData queryData)
    {
        super(queryData);
        this.clientQueryable = entityQueryable;
    }

    // region [HAVING]
    public GroupedQuery<Key, T> having(@Expr Func1<Group<Key, T>, Boolean> func)
    {
        throw new RuntimeException();
    }

    public GroupedQuery<Key, T> having(ExprTree<Func1<Group<Key, T>, Boolean>> expr)
    {
        Having having = new Having(expr.getTree());
        having.analysis(clientQueryable, queryData);
        return this;
    }

    // endregion

    // region [ORDER BY]

    public <R> GroupedQuery<Key, T> orderBy(@Expr Func1<Group<Key, T>, R> expr, boolean asc)
    {
        throw new RuntimeException();
    }

    public <R> GroupedQuery<Key, T> orderBy(ExprTree<Func1<Group<Key, T>, R>> expr, boolean asc)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), asc);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }


    public <R> GroupedQuery<Key, T> orderBy(@Expr Func1<Group<Key, T>, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> GroupedQuery<Key, T> orderBy(ExprTree<Func1<Group<Key, T>, R>> expr)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), true);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [SELECT]

    public <R> LQuery<R> select(@Expr Func1<Group<Key, T>, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<R> select(ExprTree<Func1<Group<Key, T>, R>> expr)
    {
        Select select = new Select(expr.getTree());
        return new LQuery<>(select.analysis(clientQueryable, queryData), queryData.getDbType());
    }

    // endregion
}
