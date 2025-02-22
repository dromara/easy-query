package com.easy.query.dameng.func;

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
public class DamengDateTime2PlusSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final TimeUnitEnum timeUnit;

    public DamengDateTime2PlusSQLFunction(List<ColumnExpression> columnExpressions, TimeUnitEnum timeUnit) {

        this.columnExpressions = columnExpressions;
        this.timeUnit = timeUnit;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("columnExpressions.size() != 2");
        }
        switch (timeUnit){
            case MILLISECONDS: return "({0}+({1})/86400000.0)";
            case SECONDS: return "({0}+({1})/86400.0)";
            case MINUTES: return "({0}+({1})/1440.0)";
            case HOURS: return "({0}+({1})/24.0)";
            case DAYS: return "({0}+{1})";
            case MONTHS: return "add_months({0},{1})";
            case YEARS: return "add_months({0},({1})*12)";
        }
        throw new UnsupportedOperationException("not support current function DamengDateTime2PlusSQLFunction:"+ timeUnit);
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
