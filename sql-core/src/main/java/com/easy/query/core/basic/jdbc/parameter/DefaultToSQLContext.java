package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.ToTableContext;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/23 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultToSQLContext implements ToSQLContext {
    private final List<SQLParameter> parameters;
    private final SQLRewriteUnit sqlRewriteUnit;
    private final ToTableContext toTableContext;
    private int invokeCount;

    public DefaultToSQLContext(TableContext tableContext,boolean query, String alias) {
        this(tableContext, 10, null,query, alias);
    }

    public DefaultToSQLContext(TableContext tableContext,boolean query) {
        this(tableContext,query, "t");
    }

    public DefaultToSQLContext(TableContext tableContext, SQLRewriteUnit sqlRewriteUnit,boolean query) {
        this(tableContext, 10, sqlRewriteUnit,query, "t");
    }

    public DefaultToSQLContext(TableContext tableContext, int initialCapacity, SQLRewriteUnit sqlRewriteUnit,boolean query, String alias) {
        if (tableContext.isEmpty()) {
            throw new IllegalArgumentException("invalid arguments:tableContext is empty");
        }
        this.toTableContext = tableContext.getToTableContext(alias,query);
        this.parameters = new ArrayList<>(initialCapacity);
        this.sqlRewriteUnit = sqlRewriteUnit;
        this.invokeCount = 0;
    }

    @Override
    public int expressionInvokeCountGetIncrement() {
        int oldInvokeCount = invokeCount;
        invokeCount++;
        return oldInvokeCount;
    }

    @Override
    public int currentInvokeCount() {
        return invokeCount;
    }

    @Override
    public void addParameter(SQLParameter sqlParameter) {
        parameters.add(sqlParameter);
    }

    @Override
    public List<SQLParameter> getParameters() {
        return parameters;
    }

    @Override
    public SQLRewriteUnit getSQLRewriteUnit() {
        return sqlRewriteUnit;
    }

    @Override
    public String getAlias(TableAvailable table) {
        return toTableContext.getAlias(table);
    }

    public static ToSQLContext defaultToSQLContext(TableContext tableContext,boolean query) {
        return defaultToSQLContext(tableContext, null,query);
    }
    public static ToSQLContext defaultToSQLContext(TableContext tableContext, SQLRewriteUnit sqlRewriteUnit,boolean query) {
        return new DefaultToSQLContext(tableContext, sqlRewriteUnit,query);
    }
}
