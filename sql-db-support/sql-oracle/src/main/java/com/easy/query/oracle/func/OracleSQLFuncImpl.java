package com.easy.query.oracle.func;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;

import java.util.List;

/**
 * create time 2023/10/13 18:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction valueOrDefault(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new OracleDateTimeFormatSQLFunction(getTable(tableOwner), property, javaFormat);
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

    @Override
    public DistinctDefaultSQLFunction sum(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleSumSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleCountSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleAvgSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction leftPad(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleLeftPadSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction rightPad(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleRightPadSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction join(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleJoinSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction length(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleLengthSQLFunction(getColumnExpressions(sqlExpression));
    }
}
