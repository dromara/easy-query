package com.easy.query.dameng.func;

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
public class DamengDateTimePropertySQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final DateTimeUnitEnum dateTimeUnitEnum;

    public DamengDateTimePropertySQLFunction(List<ColumnExpression> columnExpressions, DateTimeUnitEnum dateTimeUnitEnum) {

        this.columnExpressions = columnExpressions;
        this.dateTimeUnitEnum = dateTimeUnitEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(EasyCollectionUtil.isEmpty(columnExpressions)){
            throw new IllegalArgumentException("columnExpressions is empty");
        }
        switch (dateTimeUnitEnum){
            case DayOfYear:return "cast(to_char({0},'DDD') as number)";
            case DayOfWeek:return "case when to_char({0},'D')='7' then 0 else cast(to_char({0},'D') as number) end";
            case Year:return "cast(to_char({0},'YYYY') as number)";
            case Month:return "cast(to_char({0},'MM') as number)";
            case Day:return "cast(to_char({0},'DD') as number)";
            case Hour:return "cast(to_char({0},'HH24') as number)";
            case Minute:return "cast(to_char({0},'MI') as number)";
            case Second:return "cast(to_char({0},'SS') as number)";
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
