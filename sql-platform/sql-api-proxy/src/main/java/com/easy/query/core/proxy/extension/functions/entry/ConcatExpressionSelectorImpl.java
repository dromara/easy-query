package com.easy.query.core.proxy.extension.functions.entry;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2024/1/13 21:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConcatExpressionSelectorImpl implements ConcatExpressionSelector{
    private final SQLFunc sqlFunc;
    private final ColumnFuncSelector columnFuncSelector;

    public ConcatExpressionSelectorImpl(SQLFunc sqlFunc, ColumnFuncSelector columnFuncSelector){
        this.sqlFunc = sqlFunc;

        this.columnFuncSelector = columnFuncSelector;
    }
    @Override
    public ConcatExpressionSelector value(String val) {
        columnFuncSelector.value(val);
        return this;
    }

    @Override
    public ConcatExpressionSelector format(Object valFormat) {
        columnFuncSelector.format(valFormat);
        return this;
    }

    @Override
    public ConcatExpressionSelector subQuery(Query<?> subQuery) {
        columnFuncSelector.subQuery(subQuery);
        return this;
    }

    @Override
    public ConcatExpressionSelector expression(PropTypeColumn<String> propTypeColumn) {
        if(propTypeColumn instanceof DSLSQLFunctionAvailable){
            DSLSQLFunctionAvailable functionAvailable = (DSLSQLFunctionAvailable) propTypeColumn;
            SQLFunction sqlFunction = functionAvailable.func().apply(sqlFunc);
            columnFuncSelector.sqlFunc(propTypeColumn.getTable(),sqlFunction);
        }else {
            columnFuncSelector.column(propTypeColumn.getTable(), propTypeColumn.getValue());
        }
        return this;
    }
}
