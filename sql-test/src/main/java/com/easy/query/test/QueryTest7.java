package com.easy.query.test;

import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.entity.Topic;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;

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
}
