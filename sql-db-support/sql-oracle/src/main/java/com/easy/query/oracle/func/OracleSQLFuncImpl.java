package com.easy.query.oracle.func;

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
public class OracleSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction ifNull(List<ColumnExpression> columnExpressions) {
        return new OracleNVLSQLFunction(columnExpressions);
    }

    @Override
    public SQLFunction dateTimeJavaFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new OracleDateTimeJavaFormatSQLFunction(getTable(tableOwner), property, javaFormat);
    }
    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new OracleDateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new OracleConcatSQLFunction(concatExpressions);
    }
//    @Override
//    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return new OracleStringJoinSQLFunction(separator, concatExpressions);
//    }

    @Override
    public SQLFunction now() {
        return OracleNowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction utcNow() {
        return OracleUtcNowSQLFunction.INSTANCE;
    }
}
