package com.easy.query.api4j.sql;

import com.easy.query.api4j.sql.core.SQLLambdaNative;
import com.easy.query.api4j.sql.core.available.LambdaSQLFuncAvailable;
import com.easy.query.api4j.sql.core.filter.SQLAssertPredicate;
import com.easy.query.api4j.sql.core.filter.SQLFuncColumnPredicate;
import com.easy.query.api4j.sql.core.filter.SQLFuncValuePredicate;
import com.easy.query.api4j.sql.core.filter.SQLLikePredicate;
import com.easy.query.api4j.sql.core.filter.SQLRangePredicate;
import com.easy.query.api4j.sql.core.filter.SQLSelfPredicate;
import com.easy.query.api4j.sql.core.filter.SQLSubQueryPredicate;
import com.easy.query.api4j.sql.core.filter.SQLValuePredicate;
import com.easy.query.api4j.sql.core.filter.SQLValuesPredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * @author xuejiaming
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 */
public interface SQLWherePredicate<T1> extends EntitySQLTableOwner<T1>, LambdaSQLFuncAvailable<T1>, SQLLambdaNative<T1, SQLWherePredicate<T1>>
        , SQLLikePredicate<T1, SQLWherePredicate<T1>>
        , SQLRangePredicate<T1, SQLWherePredicate<T1>>
        , SQLSelfPredicate<T1, SQLWherePredicate<T1>>
        , SQLFuncColumnPredicate<T1, SQLWherePredicate<T1>>
        , SQLFuncValuePredicate<T1, SQLWherePredicate<T1>>
        , SQLValuePredicate<T1, SQLWherePredicate<T1>>
        , SQLValuesPredicate<T1, SQLWherePredicate<T1>>
        , SQLSubQueryPredicate<T1, SQLWherePredicate<T1>>
        , SQLAssertPredicate<T1, SQLWherePredicate<T1>> {
    WherePredicate<T1> getWherePredicate();

    default TableAvailable getTable() {
        return getWherePredicate().getTable();
    }

    default QueryRuntimeContext getRuntimeContext() {
        return getWherePredicate().getRuntimeContext();
    }


    default <TProperty> SQLWherePredicate<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    default <TProperty> SQLWherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        getWherePredicate().columnFunc(condition, columnPropertyFunction, sqlPredicateCompare, val);
        return this;
    }


    default <T2> SQLWherePredicate<T2> then(SQLWherePredicate<T2> sub) {
        getWherePredicate().then(sub.getWherePredicate());
        return sub;
    }

    default SQLWherePredicate<T1> and() {
        return and(true);
    }

    default SQLWherePredicate<T1> and(boolean condition) {
        getWherePredicate().and(condition);
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
    @Deprecated
    default SQLWherePredicate<T1> and(SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
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
    @Deprecated
    SQLWherePredicate<T1> and(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression);

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
    default SQLWherePredicate<T1> and(SQLActionExpression sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
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
   default SQLWherePredicate<T1> and(boolean condition, SQLActionExpression sqlWherePredicateSQLExpression){
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
     * @param t2SQLWherePredicate
     * @param sqlWherePredicateSQLExpression
     * @return
     * @param <T2>
     */
    @Deprecated
    default <T2> SQLWherePredicate<T1> and(SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return and(true, t2SQLWherePredicate, sqlWherePredicateSQLExpression);
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
     * @param t2SQLWherePredicate
     * @param sqlWherePredicateSQLExpression
     * @return
     * @param <T2>
     */
    @Deprecated
    <T2> SQLWherePredicate<T1> and(boolean condition, SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression);

    default SQLWherePredicate<T1> or() {
        return or(true);
    }

    default SQLWherePredicate<T1> or(boolean condition) {
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
    @Deprecated
    default SQLWherePredicate<T1> or(SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
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
    @Deprecated
    SQLWherePredicate<T1> or(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression);

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
    default SQLWherePredicate<T1> or(SQLActionExpression sqlWherePredicateSQLExpression) {
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
    default SQLWherePredicate<T1> or(boolean condition, SQLActionExpression sqlWherePredicateSQLExpression){
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
     * @param t2SQLWherePredicate
     * @param sqlWherePredicateSQLExpression
     * @return
     * @param <T2>
     */
    @Deprecated
    default <T2> SQLWherePredicate<T1> or(SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return or(true, t2SQLWherePredicate, sqlWherePredicateSQLExpression);
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
     * @param t2SQLWherePredicate
     * @param sqlWherePredicateSQLExpression
     * @return
     * @param <T2>
     */
    @Deprecated
    <T2> SQLWherePredicate<T1> or(boolean condition, SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression);

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
    @Deprecated
    default <T2> SQLWherePredicate<T2> withOther(SQLWherePredicate<T2> wherePredicate){
        WherePredicate<T2> with = getWherePredicate().withOther(wherePredicate.getWherePredicate());
        return new SQLWherePredicateImpl<>(with);
    }

    @Override
    default SQLWherePredicate<T1> isBlank(boolean condition, Property<T1, String> column) {
        getWherePredicate().isBlank(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    @Override
    default SQLWherePredicate<T1> isNotBlank(boolean condition, Property<T1, String> column) {
        getWherePredicate().isNotBlank(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }
    @Override
    default SQLWherePredicate<T1> isEmpty(boolean condition, Property<T1, String> column) {
        getWherePredicate().isEmpty(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    @Override
    default SQLWherePredicate<T1> isNotEmpty(boolean condition, Property<T1, String> column) {
        getWherePredicate().isNotEmpty(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }

}
