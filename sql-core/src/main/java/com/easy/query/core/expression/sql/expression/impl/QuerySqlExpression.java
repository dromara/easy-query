package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.util.SqlExpressionUtil;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/4/22 20:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class QuerySqlExpression implements EasyQuerySqlExpression {

    protected SqlBuilderSegment projects;
    protected PredicateSegment where;
    protected PredicateSegment allPredicate;
    protected SqlBuilderSegment group;
    protected PredicateSegment having;
    protected SqlBuilderSegment order;
    protected long offset;
    protected long rows;
    protected boolean distinct;
    protected final List<EasyTableSqlExpression> tables=new ArrayList<>();
    protected final EasyQueryRuntimeContext runtimeContext;

    public QuerySqlExpression(EasyQueryRuntimeContext runtimeContext) {
        this.runtimeContext = runtimeContext;
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public SqlBuilderSegment getProjects() {
        return projects;
    }

    @Override
    public void setProjects(SqlBuilderSegment projects) {
        this.projects = projects;
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }

    @Override
    public void setWhere(PredicateSegment where) {
        this.where = where;
    }

    @Override
    public SqlBuilderSegment getGroup() {
        return group;
    }

    @Override
    public void setGroup(SqlBuilderSegment group) {
        this.group = group;
    }

    @Override
    public PredicateSegment getHaving() {
        return having;
    }

    @Override
    public void setHaving(PredicateSegment having) {
        this.having = having;
    }

    @Override
    public SqlBuilderSegment getOrder() {
        return order;
    }

    @Override
    public void setOrder(SqlBuilderSegment order) {
        this.order = order;
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
        return this.rows>0;
    }

    @Override
    public void setAllPredicate(PredicateSegment allPredicate) {
        this.allPredicate=allPredicate;
    }

    @Override
    public PredicateSegment getAllPredicate() {
        return allPredicate;
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
    public List<EasyTableSqlExpression> getTables() {
        return tables;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        boolean root = SqlExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
        StringBuilder sql = new StringBuilder("SELECT ");
        if(this.distinct){
            sql.append("DISTINCT ");
        }

        sql.append(this.projects.toSql(sqlParameterCollector));

        Iterator<EasyTableSqlExpression> iterator = getTables().iterator();
        EasyTableSqlExpression firstTable = iterator.next();
        sql.append(firstTable.toSql(sqlParameterCollector));
        while (iterator.hasNext()) {
            EasyTableSqlExpression table = iterator.next();
            sql.append(table.toSql(sqlParameterCollector));// [from table alias] | [left join table alias] 匿名表 应该使用  [left join (table) alias]

            PredicateSegment on = table.getOn();
            if (on != null && on.isNotEmpty()) {
                sql.append(" ON ").append(on.toSql(sqlParameterCollector));
            }
        }
        boolean notExistsSql = SqlSegmentUtil.isNotEmpty(this.allPredicate);
        boolean hasWhere = SqlSegmentUtil.isNotEmpty(this.where);
        if (hasWhere) {
            String whereSql = this.where.toSql(sqlParameterCollector);
            if(root&&notExistsSql){
                sql.append(" WHERE ").append("( ").append(whereSql).append(" )");
            }else{
                sql.append(" WHERE ").append(whereSql);
            }
        }
        boolean onlyWhere=true;
        if (this.group!=null&&this.group.isNotEmpty()) {
            onlyWhere=false;
            sql.append(" GROUP BY ").append(this.group.toSql(sqlParameterCollector));
        }
        if (this.having!=null&&this.having.isNotEmpty()) {
            onlyWhere=false;
            sql.append(" HAVING ").append(this.having.toSql(sqlParameterCollector));
        }
        if (this.order!=null&&this.order.isNotEmpty()) {
            onlyWhere=false;
            sql.append(" ORDER BY ").append(this.order.toSql(sqlParameterCollector));
        }
        if (this.rows > 0) {
            onlyWhere=false;
            sql.append(" LIMIT ");
            if (this.offset > 0) {
                sql.append(this.rows).append(" OFFSET ").append(this.offset);
            } else {
                sql.append(this.rows);
            }
        }
        String resultSql = sql.toString();
        if(root&&notExistsSql){
            StringBuilder notExistsResultSql = new StringBuilder("SELECT NOT EXISTS ( ");
            if(onlyWhere){

                notExistsResultSql.append(resultSql).append(hasWhere?" AND ":" WHERE ").append("( ").append(allPredicate.toSql(sqlParameterCollector))
                        .append(" )").append(" )");
            }else{
                notExistsResultSql.append("SELECT 1 FROM ( ").append(resultSql).append(" ) t ").append(" WHERE ").append(allPredicate.toSql(sqlParameterCollector))
                        .append(" )");
            }
            return notExistsResultSql.toString();
        }else{
            return resultSql;
        }
    }

    @Override
    public EasyQuerySqlExpression cloneSqlExpression() {

        EasyExpressionFactory expressionFactory = getRuntimeContext().getExpressionFactory();
        EasyQuerySqlExpression easyQuerySqlExpression = expressionFactory.createEasyQuerySqlExpression(getRuntimeContext());

        if(SqlSegmentUtil.isNotEmpty(this.where)){
            easyQuerySqlExpression.setWhere(where.clonePredicateSegment());
        }
        if(SqlSegmentUtil.isNotEmpty(this.group)){
            easyQuerySqlExpression.setGroup(group.cloneSqlBuilder());
        }
        if(SqlSegmentUtil.isNotEmpty(this.having)){
            easyQuerySqlExpression.setHaving(having.clonePredicateSegment());
        }
        if(SqlSegmentUtil.isNotEmpty(this.order)){
            easyQuerySqlExpression.setOrder(order.cloneSqlBuilder());
        }
        if(SqlSegmentUtil.isNotEmpty(this.projects)){
            easyQuerySqlExpression.setProjects(projects.cloneSqlBuilder());
        }
        if(SqlSegmentUtil.isNotEmpty(this.allPredicate)){
            easyQuerySqlExpression.setAllPredicate(allPredicate.clonePredicateSegment());
        }
        easyQuerySqlExpression.setOffset(this.offset);
        easyQuerySqlExpression.setRows(this.rows);
        for (EasyTableSqlExpression table : this.tables) {
            easyQuerySqlExpression.getTables().add(table.cloneSqlExpression());
        }
        return easyQuerySqlExpression;
    }
}
