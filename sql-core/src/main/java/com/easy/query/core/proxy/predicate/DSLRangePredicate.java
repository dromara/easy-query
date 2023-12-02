package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.proxy.SQLPredicate;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLRangePredicate<TProperty> extends TablePropColumn {

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default SQLPredicate rangeOpenClosed(TProperty valLeft, TProperty valRight) {
        return rangeOpenClosed(true, valLeft, true, valRight);
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
    default SQLPredicate rangeOpenClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (!conditionLeft || !conditionRight) {
            return new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.value(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN_CLOSED);
            });
        }
        return SQLPredicate.empty;
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default SQLPredicate rangeOpen(TProperty valLeft, TProperty valRight) {
        return rangeOpen(true, valLeft, true, valRight);
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
    default SQLPredicate rangeOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (!conditionLeft || !conditionRight) {
            return new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.value(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN);
            });
        }
        return SQLPredicate.empty;
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default SQLPredicate rangeClosedOpen(TProperty valLeft, TProperty valRight) {
        return rangeClosedOpen(true, valLeft, true, valRight);
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
    default SQLPredicate rangeClosedOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (!conditionLeft || !conditionRight) {
            return new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.value(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED_OPEN);
            });
        }
        return SQLPredicate.empty;
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default SQLPredicate rangeClosed(TProperty valLeft, TProperty valRight) {
        return rangeClosed(true, valLeft, true, valRight);
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
    default SQLPredicate rangeClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (!conditionLeft || !conditionRight) {
            return new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.value(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED);
            });
        }
        return SQLPredicate.empty;
    }
}
