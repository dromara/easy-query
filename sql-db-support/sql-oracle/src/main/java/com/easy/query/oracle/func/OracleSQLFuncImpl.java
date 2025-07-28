package com.easy.query.oracle.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.enums.MathMethodEnum;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/10/13 18:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction nullOrDefault(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeFormat(SQLActionExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new OracleDateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
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
    public DistinctDefaultSQLFunction sum(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleSumSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleCountSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleAvgSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction leftPad(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleLeftPadSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction rightPad(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleRightPadSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction joining(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean distinct) {
        return new OracleJoiningSQLFunction(getColumnExpressions(sqlExpression), distinct);
    }

    @Override
    public SQLFunction length(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleLengthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction cast(SQLActionExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new OracleCastSQLFunction(getColumnExpressions(sqlExpression), targetClazz);
    }

    @Override
    public SQLFunction plusDateTime(SQLActionExpression1<ColumnFuncSelector> sqlExpression, long duration, TimeUnit timeUnit) {
        return new OracleDateTimePlusSQLFunction(getColumnExpressions(sqlExpression), duration, timeUnit);
    }

    @Override
    public SQLFunction plusDateTime2(SQLActionExpression1<ColumnFuncSelector> sqlExpression, TimeUnitEnum timeUnit) {
        return new OracleDateTime2PlusSQLFunction(getColumnExpressions(sqlExpression), timeUnit);
    }

    @Override
    public SQLFunction plusDateTimeMonths(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleDateTimePlusMonthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction plusDateTimeYears(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleDateTimePlusYearSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeProperty(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new OracleDateTimePropertySQLFunction(getColumnExpressions(sqlExpression), dateTimeUnitEnum);
    }

    @Override
    public SQLFunction duration(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new OracleDateTimeDurationSQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction duration2(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new OracleDateTimeDuration2SQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction math(SQLActionExpression1<ColumnFuncSelector> sqlExpression, MathMethodEnum mathMethodEnum) {
        return new OracleMathSQLFunction(getColumnExpressions(sqlExpression), mathMethodEnum);
    }

    @Override
    public SQLFunction orderByNullsMode(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean asc, OrderByModeEnum orderByModeEnum) {
        return new OracleOrderByNullsModeSQLFunction(getColumnExpressions(sqlExpression), asc, orderByModeEnum);
    }

    @Override
    public SQLFunction like(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        OracleLikeSQLFunction likeSQLFunction = new OracleLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if (!like) {
            return not(x -> x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }

    @Override
    public SQLFunction booleanConstantSQLFunction(boolean trueOrFalse) {
        return new OracleBooleanConstantSQLFunction(trueOrFalse);
    }

    @Override
    public SQLFunction indexOf(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new OracleIndexOfSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction random() {
        return new OracleRandomSQLFunction();
    }
    @Override
    public SQLFunction booleanSQLFunction(String sqlSegment, SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        SQLFunction sqlFunction = anySQLFunction(sqlSegment, sqlExpression);
        SQLActionExpression1<ColumnFuncSelector> sqlExpressionFunc = columnFuncSelector -> {
            columnFuncSelector.sqlFunc(sqlFunction);
            columnFuncSelector.value(true);
            columnFuncSelector.value(false);
        };
        return new OracleBooleanSQLFunction(getColumnExpressions(sqlExpressionFunc));
    }
}
