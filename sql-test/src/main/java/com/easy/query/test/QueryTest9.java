package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.grouping.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyTypeUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.base.TopicTestProxy;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.h2.vo.QueryVO;
import com.easy.query.test.h2.vo.proxy.QueryVOProxy;
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

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
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

        Topic topic = easyEntityQuery.queryable(Topic.class)
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

        Topic topic2 = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (a, b) -> a.id().eq(b.id()))
                .where((a, b) -> {
                    a.title().eq("1");
                    b.createTime().ge(LocalDateTime.of(2021, 1, 1, 1, 1));
                })
                .orderBy((a, b) -> {
                    a.title().asc();
                })
                .fetcher(o -> o.FETCHER.title().stars())
                .firstOrNull();
        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().eq("title");
                    o.id().eq("1");
                })
                .groupBy(o -> GroupKeys.of( o.title()))
                .select(o -> new TopicProxy() {{
                    title().set(o.key1());
                    stars().set(o.intCount(o.group().id()));
                }})
                .toList();

    }

    @Test
    public void testDsl1() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list3 = easyEntityQuery.queryable(Topic.class)
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
            List<Topic> list3 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title");
                        o.id().eq("1");
                    })
                    .orderBy(o -> o.createTime().format("yyyy-MM-dd HH:mm:ss").asc())
                    .select(o -> new TopicProxy() {{
                        selectColumns(o.FETCHER.title().id(), o.createTime().format("yyyy-MM-dd HH:mm:ss"));
                    }})
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
            List<Topic> list3 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title");
                        o.id().eq("1");
                    })
                    .orderBy(o -> o.createTime().format("yyyy-MM-dd HH:mm:ss").desc())
                    .select(o -> new TopicProxy() {{
                        selectColumns(o.FETCHER.title().id(), o.createTime().format("yyyy-MM-dd HH:mm:ss"));
                    }})
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
            List<Topic> list3 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title");
                        o.id().eq("1");
                    })
                    .orderBy(o -> {
                        o.createTime().format("yyyy-MM-dd HH:mm:ss").desc();
                        o.executeSQL("IFNULL({0},'') ASC", c -> {
                            c.keepStyle().expression(o.stars());
                        });
                    })
                    .select(o -> new TopicProxy() {{
                        selectColumns(o.FETCHER.title().id(), o.createTime().format("yyyy-MM-dd HH:mm:ss"));
                    }})
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                    .groupByExpression(o -> o.createTime().format("yyyy/MM/dd"))
                    .select(o -> new TopicProxy() {{
                        stars().set(o.id().intCount());
                        title().set(o.createTime().format("yyyy/MM/dd"));
                    }})
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                    .groupByExpression(o -> o.createTime().format("yyyy/MM/dd"))
                    .select(o -> new TopicProxy() {{
                        stars().set(o.id().intCount());
                        title().set(o.createTime().format("yyyy/MM/dd"));
//                        id().set(o.createTime().format("yyyy/MM/dd"));
                        id().setExpression(o.groupKeys(0));
                    }})
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT COUNT(t.`id`) AS `stars`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `title`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `id` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? GROUP BY DATE_FORMAT(t.`create_time`,'%Y/%m/%d')", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("SELECT COUNT(t.`id`) AS `stars`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `title`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `id`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `id` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? GROUP BY DATE_FORMAT(t.`create_time`,'%Y/%m/%d')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDsl3() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                    .select(o -> new TopicProxy() {{
                        title().set(o.stars().nullDefault(0).toStr());
                        alias().setSQL("IFNULL({0},'')", c -> {
                            c.keepStyle();
                            c.expression(o.id());
                        });
//                        fetchSQLNativeSegment("IFNULL({0},'')", c -> {
//                            c.keepStyle();
//                            c.expression(o.id());
//                        }, alias());
                    }})
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT CAST(IFNULL(t.`stars`,?) AS CHAR) AS `title`,IFNULL(t.`id`,'') AS `alias` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(Integer),2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                    .select(o -> new TopicProxy() {{
                        title().set(o.stars().nullDefault(0).toStr());
                        alias().setSQL("IFNULL({0},'')", c -> {
                            c.keepStyle();
                            c.expression(o.id());
                        });
                    }})
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT CAST(IFNULL(t.`stars`,?) AS CHAR) AS `title`,IFNULL(t.`id`,'') AS `alias` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(Integer),2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                    .select(o -> new TopicProxy() {{
                        title().set(o.stars().nullDefault(0).toStr());
                        alias().setSQL("IFNULL({0},'')", c -> {
                            c.keepStyle();
                            c.expression(o.id());
                        });
                    }})
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT CAST(IFNULL(t.`stars`,?) AS CHAR) AS `title`,IFNULL(t.`id`,'') AS `alias` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(Integer),2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDsl4() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd").le(o._now()))
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
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

            List<String> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().le(o.createTime().nullDefault(LocalDateTime.of(2022, 1, 1, 1, 1)));
                        o.id().nullDefault("1").isNull();
                        o.id().nullDefault("2").eq(o.title().nullDefault(c -> c.column(o.id())));
                        o.title().isEmpty();
                    })
                    .select(o -> new StringProxy(o.title()))
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                    .groupByExpression(o -> o.FETCHER.title())
                    .select(o -> new TopicProxy() {{
                        selectColumns(o.title());
                        stars().set(o.id().intCount());
                    }})
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

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {

                        o.createTime().format("yyyy/MM/dd").eq("2023/01/01");
                        o.or(() -> {
                            o.stars().ne(1);
                            o.createTime().le(LocalDateTime.of(2024, 1, 1, 1, 1));
                            o.title().notLike("abc");
                        });
                        o.createTime().format("yyyy/MM/dd").eq("2023/01/01");
                        o.id().nullDefault("yyyy/MM/dd").eq("xxx");
                        o.executeSQL("{0} != {1}", c -> {
                            c.expression(o.stars()).expression(o.createTime());
                        });
                        o.or(() -> {
                            o.createTime().format("yyyy/MM/dd").eq("2023/01/01");
                            o.id().nullDefault("yyyy/MM/dd").eq("xxx");
                            o.executeSQL("{0} != {1}", c -> {
                                c.expression(o.stars()).expression(o.createTime());
                            });
                        });

                        o.createTime().format("yyyy/MM/dd").eq("2023/01/02");
                        o.id().nullDefault("yyyy/MM/dd2").eq("xxx1");
                    })
                    .fetcher(o -> o.FETCHER
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

            List<Map<String, Object>> abc = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().format("yyyy/MM/dd").eq("2023/01/01");
                    })
                    .select(o -> new MapProxy() {{
                        selectColumns(o.allFieldsExclude(o.id(), o.title()));
                        put("abc", o.id());
                        selectColumns(o.id());
                    }})
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
            List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().format("yyyy/MM/dd").eq("2023/01/01");
                    }).select(o -> new MapProxy()).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT * FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<StringProxy, String> idQuery = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123"))
                    .select(o -> new StringProxy(o.id()));
            List<Map<String, Object>> abc = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().in(idQuery))
                    .select(o -> new MapProxy() {{
                        selectColumns(o.allFieldsExclude(o.id(), o.title()));
                        put("abc", o.id());
                        selectColumns(o.id());
                    }})
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` FROM `t_topic` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//            EntityQueryable<StringProxy, String> idQuery = entityQuery.queryable(BlogEntity.class)
//                    .where(o -> o.id().eq("123"))
//                    .selectAs(StringProxy.createTable(), o -> o.id());
//            List<Map<String, Object>> abc = entityQuery.queryable(Topic.class)
//                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
//                    .where(o -> o.id().in(idQuery))
//                    .select((a,b)->new MapProxy(){{
//                        _select(a.allFieldsExclude(a.id(), a.title()));
////                        put("abc",a.stars());
//                    }})
////                    .selectAs(MapProxy.createTable(), (a, b) -> a.FETCHER
////                            .allFieldsExclude(a.id(), a.title())
////                            .id().as("abc")
////                            .id())
//                    .toList();
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (SELECT t2.`id` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("false(Boolean),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<StringProxy, String> idQuery = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123"))
                    .select(o -> new StringProxy(o.id()));
            List<Map<String, Object>> abc = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
                    .where(o -> o.id().in(idQuery))
                    .select(a -> new MapProxy() {{
                        selectColumns(a.allFieldsExclude(a.id(), a.title()));
                        put("abc", a.id());
                        put("id", a.id());
                        put("efg", a.createTime().format("yyyy-MM-dd HH:mm:ss"));
                    }})
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` AS `id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') AS `efg` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (SELECT t2.`id` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<StringProxy, String> idQuery = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123"))
                    .select(o -> new StringProxy(o.id()));
            List<Map<String, Object>> abc = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
                    .where(o -> {
                        o.id().in(idQuery);
                    })
                    .select(a -> new MapProxy() {{
                        selectColumns(a.allFieldsExclude(a.id(), a.title()));
                        put("abc", a.id());
                        put("id", a.id());
                        put("efg", a.createTime().format("yyyy-MM-dd HH:mm:ss"));
                    }})
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` AS `id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') AS `efg` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (SELECT t2.`id` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void havingQueryTest1() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123"))
                    .groupByExpression(o -> o.id())
                    .having(o -> {
                        o.id().count().ne(1);
                        o.star().sum().ge(10);
                    })
                    .select(o -> new BlogEntityProxy() {{
                        id().set(o.id());
                        star().set(o.id().count().setPropertyType(Integer.class));
//                        star().set(o.id().intCount());
                        title().set(o.id().max());
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`id`) AS `star`,MAX(t.`id`) AS `title` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id` HAVING COUNT(t.`id`) <> ? AND SUM(t.`star`) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),1(Integer),10(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testQuery5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    o.id().eq("1");
                    o.id().eq(o.createTime().format("yyyy-MM-dd"));
                    o.createTime().format("yyyy-MM-dd").eq("2023-01-02");
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
                .groupByExpression(o -> o.id())
                .having(o -> {
                    o.id().count().ne(1);
                    o.star().sum().ge(10);
                }).select(o -> new BlogEntityProxy() {{
                    id().set(o.id());
                    star().set(o.id().intCount());
                    title().set(o.id().max());
                }}).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`id`) AS `star`,MAX(t.`id`) AS `title` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND  t.`id` = DATE_FORMAT(t.`create_time`,'%Y-%m-%d') AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') = ? AND IFNULL(t.`title`,?) LIKE ? AND IFNULL(t.`title`,?) LIKE ? AND IFNULL(t.`title`,?) LIKE ? AND IFNULL(t.`star`,?) >= ? AND IFNULL(t.`star`,?) > ? AND IFNULL(t.`star`,?) <= ? AND IFNULL(t.`star`,?) < ? AND IFNULL(t.`star`,?) = ? AND IFNULL(t.`title`,?) = t.`content` AND (t.`content` IS NOT NULL AND t.`content` <> '' AND LTRIM(t.`content`) <> '') GROUP BY t.`id` HAVING COUNT(t.`id`) <> ? AND SUM(t.`star`) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String),2023-01-02(String),unknown(String),%123%(String),unknown(String),123%(String),unknown(String),%123(String),1(Integer),101(Integer),4(Integer),102(Integer),3(Integer),103(Integer),2(Integer),104(Integer),1(Integer),105(Integer),unknown(String),1(Integer),10(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery6() {

        EntityQueryable<StringProxy, String> sss = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.id().eq("sss");
                })
                .select(o -> new StringProxy(o.id()));
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    o.id().eq(sss);
                    o.title().nullDefault("unknown").in(sss);
                })
                .groupByExpression(o -> o.id())
                .having(o -> {
                    o.id().count().ne(1);
                    o.star().sum().ge(10);
                })
                .select(o -> new BlogEntityProxy() {{
                    selectColumns(o.id()
                            , o.id().count().as(star())
                            , o.id().max().as(title())
                    );
                }}).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `star`,MAX(t.`id`) AS `title` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = (SELECT t3.`id` FROM `t_blog` t3 WHERE t3.`deleted` = ? AND t3.`id` = ?) AND IFNULL(t.`title`,?) IN (SELECT t3.`id` FROM `t_blog` t3 WHERE t3.`deleted` = ? AND t3.`id` = ?) GROUP BY t.`id` HAVING COUNT(t.`id`) <> ? AND SUM(t.`star`) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),false(Boolean),sss(String),unknown(String),false(Boolean),sss(String),1(Integer),10(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery7() {
        List<String> ids = Collections.emptyList();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    o.id().in(ids);
                    o.id().notIn(ids);
                })
                .fetcher(o -> o.FETCHER.allFieldsExclude(o.title(), o.top())).toList();
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
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    o.id().in(ids);
                    o.id().notIn(ids);
                    o.createTime().format("yyyy-MM-dd").rangeClosed("2023-01-02", "2023-01-03");
                    o.createTime().format("yyyy-MM-dd").rangeClosed(false, "2023-01-02", false, "2023-01-03");
                    o.createTime().format("yyyy-MM-dd").rangeOpen("2023-01-04", "2023-01-06");
                    o.createTime().format("yyyy-MM-dd").rangeOpen(false, "2023-01-04", false, "2023-01-06");
                    o.createTime().format("yyyy-MM-dd").rangeOpenClosed("2023-01-07", "2023-01-08");
                    o.createTime().format("yyyy-MM-dd").rangeOpenClosed(false, "2023-01-07", false, "2023-01-08");
                    o.createTime().format("yyyy-MM-dd").rangeClosedOpen("2023-01-09", "2023-01-10");
                    o.createTime().format("yyyy-MM-dd").rangeClosedOpen(false, "2023-01-09", false, "2023-01-10");
                    o.createTime().format("yyyy-MM-dd").isNull();
                    o.createTime().format("yyyy-MM-dd").isNull(false);
                    o.createTime().format("yyyy-MM-dd").isNotNull();
                    o.createTime().format("yyyy-MM-dd").isNotNull(false);
                    o.createTime().format("yyyy-MM-dd").in(ids);
                    o.createTime().format("yyyy-MM-dd").notIn(ids);
                })
                .fetcher(o -> o.FETCHER.allFieldsExclude(o.title(), o.top())).toList();
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
            List<QueryVO> list = easyEntityQuery.queryable(Topic.class)
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
                    .select((t, t1, t2) -> new QueryVOProxy() {{
                        selectColumns(
                                t.id(),
                                t1.title().as(field1()),
                                t2.id().as(field2())
                        );
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t1.`title` AS `field1`,t2.`id` AS `field2` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `easy-query-test`.`t_sys_user` t2 ON t.`id` = t2.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? AND t2.`create_time` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),123(String),%456%(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<QueryVO> list = easyEntityQuery.queryable(Topic.class)
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
                    .select((t, t1, t2) -> new QueryVOProxy() {{
                        selectColumns(
                                t.allFieldsExclude(t.title()),
                                t1.title().as(field1()),
                                t2.id().as(field2())
                        );
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t1.`title` AS `field1`,t2.`id` AS `field2` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `easy-query-test`.`t_sys_user` t2 ON t.`id` = t2.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? AND t2.`create_time` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),123(String),%456%(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.createTime().format("yyyy-MM-dd").likeMatchLeft("2023");
                    })
                    .select(o -> new BlogEntityProxy() {{
                        selectColumns(o.allFieldsExclude(o.title(), o.top()));
                        star().setSubQuery(
                                easyEntityQuery.queryable(BlogEntity.class)
                                        .where(x -> {
                                            x.id().eq(o.id());
                                        })
                                        .selectCount(Integer.class)
                        );
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,(SELECT COUNT(*) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `star` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.createTime().format("yyyy-MM-dd").likeMatchLeft("2023");
                    })
                    .select(o -> new BlogEntityProxy() {{
                        selectColumns(o.allFieldsExclude(o.title(), o.top()));
                        score().setSubQuery(easyEntityQuery.queryable(BlogEntity.class)
                                .where(x -> {
                                    x.id().eq(o.id());
                                })
                                .selectCount(BigDecimal.class));
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,(SELECT COUNT(*) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.createTime().format("yyyy-MM-dd").likeMatchLeft("2023");
                        o.exists(() -> {
                            return easyEntityQuery.queryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()));
                        });
                    })
                    .select(o -> new BlogEntityProxy() {{


                        Query<BigDecimal> subQuery = easyEntityQuery.queryable(BlogEntity.class)
                                .where(x -> {
                                    x.id().eq(o.id());
                                })
                                .selectCount(BigDecimal.class);

                        selectColumns(o.allFieldsExclude(o.title(), o.top()));
                        score().setSubQuery(subQuery);
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,(SELECT COUNT(*) FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = t.`id`) AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? AND EXISTS (SELECT 1 FROM `t_topic` t1 WHERE t1.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
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
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().in(tenantIds);
                        o.exists(() -> {
                            return easyEntityQuery.queryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()))
                                    .where(x -> x.id().in(roleIds));
                        });
                    })
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                        t1.title().like("123");
                    })
                    .select((t, t1) -> new BlogEntityProxy() {{
                        selectColumns(
                                t.FETCHER.id().content().createTime(),
                                t1.stars()
                        );
                    }})
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
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().in(tenantIds);
                        o.createTime().format("yyyy-MM-dd").likeMatchLeft("123");
                        o.createTime().format("yyyy-MM-dd").likeMatchRight("123");
                        o.exists(() -> {
                            return easyEntityQuery.queryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()))
                                    .where(x -> x.id().in(roleIds));
                        });
                    })
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                        t1.title().like("123");
                    })
                    .select((t, t1) -> new BlogEntityProxy() {{
                        selectColumns(
                                t.FETCHER.id().content().createTime(),
                                t1.stars()
                        );
                    }})
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


            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().eq("123");
                        o.createTime().format("yyyy-MM-dd").likeMatchLeft("123");
                        o.createTime().format("yyyy-MM-dd").likeMatchRight("123");
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


            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().eq("123");
                    }).groupByExpression(o -> o.id())
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
                    .select(o -> new BlogEntityProxy() {{
                        selectColumns(o.id());
                    }})
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


            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().eq("123");
                    }).groupByExpression(o -> o.id())
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
                    .select(o -> new BlogEntityProxy() {{
                        selectColumns(o.id());
                    }})
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


            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.id().eq("123");
                    }).groupByExpression(o -> o.id())
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
                    .select(o -> new BlogEntityProxy() {{
                        selectColumns(o.id());
                    }})
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
        List<BlogEntity> page = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> t1.title().isNotNull())
                .groupByExpression((t, t1) -> t1.id())
                .select((t, t1) -> new BlogEntityProxy() {{
                    selectColumns(t1.id());
                    score().set(t1.score().sum());
                }})
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void test6() {

        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Class<Draft1<String>> typeClass = EasyTypeUtil.cast(Draft1.class);
            List<Draft1<String>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupByExpression(t -> t.id())
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
            List<Draft1<String>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupByExpression(t -> t.id())
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
            List<Draft2<String, Long>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupByExpression(t -> t.id())
                    .selectDraft(t -> Select.draft(
                            t.id(),
                            t.id().count().setPropertyType(Long.class)
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
            List<Draft3<String, Long, BigDecimal>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupByExpression(t -> t.id())
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
            List<Draft2<String, String>> list = easyEntityQuery
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
            List<Draft2<String, LocalDateTime>> list = easyEntityQuery
                    .queryable(BlogEntity.class)
                    .selectDraft(t -> Select.draft(t.id(), t.createTime()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`create_time` AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            LocalDateTime value2 = list.get(0).getValue2();
            System.out.println(value2);
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, LocalDateTime, String>> list = easyEntityQuery
                    .queryable(BlogEntity.class)
                    .selectDraft(t -> Select.draft(t.id(),
                            t.createTime(),
                            Select.draftSQL("1").setPropertyType(String.class)
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
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, LocalDateTime, String>> list = easyEntityQuery
                    .queryable(BlogEntity.class)
                    .selectDraft(t -> Select.draft(t.id(),
                            t.createTime(),
                            Select.draftSQL("IFNULL({0},'1')", c -> c.keepStyle().expression(t.title())).setPropertyType(String.class)
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
    public void testDraft9() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<Integer, LocalDateTime, String>> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> {
                    t.id().eq(t1.id());
                })
                .orderBy((t, t1) -> {
                    t.id().asc();
                    t1.createTime().desc();
                })
                .selectDraft((t, t1) -> Select.draft(
                        t.stars(),
                        t.createTime(),
                        t1.title()
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`stars` AS `value1`,t.`create_time` AS `value2`,t1.`title` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` ORDER BY t.`id` ASC,t1.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
        for (Draft3<Integer, LocalDateTime, String> item : list) {

            Integer value1 = item.getValue1();
            LocalDateTime value2 = item.getValue2();
            String value3 = item.getValue3();
            if (item.getValue1() != null) {
                Assert.assertTrue(item.getValue1() instanceof Integer);
            }
            if (item.getValue2() != null) {
                Assert.assertTrue(item.getValue2() != null);
            }
            if (item.getValue3() != null) {
                Assert.assertTrue(item.getValue3() instanceof String);
            }

        }
    }

    @Test
    public void testDraft10() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, Long>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().like("123");
                    o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
                })
                .groupByExpression(o -> o.id())//多个用GroupBy.of(.....)
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.id().count()
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,COUNT(t.`id`) AS `value2` FROM `t_topic` t WHERE t.`title` LIKE ? AND t.`create_time` >= ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
    }

    @Test
    public void testDraft11() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft3<String, String, String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().toLower().like("123");
                    o.title().toLower().eq(o.id().toUpper());
                    o.title().toLower().ne(o.id().toLower());
                    o.title().toLower().eq(o.id().toUpper());
                    o.title().toUpper().like("123");
                    o.title().toUpper().eq(o.id().toUpper());
                    o.title().toUpper().ne(o.id().toLower());
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().toLower(),
                        o.title().toUpper()
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,LOWER(t.`title`) AS `value2`,UPPER(t.`title`) AS `value3` FROM `t_topic` t WHERE LOWER(t.`title`) LIKE ? AND LOWER(t.`title`) = UPPER(t.`id`) AND LOWER(t.`title`) <> LOWER(t.`id`) AND LOWER(t.`title`) = UPPER(t.`id`) AND UPPER(t.`title`) LIKE ? AND UPPER(t.`title`) = UPPER(t.`id`) AND UPPER(t.`title`) <> LOWER(t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
    }

    @Test
    public void testDraft12() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft4<String, String, String, String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().subString(1, 2).eq("123");
                    o.title().toLower().subString(1, 2).eq("123");
                    o.title().toLower().toUpper().toLower().subString(1, 2).eq("123");
                    o.createTime()
                            .format("yyyy-MM")//日期先格式化
                            .toLower()//然后转成小写
                            .subString(1, 10)//分割从第一位
                            .like("023-01");
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().toLower(),
                        o.title().toUpper(),
                        o.title().toLower().subString(1, 2)
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,LOWER(t.`title`) AS `value2`,UPPER(t.`title`) AS `value3`,SUBSTR(LOWER(t.`title`),2,2) AS `value4` FROM `t_topic` t WHERE SUBSTR(t.`title`,2,2) = ? AND SUBSTR(LOWER(t.`title`),2,2) = ? AND SUBSTR(LOWER(UPPER(LOWER(t.`title`))),2,2) = ? AND SUBSTR(LOWER(DATE_FORMAT(t.`create_time`,'%Y-%m')),2,10) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String),%023-01%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft13() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft4<String, String, String, String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().trim().subString(1, 2).eq("123");
                    o.title().trim().toLower().subString(1, 2).eq("123");
                    o.title().toLower().trim().toUpper().toLower().subString(1, 2).eq("123");
                    o.createTime()
                            .format("yyyy-MM")//日期先格式化
                            .toLower()//然后转成小写
                            .subString(1, 10)//分割从第一位
                            .trim()
                            .like("023-01");
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().toLower().trim(),
                        o.title().trim().toUpper(),
                        o.title().toLower().trim().subString(1, 2)
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,TRIM(LOWER(t.`title`)) AS `value2`,UPPER(TRIM(t.`title`)) AS `value3`,SUBSTR(TRIM(LOWER(t.`title`)),2,2) AS `value4` FROM `t_topic` t WHERE SUBSTR(TRIM(t.`title`),2,2) = ? AND SUBSTR(LOWER(TRIM(t.`title`)),2,2) = ? AND SUBSTR(LOWER(UPPER(TRIM(LOWER(t.`title`)))),2,2) = ? AND TRIM(SUBSTR(LOWER(DATE_FORMAT(t.`create_time`,'%Y-%m')),2,10)) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String),%023-01%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft14() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft4<String, String, String, String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().trimStart().subString(1, 2).eq("123");
                    o.title().trimStart().toLower().subString(1, 2).eq("123");
                    o.title().toLower().trimStart().toUpper().toLower().subString(1, 2).eq("123");
                    o.createTime()
                            .format("yyyy-MM")//日期先格式化
                            .toLower()//然后转成小写
                            .subString(1, 10)//分割从第一位
                            .trimStart()
                            .like("023-01");
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().toLower().trimStart(),
                        o.title().trimStart().toUpper(),
                        o.title().toLower().trimStart().subString(1, 2)
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,LTRIM(LOWER(t.`title`)) AS `value2`,UPPER(LTRIM(t.`title`)) AS `value3`,SUBSTR(LTRIM(LOWER(t.`title`)),2,2) AS `value4` FROM `t_topic` t WHERE SUBSTR(LTRIM(t.`title`),2,2) = ? AND SUBSTR(LOWER(LTRIM(t.`title`)),2,2) = ? AND SUBSTR(LOWER(UPPER(LTRIM(LOWER(t.`title`)))),2,2) = ? AND LTRIM(SUBSTR(LOWER(DATE_FORMAT(t.`create_time`,'%Y-%m')),2,10)) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String),%023-01%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft15() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft4<String, String, String, String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().trimEnd().subString(1, 2).eq("123");
                    o.title().trimEnd().toLower().subString(1, 2).eq("123");
                    o.title().toLower().trimEnd().toUpper().toLower().subString(1, 2).eq("123");
                    o.createTime()
                            .format("yyyy-MM")//日期先格式化
                            .toLower()//然后转成小写
                            .subString(1, 10)//分割从第一位
                            .trimEnd()
                            .like("023-01");
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().toLower().trimEnd(),
                        o.title().trimEnd().toUpper(),
                        o.title().toLower().trimEnd().subString(1, 2)
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,RTRIM(LOWER(t.`title`)) AS `value2`,UPPER(RTRIM(t.`title`)) AS `value3`,SUBSTR(RTRIM(LOWER(t.`title`)),2,2) AS `value4` FROM `t_topic` t WHERE SUBSTR(RTRIM(t.`title`),2,2) = ? AND SUBSTR(LOWER(RTRIM(t.`title`)),2,2) = ? AND SUBSTR(LOWER(UPPER(RTRIM(LOWER(t.`title`)))),2,2) = ? AND RTRIM(SUBSTR(LOWER(DATE_FORMAT(t.`create_time`,'%Y-%m')),2,10)) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String),%023-01%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft16() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().trimEnd().trimStart().eq(o.id().trimStart());
                    o.createTime().format("yyyy-MM-dd").subString(0, 4).eq("2021");
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().toLower()
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,LOWER(t.`title`) AS `value2` FROM `t_topic` t WHERE LTRIM(RTRIM(t.`title`)) = LTRIM(t.`id`) AND SUBSTR(DATE_FORMAT(t.`create_time`,'%Y-%m-%d'),1,4) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft17() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().trimEnd().trimStart().replace("title", "abc").like("abc");
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().toLower().replace("title", "abc")
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,REPLACE(LOWER(t.`title`),?,?) AS `value2` FROM `t_topic` t WHERE REPLACE(LTRIM(RTRIM(t.`title`)),?,?) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("title(String),abc(String),title(String),abc(String),%abc%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft18() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, Integer>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().compareTo(o.id()).eq(0);//==0
                    o.title().trim().compareTo(o.id().toLower().subString(1, 10)).ge(2);//>=2
                    o.id().compareTo(o.title()).le(1);//<=1
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().compareTo(o.id())
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,STRCMP(t.`title`,t.`id`) AS `value2` FROM `t_topic` t WHERE STRCMP(t.`title`,t.`id`) = ? AND STRCMP(TRIM(t.`title`),SUBSTR(LOWER(t.`id`),2,10)) >= ? AND STRCMP(t.`id`,t.`title`) <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("0(Integer),2(Integer),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft19() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().leftPad(5, '1').ne("title0");//==0
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().leftPad(9, '0')
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,LPAD(t.`title`, 9, ?) AS `value2` FROM `t_topic` t WHERE LPAD(t.`title`, 5, ?) <> ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("0(String),1(String),title0(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        List<Draft2<String, String>> list1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().leftPad(5, '1').ne("title0");//==0
                    o.id().ne("7");
                })
                .selectDraft(o -> Select.draft(
                        o.id(),
                        o.title().leftPad(9, '0')
                ))
                .toList();
        for (Draft2<String, String> stringStringDraft2 : list1) {

            Assert.assertTrue(stringStringDraft2.getValue2().startsWith("0"));
        }
    }

    @Test
    public void testDraft20() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list2 = easyEntityQuery.queryable(BlogEntity.class)
                .groupByExpression(o -> o.content().subString(0, 8))
                .selectDraft(o -> Select.draft(
                        o.groupKeys(0).toDraft(String.class),
                        o.id().join(",")
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.`content`,1,8) AS `value1`,GROUP_CONCAT(t.`id` SEPARATOR ?) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY SUBSTR(t.`content`,1,8)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

//    @Test
//    public void testGroup1(){
//
////
////        entityQuery.queryable(Topic.class)
////                .where(o -> {
////                    o.title().eq("title");
////                    o.id().eq("1");
////                })
////                .groupByDraft(o -> GroupBy.keys(
////                        o.title()
////                ))
////                .selectDraft(o -> Select.draft(
////                        o.groupKeys().key1(),
////                        o.count(x->x.title())
////                )).toList();
//    }
}
