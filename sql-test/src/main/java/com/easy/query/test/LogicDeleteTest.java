package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.entity.LogicDelTopic;
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
}
