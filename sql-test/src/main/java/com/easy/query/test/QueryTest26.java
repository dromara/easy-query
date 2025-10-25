package com.easy.query.test;

import com.bestvike.linq.Linq;
import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.ClassProxy;
import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.tuple.Tuple4;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Include;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.dameng.config.DamengDatabaseConfiguration;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.test.dto.MyDTO;
import com.easy.query.test.dto.TopicType1VO;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.proxy.MyDTOProxy;
import com.easy.query.test.dto.proxy.TopicType1VOProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MySQLGenerateKey;
import com.easy.query.test.entity.SysUserEncrypt;
import com.easy.query.test.entity.SysUserEncrypt2;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.onrelation.OnRelationA;
import com.easy.query.test.entity.onrelation.OnRelationD;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.mysql8.dto.BankCardGroupBO;
import com.easy.query.test.mysql8.dto.proxy.BankCardGroupBOProxy;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * create time 2025/6/12 14:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest26 extends BaseTest {
    @Test
    public void test1() {
        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .select(t_topic -> {
                    return new TopicProxy()
                            .stars().set(t_topic.stars())
                            .title().set(t_topic.stars().valueConvert(s -> s + "-"))
                            .createTime().set(t_topic.createTime())
                            .id().set(t_topic.createTime().format("yyyy-MM-dd HH:mm:ss").valueConvert(s -> s + ".123"));
                }).toList();
        System.out.println(list);
        for (Topic topic : list) {
            Assert.assertEquals(topic.getStars() + "-", topic.getTitle());
            Assert.assertEquals(topic.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ".123", topic.getId());
        }

    }

    @Test
    public void test2() {
        List<UserABC> list = easyEntityQuery.queryable(Topic.class)
                .select(UserABC.class, t -> Select.of(
                        t.createTime().valueConvert(d -> d.format(DateTimeFormatter.ofPattern("yyyy-MM"))).as(UserABC.Fields.createTimeStr),
                        t.stars().valueConvert(d -> d + 1 + "aaa").as(UserABC.Fields.starsStr),
                        t.createTime(),
                        t.stars()
                )).toList();
        System.out.println(list);
        for (UserABC item : list) {
            Assert.assertEquals(item.getStars() + 1 + "aaa", item.getStarsStr());
            Assert.assertEquals(item.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM")), item.getCreateTimeStr());
        }

    }

    @Test
    public void test3() {
        List<Draft4<String, String, LocalDateTime, Integer>> list = easyEntityQuery.queryable(Topic.class)
                .select(t -> Select.DRAFT.of(
                        t.createTime().valueConvert(d -> d.format(DateTimeFormatter.ofPattern("yyyy-MM"))),
                        t.stars().valueConvert(d -> d + 1 + "aaa"),
                        t.createTime(),
                        t.stars()
                )).toList();
        System.out.println(list);
        for (Draft4<String, String, LocalDateTime, Integer> item : list) {
            Assert.assertEquals(item.getValue4() + 1 + "aaa", item.getValue2());
            Assert.assertEquals(item.getValue3().format(DateTimeFormatter.ofPattern("yyyy-MM")), item.getValue1());
        }

    }

    @Test
    public void test4() {
        List<Tuple4<String, String, LocalDateTime, Integer>> list = easyEntityQuery.queryable(Topic.class)
                .select(t -> Select.TUPLE.of(
                        t.createTime().valueConvert(d -> d.format(DateTimeFormatter.ofPattern("yyyy-MM"))),
                        t.stars().valueConvert(d -> d + 1 + "aaa"),
                        t.createTime(),
                        t.stars()
                )).toList();
        System.out.println(list);
        for (Tuple4<String, String, LocalDateTime, Integer> item : list) {
            Assert.assertEquals(item.getValue4() + 1 + "aaa", item.getValue2());
            Assert.assertEquals(item.getValue3().format(DateTimeFormatter.ofPattern("yyyy-MM")), item.getValue1());
        }

    }

    @Test
    public void test5() {
        List<Tuple4<String, String, LocalDateTime, Integer>> list = easyEntityQuery.queryable(Topic.class)
                .select(t -> Select.TUPLE.of(
                        t.createTime().format("yyyy-MM").valueConvert(d -> d + "1"),
                        t.stars().valueConvert(d -> d + 1 + "aaa"),
                        t.createTime(),
                        t.stars()
                )).toList();
        System.out.println(list);
        for (Tuple4<String, String, LocalDateTime, Integer> item : list) {
            Assert.assertEquals(item.getValue4() + 1 + "aaa", item.getValue2());
            Assert.assertEquals(item.getValue3().format(DateTimeFormatter.ofPattern("yyyy-MM")) + "1", item.getValue1());
        }

    }

    @Test
    public void test6() {
        List<UserABC> list = easyEntityQuery.queryable(Topic.class)
                .select(UserABC.class, t -> Select.of(
                        t.createTime().format("yyyy-MM").valueConvert(d -> d + "2").as(UserABC.Fields.createTimeStr),
                        t.stars().valueConvert(d -> d + 1 + "aaa").as(UserABC.Fields.starsStr),
                        t.createTime(),
                        t.stars()
                )).toList();
        System.out.println(list);
        for (UserABC item : list) {
            Assert.assertEquals(item.getStars() + 1 + "aaa", item.getStarsStr());
            Assert.assertEquals(item.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM")) + "2", item.getCreateTimeStr());
        }

    }

    @Test
    public void test7() {
        List<Tuple4<String, String, LocalDateTime, LocalDateTime>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t -> Select.TUPLE.of(
                        t.createTime().format("yyyy-MM").valueConvert(d -> d + "1"),
                        t.publishTime().format("yyyy-MM").valueConvert(d -> d + "1"),
                        t.createTime(),
                        t.publishTime()
                )).toList();
        System.out.println(list);
        for (Tuple4<String, String, LocalDateTime, LocalDateTime> item : list) {
            Assert.assertEquals(null + "1", item.getValue2());
            Assert.assertEquals(item.getValue3().format(DateTimeFormatter.ofPattern("yyyy-MM")) + "1", item.getValue1());
        }

    }


    //    @Test
//    public void testxx(){
//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_blog.id().eq(t_topic.id()))
//                .asTableLink(s -> {
//                    return "FULL JOIN";
//                })
//                .toList();
//    }
    @Data
    @FieldNameConstants
    public static class UserABC {
        private String createTimeStr;
        private LocalDateTime createTime;
        private String starsStr;
        private Integer stars;
    }

    @Test
    public void testViewWhere() {
        List<TopicType1VO> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_blog.id().eq(t_topic.id()))
                .select((t_topic, t_blog) -> new TopicType1VOProxy()
                        .column1().set(t_topic.id())
                        .column2().set(t_blog.id())
                        .column3().set(t_blog.title())
                        .column4().set(t_topic.title())
                ).where(t -> {
                    t.column1().eq("123");
                }).toList();
    }

    @Test
    public void testViewWhere2() {
        List<TopicType1VO> list = easyEntityQuery.queryable(Topic.class)
                .innerJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoin(Topic.class, (t1, t2, t3) -> t1.id().eq(t3.id()))
                .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.id().eq(t4.id()))
                .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.id().eq(t5.id()))
                .leftJoin(Topic.class, (t1, t2, t3, t4, t5, t6) -> t1.id().eq(t6.id()))
                .leftJoin(Topic.class, (t1, t2, t3, t4, t5, t6, t7) -> t1.id().eq(t7.id()))
                .leftJoin(Topic.class, (t1, t2, t3, t4, t5, t6, t7, t8) -> t1.id().eq(t8.id()))
                .leftJoin(Topic.class, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> t1.id().eq(t9.id()))
                .select((a, b, c, d, e, f, g, h, i) -> new TopicType1VOProxy()
                        .column1().set(a.id())
                        .column2().set(b.id())
                        .column3().set(b.title())
                        .column4().set(a.title())
                ).where(t -> {
                    t.column1().eq("123");
                }).toList();
    }

    @Test
    public void testRelationOn() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<OnRelationA> list = easyEntityQuery.queryable(OnRelationA.class)
                    .leftJoin(OnRelationD.class, (o, o2) -> o.onRelationB().onRelationC().cid().eq(o2.did()))
                    .where((o1, o2) -> {
                        o1.onRelationB().bname().contains("123");
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`aid`,t.`aname` FROM `on_relation_a` t LEFT JOIN `on_relation_b` t2 ON t2.`aid` = t.`aid` LEFT JOIN `on_relation_c` t3 ON t3.`bid` = t2.`bid` LEFT JOIN `on_relation_d` t1 ON t3.`cid` = t1.`did` WHERE t2.`bname` LIKE CONCAT('%',?,'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testQuery() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            List<SysUserEncrypt2> list = easyEntityQuery.queryable(SysUserEncrypt2.class)
                    .where(t -> {
                        t.phone().eq("123");
                    }).orderBy(s -> s.phone().asc())
                    .toList();
        } catch (Exception e) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name`,AES_DECRYPT(from_base64(`phone`),?) AS `phone` FROM `sys_user2` WHERE AES_DECRYPT(from_base64(`phone`),?) = ? ORDER BY AES_DECRYPT(from_base64(`phone`),?) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1234567890123456(String),1234567890123456(String),123(String),1234567890123456(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.updatable(SysUserEncrypt.class)
                .setColumns(s -> s.phone().set("12333"))
                .where(t -> {
                    t.id().eq("123eeddffrrttgga");
                }).executeRows();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `sys_user_encrypt` SET `phone` = to_base64(AES_ENCRYPT(?,?)) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12333(String),1234567890123456(String),123eeddffrrttgga(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQuery3() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            easyEntityQuery.updatable(SysUserEncrypt2.class)
                    .setColumns(s -> s.phone().set("12333"))
                    .where(t -> {
                        t.id().eq("123eeddffrrttgga");
                    }).executeRows();
        } catch (Exception e) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `sys_user2` SET `phone` = to_base64(AES_ENCRYPT(?,?)) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12333(String),1234567890123456(String),123eeddffrrttgga(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
//    @Test
//    public void testQuery4(){
//        ListenerContext listenerContext = new ListenerContext();
//        listenerContextManager.startListen(listenerContext);
//
//        try {
//
//            easyEntityQuery.updatable(SysUserEncrypt2.class)
//                    .setColumns(s -> s.phone().increment(123))
//                    .where(t -> {
//                        t.id().eq("123eeddffrrttgga");
//                    }).executeRows();
//        } catch (Exception e) {
//
//        }
//        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//        Assert.assertEquals("UPDATE `sys_user2` SET `phone` = to_base64(AES_ENCRYPT(?,?)) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("12333(String),1234567890123456(String),123eeddffrrttgga(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//        listenerContextManager.clear();
//    }
//    @Test
//    public void testQuery5(){
//        ListenerContext listenerContext = new ListenerContext();
//        listenerContextManager.startListen(listenerContext);
//
//        try {
//
//            easyEntityQuery.updatable(SysUserEncrypt2.class)
//                    .setColumns(s -> s.phone().set(s.phone().nullOrDefault("").concat("123")))
//                    .where(t -> {
//                        t.id().eq("123eeddffrrttgga");
//                    }).executeRows();
//        } catch (Exception e) {
//
//        }
//        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//        Assert.assertEquals("UPDATE `sys_user2` SET `phone` = to_base64(AES_ENCRYPT(?,?)) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("12333(String),1234567890123456(String),123eeddffrrttgga(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//        listenerContextManager.clear();
//    }

    @Test
    public void testFindInSet() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {

                    Query<String> stringQuery = easyEntityQuery.queryable(Topic.class)
                            .where(t_topic -> {
                                t_topic.id().eq("123");
                                t_topic.expression().sql("find_in_set({0},{1})", c -> {
                                    c.value(104).expression(t_topic.title());
                                });
                            }).selectColumn(t_topic -> t_topic.id());
                    t_blog.id().in(stringQuery);

                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` IN (SELECT t1.`id` FROM `t_topic` t1 WHERE t1.`id` = ? AND find_in_set(?,t1.`title`))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),104(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testaa() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Map<String, Object>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                .select((b1, t2) -> {
                    MapProxy result = new MapProxy();
                    result.selectAll(b1);
                    result.selectIgnores(b1.createTime());
                    result.put("xx", t2.createTime());
                    return result;
                })
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t1.`create_time` AS `xx` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testaa1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicTypeVO> list = easyEntityQuery.queryable(Topic.class)
                .where(topic -> {
                    topic.id().isNotNull();
                }).select(topic -> new ClassProxy<>(TopicTypeVO.class)
                                .field(TopicTypeVO.Fields.title).set(topic.id())
                                .field(TopicTypeVO.Fields.id).set(topic.title())
//                        .column("title").set(topic.id())
//                        .column("id").set(topic.title())
//                        .column(TopicTypeVO::getTitle).set(topic.id())
//                        .column(TopicTypeVO::getId).set(topic.title())
                ).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `title`,t.`title` AS `id` FROM `t_topic` t WHERE t.`id` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSetConstant() {
        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .select(t_topic -> new ClassProxy<>(BlogEntity.class)
                        .field("id").set("123")
                ).toList();
        Assert.assertTrue(list.size() > 0);
        for (BlogEntity blogEntity : list) {
            Assert.assertEquals("123", blogEntity.getId());
        }
        System.out.println(list);
    }

    @Test
    public void joiningTest() {
        List<Draft3<String, String, String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(t_blog -> GroupKeys.of(
                        t_blog.title()
                )).select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.joining(s -> {
                            return s.expression().caseWhen(() -> {
                                        s.score().eq(BigDecimal.ZERO);
                                    }).then(s.content().concat(s.url()))
                                    .elseEnd(null);
                        }, "|"),
                        group.joining(s -> {
                            return s.expression().caseWhen(() -> {
                                        s.score().eq(BigDecimal.ONE);
                                    }).then(s.content().concat(s.url()))
                                    .elseEnd(null);
                        }, "|")
                )).toList();
    }

    @Test
    public void updateConcat() {
        SQLFunc fx = easyQueryClient.getRuntimeContext().fx();
        SQLFunction concat = fx.concat(s -> s.value("DSL_").column("url"));
        easyQueryClient.updatable(BlogEntity.class)
                .setSQLFunction("title", concat)
                .where(o -> o.eq("id", "1234fdcvb"))
                .executeRows();


        BigDecimal bigDecimal = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> {
                    t_topic.title().eq(t_blog.title());
                })
                .sumBigDecimalOrDefault((t_topic, t_blog) -> t_blog.score().nullOrDefault(BigDecimal.ZERO), BigDecimal.ZERO);
    }

    @Test
    public void includeFlat() {

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .include((c, s) -> {
                    c.query(s.schoolTeachers().flatElement().schoolClasses()).where(a -> a.name().like("123"));
                    c.query(s.schoolStudents().flatElement().schoolClass()).where(x -> x.schoolStudents().flatElement().name().eq("123"));
                    c.query(s.schoolStudents()).where(x -> x.name().eq("123"));
                })
                .toList();
    }

    @Test
    public void switchCase() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(t_blog -> {
                    t_blog.expression().newMap()
                            .put(1, t_blog.title())
                            .put(2, 3)
                            .getOrDefault(t_blog.content(), null).asc();
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? ORDER BY (CASE WHEN `content` = ? THEN `title` WHEN `content` = ? THEN ? ELSE NULL END) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),2(Integer),3(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void test11() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                    .where(bank_card -> {
                        bank_card.bank().filter(t -> {
                            t.or(() -> {
                                t.name().like("工商银行");
                                t.name().contains("建设银行");
                            });
                        });

                    }).toList();
        } catch (Exception e) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t INNER JOIN `t_bank` t1 ON t1.`id` = t.`bank_id` AND (t1.`name` LIKE ? OR t1.`name` LIKE CONCAT('%',?,'%'))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%工商银行%(String),建设银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void test22() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {
            List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                    .where(bank_card -> {
                        bank_card.bank().filter(t -> {
                            t.or(() -> {
                                t.name().like("工商银行");
                                t.name().contains("建设银行");
                            });
                        });

                        bank_card.bank().name().contains("123");
                    }).toList();
        } catch (Exception e) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t INNER JOIN `t_bank` t1 ON t1.`id` = t.`bank_id` AND (t1.`name` LIKE ? OR t1.`name` LIKE CONCAT('%',?,'%')) WHERE t1.`name` LIKE CONCAT('%',?,'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%工商银行%(String),建设银行(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void test23() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {
            List<BankCardGroupBO> list = easyEntityQuery.queryable(SysBankCard.class)
                    .groupBy(bank_card -> GroupKeys.of(bank_card.uid()))
                    .select(group -> new BankCardGroupBOProxy()
                            .uid().set(group.key1())
                            .count().set(group.count())
                    ).where(b -> {
                        b.user().name().contains("123");
                    }).toList();
        } catch (Exception e) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`uid` AS `uid`,t1.`count` AS `count` FROM (SELECT t.`uid` AS `uid`,COUNT(*) AS `count` FROM `t_bank_card` t GROUP BY t.`uid`) t1 LEFT JOIN `t_sys_user` t2 ON t2.`id` = t1.`uid` WHERE t2.`name` LIKE CONCAT('%',?,'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void test() {
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class);
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = queryable.getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.setOffset(1);
        sqlEntityExpressionBuilder.setDistinct(true);
        sqlEntityExpressionBuilder.setRows(10);
        List<BlogEntity> list1 = queryable.toList();


        ArrayList<Fruit> fruits = new ArrayList<>();
        List<ColorCategory> list = Linq.of(fruits)
                .groupBy(o -> o.getColor())
                .select(g -> {
                    ColorCategory colorCategory = new ColorCategory();
                    colorCategory.setColor(g.getKey());
                    List<ShapeCategory> shapes = g.groupBy(ig -> ig.getShape()).select(ig -> {
                        ShapeCategory shapeCategory = new ShapeCategory();
                        shapeCategory.setShape(ig.getKey());
                        shapeCategory.setFruits(ig.toList());
                        return shapeCategory;
                    }).toList();

                    colorCategory.setShape(shapes);
                    return colorCategory;
                }).toList();


        Map<String, List<Fruit>> collect = fruits.stream().collect(Collectors.groupingBy(o -> o.getColor()));
        List<ColorCategory> result = new ArrayList<>();
        for (Map.Entry<String, List<Fruit>> stringListEntry : collect.entrySet()) {

            String key = stringListEntry.getKey();
            List<Fruit> value = stringListEntry.getValue();
            ColorCategory colorCategory = new ColorCategory();
            colorCategory.setColor(key);
            Map<String, List<Fruit>> shapeMap = value.stream().collect(Collectors.groupingBy(o -> o.getShape()));
            List<ShapeCategory> shapeCategories = shapeMap.entrySet().stream().map(o -> {
                ShapeCategory shapeCategory = new ShapeCategory();
                shapeCategory.setShape(o.getKey());
                shapeCategory.setFruits(o.getValue());
                return shapeCategory;
            }).collect(Collectors.toList());
            colorCategory.setShape(shapeCategories);

            result.add(colorCategory);
        }
    }


    @Data
    public static class Fruit {

        /**
         * id
         */
        private String id;
        /**
         * 水果名称
         */
        private String name;
        /**
         * 颜色
         */
        private String color;
        /**
         * 形状
         */
        private String shape;
    }

    @Data
    public static class ColorCategory {
        /**
         * 颜色
         */
        private String color;

        public List<ShapeCategory> shape;

    }

    @Data
    public static class ShapeCategory {

        /**
         * 形状
         */
        private String shape;

        public List<Fruit> fruits;
    }

    @Test
    public void testChunk3() {
        HashMap<String, BlogEntity> ids = new HashMap<>();
        easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(b -> b.createTime().asc())
                .orderBy(b -> b.id().asc())
                .offsetChunk(3, chunk -> {
                    Assert.assertTrue(chunk.getValues().size() <= 3);
                    for (BlogEntity blog : chunk.getValues()) {
                        if (ids.containsKey(blog.getId())) {
                            throw new RuntimeException("id 重复:" + blog.getId());
                        }
                        ids.put(blog.getId(), blog);
                    }
                    return chunk.offset(chunk.getValues().size());
                });
        Assert.assertEquals(100, ids.size());
    }

    @Test
    public void testChunk4() {
        AtomicInteger a = new AtomicInteger(0);
        easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(b -> b.createTime().asc())
                .orderBy(b -> b.id().asc())
                .offsetChunk(3, chunk -> {
                    for (BlogEntity blog : chunk.getValues()) {
                        a.incrementAndGet();
                    }
                    return chunk.offset(chunk.getValues().size());
                });
        Assert.assertEquals(100, a.intValue());

    }

    @Test
    public void testChunk5() {
        AtomicInteger a = new AtomicInteger(0);
        easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(b -> b.createTime().asc())
                .orderBy(b -> b.id().asc())
                .offsetChunk(100, chunk -> {
                    for (BlogEntity blog : chunk.getValues()) {
                        a.incrementAndGet();
                    }
                    return chunk.offset(0);
                });
        Assert.assertEquals(100000, a.intValue());


    }

    @Test
    public void testChunk6() {
        AtomicInteger a = new AtomicInteger(0);
        easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(b -> b.createTime().asc())
                .orderBy(b -> b.id().asc())
                .offsetChunk(100, chunk -> {
                    chunk.setMaxFetchSize(100001);
                    for (BlogEntity blog : chunk.getValues()) {
                        a.incrementAndGet();
                    }
                    return chunk.offset(0);
                });
        Assert.assertEquals(100001, a.intValue());


    }


    @Test
    public void testGenerateKeys() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        {
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(MySQLGenerateKey.class));
            codeFirstCommand.executeWithTransaction(s -> s.commit());
        }
        {
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MySQLGenerateKey.class));
            codeFirstCommand.executeWithTransaction(s -> s.commit());

        }
        {

            MySQLGenerateKey mySQLGenerateKey = new MySQLGenerateKey();
            mySQLGenerateKey.setName("test");
            System.out.println("插入前" + mySQLGenerateKey);
            easyEntityQuery.insertable(mySQLGenerateKey).executeRows(true);
            System.out.println("插入后" + mySQLGenerateKey);

            MySQLGenerateKey mySQLGenerateKey1 = easyEntityQuery.queryable(MySQLGenerateKey.class).whereById(1).singleOrNull();
            System.out.println("查询后" + mySQLGenerateKey1);
            Assert.assertNotNull(mySQLGenerateKey1);
            Assert.assertNotNull(mySQLGenerateKey1.getId());
            Assert.assertEquals(1, (int) mySQLGenerateKey1.getId());
            Assert.assertNotNull(mySQLGenerateKey1.getNow());
            LocalDateTime now = LocalDateTime.now();
            Assert.assertEquals(mySQLGenerateKey1.getNow().getYear(), now.getYear());
            Assert.assertEquals(mySQLGenerateKey1.getNow().getMonth(), now.getMonth());
            Assert.assertEquals(mySQLGenerateKey1.getNow().getDayOfYear(), now.getDayOfYear());
            Assert.assertEquals(mySQLGenerateKey1.getNow().getHour(), now.getHour());
        }

    }

    @Test
    public void testMaxInCaluse() {

        ListenerContextManager listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setMaxInClauseSize(10);
                })
                .useDatabaseConfigure(new OracleDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 21; i++) {
                    strings.add(i + "");
                }
                List<Topic> list = defaultEasyEntityQuery.queryable(Topic.class)
                        .whereByIds(strings)
                        .toList();
            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT \"id\",\"stars\",\"title\",\"create_time\" FROM \"t_topic\" WHERE (\"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 21; i++) {
                    strings.add(i + "");
                }
                List<Topic> list = defaultEasyEntityQuery.queryable(Topic.class)
                        .where(t_topic -> {
                            t_topic.id().in(strings);
                        })
                        .toList();
            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT \"id\",\"stars\",\"title\",\"create_time\" FROM \"t_topic\" WHERE (\"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 21; i++) {
                    strings.add(i + "");
                }
                List<Topic> list = defaultEasyEntityQuery.queryable(Topic.class)
                        .where(t_topic -> {
                            t_topic.title().contains("123");
                            t_topic.id().in(strings);
                        })
                        .toList();
            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT \"id\",\"stars\",\"title\",\"create_time\" FROM \"t_topic\" WHERE \"title\" LIKE ('%'||TO_CHAR(?)||'%') AND (\"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 21; i++) {
                    strings.add(i + "");
                }
                List<Topic> list = defaultEasyEntityQuery.queryable(Topic.class)
                        .where(t_topic -> {
                            t_topic.id().in(strings);
                            t_topic.or(() -> {
                                t_topic.title().contains("123");
                                t_topic.id().in(strings);
                                t_topic.id().in(strings);
                            });
                        })
                        .toList();
            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT \"id\",\"stars\",\"title\",\"create_time\" FROM \"t_topic\" WHERE (\"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?)) AND (\"title\" LIKE ('%'||TO_CHAR(?)||'%') OR (\"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?)) OR (\"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?)))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String),123(String),0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String),0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 21; i++) {
                    strings.add(i + "");
                }
                List<Topic> list = defaultEasyEntityQuery.queryable(Topic.class)
                        .where(t_topic -> {
                            t_topic.and(() -> {
                                t_topic.id().in(strings);
                                t_topic.or(() -> {
                                    t_topic.title().contains("123");
                                    t_topic.id().in(strings);
                                    t_topic.id().in(strings);
                                });
                            });
                        })
                        .toList();
            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT \"id\",\"stars\",\"title\",\"create_time\" FROM \"t_topic\" WHERE ((\"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?)) AND (\"title\" LIKE ('%'||TO_CHAR(?)||'%') OR (\"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?)) OR (\"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?,?,?,?,?,?,?,?,?,?) OR \"id\" IN (?))))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String),123(String),0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String),0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
    }

    @Test
    public void testAAA() {

        ListenerContextManager listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(2);
                    op.setMaxShardingQueryLimit(1);
                    op.setUpdateStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS);
                })
                .useDatabaseConfigure(new DamengDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);

        try {
            defaultEasyEntityQuery.deletable(SysBankCard.class)
                    .where(bank_card -> {
                        bank_card.user().id().eq("123");
                    }).executeRows();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
//
//    @Test
//    public void testAAA1() {
//
//        ListenerContextManager listenerContextManager = new ListenerContextManager();
//        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
//        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
//                .setDefaultDataSource(dataSource)
//                .optionConfigure(op -> {
//                    op.setDeleteThrowError(false);
//                    op.setExecutorCorePoolSize(1);
//                    op.setExecutorMaximumPoolSize(2);
//                    op.setMaxShardingQueryLimit(1);
//                    op.setUpdateStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS);
//                })
//                .useDatabaseConfigure(new OracleDatabaseConfiguration())
//                .replaceService(JdbcExecutorListener.class, myJdbcListener)
//                .build();
//        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
//
//        try {
//            defaultEasyEntityQuery.deletable(SysBankCard.class)
//                    .where(bank_card -> {
//                        bank_card.user().id().eq("123");
//                    }).executeRows();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testAAX() {
//        List<MyDTO> list = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> {
//                    t_topic.id().eq(t_blog.id());
//                })
//                .where((t_topic, t_blog) -> {
//                    t_topic.title().isNotBlank();
//                }).select((t_topic, t_blog) -> new MyDTOProxy()
//                        .column1().set(t_topic.id())
//                        .column2().set(t_topic.title())
//                        .column3().set(t_blog.id())
//                        .column4().set(t_blog.title())
//
//                ).toList();
//    }

}
