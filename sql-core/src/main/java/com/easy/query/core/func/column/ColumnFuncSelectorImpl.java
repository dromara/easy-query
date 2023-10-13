package com.easy.query.core.func.column;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.column.impl.ColumnFuncExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFuncFormatExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;

import java.util.List;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFuncSelectorImpl implements ColumnFuncSelector {
    private final List<ColumnExpression> concatExpressions;

    public ColumnFuncSelectorImpl(List<ColumnExpression> concatExpressions){

        this.concatExpressions = concatExpressions;
    }
    @Override
    public ColumnFuncSelector column(String property) {
        concatExpressions.add(new ColumnFuncExpressionImpl(null,property));
        return this;
    }

    @Override
    public ColumnFuncSelector column(SQLTableOwner tableOwner, String property) {
        concatExpressions.add(new ColumnFuncExpressionImpl(tableOwner.getTable(),property));
        return this;
    }

    @Override
    public ColumnFuncSelector value(Object val) {
        concatExpressions.add(new ColumnFuncValueExpressionImpl(val));
        return this;
    }

    @Override
    public ColumnFuncSelector format(Object valFormat) {
        concatExpressions.add(new ColumnFuncFormatExpressionImpl(valFormat));
        return this;
    }
}
