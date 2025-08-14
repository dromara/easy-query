package com.easy.query.test;

import com.alibaba.fastjson2.JSON;
import com.easy.query.api.proxy.base.LocalDateTimeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyCategory;
import com.easy.query.test.entity.MyCategoryVO;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.blogtest.SysUser;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.MyCategoryVOProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.relation.MyRelationUser;
import com.easy.query.test.entity.relation.MyRelationUserDTO;
import com.easy.query.test.entity.relation.MyRelationUserDTO1;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.navigateflat.MyUserHome;
import com.easy.query.test.navigateflat.MyUserHome2;
import com.easy.query.test.vo.MyUnion;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2024/11/25 20:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest19 extends BaseTest {

    public static class MyNotNullOrEmptyValueFilter implements ValueFilter {
        public static final ValueFilter DEFAULT = new MyNotNullOrEmptyValueFilter();

        @Override
        public boolean accept(@NotNull TableAvailable table, @NotNull String property, Object value) {
            if (Objects.equals(table.getEntityClass(), Topic.class) && Objects.equals(Topic.Fields.title, property)) {
                return true;
            }

            if (value == null) {
                return false;
            }
            if (value instanceof String) {
                return EasyStringUtil.isNotBlank((String) value);
            }
            return true;
        }

    }

    @Test
    public void testMyFilter() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .filterConfigure(MyNotNullOrEmptyValueFilter.DEFAULT)
                .where(t_topic -> {
                    t_topic.title().like("");
                    t_topic.id().like("");
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @org.junit.Test
    public void testDoc99() {
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        try {

            List<MyRelationUserDTO> users = easyEntityQuery.queryable(MyRelationUser.class)
                    .selectAutoInclude(MyRelationUserDTO.class)
                    .toList();
        } catch (Exception ex) {

        }
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `t_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("1(Integer),%小学%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`user_id`,t.`name`,t.`book_type`,t.`create_time` FROM `relation_book` t WHERE (t.`user_id` IN (?) AND t.`create_time` <= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),2022-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        listenerContextManager.clear();
    }

    @Test
    public void testDoc100() {
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        try {

            List<MyRelationUserDTO1> users = easyEntityQuery.queryable(MyRelationUser.class)
                    .selectAutoInclude(MyRelationUserDTO1.class)
                    .toList();
        } catch (Exception ex) {
        }

        Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `t_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("1(Integer),%小学%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT `first_id`,`second_id` FROM `relation_route` WHERE `first_id` IN (?) AND `type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `relation_teacher` t WHERE (t.`id` IN (?) AND t.`name` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2(String),12345(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        listenerContextManager.clear();
    }

    @Test
    public void test1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> userInHz = easyEntityQuery.queryable(SysUser.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(u -> {
                        u.name().eq("小明");
                        //隐式子查询会自动join地址表
                        //根据条件是否生效自动添加address表的join，比如eq("")和eq("杭州生成的表不存在address和city的区别")
                        u.address().city().eq("");
                    }).toList();
        } catch (Exception ignored) {

        }

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t WHERE t.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("小明(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test1_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> userInHz = easyEntityQuery.queryable(SysUser.class)
                    .where(u -> {
                        u.name().eq("小明");
                        u.roles().any(role -> role.name().like("管理员"));
                    }).toList();
        } catch (Exception ignored) {

        }

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t WHERE t.`name` = ? AND EXISTS (SELECT 1 FROM `t_role` t1 WHERE EXISTS (SELECT 1 FROM `t_user_role` t2 WHERE t2.`role_id` = t1.`id` AND t2.`user_id` = t.`id` LIMIT 1) AND t1.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("小明(String),%管理员%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void test1_2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> userInHz = easyEntityQuery.queryable(SysUser.class)
                    .where(u -> {
                        u.name().eq("小明");
                        u.roles().none(role -> role.name().like("管理员"));
                        u.roles().any(role -> role.name().like("普通员工"));
                    }).toList();
        } catch (Exception ignored) {

        }

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t WHERE t.`name` = ? AND NOT ( EXISTS (SELECT 1 FROM `t_role` t1 WHERE EXISTS (SELECT 1 FROM `t_user_role` t2 WHERE t2.`role_id` = t1.`id` AND t2.`user_id` = t.`id` LIMIT 1) AND t1.`name` LIKE ? LIMIT 1)) AND EXISTS (SELECT 1 FROM `t_role` t3 WHERE EXISTS (SELECT 1 FROM `t_user_role` t4 WHERE t4.`role_id` = t3.`id` AND t4.`user_id` = t.`id` LIMIT 1) AND t3.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("小明(String),%管理员%(String),%普通员工%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void test1_3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> userInHz = easyEntityQuery.queryable(SysUser.class)
                    .subQueryToGroupJoin(u->u.roles())
                    .where(u -> {
                        u.name().eq("小明");
                        u.roles().none(role -> role.name().like("管理员"));
                        u.roles().any(role -> role.name().like("普通员工"));
                    }).toList();
        } catch (Exception ignored) {

        }

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,(COUNT((CASE WHEN t1.`name` LIKE ? THEN ? ELSE NULL END)) <= 0) AS `__none2__`,(COUNT((CASE WHEN t1.`name` LIKE ? THEN ? ELSE NULL END)) > 0) AS `__any3__` FROM `t_role` t1 INNER JOIN `t_user_role` t2 ON t1.`id` = t2.`role_id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE t.`name` = ? AND IFNULL(t4.`__none2__`,?) = ? AND IFNULL(t4.`__any3__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%管理员%(String),1(Integer),%普通员工%(String),1(Integer),小明(String),true(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {
//            EasyQueryClient easyQueryClient1 = easyEntityQuery.getEasyQueryClient();
//            easyQueryClient1.insertable(new SysUser())
//                    .onConflictThen(s->{},"id")
//                    .executeRows();
//
//
//            easyEntityQuery.insertable(new SysUser())


            List<LocalDateTime> list = easyEntityQuery.queryable(Topic.class)
                    .select(t_topic -> new LocalDateTimeProxy(t_topic.expression().now())).toList();


            List<SysUser> userInHz = easyEntityQuery.queryable(SysUser.class)
                    .where(u -> {
                        u.name().eq("小明");
                        //隐式子查询会自动join地址表
                        //根据条件是否生效自动添加address表的join，比如eq("")和eq("杭州生成的表不存在address和city的区别")
                        u.address().city().eq("");
                    }).toList();
        } catch (Exception ignored) {

        }

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_user_address` t1 ON t1.`user_id` = t.`id` WHERE t.`name` = ? AND t1.`city` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("小明(String),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testUnion1() {
        String sql = "SELECT '未开始'AS task_status\n" +
                "UNION ALL SELECT '进行中'\n" +
                "UNION ALL SELECT '已完成'\n" +
                "UNION ALL SELECT '审核中'\n" +
                "UNION ALL SELECT '已关闭'\n" +
                "UNION ALL SELECT '待修改（无需审核）'\n" +
                "UNION ALL SELECT '待修改（需审核）'";
        List<BlogEntity> list = easyEntityQuery.queryable(sql, MyUnion.class)
                .leftJoin(Topic.class, (m, t2) -> {
                    m.taskStatus().eq(t2.stars());
                    t2.id().eq("10");

                }).groupBy((m1, t2) -> GroupKeys.of(m1.taskStatus()))
                .select(group -> {
                    BlogEntityProxy r = new BlogEntityProxy();
                    r.id().set(group.key1());
                    r.title().set(String.valueOf(group.groupTable().t2.stars().count()));//把表达式字符串化给title
                    r.title().set(group.groupTable().t2.stars().count().asAnyType(String.class));//语言层面的强转
                    r.title().set(group.groupTable().t2.stars().count().toStr());//sql层面的cast
                    return r;

                }).toList();
    }

    @Test
    public void testTime() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        LocalDateTime localDateTime = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.id().eq("1");
                }).select(t -> new LocalDateTimeProxy(t.createTime())).singleNotNull();
        System.out.println(localDateTime);
        Assert.assertEquals(LocalDateTime.of(2023, 5, 25, 10, 48, 5), localDateTime);

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testTime1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        LocalDateTime localDateTime = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.id().eq("1");
                }).select(t -> new LocalDateTimeProxy(LocalDateTime.of(2023, 5, 25, 10, 48, 6))).singleNotNull();
        System.out.println(localDateTime);
        Assert.assertEquals(LocalDateTime.of(2023, 5, 25, 10, 48, 6), localDateTime);

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2023-05-25T10:48:06(LocalDateTime),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test122() {
        List<MyCategoryVO> list = easyEntityQuery.queryable(MyCategory.class)
                .select(m -> {
                    MyCategoryVOProxy r = new MyCategoryVOProxy();
                    r.selectAll(m);
                    r.count().set(
                            m.children().count()
                    );
                    return r;
                }).toList();
    }

    @Test
    public void testaaa() {
        List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
                .select(t -> Select.DRAFT.of(
                        t.id(),
                        t.expression().subQuery(() -> {
                            return easyEntityQuery.queryable(BlogEntity.class)
                                    .where(b -> {
                                        b.createTime().lt(LocalDateTime.now());
                                        b.id().eq(t.id());
                                    }).groupBy(b -> GroupKeys.of(b.title()))
                                    .selectColumn(group -> group.groupTable().content().joining("->"));
                        })
                )).toList();
    }
//    @Test
//    public void testaaa1() {
//        List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
//                .select(t -> Select.DRAFT.of(
//                        t.id(),
//                        t.expression().sqlSegment("GROUP_CONCAT(CASE WHEN {0} =FLOOR({0}) THEN CASE(FLOOR({0}) AS CHAR) ELSE TRIM(TRAILING '0' FROM FORMAT({0},2)) END SEPARATOR {1})",c->{
//                            c.keepStyle().expression(t.title()).value("->");
//                        },String.class)
//                )).toList();
//    }

//    @Test
//    public void testCteUnion(){
//        EntityQueryable<MyCategoryProxy, MyCategory> sql = easyEntityQuery.queryable(MyCategory.class)
//                .where(m -> {
//                    m.id().eq("123");
//                })
//                .asTreeCTE();
//        List<MyCategory> list = sql.cloneQueryable().union(sql.cloneQueryable())
//                .toList();
//
//    }

//    public void testBindQuery(){
//        List<TopicTestBind> list = easyEntityQuery.queryable(TopicTestBind.class).toList();
//    }

    @Test
    public void aaa() {
        List<MyUserHome> list = easyEntityQuery.queryable(MyUserHome.class)
                .where(m -> {
                    m.id().toNumber(Integer.class).add(1).eq(BigDecimal.valueOf(123));
                }).toList();

//        Queryable<MyUser1> queryable = easyQuery.queryable(MyUser1.class);
    }

    @Test
    public void aaa1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<MyUserHome2> list = easyEntityQuery.queryable(MyUserHome2.class)
                    .toList();
        } catch (Exception e) {

        }

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name`,`age` FROM `a`.`dbo`.`my_user`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("2023-05-25T10:48:06(LocalDateTime),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


//        Queryable<MyUser1> queryable = easyQuery.queryable(MyUser1.class);
    }


//    @Test
//     public void testFETCHer_ORDER(){
//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    t.id().eq("1");
//                }).select(t -> t.FETCHER.title().id())
//                .orderBy(t -> t.title().asc())
//                .toList();
//    }

    @Test
    public void testNow() {
        SQLFunc fx = easyQueryClient.getRuntimeContext().fx();
        SQLFunction now = fx.now();

        LocalDateTime localDateTime = easyQueryClient.queryable(Map.class)
                .asTable("t_topic")
                .select(LocalDateTime.class, m -> m.sqlFunc(now))
                .limit(1)
                .singleNotNull();
        System.out.println(localDateTime);

        Long l = easyEntityQuery.queryable(Topic.class)
                .maxOrDefault(o -> o.id().toNumber(Long.class), 0L);
        System.out.println(l);

        Long l1 = easyEntityQuery.queryable(Topic.class)
                .selectColumn(t_topic -> t_topic.id().toNumber(Long.class).max()).singleOrDefault(0L);
        System.out.println(l1);
        Assert.assertEquals(l, l1);
    }


    @Test
    public void testConcat() {
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    Expression expression = b.expression();
                    b.updateTime().asAny().concat(s -> s.value(",").expression(b.id()))
                            .ge(
                                    expression.concat(o -> {
                                        o.expression(b.updateTime().asAnyType(String.class)).value(",").expression(b.id());
                                    })
                            );


                    expression.concat(o -> {
                        o.expression(b.updateTime().asAnyType(String.class)).value(",").expression(b.id());
                    }).ge(
                            expression.concat(o -> {
                                o.expression(b.updateTime().asAnyType(String.class)).value(",").expression(b.id());
                            })
                    );
                    val left = expression.concat(o -> {
                        o.expression(b.updateTime().asAnyType(String.class)).value(",").expression(b.id());
                    });

                    val right = expression.concat(o -> {
                        o.expression(b.updateTime().asAny()).value(",").expression(b.id());
                    });

                    left.ge(right);


                }).toList();
    }


    @Test
    public void mapQuery() {

        String tableName = "aaa";
        ArrayList<String> filterColumns = new ArrayList<>();
        filterColumns.add("id");
        filterColumns.add("name");
        ArrayList<String> selectColumns = new ArrayList<>();
        selectColumns.add("id");
        selectColumns.add("age");
        selectColumns.add("id_card");

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        try {
            List<Map<String, Object>> maps = easyQueryClient.queryable(Map.class)
                    .asTable(tableName)
                    .where(m -> {
                        for (String filterColumn : filterColumns) {
                            m.eq(filterColumn, "123");
                        }
                    })
                    .select(m -> {
                        for (String selectColumn : selectColumns) {
                            m.column(selectColumn);
                        }
                    }).toMaps();

        } catch (Exception e) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id` AS `id`,`age` AS `age`,`id_card` AS `id_card` FROM `aaa` WHERE `id` = ? AND `name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void mapInsert() {

        List<Map<String, Object>> map = new ArrayList<>();
        LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
        entity.put("id", "1");
        entity.put("stars", "2");

        map.add(entity);

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        try {

            easyQueryClient.mapInsertable(map).asTable("xxxxx").executeRows();

        } catch (Exception e) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("INSERT INTO `xxxxx` (`id`,`stars`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void mapUpdate() {

        List<Map<String, Object>> map = new ArrayList<>();
        LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
        entity.put("id", "1");
        entity.put("stars", "2");

        map.add(entity);


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        try {

            easyQueryClient.mapUpdatable(map).asTable("xxxxx").whereColumns("id").executeRows();

        } catch (Exception e) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `xxxxx` SET `stars` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void mapJoinFixed() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        try {

            List<Map<String, Object>> maps = easyQueryClient.queryable(Map.class)
                    .asTable("table1")
                    .leftJoin(Map.class, (t_table1, t_table2) -> {
                        t_table1.eq(t_table2, "table1Id", "table2Id");
                    }).asTable("table2")
                    .where((t_table1, t_table2) -> {
                        t_table1.eq("name1", "123");
                        t_table2.eq("name2", "123");
                    }).select(Map.class, (t_table1, t_table2) -> {
                        t_table1.column("id1");
                        t_table1.column("name1");
                        t_table2.column("id2");
                        t_table2.column("name2");
                    }).toMaps();

        } catch (Exception e) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id1` AS `id1`,t.`name1` AS `name1`,t1.`id2` AS `id2`,t1.`name2` AS `name2` FROM `table1` t LEFT JOIN `table2` t1 ON t.`table1Id` = t1.`table2Id` WHERE t.`name1` = ? AND t1.`name2` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void mapJoin() {
        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add("table1");
        tableNames.add("table2");
        tableNames.add("table3");
        ArrayList<String> filterColumns = new ArrayList<>();
        filterColumns.add("id");
        filterColumns.add("name");

        MapQueryable mapQueryable = easyQueryClient.mapQueryable();
        for (int i = 0; i < tableNames.size(); i++) {
            String tableName = tableNames.get(i);
            if (i == 0) {//使用from
                mapQueryable = mapQueryable.asTable(tableName);
            } else {
                int finalI = i;
                mapQueryable = mapQueryable.join(MultiTableTypeEnum.LEFT_JOIN, on -> {
                    EntitySQLTableOwner<?> nextTable = on.getTableOwner(finalI);
                    WherePredicate<?> fromTableWhere = on.getWherePredicate(0);
                    WherePredicate<?> joinTableWhere = on.getWherePredicate(finalI);
                    fromTableWhere.eq(nextTable, "id", "id");
                    joinTableWhere.eq("name", "aaa");
                }).asTable(tableName);
            }
        }


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        try {

            List<Map<String, Object>> list = mapQueryable.where(filter -> {
                        WherePredicate<?> table1Where = filter.getWherePredicate(0);
                        WherePredicate<?> table2Where = filter.getWherePredicate(1);
                        WherePredicate<?> table3Where = filter.getWherePredicate(2);
                        for (String filterColumn : filterColumns) {
                            table1Where.eq(filterColumn, "123");
                            table2Where.eq(filterColumn, "123");
                            table3Where.eq(filterColumn, "123");
                        }
                    })
                    .select(selector -> {
                        ColumnAsSelector<?, ?> table1AsSelector = selector.getAsSelector(0);
                        ColumnAsSelector<?, ?> table2AsSelector = selector.getAsSelector(1);
                        ColumnAsSelector<?, ?> table3AsSelector = selector.getAsSelector(2);
                        table1AsSelector.column("table1Column");
                        table2AsSelector.column("table2Column");
                        table3AsSelector.column("table3Column");
                    }).toList();

        } catch (Exception e) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`table1Column` AS `table1Column`,t1.`table2Column` AS `table2Column`,t2.`table3Column` AS `table3Column` FROM `table1` t LEFT JOIN `table2` t1 ON t.`id` = t1.`id` AND t1.`name` = ? LEFT JOIN `table3` t2 ON t.`id` = t2.`id` AND t2.`name` = ? WHERE t.`id` = ? AND t1.`id` = ? AND t2.`id` = ? AND t.`name` = ? AND t1.`name` = ? AND t2.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("aaa(String),aaa(String),123(String),123(String),123(String),123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testMapJson() {
        ArrayList<MapQueryJson> mapQueryJsons = new ArrayList<>();
        {
            MapQueryJson mapQueryJson = new MapQueryJson();
            mapQueryJson.setTableName("table1");
            mapQueryJson.setTableIndex(0);
            ArrayList<MapQueryJson.InternalWhere> internalWheres = new ArrayList<>();
            {
                MapQueryJson.InternalWhere internalWhere = new MapQueryJson.InternalWhere();
                internalWhere.setColumn("id1");
                internalWhere.setCompare("123");
                internalWheres.add(internalWhere);
            }
            {
                MapQueryJson.InternalWhere internalWhere = new MapQueryJson.InternalWhere();
                internalWhere.setColumn("name1");
                internalWhere.setCompare("456");
                internalWheres.add(internalWhere);
            }
            mapQueryJson.setWhereFilters(internalWheres);
            mapQueryJsons.add(mapQueryJson);
        }
        {
            MapQueryJson mapQueryJson = new MapQueryJson();
            mapQueryJson.setTableName("table2");
            mapQueryJson.setTableIndex(1);
            ArrayList<MapQueryJson.InternalOn> internalOns = new ArrayList<>();
            {
                MapQueryJson.InternalOn internalOn = new MapQueryJson.InternalOn();
                internalOn.setTableIndex(0);
                internalOn.setColumn("id1");
                internalOn.setCompare("id2");
                internalOns.add(internalOn);
            }
            mapQueryJson.setOnFilters(internalOns);

            ArrayList<MapQueryJson.InternalWhere> internalWheres = new ArrayList<>();
            {
                MapQueryJson.InternalWhere internalWhere = new MapQueryJson.InternalWhere();
                internalWhere.setColumn("id1");
                internalWhere.setCompare("123");
                internalWheres.add(internalWhere);
            }
            {
                MapQueryJson.InternalWhere internalWhere = new MapQueryJson.InternalWhere();
                internalWhere.setColumn("name1");
                internalWhere.setCompare("456");
                internalWheres.add(internalWhere);
            }
            mapQueryJson.setWhereFilters(internalWheres);
            mapQueryJsons.add(mapQueryJson);
        }
        {
            MapQueryJson mapQueryJson = new MapQueryJson();
            mapQueryJson.setTableName("table3");
            mapQueryJson.setTableIndex(2);
            ArrayList<MapQueryJson.InternalOn> internalOns = new ArrayList<>();
            {
                MapQueryJson.InternalOn internalOn = new MapQueryJson.InternalOn();
                internalOn.setTableIndex(0);
                internalOn.setColumn("id1");
                internalOn.setCompare("id3");
                internalOns.add(internalOn);
            }
            mapQueryJson.setOnFilters(internalOns);

            ArrayList<MapQueryJson.InternalWhere> internalWheres = new ArrayList<>();
            {
                MapQueryJson.InternalWhere internalWhere = new MapQueryJson.InternalWhere();
                internalWhere.setColumn("id3");
                internalWhere.setCompare("123");
                internalWheres.add(internalWhere);
            }
            {
                MapQueryJson.InternalWhere internalWhere = new MapQueryJson.InternalWhere();
                internalWhere.setColumn("name3");
                internalWhere.setCompare("456");
                internalWheres.add(internalWhere);
            }
            mapQueryJson.setWhereFilters(internalWheres);
            mapQueryJsons.add(mapQueryJson);
        }
        System.out.println(JSON.toJSONString(mapQueryJsons));

        MapQueryable mapQueryable = easyQueryClient.mapQueryable();
        for (int i = 0; i < mapQueryJsons.size(); i++) {
            MapQueryJson mapQueryJson = mapQueryJsons.get(i);
            if (i == 0) {
                mapQueryable.asTable(mapQueryJson.getTableName());
            } else {

                int finalI = i;
                mapQueryable = mapQueryable.join(MultiTableTypeEnum.LEFT_JOIN, on -> {
                    WherePredicate<?> joinTableWhere = on.getWherePredicate(finalI);
                    for (MapQueryJson.InternalOn onFilter : mapQueryJson.getOnFilters()) {
                        if (onFilter.getTableIndex() == null) {
                            joinTableWhere.eq(onFilter.getColumn(), onFilter.getCompare());
                        } else {
                            EntitySQLTableOwner<?> otherTable = on.getTableOwner(onFilter.getTableIndex());
                            joinTableWhere.eq(otherTable, onFilter.getColumn(), onFilter.getCompare().toString());
                        }
                    }
                }).asTable(mapQueryJson.getTableName());
            }
        }


        for (int i = 0; i < mapQueryJsons.size(); i++) {
            MapQueryJson mapQueryJson = mapQueryJsons.get(i);
            mapQueryable = mapQueryable.where(where -> {
                for (MapQueryJson.InternalWhere whereFilter : mapQueryJson.getWhereFilters()) {
                    WherePredicate<?> wherePredicate = where.getWherePredicate(mapQueryJson.getTableIndex());
                    wherePredicate.eq(whereFilter.getColumn(), whereFilter.getCompare());
                }
            });
        }

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        try {

            List<Map<String, Object>> list = mapQueryable.toList();

        } catch (Exception e) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT * FROM `table1` t LEFT JOIN `table2` t1 ON t1.`id1` = t.`id2` LEFT JOIN `table3` t2 ON t2.`id1` = t.`id3` WHERE t.`id1` = ? AND t.`name1` = ? AND t1.`id1` = ? AND t1.`name1` = ? AND t2.`id3` = ? AND t2.`name3` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),456(String),123(String),456(String),123(String),456(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testToWithAs() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            ClientQueryable<Topic> topicSQL = easyQueryClient.queryable(Topic.class)
                    .where(t_topic -> t_topic.eq("id", "456"))
                    .toCteAs("withTableTopic");

            List<Topic> list = easyQueryClient.queryable(Topic.class)
                    .leftJoin(topicSQL, (t_topic, t_topic1) -> t_topic.eq(t_topic1, "id", "id"))
                    .where((t_topic, t_topic1) -> {
                        t_topic.eq("id", "123");
                    }).toList();
        } catch (Exception ex) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `withTableTopic` AS (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `withTableTopic` t2 ON t.`id` = t2.`id` WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("456(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testToWithAs2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            ClientQueryable<Topic> topicSQL = easyQueryClient.queryable(Topic.class)
                    .where(t_topic -> t_topic.eq("id", "456"))
                    .toCteAs();

            List<Topic> list = easyQueryClient.queryable(Topic.class)
                    .leftJoin(topicSQL, (t_topic, t_topic1) -> t_topic.eq(t_topic1, "id", "id"))
                    .leftJoin(topicSQL, (t_topic, t_topic1, t_topic2) -> t_topic.eq(t_topic2, "id", "id"))
                    .where((t_topic, t_topic1, t_topic2) -> {
                        t_topic.eq("id", "123");
                        t_topic2.eq("id", "t2123");
                    }).toList();
        } catch (Exception ex) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `with_Topic` AS (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `with_Topic` t2 ON t.`id` = t2.`id` LEFT JOIN `with_Topic` t2 ON t.`id` = t2.`id` WHERE t.`id` = ? AND t2.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("456(String),123(String),t2123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testToWithAs3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {
            EntityQueryable<TopicProxy, Topic> cteAs = easyEntityQuery.queryable(Topic.class)
                    .where(t_topic -> {
                        t_topic.id().eq("456");
                    }).toCteAs();

            List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(cteAs, (t_topic, t2) -> t_topic.id().eq(t2.id()))
                    .leftJoin(cteAs, (t_topic, t_topic2, t3) -> t_topic.id().eq(t3.id()))
                    .where((t_topic, t_topic2, t_topic3) -> {
                        t_topic.id().eq("123");
                        t_topic3.id().eq("t2123");
                    }).toList();

//            ClientQueryable<Topic> topicSQL = easyQueryClient.queryable(Topic.class)
//                    .where(t_topic -> t_topic.eq("id", "456"))
//                    .toCteAs();
//
//            List<Topic> list = easyQueryClient.queryable(Topic.class)
//                    .leftJoin(topicSQL, (t_topic, t_topic1) -> t_topic.eq(t_topic1, "id", "id"))
//                    .leftJoin(topicSQL, (t_topic, t_topic1, t_topic2) -> t_topic.eq(t_topic2, "id", "id"))
//                    .where((t_topic, t_topic1,t_topic2) -> {
//                        t_topic.eq("id", "123");
//                        t_topic2.eq("id", "t2123");
//                    }).toList();
        } catch (Exception ex) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `with_Topic` AS (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `with_Topic` t2 ON t.`id` = t2.`id` LEFT JOIN `with_Topic` t2 ON t.`id` = t2.`id` WHERE t.`id` = ? AND t2.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("456(String),123(String),t2123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testToWithA43() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {
            EntityQueryable<TopicProxy, Topic> cteAs = easyEntityQuery.queryable(Topic.class)
                    .where(t_topic -> {
                        t_topic.id().eq("456");
                    }).toCteAs();

            List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                    .innerJoin(cteAs, (t_topic, t2) -> t_topic.id().eq(t2.id()))
                    .innerJoin(cteAs, (t_topic, t_topic2, t3) -> t_topic.id().eq(t3.id()))
                    .where((t_topic, t_topic2, t_topic3) -> {
                        t_topic.id().eq("123");
                        t_topic3.id().eq("t2123");
                    }).toList();

//            ClientQueryable<Topic> topicSQL = easyQueryClient.queryable(Topic.class)
//                    .where(t_topic -> t_topic.eq("id", "456"))
//                    .toCteAs();
//
//            List<Topic> list = easyQueryClient.queryable(Topic.class)
//                    .leftJoin(topicSQL, (t_topic, t_topic1) -> t_topic.eq(t_topic1, "id", "id"))
//                    .leftJoin(topicSQL, (t_topic, t_topic1, t_topic2) -> t_topic.eq(t_topic2, "id", "id"))
//                    .where((t_topic, t_topic1,t_topic2) -> {
//                        t_topic.eq("id", "123");
//                        t_topic2.eq("id", "t2123");
//                    }).toList();
        } catch (Exception ex) {

        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `with_Topic` AS (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t INNER JOIN `with_Topic` t2 ON t.`id` = t2.`id` INNER JOIN `with_Topic` t2 ON t.`id` = t2.`id` WHERE t.`id` = ? AND t2.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("456(String),123(String),t2123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

}
