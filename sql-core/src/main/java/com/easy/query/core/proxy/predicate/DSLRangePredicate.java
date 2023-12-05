package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.proxy.SQLPredicateExpression;
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
    default SQLPredicateExpression rangeOpenClosed(TProperty valLeft, TProperty valRight) {
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
    default SQLPredicateExpression rangeOpenClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (!conditionLeft || !conditionRight) {
            return new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN_CLOSED);
            });
        }
        return SQLPredicateExpression.empty;
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default SQLPredicateExpression rangeOpen(TProperty valLeft, TProperty valRight) {
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
    default SQLPredicateExpression rangeOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (!conditionLeft || !conditionRight) {
            return new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN);
            });
        }
        return SQLPredicateExpression.empty;
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default SQLPredicateExpression rangeClosedOpen(TProperty valLeft, TProperty valRight) {
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
    default SQLPredicateExpression rangeClosedOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (!conditionLeft || !conditionRight) {
            return new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED_OPEN);
            });
        }
        return SQLPredicateExpression.empty;
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param valLeft
     * @param valRight
     * @return
     */
    default SQLPredicateExpression rangeClosed(TProperty valLeft, TProperty valRight) {
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
    default SQLPredicateExpression rangeClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (!conditionLeft || !conditionRight) {
            return new SQLPredicateImpl(f -> {
                f.range(this.getTable(), this.getValue(), conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED);
            });
        }
        return SQLPredicateExpression.empty;
    }
}
