package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2024/8/4 15:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionComparablePartitionByChainExpression<T>  extends ColumnFunctionComparableAnyChainExpression<T>{
    <TProperty> ColumnFunctionComparablePartitionByChainExpression<T> orderBy(PropTypeColumn<TProperty> propTypeColumn);
    <TProperty> ColumnFunctionComparablePartitionByChainExpression<T> orderByDescending(PropTypeColumn<TProperty> propTypeColumn);
}
