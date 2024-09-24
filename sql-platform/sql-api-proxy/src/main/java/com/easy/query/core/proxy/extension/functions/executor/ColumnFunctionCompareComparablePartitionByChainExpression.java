package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2024/8/4 15:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparablePartitionByChainExpression<T>  extends ColumnFunctionCompareComparableAnyChainExpression<T> {
    <TProperty> ColumnFunctionCompareComparablePartitionByChainExpression<T> orderBy(PropTypeColumn<TProperty> propTypeColumn);
    <TProperty> ColumnFunctionCompareComparablePartitionByChainExpression<T> orderByDescending(PropTypeColumn<TProperty> propTypeColumn);
}
