package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.dto.TopicGroupTestDTO;
import com.easy.query.dto.TopicRequest;
import com.easy.query.entity.BlogEntity;
import com.easy.query.entity.Topic;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @FileName: QueryTest.java
 * @Description: 文件说明
 * @Date: 2023/3/16 17:31
 * @author xuejiaming
 */
public class QueryTest extends BaseTest {
    @Test
    public void query1() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"));
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);
    }

    @Test
    public void query2() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97"));
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("97", blogEntity.getId());
    }

    @Test
    public void query3() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97")).limit(1).select(" 1 ");
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT  1  FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertNull(blogEntity.getId());
    }

    @Test
    public void query4() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97")).limit(1).select("1 as id");
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT 1 as id FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("1", blogEntity.getId());
    }

    @Test
    public void query5() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("97", "98")));
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM t_blog t WHERE t.`deleted` = ? AND t.`id` IN (?,?)", sql);
        List<BlogEntity> blogs = queryable.toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(2, blogs.size());
        Assert.assertEquals(false, blogs.get(0).getIsTop());
        Assert.assertEquals(false, blogs.get(0).getTop());
        Assert.assertEquals(true, blogs.get(1).getIsTop());
        Assert.assertEquals(true, blogs.get(1).getTop());
    }

    @Test
    public void query6() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .whereId("97");
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ?", sql);
        List<BlogEntity> blogs = queryable.toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(1, blogs.size());
        Assert.assertEquals("97", blogs.get(0).getId());
        Assert.assertEquals(false, blogs.get(0).getIsTop());
        Assert.assertEquals(false, blogs.get(0).getTop());
    }

    @Test
    public void query7() {
        List<BlogEntity> blogEntities = easyQuery.queryable(BlogEntity.class).orderByAsc(o -> o.column(BlogEntity::getCreateTime)).toList();
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        int i = 0;
        for (BlogEntity blog : blogEntities) {
            String indexStr = String.valueOf(i);
            Assert.assertEquals(indexStr, blog.getId());
            Assert.assertEquals(indexStr, blog.getCreateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
            Assert.assertEquals(indexStr, blog.getUpdateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getUpdateTime());
            Assert.assertEquals("title" + indexStr, blog.getTitle());
            Assert.assertEquals("content" + indexStr, blog.getContent());
            Assert.assertEquals("http://blog.easy-query.com/" + indexStr, blog.getUrl());
            Assert.assertEquals(i, (int) blog.getStar());
            Assert.assertEquals(0, new BigDecimal("1.2").compareTo(blog.getScore()));
            Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
            Assert.assertEquals(0, new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
            Assert.assertEquals(i % 2 == 0, blog.getIsTop());
            Assert.assertEquals(i % 2 == 0, blog.getTop());
            Assert.assertEquals(false, blog.getDeleted());
            i++;
        }
    }

    @Test
    public void query8() {
        String sql = easyQuery
                .queryable(Topic.class)
                .limit(1).toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM t_topic t LIMIT 1", sql);
        List<Topic> topics = easyQuery
                .queryable(Topic.class)
                .limit(1)
                .toList();
        Assert.assertNotNull(topics);
        Assert.assertEquals(1, topics.size());
        Topic topic = easyQuery
                .queryable(Topic.class)
                .firstOrNull();
        Assert.assertNotNull(topic);
    }

    @Test
    public void query9() {
        Topic topic = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .firstOrNull();
        Assert.assertNotNull(topic);
        List<Topic> topics = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .toList();
        Assert.assertNotNull(topics);
        Assert.assertEquals(1, topics.size());
    }

    @Test
    public void query10() {
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3"))
                .toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM t_topic t LEFT JOIN t_blog t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?",
                toSql);
        Topic topic = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3"))
                .firstOrNull();
        Assert.assertNotNull(topic);
        String toSql1 = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle).then(t).eq(Topic::getId, "3"))
                .select(BlogEntity.class, (t, t1) -> t1.columnAll()).toSql();
        Assert.assertEquals("SELECT t1.`id`,t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM t_topic t INNER JOIN t_blog t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL AND t.`id` = ?",
                toSql1);
        List<BlogEntity> blogEntities = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle).then(t).eq(Topic::getId, "3"))
                .select(BlogEntity.class, (t, t1) -> t1.columnAll())
                .toList();
        Assert.assertNotNull(blogEntities);

        List<BlogEntity> blogEntities1 = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle).then(t).eq(Topic::getId, "3"))
                .select(BlogEntity.class, (t, t1) -> t1.columnAll().columnIgnore(BlogEntity::getId))
                .toList();
    }
    @Test
    public void query11() {
        Queryable<Topic> sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"));
        Assert.assertNotNull(sql);
        List<BlogEntity> topics = easyQuery
                .queryable(BlogEntity.class)
                .leftJoin(sql,(a,b)->a.eq(b,BlogEntity::getId,Topic::getId))
                .where(o -> o.isNotNull(BlogEntity::getId).eq(BlogEntity::getId,"3"))
                .toList();
        Assert.assertNotNull(topics);
        Assert.assertEquals(1, topics.size());
    }
    @Test
    public void query12() {

        EasyPageResult<Topic> topicPageResult = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotNull(Topic::getId))
                .toPageResult(2, 20);
        List<Topic> data = topicPageResult.getData();
        Assert.assertEquals(20,data.size());
    }
    @Test
    public void query13() {

        EasyPageResult<BlogEntity> page = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle).then(t).eq(Topic::getId, "3"))
                .select(BlogEntity.class, (t, t1) -> t1.columnAll().columnIgnore(BlogEntity::getId))
                .toPageResult(1, 20);
        Assert.assertEquals(1,page.getTotal());
        Assert.assertEquals(1,page.getData().size());
    }
    @Test
    public void query14() {

        EasyPageResult<BlogEntity> page = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1)->t1.column(BlogEntity::getId))
                .select(BlogEntity.class, (t, t1) -> t1.column(BlogEntity::getId).columnSum(BlogEntity::getScore))
                .toPageResult(1, 20);
        Assert.assertEquals(100,page.getTotal());
        Assert.assertEquals(20,page.getData().size());
    }
    @Test
    public void query15() {

        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        Topic topic = easyQuery
                .queryable(Topic.class).whereObject(topicRequest).firstOrNull();
        Assert.assertNotNull(topic);
    }
    @Test
    public void query16() {
        Queryable<BlogEntity> sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.column(BlogEntity::getId))
                .select(BlogEntity.class, (t, t1) -> t1.column(BlogEntity::getId).columnSum(BlogEntity::getScore));
        String countSql = sql.cloneQueryable().select(" COUNT(1) ").toSql();
        Assert.assertEquals("SELECT  COUNT(1)  FROM (SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM t_topic t INNER JOIN t_blog t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id`) t2",countSql);
        String limitSql= sql.limit(2, 2).toSql();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM t_topic t INNER JOIN t_blog t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` LIMIT 2 OFFSET 2",limitSql);
    }
    @Test
    public void query17() {
        Queryable<BlogEntity> sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle).then(t).eq(Topic::getId, "3"))
                .select(BlogEntity.class, (t, t1) -> t1.columnAll().columnIgnore(BlogEntity::getId));
        String countSql = sql.cloneQueryable().select(" COUNT(1) ").toSql();
        Assert.assertEquals("SELECT  COUNT(1)  FROM (SELECT t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM t_topic t INNER JOIN t_blog t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL AND t.`id` = ?) t2",countSql);
        String limitSql = sql.limit(2, 2).toSql();
        Assert.assertEquals("SELECT t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM t_topic t INNER JOIN t_blog t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL AND t.`id` = ? LIMIT 2 OFFSET 2",limitSql);
    }
    @Test
    public void query18() {

        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        topicRequest.getOrders().add("id");
        try {
            Topic topic = easyQuery
                    .queryable(Topic.class).whereObject(topicRequest).orderByDynamic(topicRequest).firstOrNull();
        }catch (Exception e){
            Assert.assertTrue(e instanceof EasyQueryOrderByInvalidOperationException);
        }
        topicRequest.getOrders().clear();
        topicRequest.getOrders().add("createTime");
        String orderSql = easyQuery
                .queryable(Topic.class).whereObject(topicRequest).orderByDynamic(topicRequest).limit(1).toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM t_topic t WHERE t.`create_time` > ? ORDER BY t.`create_time` ASC LIMIT 1",orderSql);
        Topic topic = easyQuery
                .queryable(Topic.class).whereObject(topicRequest).orderByDynamic(topicRequest).firstOrNull();
        Assert.assertNotNull(topic);
    }
    @Test
    public void query19() {

        Queryable<TopicGroupTestDTO> sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .groupBy(o->o.column(Topic::getId))
                .select(TopicGroupTestDTO.class, o->o.columnAs(Topic::getId,TopicGroupTestDTO::getId).columnCount(Topic::getId,TopicGroupTestDTO::getIdCount));
        List<BlogEntity> topics = easyQuery
                .queryable(BlogEntity.class)
                .leftJoin(sql,(a,b)->a.eq(b,BlogEntity::getId,TopicGroupTestDTO::getId))
                .where(o -> o.isNotNull(BlogEntity::getId).eq(BlogEntity::getId,"3"))
                .toList();
        Assert.assertNotNull(topics);
        Assert.assertEquals(1, topics.size());


    }
    @Test
    public void query20() {

        Queryable<TopicGroupTestDTO> sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .groupBy(o->o.column(Topic::getId))
                .select(TopicGroupTestDTO.class, o->o.columnAs(Topic::getId,TopicGroupTestDTO::getId).columnCount(Topic::getId,TopicGroupTestDTO::getIdCount));
        String s = sql.toSql();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`id`) AS `idCount` FROM t_topic t WHERE t.`id` = ? GROUP BY t.`id`",s);
        List<TopicGroupTestDTO> topicGroupTestDTOS = sql.toList();
        Assert.assertNotNull(topicGroupTestDTOS);
        Assert.assertEquals(1,topicGroupTestDTOS.size());
        TopicGroupTestDTO topicGroupTestDTO = topicGroupTestDTOS.get(0);
        Assert.assertEquals(1,(int)topicGroupTestDTO.getIdCount());
        Assert.assertEquals("3",topicGroupTestDTO.getId());
    }
    @Test
    public void query21() {

        Queryable<TopicGroupTestDTO> sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .groupBy(o->o.column(Topic::getId))
                .select(TopicGroupTestDTO.class, o->o.column(Topic::getId).columnCount(Topic::getId,TopicGroupTestDTO::getIdCount));
        String s = sql.toSql();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `idCount` FROM t_topic t WHERE t.`id` = ? GROUP BY t.`id`",s);
        List<TopicGroupTestDTO> topicGroupTestDTOS = sql.toList();
        Assert.assertNotNull(topicGroupTestDTOS);
        Assert.assertEquals(1,topicGroupTestDTOS.size());
        TopicGroupTestDTO topicGroupTestDTO = topicGroupTestDTOS.get(0);
        Assert.assertEquals(1,(int)topicGroupTestDTO.getIdCount());
        Assert.assertEquals("3",topicGroupTestDTO.getId());
    }
    @Test
    public void query22() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class);
        List<Map<String,Object>> maps = queryable.toMaps();
        Assert.assertNotNull(maps);
        Assert.assertEquals(100,maps.size());
    }
    @Test
    public void query23() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class);
        Map<String,Object> map = queryable.toMap();
        Assert.assertNotNull(map);
        Assert.assertTrue(map.containsKey("create_Time"));
    }

}
