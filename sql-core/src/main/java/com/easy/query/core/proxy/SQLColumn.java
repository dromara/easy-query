package com.easy.query.core.proxy;

import com.easy.query.core.proxy.extension.ColumnAggregatable;
import com.easy.query.core.proxy.extension.ColumnComparable;

/**
 * create time 2023/6/22 13:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumn<TProxy, TProperty> extends ColumnComparable<TProperty>,
        ColumnAggregatable<TProperty> {
}
