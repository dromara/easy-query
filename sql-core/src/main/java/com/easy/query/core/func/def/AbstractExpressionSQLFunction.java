package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncExpression;
import com.easy.query.core.func.column.ColumnFuncFormatExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.util.EasyClassUtil;

import java.util.List;

/**
 * create time 2023/10/13 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractExpressionSQLFunction extends AbstractSQLFunction {
    protected abstract List<ColumnExpression> getColumnExpressions();

    protected void invokeExpression(SQLNativeChainExpressionContext context) {
        for (ColumnExpression concatExpression : getColumnExpressions()) {
            invokeExpression0(context,concatExpression);
        }
    }

    protected void invokeExpression0(SQLNativeChainExpressionContext context, ColumnExpression concatExpression) {
        if (concatExpression instanceof ColumnFuncExpression) {
            ColumnFuncExpression concatColumnExpression = (ColumnFuncExpression) concatExpression;
            TableAvailable tableOrNull = concatColumnExpression.getTableOrNull();
            if (tableOrNull == null) {
                context.expression(concatColumnExpression.getProperty());
            } else {
                context.expression(tableOrNull, concatColumnExpression.getProperty());
            }
        } else if (concatExpression instanceof ColumnFuncValueExpression) {
            ColumnFuncValueExpression concatValueExpression = (ColumnFuncValueExpression) concatExpression;
            context.value(concatValueExpression.getValue());

        } else if (concatExpression instanceof ColumnFuncFormatExpression) {
            ColumnFuncFormatExpression concatFormatExpression = (ColumnFuncFormatExpression) concatExpression;
            context.format(concatFormatExpression.getFormat());
        } else {
            throw new UnsupportedOperationException(EasyClassUtil.getInstanceSimpleName(concatExpression));
        }
    }
}
