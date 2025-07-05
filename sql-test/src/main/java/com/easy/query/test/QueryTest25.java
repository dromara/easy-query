package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.key.MapKey;
import com.easy.query.api.proxy.key.MapKeys;
import com.easy.query.cache.core.common.CacheItem;
import com.easy.query.cache.core.common.DefaultCacheKey;
import com.easy.query.cache.core.manager.EasyCacheManager;
import com.easy.query.cache.core.provider.EasyCacheProvider;
import com.easy.query.cache.core.base.CacheMethodEnum;
import com.easy.query.cache.core.base.DefaultClearParameter;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.entity.EntityPropertyDescriptorMatcher;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.builder.core.ValueFilterFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.tuple.Tuple1;
import com.easy.query.core.proxy.core.tuple.Tuple2;
import com.easy.query.core.proxy.core.tuple.Tuple3;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.part.Part1;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyMD5Util;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyTypeUtil;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.cache.DefaultCacheManager;
import com.easy.query.test.cache.JsonUtil;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.conversion.JavaEncryptionStrategy;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.proxy.TopicTypeVOProxy;
import com.easy.query.test.encryption.Base64EncryptionStrategy;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.test.encryption.MyEncryptionStrategy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.tolistflat.TestA;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.github.benmanes.caffeine.cache.Cache;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

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
        DefaultCacheKey cacheKey = new DefaultCacheKey(BlogEntity.class, "1");
        easyCacheClient.deleteBy(cacheKey);
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
        DefaultCacheManager redisManagerMultiLevel = (DefaultCacheManager) service;
        Cache<String, Map<String, CacheItem>> cache = redisManagerMultiLevel.caffeineCache;
        {
            Map<String, CacheItem> cacheItemMap = cache.get("CACHE:BlogEntity:1", k -> null);
            Assert.assertNotNull(cacheItemMap);
            CacheItem cacheItem = cacheItemMap.get("{\"sql\":\"`deleted` = ?\",\"parameters\":\"false(Boolean)\"}");
            String json = cacheItem.getJson();
            BlogEntity blogEntity = JsonUtil.jsonStr2Object(json, BlogEntity.class);
            Assert.assertEquals("1", blogEntity.getId());
        }
        {
            Map<String, CacheItem> cacheItemMap = cache.get("CACHE:BlogEntity:1", k -> null);
            Assert.assertNotNull(cacheItemMap);
            CacheItem cacheItem = cacheItemMap.get("{\"sql\":\"`deleted` = ? AND `content` = ?\",\"parameters\":\"false(Boolean),123(String)\"}");
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

        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class).where(b -> {
                b.content().contains("123xx");
                b.content().contains("123xx4");
            }).singleOrNull("2");
            Assert.assertNull(blogEntity);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE CONCAT('%',?,'%') AND `content` LIKE CONCAT('%',?,'%') AND `id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123xx(String),123xx4(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class).where(b -> {
                b.content().contains("123xx");
            }).singleOrNull("2");
            Assert.assertNull(blogEntity);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE CONCAT('%',?,'%') AND `id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123xx(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class).where(b -> {
                b.users().flatElement().phone().eq("133333");
            }).singleOrNull("2");
            Assert.assertNull(blogEntity);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND EXISTS (SELECT 1 FROM `easy-query-test`.`t_sys_user` t1 WHERE t1.`id` = t.`title` AND t1.`phone` = ? LIMIT 1) AND t.`id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),Nun4XeChdyK43Ss/oLE6eQ==TAIxYGF+bNgNUIbsBsV2LA==TAIxYGF+bNgNUIbsBsV2LA==(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


            String key = "SSELECT * FROM `t_blog` t LEFT JOIN (SELECT t1.`id` AS `id`,(CASE WHEN COUNT((CASE WHEN t1.`phone` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `easy-query-test`.`t_sys_user` t1 GROUP BY t1.`id`) t2 ON t2.`id` = t.`title` WHERE t.`deleted` = ? AND IFNULL(t2.`__any2__`,?) = ?:133333(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),false(Boolean),true(Boolean)";
            Assert.assertEquals("4d34dafc06c8c83b302136c4b7c3cf03", EasyMD5Util.getMD5Hash(key));
            listenerContextManager.clear();
        }
        System.out.println("1");
    }

    @Test
    public void test11a1() {

        DefaultCacheKey cacheKey = new DefaultCacheKey(Topic.class, "1");
        easyCacheClient.deleteBy(cacheKey);
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
        DefaultCacheKey k1 = new DefaultCacheKey(Topic.class, "1");
        easyCacheClient.deleteBy(k1);
        DefaultCacheKey k2 = new DefaultCacheKey(Topic.class, "INDEX");
        easyCacheClient.deleteBy(k2);
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
                Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IN (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("88(String),89(String),995(String),90(String),91(String),92(String),93(String),94(String),95(String),96(String),97(String),10(String),98(String),11(String),99(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),20(String),21(String),22(String),23(String),24(String),25(String),26(String),27(String),28(String),29(String),30(String),31(String),32(String),33(String),34(String),35(String),36(String),37(String),38(String),39(String),40(String),41(String),42(String),43(String),44(String),45(String),46(String),47(String),48(String),49(String),50(String),51(String),52(String),53(String),54(String),55(String),56(String),57(String),58(String),59(String),60(String),61(String),62(String),63(String),64(String),65(String),66(String),67(String),68(String),69(String),70(String),71(String),72(String),73(String),74(String),75(String),76(String),77(String),78(String),79(String),80(String),81(String),82(String),83(String),84(String),85(String),86(String),87(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            listenerContextManager.clear();
        }

        EasyCacheManager service = easyCacheClient.getService(EasyCacheManager.class);
        DefaultCacheManager redisManagerMultiLevel = (DefaultCacheManager) service;
        Cache<String, Map<String, CacheItem>> cache = redisManagerMultiLevel.caffeineCache;
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
            Topic blogEntity = easyCacheClient.allStorage(Topic.class).filter(topic -> Objects.equals(topic.getId(), "1")).singleOrNull("1");
            Assert.assertNotNull(blogEntity);
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Topic blogEntity = easyCacheClient.allStorage(Topic.class).filter(topic -> !Objects.equals(topic.getId(), "1")).singleOrNull("1");
            Assert.assertNull(blogEntity);
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Topic blogEntity = easyCacheClient.allStorage(Topic.class).filter(topic -> Objects.equals(topic.getId(), "1")).singleOrNull("1");
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

    @Test
    public void xxx() {
        List<Draft1<Boolean>> list = easyEntityQuery.queryable(Topic.class)
                .select(t_topic -> {

                    Expression expression = t_topic.expression();

                    BooleanTypeExpression<Boolean> booleanTypeExpression = expression.valueOf(() -> {

                        expression.exists(() -> {
                            return expression.subQueryable(BlogEntity.class).where(t -> {
                                t.title().eq(t_topic.title());
                            });
                        });
                    });


                    return Select.DRAFT.of(
                            booleanTypeExpression
                    );
                }).toList();

    }

    @Test
    public void xxx22() {


        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(1);

        ListenerContextManager listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setDefaultDataSourceName("ds2020");
                    op.setReverseOffsetThreshold(10);
                })
//                .replaceService(Column2MapKeyConversion.class, UpperColumn2MapKeyConversion.class)
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .replaceService(Dialect.class, DefaultDialect.class)
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .replaceService(QueryConfiguration.class, MyQueryConfiguration.class)
//                .replaceService(EntityMappingRule.class, PropertyEntityMappingRule.class)
                .replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class)
                .replaceService(PropertyDescriptorMatcher.class, EntityPropertyDescriptorMatcher.class)
                .replaceService(ValueFilterFactory.class, MyValueFactory.class)
//                .replaceService(EasyPageResultProvider.class,MyEasyPageResultProvider.class)
//                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        DefaultEasyEntityQuery easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        QueryRuntimeContext runtimeContext = easyEntityQuery.getRuntimeContext();
        QueryConfiguration configuration = runtimeContext.getQueryConfiguration();
        configuration.applyEncryptionStrategy(new DefaultAesEasyEncryptionStrategy());
        configuration.applyEncryptionStrategy(new Base64EncryptionStrategy());
        configuration.applyEncryptionStrategy(new MyEncryptionStrategy());
        configuration.applyEncryptionStrategy(new JavaEncryptionStrategy());

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(t -> {
//                    t.blogs().any(s -> s.content().eq((String) null));
                    t.blogs().flatElement().content().eq((String) null);
                    t.blogs().flatElement().users().flatElement().phone().eq((String) "123");
                }).toList();

        EntityMetadataManager entityMetadataManager = easyEntityQuery.getRuntimeContext().getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(Topic.class);

        Supplier<Object> beanConstructorCreator = entityMetadata.getBeanConstructorCreator();
        //new topic
        Object o = beanConstructorCreator.get();
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull("id");

        //topic.getId()
        Object id = columnMetadata.getGetterCaller().apply(o);

        //topic.setId("123")
        columnMetadata.getSetterCaller().call(o, "123");

//        easyEntityQuery.getEasyQueryClient().deletable(Map.class)
//                .asTable("t_user")
//                .where(m -> {
//                    m.eq("id","123");
//                }).executeRows();

    }

    public static class MyValueFactory implements ValueFilterFactory {


        @Override
        public ValueFilter getQueryValueFilter() {
            return NotNullOrEmptyValueFilter.DEFAULT;
        }

        @Override
        public ValueFilter getInsertValueFilter() {
            return AnyValueFilter.DEFAULT;
        }

        @Override
        public ValueFilter getExecuteValueFilter() {
            return AnyValueFilter.DEFAULT;
        }

        @Override
        public ValueFilter getUpdateValueFilter() {
            return AnyValueFilter.DEFAULT;
        }

        @Override
        public ValueFilter getDeleteValueFilter() {
            return AnyValueFilter.DEFAULT;
        }
    }


    @Test
    public void testMap() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> new MapProxy()
                        .put("aa", t_blog.id())
                        .put("bb", t_blog.star())
                )
                .where(o -> {
                    o.get("aa").eq("123");
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`aa` AS `aa`,t1.`bb` AS `bb` FROM (SELECT t.`id` AS `aa`,t.`star` AS `bb` FROM `t_blog` t WHERE t.`deleted` = ?) t1 WHERE t1.`aa` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testMap1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        MapKey<String> aa = MapKeys.stringKey("aa");
        MapKey<Integer> bb = MapKeys.integerKey("bb");

        List<Map<String, Object>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> new MapTypeProxy()
                        .put(aa, t_blog.id())
                        .put(bb, t_blog.star())
                )
                .where(o -> {
                    o.get(aa).eq("123");
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`aa` AS `aa`,t1.`bb` AS `bb` FROM (SELECT t.`id` AS `aa`,t.`star` AS `bb` FROM `t_blog` t WHERE t.`deleted` = ?) t1 WHERE t1.`aa` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


        List<Part1<BlogEntity, String>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.PART.of(
                        t_blog,
                        t_blog.title().concat("123")
                ))
                .where(part -> {
                    part.partColumn1().eq("123");
                    part.entityTable().star().eq(1);
                }).toList();
    }

    @Test
    public void testDraft() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_topic.id().eq(t_blog.star()))
                .select((t_topic, t_blog) -> {
                    BlogEntityProxy blogEntityProxy = t_blog.FETCHER.id().star().fetchProxy();
                    blogEntityProxy.content().set(t_topic.title());
                    return blogEntityProxy;
                })
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`star`,t.`title` AS `content` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`star`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testDraft2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_topic.id().eq(t_blog.star()))
                .select((t_topic, t_blog) -> {
                    BlogEntityProxy blogEntityProxy = t_blog.FETCHER.id().star().fetchProxy();
                    blogEntityProxy.content().set(t_topic.title());
                    return blogEntityProxy;
                })
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`star`,t.`title` AS `content` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`star`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testList() {

        List<String> list1 = easyEntityQuery.queryable(Topic.class).orderBy(t_topic -> t_topic.id().asc()).selectColumn(t_topic -> t_topic.id()).toList();
        List<String> list2 = easyEntityQuery.queryable(Topic.class).orderBy(t_topic -> t_topic.id().asc()).toList(t -> t.id());
        Assert.assertFalse(list1.isEmpty());
        Assert.assertEquals(list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            Assert.assertEquals(list1.get(i), list2.get(i));
        }
    }

    @Test
    public void testOnlyIgnore() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .select(t_topic -> t_topic.selectIgnores(t_topic.id()))
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
    }

    @Test
    public void testOnlyIgnore2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .select(t_topic -> t_topic.selectIgnores(t_topic.id()))
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
    }

    @Test
    public void testOnlyIgnore3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .select(t_topic -> new TopicProxy().selectIgnores(t_topic.id()))
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
    }

    @Test
    public void testToListFlat1() {
        Exception ee = null;
        try {

            List<String> list1 = easyEntityQuery.queryable(TestA.class)
                    .where(t -> {
                        t.aname().like("123");
                    }).toList(t -> t.bList().flatElement().cList().flatElement().cname());
        } catch (Exception ex) {
            ee = ex;
        }
        Assert.assertNotNull(ee);
        Assert.assertEquals("com.easy.query.core.exception.EasyQuerySQLStatementException: java.sql.SQLSyntaxErrorException: Table 'easy-query-test.test_a' doesn't exist", ee.getMessage());
    }

}
