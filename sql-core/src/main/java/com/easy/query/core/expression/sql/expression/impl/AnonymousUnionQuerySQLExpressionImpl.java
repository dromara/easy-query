package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.AnonymousEntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.AnonymousUnionEntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/4/22 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousUnionQuerySQLExpressionImpl implements AnonymousUnionEntityQuerySQLExpression {
    protected final QueryRuntimeContext runtimeContext;
    private final List<EntityQuerySQLExpression> querySQLExpressions;
    private final SQLUnionEnum sqlUnion;
    protected final List<EntityTableSQLExpression> tables;

    public AnonymousUnionQuerySQLExpressionImpl(QueryRuntimeContext runtimeContext, List<EntityQuerySQLExpression> querySQLExpressions, SQLUnionEnum sqlUnion){
        this.runtimeContext = runtimeContext;
        this.querySQLExpressions = querySQLExpressions;
        this.sqlUnion = sqlUnion;

        this.tables = Collections.emptyList();
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        Iterator<EntityQuerySQLExpression> iterator = querySQLExpressions.iterator();
        EntityQuerySQLExpression firstQuerySQLExpression = iterator.next();
        String unionSQL = " " + sqlUnion.getSQL() + " ";
        StringBuilder sql = new StringBuilder();
        sql.append(firstQuerySQLExpression.toSQL(toSQLContext));
        while(iterator.hasNext()){
            sql.append(unionSQL);
            EntityQuerySQLExpression querySQLExpression = iterator.next();
            sql.append(querySQLExpression.toSQL(toSQLContext));
        }
        return sql.toString();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
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
    public List<EntityTableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public EntityQuerySQLExpression cloneSQLExpression() {
        ArrayList<EntityQuerySQLExpression> entityQuerySQLExpressionCopies = new ArrayList<>(querySQLExpressions.size());
        for (EntityQuerySQLExpression querySQLExpression : querySQLExpressions) {
            entityQuerySQLExpressionCopies.add(querySQLExpression.cloneSQLExpression());
        }
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        AnonymousEntityQuerySQLExpression easyAnonymousUnionQuerySQLExpression = expressionFactory.createEasyAnonymousUnionQuerySQLExpression(runtimeContext,entityQuerySQLExpressionCopies , sqlUnion);
        for (EntityTableSQLExpression table : tables) {
            easyAnonymousUnionQuerySQLExpression.getTables().add(table.cloneSQLExpression());
        }
        return easyAnonymousUnionQuerySQLExpression;
    }

    @Override
    public List<EntityQuerySQLExpression> getEntityQuerySQLExpressions() {
        return querySQLExpressions;
    }
}
