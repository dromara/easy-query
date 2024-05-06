package com.easy.query.api4j.func.column;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnFuncSelector<T> {
    ColumnFuncSelector getColumnFuncSelector();
   default SQLColumnFuncSelector<T> column(Property<T,?> property){
       getColumnFuncSelector().column(EasyLambdaUtil.getPropertyName(property));
       return this;
   }
    default <T2> SQLColumnFuncSelector<T> column(SQLTableOwner tableOwner, Property<T2,?> property){
        getColumnFuncSelector().column(tableOwner,EasyLambdaUtil.getPropertyName(property));
        return this;
    }
    default SQLColumnFuncSelector<T> value(Object val){
        getColumnFuncSelector().value(val);
        return this;
    }
    default SQLColumnFuncSelector<T> format(Object valFormat){
        getColumnFuncSelector().format(valFormat);
        return this;
    }
    default SQLColumnFuncSelector<T> sqlFunc(SQLFunction sqlFunction){
        getColumnFuncSelector().sqlFunc(sqlFunction);
        return this;
    }
}
