package com.easy.query.test;

import com.easy.query.api4j.select.Queryable10;
import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.select.Queryable7;
import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.select.Queryable9;
import com.easy.query.test.entity.Topic;

/**
 * create time 2023/10/31 08:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryAggregateTest3 extends BaseTest{

    public Queryable10<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> join1(){
        Queryable10<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t8(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t9(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable9<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> join2(){
        Queryable9<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t8(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable8<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> join3(){
        Queryable8<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable7<Topic, Topic, Topic, Topic, Topic, Topic, Topic> join4(){
        Queryable7<Topic, Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable6<Topic, Topic, Topic, Topic, Topic, Topic> join5(){
        Queryable6<Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId));
        return query;
    }
    public  Queryable5<Topic, Topic, Topic, Topic, Topic> join6(){
        Queryable5<Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable4<Topic, Topic, Topic, Topic> join7(){
        Queryable4<Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable3<Topic, Topic, Topic> join8(){
        Queryable3<Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId));
        return query;
    }
}
