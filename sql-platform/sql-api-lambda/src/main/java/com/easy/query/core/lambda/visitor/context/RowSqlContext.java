package com.easy.query.core.lambda.visitor.context;

import java.util.ArrayList;
import java.util.List;

public class RowSqlContext extends SqlContext
{
    private final StringBuilder rowSql = new StringBuilder();
    private final List<SqlContext> sqlContexts = new ArrayList<>(2);

    public StringBuilder getRowSql()
    {
        return rowSql;
    }

    public List<SqlContext> getSqlContexts()
    {
        return sqlContexts;
    }
}
