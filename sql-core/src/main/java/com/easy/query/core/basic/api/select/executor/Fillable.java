package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.fill.FillParams;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * create time 2024/7/26 14:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Fillable<T1> {
    /**
     * <blockquote><pre>
     *     {@code
     *          List<Province> provinces =  easyQuery.queryable(Province.class)
     *                 .fillMany(()->{
     *                     return easyQuery.queryable(City.class).where(c -> c.eq(City::getCode, "3306"));
     *                 },"provinceCode", "code", (x, y) -> {
     *                     x.setCities(new ArrayList<>(y));
     *                 }).toList();
     *      }
     * </pre></blockquote>
     *
     * @param fillSetterExpression
     * @param targetProperty       如果需要强类型可以用Lombok的@FieldNameConstants
     * @param selfProperty
     * @param produce
     * @param <TREntity>
     * @return
     */
    default <TREntity> Query<T1> fillMany(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        return fillMany(fillSetterExpression, targetProperty, selfProperty, produce, true);
    }

    /**
     * <blockquote><pre>
     *     {@code
     *          List<Province> provinces =  easyQuery.queryable(Province.class)
     *                 .fillMany(()->{
     *                     return easyQuery.queryable(City.class).where(c -> c.eq(City::getCode, "3306"));
     *                 },"provinceCode", "code", (x, y) -> {
     *                     x.setCities(new ArrayList<>(y));
     *                 },false).toList();
     *      }
     * </pre></blockquote>
     *
     * @param fillSetterExpression
     * @param targetProperty
     * @param selfProperty
     * @param produce
     * @param consumeNull
     * @param <TREntity>
     * @return
     */
    <TREntity> Query<T1> fillMany(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T1, Collection<TREntity>> produce, boolean consumeNull);

    /**
     * <blockquote><pre>
     *     {@code
     *         List<City> cities = easyQuery.queryable(City.class)
     *                 .fillOne(()->{
     *                     return easyQuery.queryable(Province.class);
     *                 },"code","provinceCode", (x, y) -> {
     *                     x.setProvince(y);
     *                 })
     *                 .toList();
     *      }
     * </pre></blockquote>
     *
     * @param fillSetterExpression
     * @param targetProperty
     * @param selfProperty
     * @param produce
     * @param <TREntity>
     * @return
     */
    default <TREntity> Query<T1> fillOne(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T1, TREntity> produce) {
        return fillOne(fillSetterExpression, targetProperty, selfProperty, produce, true);
    }

    /**
     * <blockquote><pre>
     *     {@code
     *         List<City> cities = easyQuery.queryable(City.class)
     *                 .fillOne(()->{
     *                     return easyQuery.queryable(Province.class);
     *                 },"code","provinceCode", (x, y) -> {
     *                     x.setProvince(y);
     *                 },false)
     *                 .toList();
     *      }
     * </pre></blockquote>
     *
     * @param fillSetterExpression
     * @param targetProperty
     * @param selfProperty
     * @param produce
     * @param consumeNull
     * @param <TREntity>
     * @return
     */

    <TREntity> Query<T1> fillOne(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T1, TREntity> produce, boolean consumeNull);

}