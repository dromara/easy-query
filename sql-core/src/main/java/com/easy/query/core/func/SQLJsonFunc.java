package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2023/12/21 12:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLJsonFunc {
    SQLFunction jsonObjectField(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    SQLFunction jsonObjectExtract(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    SQLFunction jsonObjectContainsKey(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    SQLFunction jsonArrayByIndex(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    SQLFunction jsonArrayExtractByIndex(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    SQLFunction jsonArrayLength(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
}
