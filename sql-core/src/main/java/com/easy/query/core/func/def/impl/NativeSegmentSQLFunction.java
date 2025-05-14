package com.easy.query.core.func.def.impl;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.Collections;
import java.util.List;

/**
 * create time 2024/2/27 20:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class NativeSegmentSQLFunction extends AbstractExpressionSQLFunction {

    private final String sqlSegment;
    private final SQLActionExpression1<SQLNativeChainExpressionContext> consume;

    public NativeSegmentSQLFunction(String  sqlSegment, SQLActionExpression1<SQLNativeChainExpressionContext> consume) {
        this.sqlSegment = sqlSegment;
        this.consume = consume;
    }


    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return sqlSegment;
    }

    @Override
    public int paramMarks() {
        throw new EasyQueryInvalidOperationException("NativeSQLFunction paramMarks Invalid Operation");
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return Collections.emptyList();
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        consume.apply(context);
    }
}
