package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.functions.ColumnStringFunctionAvailable;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionComparableStringChainExpression<T> extends ColumnFunctionComparableObjectChainExpression<T>,
        ColumnStringFunctionAvailable<T>, DSLSQLFunctionAvailable {

    @Override
    default <TR> ColumnFunctionComparableStringChainExpression<TR> setPropertyType(Class<TR> clazz) {
        ColumnFunctionComparableObjectChainExpression.super.setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
