package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.core.SQLProxyNative;
import com.easy.query.core.expression.builder.UpdateSetSelector;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/25 19:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyUpdateSetSelector<TProxy extends ProxyEntity<TProxy,T>,T> extends SQLProxyNative<ProxyUpdateSetSelector<TProxy,T>> {
    UpdateSetSelector getUpdateSetSelector();

    default  ProxyUpdateSetSelector<TProxy,T> columnKeys(TProxy tableProxy) {
        getUpdateSetSelector().columnKeys(tableProxy.getTable());
        return this;
    }
    default ProxyUpdateSetSelector<TProxy,T> columns(SQLColumn<TProxy,?>... columns){
        if(columns != null){
            for (SQLColumn<TProxy,?> sqlColumn : columns) {
                column(sqlColumn);
            }
        }
        return this;
    }
    default ProxyUpdateSetSelector<TProxy,T> column(SQLColumn<TProxy,?> column){
        getUpdateSetSelector().column(column.getTable(),column.value());
        return this;
    }

    default ProxyUpdateSetSelector<TProxy,T> columnIgnore(SQLColumn<TProxy,?> column){
        getUpdateSetSelector().columnIgnore(column.getTable(),column.value());
        return this;
    }

    default ProxyUpdateSetSelector<TProxy,T> columnAll(TProxy tableProxy){
        getUpdateSetSelector().columnAll(tableProxy.getTable());
        return this;
    }
}
