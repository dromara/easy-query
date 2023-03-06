package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractSqlEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:58
 * @Created by xuejiaming
 */
public abstract class AbstractSqlEntityExpression implements SqlEntityExpression {
    protected final SqlExpressionContext queryExpressionContext;
    protected final List<SqlEntityTableExpression> tables;

    public AbstractSqlEntityExpression(SqlExpressionContext queryExpressionContext){
        this.queryExpressionContext = queryExpressionContext;
        this.tables = new ArrayList<>();
    }

    @Override
    public SqlExpressionContext getSqlExpressionContext() {
        return queryExpressionContext;
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return queryExpressionContext.getRuntimeContext();
    }

    @Override
    public void addSqlEntityTableExpression(SqlEntityTableExpression tableExpression) {
        tables.add(tableExpression);
    }

    @Override
    public List<SqlEntityTableExpression> getTables() {
        return tables;
    }

    @Override
    public SqlEntityTableExpression getTable(int index) {
        return tables.get(index);
    }

    public String getQuoteName(String value) {
        return queryExpressionContext.getQuoteName(value);
    }

    @Override
    public String getSqlOwnerColumn(SqlEntityTableExpression table, String propertyName) {
        String alias = table.getAlias();
        String columnName = table.getColumnName(propertyName);
        String quoteName = getQuoteName(columnName);
        if (alias == null) {
            return quoteName;
        } else {
            return alias + "." + quoteName;
        }
    }
    @Override
    public List<SQLParameter> getParameters() {
        return queryExpressionContext.getParameters();
    }

    @Override
    public void addParameter(SQLParameter parameter) {
        queryExpressionContext.addParameter(parameter);
    }
}
