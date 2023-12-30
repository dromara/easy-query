package com.easy.query.test.doc;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.BaseTest;
import com.easy.query.test.doc.dto.SysUserQueryRequest;
import com.easy.query.test.doc.entity.SysUser;
import com.easy.query.test.doc.entity.proxy.SysUserProxy;
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
                    EasyPageResult<SysUser> pageResult = easyEntityQuery.queryable(SysUser.class)
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
                    List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
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
                    List<SysUser> pageResult = easyEntityQuery.queryable(SysUser.class)
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

                    List<SysUser> pageResult = easyEntityQuery.queryable(SysUser.class)
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

                    List<SysUser> pageResult = easyEntityQuery.queryable(SysUser.class)
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
                    List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                            .asTable("a222")
                            .where(o -> {
                                o.id().eq("1");
                                o.id().eq(false, "1");//true/false表示是否使用该条件默认true
                                o.id().like("123");
                                o.id().like(false, "123");
                            })
                            .groupByExpression(o -> o.id())
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
                    List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
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
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
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


        {

            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                            .asTable("a222")
                            .where(o -> {
                                o.id().eq("1");
                                o.id().eq(false, "1");//true/false表示是否使用该条件默认true
                                o.id().like("123");
                                o.id().like(false, "123");
                            })
                            .groupBy(o -> GroupKeys.of(o.id()))
                            .select(o -> new SysUserProxy() {{
                                id().set(o.key1());
                                phone().set(o.count().toStr());
                            }})
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
            Assert.assertEquals("SELECT t.`id` AS `id`,CAST(COUNT(*) AS CHAR) AS `phone` FROM `a222` t WHERE t.`id` = ? AND t.`id` LIKE ? GROUP BY t.`id`", easyQuerySQLStatementException.getSQL());

        }

        {

            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                            .asTable("a222")
                            .where(o -> {
                                o.id().eq("1");
                                o.id().eq(false, "1");//true/false表示是否使用该条件默认true
                                o.id().like("123");
                                o.id().like(false, "123");
                            })
                            .groupBy(o -> GroupKeys.of(o.id()))
                            .select(o -> {
                                SysUserProxy sysUserProxy = new SysUserProxy();
                                sysUserProxy.id().set(o.key1());
                                sysUserProxy.phone().set(o.count().toStr());
                                return sysUserProxy;
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
            Assert.assertEquals("SELECT t.`id` AS `id`,CAST(COUNT(*) AS CHAR) AS `phone` FROM `a222` t WHERE t.`id` = ? AND t.`id` LIKE ? GROUP BY t.`id`", easyQuerySQLStatementException.getSQL());

        }
    }

    @Test
    public void testLikeOr() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
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
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.createTime().format("yyyy-MM-dd").likeMatchLeft("2023");
                })
                .fetcher(o -> {
                    PropTypeColumn<BlogEntity> subQuery = o.subQuery(() -> {
                        return easyEntityQuery.queryable(BlogEntity.class)
                                .where(x -> {
                                    x.id().eq(o.id());
                                })
                                .fetcher(x -> x.FETCHER.createTime());
                    });
//                    SQLSelectAsExpression subQuery = Select.subQueryAs(() -> {
//                        return easyEntityQuery.queryable(BlogEntity.class)
//                                .where(x -> {
//                                    x.id().eq(o.id());
//                                })
//                                .fetcher(x -> x.FETCHER.createTime());
//                    }, o.createTime());

                    return Select.of(
                            o.FETCHER.allFieldsExclude(o.title(), o.top()),
                            subQuery.as(o.createTime())
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
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.createTime().format("yyyy-MM-dd").likeMatchLeft("2023");
                })
                .fetcher(o -> {

                    PropTypeColumn<BlogEntity> subQuery = o.subQuery(() -> {
                        return easyEntityQuery.queryable(BlogEntity.class)
                                .where(x -> {
                                    x.id().eq(o.id());
                                })
                                .fetcher(x -> x.id().count());
                    });
//                    SQLSelectAsExpression subQuery = Select.subQueryAs(() -> {
//                        return easyEntityQuery.queryable(BlogEntity.class)
//                                .where(x -> {
//                                    x.id().eq(o.id());
//                                })
//                                .fetcher(x -> x.id().count());
//                    }, o.createTime());

                    return Select.of(
                            o.FETCHER.allFieldsExclude(o.title(), o.top()),
                            subQuery.as(o.createTime())
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
            long count = easyEntityQuery.queryable(Topic.class)
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
            long intCount = easyEntityQuery.queryable(Topic.class)
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
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                    })
                    .where((t, t1) -> {
                        t.title().like("11");
                        t1.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
                    })
                    .select((t, t1) -> new TopicProxy() {{
                        selectExpression(t.FETCHER.id().stars(), t1.FETCHER.id().as(title()));
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
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                    })
                    .where((t, t1) -> {
                        t.title().like("11");
                        t1.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
                    })
                    .select((t, t1) -> new TopicProxy() {{
                        selectExpression(t.FETCHER.id().stars(), t1.FETCHER.id().as(title()));
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
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                    })
                    .where((t, t1) -> {
                        t.title().like("11");
                        t1.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
                    })
                    .select((t, t1) -> new TopicProxy() {{
                        selectExpression(t.id(), t1.stars(), t1.id().as(title()));
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
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                    })
                    .orderBy((t, t1) -> {
                        t.id().asc();
                        t1.createTime().desc();
                    })
                    .select((t, t1) -> {
                        return new TopicProxy() {{

                            selectAll(t);
                            selectIgnores(t.title(), t.id());
                            selectExpression(t1.title().as(id()));
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
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().like("123");
                        o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
                    })
                    .orderBy(o -> {
                        o.id().asc();
                        o.createTime().desc();
                    })
                    .fetcher(o -> o.FETCHER.id().title())
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
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().like("123");
                        o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
                    })
                    .orderBy(o -> {
                        o.id().asc();
                        o.createTime().desc();
                    })
                    .fetcher(o -> o.FETCHER.allFieldsExclude(o.id()))//返回所有字段除了id
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
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().like("123");
                        o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
                    })
                    .groupByExpression(o -> GroupKeys.expressions(
                            o.id()
                    ))
                    .select(o -> new TopicProxy() {{
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
            EasyPageResult<Topic> pageResult = easyEntityQuery.queryable(Topic.class)
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
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().like("123");
                        o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
                    })
                    .orderBy(o -> {
                        o.id().asc();
                        o.createTime().desc();
                    })
                    .fetcher(o -> {
                        return o.FETCHER.id().title().stars().as(o.stars());
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`stars` AS `stars` FROM `t_topic` t WHERE t.`title` LIKE ? AND t.`create_time` >= ? ORDER BY t.`id` ASC,t.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String),2022-02-01T03:04(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDoc3x() {
//        List<SysUser> list = easyQuery.queryable(SysUser.class)
//                .where(o -> {
//                    o.eq(SysUser::getId, "1")
//                            .eq(false, SysUser::getId, "1")
//                            .like(SysUser::getId, "123")
//                            .like(false, SysUser::getId, "123");
//                })
//                .groupBy(o -> o.column(SysUser::getId))
//                .select(SysUser.class, o -> {
//                    o.columnAs(SysUser::getId, SysUser::getId)
//                            .columnCountAs(SysUser::getId, SysUser::getPhone);
//                }).toList();
//
//        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
//                 .where(o -> {
//                     o.id().eq("1");
//                     o.id().eq(false, "1");//true/false表示是否使用该条件默认true
//                     o.id().like("123");
//                     o.id().like(false, "123");
//                 })
//                 .groupBy(o->GroupKeys.of(o.id()))//创建group by
//                 .select(o -> new SysUserProxy(){{//创建user代理
//                     id().set(o.key1());//对当前id进行赋值
//                     phone().set(o.count().toStr());//对当前phone进行赋值因为phone是string类型所以goup后的count需要强转成string也就是cast
//                 }})
        //下面是平替写法其实是一样的
        // .select(o -> {
        //     SysUserProxy sysUserProxy = new SysUserProxy();
        //     sysUserProxy.id().set(o.key1());
        //     sysUserProxy.phone().set(o.count().toStr());
        //     return sysUserProxy;
        // })
//                 .toList();

//                 List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
//                 .where(o->{
//                     o.id().eq("1");// t.`id` = 1
//                     o.id().eq(o.createTime().format("yyyy-MM-dd"));// t.`id` = DATE_FORMAT(t.`create_time`,'%Y-%m-%d')
//                     o.createTime().format("yyyy-MM-dd").eq("2023-01-02");//DATE_FORMAT(t.`create_time`,'%Y-%m-%d') = '2023-01-02'
//                     o.name().nullDefault("unknown").like("123");
//                     o.phone().isNotBank();
//                 })
//                 //可以使用select也可以使用fetcher来实现 fetcher适合返回单个对象的数据获取
//                 .fetcher(o->o.FETCHER.id().name().phone().departName())
//                 .toList();

//        List<SysUser> list = easyQuery.queryable(SysUser.class)
//                .where(o -> {
//                    LambdaSQLFunc<SysUser> fx = o.fx();
//                    o.eq(SysUser::getId, "1");
//                    o.eq(fx.dateTimeFormat(SysUser::getCreateTime, "yyyy-MM-dd"), "2023-01-01");
//                    o.isNotBank(SysUser::getPhone);
//                })
//                .select(o -> o.column(SysUser::getId).column(SysUser::getName).column(SysUser::getPhone).column(SysUser::getDepartName))
//                .toList();

//        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
//                .where(o -> {
//                    o.id().eq("1");
//                    o.id().eq(false, "1");//true/false表示是否使用该条件默认true
//                    o.id().like("123");
//                    o.id().like(false, "123");
//                })
//                .groupBy(o->GroupKeys.of(o.id()))//创建group by
//                .select(o -> new SysUserProxy(){{//创建user代理
//                    id().set(o.key1());//对当前id进行赋值
//                    phone().set(o.count().toStr());//对当前phone进行赋值因为phone是string类型所以goup后的count需要强转成string也就是cast
//                }})
//                //下面是平替写法其实是一样的
//                // .select(o -> {
//                //     SysUserProxy sysUserProxy = new SysUserProxy();
//                //     sysUserProxy.id().set(o.key1());
//                //     sysUserProxy.phone().set(o.count().toStr());
//                //     return sysUserProxy;
//                // })
//                .toList();


//        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
//                .where(o->{
//                    o.id().eq("1");// t.`id` = 1
//                    o.id().eq(o.createTime().format("yyyy-MM-dd"));// t.`id` = DATE_FORMAT(t.`create_time`,'%Y-%m-%d')
//                    o.createTime().format("yyyy-MM-dd").eq("2023-01-02");//DATE_FORMAT(t.`create_time`,'%Y-%m-%d') = '2023-01-02'
//                    o.name().nullDefault("unknown").like("123");
//                    o.phone().isNotBank();
//                })
//                //可以使用select也可以使用fetcher来实现 fetcher适合返回单个对象的数据获取
//                .fetcher(o->o.FETCHER.id().name().phone().departName())
//                .toList();
        SysUserProxy utable = SysUserProxy.createTable();
        List<SysUser> list = easyProxyQuery.queryable(utable)
                .where(o -> {
                    o.eq(utable.id(), "1")
                            .eq(utable.id(), utable.createTime().format("yyyy-MM-dd"))
                            .eq(utable.createTime().format("yyyy-MM-dd"),"2023-01-01")
                            .eq(utable.createTime().format("yyyy-MM-dd"),utable.name().nullDefault("unknown"))
                            .like(utable.name().nullDefault("unknown"),"123")
                            .isNotBank(utable.phone());
                })
                .groupBy(o -> o.column(utable.id()))
                .select(SysUserProxy.createTable(), o -> o.columns(utable.id(),utable.name(),utable.phone(),utable.departName()))
                .toList();
//
//                List<SysUser> list = easyQueryClient.queryable(SysUser.class)
//                .where(o -> {
//                    SQLFunc fx = o.fx();
//                    o.eq("id", "1");
//                    o.eq(fx.dateTimeFormat("createTime", "yyyy-MM-dd"), "2023-01-01");
//                    o.isNotBank("phone");
//                })
//                .select(o -> o.column("id").column("name").column("phone").column("departName"))
//                .toList();


//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .where(o->{
//                    o.title().like("123");
//                    o.createTime().ge(LocalDateTime.of(2022,2,1,3,4));
//                })
//                //会生成{key1:x,key2:x.... group:{t1:xx,t2:xx}}其中key1...keyn表示key默认支持10个 t1...tn表示前面的表
//                //无论join了多少张表group后全部只有一个入参参数其余参数在group属性里面
//                .groupBy(o-> GroupKeys.of(o.id()))
//                .select(o->new TopicProxy(){{
//                    id().set(o.key1());//key1就是id
//                    stars().set(o.intCount());//COUNT(*)返回int 默认返回long类型
//                }})
//                .toList();


//        //草稿模式无需定义返回结果,返回草稿支持1-10 Draft1-Draft10
//        List<Draft3<String, Integer, Integer>> list = easyEntityQuery.queryable(Topic.class)
//                .where(o -> {
//                    o.title().like("123");
//                    o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
//                })
//                .groupBy(o -> GroupKeys.of(o.id()))
//                .select(o -> new TopicProxy() {{
//                    id().set(o.key1());//key1就是id
//                    stars().set(o.intCount());//COUNT(*)返回int 默认返回long类型
//                }})
//                .selectDraft(o -> Select.draft(
//                        o.id().nullDefault("123"),//如果为空就赋值123
//                        o.stars(),
//                        o.stars().abs()//取绝对值
//                ))
//                .toList();

//
//        EasyPageResult<Topic> pageResult = easyEntityQuery.queryable(Topic.class)
//                .where(o -> {
//                    o.title().like("123");
//                    o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
//                })
//                .orderBy(o -> {
//                    o.id().asc();
//                    o.createTime().desc();
//                })
//                .select(o -> new TopicProxy(){{
//                    selectExpression(o.id(),o.title());
//                }})
//                .toPageResult(1, 20);

//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(Topic.class, (t, t1) -> {//第一个参数t表示第一个表,第二个参数t1表示第二个表
//                    t.id().eq(t1.id());// ON t.`id` = t1.`id`
//                })
//                .where((t, t1) -> {
//                    t.title().like("11");
//                    t1.createTime().le(LocalDateTime.of(2021, 1, 1, 1, 1));
//                })
//                .select((t, t1) -> new TopicProxy() {{
//                    id().set(t.id());
//                    stars().set(t.stars());
//                    title().set(t1.id());
//                }}).toList();



//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(Topic.class, (t, t1) -> {
//                    t.id().eq(t1.id());
//                })
//                .orderBy((t, t1) -> {
//                    t.id().asc();
//                    t1.createTime().desc();
//                })
//                //查询t表的所有除了id和title,并且返回t1的title取别名为content
//                .select((t,t1)->new TopicProxy(){{
//                    selectAll(t);
//                    selectIgnores(t.id(),t.title());
//                    id().set(t1.title());
//                }})
//                .toList();
    }
}
