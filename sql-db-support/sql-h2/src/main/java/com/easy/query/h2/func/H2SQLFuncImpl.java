package com.easy.query.h2.func;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2025/10/14 15:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2SQLFuncImpl extends SQLFuncImpl{

    @Override
    public SQLFunction maxColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new H2MaxMinColumnsSQLFunction(true,getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction minColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new H2MaxMinColumnsSQLFunction(false,getColumnExpressions(sqlExpression));
    }

}
