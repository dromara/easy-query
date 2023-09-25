package com.easy.query.api4kt.sql.core.filter;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/9/25 17:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtRangePredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLKtWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param valLeft
     * @param valRight
     * @return
     */
    default <TProperty> TChain rangeOpenClosed(KProperty1<T1, TProperty> column, TProperty valLeft, TProperty valRight) {
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
    default <TProperty> TChain rangeOpenClosed(KProperty1<T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> TChain rangeOpenClosed(boolean condition, KProperty1<T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> TChain rangeOpen(KProperty1<T1, TProperty> column, TProperty valLeft, TProperty valRight) {
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
    default <TProperty> TChain rangeOpen(KProperty1<T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> TChain rangeOpen(boolean condition, KProperty1<T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> TChain rangeClosedOpen(KProperty1<T1, TProperty> column, TProperty valLeft, TProperty valRight) {
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
    default <TProperty> TChain rangeClosedOpen(KProperty1<T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> TChain rangeClosedOpen(boolean condition, KProperty1<T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> TChain rangeClosed(KProperty1<T1, TProperty> column, TProperty valLeft, TProperty valRight) {
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
    default <TProperty> TChain rangeClosed(KProperty1<T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> TChain rangeClosed(boolean condition, KProperty1<T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> TChain range(boolean condition, KProperty1<T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight, SQLRangeEnum sqlRange) {
        getWherePredicate().range(condition, EasyKtLambdaUtil.getPropertyName(column), conditionLeft, valLeft, conditionRight, valRight, sqlRange);
        return castChain();
    }
}
