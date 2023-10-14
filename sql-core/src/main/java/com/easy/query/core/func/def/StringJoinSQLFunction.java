package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Objects;

/**
 * create time 2023/10/11 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class StringJoinSQLFunction extends ConcatSQLFunction {
    private final String separator;
    private final List<ColumnExpression> concatExpressions;

    public StringJoinSQLFunction(String separator, List<ColumnExpression> concatExpressions) {
        super(concatExpressions);
        this.concatExpressions = concatExpressions;
        Objects.requireNonNull(separator, "concat_ws separator can not be null");
        this.separator = separator;
    }

    @Override
    public String sqlSegment() {
        Iterable<String> params = EasyCollectionUtil.select(concatExpressions, (t, i) -> "{" + (i + 1) + "}");
        return String.format("CONCAT_WS({0},%s)", String.join(",", params));
    }

    @Override
    public int paramMarks() {
        return super.paramMarks()+1;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        context.value(separator);
        super.consume0(context);
    }

}
