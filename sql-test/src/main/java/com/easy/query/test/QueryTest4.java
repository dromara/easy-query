package com.easy.query.test;

import com.easy.query.api4j.func.LambdaSQLFunc;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.common.anonymous.AnonymousType2;
import com.easy.query.core.common.anonymous.AnonymousType3;
import com.easy.query.core.common.anonymous.AnonymousType4;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyTypeUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeArrayJson;
import com.easy.query.test.entity.TopicTypeJsonValue;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/9/26 08:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest4 extends BaseTest {

    @Test
    public void query1233() {
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.eq(Topic::getCreateTime, maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime, "1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.ge(Topic::getCreateTime, maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime, "1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` >= (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.gt(Topic::getCreateTime, maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime, "1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` > (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.ne(Topic::getCreateTime, maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime, "1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <> (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.le(Topic::getCreateTime, maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime, "1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <= (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.lt(Topic::getCreateTime, maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime, "1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` < (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
    }

    @Test
    public void testJson1() {
        TopicTypeArrayJson topicType = easyQuery.queryable(TopicTypeArrayJson.class)
                .whereById("1231").firstOrNull();
        if (topicType != null) {
            long l = easyQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        topicType = easyQuery.queryable(TopicTypeArrayJson.class)
                .whereById("1231").firstOrNull();
        Assert.assertNull(topicType);
        TopicTypeArrayJson topicType1 = new TopicTypeArrayJson();
        topicType1.setId("1231");
        topicType1.setStars(123);
        TopicTypeJsonValue topicTypeJsonValue = new TopicTypeJsonValue();
        topicTypeJsonValue.setName("123");
        topicTypeJsonValue.setAge(456);
        topicType1.setTitle(topicTypeJsonValue);
        ArrayList<TopicTypeJsonValue> topicTypeJsonValues = new ArrayList<>();
        TopicTypeJsonValue topicTypeJsonValue1 = new TopicTypeJsonValue();
        topicTypeJsonValue1.setName("1234");
        topicTypeJsonValue1.setAge(4565);
        topicTypeJsonValues.add(topicTypeJsonValue1);
        topicType1.setTitle2(topicTypeJsonValues);

        topicType1.setTopicType(TopicTypeEnum.CLASSER.getCode());
        topicType1.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(topicType1).executeRows();
        Assert.assertEquals(1, l);

        TopicTypeArrayJson topicTypeVO = easyQuery.queryable(TopicTypeArrayJson.class)
                .whereById("1231")
                .firstOrNull();
        Assert.assertNotNull(topicTypeVO);
        System.out.println(topicTypeVO);
        Assert.assertNotNull(topicTypeVO.getTitle());
        Assert.assertEquals("123", topicTypeVO.getTitle().getName());
        Assert.assertEquals(456, (int) topicTypeVO.getTitle().getAge());
        Assert.assertNotNull(topicTypeVO.getTitle2());
        Assert.assertEquals(1, topicTypeVO.getTitle2().size());
        TopicTypeJsonValue topicTypeJsonValue2 = topicTypeVO.getTitle2().get(0);
        Assert.assertEquals("1234", topicTypeJsonValue2.getName());
        Assert.assertEquals(4565, (int) topicTypeJsonValue2.getAge());

        Assert.assertEquals(TopicTypeEnum.CLASSER.getCode(), topicTypeVO.getTopicType());
    }

    @Test
    public void testJson2() {
        String id = "1231";
        TopicTypeArrayJson topicType = easyQuery.queryable(TopicTypeArrayJson.class)
                .whereById(id).firstOrNull();
        if (topicType != null) {
            long l = easyQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        topicType = easyQuery.queryable(TopicTypeArrayJson.class)
                .whereById(id).firstOrNull();
        Assert.assertNull(topicType);
        TopicTypeArrayJson topicType1 = new TopicTypeArrayJson();
        topicType1.setId(id);
        topicType1.setStars(123);
        TopicTypeJsonValue topicTypeJsonValue = new TopicTypeJsonValue();
        topicTypeJsonValue.setName("123");
        topicTypeJsonValue.setAge(456);
        topicType1.setTitle(topicTypeJsonValue);
        ArrayList<TopicTypeJsonValue> topicTypeJsonValues = new ArrayList<>();
        {

            TopicTypeJsonValue topicTypeJsonValue1 = new TopicTypeJsonValue();
            topicTypeJsonValue1.setName("1234");
            topicTypeJsonValue1.setAge(4565);
            topicTypeJsonValues.add(topicTypeJsonValue1);
        }
        {

            TopicTypeJsonValue topicTypeJsonValue1 = new TopicTypeJsonValue();
            topicTypeJsonValue1.setName("12345");
            topicTypeJsonValue1.setAge(45655);
            topicTypeJsonValues.add(topicTypeJsonValue1);
        }
        topicType1.setTitle2(topicTypeJsonValues);

        topicType1.setTopicType(TopicTypeEnum.CLASSER.getCode());
        topicType1.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(topicType1).executeRows();
        Assert.assertEquals(1, l);

        TopicTypeArrayJson topicTypeVO = easyQuery.queryable(TopicTypeArrayJson.class)
                .whereById(id)
                .firstOrNull();
        Assert.assertNotNull(topicTypeVO);
        System.out.println(topicTypeVO);
        Assert.assertNotNull(topicTypeVO.getTitle());
        Assert.assertEquals("123", topicTypeVO.getTitle().getName());
        Assert.assertEquals(456, (int) topicTypeVO.getTitle().getAge());
        Assert.assertNotNull(topicTypeVO.getTitle2());
        Assert.assertEquals(2, topicTypeVO.getTitle2().size());
        TopicTypeJsonValue topicTypeJsonValue2 = topicTypeVO.getTitle2().get(0);
        Assert.assertEquals("1234", topicTypeJsonValue2.getName());
        Assert.assertEquals(4565, (int) topicTypeJsonValue2.getAge());
        TopicTypeJsonValue topicTypeJsonValue3 = topicTypeVO.getTitle2().get(1);
        Assert.assertEquals("12345", topicTypeJsonValue3.getName());
        Assert.assertEquals(45655, (int) topicTypeJsonValue3.getAge());

        Assert.assertEquals(TopicTypeEnum.CLASSER.getCode(), topicTypeVO.getTopicType());
    }

    //    @Test
//    public void testBean1(){
//        List<TestBean> list = easyQuery.queryable(TestBean.class)
//                .toList();
//    }
    @Test
    public void testSQLFunc1() {
        SQLFunc sqlFunc = easyQueryClient.sqlFunc();
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.func(sqlFunc.ifNull("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc2() {
        SQLFunc sqlFunc = easyQueryClient.sqlFunc();
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.func(sqlFunc.ifNull("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc2_1() {
        SQLFunc sqlFunc = easyQueryClient.sqlFunc();
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.func(sqlFunc.ifNull(x -> x.column("id").column("title").value("1")))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,t.`title`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc3() {
        SQLFunc sqlFunc = easyQueryClient.sqlFunc();
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.func(sqlFunc.dateTimeJavaFormat("createTime", "yyyy/MM/dd"))).toSQL();
        Assert.assertEquals("SELECT DATE_FORMAT(t.`create_time`, '%Y/%m/%d') FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc4() {
        SQLFunc sqlFunc = easyQueryClient.sqlFunc();
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.func(sqlFunc.dateTimeJavaFormat("createTime", "yyyy-MM-dd"))).toSQL();
        Assert.assertEquals("SELECT DATE_FORMAT(t.`create_time`, '%Y-%m-%d') FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc5() {
        SQLFunc sqlFunc = easyQueryClient.sqlFunc();
        List<String> list = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column("createTime"))
                .select(String.class, o -> o.func(sqlFunc.dateTimeJavaFormat("createTime", "yyyy-MM-dd"))).toList();
        for (String s : list) {
            Assert.assertEquals("2023-05-25", s);
        }
    }

    @Test
    public void testSQLFunc6() {
        SQLFunc sqlFunc = easyQueryClient.sqlFunc();
        List<String> list = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column("createTime"))
                .select(String.class, o -> o.func(sqlFunc.dateTimeJavaFormat("createTime", "yyyy-MM-dd"))).toList();
        for (String s : list) {
            Assert.assertEquals("2023-05-25", s);
        }
    }

    @Test
    public void testSQLFunc7() {
        SQLFunc sqlFunc = easyQueryClient.sqlFunc();
        List<Topic> list = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column("createTime"))
                .select(Topic.class, o -> o.funcAs(sqlFunc.dateTimeJavaFormat("createTime", "yyyy-MM-dd"), "title")).toList();
        for (Topic s : list) {
            Assert.assertEquals("2023-05-25", s.getTitle());
        }
        String sql = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column("createTime"))
                .select(Topic.class, o -> o.funcAs(sqlFunc.dateTimeJavaFormat("createTime", "yyyy-MM-dd"), "title")).toSQL();
        Assert.assertEquals("SELECT DATE_FORMAT(t.`create_time`, '%Y-%m-%d') AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
    }

    @Test
    public void testSQLFunc8() {
        SQLFunc sqlFunc = easyQueryClient.sqlFunc();
        String sql = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column("createTime"))
                .select(Topic.class, o -> o.funcAs(sqlFunc.abs("createTime"), "title")).toSQL();
        Assert.assertEquals("SELECT ABS(t.`create_time`) AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
    }

    @Test
    public void testSQLFunc9() {
        {
            SQLFunc sqlFunc = easyQueryClient.sqlFunc();
            String sql = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id", "1")
                            //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                    )
                    .orderByDesc(o -> o.column("createTime"))
                    .select(Topic.class, o -> o.funcAs(sqlFunc.round("stars", 1), "title")).toSQL();
            Assert.assertEquals("SELECT ROUND(t.`stars`,1) AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
        }
        {
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1")
                            //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                    )
                    .orderByDesc(o -> o.column(Topic::getCreateTime))
                    .select(Topic.class, o -> {
                        LambdaSQLFunc<Topic> sqlFunc = o.sqlFunc();
                        o.funcAs(sqlFunc.round(Topic::getStars, 1), Topic::getTitle);
                    }).toSQL();
            Assert.assertEquals("SELECT ROUND(t.`stars`,1) AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
        }
    }

    @Test
    public void testSQLFunc10() {
        {
            SQLFunc sqlFunc = easyQueryClient.sqlFunc();
            String sql = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id", "1")
                            //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                    )
                    .orderByDesc(o -> o.column("createTime"))
                    .select(Topic.class, o -> o.funcAs(sqlFunc.concat("stars", "id"), "title")).toSQL();
            Assert.assertEquals("SELECT CONCAT(t.`stars`,t.`id`) AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
        }
        {
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1")
                            //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                    )
                    .orderByDesc(o -> o.column(Topic::getCreateTime))
                    .select(Topic.class, o -> {
                        LambdaSQLFunc<Topic> sqlFunc = o.sqlFunc();
                        o.funcAs(sqlFunc.concat(Topic::getStars, Topic::getId), Topic::getTitle);
                    }).toSQL();
            Assert.assertEquals("SELECT CONCAT(t.`stars`,t.`id`) AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
        }
    }


    @Test
    public void testSQLFunc11() {

        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> {
                    LambdaSQLFunc<SysUser> userSqlFunc = o.sqlFunc();
                    o.eq(userSqlFunc.ifNull(SysUser::getId, "123"), userSqlFunc.ifNull(x -> x.column(SysUser::getIdCard).value("1243")));
                });
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE IFNULL(`id`,?) = IFNULL(`id_card`,?)", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }


    @Test
    public void testSQLFunc11_1() {

        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> {
                    LambdaSQLFunc<SysUser> userSqlFunc = o.sqlFunc();
                    o.eq(userSqlFunc.ifNull(SysUser::getId, "123"), "abc");
                });
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE IFNULL(`id`,?) = ?", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void testSQLFunc12() {
        TopicProxy table = TopicProxy.createTable();
        String sql1 = easyProxyQuery.queryable(table)
                .where(o -> o.eq(o.sqlFunc().ifNull(table.id(), "123"), o.sqlFunc().ifNull(table.createTime(), "123")))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`id`,?) = IFNULL(`create_time`,?)",sql1);
    }
    @Test
    public void testSQLFunc13() {
        {
            TopicProxy table = TopicProxy.createTable();
            String sql1 = easyProxyQuery.queryable(table)
                    .where(o -> o.eq(o.sqlFunc().ifNull(table.id(), "123"), "111"))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`id`,?) = ?",sql1);

        }
        {
            TopicProxy table = TopicProxy.createTable();
            Topic topic = easyProxyQuery.queryable(table)
                    .where(o -> o.eq(o.sqlFunc().ifNull(table.id(), "123"), "111"))
                    .firstOrNull();
            Assert.assertNull(topic);

        }
       }


    @Test
    public void queryFirstSingle() {
        {
            try {

                Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                        .asTable("sys_123")
                        .where(o -> o.eq(SysUser::getId, "123xxx"));
                SysUser sysUser = queryable.firstOrNull();
                Assert.assertNull(sysUser);
            } catch (Exception e) {
                Throwable cause = e.getCause();
                Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
                EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
                String sql = cause1.getSQL();
                Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`sys_123` WHERE `id` = ? LIMIT 1", sql);
            }
        }
        {

            try {

                Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                        .asTable("sys_123")
                        .where(o -> o.eq(SysUser::getId, "123xxx"));
                SysUser sysUser = queryable.singleOrNull();
                Assert.assertNull(sysUser);
            } catch (Exception e) {
                Throwable cause = e.getCause();
                Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
                EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
                String sql = cause1.getSQL();
                Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`sys_123` WHERE `id` = ?", sql);
            }
        }
        {

            try {

                Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class);
                SysUser sysUser = queryable.singleOrNull();
                Assert.assertNull(sysUser);
            } catch (Exception e) {
                Assert.assertTrue(e instanceof EasyQuerySingleMoreElementException);
            }
        }
    }

    @Test
    public void queryTest4() {
        Class<AnonymousType3<String, String, String>> resultClass = EasyTypeUtil.cast(AnonymousType3.class);

        Queryable<AnonymousType3<String, String, String>> select = easyQuery.queryable(SysUser.class)
                .select(resultClass, o -> o.columnAs(SysUser::getId, AnonymousType3::getP1).columnAs(SysUser::getIdCard, AnonymousType2::getP2));
        String sql = select.toSQL();
        Assert.assertEquals("SELECT t.`id` AS `anonymous_type_p1`,t.`id_card` AS `anonymous_type_p2` FROM `easy-query-test`.`t_sys_user` t", sql);
        AnonymousType3<String, String, String> sysUser = select.firstOrNull();
        Assert.assertNotNull(sysUser);
    }

    @Test
    public void queryTest5() {

        Class<AnonymousType4<String, Integer, String, String>> resultClass = EasyObjectUtil.typeCastNullable(AnonymousType4.class);
        Queryable<AnonymousType4<String, Integer, String, String>> select = easyQuery.queryable(BlogEntity.class)
                .select(resultClass, o -> o.columnAs(BlogEntity::getId, AnonymousType4::getP1).columnAs(BlogEntity::getStar, AnonymousType4::getP2));
        String sql = select.toSQL();
        Assert.assertEquals("SELECT t.`id` AS `anonymous_type_p1`,t.`star` AS `anonymous_type_p2` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
        AnonymousType4<String, Integer, String, String> blog = select.firstOrNull();
        Assert.assertNotNull(blog);
        Assert.assertNotNull(blog.getP1());
        Assert.assertNotNull(blog.getP2());
    }

    @Test
    public void queryTest6() {

        Class<AnonymousType4<String, Integer, String, String>> resultClass = EasyObjectUtil.typeCastNullable(AnonymousType4.class);
        Queryable<AnonymousType4<String, Integer, String, String>> select = easyQuery.queryable(BlogEntity.class)
                .select(resultClass, o -> o
                        .columnAs(BlogEntity::getId, AnonymousType4::getP1)
                        .columnAs(BlogEntity::getStar, AnonymousType4::getP2)
                );
        String sql = select.toSQL();
        Assert.assertEquals("SELECT t.`id` AS `anonymous_type_p1`,t.`star` AS `anonymous_type_p2` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
        AnonymousType4<String, Integer, String, String> blog = select.firstOrNull();
        Assert.assertNotNull(blog);
        Assert.assertNotNull(blog.getP1());
        Assert.assertNotNull(blog.getP2());
    }
}
