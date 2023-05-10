package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.builder.GroupBySqlBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.OrderBySqlBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.SqlEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.Iterator;

/**
 * @author xuejiaming
 * @FileName: EasySqlExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 22:10
 */
public class QueryExpressionBuilder extends AbstractPredicateEntityExpressionBuilder implements EntityQueryExpressionBuilder {

    protected PredicateSegment where;
    protected PredicateSegment allPredicate;
    protected SqlBuilderSegment group;
    protected PredicateSegment having;
    protected SqlBuilderSegment order;
    protected long offset;
    protected long rows;
    protected boolean distinct;

    protected final SqlBuilderSegment projects;

    public QueryExpressionBuilder(ExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
        this.projects = new ProjectSqlBuilderSegmentImpl();
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
    public boolean hasAllPredicate() {
        return SqlSegmentUtil.isNotEmpty(allPredicate);
    }

    @Override
    public PredicateSegment getAllPredicate() {
        if (allPredicate == null) {
            allPredicate = new AndPredicateSegment(true);
        }
        return allPredicate;
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
        return SqlSegmentUtil.isNotEmpty(having);
    }

    @Override
    public SqlBuilderSegment getGroup() {
        if (group == null) {
            group = new GroupBySqlBuilderSegmentImpl();
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
            order = new OrderBySqlBuilderSegmentImpl();
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
    public EasyQuerySqlExpression toExpression() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        boolean emptySelect = getProjects().isEmpty();
        Iterator<EntityTableExpressionBuilder> iterator = getTables().iterator();
        EntityTableExpressionBuilder firstTable = iterator.next();
        //如果有order需要将order移到内部表达式
        if (emptySelect && tableCount == 1 && (firstTable instanceof AnonymousEntityTableExpressionBuilder && !(((AnonymousEntityTableExpressionBuilder) firstTable).getEntityQueryExpressionBuilder() instanceof SqlEntityQueryExpressionBuilder))) {
            return (EasyQuerySqlExpression) toTableExpressionSql(firstTable, true);
        }
        EasyQueryRuntimeContext runtimeContext = getRuntimeContext();
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EasyQuerySqlExpression easyQuerySqlExpression = expressionFactory.createEasyQuerySqlExpression(runtimeContext,sqlExpressionContext.getExecuteMethod());
        easyQuerySqlExpression.setDistinct(isDistinct());

        if (emptySelect) {
            if (!hasGroup()) {
                ProjectSqlBuilderSegmentImpl projects = new ProjectSqlBuilderSegmentImpl();
                projects.append(new SelectConstSegment(firstTable.getAlias() + ".*"));
                easyQuerySqlExpression.setProjects(projects);
            } else {
                ProjectSqlBuilderSegmentImpl projects = new ProjectSqlBuilderSegmentImpl();
                getGroup().copyTo(projects);
                easyQuerySqlExpression.setProjects(projects);
            }
        } else {
            easyQuerySqlExpression.setProjects(getProjects());
        }
        easyQuerySqlExpression.getTables().add((EasyTableSqlExpression) toTableExpressionSql(firstTable, false));
        while (iterator.hasNext()) {
            EntityTableExpressionBuilder table = iterator.next();
            EasyTableSqlExpression tableExpression = (EasyTableSqlExpression) toTableExpressionSql(table, false);
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
        easyQuerySqlExpression.setGroup(getGroup());
        easyQuerySqlExpression.setHaving(getHaving());
        easyQuerySqlExpression.setOrder(getOrder());
        easyQuerySqlExpression.setOffset(getOffset());
        easyQuerySqlExpression.setRows(getRows());
        easyQuerySqlExpression.setAllPredicate(getAllPredicate());
        return easyQuerySqlExpression;
    }

    protected EasySqlExpression toTableExpressionSql(EntityTableExpressionBuilder entityTableExpressionBuilder, boolean onlySingleAnonymousTable) {
        if (entityTableExpressionBuilder instanceof AnonymousEntityTableExpressionBuilder) {

            EntityQueryExpressionBuilder sqlEntityQueryExpression = ((AnonymousEntityTableExpressionBuilder) entityTableExpressionBuilder).getEntityQueryExpressionBuilder();
            //如果只有单匿名表且未对齐select那么嵌套表需要被展开
            //todo 如果对其进行order 或者 where了呢怎么办
            return onlySingleAnonymousTable ? sqlEntityQueryExpression.toExpression() : entityTableExpressionBuilder.toExpression();
        }
        return entityTableExpressionBuilder.toExpression();
    }

    protected PredicateSegment getTableOnWithQueryFilter(EntityTableExpressionBuilder table) {
        return sqlPredicateFilter(table, table.hasOn() ? table.getOn() : null);
    }

    protected PredicateSegment getSqlWhereWithQueryFilter() {
        EntityTableExpressionBuilder table = getTable(0);
        return sqlPredicateFilter(table, hasWhere() ? getWhere() : null);
    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {

        ExpressionContext sqlExpressionContext = getExpressionContext();
        QueryExpressionBuilder queryExpressionBuilder = new QueryExpressionBuilder(sqlExpressionContext);
        if (hasWhere()) {
            getWhere().copyTo(queryExpressionBuilder.getWhere());
        }
        if (hasGroup()) {
            getGroup().copyTo(queryExpressionBuilder.getGroup());
        }
        if (hasHaving()) {
            getHaving().copyTo(queryExpressionBuilder.getHaving());
        }
        if (hasOrder()) {
            getOrder().copyTo(queryExpressionBuilder.getOrder());
        }
        if(hasAllPredicate()){
            getAllPredicate().copyTo(queryExpressionBuilder.getAllPredicate());
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
