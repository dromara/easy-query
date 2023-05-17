package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.expression.sql.expression.InsertSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.util.SQLExpressionUtil;
import com.easy.query.core.util.SQLSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/22 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public  class InsertSQLExpressionImpl implements InsertSQLExpression {

    protected final SQLBuilderSegment columns;
    protected final EasyQueryRuntimeContext runtimeContext;
    protected final List<TableSQLExpression> tables=new ArrayList<>(1);

    public InsertSQLExpressionImpl(EasyQueryRuntimeContext runtimeContext, TableSQLExpression table) {
        this.runtimeContext = runtimeContext;
        this.tables.add(table);
        columns=new ProjectSQLBuilderSegmentImpl();
    }

    @Override
    public List<TableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public SQLBuilderSegment getColumns() {
        return columns;
    }
    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }


    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        SQLExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
        TableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.getTableName();
        int insertColumns = columns.getSQLSegments().size();
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (").append(columns.toSQL(sqlParameterCollector)).append(") VALUES (");
        sql.append("?");
        for (int i = 0; i < insertColumns - 1; i++) {
            sql.append(",?");
        }
        sql.append(") ");
        return sql.toString();
    }

    @Override
    public com.easy.query.core.expression.sql.expression.InsertSQLExpression cloneSQLExpression() {

        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();

        com.easy.query.core.expression.sql.expression.InsertSQLExpression easyInsertSQLExpression = expressionFactory.createEasyInsertSQLExpression(runtimeContext,tables.get(0).cloneSQLExpression());
        if(SQLSegmentUtil.isNotEmpty(columns)){
            columns.copyTo(easyInsertSQLExpression.getColumns());
        }
        return easyInsertSQLExpression;
    }
}
