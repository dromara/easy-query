package com.easy.query.pgsql.func;

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
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.func.def.impl.JSONArrayByIndexSQLFunction;
import com.easy.query.core.func.def.impl.JSONArrayExtractByIndexSQLFunction;
import com.easy.query.core.func.def.impl.JSONArrayLengthSQLFunction;
import com.easy.query.core.func.def.impl.JSONObjectContainsKeySQLFunction;
import com.easy.query.core.func.def.impl.JSONObjectExtractSQLFunction;
import com.easy.query.core.func.def.impl.JSONObjectFieldSQLFunction;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/10/13 18:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class PgSQLFuncImpl extends SQLFuncImpl {


    @Override
    public SQLFunction nullOrDefault(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction dateTimeFormat(SQLActionExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new PgSQLDateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
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

    @Override
    public DistinctDefaultSQLFunction sum(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLSumSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLCountSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction random() {
        return new PgSQLRandomSQLFunction();
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLAvgSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction joining(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean distinct) {
        return new PgSQLJoiningSQLFunction(getColumnExpressions(sqlExpression),distinct);
    }

    @Override
    public SQLFunction cast(SQLActionExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new PgSQLCastSQLFunction(getColumnExpressions(sqlExpression),targetClazz);
    }

    @Override
    public SQLFunction plusDateTime(SQLActionExpression1<ColumnFuncSelector> sqlExpression, long duration, TimeUnit timeUnit) {
        return new PgSQLDateTimePlusSQLFunction(getColumnExpressions(sqlExpression),duration,timeUnit);
    }

    @Override
    public SQLFunction plusDateTime2(SQLActionExpression1<ColumnFuncSelector> sqlExpression, TimeUnitEnum timeUnit) {
        return new PgSQLDateTime2PlusSQLFunction(getColumnExpressions(sqlExpression),timeUnit);
    }

    @Override
    public SQLFunction plusDateTimeMonths(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLDateTimePlusMonthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction plusDateTimeYears(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLDateTimePlusYearSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeProperty(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new PgSQLDateTimePropertySQLFunction(getColumnExpressions(sqlExpression),dateTimeUnitEnum);
    }

    @Override
    public SQLFunction duration(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new PgSQLDateTimeDurationSQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction duration2(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new PgSQLDateTimeDuration2SQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction math(SQLActionExpression1<ColumnFuncSelector> sqlExpression, MathMethodEnum mathMethodEnum) {
        return new PgSQLMathSQLFunction(getColumnExpressions(sqlExpression),mathMethodEnum);
    }

    @Override
    public SQLFunction like(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        PgSQLLikeSQLFunction likeSQLFunction = new PgSQLLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if(!like){
            return not(x->x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }

    @Override
    public SQLFunction indexOf(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLIndexOfSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction maxColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLMaxMinColumnsSQLFunction(true,getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction minColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLMaxMinColumnsSQLFunction(false,getColumnExpressions(sqlExpression));
    }


    @Override
    public SQLFunction jsonObjectField(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLJSONObjectFieldSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonObjectExtract(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLJSONObjectExtractSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonObjectContainsKey(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLJSONObjectContainsKeySQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonArrayByIndex(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLJSONArrayByIndexSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonArrayExtractByIndex(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLJSONArrayExtractByIndexSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonArrayLength(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new PgSQLJSONArrayLengthSQLFunction(getColumnExpressions(sqlExpression));
    }
}
