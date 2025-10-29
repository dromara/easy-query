package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.sql.fill.FillContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
    @Deprecated
    default <TREntity> Query<T1> fillMany(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        return fillMany(fillSetterExpression, targetProperty, selfProperty, produce, false);
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
    @Deprecated
    default <TREntity> Query<T1> fillMany(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T1, Collection<TREntity>> produce, boolean consumeNull) {
        return fillMany(fillSetterExpression, c -> c.self_target(selfProperty, targetProperty, consumeNull), produce);
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
     * @param fillContextConsumer
     * @param produce
     * @param <TREntity>
     * @return
     */
    <TREntity> Query<T1> fillMany(SQLFuncExpression<Query<TREntity>> fillSetterExpression, Consumer<FillContext> fillContextConsumer, BiConsumer<T1, Collection<TREntity>> produce);

    /**
     * <blockquote><pre>
     *     {@code
     *         List<City> cities = easyQuery.queryable(City.class)
     *                 .fillOne(()->{
     *                     return easyQuery.queryable(Province.class);
     *                 },c-> c.self("provinceCode").target("code"), (x, y) -> {
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
    @Deprecated
    default <TREntity> Query<T1> fillOne(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T1, TREntity> produce) {
        return fillOne(fillSetterExpression, targetProperty, selfProperty, produce, false);
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

    @Deprecated
    default <TREntity> Query<T1> fillOne(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T1, TREntity> produce, boolean consumeNull) {
        return fillOne(fillSetterExpression, (fillContext) -> {
            fillContext.self_target(selfProperty, targetProperty, consumeNull);
        }, produce);
    }

    /**
     *
     * <blockquote><pre>
     *     {@code
     *         List<City> cities = easyQuery.queryable(City.class)
     *                 .fillOne(()->{
     *                     return easyQuery.queryable(Province.class);
     *                 },c-> c.self("provinceCode").target("code"), (x, y) -> {
     *                     x.setProvince(y);
     *                 })
     *                 .toList();
     *      }
     * </pre></blockquote>
     *
     * @param fillSetterExpression
     * @param fillContextConsumer  设置self和target还有是否消费null
     * @param produce
     * @param <TREntity>
     * @return
     */
    <TREntity> Query<T1> fillOne(SQLFuncExpression<Query<TREntity>> fillSetterExpression, Consumer<FillContext> fillContextConsumer, BiConsumer<T1, TREntity> produce);

}