package com.easy.query.db2.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.SumSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 08:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class DB2SumSQLFunction extends SumSQLFunction {

    public DB2SumSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new DB2NullDefaultSQLFunction(columnExpressions);
    }
}
