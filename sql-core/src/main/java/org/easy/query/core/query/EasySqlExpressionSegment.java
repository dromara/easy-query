package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryLambdaFactory;
import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.context.SelectContext;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.segment.builder.GroupBySqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.OrderBySqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: EasySqlExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 22:10
 * @Created by xuejiaming
 */
public class EasySqlExpressionSegment implements SqlEntityExpressionSegment {

    private final QueryExpressionContext queryExpressionContext;
    private PredicateSegment where;
    private SqlBuilderSegment group;
    private PredicateSegment having;
    private SqlBuilderSegment order;
    private long offset;
    private long rows;

    private final SqlBuilderSegment projects;
    private final List<SqlEntityTableExpressionSegment> tables;

    public EasySqlExpressionSegment(QueryExpressionContext queryExpressionContext) {
        this.queryExpressionContext = queryExpressionContext;
        tables = new ArrayList<>();
        projects = new ProjectSqlBuilderSegment();
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
    public SqlEntityTableExpressionSegment getTable(int index) {
        return tables.get(index);
    }

    @Override
    public String getQuoteName(String value) {
        return queryExpressionContext.getQuoteName(value);
    }
 @Override
    public String getSqlColumnSegment(SqlEntityTableExpressionSegment table, String propertyName) {
        String alias = table.getAlias();
        String columnName = table.getColumnName(propertyName);
        String quoteName = getQuoteName(columnName);
        if(alias==null){
            return quoteName;
        }else{
            return alias+"."+quoteName;
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
    public void addSqlEntityTableExpressionSegment(SqlEntityTableExpressionSegment tableSegment) {
        tables.add(tableSegment);
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

    @Override
    public String toSql() {

        //将条件参数清空
        if (!getParameters().isEmpty()) {
            getParameters().clear();
        }
        int tableCount = tables.size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        String select = projects.toSql();
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < tableCount; i++) {
            //采用table.toSql()
            SqlEntityTableExpressionSegment table = getTable(i);
            if (i == 0) {
                if (StringUtil.isEmpty(select)) {
                    if (!hasGroup()) {
                        sql.append(table.getAlias()).append(".*");
                    } else {
                        sql.append(getGroup().toSql());
                    }
                } else {
                    sql.append(select);
                }
            }

            sql.append(table.getSelectTableSource()).append(table.getEntityMetadata().getTableName());
            if (table.getAlias() != null) {
                sql.append(" ").append(table.getAlias());
            }

            if (i > 0) {
                PredicateSegment on = getTableOnWithQueryFilter(table);
                if (on != null && on.isNotEmpty()) {
                    sql.append(" ON ").append(on.toSql());
                }
            }
        }
        PredicateSegment where = getSqlWhereWithQueryFilter();
        if (where != null && where.isNotEmpty()) {
            sql.append(" WHERE ").append(where.toSql());
        }
        if (hasGroup()) {
            sql.append(" GROUP BY ").append(getGroup().toSql());
        }
        if (hasHaving()) {
            sql.append(" HAVING ").append(getHaving().toSql());
        }
        if (hasOrder()) {
            sql.append(" ORDER BY ").append(getOrder().toSql());
        }
        if (getRows() > 0) {
            sql.append(" LIMIT ");
            if (getOffset() > 0) {
                sql.append(getOffset()).append(" OFFSET ").append(getRows());
            } else {
                sql.append(getRows());
            }
        }
        return sql.toString();
    }
    private  PredicateSegment getTableOnWithQueryFilter( SqlEntityTableExpressionSegment table) {
        return getSqlPredicateSegment(table, table.hasOn() ? table.getOn() : null);
    }

    private  PredicateSegment getSqlWhereWithQueryFilter() {
        SqlEntityTableExpressionSegment table = getTable(0);
        return getSqlPredicateSegment( table, hasWhere() ? getWhere() : null);
    }

    private  PredicateSegment getSqlPredicateSegment(SqlEntityTableExpressionSegment table, PredicateSegment originalPredicate) {
        PredicateSegment predicateSegment = null;
        SqlExpression<SqlPredicate<?>> queryFilterExpression = table.getQueryFilterExpression();
        if (queryFilterExpression != null) {
            predicateSegment = new AndPredicateSegment(true);
            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
            SqlPredicate<?> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), this, predicateSegment);
            queryFilterExpression.apply(sqlPredicate);
            if (originalPredicate != null && originalPredicate.isNotEmpty()) {
                predicateSegment.addPredicateSegment(originalPredicate);
            }
        } else {
            predicateSegment = originalPredicate;
        }
        return predicateSegment;
    }
}
