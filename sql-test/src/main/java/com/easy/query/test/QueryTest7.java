package com.easy.query.test;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * create time 2023/10/19 15:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest7 extends BaseTest {


    @Test
    public void joinTest1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t8(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t9(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
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
                .groupByMerge(o -> o.t().column(Topic::getId))
                .groupByMerge(false, o -> o.t().column(Topic::getId))
                .havingMerge(o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .havingMerge(false, o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` INNER JOIN `t_topic` t6 ON t.`id` = t6.`id` INNER JOIN `t_topic` t7 ON t.`id` = t7.`id` INNER JOIN `t_topic` t8 ON t.`id` = t8.`id` INNER JOIN `t_topic` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest2() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t8(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
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
                .groupByMerge(o -> o.t().column(Topic::getId))
                .groupByMerge(false, o -> o.t().column(Topic::getId))
                .havingMerge(o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .havingMerge(false, o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` INNER JOIN `t_topic` t6 ON t.`id` = t6.`id` INNER JOIN `t_topic` t7 ON t.`id` = t7.`id` INNER JOIN `t_topic` t8 ON t.`id` = t8.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest3() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
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
                .groupByMerge(o -> o.t().column(Topic::getId))
                .groupByMerge(false, o -> o.t().column(Topic::getId))
                .havingMerge(o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .havingMerge(false, o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` INNER JOIN `t_topic` t6 ON t.`id` = t6.`id` INNER JOIN `t_topic` t7 ON t.`id` = t7.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest4() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
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
                .groupByMerge(o -> o.t().column(Topic::getId))
                .groupByMerge(false, o -> o.t().column(Topic::getId))
                .havingMerge(o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .havingMerge(false, o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` INNER JOIN `t_topic` t6 ON t.`id` = t6.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest5() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
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
                .groupByMerge(o -> o.t().column(Topic::getId))
                .groupByMerge(false, o -> o.t().column(Topic::getId))
                .havingMerge(o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .havingMerge(false, o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest6() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
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
                .groupByMerge(o -> o.t().column(Topic::getId))
                .groupByMerge(false, o -> o.t().column(Topic::getId))
                .havingMerge(o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .havingMerge(false, o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest7() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .innerJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .where(o -> o.eq(Topic::getId, 1))
                .where(false, o -> o.eq(Topic::getId, 1))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
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
                .groupByMerge(o -> o.t().column(Topic::getId))
                .groupByMerge(false, o -> o.t().column(Topic::getId))
                .havingMerge(o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .havingMerge(false, o -> o.t().count(Topic::getId, AggregatePredicateCompare.GE, 1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void forEachTest1() {
        {

            Topic topic = easyQuery
                    .queryable(Topic.class)
                    .whereById("1")
//                    .forEach(o -> {
////                        o.setAlias("xxxx");
//                    })
                    .firstOrNull();
            Assert.assertNotNull(topic);
            Assert.assertNull(topic.getAlias());
        }
        {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            Topic topic = easyQuery
                    .queryable(Topic.class)
                    .whereById("1")
                    .forEach(o -> {
                        atomicInteger.getAndIncrement();
                        o.setAlias("xxxx");
                    })
                    .firstOrNull();
            Assert.assertNotNull(topic);
            Assert.assertEquals("xxxx", topic.getAlias());
            Assert.assertEquals(1, atomicInteger.intValue());
        }
        {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            EasyPageResult<Topic> pageResult = easyQuery
                    .queryable(Topic.class)
                    .whereById("1")
                    .forEach(o -> {
                        atomicInteger.getAndIncrement();
                        o.setAlias("xxxx");
                    })
                    .toPageResult(1, 1);
            Assert.assertNotNull(pageResult);
            Assert.assertEquals(1, pageResult.getData().size());
            Assert.assertEquals("xxxx", pageResult.getData().get(0).getAlias());
            Assert.assertEquals(1, atomicInteger.intValue());
        }
    }

    @Test
    public void testCTE1() {
        String sql = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"))
                .asTreeCTE()//"id", "stars"
                .toSQL();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t", sql);
//        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t2.`id` = t3.`stars`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t", sql);
    }
    @Test
    public void testCTE1_1() {
        String sql = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"))
                .asTreeCTECustom("id", "stars")//
                .toSQL();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t", sql);
//        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t2.`id` = t3.`stars`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t", sql);
    }

    @Test
    public void testCTE2() {
        String sql = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"))
                .asTreeCTE()//"id", "stars"
                .leftJoin(BlogEntity.class, (t, t1) -> {
                    t.eq(t1, "id", "id");
                })
                .where(t -> t.like("title", "123"))
                .select(BlogEntity.class, (t, t1) -> {
                    t.column("id");
                    t1.column("url");
                }).toSQL();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) )  SELECT t.`id`,t6.`url` FROM `as_tree_cte` t LEFT JOIN `t_blog` t6 ON t6.`deleted` = ? AND t.`id` = t6.`id` WHERE t.`title` LIKE ?", sql);
    }

    @Test
    public void testCTE3() {
        String sql = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"))
                .asTreeCTE(o -> {//"id", "stars"
                    o.setUp(true);
                })
                .toSQL();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`id` = t2.`stars`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t", sql);
    }

    @Test
    public void testCTE4() {
        String sql = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"))
                .asTreeCTE(o -> {//"id", "stars"
                    o.setLimitDeep(0);
                })
                .toSQL();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
    }

    @Test
    public void testCTE5() {
//        List<Topic> list = easyQuery.queryable(Topic.class)
//                .where(t -> t.eq(Topic::getId, "123"))
//                .select(t -> t.columnAll().columnIgnore(Topic::getTitle))
//                .toList();
//
//        List<BlogEntityVO1> list4 = easyQuery.queryable(Topic.class)
//                .where(t -> t.eq(Topic::getId, "123"))
//                .select(BlogEntityVO1.class)
//                .toList();
//
//        List<Topic> list1 = easyQuery.queryable(Topic.class)
//                .where(t -> t.eq(Topic::getId, "123"))
//                .select(t -> t.columns(Arrays.asList(Topic::getId,Topic::getTitle,Topic::getCreateTime)))
//                .toList();
//
//        List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
//                .where(t -> t.id().eq("123"))
//                .fetchBy(t -> t.FETCHER.id().title().createTime()).toList();
//
//
//
//
//        List<BlogEntityVO1> list3 = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(SysUser.class, (t, s2) -> t.id().eq(s2.id()))
//                .where((t1, s2) -> {
//                    t1.title().like("123");
//                })
//                .select(BlogEntityVO1.class, (t1, s2) -> Select.of(
//                        t1.FETCHER.id().title().createTime(),
//                        s2.FETCHER.phone().username()
//                )).toList();

        String sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotNull(Topic::getId))
                .asTreeCTE( o -> {//Topic::getId, Topic::getStars,
                    o.setLimitDeep(0);
                })
                .toSQL();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
    }
    @Test
    public void testCTE6() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().isNotNull())
                .asTreeCTE( o -> {//o->o.id(), o->o.title(),
                    o.setLimitDeep(0);
                })
                .toSQL();
        System.out.println(sql);
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
    }

    @Test
    public void testBank1() {
        String sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotBank(Topic::getId))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '' AND LTRIM(`id`) <> '')", sql);
    }

    @Test
    public void testBank2() {
        String sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isBank(Topic::getId))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '' OR LTRIM(`id`) = '')", sql);
    }
    @Test
    public void testBank3() {
        String sql = easyProxyQuery
                .queryable(TopicProxy.createTable())
                .where(o -> o.id().isNotBank())
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '' AND LTRIM(`id`) <> '')", sql);
    }

    @Test
    public void testBank4() {
        String sql = easyProxyQuery
                .queryable(TopicProxy.createTable())
                .where(o -> o.id().isBank())
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '' OR LTRIM(`id`) = '')", sql);
    }
    @Test
    public void testEmpty1() {
        String sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotEmpty(Topic::getId))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '')", sql);
    }

    @Test
    public void testEmpty2() {
        String sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isEmpty(Topic::getId))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '')", sql);
    }
    @Test
    public void testEmpty3() {
        String sql = easyProxyQuery.queryable(TopicProxy.createTable())
                .where(o -> o.id().isEmpty())
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '')", sql);
    }
    @Test
    public void testEmpty4() {
        String sql = easyProxyQuery.queryable(TopicProxy.createTable())
                .where(o -> o.id().isNotEmpty())
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '')", sql);
    }

    @Test
    public void testQuery9() {
        List<String> searchValues = Arrays.asList("1", "小明", "小红");
        String sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isBank(Topic::getId))
                .where(o -> {
                    for (String searchValue : searchValues) {
                        o.and(x -> {
                            x.like(Topic::getId, searchValue)
                                    .or().like(Topic::getTitle, searchValue);
                        });
                    }
                })
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '' OR LTRIM(`id`) = '') AND (`id` LIKE ? OR `title` LIKE ?) AND (`id` LIKE ? OR `title` LIKE ?) AND (`id` LIKE ? OR `title` LIKE ?)", sql);
    }

    @Test
    public void testQuery10(){
        String sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .having((t, t1) -> t1.count(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeys(0).columnSum(BlogEntity::getScore))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING COUNT(t1.`id`) >= ?",sql);
    }
    @Test
    public void testQuery11(){
        String sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .having(true,(t, t1) -> t1.count(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                .having(false,(t, t1) -> t1.count(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeys(0).columnSum(BlogEntity::getScore))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING COUNT(t1.`id`) >= ?",sql);
    }
    @Test
    public void testQuery12(){
        String sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .having(true,(t, t1) -> t1.avg(BlogEntity::getScore, AggregatePredicateCompare.GE, 1))
                .having(false,(t, t1) -> t1.avg(BlogEntity::getScore, AggregatePredicateCompare.GE, 1))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeys(0).columnSum(BlogEntity::getScore))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING AVG(t1.`score`) >= ?",sql);
    }
    @Test
    public void testQuery13(){
        String sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .having(true,(t, t1) -> t1.avgDistinct(BlogEntity::getScore, AggregatePredicateCompare.GE, 1))
                .having(false,(t, t1) -> t1.avgDistinct(BlogEntity::getScore, AggregatePredicateCompare.GE, 1))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeys(0).columnSum(BlogEntity::getScore))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING AVG(DISTINCT t1.`score`) >= ?",sql);
    }
    @Test
    public void testQuery14(){
        String sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .having(true,(t, t1) -> t1.sum(BlogEntity::getScore, AggregatePredicateCompare.EQ, 1))
                .having(false,(t, t1) -> t1.sum(BlogEntity::getScore, AggregatePredicateCompare.EQ, 1))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeys(0).columnSum(BlogEntity::getScore))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING SUM(t1.`score`) = ?",sql);
    }
    @Test
    public void testQuery15(){
        String sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .having(true,(t, t1) -> t1.sumDistinct(BlogEntity::getScore, AggregatePredicateCompare.EQ, 1))
                .having(false,(t, t1) -> t1.sumDistinct(BlogEntity::getScore, AggregatePredicateCompare.EQ, 1))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeys(0).columnSum(BlogEntity::getScore))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING SUM(DISTINCT t1.`score`) = ?",sql);
    }
    @Test
    public void testQuery16(){
        String sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .having(true,(t, t1) -> t1.countDistinct(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                .having(false,(t, t1) -> t1.countDistinct(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeys(0).columnSum(BlogEntity::getScore))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING COUNT(DISTINCT t1.`id`) >= ?",sql);
    }
    @Test
    public void testQuery17(){
        String sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .having(true,(t, t1) -> t1.min(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                .having(false,(t, t1) -> t1.min(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeys(0).columnSum(BlogEntity::getScore))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING MIN(t1.`id`) >= ?",sql);
    }
    @Test
    public void testQuery18(){
        String sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .having(true,(t, t1) -> {
                    Assert.assertNotNull(t.getTable());
                    Assert.assertNotNull(t.getRuntimeContext());
                    t1.max(BlogEntity::getId, AggregatePredicateCompare.GE, 1);
                })
                .having(false,(t, t1) -> t1.max(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeys(0).columnSum(BlogEntity::getScore))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING MAX(t1.`id`) >= ?",sql);
    }
}
