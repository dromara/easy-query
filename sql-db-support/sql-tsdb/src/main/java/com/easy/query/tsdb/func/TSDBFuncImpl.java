package com.easy.query.tsdb.func;

import com.easy.query.core.enums.SQLLikeEnum;
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
public class TSDBFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction stringCompareTo(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new TSDBStringCompareToSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction maxColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new TSDBMaxMinColumnsSQLFunction(true,getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction minColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new TSDBMaxMinColumnsSQLFunction(false,getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction like(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        TSDBLikeSQLFunction likeSQLFunction = new TSDBLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if (!like) {
            return not(x -> x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }
}