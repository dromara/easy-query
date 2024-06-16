package com.easy.query.api.lambda.crud.update;

import com.easy.query.api.lambda.crud.read.QueryBase;
import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.lambda.condition.set.Set;
import com.easy.query.core.lambda.condition.where.Where;
import io.github.kiryu1223.expressionTree.delegate.Action1;
import io.github.kiryu1223.expressionTree.delegate.Func1;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

public class LUpdate<T> extends QueryBase
{
    private final ClientExpressionUpdatable<T> updatable;

    public LUpdate(ClientExpressionUpdatable<T> updatable, DbType dbType)
    {
        super(new QueryData(dbType));
        this.updatable = updatable;
    }

    public LUpdate<T> set(@Expr Action1<T> expr)
    {
        throw new RuntimeException();
    }

    public LUpdate<T> set(ExprTree<Action1<T>> expr)
    {
        Set set = new Set(expr.getTree());
        set.analysis(updatable, queryData);
        return this;
    }

    public LUpdate<T> where(@Expr Func1<T, Boolean> func)
    {
        throw new RuntimeException();
    }

    public LUpdate<T> where(ExprTree<Func1<T, Boolean>> expr)
    {
        Where where = new Where(expr.getTree());
        where.analysis(updatable, queryData);
        return this;
    }

    public String toSql()
    {
        return updatable.toSQL();
    }

    public long executeRows()
    {
        return updatable.executeRows();
    }
}
