package com.easy.query.api.lambda.crud.read;

import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable9;

public class LQuery9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends QueryBase
{
    protected final ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> clientQueryable;

    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> getClientQueryable()
    {
        return clientQueryable;
    }

    public LQuery9(ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> clientQueryable, DbType dbType)
    {
        super(new QueryData(dbType));
        this.clientQueryable = clientQueryable;
    }
}
