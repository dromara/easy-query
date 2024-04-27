package com.easy.query.clickhouse.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.impl.UtcNowSQLFunction;

import java.util.concurrent.TimeUnit;

/**
 * create time 2023/12/21 10:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClickHouseFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction stringCompareTo(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new ClickHouseStringCompareToSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction cast(SQLExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new ClickHouseCastSQLFunction(getColumnExpressions(sqlExpression),targetClazz);
    }

    @Override
    public SQLFunction like(SQLExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        ClickHouseLikeSQLFunction likeSQLFunction = new ClickHouseLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if(!like){
            return not(x->x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }
    @Override
    public SQLFunction plusDateTime(SQLExpression1<ColumnFuncSelector> sqlExpression, long duration, TimeUnit timeUnit) {
        return new ClickHouseDateTimePlusSQLFunction(getColumnExpressions(sqlExpression),duration,timeUnit);
    }
    @Override
    public SQLFunction plusDateTimeMonths(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new ClickHouseDateTimePlusMonthSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction plusDateTimeYears(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new ClickHouseDateTimePlusYearSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction dateTimeProperty(SQLExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new ClickHouseDateTimePropertySQLFunction(getColumnExpressions(sqlExpression),dateTimeUnitEnum);
    }
    @Override
    public SQLFunction dateTimeFormat(SQLExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new ClickHouseDateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
    }
    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new ClickHouseDateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }
    @Override
    public SQLFunction join(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new ClickHouseJoinSQLFunction(getColumnExpressions(sqlExpression));
    }
    @Override
    public SQLFunction utcNow() {
        return ClickHouseUtcNowSQLFunction.INSTANCE;
    }
}