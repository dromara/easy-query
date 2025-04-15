package com.easy.query.sqlite.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteDateTimePropertySQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final DateTimeUnitEnum dateTimeUnitEnum;

    public SQLiteDateTimePropertySQLFunction(List<ColumnExpression> columnExpressions, DateTimeUnitEnum dateTimeUnitEnum) {

        this.columnExpressions = columnExpressions;
        this.dateTimeUnitEnum = dateTimeUnitEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (EasyCollectionUtil.isEmpty(columnExpressions)) {
            throw new IllegalArgumentException("columnExpressions is empty");
        }
        switch (dateTimeUnitEnum) {
            case DayOfYear:
                return "CAST(strftime('%j',{0}) AS INTEGER)";
            case DayOfWeek:
                return "CAST(strftime('%w',{0}) AS INTEGER)";
            case DayOfWeekSunDayLastDay:
                return "(CASE WHEN CAST(strftime('%w',{0}) AS INTEGER) = 0 THEN 7 ELSE CAST(strftime('%w',{0}) AS INTEGER) END)";
            case Year:
                return "CAST(strftime('%Y',{0}) AS INTEGER)";
            case Month:
                return "CAST(strftime('%m',{0}) AS INTEGER)";
            case Day:
                return "CAST(strftime('%d',{0}) AS INTEGER)";
            case Hour:
                return "CAST(strftime('%H',{0}) AS INTEGER)";
            case Minute:
                return "CAST(strftime('%M',{0}) AS INTEGER)";
            case Second:
                return "CAST(strftime('%S',{0}) AS INTEGER)";
        }
        throw new UnsupportedOperationException("不支持当前属性获取:" + dateTimeUnitEnum);
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
