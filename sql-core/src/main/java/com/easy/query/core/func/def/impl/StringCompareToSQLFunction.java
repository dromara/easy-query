package com.easy.query.core.func.def.impl;

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
public class StringCompareToSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;

    public StringCompareToSQLFunction(List<ColumnExpression> columnExpressions) {

        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()<2){
            throw new IllegalArgumentException("compare to arguments < 2");
        }
        return "(CASE WHEN {0} = {1} THEN 0 WHEN {0} > {1} THEN 1 ELSE -1 END)";
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
