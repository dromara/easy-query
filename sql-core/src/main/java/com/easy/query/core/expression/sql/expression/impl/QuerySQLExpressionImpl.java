package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.expression.sql.expression.QuerySQLExpression;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.util.SQLExpressionUtil;
import com.easy.query.core.util.SQLSegmentUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/4/22 20:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class QuerySQLExpressionImpl implements QuerySQLExpression {

    protected SQLBuilderSegment projects;
    protected PredicateSegment where;
    protected PredicateSegment allPredicate;
    protected SQLBuilderSegment group;
    protected PredicateSegment having;
    protected SQLBuilderSegment order;
    protected long offset;
    protected long rows;
    protected boolean distinct;
    protected final List<TableSQLExpression> tables=new ArrayList<>();
    protected final EasyQueryRuntimeContext runtimeContext;

    public QuerySQLExpressionImpl(EasyQueryRuntimeContext runtimeContext) {
        this.runtimeContext = runtimeContext;
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public SQLBuilderSegment getProjects() {
        return projects;
    }

    @Override
    public void setProjects(SQLBuilderSegment projects) {
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
    public SQLBuilderSegment getGroup() {
        return group;
    }

    @Override
    public void setGroup(SQLBuilderSegment group) {
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
    public SQLBuilderSegment getOrder() {
        return order;
    }

    @Override
    public void setOrder(SQLBuilderSegment order) {
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
    public List<TableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        boolean root = SQLExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
        StringBuilder sql = new StringBuilder("SELECT ");
        if(this.distinct){
            sql.append("DISTINCT ");
        }

        sql.append(this.projects.toSQL(sqlParameterCollector));

        Iterator<TableSQLExpression> iterator = getTables().iterator();
        TableSQLExpression firstTable = iterator.next();
        sql.append(firstTable.toSQL(sqlParameterCollector));
        while (iterator.hasNext()) {
            TableSQLExpression table = iterator.next();
            sql.append(table.toSQL(sqlParameterCollector));// [from table alias] | [left join table alias] 匿名表 应该使用  [left join (table) alias]

            PredicateSegment on = table.getOn();
            if (on != null && on.isNotEmpty()) {
                sql.append(" ON ").append(on.toSQL(sqlParameterCollector));
            }
        }
        boolean notExistsSQL = SQLSegmentUtil.isNotEmpty(this.allPredicate);
        boolean hasWhere = SQLSegmentUtil.isNotEmpty(this.where);
        if (hasWhere) {
            String whereSQL = this.where.toSQL(sqlParameterCollector);
            if(root&&notExistsSQL){
                sql.append(" WHERE ").append("( ").append(whereSQL).append(" )");
            }else{
                sql.append(" WHERE ").append(whereSQL);
            }
        }
        boolean onlyWhere=true;
        if (this.group!=null&&this.group.isNotEmpty()) {
            onlyWhere=false;
            sql.append(" GROUP BY ").append(this.group.toSQL(sqlParameterCollector));
        }
        if (this.having!=null&&this.having.isNotEmpty()) {
            onlyWhere=false;
            sql.append(" HAVING ").append(this.having.toSQL(sqlParameterCollector));
        }
        if (this.order!=null&&this.order.isNotEmpty()) {
            onlyWhere=false;
            sql.append(" ORDER BY ").append(this.order.toSQL(sqlParameterCollector));
        }
        if (this.rows > 0) {
            onlyWhere=false;
            sql.append(" LIMIT ");
            sql.append(this.rows);
            if (this.offset > 0) {
                sql.append(" OFFSET ").append(this.offset);
            }
        }
        String resultSQL = sql.toString();
        if(root&&notExistsSQL){
            StringBuilder notExistsResultSQL = new StringBuilder("SELECT NOT EXISTS ( ");
            if(onlyWhere){

                notExistsResultSQL.append(resultSQL).append(hasWhere?" AND ":" WHERE ").append("( ").append(allPredicate.toSQL(sqlParameterCollector))
                        .append(" )").append(" )");
            }else{
                notExistsResultSQL.append("SELECT 1 FROM ( ").append(resultSQL).append(" ) t ").append(" WHERE ").append(allPredicate.toSQL(sqlParameterCollector))
                        .append(" )");
            }
            return notExistsResultSQL.toString();
        }else{
            return resultSQL;
        }
    }

    @Override
    public com.easy.query.core.expression.sql.expression.QuerySQLExpression cloneSQLExpression() {

        EasyExpressionFactory expressionFactory = getRuntimeContext().getExpressionFactory();
        com.easy.query.core.expression.sql.expression.QuerySQLExpression easyQuerySQLExpression = expressionFactory.createEasyQuerySQLExpression(getRuntimeContext());

        if(SQLSegmentUtil.isNotEmpty(this.where)){
            easyQuerySQLExpression.setWhere(where.clonePredicateSegment());
        }
        if(SQLSegmentUtil.isNotEmpty(this.group)){
            easyQuerySQLExpression.setGroup(group.cloneSQLBuilder());
        }
        if(SQLSegmentUtil.isNotEmpty(this.having)){
            easyQuerySQLExpression.setHaving(having.clonePredicateSegment());
        }
        if(SQLSegmentUtil.isNotEmpty(this.order)){
            easyQuerySQLExpression.setOrder(order.cloneSQLBuilder());
        }
        if(SQLSegmentUtil.isNotEmpty(this.projects)){
            easyQuerySQLExpression.setProjects(projects.cloneSQLBuilder());
        }
        if(SQLSegmentUtil.isNotEmpty(this.allPredicate)){
            easyQuerySQLExpression.setAllPredicate(allPredicate.clonePredicateSegment());
        }
        easyQuerySQLExpression.setOffset(this.offset);
        easyQuerySQLExpression.setRows(this.rows);
        for (TableSQLExpression table : this.tables) {
            easyQuerySQLExpression.getTables().add(table.cloneSQLExpression());
        }
        return easyQuerySQLExpression;
    }
}
