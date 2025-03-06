package com.easy.query.core.proxy.extension.functions.executor.filter;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableNumberFilterChainExpression<T> extends ColumnFunctionCompareComparableNumberChainExpression<T> {
    default ColumnFunctionCompareComparableNumberChainExpression<T> filter(SQLActionExpression predicate) {
        this._toFilter(predicate);
        return this;
    }

    @Override
    default <TR> ColumnFunctionCompareComparableNumberFilterChainExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFunctionCompareComparableNumberChainExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
    void _toFilter(SQLActionExpression predicate);

}
