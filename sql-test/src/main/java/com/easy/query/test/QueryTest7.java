package com.easy.query.test;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * create time 2023/10/19 15:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest7 extends BaseTest{


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
    public void forEachTest1(){
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
            Assert.assertEquals("xxxx",topic.getAlias());
            Assert.assertEquals(1,atomicInteger.intValue());
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
            Assert.assertEquals(1,pageResult.getData().size());
            Assert.assertEquals("xxxx",pageResult.getData().get(0).getAlias());
            Assert.assertEquals(1,atomicInteger.intValue());
        }
    }

    @Test
    public void testCTE1(){
        String sql = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"))
                .asTreeCTE("id", "stars")
                .toSQL();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL UNION ALL SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t2.`stars` = t3.`id`)  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t",sql);
    }
    @Test
    public void testCTE2(){
        String sql = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"))
                .asTreeCTE("id", "stars")
                .leftJoin(BlogEntity.class, (t, t1) -> {
                    t.eq(t1, "id", "id");
                })
                .where(t -> t.like("title", "123"))
                .select(BlogEntity.class, (t, t1) -> {
                    t.column("id");
                    t1.column("url");
                }).toSQL();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL UNION ALL SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t2.`stars` = t3.`id`)  SELECT t.`id`,t6.`url` FROM `as_tree_cte` t LEFT JOIN `t_blog` t6 ON t6.`deleted` = ? AND t.`id` = t6.`id` WHERE t.`title` LIKE ?",sql);
    }
}
