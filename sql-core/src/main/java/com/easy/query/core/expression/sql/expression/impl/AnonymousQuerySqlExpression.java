package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasyAnonymousQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;

import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/22 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousQuerySqlExpression implements EasyAnonymousQuerySqlExpression {
    private final EasyQueryRuntimeContext runtimeContext;
    private final String sql;
    private final List<EasyTableSqlExpression> tables;

    public AnonymousQuerySqlExpression(EasyQueryRuntimeContext runtimeContext,String sql){
        this.runtimeContext = runtimeContext;

        this.sql = sql;
        this.tables = Collections.emptyList();
    }
    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        return sql;
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
    public boolean hasLimit() {
        return false;
    }

    @Override
    public List<EasyTableSqlExpression> getTables() {
        return tables;
    }

    @Override
    public EasyQuerySqlExpression cloneSqlExpression() {
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        return expressionFactory.createEasyAnonymousQuerySqlExpression(runtimeContext,sql);
    }

}
