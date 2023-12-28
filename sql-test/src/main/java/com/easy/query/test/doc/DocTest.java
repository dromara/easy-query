package com.easy.query.test.doc;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.Fetcher;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.sql.GroupBy;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.BaseTest;
import com.easy.query.test.doc.dto.SysUserQueryRequest;
import com.easy.query.test.doc.entity.SysUser;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/11/21 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DocTest extends BaseTest {
    @Test
    public void test1() {
        SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setName("小明");
        sysUserQueryRequest.setCreateTimeBegin(LocalDateTime.now().plusDays(-10));
        sysUserQueryRequest.setCreateTimeEnd(LocalDateTime.now());
        sysUserQueryRequest.setPhone("180");

        {

            Supplier<Exception> f = () -> {
                try {
                    EasyPageResult<SysUser> pageResult = entityQuery.queryable(SysUser.class)
                            .whereObject(sysUserQueryRequest)
                            .toPageResult(1, 10);
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT COUNT(*) FROM `t_sys_user` WHERE `name` LIKE ? AND `phone` LIKE ? AND `create_time` >= ? AND `create_time` <= ?", easyQuerySQLStatementException.getSQL());

        }
        {

            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> users = entityQuery.queryable(SysUser.class)
                            .whereObject(sysUserQueryRequest)
                            .limit(0, 10).toList();
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT `id`,`name`,`account`,`depart_name`,`phone`,`create_time` FROM `t_sys_user` WHERE `name` LIKE ? AND `phone` LIKE ? AND `create_time` >= ? AND `create_time` <= ? LIMIT 10", easyQuerySQLStatementException.getSQL());

        }
        {

            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> pageResult = entityQuery.queryable(SysUser.class)
                            .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                            .where(o -> {
                                o.name().like(sysUserQueryRequest.getName());
                                o.account().like(sysUserQueryRequest.getAccount());
                                o.phone().like(sysUserQueryRequest.getPhone());
                                o.departName().like(sysUserQueryRequest.getDepartName());
                                o.createTime().rangeClosed(sysUserQueryRequest.getCreateTimeBegin(), sysUserQueryRequest.getCreateTimeEnd());
                            })
                            .toList();
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT `id`,`name`,`account`,`depart_name`,`phone`,`create_time` FROM `t_sys_user` WHERE `name` LIKE ? AND `phone` LIKE ? AND `create_time` >= ? AND `create_time` <= ?", easyQuerySQLStatementException.getSQL());

        }
        {


            Supplier<Exception> f = () -> {
                try {

                    List<SysUser> pageResult = entityQuery.queryable(SysUser.class)
                            .where(o -> {
                                o.name().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getName()), sysUserQueryRequest.getName());
                                o.account().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getAccount()), sysUserQueryRequest.getAccount());
                                o.phone().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getPhone()), sysUserQueryRequest.getPhone());
                                o.departName().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getDepartName()), sysUserQueryRequest.getDepartName());
                                o.createTime().rangeClosed(sysUserQueryRequest.getCreateTimeBegin() != null, sysUserQueryRequest.getCreateTimeBegin(), sysUserQueryRequest.getCreateTimeEnd() != null, sysUserQueryRequest.getCreateTimeEnd());
                            })
                            .toList();
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT `id`,`name`,`account`,`depart_name`,`phone`,`create_time` FROM `t_sys_user` WHERE `name` LIKE ? AND `phone` LIKE ? AND `create_time` >= ? AND `create_time` <= ?", easyQuerySQLStatementException.getSQL());

        }
        {


            Supplier<Exception> f = () -> {
                try {

                    List<SysUser> pageResult = entityQuery.queryable(SysUser.class)
                            .where(EasyStringUtil.isNotBlank(sysUserQueryRequest.getName()), o -> o.name().like(sysUserQueryRequest.getName()))
                            .where(EasyStringUtil.isNotBlank(sysUserQueryRequest.getAccount()), o -> o.account().like(sysUserQueryRequest.getAccount()))
                            .where(EasyStringUtil.isNotBlank(sysUserQueryRequest.getPhone()), o -> o.phone().like(sysUserQueryRequest.getPhone()))
                            .where(sysUserQueryRequest.getCreateTimeBegin() != null, o -> o.createTime().ge(sysUserQueryRequest.getCreateTimeBegin()))
                            .where(sysUserQueryRequest.getCreateTimeEnd() != null, o -> o.createTime().le(sysUserQueryRequest.getCreateTimeEnd()))
                            .toList();
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT `id`,`name`,`account`,`depart_name`,`phone`,`create_time` FROM `t_sys_user` WHERE `name` LIKE ? AND `phone` LIKE ? AND `create_time` >= ? AND `create_time` <= ?", easyQuerySQLStatementException.getSQL());

        }
    }


    @Test
    public void test2() {

        {

            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> users = entityQuery.queryable(SysUser.class)
                            .asTable("a222")
                            .where(o -> {
                                o.id().eq("1");
                                o.id().eq(false, "1");//true/false表示是否使用该条件默认true
                                o.id().like("123");
                                o.id().like(false, "123");
                            })
                            .groupBy(o -> o.id())
//                            .groupBy(o->o.id().then(o.name()))
//                            .groupBy(o->{
//                                return o.id().then(o.name());
//                            })
//                            .groupBy(o->o.FETCHER.id().name())
                            .fetcher(o -> o.id()._concat(o.id().count().as(o.phone())))
                            .toList();
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `phone` FROM `a222` t WHERE t.`id` = ? AND t.`id` LIKE ? GROUP BY t.`id`", easyQuerySQLStatementException.getSQL());

        }

        {

            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> users = entityQuery.queryable(SysUser.class)
                            .asTable("a222")
                            .where(o -> {
                                o.id().eq("1");
                                o.id().eq(o.createTime().format("yyyy-MM-dd"));
                                o.createTime().format("yyyy-MM-dd").eq("2023-01-02");
                                o.name().nullDefault("unknown").like("123");
                                o.phone().isNotBank();
                            })
                            .fetcher(o -> o.FETCHER.id().name().phone().departName())
                            .toList();
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`depart_name` FROM `a222` t WHERE t.`id` = ? AND  t.`id` = DATE_FORMAT(t.`create_time`,'%Y-%m-%d') AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') = ? AND IFNULL(t.`name`,?) LIKE ? AND (t.`phone` IS NOT NULL AND t.`phone` <> '' AND LTRIM(t.`phone`) <> '')", easyQuerySQLStatementException.getSQL());

        }
        {


            List<String> times = Collections.emptyList();
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.createTime().format("yyyy-MM-dd").in(Arrays.asList("2023-01-02", "2023-01-03"));
                        o.createTime().format("yyyy-MM-dd").notIn(Arrays.asList("2023-01-02", "2023-01-03"));
                        o.createTime().format("yyyy-MM-dd").in(times);
                        o.createTime().format("yyyy-MM-dd").notIn(times);
                    })
                    .fetcher(o -> o.FETCHER.allFieldsExclude(o.title(), o.top())).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') IN (?,?) AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') NOT IN (?,?) AND 1 = 2 AND 1 = 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2023-01-02(String),2023-01-03(String),2023-01-02(String),2023-01-03(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
    }

    @Test
    public void testLikeOr() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.or(() -> {
                        for (int i = 0; i < 3; i++) {
                            o.createTime().format("yyyy-MM-dd").likeMatchLeft("202" + i);
                        }
                    });
                })
                .fetcher(o -> o.FETCHER.allFieldsExclude(o.title(), o.top())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top` FROM `t_blog` t WHERE t.`deleted` = ? AND (DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? OR DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? OR DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020%(String),2021%(String),2022%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSubQuerySelect() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.createTime().format("yyyy-MM-dd").likeMatchLeft("2023");
                })
                .fetcher(o -> {

                    SQLSelectAsExpression subQuery = Select.subQueryAs(() -> {
                        return entityQuery.queryable(BlogEntity.class)
                                .where(x -> {
                                    x.id().eq(o.id());
                                })
                                .fetcher(x -> x.FETCHER.createTime());
                    }, o.createTime());

                    return Select.of(
                            o.FETCHER.allFieldsExclude(o.title(), o.top()),
                            subQuery
                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,(SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `create_time` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testSubQuerySelect2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.createTime().format("yyyy-MM-dd").likeMatchLeft("2023");
                })
                .fetcher(o -> {

                    SQLSelectAsExpression subQuery = Select.subQueryAs(() -> {
                        return entityQuery.queryable(BlogEntity.class)
                                .where(x -> {
                                    x.id().eq(o.id());
                                })
                                .fetcher(x -> x.id().count());
                    }, o.createTime());

                    return Select.of(
                            o.FETCHER.allFieldsExclude(o.title(), o.top()),
                            subQuery
                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,(SELECT COUNT(t1.`id`) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `create_time` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testTopic() {
//        Topic
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            long count = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().like("11");
                        o.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
                    }).count();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` WHERE `title` LIKE ? AND `create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%11%(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            long intCount = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().like("11");
                        o.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
                    }).intCount();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` WHERE `title` LIKE ? AND `create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%11%(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = entityQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                    })
                    .where((t, t1) -> {
                        t.title().like("11");
                        t1.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
                    })
                    .select((t,t1)->new TopicProxy(){{
                        selectColumns(t.FETCHER.id().stars(),t1.FETCHER.id().as(title()));
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t1.`id` AS `title` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`title` LIKE ? AND t1.`create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%11%(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = entityQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                    })
                    .where((t, t1) -> {
                        t.title().like("11");
                        t1.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
                    })
                    .select((t,t1)->new TopicProxy(){{
                        selectColumns(t.FETCHER.id().stars(),t1.FETCHER.id().as(title()));
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t1.`id` AS `title` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`title` LIKE ? AND t1.`create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%11%(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = entityQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                    })
                    .where((t, t1) -> {
                        t.title().like("11");
                        t1.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
                    })
                    .select((t,t1)->new TopicProxy(){{
                        selectColumns(t.id(),t1.stars(),t1.id().as(title()));
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t1.`stars`,t1.`id` AS `title` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`title` LIKE ? AND t1.`create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%11%(String),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = entityQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                    })
                    .orderBy((t, t1) -> {
                        t.id().asc();
                        t1.createTime().desc();
                    })
                    .select((t,t1)->{
                        return new TopicProxy(){{
                           selectColumns(t.allFieldsExclude(t.id(),t.title()));
                           selectColumns(t1.title().as(id()));
                        }};
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`create_time`,t1.`title` AS `id` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` ORDER BY t.`id` ASC,t1.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = entityQuery.queryable(Topic.class)
                    .where(o->{
                        o.title().like("123");
                        o.createTime().ge(LocalDateTime.of(2022,2,1,3,4));
                    })
                    .orderBy(o -> {
                        o.id().asc();
                        o.createTime().desc();
                    })
                    .fetcher(o->o.FETCHER.id().title())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`title` FROM `t_topic` t WHERE t.`title` LIKE ? AND t.`create_time` >= ? ORDER BY t.`id` ASC,t.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String),2022-02-01T03:04(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = entityQuery.queryable(Topic.class)
                    .where(o->{
                        o.title().like("123");
                        o.createTime().ge(LocalDateTime.of(2022,2,1,3,4));
                    })
                    .orderBy(o -> {
                        o.id().asc();
                        o.createTime().desc();
                    })
                    .fetcher(o->o.FETCHER.allFieldsExclude(o.id()))//返回所有字段除了id
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`title` LIKE ? AND t.`create_time` >= ? ORDER BY t.`id` ASC,t.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String),2022-02-01T03:04(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = entityQuery.queryable(Topic.class)
                    .where(o->{
                        o.title().like("123");
                        o.createTime().ge(LocalDateTime.of(2022,2,1,3,4));
                    })
                    .groupBy(o-> GroupBy.of(
                            o.id()
                    ))
                    .select(o->new TopicProxy(){{
                        id().set(o.id());
                        stars().set(o.id().count().setPropertyType(Integer.class));//count(id) as stars
        }})
//                    .selectAs(Topic.class,(o, tr)->Select.of(
//                            o.id(),
//                            o.id().count().as(tr.stars())//count(id) as stars
//                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t WHERE t.`title` LIKE ? AND t.`create_time` >= ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String),2022-02-01T03:04(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EasyPageResult<Topic> pageResult = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().like("123");
                        o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
                    })
                    .orderBy(o -> {
                        o.id().asc();
                        o.createTime().desc();
                    })
                    .fetcher(o -> o.FETCHER.id().title())
                    .toPageResult(1, 20);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` t WHERE t.`title` LIKE ? AND t.`create_time` >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String),2022-02-01T03:04(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = entityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().like("123");
                        o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
                    })
                    .orderBy(o -> {
                        o.id().asc();
                        o.createTime().desc();
                    })
                    .fetcher(o -> {
                        Fetcher fetcher = Select.createFetcher();
                        fetcher.fetch(o.id(), o.title());
                        fetcher.fetch(o.stars().as(o.stars()));
                        return fetcher;
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`stars` AS `stars` FROM `t_topic` t WHERE t.`title` LIKE ? AND t.`create_time` >= ? ORDER BY t.`id` ASC,t.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String),2022-02-01T03:04(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
}
