package com.easy.query.sqllite.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.CountSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 09:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteCountSQLFunction extends CountSQLFunction {
    public SQLiteCountSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new SQLiteValueOrDefaultSQLFunction(columnExpressions);
    }
}
