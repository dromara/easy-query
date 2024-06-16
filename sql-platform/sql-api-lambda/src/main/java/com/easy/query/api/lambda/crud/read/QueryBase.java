package com.easy.query.api.lambda.crud.read;


public abstract class QueryBase
{
    protected final QueryData queryData;

    public QueryBase(QueryData queryData)
    {
        this.queryData = queryData;
    }
}
