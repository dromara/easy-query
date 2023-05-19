package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.expression.EntityDeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/23 07:54
 * 文件说明
 *
 * @author xuejiaming
 */
public  class DeleteSQLExpressionImpl implements EntityDeleteSQLExpression {

    protected final QueryRuntimeContext runtimeContext;
    protected final PredicateSegment where;
    protected final List<EntityTableSQLExpression> tables=new ArrayList<>(1);

    public DeleteSQLExpressionImpl(QueryRuntimeContext runtimeContext, EntityTableSQLExpression table) {
        this.runtimeContext = runtimeContext;
        this.tables.add(table);
        this.where = new AndPredicateSegment(true);
    }


    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public List<EntityTableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }


    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.toSQL(toSQLContext);

        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(tableName);
        sql.append(" WHERE ");
        sql.append(where.toSQL(toSQLContext));
        return sql.toString();
    }

    @Override
    public EntityDeleteSQLExpression cloneSQLExpression() {

        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntityDeleteSQLExpression easyDeleteSQLExpression = expressionFactory.createEasyDeleteSQLExpression(runtimeContext, tables.get(0).cloneSQLExpression());
        if(EasySQLSegmentUtil.isNotEmpty(where)){
            where.copyTo(easyDeleteSQLExpression.getWhere());
        }
        return easyDeleteSQLExpression;
    }
}
