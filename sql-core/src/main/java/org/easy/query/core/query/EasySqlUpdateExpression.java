package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: EasySqlUpdateExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 17:05
 * @Created by xuejiaming
 */
public abstract class EasySqlUpdateExpression implements SqlEntityUpdateExpression{

    private final SqlExpressionContext queryExpressionContext;
    private  SqlBuilderSegment setColumns;
    private  PredicateSegment where;
    private SqlBuilderSegment setIgnoreColumns;
    private SqlBuilderSegment whereColumns;
    private final List<SqlEntityTableExpression> tables;
    public EasySqlUpdateExpression(SqlExpressionContext queryExpressionContext) {
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
    @Override
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
    public SqlBuilderSegment getSetColumns() {
        if(setColumns==null){
            setColumns=new UpdateSetSqlBuilderSegment();
        }
        return setColumns;
    }

    @Override
    public boolean hasSetColumns() {
        return setColumns!=null&&setColumns.isNotEmpty();
    }

    @Override
    public boolean hasWhere() {
        return where!=null&&where.isNotEmpty();
    }

    @Override
    public PredicateSegment getWhere() {
        if(where==null)
        {
            where=new AndPredicateSegment(true);
        }
        return where;
    }

    @Override
    public SqlBuilderSegment getSetIgnoreColumns() {
        if(setIgnoreColumns==null){
            setIgnoreColumns=new UpdateSetSqlBuilderSegment();
        }
        return setIgnoreColumns;
    }

    @Override
    public boolean hasSetIgnoreColumns() {
        return setIgnoreColumns!=null&&setIgnoreColumns.isNotEmpty();
    }


    @Override
    public SqlBuilderSegment getWhereColumns() {
        if(whereColumns==null){
            whereColumns=new ProjectSqlBuilderSegment();
        }
        return whereColumns;
    }
    @Override
    public boolean hasWhereColumns() {
        return whereColumns!=null&&whereColumns.isNotEmpty();
    }
}
