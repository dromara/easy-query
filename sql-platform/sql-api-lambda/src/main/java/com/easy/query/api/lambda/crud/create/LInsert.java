package com.easy.query.api.lambda.crud.create;

import com.easy.query.api.lambda.crud.read.QueryBase;
import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.insert.ClientInsertable;

public class LInsert<T> extends QueryBase
{
    public final ClientInsertable<T> clientInsertable;

    public LInsert(ClientInsertable<T> clientInsertable, DbType dbType)
    {
        super(new QueryData(dbType));
        this.clientInsertable = clientInsertable;
    }

    public long executeRows()
    {
        return clientInsertable.executeRows();
    }
}
