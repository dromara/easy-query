package com.easy.query.kingbase.es.func;

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
public class KingbaseESSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction nullOrDefault(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new KingbaseESNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeFormat(SQLExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new KingbaseESDateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
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

    @Override
    public DistinctDefaultSQLFunction sum(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new KingbaseESSumSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new KingbaseESCountSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new KingbaseESAvgSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction joining(SQLExpression1<ColumnFuncSelector> sqlExpression, boolean distinct) {
        return new KingbaseESJoiningSQLFunction(getColumnExpressions(sqlExpression), distinct);
    }

    @Override
    public SQLFunction cast(SQLExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new KingbaseESCastSQLFunction(getColumnExpressions(sqlExpression), targetClazz);
    }

    @Override
    public SQLFunction plusDateTime(SQLExpression1<ColumnFuncSelector> sqlExpression, long duration, TimeUnit timeUnit) {
        return new KingbaseESDateTimePlusSQLFunction(getColumnExpressions(sqlExpression), duration, timeUnit);
    }

    @Override
    public SQLFunction plusDateTime2(SQLExpression1<ColumnFuncSelector> sqlExpression, TimeUnitEnum timeUnit) {
        return new KingbaseESDateTime2PlusSQLFunction(getColumnExpressions(sqlExpression), timeUnit);
    }

    @Override
    public SQLFunction plusDateTimeMonths(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new KingbaseESDateTimePlusMonthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction plusDateTimeYears(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new KingbaseESDateTimePlusYearSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeProperty(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new KingbaseESDateTimePropertySQLFunction(getColumnExpressions(sqlExpression), dateTimeUnitEnum);
    }

    @Override
    public SQLFunction duration(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new KingbaseESDateTimeDurationSQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction duration2(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new KingbaseESDateTimeDuration2SQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction math(SQLExpression1<ColumnFuncSelector> sqlExpression, MathMethodEnum mathMethodEnum) {
        return new KingbaseESMathSQLFunction(getColumnExpressions(sqlExpression), mathMethodEnum);
    }

    @Override
    public SQLFunction like(SQLExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        KingbaseESLikeSQLFunction likeSQLFunction = new KingbaseESLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if (!like) {
            return not(x -> x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }

    @Override
    public SQLFunction indexOf(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new KingbaseESIndexOfSQLFunction(getColumnExpressions(sqlExpression));
    }
}
