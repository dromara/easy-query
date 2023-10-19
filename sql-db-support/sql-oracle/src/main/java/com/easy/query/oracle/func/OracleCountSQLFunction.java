package com.easy.query.oracle.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.CountSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 09:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleCountSQLFunction extends CountSQLFunction {
    public OracleCountSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new OracleNullDefaultSQLFunction(columnExpressions);
    }
}
