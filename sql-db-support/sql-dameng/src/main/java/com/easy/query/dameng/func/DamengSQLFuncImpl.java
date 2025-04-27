package com.easy.query.dameng.func;

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
import com.easy.query.core.func.def.enums.MathMethodEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/10/13 18:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction nullOrDefault(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeFormat(SQLExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new DamengDateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
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
    public SQLFunction joining(SQLExpression1<ColumnFuncSelector> sqlExpression, boolean distinct) {
        return new DamengJoiningSQLFunction(getColumnExpressions(sqlExpression), distinct);
    }

    @Override
    public SQLFunction length(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengLengthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction cast(SQLExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new DamengCastSQLFunction(getColumnExpressions(sqlExpression), targetClazz);
    }

    @Override
    public SQLFunction plusDateTime(SQLExpression1<ColumnFuncSelector> sqlExpression, long duration, TimeUnit timeUnit) {
        return new DamengDateTimePlusSQLFunction(getColumnExpressions(sqlExpression), duration, timeUnit);
    }

    @Override
    public SQLFunction plusDateTime2(SQLExpression1<ColumnFuncSelector> sqlExpression, TimeUnitEnum timeUnit) {
        return new DamengDateTime2PlusSQLFunction(getColumnExpressions(sqlExpression), timeUnit);
    }

    @Override
    public SQLFunction plusDateTimeMonths(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengDateTimePlusMonthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction plusDateTimeYears(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengDateTimePlusYearSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeProperty(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new DamengDateTimePropertySQLFunction(getColumnExpressions(sqlExpression), dateTimeUnitEnum);
    }

    @Override
    public SQLFunction duration(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new DamengDateTimeDurationSQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction duration2(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new DamengDateTimeDuration2SQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction math(SQLExpression1<ColumnFuncSelector> sqlExpression, MathMethodEnum mathMethodEnum) {
        return new DamengMathSQLFunction(getColumnExpressions(sqlExpression), mathMethodEnum);
    }

    @Override
    public SQLFunction like(SQLExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        DamengLikeSQLFunction likeSQLFunction = new DamengLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if (!like) {
            return not(x -> x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }

    @Override
    public SQLFunction indexOf(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new DamengIndexOfSQLFunction(getColumnExpressions(sqlExpression));
    }
}
