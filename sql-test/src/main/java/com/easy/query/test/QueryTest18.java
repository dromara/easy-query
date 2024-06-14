package com.easy.query.test;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.vo.TestUserAAA;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2024/6/13 12:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest18 extends BaseTest{
    @Test
    public void test1(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Topic topic = easyEntityQuery.queryable(Topic.class)
                .where(t -> t.id().isNull())
                .singleOrDefault(new Topic());
        Assert.assertNull(topic.getId());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IS NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test2(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Topic topic = easyEntityQuery.queryable(Topic.class)
                .where(t -> t.id().isNull())
                .singleOrDefault(new Topic(),result->result==null);
        Assert.assertNull(topic.getId());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IS NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        Character x='a';

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.id().in(Arrays.asList("1", "2"));
                }).toList();
    }

    @Test
    public  void testxxx(){
        Queryable<TestUserAAA> queryable = easyQuery.queryable(TestUserAAA.class);
    }
}
