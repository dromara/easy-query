package com.easy.query.test;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeArrayJson;
import com.easy.query.test.entity.TopicTypeJsonValue;
import com.easy.query.test.enums.TopicTypeEnum;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
}
