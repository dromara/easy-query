package com.easy.query.core.proxy.columns;

import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.extension.functions.ColumnBooleanFunctionAvailable;

/**
 * create time 2023/12/24 00:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLBooleanColumn<TProxy, TProperty> extends SQLColumn<TProxy,TProperty>,
        ColumnBooleanFunctionAvailable<TProperty> {
}
