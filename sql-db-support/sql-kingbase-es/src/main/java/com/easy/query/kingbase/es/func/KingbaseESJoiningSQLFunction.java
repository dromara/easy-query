package com.easy.query.kingbase.es.func;

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
public class KingbaseESJoiningSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final boolean distinct;

    public KingbaseESJoiningSQLFunction(List<ColumnExpression> columnExpressions, boolean distinct) {

        this.columnExpressions = columnExpressions;
        this.distinct = distinct;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("joining arguments != 2");
        }
        if (distinct) {
            return "STRING_AGG(DISTINCT ({1})::TEXT,{0})";
        }
        return "STRING_AGG(({1})::TEXT,{0})";
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
