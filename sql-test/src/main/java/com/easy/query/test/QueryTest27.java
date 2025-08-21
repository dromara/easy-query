package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.enums.MapKeyModeEnum;
import com.easy.query.api.proxy.util.EasyProxyParamExpressionUtil;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityValueState;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.SubQueryModeEnum;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.UUIDEntity2;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * create time 2025/7/16 21:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest27 extends BaseTest {
    @Test
    public void testRandom() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .orderBy(t_topic -> {
                    t_topic.expression().random().asc();
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` ORDER BY RAND() ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void selectAllMap() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(o -> {
                    o.expression().random().asc();
                }).select(t_blog -> new MapProxy().selectAll(t_blog, MapKeyModeEnum.FIELD_NAME))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,t.`create_time` AS `createTime`,t.`update_time` AS `updateTime`,t.`create_by` AS `createBy`,t.`update_by` AS `updateBy`,t.`deleted` AS `deleted`,t.`title` AS `title`,t.`content` AS `content`,t.`url` AS `url`,t.`star` AS `star`,t.`publish_time` AS `publishTime`,t.`score` AS `score`,t.`status` AS `status`,t.`order` AS `order`,t.`is_top` AS `isTop`,t.`top` AS `top` FROM `t_blog` t WHERE t.`deleted` = ? ORDER BY RAND() ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Map<String, Object> stringObjectMap : list) {

            Object o = stringObjectMap.get("createTime");
            Assert.assertNotNull(o);
        }
    }


    @Test
    public void testDiff() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();

            Topic topic = new Topic();
            topic.setId("123");
            topic.setTitle("456");

            easyEntityQuery.addTracking(topic);


            topic.setStars(1);
            topic.setTitle("567");


            EntityState entityState = easyEntityQuery.getTrackEntityStateNotNull(topic);
            EntityValueState entityValueState = entityState.getEntityValueState("title");
            Assert.assertTrue(entityValueState.isChanged());
            System.out.println("title是否有变动:" + entityValueState.isChanged());
            if (entityValueState.isChanged()) {
                System.out.println("title变动了:" + entityValueState.getOriginal() + "------->" + entityValueState.getCurrent());
            }
            Assert.assertTrue(entityValueState.isChanged());


        } finally {

            trackManager.release();
        }
    }

    @Test
    public void testDiff2() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();

            Topic topic = new Topic();
            topic.setId("123");
            topic.setTitle("456");

            easyEntityQuery.addTracking(topic);


            topic.setStars(1);
            topic.setTitle("456");


            EntityState entityState = easyEntityQuery.getTrackEntityStateNotNull(topic);

            EntityValueState entityValueState = entityState.getEntityValueState("title");
            Assert.assertFalse(entityValueState.isChanged());
            System.out.println("title是否有变动:" + entityValueState.isChanged());
            if (entityValueState.isChanged()) {
                System.out.println("title变动了:" + entityValueState.getOriginal() + "------->" + entityValueState.getCurrent());
            }
            Assert.assertFalse(entityValueState.isChanged());


        } finally {

            trackManager.release();
        }
    }

    @Test
    public void testAAA() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(o -> {
                    o.myBlog().configure(r -> r.disableLogicDelete());
                    o.myBlog().star().eq(1);
//                    o.blogs().where(o->o._configurer().)
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `t_blog` t1 ON t1.`id` = t.`id` WHERE t1.`star` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testAAA1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(o -> {
                    o.myBlog().star().eq(1);
//                    o.blogs().where(o->o._configurer().)
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t1.`id` = t.`id` WHERE t1.`star` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testAAA2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                    .where(o -> {
                        o.myBlog().configure(r -> r.asTable("BlogAbc"));
                        o.myBlog().star().eq(1);
//                    o.blogs().where(o->o._configurer().)
                    }).toList();
        } catch (Exception ex) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `BlogAbc` t1 ON t1.`deleted` = ? AND t1.`id` = t.`id` WHERE t1.`star` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testAAA4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                    .where(o -> {
                        o.myBlog().configure(r -> r.asTable("BlogAbc"));
                        o.myBlog().star().eq(1);
                        o.blogs().where(x -> {
                            x.configure(r -> r.asTable("BInner"));
                            x.star().eq(1);
                        }).any();
//                    o.blogs().where(o->o._configurer().)
                    }).toList();
        } catch (Exception ex) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `BlogAbc` t1 ON t1.`deleted` = ? AND t1.`id` = t.`id` WHERE t1.`star` = ? AND EXISTS (SELECT 1 FROM `BInner` t2 WHERE t2.`deleted` = ? AND t2.`title` = t.`id` AND t2.`star` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),false(Boolean),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testAAA42() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                    .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                    .where(o -> {
                        o.myBlog().configure(r -> {
                            r.asTable("BlogAbc");
                        });
                        o.myBlog().star().eq(1);
                        o.blogs().where(x -> {
                            x.configure(r -> {
                                r.asTable("BInner");
                            });
                            x.star().eq(1);
                        }).any();
//                    o.blogs().where(o->o._configurer().)
                    }).toList();
        } catch (Exception ex) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `BlogAbc` t1 ON t1.`deleted` = ? AND t1.`id` = t.`id` LEFT JOIN (SELECT t2.`title` AS `title`,(COUNT(?) > 0) AS `__any2__` FROM `BInner` t2 WHERE t2.`deleted` = ? AND t2.`star` = ? GROUP BY t2.`title`) t3 ON t3.`title` = t.`id` WHERE t1.`star` = ? AND IFNULL(t3.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),false(Boolean),1(Integer),1(Integer),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testAAA43() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                    .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                    .where(o -> {
                        o.myBlog().configure(r -> {
                            r.asTable("BlogAbc");
                        });
                        o.myBlog().star().eq(1);
                        o.blogs().configure(x -> x.asTable("BInner")).where(x -> {
                            x.star().eq(1);
                        }).any();
//                    o.blogs().where(o->o._configurer().)
                    }).toList();
        } catch (Exception ex) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `BlogAbc` t1 ON t1.`deleted` = ? AND t1.`id` = t.`id` LEFT JOIN (SELECT t2.`title` AS `title`,(COUNT(?) > 0) AS `__any2__` FROM `BInner` t2 WHERE t2.`deleted` = ? AND t2.`star` = ? GROUP BY t2.`title`) t3 ON t3.`title` = t.`id` WHERE t1.`star` = ? AND IFNULL(t3.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),false(Boolean),1(Integer),1(Integer),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testAAA45() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                    .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                    .where(o -> {
                        o.myBlog().configure(r -> {
                            r.asTable("BlogAbc");
                        });
                        o.blogs().mode(SubQueryModeEnum.SUB_QUERY_ONLY);
                        o.myBlog().star().eq(1);
                        o.blogs().configure(x -> x.asTable("BInner")).where(x -> {
                            x.star().eq(1);
                        }).any();
//                    o.blogs().where(o->o._configurer().)
                    }).toList();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `BlogAbc` t1 ON t1.`deleted` = ? AND t1.`id` = t.`id` WHERE t1.`star` = ? AND EXISTS (SELECT 1 FROM `BInner` t2 WHERE t2.`deleted` = ? AND t2.`title` = t.`id` AND t2.`star` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),false(Boolean),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testaaaa54() {
        EntityQueryable<BlogEntityProxy, BlogEntity> where = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().eq("123");
                });
        List<BlogEntity> list = where.leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.title()))
                .leftJoin(BlogEntity.class, (a, b, c) -> c.id().eq(b.title()))
                .toList();
    }

    @Test
    public void testaaaa541() {
        EntityQueryable<BlogEntityProxy, BlogEntity> where = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    if (false) {
                        t_blog.title().eq("123");
                    }
                });
        List<BlogEntity> list = where.leftJoin(BlogEntity.class, (a, b) -> a.id().eq(b.title()))
                .leftJoin(BlogEntity.class, (a, b, c) -> {
                    if (true) {
                        c.id().eq(b.title());
                    }
                })
                .where((a, b, c) -> {
                    c.id().eq(b.title());
                })
                .toList();
    }

    @Test
    public void testUUId() {
        UUID uuid = UUID.randomUUID();

        UUIDEntity2 uuidEntity = new UUIDEntity2();
        uuidEntity.setId1(uuid);
        uuidEntity.setId2(uuid);
        uuidEntity.setId3(uuid);

        easyEntityQuery.insertable(uuidEntity).executeRows();

        UUIDEntity2 uuidEntity1 = easyEntityQuery.queryable(UUIDEntity2.class).firstNotNull();
        System.out.println(uuidEntity1);
        Assert.assertEquals(uuid, uuidEntity1.getId1());
        Assert.assertEquals(uuid, uuidEntity1.getId2());
        Assert.assertEquals(uuid, uuidEntity1.getId3());
    }

    @Test
    public void testCountSubQueryCount() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(t_blog -> {
                    t_blog.star().le(t_blog.users().count());
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id` AS `id`,COUNT(*) AS `__count2__` FROM `easy-query-test`.`t_sys_user` t1 GROUP BY t1.`id`) t2 ON t2.`id` = t.`title` WHERE t.`deleted` = ? AND  t.`star` <= IFNULL(t2.`__count2__`,0)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void staticTestCustomFunction() {



        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {

                    findInSet("123", t_blog.content());

                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND FIND_IN_SET(?,`content`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void staticTestCustomFunction2() {



        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {

                    findInSet("123", subStr(t_blog.title(),1,2));

                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND FIND_IN_SET(?,SUBSTR(`title`,?,?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void staticTestCustomFunction3() {



        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list2 = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {

                    findInSet("123", t_blog.title().nullOrDefault("123"));

                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND FIND_IN_SET(?,IFNULL(`title`,?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void staticTestCustomFunction4() {



        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(it -> {
                    it.expression().rawSQLStatement("SUBSTR({0},{1},{2})", it.idCard(),1,2).asStr().eq("312345");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE SUBSTR(`id_card`,?,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),2(Integer),312345(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    public static void findInSet(String value, PropTypeColumn<String> column) {
        Expression expression = EasyProxyParamExpressionUtil.parseContextExpressionByParameters(value, column);

        expression.rawSQLCommand("FIND_IN_SET({0},{1})", value, column);
    }
    public static AnyTypeExpression<String> subStr(PropTypeColumn<String> column, int begin, int end) {
        Expression expression = EasyProxyParamExpressionUtil.parseContextExpressionByParameters(column);

        return expression.rawSQLStatement("SUBSTR({0},{1},{2})",column, begin,end).asAnyType(String.class);
    }
}
