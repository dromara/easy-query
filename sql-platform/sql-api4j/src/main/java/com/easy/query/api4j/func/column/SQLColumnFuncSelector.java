package com.easy.query.api4j.func.column;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
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
    default SQLColumnFuncSelector<T> column(EntitySQLTableOwner<T> tableOwner, Property<T,?> property){
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
}
