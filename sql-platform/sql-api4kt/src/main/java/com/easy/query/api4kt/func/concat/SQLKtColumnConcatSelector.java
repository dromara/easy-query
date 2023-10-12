package com.easy.query.api4kt.func.concat;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.func.concat.ColumnConcatSelector;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtColumnConcatSelector<T> {
    ColumnConcatSelector getColumnConcatSelector();
   default SQLKtColumnConcatSelector<T> column(KProperty1<? super T,?> property){
       getColumnConcatSelector().column(EasyKtLambdaUtil.getPropertyName(property));
       return this;
   }
    default SQLKtColumnConcatSelector<T> column(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T,?> property){
        getColumnConcatSelector().column(tableOwner,EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }
    default SQLKtColumnConcatSelector<T> value(Object val){
        getColumnConcatSelector().value(val);
        return this;
    }
    default SQLKtColumnConcatSelector<T> format(Object valFormat){
        getColumnConcatSelector().format(valFormat);
        return this;
    }
}
