package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.base.TopicTestProxy;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * create time 2023/11/23 19:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest9 extends BaseTest {


    @Test
    public void selectCount1() {
        String sql = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, 123))
                .selectCount().toSQL();
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` WHERE `id` = ?", sql);
    }

    @Test
    public void selectCount2() {
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, 123))
                .selectCount().toSQL();
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?", sql);
    }

    @Test
    public void selectCount3() {
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, 123))
                .selectCount().toSQL();
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?", sql);
    }

    @Test
    public void selectThrow3() {
        ListenerContext listenerContext = new ListenerContext();
        Supplier<Exception> f = () -> {
            try {
                listenerContextManager.startListen(listenerContext);
                Topic topic = easyQuery.queryable(Topic.class)
                        .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                        .where(o -> o.eq(Topic::getId, UUID.randomUUID()))
                        .firstNotNull(() -> new MyAppException("asv"));
            } catch (Exception ex) {
                return ex;
            } finally {
                listenerContextManager.clear();
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof MyAppException);
        MyAppException myAppException = (MyAppException) exception;
        Assert.assertEquals("asv", myAppException.getMessage());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
    }

    @Test
    public void selectThrow4() {

        ListenerContext listenerContext = new ListenerContext();
        Supplier<Exception> f = () -> {
            try {
                listenerContextManager.startListen(listenerContext);
                Topic topic = easyQuery.queryable(Topic.class)
                        .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                        .where(o -> o.eq(Topic::getId, UUID.randomUUID()))
                        .singleNotNull(() -> new MyAppException("asv"));
            } catch (Exception ex) {
                return ex;
            } finally {
                listenerContextManager.clear();
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof MyAppException);
        MyAppException myAppException = (MyAppException) exception;
        Assert.assertEquals("asv", myAppException.getMessage());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
    }

    @Test
    public void selectThrow5() {
        ListenerContext listenerContext = new ListenerContext();
        Supplier<Exception> f = () -> {
            try {
                listenerContextManager.startListen(listenerContext);
                Topic topic = easyQuery.queryable(Topic.class)
                        .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                        .where(o -> o.eq(Topic::getId, UUID.randomUUID()))
                        .singleNotNull("1");
            } catch (Exception ex) {
                return ex;
            } finally {
                listenerContextManager.clear();
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySingleNotNullException);
    }

    @Test
    public void selectThrow6() {
        ListenerContext listenerContext = new ListenerContext();
        Supplier<Exception> f = () -> {
            try {
                listenerContextManager.startListen(listenerContext);
                Topic topic = easyQuery.queryable(Topic.class)
                        .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                        .where(o -> o.eq(Topic::getId, UUID.randomUUID()))
                        .firstNotNull("1");
            } catch (Exception ex) {
                return ex;
            } finally {
                listenerContextManager.clear();
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQueryFirstNotNullException);
    }

    @Test
    public void selectThrow7() {
        ListenerContext listenerContext = new ListenerContext();
        Supplier<Exception> f = () -> {
            try {
                listenerContextManager.startListen(listenerContext);
                Topic topic = easyQuery.queryable(Topic.class)
                        .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                        .singleOrNull();
            } catch (Exception ex) {
                return ex;
            } finally {
                listenerContextManager.clear();
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySingleMoreElementException);
    }


    @Test
    public void entityQuery1() {

        TopicTestProxy table = TopicTestProxy.createTable();
        BlogEntityProxy table1 = BlogEntityProxy.createTable();
//        TopicTestProxy.TopicTestSelector title = table.selector().id().title();
//        TopicTestProxy.TopicTestSelector blogEntityProxyTopicTestFetcher = table.selector().id().title().as(table1.createTime()).columns(table.id());
//        TopicProxy topicProxy = EntityQueryProxyManager.create(Topic.class);
//        TopicProxy topicProxy1 = EntityQueryProxyManager.create(Topic.class);
//        ValueCompany1Proxy table2 = ValueCompany1Proxy.createTable();
//        table2.FETCHER.address().name().id()

        List<Topic> list = entityQuery.queryable(Topic.class)
                .where(o -> {
                    o.id().eq("1");
                    o.title().like("xxx");
                })
                .where(o -> {
                    o.id().eq("1");
                    o.title().like("xxx");
                    o.createTime().ge(LocalDateTime.now());
                })
                .where(o -> {
                            o.id().eq("1");
                            o.title().like("xxx");
                            o.createTime().ge(LocalDateTime.now());
                        }
                ).toList();

        Topic topic = entityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (a, b) -> {
                    a.id().eq(b.id());
                })
                .where((a, b) -> {
                    a.title().eq("1");
                    b.createTime().ge(LocalDateTime.of(2021, 1, 1, 1, 1));
                })
                .orderBy((a, b) ->{
                    a.title().asc();
                    a.id().desc();
                })
                .firstOrNull();

        Topic topic2 = entityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (a, b) -> a.id().eq(b.id()))
                .where((a, b) -> {
                    a.title().eq("1");
                    b.createTime().ge(LocalDateTime.of(2021, 1, 1, 1, 1));
                })
                .orderBy((a, b) ->{
                    a.title().asc();
                })
                .select(o -> o.FETCHER.title().stars())
                .firstOrNull();
        List<Topic> list1 = entityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().eq("title");
                    o.id().eq("1");
                })
                .groupBy(o -> o.title())
                .select(Topic.class, (o, tr) -> Select.of(
                        o.title()
                        , o.id().count().as(tr.stars())
                ))
                .toList();
    }

    @Test
    public void testDsl1() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list3 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.stars().eq(123);
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `stars` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list3 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title");
                        o.id().eq("1");
                    })
                    .orderBy(o -> o.createTime().dateTimeFormat("yyyy-MM-dd HH:mm:ss").asc())
                    .select(Topic.class, (o, tr) -> Select.of(
                            o.title(),
                            o.id(),
                            o.createTime().dateTimeFormat("yyyy-MM-dd HH:mm:ss")
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic` t WHERE t.`title` = ? AND t.`id` = ? ORDER BY DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("title(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list3 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title");
                        o.id().eq("1");
                    })
                    .orderBy(o -> o.createTime().dateTimeFormat("yyyy-MM-dd HH:mm:ss").desc())
                    .select(Topic.class, (o, tr) -> Select.of(
                            o.title(),
                            o.id(),
                            o.createTime().dateTimeFormat("yyyy-MM-dd HH:mm:ss")
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic` t WHERE t.`title` = ? AND t.`id` = ? ORDER BY DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("title(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list3 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title");
                        o.id().eq("1");
                    })
                    .orderBy(o -> {
                        o.createTime().dateTimeFormat("yyyy-MM-dd HH:mm:ss").desc();
                        o.sqlNativeSegment("IFNULL({0},'') ASC",c->{
                            c.keepStyle().expression(o.stars());
                        });
                    })
                    .select(Topic.class, (o, tr) -> Select.of(
                            o.title(),
                            o.id(),
                            o.createTime().dateTimeFormat("yyyy-MM-dd HH:mm:ss")
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic` t WHERE t.`title` = ? AND t.`id` = ? ORDER BY DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') DESC,IFNULL(t.`stars`,'') ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("title(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDsl2() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01"))
                    .groupBy(o -> o.createTime().dateTimeFormat("yyyy/MM/dd"))
                    .select(Topic.class, (o, tr) -> Select.of(
                            o.id().count().as(tr.stars()),
                            o.createTime().dateTimeFormat("yyyy/MM/dd").as(tr.title())
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT COUNT(t.`id`) AS `stars`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `title` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? GROUP BY DATE_FORMAT(t.`create_time`,'%Y/%m/%d')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01"))
                    .groupBy(o -> o.createTime().dateTimeFormat("yyyy/MM/dd"))
                    .select(Topic.class, (o, tr) -> Select.of(
                            o.id().count().as(tr.stars()),
                            o.createTime().dateTimeFormat("yyyy/MM/dd").as(tr.title()),
                            Select.groupKeys(0).as(tr.id())
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT COUNT(t.`id`) AS `stars`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `title`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `id` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? GROUP BY DATE_FORMAT(t.`create_time`,'%Y/%m/%d')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDsl3() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01"))
                    .select(Topic.class, (o, tr) -> Select.of(
                            o.stars().nullDefault(0).as(tr.title()),
                            Select.sql("IFNULL({0},'')", c -> {
                                c.keepStyle();
                                c.expression(o.id());
                            }, tr.alias())
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT IFNULL(t.`stars`,?) AS `title`,IFNULL(t.`id`,'') AS `alias` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(Integer),2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01"))
                    .select(Topic.class, (o, tr) -> Select.of(
                            o.stars().nullDefault(0).as(tr.title()),
                            Select.sql("IFNULL({0},'')", c -> {
                                c.keepStyle();
                                c.expression(o.id());
                            }, tr.alias())
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT IFNULL(t.`stars`,?) AS `title`,IFNULL(t.`id`,'') AS `alias` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(Integer),2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01"))
                    .select(Topic.class, (o, tr) -> Select.of(
                            o.stars().nullEmpty().as(tr.title()),
                            Select.sql("IFNULL({0},'')", c -> {
                                c.keepStyle();
                                c.expression(o.id());
                            }, tr.alias())
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT IFNULL(t.`stars`,?) AS `title`,IFNULL(t.`id`,'') AS `alias` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("(String),2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDsl4() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.id().isEmpty();
                        o.title().isEmpty(false);
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.id().isBank();
                        o.title().isBank(false);
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '' OR LTRIM(`id`) = '')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.id().isNotEmpty();
                        o.title().isNotEmpty(false);
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.id().isNotBank();
                        o.title().isNotBank(false);
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '' AND LTRIM(`id`) <> '')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDsl5() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().le(o.createTime()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` <= `create_time`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().dateTimeFormat("yyyy/MM/dd").le(o._now()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE DATE_FORMAT(`create_time`,'%Y/%m/%d') <= NOW()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().le(o._now()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` <= NOW()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().le(o.createTime().nullDefault("")))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` <= IFNULL(`create_time`,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().le(o.createTime().nullDefault(LocalDateTime.of(2022, 1, 1, 1, 1)));
                        o.id().isNotBank();
                        o.id().nullDefault("").eq(o.title().nullDefault(c -> c.column(o.id())));
                        o.title().isEmpty();
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` <= IFNULL(`create_time`,?) AND (`id` IS NOT NULL AND `id` <> '' AND LTRIM(`id`) <> '') AND IFNULL(`id`,?) = IFNULL(`title`,`id`) AND (`title` IS NULL OR `title` = '')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2022-01-01T01:01(LocalDateTime),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().le(o.createTime().nullDefault(LocalDateTime.of(2022, 1, 1, 1, 1)));
                        o.id().nullDefault("1").isNull();
                        o.id().nullDefault("2").eq(o.title().nullDefault(c -> c.column(o.id())));
                        o.title().isEmpty();
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` <= IFNULL(`create_time`,?) AND IFNULL(`id`,?) IS NULL AND IFNULL(`id`,?) = IFNULL(`title`,`id`) AND (`title` IS NULL OR `title` = '')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2022-01-01T01:01(LocalDateTime),1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void dslTest6() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<String> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().le(o.createTime().nullDefault(LocalDateTime.of(2022, 1, 1, 1, 1)));
                        o.id().nullDefault("1").isNull();
                        o.id().nullDefault("2").eq(o.title().nullDefault(c -> c.column(o.id())));
                        o.title().isEmpty();
                    })
                    .selectProxy(StringProxy.createTable(), a -> a.title())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title` FROM `t_topic` t WHERE  t.`create_time` <= IFNULL(t.`create_time`,?) AND IFNULL(t.`id`,?) IS NULL AND IFNULL(t.`id`,?) = IFNULL(t.`title`,t.`id`) AND (t.`title` IS NULL OR t.`title` = '')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2022-01-01T01:01(LocalDateTime),1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01"))
                    .groupBy(o -> o.FETCHER.title())
                    .select(Topic.class, (o, tr) -> Select.of(
                            o.FETCHER.title(),
                            o.id().count().as(tr.stars())
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,COUNT(t.`id`) AS `stars` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = entityQuery.queryable(Topic.class)
                    .where(o -> {

                        o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01");
                        o.or(() -> {
                            o.stars().ne(1);
                            o.createTime().le(LocalDateTime.of(2024, 1, 1, 1, 1));
                            o.title().notLike("abc");
                        });
                        o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01");
                        o.id().nullDefault("yyyy/MM/dd").eq("xxx");
                        o.sqlNativeSegment("{0} != {1}",c->{
                            c.expression(o.stars()).expression(o.createTime());
                        });
                        o.or(()->{
                            o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01");
                            o.id().nullDefault("yyyy/MM/dd").eq("xxx");
                            o.sqlNativeSegment("{0} != {1}",c->{
                                c.expression(o.stars()).expression(o.createTime());
                            });
                        });
                        o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/02");
                        o.id().nullDefault("yyyy/MM/dd2").eq("xxx1");
                    })
                    .select(o -> o.FETCHER
                            .allFieldsExclude(o.id(), o.title())
                            .id().as(o.title())
                            .id())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `title`,t.`id` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? AND (t.`stars` <> ? OR t.`create_time` <= ? OR t.`title` NOT LIKE ?) AND DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? AND IFNULL(t.`id`,?) = ? AND t.`stars` != t.`create_time` AND (DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? OR IFNULL(t.`id`,?) = ? OR t.`stars` != t.`create_time`) AND DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? AND IFNULL(t.`id`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String),1(Integer),2024-01-01T01:01(LocalDateTime),%abc%(String),2023/01/01(String),yyyy/MM/dd(String),xxx(String),2023/01/01(String),yyyy/MM/dd(String),xxx(String),2023/01/02(String),yyyy/MM/dd2(String),xxx1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void dslTest7() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Map<String, Object>> abc = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01");
                    })
                    .selectProxy(MapProxy.createTable(), o -> o.FETCHER
                            .allFieldsExclude(o.id(), o.title())
                            .id().as("abc")
                            .id())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            MapProxy table = MapProxy.createTable();
            List<Map<String, Object>> list = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01");
                    })
                    .selectProxy(table).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT * FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<StringProxy, String> idQuery = entityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123"))
                    .selectProxy(StringProxy.createTable(), o -> o.id());
            List<Map<String, Object>> abc = entityQuery.queryable(Topic.class)
                    .where(o -> o.id().in(idQuery))
                    .selectProxy(MapProxy.createTable(), o -> o.FETCHER
                            .allFieldsExclude(o.id(), o.title())
                            .id().as("abc")
                            .id())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` FROM `t_topic` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<StringProxy, String> idQuery = entityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123"))
                    .selectProxy(StringProxy.createTable(), o -> o.id());
            List<Map<String, Object>> abc = entityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
                    .where(o -> o.id().in(idQuery))
                    .select(MapProxy.createTable(), (a, b) -> a.FETCHER
                            .allFieldsExclude(a.id(), a.title())
                            .id().as("abc")
                            .id())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (SELECT t2.`id` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<StringProxy, String> idQuery = entityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123"))
                    .selectProxy(StringProxy.createTable(), o -> o.id());
            List<Map<String, Object>> abc = entityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
                    .where(o -> o.id().in(idQuery))
                    .selectMerge(MapProxy.createTable(), a -> a.t1.FETCHER
                            .allFieldsExclude(a.t1.id(), a.t1.title())
                            .id().as("abc")
                            .id().concat(
                                    a.t1.id()
                                    , a.t1.createTime().dateTimeFormat("yyyy-MM-dd HH:mm:ss")
                            ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (SELECT t2.`id` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<StringProxy, String> idQuery = entityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123"))
                    .selectProxy(StringProxy.createTable(), o -> o.id());
            List<Map<String, Object>> abc = entityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
                    .where(o -> {
                        o.id().in(idQuery);
                    })
                    .selectMerge(MapProxy.createTable(), a -> a.t1.FETCHER
                            .allFieldsExclude(a.t1.id(), a.t1.title())
                            .id().as("abc")
                            .id().concat(
                                    a.t1.id()
                                    , a.t1.createTime().dateTimeFormat("yyyy-MM-dd HH:mm:ss")
                            ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (SELECT t2.`id` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void havingQueryTest1(){
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123"))
                    .groupBy(o -> o.id())
                    .having(o -> {
                        o.id().count().ne(1);
                        o.id().sum().ge(10);
                    })
                    .select(BlogEntity.class, (o, tr) -> {
                        return Select.of(o.id(),
                                o.id().count().as(tr.star()),
                                o.id().max().as(tr.title())
                        );
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `star`,MAX(t.`id`) AS `title` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id` HAVING COUNT(t.`id`) <> ? AND SUM(t.`id`) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),1(Integer),10(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
     public void testQuery5(){


         ListenerContext listenerContext = new ListenerContext();
         listenerContextManager.startListen(listenerContext);
         List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                 .where(o -> {

                     o.id().eq("1");
                     o.id().eq(o.createTime().dateTimeFormat("yyyy-MM-dd"));
                     o.createTime().dateTimeFormat("yyyy-MM-dd").eq("2023-01-02");
                     o.title().nullDefault("unknown").like("123");
                     o.title().nullDefault("unknown").likeMatchLeft("123");
                     o.title().nullDefault("unknown").likeMatchLeft(false,"123");
                     o.title().nullDefault("unknown").likeMatchRight("123");
                     o.title().nullDefault("unknown").likeMatchRight(false,"123");
                     o.star().nullDefault(1).ge(101);
                     o.star().nullDefault(4).gt(102);
                     o.star().nullDefault(3).le(103);
                     o.star().nullDefault(2).lt(104);
                     o.star().nullDefault(1).eq(105);
                     o.title().nullDefault("unknown").eq(o.content());
                     o.content().isNotBank();
                 })
                 .groupBy(o -> o.id())
                 .having(o -> {
                     o.id().count().ne(1);
                     o.id().sum().ge(10);
                 })
                 .select(BlogEntity.class, (o, tr) -> {
                     return Select.of(o.id(),
                             o.id().count().as(tr.star()),
                             o.id().max().as(tr.title())
                     );
                 }).toList();
         Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
         JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
         Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `star`,MAX(t.`id`) AS `title` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND  t.`id` = DATE_FORMAT(t.`create_time`,'%Y-%m-%d') AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') = ? AND IFNULL(t.`title`,?) LIKE ? AND IFNULL(t.`title`,?) LIKE ? AND IFNULL(t.`title`,?) LIKE ? AND IFNULL(t.`star`,?) >= ? AND IFNULL(t.`star`,?) > ? AND IFNULL(t.`star`,?) <= ? AND IFNULL(t.`star`,?) < ? AND IFNULL(t.`star`,?) = ? AND IFNULL(t.`title`,?) = t.`content` AND (t.`content` IS NOT NULL AND t.`content` <> '' AND LTRIM(t.`content`) <> '') GROUP BY t.`id` HAVING COUNT(t.`id`) <> ? AND SUM(t.`id`) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
         Assert.assertEquals("false(Boolean),1(String),2023-01-02(String),unknown(String),%123%(String),unknown(String),123%(String),unknown(String),123%(String),1(Integer),101(Integer),4(Integer),102(Integer),3(Integer),103(Integer),2(Integer),104(Integer),1(Integer),105(Integer),unknown(String),1(Integer),10(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
         listenerContextManager.clear();
     }
    @Test
     public void testQuery6(){

        EntityQueryable<StringProxy, String> sss = entityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.id().eq("sss");
                })
                .selectProxy(StringProxy.createTable(), o -> o.id());
        ListenerContext listenerContext = new ListenerContext();
         listenerContextManager.startListen(listenerContext);
         List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                 .where(o -> {

                     o.id().eq(sss);
                     o.title().nullDefault("unknown").in(sss);
                 })
                 .groupBy(o -> o.id())
                 .having(o -> {
                     o.id().count().ne(1);
                     o.id().sum().ge(10);
                 })
                 .select(BlogEntity.class, (o, tr) -> {
                     return Select.of(o.id(),
                             o.id().count().as(tr.star()),
                             o.id().max().as(tr.title())
                     );
                 }).toList();
         Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
         JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
         Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `star`,MAX(t.`id`) AS `title` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = (SELECT t3.`id` FROM `t_blog` t3 WHERE t3.`deleted` = ? AND t3.`id` = ?) AND IFNULL(t.`title`,?) IN (SELECT t3.`id` FROM `t_blog` t3 WHERE t3.`deleted` = ? AND t3.`id` = ?) GROUP BY t.`id` HAVING COUNT(t.`id`) <> ? AND SUM(t.`id`) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
         Assert.assertEquals("false(Boolean),false(Boolean),sss(String),unknown(String),false(Boolean),sss(String),1(Integer),10(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
         listenerContextManager.clear();
     }

}
