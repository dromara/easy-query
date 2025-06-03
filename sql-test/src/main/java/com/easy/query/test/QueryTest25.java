package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.cache.core.EasyCacheManager;
import com.easy.query.cache.core.base.CacheMethodEnum;
import com.easy.query.cache.core.base.DefaultClearParameter;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.proxy.core.tuple.Tuple1;
import com.easy.query.core.proxy.core.tuple.Tuple2;
import com.easy.query.core.proxy.core.tuple.Tuple3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyTypeUtil;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.test.cache.CacheItem;
import com.easy.query.test.cache.DefaultEasyRedisManagerMultiLevel;
import com.easy.query.test.cache.JsonUtil;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.proxy.TopicTypeVOProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.listener.ListenerContext;
import com.github.benmanes.caffeine.cache.Cache;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2025/5/23 10:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest25 extends BaseTest {

    @Test
    public void testOrderCaseWhen() {

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_topic.id().eq(t_blog.id()))
                .leftJoin(BlogEntity.class, (t_topic, t_blog, t_blog1) -> t_topic.id().eq(t_blog.id()))
                .orderBy((t_topic, t_blog, t_blog1) -> {
                    t_blog.expression().caseWhen(() -> {
                                t_blog.title().like("123");
                            }).then(1)
                            .caseWhen(() -> {
                                t_blog.content().like("456");
                            }).then(2)
                            .caseWhen(() -> {
                                t_blog.title().like("4561");
                            }).then(3)
                            .caseWhen(() -> {
                                t_blog.title().like("4561");
                            }).then(4)
                            .elseEnd(5).asc();
                }).toList();
    }

    @Test
    public void test11() {
        List<TopicTypeVO> list = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.title().contains("123");
                }).select(t_topic -> new TopicTypeVOProxy() {{
                    id().set(t_topic.id());
                    stars().set(t_topic.stars());
                }}).toList();
    }

    @Test
    public void test12() {
        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.title().contains("123");
                }).toList();

        EntityMetadataManager entityMetadataManager = easyEntityQuery.getRuntimeContext().getEntityMetadataManager();
        List<EntityMetadata> entityMetadataList = entityMetadataManager.getEntityMetadataList("t_topic");
        Assert.assertNotNull(entityMetadataList);
        for (EntityMetadata entityMetadata : entityMetadataList) {
            System.out.println(entityMetadata.getEntityClass());
        }
        boolean anyMatch = entityMetadataList.stream().anyMatch(s -> Objects.equals(s.getEntityClass(), Topic.class));
        Assert.assertTrue(anyMatch);

    }


    @Test
    public void tupleTest1() {

        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Class<Tuple1<String>> typeClass = EasyTypeUtil.cast(Tuple1.class);
            List<Tuple1<String>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> GroupKeys.of(t.id()))
                    .select(t -> Select.TUPLE.of(t.key1()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1` FROM `t_topic` t GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
    }

    @Test
    public void tupleTest2() {

        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Class<Tuple1<String>> typeClass = EasyTypeUtil.cast(Tuple1.class);
            List<Tuple1<String>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> GroupKeys.of(t.id(), t.title()))
                    .select(t -> Select.TUPLE.of(t.key1()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1` FROM `t_topic` t GROUP BY t.`id`,t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
    }

    @Test
    public void tupleTest3() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Class<Tuple1<String>> typeClass = EasyTypeUtil.cast(Tuple1.class);
            List<Tuple1<String>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> GroupKeys.of(t.id()))
                    .select(t -> Select.TUPLE.of(t.key1()))
                    .where(o -> o.value1().eq("123"))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t1.`value1` AS `value1` FROM (SELECT t.`id` AS `value1` FROM `t_topic` t GROUP BY t.`id`) t1 WHERE t1.`value1` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
    }

    @Test
    public void tupleTest4() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Tuple2<String, Long>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> GroupKeys.of(t.id()))
                    .select(t -> Select.TUPLE.of(
                            t.key1(),
                            t.count()
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,COUNT(*) AS `value2` FROM `t_topic` t GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
    }

    @Test
    public void tupleTest5() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Tuple3<String, Long, Integer>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> GroupKeys.of(t.id()))
                    .select(g -> Select.TUPLE.of(g.key1(), g.count(), g.sum(s -> s.stars())))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,COUNT(*) AS `value2`,SUM(t.`stars`) AS `value3` FROM `t_topic` t GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            Integer value3 = list.get(0).getValue3();
        }

    }

    @Test
    public void tupleTest6() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Tuple2<String, String>> list = easyEntityQuery
                    .queryable(ValueCompany.class)
                    .select(t -> Select.TUPLE.of(t.id(), t.address().province()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`province` AS `value2` FROM `my_company` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }

    }

    @Test
    public void tupleTest7() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Tuple2<String, LocalDateTime>> list = easyEntityQuery
                    .queryable(BlogEntity.class)
                    .select(t -> Select.TUPLE.of(t.id(), t.createTime()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`create_time` AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            LocalDateTime value2 = list.get(0).getValue2();
            System.out.println(value2);
        }

    }

    @Test
    public void tupleTest8() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Tuple3<String, LocalDateTime, String>> list = easyEntityQuery
                    .queryable(BlogEntity.class)
                    .select(t -> Select.TUPLE.of(t.id(),
                            t.createTime(),
                            t.expression().sqlSegment("1").asAnyType(String.class)
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`create_time` AS `value2`,1 AS `value3` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            LocalDateTime value2 = list.get(0).getValue2();
            String value3 = list.get(0).getValue3();
            Assert.assertEquals("1", value3);
        }

    }

    @Test
    public void tupleTest9() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Tuple3<String, LocalDateTime, String>> list = easyEntityQuery
                    .queryable(BlogEntity.class)
                    .select(t -> Select.TUPLE.of(t.id(),
                            t.createTime(),
                            t.expression().sqlSegment("IFNULL({0},'1')", c -> c.expression(t.title())).asAnyType(String.class)
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`create_time` AS `value2`,IFNULL(t.`title`,'1') AS `value3` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            LocalDateTime value2 = list.get(0).getValue2();
            String value3 = list.get(0).getValue3();
            Assert.assertEquals("title0", value3);
        }

    }

    @Test
    public void test11a() {
        easyCacheClient.clear(new DefaultClearParameter("1", null, CacheMethodEnum.UPDATE, LocalDateTime.of(2020, 1, 1, 1, 1), "t_blog", new HashMap<>()));
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class).singleOrNull("1");

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class).singleOrNull("1");
            Assert.assertNotNull(blogEntity);
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class).useInterceptor("blog-cache").singleOrNull("1");

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` = ? AND `id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        EasyCacheManager service = easyCacheClient.getService(EasyCacheManager.class);
        DefaultEasyRedisManagerMultiLevel redisManagerMultiLevel = (DefaultEasyRedisManagerMultiLevel) service;
        Cache<String, Map<String, CacheItem>> cache = redisManagerMultiLevel.cache;
        {
            Map<String, CacheItem> cacheItemMap = cache.get("CACHE:BlogEntity:1", k -> null);
            Assert.assertNotNull(cacheItemMap);
            CacheItem cacheItem = cacheItemMap.get("{}");
            String json = cacheItem.getJson();
            BlogEntity blogEntity = JsonUtil.jsonStr2Object(json, BlogEntity.class);
            Assert.assertEquals("1", blogEntity.getId());
        }
        {
            Map<String, CacheItem> cacheItemMap = cache.get("CACHE:BlogEntity:1", k -> null);
            Assert.assertNotNull(cacheItemMap);
            CacheItem cacheItem = cacheItemMap.get("{\"sql\":\"`content` = ?\",\"parameters\":\"123(String)\"}");
            String json = cacheItem.getJson();
            Assert.assertNull(json);
        }

        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class).filter(b -> !Objects.equals(b.getId(), "1")).singleOrNull("1");
            Assert.assertNull(blogEntity);
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
            listenerContextManager.clear();
        }
        System.out.println("1");
    }

    @Test
    public void test11a1() {
        easyCacheClient.clear(new DefaultClearParameter("1", null, CacheMethodEnum.UPDATE, LocalDateTime.of(2020, 1, 1, 1, 1), "t_topic", new HashMap<>()));
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Topic blogEntity = easyCacheClient.allStorage(Topic.class).singleOrNull("1");
            Assert.assertNotNull(blogEntity);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        easyCacheClient.clear(new DefaultClearParameter("1", "INDEX", CacheMethodEnum.DELETE, LocalDateTime.of(2020, 1, 1, 1, 1), "t_topic", new HashMap<>()));
        {

            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);
            easyCacheClient.allStorage(Topic.class).toList();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT t.`id` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            }
            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            listenerContextManager.clear();
        }

        EasyCacheManager service = easyCacheClient.getService(EasyCacheManager.class);
        DefaultEasyRedisManagerMultiLevel redisManagerMultiLevel = (DefaultEasyRedisManagerMultiLevel) service;
        Cache<String, Map<String, CacheItem>> cache = redisManagerMultiLevel.cache;
        {
            Map<String, CacheItem> cacheItemMap = cache.get("CACHE:Topic:1", k -> null);
            Assert.assertNotNull(cacheItemMap);
            CacheItem cacheItem = cacheItemMap.get("{}");
            String json = cacheItem.getJson();
            Topic blogEntity = JsonUtil.jsonStr2Object(json, Topic.class);
            Assert.assertEquals("1", blogEntity.getId());
        }
        {
            Map<String, CacheItem> cacheItemMap = cache.get("CACHE:Topic:INDEX", k -> null);
            Assert.assertNotNull(cacheItemMap);
            CacheItem cacheItem = cacheItemMap.get("{}");
            String json = cacheItem.getJson();
            Assert.assertEquals("{\"index\":[\"88\",\"89\",\"995\",\"90\",\"91\",\"92\",\"93\",\"94\",\"95\",\"96\",\"97\",\"10\",\"98\",\"11\",\"99\",\"12\",\"13\",\"14\",\"15\",\"16\",\"17\",\"18\",\"19\",\"0\",\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"7\",\"8\",\"9\",\"20\",\"21\",\"22\",\"23\",\"24\",\"25\",\"26\",\"27\",\"28\",\"29\",\"30\",\"31\",\"32\",\"33\",\"34\",\"35\",\"36\",\"37\",\"38\",\"39\",\"40\",\"41\",\"42\",\"43\",\"44\",\"45\",\"46\",\"47\",\"48\",\"49\",\"50\",\"51\",\"52\",\"53\",\"54\",\"55\",\"56\",\"57\",\"58\",\"59\",\"60\",\"61\",\"62\",\"63\",\"64\",\"65\",\"66\",\"67\",\"68\",\"69\",\"70\",\"71\",\"72\",\"73\",\"74\",\"75\",\"76\",\"77\",\"78\",\"79\",\"80\",\"81\",\"82\",\"83\",\"84\",\"85\",\"86\",\"87\"]}", json);
        }
        System.out.println("1");

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Topic blogEntity = easyCacheClient.allStorage(Topic.class).filter(topic->Objects.equals(topic.getId(),"1")).singleOrNull("1");
            Assert.assertNotNull(blogEntity);
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Topic blogEntity = easyCacheClient.allStorage(Topic.class).filter(topic->!Objects.equals(topic.getId(),"1")).singleOrNull("1");
            Assert.assertNull(blogEntity);
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Topic blogEntity = easyCacheClient.allStorage(Topic.class).filter(topic->Objects.equals(topic.getId(),"1")).singleOrNull("1");
            Assert.assertNotNull(blogEntity);
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
            listenerContextManager.clear();
        }
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//            BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class).singleOrNull("1");
//
//            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
//            listenerContextManager.clear();
//        }
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//            BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class).useInterceptor("blog-cache").singleOrNull("1");
//
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` = ? AND `id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("false(Boolean),123(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//        EasyCacheManager service = easyCacheClient.getService(EasyCacheManager.class);
//        DefaultEasyRedisManagerMultiLevel redisManagerMultiLevel = (DefaultEasyRedisManagerMultiLevel) service;
//        Cache<String, Map<String, CacheItem>> cache = redisManagerMultiLevel.cache;
//        {
//            Map<String, CacheItem> cacheItemMap = cache.get("CACHE:BlogEntity:1", k -> null);
//            Assert.assertNotNull(cacheItemMap);
//            CacheItem cacheItem = cacheItemMap.get("{}");
//            String json = cacheItem.getJson();
//            BlogEntity blogEntity = JsonUtil.jsonStr2Object(json, BlogEntity.class);
//            Assert.assertEquals("1",blogEntity.getId());
//        }
//        {
//            Map<String, CacheItem> cacheItemMap = cache.get("CACHE:BlogEntity:1", k -> null);
//            Assert.assertNotNull(cacheItemMap);
//            CacheItem cacheItem = cacheItemMap.get("{\"sql\":\"`content` = ?\",\"parameters\":\"123(String)\"}");
//            String json = cacheItem.getJson();
//            Assert.assertNull(json);
//        }
//        System.out.println("1");
    }

}
