package com.easy.query.mssql.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.CountSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 08:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLCountSQLFunction extends CountSQLFunction {
    public MsSQLCountSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new MsSQLValueOrDefaultSQLFunction(columnExpressions);
    }
}
