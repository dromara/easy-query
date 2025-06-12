package com.easy.query.test;

import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.util.List;

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
                            .id().set(t_topic.createTime().format("yyyy-MM-dd HH:mm:ss").valueConvert(s->s+".123"));
                }).toList();
        System.out.println(list);
        for (Topic topic : list) {
            Assert.assertEquals(topic.getStars() + "-",topic.getTitle());
            Assert.assertEquals(topic.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+".123", topic.getId());
        }

    }
}
