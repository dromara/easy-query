package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

import java.math.BigDecimal;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableNumberChainExpression<T> extends ColumnFunctionCompareComparableObjectChainExpression<T>,
        ColumnNumberFunctionAvailable<T> {
    @Override
    default <TR> ColumnFunctionCompareComparableNumberChainExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFunctionCompareComparableObjectChainExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> asInteger() {
        return asAnyType(Integer.class);
    }
    default ColumnFunctionCompareComparableNumberChainExpression<Long> asLong() {
        return asAnyType(Long.class);
    }
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> asBIgDecimal() {
        return asAnyType(BigDecimal.class);
    }
}
