package com.easy.query.duckdb.func;

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

import java.util.List;

/**
 * create time 2023/10/13 18:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBSQLFuncImpl extends SQLFuncImpl {

    @Override
    public SQLFunction nullOrDefault(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBSQLNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction dateTimeFormat(SQLActionExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new DuckDBSQLDateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
    }
    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new DuckDBSQLDateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new DuckDBSQLConcatSQLFunction(concatExpressions);
    }
//    @Override
//    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return new DuckDBSQLStringJoinSQLFunction(separator, concatExpressions);
//    }

    @Override
    public SQLFunction now() {
        return DuckDBSQLNowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction utcNow() {
        return DuckDBSQLUtcNowSQLFunction.INSTANCE;
    }

    @Override
    public DistinctDefaultSQLFunction sum(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBSQLSumSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBSQLCountSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction random() {
        return new DuckDBSQLRandomSQLFunction();
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBSQLAvgSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction joining(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean distinct) {
        return new DuckDBSQLJoiningSQLFunction(getColumnExpressions(sqlExpression),distinct);
    }

    @Override
    public SQLFunction cast(SQLActionExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new DuckDBSQLCastSQLFunction(getColumnExpressions(sqlExpression),targetClazz);
    }

    @Override
    public SQLFunction plusDateTime2(SQLActionExpression1<ColumnFuncSelector> sqlExpression, TimeUnitEnum timeUnit) {
        return new DuckDBSQLDateTime2PlusSQLFunction(getColumnExpressions(sqlExpression),timeUnit);
    }

    @Override
    public SQLFunction dateTimeProperty(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new DuckDBSQLDateTimePropertySQLFunction(getColumnExpressions(sqlExpression),dateTimeUnitEnum);
    }

    @Override
    public SQLFunction duration(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new DuckDBSQLDateTimeDurationSQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction duration2(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new DuckDBSQLDateTimeDuration2SQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction math(SQLActionExpression1<ColumnFuncSelector> sqlExpression, MathMethodEnum mathMethodEnum) {
        return new DuckDBSQLMathSQLFunction(getColumnExpressions(sqlExpression),mathMethodEnum);
    }

    @Override
    public SQLFunction like(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        DuckDBSQLLikeSQLFunction likeSQLFunction = new DuckDBSQLLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if(!like){
            return not(x->x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }

    @Override
    public SQLFunction indexOf(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBSQLIndexOfSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction maxColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBSQLMaxMinColumnsSQLFunction(true,getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction minColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBSQLMaxMinColumnsSQLFunction(false,getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction jsonObjectField(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBJSONObjectFieldSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonObjectExtract(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBJSONObjectExtractSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonObjectContainsKey(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBJSONObjectContainsKeySQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonArrayByIndex(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBJSONArrayByIndexSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonArrayExtractByIndex(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBJSONArrayExtractByIndexSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonArrayLength(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DuckDBJSONArrayLengthSQLFunction(getColumnExpressions(sqlExpression));
    }

}
