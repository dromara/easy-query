package com.easy.query.db2.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.AvgSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 08:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DB2AvgSQLFunction extends AvgSQLFunction {
    public DB2AvgSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new DB2NullDefaultSQLFunction(columnExpressions);
    }
}
