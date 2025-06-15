package com.easy.query.test;

import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.tuple.Tuple4;
import com.easy.query.core.proxy.core.tuple.Tuple9;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
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
    @Test
    public void test2() {
        List<UserABC> list = easyEntityQuery.queryable(Topic.class)
                .select(UserABC.class,t-> Select.of(
                        t.createTime().valueConvert(d->d.format(DateTimeFormatter.ofPattern("yyyy-MM"))).as(UserABC.Fields.createTimeStr),
                        t.stars().valueConvert(d->d+1+"aaa").as(UserABC.Fields.starsStr),
                        t.createTime(),
                        t.stars()
                )).toList();
        System.out.println(list);
        for (UserABC item : list) {
            Assert.assertEquals(item.getStars()+1+"aaa",item.getStarsStr());
            Assert.assertEquals(item.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM")),item.getCreateTimeStr());
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
            Assert.assertEquals(item.getValue4()+1+"aaa",item.getValue2());
            Assert.assertEquals(item.getValue3().format(DateTimeFormatter.ofPattern("yyyy-MM")),item.getValue1());
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
            Assert.assertEquals(item.getValue4()+1+"aaa",item.getValue2());
            Assert.assertEquals(item.getValue3().format(DateTimeFormatter.ofPattern("yyyy-MM")),item.getValue1());
        }

    }
    @Test
    public void test5() {
        List<Tuple4<String, String, LocalDateTime, Integer>> list = easyEntityQuery.queryable(Topic.class)
                .select(t -> Select.TUPLE.of(
                        t.createTime().format("yyyy-MM").valueConvert(d -> d+"1"),
                        t.stars().valueConvert(d -> d + 1 + "aaa"),
                        t.createTime(),
                        t.stars()
                )).toList();
        System.out.println(list);
        for (Tuple4<String, String, LocalDateTime, Integer> item : list) {
            Assert.assertEquals(item.getValue4()+1+"aaa",item.getValue2());
            Assert.assertEquals(item.getValue3().format(DateTimeFormatter.ofPattern("yyyy-MM"))+"1",item.getValue1());
        }

    }
    @Test
    public void test6() {
        List<UserABC> list = easyEntityQuery.queryable(Topic.class)
                .select(UserABC.class,t-> Select.of(
                        t.createTime().format("yyyy-MM").valueConvert(d->d+"2").as(UserABC.Fields.createTimeStr),
                        t.stars().valueConvert(d->d+1+"aaa").as(UserABC.Fields.starsStr),
                        t.createTime(),
                        t.stars()
                )).toList();
        System.out.println(list);
        for (UserABC item : list) {
            Assert.assertEquals(item.getStars()+1+"aaa",item.getStarsStr());
            Assert.assertEquals(item.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM"))+"2",item.getCreateTimeStr());
        }

    }
    @Data
    @FieldNameConstants
    public static class UserABC{
        private String createTimeStr;
        private LocalDateTime createTime;
        private String starsStr;
        private Integer stars;
    }
}
