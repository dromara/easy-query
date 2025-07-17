package com.easy.query.test.click;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dameng.entity.DamengMyTopic;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * create time 2025/7/16 21:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClickHouseTest extends ClickHouseBaseTest {
    @Test
    public void testUpdate1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Exception exception = null;
        try {

            entityQuery.updatable(Topic.class)
                    .setColumns(t_topic -> {
                        t_topic.title().set("123");
                    }).whereById("123123")
                    .executeRows();
        } catch (Exception ex) {
            exception=ex;
        }
        listenerContextManager.clear();

        Assert.assertNotNull(exception);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("ALTER TABLE `t_topic` UPDATE `TITLE` = ? WHERE `ID` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testUpdate2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Exception exception = null;
        try {
            Topic topic = new Topic();
            topic.setId("123");
            topic.setTitle("456");
            entityQuery.updatable(topic)
                    .executeRows();
        } catch (Exception ex) {
            exception=ex;
        }
        listenerContextManager.clear();

        Assert.assertNotNull(exception);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("ALTER TABLE `t_topic` UPDATE `STARS` = ?,`TITLE` = ?,`CREATE_TIME` = ? WHERE `ID` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("null(null),456(String),null(null),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testDelete1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Exception exception = null;
        try {

            entityQuery.deletable(Topic.class).whereById("123123")
                    .executeRows();
        } catch (Exception ex) {
            exception=ex;
        }
        listenerContextManager.clear();

        Assert.assertNotNull(exception);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("ALTER TABLE `t_topic` DELETE WHERE `ID` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testDelete2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Exception exception = null;
        try {
            Topic topic = new Topic();
            topic.setId("123");
            topic.setTitle("456");
            entityQuery.deletable(topic)
                    .executeRows();
        } catch (Exception ex) {
            exception=ex;
        }
        listenerContextManager.clear();

        Assert.assertNotNull(exception);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("ALTER TABLE `t_topic` DELETE WHERE `ID` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

}
