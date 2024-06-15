package com.easy.query.core.lambda.condition.limit;


import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.lambda.condition.criteria.Criteria;

public class Limit extends Criteria
{
    private final long limit;
    private final long offset;

    public Limit(long limit)
    {
        this.limit = limit;
        offset = -1;
    }

    public Limit(long offset, long limit)
    {
        this.limit = limit;
        this.offset = offset;
    }

    public long getLimit()
    {
        return limit;
    }

    public void analysis(ClientQueryable<?> queryable, QueryData queryData)
    {
        if (offset > -1)
        {
            queryable.limit(offset, limit);
        }
        else
        {
            queryable.limit(limit);
        }
    }
}
