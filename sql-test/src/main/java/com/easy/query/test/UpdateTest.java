package com.easy.query.test;

import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.basic.api.update.impl.EasyEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.test.entity.Topic;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

/**
 * @FileName: UpdateTest.java
 * @Description: 文件说明
 * @Date: 2023/3/20 21:58
 * @author xuejiaming
 */
public class UpdateTest extends BaseTest {

    @Test
    public void updateTest1() {
        long rows = easyQuery.updatable(Topic.class)
                .set(Topic::getStars, 12)
                .where(o -> o.eq(Topic::getId, "2"))
                .executeRows();
        Assert.assertEquals(1, rows);
    }

    @Test
    public void updateTest2() {
        long rows = easyQuery.updatable(Topic.class)
                .set(Topic::getStars, 12)
                .where(o -> o.eq(Topic::getId, "2x"))
                .executeRows();
        Assert.assertEquals(0, rows);
    }

    @Test
    public void updateTest3() {
        Topic topic = easyQuery.queryable(Topic.class).whereById("3").firstOrNull();
        Assert.assertNotNull(topic);
        Assert.assertEquals("3", topic.getId());
        long rows = easyQuery.updatable(Topic.class)
                .setSelfColumn(Topic::getTitle, Topic::getStars)
                .where(o -> o.eq(Topic::getId, topic.getId()))
                .executeRows();
        Assert.assertEquals(1, rows);
        Topic topic2 = easyQuery.queryable(Topic.class).whereById("3").firstOrNull();
        Assert.assertNotNull(topic2);
        Assert.assertEquals(String.valueOf(topic.getStars()), topic2.getTitle());
    }

    @Test
    public void updateTest4() {
        long rows = easyQuery.updatable(Topic.class)
                .set(Topic::getStars, 2)
                .whereById("5").executeRows();
        Assert.assertEquals(1, rows);


        long rows1 = easyQuery.updatable(Topic.class)
                .setIncrement(Topic::getStars)
                .whereById("5").executeRows();
        Assert.assertEquals(1, rows1);
        Topic topic1 = easyQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic1);
        Assert.assertEquals(3, (int) topic1.getStars());

        long rows2 = easyQuery.updatable(Topic.class)
                .setIncrement(Topic::getStars, 2)
                .where(o -> o.eq(Topic::getId, "5")).executeRows();
        Assert.assertEquals(1, rows2);
        Topic topic2 = easyQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic2);
        Assert.assertEquals(5, (int) topic2.getStars());

        long rows3 = easyQuery.updatable(Topic.class)
                .setDecrement(Topic::getStars)
                .where(o -> o.eq(Topic::getId, "5")).executeRows();
        Assert.assertEquals(1, rows3);
        Topic topic3 = easyQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic3);
        Assert.assertEquals(4, (int) topic3.getStars());

        long rows4 = easyQuery.updatable(Topic.class)
                .setDecrement(Topic::getStars, 2)
                .where(o -> o.eq(Topic::getId, "5")).executeRows();
        Assert.assertEquals(1, rows4);
        Topic topic4 = easyQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic4);
        Assert.assertEquals(2, (int) topic4.getStars());
    }

    @Test
    public void updateTest5() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try{

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).firstNotNull("未找到对应的数据");
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `stars` = ?,`title` = ?,`create_time` = ? WHERE `id` = ?", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(1,l);
        }finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest6() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try{

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).asTracking().firstNotNull("未找到对应的数据");
            String newTitle = "test123" + new Random().nextInt(100000);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(1,l);
        }finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }
    @Test
    public void updateTest7() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try{

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).firstNotNull("未找到对应的数据");
            boolean b = easyQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(1,l);
        }finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }
    @Test
    public void updateTest8() {
        long l = easyQuery.updatable(Topic.class)
                .set(Topic::getTitle, null)
                .whereById("9").executeRows();
        Assert.assertEquals(1,l);
        Topic topic = easyQuery.queryable(Topic.class)
                .whereById("9").firstOrNull();
        Assert.assertNotNull(topic);
        String updateSql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS))
                .toSQL(topic);
        Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?",updateSql);
        long l1 = easyQuery.updatable(topic)
                .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS)
                .executeRows();
        Assert.assertEquals(1,l1);
    }
    @Test
    public void updateTest9() {
        long l = easyQuery.updatable(Topic.class)
                .set(Topic::getTitle, null)
                .whereById("10").executeRows();
        Assert.assertEquals(1,l);
        Topic topic = easyQuery.queryable(Topic.class)
                .whereById("10").firstOrNull();
        Assert.assertNotNull(topic);
        String updateSQL = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic)
                .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS))
                .toSQL(topic);
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ?,`create_time` = ? WHERE `id` = ?",updateSQL);
        long l1 = easyQuery.updatable(topic)
                .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS)
                .executeRows();
        Assert.assertEquals(1,l1);
    }
    @Test
    public void updateTest10() {
        try {

            easyQuery.updatable(Topic.class)
                    .set(Topic::getStars, 12)
                    .where(o -> o.eq(Topic::getId, UUID.randomUUID().toString()))
                    .executeRows(1,"123");
        }catch (Exception e){
            Assert.assertEquals(EasyQueryConcurrentException.class,e.getClass());
        }
    }
}
