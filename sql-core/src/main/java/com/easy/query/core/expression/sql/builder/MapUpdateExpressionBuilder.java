package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;

/**
 * create time 2023/10/3 16:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapUpdateExpressionBuilder extends EntityUpdateExpressionBuilder{

    void addWhereColumns(String... columnNames);
    @Override
    MapUpdateExpressionBuilder cloneEntityExpressionBuilder();
    @Override
    EntityUpdateSQLExpression toExpression();

    @Override
    EntityUpdateSQLExpression toExpression(Object entity);
}
