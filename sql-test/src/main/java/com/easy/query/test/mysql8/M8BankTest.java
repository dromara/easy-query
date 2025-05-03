package com.easy.query.test.mysql8;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft3Proxy;
import com.easy.query.core.proxy.part.Part1;
import com.easy.query.core.proxy.part.Part2;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.common.M8Interceptor;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.bank.SysUserBook;
import com.easy.query.test.mysql8.entity.bank.proxy.SysUserProxy;
import com.easy.query.test.mysql8.vo.MyUserVO;
import com.easy.query.test.mysql8.vo.SysBankCardDTO;
import com.easy.query.test.mysql8.vo.SysBankDTO;
import com.easy.query.test.mysql8.vo.SysUserDTO;
import com.easy.query.test.mysql8.vo.SysUserDTO2;
import com.easy.query.test.mysql8.vo.proxy.MyUserVOProxy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * create time 2025/4/3 20:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8BankTest extends BaseTest {

    @Test
    public void test1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .where(bank_card -> {
                    bank_card.user().name().eq("小明");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t LEFT JOIN `t_sys_user` t1 ON t1.`id` = t.`uid` WHERE t1.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("小明(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysBankCard> bankCards = easyEntityQuery.queryable(SysBankCard.class)
                .where(bank_card -> {
                    bank_card.user().phone().like("1234");
                    bank_card.bank().name().eq("工商银行");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t LEFT JOIN `t_sys_user` t1 ON t1.`id` = t.`uid` INNER JOIN `t_bank` t2 ON t2.`id` = t.`bank_id` WHERE t1.`phone` LIKE ? AND t2.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%1234%(String),工商银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft3<String, String, String>> list2 = easyEntityQuery.queryable(SysBankCard.class)
                .where(bank_card -> {
                    bank_card.user().name().eq("小明");
                    bank_card.bank().name().eq("工商银行");
                })
                .orderBy(bank_card -> bank_card.code().asc())
                .select(bank_card -> Select.DRAFT.of(
                        bank_card.user().name(),
                        bank_card.bank().name(),
                        bank_card.code()
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`name` AS `value1`,t2.`name` AS `value2`,t.`code` AS `value3` FROM `t_bank_card` t LEFT JOIN `t_sys_user` t1 ON t1.`id` = t.`uid` INNER JOIN `t_bank` t2 ON t2.`id` = t.`bank_id` WHERE t1.`name` = ? AND t2.`name` = ? ORDER BY t.`code` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("小明(String),工商银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.bankCards().where(card -> {
                        card.bank().name().eq("工商银行");
                    }).count().ge(2L);

                    user.bankCards().none(card -> {
                        card.bank().name().eq("建设银行");
                    });
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE (SELECT COUNT(*) FROM `t_bank_card` t1 INNER JOIN `t_bank` t2 ON t2.`id` = t1.`bank_id` WHERE t1.`uid` = t.`id` AND t2.`name` = ?) >= ? AND NOT ( EXISTS (SELECT 1 FROM `t_bank_card` t3 INNER JOIN `t_bank` t4 ON t4.`id` = t3.`bank_id` WHERE t3.`uid` = t.`id` AND t4.`name` = ? LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("工商银行(String),2(Long),建设银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .manyJoin(x -> x.bankCards())
                .where(user -> {
                    user.bankCards().where(card -> {
                        card.bank().name().eq("工商银行");
                    }).count().ge(2L);

                    user.bankCards().none(card -> {
                        card.bank().name().eq("建设银行");
                    });
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,COUNT((CASE WHEN t3.`name` = ? THEN ? ELSE NULL END)) AS `__count2__`,(CASE WHEN COUNT((CASE WHEN t3.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__none3__` FROM `t_bank_card` t1 INNER JOIN `t_bank` t3 ON t3.`id` = t1.`bank_id` GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE IFNULL(t2.`__count2__`,0) >= ? AND IFNULL(t2.`__none3__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("工商银行(String),1(Integer),建设银行(String),1(Integer),false(Boolean),true(Boolean),2(Long),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test6() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    //用户的银行卡中第一个开户银行卡是工商银行的
                    user.bankCards().orderBy(x -> x.openTime().asc()).firstElement().bank().name().eq("工商银行");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id`,t2.`open_time` AS `open_time` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t1.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` INNER JOIN `t_bank` t5 ON t5.`id` = t4.`bank_id` WHERE t5.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),工商银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test7() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.name().like("小明");

                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        //用户的银行卡中前两个开户银行卡类型
                        user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).joining(x -> x.type(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,(SELECT GROUP_CONCAT(t1.`type` SEPARATOR ?) FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` ORDER BY t1.`open_time` ASC LIMIT 2) AS `value2` FROM `t_sys_user` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test7_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.name().like("小明");

                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        //用户的银行卡中前两个开户银行卡类型
                        user.bankCards().where(x -> x.type().eq("储蓄卡")).orderBy(x -> x.openTime().asc()).elements(0, 1).where(o -> o.bank().name().eq("建设银行")).joining(x -> x.type(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,(SELECT GROUP_CONCAT(t2.`type` SEPARATOR ?) FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ? ORDER BY t1.`open_time` ASC LIMIT 2) t2 INNER JOIN `t_bank` t3 ON t3.`id` = t2.`bank_id` WHERE t3.`name` = ?) AS `value2` FROM `t_sys_user` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),储蓄卡(String),建设银行(String),%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test7_2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.name().like("小明");

                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        //用户的银行卡中前两个开户银行卡类型
                        user.bankCards().where(x -> x.type().eq("储蓄卡")).orderBy(x -> x.openTime().asc()).elements(0, 1).joining(x -> x.type(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,(SELECT GROUP_CONCAT(t1.`type` SEPARATOR ?) FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ? ORDER BY t1.`open_time` ASC LIMIT 2) AS `value2` FROM `t_sys_user` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),储蓄卡(String),%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test8() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).none(x -> x.bank().name().eq("杭州银行"));
                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        //用户的银行卡中前两个开户银行卡类型
                        user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).joining(x -> x.type(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,(SELECT GROUP_CONCAT(t4.`type` SEPARATOR ?) FROM `t_bank_card` t4 WHERE t4.`uid` = t.`id` ORDER BY t4.`open_time` ASC LIMIT 2) AS `value2` FROM `t_sys_user` t WHERE t.`name` LIKE ? AND NOT ( EXISTS (SELECT 1 FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` ORDER BY t1.`open_time` ASC LIMIT 2) t2 INNER JOIN `t_bank` t3 ON t3.`id` = t2.`bank_id` WHERE t3.`name` = ? LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),%小明%(String),杭州银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test9() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        String queryName = null;
        List<SysBankCard> xmCards = easyEntityQuery.queryable(SysBankCard.class)
                //如果查询条件不符合那么将不会加入到条件中
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(bank_card -> {
                    bank_card.user().name().eq(queryName);
                })
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t", jdbcExecuteAfterArg.getBeforeArg().getSql());

    }

    @Test
    public void test10() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft3<String, String, String>> result = easyEntityQuery.queryable(SysBankCard.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .leftJoin(SysBank.class, (bank_card, bank) -> bank_card.bankId().eq(bank.id()))
                .where((bank_card, bank) -> {
                    bank_card.user().name().eq("小明");
                })
                .select((bank_card, bank) -> Select.DRAFT.of(
                        bank_card.code(),
                        bank_card.user().name(),
                        bank.name()
                )).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`code` AS `value1`,t2.`name` AS `value2`,t1.`name` AS `value3` FROM `t_bank_card` t LEFT JOIN `t_bank` t1 ON t.`bank_id` = t1.`id` LEFT JOIN `t_sys_user` t2 ON t2.`id` = t.`uid` WHERE t2.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("小明(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test11() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(x -> x.bankCards())
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).none(x -> x.bank().name().eq("杭州银行"));
                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        //用户的银行卡中前两个开户银行卡类型
                        user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).joining(x -> x.type(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,t3.`__joining3__` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`uid` AS `uid`,(CASE WHEN COUNT((CASE WHEN t4.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__none2__`,GROUP_CONCAT(t2.`type` SEPARATOR ?) AS `__joining3__` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 ORDER BY t1.`open_time` ASC LIMIT 2) t2 INNER JOIN `t_bank` t4 ON t4.`id` = t2.`bank_id` GROUP BY t2.`uid`) t3 ON t3.`uid` = t.`id` WHERE t.`name` LIKE ? AND IFNULL(t3.`__none2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("杭州银行(String),1(Integer),false(Boolean),true(Boolean),,(String),%小明%(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test12() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(x -> x.bankCards())
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().where(x -> x.type().eq("123")).orderBy(x -> x.openTime().asc()).elements(0, 1).none(x -> x.bank().name().eq("杭州银行"));
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`uid` AS `uid`,(CASE WHEN COUNT((CASE WHEN t4.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__none2__` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`type` = ? ORDER BY t1.`open_time` ASC LIMIT 2) t2 INNER JOIN `t_bank` t4 ON t4.`id` = t2.`bank_id` GROUP BY t2.`uid`) t3 ON t3.`uid` = t.`id` WHERE t.`name` LIKE ? AND IFNULL(t3.`__none2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("杭州银行(String),1(Integer),false(Boolean),true(Boolean),123(String),%小明%(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test13() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(x -> x.bankCards())
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().where(x -> x.type().eq("储蓄卡"))
                            .orderBy(x -> x.openTime().asc())
                            .elements(1, 2)
                            .none(x -> x.bank().name().eq("杭州银行"));
                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        user.bankCards().where(x -> x.type().eq("储蓄卡"))
                                .orderBy(x -> x.openTime().asc())
                                .elements(1, 2).joining(x -> x.bank().id(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,t3.`__joining3__` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`uid` AS `uid`,(CASE WHEN COUNT((CASE WHEN t4.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__none2__`,GROUP_CONCAT(t4.`id` SEPARATOR ?) AS `__joining3__` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`type` = ? ORDER BY t1.`open_time` ASC LIMIT 2 OFFSET 1) t2 INNER JOIN `t_bank` t4 ON t4.`id` = t2.`bank_id` GROUP BY t2.`uid`) t3 ON t3.`uid` = t.`id` WHERE t.`name` LIKE ? AND IFNULL(t3.`__none2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("杭州银行(String),1(Integer),false(Boolean),true(Boolean),,(String),储蓄卡(String),%小明%(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test14() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(x -> x.bankCards())
                .subQueryConfigure(x -> x.bankCards(), query -> query.where(x -> x.type().eq("储蓄卡")).orderBy(x -> x.openTime().asc()))
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().elements(1, 2).none(x -> x.bank().createTime().ge(LocalDateTime.of(2000, 1, 1, 0, 0)));
                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        user.bankCards().elements(1, 2).joining(x -> x.bank().name(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,t3.`__joining3__` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`uid` AS `uid`,(CASE WHEN COUNT((CASE WHEN t4.`create_time` >= ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__none2__`,GROUP_CONCAT(t4.`name` SEPARATOR ?) AS `__joining3__` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`type` = ? ORDER BY t1.`open_time` ASC LIMIT 2 OFFSET 1) t2 INNER JOIN `t_bank` t4 ON t4.`id` = t2.`bank_id` GROUP BY t2.`uid`) t3 ON t3.`uid` = t.`id` WHERE t.`name` LIKE ? AND IFNULL(t3.`__none2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2000-01-01T00:00(LocalDateTime),1(Integer),false(Boolean),true(Boolean),,(String),储蓄卡(String),%小明%(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test15() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(x -> x.bankCards())
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().where(x -> x.type().eq("储蓄卡")).orderBy(x -> x.openTime().asc()).elements(1, 2).none(x -> x.bank().createTime().ge(LocalDateTime.of(2000, 1, 1, 0, 0)));
                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        user.bankCards().where(x -> x.type().eq("储蓄卡")).orderBy(x -> x.openTime().asc()).elements(1, 2).joining(x -> x.bank().name(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,t3.`__joining3__` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`uid` AS `uid`,(CASE WHEN COUNT((CASE WHEN t4.`create_time` >= ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__none2__`,GROUP_CONCAT(t4.`name` SEPARATOR ?) AS `__joining3__` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`type` = ? ORDER BY t1.`open_time` ASC LIMIT 2 OFFSET 1) t2 INNER JOIN `t_bank` t4 ON t4.`id` = t2.`bank_id` GROUP BY t2.`uid`) t3 ON t3.`uid` = t.`id` WHERE t.`name` LIKE ? AND IFNULL(t3.`__none2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2000-01-01T00:00(LocalDateTime),1(Integer),false(Boolean),true(Boolean),,(String),储蓄卡(String),%小明%(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test16() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(x -> x.bankCards())
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().where(x -> x.type().eq("储蓄卡")).orderBy(x -> x.openTime().asc()).elements(0, 2).none(x -> x.bank().createTime().ge(LocalDateTime.of(2000, 1, 1, 0, 0)));
                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        user.bankCards().where(x -> x.type().eq("储蓄卡")).orderBy(x -> x.openTime().asc()).elements(0, 2).joining(x -> x.bank().name(), ",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,t3.`__joining3__` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`uid` AS `uid`,(CASE WHEN COUNT((CASE WHEN t4.`create_time` >= ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__none2__`,GROUP_CONCAT(t4.`name` SEPARATOR ?) AS `__joining3__` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`type` = ? ORDER BY t1.`open_time` ASC LIMIT 3) t2 INNER JOIN `t_bank` t4 ON t4.`id` = t2.`bank_id` GROUP BY t2.`uid`) t3 ON t3.`uid` = t.`id` WHERE t.`name` LIKE ? AND IFNULL(t3.`__none2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2000-01-01T00:00(LocalDateTime),1(Integer),false(Boolean),true(Boolean),,(String),储蓄卡(String),%小明%(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test17() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(x -> x.bankCards())
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 2).none(x -> {
                        x.bank().createTime().ge(LocalDateTime.of(2000, 1, 1, 0, 0));
                        x.type().eq("储蓄卡");
                    });
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`uid` AS `uid`,(CASE WHEN COUNT((CASE WHEN t4.`create_time` >= ? AND t2.`type` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__none2__` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 ORDER BY t1.`open_time` ASC LIMIT 3) t2 INNER JOIN `t_bank` t4 ON t4.`id` = t2.`bank_id` GROUP BY t2.`uid`) t3 ON t3.`uid` = t.`id` WHERE t.`name` LIKE ? AND IFNULL(t3.`__none2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2000-01-01T00:00(LocalDateTime),储蓄卡(String),1(Integer),false(Boolean),true(Boolean),%小明%(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void testQueryBankAndCardImplicitCountTest() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part1<SysBank, Long>> bankAndCounts = easyEntityQuery.queryable(SysBank.class)
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().count()
                )).toList();
        Assert.assertFalse(bankAndCounts.isEmpty());

        for (Part1<SysBank, Long> bankAndCount : bankAndCounts) {
            if ("工商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(3), bankAndCount.getPartColumn1());
            }
            if ("建设银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(2), bankAndCount.getPartColumn1());
            }
            if ("招商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn1());
            }
        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,(SELECT COUNT(*) FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id`) AS `__part__column1` FROM `t_bank` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testQueryBankAndCardExplicitCountTest() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part1<SysBank, Long>> bankAndCounts = easyEntityQuery.queryable(SysBank.class)
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.expression().subQuery(() -> {
                            return bank.expression().subQueryable(SysBankCard.class)
                                    .where(bank_card -> {
                                        bank_card.bankId().eq(bank.id());
                                    })
                                    .selectColumn(bank_card -> bank_card.id().count());
                            //.selectCount()
                        })
                )).toList();
        Assert.assertFalse(bankAndCounts.isEmpty());

        for (Part1<SysBank, Long> bankAndCount : bankAndCounts) {
            if ("工商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(3), bankAndCount.getPartColumn1());
            }
            if ("建设银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(2), bankAndCount.getPartColumn1());
            }
            if ("招商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn1());
            }
        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,(SELECT COUNT(t1.`id`) FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id`) AS `__part__column1` FROM `t_bank` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testQueryBankAndCardImplicitCountToGroupTest() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part1<SysBank, Long>> bankAndCounts = easyEntityQuery.queryable(SysBank.class)
                .subQueryToGroupJoin(bank -> bank.bankCards())
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().count()
                )).toList();
        Assert.assertFalse(bankAndCounts.isEmpty());

        for (Part1<SysBank, Long> bankAndCount : bankAndCounts) {
            if ("工商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(3), bankAndCount.getPartColumn1());
            }
            if ("建设银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(2), bankAndCount.getPartColumn1());
            }
            if ("招商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn1());
            }
        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,IFNULL(t2.`__count2__`,0) AS `__part__column1` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,COUNT(*) AS `__count2__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testQueryBankAndCardImplicitCountTest1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part2<SysBank, Long, Long>> bankAndCounts = easyEntityQuery.queryable(SysBank.class)
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().where(card -> card.type().eq("储蓄卡")).count(),
                        bank.bankCards().where(card -> card.type().eq("信用卡")).count()
                )).toList();
        Assert.assertFalse(bankAndCounts.isEmpty());

        for (Part2<SysBank, Long, Long> bankAndCount : bankAndCounts) {
            if ("工商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(1), bankAndCount.getPartColumn1());
                Assert.assertEquals(Long.valueOf(2), bankAndCount.getPartColumn2());
            }
            if ("建设银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(2), bankAndCount.getPartColumn1());
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn2());
            }
            if ("招商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn1());
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn2());
            }
        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,(SELECT COUNT(*) FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id` AND t1.`type` = ?) AS `__part__column1`,(SELECT COUNT(*) FROM `t_bank_card` t2 WHERE t2.`bank_id` = t.`id` AND t2.`type` = ?) AS `__part__column2` FROM `t_bank` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),信用卡(String),%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testQueryBankAndCardExplicitCountTest1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part2<SysBank, Long, Long>> bankAndCounts = easyEntityQuery.queryable(SysBank.class)
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.expression().subQuery(() -> {
                            return bank.expression().subQueryable(SysBankCard.class)
                                    .where(bank_card -> {
                                        bank_card.bankId().eq(bank.id());
                                        bank_card.type().eq("储蓄卡");
                                    })
                                    .selectColumn(bank_card -> bank_card.id().count());
                            //.selectCount()
                        }),
                        bank.expression().subQuery(() -> {
                            return bank.expression().subQueryable(SysBankCard.class)
                                    .where(bank_card -> {
                                        bank_card.bankId().eq(bank.id());
                                        bank_card.type().eq("信用卡");
                                    })
                                    .selectColumn(bank_card -> bank_card.id().count());
                            //.selectCount()
                        })
                )).toList();
        Assert.assertFalse(bankAndCounts.isEmpty());

        for (Part2<SysBank, Long, Long> bankAndCount : bankAndCounts) {
            if ("工商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(1), bankAndCount.getPartColumn1());
                Assert.assertEquals(Long.valueOf(2), bankAndCount.getPartColumn2());
            }
            if ("建设银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(2), bankAndCount.getPartColumn1());
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn2());
            }
            if ("招商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn1());
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn2());
            }
        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,(SELECT COUNT(t1.`id`) FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id` AND t1.`type` = ?) AS `__part__column1`,(SELECT COUNT(t3.`id`) FROM `t_bank_card` t3 WHERE t3.`bank_id` = t.`id` AND t3.`type` = ?) AS `__part__column2` FROM `t_bank` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),信用卡(String),%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testQueryBankAndCardImplicitCountToGroupTest1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part2<SysBank, Long, Long>> bankAndCounts = easyEntityQuery.queryable(SysBank.class)
                .subQueryToGroupJoin(bank -> bank.bankCards())
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().where(card -> card.type().eq("储蓄卡")).count(),
                        bank.bankCards().where(card -> card.type().eq("信用卡")).count()
                )).toList();
        Assert.assertFalse(bankAndCounts.isEmpty());

        for (Part2<SysBank, Long, Long> bankAndCount : bankAndCounts) {
            if ("工商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(1), bankAndCount.getPartColumn1());
                Assert.assertEquals(Long.valueOf(2), bankAndCount.getPartColumn2());
            }
            if ("建设银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(2), bankAndCount.getPartColumn1());
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn2());
            }
            if ("招商银行".equals(bankAndCount.getEntity().getName())) {
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn1());
                Assert.assertEquals(Long.valueOf(0), bankAndCount.getPartColumn2());
            }
        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,IFNULL(t2.`__count2__`,0) AS `__part__column1`,IFNULL(t2.`__count3__`,0) AS `__part__column2` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE NULL END)) AS `__count2__`,COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE NULL END)) AS `__count3__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),1(Integer),信用卡(String),1(Integer),%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testQueryBankAndCardImplicitCountTest2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part1<SysBank, Boolean>> bankCardTop2s = easyEntityQuery.queryable(SysBank.class)
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().orderBy(o -> o.openTime().asc()).elements(0, 1)
                                .where(card -> card.openTime().ge(LocalDateTime.of(2002, 1, 1, 0, 0)))
                                .noneValue()
                )).toList();
        Assert.assertFalse(bankCardTop2s.isEmpty());

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,(NOT EXISTS((SELECT 1 FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id` ORDER BY t1.`open_time` ASC LIMIT 2) t2 WHERE t2.`open_time` >= ? LIMIT 1))) AS `__part__column1` FROM `t_bank` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2002-01-01T00:00(LocalDateTime),%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testQueryBankAndCardImplicitCountTest3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part1<SysBank, Boolean>> bankCardTop2s = easyEntityQuery.queryable(SysBank.class)
                .subQueryToGroupJoin(s -> s.bankCards())
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().orderBy(o -> o.openTime().asc()).elements(0, 1)
                                .where(card -> card.openTime().ge(LocalDateTime.of(2002, 1, 1, 0, 0)))
                                .noneValue()
                )).toList();
        Assert.assertFalse(bankCardTop2s.isEmpty());

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time`,IFNULL(t3.`__none2__`,?) AS `__part__column1` FROM `t_bank` t LEFT JOIN (SELECT t2.`bank_id` AS `bankId`,(CASE WHEN COUNT((CASE WHEN t2.`open_time` >= ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__none2__` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 ORDER BY t1.`open_time` ASC LIMIT 2) t2 GROUP BY t2.`bank_id`) t3 ON t3.`bankId` = t.`id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("true(Boolean),2002-01-01T00:00(LocalDateTime),1(Integer),false(Boolean),true(Boolean),%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testQueryBankAndCardImplicitCountTest4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft3<String, String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.age().eq(18);
                })
                .select(user -> Select.DRAFT.of(
                        user.name(),
                        user.bankCards().orderBy(o -> o.openTime().asc()).firstElement().code(),
                        user.bankCards().orderBy(o -> o.openTime().asc()).firstElement().bank().name()
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,t4.`code` AS `value2`,t5.`name` AS `value3` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id`,t2.`open_time` AS `open_time` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t1.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` INNER JOIN `t_bank` t5 ON t5.`id` = t4.`bank_id` WHERE t.`age` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),18(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void whereSubQuery1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.bankCards().any(card -> card.type().eq("储蓄卡"));
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void whereSubQuery2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().any(card -> card.type().eq("储蓄卡"));
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,(CASE WHEN COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `t_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void whereSubQuery3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(user -> user.bankCards())
                .where(user -> {

                    user.expression().exists(() -> {
                        return user.expression().subQueryable(SysBankCard.class)
                                .where(bank_card -> {
                                    bank_card.uid().eq(user.id());
                                    bank_card.type().eq("储蓄卡");
                                });
                    });
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void whereSubQuery4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                .subQueryToGroupJoin(user -> user.bankCards())
                .where(user -> {

                    user.bankCards().where(card -> card.type().eq("储蓄卡")).count().eq(2L);

                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE NULL END)) AS `__count2__` FROM `t_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE IFNULL(t2.`__count2__`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void whereSubQuery5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {

                    user.bankCards().where(card -> card.type().eq("储蓄卡")).count().eq(2L);

                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE (SELECT COUNT(*) FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void whereSubQuery6() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {

                    user.expression().subQuery(() -> {
                        return user.expression().subQueryable(SysBankCard.class)
                                .where(bank_card -> {
                                    bank_card.uid().eq(user.id());
                                    bank_card.type().eq("储蓄卡");
                                }).selectCount();
                    }).eq(2L);


                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE (SELECT COUNT(*) FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void whereSubQuery7() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.expression().constant(2L).eq(
                            user.expression().subQueryable(SysBankCard.class)
                                    .where(bank_card -> {
                                        bank_card.uid().eq(user.id());
                                        bank_card.type().eq("储蓄卡");
                                    }).selectCount()
                    );

                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE ? = (SELECT COUNT(*) FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Long),储蓄卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void fromSubQuery1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<String, String, String>> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.name().like("123");
                })
                .select(user -> Select.DRAFT.of(
                        user.name(),
                        user.id(),
                        user.phone()
                )).where(user -> {
                    user.value3().eq("1234567");
                }).orderBy(user -> user.value1().asc())
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`value1` AS `value1`,t1.`value2` AS `value2`,t1.`value3` AS `value3` FROM (SELECT t.`name` AS `value1`,t.`id` AS `value2`,t.`phone` AS `value3` FROM `t_sys_user` t WHERE t.`name` LIKE ?) t1 WHERE t1.`value3` = ? ORDER BY t1.`value1` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),1234567(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void fromSubQuery2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyUserVO> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.name().like("123");
                })
                .select(user -> new MyUserVOProxy()
                        .vo1().set(user.name())
                        .vo2().set(user.id())
                        .vo3().set(user.phone())
                        .bankCardCount().set(user.bankCards().count()) // 银行卡数量
                ).where(user -> {
                    user.bankCardCount().gt(1L);
                }).orderBy(user -> user.bankCardCount().asc())
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`vo1` AS `vo1`,t2.`vo2` AS `vo2`,t2.`vo3` AS `vo3`,t2.`bank_card_count` AS `bank_card_count` FROM (SELECT t.`name` AS `vo1`,t.`id` AS `vo2`,t.`phone` AS `vo3`,(SELECT COUNT(*) FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id`) AS `bank_card_count` FROM `t_sys_user` t WHERE t.`name` LIKE ?) t2 WHERE t2.`bank_card_count` > ? ORDER BY t2.`bank_card_count` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void includeSubQuery2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<MyUserVO> list = easyEntityQuery.queryable(SysUser.class)
//                .includes(user -> user.bankCards())
                .where(user -> {
//                    user.name().like("123");
                })
                .select(user -> new MyUserVOProxy()
                                .vo1().set(user.name())
                                .vo2().set(user.id())
                                .vo3().set(user.phone())
//                        .bankCardCount().set(user.bankCards().count()) // 银行卡数量
                                .cards().set(user.bankCards(), (self, target) -> {
                                    self.type().set(target.code());
                                    self.code().set(target.type());
                                })
                ).toList();

        listenerContextManager.clear();
//        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`name` AS `vo1`,t.`id` AS `vo2`,t.`phone` AS `vo3`,t.`id` AS `__relation__id` FROM `t_sys_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`code` AS `type`,t.`type` AS `code`,t.`uid` AS `__relation__uid` FROM `t_bank_card` t WHERE t.`uid` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u1(String),u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }

    }

    @Test
    public void includeSubQuery3() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<MyUserVO> list = easyEntityQuery.queryable(SysUser.class)
                .select(user -> new MyUserVOProxy()
                        .vo1().set(user.name())
                        .vo2().set(user.id())
                        .vo3().set(user.phone())
                        .cards().set(user.bankCards().where(bc -> bc.type().eq("储蓄卡")), (self, target) -> {
                            self.type().set(target.code());
                            self.code().set(target.bank().name());
                        })
                ).toList();

        listenerContextManager.clear();
//        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`name` AS `vo1`,t.`id` AS `vo2`,t.`phone` AS `vo3`,t.`id` AS `__relation__id` FROM `t_sys_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`code` AS `type`,t1.`name` AS `code`,t.`uid` AS `__relation__uid` FROM `t_bank_card` t INNER JOIN `t_bank` t1 ON t1.`id` = t.`bank_id` WHERE t.`type` = ? AND t.`uid` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),u1(String),u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }

    }

    @Test
    public void includeSubQuery4() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<MyUserVO> list = easyEntityQuery.queryable(SysUser.class)
                .select(user -> new MyUserVOProxy()
                        .vo1().set(user.name())
                        .vo2().set(user.id())
                        .vo3().set(user.phone())
                        .cards().set(user.bankCards().where(bc -> bc.type().eq("储蓄卡")), (self, target) -> {
                            self.type().set(target.code());
                            self.code().set(target.bank().bankCards().max(x -> x.openTime().format("yyyy-MM-dd")));
                        })
                ).toList();

        listenerContextManager.clear();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`name` AS `vo1`,t.`id` AS `vo2`,t.`phone` AS `vo3`,t.`id` AS `__relation__id` FROM `t_sys_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`code` AS `type`,(SELECT MAX(DATE_FORMAT(t2.`open_time`,'%Y-%m-%d')) FROM `t_bank_card` t2 WHERE t2.`bank_id` = t1.`id`) AS `code`,t.`uid` AS `__relation__uid` FROM `t_bank_card` t INNER JOIN `t_bank` t1 ON t1.`id` = t.`bank_id` WHERE t.`type` = ? AND t.`uid` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),u1(String),u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }

    }

    @Test
    public void includeSubQuery5() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .select(user -> new SysUserProxy()
                        .selectAll(user)
                        .bankCards().set(user.bankCards(), (self, target) -> {
                            self.selectAll(target);
                            self.bank().set(target.bank());
                        })
                ).toList();

        listenerContextManager.clear();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t WHERE t.`uid` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u1(String),u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }

    }

    @Test
    public void testExtraFilter() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<SysUserDTO> list = easyEntityQuery.queryable(SysUser.class)
                .selectAutoInclude(SysUserDTO.class)
                .toList();

        listenerContextManager.clear();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t WHERE t.`type` = ? AND t.`uid` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),u1(String),u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testExtraFilter2() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);


        UUID uuid = UUID.randomUUID();
        System.out.println("uuid:" + uuid);
        List<SysUserDTO2> list = easyEntityQuery.queryable(SysUser.class)
                .configure(o -> {
                    o.setConfigureArgument(uuid);
                })
                .selectAutoInclude(SysUserDTO2.class)
                .toList();

        listenerContextManager.clear();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT IFNULL(t2.`__count2__`,0) AS `card_count`,t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,COUNT(*) AS `__count2__` FROM `t_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time`,DATE_FORMAT(t1.`create_time`,'%Y-%m-%d') AS `bank_name_or123` FROM `t_bank_card` t INNER JOIN `t_bank` t1 ON t1.`id` = t.`bank_id` WHERE t.`type` = ? AND t.`uid` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),u1(String),u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testExtraFilter3() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<Draft2Proxy<String, Long>, Draft2<String, Long>> groupBankCardCountQuery = easyEntityQuery.queryable(SysBankCard.class)
                .groupBy(bank_card -> GroupKeys.of(bank_card.bankId()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),//value1
                        group.count()//value2
                ));

        List<Draft2<String, Long>> list1 = easyEntityQuery.queryable(SysBank.class)
                .leftJoin(groupBankCardCountQuery, (bank, cardGroup) -> bank.id().eq(cardGroup.value1()))
                .select((bank, cardGroup) -> Select.DRAFT.of(
                        bank.name(),
                        cardGroup.value2()
                )).toList();

        listenerContextManager.clear();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`name` AS `value1`,t3.`value2` AS `value2` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `value1`,COUNT(*) AS `value2` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t3 ON t.`id` = t3.`value1`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testExtraFilter4() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<Draft2Proxy<String, Long>, Draft2<String, Long>> groupBankCardCountQuery = easyEntityQuery.queryable(SysBankCard.class)
                .groupBy(bank_card -> GroupKeys.of(bank_card.bankId()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),//value1
                        group.count()//value2
                )).toCteAs();

        List<Draft2<String, Long>> list1 = easyEntityQuery.queryable(SysBank.class)
                .leftJoin(groupBankCardCountQuery, (bank, cardGroup) -> bank.id().eq(cardGroup.value1()))
                .select((bank, cardGroup) -> Select.DRAFT.of(
                        bank.name(),
                        cardGroup.value2()
                )).toList();

        listenerContextManager.clear();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("WITH `with_Draft2` AS (SELECT t1.`bank_id` AS `value1`,COUNT(*) AS `value2` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`)  SELECT t.`name` AS `value1`,t3.`value2` AS `value2` FROM `t_bank` t LEFT JOIN `with_Draft2` t3 ON t.`id` = t3.`value1`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testExtraFilter5() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, Long>> list1 = easyEntityQuery.queryable(SysBank.class)
                .subQueryToGroupJoin(bank -> bank.bankCards())
                .select(bank -> Select.DRAFT.of(
                        bank.name(),
                        bank.bankCards().count()
                )).toList();

        listenerContextManager.clear();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`name` AS `value1`,IFNULL(t2.`__count2__`,0) AS `value2` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,COUNT(*) AS `__count2__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testExtraDTO() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        String arg = "myArg";
        List<SysBankDTO> list = easyEntityQuery.queryable(SysBank.class)
                .configure(o -> {
                    o.setConfigureArgument(arg);
                })
                .selectAutoInclude(SysBankDTO.class)
                .toList();
        String jsonString = JSON.toJSONString(list, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteNullStringAsEmpty);
        System.out.println(jsonString);

        listenerContextManager.clear();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t WHERE t.`bank_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time`,IFNULL(t2.`__count2__`,0) AS `book_count`,t6.`name` AS `book_name`,t6.`price` AS `book_price` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,COUNT(*) AS `__count2__` FROM `t_sys_user_book` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` LEFT JOIN (SELECT t4.`id` AS `id`,t4.`name` AS `name`,t4.`uid` AS `uid`,t4.`price` AS `price` FROM (SELECT t3.`id`,t3.`name`,t3.`uid`,t3.`price`,(ROW_NUMBER() OVER (PARTITION BY t3.`uid` ORDER BY t3.`price` DESC)) AS `__row__` FROM `t_sys_user_book` t3) t4 WHERE t4.`__row__` = ?) t6 ON t6.`uid` = t.`id` WHERE t.`name` <> ? AND t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),myArg(String),u1(String),u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }


    @Test
    public void testaa1() {
//        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class).where(bank_card -> bank_card.id().isNotNull()).toList();
        AtomicInteger atomicInteger = M8Interceptor.count.get();
        atomicInteger.set(0);
        easyEntityQuery.updatable(SysBankCard.class)
                .setColumns(bank_card -> bank_card.id().set("123123"))
                .where(bank_card -> {
                    bank_card.id().eq("1");
                    bank_card.id().isNull();
                }).executeRows();
        int i = atomicInteger.get();
        Assert.assertEquals(1, i);
        atomicInteger.set(0);

        easyEntityQuery.deletable(SysBankCard.class)
                .where(bank_card -> {
                    bank_card.id().eq("1");
                    bank_card.id().isNull();
                }).executeRows();
        int i1 = atomicInteger.get();
        Assert.assertEquals(1, i1);
    }


    @Test
    public void testFlatTest() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(SysBank.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {

                    bank.bankCards().flatElement().code().eq("123");
                    bank.bankCards().flatElement().type().eq("储蓄卡");

                    //在隐式group上下两个是一样的功能
//                    bank.bankCards().any(card->{
//                        card.code().eq("123");
//                        card.type().eq("储蓄卡");
//                    });
                })
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,(CASE WHEN COUNT((CASE WHEN t1.`code` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__`,(CASE WHEN COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any3__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ? AND IFNULL(t2.`__any3__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),true(Boolean),false(Boolean),储蓄卡(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void dynamicPartition() {
        boolean likeICBC = false;
        String name = "小明";
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    if (EasyStringUtil.isNotBlank(name)) {
                        user.name().like(name);
                    }
                    if (likeICBC) {
                        //用户的银行卡中第一个开户银行卡是工商银行的
                        user.bankCards().orderBy(x -> x.openTime().asc()).firstElement().bank().name().eq("工商银行");
                    }
                }).toList();
    }


    @Test
    public void partitionBy() {
        List<Part1<SysUser, String>> list = easyEntityQuery.queryable(SysUser.class)
                .select(user -> Select.PART.of(
                        user,
                        user.bankCards().orderBy(card -> card.openTime().asc()).firstElement().code()
                )).toList();


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<SysBankCardDTO> list1 = easyEntityQuery.queryable(SysBankCard.class)
                .configure(o -> o.setGroupSize(1))
                .selectAutoInclude(SysBankCardDTO.class)
                .toList();
        String jsonString = JSON.toJSONString(list, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteNullStringAsEmpty);
        System.out.println(jsonString);

        listenerContextManager.clear();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT `phone`,`name`,`id` AS `__relation__id` FROM `t_sys_user` WHERE `id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT `phone`,`name`,`id` AS `__relation__id` FROM `t_sys_user` WHERE `id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testFirstElementInnerJoin() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list1 = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {

                    user.bankCards3()
                            .orderBy(o -> o.openTime().asc())
                            .firstElement().type().eq("储蓄卡");
                }).toList();

        listenerContextManager.clear();


        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t INNER JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id`,t2.`open_time` AS `open_time` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t1.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` WHERE t4.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),储蓄卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testUserAutoSubQueryToGroupJoin() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<Part1<SysUser, Long>> list = easyEntityQuery.queryable(SysUser.class)
                .select(user -> Select.PART.of(
                        user,
                        user.bankCards2().where(s -> s.type().eq("储蓄卡")).count()
                )).toList();

        listenerContextManager.clear();


        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time`,IFNULL(t2.`__count2__`,0) AS `__part__column1` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE NULL END)) AS `__count2__` FROM `t_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testUserAutoSubQueryToGroupJoin2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);



        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {

                    user.bankCards2()
                            .orderBy(o -> o.openTime().asc())
                            .firstElement().type().eq("储蓄卡");


                    user.bankCards2()
                            .orderBy(o -> o.openTime().asc())
                            .firstElement().bank().name().eq("工商银行");
                }).toList();

        listenerContextManager.clear();


        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id`,t2.`open_time` AS `open_time` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t1.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` INNER JOIN `t_bank` t5 ON t5.`id` = t4.`bank_id` WHERE t4.`type` = ? AND t5.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),储蓄卡(String),工商银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testFirst(){


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<Part1<SysUser, String>> userParts = easyEntityQuery.queryable(SysUser.class)
                .where(user -> user.age().gt(18))
                .select(user -> Select.PART.of(
                        user,
                        user.bankCards().orderBy(card -> card.openTime().asc()).firstElement().code()
                )).toList();

        for (Part1<SysUser, String> userPart : userParts) {
            SysUser user = userPart.getEntity();
            String firstCardCode = userPart.getPartColumn1();
        }

        listenerContextManager.clear();


        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time`,t4.`code` AS `__part__column1` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id`,t2.`open_time` AS `open_time` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t1.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` WHERE t.`age` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),18(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFirst2(){

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> userNameAndFirstCardCodes = easyEntityQuery.queryable(SysUser.class)
                .where(user -> user.age().gt(18))
                .select(user -> Select.DRAFT.of(
                        user.name(),
                        user.bankCards().orderBy(card -> card.openTime().asc()).firstElement().code()
                )).toList();
        for (Draft2<String, String> userNameAndFirstCardCode : userNameAndFirstCardCodes) {
            String userName = userNameAndFirstCardCode.getValue1();
            String firstCardCode = userNameAndFirstCardCode.getValue2();
        }
        listenerContextManager.clear();


        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("SELECT t.`name` AS `value1`,t4.`code` AS `value2` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id`,t2.`open_time` AS `open_time` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t1.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` WHERE t.`age` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),18(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

//    @Test
//    public void partitionBy2() {
//        EntityQueryable<Draft3Proxy<String, String, Long>, Draft3<String, String, Long>> cteAs = easyEntityQuery.queryable(SysBankCard.class)
//                .select(bank_card -> Select.DRAFT.of(
//                        bank_card.uid(),
//                        bank_card.code(),
//                        bank_card.expression().rowNumberOver().partitionBy(bank_card.uid()).orderBy(bank_card.openTime())
//                )).toCteAs();
//
//        List<Part1<SysUser, String>> list = easyEntityQuery.queryable(SysUser.class)
//                .leftJoin(cteAs, (user, cte) -> user.id().eq(cte.value1()))
//                .where((user, cte) -> cte.value3().eq(1L))
//                .select((user, cte) -> Select.PART.of(
//                        user,
//                        cte.value2()
//                )).toList();
//    }
}
