package com.easy.query.test;

import com.easy.query.api.proxy.base.LongProxy;
import com.easy.query.api.proxy.base.MapProxy;
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
import com.easy.query.test.dto.BlogEntityTest2;
import com.easy.query.test.dto.TopicSubQueryBlog;
import com.easy.query.test.dto.proxy.BlogEntityTest2Proxy;
import com.easy.query.test.dto.proxy.TopicSubQueryBlogProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.vo.proxy.BlogEntityVO1Proxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                .select(g->new BlogEntityProxy().adapter(r->{
                    r.id().set(g.key1());
                    r.score().set(g.sum(g.group().t2.score()));
                }))
                .toPageResult(1, 20);

        EntityQueryable<TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
//                .filterConfigure()
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy().selectExpression(o.id(), o.title()));

        EntityQueryable<TopicProxy, Topic> query2 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy());//这样等于selectAll


        Query<Topic> query1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy().selectExpression(o.id(), o.title()));


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
                    .select(o -> new BlogEntityProxy().adapter(r->{
                        PropTypeColumn<BigDecimal> integerPropTypeColumn = o.sql("1").setPropertyType(BigDecimal.class);
                        r.score().set(integerPropTypeColumn);
                    })).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT 1 AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? AND EXISTS (SELECT 1 FROM `t_topic` t1 WHERE t1.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<TopicProxy, Topic> sql = easyEntityQuery
                    .queryable(Topic.class)
                    .where(o -> o.id().eq("3" ));

            List<BlogEntity> topics = easyEntityQuery
                    .queryable(BlogEntity.class)
                    .leftJoin(sql,(a,b)->a.id().eq(b.id()))
                    .where((a,b) -> {
                        a.id().isNotNull();
                        b.id().isNotNull();
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) t2 ON t.`id` = t2.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t2.`id` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Map<String,Object>> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(f -> f.id().eq( "1"))
                    .select(s -> new MapProxy().adapter(r->{
                        r.put("id",s.id());
                        r.put("name",s.stars());
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `id`,t.`stars` AS `name` FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Map<String,Object>> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(f -> f.id().eq( "1"))
                    .select(s -> new MapProxy().selectAll(s))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Map<String,Object>> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(f -> f.id().eq( "1"))
                    .select(s -> new MapProxy())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT * FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void testx2() {
        {

            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq( "2"))
                    .select(o->{
                        BlogEntityVO1Proxy r = new BlogEntityVO1Proxy();
                        r.score().set(o.order());
                        return r;
                    })
                    .limit(1).toSQL();
            Assert.assertEquals("SELECT t.`order` AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
        }
        {

            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq( "2"))
                    .select(o->{
                        BlogEntityVO1Proxy r = new BlogEntityVO1Proxy();
                        r.score().set(o.order());
                        return r;
                    })
                    .limit(1).toSQL();
            Assert.assertEquals("SELECT t.`order` AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
        }
        {

            EntityQueryable<BlogEntityTest2Proxy, BlogEntityTest2> queryable = easyEntityQuery.queryable(BlogEntity.class)
                    .select(o -> new BlogEntityTest2Proxy().selectAll(o).selectIgnores(o.title()).adapter(r->{
                        r.url().set(o.url());
                    }));
            String sql1 = queryable.toSQL();
            Assert.assertEquals("SELECT t.`content`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t.`url` AS `my_url` FROM `t_blog` t WHERE t.`deleted` = ?", sql1);

        }
    }
    @Test
    public void testx3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.id().eq("2" );
                    o.id().eq("3" );
                    o.or(() -> {
                        o.id().eq("4" );
                        o.id().eq("5" );
                    });
                })
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? AND `id` = ? AND (`id` = ? OR `id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2(String),3(String),4(String),5(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TopicSubQueryBlog> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().isNotNull())
                .select(o->new TopicSubQueryBlogProxy().adapter(r->{
                    r.selectAll(o);
                    Query<Long> subQuery = easyEntityQuery.queryable(BlogEntity.class).where(x -> x.id().eq(o.id())).selectCount();//count(*)
                    r.blogCount().setSubQuery(subQuery);
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,(SELECT COUNT(*) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TopicSubQueryBlog> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().isNotNull())
                .select(o->new TopicSubQueryBlogProxy().adapter(r->{
                    r.selectAll(o);
                    EntityQueryable<LongProxy, Long> subQuery = easyEntityQuery.queryable(BlogEntity.class).where(x -> x.id().eq(o.id())).select(x -> new LongProxy(x.star().sum()));//SUM(t1.`star`)
                    r.blogCount().setSubQuery(subQuery);
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,(SELECT SUM(t1.`star`) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx6() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<BlogEntityProxy, BlogEntity> subQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("1" ));

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.exists(() -> {
                    return subQueryable.where(q -> q.id().eq(o.id()));
                })).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ? AND t1.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx7() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<BlogEntityProxy, BlogEntity> subQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("1" ));

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.notExists(() -> {
                    return subQueryable.where(q -> q.id().eq(o.id()));
                })).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE NOT EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ? AND t1.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx8() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<StringProxy, String> idQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("123" ))
                .select(o -> new StringProxy(o.id()));//如果子查询in string那么就需要select string，如果integer那么select要integer 两边需要一致

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().in(idQueryable)).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx10() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<StringProxy, String> idQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("123" ))
                .select(o -> new StringProxy(o.id()));//如果子查询in string那么就需要select string，如果integer那么select要integer 两边需要一致

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().notIn(idQueryable)).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` NOT IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx11(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Query<TopicTypeEnum> query = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> o.id().eq("123" )).selectColumn(o -> o.topicType());
        List<TopicTypeTest1> list = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.topicType().in(query);
        }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`topic_type`,t.`create_time` FROM `t_topic_type` t WHERE t.`topic_type` IN (SELECT t1.`topic_type` FROM `t_topic_type` t1 WHERE t1.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx12(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Integer> list = easyEntityQuery.queryable(TopicTypeTest1.class).selectColumn(o -> o.topicType().toNumber(Integer.class)).toList();
        Integer integer = list.get(0);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`topic_type` FROM `t_topic_type` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx13(){
        ArrayList<TopicTypeEnum> topicTypeEnums = new ArrayList<>();
        topicTypeEnums.add(TopicTypeEnum.CLASSER);
        topicTypeEnums.add(TopicTypeEnum.STUDENT);
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicTypeTest1> list = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.topicType().in(topicTypeEnums);
        }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE `topic_type` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("9(Integer),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
