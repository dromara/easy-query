package com.easy.query.kingbase.es.func;

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
public class KingbaseESDateTime2PlusSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final TimeUnitEnum timeUnit;

    public KingbaseESDateTime2PlusSQLFunction(List<ColumnExpression> columnExpressions, TimeUnitEnum timeUnit) {

        this.columnExpressions = columnExpressions;
        this.timeUnit = timeUnit;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("columnExpressions.size() != 2");
        }
        switch (timeUnit){
            case MILLISECONDS: return "(({0})::timestamp+(({1})||' milliseconds')::interval)";
            case SECONDS: return "(({0})::timestamp+(({1})||' second')::interval)";
            case MINUTES: return "(({0})::timestamp+(({1})||' minute')::interval)";
            case HOURS: return "(({0})::timestamp+(({1})||' hour')::interval)";
            case DAYS: return "(({0})::timestamp+(({1})||' day')::interval)";
            case MONTHS: return "(({0})::timestamp+(({1})||' month')::interval)";
            case YEARS: return "(({0})::timestamp+(({1})||' year')::interval)";
        }
        throw new UnsupportedOperationException("not support current function KingbaseESDateTime2PlusSQLFunction:"+ timeUnit);
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
