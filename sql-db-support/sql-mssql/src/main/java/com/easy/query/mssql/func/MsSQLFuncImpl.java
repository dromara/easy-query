package com.easy.query.mssql.func;

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
public class MsSQLFuncImpl extends SQLFuncImpl {
    //    private final ServiceProvider serviceProvider;
//
//    public MsSQLFuncImpl(ServiceProvider serviceProvider){
//
//        this.serviceProvider = serviceProvider;
//    }
    @Override
    public SQLFunction nullOrDefault(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeFormat(SQLExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new MsSQLDateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
    }

    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new MsSQLDateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new MsSQLConcatSQLFunction(concatExpressions);
    }
//    @Override
//    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return new PgSQLStringJoinSQLFunction(separator, concatExpressions);
//    }

    @Override
    public SQLFunction now() {
        return MsSQLNowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction utcNow() {
        return MsSQLUtcNowSQLFunction.INSTANCE;
    }

    @Override
    public DistinctDefaultSQLFunction sum(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLSumSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLCountSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLAvgSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction subString(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLSubStringSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction trim(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLTrimSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction joining(SQLExpression1<ColumnFuncSelector> sqlExpression,boolean distinct) {
        return new MsSQLJoiningSQLFunction(getColumnExpressions(sqlExpression),distinct);
    }

    @Override
    public SQLFunction length(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLLengthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction cast(SQLExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new MsSQLCastSQLFunction(getColumnExpressions(sqlExpression), targetClazz);
    }

    @Override
    public SQLFunction plusDateTime(SQLExpression1<ColumnFuncSelector> sqlExpression, long duration, TimeUnit timeUnit) {
        return new MsSQLDateTimePlusSQLFunction(getColumnExpressions(sqlExpression), duration, timeUnit);
    }

    @Override
    public SQLFunction plusDateTime2(SQLExpression1<ColumnFuncSelector> sqlExpression, TimeUnitEnum timeUnit) {
        return new MsSQLDateTime2PlusSQLFunction(getColumnExpressions(sqlExpression), timeUnit);
    }

    @Override
    public SQLFunction plusDateTimeMonths(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLDateTimePlusMonthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction plusDateTimeYears(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLDateTimePlusYearSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeProperty(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new MsSQLDateTimePropertySQLFunction(getColumnExpressions(sqlExpression), dateTimeUnitEnum);
    }

    @Override
    public SQLFunction duration(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new MsSQLDateTimeDurationSQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }
    @Override
    public SQLFunction duration2(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new MsSQLDateTimeDuration2SQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction math(SQLExpression1<ColumnFuncSelector> sqlExpression, MathMethodEnum mathMethodEnum) {
        return new MsSQLMathSQLFunction(getColumnExpressions(sqlExpression), mathMethodEnum);
    }

    @Override
    public SQLFunction like(SQLExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        MsSQLLikeSQLFunction likeSQLFunction = new MsSQLLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if (!like) {
            return not(x -> x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }

    @Override
    public SQLFunction booleanConstantSQLFunction(boolean trueOrFalse) {
        return new MsSQLBooleanConstantSQLFunction(trueOrFalse);
    }

    @Override
    public SQLFunction indexOf(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLIndexOfSQLFunction(getColumnExpressions(sqlExpression));
    }
}
