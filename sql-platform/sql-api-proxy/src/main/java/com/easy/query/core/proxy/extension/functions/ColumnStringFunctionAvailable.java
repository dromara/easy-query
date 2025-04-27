package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastBooleanAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelector;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelectorImpl;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.filter.ColumnFunctionCompareComparableNumberFilterChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.filter.ColumnFunctionCompareComparableStringFilterChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.filter.impl.ColumnFunctionCompareComparableNumberFilterChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.filter.impl.ColumnFunctionCompareComparableStringFilterChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.impl.SQLColumnFunctionCompareComparableExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.Objects;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnStringFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionCompareComparableStringChainExpression<TProperty>>,
        ColumnAggregateFilterFunctionAvailable<TProperty, ColumnFunctionCompareComparableStringFilterChainExpression<TProperty>>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty> {

    @Override
    default ColumnFunctionCompareComparableStringFilterChainExpression<TProperty> max() {
        return createFilterChainExpression(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.max(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            });
        }, getPropertyType());
    }

    @Override
    default ColumnFunctionCompareComparableStringFilterChainExpression<TProperty> min() {
        return createFilterChainExpression(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.min(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
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
    default ColumnFunctionCompareComparableStringChainExpression<TProperty> concat(PropTypeColumn<String> propTypeColumn) {
        return concat(x -> x.expression(propTypeColumn));
    }

    /**
     * 链接常量
     * 调整顺序可以使用{@link com.easy.query.core.proxy.core.Expression#stringFormat(String, Object...)}
     *
     * @param value
     * @return
     */
    default ColumnFunctionCompareComparableStringChainExpression<TProperty> concat(String value) {
        return concat(x -> x.value(value));
    }

    /**
     * 链接多个片段可以是表列,函数,片段,常量
     * 调整顺序可以使用{@link com.easy.query.core.proxy.core.Expression#stringFormat(String, Object...)}
     *
     * @param stringExpressions
     * @return
     */
    default ColumnFunctionCompareComparableStringChainExpression<TProperty> concat(SQLExpression1<ConcatExpressionSelector> stringExpressions) {
        SQLExpression1<ColumnFuncSelector> selector = o -> {
            stringExpressions.apply(new ConcatExpressionSelectorImpl(getEntitySQLContext().getRuntimeContext().fx(), o));
        };
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.concat(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                selector.apply(o);
            });
        }, String.class);
    }
//
//    default ColumnFunctionCompareComparableStringChainExpression<String> appendFormat(String format, Object... args) {
//        Object[] newArgs = EasyArrayUtil.concat(args, new Object[]{this});
//        return Expression.of(getEntitySQLContext()).stringFormat(String.format("{%s}" + format, args.length), newArgs);
//    }

    /**
     * 请使用 nullOrEmpty
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableStringChainExpression<TProperty> nullEmpty() {
        return nullOrDefault(o -> o.value(EasyStringUtil.EMPTY));
    }

    default ColumnFunctionCompareComparableStringChainExpression<TProperty> nullOrEmpty() {
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
    default ColumnFunctionCompareComparableStringChainExpression<String> toLower() {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionCompareComparableStringChainExpression<String> toUpper() {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionCompareComparableStringChainExpression<String> subString(int begin, int length) {
        if (begin < 0) {
            throw new IllegalArgumentException("begin must be greater than 0");
        }
        if (length < 0) {
            throw new IllegalArgumentException("length must be greater than 0");
        }
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.subString(sqlFunction, begin, length);
            } else {
                return fx.subString(this.getValue(), begin, length);
            }
        }, String.class);
    }

    default <T extends Number> ColumnFunctionCompareComparableStringChainExpression<String> subString(PropTypeColumn<T> begin, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must be greater than 0");
        }
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.subString(selector -> {
                PropTypeColumn.columnFuncSelector(selector, this);
                PropTypeColumn.columnFuncSelector(selector, begin);
                selector.format(length);
            });
        }, String.class);
    }

    default <T1 extends Number, T2 extends Number> ColumnFunctionCompareComparableStringChainExpression<String> subString(PropTypeColumn<T1> begin, PropTypeColumn<T2> length) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.subString(selector -> {
                PropTypeColumn.columnFuncSelector(selector, this);
                PropTypeColumn.columnFuncSelector(selector, begin);
                PropTypeColumn.columnFuncSelector(selector, length);
            });
        }, String.class);
    }

    default <T extends Number> ColumnFunctionCompareComparableStringChainExpression<String> subString(int begin, PropTypeColumn<T> length) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionCompareComparableStringChainExpression<String> trim() {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
     * {@link #ltrim()}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableStringChainExpression<String> trimStart() {
        return ltrim();
    }

    /**
     * 去头部空格
     *
     * @return
     */
    default ColumnFunctionCompareComparableStringChainExpression<String> ltrim() {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
     * {@link #rtrim()}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableStringChainExpression<String> trimEnd() {
        return rtrim();
    }

    /**
     * 去尾部空格
     *
     * @return
     */
    default ColumnFunctionCompareComparableStringChainExpression<String> rtrim() {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimEnd(sqlFunction);
            } else {
                return fx.trimEnd(this.getValue());
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableStringChainExpression<String> replace(String oldValue, String newValue) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionCompareComparableStringChainExpression<String> leftPad(int totalWidth) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction, totalWidth);
            } else {
                return fx.leftPad(this.getValue(), totalWidth);
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableStringChainExpression<String> leftPad(int totalWidth, char paddingChar) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionCompareComparableStringChainExpression<String> rightPad(int totalWidth) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction, totalWidth);
            } else {
                return fx.rightPad(this.getValue(), totalWidth);
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableStringChainExpression<String> rightPad(int totalWidth, char paddingChar) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction, totalWidth, paddingChar);
            } else {
                return fx.rightPad(this.getValue(), totalWidth, paddingChar);
            }
        }, String.class);
    }

    /**
     * 请使用{@link #joining(String)}
     *
     * @param delimiter
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableStringFilterChainExpression<TProperty> join(String delimiter) {
        return joining(delimiter, false);
    }

    /**
     * 请使用 {@link #joining(String, boolean)}
     *
     * @param delimiter
     * @param distinct
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableStringFilterChainExpression<TProperty> join(String delimiter, boolean distinct) {
        return joining(delimiter, distinct);
    }

    default ColumnFunctionCompareComparableStringFilterChainExpression<TProperty> joining(String delimiter) {
        return joining(delimiter, false);
    }

    default ColumnFunctionCompareComparableStringFilterChainExpression<TProperty> joining(String delimiter, boolean distinct) {
        return createFilterChainExpression(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.joining(x -> {
                x.value(delimiter);
                PropTypeColumn.columnFuncSelector(x, self);
            }, distinct);
        }, String.class);


    }

    /**
     * 长度函数返回当前列的长度值
     *
     * @return
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> length() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.length(sqlFunction);
            } else {
                return fx.length(this.getValue());
            }
        }, Integer.class);
    }

    @Override
    default ColumnFunctionCompareComparableStringChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
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
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> indexOf(String val) {
        String property = this.getValue();
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), property, fx -> {
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
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> indexOf(PropTypeColumn<TProperty> stringSegment) {
        String property = this.getValue();
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), property, fx -> {
            return fx.indexOf(s -> {
                PropTypeColumn.columnFuncSelector(s, this);
                PropTypeColumn.columnFuncSelector(s, stringSegment);
            });
        }, Integer.class);
    }

    @Override
    default ColumnFunctionCompareComparableStringFilterChainExpression<TProperty> createFilterChainExpression(EntitySQLContext entitySQLContext, PropTypeColumn<?> self, TableAvailable table, String property, SQLFuncExpression2<PropTypeColumn<?>, SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionCompareComparableStringFilterChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), func, getPropertyType());
    }
}
