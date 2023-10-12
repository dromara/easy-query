package com.easy.query.api4j.func.concat;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.func.concat.ColumnConcatSelector;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnConcatSelector<T> {
    ColumnConcatSelector getColumnConcatSelector();
   default SQLColumnConcatSelector<T> column(Property<T,?> property){
       getColumnConcatSelector().column(EasyLambdaUtil.getPropertyName(property));
       return this;
   }
    default SQLColumnConcatSelector<T> column(EntitySQLTableOwner<T> tableOwner, Property<T,?> property){
        getColumnConcatSelector().column(tableOwner,EasyLambdaUtil.getPropertyName(property));
        return this;
    }
    default SQLColumnConcatSelector<T> value(Object val){
        getColumnConcatSelector().value(val);
        return this;
    }
    default SQLColumnConcatSelector<T> format(Object valFormat){
        getColumnConcatSelector().format(valFormat);
        return this;
    }
}
