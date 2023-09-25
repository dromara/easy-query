package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/9/25 17:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyRangePredicate<TChain> extends FilterAvailable, ChainCast<TChain> {

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param valLeft
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeOpenClosed(SQLColumn<TProxy, TProperty> column, TProperty valLeft, TProperty valRight) {
        return rangeOpenClosed(true, column, true, valLeft, true, valRight);
    }
    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeOpenClosed(SQLColumn<TProxy, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        return rangeOpenClosed(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeOpenClosed(boolean condition, SQLColumn<TProxy, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN_CLOSED);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column   数据库列
     * @param valLeft  区间左侧值
     * @param valRight 区间右侧的值
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeOpen(SQLColumn<TProxy, TProperty> column, TProperty valLeft, TProperty valRight) {
        return rangeOpen(true, column, true, valLeft, true, valRight);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column         数据库列
     * @param conditionLeft  是否添加左侧条件
     * @param valLeft        区间左侧值
     * @param conditionRight 是否添加右侧条件
     * @param valRight       区间右侧的值
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeOpen(SQLColumn<TProxy, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        return rangeOpen(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeOpen(boolean condition, SQLColumn<TProxy, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN);
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param valLeft
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeClosedOpen(SQLColumn<TProxy, TProperty> column, TProperty valLeft, TProperty valRight) {
        return rangeClosedOpen(true, column, true, valLeft, true, valRight);
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeClosedOpen(SQLColumn<TProxy, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        return rangeClosedOpen(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeClosedOpen(boolean condition, SQLColumn<TProxy, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED_OPEN);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param valLeft
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeClosed(SQLColumn<TProxy, TProperty> column, TProperty valLeft, TProperty valRight) {
        return rangeClosed(true, column, true, valLeft, true, valRight);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeClosed(SQLColumn<TProxy, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        return rangeClosed(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain rangeClosed(boolean condition, SQLColumn<TProxy, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED);
    }

    /**
     * 自定义范围
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @param sqlRange
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain range(boolean condition, SQLColumn<TProxy, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight, SQLRangeEnum sqlRange) {
        if (condition) {
            getFilter().range(column.getTable(), column.value(), conditionLeft, valLeft, conditionRight, valRight, sqlRange);
        }
        return castChain();
    }

}
