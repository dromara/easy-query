package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.core.SQLProxyNative;
import com.easy.query.api.proxy.sql.core.available.ProxySQLFuncAvailable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/23 15:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOrderSelector extends SQLProxyNative<ProxyOrderSelector>, ProxySQLFuncAvailable {
    OrderSelector getOrderSelector();
    default QueryRuntimeContext getRuntimeContext() {
        return getOrderSelector().getRuntimeContext();
    }


    default ProxyOrderSelector columns(SQLColumn<?,?>... columns) {
        if (columns != null) {
            for (SQLColumn<?,?> sqlColumn : columns) {
                getOrderSelector().column(sqlColumn.getTable(), sqlColumn.getValue());
            }
        }
        return this;
    }


   default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> ProxyOrderSelector column(SQLColumn<TProxy,TProperty> column){
       getOrderSelector().column(column.getTable(), column.getValue());
       return this;
   }

   default ProxyOrderSelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction){
       getOrderSelector().columnFunc(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction());
       return this;
   }
}
