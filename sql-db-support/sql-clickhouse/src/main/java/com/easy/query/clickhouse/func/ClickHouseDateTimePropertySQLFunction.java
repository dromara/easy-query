package com.easy.query.clickhouse.func;

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
public class ClickHouseDateTimePropertySQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final DateTimeUnitEnum dateTimeUnitEnum;

    public ClickHouseDateTimePropertySQLFunction(List<ColumnExpression> columnExpressions, DateTimeUnitEnum dateTimeUnitEnum) {

        this.columnExpressions = columnExpressions;
        this.dateTimeUnitEnum = dateTimeUnitEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(EasyCollectionUtil.isEmpty(columnExpressions)){
            throw new IllegalArgumentException("columnExpressions is empty");
        }
        switch (dateTimeUnitEnum){
            case DayOfYear:return "toDayOfYear({0})";
            case DayOfWeek:return "(toDayOfWeek({0})-1)";
            case DayOfWeekSunDayEndDay:return "(CASE WHEN (toDayOfWeek({0})-1) = 0 THEN 7 ELSE (toDayOfWeek({0})-1) END)";
            case Year:return "toYear({0})";
            case Month:return "toMonth({0})";
            case Day:return "toDayOfMonth({0})";
            case Hour:return "toHour({0})";
            case Minute:return "toMinute({0})";
            case Second:return "toSecond({0})";
        }
        throw new UnsupportedOperationException("不支持当前属性获取:"+ dateTimeUnitEnum);
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
