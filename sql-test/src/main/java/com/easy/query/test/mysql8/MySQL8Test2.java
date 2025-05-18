package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.part.Part1;
import com.easy.query.core.proxy.part.proxy.Part1Proxy;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.common.MD5Util;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8TestIndex;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.proxy.SysBankProxy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/4/27 15:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQL8Test2 extends BaseTest {
    @Test
    public void test() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(M8TestIndex.class));
        codeFirstCommand.executeWithTransaction(s -> {
            s.commit();
        });
        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.dropTableCommand(Arrays.asList(M8TestIndex.class));
        codeFirstCommand1.executeWithTransaction(s -> s.commit());

        CodeFirstCommand codeFirstCommand2 = databaseCodeFirst.syncTableCommand(Arrays.asList(M8TestIndex.class));
        codeFirstCommand2.executeWithTransaction(s -> {
            String md5Hash = MD5Util.getMD5Hash(s.getSQL());
            System.out.println(s.getSQL());
            Assert.assertEquals("6aa967f09881fb7edbde7bb159a1bf93", md5Hash);
        });
//        List<M8TestIndex> list = easyEntityQuery.queryable(M8TestIndex.class)
//                .toList();
    }

    @Test
    public void test1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part1<SysBank, Long>> banks = easyEntityQuery.queryable(SysBank.class)
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().count()
                ))
                .leftJoin(SysBankCard.class, (bank, bankCard) -> {
                    bank.entityTable().id().eq(bankCard.bankId());
                })
                .where((bank, bank_card) -> {
                    bank.partColumn1().eq(bank_card.type());
                    SysBankProxy sysBankProxy = bank.entityTable();
                    sysBankProxy.bankCards().any();
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`id`,t2.`name`,t2.`create_time`,t2.`__part__column1` AS `__part__column1` FROM (SELECT t.`id`,t.`name`,t.`create_time`,(SELECT COUNT(*) FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id`) AS `__part__column1` FROM `t_bank` t WHERE t.`name` LIKE ?) t2 LEFT JOIN `t_bank_card` t3 ON t2.`id` = t3.`bank_id` WHERE t2.`__part__column1` = t3.`type` AND EXISTS (SELECT 1 FROM `t_bank_card` t4 WHERE t4.`bank_id` = t2.`id` LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void test2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Part1<SysBank, Long>> banks = easyEntityQuery.queryable(SysBank.class)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().count()
                ))
                .leftJoin(SysBankCard.class, (bank, bankCard) -> {
                    bank.entityTable().id().eq(bankCard.bankId());
                })
                .where((bank, bank_card) -> {
                    bank.partColumn1().eq(bank_card.type());
                    SysBankProxy sysBankProxy = bank.entityTable();
                    sysBankProxy.bankCards().any();
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t3.`id`,t3.`name`,t3.`create_time`,t3.`__part__column1` AS `__part__column1` FROM (SELECT t.`id`,t.`name`,t.`create_time`,IFNULL(t2.`__count2__`,0) AS `__part__column1` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,COUNT(*) AS `__count2__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id` WHERE t.`name` LIKE ?) t3 LEFT JOIN `t_bank_card` t4 ON t3.`id` = t4.`bank_id` LEFT JOIN (SELECT t5.`bank_id` AS `bankId`,(CASE WHEN COUNT(*) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `t_bank_card` t5 GROUP BY t5.`bank_id`) t6 ON t6.`bankId` = t3.`id` WHERE t3.`__part__column1` = t4.`type` AND IFNULL(t6.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<Part1Proxy<NumberTypeExpression<Long>, Long, SysBankProxy, SysBank>, Part1<SysBank, Long>> queryable = easyEntityQuery.queryable(SysBank.class)
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().count()
                ));
        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .leftJoin(queryable, (a, b) -> a.bankId().eq(b.entityTable().id()))
                .where((a, b) -> {
                    b.entityTable().bankCards().any();
                    b.partColumn1().eq(a.type());
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t LEFT JOIN (SELECT t1.`id`,t1.`name`,t1.`create_time`,(SELECT COUNT(*) FROM `t_bank_card` t2 WHERE t2.`bank_id` = t1.`id`) AS `__part__column1` FROM `t_bank` t1 WHERE t1.`name` LIKE ?) t4 ON t.`bank_id` = t4.`id` WHERE EXISTS (SELECT 1 FROM `t_bank_card` t5 WHERE t5.`bank_id` = t4.`id` LIMIT 1) AND t4.`__part__column1` = t.`type`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<Part1Proxy<NumberTypeExpression<Long>, Long, SysBankProxy, SysBank>, Part1<SysBank, Long>> queryable = easyEntityQuery.queryable(SysBank.class)
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().count()
                ));
        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .leftJoin(queryable, (a, b) -> a.bankId().eq(b.entityTable().id()))
                .where((a, b) -> {
                    b.entityTable().bankCards().any();
                    b.partColumn1().eq(a.type());
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t LEFT JOIN (SELECT t1.`id`,t1.`name`,t1.`create_time`,(SELECT COUNT(*) FROM `t_bank_card` t2 WHERE t2.`bank_id` = t1.`id`) AS `__part__column1` FROM `t_bank` t1 WHERE t1.`name` LIKE ?) t4 ON t.`bank_id` = t4.`id` LEFT JOIN (SELECT t5.`bank_id` AS `bankId`,(CASE WHEN COUNT(*) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `t_bank_card` t5 GROUP BY t5.`bank_id`) t6 ON t6.`bankId` = t4.`id` WHERE IFNULL(t6.`__any2__`,?) = ? AND t4.`__part__column1` = t.`type`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void test5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<Part1Proxy<NumberTypeExpression<Long>, Long, SysBankProxy, SysBank>, Part1<SysBank, Long>> queryable = easyEntityQuery.queryable(SysBank.class)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().count()
                ));
        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .leftJoin(queryable, (a, b) -> a.bankId().eq(b.entityTable().id()))
                .where((a, b) -> {
                    b.entityTable().bankCards().any();
                    b.partColumn1().eq(a.type());
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t LEFT JOIN (SELECT t1.`id`,t1.`name`,t1.`create_time`,IFNULL(t3.`__count2__`,0) AS `__part__column1` FROM `t_bank` t1 LEFT JOIN (SELECT t2.`bank_id` AS `bankId`,COUNT(*) AS `__count2__` FROM `t_bank_card` t2 GROUP BY t2.`bank_id`) t3 ON t3.`bankId` = t1.`id` WHERE t1.`name` LIKE ?) t5 ON t.`bank_id` = t5.`id` LEFT JOIN (SELECT t6.`bank_id` AS `bankId`,(CASE WHEN COUNT(*) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `t_bank_card` t6 GROUP BY t6.`bank_id`) t7 ON t7.`bankId` = t5.`id` WHERE IFNULL(t7.`__any2__`,?) = ? AND t5.`__part__column1` = t.`type`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public  void testJoin(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .where(bank_card -> {
                    bank_card.bank().name().eq("ICBC");
                    bank_card.user().name().eq("小明");
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t INNER JOIN `t_bank` t1 ON t1.`id` = t.`bank_id` LEFT JOIN `t_sys_user` t2 ON t2.`id` = t.`uid` WHERE t1.`name` = ? AND t2.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("ICBC(String),小明(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public  void testDelete(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.deletable(SysBank.class)
                .where(bank -> {
                    bank.name().eq("123");
                }).executeRows();
        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .where(bank_card -> {
                    bank_card.bank().name().eq("ICBC");
                    bank_card.user().name().eq("小明");
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t INNER JOIN `t_bank` t1 ON t1.`id` = t.`bank_id` LEFT JOIN `t_sys_user` t2 ON t2.`id` = t.`uid` WHERE t1.`name` = ? AND t2.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("ICBC(String),小明(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

  /*  @Test
     public void test111(){
         var rankedSnapshots = easyEntityQuery.queryable(SysBankCard.class)
                 .leftJoin(SysUser.class, (bank_card, user) -> bank_card.uid().eq(user.id()))
                 .select((bank_card, user) -> Select.DRAFT.of(
                         bank_card.code(),
                         bank_card.type(),
                         user.name(),
                         bank_card.expression().rowNumberOver().partitionBy(bank_card.type()).orderBy(bank_card.openTime())
                 )).toCteAs("RankedSnapshots");
         List<Draft2<String, Long>> list = rankedSnapshots.cloneQueryable().where(s -> s.value4().eq(1L)).select(a -> a).toCteAs("lateSnapshot")
                 .leftJoin(rankedSnapshots.cloneQueryable().where(s -> s.value4().eq(1L)), (lateSnapshot1, previousSnapshot1) -> lateSnapshot1.value1().eq(previousSnapshot1.value1()))
                 .select((a, b) -> Select.DRAFT.of(
                         a.value3(),
                         b.value4()
                 )).toList();
     }*/

}
