package com.easy.query.api.lambda.crud.read;

import com.easy.query.api.lambda.crud.read.group.GroupedQuery3;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.lambda.condition.groupBy.GroupBy;
import com.easy.query.core.lambda.condition.join.Join;
import com.easy.query.core.lambda.condition.limit.Limit;
import com.easy.query.core.lambda.condition.orderby.OrderBy;
import com.easy.query.core.lambda.condition.select.Select;
import com.easy.query.core.lambda.condition.where.Where;
import com.easy.query.core.lambda.visitor.SqlValue;
import com.easy.query.core.lambda.visitor.WhereVisitor;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.delegate.Func3;
import io.github.kiryu1223.expressionTree.delegate.Func4;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.util.ArrayList;
import java.util.List;

public class LQuery3<T1, T2, T3> extends QueryBase
{
    protected final ClientQueryable3<T1, T2, T3> clientQueryable;

    public ClientQueryable3<T1, T2, T3> getClientQueryable()
    {
        return clientQueryable;
    }

    public LQuery3(ClientQueryable3<T1, T2, T3> clientQueryable3, DbType dbType)
    {
        super(new QueryData(dbType));
        this.clientQueryable = clientQueryable3;
    }

    //region [JOIN]
    public <Tn> LQuery4<T1, T2, T3, Tn> innerJoin(Class<Tn> target, @Expr Func4<T1, T2, T3, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> innerJoin(Class<Tn> target, ExprTree<Func4<T1, T2, T3, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable4<T1, T2, T3, Tn> clientQueryable4 = join.innerJoin(target, clientQueryable, queryData);
        return new LQuery4<>(clientQueryable4, queryData.getDbType());
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> innerJoin(LQuery<Tn> target, @Expr Func4<T1, T2, T3, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> innerJoin(LQuery<Tn> target, ExprTree<Func4<T1, T2, T3, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable4<T1, T2, T3, Tn> clientQueryable4 = join.innerJoin(target.getClientQueryable(), clientQueryable, queryData);
        return new LQuery4<>(clientQueryable4, queryData.getDbType());
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> leftJoin(Class<Tn> target, @Expr Func4<T1, T2, T3, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> leftJoin(Class<Tn> target, ExprTree<Func4<T1, T2, T3, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable4<T1, T2, T3, Tn> clientQueryable4 = join.leftJoin(target, clientQueryable, queryData);
        return new LQuery4<>(clientQueryable4, queryData.getDbType());
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> leftJoin(LQuery<Tn> target, @Expr Func4<T1, T2, T3, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> leftJoin(LQuery<Tn> target, ExprTree<Func4<T1, T2, T3, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable4<T1, T2, T3, Tn> clientQueryable4 = join.leftJoin(target.getClientQueryable(), clientQueryable, queryData);
        return new LQuery4<>(clientQueryable4, queryData.getDbType());
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> rightJoin(Class<Tn> target, @Expr Func4<T1, T2, T3, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> rightJoin(Class<Tn> target, ExprTree<Func4<T1, T2, T3, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable4<T1, T2, T3, Tn> clientQueryable4 = join.rightJoin(target, clientQueryable, queryData);
        return new LQuery4<>(clientQueryable4, queryData.getDbType());
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> rightJoin(LQuery<Tn> target, @Expr Func4<T1, T2, T3, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery4<T1, T2, T3, Tn> rightJoin(LQuery<Tn> target, ExprTree<Func4<T1, T2, T3, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable4<T1, T2, T3, Tn> clientQueryable4 = join.rightJoin(target.getClientQueryable(), clientQueryable, queryData);
        return new LQuery4<>(clientQueryable4, queryData.getDbType());
    }

    // endregion

    // region [WHERE]
    public LQuery3<T1, T2, T3> where(@Expr Func3<T1, T2, T3, Boolean> func)
    {
        throw new RuntimeException();
    }

    public LQuery3<T1, T2, T3> where(ExprTree<Func3<T1, T2, T3, Boolean>> expr)
    {
        Where where = new Where(expr.getTree());
        where.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [ORDER BY]
    public <R> LQuery3<T1, T2, T3> orderBy(@Expr Func3<T1, T2, T3, R> expr, boolean asc)
    {
        throw new RuntimeException();
    }

    public <R> LQuery3<T1, T2, T3> orderBy(ExprTree<Func3<T1, T2, T3, R>> expr, boolean asc)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), asc);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }

    public <R> LQuery3<T1, T2, T3> orderBy(@Expr Func3<T1, T2, T3, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery3<T1, T2, T3> orderBy(ExprTree<Func3<T1, T2, T3, R>> expr)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), true);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [LIMIT]
    public LQuery3<T1, T2, T3> limit(long rows)
    {
        Limit limit = new Limit(rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }

    public LQuery3<T1, T2, T3> limit(long offset, long rows)
    {
        Limit limit = new Limit(offset, rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [GROUP BY]
    public <Key> GroupedQuery3<Key, T1, T2, T3> groupBy(@Expr Func3<T1, T2, T3, Key> expr)
    {
        throw new RuntimeException();
    }

    public <Key> GroupedQuery3<Key, T1, T2, T3> groupBy(ExprTree<Func3<T1, T2, T3, Key>> expr)
    {
        GroupBy groupBy = new GroupBy(expr.getTree());
        groupBy.analysis(clientQueryable, queryData);
        return new GroupedQuery3<>(clientQueryable, queryData);
    }
    // endregion

    // region [SELECT]
    public LQuery<T1> select()
    {
        ClientQueryable<T1> select = clientQueryable.select(s -> s.columnAll());
        return new LQuery<>(select, queryData.getDbType());
    }

    public <R> LQuery<R> select(Class<R> r)
    {
        ClientQueryable<R> select = clientQueryable.select(r);
        return new LQuery<>(select, queryData.getDbType());
    }

    public <R> LQuery<R> select(@Expr Func3<T1, T2, T3, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<R> select(ExprTree<Func3<T1, T2, T3, R>> expr)
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
