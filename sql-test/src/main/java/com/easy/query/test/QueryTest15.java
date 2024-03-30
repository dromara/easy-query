package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.UserExtra;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
}
