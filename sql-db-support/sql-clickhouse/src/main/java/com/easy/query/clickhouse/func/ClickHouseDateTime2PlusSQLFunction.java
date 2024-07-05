package com.easy.query.clickhouse.func;

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
public class ClickHouseDateTime2PlusSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final TimeUnitEnum timeUnit;

    public ClickHouseDateTime2PlusSQLFunction(List<ColumnExpression> columnExpressions, TimeUnitEnum timeUnit) {

        this.columnExpressions = columnExpressions;
        this.timeUnit = timeUnit;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("columnExpressions.size() != 2");
        }
        switch (timeUnit){
            case MILLISECONDS: return "addSeconds(toDateTime({0}), {1}/1000)";
            case SECONDS: return "addSeconds(toDateTime({0}),{1})";
            case MINUTES: return "addMinutes(toDateTime({0}),{1})";
            case HOURS: return "addHours(toDateTime({0}), {1})";
            case DAYS: return "addDays(toDateTime({0}), {1})";
            case MONTHS: return "addMonths(toDateTime({0}),{1})";
            case YEARS: return "addYears(toDateTime({0}),{1})";
        }
        throw new UnsupportedOperationException("not support current function ClickHouseDateTime2PlusSQLFunction:"+ timeUnit);
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
