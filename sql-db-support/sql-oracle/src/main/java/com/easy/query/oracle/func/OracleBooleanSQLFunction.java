package com.easy.query.oracle.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.List;

/**
 * create time 2024/4/8 16:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleBooleanSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;

    public OracleBooleanSQLFunction(List<ColumnExpression> columnExpressions) {
        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return "(CASE WHEN {0} THEN {1} ELSE {2} END)";
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
