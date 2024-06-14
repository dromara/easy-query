package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/18 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropTypeColumn<TProperty> extends PropTypeSetColumn<TProperty>{
    @Override
    <TR> PropTypeColumn<TR> asAnyType(Class<TR> clazz);
    static <TR> void selectColumn(AsSelector asSelector, PropTypeColumn<TR> column){

        if(column instanceof DSLSQLFunctionAvailable){
            SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) column).func().apply(asSelector.getRuntimeContext().fx());
            asSelector.sqlFunc(column.getTable(),sqlFunction);
        }else{
            column.accept(asSelector);
        }
    }
    static <TR> void columnFuncSelector(ColumnFuncSelector columnFuncSelector, PropTypeColumn<TR> column){
        if(column instanceof DSLSQLFunctionAvailable){
            SQLFunc fx = column.getEntitySQLContext().getRuntimeContext().fx();
            SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) column).func().apply(fx);
            columnFuncSelector.sqlFunc(column.getTable(),sqlFunction);
        }else{
            columnFuncSelector.column(column.getTable(),column.getValue());
        }
    }
}
