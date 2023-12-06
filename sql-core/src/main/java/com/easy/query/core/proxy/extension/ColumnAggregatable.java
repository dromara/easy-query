package com.easy.query.core.proxy.extension;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.ACSSelector;
import com.easy.query.core.func.DistinctDefaultSettingImpl;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;

import java.util.function.Consumer;

/**
 * create time 2023/12/2 23:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAggregatable<TProperty> extends SQLSelectAsExpression {
    default <T extends Number> ColumnFuncComparableExpression<T> count(){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.count(this.getValue());
        });
    }
    default <T extends Number> ColumnFuncComparableExpression<T> count(Consumer<ACSSelector> setting){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            DistinctDefaultSQLFunction count = fx.count(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(count);
            setting.accept(distinctDefaultSetting);
            return count;
        });
    }
    default <T extends Number> ColumnFuncComparableExpression<T> avg(){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.avg(this.getValue());
        });
    }
    default <T extends Number> ColumnFuncComparableExpression<T> avg(Consumer<ACSSelector> setting){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            DistinctDefaultSQLFunction avg = fx.avg(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(avg);
            setting.accept(distinctDefaultSetting);
            return avg;
        });
    }
    default <T extends Number> ColumnFuncComparableExpression<T> sum(){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.sum(this.getValue());
        });
    }
    default <T extends Number> ColumnFuncComparableExpression<T> sum(Consumer<ACSSelector> setting){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            DistinctDefaultSQLFunction sum = fx.sum(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(sum);
            setting.accept(distinctDefaultSetting);
            return sum;
        });
    }
    default <T> ColumnFuncComparableExpression<T> dateTimeFormat(String javaFormat){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.dateTimeFormat(this.getValue(),javaFormat);
        });
    }
    default <T> ColumnFuncComparableExpression<T> max(){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.max(this.getValue());
        });
    }
    default <T> ColumnFuncComparableExpression<T> min(){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.min(this.getValue());
        });
    }
    default <T> ColumnFuncComparableExpression<T> abs(){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.abs(this.getValue());
        });
    }
    default <T> ColumnFuncComparableExpression<T> concat(TablePropColumn... propColumns){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{

            return fx.concat(o->{
                for (TablePropColumn propColumn : propColumns) {
                    o.column(propColumn.getTable(),propColumn.getValue());
                }
            });
        });
    }
    default <T> ColumnFuncComparableExpression<T> concat(SQLExpression1<ProxyColumnFuncSelector> selector){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.concat(o->{
                selector.apply(new ProxyColumnFuncSelectorImpl(o));
            });
        });
    }
    default <T> ColumnFuncComparableExpression<T> nullEmpty(){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.valueOrDefault(getValue(),"");
        });
    }
    default <T> ColumnFuncComparableExpression<T> nullDefault(T value){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.valueOrDefault(getValue(),value);
        });
    }
    default <T> ColumnFuncComparableExpression<T> nullDefault(SQLExpression1<ProxyColumnFuncSelector> selector){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),this.getValue(), fx->{
            return fx.valueOrDefault(o->{
                ProxyColumnFuncSelectorImpl proxyColumnFuncSelector = new ProxyColumnFuncSelectorImpl(o);
                o.column(this.getTable(),this.getValue());
                selector.apply(proxyColumnFuncSelector);
            });
        });
    }
}
