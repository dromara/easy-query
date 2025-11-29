package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8User;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/10/19 22:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQL8Test5 extends BaseTest {
    @Test
    public void testManyLimit1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list = easyEntityQuery.queryable(M8User.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .select(m -> Select.DRAFT.of(
                        m.id(),
                        m.roles().where(r -> r.name().startsWith("管理员")).orderBy(s -> s.name().asc()).elements(0, 5).joining(s -> s.name(), ",")
                )).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t6.`__joining2__` AS `value2` FROM `m8_user` t LEFT JOIN (SELECT t4.`__group_key1__`,GROUP_CONCAT(t4.`name` SEPARATOR ?) AS `__joining2__` FROM (SELECT t3.`id` AS `id`,t3.`name` AS `name`,t3.`create_time` AS `create_time`,t3.`__group_key1__` AS `__group_key1__` FROM (SELECT t1.`id`,t1.`name`,t1.`create_time`,t2.`user_id` AS `__group_key1__`,(ROW_NUMBER() OVER (PARTITION BY t2.`user_id` ORDER BY t1.`name` ASC)) AS `__row__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` WHERE t1.`name` LIKE CONCAT(?,'%')) t3 WHERE t3.`__row__` >= ? AND t3.`__row__` <= ?) t4 GROUP BY t4.`__group_key1__`) t6 ON t6.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),管理员(String),1(Long),6(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testManyLimit1_1() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Draft2<String, String>> list = easyEntityQuery.queryable(M8User.class)
                    .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                    .select(m -> Select.DRAFT.of(
                            m.id(),
                            m.roles().where(r -> r.name().startsWith("管理员")).orderBy(s -> s.name().asc()).joining(s -> s.m8SaveA().name(), ",")
                    )).toList();
            listenerContextManager.clear();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t4.`__joining2__` AS `value2` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `__group_key1__`,GROUP_CONCAT(t5.`name` ORDER BY t1.`name` ASC SEPARATOR ?) AS `__joining2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` LEFT JOIN `m8_save_a` t5 ON t5.`id` = t1.`id` WHERE t1.`name` LIKE CONCAT(?,'%') GROUP BY t2.`user_id`) t4 ON t4.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals(",(String),管理员(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


        }
    }

    @Test
    public void testManyLimit1_2() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Draft2<String, String>> list = easyEntityQuery.queryable(M8User.class)
                    .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                    .select(m -> Select.DRAFT.of(
                            m.id(),
                            m.roles().where(r -> r.name().startsWith("管理员")).orderBy(s -> s.name().asc()).elements(0, 5).joining(s -> s.m8SaveA().name(), ",")
                    )).toList();
            listenerContextManager.clear();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t6.`__joining2__` AS `value2` FROM `m8_user` t LEFT JOIN (SELECT t4.`__group_key1__`,GROUP_CONCAT(t7.`name` SEPARATOR ?) AS `__joining2__` FROM (SELECT t3.`id` AS `id`,t3.`name` AS `name`,t3.`create_time` AS `create_time`,t3.`__group_key1__` AS `__group_key1__` FROM (SELECT t1.`id`,t1.`name`,t1.`create_time`,t2.`user_id` AS `__group_key1__`,(ROW_NUMBER() OVER (PARTITION BY t2.`user_id` ORDER BY t1.`name` ASC)) AS `__row__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` WHERE t1.`name` LIKE CONCAT(?,'%')) t3 WHERE t3.`__row__` >= ? AND t3.`__row__` <= ?) t4 LEFT JOIN `m8_save_a` t7 ON t7.`id` = t4.`id` GROUP BY t4.`__group_key1__`) t6 ON t6.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals(",(String),管理员(String),1(Long),6(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


        }
    }

    @Test
    public void testManyLimit1_3() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            easyEntityQuery.queryable(M8User.class)
                    .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                    .select(m -> Select.DRAFT.of(
                            m.id(),
                            m.roles().where(r -> r.name().startsWith("管理员")).orderBy(s -> s.name().asc()).elements(0, 5).joining(s -> s.m8SaveA().name(), ","),
                            m.roles().where(r -> r.name().startsWith("管理员")).orderBy(s -> s.name().asc()).elements(0, 5).joining(s -> s.m8SaveA().id(), ",")
                    )).toList();
            listenerContextManager.clear();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t6.`__joining2__` AS `value2`,t6.`__joining3__` AS `value3` FROM `m8_user` t LEFT JOIN (SELECT t4.`__group_key1__`,GROUP_CONCAT(t7.`name` SEPARATOR ?) AS `__joining2__`,GROUP_CONCAT(t7.`id` SEPARATOR ?) AS `__joining3__` FROM (SELECT t3.`id` AS `id`,t3.`name` AS `name`,t3.`create_time` AS `create_time`,t3.`__group_key1__` AS `__group_key1__` FROM (SELECT t1.`id`,t1.`name`,t1.`create_time`,t2.`user_id` AS `__group_key1__`,(ROW_NUMBER() OVER (PARTITION BY t2.`user_id` ORDER BY t1.`name` ASC)) AS `__row__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` WHERE t1.`name` LIKE CONCAT(?,'%')) t3 WHERE t3.`__row__` >= ? AND t3.`__row__` <= ?) t4 LEFT JOIN `m8_save_a` t7 ON t7.`id` = t4.`id` GROUP BY t4.`__group_key1__`) t6 ON t6.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals(",(String),,(String),管理员(String),1(Long),6(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


        }
    }

    @Test
    public void testManyLimit2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list1 = easyEntityQuery.queryable(M8User.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .select(m -> Select.DRAFT.of(
                        m.id(),
                        m.roles().where(r -> r.name().startsWith("管理员")).joining(s -> s.name(), ",")
                )).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t4.`__joining2__` AS `value2` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `__group_key1__`,GROUP_CONCAT(t1.`name` SEPARATOR ?) AS `__joining2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` WHERE t1.`name` LIKE CONCAT(?,'%') GROUP BY t2.`user_id`) t4 ON t4.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),管理员(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }
//    @Test
//    public void testManyLimit3(){
//
//
//        ListenerContext listenerContext = new ListenerContext();
//        listenerContextManager.startListen(listenerContext);
//
//        List<Draft2<String, String>> list1 = easyEntityQuery.queryable(M8User.class)
//                .configure(s->s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
//                .select(m -> Select.DRAFT.of(
//                        m.id(),
//                        m.roles().where(r-> r.name().startsWith("管理员")).orderBy(s->s.name().asc()).joining(s -> s.name(), ",")
//                )).toList();
//        listenerContextManager.clear();
//        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//        Assert.assertEquals("SELECT t.`id` AS `value1`,t4.`__joining2__` AS `value2` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `__group_key1__`,GROUP_CONCAT(t1.`name` SEPARATOR ?) AS `__joining2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` WHERE t1.`name` LIKE CONCAT(?,'%') GROUP BY t2.`user_id`) t4 ON t4.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals(",(String),管理员(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//
//
//    }


    @Test
    public void testManyLimit4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, String>> list2 = easyEntityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .select(user -> Select.DRAFT.of(
                        user.id(),
                        user.bankCards().where(bankCard -> bankCard.code().startsWith("小")).joining(s -> s.type(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t2.`__joining2__` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `__group_key1__`,GROUP_CONCAT(t1.`type` SEPARATOR ?) AS `__joining2__` FROM `t_bank_card` t1 WHERE t1.`code` LIKE CONCAT(?,'%') GROUP BY t1.`uid`) t2 ON t2.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),小(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testManyLimit5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, String>> list2 = easyEntityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .select(user -> Select.DRAFT.of(
                        user.id(),
                        user.bankCards().where(bankCard -> bankCard.code().startsWith("小")).orderBy(s -> s.bank().createTime().asc()).elements(0, 2).joining(s -> s.type(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t5.`__joining2__` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t4.`uid` AS `__group_key1__`,GROUP_CONCAT(t4.`type` SEPARATOR ?) AS `__joining2__` FROM (SELECT t3.`id` AS `id`,t3.`uid` AS `uid`,t3.`code` AS `code`,t3.`type` AS `type`,t3.`bank_id` AS `bank_id`,t3.`open_time` AS `open_time`,t3.`__row__` AS `__row__` FROM (SELECT t1.`id` AS `id`,t1.`uid` AS `uid`,t1.`code` AS `code`,t1.`type` AS `type`,t1.`bank_id` AS `bank_id`,t1.`open_time` AS `open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t2.`create_time` ASC)) AS `__row__` FROM `t_bank_card` t1 INNER JOIN `t_bank` t2 ON t2.`id` = t1.`bank_id` WHERE t1.`code` LIKE CONCAT(?,'%')) t3 WHERE t3.`__row__` >= ? AND t3.`__row__` <= ?) t4 GROUP BY t4.`uid`) t5 ON t5.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),小(String),1(Long),3(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testManyLimit6() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, String>> list2 = easyEntityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .select(user -> Select.DRAFT.of(
                        user.id(),
                        user.bankCards().where(bankCard -> bankCard.code().startsWith("小")).orderBy(s -> s.bank().createTime().asc()).elements(0, 2).joining(s -> s.bank().name(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t5.`__joining2__` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t4.`uid` AS `__group_key1__`,GROUP_CONCAT(t6.`name` SEPARATOR ?) AS `__joining2__` FROM (SELECT t3.`id` AS `id`,t3.`uid` AS `uid`,t3.`code` AS `code`,t3.`type` AS `type`,t3.`bank_id` AS `bank_id`,t3.`open_time` AS `open_time`,t3.`__row__` AS `__row__` FROM (SELECT t1.`id` AS `id`,t1.`uid` AS `uid`,t1.`code` AS `code`,t1.`type` AS `type`,t1.`bank_id` AS `bank_id`,t1.`open_time` AS `open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t2.`create_time` ASC)) AS `__row__` FROM `t_bank_card` t1 INNER JOIN `t_bank` t2 ON t2.`id` = t1.`bank_id` WHERE t1.`code` LIKE CONCAT(?,'%')) t3 WHERE t3.`__row__` >= ? AND t3.`__row__` <= ?) t4 INNER JOIN `t_bank` t6 ON t6.`id` = t4.`bank_id` GROUP BY t4.`uid`) t5 ON t5.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),小(String),1(Long),3(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testManyLimit7() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, String>> list2 = easyEntityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .select(user -> Select.DRAFT.of(
                        user.id(),
                        user.bankCards().where(bankCard -> bankCard.code().startsWith("小")).joining(s -> s.bank().name(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t2.`__joining2__` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `__group_key1__`,GROUP_CONCAT(t3.`name` SEPARATOR ?) AS `__joining2__` FROM `t_bank_card` t1 INNER JOIN `t_bank` t3 ON t3.`id` = t1.`bank_id` WHERE t1.`code` LIKE CONCAT(?,'%') GROUP BY t1.`uid`) t2 ON t2.`__group_key1__` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),小(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testJoining1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(SysBank.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().orderBy(o -> o.openTime().asc()).orderBy(o -> o.type().desc()).joining(s -> s.type())
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,t2.`__joining2__` AS `__part__column1` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `__group_key1__`,GROUP_CONCAT(t1.`type` ORDER BY t1.`open_time` ASC,t1.`type` DESC SEPARATOR ?) AS `__joining2__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`__group_key1__` = t.`id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testJoining2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(SysBank.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards()
                                .orderBy(o -> o.openTime().asc())
                                .orderBy(o -> o.type().desc())
                                .distinct()
                                .joining(s -> s.type())
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,t2.`__joining2__` AS `__part__column1` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `__group_key1__`,GROUP_CONCAT(DISTINCT t1.`type` ORDER BY t1.`open_time` ASC,t1.`type` DESC SEPARATOR ?) AS `__joining2__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`__group_key1__` = t.`id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testToSelectPageResult() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        String type = null;
        easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().like("银行");
                })
                .toPageSelectResult(q -> {
                    return q.select(bank -> Select.PART.of(
                            bank,
                            bank.bankCards()
                                    .where(s -> s.type().eq(type))
                                    .joining(s -> s.type())
                    ));
                }, 1, 2);

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`name`,t1.`create_time`,t3.`__joining2__` AS `__part__column1` FROM (SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE t.`name` LIKE ? LIMIT 2) t1 LEFT JOIN (SELECT t2.`bank_id` AS `__group_key1__`,GROUP_CONCAT(t2.`type` SEPARATOR ?) AS `__joining2__` FROM `t_bank_card` t2 GROUP BY t2.`bank_id`) t3 ON t3.`__group_key1__` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String),,(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testToSelectPageResult2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        String type = null;
        easyEntityQuery.queryable(SysBank.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().like("银行");
                })
                .toPageSelectResult(q -> {
                    return q.select(bank -> Select.PART.of(
                            bank,
                            bank.bankCards()
                                    .where(s -> s.type().eq(type))
                                    .joining(s -> s.type())
                    ));
                }, 1, 2);

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`name`,t1.`create_time`,t3.`__joining2__` AS `__part__column1` FROM (SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE t.`name` LIKE ? LIMIT 2) t1 LEFT JOIN (SELECT t2.`bank_id` AS `__group_key1__`,GROUP_CONCAT(t2.`type` SEPARATOR ?) AS `__joining2__` FROM `t_bank_card` t2 WHERE t2.`type` = ? GROUP BY t2.`bank_id`) t3 ON t3.`__group_key1__` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String),,(String),null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testToSelectPageResult3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        String type = "123";
        easyEntityQuery.queryable(SysBank.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().like("银行");
                    bank.bankCards().filter(t->{
                        t.type().eq(type);
                    });
                })
                .toPageSelectResult(q -> {
                    return q.select(bank -> Select.PART.of(
                            bank,
                            bank.bankCards()
                                    .joining(s -> s.type())
                    ));
                }, 1, 2);

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`name`,t1.`create_time`,t3.`__joining2__` AS `__part__column1` FROM (SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE t.`name` LIKE ? LIMIT 2) t1 LEFT JOIN (SELECT t2.`bank_id` AS `__group_key1__`,GROUP_CONCAT(t2.`type` SEPARATOR ?) AS `__joining2__` FROM `t_bank_card` t2 WHERE t2.`type` = ? GROUP BY t2.`bank_id`) t3 ON t3.`__group_key1__` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String),,(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

}
