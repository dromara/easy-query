package com.easy.query.core.proxy.extension.functions.executor.filter;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableStringFilterChainExpression<T> extends ColumnFunctionCompareComparableStringChainExpression<T> {
    default ColumnFunctionCompareComparableStringChainExpression<T> filter(SQLActionExpression predicate) {
        this._toFilter(predicate);
        return this;
    }

    @Override
    default <TR> ColumnFunctionCompareComparableStringFilterChainExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFunctionCompareComparableStringChainExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
    void _toFilter(SQLActionExpression predicate);

}
