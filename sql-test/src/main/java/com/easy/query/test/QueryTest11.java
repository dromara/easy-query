package com.easy.query.test;

import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable2;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.BlogEntityTest;
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
public class QueryTest11 extends BaseTest {

    @Test
    public void testx() {

        Queryable<BlogEntity> where1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "1"));
        List<Topic> x = easyQuery
                .queryable(Topic.class).where(o -> o.exists(where1.where(q -> q.eq(o, BlogEntity::getId, Topic::getId)))).toList();


        EntityQueryable<BlogEntityProxy, BlogEntity> where = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("1" ));
        List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.exists(() -> where.where(q -> q.id().eq(o.id())));
                }).toList();

        EntityQueryable<StringProxy, String> idQuery = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("1" ))
                .select(o -> new StringProxy(o.id()));

        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().in(idQuery))
                .toList();

        easyEntityQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class,(t1,t2)->t1.id().eq(t2.id()))
                .where((t1,t2)->t2.title().isNotNull())
                .groupBy((t1,t2)->GroupKeys.of(t2.id()))
                .select(g->new BlogEntityProxy(){{
                    id().set(g.key1());
                    score().set(g.sum(g.group().t2.score()));
                }})
                .toPageResult(1, 20);

        EntityQueryable<TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
//                .filterConfigure()
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy() {{
                    selectExpression(o.id(), o.title());
                }});

        EntityQueryable<TopicProxy, Topic> query2 = easyEntityQuery.queryable(Topic.class)
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
                    t.id().eq("456");
                }).toList();

    }

    @Test
    public void testx9() {
        {
            Queryable<Topic> query = easyQuery.queryable(Topic.class)
                    .limit(100);
            Queryable2<Topic, BlogEntity> join = query.select(Topic.class, o -> o.column(Topic::getId).column(Topic::getStars))
                    .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId));
            Queryable2<BlogEntityTest, BlogEntity> join2 = join.select(BlogEntityTest.class, (t, t1) -> {
                        t.columnAs(Topic::getId, BlogEntityTest::getUrl);
                        t1.column(BlogEntity::getTitle);
                    })
                    .innerJoin(BlogEntity.class, (t, t1) -> {
                        t.eq(t1, BlogEntityTest::getUrl, BlogEntity::getId);
                    });
            List<BlogEntity> list = join2.select(BlogEntity.class, (t, t1) -> {
                t.columnAs(BlogEntityTest::getUrl, BlogEntity::getUrl);
                t1.columnAs(BlogEntity::getTitle, BlogEntity::getContent);
            }).toList();

//            var query = rep.GetLambdaQuery().Take(100);
//            var join = query.Select(b => new { a1 = b.Id, a2 = b.F_String }).Join<TestEntityItem>((a, b) => a.a1 == b.TestEntityId);//第一次关联
//            var join2 = join.Select((a, b) => new { a3 = a.a1, a4 = b.Name })
//                .Join<TestEntity>((a, b) => a.a3 == b.Id);//第二次关联
//            join2.Select((a, b) => new
//            {
//                a.a4,
//                        b.Id
//            });

        }
        {
            List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class).limit(100)
                    .selectDraft(o -> Select.draft(o.id(), o.stars()))
                    .leftJoin(BlogEntity.class, (t, t1) -> t.value1().eq(t1.id()))
                    .selectDraft((a, b) -> Select.draft(a.value1(), b.url()))
                    .innerJoin(BlogEntity.class, (t, t1) -> t.value2().eq(t1.id()))
                    .selectDraft((a, b) -> Select.draft(a.value1(), b.url())).toList();
        }
    }

    @Test
    public void testx1() {


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
