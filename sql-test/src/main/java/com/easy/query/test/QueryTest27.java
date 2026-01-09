package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.enums.MapKeyModeEnum;
import com.easy.query.api.proxy.enums.ValueTypeMode;
import com.easy.query.api.proxy.util.EasyProxyParamExpressionUtil;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityValueState;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SubQueryModeEnum;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.UUIDEntity2;
import com.easy.query.test.entity.m2m.Station;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.TableNoKey;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.vo.BlogEntityVO1;
import com.easy.query.test.vo.proxy.BlogEntityVO1Proxy;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
//        Topic[] objects = easyEntityQuery.queryable(Topic.class)
//                .where(t_topic -> {
//                    t_topic.title().isNotNull();
//                }).streamBy(s -> s.toArray(Topic[]::new));

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

//        easyEntityQuery.queryable(Topic.class)
//                .groupBy(t_topic -> GroupKeys.of(t_topic.createTime().duration(LocalDateTime.now()).toDays()))
//                .select(group -> Select.DRAFT.of(
//                        group.key1(),
//                        group.groupTable().createTime().duration(LocalDateTime.now()).toDays()
//                ))
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
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `BlogAbc` t1 ON t1.`deleted` = ? AND t1.`id` = t.`id` LEFT JOIN (SELECT t2.`title` AS `__group_key1__`,(COUNT(?) > 0) AS `__any2__` FROM `BInner` t2 WHERE t2.`deleted` = ? AND t2.`star` = ? GROUP BY t2.`title`) t3 ON t3.`__group_key1__` = t.`id` WHERE t1.`star` = ? AND IFNULL(t3.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
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
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t LEFT JOIN `BlogAbc` t1 ON t1.`deleted` = ? AND t1.`id` = t.`id` LEFT JOIN (SELECT t2.`title` AS `__group_key1__`,(COUNT(?) > 0) AS `__any2__` FROM `BInner` t2 WHERE t2.`deleted` = ? AND t2.`star` = ? GROUP BY t2.`title`) t3 ON t3.`__group_key1__` = t.`id` WHERE t1.`star` = ? AND IFNULL(t3.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
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
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id` AS `__group_key1__`,COUNT(*) AS `__count2__` FROM `easy-query-test`.`t_sys_user` t1 GROUP BY t1.`id`) t2 ON t2.`__group_key1__` = t.`title` WHERE t.`deleted` = ? AND  t.`star` <= IFNULL(t2.`__count2__`,0)", jdbcExecuteAfterArg.getBeforeArg().getSql());
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

                    findInSet("123", subStr(t_blog.title(), 1, 2));

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
                    it.expression().rawSQLStatement("SUBSTR({0},{1},{2})", it.idCard(), 1, 2).asStr().eq("312345");
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

        return expression.rawSQLStatement("SUBSTR({0},{1},{2})", column, begin, end).asAnyType(String.class);
    }


    @Test
    public void testMap() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                .select(t_topic -> new MapProxy(ValueTypeMode.TRY_TYPE)
                        .put("a1", t_topic.createTime())
                        .put("a2", t_topic.createTime().nullOrDefault(LocalDateTime.of(2025, 1, 1, 0, 0, 0)))
                ).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` AS `a1`,IFNULL(t.`create_time`,?) AS `a2` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2025-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


        for (Map<String, Object> stringObjectMap : list) {

            {

                Object o = stringObjectMap.get("a1");
                Assert.assertEquals(LocalDateTime.class, o.getClass());
            }
            {

                Object o = stringObjectMap.get("a2");
                Assert.assertEquals(LocalDateTime.class, o.getClass());
            }
        }

    }

    @Test
    public void testMap2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                .select(t_topic -> new MapProxy(ValueTypeMode.TRY_TYPE).selectAll(t_topic)).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("2025-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


        for (Map<String, Object> stringObjectMap : list) {

            {

                Object o = stringObjectMap.get("create_time");
                Assert.assertEquals(LocalDateTime.class, o.getClass());
            }
        }

    }


    @Test
    public void testSave1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(easyEntityQuery.queryable(SysUser.class), (t_topic, e2) -> t_topic.id().eq(e2.id()))
                .leftJoin(easyEntityQuery.queryable(SysUser.class), (a, v, c) -> {
                    a.id().eq(c.id());
                })
                .toList();
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `easy-query-test`.`t_sys_user` t1 ON t.`id` = t1.`id` LEFT JOIN `easy-query-test`.`t_sys_user` t2 ON t.`id` = t2.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("2025-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void updateTable() {


        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(b -> {
                    b.id().eq("123");
                }).orderBy(t -> {
                    t.expression().rawSQLStatement("RAND()").executeSQL();
                }).toList();


        List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                .where(b -> {
                    b.id().eq("123");
                }).orderBy(t -> {
                    t.expression().rawSQLCommand("IFNULL({0},{1}) DESC", t.stars(), 1);
                    t.expression().rawSQLCommand("RAND()");
                }).toList();
        List<Draft2<Double, Integer>> list3 = easyEntityQuery.queryable(Topic.class)
                .where(b -> {
                    b.id().eq("123");
                }).select(t -> Select.DRAFT.of(
                        t.expression().rawSQLStatement("RAND()").asAnyType(Double.class),
                        t.expression().rawSQLStatement("IFNULL({0},{1})", t.stars(), 1).asInteger()
                )).toList();


        List<Topic> listx = easyEntityQuery.queryable(Topic.class)
                .where(b -> {
                    b.id().eq("123");
                }).select(Topic.class, t -> Select.of(
                        t.expression().rawSQLStatement("RAND()").asAnyType(Double.class).as("stars"),
                        t.expression().rawSQLStatement("IFNULL({0},{1})", t.stars(), 1).asInteger().as("createTime")
                )).toList();
    }

    @Test
    public void testQuery() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<Topic> list = dbContext.topic().where(s -> {
                s.id().eq("123");
            }).toList();
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<Topic> list1 = dbContext.topic()
                    .leftJoin(dbContext.blog(), (a, b) -> {
                        a.id().eq(b.id());
                    }).where((t_topic, t_blog) -> {
                        t_topic.id().eq("123");
                    }).toList();
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
//        dbContext.topic().updatable().setColumns(t_topic -> {
//
//        }).where(t_topic -> {
//
//        }).executeRows();

    }

//    @Test
//    public void testRowTable(){
//        easyEntityQuery.queryable(BlogEntity.class).where(t_blog -> {
//            t_blog.id().eq("123");
//
//            t_blog.expression()
//
//            t_blog.asListTable(o->{
//                o.addRow(t_blog.id(),t_blog.title());
//                o.addRow(t_blog.title(),t_blog.id());
//                o.addRow(t_blog.title());
//            }).where(o->o.cell1().isNotNull()).max()
//        })
//    }

    @Test
    public void autoMergeMany2Many1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            List<Station> list = easyEntityQuery.queryable(Station.class)
                    .where(s -> {
                        s.stationId().eq("123");
                        s.operators().any(x -> x.tenantOperatorId().eq("1234"));
                    }).toList();
        } catch (Exception ex) {

        }
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`station_id`,t.`station_name` FROM `t_station` t LEFT JOIN (SELECT t2.`station_id` AS `__group_key1__`,(COUNT(?) > 0) AS `__any2__` FROM `t_tenant_operator` t1 INNER JOIN `t_station_tenant` t2 ON t1.`tenant_operator_id` = t2.`tenant_id` WHERE t1.`tenant_operator_id` = ? AND t2.`station_id` = ? GROUP BY t2.`station_id`) t4 ON t4.`__group_key1__` = t.`station_id` WHERE t.`station_id` = ? AND IFNULL(t4.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1234(String),123(String),123(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void autoMergeMany2Many2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            List<Station> list = easyEntityQuery.queryable(Station.class)
                    .where(s -> {
                        s.operators().any(x -> x.tenantOperatorId().eq("1234"));
                    }).toList();
        } catch (Exception ex) {

        }
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`station_id`,t.`station_name` FROM `t_station` t LEFT JOIN (SELECT t2.`station_id` AS `__group_key1__`,(COUNT(?) > 0) AS `__any2__` FROM `t_tenant_operator` t1 INNER JOIN `t_station_tenant` t2 ON t1.`tenant_operator_id` = t2.`tenant_id` WHERE t1.`tenant_operator_id` = ? GROUP BY t2.`station_id`) t4 ON t4.`__group_key1__` = t.`station_id` WHERE IFNULL(t4.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1234(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

//    @Test
//    public void testSelectSet() {
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .where(t_blog -> t_blog.id().eq("123"))
//                .select(t_blog -> {
//                    return t_blog.selectBy(t_blog.id(), t_blog.title());
//                }).toList();
//
//        List<Part1<SysUser, String>> userAndTypes = easyEntityQuery.queryable(SysUser.class)
//                .leftJoin(SysBankCard.class, (user, bank_card) -> user.id().eq(bank_card.uid()))
//                .where((user, bank_card) -> {
//                    user.username().contains("小明");
//                })
//                .select((user, bank_card) -> Select.PART.of(
//                        user,
//                        bank_card.type()
//                )).toList();
//
//        List<SysUser> list1 = easyEntityQuery.queryable(SysUser.class)
//                .where(user -> {
//                    user.id().in(
//                            easyEntityQuery.queryable(SysBankCard.class)
//                                    .where(bank_card -> {
//                                        bank_card.uid().eq(user.id());
//                                        bank_card.type().eq("储蓄卡");
//                                    }).select(bank_card -> bank_card.uid())
//                    );
//                }).toList();
//
//    }


    @Test
    public void testDerivedTable1() {
        EntityQueryable<BlogEntityVO1Proxy, BlogEntityVO1> query = easyEntityQuery.queryable(BlogEntity.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.SMART_PREDICATE))
                .select(t_blog -> new BlogEntityVO1Proxy()
                        .score().set(t_blog.score()) // 评分
                        .status().set(t_blog.status()) // 状态
                        .order().set(t_blog.order()) // 排序
                        .isTop().set(t_blog.isTop()) // 是否置顶
                        .top().set(t_blog.top()) // 是否置顶
                );


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        //下游操作或者前端用户传递json操作query
        List<BlogEntityVO1> list = query.where(o -> {
            o.status().eq(0);
            o.order().eq(BigDecimal.ONE);
        }).toList();
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`score` AS `score`,t1.`status1` AS `status1`,t1.`order` AS `order`,t1.`is_top` AS `is_top`,t1.`top` AS `top` FROM (SELECT t.`score` AS `score`,t.`status` AS `status1`,t.`order` AS `order`,t.`is_top` AS `is_top`,t.`top` AS `top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`status` = ? AND t.`order` = ?) t1 WHERE t1.`status1` = ? AND t1.`order` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),0(Integer),1(BigDecimal),0(Integer),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }
    @Test
    public void testDerivedTable2() {
        EntityQueryable<BlogEntityVO1Proxy, BlogEntityVO1> query = easyEntityQuery.queryable(BlogEntity.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.SMART_PREDICATE))
                .leftJoin(BlogEntity.class, (b, t) -> b.id().eq(t.id()))
                .select((t_blog, t) -> new BlogEntityVO1Proxy()
                        .score().set(t_blog.score()) // 评分
                        .status().set(t.status()) // 状态
                        .order().set(t_blog.order()) // 排序
                        .isTop().set(t_blog.isTop()) // 是否置顶
                        .top().set(t_blog.top()) // 是否置顶
                );


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        //下游操作或者前端用户传递json操作query
        List<BlogEntityVO1> list = query.where(o -> {
            o.status().eq(0);
            o.order().eq(BigDecimal.ONE);
        }).toList();
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`score` AS `score`,t2.`status1` AS `status1`,t2.`order` AS `order`,t2.`is_top` AS `is_top`,t2.`top` AS `top` FROM (SELECT t.`score` AS `score`,t1.`status` AS `status1`,t.`order` AS `order`,t.`is_top` AS `is_top`,t.`top` AS `top` FROM `t_blog` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` AND t1.`status` = ? WHERE t.`deleted` = ? AND t.`order` = ?) t2 WHERE t2.`status1` = ? AND t2.`order` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),0(Integer),false(Boolean),1(BigDecimal),0(Integer),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDerivedTable3() {
        EntityQueryable<BlogEntityVO1Proxy, BlogEntityVO1> query = easyEntityQuery.queryable(BlogEntity.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.SMART_PREDICATE))
                .leftJoin(BlogEntity.class, (b, t) -> b.id().eq(t.id()))
                .select((t_blog, t) -> new BlogEntityVO1Proxy()
                        .score().set(t_blog.score()) // 评分
                        .status().set(t.status()) // 状态
                        .order().set(t_blog.order()) // 排序
                        .isTop().set(t_blog.isTop()) // 是否置顶
                        .top().set(t_blog.top()) // 是否置顶
                );


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        //下游操作或者前端用户传递json操作query
        List<BlogEntityVO1> list = query.where(o -> {
            o.or(()->{
                o.status().eq(0);
                o.order().eq(BigDecimal.ONE);
            });
        }).toList();
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`score` AS `score`,t2.`status1` AS `status1`,t2.`order` AS `order`,t2.`is_top` AS `is_top`,t2.`top` AS `top` FROM (SELECT t.`score` AS `score`,t1.`status` AS `status1`,t.`order` AS `order`,t.`is_top` AS `is_top`,t.`top` AS `top` FROM `t_blog` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`deleted` = ?) t2 WHERE (t2.`status1` = ? OR t2.`order` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),false(Boolean),0(Integer),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDerivedTable4() {
        EntityQueryable<BlogEntityVO1Proxy, BlogEntityVO1> query = easyEntityQuery.queryable(BlogEntity.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.SMART_PREDICATE))
                .leftJoin(BlogEntity.class, (b, t) -> b.id().eq(t.id()))
                .select((t_blog, t) -> new BlogEntityVO1Proxy()
                        .score().set(t_blog.score()) // 评分
                        .status().set(t.status()) // 状态
                        .order().set(t_blog.order()) // 排序
                        .isTop().set(t_blog.isTop()) // 是否置顶
                        .top().set(t_blog.top()) // 是否置顶
                );


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        //下游操作或者前端用户传递json操作query
        List<BlogEntityVO1> list = query.where(o -> {
            o.or(()->{
                o.status().eq(0);
                o.order().eq(BigDecimal.ONE);
            });
        }).toList();
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`score` AS `score`,t2.`status1` AS `status1`,t2.`order` AS `order`,t2.`is_top` AS `is_top`,t2.`top` AS `top` FROM (SELECT t.`score` AS `score`,t1.`status` AS `status1`,t.`order` AS `order`,t.`is_top` AS `is_top`,t.`top` AS `top` FROM `t_blog` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`deleted` = ?) t2 WHERE (t2.`status1` = ? OR t2.`order` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),false(Boolean),0(Integer),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


}
