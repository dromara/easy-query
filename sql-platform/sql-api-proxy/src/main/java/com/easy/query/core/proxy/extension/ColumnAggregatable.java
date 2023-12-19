package com.easy.query.core.proxy.extension;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.ACSSelector;
import com.easy.query.core.func.DistinctDefaultSettingImpl;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * create time 2023/12/2 23:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAggregatable<TProperty> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {
    default <T extends Long> ColumnFuncComparableExpression<T> count(){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.count(this.getValue());
        },Long.class);
    }
    default <T extends Long> ColumnFuncComparableExpression<T> count(Consumer<ACSSelector> setting){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            DistinctDefaultSQLFunction count = fx.count(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(count);
            setting.accept(distinctDefaultSetting);
            return count;
        },Long.class);
    }
    default <T extends BigDecimal> ColumnFuncComparableExpression<T> avg(){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.avg(this.getValue());
        }, BigDecimal.class);
    }
    default <T extends BigDecimal> ColumnFuncComparableExpression<T> avg(Consumer<ACSSelector> setting){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            DistinctDefaultSQLFunction avg = fx.avg(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(avg);
            setting.accept(distinctDefaultSetting);
            return avg;
        }, BigDecimal.class);
    }
    default <T extends BigDecimal> ColumnFuncComparableExpression<T> sum(){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.sum(this.getValue());
        }, BigDecimal.class);
    }
    default <T extends BigDecimal> ColumnFuncComparableExpression<T> sum(Consumer<ACSSelector> setting){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            DistinctDefaultSQLFunction sum = fx.sum(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(sum);
            setting.accept(distinctDefaultSetting);
            return sum;
        }, BigDecimal.class);
    }
    default ColumnFuncComparableExpression<String> dateTimeFormat(String javaFormat){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.dateTimeFormat(this.getValue(),javaFormat);
        },String.class);
    }
    default ColumnFuncComparableExpression<TProperty> max(){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.max(this.getValue());
        },propertyType());
    }
    default ColumnFuncComparableExpression<TProperty> min(){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.min(this.getValue());
        },propertyType());
    }
    default ColumnFuncComparableExpression<TProperty> abs(){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.abs(this.getValue());
        },propertyType());
    }
    default ColumnFuncComparableExpression<String> concat(TablePropColumn... propColumns){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{

            return fx.concat(o->{
                for (TablePropColumn propColumn : propColumns) {
                    o.column(propColumn.getTable(),propColumn.getValue());
                }
            });
        },String.class);
    }
    default ColumnFuncComparableExpression<String> concat(SQLExpression1<ProxyColumnFuncSelector> selector){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.concat(o->{
                selector.apply(new ProxyColumnFuncSelectorImpl(o));
            });
        },String.class);
    }
    default ColumnFuncComparableExpression<String> nullEmpty(){
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.valueOrDefault(getValue(),"");
        },String.class);
    }
    default <T> ColumnFuncComparableExpression<T> nullDefault(T value){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.valueOrDefault(getValue(),value);
        });
    }
    default <T> ColumnFuncComparableExpression<T> nullDefault(SQLExpression1<ProxyColumnFuncSelector> selector){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(),this.getTable(),this.getValue(), fx->{
            return fx.valueOrDefault(o->{
                ProxyColumnFuncSelectorImpl proxyColumnFuncSelector = new ProxyColumnFuncSelectorImpl(o);
                o.column(this.getTable(),this.getValue());
                selector.apply(proxyColumnFuncSelector);
            });
        });
    }
    //todo length trim left trim right trim padleft padright
}
