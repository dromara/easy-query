package com.easy.query.core.proxy.extension;

import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLAssertAggregatePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLFunctionAggregateComparePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLLikeAggregatePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLLikeFunctionAggregatePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLLikeColumnAggregatePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLColumnCompareAggregatePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLRangeAggregatePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLRangeColumnFunctionAggregatePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLSubQueryAggregatePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLValueAggregatePredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLValuesAggregatePredicate;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/3 09:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFuncComparableExpression<T> extends ColumnComparableExpression<T>, SQLOrderByExpression, PropTypeColumn<T>,
        DSLValueAggregatePredicate<T>,
        DSLLikeAggregatePredicate<T>,
        DSLFunctionAggregateComparePredicate<T>,
        DSLLikeFunctionAggregatePredicate<T>,
        DSLColumnCompareAggregatePredicate<T>,
        DSLLikeColumnAggregatePredicate<T>,
        DSLSubQueryAggregatePredicate<T>,
        DSLValuesAggregatePredicate<T>,
        DSLRangeAggregatePredicate<T>,
        DSLRangeColumnFunctionAggregatePredicate<T>,
        DSLAssertAggregatePredicate<T> {
    @Override
    default SQLSelectAsExpression as(TablePropColumn propColumn) {
        return as(propColumn.getValue());
    }

    @Override
    default SQLSelectAsExpression as(String propertyAlias) {
        return new SQLSelectAsImpl(s -> {
            SQLFunc fx = s.getRuntimeContext().fx();
            s.columnFunc(this.getTable(), func().apply(fx), propertyAlias);
        }, s -> {
            SQLFunc fx = s.getRuntimeContext().fx();
            s.columnFunc(this.getTable(), this.getValue(), func().apply(fx), propertyAlias, () -> {
            });
        }, s -> {
            throw new UnsupportedOperationException();
        });
    }

    @Override
    default <TR> ColumnFuncComparableExpression<TR> asAnyType(Class<TR> clazz) {
        _setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }

    //    default <TR> ColumnFuncComparableExpression<TR> castType(Class<TR> clazz) {
//        return EasyObjectUtil.typeCastNullable(this);
//    }
}
