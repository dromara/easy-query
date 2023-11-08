package com.easy.query.test;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable2;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.test.dto.BlogEntityTest;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.dto.proxy.BlogEntityTestProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.company.ValueCompanyDTO;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/10/31 07:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest8 extends BaseTest{

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
    public void test1(){
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
    public void test2(){
        ProxyQueryable<TopicProxy, Topic> query = easyProxyQuery.queryable(TopicProxy.createTable())
                .limit(100);
        ProxyQueryable2<TopicProxy, Topic, BlogEntityProxy, BlogEntity> join = query
                .select(TopicProxy.createTable(), o -> o.columns(o.t().id(), o.t().stars()))
                .innerJoin(BlogEntityProxy.createTable(), o -> o.eq(o.t().id(), o.t1().id()));
        BlogEntityTestProxy blogTestVO = BlogEntityTestProxy.createTable();
        ProxyQueryable2<BlogEntityTestProxy, BlogEntityTest, BlogEntityProxy, BlogEntity> join2 = join.select(blogTestVO, o -> o.columnAs(o.t().id(), o.tr().url())
                        .column(o.t1().title()))
                .innerJoin(BlogEntityProxy.createTable(), o -> o.eq(o.t().url(), o.t1().id()));
        List<BlogEntity> list = join2.select(BlogEntityProxy.createTable(), o -> {
            o.columnAs(o.t().url(), o.tr().url()).columnAs(o.t1().title(),o.tr().content());
        }).toList();
    }

    @Test
     public void test3(){
        ValueCompanyDTO companyDTO = easyQuery.queryable(ValueCompany.class)
                 .select(ValueCompanyDTO.class,o->o.columnAll())
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
        Assert.assertEquals(companyDTO.getAddress().getProvince(),companyDTO1.getLicense().getExtra().getLicenseContent());
    }
    @Test
     public void test4(){

        Supplier<Exception> f = () -> {
            try {
                ValueCompanyDTO companyDTO = easyQuery.queryable(ValueCompany.class)
                        .asTable("COMPANY_A")
                        .select(ValueCompanyDTO.class,o->o.columnAll())
//                 .select(BlogEntity.class, o -> o.column(BlogEntity::getId).columnCountAs(BlogEntity::getId, BlogEntity::getStar))
                        .firstOrNull();
            }catch (Exception ex){
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
        Assert.assertEquals("SELECT t.`name`,t.`province`,t.`area`,t.`license_no`,t.`license_deadline`,t.`license_image`,t.`license_content` FROM `COMPANY_A` t LIMIT 1",easyQuerySQLStatementException.getSQL());

     }
}
