package com.easy.query.core.proxy.extension;

import com.easy.query.core.func.ACSSelector;
import com.easy.query.core.func.DistinctDefaultSettingImpl;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.proxy.SQLSelectAs;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableImpl;

import java.util.function.Consumer;

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
    default <T extends Number> ColumnFuncComparable<T> count(Consumer<ACSSelector> setting){
        return new SQLColumnFunctionComparableImpl<T>(this.getTable(),this.value(),fx->{
            DistinctDefaultSQLFunction count = fx.count(this.value());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(count);
            setting.accept(distinctDefaultSetting);
            return count;
        });
    }
    default <T extends Number> ColumnFuncComparable<T> avg(){
        return new SQLColumnFunctionComparableImpl<T>(this.getTable(),this.value(),fx->{
            return fx.avg(this.value());
        });
    }
}
