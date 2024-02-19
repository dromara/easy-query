package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastBooleanAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelector;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelectorImpl;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyStringUtil;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnStringFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty,ColumnFunctionComparableStringChainExpression<TProperty>>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty> {

    /**
     * 链接表列
     * @param propTypeColumn
     * @return
     */
    default ColumnFunctionComparableStringChainExpression<TProperty> concat(PropTypeColumn<String> propTypeColumn) {
        return concat(x->x.expression(propTypeColumn));
    }
    /**
     * 链接常量
     * @param value
     * @return
     */
    default ColumnFunctionComparableStringChainExpression<TProperty> concat(String value) {
        return concat(x->x.value(value));
    }
    /**
     * 链接多个片段可以是表列,函数,片段,常量
     * @param stringExpressions
     * @return
     */
    default ColumnFunctionComparableStringChainExpression<TProperty> concat(SQLExpression1<ConcatExpressionSelector> stringExpressions) {
        SQLExpression1<ColumnFuncSelector> selector= o->{
            stringExpressions.apply(new ConcatExpressionSelectorImpl(getEntitySQLContext().getRuntimeContext().fx(),o));
        };
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.concat(o -> {
                    o.sqlFunc(sqlFunction);
                    selector.apply(o);
                });
            } else {
                return fx.concat(o -> {
                    o.column(this.getTable(), this.getValue());
                    selector.apply(o);
                });
            }
        }, String.class);
    }

    /**
     * 请使用 nullOrEmpty
     * @return
     */
    @Deprecated
    default ColumnFunctionComparableStringChainExpression<TProperty> nullEmpty() {
        return nullOrDefault(o->o.value(EasyStringUtil.EMPTY));
    }
    default ColumnFunctionComparableStringChainExpression<TProperty> nullOrEmpty() {
        return nullOrDefault(o->o.value(EasyStringUtil.EMPTY));
    }

//    @Override
//    default <T> ColumnFunctionComparableStringChainExpression<TProperty> nullDefault(SQLExpression1<ProxyColumnFuncSelector> selector) {
//        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
//            if (this instanceof DSLSQLFunctionAvailable) {
//                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
//                return fx.valueOrDefault(o -> {
//                    o.sqlFunc(sqlFunction);
//                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
//                });
//            } else {
//                return fx.valueOrDefault(o -> {
//                    o.column(this.getTable(), this.getValue());
//                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
//                });
//            }
//        });
//    }

    /**
     * 转成小写
     * @return
     */
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

    /**
     * 转成小写
     * @return
     */
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
     * 字符串截取[column.subString(0,2)] 如果column值为"abcdefg"返回"ab"
     * @param begin 开始索引默认0
     * @param length 截取长度
     * @return 支持比较操作的字符串方法表达式
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

    /**
     * 左侧补齐totalWidth位数用空格补齐
     * @param totalWidth
     * @return
     */
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

    /**
     * 右侧补齐totalWidth位数用空格补齐
     * @param totalWidth
     * @return
     */
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

    /**
     * 长度函数返回当前列的长度值
     * @return
     */
    default ColumnFunctionComparableStringChainExpression<Integer> length() {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.length(sqlFunction);
            } else {
                return fx.length(this.getValue());
            }
        }, Integer.class);
    }

    @Override
    default ColumnFunctionComparableStringChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(),func, getPropertyType());
    }

    /**
     * 比较两个字符串 一样返回0 前一个比后一个大返回1 前一个比后一个小返回-1
     * @param comparedValue
     * @return
     */
    default ColumnFuncComparableExpression<Integer> compareTo(String comparedValue) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.stringCompareTo(sqlFunction, comparedValue);
            } else {
                return fx.stringCompareTo(this.getValue(), comparedValue);
            }
        }, Integer.class);
    }

    default ColumnFuncComparableExpression<Integer> compareTo(ColumnStringFunctionAvailable<TProperty> otherColumn) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                if(otherColumn instanceof DSLSQLFunctionAvailable){
                    SQLFunction columnFunction = ((DSLSQLFunctionAvailable) otherColumn).func().apply(fx);
                    return fx.stringCompareTo(sqlFunction, columnFunction);
                }else{
                    return fx.stringCompareTo(sqlFunction, otherColumn,otherColumn.getValue());
                }
            } else {
                if(otherColumn instanceof DSLSQLFunctionAvailable){
                    SQLFunction columnFunction = ((DSLSQLFunctionAvailable) otherColumn).func().apply(fx);
                    return fx.stringCompareTo(this.getValue(), columnFunction);
                }else{
                    return fx.stringCompareTo(this.getValue(), otherColumn,otherColumn.getValue());
                }
            }
        }, Integer.class);
    }
}
