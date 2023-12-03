package com.easy.query.core.proxy.extension;

import com.easy.query.core.proxy.SQLSelectAs;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableImpl;

/**
 * create time 2023/12/2 23:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAggregatable<TProperty> extends SQLSelectAs {
    default <T extends Number> ColumnFuncComparable<T> count(){
        return new SQLColumnFunctionComparableImpl<T>(this.getTable(),this.value(),fx->{
            return fx.count(this.value());
        });
    }
}
