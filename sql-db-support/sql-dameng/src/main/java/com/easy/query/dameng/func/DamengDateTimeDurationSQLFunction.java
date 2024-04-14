package com.easy.query.dameng.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;

import java.util.List;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengDateTimeDurationSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final DateTimeDurationEnum durationEnum;

    public DamengDateTimeDurationSQLFunction(List<ColumnExpression> columnExpressions, DateTimeDurationEnum durationEnum) {

        this.columnExpressions = columnExpressions;
        this.durationEnum = durationEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
            throw new IllegalArgumentException("date time duration sql arguments != 2");
        }
        switch (durationEnum){
            case Days:return "EXTRACT(DAY FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1}))";
            case Hours:return "(EXTRACT(DAY FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1}))*24+EXTRACT(HOUR FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1})))";
            case Minutes:return "(EXTRACT(DAY FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1}))*1440+EXTRACT(HOUR FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1}))*60+EXTRACT(MINUTE FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1})))";
            case Seconds:return "(EXTRACT(DAY FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1}))*86400+EXTRACT(HOUR FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1}))*3600+EXTRACT(MINUTE FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1}))*60+EXTRACT(SECOND FROM (CAST({0} AS TIMESTAMP WITH TIME ZONE)-{1})))";
        }
        throw new UnsupportedOperationException("不支持当前函数DamengDateTimeDurationSQLFunction:"+ durationEnum);
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
