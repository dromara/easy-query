package com.easy.query.mssql.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.SumSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 08:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLSumSQLFunction extends SumSQLFunction {
    public MsSQLSumSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new MsSQLNullDefaultSQLFunction(columnExpressions);
    }
}
