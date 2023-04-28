package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.entity.TopicSharding;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
}
