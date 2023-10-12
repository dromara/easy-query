package com.easy.query.api.proxy.func.concat;

import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.concat.ColumnConcatSelector;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyColumnConcatSelector{
    ColumnConcatSelector getColumnConcatSelector();
   default <TProxy,T> ProxyColumnConcatSelector column(SQLColumn<TProxy, T> sqlColumn){
       getColumnConcatSelector().column(new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.value());
       return this;
   }
   default ProxyColumnConcatSelector columns(SQLColumn<?, ?>... sqlColumns){
       if(EasyArrayUtil.isNotEmpty(sqlColumns)){
           for (SQLColumn<?, ?> sqlColumn : sqlColumns) {
               getColumnConcatSelector().column(new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.value());
           }
       }
       return this;
   }
    default ProxyColumnConcatSelector value(Object val){
        getColumnConcatSelector().value(val);
        return this;
    }
    default ProxyColumnConcatSelector format(Object valFormat){
        getColumnConcatSelector().format(valFormat);
        return this;
    }
}
