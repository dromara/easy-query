package com.easy.query.test;

import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
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
public class QueryTest6 extends BaseTest {


    @Test
    public void joinTest1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t10.id()))
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
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `t_topic` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` IN (?) AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest2() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()))
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
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest3() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
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
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest4() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
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
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest5() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
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
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest6() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
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
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest7() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
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
                .distinct().useInterceptor("A").noInterceptor().useInterceptor("b").noInterceptor("b")
                .disableLogicDelete().enableLogicDelete().asTracking().asNoTracking()
                .select(Topic.class, o -> {
                    return Select.of(
                            o.key1(),
                            o.count(s -> s.t1.id()).as(Topic::getStars)
                    );
                }).toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }
}
