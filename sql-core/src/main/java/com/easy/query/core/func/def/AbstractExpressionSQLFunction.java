package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.func.SQLFunctionTranslateImpl;
import com.easy.query.core.func.column.ColumSQLExpression;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncFormatExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.column.ColumnFunctionExpression;
import com.easy.query.core.func.column.ColumnPropertyExpression;
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

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        invokeExpression(context);
    }

    protected void invokeExpression(SQLNativeChainExpressionContext context) {
        for (ColumnExpression concatExpression : getColumnExpressions()) {
            invokeExpression0(context,concatExpression);
        }
    }

    protected void invokeExpression0(SQLNativeChainExpressionContext context, ColumnExpression columnExpression) {
        if (columnExpression instanceof ColumnPropertyExpression) {
            ColumnPropertyExpression concatColumnExpression = (ColumnPropertyExpression) columnExpression;
            TableAvailable tableOrNull = concatColumnExpression.getTableOrNull();
            if (tableOrNull == null) {
                context.expression(concatColumnExpression.getProperty());
            } else {
                context.expression(tableOrNull, concatColumnExpression.getProperty());
            }
        } else if (columnExpression instanceof ColumnFuncValueExpression) {
            ColumnFuncValueExpression concatValueExpression = (ColumnFuncValueExpression) columnExpression;
            context.value(concatValueExpression.getValue());
        } else if (columnExpression instanceof ColumnFuncFormatExpression) {
            ColumnFuncFormatExpression concatFormatExpression = (ColumnFuncFormatExpression) columnExpression;
            context.format(concatFormatExpression.getFormat());
        } else if (columnExpression instanceof ColumSQLExpression) {
            ColumSQLExpression columSQLExpression = (ColumSQLExpression) columnExpression;
            context.sql(columSQLExpression.getSQLSegment());
        } else if(columnExpression instanceof ColumnFunctionExpression){
            ColumnFunctionExpression columnFunctionExpression = (ColumnFunctionExpression) columnExpression;
            SQLSegment sqlSegment = new SQLFunctionTranslateImpl(columnFunctionExpression.getSQLFunction()).toSQLSegment(context.getExpressionContext(), context.getDefaultTable(), context.getExpressionContext().getRuntimeContext());
            context.sql(sqlSegment);
        }else {
            throw new UnsupportedOperationException(EasyClassUtil.getInstanceSimpleName(columnExpression));
        }
    }
}
