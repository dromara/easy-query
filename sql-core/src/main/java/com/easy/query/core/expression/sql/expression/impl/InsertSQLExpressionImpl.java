package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.expression.sql.expression.EntityInsertSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/22 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public  class InsertSQLExpressionImpl implements EntityInsertSQLExpression {

    protected final SQLBuilderSegment columns;
    protected final QueryRuntimeContext runtimeContext;
    protected final List<EntityTableSQLExpression> tables=new ArrayList<>(1);

    public InsertSQLExpressionImpl(QueryRuntimeContext runtimeContext, EntityTableSQLExpression table) {
        this.runtimeContext = runtimeContext;
        this.tables.add(table);
        columns=new ProjectSQLBuilderSegmentImpl();
    }

    @Override
    public List<EntityTableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public SQLBuilderSegment getColumns() {
        return columns;
    }
    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }


    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        EasySQLExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
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
    public EntityInsertSQLExpression cloneSQLExpression() {

        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();

        EntityInsertSQLExpression easyInsertSQLExpression = expressionFactory.createEasyInsertSQLExpression(runtimeContext,tables.get(0).cloneSQLExpression());
        if(EasySQLSegmentUtil.isNotEmpty(columns)){
            columns.copyTo(easyInsertSQLExpression.getColumns());
        }
        return easyInsertSQLExpression;
    }
}
