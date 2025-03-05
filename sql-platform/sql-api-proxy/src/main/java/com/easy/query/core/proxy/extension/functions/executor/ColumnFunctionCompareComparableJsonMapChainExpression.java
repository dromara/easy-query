package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnJsonMapFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableJsonMapChainExpression<T> extends ColumnFunctionCompareComparableObjectChainExpression<T>,
        ColumnJsonMapFunctionAvailable<T> {
    @Override
    default <TR> ColumnFunctionCompareComparableJsonMapChainExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFunctionCompareComparableObjectChainExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
