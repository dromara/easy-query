package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityTrackProperty;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.common.tuple.Tuple3;
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
import com.easy.query.core.func.def.enums.OrderByModeEnum;
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
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.dto.autodto.SchoolClassAOProp14;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.navf.User;
import com.easy.query.test.entity.navf.proxy.UserProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.page.mp.SearchCountPager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
                    QueryExpressionBuilder entityExpressionBuilder = (QueryExpressionBuilder) entitySQLContext.getEntityExpressionBuilder();
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

                        if (sqlSegment instanceof SQLEntityAliasSegment) {
                            SQLEntityAliasSegment sqlSegment1 = (SQLEntityAliasSegment) sqlSegment;
                            if (Objects.equals("mobile", sqlSegment1.getAlias())) {
                                String propertyName = sqlSegment1.getPropertyName();
                                TableAvailable table1 = sqlSegment1.getTable();
                                filter.eq(table1, propertyName, "topic的mobile");
                            }
                            if (Objects.equals("email", sqlSegment1.getAlias())) {
                                String propertyName = sqlSegment1.getPropertyName();
                                TableAvailable table1 = sqlSegment1.getTable();
                                filter.eq(table1, propertyName, "blog的email");
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
        int partitionSize = 10;
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
    public void TestReverse() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        EasyPageResult<Topic> pageResult2 = easyEntityQuery.queryable(Topic.class)
                .configure(op -> {
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
    public void testWhereGroup() {

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

        testNameConversion(defaultNameConversion, "userAge");
        testNameConversion(defaultNameConversion, "user_age");
        testNameConversion(underlinedNameConversion, "userAge");
        testNameConversion(underlinedNameConversion, "user_age");
        testNameConversion(upperUnderlinedNameConversion, "userAge");
        testNameConversion(upperUnderlinedNameConversion, "user_age");
        testNameConversion(lowerCamelCaseNameConversion, "userAge");
        testNameConversion(lowerCamelCaseNameConversion, "user_age");
        testNameConversion(upperCamelCaseNameConversion, "userAge");
        testNameConversion(upperCamelCaseNameConversion, "user_age");
    }

    @Test
    public void testWhereGroup1() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.where(t -> t.star().ge(123)).count()
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT((CASE WHEN t.`star` >= ? THEN ? ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),1(Integer),null(null),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testWhereGroup2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.where(s -> s.star().ge(123)).distinct().count()
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT(DISTINCT (CASE WHEN t.`star` >= ? THEN ? ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),1(Integer),null(null),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testWhereGroup3() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.where(s -> {
                        }).distinct().count()
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT(DISTINCT (CASE WHEN 1 = 1 THEN ? ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),null(null),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testWhereGroup4() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.where(s -> s.star().ge(123)).distinct().count()
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,COUNT(DISTINCT (CASE WHEN t.`star` >= ? THEN ? ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),1(Integer),null(null),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testWhereGroup5() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.groupTable().score().sumBigDecimal().filter(() -> {
                            group.groupTable().star().ge(123);
                        })
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,SUM((CASE WHEN t.`star` >= ? THEN t.`score` ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),0(Long),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testWhereGroup6() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");

                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.groupTable().score().sumBigDecimal(true).filter(() -> {
                            group.groupTable().star().ge(123);
                        })
                )).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`title` AS `value1`,SUM(DISTINCT (CASE WHEN t.`star` >= ? THEN t.`score` ELSE ? END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ? GROUP BY t.`title`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),0(Long),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    public static void testNameConversion(NameConversion nameConversion, String uag) {

        System.out.printf("%s-->%s-->%s%n", uag, EasyClassUtil.getSimpleName(nameConversion.getClass()), nameConversion.convert(uag));
    }


    @Test
    public void joinOrderTest() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .orderBy(bank_card -> {
                    bank_card.user().age().asc(OrderByModeEnum.NULLS_LAST);
                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` ORDER BY CASE WHEN t1.`age` IS NULL THEN 1 ELSE 0 END ASC,t1.`age` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(Integer),0(Long),false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void joinOrderTest2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .orderBy(bank_card -> {
                    bank_card.user().age().nullOrDefault(1).asc();
                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` ORDER BY IFNULL(t1.`age`,?) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void joinOrderTest3() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .orderBy(bank_card -> {
                    bank_card.user().age().nullOrDefault(1).asc(OrderByModeEnum.NULLS_LAST);
                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` ORDER BY CASE WHEN IFNULL(t1.`age`,?) IS NULL THEN 1 ELSE 0 END ASC,IFNULL(t1.`age`,?) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void subNumberRangeClosed() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().count().rangeOpenClosed(1L, 2L);

                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t WHERE ((SELECT COUNT(*) FROM `doc_bank_card` t1 WHERE t1.`uid` = t.`id`) > ? AND (SELECT COUNT(*) FROM `doc_bank_card` t1 WHERE t1.`uid` = t.`id`) <= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Long),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void subNumberRangeClosed1() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().count().rangeClosed(1L, 2L);

                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t WHERE (SELECT COUNT(*) FROM `doc_bank_card` t1 WHERE t1.`uid` = t.`id`) BETWEEN ? AND ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Long),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testOrderBy() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .orderBy(bank_card -> {
                    EasySQLUtil.dynamicOrderBy(bank_card.getEntitySQLContext().getOrderSelector(), bank_card.getTable(), "user.age", true, OrderByModeEnum.NULLS_LAST, true);
                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` ORDER BY CASE WHEN t1.`age` IS NULL THEN 1 ELSE 0 END ASC,t1.`age` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Long),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testOrderBy2() {
        LinkedHashMap<String, OrderConfig> map = new LinkedHashMap<>();
        map.put("type", OrderConfig.builder().asc(false).build());
        map.put("user.age", OrderConfig.builder().nullsModeEnum(OrderByModeEnum.NULLS_LAST).asc(false).build());
        UISort uiSort = new UISort(map);
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .orderByObject(uiSort)
                .toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` ORDER BY t.`type` DESC,CASE WHEN t1.`age` IS NULL THEN 1 ELSE 0 END ASC,t1.`age` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Long),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    public static class UISort implements ObjectSort {

        private final Map<String, OrderConfig> sort;
        private boolean strictMode;

        public UISort(Map<String, OrderConfig> sort) {
            this(sort, false);
        }

        public UISort(Map<String, OrderConfig> sort, boolean strictMode) {

            this.sort = sort;
            this.strictMode = strictMode;
        }

        @Override
        public void configure(ObjectSortBuilder builder) {
            for (Map.Entry<String, OrderConfig> s : sort.entrySet()) {
                OrderConfig value = s.getValue();
                builder.orderBy(s.getKey(), value.isAsc(), value.getNullsModeEnum());
            }
        }

        @Override
        public boolean useStrictMode() {
            return strictMode;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class OrderConfig {
        private boolean asc;
        private OrderByModeEnum nullsModeEnum;
    }


    @Test
    public void testOrderBy3() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .orderBy(bank_card -> {
                    bank_card.user().age().asc(OrderByModeEnum.NULLS_LAST);
                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` ORDER BY CASE WHEN t1.`age` IS NULL THEN 1 ELSE 0 END ASC,t1.`age` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Long),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void anyColumnTest1() {
        ArrayList<Tuple3<String, Boolean, OrderByModeEnum>> sorts = new ArrayList<>();
        sorts.add(new Tuple3<>("user.age", false, OrderByModeEnum.NULLS_LAST));
        sorts.add(new Tuple3<>("type", true, OrderByModeEnum.NULLS_LAST));


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.anyColumn("code").nullOrDefault("123").eq("456");
                    bank_card.code().asAny().nullOrDefault("789").eq("987");
                    bank_card.code().nullOrDefault("654").eq("321");
                })
                .orderBy(bank_card -> {
                    for (Tuple3<String, Boolean, OrderByModeEnum> sort : sorts) {
                        bank_card.anyColumn(sort.t()).orderBy(sort.t1(), sort.t2());
                    }
                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE IFNULL(t.`code`,?) = ? AND IFNULL(t.`code`,?) = ? AND IFNULL(t.`code`,?) = ? ORDER BY CASE WHEN t1.`age` IS NULL THEN 1 ELSE 0 END ASC,t1.`age` DESC,CASE WHEN t.`type` IS NULL THEN 1 ELSE 0 END ASC,t.`type` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),456(String),789(String),987(String),654(String),321(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

//        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
//                .where(user -> {
//                    user.bankCards().count().rangeOpenClosed(1L, 2L);
//
//                }).toList();
    }
    @Test
    public void anyColumnTest2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.anyColumn("user.age").eq(123);;
                    bank_card.user().age().eq(123);
                    bank_card.code().eq("321");
                    bank_card.anyColumn("code").nullOrDefault("123").eq("456");
                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE t1.`age` = ? AND t1.`age` = ? AND t.`code` = ? AND IFNULL(t.`code`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(Integer),123(Integer),321(String),123(String),456(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

//        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
//                .where(user -> {
//                    user.bankCards().count().rangeOpenClosed(1L, 2L);
//
//                }).toList();
    }
    @Test
    public void anyColumnOrderByTest1() {
        ArrayList<Tuple3<String, Boolean, OrderByModeEnum>> sorts = new ArrayList<>();
        sorts.add(new Tuple3<>("user.age", false, OrderByModeEnum.NULLS_LAST));
        sorts.add(new Tuple3<>("type", true, OrderByModeEnum.NULLS_LAST));


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.anyColumn("code").nullOrDefault("123").eq("456");
                    bank_card.code().asAny().nullOrDefault("789").eq("987");
                    bank_card.code().nullOrDefault("654").eq("321");
                })
                .orderBy(bank_card -> {
                    for (Tuple3<String, Boolean, OrderByModeEnum> sort : sorts) {
                        bank_card.anyColumn(sort.t()).orderBy(sort.t1(), sort.t2());
                    }
                }).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE IFNULL(t.`code`,?) = ? AND IFNULL(t.`code`,?) = ? AND IFNULL(t.`code`,?) = ? ORDER BY CASE WHEN t1.`age` IS NULL THEN 1 ELSE 0 END ASC,t1.`age` DESC,CASE WHEN t.`type` IS NULL THEN 1 ELSE 0 END ASC,t.`type` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),456(String),789(String),987(String),654(String),321(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

//        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
//                .where(user -> {
//                    user.bankCards().count().rangeOpenClosed(1L, 2L);
//
//                }).toList();
    }

    @Test
    public  void testDistinctCount(){

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<Long> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");
                }).selectColumn(t_blog -> t_blog.id().count(true)).toList();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT COUNT(DISTINCT t.`id`) FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public  void testDistinctCount1(){

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        long count = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like("123");
                }).selectColumn(t_blog -> t_blog.id()).distinct().count();
        listenerContextManager.clear();

        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
        Assert.assertEquals("SELECT COUNT(DISTINCT t.`id`) FROM `t_blog` t WHERE t.`deleted` = ? AND t.`title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

}
