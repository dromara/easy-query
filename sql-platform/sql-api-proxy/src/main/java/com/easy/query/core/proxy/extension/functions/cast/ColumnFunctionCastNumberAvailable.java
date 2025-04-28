package com.easy.query.core.proxy.extension.functions.cast;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;

/**
 * create time 2023/12/25 09:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCastNumberAvailable<TProperty> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {

    /**
     * sql层面进行数字相关的转换
     * @return
     */
    default <T extends Number> ColumnFunctionCompareComparableNumberChainExpression<T> toNumber(Class<T> clazz) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.cast(sqlFunction, clazz);
            } else {
                return fx.cast(this.getValue(), clazz);
            }
        }, clazz);
    }

    /**
     * 编译层面欺骗编译器将其视作Number
     * @return
     */
    default <T extends Number> ColumnFunctionCompareComparableNumberChainExpression<T> asNumber(Class<T> clazz) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                return ((DSLSQLFunctionAvailable) this).func().apply(fx);
            } else {
                return fx.anySQLFunction("{0}", c -> c.column(this.getTable(), this.getValue()));
            }
        }, clazz);
    }

    /**
     * 编译层面欺骗编译器将其视作Integer
     * @return
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> asInteger() {
        return asNumber(Integer.class);
    }


    /**
     * 编译层面欺骗编译器将其视作Long
     * @return
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Long> asLong() {
        return asNumber(Long.class);
    }


    /**
     * 编译层面欺骗编译器将其视作BigDecimal
     * @return
     */
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> asBigDecimal() {
        return asNumber(BigDecimal.class);
    }

}
