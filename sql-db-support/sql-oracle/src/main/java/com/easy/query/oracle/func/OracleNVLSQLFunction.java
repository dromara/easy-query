package com.easy.query.oracle.func;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/10/13 18:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleNVLSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;

    public OracleNVLSQLFunction(List<ColumnExpression> columnExpressions) {

        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment() {
        Iterable<String> params = EasyCollectionUtil.select(columnExpressions, (t, i) -> "{" + i + "}");
        return String.format("NVL(%s)", String.join(",", params));
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    public void consume0(SQLNativeChainExpressionContext context) {
        invokeExpression(context);
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
