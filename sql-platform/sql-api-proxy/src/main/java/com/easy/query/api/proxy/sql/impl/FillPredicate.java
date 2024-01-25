package com.easy.query.api.proxy.sql.impl;

import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2024/1/25 11:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class FillPredicate<TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity,T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, TProperty> {
    private final SQLColumn<TRProxyEntity, TProperty> targetSQLColumn;
    private final SQLColumn<T1Proxy, TProperty> selfSQLColumn;
    private final boolean consumeNull;

    public FillPredicate(SQLColumn<T1Proxy, TProperty> selfSQLColumn,SQLColumn<TRProxyEntity, TProperty> targetSQLColumn) {
        this(selfSQLColumn,targetSQLColumn,false);
    }


    public FillPredicate(SQLColumn<T1Proxy, TProperty> selfSQLColumn,SQLColumn<TRProxyEntity, TProperty> targetSQLColumn, boolean consumeNull) {
        this.targetSQLColumn = targetSQLColumn;
        this.selfSQLColumn = selfSQLColumn;

        this.consumeNull = consumeNull;
    }

    public SQLColumn<TRProxyEntity, TProperty> getTargetSQLColumn() {
        return targetSQLColumn;
    }

    public SQLColumn<T1Proxy, TProperty> getSelfSQLColumn() {
        return selfSQLColumn;
    }

    public boolean isConsumeNull() {
        return consumeNull;
    }
}
