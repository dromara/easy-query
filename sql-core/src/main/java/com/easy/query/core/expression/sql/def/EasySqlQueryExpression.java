package com.easy.query.core.expression.sql.def;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.builder.GroupBySqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.internal.AbstractSqlPredicateEntityExpression;
import com.easy.query.core.expression.sql.AnonymousEntityTableExpression;
import com.easy.query.core.expression.sql.SqlEntityQueryExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.expression.sql.SqlExpressionContext;
import com.easy.query.core.util.StringUtil;

import java.util.Iterator;

/**
 * @FileName: EasySqlExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 22:10
 * @author xuejiaming
 */
public abstract class EasySqlQueryExpression extends AbstractSqlPredicateEntityExpression implements SqlEntityQueryExpression {

    protected PredicateSegment where;
    protected SqlBuilderSegment group;
    protected PredicateSegment having;
    protected SqlBuilderSegment order;
    protected long offset;
    protected long rows;
    protected boolean distinct;

    protected final SqlBuilderSegment projects;

    public EasySqlQueryExpression(SqlExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
        this.projects = new ProjectSqlBuilderSegment();
    }

    @Override
    public boolean isEmpty() {
        return tables.isEmpty();
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
    public boolean hasLimit() {
        return this.rows > 0;
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
    public boolean isDistinct() {
        return distinct;
    }

    @Override
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public String toSql() {
        sqlExpressionContext.clearParameters();
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        String select = getProjects().toSql();
        Iterator<SqlEntityTableExpression> iterator = getTables().iterator();
        SqlEntityTableExpression firstTable = iterator.next();
        if (tableCount == 1 && firstTable instanceof AnonymousEntityTableExpression && StringUtil.isEmpty(select)) {
            return toTableExpressionSql(firstTable, true);
        }
        StringBuilder sql = new StringBuilder("SELECT ");
        if(isDistinct()){
            sql.append("DISTINCT ");
        }

        if (StringUtil.isEmpty(select)) {
            if (!hasGroup()) {
                sql.append(firstTable.getAlias()).append(".*");
            } else {
                sql.append(getGroup().toSql());
            }
        } else {
            sql.append(select);
        }
        sql.append(toTableExpressionSql(firstTable, false));
        while (iterator.hasNext()) {
            SqlEntityTableExpression table = iterator.next();
            sql.append(toTableExpressionSql(table, false));// [from table alias] | [left join table alias] 匿名表 应该使用  [left join (table) alias]

            PredicateSegment on = getTableOnWithQueryFilter(table);
            if (on != null && on.isNotEmpty()) {
                sql.append(" ON ").append(on.toSql());
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

    protected String toTableExpressionSql(SqlEntityTableExpression sqlEntityTableExpression, boolean onlySingleAnonymousTable) {
        if (sqlEntityTableExpression instanceof AnonymousEntityTableExpression) {

            SqlEntityQueryExpression sqlEntityQueryExpression = ((AnonymousEntityTableExpression) sqlEntityTableExpression).getSqlEntityQueryExpression();
            //如果只有单匿名表且未对齐select那么嵌套表需要被展开
            //todo 如果对其进行order 或者 where了呢怎么办
            String s = onlySingleAnonymousTable ? sqlEntityQueryExpression.toSql() : sqlEntityTableExpression.toSql();
            sqlExpressionContext.extractParameters(sqlEntityQueryExpression.getSqlExpressionContext());
            return s;
        }
        return sqlEntityTableExpression.toSql();
    }

    protected PredicateSegment getTableOnWithQueryFilter(SqlEntityTableExpression table) {
        return sqlPredicateFilter(table, table.hasOn() ? table.getOn() : null);
    }

    protected PredicateSegment getSqlWhereWithQueryFilter() {
        SqlEntityTableExpression table = getTable(0);
        return sqlPredicateFilter(table, hasWhere() ? getWhere() : null);
    }


}
