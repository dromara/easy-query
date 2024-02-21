package com.easy.query.test;

import com.easy.query.api.proxy.base.BigDecimalProxy;
import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                    result.put("xx",t2.createTime());
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
                    result.put("xx",t2.createTime());
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
                    result.put("xx",t2.createTime());
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
    public void testDraft23(){


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
                .select(b -> new BlogEntityProxy().adapter(r->{
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
                .select(b -> new BlogEntityProxy().adapter(r->{
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
                .selectColumn(b ->b.star().multiply(b.score()).add(b.score())).toList();
    }
    @Test
    public void testNum4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BigDecimal> list = easyEntityQuery.queryable(BlogEntity.class)
                .selectColumn(b ->b.star().multiply(b.score()).add(b.score())).toList();
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
                .selectColumn(b ->b.star().multiply(b.score()).add(100)).toList();
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
                .selectColumn(b ->b.star().multiply(b.score()).add(100)).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ((t.`star` * t.`score`) + ?) FROM `t_blog` t WHERE t.`deleted` = ? AND FLOOR(t.`score`) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("100(Integer),false(Boolean),0(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


}
