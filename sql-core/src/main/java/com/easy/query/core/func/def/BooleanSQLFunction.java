package com.easy.query.core.func.def;

import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;

import java.util.List;

/**
 * create time 2024/4/8 16:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class BooleanSQLFunction implements SQLFunction{

    private final SQLFunction sqlFunction;

    public BooleanSQLFunction(SQLFunction sqlFunction) {
        this.sqlFunction = sqlFunction;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return sqlFunction.sqlSegment(defaultTable);
    }

    @Override
    public int paramMarks() {
        return sqlFunction.paramMarks();
    }

    @Override
    public void consume(SQLNativeChainExpressionContext context) {
        sqlFunction.consume(context);
    }

    @Override
    public AggregationType getAggregationType() {
        return sqlFunction.getAggregationType();
    }
}
