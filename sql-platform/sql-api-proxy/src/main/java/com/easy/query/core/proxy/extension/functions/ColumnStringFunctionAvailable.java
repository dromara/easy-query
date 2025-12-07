package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastBooleanAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastJSONObjectAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelector;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelectorImpl;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.StringFilterTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.impl.StringFilterTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.NumberTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.StringTypeExpressionImpl;
import com.easy.query.core.proxy.impl.SQLColumnFunctionCompareComparableExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyStringUtil;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnStringFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, StringTypeExpression<TProperty>>,
        ColumnAggregateFilterFunctionAvailable<TProperty, StringFilterTypeExpression<TProperty>>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty>,
        ColumnFunctionCastJSONObjectAvailable<TProperty> {

    @Override
    default StringFilterTypeExpression<TProperty> max() {
        return createFilterChainExpression((self, fx) -> {
            return fx.max(x -> {
                PropTypeColumn.acceptAnyValue(x, self);
            });
        }, getPropertyType());
    }

    @Override
    default StringFilterTypeExpression<TProperty> min() {
        return createFilterChainExpression((self, fx) -> {
            return fx.min(x -> {
                PropTypeColumn.acceptAnyValue(x, self);
            });
        }, getPropertyType());
    }

    /**
     * 链接表列
     * 调整顺序可以使用{@link com.easy.query.core.proxy.core.Expression#stringFormat(String, Object...)}
     *
     * @param propTypeColumn
     * @return
     */
    default StringTypeExpression<TProperty> concat(PropTypeColumn<String> propTypeColumn) {
        return concat(x -> x.expression(propTypeColumn));
    }

    /**
     * 链接常量
     * 调整顺序可以使用{@link com.easy.query.core.proxy.core.Expression#stringFormat(String, Object...)}
     *
     * @param value
     * @return
     */
    default StringTypeExpression<TProperty> concat(String value) {
        return concat(x -> x.value(value));
    }

    /**
     * 链接多个片段可以是表列,函数,片段,常量
     * 调整顺序可以使用{@link com.easy.query.core.proxy.core.Expression#stringFormat(String, Object...)}
     *
     * @param stringExpressions
     * @return
     */
    default StringTypeExpression<TProperty> concat(SQLActionExpression1<ConcatExpressionSelector> stringExpressions) {
        SQLActionExpression1<ColumnFuncSelector> selector = o -> {
            stringExpressions.apply(new ConcatExpressionSelectorImpl(getEntitySQLContext().getRuntimeContext().fx(), o));
        };
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.concat(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                selector.apply(o);
            });
        }, String.class);
    }

    default StringTypeExpression<TProperty> nullOrEmpty() {
        return nullOrDefault(o -> o.value(EasyStringUtil.EMPTY));
    }

//    @Override
//    default <T> ColumnFunctionComparableStringChainExpression<TProperty> nullDefault(SQLExpression1<ProxyColumnFuncSelector> selector) {
//        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
     *
     * @return
     */
    default StringTypeExpression<String> toLower() {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
     *
     * @return
     */
    default StringTypeExpression<String> toUpper() {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
     *
     * @param begin  开始索引默认0
     * @param length 截取长度
     * @return 支持比较操作的字符串方法表达式
     */
    default StringTypeExpression<String> subString(int begin, int length) {
        if (begin < 0) {
            throw new IllegalArgumentException("begin must be greater than 0");
        }
        if (length < 0) {
            throw new IllegalArgumentException("length must be greater than 0");
        }
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.subString(sqlFunction, begin, length);
            } else {
                return fx.subString(this.getValue(), begin, length);
            }
        }, String.class);
    }

    default <T extends Number> StringTypeExpression<String> subString(PropTypeColumn<T> begin, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must be greater than 0");
        }
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.subString(selector -> {
                PropTypeColumn.columnFuncSelector(selector, this);
                PropTypeColumn.columnFuncSelector(selector, begin);
                selector.format(length);
            });
        }, String.class);
    }

    default <T1 extends Number, T2 extends Number> StringTypeExpression<String> subString(PropTypeColumn<T1> begin, PropTypeColumn<T2> length) {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.subString(selector -> {
                PropTypeColumn.columnFuncSelector(selector, this);
                PropTypeColumn.columnFuncSelector(selector, begin);
                PropTypeColumn.columnFuncSelector(selector, length);
            });
        }, String.class);
    }

    default <T extends Number> StringTypeExpression<String> subString(int begin, PropTypeColumn<T> length) {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.subString(selector -> {
                PropTypeColumn.columnFuncSelector(selector, this);
                selector.format(begin);
                PropTypeColumn.columnFuncSelector(selector, length);
            });
        }, String.class);
    }

    /**
     * 去空格
     *
     * @return
     */
    default StringTypeExpression<String> trim() {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
     *
     * @return
     */
    default StringTypeExpression<String> ltrim() {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
     *
     * @return
     */
    default StringTypeExpression<String> rtrim() {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimEnd(sqlFunction);
            } else {
                return fx.trimEnd(this.getValue());
            }
        }, String.class);
    }

    default StringTypeExpression<String> replace(String oldValue, String newValue) {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.replace(sqlFunction, oldValue, newValue);
            } else {
                return fx.replace(this.getValue(), oldValue, newValue);
            }
        }, String.class);
    }

    /**
     * 左侧补齐totalWidth位数用空格补齐
     *
     * @param totalWidth
     * @return
     */
    default StringTypeExpression<String> leftPad(int totalWidth) {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction, totalWidth);
            } else {
                return fx.leftPad(this.getValue(), totalWidth);
            }
        }, String.class);
    }

    default StringTypeExpression<String> leftPad(int totalWidth, char paddingChar) {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction, totalWidth, paddingChar);
            } else {
                return fx.leftPad(this.getValue(), totalWidth, paddingChar);
            }
        }, String.class);
    }

    /**
     * 右侧补齐totalWidth位数用空格补齐
     *
     * @param totalWidth
     * @return
     */
    default StringTypeExpression<String> rightPad(int totalWidth) {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction, totalWidth);
            } else {
                return fx.rightPad(this.getValue(), totalWidth);
            }
        }, String.class);
    }

    default StringTypeExpression<String> rightPad(int totalWidth, char paddingChar) {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction, totalWidth, paddingChar);
            } else {
                return fx.rightPad(this.getValue(), totalWidth, paddingChar);
            }
        }, String.class);
    }

    default StringFilterTypeExpression<TProperty> joining(String delimiter) {
        return joining(delimiter, false);
    }

    default StringFilterTypeExpression<TProperty> joining(String delimiter, boolean distinct) {
        return createFilterChainExpression((self, fx) -> {
            return fx.joining(x -> {
                x.value(delimiter);
                PropTypeColumn.acceptAnyValue(x, self);
            }, distinct);
        }, String.class);


    }

    /**
     * 长度函数返回当前列的长度值
     *
     * @return
     */
    default NumberTypeExpression<Integer> length() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.length(sqlFunction);
            } else {
                return fx.length(this.getValue());
            }
        }, Integer.class);
    }

    @Override
    default StringTypeExpression<TProperty> createChainExpression(Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, propType);
    }

    /**
     * 比较两个字符串 一样返回0 前一个比后一个大返回1 前一个比后一个小返回-1
     *
     * @param comparedValue
     * @return
     */
    default ColumnFuncComparableExpression<Integer> compareTo(String comparedValue) {
        return new SQLColumnFunctionCompareComparableExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.stringCompareTo(sqlFunction, comparedValue);
            } else {
                return fx.stringCompareTo(this.getValue(), comparedValue);
            }
        }, Integer.class);
    }

    default ColumnFuncComparableExpression<Integer> compareTo(PropTypeColumn<TProperty> otherColumn) {
        return new SQLColumnFunctionCompareComparableExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                if (otherColumn instanceof DSLSQLFunctionAvailable) {
                    SQLFunction columnFunction = ((DSLSQLFunctionAvailable) otherColumn).func().apply(fx);
                    return fx.stringCompareTo(sqlFunction, columnFunction);
                } else {
                    return fx.stringCompareTo(sqlFunction, otherColumn, otherColumn.getValue());
                }
            } else {
                if (otherColumn instanceof DSLSQLFunctionAvailable) {
                    SQLFunction columnFunction = ((DSLSQLFunctionAvailable) otherColumn).func().apply(fx);
                    return fx.stringCompareTo(this.getValue(), columnFunction);
                } else {
                    return fx.stringCompareTo(this.getValue(), otherColumn, otherColumn.getValue());
                }
            }
        }, Integer.class);
    }

    /**
     * 字符串P按断是否包含某个字符串返回索引大于等于0表示包含
     *
     * @param val 开始索引默认0
     * @return 字符串在字符串中第一次出现的索引值，如果字符串中不包含该字符则返回-1
     */
    default NumberTypeExpression<Integer> indexOf(String val) {
        String property = this.getValue();
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), property, fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.indexOf(s -> {
                    s.sqlFunc(sqlFunction).value(val);
                });
            } else {
                return fx.indexOf(s -> {
                    s.column(property).value(val);
                });
            }
        }, Integer.class);
    }

    default NumberTypeExpression<Integer> indexOf(PropTypeColumn<TProperty> stringSegment) {
        String property = this.getValue();
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), property, fx -> {
            return fx.indexOf(s -> {
                PropTypeColumn.columnFuncSelector(s, this);
                PropTypeColumn.columnFuncSelector(s, stringSegment);
            });
        }, Integer.class);
    }

    @Override
    default StringFilterTypeExpression<TProperty> createFilterChainExpression(SQLFuncExpression2<PropTypeColumn<?>, SQLFunc, SQLFunction> func, Class<?> propType) {
        return new StringFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), func, propType);
    }
}
