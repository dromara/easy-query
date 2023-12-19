package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyTypeUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.base.TopicTestProxy;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.h2.vo.QueryVO;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
                .orderBy((a, b) -> {
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
                .orderBy((a, b) -> {
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
                        o.sqlNativeSegment("IFNULL({0},'') ASC", c -> {
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
                    .select(StringProxy.createTable(), a -> a.title())
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
                        o.sqlNativeSegment("{0} != {1}", c -> {
                            c.expression(o.stars()).expression(o.createTime());
                        });
                        o.or(() -> {
                            o.createTime().dateTimeFormat("yyyy/MM/dd").eq("2023/01/01");
                            o.id().nullDefault("yyyy/MM/dd").eq("xxx");
                            o.sqlNativeSegment("{0} != {1}", c -> {
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
                    .select(MapProxy.createTable(), o -> o.FETCHER
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
                    .select(StringProxy.createTable(), o -> o.id());
            List<Map<String, Object>> abc = entityQuery.queryable(Topic.class)
                    .where(o -> o.id().in(idQuery))
                    .select(MapProxy.createTable(), o -> o.FETCHER
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
                    .select(StringProxy.createTable(), o -> o.id());
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
                    .select(StringProxy.createTable(), o -> o.id());
            List<Map<String, Object>> abc = entityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
                    .where(o -> o.id().in(idQuery))
                    .selectMerge(MapProxy.createTable(), a -> a.t1.FETCHER
                            .allFieldsExclude(a.t1.id(), a.t1.title())
                            .id().as("abc")
                            .id()._concat(
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
                    .select(StringProxy.createTable(), o -> o.id());
            List<Map<String, Object>> abc = entityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
                    .where(o -> {
                        o.id().in(idQuery);
                    })
                    .selectMerge(MapProxy.createTable(), a -> a.t1.FETCHER
                            .allFieldsExclude(a.t1.id(), a.t1.title())
                            .id().as("abc")
                            .id()._concat(
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
    public void havingQueryTest1() {
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
    public void testQuery5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    o.id().eq("1");
                    o.id().eq(o.createTime().dateTimeFormat("yyyy-MM-dd"));
                    o.createTime().dateTimeFormat("yyyy-MM-dd").eq("2023-01-02");
                    o.title().nullDefault("unknown").like("123");
                    o.title().nullDefault("unknown").likeMatchLeft("123");
                    o.title().nullDefault("unknown").likeMatchLeft(false, "123");
                    o.title().nullDefault("unknown").likeMatchRight("123");
                    o.title().nullDefault("unknown").likeMatchRight(false, "123");
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
        Assert.assertEquals("false(Boolean),1(String),2023-01-02(String),unknown(String),%123%(String),unknown(String),123%(String),unknown(String),%123(String),1(Integer),101(Integer),4(Integer),102(Integer),3(Integer),103(Integer),2(Integer),104(Integer),1(Integer),105(Integer),unknown(String),1(Integer),10(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery6() {

        EntityQueryable<StringProxy, String> sss = entityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.id().eq("sss");
                })
                .select(StringProxy.createTable(), o -> o.id());
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

    @Test
    public void testQuery7() {
        List<String> ids = Collections.emptyList();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    o.id().in(ids);
                    o.id().notIn(ids);
                })
                .select(o -> o.FETCHER.allFieldsExclude(o.title(), o.top())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top` FROM `t_blog` t WHERE t.`deleted` = ? AND 1 = 2 AND 1 = 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery8() {
        List<String> ids = Collections.emptyList();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    o.id().in(ids);
                    o.id().notIn(ids);
                    o.createTime().dateTimeFormat("yyyy-MM-dd").rangeClosed("2023-01-02", "2023-01-03");
                    o.createTime().dateTimeFormat("yyyy-MM-dd").rangeClosed(false, "2023-01-02", false, "2023-01-03");
                    o.createTime().dateTimeFormat("yyyy-MM-dd").rangeOpen("2023-01-04", "2023-01-06");
                    o.createTime().dateTimeFormat("yyyy-MM-dd").rangeOpen(false, "2023-01-04", false, "2023-01-06");
                    o.createTime().dateTimeFormat("yyyy-MM-dd").rangeOpenClosed("2023-01-07", "2023-01-08");
                    o.createTime().dateTimeFormat("yyyy-MM-dd").rangeOpenClosed(false, "2023-01-07", false, "2023-01-08");
                    o.createTime().dateTimeFormat("yyyy-MM-dd").rangeClosedOpen("2023-01-09", "2023-01-10");
                    o.createTime().dateTimeFormat("yyyy-MM-dd").rangeClosedOpen(false, "2023-01-09", false, "2023-01-10");
                    o.createTime().dateTimeFormat("yyyy-MM-dd").isNull();
                    o.createTime().dateTimeFormat("yyyy-MM-dd").isNull(false);
                    o.createTime().dateTimeFormat("yyyy-MM-dd").isNotNull();
                    o.createTime().dateTimeFormat("yyyy-MM-dd").isNotNull(false);
                    o.createTime().dateTimeFormat("yyyy-MM-dd").in(ids);
                    o.createTime().dateTimeFormat("yyyy-MM-dd").notIn(ids);
                })
                .select(o -> o.FETCHER.allFieldsExclude(o.title(), o.top())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top` FROM `t_blog` t WHERE t.`deleted` = ? AND 1 = 2 AND 1 = 1 AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') >= ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') <= ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') > ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') < ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') > ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') <= ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') >= ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') < ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') IS NULL AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') IS NOT NULL AND 1 = 2 AND 1 = 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2023-01-02(String),2023-01-03(String),2023-01-04(String),2023-01-06(String),2023-01-07(String),2023-01-08(String),2023-01-09(String),2023-01-10(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void testVO1() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<QueryVO> list = entityQuery.queryable(Topic.class)
                    //第一个join采用双参数,参数1表示第一张表Topic 参数2表示第二张表 BlogEntity
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    //第二个join采用三参数,参数1表示第一张表Topic 参数2表示第二张表 BlogEntity 第三个参数表示第三张表 SysUser
                    .leftJoin(SysUser.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("123"))//单个条件where参数为主表Topic
                    //支持单个参数或者全参数,全参数个数为主表+join表个数 链式写法期间可以通过then来切换操作表
                    .where((t, t1, t2) -> {
                        t.id().eq("123");
                        t1.title().like("456");
                        t2.createTime().eq(LocalDateTime.of(2021, 1, 1, 1, 1));
                    })
                    .select(QueryVO.class, (t, t1, t2, tr) -> {
                        return Select.of(
                                t.id(),
                                t1.title().as(tr.field1()),
                                t2.id().as(tr.field2())
                        );
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t1.`title` AS `field1`,t2.`id` AS `field2` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `easy-query-test`.`t_sys_user` t2 ON t.`id` = t2.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? AND t2.`create_time` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),123(String),%456%(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<QueryVO> list = entityQuery.queryable(Topic.class)
                    //第一个join采用双参数,参数1表示第一张表Topic 参数2表示第二张表 BlogEntity
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    //第二个join采用三参数,参数1表示第一张表Topic 参数2表示第二张表 BlogEntity 第三个参数表示第三张表 SysUser
                    .leftJoin(SysUser.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("123"))//单个条件where参数为主表Topic
                    //支持单个参数或者全参数,全参数个数为主表+join表个数 链式写法期间可以通过then来切换操作表
                    .where((t, t1, t2) -> {
                        t.id().eq("123");
                        t1.title().like("456");
                        t2.createTime().eq(LocalDateTime.of(2021, 1, 1, 1, 1));
                    })
                    .select(QueryVO.class, (t, t1, t2, tr) -> {
                        return Select.of(
                                //将第一张表的所有属性的列映射到vo的列名上,忽略掉title属性的映射
                                t.allFieldsExclude(t.title()),
                                t1.title().as(tr.field1()),//将第二张表的title字段映射到VO的field1字段上
                                t2.id().as(tr.field2())//将第三张表的id字段映射到VO的field2字段上
                        );
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t1.`title` AS `field1`,t2.`id` AS `field2` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `easy-query-test`.`t_sys_user` t2 ON t.`id` = t2.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? AND t2.`create_time` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),123(String),%456%(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.createTime().dateTimeFormat("yyyy-MM-dd").likeMatchLeft("2023");
                    })
                    .select(o -> {

                        SQLSelectAsExpression subQuery = Select.subQueryAs(() -> {
                            return entityQuery.queryable(BlogEntity.class)
                                    .where(x -> {
                                        x.id().eq(o.id());
                                    })
                                    .selectCount();
                        }, o.createTime());

                        return Select.of(
                                o.FETCHER.allFieldsExclude(o.title(), o.top()),
                                subQuery
                        );
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,(SELECT COUNT(*) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `create_time` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.createTime().dateTimeFormat("yyyy-MM-dd").likeMatchLeft("2023");
                    })
                    .select(o -> {

                        SQLSelectAsExpression subQuery = Select.subQueryAs(() -> {
                            return entityQuery.queryable(BlogEntity.class)
                                    .where(x -> {
                                        x.id().eq(o.id());
                                    })
                                    .selectCount(BigDecimal.class);
                        }, o.createTime());

                        return Select.of(
                                o.FETCHER.allFieldsExclude(o.title(), o.top()),
                                subQuery
                        );
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,(SELECT COUNT(*) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `create_time` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.createTime().dateTimeFormat("yyyy-MM-dd").likeMatchLeft("2023");
                        o.exists(() -> {
                            return entityQuery.queryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()));
                        });
                    })
                    .select(o -> {

                        SQLSelectAsExpression subQuery = Select.subQueryAs(() -> {
                            return entityQuery.queryable(BlogEntity.class)
                                    .where(x -> {
                                        x.id().eq(o.id());
                                    })
                                    .selectCount(BigDecimal.class);
                        }, o.createTime());

                        return Select.of(
                                o.FETCHER.allFieldsExclude(o.title(), o.top()),
                                subQuery
                        );
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,(SELECT COUNT(*) FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = t.`id`) AS `create_time` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? AND EXISTS (SELECT 1 FROM `t_topic` t1 WHERE t1.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

    }

    @Test
    public void testLike1() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            ArrayList<String> tenantIds = new ArrayList<>();
            ArrayList<String> roleIds = new ArrayList<>();
            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().in(tenantIds);
                        o.exists(() -> {
                            return entityQuery.queryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()))
                                    .where(x -> x.id().in(roleIds));
                        });
                    })
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                        t1.title().like("123");
                    })
                    .select(BlogEntity.class, (t, t1, tr) -> Select.of(
                            t.FETCHER.id().content().createTime(),
                            t1.stars()
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`content`,t.`create_time`,t2.`stars` FROM `t_blog` t LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` AND t2.`title` LIKE ? WHERE t.`deleted` = ? AND 1 = 2 AND EXISTS (SELECT 1 FROM `t_topic` t1 WHERE t1.`id` = t.`id` AND 1 = 2)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            ArrayList<String> tenantIds = new ArrayList<>();
            ArrayList<String> roleIds = new ArrayList<>();
            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().in(tenantIds);
                        o.createTime().dateTimeFormat("yyyy-MM-dd").likeMatchLeft("123");
                        o.createTime().dateTimeFormat("yyyy-MM-dd").likeMatchRight("123");
                        o.exists(() -> {
                            return entityQuery.queryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()))
                                    .where(x -> x.id().in(roleIds));
                        });
                    })
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                        t1.title().like("123");
                    })
                    .select(BlogEntity.class, (t, t1, tr) -> Select.of(
                            t.FETCHER.id().content().createTime(),
                            t1.stars()
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`content`,t.`create_time`,t2.`stars` FROM `t_blog` t LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` AND t2.`title` LIKE ? WHERE t.`deleted` = ? AND 1 = 2 AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? AND EXISTS (SELECT 1 FROM `t_topic` t1 WHERE t1.`id` = t.`id` AND 1 = 2)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String),false(Boolean),123%(String),%123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().eq("123");
                        o.createTime().dateTimeFormat("yyyy-MM-dd").likeMatchLeft("123");
                        o.createTime().dateTimeFormat("yyyy-MM-dd").likeMatchRight("123");
                    })
                    .where(o -> {
                        o.title().like("你好");
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? AND DATE_FORMAT(`create_time`,'%Y-%m-%d') LIKE ? AND DATE_FORMAT(`create_time`,'%Y-%m-%d') LIKE ? AND `title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),123%(String),%123(String),%你好%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test2() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().eq("123");
                    }).groupBy(o -> o.id())
                    .having(o -> {
                        o.id().max().like("1");
                        o.id().max().like(false, "2");
                        o.id().max().likeMatchLeft("3");
                        o.id().max().likeMatchLeft(false, "4");
                        o.id().max().likeMatchRight("5");
                        o.id().max().likeMatchRight(false, "6");


                        o.id().max().notLike("1");
                        o.id().max().notLike(false, "2");
                        o.id().max().notLikeMatchLeft("3");
                        o.id().max().notLikeMatchLeft(false, "4");
                        o.id().max().notLikeMatchRight("5");
                        o.id().max().notLikeMatchRight(false, "6");
                    })
                    .select(BlogEntity.class, (t, tr) -> {
                        return Select.of(
                                t.id()
                        );
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id` HAVING MAX(t.`id`) LIKE ? AND MAX(t.`id`) LIKE ? AND MAX(t.`id`) LIKE ? AND MAX(t.`id`) NOT LIKE ? AND MAX(t.`id`) NOT LIKE ? AND MAX(t.`id`) NOT LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),%1%(String),3%(String),%5(String),%1%(String),3%(String),%5(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test3() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().eq("123");
                    }).groupBy(o -> o.id())
                    .having(o -> {
                        o.id().max().eq("1");
                        o.id().max().eq(false, "2");
                        o.id().max().ge("3");
                        o.id().max().ge(false, "4");
                        o.id().max().gt("5");
                        o.id().max().gt(false, "6");


                        o.id().max().ne("1");
                        o.id().max().ne(false, "2");
                        o.id().max().le("3");
                        o.id().max().le(false, "4");
                        o.id().max().lt("5");
                        o.id().max().lt(false, "6");
                    })
                    .select(BlogEntity.class, (t, tr) -> {
                        return Select.of(
                                t.id()
                        );
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id` HAVING MAX(t.`id`) = ? AND MAX(t.`id`) >= ? AND MAX(t.`id`) > ? AND MAX(t.`id`) <> ? AND MAX(t.`id`) <= ? AND MAX(t.`id`) < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),1(String),3(String),5(String),1(String),3(String),5(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test4() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().eq("123");
                    }).groupBy(o -> o.id())
                    .having(o -> {
                        o.id().max().eq(o.id().min());
                        o.id().max().eq(false, o.id().min());
                        o.id().max().ge(o.id().min());
                        o.id().max().ge(false, o.id().min());
                        o.id().max().gt(o.id().min());
                        o.id().max().gt(false, o.id().min());


                        o.id().max().ne(o.id().min());
                        o.id().max().ne(false, o.id().min());
                        o.id().max().le(o.id().min());
                        o.id().max().le(false, "4");
                        o.id().max().lt(o.id().min());
                        o.id().max().lt(false, o.id().min());
                    })
                    .select(BlogEntity.class, (t, tr) -> {
                        return Select.of(
                                t.id()
                        );
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id` HAVING MAX(t.`id`) = MIN(t.`id`) AND MAX(t.`id`) >= MIN(t.`id`) AND MAX(t.`id`) > MIN(t.`id`) AND MAX(t.`id`) <> MIN(t.`id`) AND MAX(t.`id`) <= MIN(t.`id`) AND MAX(t.`id`) < MIN(t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> page = entityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupBy((t, t1) -> t1.id())
                .select(BlogEntity.class, (t, t1, tr) -> Select.of(t1.id(), t1.score().sum().as(tr.score())))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id` HAVING MAX(t.`id`) = MIN(t.`id`) AND MAX(t.`id`) >= MIN(t.`id`) AND MAX(t.`id`) > MIN(t.`id`) AND MAX(t.`id`) <> MIN(t.`id`) AND MAX(t.`id`) <= MIN(t.`id`) AND MAX(t.`id`) < MIN(t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void test6() {

        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Class<Draft1<String>> typeClass = EasyTypeUtil.cast(Draft1.class);
            List<Draft1<String>> list = entityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> t.id())
                    .selectDraft(t -> Select.draft(t.id()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1` FROM `t_topic` t GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Class<Draft1<String>> typeClass = EasyTypeUtil.cast(Draft1.class);
            List<Draft1<String>> list = entityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> t.id())
                    .selectDraft(t -> Select.draft(t.id()))
                    .where(o -> o.value1().eq("123"))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t1.`value1` AS `value1` FROM (SELECT t.`id` AS `value1` FROM `t_topic` t GROUP BY t.`id`) t1 WHERE t1.`value1` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft2<String, Long>> list = entityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> t.id())
                    .selectDraft(t -> Select.draft(
                            t.id(),
                            t.id().count().castType(Long.class)
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,COUNT(t.`id`) AS `value2` FROM `t_topic` t GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Long, BigDecimal>> list = entityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> t.id())
                    .selectDraft(t -> Select.draft(t.id(), t.id().count(), t.stars().sum()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,COUNT(t.`id`) AS `value2`,SUM(t.`stars`) AS `value3` FROM `t_topic` t GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            BigDecimal value3 = list.get(0).getValue3();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft2<String, String>> list = entityQuery
                    .queryable(ValueCompany.class)
                    .selectDraft(t -> Select.draft(t.id(), t.address().province()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`province` AS `value2` FROM `my_company` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft2<String, LocalDateTime>> list = entityQuery
                    .queryable(BlogEntity.class)
                    .selectDraft(t -> Select.draft(t.id(), t.createTime()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`create_time` AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            LocalDateTime value2 = list.get(0).getValue2();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, LocalDateTime, String>> list = entityQuery
                    .queryable(BlogEntity.class)
                    .selectDraft(t -> Select.draft(t.id(),
                            t.createTime(),
                            Select.draftSQL("1").castType(String.class)
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`create_time` AS `value2`,1 AS `value3` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            LocalDateTime value2 = list.get(0).getValue2();
            String value3 = list.get(0).getValue3();
            Assert.assertEquals("1",value3);
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, LocalDateTime, String>> list = entityQuery
                    .queryable(BlogEntity.class)
                    .selectDraft(t -> Select.draft(t.id(),
                            t.createTime(),
                            Select.draftSQL("IFNULL({0},'1')",c->c.keepStyle().expression(t.title())).castType(String.class)
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`create_time` AS `value2`,IFNULL(t.`title`,'1') AS `value3` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            LocalDateTime value2 = list.get(0).getValue2();
            String value3 = list.get(0).getValue3();
            Assert.assertEquals("title0",value3);
        }
    }
}
