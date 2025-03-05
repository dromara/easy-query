package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.sql.core.SQLLambdaKtNative;
import com.easy.query.api4kt.sql.core.available.SQLKtLambdaFuncAvailable;
import com.easy.query.api4kt.sql.core.filter.SQLKtAssertPredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtFuncValuePredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtLikePredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtRangePredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtSelfPredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtSubQueryPredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtValuePredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtValuesPredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import kotlin.Deprecated;
import kotlin.reflect.KProperty1;

/**
 * @author xuejiaming
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 */
public interface SQLKtWherePredicate<T1> extends EntitySQLTableOwner<T1>, SQLKtLambdaFuncAvailable<T1>, SQLLambdaKtNative<T1, SQLKtWherePredicate<T1>>
        , SQLKtAssertPredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtRangePredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtSelfPredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtSubQueryPredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtFuncValuePredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtValuePredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtValuesPredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtLikePredicate<T1, SQLKtWherePredicate<T1>> {
    WherePredicate<T1> getWherePredicate();

    default TableAvailable getTable() {
        return getWherePredicate().getTable();
    }
    default QueryRuntimeContext getRuntimeContext() {
        return getWherePredicate().getRuntimeContext();
    }

    default <TProperty> SQLKtWherePredicate<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    default <TProperty> SQLKtWherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        getWherePredicate().columnFunc(condition, columnPropertyFunction, sqlPredicateCompare, val);
        return this;
    }

    default <T2> SQLKtWherePredicate<T2> then(SQLKtWherePredicate<T2> sub) {
        getWherePredicate().then(sub.getWherePredicate());
        return sub;
    }

    default SQLKtWherePredicate<T1> and() {
        return and(true);
    }

    default SQLKtWherePredicate<T1> and(boolean condition) {
        getWherePredicate().and(condition);
        return this;
    }

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * .where((t1, t2) -> {
     *                      t1.and(()->{
     *                          t1.eq(Topic::getStars, 1).or();
     *                          t2.eq(BlogEntity::getOrder, "1");
     *                      }); //(t.`stars` = ? OR t1.`order` = ?)
     *                      t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                      t2.and(()->{
     *                          t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                              t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                      .or();
     *                              t2.like(BlogEntity::getContent,"111");
     *                          });
     *                      });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                  })
     * @param sqlWherePredicateSQLExpression
     * @return
     */
    @Deprecated(message = "使用无参数lambda模式的and")
    default SQLKtWherePredicate<T1> and(SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
    }

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * .where((t1, t2) -> {
     *                      t1.and(()->{
     *                          t1.eq(Topic::getStars, 1).or();
     *                          t2.eq(BlogEntity::getOrder, "1");
     *                      }); //(t.`stars` = ? OR t1.`order` = ?)
     *                      t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                      t2.and(()->{
     *                          t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                              t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                      .or();
     *                              t2.like(BlogEntity::getContent,"111");
     *                          });
     *                      });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                  })
     * @param condition
     * @param sqlWherePredicateSQLExpression
     * @return
     */
    @Deprecated(message = "使用无参数lambda模式的and")
    SQLKtWherePredicate<T1> and(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression);
    default SQLKtWherePredicate<T1> and(SQLActionExpression sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
    }

    default SQLKtWherePredicate<T1> and(boolean condition, SQLActionExpression sqlWherePredicateSQLExpression){
        if(condition){
            getWherePredicate().and(sqlWherePredicateSQLExpression);
        }
        return this;
    }

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * <blockquote><pre>
     * {@code
     *   .where((t1, t2) -> {
     *                        t1.and(()->{
     *                            t1.eq(Topic::getStars, 1).or();
     *                            t2.eq(BlogEntity::getOrder, "1");
     *                        }); //(t.`stars` = ? OR t1.`order` = ?)
     *                        t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                        t2.and(()->{
     *                            t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                                t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                        .or();
     *                                t2.like(BlogEntity::getContent,"111");
     *                            });
     *                        });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                    })}
     * </pre></blockquote>
     * @param t2SQLKtWherePredicate
     * @param sqlWherePredicateSQLExpression
     * @return
     * @param <T2>
     */
    @Deprecated(message = "使用无参数lambda模式的and")
    default <T2> SQLKtWherePredicate<T1> and(SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return and(true, t2SQLKtWherePredicate, sqlWherePredicateSQLExpression);
    }

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * <blockquote><pre>
     * {@code
     *   .where((t1, t2) -> {
     *                        t1.and(()->{
     *                            t1.eq(Topic::getStars, 1).or();
     *                            t2.eq(BlogEntity::getOrder, "1");
     *                        }); //(t.`stars` = ? OR t1.`order` = ?)
     *                        t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                        t2.and(()->{
     *                            t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                                t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                        .or();
     *                                t2.like(BlogEntity::getContent,"111");
     *                            });
     *                        });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                    })}
     * </pre></blockquote>
     * @param condition
     * @param t2SQLKtWherePredicate
     * @param sqlWherePredicateSQLExpression
     * @return
     * @param <T2>
     */
    @Deprecated(message = "采用无参数lambda模式的and")
    <T2> SQLKtWherePredicate<T1> and(boolean condition, SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression);

    default SQLKtWherePredicate<T1> or() {
        return or(true);
    }

    default SQLKtWherePredicate<T1> or(boolean condition) {
        getWherePredicate().or(condition);
        return this;
    }

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * <blockquote><pre>
     * {@code
     *   .where((t1, t2) -> {
     *                        t1.and(()->{
     *                            t1.eq(Topic::getStars, 1).or();
     *                            t2.eq(BlogEntity::getOrder, "1");
     *                        }); //(t.`stars` = ? OR t1.`order` = ?)
     *                        t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                        t2.and(()->{
     *                            t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                                t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                        .or();
     *                                t2.like(BlogEntity::getContent,"111");
     *                            });
     *                        });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                    })}
     * </pre></blockquote>
     * @param sqlWherePredicateSQLExpression
     * @return
     */
    @Deprecated(message = "采用无参数lamda的or")
    default SQLKtWherePredicate<T1> or(SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * <blockquote><pre>
     * {@code
     *   .where((t1, t2) -> {
     *                        t1.and(()->{
     *                            t1.eq(Topic::getStars, 1).or();
     *                            t2.eq(BlogEntity::getOrder, "1");
     *                        }); //(t.`stars` = ? OR t1.`order` = ?)
     *                        t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                        t2.and(()->{
     *                            t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                                t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                        .or();
     *                                t2.like(BlogEntity::getContent,"111");
     *                            });
     *                        });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                    })}
     * </pre></blockquote>
     * @param condition
     * @param sqlWherePredicateSQLExpression
     * @return
     */
    @Deprecated(message = "采用无参数lambda的or")
    SQLKtWherePredicate<T1> or(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression);

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * <blockquote><pre>
     * {@code
     *   .where((t1, t2) -> {
     *                        t1.and(()->{
     *                            t1.eq(Topic::getStars, 1).or();
     *                            t2.eq(BlogEntity::getOrder, "1");
     *                        }); //(t.`stars` = ? OR t1.`order` = ?)
     *                        t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                        t2.and(()->{
     *                            t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                                t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                        .or();
     *                                t2.like(BlogEntity::getContent,"111");
     *                            });
     *                        });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                    })}
     * </pre></blockquote>
     * @param sqlWherePredicateSQLExpression
     * @return
     */
    default SQLKtWherePredicate<T1> or(SQLActionExpression sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * <blockquote><pre>
     * {@code
     *   .where((t1, t2) -> {
     *                        t1.and(()->{
     *                            t1.eq(Topic::getStars, 1).or();
     *                            t2.eq(BlogEntity::getOrder, "1");
     *                        }); //(t.`stars` = ? OR t1.`order` = ?)
     *                        t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                        t2.and(()->{
     *                            t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                                t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                        .or();
     *                                t2.like(BlogEntity::getContent,"111");
     *                            });
     *                        });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                    })}
     * </pre></blockquote>
     * @param condition
     * @param sqlWherePredicateSQLExpression
     * @return
     */
    default SQLKtWherePredicate<T1> or(boolean condition, SQLActionExpression sqlWherePredicateSQLExpression){
        if(condition){
            getWherePredicate().or(sqlWherePredicateSQLExpression);
        }
        return this;
    }

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * <blockquote><pre>
     * {@code
     *   .where((t1, t2) -> {
     *                        t1.and(()->{
     *                            t1.eq(Topic::getStars, 1).or();
     *                            t2.eq(BlogEntity::getOrder, "1");
     *                        }); //(t.`stars` = ? OR t1.`order` = ?)
     *                        t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                        t2.and(()->{
     *                            t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                                t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                        .or();
     *                                t2.like(BlogEntity::getContent,"111");
     *                            });
     *                        });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                    })}
     * </pre></blockquote>
     * @param t2SQLKtWherePredicate
     * @param sqlWherePredicateSQLExpression
     * @return
     * @param <T2>
     */
    @Deprecated(message = "采用单参数无,具体看注释")

    default <T2> SQLKtWherePredicate<T1> or(SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return or(true, t2SQLKtWherePredicate, sqlWherePredicateSQLExpression);
    }

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * <blockquote><pre>
     * {@code
     *   .where((t1, t2) -> {
     *                        t1.and(()->{
     *                            t1.eq(Topic::getStars, 1).or();
     *                            t2.eq(BlogEntity::getOrder, "1");
     *                        }); //(t.`stars` = ? OR t1.`order` = ?)
     *                        t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                        t2.and(()->{
     *                            t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                                t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                        .or();
     *                                t2.like(BlogEntity::getContent,"111");
     *                            });
     *                        });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                    })}
     * </pre></blockquote>
     * @param condition
     * @param t2SQLKtWherePredicate
     * @param sqlWherePredicateSQLExpression
     * @return
     * @param <T2>
     */
    @Deprecated(message = "采用无参数or,具体看注释")
    <T2> SQLKtWherePredicate<T1> or(boolean condition, SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression);

    /**
     * 采用无参数and or处理括号和多表问题
     * (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     * <blockquote><pre>
     * {@code
     *   .where((t1, t2) -> {
     *                        t1.and(()->{
     *                            t1.eq(Topic::getStars, 1).or();
     *                            t2.eq(BlogEntity::getOrder, "1");
     *                        }); //(t.`stars` = ? OR t1.`order` = ?)
     *                        t2.eq(BlogEntity::getId,1);//t1.`id` = ?
     *                        t2.and(()->{
     *                            t1.eq(Topic::getStars, 1).or(()->{ //使用or表示or内部是括号括号和外面是or链接
     *                                t1.eq(Topic::getCreateTime,LocalDateTime.now())
     *                                        .or();
     *                                t2.like(BlogEntity::getContent,"111");
     *                            });
     *                        });//(t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))
     *                    })}
     * </pre></blockquote>
     * @param wherePredicate
     * @return
     * @param <T2>
     */
    @Deprecated(message = "使用无参数模式的and or")
    default <T2> SQLKtWherePredicate<T2> withOther(SQLKtWherePredicate<T2> wherePredicate){
        WherePredicate<T2> with = getWherePredicate().withOther(wherePredicate.getWherePredicate());
        return new SQLKtWherePredicateImpl<>(with);
    }

    @Override
    default SQLKtWherePredicate<T1> isBlank(boolean condition, KProperty1<? super T1, String> column) {
        getWherePredicate().isBlank(condition, EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    @Override
    default SQLKtWherePredicate<T1> isNotBlank(boolean condition, KProperty1<? super T1, String> column) {
        getWherePredicate().isNotBlank(condition, EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }
    @Override
    default SQLKtWherePredicate<T1> isEmpty(boolean condition, KProperty1<? super T1, String> column) {
        getWherePredicate().isEmpty(condition, EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    @Override
    default SQLKtWherePredicate<T1> isNotEmpty(boolean condition, KProperty1<? super T1, String> column) {
        getWherePredicate().isNotEmpty(condition, EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }
}
