package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.api.pagination.DefaultPageResult;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.exception.EasyQueryResultSizeLimitException;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.partition.Partition1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.school.proxy.SchoolClassProxy;
import com.easy.query.test.entity.school.proxy.SchoolStudentProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.keytest.MyTestPrimaryKey;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.vo.BlogEntityVO1;
import com.easy.query.test.vo.BlogEntityVO2;
import com.easy.query.test.vo.TestUserAAA;
import com.easy.query.test.vo.proxy.BlogEntityVO1Proxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * create time 2024/6/13 12:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest18 extends BaseTest {

    @Test
    public void test1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Topic topic = easyEntityQuery.queryable(Topic.class)
                .where(t -> t.id().isNull())
                .singleOrDefault(new Topic());
        Assert.assertNull(topic.getId());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IS NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void test2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Topic topic = easyEntityQuery.queryable(Topic.class)
                .where(t -> t.id().isNull())
                .singleOrDefault(new Topic(), result -> result == null);
        Assert.assertNull(topic.getId());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IS NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        Character x = 'a';

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.id().in(Arrays.asList("1", "2"));
                }).toList();
    }

    @Test
    public void testxxx() {

//        String sql="";
//        easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    if(EasyStringUtil.isNotBlank(sql)){
//                        t.expression().sql(sql);
//                    }
//                })
//                .where(EasyStringUtil.isNotBlank(sql),t -> {
//                    t.expression().sql(sql);
//                })

        Queryable<TestUserAAA> queryable = easyQuery.queryable(TestUserAAA.class);
    }

    @Test
    public void test8() {
        MappingPath address = SchoolStudentProxy.TABLE.schoolStudentAddress().address();
        String s = address.__getMappingPath();
        System.out.println(s);
        Assert.assertEquals("schoolStudentAddress.address", s);
    }

    @Test
    public void test8_1() {
        {
            MappingPath address = SchoolStudentProxy.TABLE.schoolStudentAddress().address();
            String s = address.__getMappingPath();
            Assert.assertEquals("schoolStudentAddress.address", s);

        }
        {
            MappingPath address = SchoolStudentProxy.TABLE.schoolStudentAddress().schoolStudent();
            String s = address.__getMappingPath();
            Assert.assertEquals("schoolStudentAddress.schoolStudent", s);

        }
        {
            MappingPath address = SchoolStudentProxy.TABLE.schoolStudentAddress().studentId();
            String s = address.__getMappingPath();
            Assert.assertEquals("schoolStudentAddress.studentId", s);

        }
        {
            MappingPath address = SchoolStudentProxy.TABLE.schoolStudentAddress().schoolStudent();
            String s = address.__getMappingPath();
            Assert.assertEquals("schoolStudentAddress.schoolStudent", s);

        }
        {

            MappingPath address = SchoolClassProxy.TABLE.schoolStudents().flatElement().schoolStudentAddress();
            String s = address.__getMappingPath();
            Assert.assertEquals("schoolStudents.schoolStudentAddress", s);
        }
        {

            MappingPath address1 = SchoolClassProxy.TABLE.schoolStudents().flatElement().schoolStudentAddress().studentId();
            String s1 = address1.__getMappingPath();
            Assert.assertEquals("schoolStudents.schoolStudentAddress.studentId", s1);
        }
        {

            MappingPath address = SchoolClassProxy.TABLE.schoolStudents().flatElement().schoolStudentAddress();
            String s = address.__getMappingPath();
            Assert.assertEquals("schoolStudents.schoolStudentAddress", s);
        }
        {

            MappingPath address1 = SchoolClassProxy.TABLE.schoolStudents().flatElement().schoolStudentAddress().studentId();
            String s1 = address1.__getMappingPath();
            Assert.assertEquals("schoolStudents.schoolStudentAddress.studentId", s1);
        }
    }

    @Test
    public void test9() {
        List<Topic> list4 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                .select(o -> {
                    TopicProxy r = new TopicProxy();
                    r.title().set(o.stars().nullOrDefault(0).toStr());
                    ColumnFunctionComparableAnyChainExpression<String> nullProperty = o.expression().sqlSegment("IFNULL({0},'')", c -> {
                        c.expression(o.id());
                    }, String.class);
                    r.alias().set(nullProperty);
                    return r;
                })
                .toList();
    }

    @Test
    public void test10() {
        List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                .select(o -> {
                    TopicProxy r = new TopicProxy();
                    r.title().set(o.stars().nullOrDefault(0).toStr());
                    r.alias().setSQL("IFNULL({0},'')", c -> {
                        c.keepStyle();
                        c.expression(o.id());
                    });
                    return r;
                })
                .toList();
    }

    @Test
    public void test11() {
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(s -> {
                    s.blogs().any(x -> {
                        x.content().eq("");
                    });
                }).toList();
    }

    @Test
    public void test13() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(s -> {
                    s.expression().caseWhen(() -> s.id().eq("123")).then(s.id()).elseEnd(s.id().nullOrDefault("1234")).eq("123xx");
                    s.blogs().any(x -> {
                        x.content().eq("");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE (CASE WHEN t.`id` = ? THEN t.`id` ELSE IFNULL(t.`id`,?) END) = ? AND EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`title` = t.`id` AND t1.`content` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1234(String),123xx(String),false(Boolean),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    //    @Test
//    public void test14(){
//
//        List<Map> list1 = easyQueryClient.queryable(Map.class)
//                .asTable("t_user")
//                .leftJoin(Map.class, (t, t1) -> {
//                    t.eq(t1, "id", "uid");
//                }).asTable("t_user_extra")
//                .where((m1, m2) -> {
//                    m1.eq("name","111");
//                    m2.like("content","456");
//                })
//                .toList();
//    }
    @Test
    public void test15() {
        //配置来源数据库
        List<String> columns = Arrays.asList("id", "stars", "title");
        List<Topic> list = easyQueryClient.queryable(Topic.class)
                .select(Topic.class, t -> {
                    for (String column : columns) {
                        t.column(column);
                    }
                    SQLFunction sqlFunction = t.fx().nullOrDefault("id", "123");
                    t.sqlFunc(sqlFunction);
                }).toList();
//        LocalDateTime[]  times= new LocalDateTime[2];
//        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    t.createTime().rangeClosed(getIndexValueOrNull(times, 0), getIndexValueOrNull(times, 1));
//                }).toList();
    }

    public static <TValue> TValue getIndexValueOrNull(TValue[] values, int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index < 0.");
        }
        if (EasyArrayUtil.isEmpty(values)) {
            return null;
        }
        if (values.length <= index) {
            return null;
        }
        return values[index];
    }

    @Test
    public void test14() {

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .include(s -> s.myBlog())
                .includes(s -> s.blogs(), b -> {
                    b.includes(x -> x.users());
                }).toList();

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        EasyPageResult<BlogEntityVO1> pageResult = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                .select(BlogEntityVO1.class, (t1, b2) -> Select.of(
                        t1.FETCHER.id().stars(),
                        b2.createTime()
                )).distinct()
                .toPageResult(1, 2);

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT COUNT(DISTINCT t.`id`,t.`stars`,t1.`create_time`) FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT DISTINCT t.`id`,t.`stars`,t1.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LIMIT 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void test16() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        EasyPageResult<BlogEntityVO1> pageResult = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                .select((t1, b2) -> new BlogEntityVO1Proxy()
                        .score().set(t1.stars().asAnyType(BigDecimal.class))
                ).distinct()
                .toPageResult(1, 2);

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT COUNT(DISTINCT t.`stars`) FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT DISTINCT t.`stars` AS `score` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LIMIT 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void test17() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);


        EasyPageResult<Topic> pageResult = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                .select(Topic.class)
                .distinct()
                .toPageResult(1, 2);

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT COUNT(DISTINCT t.`id`,t.`stars`,t.`title`,t.`create_time`) FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT DISTINCT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LIMIT 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }


    @Test
    public void test18() {
        EntityQueryable2<TopicProxy, Topic, BlogEntityProxy, BlogEntity> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()));

        long count = query.cloneQueryable().select((t, b2) -> t.id()).distinct().count();
        List<Topic> list = query.cloneQueryable().select(Topic.class).distinct().limit(1, 20).toList();
    }

    @Test
    public void testMaxEnum() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        TopicTypeEnum topicTypeEnum = easyEntityQuery.queryable(TopicTypeTest1.class)
                .maxOrNull(x -> x.topicType());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MAX(`topic_type`) FROM `t_topic_type`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(String),1234(String),123xx(String),false(Boolean),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        System.out.println(topicTypeEnum);
    }

    @Test
    public void textDistinct1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        long count = easyEntityQuery.queryable(Topic.class)
                .distinct().count();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT COUNT(DISTINCT `id`,`stars`,`title`,`create_time`) FROM `t_topic`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(String),1234(String),123xx(String),false(Boolean),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void textDistinct2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);


        EasyPageResult<Topic> pageResult = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                .distinct()
                .toPageResult(1, 2);

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT COUNT(DISTINCT t.`id`,t.`stars`,t.`title`,t.`create_time`) FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT DISTINCT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LIMIT 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void test111() {
        MyTestPrimaryKey myTestPrimaryKey = new MyTestPrimaryKey();
        Assert.assertNull(myTestPrimaryKey.getId());
        try {
            easyQuery.insertable(myTestPrimaryKey).executeRows();
        } catch (Exception exception) {

        }
        System.out.println(myTestPrimaryKey.getId());
        Assert.assertNotNull(myTestPrimaryKey.getId());
        Assert.assertEquals("18bb13b4-121e-429c-bcd9-9b89e2345d9d".length(), myTestPrimaryKey.getId().length());

    }

    @Test
    public void test123() {
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setId("123");
        easyEntityQuery.updatable(blogEntity)
//                .setColumns(b -> b.FETCHER.content().order())
//                .setIgnoreColumns(b->b.FETCHER.createTime().createBy())
                .executeRows();
        easyEntityQuery.updatable(blogEntity)
//                .setColumns(b -> b.FETCHER.content().order())
                .setIgnoreColumns(b -> b.FETCHER.createTime().createBy().updateTime().updateBy())
                .whereColumns(b -> b.FETCHER.columnKeys().order().content())
                .executeRows();


    }

    @Test
    public void test124() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntityVO2> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                .select(BlogEntityVO2.class, (t, b2) -> Select.of(
                        t.FETCHER.allFields(),
                        b2.FETCHER.id().as(BlogEntityVO2::getContent).top().as(BlogEntityVO2::getScore)
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`title`,t1.`id` AS `content`,t1.`top` AS `score` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


    }

    @Test
    public void test125() {
//        PageAble<BlogEntity> pageAble = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> {
//                    b.createTime().gt(LocalDateTime.now());
//                }).orderBy(b -> {
//                    b.createTime().asc();
//                });
//
//        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = ((Query) pageAble).getSQLEntityExpressionBuilder();
//        OrderBySQLBuilderSegment order = sqlEntityExpressionBuilder.getOrder();
//        List<SQLSegment> sqlSegments = order.getSQLSegments();
//        for (SQLSegment sqlSegment : sqlSegments) {
//
//            System.out.println(sqlSegment);
//        }

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.createTime().plus(b.star(), TimeUnitEnum.HOURS).gt(LocalDateTime.of(2024, 1, 1, 0, 0));
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND date_add(`create_time`, interval (`star`) hour) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2024-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


    }

    @Test
    public void testxx() {
//        List<Topic> list = easyEntityQuery.queryable(Topic.class).toList();

//        EasyPageResult<Topic> pageResult = easyEntityQuery.queryable("select * from t_topic ", Topic.class)
//                .toPageResult(1, 2);

//
//        List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
//                .where((t1, b2) -> t1.id().eq("123"))
//                .groupBy((t1, b2) -> GroupKeys.TABLE2.of(
//                        t1.id(), b2.content()
//                ))
//                .select(group -> Select.DRAFT.of(
//                        group.key1(),
//                        group.key2()
//                )).toList();

//
//        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
//                .setDefaultDataSource(dataSource)
//                .optionConfigure(op -> {
//                    op.setDeleteThrowError(false);
//                    op.setExecutorCorePoolSize(1);
//                    op.setExecutorMaximumPoolSize(2);
//                    op.setMaxShardingQueryLimit(1);
//                })
//                .useDatabaseConfigure(new OracleDatabaseConfiguration())
//                .build();
//        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
//        EasyPageResult<Topic> pageResult1 = defaultEasyEntityQuery.queryable("select * from t_topic ", Topic.class)
//                .toPageResult(1, 2);

        EasyPageResult<Topic> pageResult1 = easyEntityQuery.queryable("select * from t_topic where id != ? ", Topic.class, Arrays.asList("123"))
                .where(t -> t.id().ne("456"))
                .toPageResult(1, 2);

        EntityQueryable<TopicProxy, Topic> joinTable = easyEntityQuery.queryable("select * from t_topic where id != ? ", Topic.class, Arrays.asList("123"));
        List<Draft2<String, String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(joinTable, (b, t2) -> b.id().eq(t2.id()))
                .where((b1, t2) -> {
                    b1.createTime().gt(LocalDateTime.now());
                    t2.createTime().format("yyyy").eq("2014");
                }).select((b1, t2) -> Select.DRAFT.of(
                        b1.id(),
                        t2.id()
                )).toList();

    }

    @Test
    public void testChunk1() {
        HashMap<String, BlogEntity> ids = new HashMap<>();
        easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(b -> b.createTime().asc())
                .orderBy(b -> b.id().asc())
                .toChunk(20, blogs -> {
                    Assert.assertTrue(blogs.size() <= 20);
                    for (BlogEntity blog : blogs) {
                        if (ids.containsKey(blog.getId())) {
                            throw new RuntimeException("id 重复:" + blog.getId());
                        }
                        ids.put(blog.getId(), blog);
                    }
                    return true;
                });
    }

    @Test
    public void testChunk2() {
        HashMap<String, BlogEntity> ids = new HashMap<>();
        easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(b -> b.createTime().asc())
                .orderBy(b -> b.id().asc())
                .toChunk(20, blogs -> {
                    Assert.assertTrue(blogs.size() <= 20);
                    for (BlogEntity blog : blogs) {
                        if (ids.containsKey(blog.getId())) {
                            throw new RuntimeException("id 重复:" + blog.getId());
                        }
                        ids.put(blog.getId(), blog);
                    }
                });
        Assert.assertEquals(100, ids.size());
    }

    @Test
    public void testStreamChunk1() {
        HashMap<String, BlogEntity> ids = new HashMap<>();
        try (JdbcStreamResult<BlogEntity> streamResult = easyEntityQuery.queryable(BlogEntity.class).toStreamResult(1000)) {
            //每20个一组消费
            streamResult.toChunk(20, blogs -> {
                Assert.assertTrue(blogs.size() <= 20);
                for (BlogEntity blog : blogs) {
                    if (ids.containsKey(blog.getId())) {
                        throw new RuntimeException("id 重复:" + blog.getId());
                    }
                    ids.put(blog.getId(), blog);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testResultSizeLimit1() {
        boolean exception = false;
        try {
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .configure(x -> {
                        x.setResultSizeLimit(10);
                    })
                    .toList();
        } catch (Exception ex) {
            exception = true;
            Assert.assertTrue(ex instanceof EasyQueryResultSizeLimitException);
            EasyQueryResultSizeLimitException ex1 = (EasyQueryResultSizeLimitException) ex;
            Assert.assertEquals(10, ex1.getLimit());
        }
        Assert.assertTrue(exception);
    }

    @Test
    public void testPartition1() {
//        easyEntityQuery.queryable(BlogEntity.class)
//                .select(o -> Select.DRAFT.of(
//                        o
//                ))
    }

    @Test
    public void testChunk3() {
        HashMap<String, BlogEntity> ids = new HashMap<>();
        easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(b -> b.createTime().asc())
                .orderBy(b -> b.id().asc())
                .toChunk(3, blogs -> {
                    Assert.assertTrue(blogs.size() <= 3);
                    for (BlogEntity blog : blogs) {
                        if (ids.containsKey(blog.getId())) {
                            throw new RuntimeException("id 重复:" + blog.getId());
                        }
                        ids.put(blog.getId(), blog);
                    }
                });
        Assert.assertEquals(100, ids.size());
    }

    @Test
    public void testChunk4() {
        AtomicInteger a = new AtomicInteger(0);
        easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(b -> b.createTime().asc())
                .orderBy(b -> b.id().asc())
                .toChunk(3, blogs -> {
                    for (BlogEntity blog : blogs) {
                        a.incrementAndGet();
                    }
                    return true;
                });
        Assert.assertEquals(100, a.intValue());

        EntityMetadataManager entityMetadataManager = easyEntityQuery.getRuntimeContext().getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(Topic.class);
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        String singleKeyProperty = entityMetadata.getSingleKeyProperty();

    }

    @Test
    public void testDraft() {
        Class<Draft2<String, String>> draft2Class = EasyObjectUtil.typeCastNullable(Draft2.class);
        List<Draft2<String, String>> list = easyQuery.queryable(Topic.class)
                .limit(true, 100)
                .where(t -> {
                    t.ne(Topic::getId, "123");
                }).select(draft2Class, t -> t.columnAs(Topic::getId, Draft2::getValue1).columnAs(Topic::getTitle, Draft2::getValue2))
                .toList();
        for (Draft2<String, String> stringLocalDateTimeDraft2 : list) {
            String value1 = stringLocalDateTimeDraft2.getValue1();
            String value2 = stringLocalDateTimeDraft2.getValue2();
            System.out.println(value1);
            System.out.println(value2);
        }
//        int limit=100;
//        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    t.id().eq("123");
//                })
//                .limit(limit>0, limit).toList();
//
//        EntityQueryable<TopicProxy, Topic> where = easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    t.id().eq("123");
//                });
//        EntityQueryable<TopicProxy, Topic> topicProxyTopicEntityQueryable1 = where.cloneQueryable();
//        EntityQueryable<TopicProxy, Topic> topicProxyTopicEntityQueryable2 = where.cloneQueryable();
//
//
//        EasyPageResult<Topic> pageResult = getQuery().toPageResult(1, 2);
//        processVOList(pageResult.getData());
//        List<Topic> list2 = getQuery().toList();
//        processVOList(list2);
    }

    private EasyPageResult<Topic> pageOrExport(int limit) {
        if (limit < 0) {
            List<Topic> list = getQuery().toList();
            return new DefaultPageResult<>(-1, list);
        }
        return getQuery().toPageResult(1, limit);
    }

    private EntityQueryable<TopicProxy, Topic> getQuery() {
        return easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.id().eq("123");
                });
    }

    private void processVOList(List<Topic> topics) {
        for (Topic topic : topics) {


        }
    }

    @Test
    public void testPartitionBy1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<Partition1<Topic, Integer>> list1 = easyEntityQuery.queryable(Topic.class)
                    .select(t -> Select.PARTITION.of(
                            t,
                            t.expression().rowNumberOver().partitionBy(t.id()).orderBy(t.id())
                                    .orderByDescending(t.createTime()).asAnyType(Integer.class)
                    ))
                    .where(p -> {
                        p.entityTable().stars().gt(1);
                        p.partitionColumn1().gt(1);
                    }).toList();
        }catch (Exception ex){

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time`,t1.`__partition__column1` AS `__partition__column1` FROM (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,(ROW_NUMBER() OVER (PARTITION BY t.`id` ORDER BY t.`id` ASC, t.`create_time` DESC)) AS `__partition__column1` FROM `t_topic` t) t1 WHERE t1.`stars` > ? AND t1.`__partition__column1` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testPartitionBy1_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<Partition1<Topic, Integer>> list1 = easyEntityQuery.queryable(Topic.class)
                    .select(t -> Select.PARTITION.of(
                            t,
                            t.expression().rowNumberOver().partitionBy(t.id(),t.title()).orderBy(t.id())
                                    .orderByDescending(t.createTime()).asAnyType(Integer.class)
                    ))
                    .where(p -> {
                        p.entityTable().stars().gt(1);
                        p.partitionColumn1().gt(1);
                    }).toList();
        }catch (Exception ex){

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time`,t1.`__partition__column1` AS `__partition__column1` FROM (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,(ROW_NUMBER() OVER (PARTITION BY t.`id`, t.`title` ORDER BY t.`id` ASC, t.`create_time` DESC)) AS `__partition__column1` FROM `t_topic` t) t1 WHERE t1.`stars` > ? AND t1.`__partition__column1` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

//    @Test
//    public void testUppper1(){
//        List<Map<String, Object>> maps = easyEntityQuery.queryable(Topic.class)
//                .toMaps();
//        System.out.println(maps);
//    }

//    private EasyQueryClient buildClient(DataSource mydatasource,int type){
//        EasyQueryBuilderConfiguration easyQueryBuilderConfiguration = EasyQueryBootstrapper.defaultBuilderConfiguration()
//                .setDefaultDataSource(mydatasource)
//                .optionConfigure(op -> {
//                    op.setDeleteThrowError(false);
//                    op.setExecutorCorePoolSize(1);
//                    op.setExecutorMaximumPoolSize(2);
//                    op.setMaxShardingQueryLimit(1);
//                });
//        try(Connection connection = mydatasource.getConnection()){
//            Class<? extends Connection> aClass = connection.getClass();
//            if(PgConnection.class.isAssignableFrom(aClass)){
//                easyQueryBuilderConfiguration.useDatabaseConfigure(new PgSQLDatabaseConfiguration());
//            }
//            if(MysqlConnection.class.isAssignableFrom(aClass)){
//                easyQueryBuilderConfiguration.useDatabaseConfigure(new MySQLDatabaseConfiguration());
//            }
//        }catch (SQLException ex){
//            throw new RuntimeException(ex);
//        }
//        EasyQueryClient build = easyQueryBuilderConfiguration.build();
//        return build;
//    }

}
