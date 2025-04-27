package com.easy.query.oracle.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractSubStringExpressionSQLFunction;

import java.util.List;

/**
 * create time 2025/4/27 16:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleIndexOfSQLFunction extends AbstractSubStringExpressionSQLFunction {

    private final List<ColumnExpression> columnExpressions;

    public OracleIndexOfSQLFunction(List<ColumnExpression> columnExpressions) {
        this.columnExpressions = columnExpressions;
    }


    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new UnsupportedOperationException("indexOf sql function must have 2 params");
        }

        return "(INSTR({0},{1},1,1) - 1)";
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
