package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyClassUtil;

/**
 * create time 2023/12/2 14:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLRangePredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default void rangeOpenClosed(TProperty valLeft, TProperty valRight) {
        rangeOpenClosed(true, valLeft, true, valRight);
    }

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default void rangeOpenClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        range(getEntitySQLContext(), this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN_CLOSED);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default void rangeOpen(TProperty valLeft, TProperty valRight) {
        rangeOpen(true, valLeft, true, valRight);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default void rangeOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        range(getEntitySQLContext(), this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN);
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default void rangeClosedOpen(TProperty valLeft, TProperty valRight) {
        rangeClosedOpen(true, valLeft, true, valRight);
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default void rangeClosedOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        range(getEntitySQLContext(), this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED_OPEN);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default void rangeClosed(TProperty valLeft, TProperty valRight) {
        rangeClosed(true, valLeft, true, valRight);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default void rangeClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        range(getEntitySQLContext(), this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED);
    }

    static <TProp> void range(EntitySQLContext entitySQLContext, TableAvailable table, String property, boolean conditionLeft, TProp valLeft, boolean conditionRight, TProp valRight, SQLRangeEnum sqlRange) {
        ValueFilter valueFilter = entitySQLContext.getEntityExpressionBuilder().getExpressionContext().getValueFilter();
        boolean acceptLeft = valueFilter.accept(table, property, valLeft);
        boolean acceptRight = valueFilter.accept(table, property, valRight);
        range0(entitySQLContext, table, property, conditionLeft && acceptLeft, valLeft, conditionRight && acceptRight, valRight, sqlRange);
    }

    static <TProp> void range0(EntitySQLContext entitySQLContext, TableAvailable table, String property, boolean conditionLeft, TProp valLeft, boolean conditionRight, TProp valRight, SQLRangeEnum sqlRange) {
        entitySQLContext.accept(new SQLPredicateImpl(filter -> {
            filter.range(table,property,conditionLeft,valLeft,conditionRight,valRight,sqlRange);
        }));
//        if (conditionLeft && conditionRight) {
//            entitySQLContext.accept(new SQLPredicateImpl(filter -> {
//                boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
//                filter.valueCompare(table, property, valLeft, openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE);
//                boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
//                filter.valueCompare(table, property, valRight, openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE);
//            }));
//        } else {
//            if (conditionLeft) {
//                entitySQLContext.accept(new SQLPredicateImpl(filter -> {
//                    boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
//                    filter.valueCompare(table, property, valLeft, openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE);
//                }));
//            }
//            if (conditionRight) {
//                entitySQLContext.accept(new SQLPredicateImpl(filter -> {
//                    boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
//                    filter.valueCompare(table, property, valLeft, openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE);
//                }));
//            }
//        }
    }
}
