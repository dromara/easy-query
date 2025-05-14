package com.easy.query.mysql.func;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2023/12/21 10:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction stringCompareTo(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new MySQLStringCompareToSQLFunction(getColumnExpressions(sqlExpression));
    }
}