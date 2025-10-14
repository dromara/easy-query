package com.easy.query.core.func.def.impl;

import com.easy.query.core.common.KeywordTool;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.List;

/**
 * create time 2025/10/14 10:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class MaxMinColumnsSQLFunction extends AbstractExpressionSQLFunction {
    private final boolean isMax;
    private final List<ColumnExpression> columnExpressions;

    public MaxMinColumnsSQLFunction(boolean isMax,List<ColumnExpression> columnExpressions) {
        this.isMax = isMax;

        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return buildMaxUnionSql(columnExpressions);
    }

    public String buildMaxUnionSql(List<ColumnExpression> columns) {
        StringBuilder sb = new StringBuilder();
        sb.append("(SELECT ").append(isMax ? "MAX" : "MIN").append("(__mmc.").append(KeywordTool.MAX_COLUMN).append(") FROM (");

        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sb.append(" UNION ALL ");
            }
            sb.append("SELECT {").append(i).append("} AS "+KeywordTool.MAX_COLUMN);
        }

        sb.append(") __mmc)");
        return sb.toString();
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
