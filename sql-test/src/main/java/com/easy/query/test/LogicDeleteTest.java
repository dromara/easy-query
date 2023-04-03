package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.core.basic.api.update.ExpressionUpdatable;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.entity.LogicDelTopic;
import com.easy.query.entity.LogicDelTopicCustom;
import com.easy.query.logicdel.CurrentUserHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2023/3/28 09:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class LogicDeleteTest extends BaseTest {

    @Test
    public void Test1(){
        String logicDeleteSql = easyQuery.queryable(LogicDelTopic.class)
                .toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`deleted`,t.`create_time` FROM t_logic_del_topic t WHERE t.`deleted` = ?",logicDeleteSql);
        List<LogicDelTopic> logicDelTopics = easyQuery.queryable(LogicDelTopic.class).toList();
        Assert.assertTrue(ArrayUtil.isNotEmpty(logicDelTopics));
        Assert.assertEquals(100,logicDelTopics.size());
    }
    @Test
    public void Test2(){
        String logicDeleteSql = easyQuery.deletable(LogicDelTopic.class)
                .whereById("111")
                .toSql();
        Assert.assertEquals("UPDATE t_logic_del_topic SET `deleted` = ? WHERE `deleted` = ? AND `id` = ?",logicDeleteSql);
        long l = easyQuery.deletable(LogicDelTopic.class)
                .whereById("111")
                .executeRows();
        Assert.assertEquals(0,l);
    }
    @Test
    public void Test3(){
        LogicDelTopic logicDelTopic = new LogicDelTopic();
        logicDelTopic.setId("111");
        String logicDeleteSql = easyQuery.deletable(logicDelTopic)
                .toSql();
        Assert.assertEquals("UPDATE t_logic_del_topic SET `deleted` = ? WHERE `deleted` = ? AND `id` = ?",logicDeleteSql);

        long l = easyQuery.deletable(logicDelTopic)
                .executeRows();
        Assert.assertEquals(0,l);
    }


    @Test
    public void Test4(){
        String logicDeleteSql = easyQuery.queryable(LogicDelTopic.class)
                .disableLogicDelete()
                .toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`deleted`,t.`create_time` FROM t_logic_del_topic t",logicDeleteSql);
        List<LogicDelTopic> logicDelTopics = easyQuery.queryable(LogicDelTopic.class).disableLogicDelete().toList();
        Assert.assertTrue(ArrayUtil.isNotEmpty(logicDelTopics));
        Assert.assertEquals(100,logicDelTopics.size());
    }
    @Test
    public void Test5(){
        String logicDeleteSql = easyQuery.deletable(LogicDelTopic.class)
                .disableLogicDelete()
                .whereById("111xx")
                .toSql();
        Assert.assertEquals("DELETE FROM t_logic_del_topic WHERE `id` = ?",logicDeleteSql);
        long l = easyQuery.deletable(LogicDelTopic.class)
                .disableLogicDelete()
                .whereById("111xx")
                .executeRows();
        Assert.assertEquals(0,l);
    }
    @Test
    public void Test6(){
        LogicDelTopic logicDelTopic = easyQuery.queryable(LogicDelTopic.class)
                .disableLogicDelete().firstOrNull();
        Assert.assertNotNull(logicDelTopic);
        ExpressionUpdatable<LogicDelTopic> logicDelTopicExpressionUpdatable = easyQuery.updatable(LogicDelTopic.class)
                .disableLogicDelete()
                .set(LogicDelTopic::getTitle, logicDelTopic.getTitle())
                .whereById(logicDelTopic.getId());
        String s = logicDelTopicExpressionUpdatable.toSql();
        Assert.assertEquals("UPDATE t_logic_del_topic SET `title` = ? WHERE `id` = ?",s);

        long l = logicDelTopicExpressionUpdatable.executeRows();
        Assert.assertEquals(1,l);
    }
    @Test
    public void Test7(){
        String logicDeleteSql = easyQuery.queryable(LogicDelTopicCustom.class)
                .toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`deleted_at`,t.`deleted_user`,t.`create_time` FROM t_logic_del_topic_custom t WHERE t.`deleted_at` IS NULL",logicDeleteSql);
        List<LogicDelTopicCustom> logicDelTopics = easyQuery.queryable(LogicDelTopicCustom.class).toList();
        Assert.assertTrue(ArrayUtil.isNotEmpty(logicDelTopics));
        Assert.assertEquals(100,logicDelTopics.size());
    }
    @Test
    public void Test8(){
        String logicDeleteSql = easyQuery.queryable(LogicDelTopicCustom.class)
                .where(o->o.eq(LogicDelTopicCustom::getId,"1"))
                .toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`deleted_at`,t.`deleted_user`,t.`create_time` FROM t_logic_del_topic_custom t WHERE t.`deleted_at` IS NULL AND t.`id` = ?",logicDeleteSql);
        LogicDelTopicCustom logicDelTopic = easyQuery.queryable(LogicDelTopicCustom.class)
                .where(o->o.eq(LogicDelTopicCustom::getId,"1")).firstOrNull();
        Assert.assertNotNull(logicDelTopic);
        long l = easyQuery.updatable(logicDelTopic).executeRows();
        Assert.assertEquals(1,l);
    }
    @Test
    public void Test9(){
        String logicDeleteSql = easyQuery.queryable(LogicDelTopicCustom.class)
                .where(o->o.eq(LogicDelTopicCustom::getId,"1"))
                .toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`deleted_at`,t.`deleted_user`,t.`create_time` FROM t_logic_del_topic_custom t WHERE t.`deleted_at` IS NULL AND t.`id` = ?",logicDeleteSql);
        LogicDelTopicCustom logicDelTopic = easyQuery.queryable(LogicDelTopicCustom.class)
                .where(o->o.eq(LogicDelTopicCustom::getId,"1")).firstOrNull();
        Assert.assertNotNull(logicDelTopic);
        logicDelTopic.setId("11xx");
        CurrentUserHelper.setUserId("easy-query");
        long l = easyQuery.deletable(logicDelTopic).executeRows();
        Assert.assertEquals(0,l);
    }
}
