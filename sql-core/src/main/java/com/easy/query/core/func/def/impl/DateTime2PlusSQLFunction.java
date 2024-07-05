package com.easy.query.core.func.def.impl;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DateTime2PlusSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final TimeUnitEnum timeUnit;

    public DateTime2PlusSQLFunction(List<ColumnExpression> columnExpressions, TimeUnitEnum timeUnit) {

        this.columnExpressions = columnExpressions;
        this.timeUnit = timeUnit;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("columnExpressions.size() != 2");
        }
        switch (timeUnit){
            case MILLISECONDS: return "date_add({0}, interval ({1})*1000 microsecond)";
            case SECONDS: return "date_add({0}, interval ({1}) second)";
            case MINUTES: return "date_add({0}, interval ({1}) minute)";
            case HOURS: return "date_add({0}, interval ({1}) hour)";
            case DAYS: return "date_add({0}, interval ({1}) day)";
            case MONTHS: return "date_add({0}, interval ({1}) month)";
            case YEARS: return "date_add({0}, interval ({1}) year)";
        }
        throw new UnsupportedOperationException("not support current function DateTime2PlusSQLFunction:"+ timeUnit);
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
