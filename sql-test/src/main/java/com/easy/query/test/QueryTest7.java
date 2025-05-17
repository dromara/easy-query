package com.easy.query.test;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
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
        String sql = easyEntityQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t10.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereByIds(Collections.singletonList("1"))
                .whereByIds(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L))
                .useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        System.out.println(sql);
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` INNER JOIN `t_topic` t6 ON t.`id` = t6.`id` INNER JOIN `t_topic` t7 ON t.`id` = t7.`id` INNER JOIN `t_topic` t8 ON t.`id` = t8.`id` INNER JOIN `t_topic` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` IN (?) AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest2() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L))
                .useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        System.out.println(sql);
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` INNER JOIN `t_topic` t6 ON t.`id` = t6.`id` INNER JOIN `t_topic` t7 ON t.`id` = t7.`id` INNER JOIN `t_topic` t8 ON t.`id` = t8.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest3() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L))
                .useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        System.out.println(sql);
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` INNER JOIN `t_topic` t6 ON t.`id` = t6.`id` INNER JOIN `t_topic` t7 ON t.`id` = t7.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest4() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L))
                .useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` INNER JOIN `t_topic` t6 ON t.`id` = t6.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest5() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L))
                .useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` INNER JOIN `t_topic` t5 ON t.`id` = t5.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest6() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L))
                .useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` INNER JOIN `t_topic` t4 ON t.`id` = t4.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest7() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .innerJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L))
                .useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` INNER JOIN `t_topic` t2 ON t.`id` = t2.`id` INNER JOIN `t_topic` t3 ON t.`id` = t3.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void forEachTest1() {
        {

            Topic topic = easyEntityQuery
                    .queryable(Topic.class)
                    .whereById("1")
//                    .forEach(o -> {
////                        o.setAlias("xxxx");
//                    })
                    .firstOrNull();
            Assert.assertNotNull(topic);
            Assert.assertNull(topic.getAlias());
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
//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .asTreeCTE()
//                .toList();


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
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().isNotNull())
                .asTreeCTE(o -> {//Topic::getId, Topic::getStars,
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
                .asTreeCTE(o -> {//o->o.id(), o->o.title(),
                    o.setLimitDeep(0);
                })
                .toSQL();
        System.out.println(sql);
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
    }

    @Test
    public void testCTE7() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().isNotNull())
                .asTreeCTE(o -> {//Topic::getId, Topic::getStars,
                    o.setLimitDeep(0);
                    o.setUp(true);
                    o.setUnionAll(false);
                    o.setCTETableName("abc");
                    o.setDeepColumnName("xyz");
                })
                .toSQL();
        System.out.println(sql);
        Assert.assertEquals("WITH RECURSIVE `abc` AS ( (SELECT 0 AS `xyz`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION  (SELECT t2.`xyz` + 1 AS `xyz`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `abc` t2 INNER JOIN `t_topic` t3 ON t3.`id` = t2.`stars`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `abc` t WHERE t.`xyz` <= ?", sql);
    }

    @Test
    public void testBank1() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().isNotBlank())
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '' AND LTRIM(`id`) <> '')", sql);
    }

    @Test
    public void testBank2() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().isBlank())
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '' OR LTRIM(`id`) = '')", sql);
    }

    @Test
    public void testEmpty1() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().isNotEmpty())
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '')", sql);
    }

    @Test
    public void testEmpty2() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().isEmpty())
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '')", sql);
    }

    @Test
    public void testQuery9() {
        List<String> searchValues = Arrays.asList("1", "小明", "小红");
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().isBlank())
                .where(o -> {
                    for (String searchValue : searchValues) {
                        o.or(() -> {
                            o.id().like(searchValue);
                            o.title().like(searchValue);
                        });
                    }
                })
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '' OR LTRIM(`id`) = '') AND (`id` LIKE ? OR `title` LIKE ?) AND (`id` LIKE ? OR `title` LIKE ?) AND (`id` LIKE ? OR `title` LIKE ?)", sql);
    }

    @Test
    public void testQuery10() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .having(group -> group.count(s -> s.t2.id()).ge(1L))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1(),
                        group.sum(s -> s.t2.score()).as(BlogEntity::getScore)
                ))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING COUNT(t1.`id`) >= ?", sql);
    }

    @Test
    public void testQuery11() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .having(true, group -> group.count(s -> s.t2.id()).ge(1L))
                .having(false, group -> group.count(s -> s.t2.id()).ge(1L))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1(),
                        group.sum(s -> s.t2.score()).as(BlogEntity::getScore)
                ))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING COUNT(t1.`id`) >= ?", sql);
    }

    @Test
    public void testQuery12() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .having(true, group -> group.avg(s -> s.t2.score()).ge(BigDecimal.valueOf(1L)))
                .having(false, group -> group.avg(s -> s.t2.score()).ge(BigDecimal.valueOf(1L)))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1(),
                        group.sum(s -> s.t2.score()).as(BlogEntity::getScore)
                ))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING AVG(t1.`score`) >= ?", sql);
    }

    @Test
    public void testQuery13() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .having(true, group -> group.distinct().avg(s -> s.t2.score()).ge(BigDecimal.valueOf(1L)))
                .having(false, group -> group.distinct().avg(s -> s.t2.score()).ge(BigDecimal.valueOf(1L)))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1(),
                        group.sum(s -> s.t2.score()).as(BlogEntity::getScore)
                ))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING AVG(DISTINCT t1.`score`) >= ?", sql);
    }

    @Test
    public void testQuery14() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .having(true, group -> group.sumBigDecimal(s -> s.t2.score()).eq(BigDecimal.valueOf(1L)))
                .having(false, group -> group.sumBigDecimal(s -> s.t2.score()).ge(BigDecimal.valueOf(1L)))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1(),
                        group.sum(s -> s.t2.score()).as(BlogEntity::getScore)
                ))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING SUM(t1.`score`) = ?", sql);
    }

    @Test
    public void testQuery15() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .having(true, group -> group.distinct().sumBigDecimal(s -> s.t2.score()).eq(BigDecimal.valueOf(1L)))
                .having(false, group -> group.distinct().sumBigDecimal(s -> s.t2.score()).eq(BigDecimal.valueOf(1L)))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1(),
                        group.sum(s -> s.t2.score()).as(BlogEntity::getScore)
                ))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING SUM(DISTINCT t1.`score`) = ?", sql);
    }

    @Test
    public void testQuery16() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .having(true, group -> group.distinct().count(s -> s.t2.id()).ge(1L))
                .having(false, group -> group.distinct().count(s -> s.t2.id()).ge(1L))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1(),
                        group.sum(s -> s.t2.score()).as(BlogEntity::getScore)
                ))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING COUNT(DISTINCT t1.`id`) >= ?", sql);
    }

    @Test
    public void testQuery17() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .having(true, group -> group.min(s -> s.t2.id()).ge("1"))
                .having(false, group -> group.min(s -> s.t2.id()).ge("1"))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1(),
                        group.sum(s -> s.t2.score()).as(BlogEntity::getScore)
                ))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING MIN(t1.`id`) >= ?", sql);
    }

    @Test
    public void testQuery18() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .having(true, group -> {
                    Assert.assertNotNull(group.getTable());
                    Assert.assertNotNull(group.getEntitySQLContext().getRuntimeContext());
                    group.max(s->s.t2.id()).ge("1");
                })
                .having(false, group-> group.max(s->s.t2.id()).ge("1"))
                .select(BlogEntity.class, group -> Select.of(
                group.key1(),
                group.sum(s -> s.t2.score()).as(BlogEntity::getScore)
        ))
                .toSQL();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` HAVING MAX(t1.`id`) >= ?", sql);
    }
}
