package com.easy.query.mysql.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.impl.LikeSQLFunction;

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
    @Override
    public SQLFunction maxColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new MySQLMaxMinColumnsSQLFunction(true,getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction minColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new MySQLMaxMinColumnsSQLFunction(false,getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction like(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        MySQLLikeSQLFunction likeSQLFunction = new MySQLLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if (!like) {
            return not(x -> x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }
}