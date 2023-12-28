package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionComparableDateTimeChainExpression<T> extends ColumnFunctionComparableObjectChainExpression<T>,
        ColumnDateTimeFunctionAvailable<T> {
    @Override
    default <TR> ColumnFunctionComparableDateTimeChainExpression<TR> setPropertyType(Class<TR> clazz) {
        ColumnFunctionComparableObjectChainExpression.super.setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
