package com.easy.query.test;

import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.client.DefaultEntityQuery;
import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import com.easy.query.core.proxy.sql.GroupBy;
import com.easy.query.core.proxy.sql.Predicate;
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
    public void testQuery1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        String xq = "123";
        List<BlogEntity> list = easyProxyQuery.queryable(topicTable)
                .where(topicTable.id().eq(xq),
                        Predicate.or(
                                topicTable.title().ge("11" )
                                , topicTable.id().le("11" )
                                , topicTable.createTime().lt(LocalDateTime.of(2023, 1, 1, 1, 1))
                        )
                        , topicTable.id().like("11" ))
                .orderBy(topicTable.id().asc(false)
                        , topicTable.title().desc()
                        , topicTable.title().asc())
                .select(blogTable
                        , topicTable.allFieldsExclude(topicTable.stars())
                        , topicTable.stars().as(blogTable.star())
                        , topicTable.createTime()
                )
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`title`,t.`create_time`,t.`stars` AS `star`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ? AND (t.`title` >= ? OR t.`id` <= ? OR t.`create_time` < ?) AND t.`id` LIKE ? ORDER BY t.`title` DESC,t.`title` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),11(String),11(String),2023-01-01T01:01(LocalDateTime),%11%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();

        BlogEntityProxy subBlogTable = BlogEntityProxy.createTable();
        ProxyQueryable<StringProxy, String> idQueryable = easyProxyQuery.queryable(subBlogTable)
                .where(subBlogTable.title().like("你好" ))
                .select(StringProxy.createTable(), subBlogTable.id());
        String xq = "123";
        List<BlogEntity> list = easyProxyQuery.queryable(topicTable)
                .where(topicTable.id().eq(xq),
                        Predicate.or(
                                topicTable.title().ge("11" )
                                , topicTable.id().le("11" )
                                , topicTable.createTime().lt(LocalDateTime.of(2023, 1, 1, 1, 1))
                        )
                        , topicTable.id().in(idQueryable))
                .orderBy(topicTable.id().asc(false)
                        , topicTable.title().desc()
                        , topicTable.title().asc())
                .select(blogTable
                        , topicTable.allFieldsExclude(topicTable.stars())
                        , topicTable.stars().as(blogTable.star())
                        , topicTable.createTime()
                )
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`title`,t.`create_time`,t.`stars` AS `star`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ? AND (t.`title` >= ? OR t.`id` <= ? OR t.`create_time` < ?) AND t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`title` LIKE ?) ORDER BY t.`title` DESC,t.`title` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),11(String),11(String),2023-01-01T01:01(LocalDateTime),false(Boolean),%你好%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        listenerContextManager.clear();
    }

    @Test
    public void testQuery3() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();

        BlogEntityProxy subBlogTable = BlogEntityProxy.createTable();

        String xq = "123";
        List<BlogEntity> list = easyProxyQuery.queryable(topicTable)
                .where(topicTable.id().eq(xq),
                        Predicate.or(
                                topicTable.title().ge("11" )
                                , topicTable.id().le("11" )
                                , topicTable.createTime().lt(LocalDateTime.of(2023, 1, 1, 1, 1))
                        )
                        , topicTable.exists(() -> {
                            return easyProxyQuery.queryable(subBlogTable)
                                    .where(subBlogTable.title().like("你好" ),
                                            subBlogTable.id().eq(topicTable.id()));
                        }))
                .orderBy(topicTable.id().asc(false)
                        , topicTable.title().desc()
                        , topicTable.title().asc())
                .select(blogTable
                        , topicTable.allFieldsExclude(topicTable.stars())
                        , topicTable.stars().as(blogTable.star())
//                        , topicTable.stars().as(topicTable.stars())
                        , topicTable.createTime()
                )
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`title`,t.`create_time`,t.`stars` AS `star`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ? AND (t.`title` >= ? OR t.`id` <= ? OR t.`create_time` < ?) AND EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`title` LIKE ? AND t1.`id` = t.`id`) ORDER BY t.`title` DESC,t.`title` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),11(String),11(String),2023-01-01T01:01(LocalDateTime),false(Boolean),%你好%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testQuery4() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();

        BlogEntityProxy subBlogTable = BlogEntityProxy.createTable();

        String xq = "123";
        List<BlogEntity> list = easyProxyQuery.queryable(topicTable)
                .where(topicTable.id().eq(xq),
                        Predicate.or(
                                topicTable.title().ge("11" )
                                , topicTable.id().le("11" )
                                , topicTable.createTime().lt(LocalDateTime.of(2023, 1, 1, 1, 1))
                        )
                        , topicTable.exists(() -> {
                            return easyProxyQuery.queryable(subBlogTable)
                                    .where(subBlogTable.title().like("你好" ),
                                            subBlogTable.id().eq(topicTable.id()));
                        }),
                        Predicate.sql("IFNULL({0},'') = ''", c -> {
                            c.keepStyle();
                            c.expression(topicTable.title());
                        }))
                .orderBy(topicTable.id().asc(false)
                        , topicTable.title().desc()
                        , topicTable.title().asc())
                .select(blogTable
                        , topicTable.allFieldsExclude(topicTable.stars())
                        , topicTable.stars().as(blogTable.star())
//                        , topicTable.stars().as(topicTable.stars())
                        , topicTable.createTime()
//                        ,SelectExpression.groupKeys(0).as()
                        , Select.sqlAlias(blogTable, "1", c -> {
                            c.setPropertyAlias(blogTable.order());
                        })
                )
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`title`,t.`create_time`,t.`stars` AS `star`,t.`create_time`,1 AS `order` FROM `t_topic` t WHERE t.`id` = ? AND (t.`title` >= ? OR t.`id` <= ? OR t.`create_time` < ?) AND EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`title` LIKE ? AND t1.`id` = t.`id`) AND IFNULL(t.`title`,'') = '' ORDER BY t.`title` DESC,t.`title` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),11(String),11(String),2023-01-01T01:01(LocalDateTime),false(Boolean),%你好%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testQuery5() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();

        String xq = "123";
        List<BlogEntity> list = easyProxyQuery.queryable(topicTable)
                .where(topicTable.id().eq(xq))
                .groupBy(o -> o.column(topicTable.id()))
                .select(blogTable, Select.groupKeys(0).as(blogTable.star()))
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `star` FROM `t_topic` t WHERE t.`id` = ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery6() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();

        String xq = "123";
        List<Topic> list = easyProxyQuery.queryable(topicTable)
                .where(topicTable.id().eq(xq))
                .groupBy(topicTable.id())
                .select(Select.groupKeys(0))
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id` FROM `t_topic` WHERE `id` = ? GROUP BY `id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery7() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        TopicTestProxy topicTable1 = TopicTestProxy.createTable();

        String xq = "123";
        List<Topic> list = easyProxyQuery.queryable(topicTable)
                .where(topicTable.id().eq(xq))
                .groupBy(topicTable.id())
                .select(topicTable1,
                        Select.groupKeys(0).as(topicTable1.id())
                        , topicTable.stars().count().as(topicTable1.title()))
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`stars`) AS `title` FROM `t_topic` t WHERE t.`id` = ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery8() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        TopicTestProxy topicTable1 = TopicTestProxy.createTable();

        String xq = "123";
        List<Topic> list = easyProxyQuery.queryable(topicTable)
                .where(
                        topicTable.id().eq(xq),
                        topicTable.stars().le(1),
                        topicTable.stars().le(2),
                        Predicate.or(
                                topicTable.id().like("111" ),
                                topicTable.id().eq("1" )
                        )
                        , topicTable.stars().le(1)
                )
                .groupBy(topicTable.id())
                .having(topicTable.id().count().eq(1))
                .select(topicTable1,
                        Select.groupKeys(0).as(topicTable1.id())
                        , topicTable.stars().count().as(topicTable1.title())
                )
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`stars`) AS `title` FROM `t_topic` t WHERE t.`id` = ? AND t.`stars` <= ? AND t.`stars` <= ? AND (t.`id` LIKE ? OR t.`id` = ?) AND t.`stars` <= ? GROUP BY t.`id` HAVING COUNT(t.`id`) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(Integer),%111%(String),1(String),1(Integer),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery9() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        TopicTestProxy topicTable1 = TopicTestProxy.createTable();

        String xq = "123";
        List<Topic> list = easyProxyQuery.queryable(topicTable)
                .leftJoin(blogTable).on(topicTable.id().eq(blogTable.id()))
                .where(topicTable.id().eq(xq), topicTable.stars().le(1))
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? AND t.`stars` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery10() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        TopicTestProxy topicTable1 = TopicTestProxy.createTable();

        String xq = "123";
        List<Topic> list = easyProxyQuery.queryable(topicTable)
                .where(
                        topicTable.id().eq(xq),
                        topicTable.stars().le(1),
                        topicTable.stars().le(2),
                        Predicate.or(
                                topicTable.id().like("111" ),
                                topicTable.id().eq("1" )
                        )
                        , topicTable.stars().le(1)
                )
                .groupBy(topicTable.id())
                .having(topicTable.id().count().eq(1))
                .select(topicTable1,
                        Select.groupKeys(0).as(topicTable1.id())
                        , topicTable.stars().count(c -> c.distinct(true).nullDefault(1)).as(topicTable1.title())
                )
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(DISTINCT IFNULL(t.`stars`,?)) AS `title` FROM `t_topic` t WHERE t.`id` = ? AND t.`stars` <= ? AND t.`stars` <= ? AND (t.`id` LIKE ? OR t.`id` = ?) AND t.`stars` <= ? GROUP BY t.`id` HAVING COUNT(t.`id`) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),123(String),1(Integer),2(Integer),%111%(String),1(String),1(Integer),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery11() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTestProxy topicTable = TopicTestProxy.createTable();
        TopicTestProxy topicTable1 = TopicTestProxy.createTable();

        String xq = "123";
        List<Topic> list = easyProxyQuery.queryable(topicTable)
                .where(
                        topicTable.id().eq(xq),
                        topicTable.stars().le(1),
                        topicTable.stars().le(2),
                        Predicate.or(
                                topicTable.id().like("111" ),
                                topicTable.id().eq("1" )
                        )
                        , topicTable.stars().le(1)
                )
                .orderBy(topicTable.id().asc().thenBy(topicTable.title().desc()))
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? AND `stars` <= ? AND `stars` <= ? AND (`id` LIKE ? OR `id` = ?) AND `stars` <= ? ORDER BY `id` ASC,`title` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(Integer),%111%(String),1(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void entityQuery1() {

        TopicTestProxy table = TopicTestProxy.createTable();
        BlogEntityProxy table1 = BlogEntityProxy.createTable();
//        TopicTestProxy.TopicTestSelector title = table.selector().id().title();
//        TopicTestProxy.TopicTestSelector blogEntityProxyTopicTestFetcher = table.selector().id().title().as(table1.createTime()).columns(table.id());
//        TopicProxy topicProxy = EntityQueryProxyManager.create(Topic.class);
//        TopicProxy topicProxy1 = EntityQueryProxyManager.create(Topic.class);

        DefaultEntityQuery entityQuery = new DefaultEntityQuery(easyQueryClient);
        List<Topic> list = entityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1" ).and(o.title().like("xxx" )))
                .where(o -> {
                    return o.id().eq("1" )
                            .and(o.title().like("xxx" ))
                            .and(o.createTime().ge(LocalDateTime.now()));
                })
                .where(o ->
                        Predicate.and(
                                o.id().eq("1" ),
                                o.title().like("xxx" ),
                                o.createTime().ge(LocalDateTime.now())
                        )
                ).toList();

        Topic topic = entityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (a, b) -> a.id().eq(b.id()))
                .where((a, b) -> a.title().eq("1" ).and(b.createTime().ge(LocalDateTime.of(2021, 1, 1, 1, 1))))
                .orderBy((a, b) -> a.title().asc().thenBy(a.id().desc()))
                .firstOrNull();

        Topic topic2 = entityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (a, b) -> a.id().eq(b.id()))
                .where((a, b) -> Predicate.and(
                        a.title().eq("1" ),
                        b.createTime().ge(LocalDateTime.of(2021, 1, 1, 1, 1))
                ))
                .orderBy((a, b) -> a.title().asc())
                .select(o -> o.cols().id().createTime().stars().title().as(o.createTime()))
                .firstOrNull();
        List<Topic> list1 = entityQuery.queryable(Topic.class)
                .where(o -> o.title().eq("title" ).and(o.id().eq("1" )))
                .groupBy(o -> o.cols().title())
                .select(Topic.class, (o, tr) -> Select.of(
                        o.title()
                        , o.id().count().as(tr.stars())
                ))
                .toList();
        List<Topic> list2 = entityQuery.queryable(Topic.class)
                .where(o -> o.title().eq("title" ).and(o.id().eq("1" )))
                .groupBy(o -> GroupBy.of(
                        o.title(),
                        o.id()
                ))
                .select(Topic.class, (o, tr) -> Select.of(
                        o.title(),
                        o.id(),
                        o.id().count().as(tr.stars())
                ))
                .toList();
    }

}
