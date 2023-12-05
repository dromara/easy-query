package com.easy.query.core.proxy.extension;

import com.easy.query.core.func.ACSSelector;
import com.easy.query.core.func.DistinctDefaultSettingImpl;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableImpl;

import java.util.function.Consumer;

/**
 * create time 2023/12/2 23:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAggregatable<TProperty> extends SQLSelectAsExpression {
    default <T extends Number> ColumnFuncComparable<T> count(){
        return new SQLColumnFunctionComparableImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.count(this.getValue());
        });
    }
    default <T extends Number> ColumnFuncComparable<T> count(Consumer<ACSSelector> setting){
        return new SQLColumnFunctionComparableImpl<T>(this.getTable(),this.getValue(), fx->{
            DistinctDefaultSQLFunction count = fx.count(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(count);
            setting.accept(distinctDefaultSetting);
            return count;
        });
    }
    default <T extends Number> ColumnFuncComparable<T> avg(){
        return new SQLColumnFunctionComparableImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.avg(this.getValue());
        });
    }
}
