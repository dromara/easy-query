package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.enums.MapKeyModeEnum;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/7/16 21:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest27 extends BaseTest {
    @Test
    public void testRandom() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .orderBy(t_topic -> {
                    t_topic.expression().random().asc();
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` ORDER BY RAND() ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void selectAllMap() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(o -> {
                    o.expression().random().asc();
                }).select(t_blog -> new MapProxy().selectAll(t_blog, MapKeyModeEnum.FIELD_NAME))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,t.`create_time` AS `createTime`,t.`update_time` AS `updateTime`,t.`create_by` AS `createBy`,t.`update_by` AS `updateBy`,t.`deleted` AS `deleted`,t.`title` AS `title`,t.`content` AS `content`,t.`url` AS `url`,t.`star` AS `star`,t.`publish_time` AS `publishTime`,t.`score` AS `score`,t.`status` AS `status`,t.`order` AS `order`,t.`is_top` AS `isTop`,t.`top` AS `top` FROM `t_blog` t WHERE t.`deleted` = ? ORDER BY RAND() ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Map<String, Object> stringObjectMap : list) {

            Object o = stringObjectMap.get("createTime");
            Assert.assertNotNull(o);
        }
    }
}
