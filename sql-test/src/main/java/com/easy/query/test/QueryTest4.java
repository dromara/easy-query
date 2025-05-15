package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeArrayJson;
import com.easy.query.test.entity.TopicTypeJsonValue;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.provider.MySQLProviderImpl;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
            Query<LocalDateTime> maxCreateTimeQuery = easyEntityQuery.queryable(Topic.class)
                    .selectColumn(x -> x.createTime().max());
            String sql = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
//                        o.getWherePredicate().in("id", Arrays.asList("1","2"));

                        o.createTime().eq(maxCreateTimeQuery);
                        o.createTime().asStr().eq("1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
            Query<LocalDateTime> maxCreateTimeQuery = easyEntityQuery.queryable(Topic.class)
                    .selectColumn(x -> x.createTime().max());
            String sql = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().ge(maxCreateTimeQuery);
                        o.createTime().asStr().eq("1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` >= (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
//            Integer integer = easyEntityQuery.queryable(Topic.class)
//                    .where(o -> o.eq(Topic::getTitle, 111))
//                    .sumOrDefault(Topic::getStars, 0);
            Query<LocalDateTime> maxCreateTimeQuery = easyEntityQuery.queryable(Topic.class)
                    .selectColumn(x -> x.createTime().max());
            String sql = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().gt(maxCreateTimeQuery);
                        o.createTime().asStr().eq("1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` > (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
            Query<LocalDateTime> maxCreateTimeQuery = easyEntityQuery.queryable(Topic.class)
                    .selectColumn(x -> x.createTime().max());
            String sql = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().ne(maxCreateTimeQuery);
                        o.createTime().asStr().eq("1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <> (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
            Query<LocalDateTime> maxCreateTimeQuery = easyEntityQuery.queryable(Topic.class)
                    .selectColumn(x -> x.createTime().max());
            String sql = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().le(maxCreateTimeQuery);
                        o.createTime().asStr().eq("1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <= (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
        {
            Query<LocalDateTime> maxCreateTimeQuery = easyEntityQuery.queryable(Topic.class)
                    .selectColumn(x -> x.createTime().max());
            String sql = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.createTime().lt(maxCreateTimeQuery);
                        o.createTime().asStr().eq("1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` < (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?", sql);
        }
    }

    @Test
    public void testJson1() {
        TopicTypeArrayJson topicType = easyEntityQuery.queryable(TopicTypeArrayJson.class)
                .whereById("1231").firstOrNull();
        if (topicType != null) {
            long l = easyEntityQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        topicType = easyEntityQuery.queryable(TopicTypeArrayJson.class)
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
        long l = easyEntityQuery.insertable(topicType1).executeRows();
        Assert.assertEquals(1, l);

        TopicTypeArrayJson topicTypeVO = easyEntityQuery.queryable(TopicTypeArrayJson.class)
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
        TopicTypeArrayJson topicType = easyEntityQuery.queryable(TopicTypeArrayJson.class)
                .whereById(id).firstOrNull();
        if (topicType != null) {
            long l = easyEntityQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        topicType = easyEntityQuery.queryable(TopicTypeArrayJson.class)
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
        long l = easyEntityQuery.insertable(topicType1).executeRows();
        Assert.assertEquals(1, l);

        TopicTypeArrayJson topicTypeVO = easyEntityQuery.queryable(TopicTypeArrayJson.class)
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
//        List<TestBean> list = easyEntityQuery.queryable(TestBean.class)
//                .toList();
//    }
    @Test
    public void testSQLFunc1() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.sqlFunc(o.fx().nullOrDefault("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc2() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.sqlFunc(o.fx().nullOrDefault("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFuncExtension1() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> {
                    o.eq("id", "1");
                    MySQLProviderImpl<Topic> mySQLProvider = new MySQLProviderImpl<>(o);
                    mySQLProvider.findInSet(c -> c.value("1"), c -> c.expression("id"));
                })
                .select(String.class, o -> o.sqlFunc(o.fx().nullOrDefault("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ? AND FIND_IN_SET(?,t.`id`)", sql1);
    }

    @Test
    public void testSQLFuncExtension2() {
        {
            String sql1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> {
                        o.eq("id", "1");
                        MySQLProviderImpl<Topic> mySQLProvider = new MySQLProviderImpl<>(o);
                        mySQLProvider.findInSet(c -> c.value("1"), c -> c.expression("id"));
                    })
                    .select(String.class, o -> o.column("id")).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t WHERE t.`id` = ? AND FIND_IN_SET(?,t.`id`)", sql1);
        }
        {
            String sql1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> {
                        o.eq("id", "1");
                        o.sqlNativeSegment("FIND_IN_SET({0},{1})", c -> {
                            c.value("1").expression("id");
                        });
                    })
                    .select(String.class, o -> o.column("id")).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t WHERE t.`id` = ? AND FIND_IN_SET(?,t.`id`)", sql1);
        }
    }

    @Test
    public void testSQLFunc2_2() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> {
                    o.eq("id", "1");//.use(new MySQLWherePredicater<>()).findInSet();
                })
                .select(String.class, o -> o.sqlFunc(o.fx().nullOrDefault("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc2_1() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.sqlFunc(o.fx().nullOrDefault(x -> x.column("id").column("title").value("1")))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,t.`title`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc3() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.sqlFunc(o.fx().dateTimeFormat("createTime", "yyyy/MM/dd"))).toSQL();
        Assert.assertEquals("SELECT DATE_FORMAT(t.`create_time`,'%Y/%m/%d') FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc4() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.sqlFunc(o.fx().dateTimeFormat("createTime", "yyyy-MM-dd"))).toSQL();
        Assert.assertEquals("SELECT DATE_FORMAT(t.`create_time`,'%Y-%m-%d') FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc5() {
        List<String> list = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column("createTime"))
                .select(String.class, o -> o.sqlFunc(o.fx().dateTimeFormat("createTime", "yyyy-MM-dd"))).toList();
        for (String s : list) {
            Assert.assertEquals("2023-05-25", s);
        }
    }

    @Test
    public void testSQLFunc6() {
        List<String> list = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column("createTime"))
                .select(String.class, o -> o.sqlFunc(o.fx().dateTimeFormat("createTime", "yyyy-MM-dd"))).toList();
        for (String s : list) {
            Assert.assertEquals("2023-05-25", s);
        }
    }

    @Test
    public void testSQLFunc7() {
        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq( "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderBy(o -> o.createTime().desc())
                .select(Topic.class, t_topic -> Select.of(
                        t_topic.createTime().format("yyyy-MM-dd").as(Topic::getTitle)
                ))
                .toList();
        for (Topic s : list) {
            Assert.assertEquals("2023-05-25", s.getTitle());
        }
        String sql = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq( "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderBy(o -> o.createTime().desc())
                .select(Topic.class, t_topic -> Select.of(
                        t_topic.createTime().format("yyyy-MM-dd").as(Topic::getTitle)
                )).toSQL();
        Assert.assertEquals("SELECT DATE_FORMAT(t.`create_time`,'%Y-%m-%d') AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
    }

    @Test
    public void testSQLFunc8() {
        String sql = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column("createTime"))
                .select(Topic.class, o -> o.sqlFuncAs(o.fx().abs("createTime"), "title")).toSQL();
        Assert.assertEquals("SELECT ABS(t.`create_time`) AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
    }

    @Test
    public void testSQLFunc9() {
        {
            String sql = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id", "1")
                            //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                    )
                    .orderByDesc(o -> o.column("createTime"))
                    .select(Topic.class, o -> o.sqlFuncAs(o.fx().round("stars", 1), "title")).toSQL();
            Assert.assertEquals("SELECT ROUND(t.`stars`,1) AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
        }
    }

    @Test
    public void testSQLFunc10() {
        {
            String sql = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id", "1")
                            //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                    )
                    .orderByDesc(o -> o.column("createTime"))
                    .select(Topic.class, o -> o.sqlFuncAs(o.fx().concat("stars", "id"), "title")).toSQL();
            Assert.assertEquals("SELECT CONCAT(t.`stars`,t.`id`) AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
        }
    }


    @Test
    public void testSQLFunc12() {
        {
            String sql = easyEntityQuery.queryable(Topic.class)
                    .where(t_topic -> {
                        t_topic.id().nullOrDefault("123").eq(t_topic.createTime().nullOrDefault((LocalDateTime) null));
                    }).toSQL();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`id`,?) = IFNULL(`create_time`,?)", sql);
        }
    }

    @Test
    public void testSQLFunc12_1() {
        {
            String sql1 = easyEntityQuery.queryable(Topic.class)
                    .where(t_topic -> {
                        t_topic.id().nullOrDefault("123").eq(t_topic.createTime().format("yyyy/MM/dd HH:mm:ss"));
                    }).toSQL();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`id`,?) = DATE_FORMAT(`create_time`,'%Y/%m/%d %H:%i:%s')", sql1);

        }
    }


    @Test
    public void queryFirstSingle() {
        {
            try {

                Query<SysUser> queryable = easyEntityQuery.queryable(SysUser.class)
                        .asTable("sys_123")
                        .where(o -> o.id().eq( "123xxx"));
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

                Query<SysUser> queryable = easyEntityQuery.queryable(SysUser.class)
                        .asTable("sys_123")
                        .where(o -> o.id().eq( "123xxx"));
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

                Query<SysUser> queryable = easyEntityQuery.queryable(SysUser.class);
                SysUser sysUser = queryable.singleOrNull();
                Assert.assertNull(sysUser);
            } catch (Exception e) {
                Assert.assertTrue(e instanceof EasyQuerySingleMoreElementException);
            }
        }
    }
    @Test
    public void queryTest7() {
        {

            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "123"))
                    .firstOrNull();
            Assert.assertNull(topic);
        }
        {

            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "1"))
                    .select(t_topic -> t_topic.FETCHER.id().title())
                    .firstOrNull();
            Assert.assertNotNull(topic);
            Assert.assertNotNull(topic.getId());
            Assert.assertNotNull(topic.getTitle());
            Assert.assertNull(topic.getStars());
            Assert.assertNull(topic.getCreateTime());
        }
        {

            EntityQueryable<TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq("1"))
                    .select(o -> o.FETCHER.id().title().fetchProxy());

            List<Topic> list = query.leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                    .where((t, t1) -> {
                        t1.id().eq("123");
                        t.id().eq( "456");
                    }).toList();


            Assert.assertNotNull(list);
            Assert.assertEquals(0, list.size());
        }
        {

            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "123"))
                    .singleOrNull();
            Assert.assertNull(topic);
        }
        {
            Exception exception = firstNotNull1();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQueryFirstNotNullException);

        }
        {
            Exception exception = singleNotNull("123");
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySingleNotNullException);
        }
        {
            Exception exception = singleNotNull("1");
            Assert.assertNull(exception);
        }
    }

    public Exception firstNotNull1() {

        try {

            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "123"))
                    .firstNotNull("未找到对应的数据");
        } catch (Exception ex) {
            return ex;
        }
        return null;
    }

    public Exception singleNotNull(String id) {

        try {

            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( id))
                    .singleNotNull("未找到对应的数据");
        } catch (Exception ex) {
            return ex;
        }
        return null;
    }

    @Test
    public void joinTest1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(Topic.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.id().eq(t4.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.id().eq(t5.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6) -> t.id().eq(t6.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7) -> t.id().eq(t7.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> t.id().eq(t8.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> t.id().eq(t9.id()))
                .where(o -> o.id().eq( "1"))
                .where(false, o -> o.id().eq( "1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq( "1");
                    o.t1.id().eq(false,  "1");
                    o.t1.id().ne( "1");
                    o.t1.id().ne(false,  "1");
                    o.t1.id().ge( "1");
                    o.t1.id().ge(false,  "1");
                    o.t1.id().gt( "1");
                    o.t1.id().gt(false,  "1");
                    o.t1.id().le( "1");
                    o.t1.id().le(false,  "1");
                    o.t1.id().lt( "1");
                    o.t1.id().lt(false,  "1");
                    o.t1.id().like( "1");
                    o.t1.id().like(false,  "1");
                    o.t1.id().notLike( "1");
                    o.t1.id().notLike(false,  "1");
                    o.t1.id().likeMatchLeft( "1");
                    o.t1.id().likeMatchLeft(false,  "1");
                    o.t1.id().likeMatchRight( "1");
                    o.t1.id().likeMatchRight(false,  "1");
                    o.t1.id().notLikeMatchLeft( "1");
                    o.t1.id().notLikeMatchLeft(false,  "1");
                    o.t1.id().notLikeMatchRight( "1");
                    o.t1.id().notLikeMatchRight(false,  "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false,o -> o.t1.createTime().asc())
                .orderByMerge(false,o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false,o -> o.groupTable().t1.id().count().ge(1L))
                .select(Topic.class, o -> Select.of(
                    o.groupTable().t1.id(),
                    o.groupTable().t1.id().count().as(Topic::getStars)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest2() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(Topic.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.id().eq(t4.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.id().eq(t5.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6) -> t.id().eq(t6.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7) -> t.id().eq(t7.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> t.id().eq(t8.id()))
                .where(o -> o.id().eq( "1"))
                .where(false, o -> o.id().eq( "1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq( "1");
                    o.t1.id().eq(false,  "1");
                    o.t1.id().ne( "1");
                    o.t1.id().ne(false,  "1");
                    o.t1.id().ge( "1");
                    o.t1.id().ge(false,  "1");
                    o.t1.id().gt( "1");
                    o.t1.id().gt(false,  "1");
                    o.t1.id().le( "1");
                    o.t1.id().le(false,  "1");
                    o.t1.id().lt( "1");
                    o.t1.id().lt(false,  "1");
                    o.t1.id().like( "1");
                    o.t1.id().like(false,  "1");
                    o.t1.id().notLike( "1");
                    o.t1.id().notLike(false,  "1");
                    o.t1.id().likeMatchLeft( "1");
                    o.t1.id().likeMatchLeft(false,  "1");
                    o.t1.id().likeMatchRight( "1");
                    o.t1.id().likeMatchRight(false,  "1");
                    o.t1.id().notLikeMatchLeft( "1");
                    o.t1.id().notLikeMatchLeft(false,  "1");
                    o.t1.id().notLikeMatchRight( "1");
                    o.t1.id().notLikeMatchRight(false,  "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false,o -> o.t1.createTime().asc())
                .orderByMerge(false,o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false,o -> o.groupTable().t1.id().count().ge(1L))
                .select(Topic.class, o -> Select.of(
                    o.groupTable().t1.id(),
                    o.groupTable().t1.id().count().as(Topic::getStars)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest3() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(Topic.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.id().eq(t4.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.id().eq(t5.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6) -> t.id().eq(t6.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7) -> t.id().eq(t7.id()))
                .where(o -> o.id().eq( "1"))
                .where(false, o -> o.id().eq( "1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq( "1");
                    o.t1.id().eq(false,  "1");
                    o.t1.id().ne( "1");
                    o.t1.id().ne(false,  "1");
                    o.t1.id().ge( "1");
                    o.t1.id().ge(false,  "1");
                    o.t1.id().gt( "1");
                    o.t1.id().gt(false,  "1");
                    o.t1.id().le( "1");
                    o.t1.id().le(false,  "1");
                    o.t1.id().lt( "1");
                    o.t1.id().lt(false,  "1");
                    o.t1.id().like( "1");
                    o.t1.id().like(false,  "1");
                    o.t1.id().notLike( "1");
                    o.t1.id().notLike(false,  "1");
                    o.t1.id().likeMatchLeft( "1");
                    o.t1.id().likeMatchLeft(false,  "1");
                    o.t1.id().likeMatchRight( "1");
                    o.t1.id().likeMatchRight(false,  "1");
                    o.t1.id().notLikeMatchLeft( "1");
                    o.t1.id().notLikeMatchLeft(false,  "1");
                    o.t1.id().notLikeMatchRight( "1");
                    o.t1.id().notLikeMatchRight(false,  "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false,o -> o.t1.createTime().asc())
                .orderByMerge(false,o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false,o -> o.groupTable().t1.id().count().ge(1L))
                .select(Topic.class, o -> Select.of(
                    o.groupTable().t1.id(),
                    o.groupTable().t1.id().count().as(Topic::getStars)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest4() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(Topic.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.id().eq(t4.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.id().eq(t5.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6) -> t.id().eq(t6.id()))
                .where(o -> o.id().eq( "1"))
                .where(false, o -> o.id().eq( "1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq( "1");
                    o.t1.id().eq(false,  "1");
                    o.t1.id().ne( "1");
                    o.t1.id().ne(false,  "1");
                    o.t1.id().ge( "1");
                    o.t1.id().ge(false,  "1");
                    o.t1.id().gt( "1");
                    o.t1.id().gt(false,  "1");
                    o.t1.id().le( "1");
                    o.t1.id().le(false,  "1");
                    o.t1.id().lt( "1");
                    o.t1.id().lt(false,  "1");
                    o.t1.id().like( "1");
                    o.t1.id().like(false,  "1");
                    o.t1.id().notLike( "1");
                    o.t1.id().notLike(false,  "1");
                    o.t1.id().likeMatchLeft( "1");
                    o.t1.id().likeMatchLeft(false,  "1");
                    o.t1.id().likeMatchRight( "1");
                    o.t1.id().likeMatchRight(false,  "1");
                    o.t1.id().notLikeMatchLeft( "1");
                    o.t1.id().notLikeMatchLeft(false,  "1");
                    o.t1.id().notLikeMatchRight( "1");
                    o.t1.id().notLikeMatchRight(false,  "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false,o -> o.t1.createTime().asc())
                .orderByMerge(false,o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false,o -> o.groupTable().t1.id().count().ge(1L))
                .select(Topic.class, o -> Select.of(
                    o.groupTable().t1.id(),
                    o.groupTable().t1.id().count().as(Topic::getStars)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest5() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(Topic.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.id().eq(t4.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.id().eq(t5.id()))
                .where(o -> o.id().eq( "1"))
                .where(false, o -> o.id().eq( "1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq( "1");
                    o.t1.id().eq(false,  "1");
                    o.t1.id().ne( "1");
                    o.t1.id().ne(false,  "1");
                    o.t1.id().ge( "1");
                    o.t1.id().ge(false,  "1");
                    o.t1.id().gt( "1");
                    o.t1.id().gt(false,  "1");
                    o.t1.id().le( "1");
                    o.t1.id().le(false,  "1");
                    o.t1.id().lt( "1");
                    o.t1.id().lt(false,  "1");
                    o.t1.id().like( "1");
                    o.t1.id().like(false,  "1");
                    o.t1.id().notLike( "1");
                    o.t1.id().notLike(false,  "1");
                    o.t1.id().likeMatchLeft( "1");
                    o.t1.id().likeMatchLeft(false,  "1");
                    o.t1.id().likeMatchRight( "1");
                    o.t1.id().likeMatchRight(false,  "1");
                    o.t1.id().notLikeMatchLeft( "1");
                    o.t1.id().notLikeMatchLeft(false,  "1");
                    o.t1.id().notLikeMatchRight( "1");
                    o.t1.id().notLikeMatchRight(false,  "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false,o -> o.t1.createTime().asc())
                .orderByMerge(false,o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false,o -> o.groupTable().t1.id().count().ge(1L))
                .select(Topic.class, o -> Select.of(
                    o.groupTable().t1.id(),
                    o.groupTable().t1.id().count().as(Topic::getStars)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest6() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(Topic.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.id().eq(t4.id()))
                .where(o -> o.id().eq( "1"))
                .where(false, o -> o.id().eq( "1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq( "1");
                    o.t1.id().eq(false,  "1");
                    o.t1.id().ne( "1");
                    o.t1.id().ne(false,  "1");
                    o.t1.id().ge( "1");
                    o.t1.id().ge(false,  "1");
                    o.t1.id().gt( "1");
                    o.t1.id().gt(false,  "1");
                    o.t1.id().le( "1");
                    o.t1.id().le(false,  "1");
                    o.t1.id().lt( "1");
                    o.t1.id().lt(false,  "1");
                    o.t1.id().like( "1");
                    o.t1.id().like(false,  "1");
                    o.t1.id().notLike( "1");
                    o.t1.id().notLike(false,  "1");
                    o.t1.id().likeMatchLeft( "1");
                    o.t1.id().likeMatchLeft(false,  "1");
                    o.t1.id().likeMatchRight( "1");
                    o.t1.id().likeMatchRight(false,  "1");
                    o.t1.id().notLikeMatchLeft( "1");
                    o.t1.id().notLikeMatchLeft(false,  "1");
                    o.t1.id().notLikeMatchRight( "1");
                    o.t1.id().notLikeMatchRight(false,  "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false,o -> o.t1.createTime().asc())
                .orderByMerge(false,o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false,o -> o.groupTable().t1.id().count().ge(1L))
                .select(Topic.class, o -> Select.of(
                    o.groupTable().t1.id(),
                    o.groupTable().t1.id().count().as(Topic::getStars)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest7() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(Topic.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq( "1"))
                .where(false, o -> o.id().eq( "1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq( "1");
                    o.t1.id().eq(false,  "1");
                    o.t1.id().ne( "1");
                    o.t1.id().ne(false,  "1");
                    o.t1.id().ge( "1");
                    o.t1.id().ge(false,  "1");
                    o.t1.id().gt( "1");
                    o.t1.id().gt(false,  "1");
                    o.t1.id().le( "1");
                    o.t1.id().le(false,  "1");
                    o.t1.id().lt( "1");
                    o.t1.id().lt(false,  "1");
                    o.t1.id().like( "1");
                    o.t1.id().like(false,  "1");
                    o.t1.id().notLike( "1");
                    o.t1.id().notLike(false,  "1");
                    o.t1.id().likeMatchLeft( "1");
                    o.t1.id().likeMatchLeft(false,  "1");
                    o.t1.id().likeMatchRight( "1");
                    o.t1.id().likeMatchRight(false,  "1");
                    o.t1.id().notLikeMatchLeft( "1");
                    o.t1.id().notLikeMatchLeft(false,  "1");
                    o.t1.id().notLikeMatchRight( "1");
                    o.t1.id().notLikeMatchRight(false,  "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false,o -> o.t1.createTime().asc())
                .orderByMerge(false,o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false,o -> o.groupTable().t1.id().count().ge(1L))
                .select(Topic.class, o -> Select.of(
                    o.groupTable().t1.id(),
                    o.groupTable().t1.id().count().as(Topic::getStars)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

}
