package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractSubStringExpressionSQLFunction;

import java.util.List;

/**
 * create time 2023/10/18 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class IndexOfSQLFunction extends AbstractSubStringExpressionSQLFunction {

    private final List<ColumnExpression> columnExpressions;

    public IndexOfSQLFunction(List<ColumnExpression> columnExpressions) {
        this.columnExpressions = columnExpressions;
    }
    

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new UnsupportedOperationException("indexOf sql function must have 2 params");
        }

        return "(LOCATE({1},{0}) - 1)";
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
