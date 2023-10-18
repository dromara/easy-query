package com.easy.query.core.func.column.impl;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFunctionExpression;

/**
 * create time 2023/10/18 13:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFunctionExpressionImpl implements ColumnFunctionExpression {
    private final SQLFunction sqlFunction;

    public ColumnFunctionExpressionImpl(SQLFunction sqlFunction){

        this.sqlFunction = sqlFunction;
    }
    @Override
    public SQLFunction getSQLFunction() {
        return sqlFunction;
    }
}
