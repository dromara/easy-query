package com.easy.query.api.lambda.client;

import com.easy.query.api.lambda.crud.create.LInsert;
import com.easy.query.api.lambda.crud.delete.LDelete;
import com.easy.query.api.lambda.crud.read.*;
import com.easy.query.api.lambda.crud.update.LUpdate;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.lambda.common.UnsafeFastBean;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.h2.expression.H2ExpressionFactory;
import com.easy.query.mysql.expression.MySQLQuerySQLExpression;
import com.easy.query.oracle.expression.OracleExpressionFactory;

import java.lang.invoke.MethodHandles;

public class EasyLambdaQueryClient
{
    private final EasyQueryClient easyQueryClient;
    private final DbType dbType;

    public EasyQueryClient getEasyQueryClient()
    {
        return easyQueryClient;
    }

    public DbType getDbType()
    {
        return dbType;
    }

    public EasyLambdaQueryClient(EasyQueryClient easyQueryClient, MethodHandles.Lookup lookup)
    {
        this.easyQueryClient = easyQueryClient;
        this.dbType = getDbTypeByEasyQueryClient(easyQueryClient);
        unsafeFastBean(lookup);
    }

    private void unsafeFastBean(MethodHandles.Lookup lookup)
    {
        EasyBeanUtil.FAST_BEAN_FUNCTION = (c) -> new UnsafeFastBean(c, lookup);
    }

    private DbType getDbTypeByEasyQueryClient(EasyQueryClient easyQueryClient)
    {
        ExpressionFactory service = easyQueryClient.getRuntimeContext().getService(ExpressionFactory.class);
        if (service instanceof MySQLQuerySQLExpression)
        {
            return DbType.MySQL;
        }
        else if (service instanceof OracleExpressionFactory)
        {
            return DbType.Oracle;
        }
        else if (service instanceof H2ExpressionFactory)
        {
            return DbType.H2;
        }
        else
        {
            return DbType.MySQL;
        }
    }

    public Transaction beginTransaction()
    {
        return easyQueryClient.beginTransaction();
    }

    public Transaction beginTransaction(int isolationLevel)
    {
        return easyQueryClient.beginTransaction(isolationLevel);
    }

    public <T> LInsert<T> insertable(T entity)
    {
        return new LInsert<>(easyQueryClient.insertable(entity), dbType);
    }

    public <T> LDelete<T> deletable(Class<T> c)
    {
        return new LDelete<>(easyQueryClient.deletable(c), dbType);
    }

    public <T> LUpdate<T> updatable(Class<T> c)
    {
        return new LUpdate<>(easyQueryClient.updatable(c), dbType);
    }

    public <T> LQuery<T> queryable(Class<T> c)
    {
        return new LQuery<>(easyQueryClient.queryable(c), dbType);
    }

    public <T1, T2> LQuery2<T1, T2> queryable(Class<T1> c1, Class<T2> c2)
    {
        return new LQuery2<>(easyQueryClient.queryable(c1).from(c2), dbType);
    }

    public <T1, T2, T3> LQuery3<T1, T2, T3> queryable(Class<T1> c1, Class<T2> c2, Class<T3> c3)
    {
        return new LQuery3<>(easyQueryClient.queryable(c1).from(c2, c3), dbType);
    }

    public <T1, T2, T3, T4> LQuery4<T1, T2, T3, T4> queryable(Class<T1> c1, Class<T2> c2, Class<T3> c3, Class<T4> c4)
    {
        return new LQuery4<>(easyQueryClient.queryable(c1).from(c2, c3, c4), dbType);
    }

    public <T1, T2, T3, T4, T5> LQuery5<T1, T2, T3, T4, T5> queryable(Class<T1> c1, Class<T2> c2, Class<T3> c3, Class<T4> c4, Class<T5> c5)
    {
        return new LQuery5<>(easyQueryClient.queryable(c1).from(c2, c3, c4, c5), dbType);
    }

    public <T1, T2, T3, T4, T5, T6> LQuery6<T1, T2, T3, T4, T5, T6> queryable(Class<T1> c1, Class<T2> c2, Class<T3> c3, Class<T4> c4, Class<T5> c5, Class<T6> c6)
    {
        return new LQuery6<>(easyQueryClient.queryable(c1).from(c2, c3, c4, c5, c6), dbType);
    }

    public <T1, T2, T3, T4, T5, T6, T7> LQuery7<T1, T2, T3, T4, T5, T6, T7> queryable(Class<T1> c1, Class<T2> c2, Class<T3> c3, Class<T4> c4, Class<T5> c5, Class<T6> c6, Class<T7> c7)
    {
        return new LQuery7<>(easyQueryClient.queryable(c1).from(c2, c3, c4, c5, c6, c7), dbType);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> LQuery8<T1, T2, T3, T4, T5, T6, T7, T8> queryable(Class<T1> c1, Class<T2> c2, Class<T3> c3, Class<T4> c4, Class<T5> c5, Class<T6> c6, Class<T7> c7, Class<T8> c8)
    {
        return new LQuery8<>(easyQueryClient.queryable(c1).from(c2, c3, c4, c5, c6, c7, c8), dbType);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> LQuery9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryable(Class<T1> c1, Class<T2> c2, Class<T3> c3, Class<T4> c4, Class<T5> c5, Class<T6> c6, Class<T7> c7, Class<T8> c8, Class<T9> c9)
    {
        return new LQuery9<>(easyQueryClient.queryable(c1).from(c2, c3, c4, c5, c6, c7, c8, c9), dbType);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> LQuery10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryable(Class<T1> c1, Class<T2> c2, Class<T3> c3, Class<T4> c4, Class<T5> c5, Class<T6> c6, Class<T7> c7, Class<T8> c8, Class<T9> c9, Class<T10> c10)
    {
        return new LQuery10<>(easyQueryClient.queryable(c1).from(c2, c3, c4, c5, c6, c7, c8, c9, c10), dbType);
    }
}
