package com.easy.query.test;

import com.easy.query.api.proxy.base.BigDecimalProxy;
import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLConstantExpression;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.BlogEntity2;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.relation.RelationUser;
import com.easy.query.test.entity.school.MySchoolStudent;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * create time 2024/1/19 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest12 extends BaseTest {
    @Test
    public void orderTest1() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .orderBy(t -> t.id().asc())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` ORDER BY `id` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .orderBy(t -> t.id().asc())
                    .orderBy(t -> t.createTime().desc())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` ORDER BY `id` ASC,`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .orderBy(t -> {
                        t.id().asc();
                        t.createTime().desc();
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` ORDER BY `id` ASC,`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .orderBy(t -> {
                        t.id().asc(false);
                        t.createTime().desc();
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` ORDER BY `create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .orderBy(t -> {
                        t.createTime().format("yyyy-MM-dd").desc();
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` ORDER BY DATE_FORMAT(`create_time`,'%Y-%m-%d') DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .orderBy(t -> {
                        t.id().asc(OrderByModeEnum.NULLS_LAST);
                        t.createTime().desc(OrderByModeEnum.NULLS_FIRST);
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` ORDER BY CASE WHEN `id` IS NULL THEN 1 ELSE 0 END ASC,`id` ASC,CASE WHEN `create_time` IS NULL THEN 0 ELSE 1 END ASC,`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testAny1() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            boolean any = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.title().eq("1"))
                    .orderBy(t -> {
                        t.id().asc(OrderByModeEnum.NULLS_LAST);
                        t.createTime().desc(OrderByModeEnum.NULLS_FIRST);
                    })
                    .any();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT  1  FROM `t_topic` WHERE `title` = ? ORDER BY CASE WHEN `id` IS NULL THEN 1 ELSE 0 END ASC,`id` ASC,CASE WHEN `create_time` IS NULL THEN 0 ELSE 1 END ASC,`create_time` DESC LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testJoinSelect() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (topic, blog) -> topic.id().eq(blog.id()))
                    .where((topic, blog) -> {
                        topic.id().eq("111");
                    })
                    .select((topic, blog) -> blog)
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t1.`id`,t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),111(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (topic, blog) -> topic.id().eq(blog.id()))
                    .where((topic, blog) -> {
                        topic.id().eq("111");
                    })
                    .select((topic, blog) -> topic)
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),111(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (topic, blog) -> topic.id().eq(blog.id()))
                    .where((topic, blog) -> {
                        topic.id().eq("111");
                    })
                    .select((topic, blog) -> topic.selectExpression(topic.FETCHER.id().title()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`title` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),111(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();


            List<Draft1<String>> list1 = easyEntityQuery.queryable(Topic.class).select(o -> Select.DRAFT.of(o.id())).toList();

            List<String> list2 = easyEntityQuery.queryable(Topic.class).selectColumn(o -> o.id()).toList();
            List<LocalDateTime> list3 = easyEntityQuery.queryable(BlogEntity.class).selectColumn(o -> o.createTime()).toList();

        }
    }

    @Test
    public void testDraft21() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<Draft2Proxy<String, String>, Draft2<String, String>> draft2ProxyDraft2EntityQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(o -> GroupKeys.TABLE1.of(o.content().subString(0, 8)))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.join(o.group().id(), ",")
                ));

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(draft2ProxyDraft2EntityQueryable, (a, b) -> a.id().eq(b.value1()))
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT SUBSTR(t1.`content`,1,8) AS `value1`,GROUP_CONCAT(t1.`id` SEPARATOR ?) AS `value2` FROM `t_blog` t1 WHERE t1.`deleted` = ? GROUP BY SUBSTR(t1.`content`,1,8)) t3 ON t.`id` = t3.`value1` WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),false(Boolean),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

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

    }

    @Test
    public void testMap1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                .select((b1, t2) -> {
                    MapProxy result = new MapProxy();
                    result.selectAll(b1);
                    result.put("xx", t2.createTime());
                    return result;
                })
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t1.`create_time` AS `xx` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testMap2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

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
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t1.`create_time` AS `xx` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testDraft23() {


        List<Draft1<LocalDateTime>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(o -> Select.DRAFT.of(
                        o.createTime()
                )).toList();
        for (Draft1<LocalDateTime> localDateTimeDraft1 : list) {

            LocalDateTime value1 = localDateTimeDraft1.getValue1();

        }
//        easyEntityQuery.queryable(BlogEntity.class)
//                .select(b ->new UserNameAndId)

        List<Draft2<String, LocalDateTime>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(o -> Select.DRAFT.of(
                        o.id(),
                        o.createTime().asAny()
                )).toList();
        for (Draft2<String, LocalDateTime> localDateTimeDraft1 : list1) {

            String value1 = localDateTimeDraft1.getValue1();
            LocalDateTime value2 = localDateTimeDraft1.getValue2();

        }
//        List<Map<String, Object>> list2 = easyEntityQuery.queryable(BlogEntity.class)
//                .select(o -> {
//                    MapProxy mapProxy = new MapProxy();
//                    mapProxy.put("id", o.id());
//                    mapProxy.put("title", o.title());
//                    return mapProxy;
//                })
//                .where(o -> {
//                    o.getColumn("title").eq("1");
//                }).toList();
//        for (Map<String, Object> stringObjectMap : list2) {
//
//            System.out.println(stringObjectMap);
//        }
    }

    @Test
    public void testOr26() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.or(() -> {
                        b.id().eq("123");
                        b.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`id` = ? OR `create_time` <= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testNum1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(b -> new BlogEntityProxy().adapter(r -> {
                    r.score().set(
                            b.expression().sqlType("SUM({0})-SUM({1})", c -> {
                                c.expression(b.score()).expression(b.score());
                            }).setPropertyType(BigDecimal.class)
                    );
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUM(t.`score`)-SUM(t.`score`) AS `score` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testNum2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(b -> new BlogEntityProxy().adapter(r -> {
                    r.score().set(
                            b.score().sum().subtract(b.score().sum())
                    );
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SUM(t.`score`) - SUM(t.`score`)) AS `score` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testNum3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BigDecimal> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(b -> new BigDecimalProxy(b.score().sum().subtract(b.score().sum()))).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SUM(t.`score`) - SUM(t.`score`)) FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        List<BigDecimal> list = easyEntityQuery.queryable(BlogEntity.class)
                .selectColumn(b -> b.star().multiply(b.score()).add(b.score())).toList();
    }

    @Test
    public void testNum4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BigDecimal> list = easyEntityQuery.queryable(BlogEntity.class)
                .selectColumn(b -> b.star().multiply(b.score()).add(b.score())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ((t.`star` * t.`score`) + t.`score`) FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testNum5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BigDecimal> list = easyEntityQuery.queryable(BlogEntity.class)
                .selectColumn(b -> b.star().multiply(b.score()).add(100)).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ((t.`star` * t.`score`) + ?) FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("100(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testNum6() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BigDecimal> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    SQLMathExpression.floor(b.score()).eq(BigDecimal.ZERO);
                })
                .selectColumn(b -> b.star().multiply(b.score()).add(100)).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ((t.`star` * t.`score`) + ?) FROM `t_blog` t WHERE t.`deleted` = ? AND FLOOR(t.`score`) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("100(Integer),false(Boolean),0(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testNum7() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Query<String> objectQuery = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    SQLMathExpression.floor(b.score()).eq(BigDecimal.ZERO);
                })
                .selectColumn(b -> b.expression().sqlType("GROUP_CONCAT({0})", c -> c.expression(b.title())).setPropertyType(String.class));
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    Expression expression = b.expression();
                    expression.sql("FIND_IN_SET({0},{1})", c -> {
                        c.expression(b.title());
                        c.expression(objectQuery);
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND FIND_IN_SET(t.`title`,(SELECT GROUP_CONCAT(t1.`title`) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND FLOOR(t1.`score`) = ?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),false(Boolean),0(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testNum8() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Query<String> objectQuery = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    SQLMathExpression.floor(b.score()).eq(BigDecimal.ZERO);
                })
                .selectColumn(b -> b.title().join(","));
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    Expression expression = b.expression();
                    expression.sql("FIND_IN_SET({0},{1})", c -> {
                        c.expression(b.title());
                        c.expression(objectQuery);
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND FIND_IN_SET(t.`title`,(SELECT GROUP_CONCAT(t1.`title` SEPARATOR ?) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND FLOOR(t1.`score`) = ?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),,(String),false(Boolean),0(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


//        List<String> list = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> {
//                    SQLMathExpression.floor(b.score()).eq(BigDecimal.ZERO);
//                })
//                .selectColumn(b -> b.score().asAny().join(",")).toList();
    }

    @Test
    public void testOrSub1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.or(() -> {
                        Expression expression = b.expression();
                        expression.sql("FIND_IN_SET({0},{1})", c -> {
                            c.expression(b.title());
                            c.expression(easyEntityQuery.queryable(BlogEntity.class)
                                    .where(x -> {
                                        x.id().eq("1");
                                        x.title().eq("2");
                                    })
                                    .selectColumn(x -> x.title().join(",")));
                        });
                        b.score().gt(BigDecimal.valueOf(1));
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND (FIND_IN_SET(t.`title`,(SELECT GROUP_CONCAT(t1.`title` SEPARATOR ?) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ? AND t1.`title` = ?)) OR t.`score` > ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),,(String),false(Boolean),1(String),2(String),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testFetchSub2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MySchoolStudent> list = easyEntityQuery.queryable(MySchoolStudent.class)
                .where(m -> m.name().like("123"))
                .fetchBy(b -> b.FETCHER.allFields()
                        ._concat(b.schoolClass().FETCHER.id().name())
                        ._concat(b.schoolStudentAddress().FETCHER.address()))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name`,t1.`id`,t1.`name`,t2.`address` FROM `my_school_student` t LEFT JOIN `my_school_class` t1 ON t1.`id` = t.`class_id` LEFT JOIN `school_student_address` t2 ON t2.`student_id` = t.`id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testFetchSub3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MySchoolStudent> list = easyEntityQuery.queryable(MySchoolStudent.class)
                .where(m -> m.name().like("123"))
                .fetchBy(b -> b.FETCHER.allFields()
                        ._concat(b.schoolClass().FETCHER.id().name())
                        ._concat(b.schoolStudentAddress().FETCHER.address()))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name`,t1.`id`,t1.`name`,t2.`address` FROM `my_school_student` t LEFT JOIN `my_school_class` t1 ON t1.`id` = t.`class_id` LEFT JOIN `school_student_address` t2 ON t2.`student_id` = t.`id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testLogicDeleteRecentlyTable() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(BlogEntity.class, (b, b2) -> b.id().eq(b2.id()))
                .tableLogicDelete(() -> false)
                .where((b1, b2) -> b1.title().like("123"))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_blog` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? AND t.`title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testLogicDeleteRecentlyTable1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .tableLogicDelete(() -> false)
                .leftJoin(BlogEntity.class, (b, b2) -> b.id().eq(b2.id()))
                .where((b1, b2) -> b1.title().like("123"))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn1() {
//        SQLProxyFunc.caseWhenBuilder()
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(b -> GroupKeys.TABLE1.of(b.id()))
                .select(b -> {
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    PropTypeColumn<Integer> integerPropTypeColumn = b.expression().sqlType("case {0} when {1} then 1 else 0 end",
                            c -> {
                                c.expression(b.group().score()).value(1);
                            }).setPropertyType(Integer.class);
                    blogEntityProxy.star().set(b.sum(integerPropTypeColumn));
                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUM(case t.`score` when ? then 1 else 0 end) AS `star` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(b -> GroupKeys.TABLE1.of(b.id()))
                .select(b -> {
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    blogEntityProxy.score().set(b.min(b.expression().sqlType("case {0} when {1} then 1 else 0 end",
                            c -> {
                                c.expression(b.group().score()).value(1);
                            }).setPropertyType(BigDecimal.class)));
                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MIN(case t.`score` when ? then 1 else 0 end) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.title().eq(
                            b.title().subString(1, 2)
                    );
                })
                .select(b -> {
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    ColumnFunctionComparableAnyChainExpression<BigDecimal> caseWhen = b.expression().caseWhen(() -> b.id().eq("123"))
                            .then(1)
                            .elseEnd("2").setPropertyType(BigDecimal.class);

                    blogEntityProxy.score().set(caseWhen);
                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`id` = ? THEN ? ELSE ? END) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND  t.`title` = SUBSTR(t.`title`,2,2)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.title().eq(
                            b.title().subString(1, 2)
                    );
                })
                .select(b -> {
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    ColumnFunctionComparableAnyChainExpression<BigDecimal> caseWhen = b.expression().caseWhen(() -> {
                        b.id().eq("123");
                    }).then(1).elseEnd("2").setPropertyType(BigDecimal.class);
                    blogEntityProxy.score().set(caseWhen);

                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`id` = ? THEN ? ELSE ? END) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND  t.`title` = SUBSTR(t.`title`,2,2)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    Expression expression = b.expression();
                    b.title().eq(
                            expression.caseWhen(() -> {
                                b.title().eq(b.id());
                            }).then(1).elseEnd("2")
                    );
                })
                .select(b -> {
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    ColumnFunctionComparableAnyChainExpression<BigDecimal> caseWhen = b.expression().caseWhen(() -> {
                        b.id().eq("123");
                    }).then(1).elseEnd("2").setPropertyType(BigDecimal.class);
                    blogEntityProxy.score().set(caseWhen);

                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`id` = ? THEN ? ELSE ? END) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND  t.`title` = (CASE WHEN t.`title` = t.`id` THEN ? ELSE ? END)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(String),false(Boolean),1(Integer),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn6() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    Expression expression = b.expression();
                    expression.caseWhen(() -> {
                        b.title().eq(b.id());
                    }).then(1).elseEnd("2").eq(b.title());
                })
                .select(b -> {
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    ColumnFunctionComparableAnyChainExpression<BigDecimal> caseWhen = b.expression().caseWhen(() -> {
                        b.id().eq("123");
                    }).then(1).elseEnd("2").setPropertyType(BigDecimal.class);
                    blogEntityProxy.score().set(caseWhen);

                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`id` = ? THEN ? ELSE ? END) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND (CASE WHEN t.`title` = t.`id` THEN ? ELSE ? END) = t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(String),false(Boolean),1(Integer),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn7() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    Expression expression = b.expression();
                    expression.caseWhen(() -> {
                        b.title().eq(b.id());
                    }).then(1).elseEnd("2").nullOrDefault("xx").eq(b.title().nullOrDefault("yy"));
                })
                .select(b -> {
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    ColumnFunctionComparableAnyChainExpression<BigDecimal> caseWhen = b.expression().caseWhen(() -> {
                        b.id().eq("123");
                    }).then(1).elseEnd("2").setPropertyType(BigDecimal.class);
                    blogEntityProxy.score().set(caseWhen);

                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`id` = ? THEN ? ELSE ? END) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND IFNULL((CASE WHEN t.`title` = t.`id` THEN ? ELSE ? END),?) = IFNULL(t.`title`,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(String),false(Boolean),1(Integer),2(String),xx(String),yy(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn8() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    Expression expression = b.expression();
                    b.title().nullOrDefault("yy").eq(
                            expression.caseWhen(() -> {
                                b.title().eq(b.id());
                            }).then(1).elseEnd("2").nullOrDefault("xx")
                    );
                })
                .select(b -> {
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    blogEntityProxy.score().set(
                            b.expression().caseWhen(() -> {
                                b.id().eq("123");
                            }).then(1).elseEnd("2")
                    );

                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`id` = ? THEN ? ELSE ? END) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND IFNULL(t.`title`,?) = IFNULL((CASE WHEN t.`title` = t.`id` THEN ? ELSE ? END),?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(String),false(Boolean),yy(String),1(Integer),2(String),xx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn9() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    Expression expression = b.expression();
                    b.title().nullOrDefault("yy").eq(
                            expression.caseWhen(() -> {
                                b.or(() -> {
                                    b.title().eq(b.id());
                                    b.title().like("123");
                                });
                            }).then(1).elseEnd("2").nullOrDefault("xx")
                    );
                })
                .select(b -> {
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    blogEntityProxy.score().set(
                            b.expression().caseWhen(() -> {
                                b.id().eq("123");
                            }).then(1).elseEnd("2")
                    );

                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`id` = ? THEN ? ELSE ? END) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND IFNULL(t.`title`,?) = IFNULL((CASE WHEN (t.`title` = t.`id` OR t.`title` LIKE ?) THEN ? ELSE ? END),?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(String),false(Boolean),yy(String),%123%(String),1(Integer),2(String),xx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void testSelectPropTypeColumn10() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(b -> GroupKeys.TABLE1.of(b.id()))
                .select(b -> {
                    Expression expression = b.expression();
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    blogEntityProxy.star().set(
                            expression.caseWhen(() -> b.groupTable().id().eq("123"))
                                    .then(1).elseEnd(0)
                                    .sum()
                    );
                    blogEntityProxy.score().set(b.min(b.expression().sqlType("case {0} when {1} then 1 else 0 end",
                            c -> {
                                c.expression(b.groupTable().score()).value(1);
                            }).setPropertyType(BigDecimal.class)));
                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUM((CASE WHEN t.`id` = ? THEN ? ELSE ? END)) AS `star`,MIN(case t.`score` when ? then 1 else 0 end) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),0(Integer),1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn11() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    Expression expression = b.expression();
                    b.title().nullOrDefault("yy").eq(
                            expression.caseWhen(() -> {
                                b.or(() -> {
                                    b.title().eq(b.id());
                                    b.title().like("123");
                                });
                            }).then(b.score().nullOrDefault(BigDecimal.valueOf(12))).elseEnd("2").nullOrDefault("xx")
                    );
                })
                .select(b -> {
                    Expression expression = b.expression();
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    blogEntityProxy.star().set(
                            expression.caseWhen(() -> b.id().eq("123"))
                                    .then(b.score()).elseEnd(0)
                                    .sum()
                    );
                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUM((CASE WHEN t.`id` = ? THEN t.`score` ELSE ? END)) AS `star` FROM `t_blog` t WHERE t.`deleted` = ? AND IFNULL(t.`title`,?) = IFNULL((CASE WHEN (t.`title` = t.`id` OR t.`title` LIKE ?) THEN IFNULL(t.`score`,?) ELSE ? END),?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),0(Integer),false(Boolean),yy(String),%123%(String),12(BigDecimal),2(String),xx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelectPropTypeColumn12() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {

                    Expression expression = b.expression();
                    b.title().nullOrDefault("yy").eq(
                            expression.caseWhen(() -> {
                                b.or(() -> {
                                    b.title().eq(b.id());
                                    b.title().like("123");
                                });
                            }).then(
                                    b.score().nullOrDefault(BigDecimal.valueOf(12))
                            ).elseEnd(
                                    b.score().nullOrDefault(BigDecimal.valueOf(13))
                            ).nullOrDefault("xx")
                    );
                })
                .select(b -> {
                    Expression expression = b.expression();
                    BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                    blogEntityProxy.star().set(
                            expression.caseWhen(() -> b.id().eq("123"))
                                    .then(b.score()).elseEnd(b.status().multiply(1))
                                    .sum()
                    );
                    return blogEntityProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUM((CASE WHEN t.`id` = ? THEN t.`score` ELSE (t.`status` * ?) END)) AS `star` FROM `t_blog` t WHERE t.`deleted` = ? AND IFNULL(t.`title`,?) = IFNULL((CASE WHEN (t.`title` = t.`id` OR t.`title` LIKE ?) THEN IFNULL(t.`score`,?) ELSE IFNULL(t.`score`,?) END),?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),false(Boolean),yy(String),%123%(String),12(BigDecimal),13(BigDecimal),xx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDoc1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> {
                    s.schoolStudents().any(stu -> {
                        stu.name().likeMatchLeft("金");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("金%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDoc2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> {
                    s.schoolStudents().none(stu -> {
                        stu.name().likeMatchLeft("金");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE NOT ( EXISTS (SELECT 1 FROM `school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ? LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("金%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDoc3() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> {
                    s.schoolStudents().any(stu -> {
                        stu.schoolStudentAddress().address().like("绍兴市");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_student` t1 LEFT JOIN `school_student_address` t2 ON t2.`student_id` = t1.`id` WHERE t1.`class_id` = t.`id` AND t2.`address` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%绍兴市%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDoc4() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> {

                    s.schoolStudents().where(stu -> {
                        stu.name().likeMatchLeft("金");
                    }).count().eq(5L);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE (SELECT COUNT(*) FROM `school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("金%(String),5(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void testDoc5(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> {
                    s.schoolTeachers().any(teacher->{
                        teacher.or(()->{
                            teacher.name().like("123");
                            teacher.name().like("456");
                        });
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_teacher` t1 WHERE EXISTS (SELECT 1 FROM `school_class_teacher` t2 WHERE t2.`teacher_id` = t1.`id` AND t2.`class_id` = t.`id` LIMIT 1) AND (t1.`name` LIKE ? OR t1.`name` LIKE ?) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),%456%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc6(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> {
                    s.schoolTeachers().any(teacher->{
                        teacher.or(()->{
                            teacher.name().like("123");
                            teacher.name().like("456");
                        });
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_teacher` t1 WHERE EXISTS (SELECT 1 FROM `school_class_teacher` t2 WHERE t2.`teacher_id` = t1.`id` AND t2.`class_id` = t.`id` LIMIT 1) AND (t1.`name` LIKE ? OR t1.`name` LIKE ?) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),%456%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc7(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(s -> {
                    s.users().any(u->{
                        u.idCard().like("123123");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND EXISTS (SELECT 1 FROM `easy-query-test`.`t_sys_user` t1 WHERE t1.`id` = t.`title` AND t1.`id_card` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%l46D3q3sDqT7sJprBhRRog==oP64xEZMF/9uOzl/BwZiHw==5iSDNEWwPdp8glBiaPFCWQ==%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc8(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(s -> {
                    s.users().any(u->{
                        u.idCard().like("123123");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND EXISTS (SELECT 1 FROM `easy-query-test`.`t_sys_user` t1 WHERE t1.`id` = t.`title` AND t1.`id_card` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%l46D3q3sDqT7sJprBhRRog==oP64xEZMF/9uOzl/BwZiHw==5iSDNEWwPdp8glBiaPFCWQ==%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc9(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.blogs().any(u->{
                        u.title().like("123123");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`title` = t.`id` AND t1.`title` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc10(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.blogs().disableLogicDelete().any(u->{
                        u.title().like("123123");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`title` = t.`id` AND t1.`title` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc11(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.myBlog().title().like("123123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t1.`id` = t.`id` WHERE t1.`title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc12(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().in(
                            easyEntityQuery.queryable(BlogEntity.class)
                                    .disableLogicDelete()
                                    .selectColumn(b1 -> b1.id())
                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` IN (SELECT t1.`id` FROM `t_blog` t1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc13(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .disableLogicDelete()
                .where(b -> {
                    b.id().in(
                            easyEntityQuery.queryable(BlogEntity.class)
                                    .selectColumn(b1 -> b1.id())
                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }
    @Test
    public void testDoc14(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

       List<String> ids = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22");
        List<List<String>> partition = EasyCollectionUtil.partition(ids, 10);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.or(() -> {
                        for (List<String> strings : partition) {
                            b.id().in(strings);
                        }
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`id` IN (?,?,?,?,?,?,?,?,?,?) OR `id` IN (?,?,?,?,?,?,?,?,?,?) OR `id` IN (?,?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String),21(String),22(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc15(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .innerJoin(SysUser.class, (b, s2) -> {
                    b.id().eq("1");
                    b.id().eq(s2.id());
                    b.or(() -> {
                        b.title().like("123");
                        s2.idCard().like("456");
                    });
                })
                .where((b1, s2) -> b1.title().like("1233"))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t INNER JOIN `easy-query-test`.`t_sys_user` t1 ON t.`id` = ? AND t.`id` = t1.`id` AND (t.`title` LIKE ? OR t1.`id_card` LIKE ?) WHERE t.`deleted` = ? AND t.`title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),%123%(String),%AdUseaKyj6EpwMyX7l1RQw==%(String),false(Boolean),%1233%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc16(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {


            List<RelationUser> users = easyEntityQuery.queryable(RelationUser.class)
                    .where(r -> r.historyBooks().any(book -> {
                        book.name().like("小学");
                    }))
                    .toList();
        }catch (Exception ex){

        }

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `relation_user` t WHERE EXISTS (SELECT 1 FROM `relation_book` t1 WHERE (t1.`user_id` = t.`id` AND t1.`create_time` <= ?) AND t1.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2022-01-01T00:00(LocalDateTime),%小学%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc17(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<RelationUser> users = easyEntityQuery.queryable(RelationUser.class)
                    .where(r -> r.teachers().any(book -> {
                        book.name().like("小学");
                    }))
                    .toList();
        }catch (Exception ex){

        }

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `relation_user` t WHERE EXISTS (SELECT 1 FROM `relation_teacher` t1 WHERE EXISTS (SELECT 1 FROM `relation_route` t2 WHERE t2.`second_id` = t1.`id` AND t2.`first_id` = t.`id` AND t2.`type` = ? LIMIT 1) AND t1.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),%小学%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
     public void testDraftInteger(){
        List<Draft2<BigDecimal, String>> list1 = easyEntityQuery.queryable(BlogEntity2.class)
                .leftJoin(SysUser.class,(b, s2) ->  b.id().eq(s2.id()))
                .select((b, s2) -> Select.DRAFT.of(b.star(), b.id())).toList();
        for (Draft2<BigDecimal, String> bigDecimalStringDraft2 : list1) {
            String value2 = bigDecimalStringDraft2.getValue2();
            BigDecimal value1 = bigDecimalStringDraft2.getValue1();
        }
    }
    @Test
     public void testDraftInteger1(){
        List<Draft2<BigDecimal, String>> list1 = easyEntityQuery.queryable(BlogEntity2.class)
                .leftJoin(SysUser.class,(b, s2) -> b.id().eq(s2.id()))
                .leftJoin(SysUser.class,(b, s2,s3) -> s3.id().eq(b.id()))
                .select((b, s2,s3) -> Select.DRAFT.of(b.star(), b.id())).toList();
        for (Draft2<BigDecimal, String> bigDecimalStringDraft2 : list1) {
            String value2 = bigDecimalStringDraft2.getValue2();
            BigDecimal value1 = bigDecimalStringDraft2.getValue1();
        }
//        List<SchoolStudent> list = easyEntityQuery.queryable(SchoolStudent.class)
//                .where(s -> {
//                    if (false) {
//                        s.schoolStudentAddress().address().like("123");
//                    }
//                }).toList();
//        List<SchoolStudent> list2 = easyEntityQuery.queryable(SchoolStudent.class)
//                .where(s -> {
//                    s.schoolStudentAddress().address().like("123");
//                }).toList();
    }
    @Test
    public void testDoc18(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    Expression expression = s.expression();
                    expression.concat(x->{
                        x.value(",").expression(s.idCard()).value(",");
                    }).like(",2,");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE CONCAT(?,`id_card`,?) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),,(String),%,2,%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc19(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    Expression expression = s.expression();
                    SQLConstantExpression constant = expression.constant();
                    expression.concat(
                            constant.valueOf(","),
                            s.idCard(),
                            constant.valueOf(",")
                    ).like(",2,");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE CONCAT(?,`id_card`,?) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),,(String),%,2,%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc20(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    Expression expression = s.expression();
                    SQLConstantExpression constant = expression.constant();
                    expression.concat(
                            constant.valueOf(1),
                            s.idCard().toNumber(Integer.class),
                            constant.valueOf(2)
                    ).like(",2,");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE CONCAT(?,CAST(`id_card` AS SIGNED),?) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),2(Integer),%,2,%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDoc21(){
        ClientQueryable<Map> select1 = easyQueryClient.queryable("t_topic")
                .leftJoin(easyQueryClient.queryable("t_topic1"), (t1, t2) -> t1.eq(t2, "id", "id"))
                .select(Map.class, (t1, t2) -> {
                    t1.column("id");
                    t1.column("name");
                    t2.column("age");
                });

        ClientQueryable<Map> select2 = easyQueryClient.queryable("t_topic")
                .leftJoin(easyQueryClient.queryable("t_topic1"), (t1, t2) -> t1.eq(t2, "id", "id"))
                .select(Map.class, (t1, t2) -> {
                    t1.column("id");
                    t1.column("name");
                    t2.column("age");
                });
        String sql = select1.leftJoin(select2, (t1, t2) -> t1.eq(t2, "id", "id"))
                .where((t1, t2) -> t1.eq("name", 2))
                .select(Map.class,(t1, t2) -> {
                    t2.column("name");
                    t1.column("id");
                })
                .toSQL();
        System.out.println(sql);

    }

}
