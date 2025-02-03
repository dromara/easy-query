package com.easy.query.sqlite.func;

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
public class SQLiteDateTime2PlusSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final TimeUnitEnum timeUnit;

    public SQLiteDateTime2PlusSQLFunction(List<ColumnExpression> columnExpressions, TimeUnitEnum timeUnit) {

        this.columnExpressions = columnExpressions;
        this.timeUnit = timeUnit;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("columnExpressions.size() != 2");
        }
        switch (timeUnit){
            case MILLISECONDS: return "datetime({0},(({1})/1000)||' seconds')";
            case SECONDS: return "datetime({0},({1)||' seconds')";
            case MINUTES: return "datetime({0},({1})||' minutes')";
            case HOURS: return "datetime({0},({1})||' hours')";
            case DAYS: return "datetime({0},({1})||' days')";
            case MONTHS: return "datetime({0},({1})||' months')";
            case YEARS: return "datetime({0},({1})||' years')";
        }
        throw new UnsupportedOperationException("not support current function SQLiteDateTime2PlusSQLFunction:"+ timeUnit);
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
