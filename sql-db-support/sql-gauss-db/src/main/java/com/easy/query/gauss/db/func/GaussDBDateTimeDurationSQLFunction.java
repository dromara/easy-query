package com.easy.query.gauss.db.func;

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
public class GaussDBDateTimeDurationSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final DateTimeDurationEnum durationEnum;

    public GaussDBDateTimeDurationSQLFunction(List<ColumnExpression> columnExpressions, DateTimeDurationEnum durationEnum) {

        this.columnExpressions = columnExpressions;
        this.durationEnum = durationEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
            throw new IllegalArgumentException("date time duration sql arguments != 2");
        }
        switch (durationEnum){
            case Days:return "(extract(epoch from ({0})::timestamp-({1})::timestamp)/86400)::int";
            case Hours:return "(extract(epoch from ({0})::timestamp-({1})::timestamp)/3600)::int";
            case Minutes:return "(extract(epoch from ({0})::timestamp-({1})::timestamp)/60)::int";
            case Seconds:return "(extract(epoch from ({0})::timestamp-({1})::timestamp))::int";
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
