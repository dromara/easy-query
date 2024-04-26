package com.easy.query.core.proxy.columns.types;

import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLAnyColumn;

import java.util.UUID;

/**
 * create time 2024/4/26 13:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLUUIDTypeColumn<TProxy> extends SQLAnyColumn<TProxy, UUID>,
        ProxyEntity<SQLUUIDTypeColumn<TProxy>, UUID> {
}
