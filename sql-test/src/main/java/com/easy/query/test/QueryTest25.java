package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
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
import com.easy.query.core.util.EasyTypeUtil;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.proxy.TopicTypeVOProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.listener.ListenerContext;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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

}
