package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.TopicGroupTestDTO;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/2/12 09:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClientTest extends BaseTest{
    @Test
    public void testClient1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TopicGroupTestDTO> list = easyQueryClient.queryable(Topic.class)
                .where(t_topic -> t_topic.eq(TopicProxy.Fields.id, "3"))
                .groupBy(t_topic -> t_topic.column(TopicProxy.Fields.id))
                .select(TopicGroupTestDTO.class, t_topic -> t_topic.columnAs("id", "id").columnCountAs("id", "idCount"))
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`id`) AS `id_count` FROM `t_topic` t WHERE t.`id` = ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
}