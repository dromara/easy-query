package com.easy.query.api.lambda.crud.read;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.pagination.Pager;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.common.ToSQLResult;
import io.github.kiryu1223.expressionTree.delegate.Func1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class NoWayQuery<T>
{
    private final Query<T> stop;

    public NoWayQuery(Query<T> stop)
    {
        this.stop = stop;
    }

    public NoWayQuery<T> distinct()
    {
        stop.distinct();
        return this;
    }

    public NoWayQuery<T> distinct(boolean condition)
    {
        stop.distinct(condition);
        return this;
    }

    public boolean any()
    {
        return stop.any();
    }

    public void required()
    {
        stop.required();
    }

    public void required(String msg)
    {
        stop.required(msg);
    }

    public void required(String msg, String code)
    {
        stop.required(msg, code);
    }

    public void required(Supplier<RuntimeException> throwFunc)
    {
        stop.required(throwFunc);
    }

    public T firstOrNull()
    {
        return stop.firstOrNull();
    }

    public <R> R firstOrNull(Class<R> r)
    {
        return stop.firstOrNull(r);
    }

    public T firstNotNull()
    {
        return stop.firstNotNull();
    }

    public T firstNotNull(String msg)
    {
        return stop.firstNotNull(msg);
    }

    public T firstNotNull(String msg, String code)
    {
        return stop.firstNotNull(msg, code);
    }

    public T firstNotNull(Supplier<RuntimeException> throwFunc)
    {
        return stop.firstNotNull(throwFunc);
    }

    public <R> R firstNotNull(Class<R> r)
    {
        return stop.firstNotNull(r);
    }

    public <R> R firstNotNull(Class<R> r, String msg)
    {
        return stop.firstNotNull(r, msg);
    }

    public <R> R firstNotNull(Class<R> r, String msg, String code)
    {
        return stop.firstNotNull(r, msg, code);
    }

    public String toSQL()
    {
        return stop.toSQL();
    }

    public ToSQLResult toSQLResult()
    {
        return stop.toSQLResult();
    }

    public List<T> toList()
    {
        return stop.toList();
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
        return stop.toMap();
    }

    public List<Map<String, Object>> toMaps()
    {
        return stop.toMaps();
    }

//    public EasyPageResult<T> toPageResult(long pageIndex, long pageSize)
//    {
//        return stop.toPageResult(pageIndex, pageSize);
//    }
//
//    public EasyPageResult<T> toPageResult(long pageIndex, long pageSize, long pageTotal)
//    {
//        return stop.toPageResult(pageIndex, pageSize, pageTotal);
//    }
//
//    public <TPageResult> TPageResult toPageResult(Pager<T, TPageResult> pager)
//    {
//        return stop.toPageResult(pager);
//    }
}
