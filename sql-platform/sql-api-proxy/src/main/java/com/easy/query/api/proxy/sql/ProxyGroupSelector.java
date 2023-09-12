package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.core.SQLProxyNative;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/23 13:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupSelector extends SQLProxyNative<ProxyGroupSelector> {
    GroupSelector getGroupSelector();

    default <TProxy extends ProxyEntity<TProxy,T>,T> ProxyGroupSelector columns(SQLColumn<TProxy,?>... columns) {
        if (columns != null) {
            for (SQLColumn<TProxy,?> sqlColumn : columns) {
                column(sqlColumn);
            }
        }
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> ProxyGroupSelector column(SQLColumn<TProxy,TProperty> column) {
        getGroupSelector().column(column.getTable(), column.value());
        return this;
    }

    default ProxyGroupSelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction) {
        getGroupSelector().columnFunc(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction());
        return this;
    }
}
