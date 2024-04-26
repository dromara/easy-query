package com.easy.query.core.proxy.columns.types;

import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLBooleanColumn;

/**
 * create time 2024/4/26 13:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLBooleanTypeColumn<TProxy> extends SQLBooleanColumn<TProxy, Boolean>,
        ProxyEntity<SQLBooleanTypeColumn<TProxy>, Boolean> {
}
