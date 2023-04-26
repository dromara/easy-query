package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyUpdateSqlExpression;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/22 22:49
 * 文件说明
 *
 * @author xuejiaming
 */
public  class UpdateSqlExpression implements EasyUpdateSqlExpression {
    protected final EasyQueryRuntimeContext runtimeContext;
    protected final SqlBuilderSegment setColumns;
    protected final PredicateSegment where;
    protected final List<EasyTableSqlExpression> tables=new ArrayList<>(1);

    public UpdateSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression table) {
        this.runtimeContext = runtimeContext;
        this.tables.add(table);
        this.setColumns = new UpdateSetSqlBuilderSegment();
        this.where = new AndPredicateSegment(true);
    }

    @Override
    public SqlBuilderSegment getSetColumns() {
        return setColumns;
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }
//
//    @Override
//    public EasyTableSqlExpression getTable() {
//        return table;
//    }


    @Override
    public List<EasyTableSqlExpression> getTables() {
        return tables;
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }


    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        if(SqlSegmentUtil.isEmpty(setColumns)){
            return null;
        }
        EasyTableSqlExpression easyTableSqlExpression = tables.get(0);
        String tableName = easyTableSqlExpression.getTableName();
        return "UPDATE " + tableName + " SET " + setColumns.toSql(sqlParameterCollector) + " WHERE " +
                where.toSql(sqlParameterCollector);
    }

    @Override
    public EasyUpdateSqlExpression cloneSqlExpression() {

        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EasyUpdateSqlExpression easyUpdateSqlExpression = expressionFactory.createEasyUpdateSqlExpression(runtimeContext,tables.get(0).cloneSqlExpression());
        if(SqlSegmentUtil.isNotEmpty(where)){
            where.copyTo(easyUpdateSqlExpression.getWhere());
        }if(SqlSegmentUtil.isNotEmpty(setColumns)){
            setColumns.copyTo(easyUpdateSqlExpression.getSetColumns());
        }
        return easyUpdateSqlExpression;
    }
}
