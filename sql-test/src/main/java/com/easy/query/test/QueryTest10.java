package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyTypeUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
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
import java.util.function.Function;

/**
 * create time 2023/12/29 16:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest10 extends BaseTest{

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
                .select(o -> o.FETCHER.allFieldsExclude(o.title(), o.top())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top` FROM `t_blog` t WHERE t.`deleted` = ? AND 1 = 2 AND 1 = 1 AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') BETWEEN ? AND ? AND (DATE_FORMAT(t.`create_time`,'%Y-%m-%d') > ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') < ?) AND (DATE_FORMAT(t.`create_time`,'%Y-%m-%d') > ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') <= ?) AND (DATE_FORMAT(t.`create_time`,'%Y-%m-%d') >= ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') < ?) AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') IS NULL AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') IS NOT NULL AND 1 = 2 AND 1 = 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
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
//                        t.stars().eq(0);
                    })
                    .select((t, t1, t2) -> new QueryVOProxy().adapter(r->{
                        r.selectExpression(
                                t.id(),
                                t1.title().as(r.field1()),
                                t2.id().as(r.field2())
                        );
                    })).toList();
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
                    .select((t, t1, t2) -> new QueryVOProxy().adapter(r->{
                        r.selectAll(t);
                        r.selectIgnores(t.title());
                        r.selectExpression(
                                t1.title().as(r.field1()),
                                t2.id().as(r.field2())
                        );
                    })).toList();
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
                    .select(o -> new BlogEntityProxy().adapter(r->{

                        r.selectAll(o);
                        r.selectIgnores(o.title(),o.top());
                        r.star().setSubQuery(
                                easyEntityQuery.queryable(BlogEntity.class)
                                        .where(x -> {
                                            x.id().eq(o.id());
                                        })
                                        .selectCount(Integer.class)
                        );
                    })).toList();
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
                    .select(o -> new BlogEntityProxy().adapter(r->{

                        r.selectAll(o);
                        r.selectIgnores(o.title(),o.top());
                        r.score().setSubQuery(easyEntityQuery.queryable(BlogEntity.class)
                                .where(x -> {
                                    x.id().eq(o.id());
                                })
                                .selectCount(BigDecimal.class));
                    })).toList();
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
                        o.expression().exists(() -> {
                            return o.expression().subQueryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()));
                        });
                    })
                    .select(o -> new BlogEntityProxy().adapter(r->{


                        Query<BigDecimal> subQuery = easyEntityQuery.queryable(BlogEntity.class)
                                .where(x -> {
                                    x.id().eq(o.id());
                                })
                                .selectCount(BigDecimal.class);

                        r.selectAll(o);
                        r.selectIgnores(o.title(),o.top());

                        r.score().setSubQuery(subQuery);
                    })).toList();
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
                        o.expression().exists(() -> {
                            return o.expression().subQueryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()))
                                    .where(x -> x.id().in(roleIds));
                        });
                    })
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                        t1.title().like("123");
                    })
                    .select((t, t1) -> new BlogEntityProxy().adapter(r->{

                        r.selectExpression(
                                t.FETCHER.id().content().createTime(),
                                t1.stars()
                        );
                    }))
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
                        o.expression().exists(() -> {
                            return o.expression().subQueryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()))
                                    .where(x -> x.id().in(roleIds));
                        });
                    })
                    .leftJoin(Topic.class, (t, t1) -> {
                        t.id().eq(t1.id());
                        t1.title().like("123");
                    })
                    .select((t, t1) -> new BlogEntityProxy().adapter(r->{

                        r.selectExpression(
                                t.FETCHER.id().content().createTime(),
                                t1.stars()
                        );
                    }))
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
                    }).groupBy(o -> GroupKeys.of(o.id()))
                    .having(o -> {
                        o.key1().max().like("1");
                        o.key1().max().like(false, "2");
                        o.key1().max().likeMatchLeft("3");
                        o.key1().max().likeMatchLeft(false, "4");
                        o.key1().max().likeMatchRight("5");
                        o.key1().max().likeMatchRight(false, "6");


                        o.key1().max().notLike("1");
                        o.key1().max().notLike(false, "2");
                        o.key1().max().notLikeMatchLeft("3");
                        o.key1().max().notLikeMatchLeft(false, "4");
                        o.key1().max().notLikeMatchRight("5");
                        o.key1().max().notLikeMatchRight(false, "6");
                    })
                    .select(o -> new BlogEntityProxy().selectExpression(o.key1()))
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
                    }).groupBy(o -> GroupKeys.of(o.id()))
                    .having(o -> {
                        o.key1().max().eq("1");
                        o.key1().max().eq(false, "2");
                        o.key1().max().ge("3");
                        o.key1().max().ge(false, "4");
                        o.key1().max().gt("5");
                        o.key1().max().gt(false, "6");


                        o.key1().max().ne("1");
                        o.key1().max().ne(false, "2");
                        o.key1().max().le("3");
                        o.key1().max().le(false, "4");
                        o.key1().max().lt("5");
                        o.key1().max().lt(false, "6");
                    })
                    .select(o -> new BlogEntityProxy().selectExpression(o.key1()))
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
                    }).groupBy(o -> GroupKeys.of(o.id()))
                    .having(o -> {
                        o.key1().max().eq(o.key1().min());
                        o.key1().max().eq(false, o.key1().min());
                        o.key1().max().ge(o.key1().min());
                        o.key1().max().ge(false, o.key1().min());
                        o.key1().max().gt(o.key1().min());
                        o.key1().max().gt(false, o.key1().min());


                        o.key1().max().ne(o.key1().min());
                        o.key1().max().ne(false, o.key1().min());
                        o.key1().max().le(o.key1().min());
                        o.key1().max().le(false, "4");
                        o.key1().max().lt(o.key1().min());
                        o.key1().max().lt(false, o.key1().min());
                    })
                    .select(o -> new BlogEntityProxy().selectExpression(o.key1()))
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
                .groupBy((t, t1) -> GroupKeys.of(t1.id()))
                .select((g) -> new BlogEntityProxy().adapter(r->{
                    r.selectExpression(g.key1());
                    r.score().set(g.groupTable().t2.score().sum());
                }))
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
                    .groupBy(t ->GroupKeys.of( t.id()))
                    .select(t -> Select.DRAFT.of(t.key1()))
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
                    .groupBy(t ->GroupKeys.of( t.id(),t.title()))
                    .select(t -> Select.DRAFT.of(t.key1()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1` FROM `t_topic` t GROUP BY t.`id`,t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Class<Draft1<String>> typeClass = EasyTypeUtil.cast(Draft1.class);
            List<Draft1<String>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> GroupKeys.of(t.id()))
                    .select(t -> Select.DRAFT.of(t.key1()))
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
                    .groupBy(t -> GroupKeys.of(t.id()))
                    .select(t -> Select.DRAFT.of(
                            t.key1(),
                            t.count()
                    ))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,COUNT(*) AS `value2` FROM `t_topic` t GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Long, Integer>> list = easyEntityQuery
                    .queryable(Topic.class)
                    .groupBy(t -> GroupKeys.of(t.id()))
                    .select(g -> Select.DRAFT.of(g.key1(), g.count(), g.sum(s->s.stars())))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,COUNT(*) AS `value2`,SUM(t.`stars`) AS `value3` FROM `t_topic` t GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
            Integer value3 = list.get(0).getValue3();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft2<String, String>> list = easyEntityQuery
                    .queryable(ValueCompany.class)
                    .select(t -> Select.DRAFT.of(t.id(), t.address().province()))
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
                    .select(t -> Select.DRAFT.of(t.id(), t.createTime()))
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
                    .select(t -> Select.DRAFT.of(t.id(),
                            t.createTime(),
                            t.expression().sqlSegment("1").asAnyType(String.class)
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
                    .select(t -> Select.DRAFT.of(t.id(),
                            t.createTime(),
                            t.expression().sqlSegment("IFNULL({0},'1')", c -> c.keepStyle().expression(t.title())).asAnyType(String.class)
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

        {
            List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                    .orderBy(t -> {
                        t.id().asc();
                    })
                    .select(o -> new MapProxy().adapter(r->{
                        r.put("a", o.title());
                        r.put("b", o.id());
                        r.put("c", o.stars());
                    })).toList();
            System.out.println(list);
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                    .orderBy(t -> {
                        t.id().asc();
                    })
                    .select(o -> new MapProxy().put(o.title().nullOrDefault("1")).put(o.id()).put(o.stars())).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT IFNULL(t.`title`,?) AS `title`,t.`id` AS `id`,t.`stars` AS `stars` FROM `t_topic` t ORDER BY t.`id` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            listenerContextManager.clear();
        }



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
                .select((t, t1) -> Select.DRAFT.of(
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
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.count()
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,COUNT(*) AS `value2` FROM `t_topic` t WHERE t.`title` LIKE ? AND t.`create_time` >= ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
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
//                    o.title().toUpper().ne(new LocalDateTimeProxy(LocalDateTime.of(2021,1,1,1,1)).format("yyyy-MM-dd"));
                })
                .select(o -> Select.DRAFT.of(
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
                .select(o -> Select.DRAFT.of(
                        o.id(),
                        o.title().toLower().replace("123","456"),
                        o.title().toUpper(),
                        o.title().toLower().subString(1, 2)
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,REPLACE(LOWER(t.`title`),?,?) AS `value2`,UPPER(t.`title`) AS `value3`,SUBSTR(LOWER(t.`title`),2,2) AS `value4` FROM `t_topic` t WHERE SUBSTR(t.`title`,2,2) = ? AND SUBSTR(LOWER(t.`title`),2,2) = ? AND SUBSTR(LOWER(UPPER(LOWER(t.`title`))),2,2) = ? AND SUBSTR(LOWER(DATE_FORMAT(t.`create_time`,'%Y-%m')),2,10) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),456(String),123(String),123(String),123(String),%023-01%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
                .select(o -> Select.DRAFT.of(
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
                    o.title().ltrim().subString(1, 2).eq("123");
                    o.title().ltrim().toLower().subString(1, 2).eq("123");
                    o.title().toLower().ltrim().toUpper().toLower().subString(1, 2).eq("123");
                    o.createTime()
                            .format("yyyy-MM")//日期先格式化
                            .toLower()//然后转成小写
                            .subString(1, 10)//分割从第一位
                            .ltrim()
                            .like("023-01");
                })
                .select(o -> Select.DRAFT.of(
                        o.id(),
                        o.title().toLower().ltrim(),
                        o.title().ltrim().toUpper(),
                        o.title().toLower().ltrim().subString(1, 2)
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
                    o.title().rtrim().subString(1, 2).eq("123");
                    o.title().rtrim().toLower().subString(1, 2).eq("123");
                    o.title().toLower().rtrim().toUpper().toLower().subString(1, 2).eq("123");
                    o.createTime()
                            .format("yyyy-MM")//日期先格式化
                            .toLower()//然后转成小写
                            .subString(1, 10)//分割从第一位
                            .rtrim()
                            .like("023-01");
                })
                .select(o -> Select.DRAFT.of(
                        o.id(),
                        o.title().toLower().rtrim(),
                        o.title().rtrim().toUpper(),
                        o.title().toLower().rtrim().subString(1, 2)
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
                    o.title().rtrim().ltrim().eq(o.id().ltrim());
                    o.createTime().format("yyyy-MM-dd").subString(0, 4).eq("2021");
                })
                .select(o -> Select.DRAFT.of(
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
                    o.title().rtrim().ltrim().replace("title", "abc").like("abc");
                })
                .select(o -> Select.DRAFT.of(
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
                .select(o -> Select.DRAFT.of(
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
                .select(o -> Select.DRAFT.of(
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
                .select(o -> Select.DRAFT.of(
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
                .groupBy(o-> GroupKeys.of(o.content().subString(0,8)))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.join(x->x.id(),",")
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
////                .select(o -> Select.DRAFT.of(
////                        o.groupKeys().key1(),
////                        o.count(x->x.title())
////                )).toList();
//    }
//
//    @Test
//    public void test111(){
//        Queryable2<TodoSingleRecord, Topic> todoSingleRecordTopicQueryable2 = easyQuery.queryable(TodoSingleRecord.class)
//                .leftJoin(Topic.class, (a, b) -> a.eq(b, TodoSingleRecord::getTodoId, Topic::getId));
//        Queryable<TodoSingleRecord> x=todoSingleRecordTopicQueryable2;
//
////        Queryable<TodoSingleRecord> queryable = easyQuery.queryable(TodoSingleRecord.class);
////        Queryable<TodoSingleRecord> queryable1 = easyQuery.queryable(TodoSingleRecord.class);
////        EntityTableExpressionBuilder table = queryable.getSQLEntityExpressionBuilder().getTable(0);
////        EntityTableExpressionBuilder table1 = queryable1.getSQLEntityExpressionBuilder().getTable(0);
////        boolean b = table == table1;
////        System.out.println(b);
//
//        List<TodoListVo> list = easyQuery.queryable(TodoSingleRecord.class)
//                .include(o -> o.many(TodoSingleRecord::getTodoExecutorsList)
//                        .where(p1 -> p1.eq(TodoExecutors::getType, 0)))
//                .include(o -> o.many(TodoSingleRecord::getTodoExecutorsJoinList)
//                        .where(p1 -> p1.eq(TodoExecutors::getType, 1)))
//                .select(TodoListVo.class, p1 -> {
//                    p1.columnAll();
//                    p1.columnIncludeMany(TodoSingleRecord::getTodoExecutorsList, TodoListVo::getTodoExecutorsList);
//                    p1.columnIncludeMany(TodoSingleRecord::getTodoExecutorsJoinList, TodoListVo::getTodoExecutorsJoinList);
//                })
//                .toList();
//        System.out.println(list);
//
//        List<TodoListVo> list2 = easyQuery.queryable(TodoSingleRecord.class)
//                .groupBy(o -> o.column(TodoSingleRecord::getTodoId))
//                .include(o -> o.many(TodoSingleRecord::getTodoExecutorsList)
//                        .where(p1 -> p1.eq(TodoExecutors::getType, 0)))
//                .include(o -> o.many(TodoSingleRecord::getTodoExecutorsJoinList)
//                        .where(p1 -> p1.eq(TodoExecutors::getType, 1)))
//                .select(TodoListVo.class, p1 -> {
//                    p1.columnAs(TodoSingleRecord::getTodoId, TodoListVo::getTodoId);
//                    p1.columnIncludeMany(TodoSingleRecord::getTodoExecutorsList, TodoListVo::getTodoExecutorsList);
//                    p1.columnIncludeMany(TodoSingleRecord::getTodoExecutorsJoinList, TodoListVo::getTodoExecutorsJoinList);
//                })
//                .toList();
//
//
//
//
//        List<TodoSingleRecord> list1 = easyQuery.queryable(TodoSingleRecord.class)
//                .include(o -> o.many(TodoSingleRecord::getTodoExecutorsList)
//                        .where(p1 -> p1.eq(TodoExecutors::getType, 0)))
//                .include(o -> o.many(TodoSingleRecord::getTodoExecutorsJoinList)
//                        .where(p1 -> p1.eq(TodoExecutors::getType, 1)))
//                .toList();
//        System.out.println(list1);
//    }

    @Test
    public void test222(){
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class).orderBy(o -> {
            o.createTime().format("yyyy-MM-dd").asc();
        }).toList();
        List<Draft1<String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(o -> GroupKeys.of(o.content().subString(0, 8)))
                .select(o -> Select.DRAFT.of(o.key1()))
                .toList();
//        easyEntityQuery.queryable(BlogEntity.class)
//                .groupBy1(k->k.of(k.groupTable().createTime()))
    }
    @Test
    public void test223(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().ne("title0");//==0
                    o.or(()->{
                        o.title().eq("1");
                        o.title().eq("2");
                        o.title().eq("3");
                        o.and(()->{
                            o.title().eq("4");
                            o.title().eq("5");
                            o.title().eq("6");
                            o.or(()->{
                                o.title().eq("7");
                                o.title().eq("8");
                                o.title().eq("9");
                            });
                            o.title().eq("10");
                            o.title().eq("11");
                            o.title().eq("12");
                        });

                        o.title().eq("13");
                        o.title().eq("14");
                        o.title().eq("15");
                    });
                })
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `title` <> ? AND (`title` = ? OR `title` = ? OR `title` = ? OR (`title` = ? AND `title` = ? AND `title` = ? AND (`title` = ? OR `title` = ? OR `title` = ?) AND `title` = ? AND `title` = ? AND `title` = ?) OR `title` = ? OR `title` = ? OR `title` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("title0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

}
