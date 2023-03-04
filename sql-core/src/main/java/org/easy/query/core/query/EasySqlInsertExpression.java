package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: EasySqlInsertExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:49
 * @Created by xuejiaming
 */
public abstract class EasySqlInsertExpression implements SqlEntityInsertExpression{
    private final SqlExpressionContext queryExpressionContext;
    private final SqlBuilderSegment columns;
    private final List<SqlEntityTableExpression> tables;
    public EasySqlInsertExpression(SqlExpressionContext queryExpressionContext) {
        this.queryExpressionContext = queryExpressionContext;
        this.tables = new ArrayList<>();
        this.columns=new ProjectSqlBuilderSegment();
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
    public void addSqlEntityTableExpression(SqlEntityTableExpression tableSegment) {
        tables.add(tableSegment);
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

    @Override
    public SqlBuilderSegment getColumns() {
        return columns;
    }
}
