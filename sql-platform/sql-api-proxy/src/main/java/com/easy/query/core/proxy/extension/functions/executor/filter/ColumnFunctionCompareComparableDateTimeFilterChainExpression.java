package com.easy.query.core.proxy.extension.functions.executor.filter;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableDateTimeFilterChainExpression<T> extends ColumnFunctionCompareComparableDateTimeChainExpression<T> {
    default ColumnFunctionCompareComparableDateTimeChainExpression<T> filter(SQLActionExpression predicate) {
        this._toFilter(predicate);
        return this;
    }

    @Override
    default <TR> ColumnFunctionCompareComparableDateTimeFilterChainExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFunctionCompareComparableDateTimeChainExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
    void _toFilter(SQLActionExpression predicate);

}
