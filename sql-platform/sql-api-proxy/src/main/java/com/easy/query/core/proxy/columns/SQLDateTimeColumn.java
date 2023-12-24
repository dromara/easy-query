package com.easy.query.core.proxy.columns;

import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;

/**
 * create time 2023/12/24 00:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLDateTimeColumn<TProxy, TProperty> extends SQLColumn<TProxy,TProperty>,
        ColumnDateTimeFunctionAvailable<TProperty> {
}
