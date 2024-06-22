package com.easy.query.core.lambda.visitor.context;

public class SqlValueContext extends SqlContext
{
    private final Object value;

    public SqlValueContext(Object value)
    {
        this.value = value;
    }

    public Object getValue()
    {
        return value;
    }
}
