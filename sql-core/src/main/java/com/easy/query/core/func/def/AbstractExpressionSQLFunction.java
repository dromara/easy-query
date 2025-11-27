package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.SQLFunctionTranslateImpl;
import com.easy.query.core.func.column.ColumSQLExpression;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncFormatExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.column.ColumnFunctionExpression;
import com.easy.query.core.func.column.ColumnLazyFunctionExpression;
import com.easy.query.core.func.column.ColumnMultiValueExpression;
import com.easy.query.core.func.column.ColumnNameExpression;
import com.easy.query.core.func.column.ColumnPropertyExpression;
import com.easy.query.core.func.column.ColumnSubQueryExpression;
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
//            if(concatColumnExpression.getProperty()!=null){
//
//            }
            if (tableOrNull == null) {
                context.expression(concatColumnExpression.getProperty());
            } else {
                context.expression(tableOrNull, concatColumnExpression.getProperty());
            }
        }else if (columnExpression instanceof ColumnNameExpression) {
            ColumnNameExpression columnNameExpression = (ColumnNameExpression) columnExpression;
            TableAvailable table = columnNameExpression.getTableNotNull();
            String columnName = columnNameExpression.getColumnName();

            context.columnName(table, columnName);
        }else if (columnExpression instanceof ColumnFuncValueExpression) {
            ColumnFuncValueExpression concatValueExpression = (ColumnFuncValueExpression) columnExpression;
            context.value(concatValueExpression.getValue());
        } else if (columnExpression instanceof ColumnMultiValueExpression) {
            ColumnMultiValueExpression columnMultiValueExpression = (ColumnMultiValueExpression) columnExpression;
            context.collection(columnMultiValueExpression.getValues());
        } else if (columnExpression instanceof ColumnFuncFormatExpression) {
            ColumnFuncFormatExpression stringFormatExpression = (ColumnFuncFormatExpression) columnExpression;
            context.format(stringFormatExpression.getFormat());
        } else if (columnExpression instanceof ColumSQLExpression) {
            ColumSQLExpression columSQLExpression = (ColumSQLExpression) columnExpression;
            context.sql(columSQLExpression.getSQLSegment());
        } else if(columnExpression instanceof ColumnFunctionExpression){
            ColumnFunctionExpression columnFunctionExpression = (ColumnFunctionExpression) columnExpression;
            TableAvailable tableOrNull = columnFunctionExpression.getTableOrNull();
            TableAvailable sqlFunctionTable = tableOrNull == null ? context.getDefaultTable() : tableOrNull;
            SQLSegment sqlSegment = new SQLFunctionTranslateImpl(columnFunctionExpression.getSQLFunction()).toSQLSegment(context.getExpressionContext(), sqlFunctionTable, context.getRuntimeContext(),null);
            context.sql(sqlSegment);
        } else if(columnExpression instanceof ColumnLazyFunctionExpression){
            ColumnLazyFunctionExpression columnFunctionExpression = (ColumnLazyFunctionExpression) columnExpression;
            TableAvailable tableOrNull = columnFunctionExpression.getTableOrNull();
            TableAvailable sqlFunctionTable = tableOrNull == null ? context.getDefaultTable() : tableOrNull;
            SQLFunction sqlFunction = columnFunctionExpression.getSQLFunctionCreator().apply(context.getRuntimeContext().fx());
            SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction).toSQLSegment(context.getExpressionContext(), sqlFunctionTable, context.getRuntimeContext(),null);
            context.sql(sqlSegment);
        }else if(columnExpression instanceof ColumnSubQueryExpression){
            ColumnSubQueryExpression columnSubQueryExpression = (ColumnSubQueryExpression) columnExpression;
            context.expression(columnSubQueryExpression.getQuery());
        } else {
            throw new UnsupportedOperationException(EasyClassUtil.getInstanceSimpleName(columnExpression));
        }
    }
}
