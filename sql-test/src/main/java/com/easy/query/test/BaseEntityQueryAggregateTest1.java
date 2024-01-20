package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable10;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.api.proxy.entity.select.EntityQueryable5;
import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.api.proxy.entity.select.EntityQueryable7;
import com.easy.query.api.proxy.entity.select.EntityQueryable8;
import com.easy.query.api.proxy.entity.select.EntityQueryable9;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;

/**
 * create time 2023/10/31 08:11
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class BaseEntityQueryAggregateTest1 extends BaseTest {

    public EntityQueryable10<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join1() {
        EntityQueryable10<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t10.id()));
        return query;
    }

    public EntityQueryable9<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join2() {
        EntityQueryable9<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()));
        return query;
    }

    public EntityQueryable8<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join3() {
        EntityQueryable8<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()));
        return query;
    }

    public EntityQueryable7<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join4() {
        EntityQueryable7<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()));
        return query;
    }

    public EntityQueryable6<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join5() {
        EntityQueryable6<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()));
        return query;
    }

    public EntityQueryable5<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join6() {
        EntityQueryable5<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()));
        return query;
    }

    public EntityQueryable4<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join7() {
        EntityQueryable4<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()));
        return query;
    }

    public EntityQueryable3<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join8() {
        EntityQueryable3<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()));
        return query;
    }
    public EntityQueryable2<TopicProxy, Topic, TopicProxy, Topic> join9() {
        EntityQueryable2<TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()));
        return query;
    }
    public EntityQueryable<TopicProxy, Topic> join10() {
        EntityQueryable<TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class);
        return query;
    }

}
