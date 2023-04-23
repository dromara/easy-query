package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.builder.GroupBySqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.SqlEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.QuerySqlExpression;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;

import java.util.Iterator;

/**
 * @FileName: EasySqlExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 22:10
 * @author xuejiaming
 */
public  class QueryExpressionBuilder extends AbstractPredicateEntityExpressionBuilder implements EntityQueryExpressionBuilder {

    protected PredicateSegment where;
    protected SqlBuilderSegment group;
    protected PredicateSegment having;
    protected SqlBuilderSegment order;
    protected long offset;
    protected long rows;
    protected boolean distinct;

    protected final SqlBuilderSegment projects;

    public QueryExpressionBuilder(ExpressionContext queryExpressionContext) {
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
    public boolean isExpression() {
        return true;
    }

    @Override
    public EasyQuerySqlExpression toExpression(){
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        boolean emptySelect = getProjects().isEmpty();
        Iterator<EntityTableExpressionBuilder> iterator = getTables().iterator();
        EntityTableExpressionBuilder firstTable = iterator.next();
        if (tableCount == 1 && (firstTable instanceof AnonymousEntityTableExpressionBuilder && !(((AnonymousEntityTableExpressionBuilder)firstTable).getEntityQueryExpressionBuilder() instanceof SqlEntityQueryExpressionBuilder)) && emptySelect) {
            return (EasyQuerySqlExpression)toTableExpressionSql(firstTable, true);
        }
        EasyQueryRuntimeContext runtimeContext = getRuntimeContext();
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EasyQuerySqlExpression easyQuerySqlExpression = expressionFactory.createEasyQuerySqlExpression(runtimeContext);
        easyQuerySqlExpression.setDistinct(isDistinct());

        if (emptySelect) {
            if (!hasGroup()) {
                ProjectSqlBuilderSegment projects = new ProjectSqlBuilderSegment();
                projects.append(new SelectConstSegment(firstTable.getAlias()+".*"));
                easyQuerySqlExpression.setProjects(projects);
            } else {
                ProjectSqlBuilderSegment projects = new ProjectSqlBuilderSegment();
                getGroup().copyTo(projects);
                easyQuerySqlExpression.setProjects(projects);
            }
        } else {
            easyQuerySqlExpression.setProjects(getProjects());
        }
        easyQuerySqlExpression.getTables().add((EasyTableSqlExpression)toTableExpressionSql(firstTable, false));
        while (iterator.hasNext()) {
            EntityTableExpressionBuilder table = iterator.next();
            EasyTableSqlExpression tableExpression = (EasyTableSqlExpression)toTableExpressionSql(table, false);
            easyQuerySqlExpression.getTables().add(tableExpression);
            PredicateSegment on = getTableOnWithQueryFilter(table);
            if (on != null && on.isNotEmpty()) {
                tableExpression.setOn(on);
            }
        }
        PredicateSegment where = getSqlWhereWithQueryFilter();
        if (where != null && where.isNotEmpty()) {
            easyQuerySqlExpression.setWhere(where);
        }
        if (hasGroup()) {
            easyQuerySqlExpression.setGroup(getGroup());
        }
        if (hasHaving()) {
            easyQuerySqlExpression.setHaving(getHaving());
        }
        if (hasOrder()) {
            easyQuerySqlExpression.setOrder(getOrder());
        }
        easyQuerySqlExpression.setOffset(getOffset());
        easyQuerySqlExpression.setRows(getRows());
        return easyQuerySqlExpression;
    }
    protected EasySqlExpression toTableExpressionSql(EntityTableExpressionBuilder sqlEntityTableExpression, boolean onlySingleAnonymousTable) {
        if (sqlEntityTableExpression instanceof AnonymousEntityTableExpressionBuilder) {

            EntityQueryExpressionBuilder sqlEntityQueryExpression = ((AnonymousEntityTableExpressionBuilder) sqlEntityTableExpression).getEntityQueryExpressionBuilder();
            //如果只有单匿名表且未对齐select那么嵌套表需要被展开
            //todo 如果对其进行order 或者 where了呢怎么办
            return onlySingleAnonymousTable ? sqlEntityQueryExpression.toExpression() : sqlEntityTableExpression.toExpression();
        }
        return sqlEntityTableExpression.toExpression();
    }

    protected PredicateSegment getTableOnWithQueryFilter(EntityTableExpressionBuilder table) {
        return sqlPredicateFilter(table, table.hasOn() ? table.getOn() : null);
    }

    protected PredicateSegment getSqlWhereWithQueryFilter() {
        EntityTableExpressionBuilder table = getTable(0);
        return sqlPredicateFilter(table, hasWhere() ? getWhere() : null);
    }


    @Override
    public EntityExpressionBuilder cloneEntityExpressionBuilder() {

        ExpressionContext sqlExpressionContext = getExpressionContext();
        QueryExpressionBuilder queryExpressionBuilder = new QueryExpressionBuilder(sqlExpressionContext);
       if(hasWhere()){
            getWhere().copyTo(queryExpressionBuilder.getWhere());
        }
        if(hasGroup()){
            getGroup().copyTo(queryExpressionBuilder.getGroup());
        }
        if(hasHaving()){
            getHaving().copyTo(queryExpressionBuilder.getHaving());
        }
        if(hasOrder()){
            getOrder().copyTo(queryExpressionBuilder.getOrder());
        }
        getProjects().copyTo(queryExpressionBuilder.getProjects());
        queryExpressionBuilder.offset = this.offset;
        queryExpressionBuilder.rows = this.rows;
        for (EntityTableExpressionBuilder table : super.tables) {
            queryExpressionBuilder.tables.add(table.copyEntityTableExpressionBuilder());
        }
        return queryExpressionBuilder;
    }
}
