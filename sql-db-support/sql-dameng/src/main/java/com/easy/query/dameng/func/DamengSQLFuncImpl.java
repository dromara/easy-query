package com.easy.query.dameng.func;

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
public class DamengSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction valueOrDefault(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new DamengDateTimeFormatSQLFunction(getTable(tableOwner), property, javaFormat);
    }
    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new DamengDateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new DamengConcatSQLFunction(concatExpressions);
    }
//    @Override
//    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return new OracleStringJoinSQLFunction(separator, concatExpressions);
//    }

    @Override
    public SQLFunction now() {
        return DamengNowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction utcNow() {
        return DamengUtcNowSQLFunction.INSTANCE;
    }

    @Override
    public DistinctDefaultSQLFunction sum(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengSumSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengCountSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengAvgSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction leftPad(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengLeftPadSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction rightPad(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengRightPadSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction join(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengJoinSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction length(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengLengthSQLFunction(getColumnExpressions(sqlExpression));
    }
}
