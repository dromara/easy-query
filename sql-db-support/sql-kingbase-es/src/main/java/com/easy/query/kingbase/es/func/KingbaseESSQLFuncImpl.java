package com.easy.query.kingbase.es.func;

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
public class KingbaseESSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction ifNull(List<ColumnExpression> columnExpressions) {
        return new KingbaseESCOALESCESQLFunction(columnExpressions);
    }

    @Override
    public SQLFunction dateTimeFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new KingbaseESDateTimeFormatSQLFunction(getTable(tableOwner), property, javaFormat);
    }
    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new KingbaseESDateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new KingbaseESConcatSQLFunction(concatExpressions);
    }
//    @Override
//    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return new PgSQLStringJoinSQLFunction(separator, concatExpressions);
//    }

    @Override
    public SQLFunction now() {
        return KingbaseESNowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction utcNow() {
        return KingbaseESUtcNowSQLFunction.INSTANCE;
    }
}
