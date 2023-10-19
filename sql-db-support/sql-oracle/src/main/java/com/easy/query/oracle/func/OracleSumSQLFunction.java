package com.easy.query.oracle.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.SumSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 08:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleSumSQLFunction extends SumSQLFunction {

    public OracleSumSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new OracleNullDefaultSQLFunction(columnExpressions);
    }
}
