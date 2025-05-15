package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.pagination.EasyShardingPageResult;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.dto.TopicShardingGroup;
import com.easy.query.test.dto.TopicSubQueryBlog;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicSharding;
import com.easy.query.test.entity.TopicShardingDataSource;
import com.easy.query.test.entity.TopicShardingDataSourceTime;
import com.easy.query.test.entity.TopicShardingTime;
import com.easy.query.test.entity.proxy.TopicShardingTimeProxy;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create time 2023/4/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingTest extends BaseTest {
    @Test
    public void sharding1() {
        TopicSharding topicSharding = easyEntityQuery.queryable(TopicSharding.class)
                .whereById("123").firstOrNull();
        Assert.assertNotNull(topicSharding);
    }

    @Test
    public void sharding2() {
        String id = "999999";
        TopicSharding topicShardingTest = easyEntityQuery.queryable(TopicSharding.class)
                .whereById(id).firstOrNull();
        if (topicShardingTest != null) {
            long l = easyEntityQuery.deletable(topicShardingTest).executeRows();
            Assert.assertEquals(1, l);
        }

        TopicSharding topicSharding = new TopicSharding();
        topicSharding.setId(id);
        topicSharding.setTitle("title" + id);
        topicSharding.setStars(Integer.parseInt(id));
        topicSharding.setCreateTime(LocalDateTime.now());
        long l = easyEntityQuery.insertable(topicSharding).executeRows();

        TopicSharding topicSharding1 = easyEntityQuery.queryable(TopicSharding.class)
                .whereById(id).firstOrNull();
        Assert.assertNotNull(topicSharding1);
        long l1 = easyEntityQuery.deletable(TopicSharding.class).whereById(id).executeRows();
        Assert.assertEquals(1, l1);
    }

    @Test
    public void sharding3() {
        ArrayList<TopicSharding> topicShardings = new ArrayList<>(3);
        for (int i = 10000; i < 10003; i++) {
            TopicSharding topicSharding = new TopicSharding();
            topicSharding.setId(String.valueOf(i));
            topicSharding.setTitle("title" + i);
            topicSharding.setStars(i);
            topicSharding.setCreateTime(LocalDateTime.now());
            topicShardings.add(topicSharding);
        }
        List<String> deleteIds = topicShardings.stream().map(o -> o.getId()).collect(Collectors.toList());
        easyEntityQuery.deletable(TopicSharding.class)
                .where(o -> o.id().in(deleteIds)).executeRows();

        long l = easyEntityQuery.insertable(topicShardings).executeRows();
        List<TopicSharding> list = easyEntityQuery.queryable(TopicSharding.class)
                .where(o -> o.id().in(deleteIds)).toList();
        Assert.assertEquals(3, list.size());
        List<TopicSharding> orderList = easyEntityQuery.queryable(TopicSharding.class)
                .where(o -> o.id().in(deleteIds))
                .orderBy(o -> o.stars().asc())
                .toList();
        Assert.assertEquals(3, orderList.size());
        int i = 10000;
        for (TopicSharding topicSharding : orderList) {
            Assert.assertEquals(i, (int) topicSharding.getStars());
            i++;
        }
    }

    @Test
    public void sharding4() {
        List<TopicSharding> list = easyEntityQuery.queryable(TopicSharding.class).where(o -> o.stars().le(1000)).orderBy(o -> o.stars().desc()).toList();
        Assert.assertEquals(500, list.size());
        int i = 499;
        for (TopicSharding topicSharding : list) {
            Assert.assertEquals(i, (int) topicSharding.getStars());
            i--;
        }
    }

    @Test
    public void sharding5() {
        EasyPageResult<TopicSharding> pageResult = easyEntityQuery.queryable(TopicSharding.class).where(o -> o.stars().le(1000))
                .orderBy(o -> o.stars().desc()).toPageResult(1, 20);

        Assert.assertEquals(500, pageResult.getTotal());
        Assert.assertEquals(20, pageResult.getData().size());
        int i = 499;
        for (TopicSharding topicSharding : pageResult.getData()) {
            Assert.assertEquals(i, (int) topicSharding.getStars());
            i--;
        }
        EasyPageResult<TopicSharding> pageResult2 = easyEntityQuery.queryable(TopicSharding.class).where(o -> o.stars().le(1000))
                .orderBy(o -> o.stars().desc()).toPageResult(2, 20);
        Assert.assertEquals(500, pageResult2.getTotal());
        Assert.assertEquals(20, pageResult2.getData().size());
        int j = 479;
        for (TopicSharding topicSharding : pageResult2.getData()) {
            Assert.assertEquals(j, (int) topicSharding.getStars());
            j--;
        }
        EasyPageResult<TopicSharding> pageResult3 = easyEntityQuery.queryable(TopicSharding.class).where(o -> o.stars().le(1000))
                .orderBy(o -> o.stars().desc()).toPageResult(7, 7);
        Assert.assertEquals(500, pageResult3.getTotal());
        Assert.assertEquals(7, pageResult3.getData().size());
        int l = 457;
        for (TopicSharding topicSharding : pageResult3.getData()) {
            Assert.assertEquals(l, (int) topicSharding.getStars());
            l--;
        }
    }

    @Test
    public void sharding6() {

        easyEntityQuery.deletable(TopicSharding.class)
                .where(o -> {
                    o.stars().ge(20000);
                    o.stars().le(20100);
                }).executeRows();
        ArrayList<TopicSharding> topicShardings = new ArrayList<>(3);
        for (int i = 0; i < 100; i++) {
            TopicSharding topicSharding = new TopicSharding();
            topicSharding.setId(String.valueOf(i + 20000));
            topicSharding.setTitle("title" + (i % 2 == 0 ? "1" : i));
            topicSharding.setStars(i + 20000);
            topicSharding.setCreateTime(LocalDateTime.now());
            topicShardings.add(topicSharding);
        }
        long l = easyEntityQuery.insertable(topicShardings).executeRows();
        Assert.assertEquals(100, l);
        List<TopicSharding> list = easyEntityQuery.queryable(TopicSharding.class)
                .where(o -> {
                    o.stars().ge(20000);
                    o.stars().le(20100);
                })
                .orderBy(o -> o.stars().asc())
                .toList();
        Assert.assertEquals(100, list.size());
        int i = 0;
        for (TopicSharding topicSharding : list) {
            Assert.assertEquals(String.valueOf(i + 20000), topicSharding.getId());
            i++;
        }

        List<TopicShardingGroup> list1 = easyEntityQuery.queryable(TopicSharding.class)
                .where(o -> {
                    o.stars().ge(20000);
                    o.stars().le(20100);
                })
                .groupBy(t -> GroupKeys.of(t._title()))
                .orderBy(o -> o.key1().asc())
                .select(TopicShardingGroup.class, o -> Select.of(
                        o.key1(),
                        o.count(s -> s.id()).as(TopicShardingGroup::getIdCount)
                ))
                .toList();

        Assert.assertEquals(50, list1.size());
        for (TopicShardingGroup topicShardingGroup : list1) {
            if ("title1".equals(topicShardingGroup.getTitle())) {
                Assert.assertEquals(51, (int) topicShardingGroup.getIdCount());
            }
        }
        List<TopicShardingGroup> list2 = easyEntityQuery.queryable(TopicSharding.class)
                .where(o -> {
                    o.stars().ge(20000);
                    o.stars().le(20100);
                })
                .groupBy(t -> GroupKeys.of(t._title()))
                .select(TopicShardingGroup.class, o -> Select.of(
                        o.key1(),
                        o.count(s -> s.id()).as(TopicShardingGroup::getIdCount)
                ))
                .toList();

        Assert.assertEquals(50, list2.size());
        for (TopicShardingGroup topicShardingGroup : list2) {
            if ("title1".equals(topicShardingGroup.getTitle())) {
                Assert.assertEquals(51, (int) topicShardingGroup.getIdCount());
            }
        }
    }

    @Test
    public void sharding7() {
        easyEntityQuery.deletable(TopicSharding.class)
                .where(o -> {
                    o.stars().ge(30000);
                    o.stars().le(30100);
                }).executeRows();
        ArrayList<TopicSharding> topicShardings = new ArrayList<>(3);
        for (int i = 0; i < 100; i++) {
            TopicSharding topicSharding = new TopicSharding();
            topicSharding.setId(String.valueOf(i + 30000));
            topicSharding.setTitle("title" + (i % 2 == 0 ? "1" : i));
            topicSharding.setStars(i + 30000);
            topicSharding.setCreateTime(LocalDateTime.now());
            topicShardings.add(topicSharding);
        }
        long l = easyEntityQuery.insertable(topicShardings).executeRows();
        Assert.assertEquals(100, l);
        long count = easyEntityQuery.queryable(TopicSharding.class).where(o -> {
                    o.stars().ge(30000);
                    o.stars().le(30100);
                })
                .count();
        Assert.assertEquals(100, count);
    }

    @Test
    public void sharding8() {
        easyEntityQuery.deletable(TopicSharding.class)
                .where(o -> {
                    o.stars().ge(40000);
                    o.stars().le(40100);
                }).executeRows();
        ArrayList<TopicSharding> topicShardings = new ArrayList<>(3);
        for (int i = 0; i < 100; i++) {
            TopicSharding topicSharding = new TopicSharding();
            topicSharding.setId(String.valueOf(i + 40000));
            topicSharding.setTitle("title" + (i % 2 == 0 ? "1" : i));
            topicSharding.setStars(i + 40000);
            topicSharding.setCreateTime(LocalDateTime.now());
            topicShardings.add(topicSharding);
        }
        long l = easyEntityQuery.insertable(topicShardings).executeRows();
        Assert.assertEquals(100, l);
        long count = easyEntityQuery.queryable(TopicSharding.class).where(o -> {
                    o.stars().ge(40000);
                    o.stars().lt(40050);
                })
                .count();
        Assert.assertEquals(50, count);
    }

    @Test
    public void sharding9() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        List<TopicShardingTime> list = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toList();
        Assert.assertEquals(days, list.size());
        for (int i = 0; i < days; i++) {
            TopicShardingTime topicShardingTime = list.get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding10() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toPageResult(1, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding11() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toPageResult(2, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(33);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding12() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toPageResult(3, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(66);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

//    @Test
//    public void sharding13() {
//
//        TopicShardingTime topicShardingTime1 = easyEntityQuery.queryable(TopicShardingTime.class).orderBy(o -> o.createTime().desc()).firstOrNull();
//        Assert.assertNotNull(topicShardingTime1);
//        LocalDateTime localDateTime = easyEntityQuery.queryable(TopicShardingTime.class)
//                .maxOrNull(s->s.createTime());
//
//        Assert.assertNotNull(localDateTime);
//        Assert.assertEquals(localDateTime, topicShardingTime1.getCreateTime());
//    }
//
//    @Test
//    public void sharding15() {
//        easyEntityQuery.deletable(TopicSharding.class)
//                .where(o -> {o.stars().ge(50000);o.stars().le(50100);}).executeRows();
//        ArrayList<TopicSharding> topicShardings = new ArrayList<>(3);
//        for (int i = 0; i < 100; i++) {
//            TopicSharding topicSharding = new TopicSharding();
//            topicSharding.setId(String.valueOf(i + 50000));
//            topicSharding.setTitle("title" + (i % 2 == 0 ? "1" : i));
//            topicSharding.setStars(i + 50000);
//            topicSharding.setCreateTime(LocalDateTime.now());
//            topicShardings.add(topicSharding);
//        }
//        long l = easyEntityQuery.insertable(topicShardings).executeRows();
//        Assert.assertEquals(100, l);
//        long count = easyEntityQuery.queryable(TopicSharding.class).where(o -> {o.stars().ge(50000);o.stars().lt(50099);})
//                .count();
//        Assert.assertEquals(99, count);
//        TopicSharding topicSharding = easyEntityQuery.queryable(TopicSharding.class).where(o -> {o.stars().ge(50000);o.stars().lt(50100);})
//                .orderBy(o -> o.stars().desc()).firstOrNull();
//        Assert.assertNotNull(topicSharding);
//        Integer maxStars = easyEntityQuery.queryable(TopicSharding.class).where(o -> {o.stars().ge(50000);o.stars().lt(50100);})
//                .maxOrNull(s->s.stars());
//        Assert.assertNotNull(maxStars);
//        Assert.assertEquals(topicSharding.getStars(), maxStars);
//    }

    //    @Test
//    public void sharding14(){
//
//        boolean all1 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
//        Assert.assertFalse(all1);
//        boolean all2 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
//        Assert.assertFalse(all2);
//        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
//        boolean all3 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
//        Assert.assertFalse(all3);
//        boolean all4 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .all(o -> o.ge(TopicShardingTime::getCreateTime, beginTime));
//        Assert.assertTrue(all4);
//        boolean all5 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .all(o -> o.le(TopicShardingTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
//        Assert.assertTrue(all5);
//        boolean all6 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .all(o -> o.lt(TopicShardingTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
//        Assert.assertTrue(all6);
//        boolean all7 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .all(o -> o.ge(TopicShardingTime::getCreateTime, beginTime.plusDays(-1)));
//        Assert.assertTrue(all7);
//        boolean all8 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .all(o -> o.ge(TopicShardingTime::getCreateTime, beginTime.plusDays(1)));
//        Assert.assertFalse(all8);
//    }
//    @Test
//    public void sharding16() {
//
//        TopicShardingTime topicShardingTime1 = easyEntityQuery.queryable(TopicShardingTime.class).orderBy(o -> o.createTime().asc()).firstOrNull();
//        Assert.assertNotNull(topicShardingTime1);
//        LocalDateTime localDateTime = easyEntityQuery.queryable(TopicShardingTime.class)
//                .minOrNull(s->s.createTime());
//
//        Assert.assertNotNull(localDateTime);
//        Assert.assertEquals(localDateTime, topicShardingTime1.getCreateTime());
//    }
//    @Test
//    public void sharding17(){
//
//        boolean all1 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .where(o->o.le(TopicShardingTime::getCreateTime,LocalDateTime.of(2099, 1, 1, 1, 1)))
//                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
//        Assert.assertFalse(all1);
//        boolean all2 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .where(o->o.ge(TopicShardingTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1)))
//                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
//        Assert.assertTrue(all2);
//        boolean all21 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .where(o->o.ge(TopicShardingTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1).plusDays(-1)))
//                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
//        Assert.assertFalse(all21);
//        boolean all22 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .where(o->o.ge(TopicShardingTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1).plusDays(1)))
//                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
//        Assert.assertTrue(all22);
//        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
//        boolean all3 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
//        Assert.assertFalse(all3);
//        boolean all31 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .where(o -> o.gt(TopicShardingTime::getCreateTime, beginTime))
//                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
//        Assert.assertTrue(all31);
//        boolean all32 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .where(o -> o.ge(TopicShardingTime::getCreateTime, beginTime))
//                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
//        Assert.assertFalse(all32);
//        boolean all33 = easyEntityQuery.queryable(TopicShardingTime.class)
//                .where(o -> o.rangeClosed(TopicShardingTime::getCreateTime,true,beginTime,true,beginTime))
//                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
//        Assert.assertFalse(all33);
//    }


    @Test
    public void sharding18() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        List<TopicShardingDataSourceTime> list = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
                .orderBy(o -> o.createTime().asc())
                .toList();
        Assert.assertEquals(days, list.size());
        for (int i = 0; i < days; i++) {
            TopicShardingDataSourceTime topicShardingTime = list.get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding19() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingDataSourceTime> pageResult = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
                .orderBy(o -> o.createTime().asc())
                .toPageResult(1, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        for (int i = 0; i < 33; i++) {
            TopicShardingDataSourceTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding20() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingDataSourceTime> pageResult = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
                .orderBy(o -> o.createTime().asc())
                .toPageResult(2, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(33);
        for (int i = 0; i < 33; i++) {
            TopicShardingDataSourceTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding21() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingDataSourceTime> pageResult = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
                .orderBy(o -> o.createTime().asc())
                .toPageResult(3, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(66);
        for (int i = 0; i < 33; i++) {
            TopicShardingDataSourceTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

//    @Test
//    public void sharding22() {
//
//        TopicShardingDataSourceTime topicShardingTime1 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class).orderBy(o -> o.createTime().desc()).firstOrNull();
//        Assert.assertNotNull(topicShardingTime1);
//        LocalDateTime localDateTime = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .maxOrNull(s->s.createTime());
//
//        Assert.assertNotNull(localDateTime);
//        Assert.assertEquals(localDateTime, topicShardingTime1.getCreateTime());
//    }

    //    @Test
//    public void sharding23(){
//
//        boolean all1 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
//        Assert.assertFalse(all1);
//        boolean all2 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
//        Assert.assertFalse(all2);
//        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
//        boolean all3 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
//        Assert.assertFalse(all3);
//        boolean all4 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, beginTime));
//        Assert.assertTrue(all4);
//        boolean all5 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .all(o -> o.le(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
//        Assert.assertTrue(all5);
//        boolean all6 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .all(o -> o.lt(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
//        Assert.assertTrue(all6);
//        boolean all7 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, beginTime.plusDays(-1)));
//        Assert.assertTrue(all7);
//        boolean all8 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, beginTime.plusDays(1)));
//        Assert.assertFalse(all8);
//    }
//    @Test
//    public void sharding24() {
//
//        TopicShardingDataSourceTime topicShardingTime1 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class).orderBy(o -> o.createTime().asc()).firstOrNull();
//        Assert.assertNotNull(topicShardingTime1);
//        LocalDateTime localDateTime = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .minOrNull(s-> s.createTime());
//
//        Assert.assertNotNull(localDateTime);
//        Assert.assertEquals(localDateTime, topicShardingTime1.getCreateTime());
//    }

    //    @Test
//    public void sharding25(){
//
//        boolean all1 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .where(o->o.le(TopicShardingDataSourceTime::getCreateTime,LocalDateTime.of(2099, 1, 1, 1, 1)))
//                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
//        Assert.assertFalse(all1);
//        boolean all2 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .where(o->o.ge(TopicShardingDataSourceTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1)))
//                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
//        Assert.assertTrue(all2);
//        boolean all21 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .where(o->o.ge(TopicShardingDataSourceTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1).plusDays(-1)))
//                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
//        Assert.assertFalse(all21);
//        boolean all22 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .where(o->o.ge(TopicShardingDataSourceTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1).plusDays(1)))
//                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
//        Assert.assertTrue(all22);
//        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
//        boolean all3 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
//        Assert.assertFalse(all3);
//        boolean all31 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .where(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime))
//                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
//        Assert.assertTrue(all31);
//        boolean all32 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .where(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, beginTime))
//                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
//        Assert.assertFalse(all32);
//        boolean all33 = easyEntityQuery.queryable(TopicShardingDataSourceTime.class)
//                .where(o -> o.rangeClosed(TopicShardingDataSourceTime::getCreateTime,true,beginTime,true,beginTime))
//                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
//        Assert.assertFalse(all33);
//    }
    @Test
    public void sharding26() {
        List<TopicShardingTime> list = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().desc())
                .limit(10).toList();
        Assert.assertEquals(10, list.size());

        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        for (TopicShardingTime topicShardingTime : list) {
            endTime = endTime.plusDays(-1);
            Assert.assertEquals(endTime, topicShardingTime.getCreateTime());
        }
        TopicShardingTime topicShardingTime = easyEntityQuery.queryable(TopicShardingTime.class).whereById(list.get(0).getId()).firstOrNull();
        Assert.assertEquals(topicShardingTime.getId(), list.get(0).getId());
        Assert.assertEquals(topicShardingTime.getCreateTime(), list.get(0).getCreateTime());

    }

    @Test
    public void sharding27() {
        List<TopicShardingTime> list = easyEntityQuery.queryable(TopicShardingTime.class)
                .limit(10).toList();
        Assert.assertEquals(10, list.size());

        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        for (TopicShardingTime topicShardingTime : list) {
            endTime = endTime.plusDays(-1);
            Assert.assertEquals(endTime, topicShardingTime.getCreateTime());
        }
        TopicShardingTime topicShardingTime = easyEntityQuery.queryable(TopicShardingTime.class).whereById(list.get(0).getId()).firstOrNull();
        Assert.assertEquals(topicShardingTime.getId(), list.get(0).getId());
        Assert.assertEquals(topicShardingTime.getCreateTime(), list.get(0).getCreateTime());

    }


    @Test
    public void sharding28() {
        EasyPageResult<TopicSharding> pageResult = easyEntityQuery.queryable(TopicSharding.class).where(o -> o.stars().le(1000))
                .orderBy(o -> o.stars().desc()).toShardingPageResult(1, 20);

        Assert.assertEquals(500, pageResult.getTotal());
        Assert.assertEquals(20, pageResult.getData().size());
        int i = 499;
        for (TopicSharding topicSharding : pageResult.getData()) {
            Assert.assertEquals(i, (int) topicSharding.getStars());
            i--;
        }
        EasyPageResult<TopicSharding> pageResult2 = easyEntityQuery.queryable(TopicSharding.class).where(o -> o.stars().le(1000))
                .orderBy(o -> o.stars().desc()).toShardingPageResult(2, 20);
        Assert.assertEquals(500, pageResult2.getTotal());
        Assert.assertEquals(20, pageResult2.getData().size());
        int j = 479;
        for (TopicSharding topicSharding : pageResult2.getData()) {
            Assert.assertEquals(j, (int) topicSharding.getStars());
            j--;
        }
        EasyPageResult<TopicSharding> pageResult3 = easyEntityQuery.queryable(TopicSharding.class).where(o -> o.stars().le(1000))
                .orderBy(o -> o.stars().desc()).toShardingPageResult(7, 7);
        Assert.assertEquals(500, pageResult3.getTotal());
        Assert.assertEquals(7, pageResult3.getData().size());
        int l = 457;
        for (TopicSharding topicSharding : pageResult3.getData()) {
            Assert.assertEquals(l, (int) topicSharding.getStars());
            l--;
        }
    }


    @Test
    public void sharding29() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toShardingPageResult(2, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(33);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding30() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toShardingPageResult(3, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(66);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding31() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.stars().asc())
                .toShardingPageResult(2, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(33);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding32() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.stars().asc())
                .toShardingPageResult(30, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(957);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding33() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.stars().asc())
                .toShardingPageResult(3, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(66);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding34() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toShardingPageResult(30, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(957);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
        EasyShardingPageResult<TopicShardingTime> pageResult1 = (EasyShardingPageResult<TopicShardingTime>) pageResult;
        List<Long> totalLines = pageResult1.getTotalLines();
        System.out.println(totalLines);
        totalLines.set(0, -1L);
        System.out.println(totalLines);
        EasyPageResult<TopicShardingTime> pageResult2 = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toShardingPageResult(30, 33, totalLines);
        EasyShardingPageResult<TopicShardingTime> pageResult13 = (EasyShardingPageResult<TopicShardingTime>) pageResult2;

        System.out.println(pageResult13.getTotalLines());
        Assert.assertEquals(days, pageResult2.getTotal());
        beginTime = LocalDateTime.of(2020, 1, 1, 1, 1).plusDays(957);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult2.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding35() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toShardingPageResult(3, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        beginTime = beginTime.plusDays(66);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
        EasyShardingPageResult<TopicShardingTime> pageResult1 = (EasyShardingPageResult<TopicShardingTime>) pageResult;
        List<Long> totalLines = pageResult1.getTotalLines();
        int size = totalLines.size();
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                totalLines.set(i, -1L);
            }
        }
        System.out.println("----------");
        EasyPageResult<TopicShardingTime> pageResult2 = easyEntityQuery.queryable(TopicShardingTime.class)
                .orderBy(o -> o.createTime().asc())
                .toShardingPageResult(3, 33, totalLines);
        Assert.assertEquals(days, pageResult2.getTotal());
        beginTime = LocalDateTime.of(2020, 1, 1, 1, 1).plusDays(66);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult2.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicShardingTime.getCreateTime());
        }
    }

    @Test
    public void sharding36() {
        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        EasyPageResult<TopicShardingTime> pageResult = easyEntityQuery.queryable(TopicShardingTime.class)
                .where(o -> o.createTime().le(beginTime))
                .where(o -> o.createTime().ge(endTime))
                .orderBy(o -> o.createTime().asc())
                .toShardingPageResult(1, 10);
        Assert.assertEquals(0, pageResult.getTotal());
        Assert.assertEquals(0, pageResult.getData().size());
    }

    @Test
    public void sharding37() {

        List<TopicShardingTime> list = easyEntityQuery.queryable(TopicShardingTime.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> {
                    t.id().eq("0de2e78681a64343a98b0aab3c039b3a202003");
                    t1.id().eq("1");
                })
                .toList();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void sharding38() {

        List<TopicShardingTime> list = easyEntityQuery.queryable(TopicShardingTime.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> {
                    t.title().eq("0de2e78681a64343a98b0aab3c039b3a202003");
                    t1.id().eq("1");
                })
                .toList();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void sharding39() {

        LocalDateTime beginTime = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2022, 5, 1, 1, 1);
        List<TopicShardingTime> list = easyEntityQuery.queryable(TopicShardingTime.class)
                .where(o -> {
                    o.or(() -> {
                        o.createTime().rangeOpen(beginTime, endTime);
                        o.id().eq("123");
                    });
                }).toList();

        EntityQueryable<TopicShardingTimeProxy, TopicShardingTime> topicShardingTimeQueryable = easyEntityQuery.queryable(TopicShardingTime.class)
                .where(o -> o.createTime().rangeOpen(beginTime, endTime));
        Query<Topic> where = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.id().eq("123");
                            o.expression().exists(()->{
                                return topicShardingTimeQueryable.where(x -> x.id().eq(o.id()));
                            });
                });
        String sql = where.toSQL();
        //tosql是不会对表进行分片的展示
        System.out.println(sql);
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ? AND EXISTS (SELECT 1 FROM `t_topic_sharding_time` t1 WHERE (t1.`create_time` > ? AND t1.`create_time` < ?) AND t1.`id` = t.`id`)", sql);
        List<Topic> list1 = where.toList();

        Assert.assertEquals(0, list1.size());
    }

    @Test
    public void sharding40() {
        LocalDateTime beginTime = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2021, 2, 1, 1, 1);

        EntityQueryable<TopicShardingTimeProxy, TopicShardingTime> topicShardingTimeQueryable1 = easyEntityQuery.queryable(TopicShardingTime.class).where(o -> o.createTime().rangeOpen(beginTime, endTime));
        EntityQueryable<TopicShardingTimeProxy, TopicShardingTime> topicShardingTimeQueryable2 = easyEntityQuery.queryable(TopicShardingTime.class).where(o -> o.createTime().rangeOpen(beginTime, endTime));

        List<TopicShardingTime> list = topicShardingTimeQueryable1.union(topicShardingTimeQueryable2).toList();
        Assert.assertEquals(90, list.size());
        List<TopicShardingTime> list2 = topicShardingTimeQueryable1.unionAll(topicShardingTimeQueryable2).toList();
        Assert.assertEquals(90, list.size());
    }

    @Test
    public void sharding41() {
        LocalDateTime beginTime = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2021, 3, 1, 1, 1);

        EntityQueryable<TopicShardingTimeProxy, TopicShardingTime> topicShardingTimeQueryable1 = easyEntityQuery.queryable(TopicShardingTime.class).where(o -> o.createTime().rangeOpen(beginTime, endTime));
        EntityQueryable<TopicShardingTimeProxy, TopicShardingTime> topicShardingTimeQueryable2 = easyEntityQuery.queryable(TopicShardingTime.class).where(o -> o.createTime().rangeOpen(beginTime, endTime));

        List<TopicShardingTime> list = topicShardingTimeQueryable1.union(topicShardingTimeQueryable2).toList();
        Assert.assertEquals(290, list.size());
        List<TopicShardingTime> list2 = topicShardingTimeQueryable1.unionAll(topicShardingTimeQueryable2).toList();
        Assert.assertEquals(290, list.size());
    }

    @Test
    public void sharding42() {
        LocalDateTime beginTime = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2022, 5, 1, 1, 1);

        EntityQueryable<TopicShardingTimeProxy, TopicShardingTime> topicShardingTimeQueryable1 = easyEntityQuery.queryable(TopicShardingTime.class).where(o -> o.createTime().rangeOpen(beginTime, endTime));
        EntityQueryable<TopicShardingTimeProxy, TopicShardingTime> topicShardingTimeQueryable2 = easyEntityQuery.queryable(TopicShardingTime.class).where(o -> o.createTime().rangeOpen(beginTime, endTime));

        List<TopicShardingTime> list = topicShardingTimeQueryable1.union(topicShardingTimeQueryable2).toList();
        Assert.assertEquals(15972, list.size());
        List<TopicShardingTime> list2 = topicShardingTimeQueryable1.unionAll(topicShardingTimeQueryable2).toList();
        Assert.assertEquals(15972, list.size());
    }


    @Test
    public void sharding43() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        List<TopicShardingDataSource> list = easyEntityQuery.queryable(TopicShardingDataSource.class)
                .orderBy(o -> o.createTime().asc())
                .toList();
        Assert.assertEquals(days, list.size());
        for (int i = 0; i < days; i++) {
            TopicShardingDataSource topicSharding = list.get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicSharding.getCreateTime());
        }
    }

    @Test
    public void sharding44() {

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingDataSource> pageResult = easyEntityQuery.queryable(TopicShardingDataSource.class)
                .orderBy(o -> o.createTime().asc())
                .toPageResult(1, 33);
        Assert.assertEquals(days, pageResult.getTotal());
        for (int i = 0; i < 33; i++) {
            TopicShardingDataSource topicSharding = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i), topicSharding.getCreateTime());
        }
    }

    @Test
    public void sharding45() {
        LocalDateTime beginTime = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2021, 5, 2, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        List<TopicShardingTime> list = easyEntityQuery.queryable(TopicShardingTime.class)
                .where(o -> o.createTime().rangeClosed(beginTime, endTime))
                .orderBy(o -> o.createTime().asc())
                .toList();
        Assert.assertEquals(days + 1, list.size());
    }

    @Test
    public void sharding46() {
        LocalDateTime beginTime = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2021, 5, 2, 1, 1);
        EntityQueryable<TopicShardingTimeProxy, TopicShardingTime> queryable = easyEntityQuery.queryable(TopicShardingTime.class)
                .where(o -> o.createTime().rangeClosed(beginTime, endTime));
        Query<TopicSubQueryBlog> select = easyEntityQuery
                .queryable(Topic.class)
                .where(t -> t.title().isNotNull())
                .select(TopicSubQueryBlog.class, t_topic -> Select.of(
                        t_topic.FETCHER.allFieldsExclude(t_topic.createTime()),
                        t_topic.expression().subQuery(() -> {
                            return queryable.where(x -> x.id().eq(t_topic.id())).selectColumn(t -> t.id().count());
                        }).as(TopicSubQueryBlog::getBlogCount)
                ));
        String sql = select.toSQL();
        System.out.println(sql);
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,(SELECT COUNT(t1.`id`) AS `id` FROM `t_topic_sharding_time` t1 WHERE (t1.`create_time` >= ? AND t1.`create_time` <= ?) AND t1.`id` = t.`id`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", sql);
        //sharding 需要聚合
        List<TopicSubQueryBlog> list = select.toList();
        Assert.assertEquals(99, list.size());
    }

    @Test
    public void sharding47() {
        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2020, 5, 2, 1, 1);
        EntityQueryable<TopicShardingTimeProxy, TopicShardingTime> queryable = easyEntityQuery.queryable(TopicShardingTime.class)
                .where(o -> o.createTime().rangeClosed(beginTime, endTime));
        Query<TopicSubQueryBlog> select = easyEntityQuery
                .queryable(Topic.class)
                .where(t -> t.title().isNotNull())
                .select(TopicSubQueryBlog.class, t_topic -> Select.of(
                        t_topic.FETCHER.allFieldsExclude(t_topic.createTime()),
                        t_topic.expression().subQuery(() -> {
                            return queryable.where(x -> x.stars().eq(t_topic.stars())).selectColumn(t -> t.id().count());
                        }).as(TopicSubQueryBlog::getBlogCount)
                ));
        String sql = select.toSQL();
        System.out.println(sql);
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,(SELECT COUNT(t1.`id`) AS `id` FROM `t_topic_sharding_time` t1 WHERE (t1.`create_time` >= ? AND t1.`create_time` <= ?) AND t1.`stars` = t.`stars`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", sql);
        //sharding 需要聚合
        List<TopicSubQueryBlog> list = select.toList();
        Assert.assertEquals(99, list.size());

        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        for (TopicSubQueryBlog topicSubQueryBlog : list) {

            if (topicSubQueryBlog.getStars() <= days) {
                Assert.assertEquals(1, (long) topicSubQueryBlog.getBlogCount());
            } else {
                Assert.assertEquals(0, (long) topicSubQueryBlog.getBlogCount());
            }
        }
    }

    @Test
    public void shardingTest48() {
        EntityMetadataManager entityMetadataManager = easyEntityQuery.getRuntimeContext().getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(TopicSharding.class);
        Set<String> shardingTablePropertyNames = entityMetadata.getShardingTablePropertyNames();
        Assert.assertEquals(0, entityMetadata.getShardingDataSourcePropertyNames().size());
        Assert.assertEquals(1, shardingTablePropertyNames.size());
        Assert.assertTrue(shardingTablePropertyNames.contains("id"));
        Assert.assertEquals(entityMetadata.getShardingTablePropertyName(), "id");
    }

    @Test
    public void shardingTest49() {
        EntityMetadataManager entityMetadataManager = easyEntityQuery.getRuntimeContext().getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(TopicShardingDataSource.class);
        Set<String> shardingTablePropertyNames = entityMetadata.getShardingTablePropertyNames();
        Assert.assertEquals(0, shardingTablePropertyNames.size());
        Set<String> shardingDataSourcePropertyNames = entityMetadata.getShardingDataSourcePropertyNames();
        Assert.assertTrue(shardingDataSourcePropertyNames.contains("createTime"));
        Assert.assertEquals(entityMetadata.getShardingDataSourcePropertyName(), "createTime");
    }

    @Test
    public void shardingTest50() {
        List<TopicShardingGroup> list1 = easyEntityQuery.queryable(TopicSharding.class)
                .where(o -> o.id().eq("20000"))
                .groupBy(o -> GroupKeys.of(o._title()))
                .orderBy(o -> o.key1().asc())
                .select(TopicShardingGroup.class, o -> Select.of(
                        o.key1(),
                        o.count(s -> s.id()).as(TopicShardingGroup::getIdCount)
                ))
                .toList();
    }
}
