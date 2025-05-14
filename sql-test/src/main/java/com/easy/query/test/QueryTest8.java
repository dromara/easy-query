package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.BlogEntityTest;
import com.easy.query.test.dto.TopicMisDTO;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.dto.proxy.TopicMisDTOProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.company.ValueCompanyDTO;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * create time 2023/10/31 07:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest8 extends BaseTest {

    @Test
    public void joinTest1_1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t8(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t9(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereByIds(Collections.singletonList("1"))
                .whereByIds(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .where(o -> {
                    o.eq(Topic::getId, "1");
                    o.eq(false, Topic::getId, "1");
                    o.ne(Topic::getId, "1");
                    o.ne(false, Topic::getId, "1");
                    o.ge(Topic::getId, "1");
                    o.ge(false, Topic::getId, "1");
                    o.gt(Topic::getId, "1");
                    o.gt(false, Topic::getId, "1");
                    o.le(Topic::getId, "1");
                    o.le(false, Topic::getId, "1");
                    o.lt(Topic::getId, "1");
                    o.lt(false, Topic::getId, "1");
                    o.like(Topic::getId, "1");
                    o.like(false, Topic::getId, "1");
                    o.notLike(Topic::getId, "1");
                    o.notLike(false, Topic::getId, "1");
                    o.likeMatchLeft(Topic::getId, "1");
                    o.likeMatchLeft(false, Topic::getId, "1");
                    o.likeMatchRight(Topic::getId, "1");
                    o.likeMatchRight(false, Topic::getId, "1");
                    o.notLikeMatchLeft(Topic::getId, "1");
                    o.notLikeMatchLeft(false, Topic::getId, "1");
                    o.notLikeMatchRight(Topic::getId, "1");
                    o.notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o -> o.column(Topic::getCreateTime))
                .orderByDesc(o -> o.column(Topic::getCreateTime))
                .orderByAsc(false, o -> o.column(Topic::getCreateTime))
                .orderByDesc(false, o -> o.column(Topic::getCreateTime))
                .orderByAscMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false, o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false, o -> o.t().column(Topic::getCreateTime))
                .groupBy(o -> o.column(Topic::getId))
                .groupBy(false, o -> o.column(Topic::getId))
                .having(o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .having(false, o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `t_topic` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` IN (?) AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest2_1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t8(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereByIds(Collections.singletonList("1"))
                .whereByIds(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o -> o.column(Topic::getCreateTime))
                .orderByDesc(o -> o.column(Topic::getCreateTime))
                .orderByAsc(false, o -> o.column(Topic::getCreateTime))
                .orderByDesc(false, o -> o.column(Topic::getCreateTime))
                .orderByAscMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false, o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false, o -> o.t().column(Topic::getCreateTime))
                .groupBy(o -> o.column(Topic::getId))
                .groupBy(false, o -> o.column(Topic::getId))
                .having(o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .having(false, o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` IN (?) AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest3_1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereByIds(Collections.singletonList("1"))
                .whereByIds(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o -> o.column(Topic::getCreateTime))
                .orderByDesc(o -> o.column(Topic::getCreateTime))
                .orderByAsc(false, o -> o.column(Topic::getCreateTime))
                .orderByDesc(false, o -> o.column(Topic::getCreateTime))
                .orderByAscMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false, o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false, o -> o.t().column(Topic::getCreateTime))
                .groupBy(o -> o.column(Topic::getId))
                .groupBy(false, o -> o.column(Topic::getId))
                .having(o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .having(false, o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` IN (?) AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest4_1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereByIds(Collections.singletonList("1"))
                .whereByIds(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o -> o.column(Topic::getCreateTime))
                .orderByDesc(o -> o.column(Topic::getCreateTime))
                .orderByAsc(false, o -> o.column(Topic::getCreateTime))
                .orderByDesc(false, o -> o.column(Topic::getCreateTime))
                .orderByAscMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false, o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false, o -> o.t().column(Topic::getCreateTime))
                .groupBy(o -> o.column(Topic::getId))
                .groupBy(false, o -> o.column(Topic::getId))
                .having(o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .having(false, o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` IN (?) AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest5_1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereByIds(Collections.singletonList("1"))
                .whereByIds(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o -> o.column(Topic::getCreateTime))
                .orderByDesc(o -> o.column(Topic::getCreateTime))
                .orderByAsc(false, o -> o.column(Topic::getCreateTime))
                .orderByDesc(false, o -> o.column(Topic::getCreateTime))
                .orderByAscMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false, o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false, o -> o.t().column(Topic::getCreateTime))
                .groupBy(o -> o.column(Topic::getId))
                .groupBy(false, o -> o.column(Topic::getId))
                .having(o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .having(false, o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` IN (?) AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest6_1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereByIds(Collections.singletonList("1"))
                .whereByIds(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o -> o.column(Topic::getCreateTime))
                .orderByDesc(o -> o.column(Topic::getCreateTime))
                .orderByAsc(false, o -> o.column(Topic::getCreateTime))
                .orderByDesc(false, o -> o.column(Topic::getCreateTime))
                .orderByAscMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false, o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false, o -> o.t().column(Topic::getCreateTime))
                .groupBy(o -> o.column(Topic::getId))
                .groupBy(false, o -> o.column(Topic::getId))
                .having(o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .having(false, o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` IN (?) AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest7_1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereByIds(Collections.singletonList("1"))
                .whereByIds(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o -> o.column(Topic::getCreateTime))
                .orderByDesc(o -> o.column(Topic::getCreateTime))
                .orderByAsc(false, o -> o.column(Topic::getCreateTime))
                .orderByDesc(false, o -> o.column(Topic::getCreateTime))
                .orderByAscMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o -> o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false, o -> o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false, o -> o.t().column(Topic::getCreateTime))
                .groupBy(o -> o.column(Topic::getId))
                .groupBy(false, o -> o.column(Topic::getId))
                .having(o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .having(false, o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` IN (?) AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void test1() {
        Queryable<Topic> query = easyQuery.queryable(Topic.class)
                .limit(100);
        Queryable2<Topic, BlogEntity> join = query.select(Topic.class, o -> o.column(Topic::getId).column(Topic::getStars))
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId));
        Queryable2<BlogEntityTest, BlogEntity> join2 = join.select(BlogEntityTest.class, (t, t1) -> {
                    t.columnAs(Topic::getId, BlogEntityTest::getUrl);
                    t1.column(BlogEntity::getTitle);
                })
                .innerJoin(BlogEntity.class, (t, t1) -> {
                    t.eq(t1, BlogEntityTest::getUrl, BlogEntity::getId);
                });
        List<BlogEntity> list = join2.select(BlogEntity.class, (t, t1) -> {
            t.columnAs(BlogEntityTest::getUrl, BlogEntity::getUrl);
            t1.columnAs(BlogEntity::getTitle, BlogEntity::getContent);
        }).toList();
    }

    @Test
    public void test3() {
        ValueCompanyDTO companyDTO = easyQuery.queryable(ValueCompany.class)
                .select(ValueCompanyDTO.class, o -> o.columnAll())
                .firstOrNull();
        Assert.assertNotNull(companyDTO.getName());
        Assert.assertNotNull(companyDTO.getLicense());
        Assert.assertNotNull(companyDTO.getLicense().getLicenseNo());
        Assert.assertNotNull(companyDTO.getLicense().getExtra());
        Assert.assertNotNull(companyDTO.getLicense().getLicenseDeadline());
        Assert.assertNotNull(companyDTO.getLicense().getExtra().getLicenseContent());
        Assert.assertNotNull(companyDTO.getLicense().getExtra().getLicenseImage());
        Assert.assertNotNull(companyDTO.getAddress());
        Assert.assertNotNull(companyDTO.getAddress().getProvince());
        Assert.assertNotNull(companyDTO.getAddress().getArea());

        ValueCompanyDTO companyDTO1 = easyQuery.queryable(ValueCompany.class)
                .select(ValueCompanyDTO.class, o -> o.columnAs(x -> x.getAddress().getProvince(), x -> x.getLicense().getExtra().getLicenseContent()))
                .firstOrNull();
        Assert.assertNotNull(companyDTO1);
        Assert.assertEquals(companyDTO.getAddress().getProvince(), companyDTO1.getLicense().getExtra().getLicenseContent());
    }

    @Test
    public void test4() {

        Supplier<Exception> f = () -> {
            try {
                ValueCompanyDTO companyDTO = easyQuery.queryable(ValueCompany.class)
                        .asTable("COMPANY_A")
                        .select(ValueCompanyDTO.class, o -> o.columnAll())
//                 .select(BlogEntity.class, o -> o.column(BlogEntity::getId).columnCountAs(BlogEntity::getId, BlogEntity::getStar))
                        .firstOrNull();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT t.`name`,t.`province`,t.`area`,t.`license_no`,t.`license_deadline`,t.`license_image`,t.`license_content` FROM `COMPANY_A` t LIMIT 1", easyQuerySQLStatementException.getSQL());

    }



    @Test
    public void test4x() {

        List<Map<String, Object>> maps = easyQuery.sqlQueryMap("SHOW TABLES");
        System.out.println(maps);
        List<String> tables = maps.stream().flatMap(o -> o.values().stream()).map(o -> o.toString()).collect(Collectors.toList());

        System.out.println(tables);
    }


    @Test
     public void testJoin5(){
         String sql = easyQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .where((t1, t2, t3, t4, t5) -> {
                     t1.eq(Topic::getStars, 1);
                     t5.eq(BlogEntity::getOrder, "1");
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t4 ON t4.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t4.`order` = ?",sql);
     }
    @Test
     public void testJoin6(){
         String sql = easyQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .where((t1, t2, t3, t4, t5,t6) -> {
                     t1.eq(Topic::getStars, 1);
                     t6.eq(BlogEntity::getOrder, "1");
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t5 ON t5.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t5.`order` = ?",sql);
     }
    @Test
     public void testJoin7(){
         String sql = easyQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6,t7) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .where((t1, t2, t3, t4, t5,t6,t7) -> {
                     t1.eq(Topic::getStars, 1);
                     t7.eq(BlogEntity::getOrder, "1");
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t6 ON t6.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t6.`order` = ?",sql);
     }
    @Test
     public void testJoin8(){
         String sql = easyQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6,t7,t8) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .where((t1, t2, t3, t4, t5,t6,t7,t8) -> {
                     t1.eq(Topic::getStars, 1);
                     t8.eq(BlogEntity::getOrder, "1");
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t7 ON t7.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t7.`order` = ?",sql);
     }
    @Test
     public void testJoin9(){
         String sql = easyQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7,t8) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6,t7,t8,t9) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .where((t1, t2, t3, t4, t5,t6,t7,t8,t9) -> {
                     t1.eq(Topic::getStars, 1);
                     t9.eq(BlogEntity::getOrder, "1");
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t8 ON t8.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t8.`order` = ?",sql);
     }
    @Test
     public void testJoin10(){
         String sql = easyQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7,t8) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7,t8,t9) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6,t7,t8,t9,t10) -> t1.eq(t2, Topic::getId, Topic::getId))
                 .where((t1, t2, t3, t4, t5,t6,t7,t8,t9,t10) -> {
                     t1.eq(Topic::getStars, 1);
                     t10.eq(BlogEntity::getOrder, "1");
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t9 ON t9.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t9.`order` = ?",sql);
     }
    @Test
     public void testNest1(){
         String sql = easyQuery.queryable(Topic.class)
                 .leftJoin(BlogEntity.class, (t1, t2) -> t1.eq(t2, Topic::getId, BlogEntity::getId))
                 .where((t1, t2) -> {
                     t1.and(x->{
                         SQLWherePredicate<BlogEntity> y = x.withOther(t2);
                         x.eq(Topic::getStars, 1);
                         y.eq(BlogEntity::getOrder, "1");
                     });
                 }).select(x->x.column(Topic::getId)).toSQL();
         Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE (t.`stars` = ? AND t1.`order` = ?)",sql);
     }
    @Test
     public void testNest2(){
         String sql = easyQuery.queryable(Topic.class)
                 .leftJoin(BlogEntity.class, (t1, t2) -> t1.eq(t2, Topic::getId, BlogEntity::getId))
                 .where((t1, t2) -> {
                     t1.and(()->{
                         t1.eq(Topic::getStars, 1).or();
                         t2.eq(BlogEntity::getOrder, "1");
                     });
                 }).select(x->x.column(Topic::getId)).toSQL();
         Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE (t.`stars` = ? OR t1.`order` = ?)",sql);
     }
    @Test
     public void testNest3(){
         String sql = easyQuery.queryable(Topic.class)
                 .leftJoin(BlogEntity.class, (t1, t2) -> t1.eq(t2, Topic::getId, BlogEntity::getId))
                 .where((t1, t2) -> {
                     t1.and(()->{
                         t1.eq(Topic::getStars, 1).or();
                         t2.eq(BlogEntity::getOrder, "1");
                     });
                     t2.eq(BlogEntity::getId,1);
                     t2.and(()->{
                         t1.eq(Topic::getStars, 1).or(()->{
                             t1.eq(Topic::getCreateTime,LocalDateTime.now())
                                     .or();
                             t2.like(BlogEntity::getContent,"111");
                         });
                     });
                 }).select(x->x.column(Topic::getId)).toSQL();
         Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE (t.`stars` = ? OR t1.`order` = ?) AND t1.`id` = ? AND (t.`stars` = ? OR (t.`create_time` = ? OR t1.`content` LIKE ?))",sql);
     }
    @Test
     public void testNativeSql(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.sqlNativeSegment("date_format({0},''%y%m%d'') <= date_format({1},''%y%m%d'')", c -> {
                        c
                                //.keepStyle()
                                .expression(BlogEntity::getPublishTime).value("2023-01-01 00:00:00");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND date_format(`publish_time`,'%y%m%d') <= date_format(?,'%y%m%d')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2023-01-01 00:00:00(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

}
