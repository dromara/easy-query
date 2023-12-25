package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionComparableDateTimeChainExpression<T> extends ColumnFunctionComparableObjectChainExpression<T>,
        ColumnDateTimeFunctionAvailable<T> {
}
