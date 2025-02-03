package com.easy.query.sqlite.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.List;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteRightPadSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;

    public SQLiteRightPadSQLFunction(List<ColumnExpression> columnExpressions) {

        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()<2){
            throw new IllegalArgumentException("pad right arguments < 2");
        }
        if(columnExpressions.size()==2){
            return "PADR({0}, {1})";
        }
        return "{0}||RIGHTSTR(REPLACE(PADR({0},{1}),' ',{2}),CASE WHEN {1}-LENGTH({0})<=0 THEN 0 ELSE {1}-LENGTH({0})END)";
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
