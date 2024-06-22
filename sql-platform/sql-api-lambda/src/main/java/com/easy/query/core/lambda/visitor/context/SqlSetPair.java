package com.easy.query.core.lambda.visitor.context;

import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.func.SQLFunc;

public class SqlSetPair
{
    private String property;
    private SqlContext right;

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public SqlContext getRight()
    {
        return right;
    }

    public void setRight(SqlContext right)
    {
        this.right = right;
    }

    public void updatable(ClientExpressionUpdatable<?> client)
    {
        SQLFunc fx = client.getExpressionContext().getRuntimeContext().fx();
        if(right instanceof SqlPropertyContext)
        {
            SqlPropertyContext sqlPropertyContext = (SqlPropertyContext) right;
            client.setWithColumn(property,sqlPropertyContext.getProperty());
        }
        else if (right instanceof SqlValueContext)
        {
            SqlValueContext sqlValueContext = (SqlValueContext) right;
            client.set(property,sqlValueContext.getValue());
        }
        else if (right instanceof SqlFuncContext)
        {
            SqlFuncContext sqlFuncContext = (SqlFuncContext) right;
            client.setSQLFunction(property,sqlFuncContext.get(fx));
        }
    }
}
