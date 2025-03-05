package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityTrackProperty;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.configuration.dialect.AbstractSQLKeyword;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.DefaultNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.LowerCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperUnderlinedNameConversion;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.impl.SQLColumnAsSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousDefaultTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.QueryExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyTrackUtil;
import com.easy.query.test.common.PageResult;
import com.easy.query.test.dto.autodto.SchoolClassAOProp14;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.navf.User;
import com.easy.query.test.entity.navf.proxy.UserProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.page.mp.SearchCountPager;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2025/2/12 15:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest21 extends BaseTest {
    @Test
    public void test() {

        EntityMetadata entityMetadata = easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(TestColumnUpper.class);
        System.out.println(entityMetadata);


        List<User> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> {
                    t_topic.id().eq(t_topic.id());
                })
                .select((t_topic, t_blog) -> {
                    return new UserProxy()
                            .mobile().set(t_topic.id()) // 手机号码().set()
                            .email().set(t_blog.title()); // 电子邮件
                }).where(tUser -> {
                    EntitySQLContext entitySQLContext = tUser.getEntitySQLContext();
                    QueryExpressionBuilder entityExpressionBuilder = (QueryExpressionBuilder)entitySQLContext.getEntityExpressionBuilder();
                    //这张表就是select生成的匿名table
                    EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(0);
                    AnonymousDefaultTableExpressionBuilder anonymousDefaultTableExpressionBuilder = (AnonymousDefaultTableExpressionBuilder) table;

                    EntityQueryExpressionBuilder entityQueryExpressionBuilder = anonymousDefaultTableExpressionBuilder.getEntityQueryExpressionBuilder();
                    //这个就是你的select字段
                    SQLBuilderSegment projects = entityQueryExpressionBuilder.getProjects();
                    //这个就是where
                    PredicateSegment where = entityQueryExpressionBuilder.getWhere();
                    SQLExpressionProvider<Object> sqlExpressionProvider = entityQueryExpressionBuilder.getRuntimeContext().getSQLExpressionInvokeFactory().createSQLExpressionProvider(0, entityQueryExpressionBuilder);
                    FilterContext whereFilterContext = sqlExpressionProvider.getWhereFilterContext();

                    Filter filter = whereFilterContext.getFilter();

                    for (SQLSegment sqlSegment : projects.getSQLSegments()) {

                        if(sqlSegment instanceof SQLEntityAliasSegment){
                            SQLEntityAliasSegment sqlSegment1 = (SQLEntityAliasSegment) sqlSegment;
                            if(Objects.equals("mobile",sqlSegment1.getAlias())){
                                String propertyName = sqlSegment1.getPropertyName();
                                TableAvailable table1 = sqlSegment1.getTable();
                                filter.eq(table1,propertyName,"topic的mobile");
                            }
                            if(Objects.equals("email",sqlSegment1.getAlias())){
                                String propertyName = sqlSegment1.getPropertyName();
                                TableAvailable table1 = sqlSegment1.getTable();
                                filter.eq(table1,propertyName,"blog的email");
                            }
                        }
                    }


                    System.out.println(entityExpressionBuilder);

                }).toList();
    }

    @Test
    public void typeTest() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.isNull();
                    t_blog.isNotNull();
                    t_blog.id().isNull();
                    t_blog.score().nullOrDefault(BigDecimal.ZERO).gt(BigDecimal.ZERO);
                    t_blog.score().subtract(t_blog.star()).gt(BigDecimal.valueOf(15));
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` IS NULL AND `id` IS NOT NULL AND `id` IS NULL AND IFNULL(`score`,?) > ? AND (`score` - `star`) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),0(BigDecimal),0(BigDecimal),15(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void caseWhenTest1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.title().eq("someTitle");
                })
                .select(t_topic -> new TopicProxy()
                        .title().set(
                                t_topic.expression().caseWhen(() -> t_topic.title().eq("123")).then("1").elseEnd("2").asAnyType(String.class)
                        )
                        .id().set(t_topic.id())
                ).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? THEN ? ELSE ? END) AS `title`,t.`id` AS `id` FROM `t_topic` t WHERE t.`title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(String),2(String),someTitle(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        ArrayList<Topic> topics = new ArrayList<>();
        int partitionSize=10;
        List<List<Topic>> partition = EasyCollectionUtil.partition(topics, partitionSize);
        for (List<Topic> topicList : partition) {
            easyEntityQuery.insertable(topicList).batch().executeRows();
        }

    }

    @Test
    public void caseWhenTest2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.expression().caseWhen(() -> t_topic.title().eq("123"))
                            .then(1)
                            .caseWhen(() -> {
                                t_topic.title().eq("456");
                                t_topic.stars().eq(1);
                            }).then(t_topic.stars())
                            .elseEnd(3).eq(4);
                    t_topic.title().eq("someTitle");
                })
                .select(t_topic -> new TopicProxy()
                        .title().set(
                                t_topic.expression().caseWhen(() -> t_topic.title().eq("123")).then("1").elseEnd("2").asAnyType(String.class)
                        )
                        .id().set(t_topic.id())
                ).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? THEN ? ELSE ? END) AS `title`,t.`id` AS `id` FROM `t_topic` t WHERE (CASE WHEN t.`title` = ? THEN ? WHEN t.`title` = ? AND t.`stars` = ? THEN t.`stars` ELSE ? END) = ? AND t.`title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(String),2(String),123(String),1(Integer),456(String),1(Integer),3(Integer),4(Integer),someTitle(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void searchCountPageTest() {
        System.out.println("查询count");

        {


            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            PageResult<Topic> pageResult1 = easyEntityQuery.queryable(Topic.class)
                    .where(t_topic -> {
                        t_topic.title().like("123");
                    }).toPageResult(SearchCountPager.of(1, 10, true));

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` WHERE `title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `title` LIKE ? LIMIT 10", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            listenerContextManager.clear();
        }
        System.out.println("不要查询count");


        {


            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            PageResult<Topic> pageResult2 = easyEntityQuery.queryable(Topic.class)
                    .where(t_topic -> {
                        t_topic.title().like("123");
                    }).toPageResult(SearchCountPager.of(1, 10, false));

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());

            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `title` LIKE ? LIMIT 10", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            listenerContextManager.clear();
        }
        {


            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            EasyPageResult<Topic> pageResult2 = easyEntityQuery.queryable(Topic.class)
                    .where(t_topic -> {
                        t_topic.id().isNotNull();
                    }).orderBy(t_topic -> t_topic.createTime().asc()).toPageResult(7, 10);

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());

            {
                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` WHERE `id` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IS NOT NULL ORDER BY `create_time` DESC LIMIT 10 OFFSET 31", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
            }
            listenerContextManager.clear();
        }
    }

    @Test
    public void TestReverse(){


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        EasyPageResult<Topic> pageResult2 = easyEntityQuery.queryable(Topic.class)
                .configure(op->{
                    op.setReverseOrder(false);
                })
                .where(t_topic -> {
                    t_topic.id().isNotNull();
                }).orderBy(t_topic -> t_topic.createTime().asc())
                .toPageResult(7, 10);

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());

        {
            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` WHERE `id` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IS NOT NULL ORDER BY `create_time` ASC LIMIT 10 OFFSET 60", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        }
        listenerContextManager.clear();
//        Topic topic1 = new Topic();
//        topic1.setId("1");
//        topic1.setTitle("123");
//        topic1.setStars(456);
//        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
//        try {
//
//            trackManager.begin();
//            boolean b = easyEntityQuery.addTracking(topic1);
//
//            topic1.setTitle("321");
//            topic1.setStars(654);
//            EntityState trackEntityStateNotNull = easyEntityQuery.getTrackEntityStateNotNull(topic1);
//            EntityTrackProperty trackDiffProperty = EasyTrackUtil.getTrackDiffProperty(easyEntityQuery.getRuntimeContext().getEntityMetadataManager(), trackEntityStateNotNull);
//            System.out.println();
//
//
//
//            ClientExpressionUpdatable<? extends Topic> updatable = easyEntityQuery.getEasyQueryClient().updatable(topic1.getClass());
//            trackDiffProperty.getDiffProperties().forEach(a->{
//
//                updatable.set("name","4565");
//            });
//
//            updatable.executeRows();
//
//        }finally {
//
//            trackManager.release();
//        }


        ArrayList<Topic> topics = new ArrayList<>();
        easyEntityQuery.updatable(topics).executeRows();

        for (Topic topic : topics) {
            easyEntityQuery.updatable(Topic.class)
                    .setColumns(t_topic -> {

                    })
                    .where(t_topic -> {

                    })
                    .executeRows();
        }

//        easyEntityQuery.deletable(Topic.class)
//                .allowDeleteStatement(true)
//                .disableLogicDelete()
//                .where(t_topic -> {
//                    t_topic.isNotNull();
//                    t_topic.id().isNotNull();
//                    t_topic.expression().sql("1=1");
//                }).executeRows();
//        EntityMetadata entityMetadata = easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(Topic.class);
//        SQLKeyword sqlKeyword = easyEntityQuery.getRuntimeContext().getService(SQLKeyword.class);
//        String quoteName = sqlKeyword.getQuoteName(entityMetadata.getTableName());
//        easyEntityQuery.sqlExecute("truncate table "+quoteName);
    }

    @Test
    public void testWhereGroup(){

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
       easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.groupTable().id().count().filter(() -> {
                            group.groupTable().star().ge(123);
                        })
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT((CASE WHEN t.`star` >= ? THEN t.`id` ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),null(null),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));



        UpperUnderlinedNameConversion upperUnderlinedNameConversion = new UpperUnderlinedNameConversion();
        UpperCamelCaseNameConversion upperCamelCaseNameConversion = new UpperCamelCaseNameConversion();
        UnderlinedNameConversion underlinedNameConversion = new UnderlinedNameConversion();
        LowerCamelCaseNameConversion lowerCamelCaseNameConversion = new LowerCamelCaseNameConversion();
        DefaultNameConversion defaultNameConversion = new DefaultNameConversion();

        testNameConversion(defaultNameConversion,"userAge");
        testNameConversion(defaultNameConversion,"user_age");
        testNameConversion(underlinedNameConversion,"userAge");
        testNameConversion(underlinedNameConversion,"user_age");
        testNameConversion(upperUnderlinedNameConversion,"userAge");
        testNameConversion(upperUnderlinedNameConversion,"user_age");
        testNameConversion(lowerCamelCaseNameConversion,"userAge");
        testNameConversion(lowerCamelCaseNameConversion,"user_age");
        testNameConversion(upperCamelCaseNameConversion,"userAge");
        testNameConversion(upperCamelCaseNameConversion,"user_age");
    }
    @Test
    public void testWhereGroup1(){

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
       easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.where(t->t.star().ge(123)).count()
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT((CASE WHEN t.`star` >= ? THEN ? ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),1(Integer),null(null),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testWhereGroup2(){

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
       easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.where(s->s.star().ge(123)).distinct().count()
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT(DISTINCT (CASE WHEN t.`star` >= ? THEN ? ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),1(Integer),null(null),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testWhereGroup3(){

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
       easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.where(s->{}).distinct().count()
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT(DISTINCT (CASE WHEN 1 = 1 THEN ? ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),null(null),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testWhereGroup4(){

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
       easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.where(s->s.star().ge(123)).distinct().count()
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT(DISTINCT (CASE WHEN t.`star` >= ? THEN ? ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),1(Integer),null(null),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    public static void testNameConversion(NameConversion nameConversion,String uag){

        System.out.printf("%s-->%s-->%s%n",uag, EasyClassUtil.getSimpleName(nameConversion.getClass()),nameConversion.convert(uag));
    }

}
