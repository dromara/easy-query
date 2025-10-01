package com.easy.query.duckdb.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.enums.TimeUnitEnum;

import java.util.List;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBSQLDateTime2PlusSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final TimeUnitEnum timeUnit;

    public DuckDBSQLDateTime2PlusSQLFunction(List<ColumnExpression> columnExpressions, TimeUnitEnum timeUnit) {

        this.columnExpressions = columnExpressions;
        this.timeUnit = timeUnit;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("columnExpressions.size() != 2");
        }
        switch (timeUnit){
            case MILLISECONDS: return "date_add({0},cast(({1})||' microsecond' as interval))";
            case SECONDS: return "date_add({0},cast(({1})||' seconds' as interval))";
            case MINUTES: return "date_add({0},cast(({1})||' minutes' as interval))";
            case HOURS: return "date_add({0},cast(({1})||' hours' as interval))";
            case DAYS: return "date_add({0},cast(({1})||' days' as interval))";
            case MONTHS: return "date_add({0},cast(({1})||' months' as interval))";
            case YEARS: return "date_add({0}, cast(({1})||' years' as interval))";
        }
        throw new UnsupportedOperationException("not support current function DuckDBSQLDateTime2PlusSQLFunction:"+ timeUnit);
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }

}
