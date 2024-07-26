package com.easy.query.core.basic.api.select;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.api.select.executor.Fillable;
import com.easy.query.core.basic.api.select.executor.MapAble;
import com.easy.query.core.basic.api.select.executor.QueryExecutable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.fill.FillExpression;
import com.easy.query.core.expression.sql.fill.FillParams;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author xuejiaming
 * @FileName: Query.java
 * @Description: 文件说明
 * @Date: 2023/3/3 16:30
 */
public interface Query<T> extends QueryAvailable<T>, QueryExecutable<T>, MapAble<T>, Fillable<T> {

    /**
     * 只clone表达式共享上下文
     * 如果是两个独立的表达式建议重新创建如果是
     *
     * @return
     */
    Query<T> cloneQueryable();

    /**
     * 当前的查询表达式
     *
     * @return
     */
    EntityQueryExpressionBuilder getSQLEntityExpressionBuilder();

    /**
     * 设置column所有join表都会生效
     * queryable.select(" t.name,t.age ")通过字符串实现要查询的列
     *
     * @param columns
     * @return
     */
    Query<T> select(String columns);

    /**
     * 返回执行sql
     *
     * @return
     */
    default String toSQL() {
        return toSQL(queryClass());
    }

    /**
     * 传入生成sql的上下文用来获取生成sql后的表达式内部的参数
     *
     * @param toSQLContext
     * @return
     */

    default String toSQL(ToSQLContext toSQLContext) {
        return toSQL(queryClass(), toSQLContext);
    }

    /**
     * 传入生成sql的上下文用来获取生成sql后的表达式内部的参数
     *
     * @return 包含sql和sql结果比如参数
     */

    default ToSQLResult toSQLResult() {
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(getSQLEntityExpressionBuilder().getExpressionContext().getTableContext(), true);
        String sql = toSQL(queryClass(), toSQLContext);
        return new ToSQLResult(sql, toSQLContext);
    }

    /**
     * 返回执行sql
     *
     * @param resultClass
     * @param <TR>
     * @return
     */
    default <TR> String toSQL(Class<TR> resultClass) {
        TableContext tableContext = getSQLEntityExpressionBuilder().getExpressionContext().getTableContext();
        return toSQL(resultClass, DefaultToSQLContext.defaultToSQLContext(tableContext, true));
    }

    <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext);

    /**
     * 返回long类型的数量结果
     * eg. SELECT  COUNT(*)  FROM table t [WHERE t.`columns` = ?]
     *
     * @return
     */
    long count();

    /**
     * 返回int类型的数量结果
     * eg. SELECT  COUNT(*)  FROM table t [WHERE t.`columns` = ?]
     *
     * @return
     */

    default int intCount() {
        return (int) count();
    }

    /**
     * 判断是否存在
     * eg. SELECT  1  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @return 如果有行数那么就就是返回true表示存在，否则返回false表示不存在
     */
    boolean any();

    default void required() {
        required(null, null);
    }

    default void required(String msg) {
        required(() -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createRequiredException(this, msg, null));
    }

    default void required(String msg, String code) {
        required(() -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createRequiredException(this, msg, code));
    }

    default void required(Supplier<RuntimeException> throwFunc) {
        boolean any = any();
        if (!any) {
            throw throwFunc.get();
        }
    }

    /**
     * 当未查询到结果返回null
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @return
     */
    default @Nullable T firstOrNull() {
        return firstOrNull(queryClass());
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param msg
     * @param code
     * @return
     */
    default @NotNull T firstNotNull(String msg, String code) {
        return firstNotNull(queryClass(), msg, code);
    }

    default <TR> @NotNull TR firstNotNull(Class<TR> resultClass, String msg, String code) {
        return firstNotNull(resultClass, () -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createFirstNotNullException(this, msg, code));
    }

    default @NotNull T firstNotNull(Supplier<RuntimeException> throwFunc) {
        return firstNotNull(queryClass(), throwFunc);
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     *
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    default @Nullable T singleOrNull() {
        return singleOrNull(queryClass());
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * 返回值包装成 Optional 以便后续调用
     *
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    default Optional<T> singleOptional() {
        return Optional.ofNullable(singleOrNull(queryClass()));
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * 查询出来的结果值如果为 null 则返回默认值
     *
     * @param defaultValue 默认值， 当查询结果为 null 时返回默认值
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    default T singleOrDefault(T defaultValue) {
        T singleOrNull = singleOrNull(queryClass());
        return singleOrNull == null ? defaultValue : singleOrNull;
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * 查询出来的结果值如果经 usingDefault 判断为 true 则返回 defaultValue
     *
     * @param defaultValue 默认值， 当 usingDefault.test(singleOrNullValue) 为 true 时返回默认值
     * @param usingDefault 判断是否使用默认值
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    default T singleOrDefault(T defaultValue, @Nullable Predicate<@Nullable T> usingDefault) {
        T singleOrNullValue = singleOrNull(queryClass());
        return (usingDefault != null && usingDefault.test(singleOrNullValue)) ? defaultValue : singleOrNullValue;
    }


    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param msg
     * @param code
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     * @throws EasyQuerySingleNotNullException     如果查询不到数据
     */
    default @NotNull T singleNotNull(String msg, String code) {
        return singleNotNull(queryClass(), msg, code);
    }

    default <TR> @NotNull TR singleNotNull(Class<TR> resultClass, String msg, String code) {
        return singleNotNull(resultClass, () -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createSingleNotNullException(this, msg, code));
    }

    default @NotNull T singleNotNull(Supplier<RuntimeException> throwFunc) {
        return singleNotNull(queryClass(), throwFunc);
    }


    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     * <blockquote><pre>
     * {@code
     *
     * @EasyAssertMessage(
     *         notNull = "未找到主题信息"
     * )
     * public class Topic{}
     *
     *
     * @EasyAssertMessage(
     *         //notNull = "未找到主题信息",
     *         findNotNull = "未找到主题信息",
     *         firstNotNull = "未找到主题信息",
     *         singleNotNull = "未找到主题信息",
     *         singleMoreThan = "找到多条主题信息"
     * )
     * public class Topic{}
     *                    }
     * </pre></blockquote>
     *
     * @param msg
     * @param code
     * @return 返回一个不能为空的结果
     * @throws com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException 如果存在多个主键
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException    如果没有主键
     * @throws com.easy.query.core.exception.EasyQueryFindNotNullException     可以通过 {@link AssertExceptionFactory#createFindNotNullException(Query, String, String)} 自定义
     */
    default @NotNull T findNotNull(Object id, String msg, String code) {
        return findNotNull(id, () -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createFindNotNullException(this, msg, code));
    }

    /**
     * 返回所有的查询结果集
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @return 获取查询结果集
     */
    default @NotNull List<T> toList() {
        return toList(queryClass());
    }

    /**
     * 可迭代的流式结果集
     *
     * <blockquote><pre>
     *     {@code
     *
     * try(JdbcStreamResult<BlogEntity> streamResult = easyQuery.queryable(BlogEntity.class).toStreamResult()){
     *
     *             int i = 0;
     *             for (BlogEntity blog : streamResult.getStreamIterable()) {
     *                 String indexStr = String.valueOf(i);
     *                 Assert.assertEquals(indexStr, blog.getId());
     *                 Assert.assertEquals(indexStr, blog.getCreateBy());
     *                 Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
     *                 Assert.assertEquals(indexStr, blog.getUpdateBy());
     *                 i++;
     *             }
     *         } catch (SQLException e) {
     *             throw new RuntimeException(e);
     *         }
     * </pre></blockquote>
     *
     * @return
     */
    default @NotNull JdbcStreamResult<T> toStreamResult(SQLConsumer<Statement> configurer) {
        return toStreamResult(queryClass(), configurer);
    }

    /**
     * 去重
     * eg. SELECT DISTINCT projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @return
     */
    default @NotNull Query<T> distinct() {
        return distinct(true);
    }


    /**
     * 去重 {@param condition} 为true就使用distinct,false则不使用
     * eg. SELECT DISTINCT projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param condition
     * @return
     */
    @NotNull
    Query<T> distinct(boolean condition);

    default Query<T> limit(long rows) {
        return limit(true, rows);
    }

    default Query<T> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    default Query<T> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    Query<T> limit(boolean condition, long offset, long rows);


    Query<T> asTracking();

    Query<T> asNoTracking();

    Query<T> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    Query<T> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    Query<T> useConnectionMode(ConnectionModeEnum connectionMode);

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
     * @param fillSetterExpression
     * @param targetProperty
     * @param selfProperty
     * @param produce
     * @param consumeNull
     * @return
     * @param <TREntity>
     */
    @Override
    default <TREntity> Query<T> fillMany(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T, Collection<TREntity>> produce, boolean consumeNull) {
        SQLFuncExpression1<FillParams, Query<?>> fillQueryableExpression = EasySQLExpressionUtil.getFillSQLExpression(fillSetterExpression, targetProperty, consumeNull);
        FillExpression fillExpression = new FillExpression(queryClass(), true, targetProperty, selfProperty, fillQueryableExpression);
        fillExpression.setProduceMany(EasyObjectUtil.typeCastNullable(produce));
        getSQLEntityExpressionBuilder().getExpressionContext().getFills().add(fillExpression);
        return this;
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
     * @param fillSetterExpression
     * @param targetProperty
     * @param selfProperty
     * @param produce
     * @param consumeNull
     * @return
     * @param <TREntity>
     */
    @Override
    default <TREntity> Query<T> fillOne(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T, TREntity> produce, boolean consumeNull) {
        SQLFuncExpression1<FillParams, Query<?>> fillQueryableExpression = EasySQLExpressionUtil.getFillSQLExpression(fillSetterExpression, targetProperty, consumeNull);
        FillExpression fillExpression = new FillExpression(queryClass(), false, targetProperty, selfProperty, fillQueryableExpression);
        fillExpression.setProduceOne(EasyObjectUtil.typeCastNullable(produce));
        getSQLEntityExpressionBuilder().getExpressionContext().getFills().add(fillExpression);
        return this;
    }

    //    @Override
//    default  <TREntity> Query<T> fillMany(SQLFuncExpression1<FillSelector, ClientQueryable<TREntity>> fillSetterExpression, String targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
//        SQLFuncExpression1<FillParams, ClientQueryable<?>> fillQueryableExpression = fillParams -> {
//            FillSelectorImpl fillSelector = new FillSelectorImpl(runtimeContext, fillParams);
//            return fillSetterExpression.apply(fillSelector);
//        };
//        FillExpression fillExpression = new FillExpression(queryClass(), true, targetProperty, EasyObjectUtil.typeCastNullable(selfProperty), fillQueryableExpression);
//        fillExpression.setProduceMany(EasyObjectUtil.typeCastNullable(produce));
//        entityQueryExpressionBuilder.getExpressionContext().getFills().add(fillExpression);
//        return this;
//    }
//
//    @Override
//    public <TREntity> Query<T1> fillOne(SQLFuncExpression1<FillSelector, ClientQueryable<TREntity>> fillSetterExpression, String targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, TREntity> produce) {
//        SQLFuncExpression1<FillParams, ClientQueryable<?>> fillQueryableExpression = fillParams -> {
//            FillSelectorImpl fillSelector = new FillSelectorImpl(runtimeContext, fillParams);
//            return fillSetterExpression.apply(fillSelector);
//        };
//        FillExpression fillExpression = new FillExpression(queryClass(), false, targetProperty, EasyObjectUtil.typeCastNullable(selfProperty), fillQueryableExpression);
//        fillExpression.setProduceOne(EasyObjectUtil.typeCastNullable(produce));
//        entityQueryExpressionBuilder.getExpressionContext().getFills().add(fillExpression);
//        return this;
//    }
}
