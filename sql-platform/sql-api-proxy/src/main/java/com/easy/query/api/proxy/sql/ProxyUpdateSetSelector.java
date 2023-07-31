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
public interface ProxyUpdateSetSelector extends SQLProxyNative<ProxyUpdateSetSelector> {
    UpdateSetSelector getUpdateSetSelector();
    default ProxyUpdateSetSelector columns(SQLColumn<?>... columns){
        if(columns != null){
            for (SQLColumn<?> sqlColumn : columns) {
                column(sqlColumn);
            }
        }
        return this;
    }
    default ProxyUpdateSetSelector column(SQLColumn<?> column){
        getUpdateSetSelector().column(column.getTable(),column.value());
        return this;
    }

    default ProxyUpdateSetSelector columnIgnore(SQLColumn<?> column){
        getUpdateSetSelector().columnIgnore(column.getTable(),column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity> ProxyUpdateSetSelector columnAll(TProxy tableProxy){
        getUpdateSetSelector().columnAll(tableProxy.getTable());
        return this;
    }
}
