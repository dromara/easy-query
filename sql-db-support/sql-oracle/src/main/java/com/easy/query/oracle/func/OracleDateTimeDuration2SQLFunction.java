package com.easy.query.oracle.func;

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
public class OracleDateTimeDuration2SQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final DateTimeDurationEnum durationEnum;

    public OracleDateTimeDuration2SQLFunction(List<ColumnExpression> columnExpressions, DateTimeDurationEnum durationEnum) {

        this.columnExpressions = columnExpressions;
        this.durationEnum = durationEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
            throw new IllegalArgumentException("date time duration sql arguments != 2");
        }
        switch (durationEnum){
            case Days:return "EXTRACT(DAY FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY'))";
            case Hours:return "(EXTRACT(DAY FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY'))*24+EXTRACT(HOUR FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY')))";
            case Minutes:return "(EXTRACT(DAY FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY'))*1440+EXTRACT(HOUR FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY'))*60+EXTRACT(MINUTE FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY')))";
            case Seconds:return "(EXTRACT(DAY FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY'))*86400+EXTRACT(HOUR FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY'))*3600+EXTRACT(MINUTE FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY'))*60+EXTRACT(SECOND FROM NUMTODSINTERVAL(({1}+0)-({0}+0),'DAY')))";
        }
        throw new UnsupportedOperationException("不支持当前函数OracleDateTimeDurationSQLFunction:"+ durationEnum);
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
