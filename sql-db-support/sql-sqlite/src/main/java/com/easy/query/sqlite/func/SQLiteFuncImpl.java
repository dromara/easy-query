package com.easy.query.sqlite.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.func.def.impl.LikeSQLFunction;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/10/13 18:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteFuncImpl extends SQLFuncImpl {

    @Override
    public SQLFunction nullOrDefault(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeFormat(SQLExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new SQLiteDateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
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


    @Override
    public DistinctDefaultSQLFunction sum(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteSumSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteCountSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteAvgSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction leftPad(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteLeftPadSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction rightPad(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteRightPadSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction joining(SQLExpression1<ColumnFuncSelector> sqlExpression,boolean distinct) {
        return new SQLiteJoiningSQLFunction(getColumnExpressions(sqlExpression),distinct);
    }

    @Override
    public SQLFunction length(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteLengthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction cast(SQLExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new SQLiteCastSQLFunction(getColumnExpressions(sqlExpression), targetClazz);
    }

    @Override
    public SQLFunction plusDateTime(SQLExpression1<ColumnFuncSelector> sqlExpression, long duration, TimeUnit timeUnit) {
        return new SQLiteDateTimePlusSQLFunction(getColumnExpressions(sqlExpression), duration, timeUnit);
    }

    @Override
    public SQLFunction plusDateTime2(SQLExpression1<ColumnFuncSelector> sqlExpression, TimeUnitEnum timeUnit) {
        return new SQLiteDateTime2PlusSQLFunction(getColumnExpressions(sqlExpression), timeUnit);
    }

    @Override
    public SQLFunction plusDateTimeMonths(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteDateTimePlusMonthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction plusDateTimeYears(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteDateTimePlusYearSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeProperty(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new SQLiteDateTimePropertySQLFunction(getColumnExpressions(sqlExpression), dateTimeUnitEnum);
    }

    @Override
    public SQLFunction duration(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new SQLiteDateTimeDurationSQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction booleanConstantSQLFunction(boolean trueOrFalse) {
        return new SQLiteBooleanConstantSQLFunction(trueOrFalse);
    }

    @Override
    public SQLFunction indexOf(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SQLiteIndexOfSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction like(SQLExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        SQLiteLikeSQLFunction likeSQLFunction = new SQLiteLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if (!like) {
            return not(x -> x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }
}
