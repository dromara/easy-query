package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQueryFindNotNullException;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;
import com.easy.query.core.exception.EasyQueryRequiredException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.base.TopicTestProxy;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
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
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` WHERE `id` = ?" , sql);
    }

    @Test
    public void selectCount2() {
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, 123))
                .selectCount().toSQL();
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?" , sql);
    }

    @Test
    public void selectCount3() {
        String sql = easyQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, 123))
                .selectCount().toSQL();
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?" , sql);
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
                        .firstNotNull(() -> new MyAppException("asv" ));
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
        Assert.assertEquals("asv" , myAppException.getMessage());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
    }
    @Test
    public void selectThrow9() {
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    Topic topic = easyQuery.queryable(Topic.class)
                            .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                            .where(o -> o.eq(Topic::getId, UUID.randomUUID()))
                            .firstNotNull();
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
            EasyQueryFirstNotNullException myAppException = (EasyQueryFirstNotNullException) exception;
            Assert.assertEquals("未找到主题信息" , myAppException.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    Topic topic = easyQuery.queryable(Topic.class)
                            .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                            .where(o -> o.eq(Topic::getId, UUID.randomUUID()))
                            .singleNotNull();
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
            EasyQuerySingleNotNullException myAppException = (EasyQuerySingleNotNullException) exception;
            Assert.assertEquals("未找到主题信息" , myAppException.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    Topic topic = easyQuery.queryable(Topic.class)
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
            EasyQuerySingleMoreElementException myAppException = (EasyQuerySingleMoreElementException) exception;
            Assert.assertEquals("single query at most one element in result set." , myAppException.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic`" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    Topic topic = easyQuery.queryable(Topic.class)
                            .singleNotNull();
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
            EasyQuerySingleMoreElementException myAppException = (EasyQuerySingleMoreElementException) exception;
            Assert.assertEquals("single query at most one element in result set." , myAppException.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic`" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    Topic topic = easyQuery.queryable(Topic.class)
                            .findNotNull("xaaaaa");
                } catch (Exception ex) {
                    return ex;
                } finally {
                    listenerContextManager.clear();
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQueryFindNotNullException);
            EasyQueryFindNotNullException myAppException = (EasyQueryFindNotNullException) exception;
            Assert.assertEquals("未找到主题信息" , myAppException.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    Topic topic = easyQuery.queryable(Topic.class)
                            .findOrNull("xaaaaa");
                } catch (Exception ex) {
                    return ex;
                } finally {
                    listenerContextManager.clear();
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNull(exception);
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    Topic topic = easyQuery.queryable(Topic.class)
                            .findNotNull("xaaaaa","aaaa");
                } catch (Exception ex) {
                    return ex;
                } finally {
                    listenerContextManager.clear();
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQueryFindNotNullException);
            EasyQueryFindNotNullException myAppException = (EasyQueryFindNotNullException) exception;
            Assert.assertEquals("aaaa" , myAppException.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    easyEntityQuery.queryable(Topic.class)
                            .whereById("xxxaassd")
                            .required();
                } catch (Exception ex) {
                    return ex;
                } finally {
                    listenerContextManager.clear();
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQueryRequiredException);
            EasyQueryRequiredException myAppException = (EasyQueryRequiredException) exception;
            Assert.assertEquals("未找到主题信息" , myAppException.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT  1  FROM `t_topic` WHERE `id` = ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    easyEntityQuery.queryable(Topic.class)
                            .whereById("xxxaassd")
                            .required("xxxx");
                } catch (Exception ex) {
                    return ex;
                } finally {
                    listenerContextManager.clear();
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQueryRequiredException);
            EasyQueryRequiredException myAppException = (EasyQueryRequiredException) exception;
            Assert.assertEquals("xxxx" , myAppException.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT  1  FROM `t_topic` WHERE `id` = ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    easyEntityQuery.queryable(Topic.class)
                            .whereById("xxxaassd")
                            .required(()->new RuntimeException("xxxx1"));
                } catch (Exception ex) {
                    return ex;
                } finally {
                    listenerContextManager.clear();
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            RuntimeException myAppException = (RuntimeException) exception;
            Assert.assertEquals("xxxx1" , myAppException.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT  1  FROM `t_topic` WHERE `id` = ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            Supplier<Exception> f = () -> {
                try {
                    listenerContextManager.startListen(listenerContext);
                    easyEntityQuery.queryable(Topic.class)
                            .required();
                } catch (Exception ex) {
                    return ex;
                } finally {
                    listenerContextManager.clear();
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNull(exception);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT  1  FROM `t_topic` LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        }
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
                        .singleNotNull(() -> new MyAppException("asv" ));
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
        Assert.assertEquals("asv" , myAppException.getMessage());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
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
                        .singleNotNull("1" );
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
                        .firstNotNull("1" );
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
                    o.id().eq("1" );
                    o.title().like("xxx" );
                })
                .where(o -> {
                    o.id().eq("1" );
                    o.title().like("xxx" );
                    o.createTime().ge(LocalDateTime.now());
                })
                .where(o -> {
                            o.id().eq("1" );
                            o.title().like("xxx" );
                            o.createTime().ge(LocalDateTime.now());
                        }
                ).toList();

        Topic topic = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (a, b) -> {
                    a.id().eq(b.id());
                })
                .where((a, b) -> {
                    a.title().eq("1" );
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
                    a.title().eq("1" );
                    b.createTime().ge(LocalDateTime.of(2021, 1, 1, 1, 1));
                })
                .orderBy((a, b) -> {
                    a.title().asc();
                })
                .fetchBy(o -> o.FETCHER.title().stars())
                .firstOrNull();
        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().eq("title" );
                    o.id().eq("1" );
                })
                .groupBy(o -> GroupKeys.TABLE1.of(o.title()))
                .select(o -> new TopicProxy().adapter(r -> {

                    r.title().set(o.key1());
                    r.stars().set(o.intCount(o.groupTable().id()));
                }))
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
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `stars` = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(Integer)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list3 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title" );
                        o.id().eq("1" );
                    })
                    .orderBy(o -> o.createTime().format("yyyy-MM-dd HH:mm:ss" ).asc())
                    .select(o -> new TopicProxy().selectExpression(o.FETCHER.title().id(), o.createTime().format("yyyy-MM-dd HH:mm:ss" )))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic` t WHERE t.`title` = ? AND t.`id` = ? ORDER BY DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') ASC" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("title(String),1(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list3 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title" );
                        o.id().eq("1" );
                    })
                    .orderBy(o -> o.createTime().format("yyyy-MM-dd HH:mm:ss" ).desc())
                    .select(o -> new TopicProxy().selectExpression(o.FETCHER.title().id(), o.createTime().format("yyyy-MM-dd HH:mm:ss" )))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic` t WHERE t.`title` = ? AND t.`id` = ? ORDER BY DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') DESC" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("title(String),1(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list3 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title" );
                        o.id().eq("1" );
                    })
                    .orderBy(o -> {
                        o.createTime().format("yyyy-MM-dd HH:mm:ss" ).desc();
                        o.executeSQL("IFNULL({0},'') ASC" , c -> {
                            c.keepStyle().expression(o.stars());
                        });
                    })
                    .select(o -> new TopicProxy().selectExpression(o.FETCHER.title().id(), o.createTime().format("yyyy-MM-dd HH:mm:ss" )))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic` t WHERE t.`title` = ? AND t.`id` = ? ORDER BY DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') DESC,IFNULL(t.`stars`,'') ASC" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("title(String),1(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDsl2() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" ))
                    .groupBy(o -> GroupKeys.TABLE1.of(o.createTime().format("yyyy/MM/dd" )))
                    .select(o -> new TopicProxy().adapter(r -> {
                        r.stars().set(o.intCount(o.groupTable().id()));
                        r.title().set(o.key1());
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT COUNT(t.`id`) AS `stars`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `title` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? GROUP BY DATE_FORMAT(t.`create_time`,'%Y/%m/%d')" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" ))
                    .groupBy(o -> GroupKeys.TABLE1.of(o.createTime().format("yyyy/MM/dd" )))
                    .select(o -> new TopicProxy().adapter(r -> {

                        r.stars().set(o.intCount(o.groupTable().id()));
                        r.title().set(o.groupTable().createTime().format("yyyy/MM/dd" ));
//                        id().set(o.createTime().format("yyyy/MM/dd"));
                        r.id().set(o.key1());
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT COUNT(t.`id`) AS `stars`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `title`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `id` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? GROUP BY DATE_FORMAT(t.`create_time`,'%Y/%m/%d')" , jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("SELECT COUNT(t.`id`) AS `stars`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `title`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `id`,DATE_FORMAT(t.`create_time`,'%Y/%m/%d') AS `id` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? GROUP BY DATE_FORMAT(t.`create_time`,'%Y/%m/%d')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDsl3() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" ))
                    .select(o -> new TopicProxy().adapter(r -> {

                        r.title().set(o.stars().nullOrDefault(0).toStr());
                        r.alias().setSQL("IFNULL({0},'')" , c -> {
                            c.keepStyle();
                            c.expression(o.id());
                        });
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT CAST(IFNULL(t.`stars`,?) AS CHAR) AS `title`,IFNULL(t.`id`,'') AS `alias` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(Integer),2023/01/01(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" ))
                    .select(o -> new TopicProxy().adapter(r -> {

                        r.title().set(o.stars().nullOrDefault(0).toStr());
                        r.alias().setSQL("IFNULL({0},'')" , c -> {
                            c.keepStyle();
                            c.expression(o.id());
                        });
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT CAST(IFNULL(t.`stars`,?) AS CHAR) AS `title`,IFNULL(t.`id`,'') AS `alias` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(Integer),2023/01/01(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" ))
                    .select(o -> new TopicProxy().adapter(r -> {

                        r.title().set(o.stars().nullOrDefault(0).toStr());
                        r.alias().setSQL("IFNULL({0},'')" , c -> {
                            c.keepStyle();
                            c.expression(o.id());
                        });
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT CAST(IFNULL(t.`stars`,?) AS CHAR) AS `title`,IFNULL(t.`id`,'') AS `alias` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(Integer),2023/01/01(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '')" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NULL OR `id` = '' OR LTRIM(`id`) = '')" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '')" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` IS NOT NULL AND `id` <> '' AND LTRIM(`id`) <> '')" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` <= `create_time`" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd" ).le(o._now()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE DATE_FORMAT(`create_time`,'%Y/%m/%d') <= NOW()" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` <= NOW()" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().le(o.createTime().nullOrDefault(LocalDateTime.of(2021,1,1,1,1))))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` <= IFNULL(`create_time`,?)" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2021-01-01T01:01(LocalDateTime)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().le(o.createTime().nullOrDefault(LocalDateTime.of(2022, 1, 1, 1, 1)));
                        o.id().isNotBank();
                        o.id().nullOrDefault("" ).eq(o.title().nullOrDefault(c -> c.column(o.id())));
                        o.title().isEmpty();
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` <= IFNULL(`create_time`,?) AND (`id` IS NOT NULL AND `id` <> '' AND LTRIM(`id`) <> '') AND IFNULL(`id`,?) = IFNULL(`title`,`id`) AND (`title` IS NULL OR `title` = '')" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2022-01-01T01:01(LocalDateTime),(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().le(o.createTime().nullOrDefault(LocalDateTime.of(2022, 1, 1, 1, 1)));
                        o.id().nullOrDefault("1" ).isNull();
                        o.id().nullOrDefault("2" ).eq(o.title().nullOrDefault(c -> c.column(o.id())));
                        o.title().isEmpty();
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` <= IFNULL(`create_time`,?) AND IFNULL(`id`,?) IS NULL AND IFNULL(`id`,?) = IFNULL(`title`,`id`) AND (`title` IS NULL OR `title` = '')" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2022-01-01T01:01(LocalDateTime),1(String),2(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
                        o.createTime().le(o.createTime().nullOrDefault(LocalDateTime.of(2022, 1, 1, 1, 1)));
                        o.id().nullOrDefault("1" ).isNull();
                        o.id().nullOrDefault("2" ).eq(o.title().nullOrDefault(c -> c.column(o.id())));
                        o.title().isEmpty();
                    })
                    .select(o -> new StringProxy(o.title()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title` FROM `t_topic` t WHERE  t.`create_time` <= IFNULL(t.`create_time`,?) AND IFNULL(t.`id`,?) IS NULL AND IFNULL(t.`id`,?) = IFNULL(t.`title`,t.`id`) AND (t.`title` IS NULL OR t.`title` = '')" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2022-01-01T01:01(LocalDateTime),1(String),2(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" ))
                    .groupBy(o -> GroupKeys.TABLE1.of(o.title()))
                    .select(o -> new TopicProxy().adapter(r -> {

                        r.selectExpression(o.key1());
                        r.stars().set(o.intCount(o.groupTable().id()));
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,COUNT(t.`id`) AS `stars` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? GROUP BY t.`title`" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {

                        o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" );
                        o.or(() -> {
                            o.stars().ne(1);
                            o.createTime().le(LocalDateTime.of(2024, 1, 1, 1, 1));
                            o.title().notLike("abc" );
                        });
                        o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" );
                        o.id().nullOrDefault("yyyy/MM/dd" ).eq("xxx" );
                        o.executeSQL("{0} != {1}" , c -> {
                            c.expression(o.stars()).expression(o.createTime());
                        });
                        o.or(() -> {
                            o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" );
                            o.id().nullOrDefault("yyyy/MM/dd" ).eq("xxx" );
                            o.executeSQL("{0} != {1}" , c -> {
                                c.expression(o.stars()).expression(o.createTime());
                            });
                        });

                        o.createTime().format("yyyy/MM/dd" ).eq("2023/01/02" );
                        o.id().nullOrDefault("yyyy/MM/dd2" ).eq("xxx1" );
                    })
                    .fetchBy(o -> o.FETCHER
                            .allFieldsExclude(o.id(), o.title())
                            .id().as(o.title())
                            .id())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `title`,t.`id` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? AND (t.`stars` <> ? OR t.`create_time` <= ? OR t.`title` NOT LIKE ?) AND DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? AND IFNULL(t.`id`,?) = ? AND t.`stars` != t.`create_time` AND (DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? OR IFNULL(t.`id`,?) = ? OR t.`stars` != t.`create_time`) AND DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ? AND IFNULL(t.`id`,?) = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String),1(Integer),2024-01-01T01:01(LocalDateTime),%abc%(String),2023/01/01(String),yyyy/MM/dd(String),xxx(String),2023/01/01(String),yyyy/MM/dd(String),xxx(String),2023/01/02(String),yyyy/MM/dd2(String),xxx1(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
                        o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" );
                    })
                    .select(o -> new MapProxy().adapter(r -> {
                        r.selectAll(o);
                        r.selectIgnores(o.id(), o.title());
                        r.put("abc" , o.id());
                        r.selectExpression(o.id());
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" );
                    }).select(o -> new MapProxy()).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT * FROM `t_topic` t WHERE DATE_FORMAT(t.`create_time`,'%Y/%m/%d') = ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2023/01/01(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<StringProxy, String> idQuery = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123" ))
                    .select(o -> new StringProxy(o.id()));
            List<Map<String, Object>> abc = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().in(idQuery))
                    .select(o -> {
                        MapProxy map = new MapProxy();
                        map.selectAll(o);
                        map.selectIgnores(o.id(), o.title());
                        map.put("abc" , o.id());
                        map.selectExpression(o.id());
                        return map;
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` FROM `t_topic` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ?)" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
                    .where(o -> o.id().eq("123" ))
                    .select(o -> new StringProxy(o.id()));
            List<Map<String, Object>> abc = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
                    .where(o -> o.id().in(idQuery))
                    .select(a -> new MapProxy().adapter(map -> {
                        map.selectAll(a);
                        map.selectIgnores(a.id(), a.title());
                        map.put("abc" , a.id());
                        map.put("id" , a.id());
                        map.put("efg" , a.createTime().format("yyyy-MM-dd HH:mm:ss" ));
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` AS `id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') AS `efg` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (SELECT t2.`id` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = ?)" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),123(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<StringProxy, String> idQuery = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123" ))
                    .select(o -> new StringProxy(o.id()));
            List<Map<String, Object>> abc = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.id()))
                    .where(o -> {
                        o.id().in(idQuery);
                    })
                    .select(a -> new MapProxy().adapter(map -> {
                        map.selectAll(a);
                        map.selectIgnores(a.title(), a.id());
                        map.put("abc" , a.id());
                        map.put("id" , a.id());
                        map.put("efg" , a.createTime().format("yyyy-MM-dd HH:mm:ss" ));
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t.`id` AS `abc`,t.`id` AS `id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') AS `efg` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (SELECT t2.`id` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = ?)" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),false(Boolean),123(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void havingQueryTest1() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("123" ))
                    .groupBy(o -> GroupKeys.TABLE1.of(o.id()))
                    .having(o -> {
                        o.count().ne(1L);
                        o.groupTable().star().sum().ge(10);
                    })
                    .select(o -> new BlogEntityProxy().adapter(r -> {

                        r.id().set(o.key1());
                        r.star().set(o.groupTable().id().count().setPropertyType(Integer.class));
                        r.title().set(o.groupTable().id().max());
                    })).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`id`) AS `star`,MAX(t.`id`) AS `title` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id` HAVING COUNT(*) <> ? AND SUM(t.`star`) >= ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String),1(Long),10(Integer)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testQuery5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    o.id().eq("1" );
                    o.id().eq(o.createTime().format("yyyy-MM-dd" ));
                    o.createTime().format("yyyy-MM-dd" ).eq("2023-01-02" );
                    o.title().nullOrDefault("unknown" ).like("123" );
                    o.title().nullOrDefault("unknown" ).likeMatchLeft("123" );
                    o.title().nullOrDefault("unknown" ).likeMatchLeft(false, "123" );
                    o.title().nullOrDefault("unknown" ).likeMatchRight("123" );
                    o.title().nullOrDefault("unknown" ).likeMatchRight(false, "123" );
                    o.star().nullOrDefault(1).ge(101);
                    o.star().nullOrDefault(4).gt(102);
                    o.star().nullOrDefault(3).le(103);
                    o.star().nullOrDefault(2).lt(104);
                    o.star().nullOrDefault(1).eq(105);
                    o.title().nullOrDefault("unknown" ).eq(o.content());
                    o.content().isNotBank();
                })
                .groupBy(o -> GroupKeys.TABLE1.of(o.id()))
                .having(o -> {
                    o.count(o.groupTable().id()).ne(1L);
                    o.groupTable().star().sum().ge(10);
                }).select(o -> new BlogEntityProxy().adapter(r -> {
                    r.id().set(o.key1());
                    r.star().set(o.intCount());
                    r.title().set(o.groupTable().id().max());
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(*) AS `star`,MAX(t.`id`) AS `title` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND  t.`id` = DATE_FORMAT(t.`create_time`,'%Y-%m-%d') AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') = ? AND IFNULL(t.`title`,?) LIKE ? AND IFNULL(t.`title`,?) LIKE ? AND IFNULL(t.`title`,?) LIKE ? AND IFNULL(t.`star`,?) >= ? AND IFNULL(t.`star`,?) > ? AND IFNULL(t.`star`,?) <= ? AND IFNULL(t.`star`,?) < ? AND IFNULL(t.`star`,?) = ? AND IFNULL(t.`title`,?) = t.`content` AND (t.`content` IS NOT NULL AND t.`content` <> '' AND LTRIM(t.`content`) <> '') GROUP BY t.`id` HAVING COUNT(t.`id`) <> ? AND SUM(t.`star`) >= ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String),2023-01-02(String),unknown(String),%123%(String),unknown(String),123%(String),unknown(String),%123(String),1(Integer),101(Integer),4(Integer),102(Integer),3(Integer),103(Integer),2(Integer),104(Integer),1(Integer),105(Integer),unknown(String),1(Long),10(Integer)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery6() {

        EntityQueryable<StringProxy, String> sss = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.id().eq("sss" );
                })
                .select(o -> new StringProxy(o.id()));
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    o.id().eq(sss);
                    o.title().nullOrDefault("unknown" ).in(sss);
                })
                .groupBy(o -> GroupKeys.TABLE1.of(o.id()))
                .having(o -> {
                    o.count(o.groupTable().id()).ne(1L);
                    o.groupTable().star().sum().ge(10);
                })
                .select(o -> new BlogEntityProxy().adapter(r -> {

                    r.selectExpression(o.key1()
                            , o.groupTable().id().count().as(r.star())
                            , o.groupTable().id().max().as(r.title())
                    );
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `star`,MAX(t.`id`) AS `title` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = (SELECT t3.`id` FROM `t_blog` t3 WHERE t3.`deleted` = ? AND t3.`id` = ?) AND IFNULL(t.`title`,?) IN (SELECT t3.`id` FROM `t_blog` t3 WHERE t3.`deleted` = ? AND t3.`id` = ?) GROUP BY t.`id` HAVING COUNT(t.`id`) <> ? AND SUM(t.`star`) >= ?" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),false(Boolean),sss(String),unknown(String),false(Boolean),sss(String),1(Long),10(Integer)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
                .fetchBy(o -> o.FETCHER.allFieldsExclude(o.title(), o.top())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top` FROM `t_blog` t WHERE t.`deleted` = ? AND 1 = 2 AND 1 = 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

//    @Test
//    public void testQuery8() {
//        List<String> ids = Collections.emptyList();
//        ListenerContext listenerContext = new ListenerContext();
//        listenerContextManager.startListen(listenerContext);
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .where(o -> {
//                    o.id().in(ids);
//                    o.id().notIn(ids);
//                })
//                .select(BlogEntity.class,b -> b.FETCHER.allFieldsExclude(b.title(), b.top())).toList();
//        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top` FROM `t_blog` t WHERE t.`deleted` = ? AND 1 = 2 AND 1 = 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("false(Boolean)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//        listenerContextManager.clear();
//    }


}
