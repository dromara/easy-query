package com.easy.query.test.mysql8;


import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/4/3 20:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8BankTest extends BaseTest {
    @Before
    public void before() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(SysUser.class, SysBankCard.class, SysBank.class));
        codeFirstCommand.executeWithTransaction(s -> s.commit());

    }

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


        List<SysBankCard> list1 = easyEntityQuery.queryable(SysBankCard.class)
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
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,COUNT((CASE WHEN t3.`name` = ? THEN ? ELSE ? END)) AS `__count2__`,(CASE WHEN COUNT((CASE WHEN t3.`name` = ? THEN ? ELSE ? END)) > 0 THEN ? ELSE ? END) AS `__none3__` FROM `t_bank_card` t1 INNER JOIN `t_bank` t3 ON t3.`id` = t1.`bank_id` GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE IFNULL(t2.`__count2__`,0) >= ? AND IFNULL(t2.`__none3__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("工商银行(String),1(Integer),null(null),建设银行(String),1(Integer),null(null),false(Boolean),true(Boolean),2(Long),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

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
                        user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).joining(x -> x.type(),",")
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
                        user.bankCards().where(x->x.type().eq("储蓄卡")).orderBy(x -> x.openTime().asc()).elements(0, 1).where(o->o.bank().name().eq("建设银行")).joining(x -> x.type(),",")
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
                        user.bankCards().where(x->x.type().eq("储蓄卡")).orderBy(x -> x.openTime().asc()).elements(0, 1).joining(x -> x.type(),",")
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
                    user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).none(x->x.bank().name().eq("杭州银行"));
                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        //用户的银行卡中前两个开户银行卡类型
                        user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).joining(x -> x.type(),",")
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


        String queryName=null;
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
                .leftJoin(SysBank.class,(bank_card, bank) -> bank_card.bankId().eq(bank.id()))
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
                .manyJoin(x->x.bankCards())
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).none(x->x.bank().name().eq("杭州银行"));
                }).select(user -> Select.DRAFT.of(
                        user.name(),
                        //用户的银行卡中前两个开户银行卡类型
                        user.bankCards().orderBy(x -> x.openTime().asc()).elements(0, 1).joining(x -> x.type(),",")
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,(SELECT GROUP_CONCAT(t4.`type` SEPARATOR ?) FROM `t_bank_card` t4 WHERE t4.`uid` = t.`id` ORDER BY t4.`open_time` ASC LIMIT 2) AS `value2` FROM `t_sys_user` t WHERE t.`name` LIKE ? AND NOT ( EXISTS (SELECT 1 FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` ORDER BY t1.`open_time` ASC LIMIT 2) t2 INNER JOIN `t_bank` t3 ON t3.`id` = t2.`bank_id` WHERE t3.`name` = ? LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),%小明%(String),杭州银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test12() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        easyEntityQuery.queryable(SysUser.class)
//                .manyJoin(x->x.bankCards())
                .where(user -> {
                    user.name().like("小明");
                    user.bankCards().where(x->x.type().eq("123")).orderBy(x -> x.openTime().asc()).elements(0, 1).none(x->x.bank().name().eq("杭州银行"));
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE t.`name` LIKE ? AND NOT ( EXISTS (SELECT 1 FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time` FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ? ORDER BY t1.`open_time` ASC LIMIT 2) t2 INNER JOIN `t_bank` t3 ON t3.`id` = t2.`bank_id` WHERE t3.`name` = ? LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),123(String),杭州银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
