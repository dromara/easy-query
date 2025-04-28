package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableDateTimeChainExpression<T> extends ColumnFunctionCompareComparableObjectChainExpression<T>,
        ColumnDateTimeFunctionAvailable<T> {
    @Override
    default <TR> ColumnFunctionCompareComparableDateTimeChainExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFunctionCompareComparableObjectChainExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
