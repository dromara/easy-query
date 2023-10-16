package com.easy.query.sqllite.func;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
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
public class SQLiteFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction ifNull(List<ColumnExpression> columnExpressions) {
        return new SQLiteIfNullSQLFunction(columnExpressions);
    }

    @Override
    public SQLFunction dateTimeFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new SQLiteDateTimeFormatSQLFunction(getTable(tableOwner), property, javaFormat);
    }
    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new SQLiteDateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new SQLiteConcatSQLFunction(concatExpressions);
    }
//    @Override
//    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return new PgSQLStringJoinSQLFunction(separator, concatExpressions);
//    }

    @Override
    public SQLFunction now() {
        return SQLiteNowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction utcNow() {
        return SQLiteUtcNowSQLFunction.INSTANCE;
    }
}
