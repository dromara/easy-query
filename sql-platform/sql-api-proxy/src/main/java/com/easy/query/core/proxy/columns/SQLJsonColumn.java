package com.easy.query.core.proxy.columns;

import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.extension.functions.ColumnAnyFunctionAvailable;

/**
 * create time 2025/3/4 08:53
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLJsonColumn<TProxy, TProperty> extends SQLColumn<TProxy,TProperty>,
        ColumnAnyFunctionAvailable<TProperty> {
}
