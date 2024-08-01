package com.easy.query.test;

import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api4j.func.LambdaSQLFunc;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.common.anonymous.AnonymousType2;
import com.easy.query.core.common.anonymous.AnonymousType3;
import com.easy.query.core.common.anonymous.AnonymousType4;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyTypeUtil;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeArrayJson;
import com.easy.query.test.entity.TopicTypeJsonValue;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.provider.MySQLLambdaProviderImpl;
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
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
//                        o.getWherePredicate().in("id", Arrays.asList("1","2"));

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
//            Integer integer = easyQuery.queryable(Topic.class)
//                    .where(o -> o.eq(Topic::getTitle, 111))
//                    .sumOrDefault(Topic::getStars, 0);
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
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.sqlFunc(o.fx().valueOrDefault("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc2() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.sqlFunc(o.fx().valueOrDefault("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }
    @Test
    public void testSQLFuncExtension1() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> {
                    o.eq("id", "1");
                    MySQLProviderImpl<Topic> mySQLProvider = new MySQLProviderImpl<>(o);
                    mySQLProvider.findInSet(c->c.value("1"),c->c.expression("id"));
                })
                .select(String.class, o -> o.sqlFunc(o.fx().valueOrDefault("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ? AND FIND_IN_SET(?,t.`id`)", sql1);
    }
    @Test
    public void testSQLFuncExtension2() {
        {
            String sql1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> {
                        o.eq("id", "1");
                        MySQLProviderImpl<Topic> mySQLProvider = new MySQLProviderImpl<>(o);
                        mySQLProvider.findInSet(c->c.value("1"),c->c.expression("id"));
                    })
                    .select(String.class, o -> o.column("id")).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t WHERE t.`id` = ? AND FIND_IN_SET(?,t.`id`)", sql1);
        }
        {
            String sql1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> {
                        o.eq("id", "1");
                        o.sqlNativeSegment("FIND_IN_SET({0},{1})",c->{
                            c.value("1").expression("id");
                        });
                    })
                    .select(String.class, o -> o.column("id")).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t WHERE t.`id` = ? AND FIND_IN_SET(?,t.`id`)", sql1);
        }
        {
            String sql1 = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.eq(Topic::getId, "1");
                        MySQLLambdaProviderImpl<Topic> mySQLProvider = new MySQLLambdaProviderImpl<>(o);
                        mySQLProvider.findInSet(c->c.value("1"),c->c.expression(Topic::getId));
                    })
                    .select(String.class, o -> o.column(Topic::getId)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t WHERE t.`id` = ? AND FIND_IN_SET(?,t.`id`)", sql1);
        }
    }

    @Test
    public void testSQLFunc2_2() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> {
                    o.eq("id", "1");//.use(new MySQLWherePredicater<>()).findInSet();
                })
                .select(String.class, o -> o.sqlFunc(o.fx().valueOrDefault("id", "1"))).toSQL();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) FROM `t_topic` t WHERE t.`id` = ?", sql1);
    }

    @Test
    public void testSQLFunc2_1() {
        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.sqlFunc(o.fx().valueOrDefault(x -> x.column("id").column("title").value("1")))).toSQL();
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
        List<Topic> list = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column(Topic::getCreateTime))
                .select(Topic.class, o -> o.sqlFuncAs(o.fx().dateTimeFormat(Topic::getCreateTime, "yyyy-MM-dd"), Topic::getTitle)).toList();
        for (Topic s : list) {
            Assert.assertEquals("2023-05-25", s.getTitle());
        }
        String sql = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1")
                        //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                )
                .orderByDesc(o -> o.column(Topic::getCreateTime))
                .select(Topic.class, o -> o.sqlFuncAs(o.fx().dateTimeFormat(Topic::getCreateTime, "yyyy-MM-dd"),  Topic::getTitle)).toSQL();
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
        {
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1")
                            //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                    )
                    .orderByDesc(o -> o.column(Topic::getCreateTime))
                    .select(Topic.class, o -> {
                        LambdaSQLFunc<Topic> sqlFunc = o.fx();
                        o.sqlFuncAs(sqlFunc.round(Topic::getStars, 1), Topic::getTitle);
                    }).toSQL();
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
        {
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1")
                            //        .rangeClosed("createTime",LocalDateTime.of(2023,1,1,0,0),LocalDateTime.of(2023,4,1,0,0))
                    )
                    .orderByDesc(o -> o.column(Topic::getCreateTime))
                    .select(Topic.class, o -> {
                        LambdaSQLFunc<Topic> sqlFunc = o.fx();
                        o.sqlFuncAs(sqlFunc.concat(Topic::getStars, Topic::getId), Topic::getTitle);
                    }).toSQL();
            Assert.assertEquals("SELECT CONCAT(t.`stars`,t.`id`) AS `title` FROM `t_topic` t WHERE t.`id` = ? ORDER BY t.`create_time` DESC", sql);
        }
    }


    @Test
    public void testSQLFunc11() {

        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> {
                    LambdaSQLFunc<SysUser> userSqlFunc = o.fx();
                    o.eq(userSqlFunc.valueOrDefault(SysUser::getId, "123"), userSqlFunc.valueOrDefault(x -> x.column(SysUser::getIdCard).value("1243")));
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
                    LambdaSQLFunc<SysUser> userSqlFunc = o.fx();
                    o.eq(userSqlFunc.valueOrDefault(SysUser::getId, "123"), "abc");
                });
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE IFNULL(`id`,?) = ?", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void testSQLFunc12() {
        {

            TopicProxy table = TopicProxy.createTable();
            String sql = easyProxyQuery.queryable(table)
                    .where(o ->o.id().nullOrDefault("123").eq(o.createTime().nullOrDefault((LocalDateTime) null)))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`id`,?) = IFNULL(`create_time`,?)", sql);
        }
        {
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(o.fx().valueOrDefault(Topic::getId, "123"), o.fx().valueOrDefault(Topic::getTitle, "456")))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`id`,?) = IFNULL(`title`,?)", sql);

        }
    }

    @Test
    public void testSQLFunc12_1() {
        {
            TopicProxy table = TopicProxy.createTable();
            String sql1 = easyProxyQuery.queryable(table)
                    .where(t -> t.id().nullOrDefault("123").eq(t.createTime().format("yyyy/MM/dd HH:mm:ss")))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`id`,?) = DATE_FORMAT(`create_time`,'%Y/%m/%d %H:%i:%s')", sql1);

        }
        {

            TopicProxy table = TopicProxy.createTable();
            Topic topic = easyProxyQuery.queryable(table)
                    .where(t -> t.id().nullOrDefault("123").eq(t.createTime().format("yyyy/MM/dd HH:mm:ss")))
                    .firstOrNull();
            Assert.assertNull(topic);
        }
        {

            TopicProxy table = TopicProxy.createTable();
            String topic = easyProxyQuery.queryable(table)
                    .where(o -> o.id().eq("1"))
                    .select(o -> new StringProxy(o.createTime().format("yyyy/MM/dd HH:mm:ss")))
                    .firstOrNull();
            Assert.assertEquals("2023/05/25 10:48:05", topic);
        }
        {

            TopicProxy table = TopicProxy.createTable();
            String topic = easyProxyQuery.queryable(table)
                    .where(o -> o.id().eq("1"))
                    .select(String.class, o -> o.createTime().format( "yyyy-MM/dd HH:mm:ss"))
                    .firstOrNull();
            Assert.assertEquals("2023-05/25 10:48:05", topic);
        }
        {

            TopicProxy table = TopicProxy.createTable();
            String topic = easyProxyQuery.queryable(table)
                    .where(o -> o.id().eq("1"))
                    .select(String.class, o -> o.createTime().format("yyyy-MM-dd HH:mm:ss"))
                    .firstOrNull();
            Assert.assertEquals("2023-05-25 10:48:05", topic);
        }
        {

            TopicProxy table = TopicProxy.createTable();
            String topic = easyProxyQuery.queryable(table)
                    .where(o -> o.id().eq("1"))
                    .select(String.class, o -> o.createTime().format("yyyy-MM-dd HH:mm"))
                    .firstOrNull();
            Assert.assertEquals("2023-05-25 10:48", topic);
        }
        {

            TopicProxy table = TopicProxy.createTable();
            String topic = easyProxyQuery.queryable(table)
                    .where(o -> o.id().eq( "1"))
                    .select(String.class, o -> o.createTime().format("yyyy-MM-dd HH"))
                    .firstOrNull();
            Assert.assertEquals("2023-05-25 10", topic);
        }
        {

            TopicProxy table = TopicProxy.createTable();
            String topic = easyProxyQuery.queryable(table)
                    .where(o -> o.id().eq("1"))
                    .select(String.class, o -> o.createTime().format("yyyy-MM-dd "))
                    .firstOrNull();
            Assert.assertEquals("2023-05-25 ", topic);
        }
        {

            TopicProxy table = TopicProxy.createTable();
            String topic = easyProxyQuery.queryable(table)
                    .where(o -> {
                        o.id().eq("1");
                        o.id().like("1");
                        o.id().ge("1");

                    })
                    .select(String.class, o -> o.createTime().format("yyyy"))
                    .firstOrNull();
            Assert.assertEquals("2023", topic);
        }
        {

            TopicProxy table = TopicProxy.createTable();
            String topic = easyProxyQuery.queryable(table)
                    .where(o -> o.id().eq("1"))
                    .select(String.class, o -> o.createTime().format("yyyy-MM"))
                    .firstOrNull();
            Assert.assertEquals("2023-05", topic);
        }
        {

            String topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1"))
                    .select(String.class, o -> o.sqlFunc(o.fx().dateTimeFormat(Topic::getCreateTime)))
                    .firstOrNull();
            Assert.assertEquals("2023-05-25 10:48:05.000000", topic);
        }
    }

    @Test
    public void testSQLFunc13() {
        {
            TopicProxy table = TopicProxy.createTable();
            String sql1 = easyProxyQuery.queryable(table)
                    .where(o -> o.id().nullOrDefault("123").eq("111"))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`id`,?) = ?", sql1);

        }
        {
            TopicProxy table = TopicProxy.createTable();
            Topic topic = easyProxyQuery.queryable(table)
                    .where(o -> o.id().nullOrDefault("123").eq("111"))
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

    @Test
    public void queryTest7() {
        {

            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "123"))
                    .firstOrNull();
            Assert.assertNull(topic);
        }
        {

            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1"))
                    .select(o->o.column(Topic::getId).column(Topic::getTitle))
                    .firstOrNull();
            Assert.assertNotNull(topic);
            Assert.assertNotNull(topic.getId());
            Assert.assertNotNull(topic.getTitle());
            Assert.assertNull(topic.getStars());
            Assert.assertNull(topic.getCreateTime());
        }
        {

            Queryable<Topic> query = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1"))
                    .select(Topic.class, o -> o.column(Topic::getId).column(Topic::getTitle));

            List<Topic> list = query.leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                    .where((t, t1) -> {
                        t1.eq(Topic::getId, "123");
                        t.eq(Topic::getId, "456");
                    }).toList();


            Assert.assertNotNull(list);
            Assert.assertEquals(0,list.size());
        }
        {

            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "123"))
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

    public Exception firstNotNull1(){

        try {

            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "123"))
                    .firstNotNull("未找到对应的数据");
        } catch (Exception ex) {
            return ex;
       }
        return null;
    }
    public Exception singleNotNull(String id){

        try {

            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, id))
                    .singleNotNull("未找到对应的数据");
        } catch (Exception ex) {
            return ex;
       }
        return null;
    }
    
    @Test
    public void joinTest1(){
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2) -> t.eq(t2, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.eq(t4, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.eq(t5, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6) -> t.eq(t6, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7) -> t.eq(t7, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> t.eq(t8, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> t.eq(t9, Topic::getId, Topic::getId))
                .where(o->o.eq(Topic::getId,1))
                .where(false,o->o.eq(Topic::getId,1))
                .whereById("1")
                .whereById(false,"1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o->o.column(Topic::getCreateTime))
                .orderByDesc(o->o.column(Topic::getCreateTime))
                .orderByAsc(false,o->o.column(Topic::getCreateTime))
                .orderByDesc(false,o->o.column(Topic::getCreateTime))
                .orderByAscMerge(o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o->o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false,o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false,o->o.t().column(Topic::getCreateTime))
                .groupByMerge(o->o.t().column(Topic::getId))
                .groupByMerge(false,o->o.t().column(Topic::getId))
                .havingMerge(o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .havingMerge(false,o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1",sql);
    }
    @Test
    public void joinTest2(){
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2) -> t.eq(t2, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.eq(t4, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.eq(t5, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6) -> t.eq(t6, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7) -> t.eq(t7, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> t.eq(t8, Topic::getId, Topic::getId))
                .where(o->o.eq(Topic::getId,1))
                .where(false,o->o.eq(Topic::getId,1))
                .whereById("1")
                .whereById(false,"1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o->o.column(Topic::getCreateTime))
                .orderByDesc(o->o.column(Topic::getCreateTime))
                .orderByAsc(false,o->o.column(Topic::getCreateTime))
                .orderByDesc(false,o->o.column(Topic::getCreateTime))
                .orderByAscMerge(o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o->o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false,o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false,o->o.t().column(Topic::getCreateTime))
                .groupByMerge(o->o.t().column(Topic::getId))
                .groupByMerge(false,o->o.t().column(Topic::getId))
                .havingMerge(o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .havingMerge(false,o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1",sql);
    }
    @Test
    public void joinTest3(){
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2) -> t.eq(t2, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.eq(t4, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.eq(t5, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6) -> t.eq(t6, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6, t7) -> t.eq(t7, Topic::getId, Topic::getId))
                .where(o->o.eq(Topic::getId,1))
                .where(false,o->o.eq(Topic::getId,1))
                .whereById("1")
                .whereById(false,"1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o->o.column(Topic::getCreateTime))
                .orderByDesc(o->o.column(Topic::getCreateTime))
                .orderByAsc(false,o->o.column(Topic::getCreateTime))
                .orderByDesc(false,o->o.column(Topic::getCreateTime))
                .orderByAscMerge(o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o->o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false,o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false,o->o.t().column(Topic::getCreateTime))
                .groupByMerge(o->o.t().column(Topic::getId))
                .groupByMerge(false,o->o.t().column(Topic::getId))
                .havingMerge(o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .havingMerge(false,o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1",sql);
    }
    @Test
    public void joinTest4(){
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2) -> t.eq(t2, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.eq(t4, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.eq(t5, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5, t6) -> t.eq(t6, Topic::getId, Topic::getId))
                .where(o->o.eq(Topic::getId,1))
                .where(false,o->o.eq(Topic::getId,1))
                .whereById("1")
                .whereById(false,"1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o->o.column(Topic::getCreateTime))
                .orderByDesc(o->o.column(Topic::getCreateTime))
                .orderByAsc(false,o->o.column(Topic::getCreateTime))
                .orderByDesc(false,o->o.column(Topic::getCreateTime))
                .orderByAscMerge(o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o->o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false,o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false,o->o.t().column(Topic::getCreateTime))
                .groupByMerge(o->o.t().column(Topic::getId))
                .groupByMerge(false,o->o.t().column(Topic::getId))
                .havingMerge(o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .havingMerge(false,o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1",sql);
    }
    @Test
    public void joinTest5(){
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2) -> t.eq(t2, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.eq(t4, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4, t5) -> t.eq(t5, Topic::getId, Topic::getId))
                .where(o->o.eq(Topic::getId,1))
                .where(false,o->o.eq(Topic::getId,1))
                .whereById("1")
                .whereById(false,"1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o->o.column(Topic::getCreateTime))
                .orderByDesc(o->o.column(Topic::getCreateTime))
                .orderByAsc(false,o->o.column(Topic::getCreateTime))
                .orderByDesc(false,o->o.column(Topic::getCreateTime))
                .orderByAscMerge(o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o->o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false,o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false,o->o.t().column(Topic::getCreateTime))
                .groupByMerge(o->o.t().column(Topic::getId))
                .groupByMerge(false,o->o.t().column(Topic::getId))
                .havingMerge(o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .havingMerge(false,o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1",sql);
    }
    @Test
    public void joinTest6(){
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2) -> t.eq(t2, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3, t4) -> t.eq(t4, Topic::getId, Topic::getId))
                .where(o->o.eq(Topic::getId,1))
                .where(false,o->o.eq(Topic::getId,1))
                .whereById("1")
                .whereById(false,"1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o->o.column(Topic::getCreateTime))
                .orderByDesc(o->o.column(Topic::getCreateTime))
                .orderByAsc(false,o->o.column(Topic::getCreateTime))
                .orderByDesc(false,o->o.column(Topic::getCreateTime))
                .orderByAscMerge(o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o->o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false,o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false,o->o.t().column(Topic::getCreateTime))
                .groupByMerge(o->o.t().column(Topic::getId))
                .groupByMerge(false,o->o.t().column(Topic::getId))
                .havingMerge(o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .havingMerge(false,o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1",sql);
    }
    @Test
    public void joinTest7(){
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2) -> t.eq(t2, Topic::getId, Topic::getId))
                .leftJoin(Topic.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, Topic::getId))
                .where(o->o.eq(Topic::getId,1))
                .where(false,o->o.eq(Topic::getId,1))
                .whereById("1")
                .whereById(false,"1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t().eq(Topic::getId, "1");
                    o.t().eq(false, Topic::getId, "1");
                    o.t().ne(Topic::getId, "1");
                    o.t().ne(false, Topic::getId, "1");
                    o.t().ge(Topic::getId, "1");
                    o.t().ge(false, Topic::getId, "1");
                    o.t().gt(Topic::getId, "1");
                    o.t().gt(false, Topic::getId, "1");
                    o.t().le(Topic::getId, "1");
                    o.t().le(false, Topic::getId, "1");
                    o.t().lt(Topic::getId, "1");
                    o.t().lt(false, Topic::getId, "1");
                    o.t().like(Topic::getId, "1");
                    o.t().like(false, Topic::getId, "1");
                    o.t().notLike(Topic::getId, "1");
                    o.t().notLike(false, Topic::getId, "1");
                    o.t().likeMatchLeft(Topic::getId, "1");
                    o.t().likeMatchLeft(false, Topic::getId, "1");
                    o.t().likeMatchRight(Topic::getId, "1");
                    o.t().likeMatchRight(false, Topic::getId, "1");
                    o.t().notLikeMatchLeft(Topic::getId, "1");
                    o.t().notLikeMatchLeft(false, Topic::getId, "1");
                    o.t().notLikeMatchRight(Topic::getId, "1");
                    o.t().notLikeMatchRight(false, Topic::getId, "1");
                })
                .limit(1, 2)
                .orderByAsc(o->o.column(Topic::getCreateTime))
                .orderByDesc(o->o.column(Topic::getCreateTime))
                .orderByAsc(false,o->o.column(Topic::getCreateTime))
                .orderByDesc(false,o->o.column(Topic::getCreateTime))
                .orderByAscMerge(o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(o->o.t().column(Topic::getCreateTime))
                .orderByAscMerge(false,o->o.t().column(Topic::getCreateTime))
                .orderByDescMerge(false,o->o.t().column(Topic::getCreateTime))
                .groupByMerge(o->o.t().column(Topic::getId))
                .groupByMerge(false,o->o.t().column(Topic::getId))
                .havingMerge(o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .havingMerge(false,o->o.t().count(Topic::getId, AggregatePredicateCompare.GE,1))
                .selectMerge(Topic.class, o -> {
                    o.t().column(Topic::getId);
                    o.t().columnCountAs(Topic::getId, Topic::getStars);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1",sql);
    }

}
