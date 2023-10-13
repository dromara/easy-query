package com.easy.query.api4kt.func.column;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.func.column.ColumnFuncSelector;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtColumnFuncSelector<T> {
    ColumnFuncSelector getColumnFuncSelector();
   default SQLKtColumnFuncSelector<T> column(KProperty1<? super T,?> property){
       getColumnFuncSelector().column(EasyKtLambdaUtil.getPropertyName(property));
       return this;
   }
    default SQLKtColumnFuncSelector<T> column(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T,?> property){
        getColumnFuncSelector().column(tableOwner,EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }
    default SQLKtColumnFuncSelector<T> value(Object val){
        getColumnFuncSelector().value(val);
        return this;
    }
    default SQLKtColumnFuncSelector<T> format(Object valFormat){
        getColumnFuncSelector().format(valFormat);
        return this;
    }
}
