package com.easy.query.core.func.def;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnPropertyExpression;

/**
 * create time 2023/10/12 13:27
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSQLFunction implements SQLFunction {
//    protected String alias;
//    protected String propertyAlias;
//    protected TableAvailable defaultTable;

//    @Override
//    public void setAlias(String alias) {
//        this.alias = alias;
//    }
//
//    @Override
//    public void setPropertyAlias(String propertyAlias) {
//        this.propertyAlias = propertyAlias;
//    }
//
//    @Override
//    public void setDefaultTable(TableAvailable table) {
//        this.defaultTable=table;
//    }

    protected TableAvailable getTableByExpression(TableAvailable defaultTable, ColumnPropertyExpression columnFuncExpression) {
        if (columnFuncExpression.getTableOrNull() != null) {
            return columnFuncExpression.getTableOrNull();
        }
        if (defaultTable != null) {
            return defaultTable;
        }
        throw new EasyQueryException("cant get expression table info");
    }

    @Override
    public void consume(SQLNativeChainExpressionContext context) {
        consume0(context);
    }

    protected abstract void consume0(SQLNativeChainExpressionContext context);

    @Override
    public AggregationType getAggregationType() {
        return AggregationType.UNKNOWN;
    }
}
