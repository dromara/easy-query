package com.easy.query.duckdb.func;

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
public class DuckDBSQLDateTimeDuration2SQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final DateTimeDurationEnum durationEnum;

    public DuckDBSQLDateTimeDuration2SQLFunction(List<ColumnExpression> columnExpressions, DateTimeDurationEnum durationEnum) {

        this.columnExpressions = columnExpressions;
        this.durationEnum = durationEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
            throw new IllegalArgumentException("date time duration sql arguments != 2");
        }
        switch (durationEnum){
            case Days:return "DATEDIFF('day', {0}, {1})";
            case Hours:return "DATEDIFF('hour', {0}, {1})";
            case Minutes:return "DATEDIFF('minute', {0}, {1})";
            case Seconds:return "DATEDIFF('second', {0}, {1})";
        }
        throw new UnsupportedOperationException("un support current type:"+ durationEnum);
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
