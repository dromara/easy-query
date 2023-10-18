package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.column.ColumnFuncSelectorImpl;
import com.easy.query.core.func.def.AbsSQLFunction;
import com.easy.query.core.func.def.ConcatSQLFunction;
import com.easy.query.core.func.def.DateTimeFormatSQLFunction;
import com.easy.query.core.func.def.DateTimeSQLFormatSQLFunction;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.func.def.NowSQLFunction;
import com.easy.query.core.func.def.RoundSQLFunction;
import com.easy.query.core.func.def.UtcNowSQLFunction;
import com.easy.query.core.func.def.ValueOrDefaultSQLFunction;
import com.easy.query.core.func.def.impl.AvgSQLFunctionImpl;
import com.easy.query.core.func.def.impl.CountSQLFunctionImpl;
import com.easy.query.core.func.def.impl.MaxSQLFunctionImpl;
import com.easy.query.core.func.def.impl.MinSQLFunctionImpl;
import com.easy.query.core.func.def.impl.SumSQLFunctionImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/10/5 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLFuncImpl implements SQLFunc {
    protected TableAvailable getTable(SQLTableOwner tableOwner) {
        return EasyObjectUtil.getValueOrNull(tableOwner, SQLTableOwner::getTable);
    }

    protected List<ColumnExpression> getColumnExpressions(SQLExpression1<ColumnFuncSelector> sqlExpression){
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new ColumnFuncSelectorImpl(columnExpressions));
        return columnExpressions;
    }
    @Override
    public DistinctDefaultSQLFunction sum(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new SumSQLFunctionImpl(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new CountSQLFunctionImpl(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction max(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MaxSQLFunctionImpl(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction min(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new MinSQLFunctionImpl(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        return new AvgSQLFunctionImpl(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction valueOrDefault(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new ColumnFuncSelectorImpl(columnExpressions));
        return new ValueOrDefaultSQLFunction(columnExpressions);
    }

    @Override
    public SQLFunction abs(SQLTableOwner tableOwner, String property) {
        return new AbsSQLFunction(getTable(tableOwner), property);
    }

    @Override
    public SQLFunction round(SQLTableOwner tableOwner, String property, int scale) {
        return new RoundSQLFunction(getTable(tableOwner), property, scale);
    }

    @Override
    public SQLFunction dateTimeFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new DateTimeFormatSQLFunction(getTable(tableOwner), property, javaFormat);
    }

    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new DateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new ConcatSQLFunction(concatExpressions);
    }

//    @Override
//    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return new StringJoinSQLFunction(separator, concatExpressions);
//    }

    @Override
    public SQLFunction now() {
        return NowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction utcNow() {
        return UtcNowSQLFunction.INSTANCE;
    }
}
