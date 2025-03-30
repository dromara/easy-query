package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableObjectChainExpression<T> extends ColumnFuncComparableExpression<T>{

    default ColumnFunctionCompareComparableAnyChainExpression<T> asAny() {
        Class<?> propertyType = getPropertyType();
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), this.func(), propertyType);
    }
}
