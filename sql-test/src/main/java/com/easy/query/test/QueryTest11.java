package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Test;

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

        List<Topic> list = query.leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> {
                    t1.id().eq("123");
                    t.id().eq( "456");
                }).toList();
    }
}
