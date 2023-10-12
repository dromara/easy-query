package com.easy.query.core.func.concat;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.concat.impl.ConcatColumnExpressionImpl;
import com.easy.query.core.func.concat.impl.ConcatFormatExpressionImpl;
import com.easy.query.core.func.concat.impl.ConcatValueExpressionImpl;

import java.util.List;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultColumnConcatSelector implements ColumnConcatSelector{
    private final List<ConcatExpression> concatExpressions;

    public DefaultColumnConcatSelector(List<ConcatExpression> concatExpressions){

        this.concatExpressions = concatExpressions;
    }
    @Override
    public ColumnConcatSelector column(String property) {
        concatExpressions.add(new ConcatColumnExpressionImpl(null,property));
        return this;
    }

    @Override
    public ColumnConcatSelector column(SQLTableOwner tableOwner, String property) {
        concatExpressions.add(new ConcatColumnExpressionImpl(tableOwner.getTable(),property));
        return this;
    }

    @Override
    public ColumnConcatSelector value(Object val) {
        concatExpressions.add(new ConcatValueExpressionImpl(val));
        return this;
    }

    @Override
    public ColumnConcatSelector format(Object valFormat) {
        concatExpressions.add(new ConcatFormatExpressionImpl(valFormat));
        return this;
    }
}
