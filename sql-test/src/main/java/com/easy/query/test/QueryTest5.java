package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.common.tuple.MergeTuple10;
import com.easy.query.core.common.tuple.MergeTuple4;
import com.easy.query.core.common.tuple.MergeTuple5;
import com.easy.query.core.common.tuple.MergeTuple6;
import com.easy.query.core.common.tuple.MergeTuple7;
import com.easy.query.core.common.tuple.MergeTuple8;
import com.easy.query.core.common.tuple.MergeTuple9;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.expression.builder.impl.SelectorImpl;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.grouping.Grouping1;
import com.easy.query.core.proxy.grouping.proxy.Grouping1Proxy;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.dto.BlogEntityTest;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/10/17 16:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest5 extends BaseTest {

    private EntityQueryable<Grouping1Proxy<SQLStringTypeColumn<TopicProxy>, String, MergeTuple10<TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy>>, Grouping1<String>> baseQueryable1() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        EntityQueryable<Grouping1Proxy<SQLStringTypeColumn<TopicProxy>, String, MergeTuple10<TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy>>, Grouping1<String>> topicTopicTopicTopicTopicTopicTopicTopicTopicTopicQueryable10 = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t10.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L));
        return topicTopicTopicTopicTopicTopicTopicTopicTopicTopicQueryable10;
    }

    private EntityQueryable<Grouping1Proxy<SQLStringTypeColumn<TopicProxy>, String, MergeTuple9<TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy>>, Grouping1<String>> baseQueryable2() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        return easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L));
    }

    private EntityQueryable<Grouping1Proxy<SQLStringTypeColumn<TopicProxy>, String, MergeTuple8<TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy>>, Grouping1<String>> baseQueryable3() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        return easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L));
    }

    private EntityQueryable<Grouping1Proxy<SQLStringTypeColumn<TopicProxy>, String, MergeTuple7<TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy>>, Grouping1<String>> baseQueryable4() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        return easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L));
    }

    private EntityQueryable<Grouping1Proxy<SQLStringTypeColumn<TopicProxy>, String, MergeTuple6<TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy>>, Grouping1<String>> baseQueryable5() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        return easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L));
    }

    private EntityQueryable<Grouping1Proxy<SQLStringTypeColumn<TopicProxy>, String, MergeTuple5<TopicProxy, TopicProxy, TopicProxy, TopicProxy, TopicProxy>>, Grouping1<String>> baseQueryable6() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        return easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L));
    }

    private EntityQueryable<Grouping1Proxy<SQLStringTypeColumn<TopicProxy>, String, MergeTuple4<TopicProxy, TopicProxy, TopicProxy, TopicProxy>>, Grouping1<String>> baseQueryable7() {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        return easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .where(o -> o.id().eq("1"))
                .where(false, o -> o.id().eq("1"))
                .whereById("1")
                .whereById(false, "1")
                .whereById(Collections.singletonList("1"))
                .whereById(false, Collections.singletonList("1"))
                .whereObject(topicRequest)
                .whereObject(false, topicRequest)
                .whereMerge(o -> {
                    o.t1.id().eq("1");
                    o.t1.id().eq(false, "1");
                    o.t1.id().ne("1");
                    o.t1.id().ne(false, "1");
                    o.t1.id().ge("1");
                    o.t1.id().ge(false, "1");
                    o.t1.id().gt("1");
                    o.t1.id().gt(false, "1");
                    o.t1.id().le("1");
                    o.t1.id().le(false, "1");
                    o.t1.id().lt("1");
                    o.t1.id().lt(false, "1");
                    o.t1.id().like("1");
                    o.t1.id().like(false, "1");
                    o.t1.id().notLike("1");
                    o.t1.id().notLike(false, "1");
                    o.t1.id().likeMatchLeft("1");
                    o.t1.id().likeMatchLeft(false, "1");
                    o.t1.id().likeMatchRight("1");
                    o.t1.id().likeMatchRight(false, "1");
                    o.t1.id().notLikeMatchLeft("1");
                    o.t1.id().notLikeMatchLeft(false, "1");
                    o.t1.id().notLikeMatchRight("1");
                    o.t1.id().notLikeMatchRight(false, "1");
                })
                .limit(1, 2)
                .orderBy(o -> o.createTime().asc())
                .orderBy(o -> o.createTime().desc())
                .orderBy(false, o -> o.createTime().asc())
                .orderBy(false, o -> o.createTime().desc())
                .orderByMerge(o -> o.t1.createTime().asc())
                .orderByMerge(o -> o.t1.createTime().desc())
                .orderByMerge(false, o -> o.t1.createTime().asc())
                .orderByMerge(false, o -> o.t1.createTime().desc())
                .groupByMerge(o -> GroupKeys.of(o.t1.id()))
                .having(o -> o.groupTable().t1.id().count().ge(1L))
                .having(false, o -> o.groupTable().t1.id().count().ge(1L));
    }

    @Test
    public void joinTest1() {
        String sql = baseQueryable1().select(Topic.class, o -> Select.of(
                o.key1(),
                o.count(s -> s.t1.stars()).as(Topic::getStars)
        )).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);

    }

    @Test
    public void joinTest1_1() {
        Supplier<Exception> f = () -> {
            try {
                baseQueryable1().asTable("a123").sumBigDecimalOrDefault(s -> s.groupTable().t1.stars(), BigDecimal.ZERO);
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `a123` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTest1_2() {
        Supplier<Exception> f = () -> {
            try {
                baseQueryable1().asTable("a123").sumOrDefault(s -> s.groupTable().t1.stars().asBigDecimal(), BigDecimal.ZERO);
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `a123` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTest1_3() {
        Supplier<Exception> f = () -> {
            try {
                baseQueryable1().asTable("a123").avgBigDecimalOrNull(s -> s.groupTable().t1.stars());
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `a123` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTest1_4() {
        Supplier<Exception> f = () -> {
            try {
                baseQueryable1().asTable("a123").avgBigDecimalOrDefault(s -> s.groupTable().t3.stars(), BigDecimal.ZERO);
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT AVG(t2.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `a123` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTest1_5() {
        Supplier<Exception> f = () -> {
            try {
//                baseQueryable1().asTable("a123").avgBigDecimalOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->t2.);
                baseQueryable1().asTable("a123").avgBigDecimalOrNull(s -> s.groupTable().t3.stars());
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT AVG(t2.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `a123` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTest1_6() {
        Supplier<Exception> f = () -> {
            try {
                baseQueryable1().asTable("a123").avgOrNull(s -> s.groupTable().t3.stars());
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT AVG(t2.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `a123` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTest1_7() {
        Supplier<Exception> f = () -> {
            try {
                baseQueryable1().asTable("a123").avgOrDefault(s -> s.groupTable().t3.stars(), 0d);
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT AVG(t2.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `a123` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTest1_8() {
        Supplier<Exception> f = () -> {
            try {
                baseQueryable1().asTable("a123").avgFloatOrDefault(s -> s.groupTable().t3.stars(), 0f);
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT AVG(t2.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `a123` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTest1_9() {
        Supplier<Exception> f = () -> {
            try {
                baseQueryable1().asTable("a123").avgFloatOrNull(s -> s.groupTable().t3.stars());
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT AVG(t2.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `a123` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTest2() {
        String sql = baseQueryable2().select(Topic.class, o -> Select.of(o.key1(), o.count(s -> s.t1.id()).as(Topic::getStars))).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest3() {
        String sql = baseQueryable3().select(Topic.class, o -> Select.of(o.key1(), o.count(s -> s.t1.id()).as(Topic::getStars))).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest4() {
        String sql = baseQueryable4().select(Topic.class, o -> Select.of(o.key1(), o.count(s -> s.t1.id()).as(Topic::getStars))).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest5() {
        String sql = baseQueryable5().select(Topic.class, o -> Select.of(o.key1(), o.count(s -> s.t1.id()).as(Topic::getStars))).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest6() {
        String sql = baseQueryable6().select(Topic.class, o -> Select.of(o.key1(), o.count(s -> s.t1.id()).as(Topic::getStars))).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void joinTest7() {
        String sql = baseQueryable7().select(Topic.class, o -> Select.of(o.key1(), o.count(s -> s.t1.id()).as(Topic::getStars))).toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `stars` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` WHERE t.`id` = ? AND t.`id` = ? AND t.`id` = ? AND t.`create_time` > ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` >= ? AND t.`id` > ? AND t.`id` <= ? AND t.`id` < ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` LIKE ? AND t.`id` LIKE ? AND t.`id` NOT LIKE ? AND t.`id` NOT LIKE ? GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t.`create_time` ASC,t.`create_time` DESC,t.`create_time` ASC,t.`create_time` DESC LIMIT 2 OFFSET 1", sql);
    }

    @Test
    public void test1() {
        Integer integer = easyQueryClient.queryable("select 1", Integer.class)
                .firstOrNull();
        Assert.assertEquals(1, (int) integer);
        String sql = easyQueryClient.queryable("select 1", Integer.class).toSQL();
        Assert.assertEquals("SELECT * FROM (select 1) t", sql);
    }

    @Test
    public void test2() {
        {
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(o -> o.FETCHER.allFields()).toSQL();

            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic`", sql);
        }
        {
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(Topic.class, o -> o.FETCHER.allFields().alias()).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,t.`alias` FROM `t_topic` t", sql);
        }
        {
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(o -> o.FETCHER.allFields().alias()).toSQL();

            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time`,`alias` FROM `t_topic`", sql);
        }
        {
            String sql = easyEntityQuery.queryable(Topic.class).toSQL();

            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic`", sql);
        }
        {
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> Select.of(
                            o.expression().sqlSegment("ROW_NUMBER() OVER(ORDER BY {0})", c -> {
                                c.expression(o.createTime());
                            }).as(TopicTypeVO::getTitle),
                            o.FETCHER.allFields()
                    )).toSQL();

            Assert.assertEquals("SELECT ROW_NUMBER() OVER(ORDER BY t.`create_time`) AS `title`,t.`id`,t.`stars`,t.`create_time` FROM `t_topic` t", sql);
        }
        {
            //todo
            ArrayList<String> columns = new ArrayList<>();
            columns.add("id");
            columns.add("title");
            columns.add("stars");
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> {
                        o.getEntitySQLContext().accept(new SQLSelectAsImpl(null, s -> {
                            for (String column : columns) {
                                s.column(o.getTable(), column);
                            }
                        }));
                        return o.getEntitySQLContext().getSelectAsExpression();

                    }).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`stars` FROM `t_topic` t", sql);
        }
        {

            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> {
                        return Select.of(
                                o.id(),
                                o.title(),
                                o.stars()
                        );
                    }).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`stars` FROM `t_topic` t", sql);
        }
        {

            String sql = easyQueryClient.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> {
                        o.column("id");
                        o.column("title");
                        o.column("stars");

                    }).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`stars` FROM `t_topic` t", sql);
        }
        {
            ArrayList<String> columns = new ArrayList<>();
            columns.add("id");
            columns.add("title");
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> {
                        o.getEntitySQLContext().accept(new SQLSelectAsImpl(null, s -> {
                            for (String column : columns) {
                                s.column(o.getTable(), column);
                            }
                        }));
                        return o.getEntitySQLContext().getSelectAsExpression();

                    }).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`title` FROM `t_topic` t", sql);
        }
    }

    @Test
    public void groupTest5_2() {
        Supplier<Exception> x = () -> {
            try {

                BlogEntity blogEntity = easyEntityQuery.queryable(BlogEntity.class)
                        .groupBy(o -> GroupKeys.of(o.id()))
                        .select(BlogEntity.class, o -> Select.of(
                                o.key1().as(BlogEntity::getId),
                                o.distinct().sum(s -> s.score().nullOrDefault(BigDecimal.ZERO)),
                                o.distinct().count(s -> s.order().nullOrDefault(BigDecimal.ONE)),
                                o.distinct().avg(s -> s.status().nullOrDefault(3))
                        )).singleOrNull();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = x.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySingleMoreElementException);
    }

    @Test
    public void joinTestx() {
        Supplier<Exception> f = () -> {
            try {
                Long aLong = easyEntityQuery.queryable(Topic.class)
                        .asTable("a123")
                        .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                        .where((t_topic, t_blog) -> {
                            t_blog.title().isNotNull();
                        })
                        .groupBy((t_topic, t_blog) -> GroupKeys.of(t_blog.id().nullOrDefault("")))
                        .select(group -> new BlogEntityProxy()
                                .id().set(group.key1())
                                .score().set(group.sum(s -> s.t2.score()))
                        ).distinct()
                        .selectColumn(s -> s.id().count())
                        .firstOrNull();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT COUNT(t2.`id`) AS `id` FROM (SELECT DISTINCT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `a123` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'')) t2 LIMIT 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTestx1() {
        Supplier<Exception> f = () -> {
            try {

                List<BlogEntityTest> list = easyEntityQuery.queryable(BlogEntity.class)
                        .select(BlogEntityTest.class).toList();

                List<BlogEntityTest> list1 = easyEntityQuery.queryable(BlogEntity.class)
                        .asTable("a123")
                        .select(BlogEntityTest.class).toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `a123` t WHERE t.`deleted` = ?", easyQuerySQLStatementException.getSQL());
    }
}
