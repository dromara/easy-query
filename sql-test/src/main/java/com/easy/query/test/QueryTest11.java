package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2023/12/29 16:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest11 extends BaseTest{

    @Test
    public void testx(){


        EntityQueryable<TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy() {{
                    selectExpression(o.id(), o.title());
                }});

        EntityQueryable<TopicProxy, Topic> query2= easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy());//这样等于selectAll


        Query<Topic> query1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy() {{
                    selectExpression(o.id(), o.title());
                }});


        List<Topic> list = query.leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> {
                    t1.id().eq("123");
                    t.id().eq( "456");
                }).toList();
    }

    @Test
    public void testx1(){


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.createTime().format("yyyy-MM-dd").likeMatchLeft("2023");
                        o.exists(() -> {
                            return easyEntityQuery.queryable(Topic.class)
                                    .where(x -> x.id().eq(o.id()));
                        });
                    })
                    .select(o -> new BlogEntityProxy() {{

                        PropTypeColumn<BigDecimal> integerPropTypeColumn = o.sql("1").setPropertyType(BigDecimal.class);
                        score().set(integerPropTypeColumn);
                    }}).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT 1 AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? AND EXISTS (SELECT 1 FROM `t_topic` t1 WHERE t1.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
}
