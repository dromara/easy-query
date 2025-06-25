package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.ClassProxy;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.tuple.Tuple4;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.TopicType1VO;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.proxy.TopicType1VOProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUserEncrypt;
import com.easy.query.test.entity.SysUserEncrypt2;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.onrelation.OnRelationA;
import com.easy.query.test.entity.onrelation.OnRelationD;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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

}
