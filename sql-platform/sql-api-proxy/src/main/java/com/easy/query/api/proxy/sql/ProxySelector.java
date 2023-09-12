package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.core.SQLProxyNative;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/22 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelector extends SQLProxyNative<ProxySelector> {
    Selector getSelector();

   default <TProxy extends ProxyEntity<TProxy,T>,T> ProxySelector columns(SQLColumn<TProxy,?>... columns){
       if(columns != null){
           for (SQLColumn<TProxy,?> sqlColumn : columns) {
               column(sqlColumn);
           }
       }
       return this;
   }
   default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> ProxySelector column(SQLColumn<TProxy,TProperty> column){
       getSelector().column(column.getTable(),column.value());
       return this;
   }

   default ProxySelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction){
       getSelector().columnFunc(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction());
       return this;
   }

   default <TProxy extends ProxyEntity<TProxy,T>,T> ProxySelector columnIgnore(SQLColumn<TProxy,?> column){
       getSelector().columnIgnore(column.getTable(),column.value());
       return this;
   }

   default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity> ProxySelector columnAll(TProxy tableProxy){
       getSelector().columnAll(tableProxy.getTable());
       return this;
   }
}
