package com.easy.query.test;

import com.bestvike.linq.Linq;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.api.pagination.DefaultPageResult;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.exception.EasyQueryResultSizeLimitException;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.partition.Partition1;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.entity.Blog2Entity;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicFile;
import com.easy.query.test.entity.TopicIdObject;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.SysUserProxy;
import com.easy.query.test.entity.proxy.TopicIdObjectProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.school.proxy.SchoolClassProxy;
import com.easy.query.test.entity.school.proxy.SchoolStudentProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.keytest.MyTestPrimaryKey;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.proxy.MyVOProxy;
import com.easy.query.test.selectato.TopicAutoFalse;
import com.easy.query.test.vo.BlogEntityVO1;
import com.easy.query.test.vo.BlogEntityVO2;
import com.easy.query.test.vo.TestUserAAA;
import com.easy.query.test.vo.proxy.BlogEntityVO1Proxy;
import lombok.Data;
import lombok.var;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/**
 * create time 2024/6/13 12:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest18 extends BaseTest {

//    public void existsQuery(BlogEntityProxy blogFilter) {
//        blogFilter.expression().exists(()->easyEntityQuery.queryable(Topic.class)
//                .where(t_topic -> {
//                    t_topic.title().like("123");
//                    t_topic.id().eq(blogFilter.id());
//                }));
//    }
//
//    public void test1xx(){
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b1 -> {
//                    existsQuery(b1);
//                })
//                .toList();
//    }

    @Test
    public void test1_0() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicIdObject> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> t.id().isNull())
                .select(t -> {
                    TopicIdObjectProxy r = new TopicIdObjectProxy();
                    r.id().set(t.id().nullOrDefault("1"));
                    return r;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL(t.`id`,?) AS `id` FROM `t_topic` t WHERE t.`id` IS NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

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
                    ColumnFunctionCompareComparableAnyChainExpression<String> nullProperty = o.expression().sqlSegment("IFNULL({0},'')", c -> {
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
        Assert.assertEquals("SELECT MAX(t.`topic_type`) FROM `t_topic_type` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
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
//                .groupBy((t1, b2) -> GroupKeys.of(
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
        } catch (Exception ex) {

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
                            t.expression().rowNumberOver().partitionBy(t.id(), t.title()).orderBy(t.id())
                                    .orderByDescending(t.createTime()).asAnyType(Integer.class)
                    ))
                    .where(p -> {
                        p.entityTable().stars().gt(1);
                        p.partitionColumn1().gt(1);
                    }).toList();
        } catch (Exception ex) {
            System.out.println(ex);
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

    @Test
    public void test() {

        List<Draft3<String, String, BigDecimal>> list2 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                .select((b1, t2) -> Select.DRAFT.of(
                        b1.id(),
                        t2.title(),
                        b1.score()
                )).distinct().toList();


        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                .select((b1, t2) -> b1.FETCHER.id().content().order().fetchProxy())
                .distinct().toList();

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.star().eq(1);
                })
                .select(b -> b.FETCHER.content().order().fetchProxy())
                .distinct()
                .toList();

//        List<TestBeanProperty> list = easyQuery.queryable(TestBeanProperty.class).toList();
//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    t.cUserName().eq("123");
//                }).toList();
//        String sql = easyEntityQuery.deletable(BlogEntity.class)
//                .useLogicDelete(true)
//                .where(b -> {
//                    b.star().eq(1);
//                }).toSQL();

        QueryConfiguration queryConfiguration = easyQuery.getRuntimeContext().getQueryConfiguration();
        Assert.assertTrue(queryConfiguration instanceof MyQueryConfiguration);
        System.out.println(queryConfiguration);
    }

    @Test
    public void test1xa() {

        BigDecimal bigDecimal = easyEntityQuery.queryable(Topic.class)
                .sumBigDecimalOrDefault(t -> t.stars().nullOrDefault(0), BigDecimal.ZERO);
        Integer bigDecimal1 = easyEntityQuery.queryable(Topic.class)
                .sumOrDefault(t -> t.stars().nullOrDefault(0), 0);
        System.out.println(bigDecimal);
    }
//    @Test
//    public void test2xa(){
//
//        BigDecimal bigDecimal = easyEntityQuery.queryable(Topic.class).asTableLink(tableName->tableName+" FINAL")
//                .sumBigDecimalOrDefault(t -> t.stars().nullOrDefault(0), BigDecimal.ZERO);
//    }


    @Test
    public void testStarStr() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Blog2Entity> list = easyEntityQuery.queryable(Blog2Entity.class)
                .where(b -> {
                    b.star().eq(1);
                    b.starStr().eq("1");
                })
                .orderBy(b -> {
                    b.star().asc();
                    b.starStr().asc();
                })
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`star`,CAST(`star` AS CHAR) AS `star_str` FROM `t_blog` WHERE `deleted` = ? AND `star` = ? AND CAST(`star` AS CHAR) = ? ORDER BY `star` ASC,CAST(`star` AS CHAR) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testUpdate() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.updatable(Topic.class)
                .setColumns(t -> {
                    t.stars().set(
                            t.expression().caseWhen(() -> {
                                t.stars().subtract(5).lt(BigDecimal.valueOf(0));
                            }).then(0).elseEnd(t.stars().subtract(5))
                    );

                }).where(t -> {
                    t.id().eq("123xxxa");
                }).executeRows();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = (CASE WHEN (`stars` - ?) < ? THEN ? ELSE (`stars` - ?) END) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("5(Integer),0(BigDecimal),0(Integer),5(Integer),123xxxa(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
//        DataSource defaultDataSource = easyEntityQuery.getRuntimeContext().getDataSourceManager().getDefaultDataSource();
//        Connection connection1 = defaultDataSource.getConnection();
//        easyEntityQuery.getEasyQueryClient().jdbc(connection->{
//
//        })
    }

    @Test
    public void easyQuery() {
        String s = easyEntityQuery.queryable(BlogEntity.class)
                .selectColumn(b -> b.createTime().format("yyyy年MM约-dd HH小时mm分钟ss秒"))
                .firstOrNull();
        System.out.println(s);

        String s1 = easyQuery.queryable(BlogEntity.class)
                .select(String.class, b -> {
                    b.sqlFunc(b.fx().dateTimeFormat(BlogEntity::getCreateTime, "yyyy年MM约-dd HH小时mm分钟ss秒"));
                }).firstOrNull();
        System.out.println(s1);
    }

    @Test
    public void easyQueryPrint1() {
        String s = easyEntityQuery.queryable(BlogEntity.class)
                .configure(c -> {
//                    c.setGroupSize();
                    c.setPrintSQL(false);
                })
                .selectColumn(b -> b.createTime().format("yyyy年MM约-dd HH小时mm分钟ss秒"))
                .firstOrNull();
        System.out.println(s);


        List<String> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(b -> {
                    return b.id();
                }).toList();
//
//        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
//                .select(b ->{
//                    return b.FETCHER.content().isTop();
//                    return  new BlogEntityProxy()
//                            .id().set(b.id());
//                }).toList();


    }

    @Test
    public void esss() {
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .includes(s -> s.blogs())
                .toList();
        EntityQueryable<SysUserProxy, SysUser> sysUserProxySysUserEntityQueryable = easyEntityQuery.queryable(SysUser.class).cloneQueryable();
        for (SysUser sysUser : list) {

            sysUser.setBlogs(
                    Linq.of(sysUser.getBlogs()).orderBy(o -> o.getScore()).toList()
            );
        }
    }

    @Test
    public void testFETCHER() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicFile> list = easyEntityQuery.queryable(TopicFile.class)
                .where(t -> {
                    t.id().eq("123");
                })
                .select(t -> t.FETCHER.id().stars())
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars` FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

//    @Test
//    public void testGroup() {
//
//        List<Draft2<String, Long>> list1 = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> b.createTime().le(LocalDateTime.now()))
//                .groupBy(b -> GroupKeys.of(
//                        b.createTime().nullOrDefault(LocalDateTime.now()).format("yyyy-MM-dd")
//                )).select(group -> Select.DRAFT.of(
//                        group.key1(),
//                        group.count()
//                )).toList();
//
//
//        List<Draft2<String, Long>> list2 = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> b.createTime().le(LocalDateTime.now()))
//                .groupBy(b -> GroupKeys.of(
//                        b.createTime().nullOrDefault(LocalDateTime.now()).format("yyyy-MM-dd")
//                )).select(group -> Select.DRAFT.of(
//                        group.groupTable().createTime().nullOrDefault(LocalDateTime.now()).format("yyyy-MM-dd"),
//                        group.count()
//                )).toList();
//    }

    @Test
    public void testSubString() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().subString(b.star(), b.title().length().subtract(5)).eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND SUBSTR(`id`,`star`+1,(CHAR_LENGTH(`title`) - ?)) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),5(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void test1111() {
//        TopicTypeEnum topicTypeEnum = easyEntityQuery.queryable(TopicTypeTest1.class).select(t -> t.topicType()).firstOrNull();


        List<MyVO> list = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                .where((b1, t2) -> b1.star().eq(1))
                .groupBy((b1, t2) -> GroupKeys.of(b1.title(), b1.content()))
                .select(group -> {
                    MyVOProxy r = new MyVOProxy();
                    r.title().set(group.key1());
                    r.content().set(group.key2());
                    r.c().set(group.groupTable().t2.id().count());
                    return r;
                }).toList();
//
//        List<VO> list = easyEntityQuery.queryable(BlogEntity.class)
//                .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
//                .where((b1, t2) -> b1.star().eq(1))
//                .groupBy((b1, t2) -> GroupKeys.of(b1.title(), b1.content()))
//                .select(VO.class, group -> Select.of(
//                        group.key1().as(VO::getTitle),
//                        group.key2().as(VO::getContent),
//                        group.groupTable().t2.id().count().as(VO::getC)
//                )).toList();

    }

    @Test
    public void autoFalse() {
        List<TopicAutoFalse> list = easyQuery.queryable(Topic.class)
                .select(TopicAutoFalse.class, t -> t.columnAll())
                .toList();
    }


    @Data
    public static class VO {
        private String title;
        private String content;
        private Long c;
    }

    @Test
    public void testEnum() {
        TopicTypeEnum topicTypeEnum = easyEntityQuery.queryable(TopicTypeTest1.class).select(t -> t.topicType()).firstOrNull();
        System.out.println(topicTypeEnum);
    }

    @Test
    public void testClient() {
        try {
            List<Map> list = easyQueryClient.queryable(Map.class)
                    .asTable("t_test")
                    .where(m -> m.eq("name", "123"))
                    .groupBy(m ->
                            m.column("age")
                                    .sqlNativeSegment("IFNULL({0},{1})", c -> c.columnName("city").value("杭州"))
                    ).select(Map.class, m -> m.groupKeysAs(0, "ageKey").groupKeysAs(1, "addressKey").columnCount("id"))
                    .toList();
        } catch (Exception ignored) {

        }
        try {

            easyQueryClient.updatable(Map.class)
                    .asTable("t_delete")
                    .set("age", 1)
                    .where(m -> m.eq("id", "4567"))
                    .executeRows();
        } catch (Exception ignored) {

        }

    }

    @Test
    public void testSubCountPage() {

        EntityQueryable<SysUserProxy, SysUser> q = easyEntityQuery.queryable(SysUser.class)
                .where(s -> s.phone().likeMatchLeft("133"));
//        long count = q.cloneQueryable().count();
//
//        List<Draft3<String, String, Long>> list = q.cloneQueryable()
//                .limit(2, 10)
//                .select(s -> s)
//                .select(s -> Select.DRAFT.of(s.id(), s.phone(), s.blogs().count()))
//                .toList();

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            long count = q.cloneQueryable().count();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT COUNT(*) FROM `easy-query-test`.`t_sys_user` WHERE `phone` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3wApxVPL9GhKXR9/YKKGBg==%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Draft3<String, String, Long>> list = q.cloneQueryable()
                    .limitSelect(2, 10, s -> Select.DRAFT.of(s.id(), s.phone(), s.blogs().count()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t1.`id` AS `value1`,t1.`phone` AS `value2`,(SELECT COUNT(*) FROM `t_blog` t3 WHERE t3.`deleted` = ? AND t3.`title` = t1.`id`) AS `value3` FROM (SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE t.`phone` LIKE ? LIMIT 10 OFFSET 2) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t1.`id` AS `value1`,t1.`phone` AS `value2`,(SELECT COUNT(*) FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`title` = t1.`id`) AS `value3` FROM (SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE t.`phone` LIKE ? LIMIT 10 OFFSET 2) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),3wApxVPL9GhKXR9/YKKGBg==%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testSubCountLimitSelect1() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Draft3<String, String, Long>> list = easyEntityQuery.queryable(SysUser.class)
                    .where(s -> s.phone().ne("123"))
                    .orderBy(s -> s.createTime().desc())
                    .limitSelect(20, 10, s -> Select.DRAFT.of(s.id(), s.phone(), s.blogs().count()))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t1.`id` AS `value1`,t1.`phone` AS `value2`,(SELECT COUNT(*) FROM `t_blog` t3 WHERE t3.`deleted` = ? AND t3.`title` = t1.`id`) AS `value3` FROM (SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE t.`phone` <> ? ORDER BY t.`create_time` DESC LIMIT 10 OFFSET 20) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t1.`id` AS `value1`,t1.`phone` AS `value2`,(SELECT COUNT(*) FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`title` = t1.`id`) AS `value3` FROM (SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE t.`phone` <> ? ORDER BY t.`create_time` DESC LIMIT 10 OFFSET 20) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),L/vVSy7H9DYkzz3srmSVCQ==(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testSubCountPage1() {


        {

            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);


            EasyPageResult<Draft3<String, String, Long>> pageResult = easyEntityQuery.queryable(SysUser.class)
                    .where(s -> s.phone().ne("123"))
                    .orderBy(s -> s.createTime().desc())
                    .toPageSelectResult(q -> {
                        return q.select(s -> Select.DRAFT.of(s.id(), s.phone(), s.blogs().count()));
                    }, 2, 10);

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT COUNT(*) FROM `easy-query-test`.`t_sys_user` WHERE `phone` <> ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("L/vVSy7H9DYkzz3srmSVCQ==(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            }
            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                Assert.assertEquals("SELECT t1.`id` AS `value1`,t1.`phone` AS `value2`,(SELECT COUNT(*) FROM `t_blog` t3 WHERE t3.`deleted` = ? AND t3.`title` = t1.`id`) AS `value3` FROM (SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE t.`phone` <> ? ORDER BY t.`create_time` DESC LIMIT 10 OFFSET 10) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("SELECT t1.`id` AS `value1`,t1.`phone` AS `value2`,(SELECT COUNT(*) FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`title` = t1.`id`) AS `value3` FROM (SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE t.`phone` <> ? ORDER BY t.`create_time` DESC LIMIT 10 OFFSET 10) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals("SELECT t.`id` AS `value1`,t.`phone` AS `value2`,(SELECT COUNT(*) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`title` = t.`id`) AS `value3` FROM `easy-query-test`.`t_sys_user` t WHERE t.`phone` <> ? ORDER BY t.`create_time` DESC LIMIT 10 OFFSET 10", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("false(Boolean),L/vVSy7H9DYkzz3srmSVCQ==(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            }
            listenerContextManager.clear();
        }
    }

    @Test
    public void testCount() {
        long count = easyEntityQuery.queryable(BlogEntity.class).select(t -> Select.DRAFT.of(t.id())).distinct().count();
        Assert.assertEquals(100, count);
        int counti = easyEntityQuery.queryable(BlogEntity.class).select(t -> Select.DRAFT.of(t.id())).distinct().intCount();
        Assert.assertEquals(100, counti);
        boolean e = easyEntityQuery.queryable(BlogEntity.class).select(t -> Select.DRAFT.of(t.id())).distinct().any();
        Assert.assertTrue(e);
    }

    @Test
    public void andTest() {
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.star().le(1);
                    b.title().like("123");
                }).toList();

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.and(() -> {
                        b.star().le(1);
                        b.title().like("123");
                    });
                }).toList();


    }

    @Test
    public void Sum() {

        List<Draft2<String, Number>> monthWithScore = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(BlogEntity.class, (b, b2) -> b.createTime().lt(b2.createTime()))
                .groupBy((b1, b2) -> GroupKeys.of(b1.createTime().format("yyyyMMdd")))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.groupTable().t2.score().sum()
                )).toList();
 
    }

    @Test
    public void Sum1() {
        var monthQuery = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(b -> GroupKeys.of(b.createTime().format("yyyyMM").toNumber(Integer.class)))
                .select(group -> Select.DRAFT.of(group.key1(), group.groupTable().score().sumBigDecimal()));
        List<Draft2<Integer, Number>> list = monthQuery.cloneQueryable()
                .leftJoin(monthQuery.cloneQueryable(), (b, b2) -> b.value1().lt(b2.value1()))
                .groupBy((b1, b2) -> GroupKeys.of(b1.value1()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.groupTable().t2.value2().sum()
                )).toList();

    }

    @Test
    public void rangeTest1() {
        LocalDateTime time1 = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime time2 = LocalDateTime.of(2022, 1, 1, 1, 1);


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().nullOrDefault(time1).rangeClosed(time1, time2);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND IFNULL(`create_time`,?) BETWEEN ? AND ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2021-01-01T01:01(LocalDateTime),2021-01-01T01:01(LocalDateTime),2022-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().rangeClosed(time1, time2);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`create_time` >= ? AND `create_time` <= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2021-01-01T01:01(LocalDateTime),2022-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().rangeClosed(b.updateTime(), b.updateTime().nullOrDefault(LocalDateTime.of(2023, 1, 1, 1, 1)));
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`create_time` >= `update_time` AND  `create_time` <= IFNULL(`update_time`,?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2023-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list2 = easyEntityQuery.queryable(BlogEntity.class)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().rangeClosed(b.updateTime(), b.updateTime().nullOrDefault(time1));
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`create_time` >= `update_time` AND  `create_time` <= IFNULL(`update_time`,?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list3 = easyEntityQuery.queryable(BlogEntity.class)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.expression().constant().valueOf(time1).rangeClosed(b.createTime(), b.updateTime());
                        b.expression().constant().valueOf(time1).rangeClosed(b.createTime(), b.updateTime().nullOrDefault(time2));
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (? >= `create_time` AND ? <= `update_time`) AND (? >= `create_time` AND ? <= IFNULL(`update_time`,?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2021-01-01T01:01(LocalDateTime),2021-01-01T01:01(LocalDateTime),2021-01-01T01:01(LocalDateTime),2021-01-01T01:01(LocalDateTime),2022-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }


    @Test
    public void rangeTest2() {
        LocalDateTime time1 = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime time2 = LocalDateTime.of(2022, 1, 1, 1, 1);


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list3 = easyEntityQuery.queryable(BlogEntity.class)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.or(() -> {

                            b.expression().constant().valueOf(time1).rangeClosed(b.createTime(), b.updateTime());
                            b.expression().constant().valueOf(time1).rangeClosed(b.createTime(), b.updateTime().nullOrDefault(time2));
                        });
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND ((? >= `create_time` AND ? <= `update_time`) OR (? >= `create_time` AND ? <= IFNULL(`update_time`,?)))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2021-01-01T01:01(LocalDateTime),2021-01-01T01:01(LocalDateTime),2021-01-01T01:01(LocalDateTime),2021-01-01T01:01(LocalDateTime),2022-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }


    @Test
    public void rangeTest3() {

        LocalDateTime time1 = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime time2 = LocalDateTime.of(2022, 1, 1, 1, 1);


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().nullOrDefault(time1).rangeClosed(null, time2);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND IFNULL(`create_time`,?) <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2021-01-01T01:01(LocalDateTime),2022-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().nullOrDefault(time1).rangeOpenClosed(null, time2);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND IFNULL(`create_time`,?) <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2021-01-01T01:01(LocalDateTime),2022-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().rangeClosed(time1, null);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `create_time` >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2021-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().rangeClosed((LocalDateTime) null, null);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().rangeOpen((LocalDateTime) null, null);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().rangeOpenClosed((LocalDateTime) null, null);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().rangeClosedOpen((LocalDateTime) null, null);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(b -> {
//                    b.id().eq(b.id().nullOrDefault("1"));
                        b.createTime().rangeClosed((LocalDateTime) null, (LocalDateTime) null);
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testFinal() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .asTableSegment(ClickHouseTableFinal.DEFAULT)
                    .asAlias("a")
                    .where(b -> {
                        b.star().eq(123);
                    }).toList();
        } catch (Exception e) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT a.`id`,a.`create_time`,a.`update_time`,a.`create_by`,a.`update_by`,a.`deleted`,a.`title`,a.`content`,a.`url`,a.`star`,a.`publish_time`,a.`score`,a.`status`,a.`order`,a.`is_top`,a.`top` FROM `t_blog` a FINAL WHERE a.`deleted` = ? AND a.`star` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    public static class ClickHouseTableFinal implements BiFunction<String, String, String> {
        public static final ClickHouseTableFinal DEFAULT = new ClickHouseTableFinal();

        @Override
        public String apply(String table, String alias) {
            if (alias == null) {
                return table + " FINAL";
            }
            return table + " " + alias + " FINAL";
        }
    }

}
