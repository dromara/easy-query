package com.easy.query.api.lambda.crud.read;

import com.easy.query.api.lambda.crud.read.group.GroupedQuery2;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.lambda.condition.groupBy.GroupBy;
import com.easy.query.core.lambda.condition.include.Include;
import com.easy.query.core.lambda.condition.join.Join;
import com.easy.query.core.lambda.condition.limit.Limit;
import com.easy.query.core.lambda.condition.orderby.OrderBy;
import com.easy.query.core.lambda.condition.select.Select;
import com.easy.query.core.lambda.condition.where.Where;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.delegate.Func2;
import io.github.kiryu1223.expressionTree.delegate.Func3;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class LQuery2<T1, T2> extends QueryBase
{
    protected final ClientQueryable2<T1, T2> clientQueryable;

    public ClientQueryable2<T1, T2> getClientQueryable()
    {
        return clientQueryable;
    }

    public LQuery2(ClientQueryable2<T1, T2> clientQueryable2, DbType dbType)
    {
        super(new QueryData(dbType));
        this.clientQueryable = clientQueryable2;
    }

    //region [JOIN]
    public <Tn> LQuery3<T1, T2, Tn> innerJoin(Class<Tn> target, @Expr Func3<T1, T2, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery3<T1, T2, Tn> innerJoin(Class<Tn> target, ExprTree<Func3<T1, T2, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable3<T1, T2, Tn> joinQuery = join.innerJoin(target, clientQueryable, queryData);
        return new LQuery3<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery3<T1, T2, Tn> innerJoin(LQuery<Tn> target, @Expr Func3<T1, T2, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery3<T1, T2, Tn> innerJoin(LQuery<Tn> target, ExprTree<Func3<T1, T2, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable3<T1, T2, Tn> joinQuery = join.innerJoin(target.getClientQueryable(), clientQueryable, queryData);
        return new LQuery3<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery3<T1, T2, Tn> leftJoin(Class<Tn> target, @Expr Func3<T1, T2, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery3<T1, T2, Tn> leftJoin(Class<Tn> target, ExprTree<Func3<T1, T2, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable3<T1, T2, Tn> joinQuery = join.leftJoin(target, clientQueryable, queryData);
        return new LQuery3<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery3<T1, T2, Tn> leftJoin(LQuery<Tn> target, @Expr Func3<T1, T2, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery3<T1, T2, Tn> leftJoin(LQuery<Tn> target, ExprTree<Func3<T1, T2, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable3<T1, T2, Tn> joinQuery = join.leftJoin(target.getClientQueryable(), clientQueryable, queryData);
        return new LQuery3<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery3<T1, T2, Tn> rightJoin(Class<Tn> target, @Expr Func3<T1, T2, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery3<T1, T2, Tn> rightJoin(Class<Tn> target, ExprTree<Func3<T1, T2, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable3<T1, T2, Tn> joinQuery = join.rightJoin(target, clientQueryable, queryData);
        return new LQuery3<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery3<T1, T2, Tn> rightJoin(LQuery<Tn> target, @Expr Func3<T1, T2, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery3<T1, T2, Tn> rightJoin(LQuery<Tn> target, ExprTree<Func3<T1, T2, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable3<T1, T2, Tn> joinQuery = join.rightJoin(target.getClientQueryable(), clientQueryable, queryData);
        return new LQuery3<>(joinQuery, queryData.getDbType());
    }
    // endregion

    // region [WHERE]
    public LQuery2<T1, T2> where(@Expr Func2<T1, T2, Boolean> func)
    {
        throw new RuntimeException();
    }

    public LQuery2<T1, T2> where(ExprTree<Func2<T1, T2, Boolean>> expr)
    {
        Where where = new Where(expr.getTree());
        where.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [ORDER BY]
    public <R> LQuery2<T1, T2> orderBy(@Expr Func2<T1, T2, R> expr, boolean asc)
    {
        throw new RuntimeException();
    }

    public <R> LQuery2<T1, T2> orderBy(ExprTree<Func2<T1, T2, R>> expr, boolean asc)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), asc);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }

    public <R> LQuery2<T1, T2> orderBy(@Expr Func2<T1, T2, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery2<T1, T2> orderBy(ExprTree<Func2<T1, T2, R>> expr)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), true);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [LIMIT]
    public LQuery2<T1, T2> limit(long rows)
    {
        Limit limit = new Limit(rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }

    public LQuery2<T1, T2> limit(long offset, long rows)
    {
        Limit limit = new Limit(offset, rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [GROUP BY]
    public <Key> GroupedQuery2<Key, T1, T2> groupBy(@Expr Func2<T1, T2, Key> expr)
    {
        throw new RuntimeException();
    }

    public <Key> GroupedQuery2<Key, T1, T2> groupBy(ExprTree<Func2<T1, T2, Key>> expr)
    {
        GroupBy groupBy = new GroupBy(expr.getTree());
        groupBy.analysis(clientQueryable, queryData);
        return new GroupedQuery2<>(clientQueryable, queryData);
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

    public <R> LQuery<R> select(@Expr Func2<T1, T2, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<R> select(ExprTree<Func2<T1, T2, R>> expr)
    {
        Select select = new Select(expr.getTree());
        return new LQuery<>(select.analysis(clientQueryable, queryData), queryData.getDbType());
    }
    // endregion

    // region [INCLUDE]
    public <R> LQuery2<T1, T2> include(@Expr Func2<T1, T2, R> expr, int groupSize)
    {
        throw new RuntimeException();
    }

    public <R> LQuery2<T1, T2> include(ExprTree<Func2<T1, T2, R>> expr, int groupSize)
    {
        Include include = new Include(expr.getTree(), groupSize);
        include.analysis(clientQueryable, queryData);
        return this;
    }

    public <R> LQuery2<T1, T2> include(@Expr Func2<T1, T2, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery2<T1, T2> include(ExprTree<Func2<T1, T2, R>> expr)
    {
        Include include = new Include(expr.getTree());
        include.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    //region [OTHER]

    public LQuery2<T1, T2> distinct()
    {
        clientQueryable.distinct();
        return this;
    }

    public LQuery2<T1, T2> distinct(boolean condition)
    {
        clientQueryable.distinct(condition);
        return this;
    }

    public boolean any()
    {
        return clientQueryable.any();
    }

    public T1 firstOrNull()
    {
        return clientQueryable.firstOrNull();
    }

    public <R> R firstOrNull(Class<R> r)
    {
        return clientQueryable.firstOrNull(r);
    }

    public T1 firstNotNull()
    {
        return clientQueryable.firstNotNull();
    }

    public T1 firstNotNull(String msg)
    {
        return clientQueryable.firstNotNull(msg);
    }

    public T1 firstNotNull(String msg, String code)
    {
        return clientQueryable.firstNotNull(msg, code);
    }

    public T1 firstNotNull(Supplier<RuntimeException> throwFunc)
    {
        return clientQueryable.firstNotNull(throwFunc);
    }

    public <R> R firstNotNull(Class<R> r)
    {
        return clientQueryable.firstNotNull(r);
    }

    public <R> R firstNotNull(Class<R> r, String msg)
    {
        return clientQueryable.firstNotNull(r, msg);
    }

    public <R> R firstNotNull(Class<R> r, String msg, String code)
    {
        return clientQueryable.firstNotNull(r, msg, code);
    }
    //endregion

    // region [toAny]

    public String toSQL()
    {
        return clientQueryable.toSQL();
    }

    public ToSQLResult toSQLResult()
    {
        return clientQueryable.toSQLResult();
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

    // endregion
}
