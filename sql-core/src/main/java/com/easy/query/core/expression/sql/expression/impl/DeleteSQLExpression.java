package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasyDeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSQLExpression;
import com.easy.query.core.util.SQLExpressionUtil;
import com.easy.query.core.util.SQLSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/23 07:54
 * 文件说明
 *
 * @author xuejiaming
 */
public  class DeleteSQLExpression implements EasyDeleteSQLExpression {

    protected final EasyQueryRuntimeContext runtimeContext;
    protected final PredicateSegment where;
    protected final List<EasyTableSQLExpression> tables=new ArrayList<>(1);

    public DeleteSQLExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSQLExpression table) {
        this.runtimeContext = runtimeContext;
        this.tables.add(table);
        this.where = new AndPredicateSegment(true);
    }


    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public List<EasyTableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }


    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        
        SQLExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
        EasyTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.getTableName();

        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(tableName);
        sql.append(" WHERE ");
        sql.append(where.toSQL(sqlParameterCollector));
        return sql.toString();
    }

    @Override
    public EasyDeleteSQLExpression cloneSQLExpression() {

        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EasyDeleteSQLExpression easyDeleteSQLExpression = expressionFactory.createEasyDeleteSQLExpression(runtimeContext, tables.get(0).cloneSQLExpression());
        if(SQLSegmentUtil.isNotEmpty(where)){
            where.copyTo(easyDeleteSQLExpression.getWhere());
        }
        return easyDeleteSQLExpression;
    }
}
