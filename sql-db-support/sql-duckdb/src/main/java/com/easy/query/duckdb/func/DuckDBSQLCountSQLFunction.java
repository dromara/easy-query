package com.easy.query.duckdb.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.CountSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 09:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBSQLCountSQLFunction extends CountSQLFunction {
    public DuckDBSQLCountSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new DuckDBSQLNullDefaultSQLFunction(columnExpressions);
    }
}
