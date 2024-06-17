package com.easy.query.api.lambda.crud.read;

import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.select.ClientQueryable8;

public class LQuery8<T1, T2, T3, T4, T5, T6, T7, T8> extends QueryBase
{
    protected final ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable;

    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> getClientQueryable()
    {
        return clientQueryable;
    }

    public LQuery8(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable, DbType dbType)
    {
        super(new QueryData(dbType));
        this.clientQueryable = clientQueryable;
    }
}
