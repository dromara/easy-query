package com.easy.query.api.lambda.crud.read;

import com.easy.query.api.lambda.crud.read.group.GroupedQuery7;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.lambda.condition.groupBy.GroupBy;
import com.easy.query.core.lambda.condition.include.Include;
import com.easy.query.core.lambda.condition.limit.Limit;
import com.easy.query.core.lambda.condition.orderby.OrderBy;
import com.easy.query.core.lambda.condition.select.Select;
import com.easy.query.core.lambda.condition.where.Where;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.delegate.Func7;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LQuery7<T1, T2, T3, T4, T5, T6, T7> extends QueryBase
{
    protected final ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> clientQueryable;

    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> getClientQueryable()
    {
        return clientQueryable;
    }

    public LQuery7(ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> clientQueryable, DbType dbType)
    {
        super(new QueryData(dbType));
        this.clientQueryable = clientQueryable;
    }

    // region [WHERE]
    public LQuery7<T1, T2, T3, T4, T5, T6, T7> where(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, Boolean> func)
    {
        throw new RuntimeException();
    }

    public LQuery7<T1, T2, T3, T4, T5, T6, T7> where(ExprTree<Func7<T1, T2, T3, T4, T5, T6, T7, Boolean>> expr)
    {
        Where where = new Where(expr.getTree());
        where.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [ORDER BY]
    public <R> LQuery7<T1, T2, T3, T4, T5, T6, T7> orderBy(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr, boolean asc)
    {
        throw new RuntimeException();
    }

    public <R> LQuery7<T1, T2, T3, T4, T5, T6, T7> orderBy(ExprTree<Func7<T1, T2, T3, T4, T5, T6, T7, R>> expr, boolean asc)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), asc);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }

    public <R> LQuery7<T1, T2, T3, T4, T5, T6, T7> orderBy(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery7<T1, T2, T3, T4, T5, T6, T7> orderBy(ExprTree<Func7<T1, T2, T3, T4, T5, T6, T7, R>> expr)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), true);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [LIMIT]
    public LQuery7<T1, T2, T3, T4, T5, T6, T7> limit(long rows)
    {
        Limit limit = new Limit(rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }

    public LQuery7<T1, T2, T3, T4, T5, T6, T7> limit(long offset, long rows)
    {
        Limit limit = new Limit(offset, rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [GROUP BY]
    public <Key> GroupedQuery7<Key, T1, T2, T3, T4, T5, T6, T7> groupBy(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, Key> expr)
    {
        throw new RuntimeException();
    }

    public <Key> GroupedQuery7<Key, T1, T2, T3, T4, T5, T6, T7> groupBy(ExprTree<Func7<T1, T2, T3, T4, T5, T6, T7, Key>> expr)
    {
        GroupBy groupBy = new GroupBy(expr.getTree());
        groupBy.analysis(clientQueryable, queryData);
        return new GroupedQuery7<>(clientQueryable, queryData);
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

    public <R> LQuery<R> select(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<R> select(ExprTree<Func7<T1, T2, T3, T4, T5, T6, T7, R>> expr)
    {
        Select select = new Select(expr.getTree());
        return new LQuery<>(select.analysis(clientQueryable, queryData), queryData.getDbType());
    }
    // endregion

    // region [INCLUDE]
    public <R> LQuery7<T1, T2, T3, T4, T5, T6, T7> include(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr, int groupSize)
    {
        throw new RuntimeException();
    }

    public <R> LQuery7<T1, T2, T3, T4, T5, T6, T7> include(ExprTree<Func7<T1, T2, T3, T4, T5, T6, T7, R>> expr, int groupSize)
    {
        Include include = new Include(expr.getTree(), groupSize);
        include.analysis(clientQueryable, queryData);
        return this;
    }

    public <R> LQuery7<T1, T2, T3, T4, T5, T6, T7> include(@Expr Func7<T1, T2, T3, T4, T5, T6, T7, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery7<T1, T2, T3, T4, T5, T6, T7> include(ExprTree<Func7<T1, T2, T3, T4, T5, T6, T7, R>> expr)
    {
        Include include = new Include(expr.getTree());
        include.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    //region [OTHER]

    public LQuery7<T1, T2, T3, T4, T5, T6, T7> distinct()
    {
        clientQueryable.distinct();
        return this;
    }

    public LQuery7<T1, T2, T3, T4, T5, T6, T7> distinct(boolean condition)
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
