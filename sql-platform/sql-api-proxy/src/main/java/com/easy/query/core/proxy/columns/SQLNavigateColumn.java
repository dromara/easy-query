package com.easy.query.core.proxy.columns;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/12/9 08:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNavigateColumn<TProxy, TProperty> extends SQLColumn<TProxy,TProperty> {
    ClientQueryable<TProperty> asQueryable();
}
