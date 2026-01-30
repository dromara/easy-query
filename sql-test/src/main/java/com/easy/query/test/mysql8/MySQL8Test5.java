package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.util.EasyProxyParamExpressionUtil;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.SysDeptTreeResp;
import com.easy.query.test.entity.SysDept;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8User;
import com.easy.query.test.mysql8.entity.M8UserBook;
import com.easy.query.test.mysql8.entity.OffsetChunkTest;
import com.easy.query.test.mysql8.entity.TableNoKey;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.bank.proxy.SysBankCardProxy;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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



    @Test
    public void testLikeStartsWith() {
        String id = "testLikeStartsWith";
        easyEntityQuery.deletable(TableNoKey.class).where(t -> {
            t.column1().eq(id);
        }).executeRows();
        try {

            TableNoKey tableNoKey = new TableNoKey();
            tableNoKey.setColumn1(id);
            tableNoKey.setColumn2("小_明%在哪里");
            easyEntityQuery.insertable(tableNoKey).executeRows();


            List<TableNoKey> list = easyEntityQuery.queryable(TableNoKey.class)
                    .where(t -> {
                        startsWith(t.column2(), "小_明%");
                    }).toList();

            Assert.assertTrue(list.size() > 0);


            {


                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<TableNoKey> list2 = easyEntityQuery.queryable(TableNoKey.class)
                        .where(t -> {
                            t.column2().startsWith("小_明%");
                        }).toList();

                listenerContextManager.clear();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT `column1`,`column2`,`column3` FROM `no_key_table` WHERE `column2` LIKE CONCAT(?,'%') ESCAPE '\\\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("小\\_明\\%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                Assert.assertTrue(list2.size() > 0);
            }
            {


                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<TableNoKey> list2 = easyEntityQuery.queryable(TableNoKey.class)
                        .where(t -> {
                            t.column2().contains("小_明%");
                        }).toList();

                listenerContextManager.clear();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT `column1`,`column2`,`column3` FROM `no_key_table` WHERE `column2` LIKE CONCAT('%',?,'%') ESCAPE '\\\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("小\\_明\\%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                Assert.assertTrue(list2.size() > 0);
            }
            {


                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<TableNoKey> list2 = easyEntityQuery.queryable(TableNoKey.class)
                        .where(t -> {
                            t.column2().endsWith("小_明%在哪里");
                        }).toList();

                listenerContextManager.clear();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT `column1`,`column2`,`column3` FROM `no_key_table` WHERE `column2` LIKE CONCAT('%',?) ESCAPE '\\\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("小\\_明\\%在哪里(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                Assert.assertTrue(list2.size() > 0);
            }
            {


                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<TableNoKey> list2 = easyEntityQuery.queryable(TableNoKey.class)
                        .where(t -> {
                            t.column2().startsWith("小_明%");
                            t.column2().contains("小_明%");
                        }).toList();

                listenerContextManager.clear();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT `column1`,`column2`,`column3` FROM `no_key_table` WHERE `column2` LIKE CONCAT(?,'%') ESCAPE '\\\\' AND `column2` LIKE CONCAT('%',?,'%') ESCAPE '\\\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("小\\_明\\%(String),小\\_明\\%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                Assert.assertTrue(list2.size() > 0);
            }


        } finally {

            easyEntityQuery.deletable(TableNoKey.class).where(t -> {
                t.column1().eq(id);
            }).executeRows();
        }

    }

    public static void startsWith(SQLStringTypeColumn<?> column, @NotNull String matchValue) {
        if (matchValue.contains("%") || matchValue.contains("_")) {
            Expression expression = EasyProxyParamExpressionUtil.parseContextExpressionByParameters(column);
            String escape = escape(matchValue);
            expression.rawSQLCommand("{0} LIKE CONCAT({1},'%') ESCAPE '\\\\'", column, escape);
        } else {
            column.startsWith(matchValue);
        }
    }

    /**
     * 转义 LIKE 中的特殊字符：%, _, \
     * 使用 ESCAPE '\'
     */
    public static String escape(String input) {
        if (input == null) return null;

        return input
                .replace("\\", "\\\\")   // 转义反斜杠
                .replace("%", "\\%")      // 转义百分号
                .replace("_", "\\_");     // 转义下划线
    }


    @Test
    public  void testWhereJoin(){
        List<M8User> list = easyEntityQuery.queryable(M8User.class)
                .where(m -> {
                    m.name().like("123");
                }).leftJoin(M8User.class, (m, m2) -> m.id().eq(m2.id()))
                .toList();
    }

    @Test
    public  void selectJoinInclude(){
        List<SysUser> list1 = easyEntityQuery.queryable(SysUser.class)
                .leftJoin(SysBankCard.class, (user, bank_card) -> user.id().eq(bank_card.uid()))
                .select((user, bank_card) -> user)
                .include(user -> user.bankCards())
                .distinct()
                .toList();
    }


    @Test
    public void testOffsetChunk(){
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.set(0);
        ArrayList<Integer> list = new ArrayList<>();
        easyEntityQuery.queryable(OffsetChunkTest.class)
                .where(o -> o.status().isNull())
                .orderBy(o -> o.seq().asc())
                .offsetChunk(10,chunk->{
                    for (OffsetChunkTest value : chunk.getValues()) {
                        int andIncrement = atomicInteger.getAndIncrement();
                        list.add(value.getSeq());
                        Assert.assertEquals(andIncrement,value.getSeq().intValue());

                        easyEntityQuery.updatable(OffsetChunkTest.class)
                                .setColumns(o -> o.status().set(value.getId()))
                                .whereById( value.getId())
                                .executeRows();
                    }
                    return chunk.offset(0);
                });
        for (int i = 0; i < 1000; i++) {
            Integer i1 = list.get(i);
            Assert.assertEquals(i,i1.intValue());
        }
    }

    @Test
    public void testSysDeptCTE(){
        List<SysDeptTreeResp> abc = easyEntityQuery.queryable(SysDept.class)
                .where(s -> {
                    s.name().in(Arrays.asList("abc-算法部","abc-召回模型","abc-渠道部"));
//                    s.code().contains(req.getCode());
//                    s.enable().eq(req.getEnable());
                })
                .asTreeCTE(op -> {
                    op.setDeepInCustomSelect(true);
                    op.setUp(true);
                    op.setDeepColumnName("deep");
                })
                .orderBy(s -> {
                    s.id().asc();
                })
                .selectAutoInclude(SysDeptTreeResp.class)
                .toTreeList();
        System.out.println(abc);
        Assert.assertEquals("[SysDeptTreeResp(id=2, pid=0, name=总部B, deep=2, children=[SysDeptTreeResp(id=21, pid=2, name=研发中心, deep=1, children=[SysDeptTreeResp(id=211, pid=21, name=abc-算法部, deep=0, children=[])])]), SysDeptTreeResp(id=2, pid=0, name=总部B, deep=2, children=[SysDeptTreeResp(id=22, pid=2, name=市场中心, deep=1, children=[SysDeptTreeResp(id=222, pid=22, name=abc-渠道部, deep=0, children=[])])]), SysDeptTreeResp(id=2, pid=0, name=总部B, deep=4, children=[SysDeptTreeResp(id=21, pid=2, name=研发中心, deep=3, children=[SysDeptTreeResp(id=211, pid=21, name=abc-算法部, deep=2, children=[SysDeptTreeResp(id=2111, pid=211, name=推荐算法, deep=1, children=[SysDeptTreeResp(id=21111, pid=2111, name=abc-召回模型, deep=0, children=[])])])])])]",abc+"");
    }
}
