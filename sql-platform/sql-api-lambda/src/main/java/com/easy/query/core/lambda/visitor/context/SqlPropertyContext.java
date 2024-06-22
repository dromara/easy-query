package com.easy.query.core.lambda.visitor.context;

public class SqlPropertyContext extends SqlContext
{
    private final String property;

    public SqlPropertyContext(String property)
    {
        this.property = property;
    }

    public String getProperty()
    {
        return property;
    }
}
