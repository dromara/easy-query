package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

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
        if (conditionLeft || conditionRight) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN_CLOSED);
            }));
        }
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
        if (conditionLeft || conditionRight) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN);
            }));
        }
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
        if (conditionLeft || conditionRight) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED_OPEN);
            }));
        }
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
        if (conditionLeft || conditionRight) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED);
            }));
        }
    }
}
