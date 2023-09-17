package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.core.SQLProxyNative;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/25 19:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyColumnOnlySelector<TProxy extends ProxyEntity<TProxy,T>,T> extends SQLProxyNative<ProxyColumnOnlySelector<TProxy,T>> {
    OnlySelector getOnlySelector();
    TProxy getProxy();
    default ProxyColumnOnlySelector<TProxy,T> columnKeys() {
        return columnKeys(getProxy());
    }
    default ProxyColumnOnlySelector<TProxy,T> columnKeys(TProxy tableProxy) {
        getOnlySelector().columnKeys(tableProxy.getTable());
        return this;
    }
    default ProxyColumnOnlySelector<TProxy,T> columns(SQLColumn<?,?>... columns){
        if(columns != null){
            for (SQLColumn<?,?> sqlColumn : columns) {
                column(EasyObjectUtil.typeCastNullable(sqlColumn));
            }
        }
        return this;
    }
    default ProxyColumnOnlySelector<TProxy,T> column(SQLColumn<TProxy,?> column){
        getOnlySelector().column(column.getTable(),column.value());
        return this;
    }

    default ProxyColumnOnlySelector<TProxy,T> columnIgnore(SQLColumn<TProxy,?> column){
        getOnlySelector().columnIgnore(column.getTable(),column.value());
        return this;
    }

    default ProxyColumnOnlySelector<TProxy,T> columnAll(TProxy tableProxy){
        getOnlySelector().columnAll(tableProxy.getTable());
        return this;
    }
}
