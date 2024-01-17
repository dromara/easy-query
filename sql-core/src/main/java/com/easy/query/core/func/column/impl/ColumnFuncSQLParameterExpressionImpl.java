package com.easy.query.core.func.column.impl;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.func.column.ColumnFuncSQLParameterExpression;

/**
 * create time 2024/1/17 13:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFuncSQLParameterExpressionImpl implements ColumnFuncSQLParameterExpression {
    private final SQLParameter sqlParameter;

    public ColumnFuncSQLParameterExpressionImpl(SQLParameter sqlParameter){

        this.sqlParameter = sqlParameter;
    }

    @Override
    public SQLParameter getSQLParameter() {
        return sqlParameter;
    }
}
