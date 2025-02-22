package com.easy.query.sqlite.func;

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
public class SQLiteDateTimeDuration2SQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final DateTimeDurationEnum durationEnum;

    public SQLiteDateTimeDuration2SQLFunction(List<ColumnExpression> columnExpressions, DateTimeDurationEnum durationEnum) {

        this.columnExpressions = columnExpressions;
        this.durationEnum = durationEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
            throw new IllegalArgumentException("date time duration sql arguments != 2");
        }
        switch (durationEnum){
            case Days:return "(strftime('%s',{1})-strftime('%s',{0}))/86400";
            case Hours:return "(strftime('%s',{1})-strftime('%s',{0}))/3600";
            case Minutes:return "(strftime('%s',{1})-strftime('%s',{0}))/60";
            case Seconds:return "(strftime('%s',{1})-strftime('%s',{0}))";
        }
        throw new UnsupportedOperationException("不支持当前属性获取:"+ durationEnum);
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
