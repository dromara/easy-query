package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlUnionEnum;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasyAnonymousQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.util.SqlExpressionUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/4/22 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousUnionQuerySqlExpression implements EasyAnonymousQuerySqlExpression {
    protected final EasyQueryRuntimeContext runtimeContext;
    private final List<EasyQuerySqlExpression> easyQuerySqlExpressions;
    private final SqlUnionEnum sqlUnion;
    protected final List<EasyTableSqlExpression> tables;

    public AnonymousUnionQuerySqlExpression(EasyQueryRuntimeContext runtimeContext, List<EasyQuerySqlExpression> easyQuerySqlExpressions, SqlUnionEnum sqlUnion){
        this.runtimeContext = runtimeContext;
        this.easyQuerySqlExpressions = easyQuerySqlExpressions;
        this.sqlUnion = sqlUnion;

        this.tables = Collections.emptyList();
    }
    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SqlExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
        Iterator<EasyQuerySqlExpression> iterator = easyQuerySqlExpressions.iterator();
        EasyQuerySqlExpression firstEasyQuerySqlExpression = iterator.next();
        String unionSql = " " + sqlUnion.getSql() + " ";
        StringBuilder sql = new StringBuilder();
        sql.append(firstEasyQuerySqlExpression.toSql(sqlParameterCollector));
        while(iterator.hasNext()){
            sql.append(unionSql);
            EasyQuerySqlExpression easyQuerySqlExpression = iterator.next();
            sql.append(easyQuerySqlExpression.toSql(sqlParameterCollector));
        }
        return sql.toString();
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public SqlBuilderSegment getProjects() {
        return null;
    }

    @Override
    public void setProjects(SqlBuilderSegment projects) {

    }

    @Override
    public PredicateSegment getWhere() {
        return null;
    }

    @Override
    public void setWhere(PredicateSegment where) {

    }

    @Override
    public SqlBuilderSegment getGroup() {
        return null;
    }

    @Override
    public void setGroup(SqlBuilderSegment group) {

    }

    @Override
    public PredicateSegment getHaving() {
        return null;
    }

    @Override
    public void setHaving(PredicateSegment having) {

    }

    @Override
    public SqlBuilderSegment getOrder() {
        return null;
    }

    @Override
    public void setOrder(SqlBuilderSegment order) {

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
    public List<EasyTableSqlExpression> getTables() {
        return tables;
    }

    @Override
    public EasyQuerySqlExpression cloneSqlExpression() {
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        return expressionFactory.createEasyAnonymousUnionQuerySqlExpression(runtimeContext,easyQuerySqlExpressions,sqlUnion);
    }

}
