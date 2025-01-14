package com.easy.query.test;

import com.easy.query.api.proxy.base.LocalDateTimeProxy;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.MyUser1;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyCategory;
import com.easy.query.test.entity.MyCategoryVO;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.blogtest.SysUser;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.MyCategoryProxy;
import com.easy.query.test.entity.proxy.MyCategoryVOProxy;
import com.easy.query.test.entity.relation.MyRelationUser;
import com.easy.query.test.entity.relation.MyRelationUserDTO;
import com.easy.query.test.entity.relation.MyRelationUserDTO1;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.navigateflat.MyUserHome;
import com.easy.query.test.navigateflat.MyUserHome2;
import com.easy.query.test.vo.MyUnion;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * create time 2024/11/25 20:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest19 extends BaseTest {

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
                                    .selectColumn(group -> group.groupTable().content().join("->"));
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
    public void aaa(){
        List<MyUserHome> list = easyEntityQuery.queryable(MyUserHome.class)
                .where(m -> {
                    m.id().toNumber(Integer.class).add(1).eq(BigDecimal.valueOf(123));
                }).toList();

//        Queryable<MyUser1> queryable = easyQuery.queryable(MyUser1.class);
    }

    @Test
    public void aaa1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<MyUserHome2> list = easyQuery.queryable(MyUserHome2.class)
                    .toList();
        }catch (Exception e){

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
    public  void testNow(){
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
        Assert.assertEquals(l,l1);
    }


}
