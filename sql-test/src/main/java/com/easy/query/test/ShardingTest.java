package com.easy.query.test;

import com.easy.query.test.BaseTest;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.test.dto.TopicShardingGroup;
import com.easy.query.test.entity.TopicSharding;
import com.easy.query.test.entity.TopicShardingDataSourceTime;
import com.easy.query.test.entity.TopicShardingTime;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        TopicSharding topicSharding = easyQuery.queryable(TopicSharding.class)
                .whereById("123").firstOrNull();
        Assert.assertNotNull(topicSharding);
    }

    @Test
    public void sharding2() {
        String id = "999999";
        TopicSharding topicShardingTest = easyQuery.queryable(TopicSharding.class)
                .whereById(id).firstOrNull();
        if (topicShardingTest != null) {
            long l = easyQuery.deletable(topicShardingTest).executeRows();
            Assert.assertEquals(1, l);
        }

        TopicSharding topicSharding = new TopicSharding();
        topicSharding.setId(id);
        topicSharding.setTitle("title" + id);
        topicSharding.setStars(Integer.parseInt(id));
        topicSharding.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(topicSharding).executeRows();

        TopicSharding topicSharding1 = easyQuery.queryable(TopicSharding.class)
                .whereById(id).firstOrNull();
        Assert.assertNotNull(topicSharding1);
        long l1 = easyQuery.deletable(TopicSharding.class).whereById(id).executeRows();
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
        easyQuery.deletable(TopicSharding.class)
                .where(o -> o.in(TopicSharding::getId, deleteIds)).executeRows();

        long l = easyQuery.insertable(topicShardings).executeRows();
        List<TopicSharding> list = easyQuery.queryable(TopicSharding.class)
                .where(o -> o.in(TopicSharding::getId, deleteIds)).toList();
        Assert.assertEquals(3, list.size());
        List<TopicSharding> orderList = easyQuery.queryable(TopicSharding.class)
                .where(o -> o.in(TopicSharding::getId, deleteIds))
                .orderByAsc(o -> o.column(TopicSharding::getStars))
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
        List<TopicSharding> list = easyQuery.queryable(TopicSharding.class).where(o -> o.le(TopicSharding::getStars, 1000)).orderByDesc(o -> o.column(TopicSharding::getStars)).toList();
        Assert.assertEquals(500, list.size());
        int i = 499;
        for (TopicSharding topicSharding : list) {
            Assert.assertEquals(i, (int) topicSharding.getStars());
            i--;
        }
    }
    @Test
    public void sharding5() {
        EasyPageResult<TopicSharding> pageResult = easyQuery.queryable(TopicSharding.class).where(o -> o.le(TopicSharding::getStars, 1000))
                .orderByDesc(o -> o.column(TopicSharding::getStars)).toPageResult(1, 20);

        Assert.assertEquals(500, pageResult.getTotal());
        Assert.assertEquals(20, pageResult.getData().size());
        int i = 499;
        for (TopicSharding topicSharding : pageResult.getData()) {
            Assert.assertEquals(i, (int) topicSharding.getStars());
            i--;
        }
        EasyPageResult<TopicSharding> pageResult2 = easyQuery.queryable(TopicSharding.class).where(o -> o.le(TopicSharding::getStars, 1000))
                .orderByDesc(o -> o.column(TopicSharding::getStars)).toPageResult(2, 20);
        Assert.assertEquals(500, pageResult2.getTotal());
        Assert.assertEquals(20, pageResult2.getData().size());
        int j = 479;
        for (TopicSharding topicSharding : pageResult2.getData()) {
            Assert.assertEquals(j, (int) topicSharding.getStars());
            j--;
        }
        EasyPageResult<TopicSharding> pageResult3 = easyQuery.queryable(TopicSharding.class).where(o -> o.le(TopicSharding::getStars, 1000))
                .orderByDesc(o -> o.column(TopicSharding::getStars)).toPageResult(7, 7);
        Assert.assertEquals(500, pageResult3.getTotal());
        Assert.assertEquals(7, pageResult3.getData().size());
        int l = 457;
        for (TopicSharding topicSharding : pageResult3.getData()) {
            Assert.assertEquals(l, (int) topicSharding.getStars());
            l--;
        }
    }
    @Test
    public void sharding6(){

        easyQuery.deletable(TopicSharding.class)
                .where(o->o.ge(TopicSharding::getStars,20000).le(TopicSharding::getStars,20100)).executeRows();
        ArrayList<TopicSharding> topicShardings = new ArrayList<>(3);
        for (int i = 0; i < 100; i++) {
            TopicSharding topicSharding = new TopicSharding();
            topicSharding.setId(String.valueOf(i+20000));
            topicSharding.setTitle("title" + (i%2==0?"1":i));
            topicSharding.setStars(i+20000);
            topicSharding.setCreateTime(LocalDateTime.now());
            topicShardings.add(topicSharding);
        }
        long l = easyQuery.insertable(topicShardings).executeRows();
        Assert.assertEquals(100,l);
        List<TopicSharding> list = easyQuery.queryable(TopicSharding.class)
                .where(o -> o.ge(TopicSharding::getStars, 20000).le(TopicSharding::getStars, 20100))
                .orderByAsc(o->o.column(TopicSharding::getStars))
                .toList();
        Assert.assertEquals(100,list.size());
        int i=0;
        for (TopicSharding topicSharding : list) {
            Assert.assertEquals(String.valueOf(i+20000),topicSharding.getId());
            i++;
        }

        List<TopicShardingGroup> list1 = easyQuery.queryable(TopicSharding.class)
                .where(o -> o.ge(TopicSharding::getStars, 20000).le(TopicSharding::getStars, 20100))
                .groupBy(o -> o.column(TopicSharding::getTitle))
                .orderByAsc(o->o.column(TopicSharding::getTitle))
                .select(TopicShardingGroup.class, o -> o.column(TopicSharding::getTitle).columnCount(TopicSharding::getId, TopicShardingGroup::getIdCount))
                .toList();

        Assert.assertEquals(50,list1.size());
        for (TopicShardingGroup topicShardingGroup : list1) {
            if("title1".equals(topicShardingGroup.getTitle())){
                Assert.assertEquals(51,(int)topicShardingGroup.getIdCount());
            }
        }
        List<TopicShardingGroup> list2 = easyQuery.queryable(TopicSharding.class)
                .where(o -> o.ge(TopicSharding::getStars, 20000).le(TopicSharding::getStars, 20100))
                .groupBy(o -> o.column(TopicSharding::getTitle))
                .select(TopicShardingGroup.class, o -> o.column(TopicSharding::getTitle).columnCount(TopicSharding::getId, TopicShardingGroup::getIdCount))
                .toList();

        Assert.assertEquals(50,list2.size());
        for (TopicShardingGroup topicShardingGroup : list2) {
            if("title1".equals(topicShardingGroup.getTitle())){
                Assert.assertEquals(51,(int)topicShardingGroup.getIdCount());
            }
        }
    }
    @Test
    public void sharding7(){
        easyQuery.deletable(TopicSharding.class)
                .where(o->o.ge(TopicSharding::getStars,30000).le(TopicSharding::getStars,30100)).executeRows();
        ArrayList<TopicSharding> topicShardings = new ArrayList<>(3);
        for (int i = 0; i < 100; i++) {
            TopicSharding topicSharding = new TopicSharding();
            topicSharding.setId(String.valueOf(i+30000));
            topicSharding.setTitle("title" + (i%2==0?"1":i));
            topicSharding.setStars(i+30000);
            topicSharding.setCreateTime(LocalDateTime.now());
            topicShardings.add(topicSharding);
        }
        long l = easyQuery.insertable(topicShardings).executeRows();
        Assert.assertEquals(100,l);
        long count = easyQuery.queryable(TopicSharding.class).where(o -> o.ge(TopicSharding::getStars, 30000).le(TopicSharding::getStars, 30100))
                .count();
        Assert.assertEquals(100,count);
    }
    @Test
    public void sharding8(){
        easyQuery.deletable(TopicSharding.class)
                .where(o->o.ge(TopicSharding::getStars,40000).le(TopicSharding::getStars,40100)).executeRows();
        ArrayList<TopicSharding> topicShardings = new ArrayList<>(3);
        for (int i = 0; i < 100; i++) {
            TopicSharding topicSharding = new TopicSharding();
            topicSharding.setId(String.valueOf(i+40000));
            topicSharding.setTitle("title" + (i%2==0?"1":i));
            topicSharding.setStars(i+40000);
            topicSharding.setCreateTime(LocalDateTime.now());
            topicShardings.add(topicSharding);
        }
        long l = easyQuery.insertable(topicShardings).executeRows();
        Assert.assertEquals(100,l);
        long count = easyQuery.queryable(TopicSharding.class).where(o -> o.ge(TopicSharding::getStars, 40000).lt(TopicSharding::getStars, 40050))
                .count();
        Assert.assertEquals(50,count);
    }

    @Test
    public void sharding9(){

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        List<TopicShardingTime> list = easyQuery.queryable(TopicShardingTime.class)
                .orderByAsc(o->o.column(TopicShardingTime::getCreateTime))
                .toList();
        Assert.assertEquals(days,list.size());
        for (int i = 0; i < days; i++) {
            TopicShardingTime topicShardingTime = list.get(i);
            Assert.assertEquals(beginTime.plusDays(i),topicShardingTime.getCreateTime());
        }
    }
    @Test
    public void sharding10(){

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyQuery.queryable(TopicShardingTime.class)
                .orderByAsc(o -> o.column(TopicShardingTime::getCreateTime))
                .toPageResult(1, 33);
        Assert.assertEquals(days,pageResult.getTotal());
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i),topicShardingTime.getCreateTime());
        }
    }
    @Test
    public void sharding11(){

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyQuery.queryable(TopicShardingTime.class)
                .orderByAsc(o -> o.column(TopicShardingTime::getCreateTime))
                .toPageResult(2, 33);
        Assert.assertEquals(days,pageResult.getTotal());
        beginTime=beginTime.plusDays(33);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i),topicShardingTime.getCreateTime());
        }
    }
    @Test
    public void sharding12(){

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingTime> pageResult = easyQuery.queryable(TopicShardingTime.class)
                .orderByAsc(o -> o.column(TopicShardingTime::getCreateTime))
                .toPageResult(3, 33);
        Assert.assertEquals(days,pageResult.getTotal());
        beginTime=beginTime.plusDays(66);
        for (int i = 0; i < 33; i++) {
            TopicShardingTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i),topicShardingTime.getCreateTime());
        }
    }
    @Test
    public void sharding13(){

        TopicShardingTime topicShardingTime1 = easyQuery.queryable(TopicShardingTime.class).orderByDesc(o -> o.column(TopicShardingTime::getCreateTime)).firstOrNull();
        Assert.assertNotNull(topicShardingTime1);
        LocalDateTime localDateTime = easyQuery.queryable(TopicShardingTime.class)
                .maxOrNull(TopicShardingTime::getCreateTime);

        Assert.assertNotNull(localDateTime);
        Assert.assertEquals(localDateTime,topicShardingTime1.getCreateTime());
    }
    @Test
    public void sharding15(){
        easyQuery.deletable(TopicSharding.class)
                .where(o->o.ge(TopicSharding::getStars,50000).le(TopicSharding::getStars,50100)).executeRows();
        ArrayList<TopicSharding> topicShardings = new ArrayList<>(3);
        for (int i = 0; i < 100; i++) {
            TopicSharding topicSharding = new TopicSharding();
            topicSharding.setId(String.valueOf(i+50000));
            topicSharding.setTitle("title" + (i%2==0?"1":i));
            topicSharding.setStars(i+50000);
            topicSharding.setCreateTime(LocalDateTime.now());
            topicShardings.add(topicSharding);
        }
        long l = easyQuery.insertable(topicShardings).executeRows();
        Assert.assertEquals(100,l);
        long count = easyQuery.queryable(TopicSharding.class).where(o -> o.ge(TopicSharding::getStars, 50000).lt(TopicSharding::getStars, 50099))
                .count();
        Assert.assertEquals(99,count);
        TopicSharding topicSharding = easyQuery.queryable(TopicSharding.class).where(o -> o.ge(TopicSharding::getStars, 50000).lt(TopicSharding::getStars, 50100))
                .orderByDesc(o -> o.column(TopicSharding::getStars)).firstOrNull();
        Assert.assertNotNull(topicSharding);
        Integer maxStars = easyQuery.queryable(TopicSharding.class).where(o -> o.ge(TopicSharding::getStars, 50000).lt(TopicSharding::getStars, 50100))
                .maxOrNull(TopicSharding::getStars);
        Assert.assertNotNull(maxStars);
        Assert.assertEquals(topicSharding.getStars(),maxStars);
    }
    @Test
    public void sharding14(){

        boolean all1 = easyQuery.queryable(TopicShardingTime.class)
                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
        Assert.assertFalse(all1);
        boolean all2 = easyQuery.queryable(TopicShardingTime.class)
                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
        Assert.assertFalse(all2);
        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        boolean all3 = easyQuery.queryable(TopicShardingTime.class)
                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
        Assert.assertFalse(all3);
        boolean all4 = easyQuery.queryable(TopicShardingTime.class)
                .all(o -> o.ge(TopicShardingTime::getCreateTime, beginTime));
        Assert.assertTrue(all4);
        boolean all5 = easyQuery.queryable(TopicShardingTime.class)
                .all(o -> o.le(TopicShardingTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
        Assert.assertTrue(all5);
        boolean all6 = easyQuery.queryable(TopicShardingTime.class)
                .all(o -> o.lt(TopicShardingTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
        Assert.assertTrue(all6);
        boolean all7 = easyQuery.queryable(TopicShardingTime.class)
                .all(o -> o.ge(TopicShardingTime::getCreateTime, beginTime.plusDays(-1)));
        Assert.assertTrue(all7);
        boolean all8 = easyQuery.queryable(TopicShardingTime.class)
                .all(o -> o.ge(TopicShardingTime::getCreateTime, beginTime.plusDays(1)));
        Assert.assertFalse(all8);
    }
    @Test
    public void sharding16(){

        TopicShardingTime topicShardingTime1 = easyQuery.queryable(TopicShardingTime.class).orderByAsc(o -> o.column(TopicShardingTime::getCreateTime)).firstOrNull();
        Assert.assertNotNull(topicShardingTime1);
        LocalDateTime localDateTime = easyQuery.queryable(TopicShardingTime.class)
                .minOrNull(TopicShardingTime::getCreateTime);

        Assert.assertNotNull(localDateTime);
        Assert.assertEquals(localDateTime,topicShardingTime1.getCreateTime());
    }
    @Test
    public void sharding17(){

        boolean all1 = easyQuery.queryable(TopicShardingTime.class)
                .where(o->o.le(TopicShardingTime::getCreateTime,LocalDateTime.of(2099, 1, 1, 1, 1)))
                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
        Assert.assertFalse(all1);
        boolean all2 = easyQuery.queryable(TopicShardingTime.class)
                .where(o->o.ge(TopicShardingTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1)))
                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
        Assert.assertTrue(all2);
        boolean all21 = easyQuery.queryable(TopicShardingTime.class)
                .where(o->o.ge(TopicShardingTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1).plusDays(-1)))
                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
        Assert.assertFalse(all21);
        boolean all22 = easyQuery.queryable(TopicShardingTime.class)
                .where(o->o.ge(TopicShardingTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1).plusDays(1)))
                .all(o -> o.ge(TopicShardingTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
        Assert.assertTrue(all22);
        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        boolean all3 = easyQuery.queryable(TopicShardingTime.class)
                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
        Assert.assertFalse(all3);
        boolean all31 = easyQuery.queryable(TopicShardingTime.class)
                .where(o -> o.gt(TopicShardingTime::getCreateTime, beginTime))
                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
        Assert.assertTrue(all31);
        boolean all32 = easyQuery.queryable(TopicShardingTime.class)
                .where(o -> o.ge(TopicShardingTime::getCreateTime, beginTime))
                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
        Assert.assertFalse(all32);
        boolean all33 = easyQuery.queryable(TopicShardingTime.class)
                .where(o -> o.rangeClosed(TopicShardingTime::getCreateTime,true,beginTime,true,beginTime))
                .all(o -> o.gt(TopicShardingTime::getCreateTime, beginTime));
        Assert.assertFalse(all33);
    }






    @Test
    public void sharding18(){

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        List<TopicShardingDataSourceTime> list = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .orderByAsc(o->o.column(TopicShardingDataSourceTime::getCreateTime))
                .toList();
        Assert.assertEquals(days,list.size());
        for (int i = 0; i < days; i++) {
            TopicShardingDataSourceTime topicShardingTime = list.get(i);
            Assert.assertEquals(beginTime.plusDays(i),topicShardingTime.getCreateTime());
        }
    }
    @Test
    public void sharding19(){

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingDataSourceTime> pageResult = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .orderByAsc(o -> o.column(TopicShardingDataSourceTime::getCreateTime))
                .toPageResult(1, 33);
        Assert.assertEquals(days,pageResult.getTotal());
        for (int i = 0; i < 33; i++) {
            TopicShardingDataSourceTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i),topicShardingTime.getCreateTime());
        }
    }
    @Test
    public void sharding20(){

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingDataSourceTime> pageResult = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .orderByAsc(o -> o.column(TopicShardingDataSourceTime::getCreateTime))
                .toPageResult(2, 33);
        Assert.assertEquals(days,pageResult.getTotal());
        beginTime=beginTime.plusDays(33);
        for (int i = 0; i < 33; i++) {
            TopicShardingDataSourceTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i),topicShardingTime.getCreateTime());
        }
    }
    @Test
    public void sharding21(){

        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
        Duration between = Duration.between(beginTime, endTime);
        long days = between.toDays();
        EasyPageResult<TopicShardingDataSourceTime> pageResult = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .orderByAsc(o -> o.column(TopicShardingDataSourceTime::getCreateTime))
                .toPageResult(3, 33);
        Assert.assertEquals(days,pageResult.getTotal());
        beginTime=beginTime.plusDays(66);
        for (int i = 0; i < 33; i++) {
            TopicShardingDataSourceTime topicShardingTime = pageResult.getData().get(i);
            Assert.assertEquals(beginTime.plusDays(i),topicShardingTime.getCreateTime());
        }
    }
    @Test
    public void sharding22(){

        TopicShardingDataSourceTime topicShardingTime1 = easyQuery.queryable(TopicShardingDataSourceTime.class).orderByDesc(o -> o.column(TopicShardingDataSourceTime::getCreateTime)).firstOrNull();
        Assert.assertNotNull(topicShardingTime1);
        LocalDateTime localDateTime = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .maxOrNull(TopicShardingDataSourceTime::getCreateTime);

        Assert.assertNotNull(localDateTime);
        Assert.assertEquals(localDateTime,topicShardingTime1.getCreateTime());
    }
    @Test
    public void sharding23(){

        boolean all1 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
        Assert.assertFalse(all1);
        boolean all2 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
        Assert.assertFalse(all2);
        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        boolean all3 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
        Assert.assertFalse(all3);
        boolean all4 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, beginTime));
        Assert.assertTrue(all4);
        boolean all5 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .all(o -> o.le(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
        Assert.assertTrue(all5);
        boolean all6 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .all(o -> o.lt(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
        Assert.assertTrue(all6);
        boolean all7 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, beginTime.plusDays(-1)));
        Assert.assertTrue(all7);
        boolean all8 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, beginTime.plusDays(1)));
        Assert.assertFalse(all8);
    }
    @Test
    public void sharding24(){

        TopicShardingDataSourceTime topicShardingTime1 = easyQuery.queryable(TopicShardingDataSourceTime.class).orderByAsc(o -> o.column(TopicShardingDataSourceTime::getCreateTime)).firstOrNull();
        Assert.assertNotNull(topicShardingTime1);
        LocalDateTime localDateTime = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .minOrNull(TopicShardingDataSourceTime::getCreateTime);

        Assert.assertNotNull(localDateTime);
        Assert.assertEquals(localDateTime,topicShardingTime1.getCreateTime());
    }
    @Test
    public void sharding25(){

        boolean all1 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .where(o->o.le(TopicShardingDataSourceTime::getCreateTime,LocalDateTime.of(2099, 1, 1, 1, 1)))
                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2099, 1, 1, 1, 1)));
        Assert.assertFalse(all1);
        boolean all2 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .where(o->o.ge(TopicShardingDataSourceTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1)))
                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
        Assert.assertTrue(all2);
        boolean all21 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .where(o->o.ge(TopicShardingDataSourceTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1).plusDays(-1)))
                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
        Assert.assertFalse(all21);
        boolean all22 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .where(o->o.ge(TopicShardingDataSourceTime::getCreateTime,LocalDateTime.of(2022, 1, 1, 1, 1).plusDays(1)))
                .all(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, LocalDateTime.of(2022, 1, 1, 1, 1)));
        Assert.assertTrue(all22);
        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        boolean all3 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
        Assert.assertFalse(all3);
        boolean all31 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .where(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime))
                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
        Assert.assertTrue(all31);
        boolean all32 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .where(o -> o.ge(TopicShardingDataSourceTime::getCreateTime, beginTime))
                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
        Assert.assertFalse(all32);
        boolean all33 = easyQuery.queryable(TopicShardingDataSourceTime.class)
                .where(o -> o.rangeClosed(TopicShardingDataSourceTime::getCreateTime,true,beginTime,true,beginTime))
                .all(o -> o.gt(TopicShardingDataSourceTime::getCreateTime, beginTime));
        Assert.assertFalse(all33);
    }
}
