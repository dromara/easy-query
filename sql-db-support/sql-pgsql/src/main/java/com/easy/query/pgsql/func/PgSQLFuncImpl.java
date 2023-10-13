package com.easy.query.pgsql.func;

import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;

import java.util.List;

/**
 * create time 2023/10/13 18:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class PgSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction ifNull(List<ColumnExpression> columnExpressions) {
        return new COALESCESQLFunction(columnExpressions);
    }
}
