package com.easy.query.kingbase.es.func;

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
public class KingbaseESDateTimePropertySQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final DateTimeUnitEnum dateTimeUnitEnum;

    public KingbaseESDateTimePropertySQLFunction(List<ColumnExpression> columnExpressions, DateTimeUnitEnum dateTimeUnitEnum) {

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
                return "extract(doy from ({0})::timestamp)";
            case DayOfWeek:
                return "extract(dow from ({0})::timestamp)";
            case DayOfWeekSunDayEndDay:
                return "(CASE WHEN extract(dow from ({0})::timestamp) = 0 THEN 7 ELSE extract(dow from ({0})::timestamp) END)";
            case Year:
                return "extract(year from ({0})::timestamp)";
            case Month:
                return "extract(month from ({0})::timestamp)";
            case Day:
                return "extract(day from ({0})::timestamp)";
            case Hour:
                return "extract(hour from ({0})::timestamp)";
            case Minute:
                return "extract(minute from ({0})::timestamp)";
            case Second:
                return "extract(second from ({0})::timestamp)";
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
