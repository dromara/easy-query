package com.easy.query.core.func.def;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncExpression;

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

    protected TableAvailable getTableByExpression(TableAvailable defaultTable,ColumnFuncExpression columnFuncExpression) {
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
        context.keepStyle();
        consume0(context);
//        if(EasyStringUtil.isNotBlank(alias)){
//            context.setAlias(alias);
//        }else if(EasyStringUtil.isNotBlank(propertyAlias)){
//            context.setPropertyAlias(propertyAlias);
//        }
    }

    protected abstract void consume0(SQLNativeChainExpressionContext context);
}
