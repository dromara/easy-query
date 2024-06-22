package com.easy.query.api.lambda.crud.read;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.common.ToSQLResult;
import io.github.kiryu1223.expressionTree.delegate.Func1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EndQuery<T>
{
    private final Query<T> end;

    public EndQuery(Query<T> end)
    {
        this.end = end;
    }

    public EndQuery<T> distinct()
    {
        end.distinct();
        return this;
    }

    public EndQuery<T> distinct(boolean condition)
    {
        end.distinct(condition);
        return this;
    }

    public boolean any()
    {
        return end.any();
    }

    public void required()
    {
        end.required();
    }

    public void required(String msg)
    {
        end.required(msg);
    }

    public void required(String msg, String code)
    {
        end.required(msg, code);
    }

    public void required(Supplier<RuntimeException> throwFunc)
    {
        end.required(throwFunc);
    }

    public T firstOrNull()
    {
        return end.firstOrNull();
    }

    public <R> R firstOrNull(Class<R> r)
    {
        return end.firstOrNull(r);
    }

    public T firstNotNull()
    {
        return end.firstNotNull();
    }

    public T firstNotNull(String msg)
    {
        return end.firstNotNull(msg);
    }

    public T firstNotNull(String msg, String code)
    {
        return end.firstNotNull(msg, code);
    }

    public T firstNotNull(Supplier<RuntimeException> throwFunc)
    {
        return end.firstNotNull(throwFunc);
    }

    public <R> R firstNotNull(Class<R> r)
    {
        return end.firstNotNull(r);
    }

    public <R> R firstNotNull(Class<R> r, String msg)
    {
        return end.firstNotNull(r, msg);
    }

    public <R> R firstNotNull(Class<R> r, String msg, String code)
    {
        return end.firstNotNull(r, msg, code);
    }

    public String toSQL()
    {
        return end.toSQL();
    }

    public ToSQLResult toSQLResult()
    {
        return end.toSQLResult();
    }

    public List<T> toList()
    {
        return end.toList();
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
        return end.toMap();
    }

    public List<Map<String, Object>> toMaps()
    {
        return end.toMaps();
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
