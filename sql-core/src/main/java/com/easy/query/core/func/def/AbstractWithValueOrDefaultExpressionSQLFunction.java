package com.easy.query.core.func.def;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFunctionExpressionImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/10/19 08:42
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractWithValueOrDefaultExpressionSQLFunction extends AbstractExpressionSQLFunction implements DistinctDefaultSQLFunction {

    protected final List<ColumnExpression> columnExpressions;
    protected boolean distinct = false;
    protected boolean hasDefaultValue;
    public AbstractWithValueOrDefaultExpressionSQLFunction(List<ColumnExpression> columnExpressions){

        this.columnExpressions = columnExpressions;
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }

    @Override
    public DistinctDefaultSQLFunction distinct(boolean dist) {
        distinct=dist;
        return this;
    }

    @Override
    public DistinctDefaultSQLFunction nullDefault(Object value) {
        if(hasDefaultValue){
            throw new EasyQueryInvalidOperationException("can not repeat value or default");
        }
        if (value != null) {
            hasDefaultValue = true;
            List<ColumnExpression> columnCopyExpressions = new ArrayList<>(columnExpressions);
            columnCopyExpressions.add(new ColumnFuncValueExpressionImpl(value));
            SQLFunction valueOrDefaultSQLFunction = createValueOrDefaultSQLFunction(columnCopyExpressions);
            ColumnFunctionExpressionImpl columnFunctionExpression = new ColumnFunctionExpressionImpl(null,valueOrDefaultSQLFunction);
            columnExpressions.clear();
            columnExpressions.add(columnFunctionExpression);
        }
        return this;
    }
    protected abstract SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions);
}
