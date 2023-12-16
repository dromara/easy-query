package com.easy.query.test.doc;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.BaseTest;
import com.easy.query.test.doc.dto.SysUserQueryRequest;
import com.easy.query.test.doc.entity.SysUser;
import com.easy.query.test.entity.BlogEntity;
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
                                o.name().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getName()),sysUserQueryRequest.getName());
                                o.account().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getAccount()),sysUserQueryRequest.getAccount());
                                o.phone().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getPhone()),sysUserQueryRequest.getPhone());
                                o.departName().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getDepartName()),sysUserQueryRequest.getDepartName());
                                o.createTime().rangeClosed(sysUserQueryRequest.getCreateTimeBegin() != null,sysUserQueryRequest.getCreateTimeBegin(),sysUserQueryRequest.getCreateTimeEnd() != null, sysUserQueryRequest.getCreateTimeEnd());
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
                            .where(EasyStringUtil.isNotBlank(sysUserQueryRequest.getName()),o->o.name().like(sysUserQueryRequest.getName()))
                            .where(EasyStringUtil.isNotBlank(sysUserQueryRequest.getAccount()),o->o.account().like(sysUserQueryRequest.getAccount()))
                            .where(EasyStringUtil.isNotBlank(sysUserQueryRequest.getPhone()),o->o.phone().like(sysUserQueryRequest.getPhone()))
                            .where(sysUserQueryRequest.getCreateTimeBegin() != null,o->o.createTime().ge(sysUserQueryRequest.getCreateTimeBegin()))
                            .where(sysUserQueryRequest.getCreateTimeEnd() != null,o->o.createTime().le(sysUserQueryRequest.getCreateTimeEnd()))
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
    public void test2(){

        {

            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> users = entityQuery.queryable(SysUser.class)
                            .asTable("a222")
                            .where(o->{
                                o.id().eq("1");
                                o.id().eq(false,"1");//true/false表示是否使用该条件默认true
                                o.id().like("123");
                                o.id().like(false,"123");
                            })
                            .groupBy(o->o.id())
//                            .groupBy(o->o.id().then(o.name()))
//                            .groupBy(o->{
//                                return o.id().then(o.name());
//                            })
//                            .groupBy(o->o.FETCHER.id().name())
                            .select(o->o.id().concat(o.id().count().as(o.phone())))
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
                            .where(o->{
                                o.id().eq("1");
                                o.id().eq(o.createTime().dateTimeFormat("yyyy-MM-dd"));
                                o.createTime().dateTimeFormat("yyyy-MM-dd").eq("2023-01-02");
                                o.name().nullDefault("unknown").like("123");
                                o.phone().isNotBank();
                            })
                            .select(o->o.FETCHER.id().name().phone().departName())
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
                        o.createTime().dateTimeFormat("yyyy-MM-dd").in(Arrays.asList("2023-01-02","2023-01-03"));
                        o.createTime().dateTimeFormat("yyyy-MM-dd").notIn(Arrays.asList("2023-01-02","2023-01-03"));
                        o.createTime().dateTimeFormat("yyyy-MM-dd").in(times);
                        o.createTime().dateTimeFormat("yyyy-MM-dd").notIn(times);
                    })
                    .select(o->o.FETCHER.allFieldsExclude(o.title(),o.top())).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') IN (?,?) AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') NOT IN (?,?) AND 1 = 2 AND 1 = 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2023-01-02(String),2023-01-03(String),2023-01-02(String),2023-01-03(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
    }

    @Test
    public  void testLikeOr(){

        List<String> times = Collections.emptyList();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.or(()->{
                        for (int i = 0; i < 3; i++) {
                            o.createTime().dateTimeFormat("yyyy-MM-dd").likeMatchLeft("202"+i);
                        }
                    });
                })
                .select(o->o.FETCHER.allFieldsExclude(o.title(),o.top())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top` FROM `t_blog` t WHERE t.`deleted` = ? AND (DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? OR DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? OR DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020%(String),2021%(String),2022%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
