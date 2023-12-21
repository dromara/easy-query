package com.easy.query.core.proxy.extension;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.ACSSelector;
import com.easy.query.core.func.DistinctDefaultSettingImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * create time 2023/12/2 23:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAggregatable<TProperty> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {
    default <T extends Long> ColumnFuncComparableExpression<T> count() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.count(this.getValue());
        }, Long.class);
    }

    default <T extends Long> ColumnFuncComparableExpression<T> count(Consumer<ACSSelector> setting) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            DistinctDefaultSQLFunction count = fx.count(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(count);
            setting.accept(distinctDefaultSetting);
            return count;
        }, Long.class);
    }

    default <T extends BigDecimal> ColumnFuncComparableExpression<T> avg() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.avg(this.getValue());
        }, BigDecimal.class);
    }

    default <T extends BigDecimal> ColumnFuncComparableExpression<T> avg(Consumer<ACSSelector> setting) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            DistinctDefaultSQLFunction avg = fx.avg(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(avg);
            setting.accept(distinctDefaultSetting);
            return avg;
        }, BigDecimal.class);
    }

    default <T extends BigDecimal> ColumnFuncComparableExpression<T> sum() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.sum(this.getValue());
        }, BigDecimal.class);
    }

    default <T extends BigDecimal> ColumnFuncComparableExpression<T> sum(Consumer<ACSSelector> setting) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            DistinctDefaultSQLFunction sum = fx.sum(this.getValue());
            DistinctDefaultSettingImpl distinctDefaultSetting = new DistinctDefaultSettingImpl(sum);
            setting.accept(distinctDefaultSetting);
            return sum;
        }, BigDecimal.class);
    }

    default ColumnFunctionComparableChainExpression<String> dateTimeFormat(String javaFormat) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.dateTimeFormat(this.getValue(), javaFormat);
        }, String.class);
    }

    default ColumnFunctionComparableChainExpression<TProperty> max() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.max(sqlFunction);
            } else {
                return fx.max(this.getValue());
            }
        }, propertyType());
    }

    default ColumnFunctionComparableChainExpression<TProperty> min() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.min(sqlFunction);
            } else {
                return fx.min(this.getValue());
            }
        }, propertyType());
    }

    default ColumnFunctionComparableChainExpression<TProperty> abs() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.abs(this.getValue());
        }, propertyType());
    }

    default ColumnFuncComparableExpression<String> concat(TablePropColumn... propColumns) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {

            return fx.concat(o -> {
                for (TablePropColumn propColumn : propColumns) {
                    o.column(propColumn.getTable(), propColumn.getValue());
                }
            });
        }, String.class);
    }

    default ColumnFuncComparableExpression<String> concat(SQLExpression1<ProxyColumnFuncSelector> selector) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.concat(o -> {
                selector.apply(new ProxyColumnFuncSelectorImpl(o));
            });
        }, String.class);
    }

    default ColumnFuncComparableExpression<String> nullEmpty() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.valueOrDefault(getValue(), "");
        }, String.class);
    }

    default <T> ColumnFuncComparableExpression<T> nullDefault(T value) {
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.valueOrDefault(getValue(), value);
        });
    }

    default <T> ColumnFuncComparableExpression<T> nullDefault(SQLExpression1<ProxyColumnFuncSelector> selector) {
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.valueOrDefault(o -> {
                ProxyColumnFuncSelectorImpl proxyColumnFuncSelector = new ProxyColumnFuncSelectorImpl(o);
                o.column(this.getTable(), this.getValue());
                selector.apply(proxyColumnFuncSelector);
            });
        });
    }
    //todo length trim left trim right trim padleft padright

    default ColumnFunctionComparableChainExpression<String> toLower() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.toLower(sqlFunction);
            } else {
                return fx.toLower(this.getValue());
            }
        }, String.class);
    }

    default ColumnFunctionComparableChainExpression<String> toUpper() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.toUpper(sqlFunction);
            } else {
                return fx.toUpper(this.getValue());
            }
        }, String.class);
    }

    /**
     * 字符串截取
     * @param begin 开始索引默认0
     * @param length 截取长度
     * @return
     */
    default ColumnFunctionComparableChainExpression<String> subString(int begin, int length) {
        if(begin<0){
            throw new IllegalArgumentException("begin must be greater than 0");
        }
        if(length<0){
            throw new IllegalArgumentException("length must be greater than 0");
        }
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.subString(sqlFunction, begin, length);
            } else {
                return fx.subString(this.getValue(), begin, length);
            }
        }, String.class);
    }

    /**
     * 去空格
     * @return
     */
    default ColumnFunctionComparableChainExpression<String> trim() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trim(sqlFunction);
            } else {
                return fx.trim(this.getValue());
            }
        }, String.class);
    }
    /**
     * 去头部空格
     * @return
     */
    default ColumnFunctionComparableChainExpression<String> trimStart() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimStart(sqlFunction);
            } else {
                return fx.trimStart(this.getValue());
            }
        }, String.class);
    }
    /**
     * 去尾部空格
     * @return
     */
    default ColumnFunctionComparableChainExpression<String> trimEnd() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimEnd(sqlFunction);
            } else {
                return fx.trimEnd(this.getValue());
            }
        }, String.class);
    }
    default ColumnFunctionComparableChainExpression<String> replace(String oldValue,String newValue) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.replace(sqlFunction,oldValue,newValue);
            } else {
                return fx.replace(this.getValue(),oldValue,newValue);
            }
        }, String.class);
    }
    default ColumnFuncComparableExpression<Integer> compareTo(String comparedValue) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.compareTo(sqlFunction,comparedValue);
            } else {
                return fx.compareTo(this.getValue(),comparedValue);
            }
        }, Integer.class);
    }
    default <TColumnProxy> ColumnFuncComparableExpression<Integer> compareTo(SQLColumn<TColumnProxy,String> sqlColumn) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.compareTo(sqlFunction,new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
            } else {
                return fx.compareTo(this.getValue(),new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
            }
        }, Integer.class);
    }
    default ColumnFuncComparableExpression<Integer> compareTo(DSLSQLFunctionAvailable otherSQLFunction) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            SQLFunction comparedSQLFunction = otherSQLFunction.func().apply(fx);
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.compareTo(sqlFunction,comparedSQLFunction);
            } else {
                return fx.compareTo(this.getValue(),comparedSQLFunction);
            }
        }, Integer.class);
    }


    default ColumnFunctionComparableChainExpression<String> leftPad(int totalWidth) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction,totalWidth);
            } else {
                return fx.leftPad(this.getValue(),totalWidth);
            }
        }, String.class);
    }
    default ColumnFunctionComparableChainExpression<String> leftPad(int totalWidth,char paddingChar) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction,totalWidth,paddingChar);
            } else {
                return fx.leftPad(this.getValue(),totalWidth,paddingChar);
            }
        }, String.class);
    }

    default ColumnFunctionComparableChainExpression<String> rightPad(int totalWidth) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction,totalWidth);
            } else {
                return fx.rightPad(this.getValue(),totalWidth);
            }
        }, String.class);
    }
    default ColumnFunctionComparableChainExpression<String> rightPad(int totalWidth,char paddingChar) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction,totalWidth,paddingChar);
            } else {
                return fx.rightPad(this.getValue(),totalWidth,paddingChar);
            }
        }, String.class);
    }
}
