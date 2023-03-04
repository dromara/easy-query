package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.EasySqlExpressionFactory;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.expression.segment.builder.GroupBySqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.OrderBySqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: EasySqlExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 22:10
 * @Created by xuejiaming
 */
public abstract class EasySqlQueryExpression implements SqlEntityQueryExpression {

    private final SqlExpressionContext queryExpressionContext;
    protected PredicateSegment where;
    protected SqlBuilderSegment group;
    protected PredicateSegment having;
    protected SqlBuilderSegment order;
    protected long offset;
    protected long rows;

    protected final SqlBuilderSegment projects;
    protected final List<SqlEntityTableExpression> tables;

    public EasySqlQueryExpression(SqlExpressionContext queryExpressionContext) {
        this.queryExpressionContext = queryExpressionContext;
        this.tables = new ArrayList<>();
        this.projects = new ProjectSqlBuilderSegment();
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return queryExpressionContext.getRuntimeContext();
    }


    @Override
    public boolean isEmpty() {
        return tables.isEmpty();
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
    public void addSqlEntityTableExpression(SqlEntityTableExpression tableSegment) {
        tables.add(tableSegment);
    }

    @Override
    public SqlExpressionContext getSqlExpressionContext() {
        return queryExpressionContext;
    }

    @Override
    public List<SqlEntityTableExpression> getTables() {
        return tables;
    }

    @Override
    public SqlBuilderSegment getProjects() {
        return projects;
    }

    @Override
    public PredicateSegment getWhere() {
        if (where == null) {
            where = new AndPredicateSegment(true);
        }
        return where;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Override
    public long getRows() {
        return rows;
    }

    @Override
    public void setRows(long rows) {
        this.rows = rows;
    }

    @Override
    public boolean hasWhere() {
        return where != null && where.isNotEmpty();
    }

    @Override
    public PredicateSegment getHaving() {
        if (having == null) {
            having = new AndPredicateSegment(true);
        }
        return having;
    }

    @Override
    public boolean hasHaving() {
        return having != null && having.isNotEmpty();
    }

    @Override
    public SqlBuilderSegment getGroup() {
        if (group == null) {
            group = new GroupBySqlBuilderSegment();
        }
        return group;
    }

    @Override
    public boolean hasGroup() {
        return group != null && group.isNotEmpty();
    }

    @Override
    public SqlBuilderSegment getOrder() {
        if (order == null) {
            order = new OrderBySqlBuilderSegment();
        }
        return order;
    }

    @Override
    public boolean hasOrder() {
        return order != null && order.isNotEmpty();
    }

}
