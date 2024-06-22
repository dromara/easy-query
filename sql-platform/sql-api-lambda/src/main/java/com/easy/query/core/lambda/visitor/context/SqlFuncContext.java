package com.easy.query.core.lambda.visitor.context;

import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;

import java.lang.reflect.Method;

public class SqlFuncContext extends SqlContext
{
    private final Method method;
    private SqlContext context;

    public SqlFuncContext(Method method)
    {
        this.method = method;
    }

    public void setContext(SqlContext context)
    {
        this.context = context;
    }

    public SQLFunction get(SQLFunc fx)
    {
        if (context instanceof SqlPropertyContext)
        {
            SqlPropertyContext sqlPropertyContext = (SqlPropertyContext) context;
            switch (method.getName())
            {

            }
        }
        return null;
    }
}
