package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.crud.read.LQuery;
import com.easy.query.api.lambda.crud.read.QueryBase;
import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.lambda.condition.having.Having;
import com.easy.query.core.lambda.condition.orderby.OrderBy;
import com.easy.query.core.lambda.condition.select.Select;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

public class GroupedQuery8<Key, T1, T2, T3, T4, T5, T6, T7, T8> extends QueryBase
{
    protected final ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable;

    public GroupedQuery8(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable, QueryData queryData)
    {
        super(queryData);
        this.clientQueryable = clientQueryable;
    }

    // region [HAVING]
    public GroupedQuery8<Key, T1, T2, T3, T4, T5, T6, T7, T8> having(@Expr Func1<Group8<Key, T1, T2, T3, T4, T5, T6, T7, T8>, Boolean> func)
    {
        throw new RuntimeException();
    }

    public GroupedQuery8<Key, T1, T2, T3, T4, T5, T6, T7, T8> having(ExprTree<Func1<Group8<Key, T1, T2, T3, T4, T5, T6, T7, T8>, Boolean>> expr)
    {
        Having having = new Having(expr.getTree());
        having.analysis(clientQueryable, queryData);
        return this;
    }

    // endregion

    // region [ORDER BY]
    public <R> GroupedQuery8<Key, T1, T2, T3, T4, T5, T6, T7, T8> orderBy(@Expr Func1<Group8<Key, T1, T2, T3, T4, T5, T6, T7, T8>, R> expr, boolean asc)
    {
        throw new RuntimeException();
    }

    public <R> GroupedQuery8<Key, T1, T2, T3, T4, T5, T6, T7, T8> orderBy(ExprTree<Func1<Group8<Key, T1, T2, T3, T4, T5, T6, T7, T8>, R>> expr, boolean asc)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), asc);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }

    public <R> GroupedQuery8<Key, T1, T2, T3, T4, T5, T6, T7, T8> orderBy(@Expr Func1<Group8<Key, T1, T2, T3, T4, T5, T6, T7, T8>, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> GroupedQuery8<Key, T1, T2, T3, T4, T5, T6, T7, T8> orderBy(ExprTree<Func1<Group8<Key, T1, T2, T3, T4, T5, T6, T7, T8>, R>> expr)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), true);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [SELECT]
    public <R> LQuery<R> select(@Expr Func1<Group8<Key, T1, T2, T3, T4, T5, T6, T7, T8>, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<R> select(ExprTree<Func1<Group8<Key, T1, T2, T3, T4, T5, T6, T7, T8>, R>> expr)
    {
        Select select = new Select(expr.getTree());
        return new LQuery<>(select.analysis(clientQueryable, queryData), queryData.getDbType());
    }
    // endregion
}
