package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionComparableNumberChainExpression<T> extends ColumnFunctionComparableObjectChainExpression<T>,
        ColumnNumberFunctionAvailable<T> {
    @Override
    default <TR> ColumnFunctionComparableNumberChainExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFunctionComparableObjectChainExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
