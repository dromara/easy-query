package com.easy.query.core.proxy.columns.types;

import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLAnyColumn;

/**
 * create time 2024/4/26 13:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAnyTypeColumn<TProxy,TProperty> extends SQLAnyColumn<TProxy, TProperty>,
        ProxyEntity<SQLAnyTypeColumn<TProxy,TProperty>, TProperty> {
}
