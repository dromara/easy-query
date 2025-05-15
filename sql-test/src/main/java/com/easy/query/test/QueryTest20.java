package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.key.MapKey;
import com.easy.query.api.proxy.key.MapKeys;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.test.dto.TopicGroupTestDTO;
import com.easy.query.test.dto.proxy.TopicGroupTestDTOProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.vo.TopicAndBlogVO;
import com.easy.query.test.vo.proxy.GroupVOProxy;
import com.easy.query.test.vo.proxy.TopicAndBlogVOProxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/2/8 23:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest20 extends BaseTest {


    @Test
    public void testFullSingleTable() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<String, Integer, LocalDateTime>> myBlog = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                })
                .groupBy(b -> GroupKeys.of(b.title()))
                .having(group -> {
                    group.groupTable().star().sum().lt(10);
                })
                .select(group -> Select.DRAFT.of(
                        group.key1(),//value1
                        group.groupTable().star().sum().asAnyType(Integer.class),//value2
                        group.groupTable().createTime().max()//value3
                )).orderBy(group -> group.value3().desc())
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`value1` AS `value1`,t1.`value2` AS `value2`,t1.`value3` AS `value3` FROM (SELECT t.`title` AS `value1`,SUM(t.`star`) AS `value2`,MAX(t.`create_time`) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ? GROUP BY t.`title` HAVING SUM(t.`star`) < ?) t1 ORDER BY t1.`value3` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String),10(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testFullSingleAllColumns() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                })
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testFullSingleAllColumns1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                })
                .select(b -> b)
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testFullSingleAllColumns2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                })
                .select(b -> new BlogEntityProxy())
//                .select(b -> new BlogEntityProxy().selectAll(b))
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testFullSingleAllColumns3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                })
//                .select(b -> new BlogEntityProxy())
                .select(b -> new BlogEntityProxy().selectAll(b))
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testFullSingleSomeColumns1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                })
                .select(b -> b.FETCHER.id().star().status())
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`star`,t.`status` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testFullSingleSomeColumns2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                })
                .select(b -> b.FETCHER.allFieldsExclude(b.title(), b.star()))
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testFullSingleSomeColumns3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                    b.title().eq("123");
                })
                //select第一个参数表示返回类型
                //第二个参数表示要映射的列和对应的别名
                .select(BlogEntity.class, b -> Select.of(
                        //lombok 使用@FieldsNameConstant
                        //可以使用BlogEntity.Fields.id达到一样的下过
                        b.id().as(BlogEntityProxy.Fields.id),
                        b.title().as(BlogEntityProxy.Fields.title),
                        b.star().as(BlogEntityProxy.Fields.star)
                ))
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,t.`title` AS `title`,t.`star` AS `star` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ? AND t.`title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testFullSingleColumns1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<String> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                    b.title().eq("123");
                })
                .select(b -> b.id())
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ? AND t.`title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testFullSingleColumns2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<String> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                    b.title().eq("123");
                })
                .selectColumn(b -> b.id())
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ? AND t.`title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%my blog%(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testFullSingleColumns3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<String> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                    b.title().eq("123");
                })
                .selectColumn(b -> b.id().nullOrDefault("123"))
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ? AND t.`title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),false(Boolean),%my blog%(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testFullSingleColumns4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<String> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.content().like("my blog");
                    b.title().eq("123");
                })
                .select(b -> new StringProxy(b.id().nullOrDefault("123")))
                .toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ? AND t.`title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),false(Boolean),%my blog%(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testJoinMultiOn() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Topic topic = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> {
                    t.id().eq(t1.id());
                    t1.title().like("234");
                })
                .where(o -> o.id().eq("3"))
                .singleOrNull();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` AND t1.`title` LIKE ? WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%234%(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testGroupJoin1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        EntityQueryable<TopicGroupTestDTOProxy, TopicGroupTestDTO> sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().eq("3"))
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(group -> new TopicGroupTestDTOProxy()
                        .id().set(group.key1()) //
                        .idCount().set(group.groupTable().id().intCount()));
        List<Draft2<String, Integer>> list = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(sql, (a, b) -> a.id().eq(b.id()))
                .where((b1, t2) -> {
                    b1.id().isNotNull();
                    t2.id().eq("123");
                }).select((b1, t2) -> Select.DRAFT.of(
                        b1.id(),
                        t2.idCount()
                )).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t3.`id_count` AS `value2` FROM `t_blog` t LEFT JOIN (SELECT t1.`id` AS `id`,COUNT(t1.`id`) AS `id_count` FROM `t_topic` t1 WHERE t1.`id` = ? GROUP BY t1.`id`) t3 ON t.`id` = t3.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t3.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("3(String),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testGroupJoin2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            EntityQueryable<TopicGroupTestDTOProxy, TopicGroupTestDTO> sql = easyEntityQuery
                    .queryable(Topic.class)
                    .where(o -> o.id().eq("3"))
                    .groupBy(o -> GroupKeys.of(o.id()))
                    .select(group -> new TopicGroupTestDTOProxy()
                            .id().set(group.key1()) //
                            .idCount().set(group.groupTable().id().intCount()))
                    .toCteAs();

            List<Draft2<String, Integer>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(sql, (a, b) -> a.id().eq(b.id()))
                    .where((b1, t2) -> {
                        b1.id().isNotNull();
                        t2.id().eq("123");
                    }).select((b1, t2) -> Select.DRAFT.of(
                            b1.id(),
                            t2.idCount()
                    )).toList();
        } catch (Exception e) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `with_TopicGroupTestDTO` AS (SELECT t1.`id` AS `id`,COUNT(t1.`id`) AS `id_count` FROM `t_topic` t1 WHERE t1.`id` = ? GROUP BY t1.`id`)  SELECT t.`id` AS `value1`,t3.`id_count` AS `value2` FROM `t_blog` t LEFT JOIN `with_TopicGroupTestDTO` t3 ON t.`id` = t3.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t3.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("3(String),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testGroupJoin3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            EntityQueryable<TopicProxy, Topic> sql = easyEntityQuery
                    .queryable(Topic.class)
                    .where(o -> o.id().eq("3"));

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(sql, (t_blog, t2) -> t_blog.id().eq(t2.id()))
                    .where((t_blog, t_topic) -> {
                        t_blog.id().eq("345");
                        t_topic.id().eq("789");
                    }).toList();
        } catch (Exception ex) {

        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) t2 ON t.`id` = t2.`id` WHERE t.`deleted` = ? AND t.`id` = ? AND t2.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("3(String),false(Boolean),345(String),789(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testMapIgnore() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {


            List<Map<String, Object>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .select((b1, t2) -> {
                        MapProxy result = new MapProxy();
                        result.selectAll(b1);
                        result.selectIgnores(b1.createTime());
                        result.put("xx", t2.createTime());
                        return result;
                    })
                    .toList();
        } catch (Exception ex) {

        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t1.`create_time` AS `xx` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testJoinVo() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TopicAndBlogVO> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(Topic.class, (t_blog, t_topic) -> t_blog.id().eq(t_topic.id()))
                .select((t_blog, t_topic) -> new TopicAndBlogVOProxy()
                        .column1().set(t_blog.title()) //
                        .column2().set(t_topic.title()) //
                )
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`title` AS `column1`,t1.`title` AS `column2` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testGroup() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft4<String, Long, LocalDateTime, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");
                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.count(),
                        group.groupTable().createTime().max(),
                        group.groupTable().score().sum().asAnyType(BigDecimal.class)
                )).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT(*) AS `value2`,MAX(t.`create_time`) AS `value3`,SUM(t.`score`) AS `value4` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testGroup1() {

//        Select.DRAFT.of(
//                group.key1(),
//                group.count(),
//                group.groupTable().createTime().max(),
//                group.groupTable().score().sum().asAnyType(BigDecimal.class)
//        )
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");
                    t_blog.star().asAny().like("1232");
                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> new GroupVOProxy()
                        .key().set(group.key1())
                        .idCount().set(group.count())
                        .createTimeMax().set(group.groupTable().createTime().max())
                        .scoreSum().set(group.groupTable().score().sum().asAnyType(BigDecimal.class))
                ).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`title` AS `key`,COUNT(*) AS `id_count`,MAX(t.`create_time`) AS `create_time_max`,SUM(t.`score`) AS `score_sum` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? AND t.`star` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String),%1232%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testWhere1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");
                    t_blog.star().gt(1);
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE ? AND `star` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testWhere2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.title().like("123");
                    t_topic.stars().gt(1);
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `title` LIKE ? AND `stars` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testWhere3() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> {
                    t_topic.id().eq(t_blog.id());
                })
                .where((t_topic, t_blog) -> {
                    t_topic.title().like("123");
                    t_blog.status().eq(1);
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`title` LIKE ? AND t1.`status` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testMap1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> {
                    t_topic.id().eq(t_blog.id());
                })
                .where((t_topic, t_blog) -> {
                    t_topic.title().like("123");
                }).select((t_topic, t_blog) -> new MapProxy()
                        .put("v1", t_topic.id())
                        .put("v2", t_blog.star().add(1))
                        .put("v3", t_topic.createTime().nullOrDefault(LocalDateTime.of(2024, 1, 1, 0, 0)))
                ).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `v1`,(t1.`star` + ?) AS `v2`,IFNULL(t.`create_time`,?) AS `v3` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),2024-01-01T00:00(LocalDateTime),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testMap2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        MapKey<String> str = MapKeys.stringKey("str");
        MapKey<Integer> integer = MapKeys.integerKey("integer");
        MapKey<LocalDateTime> time = MapKeys.localDateTimeKey("time");

        List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> {
                    t_topic.id().eq(t_blog.id());
                })
                .where((t_topic, t_blog) -> {
                    t_topic.title().like("123");
                }).select((t_topic, t_blog) -> new MapTypeProxy()
                        .put(str, t_topic.id())
                        .put(integer, t_blog.star().add(1))
                        .put(time, t_topic.createTime().nullOrDefault(LocalDateTime.of(2024, 1, 1, 0, 0)))
                ).toList();
        for (Map<String, Object> map : list) {
            String value1OrNull = str.getValueOrNull(map);
            Integer value2OrNull = integer.getValueOrNull(map);
            LocalDateTime value3OrNull = time.getValueOrNull(map);
        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `str`,(t1.`star` + ?) AS `integer`,IFNULL(t.`create_time`,?) AS `time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),2024-01-01T00:00(LocalDateTime),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test1() {
        String format = EasyUtil.getQuarterStart(LocalDateTime.of(2025,2,3,0,1,2)).format(DateTimeFormatter.ofPattern("yyyy'Q'Q"));
        System.out.println(format);
        Assert.assertEquals("2025Q1", format);


    }


}
