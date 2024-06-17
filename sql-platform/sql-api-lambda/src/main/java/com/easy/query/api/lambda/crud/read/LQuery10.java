package com.easy.query.api.lambda.crud.read;

import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable9;

public class LQuery10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends QueryBase
{
    protected final ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> clientQueryable;

    public ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> getClientQueryable()
    {
        return clientQueryable;
    }

    public LQuery10(ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> clientQueryable, DbType dbType)
    {
        super(new QueryData(dbType));
        this.clientQueryable = clientQueryable;
    }
}
