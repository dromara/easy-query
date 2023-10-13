package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/10/11 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConcatSQLFunction extends AbstractExpressionSQLFunction {

    private final List<ColumnExpression> columnExpressions;

    public ConcatSQLFunction(List<ColumnExpression> concatExpressions) {
        if (EasyCollectionUtil.isEmpty(concatExpressions)) {
            throw new IllegalArgumentException("ConcatSQLFunction columns empty");
        }
        this.columnExpressions = concatExpressions;
    }

    @Override
    public String sqlSegment() {
        Iterable<String> params = EasyCollectionUtil.select(columnExpressions, (t, i) -> "{" + i + "}");
        return String.format("CONCAT(%s)", String.join(",", params));
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        invokeExpression(context);
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
