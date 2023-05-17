package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.AnonymousQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.QuerySQLExpression;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.util.SQLExpressionUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/4/22 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousUnionQuerySQLExpressionImpl implements AnonymousQuerySQLExpression {
    protected final EasyQueryRuntimeContext runtimeContext;
    private final List<QuerySQLExpression> querySQLExpressions;
    private final SQLUnionEnum sqlUnion;
    protected final List<TableSQLExpression> tables;

    public AnonymousUnionQuerySQLExpressionImpl(EasyQueryRuntimeContext runtimeContext, List<QuerySQLExpression> querySQLExpressions, SQLUnionEnum sqlUnion){
        this.runtimeContext = runtimeContext;
        this.querySQLExpressions = querySQLExpressions;
        this.sqlUnion = sqlUnion;

        this.tables = Collections.emptyList();
    }
    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        SQLExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
        Iterator<QuerySQLExpression> iterator = querySQLExpressions.iterator();
        QuerySQLExpression firstQuerySQLExpression = iterator.next();
        String unionSQL = " " + sqlUnion.getSQL() + " ";
        StringBuilder sql = new StringBuilder();
        sql.append(firstQuerySQLExpression.toSQL(sqlParameterCollector));
        while(iterator.hasNext()){
            sql.append(unionSQL);
            QuerySQLExpression querySQLExpression = iterator.next();
            sql.append(querySQLExpression.toSQL(sqlParameterCollector));
        }
        return sql.toString();
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public SQLBuilderSegment getProjects() {
        return null;
    }

    @Override
    public void setProjects(SQLBuilderSegment projects) {

    }

    @Override
    public PredicateSegment getWhere() {
        return null;
    }

    @Override
    public void setWhere(PredicateSegment where) {

    }

    @Override
    public SQLBuilderSegment getGroup() {
        return null;
    }

    @Override
    public void setGroup(SQLBuilderSegment group) {

    }

    @Override
    public PredicateSegment getHaving() {
        return null;
    }

    @Override
    public void setHaving(PredicateSegment having) {

    }

    @Override
    public SQLBuilderSegment getOrder() {
        return null;
    }

    @Override
    public void setOrder(SQLBuilderSegment order) {

    }

    @Override
    public long getOffset() {
        return 0;
    }

    @Override
    public void setOffset(long offset) {

    }

    @Override
    public long getRows() {
        return 0;
    }

    @Override
    public void setRows(long rows) {

    }

    @Override
    public void setDistinct(boolean distinct) {

    }

    @Override
    public boolean isDistinct() {
        return false;
    }

    @Override
    public boolean hasLimit() {
        return false;
    }

    @Override
    public void setAllPredicate(PredicateSegment allPredicate) {

    }

    @Override
    public PredicateSegment getAllPredicate() {
        return null;
    }

    @Override
    public List<TableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public QuerySQLExpression cloneSQLExpression() {
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        return expressionFactory.createEasyAnonymousUnionQuerySQLExpression(runtimeContext, querySQLExpressions,sqlUnion);
    }

}
