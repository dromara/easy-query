package com.easy.query.pgsql.func;

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
public class PgSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction ifNull(List<ColumnExpression> columnExpressions) {
        return new PgSQLCOALESCESQLFunction(columnExpressions);
    }

    @Override
    public SQLFunction dateTimeFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new PgSQLDateTimeFormatSQLFunction(getTable(tableOwner), property, javaFormat);
    }
    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new PgSQLDateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new PgSQLConcatSQLFunction(concatExpressions);
    }
//    @Override
//    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return new PgSQLStringJoinSQLFunction(separator, concatExpressions);
//    }

    @Override
    public SQLFunction now() {
        return PgSQLNowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction utcNow() {
        return PgSQLUtcNowSQLFunction.INSTANCE;
    }
}
