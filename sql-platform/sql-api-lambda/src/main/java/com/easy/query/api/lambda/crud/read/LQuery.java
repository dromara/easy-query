package com.easy.query.api.lambda.crud.read;

import com.easy.query.api.lambda.crud.read.group.GroupedQuery;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.Query;
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
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;


public class LQuery<T> extends QueryBase
{
    protected final ClientQueryable<T> clientQueryable;

    public ClientQueryable<T> getClientQueryable()
    {
        return clientQueryable;
    }

    public LQuery(ClientQueryable<T> clientQueryable, DbType dbType)
    {
        super(new QueryData(dbType));
        this.clientQueryable = clientQueryable;
    }

    //    todo: 等别人提需求
//    public <Tn> LQuery2<T, Tn> from(LQuery<Tn> Tn)
//    {
//        ClientQueryable2<?, Tn> from = clientQueryable.from(Tn.clientQueryable);
//        return new LQuery2<>(from, queryData);
//    }

//    //region [FROM]
//
//    public <Tn> LQuery2<T, Tn> from(Class<Tn> Tn)
//    {
//        return new LQuery2<>(clientQueryable.from(Tn), queryData.getDbType());
//    }
//
//    public <Tn, T3> LQuery3<T, Tn, T3> from(Class<Tn> Tn, Class<T3> t3)
//    {
//        return new LQuery3<>(clientQueryable.from(Tn, t3), queryData.getDbType());
//    }
//
//    //endregion

    //region [JOIN]

    public <Tn> LQuery2<T, Tn> innerJoin(Class<Tn> target, @Expr Func2<T, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery2<T, Tn> innerJoin(Class<Tn> target, ExprTree<Func2<T, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable2<T, Tn> joinQuery = join.innerJoin(target, clientQueryable, queryData);
        return new LQuery2<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery2<T, Tn> innerJoin(LQuery<Tn> target, @Expr Func2<T, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery2<T, Tn> innerJoin(LQuery<Tn> target, ExprTree<Func2<T, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable2<T, Tn> joinQuery = join.innerJoin(target.getClientQueryable(), clientQueryable, queryData);
        return new LQuery2<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery2<T, Tn> leftJoin(Class<Tn> target, @Expr Func2<T, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery2<T, Tn> leftJoin(Class<Tn> target, ExprTree<Func2<T, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable2<T, Tn> joinQuery = join.leftJoin(target, clientQueryable, queryData);
        return new LQuery2<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery2<T, Tn> leftJoin(LQuery<Tn> target, @Expr Func2<T, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery2<T, Tn> leftJoin(LQuery<Tn> target, ExprTree<Func2<T, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable2<T, Tn> joinQuery = join.leftJoin(target.getClientQueryable(), clientQueryable, queryData);
        return new LQuery2<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery2<T, Tn> rightJoin(Class<Tn> target, @Expr Func2<T, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery2<T, Tn> rightJoin(Class<Tn> target, ExprTree<Func2<T, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable2<T, Tn> joinQuery = join.rightJoin(target, clientQueryable, queryData);
        return new LQuery2<>(joinQuery, queryData.getDbType());
    }

    public <Tn> LQuery2<T, Tn> rightJoin(LQuery<Tn> target, @Expr Func2<T, Tn, Boolean> func)
    {
        throw new RuntimeException();
    }

    public <Tn> LQuery2<T, Tn> rightJoin(LQuery<Tn> target, ExprTree<Func2<T, Tn, Boolean>> expr)
    {
        Join join = new Join(expr.getTree());
        ClientQueryable2<T, Tn> joinQuery = join.rightJoin(target.getClientQueryable(), clientQueryable, queryData);
        return new LQuery2<>(joinQuery, queryData.getDbType());
    }

    //endregion

    // region [WHERE]

    public LQuery<T> where(@Expr Func1<T, Boolean> func)
    {
        throw new RuntimeException();
    }

    public LQuery<T> where(ExprTree<Func1<T, Boolean>> expr)
    {
        Where where = new Where(expr.getTree());
        where.analysis(clientQueryable, queryData);
        return this;
    }

    // endregion

    // region [ORDER BY]

    public <R> LQuery<T> orderBy(@Expr Func1<T, R> expr, boolean asc)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<T> orderBy(ExprTree<Func1<T, R>> expr, boolean asc)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), asc);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }

    public <R> LQuery<T> orderBy(@Expr Func1<T, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<T> orderBy(ExprTree<Func1<T, R>> expr)
    {
        OrderBy orderBy = new OrderBy(expr.getTree(), true);
        orderBy.analysis(clientQueryable, queryData);
        return this;
    }

    // endregion

    // region [LIMIT]

    public LQuery<T> limit(long rows)
    {
        Limit limit = new Limit(rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }

    public LQuery<T> limit(long offset, long rows)
    {
        Limit limit = new Limit(offset, rows);
        limit.analysis(clientQueryable, queryData);
        return this;
    }

    // endregion

    // region [GROUP BY]

    public <Key> GroupedQuery<Key, T> groupBy(@Expr Func1<T, Key> expr)
    {
        throw new RuntimeException();
    }

    public <Key> GroupedQuery<Key, T> groupBy(ExprTree<Func1<T, Key>> expr)
    {
        GroupBy groupBy = new GroupBy(expr.getTree());
        groupBy.analysis(clientQueryable, queryData);
        return new GroupedQuery<>(clientQueryable, queryData);
    }

    // endregion

    // region [SELECT]
    public LQuery<T> select()
    {
        ClientQueryable<T> select = clientQueryable.select(s -> s.columnAll());
        return new LQuery<>(select, queryData.getDbType());
    }

    public <R> LQuery<R> select(Class<R> r)
    {
        ClientQueryable<R> select = clientQueryable.select(r);
        return new LQuery<>(select, queryData.getDbType());
    }

    public <R> LQuery<R> select(@Expr Func1<T, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<R> select(ExprTree<Func1<T, R>> expr)
    {
        Select select = new Select(expr.getTree());
        return new LQuery<>(select.analysis(clientQueryable, queryData), queryData.getDbType());
    }

    public <R> EndQuery<R> selectAutoInclude(Class<R> r)
    {
        Query<R> query = clientQueryable.selectAutoInclude(r);
        return new EndQuery<>(query);
    }

    public <R> EndQuery<R> selectAutoInclude(@Expr Func1<T, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> EndQuery<R> selectAutoInclude(ExprTree<Func1<T, R>> expr)
    {
        Select select = new Select(expr.getTree());
        return new EndQuery<>(select.analysisAutoInclude(clientQueryable, queryData));
    }

    // endregion

    // region [INCLUDE]
    public <R> LQuery<T> include(@Expr Func1<T, R> expr, int groupSize)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<T> include(ExprTree<Func1<T, R>> expr, int groupSize)
    {
        Include include = new Include(expr.getTree(), groupSize);
        include.analysis(clientQueryable, queryData);
        return this;
    }

    public <R> LQuery<T> include(@Expr Func1<T, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> LQuery<T> include(ExprTree<Func1<T, R>> expr)
    {
        Include include = new Include(expr.getTree());
        include.analysis(clientQueryable, queryData);
        return this;
    }
    // endregion

    // region [UNION]

    public LQuery<T> union(LQuery<T> q1)
    {
        clientQueryable.union(q1.getClientQueryable());
        return this;
    }

    public LQuery<T> union(LQuery<T> q1, LQuery<T> q2)
    {
        clientQueryable.union(q1.getClientQueryable(), q2.getClientQueryable());
        return this;
    }

    public LQuery<T> union(LQuery<T> q1, LQuery<T> q2, LQuery<T> q3)
    {
        clientQueryable.union(q1.getClientQueryable(), q2.getClientQueryable(), q3.getClientQueryable());
        return this;
    }

    public LQuery<T> union(Collection<LQuery<T>> qs)
    {
        List<ClientQueryable<T>> clientQueryable = new ArrayList<>();
        for (LQuery<T> q : qs)
        {
            clientQueryable.add(q.getClientQueryable());
        }
        this.clientQueryable.union(clientQueryable);
        return this;
    }

    public LQuery<T> unionAll(LQuery<T> q1)
    {
        clientQueryable.unionAll(q1.getClientQueryable());
        return this;
    }

    public LQuery<T> unionAll(LQuery<T> q1, LQuery<T> q2)
    {
        clientQueryable.unionAll(q1.getClientQueryable(), q2.getClientQueryable());
        return this;
    }

    public LQuery<T> unionAll(LQuery<T> q1, LQuery<T> q2, LQuery<T> q3)
    {
        clientQueryable.unionAll(q1.getClientQueryable(), q2.getClientQueryable(), q3.getClientQueryable());
        return this;
    }

    public LQuery<T> unionAll(Collection<LQuery<T>> qs)
    {
        List<ClientQueryable<T>> clientQueryable = new ArrayList<>();
        for (LQuery<T> q : qs)
        {
            clientQueryable.add(q.getClientQueryable());
        }
        this.clientQueryable.unionAll(clientQueryable);
        return this;
    }

    // endregion

    //region [OTHER]

    public LQuery<T> asTable(String tableName)
    {
        clientQueryable.asTable(tableName);
        return this;
    }

    public LQuery<T> asTable(Function<String, String> tableNameAs)
    {
        clientQueryable.asTable(tableNameAs);
        return this;
    }

    public LQuery<T> asTracking()
    {
        clientQueryable.asTracking();
        return this;
    }

    public LQuery<T> asNoTracking()
    {
        clientQueryable.asNoTracking();
        return this;
    }

    public LQuery<T> enableLogicDelete()
    {
        clientQueryable.enableLogicDelete();
        return this;
    }

    public LQuery<T> disableLogicDelete()
    {
        clientQueryable.disableLogicDelete();
        return this;
    }

    public LQuery<T> useInterceptor()
    {
        clientQueryable.useInterceptor();
        return this;
    }

    public LQuery<T> useInterceptor(String name)
    {
        clientQueryable.useInterceptor(name);
        return this;
    }

    public LQuery<T> noInterceptor()
    {
        clientQueryable.noInterceptor();
        return this;
    }

    public LQuery<T> noInterceptor(String name)
    {
        clientQueryable.noInterceptor(name);
        return this;
    }

    public LQuery<T> distinct()
    {
        clientQueryable.distinct();
        return this;
    }

    public LQuery<T> distinct(boolean condition)
    {
        clientQueryable.distinct(condition);
        return this;
    }

    public boolean any()
    {
        return clientQueryable.any();
    }

    public void required()
    {
        clientQueryable.required();
    }

    public void required(String msg)
    {
        clientQueryable.required(msg);
    }

    public void required(String msg, String code)
    {
        clientQueryable.required(msg, code);
    }

    public void required(Supplier<RuntimeException> throwFunc)
    {
        clientQueryable.required(throwFunc);
    }

    public T firstOrNull()
    {
        return clientQueryable.firstOrNull();
    }

    public <R> R firstOrNull(Class<R> r)
    {
        return clientQueryable.firstOrNull(r);
    }

    public T firstNotNull()
    {
        return clientQueryable.firstNotNull();
    }

    public T firstNotNull(String msg)
    {
        return clientQueryable.firstNotNull(msg);
    }

    public T firstNotNull(String msg, String code)
    {
        return clientQueryable.firstNotNull(msg, code);
    }

    public T firstNotNull(Supplier<RuntimeException> throwFunc)
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

    public List<T> toList()
    {
        return clientQueryable.toList();
    }

    public <R> List<R> toList(Func1<T, R> func)
    {
        List<R> rList = new ArrayList<>();
        for (T t : toList())
        {
            rList.add(func.invoke(t));
        }
        return rList;
    }

    public Map<String, Object> toMap()
    {
        return clientQueryable.toMap();
    }

    public List<Map<String, Object>> toMaps()
    {
        return clientQueryable.toMaps();
    }

//    public EasyPageResult<T> toPageResult(long pageIndex, long pageSize)
//    {
//        return clientQueryable.toPageResult(pageIndex, pageSize);
//    }
//
//    public EasyPageResult<T> toPageResult(long pageIndex, long pageSize, long pageTotal)
//    {
//        return clientQueryable.toPageResult(pageIndex, pageSize, pageTotal);
//    }
//
//    public <TPageResult> TPageResult toPageResult(Pager<T,TPageResult> pager)
//    {
//        return clientQueryable.toPageResult(pager);
//    }

    // endregion
}
