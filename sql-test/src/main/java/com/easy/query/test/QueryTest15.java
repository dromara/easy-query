package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.formater.MyBatisSQLParameterPrintFormat;
import com.easy.query.core.basic.extension.formater.SQLParameterPrintFormat;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.dialect.DefaultDialect;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.scec.expression.FormatValueParamExpressionImpl;
import com.easy.query.core.extension.casewhen.CaseWhenBuilderExpression;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLConstantExpression;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.dto.SchoolClassAggregateDTO;
import com.easy.query.test.dto.UserExtraDTO;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.UserExtra;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.school.SchoolClassAggregate;
import com.easy.query.test.entity.school.SchoolClassAggregateProp;
import com.easy.query.test.entity.school.SchoolClassAggregatePropVO;
import com.easy.query.test.entity.school.proxy.SchoolClassAggregatePropProxy;
import com.easy.query.test.listener.ListenerContext;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2024/3/8 11:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest15 extends BaseTest {

    @Test
    public void test1() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<UserExtra> list = easyEntityQuery.queryable(UserExtra.class)
                    .where(u -> {
                        u.fullName().like("123");
                        u.fullName().in(Arrays.asList("1", "2"));
                        u.age().gt(12);
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`first_name`,`last_name`,`birthday`,CONCAT(`first_name`,`last_name`) AS `full_name`,CEILING((timestampdiff(DAY, `birthday`, NOW()) / ?)) AS `age` FROM `t_user_extra` WHERE CONCAT(`first_name`,`last_name`) LIKE ? AND CONCAT(`first_name`,`last_name`) IN (?,?) AND CEILING((timestampdiff(DAY, `birthday`, NOW()) / ?)) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("365(Integer),%123%(String),1(String),2(String),365(Integer),12(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<UserExtraDTO> list = easyEntityQuery.queryable(UserExtra.class)
                    .where(u -> {
                        u.fullName().like("123");
                        u.fullName().in(Arrays.asList("1", "2"));
                        u.age().gt(12);
                    })
                    .select(UserExtraDTO.class)
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`first_name`,t.`last_name`,t.`birthday`,CONCAT(t.`first_name`,t.`last_name`) AS `full_name`,CEILING((timestampdiff(DAY, t.`birthday`, NOW()) / ?)) AS `age` FROM `t_user_extra` t WHERE CONCAT(t.`first_name`,t.`last_name`) LIKE ? AND CONCAT(t.`first_name`,t.`last_name`) IN (?,?) AND CEILING((timestampdiff(DAY, t.`birthday`, NOW()) / ?)) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("365(Integer),%123%(String),1(String),2(String),365(Integer),12(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<SchoolClassAggregateDTO> list = easyEntityQuery.queryable(SchoolClassAggregate.class)
                    .where(u -> {
                        u.name().like("123");
                    })
                    .select(SchoolClassAggregateDTO.class)
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name`,(SELECT COUNT(t2.`id`) AS `id` FROM `school_student` t2 WHERE t2.`class_id` = t.`id`) AS `student_size` FROM `school_class` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test2() {

        try {
            easyEntityQuery.deletable(UserExtra.class).disableLogicDelete().allowDeleteStatement(true)
                    .whereById("test2")
                    .executeRows();
            UserExtra userExtra = new UserExtra();
            userExtra.setId("test2");
            userExtra.setFirstName("孙");
            userExtra.setLastName("悟空");
            userExtra.setBirthday(LocalDateTime.of(2020, 1, 1, 0, 0));
            easyEntityQuery.insertable(userExtra).executeRows();

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<UserExtra> list = easyEntityQuery.queryable(UserExtra.class)
                    .where(u -> {
                        u.id().eq("test2");
                        u.fullName().like("悟");
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`first_name`,`last_name`,`birthday`,CONCAT(`first_name`,`last_name`) AS `full_name`,CEILING((timestampdiff(DAY, `birthday`, NOW()) / ?)) AS `age` FROM `t_user_extra` WHERE `id` = ? AND CONCAT(`first_name`,`last_name`) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("365(Integer),test2(String),%悟%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            Assert.assertEquals(list.size(), 1);
            UserExtra userExtra1 = list.get(0);
            Assert.assertEquals("孙悟空", userExtra1.getFullName());
            Assert.assertTrue(userExtra1.getAge() > 4);
            listenerContextManager.clear();
        } finally {

            easyEntityQuery.deletable(UserExtra.class).disableLogicDelete().allowDeleteStatement(true)
                    .whereById("test2")
                    .executeRows();
        }

        List<UserExtra> list = easyEntityQuery.queryable(UserExtra.class)
                .where(u -> {
                    u.id().eq("test2");
                    u.fullName().like("悟");
                }).orderBy(x -> x.fullName().asc())
                .toList();
    }

    @Test
    public void test3() {

        try {
            easyEntityQuery.deletable(UserExtra.class).disableLogicDelete().allowDeleteStatement(true)
                    .whereById("test3")
                    .executeRows();
            UserExtra userExtra = new UserExtra();
            userExtra.setId("test3");
            userExtra.setFirstName("孙");
            userExtra.setLastName("悟空");
            userExtra.setBirthday(LocalDateTime.of(2020, 1, 1, 0, 0));
            easyEntityQuery.insertable(userExtra).executeRows();

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<UserExtra> list = easyEntityQuery.queryable(UserExtra.class)
                    .where(u -> {
                        u.id().eq("test3");
                        u.fullName().like("悟");
                    }).orderBy(x -> {
                        x.fullName().asc();
                        x.age().asc();
                        x.fullName().asc(OrderByModeEnum.NULLS_LAST);
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`first_name`,`last_name`,`birthday`,CONCAT(`first_name`,`last_name`) AS `full_name`,CEILING((timestampdiff(DAY, `birthday`, NOW()) / ?)) AS `age` FROM `t_user_extra` WHERE `id` = ? AND CONCAT(`first_name`,`last_name`) LIKE ? ORDER BY CONCAT(`first_name`,`last_name`) ASC,CEILING((timestampdiff(DAY, `birthday`, NOW()) / ?)) ASC,CASE WHEN CONCAT(`first_name`,`last_name`) IS NULL THEN 1 ELSE 0 END ASC,CONCAT(`first_name`,`last_name`) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("365(Integer),test3(String),%悟%(String),365(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            Assert.assertEquals(list.size(), 1);
            UserExtra userExtra1 = list.get(0);
            Assert.assertEquals("孙悟空", userExtra1.getFullName());
            Assert.assertTrue(userExtra1.getAge() > 4);
            listenerContextManager.clear();
        } finally {

            easyEntityQuery.deletable(UserExtra.class).disableLogicDelete().allowDeleteStatement(true)
                    .whereById("test3")
                    .executeRows();
        }
    }


    @Test
    public void test4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<Integer, String, String>> list = easyEntityQuery.queryable(UserExtra.class)
                .where(u -> {
                    u.id().eq("test3");
                    u.fullName().like("悟");
                }).groupBy(u -> GroupKeys.TABLE1.of(u.age(), u.fullName()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.key2(),
                        group.groupTable().fullName().max()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT CEILING((timestampdiff(DAY, t.`birthday`, NOW()) / ?)) AS `value1`,CONCAT(t.`first_name`,t.`last_name`) AS `value2`,MAX(CONCAT(t.`first_name`,t.`last_name`)) AS `value3` FROM `t_user_extra` t WHERE t.`id` = ? AND CONCAT(t.`first_name`,t.`last_name`) LIKE ? GROUP BY CEILING((timestampdiff(DAY, t.`birthday`, NOW()) / ?)),CONCAT(t.`first_name`,t.`last_name`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("365(Integer),test3(String),%悟%(String),365(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        listenerContextManager.clear();
    }

    @Test
    public void test5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<UserExtra> list1 = easyEntityQuery.queryable(UserExtra.class)
                .leftJoin(BlogEntity.class, (u, b2) -> u.fullName().eq(b2.id()))
                .where((u1, b2) -> u1.fullName().eq("123"))
                .orderBy((u1, b2) -> u1.fullName().asc())
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`first_name`,t.`last_name`,t.`birthday`,CONCAT(t.`first_name`,t.`last_name`) AS `full_name`,CEILING((timestampdiff(DAY, t.`birthday`, NOW()) / ?)) AS `age` FROM `t_user_extra` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND CONCAT(t.`first_name`,t.`last_name`) = t1.`id` WHERE CONCAT(t.`first_name`,t.`last_name`) = ? ORDER BY CONCAT(t.`first_name`,t.`last_name`) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("365(Integer),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        listenerContextManager.clear();
    }

    @Test
    public void test6() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(UserExtra.class, (u, b2) -> u.id().eq(b2.fullName()))
                .where((u1, b2) -> b2.fullName().eq("123"))
                .orderBy((u1, b2) -> b2.fullName().asc())
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_user_extra` t1 ON t.`id` = CONCAT(t1.`first_name`,t1.`last_name`) WHERE t.`deleted` = ? AND CONCAT(t1.`first_name`,t1.`last_name`) = ? ORDER BY CONCAT(t1.`first_name`,t1.`last_name`) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        listenerContextManager.clear();
    }

    @Test
    public void test7() {
        LocalDateTime of = LocalDateTime.of(2020, 1, 2, 3, 4);
//        EntityQueryable<TopicProxy, Topic> queryable = easyEntityQuery.queryable(Topic.class);
//        ClientQueryable<Topic> clientQueryable = queryable.getClientQueryable();
//        clientQueryable.where(x->{
//            x.eq("createTime",of);
//        });
//        List<Topic> list1 = queryable.toList();

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    Filter filter = t.getEntitySQLContext().getFilter();
                    filter.eq(t.getTable(), "createTime", of);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-01-02T03:04(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        listenerContextManager.clear();
    }

    @Test
    public void test8() {
        EasyQueryClient queryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(new MysqlDataSource())
                .optionConfigure(op -> {
                    op.setPrintSql(false);
                    op.setKeepNativeStyle(true);
                })
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .build();
        MapQueryable mapQuery_film = queryClient.mapQueryable().asTable("film").cloneQueryable();
        MapQueryable mapQueryable_film = mapQuery_film.cloneQueryable();
        MapQueryable mapQueryable_language = queryClient.mapQueryable().asTable("language")
                .select(f -> {
                    ColumnAsSelector<?, ?> tableColumns = f.getAsSelector(0);
                    tableColumns.columnAs("language_id", "language_id");
                    tableColumns.columnAs("name", "name");
                });
        MapQueryable mapQueryable_filmText = queryClient.mapQueryable().asTable("film_Text").select(f ->
        {
            ColumnAsSelector<?, ?> table = f.getAsSelector(0);
            table.columnAs("film_id", "film_id");
            table.columnAs("text", "text");
        });
        MapQueryable mapquery1 = mapQueryable_film.join(MultiTableTypeEnum.LEFT_JOIN, mapQueryable_language, f -> {
            EntitySQLTableOwner<?> table_language = f.getTableOwner(1);
            WherePredicate<?> table_film_where = f.getWherePredicate(0);
            table_film_where.eq(table_language, "language_id", "language_id");
        }).select(f -> {
            ColumnAsSelector<?, ?> columnAsSelector_left = f.getAsSelector(0);
            ColumnAsSelector<?, ?> columnAsSelector_right = f.getAsSelector(1);
            columnAsSelector_left.columnAs("film_id", "film_id");
            columnAsSelector_left.columnAs("title", "title");
            columnAsSelector_right.columnAs("name", "name");
        }).asAlias("query1");
        MapQueryable mapquery2 = mapQuery_film.join(MultiTableTypeEnum.LEFT_JOIN, mapQueryable_language, f -> {
            EntitySQLTableOwner<?> filmtTable = f.getTableOwner(1);
            WherePredicate<?> where = f.getWherePredicate(0);
            where.eq(filmtTable, "film_id", "film_id");
        }).select(f -> {
            ColumnAsSelector<?, ?> asSelector = f.getAsSelector(0);
            asSelector
                    .columnAs("film_id", "film_id")
                    .columnAs("text", "text");
            SQLFunc fx = asSelector.fx();
//            SQLFunction concat = fx.concat(x->{
//                x.column("id");
//                x.format("','");
//                x.column("name");
//
//                SQLFunction concat1 = fx.concat("id", "name");
//
//
//                x.sqlFunc(concat1);
//            });


            CaseWhenBuilderExpression caseWhenBuilderExpression = new CaseWhenBuilderExpression(asSelector.getRuntimeContext(), asSelector.getExpressionContext());
            SQLFunction sqlFunction = caseWhenBuilderExpression.caseWhen(x -> {
                x.sqlNativeSegment("{0} = {1}", c -> {
                    c.expression(asSelector.getTable(), "id");
                    c.format("'1'");
                });
            }, new FormatValueParamExpressionImpl("1")).elseEnd(new FormatValueParamExpressionImpl("NULL"));
            DistinctDefaultSQLFunction sum = fx.sum(sqlFunction);


            asSelector.sqlFunc(sum);
        });//.asAlias("query2");
        mapquery1.join(MultiTableTypeEnum.LEFT_JOIN, mapquery2, f -> {
            EntitySQLTableOwner<?> rightTable = f.getTableOwner(1);
            WherePredicate<?> where = f.getWherePredicate(0);
            where.eq(rightTable, "film_id", "film_id");
        }).asAlias("query2");
        String sql123 = mapquery1.toSQL();
        System.out.println(sql123);

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.expression().sql("{0} in ({1})", c -> c.expression(t.id()).collection(Arrays.asList("1", "2")));
                }).toList();

        Query<Number> numberQuery = easyEntityQuery.queryable(Topic.class)
                .selectColumn(t -> t.stars().sum());


    }

    @Test
    public void testxxx() {
        {
            String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(t -> {
                        SQLConstantExpression constant = t.expression().constant();
                        t.createTime().lt(
                                constant.valueOf(format).toDateTime(LocalDateTime.class).plus(1, TimeUnit.DAYS)
                        );
                    }).toList();
        }

//        List<Draft1<String>> list = easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    t.createTime().lt(LocalDateTime.now().plusDays(-7));
//                })
//                .select(t -> {
//
//                    Expression expression = t.expression();
//                    SQLConstantExpression constant = expression.constant();
//
//                    return Select.DRAFT.of(
////                            constant.valueOf(1).devide(constant.valueOf(2))
//                            t.createTime().plus(1, TimeUnit.DAYS).format("yyyy-MM")
//                    );
//                }).toList();

//        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
//                .includes(s -> s.schoolStudents(), x -> {
//                    x.leftJoin(Topic.class, (s, t2) -> s.id().eq(t2.id()))
//                            .select(SchoolStudentVO.class, (s1, t2) -> Select.of(
//                                    s1.FETCHER.allFields(),
//                                    t2.FETCHER.id().as(SchoolStudentVO::getName)
//                            ));
//                })
//                .select(o->new SchoolClassProxy().adapter(r->{
//                    r.selectAll(o);
//                    r.schoolStudents().set(o.schoolStudents(),x->new );
//                }))
//                .toList();


        Query<Integer> integerQuery = easyEntityQuery.queryable(Topic.class)
                .select(t -> Select.DRAFT.of(t.stars()))
                .selectSum(i -> i.value1());

        System.out.println(1);
    }

    @Test
    public void testCaseWhenLazy() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft1<Object>> list = easyEntityQuery.queryable(Topic.class)
                .select(t -> Select.DRAFT.of(
                        t.expression().caseWhen(() -> t.title().eq("123")).then("1").elseEnd("2")
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? THEN ? ELSE ? END) AS `value1` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        listenerContextManager.clear();
    }

    @Test
    public void testCaseWhenLazy2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .select(t -> {

                    TopicProxy topicProxy = new TopicProxy();
                    topicProxy.title().set(
                            t.expression().caseWhen(() -> t.title().eq("123")).then("1").elseEnd("2")
                    );
                    return topicProxy;
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? THEN ? ELSE ? END) AS `title` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        listenerContextManager.clear();
    }

    @Test
    public void testCaseSubAggregate() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<SchoolClassAggregateProp> list = easyEntityQuery.queryable(SchoolClassAggregateProp.class).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(String),1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<SchoolClassAggregateProp> list = easyEntityQuery.queryable(SchoolClassAggregateProp.class).fetchBy(s -> s.FETCHER.allFields()).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(String),1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<SchoolClassAggregateProp> list = easyEntityQuery.queryable(SchoolClassAggregateProp.class).fetchBy(s -> s.FETCHER.allFields().studentSize()).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name`,(SELECT COUNT(t2.`id`) AS `id` FROM `school_student` t2 WHERE t2.`class_id` = t.`id`) AS `student_size` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(String),1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            //如果vo是当前对象那么selectAll不会映射autoSelect=false的属性
            List<SchoolClassAggregateProp> list = easyEntityQuery.queryable(SchoolClassAggregateProp.class).
                    select(o -> new SchoolClassAggregatePropProxy().adapter(r -> {
                        r.selectAll(o);
                    })).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(String),1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            //如果vo是当前对象那么selectAll不会映射autoSelect=false的属性
            List<SchoolClassAggregatePropVO> list = easyEntityQuery.queryable(SchoolClassAggregateProp.class).
                    select(SchoolClassAggregatePropVO.class).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name`,(SELECT COUNT(t2.`id`) AS `id` FROM `school_student` t2 WHERE t2.`class_id` = t.`id`) AS `student_size` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(String),1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            //如果vo是当前对象那么selectAll不会映射autoSelect=false的属性
            List<SchoolClassAggregatePropVO> list = easyEntityQuery.queryable(SchoolClassAggregateProp.class)
                    .where(s -> s.studentSize().gt(100L)).
                    select(SchoolClassAggregatePropVO.class).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name`,(SELECT COUNT(t2.`id`) AS `id` FROM `school_student` t2 WHERE t2.`class_id` = t.`id`) AS `student_size` FROM `school_class` t WHERE (SELECT COUNT(t4.`id`) AS `id` FROM `school_student` t4 WHERE t4.`class_id` = t.`id`) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("100(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            listenerContextManager.clear();
        }
    }

    @Test
    public void test1223() {
        String name = easyQueryClient.mapQueryable().asTable("123")
                .where(x -> {
                    WherePredicate<?> wherePredicate = x.getWherePredicate(0);
                    wherePredicate.eq("name", "123");
                }).toSQL();
        Assert.assertEquals("SELECT * FROM `123` WHERE `name` = ?", name);
    }

    @Test
    public void test12234() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);

        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(0);
                    op.setMaxShardingQueryLimit(10);
                    op.setShardingOption(easyQueryShardingOption);
                    op.setDefaultDataSourceName("ds2020");
                    op.setThrowIfRouteNotMatch(false);
                    op.setMaxShardingRouteCount(512);
                    op.setDefaultDataSourceMergePoolSize(20);
                    op.setStartTimeJob(true);
                    op.setReverseOffsetThreshold(10);
                })
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .replaceService(Dialect.class, DefaultDialect.class)
                .replaceService(SQLParameterPrintFormat.class, MyBatisSQLParameterPrintFormat.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        DefaultEasyQuery easyQuery = new DefaultEasyQuery(easyQueryClient);
        List<Topic> list = easyQuery.queryable(Topic.class)

                .where(t -> t.eq(Topic::getId, 1))
                .where(t -> t.eq(Topic::getId, 1))
                .toList();
        System.out.println(list);
    }

    @Test
    public void test122345() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);

        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(0);
                    op.setMaxShardingQueryLimit(10);
                    op.setShardingOption(easyQueryShardingOption);
                    op.setDefaultDataSourceName("ds2020");
                    op.setThrowIfRouteNotMatch(false);
                    op.setMaxShardingRouteCount(512);
                    op.setDefaultDataSourceMergePoolSize(20);
                    op.setStartTimeJob(true);
                    op.setReverseOffsetThreshold(10);
                })
                .useDatabaseConfigure(new KingbaseESDatabaseConfiguration())
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);


        String sql = defaultEasyEntityQuery.queryable(Topic.class)
                .where(m -> {
                    m.createTime().format("yyyy年MM月dd日").eq("2022年01月01日");
                }).toSQL();
        Assert.assertEquals("SELECT \"id\",\"stars\",\"title\",\"create_time\" FROM \"t_topic\" WHERE to_char((\"create_time\")::TIMESTAMP,'YYYY年MM月DD日') = ?", sql);
    }


    @Test
    public void test123() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> aa = easyEntityQuery.queryable(Topic.class)
                .where(m -> {
                    m.createTime().format("yyyy年MM月dd日").eq("2022年01月01日");
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE DATE_FORMAT(`create_time`,'%Y年%m月%d日') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2022年01月01日(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testOrderCase() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> t.id().eq("123"))
                .orderBy(t -> {
                    Expression expression = t.expression();
                    expression.caseWhen(() -> t.stars().ge(1)).then(2)
                            .caseWhen(() -> t.stars().le(2)).then(3)
                            .elseEnd(4)
                            .asc();
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? ORDER BY (CASE WHEN `stars` >= ? THEN ? WHEN `stars` <= ? THEN ? ELSE ? END) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),2(Integer),2(Integer),3(Integer),4(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

//    @Test
//    public void test1234(){
//
//
//        List<Topic> list1 = easyQueryClient.queryable(Topic.class)
//                .where(t -> t.like("title", "123"))
//                .orderByAsc(t -> {
//                    t.sqlNativeSegment("CASE WHEN {0} THEN 1 WHEN {1} THEN 2 ELSE 4 END ", c -> {
//                        c.expression("title")
//                                .expression("stars");
//                    });
//                }).toList();
//
//        List<Map<String, Object>> list12 = easyQueryClient.queryable("t_table")
//                .where(m -> {
//                    m.eq("id", "123");
//                    m.like("name", "456");
//                }).toList();
//
//    }


    @Test
    public void testa() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, Long>> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    Expression expression = t.expression();
                    t.id().eq("6261");
                    t.createTime().format("yyyy-MM").ge(expression.now().plusMonths(-3).format("yyyy-MM"));
                    t.createTime().format("yyyy-MM").le(expression.now().plusMonths(-1).format("yyyy-MM"));

                }).groupBy(t -> GroupKeys.TABLE1.of(t.createTime().format("yyyy-MM")))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.count()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT DATE_FORMAT(t.`create_time`,'%Y-%m') AS `value1`,COUNT(*) AS `value2` FROM `t_topic` t WHERE t.`id` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m') >= DATE_FORMAT(date_add(NOW(), interval (?) month),'%Y-%m') AND DATE_FORMAT(t.`create_time`,'%Y-%m') <= DATE_FORMAT(date_add(NOW(), interval (?) month),'%Y-%m') GROUP BY DATE_FORMAT(t.`create_time`,'%Y-%m')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("6261(String),-3(Integer),-1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


//        List<TopicTypeVO> list1 = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
//                .groupBy((t1, b2) -> GroupKeys.TABLE2.of(t1.id()))
//                .select(group -> {
//                    TopicTypeVOProxy r = new TopicTypeVOProxy();
//                    r.id().set(group.key1());
//                    r.createTime().set(group.groupTable().t1.createTime().max());
//                    r.stars().set(group.groupTable().t1.stars().max());
//                    return r;
//                })
//                .where(t -> {
//                    t.createTime().gt(LocalDateTime.now());
//                }).toList();
//
//        List<TopicTypeVO> list2 = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
//                .groupBy((t1, b2) -> GroupKeys.TABLE2.of(t1.id()))
//                .select(group ->new TopicTypeVOProxy(){{
//                    id().set(group.key1());
//                    createTime().set(group.groupTable().t1.createTime().max());
//                    stars().set(group.groupTable().t1.stars().max());
//                }})
//                .where(t -> {
//                    t.createTime().gt(LocalDateTime.now());
//                }).toList();
    }
    @Test
    public void testColumn(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<LocalDateTime> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    Expression expression = t.expression();
                    t.id().eq("6261");
                    t.createTime().format("yyyy-MM").ge(expression.now().plusMonths(-3).format("yyyy-MM"));
                    t.createTime().format("yyyy-MM").le(expression.now().plusMonths(-1).format("yyyy-MM"));

                }).select(t -> t.createTime()).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` FROM `t_topic` t WHERE t.`id` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m') >= DATE_FORMAT(date_add(NOW(), interval (?) month),'%Y-%m') AND DATE_FORMAT(t.`create_time`,'%Y-%m') <= DATE_FORMAT(date_add(NOW(), interval (?) month),'%Y-%m')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("6261(String),-3(Integer),-1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testColumn1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<LocalDateTime> list = easyEntityQuery.queryable(Topic.class).select(t -> t.createTime()).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("6261(String),-3(Integer),-1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (LocalDateTime localDateTime : list) {
            System.out.println(localDateTime);
        }
    }
    @Test
    public void testFETCHER(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    Expression expression = t.expression();
                    t.id().eq("6261");
                    t.createTime().format("yyyy-MM").ge(expression.now().plusMonths(-3).format("yyyy-MM"));
                    t.createTime().format("yyyy-MM").le(expression.now().plusMonths(-1).format("yyyy-MM"));

                }).select(t -> t.FETCHER.id().createTime().title().fetchProxy()).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`title` FROM `t_topic` t WHERE t.`id` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m') >= DATE_FORMAT(date_add(NOW(), interval (?) month),'%Y-%m') AND DATE_FORMAT(t.`create_time`,'%Y-%m') <= DATE_FORMAT(date_add(NOW(), interval (?) month),'%Y-%m')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("6261(String),-3(Integer),-1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
     public void testxxx1(){
        List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    Expression expression = o.expression();
                    o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" );
                    o.or(() -> {
                        o.stars().ne(1);
                        o.createTime().le(LocalDateTime.of(2024, 1, 1, 1, 1));
                        o.title().notLike("abc" );
                    });
                    o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" );
                    o.id().nullOrDefault("yyyy/MM/dd" ).eq("xxx" );
                    expression.sql("{0} != {1}" , c -> {
                        c.expression(o.stars()).expression(o.createTime());
                    });
                    o.or(() -> {
                        o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" );
                        o.id().nullOrDefault("yyyy/MM/dd" ).eq("xxx" );
                        expression.sql("{0} != {1}" , c -> {
                            c.expression(o.stars()).expression(o.createTime());
                        });
                    });

                    o.createTime().format("yyyy/MM/dd" ).eq("2023/01/02" );
                    o.id().nullOrDefault("yyyy/MM/dd2" ).eq("xxx1" );
                })
                .select(o -> o.FETCHER
                        .allFieldsExclude(o.id(), o.title())
                        .id().as(o.title())
                        .id().fetchProxy())
                .toList();

        List<Topic> list3 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" ))
                .select(o -> new TopicProxy().adapter(r->{

                    r.title().set(o.stars().nullOrDefault(0).toStr());
                    r.alias().setSQL("IFNULL({0},'')" , c -> {
                        c.keepStyle();
                        c.expression(o.id());
                    });
                }))
                .toList();
        List<Topic> list4 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.createTime().format("yyyy/MM/dd" ).eq("2023/01/01" ))
                .select(o -> new TopicProxy().adapter(r->{

                    r.title().set(o.stars().nullOrDefault(0).toStr());

                    PropTypeColumn<String> nullProperty = o.expression().sqlType("IFNULL({0},'')", c -> {
                        c.keepStyle();
                        c.expression(o.id());
                    }).setPropertyType(String.class);

                    r.alias().set(nullProperty);
                }))
                .toList();

    }

}
