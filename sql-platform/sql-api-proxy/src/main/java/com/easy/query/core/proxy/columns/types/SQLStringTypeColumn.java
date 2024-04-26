package com.easy.query.core.proxy.columns.types;

import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLStringColumn;

/**
 * create time 2024/4/26 13:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLStringTypeColumn<TProxy> extends SQLStringColumn<TProxy, String>,
        ProxyEntity<SQLStringTypeColumn<TProxy>, String> {
}
