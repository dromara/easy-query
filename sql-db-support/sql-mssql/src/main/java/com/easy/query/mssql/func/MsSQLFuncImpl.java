package com.easy.query.mssql.func;

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
public class MsSQLFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction valueOrDefault(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MsSQLNullDefaultSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new MsSQLDateTimeFormatSQLFunction(getTable(tableOwner), property, javaFormat);
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
}
