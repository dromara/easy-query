package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasyDeleteSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/23 07:54
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class DeleteSqlExpression implements EasyDeleteSqlExpression {

    protected final EasyQueryRuntimeContext runtimeContext;
    protected final PredicateSegment where;
    protected final List<EasyTableSqlExpression> tables=new ArrayList<>(1);

    public DeleteSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression table) {
        this.runtimeContext = runtimeContext;
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
    public PredicateSegment getWhere() {
        return where;
    }


    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        EasyTableSqlExpression easyTableSqlExpression = tables.get(0);
        String tableName = easyTableSqlExpression.getTableName();

        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(tableName);
        sql.append(" WHERE ");
        sql.append(where.toSql(sqlParameterCollector));
        return sql.toString();
    }
}
