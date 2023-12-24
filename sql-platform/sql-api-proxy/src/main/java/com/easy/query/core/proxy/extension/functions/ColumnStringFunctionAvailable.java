package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnStringFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty,ColumnFunctionComparableStringChainExpression<TProperty>> {

    default ColumnFunctionComparableStringChainExpression<TProperty> concat(TablePropColumn... propColumns) {
        return concat(o->{
            for (TablePropColumn propColumn : propColumns) {
                o.getColumnConcatSelector().column(propColumn.getTable(), propColumn.getValue());
            }
        });
    }

    default ColumnFunctionComparableStringChainExpression<TProperty> concat(SQLExpression1<ProxyColumnFuncSelector> selector) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    default ColumnFunctionComparableStringChainExpression<TProperty> nullEmpty() {
        return nullDefault(o->o.value(EasyStringUtil.EMPTY));
    }

    @Override
    default <T> ColumnFunctionComparableStringChainExpression<TProperty> nullDefault(SQLExpression1<ProxyColumnFuncSelector> selector) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.valueOrDefault(o -> {
                    o.sqlFunc(sqlFunction);
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            } else {
                return fx.valueOrDefault(o -> {
                    o.column(this.getTable(), this.getValue());
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            }
        });
    }

    default ColumnFunctionComparableStringChainExpression<String> toLower() {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.toLower(sqlFunction);
            } else {
                return fx.toLower(this.getValue());
            }
        }, String.class);
    }

    default ColumnFunctionComparableStringChainExpression<String> toUpper() {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableStringChainExpression<String> subString(int begin, int length) {
        if(begin<0){
            throw new IllegalArgumentException("begin must be greater than 0");
        }
        if(length<0){
            throw new IllegalArgumentException("length must be greater than 0");
        }
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableStringChainExpression<String> trim() {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableStringChainExpression<String> trimStart() {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableStringChainExpression<String> trimEnd() {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimEnd(sqlFunction);
            } else {
                return fx.trimEnd(this.getValue());
            }
        }, String.class);
    }
    default ColumnFunctionComparableStringChainExpression<String> replace(String oldValue,String newValue) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.replace(sqlFunction,oldValue,newValue);
            } else {
                return fx.replace(this.getValue(),oldValue,newValue);
            }
        }, String.class);
    }

    default ColumnFunctionComparableStringChainExpression<String> leftPad(int totalWidth) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction,totalWidth);
            } else {
                return fx.leftPad(this.getValue(),totalWidth);
            }
        }, String.class);
    }
    default ColumnFunctionComparableStringChainExpression<String> leftPad(int totalWidth,char paddingChar) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction,totalWidth,paddingChar);
            } else {
                return fx.leftPad(this.getValue(),totalWidth,paddingChar);
            }
        }, String.class);
    }

    default ColumnFunctionComparableStringChainExpression<String> rightPad(int totalWidth) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction,totalWidth);
            } else {
                return fx.rightPad(this.getValue(),totalWidth);
            }
        }, String.class);
    }
    default ColumnFunctionComparableStringChainExpression<String> rightPad(int totalWidth,char paddingChar) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction,totalWidth,paddingChar);
            } else {
                return fx.rightPad(this.getValue(),totalWidth,paddingChar);
            }
        }, String.class);
    }
    default ColumnFunctionComparableStringChainExpression<String> join(String delimiter) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.join(sqlFunction,delimiter);
            } else {
                return fx.join(this.getValue(),delimiter);
            }
        }, String.class);
    }
    default ColumnFunctionComparableStringChainExpression<String> length() {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.length(sqlFunction);
            } else {
                return fx.length(this.getValue());
            }
        }, String.class);
    }
}
