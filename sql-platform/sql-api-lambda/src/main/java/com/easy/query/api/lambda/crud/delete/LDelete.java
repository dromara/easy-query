package com.easy.query.api.lambda.crud.delete;

import com.easy.query.api.lambda.crud.read.QueryBase;
import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.lambda.condition.where.Where;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

public class LDelete<T> extends QueryBase
{
    private final ClientExpressionDeletable<T> deletable;

    public LDelete(ClientExpressionDeletable<T> deletable, DbType dbType)
    {
        super(new QueryData(dbType));
        this.deletable = deletable;
    }

    public LDelete<T> where(@Expr Func1<T, Boolean> func)
    {
        throw new RuntimeException();
    }

    public LDelete<T> where(ExprTree<Func1<T, Boolean>> expr)
    {
        Where where = new Where(expr.getTree());
        where.analysis(deletable, queryData);
        return this;
    }

    public String toSql()
    {
        return deletable.toSQL();
    }

    public long executeRows()
    {
        return deletable.executeRows();
    }
}
