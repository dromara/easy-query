package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/22 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public  class InsertSqlExpression implements EasyInsertSqlExpression {

    protected final SqlBuilderSegment columns;
    protected final EasyQueryRuntimeContext runtimeContext;
    protected final List<EasyTableSqlExpression> tables=new ArrayList<>(1);

    public InsertSqlExpression(EasyQueryRuntimeContext runtimeContext,EasyTableSqlExpression table) {
        this.runtimeContext = runtimeContext;
        this.tables.add(table);
        columns=new ProjectSqlBuilderSegmentImpl();
    }

    @Override
    public List<EasyTableSqlExpression> getTables() {
        return tables;
    }

    @Override
    public SqlBuilderSegment getColumns() {
        return columns;
    }
    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }


    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        EasyTableSqlExpression easyTableSqlExpression = tables.get(0);
        String tableName = easyTableSqlExpression.getTableName();
        int insertColumns = columns.getSqlSegments().size();
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (").append(columns.toSql(sqlParameterCollector)).append(") VALUES (");
        sql.append("?");
        for (int i = 0; i < insertColumns - 1; i++) {
            sql.append(",?");
        }
        sql.append(") ");
        return sql.toString();
    }

    @Override
    public EasyInsertSqlExpression cloneSqlExpression() {

        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();

        EasyInsertSqlExpression easyInsertSqlExpression = expressionFactory.createEasyInsertSqlExpression(runtimeContext,tables.get(0).cloneSqlExpression());
        if(SqlSegmentUtil.isNotEmpty(columns)){
            columns.copyTo(easyInsertSqlExpression.getColumns());
        }
        return easyInsertSqlExpression;
    }
}
