package com.easy.query.tsdb.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2025/10/14 14:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class TSDBMaxMinColumnsSQLFunction extends AbstractExpressionSQLFunction {
    private final boolean isMax;
    private final List<ColumnExpression> columnExpressions;

    public TSDBMaxMinColumnsSQLFunction(boolean isMax, List<ColumnExpression> columnExpressions) {
        this.isMax = isMax;

        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        Iterable<String> params = EasyCollectionUtil.select(columnExpressions, (t, i) -> "{" + i + "}");
        if(isMax){
            return String.format("GREATEST(%s)", String.join(",", params));
        }
        return String.format("LEAST(%s)", String.join(",", params));
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