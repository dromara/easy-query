package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.TableContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/4/23 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultToSQLContext implements ToSQLContext {
    private final List<SQLParameter> parameters;
    private final SQLRewriteUnit sqlRewriteUnit;
    private final String alias;
    private final TableContext tableContext;
    private int invokeCount;

    public DefaultToSQLContext(TableContext tableContext, String alias) {
        this(tableContext, 10, null, alias);
    }

    public DefaultToSQLContext(TableContext tableContext) {
        this(tableContext, "t");
    }

    public DefaultToSQLContext(TableContext tableContext, SQLRewriteUnit sqlRewriteUnit) {
        this(tableContext, 10, sqlRewriteUnit, "t");
    }

    public DefaultToSQLContext(TableContext tableContext, int initialCapacity, SQLRewriteUnit sqlRewriteUnit, String alias) {
        if (tableContext.isEmpty()) {
            throw new IllegalArgumentException("invalid arguments:tableContext is empty");
        }
        this.tableContext = tableContext;
        this.parameters = new ArrayList<>(initialCapacity);
        this.sqlRewriteUnit = sqlRewriteUnit;
        this.alias = alias;
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
        return tableContext.getTableAlias(table,alias);
    }
    private String createAlias(int aliasSeq){
        if(aliasSeq==0){
            return alias;
        }
        return alias+aliasSeq;
    }

    public static ToSQLContext defaultToSQLContext(TableContext tableContext) {
        return defaultToSQLContext(tableContext, null);
    }
    public static ToSQLContext defaultToSQLContext(TableContext tableContext, SQLRewriteUnit sqlRewriteUnit) {
        return new DefaultToSQLContext(tableContext, sqlRewriteUnit);
    }
}
