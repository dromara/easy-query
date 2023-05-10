package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasyDeleteSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.util.SqlExpressionUtil;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/23 07:54
 * 文件说明
 *
 * @author xuejiaming
 */
public  class DeleteSqlExpression implements EasyDeleteSqlExpression {

    protected final EasyQueryRuntimeContext runtimeContext;
    protected final ExecuteMethodEnum executeMethod;
    protected final PredicateSegment where;
    protected final List<EasyTableSqlExpression> tables=new ArrayList<>(1);

    public DeleteSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression table, ExecuteMethodEnum executeMethod) {
        this.runtimeContext = runtimeContext;
        this.executeMethod = executeMethod;
        this.tables.add(table);
        this.where = new AndPredicateSegment(true);
    }


    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public List<EasyTableSqlExpression> getTables() {
        return tables;
    }

    @Override
    public ExecuteMethodEnum getExecuteMethod() {
        return executeMethod;
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }


    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        
        SqlExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
        EasyTableSqlExpression easyTableSqlExpression = tables.get(0);
        String tableName = easyTableSqlExpression.getTableName();

        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(tableName);
        sql.append(" WHERE ");
        sql.append(where.toSql(sqlParameterCollector));
        return sql.toString();
    }

    @Override
    public EasyDeleteSqlExpression cloneSqlExpression() {

        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EasyDeleteSqlExpression easyDeleteSqlExpression = expressionFactory.createEasyDeleteSqlExpression(runtimeContext, tables.get(0).cloneSqlExpression(),executeMethod);
        if(SqlSegmentUtil.isNotEmpty(where)){
            where.copyTo(easyDeleteSqlExpression.getWhere());
        }
        return easyDeleteSqlExpression;
    }
}
