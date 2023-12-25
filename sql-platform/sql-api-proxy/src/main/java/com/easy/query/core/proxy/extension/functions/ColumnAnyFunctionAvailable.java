package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastBooleanAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyStringUtil;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAnyFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionComparableAnyChainExpression<TProperty>>,SQLSelectAsExpression, PropTypeColumn<TProperty>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty> {

    @Override
    default ColumnFunctionComparableAnyChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }

    default ColumnFunctionComparableAnyChainExpression<String> dateTimeFormat(String javaFormat) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.dateTimeFormat(this.getValue(), javaFormat);
        }, String.class);
    }

    default ColumnFunctionComparableAnyChainExpression<TProperty> concat(TablePropColumn... propColumns) {
        return concat(o->{
            for (TablePropColumn propColumn : propColumns) {
                o.getColumnConcatSelector().column(propColumn.getTable(), propColumn.getValue());
            }
        });
    }

    default ColumnFunctionComparableAnyChainExpression<TProperty> concat(SQLExpression1<ProxyColumnFuncSelector> selector) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.concat(o -> {
                    o.sqlFunc(sqlFunction);
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            } else {
                return fx.concat(o -> {
                    o.column(this.getTable(), this.getValue());
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            }
        }, String.class);
    }

    default ColumnFunctionComparableAnyChainExpression<TProperty> nullEmpty() {
        return nullDefault(o->o.value(EasyStringUtil.EMPTY));
    }

    default ColumnFunctionComparableAnyChainExpression<String> toLower() {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.toLower(sqlFunction);
            } else {
                return fx.toLower(this.getValue());
            }
        }, String.class);
    }

    default ColumnFunctionComparableAnyChainExpression<String> toUpper() {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableAnyChainExpression<String> subString(int begin, int length) {
        if(begin<0){
            throw new IllegalArgumentException("begin must be greater than 0");
        }
        if(length<0){
            throw new IllegalArgumentException("length must be greater than 0");
        }
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableAnyChainExpression<String> trim() {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableAnyChainExpression<String> trimStart() {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableAnyChainExpression<String> trimEnd() {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimEnd(sqlFunction);
            } else {
                return fx.trimEnd(this.getValue());
            }
        }, String.class);
    }
    default ColumnFunctionComparableAnyChainExpression<String> replace(String oldValue,String newValue) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.replace(sqlFunction,oldValue,newValue);
            } else {
                return fx.replace(this.getValue(),oldValue,newValue);
            }
        }, String.class);
    }

    default ColumnFunctionComparableAnyChainExpression<String> leftPad(int totalWidth) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction,totalWidth);
            } else {
                return fx.leftPad(this.getValue(),totalWidth);
            }
        }, String.class);
    }
    default ColumnFunctionComparableAnyChainExpression<String> leftPad(int totalWidth,char paddingChar) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction,totalWidth,paddingChar);
            } else {
                return fx.leftPad(this.getValue(),totalWidth,paddingChar);
            }
        }, String.class);
    }

    default ColumnFunctionComparableAnyChainExpression<String> rightPad(int totalWidth) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction,totalWidth);
            } else {
                return fx.rightPad(this.getValue(),totalWidth);
            }
        }, String.class);
    }
    default ColumnFunctionComparableAnyChainExpression<String> rightPad(int totalWidth,char paddingChar) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction,totalWidth,paddingChar);
            } else {
                return fx.rightPad(this.getValue(),totalWidth,paddingChar);
            }
        }, String.class);
    }
    default ColumnFunctionComparableAnyChainExpression<String> join(String delimiter) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.join(sqlFunction,delimiter);
            } else {
                return fx.join(this.getValue(),delimiter);
            }
        }, String.class);
    }
    default ColumnFunctionComparableAnyChainExpression<Integer> length() {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.length(sqlFunction);
            } else {
                return fx.length(this.getValue());
            }
        }, Integer.class);
    }default <T extends BigDecimal> ColumnFunctionComparableAnyChainExpression<T> avg() {
        return avg(false);
    }
    default <T extends BigDecimal> ColumnFunctionComparableAnyChainExpression<T>  avg(boolean distinct) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.avg(sqlFunction).distinct(distinct);
            } else {
                return fx.avg(this.getValue()).distinct(distinct);
            }
        }, BigDecimal.class);
    }

    default <T extends BigDecimal> ColumnFunctionComparableAnyChainExpression<T> sum() {
        return sum(false);
    }
    default <T extends BigDecimal> ColumnFunctionComparableAnyChainExpression<T> sum(boolean distinct) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.sum(sqlFunction).distinct(distinct);
            } else {
                return fx.sum(this.getValue()).distinct(distinct);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableAnyChainExpression<TProperty> abs() {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.concat(o -> {
                    o.sqlFunc(sqlFunction);
                });
            } else {
                return fx.abs(new SimpleSQLTableOwner(this.getTable()),this.getValue());
            }
        }, getPropertyType());
    }
}
