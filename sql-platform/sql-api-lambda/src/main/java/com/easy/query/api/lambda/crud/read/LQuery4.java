package com.easy.query.api.lambda.crud.read;

import com.easy.query.api.lambda.crud.read.group.GroupedQuery3;
import com.easy.query.api.lambda.crud.read.group.GroupedQuery4;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.lambda.condition.groupBy.GroupBy;
import com.easy.query.core.lambda.condition.limit.Limit;
import com.easy.query.core.lambda.condition.orderby.OrderBy;
import com.easy.query.core.lambda.condition.select.Select;
import com.easy.query.core.lambda.condition.where.Where;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.delegate.Func4;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.util.ArrayList;
import java.util.List;

public class LQuery4<T1, T2, T3, T4> extends QueryBase
{
    protected final ClientQueryable4<T1, T2, T3, T4> clientQueryable;

    public ClientQueryable4<T1, T2, T3, T4> getClientQueryable()
    {
        return clientQueryable;
    }

    public LQuery4(ClientQueryable4<T1, T2, T3, T4> clientQueryable, DbType dbType)
    {
        super(new QueryData(dbType));
        this.clientQueryable = clientQueryable;
    }

    // region [WHERE]
    public LQuery4<T1, T2, T3, T4> where(@Expr Func4<T1, T2, T3, T4, Boolean> func)
    {
        throw new RuntimeException();
    }

    public LQuery4<T1, T2, T3, T4> where(ExprTree<Func4<T1, T2, T3, T4, Boolean>> expr)
    {
        Where where = new Where(expr.getTree());
        where.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [ORDER BY]
    public <R> LQuery4<T1, T2, T3, T4> orderBy(@Expr Func4<T1, T2, T3, T4, R> expr, boolean asc)
    {
        throw new RuntimeException();
    }

    public <R> LQuery4<T1, T2, T3, T4> orderBy(ExprTree<Func4<T1, T2, T3, T4, R>> expr, boolean asc)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), asc);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }

    public <R> LQuery4<T1, T2, T3, T4> orderBy(@Expr Func4<T1, T2, T3, T4, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery4<T1, T2, T3, T4> orderBy(ExprTree<Func4<T1, T2, T3, T4, R>> expr)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), true);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [LIMIT]
    public LQuery4<T1, T2, T3, T4> limit(long rows)
    {
        Limit limit = new Limit(rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }

    public LQuery4<T1, T2, T3, T4> limit(long offset, long rows)
    {
        Limit limit = new Limit(offset, rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [GROUP BY]
    public <Key> GroupedQuery4<Key, T1, T2, T3,T4> groupBy(@Expr Func4<T1, T2, T3, T4, Key> expr)
    {
        throw new RuntimeException();
    }

    public <Key>GroupedQuery4<Key, T1, T2, T3,T4> groupBy(ExprTree<Func4<T1, T2, T3, T4, Key>> expr)
    {
        GroupBy groupBy = new GroupBy(expr.getTree());
        groupBy.analysis(clientQueryable, queryData);
        return new GroupedQuery4<>(clientQueryable, queryData);
    }
    // endregion

    // region [SELECT]
    public LQuery<T1> select()
    {
        ClientQueryable<T1> select = clientQueryable.select(s -> s.columnAll());
        return new LQuery<>(select, queryData.getDbType());
    }

    public <R> LQuery<R> select(@Expr Func4<T1, T2, T3, T4, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<R> select(ExprTree<Func4<T1, T2, T3, T4, R>> expr)
    {
        Select select = new Select(expr.getTree());
        return new LQuery<>(select.analysis(clientQueryable, queryData), queryData.getDbType());
    }
    // endregion

    //region [OTHER]

    public T1 firstOrNull()
    {
        return clientQueryable.firstOrNull();
    }

    //endregion

    public String toSQL()
    {
        return clientQueryable.toSQL();
    }

    public List<T1> toList()
    {
        return clientQueryable.toList();
    }

    public <R> List<R> toList(Func1<T1, R> func)
    {
        List<R> rList = new ArrayList<>();
        for (T1 t : toList())
        {
            rList.add(func.invoke(t));
        }
        return rList;
    }
}
