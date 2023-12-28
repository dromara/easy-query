package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.functions.ColumnBooleanFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionComparableBooleanChainExpression<T> extends ColumnFunctionComparableObjectChainExpression<T>,
        ColumnBooleanFunctionAvailable<T> {
    @Override
    default <TR> ColumnFunctionComparableBooleanChainExpression<TR> setPropertyType(Class<TR> clazz) {
        ColumnFunctionComparableObjectChainExpression.super.setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
