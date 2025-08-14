package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.part.Part1;
import com.easy.query.core.proxy.part.proxy.Part1Proxy;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.common.MD5Util;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8TestIndex;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.bank.proxy.SysBankProxy;
import com.easy.query.test.mysql8.view.TreeC;
import com.easy.query.test.mysql8.view.proxy.TreeCProxy;
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
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
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
        Assert.assertEquals("SELECT t3.`id`,t3.`name`,t3.`create_time`,t3.`__part__column1` AS `__part__column1` FROM (SELECT t.`id`,t.`name`,t.`create_time`,IFNULL(t2.`__count2__`,0) AS `__part__column1` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,COUNT(*) AS `__count2__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id` WHERE t.`name` LIKE ?) t3 LEFT JOIN `t_bank_card` t4 ON t3.`id` = t4.`bank_id` LEFT JOIN (SELECT t5.`bank_id` AS `bankId`,(COUNT(*) > 0) AS `__any2__` FROM `t_bank_card` t5 GROUP BY t5.`bank_id`) t6 ON t6.`bankId` = t3.`id` WHERE t3.`__part__column1` = t4.`type` AND IFNULL(t6.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

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
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .leftJoin(queryable, (a, b) -> a.bankId().eq(b.entityTable().id()))
                .where((a, b) -> {
                    b.entityTable().bankCards().any();
                    b.partColumn1().eq(a.type());
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t LEFT JOIN (SELECT t1.`id`,t1.`name`,t1.`create_time`,(SELECT COUNT(*) FROM `t_bank_card` t2 WHERE t2.`bank_id` = t1.`id`) AS `__part__column1` FROM `t_bank` t1 WHERE t1.`name` LIKE ?) t4 ON t.`bank_id` = t4.`id` LEFT JOIN (SELECT t5.`bank_id` AS `bankId`,(COUNT(*) > 0) AS `__any2__` FROM `t_bank_card` t5 GROUP BY t5.`bank_id`) t6 ON t6.`bankId` = t4.`id` WHERE IFNULL(t6.`__any2__`,?) = ? AND t4.`__part__column1` = t.`type`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void test5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<Part1Proxy<NumberTypeExpression<Long>, Long, SysBankProxy, SysBank>, Part1<SysBank, Long>> queryable = easyEntityQuery.queryable(SysBank.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().like("银行");
                })
                .select(bank -> Select.PART.of(
                        bank,
                        bank.bankCards().count()
                ));
        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .leftJoin(queryable, (a, b) -> a.bankId().eq(b.entityTable().id()))
                .where((a, b) -> {
                    b.entityTable().bankCards().any();
                    b.partColumn1().eq(a.type());
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t LEFT JOIN (SELECT t1.`id`,t1.`name`,t1.`create_time`,IFNULL(t3.`__count2__`,0) AS `__part__column1` FROM `t_bank` t1 LEFT JOIN (SELECT t2.`bank_id` AS `bankId`,COUNT(*) AS `__count2__` FROM `t_bank_card` t2 GROUP BY t2.`bank_id`) t3 ON t3.`bankId` = t1.`id` WHERE t1.`name` LIKE ?) t5 ON t.`bank_id` = t5.`id` LEFT JOIN (SELECT t6.`bank_id` AS `bankId`,(COUNT(*) > 0) AS `__any2__` FROM `t_bank_card` t6 GROUP BY t6.`bank_id`) t7 ON t7.`bankId` = t5.`id` WHERE IFNULL(t7.`__any2__`,?) = ? AND t5.`__part__column1` = t.`type`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%银行%(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testJoin() {


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
    public void testDelete() {


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


    @Test
    public void testOrderCaseWhen1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysUser> list1 = easyEntityQuery.queryable(SysUser.class)
                .orderBy(
                        user -> user.bankCards().first().code().asc()
                )
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid`)) AS `__row__` FROM `t_bank_card` t1) t3 ON (t3.`uid` = t.`id` AND t3.`__row__` = ?) ORDER BY t3.`code` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testOrderCaseWhen2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysUser> list1 = easyEntityQuery.queryable(SysUser.class)
                .orderBy(
                        user -> user.bankCard4s().first().code().asc()
                )
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t1.`open_time` ASC,CASE WHEN t1.`code` IS NULL THEN 0 ELSE 1 END ASC,t1.`code` DESC)) AS `__row__` FROM `t_bank_card` t1) t3 ON (t3.`uid` = t.`id` AND t3.`__row__` = ?) ORDER BY t3.`code` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testParentNull() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .where(bank_card -> {
                    bank_card.bank().isNull();
                    bank_card.bankId().isNull();
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t INNER JOIN `t_bank` t1 ON t1.`id` = t.`bank_id` WHERE t1.`id` IS NULL AND t.`bank_id` IS NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void abc() {

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .configure(op -> op.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(t -> {
                    t.expression().caseWhen(() -> {
                                t.bankCards().none();
                            }).then(null)
                            .elseEnd(t.expression().constant(123).divide(t.bankCards().count()))
                            .eq(123);

                }).toList();

    }

    @Test
    public void testPageSelectSubQueryCount() {
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        EasyPageResult<Draft2<String, Long>> pageResult = easyEntityQuery.queryable(SysBank.class)
                .subQueryToGroupJoin(s -> s.bankCards())
                .select(bank -> Select.DRAFT.of(
                        bank.id(),
                        bank.bankCards().count()
                )).toPageResult(1, 10);

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT `id` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT COUNT(*) FROM `t_bank` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("绍兴市(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                    Assert.assertEquals("SELECT `id` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT t.`id` AS `value1`,IFNULL(t2.`__count2__`,0) AS `value2` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,COUNT(*) AS `__count2__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id` LIMIT 3", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("绍兴市(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testFilterConfigure1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().any(s -> s.type().eq(""));
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id` AND t1.`type` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testFilterConfigure2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().any(s -> s.type().eq(""));
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id` LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());

    }
    @Test
    public void testFilterConfigure3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().any(s -> s.type().eq(""));
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,(COUNT(?) > 0) AS `__any2__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFilterConfigure3_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().any(s -> {
                        s.type().eq("");
                        s.code().eq("123");
                    });
                    bank.bankCards().any(s -> {
                        s.type().eq("");
                    });
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,(COUNT((CASE WHEN t1.`code` = ? THEN ? ELSE NULL END)) > 0) AS `__any2__`,(COUNT(?) > 0) AS `__any3__` FROM `t_bank_card` t1 GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ? AND IFNULL(t2.`__any3__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),1(Integer),false(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFilterConfigure4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().any(s -> s.type().eq(""));
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t LEFT JOIN (SELECT t1.`bank_id` AS `bankId`,(COUNT(?) > 0) AS `__any2__` FROM `t_bank_card` t1 WHERE t1.`type` = ? GROUP BY t1.`bank_id`) t2 ON t2.`bankId` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFilterConfigure5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().orderBy(s->s.type().asc()).first().type().eq("");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("(String),1(Integer),true(Boolean),false(Boolean),(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFilterConfigure6() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().where(x->x.id().eq("")).orderBy(s->s.type().asc()).first().type().eq("");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("(String),1(Integer),true(Boolean),false(Boolean),(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFilterConfigure7() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().where(x->x.id().eq("1")).orderBy(s->s.type().asc()).first().type().eq("");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("(String),1(Integer),true(Boolean),false(Boolean),(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFilterConfigure8() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().where(x->x.id().eq("")).orderBy(s->s.type().asc()).first().type().eq("1");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t LEFT JOIN (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`bank_id` ORDER BY t1.`type` ASC)) AS `__row__` FROM `t_bank_card` t1 WHERE t1.`id` = ?) t3 ON (t3.`bank_id` = t.`id` AND t3.`__row__` = ?) WHERE t3.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("(String),1(Integer),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFilterConfigure9() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(bank -> {
                    bank.name().eq("");
                    bank.bankCards().where(x->x.id().eq("")).orderBy(s->s.type().asc()).first().type().eq("1");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t LEFT JOIN (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`bank_id` ORDER BY t1.`type` ASC)) AS `__row__` FROM `t_bank_card` t1) t3 ON (t3.`bank_id` = t.`id` AND t3.`__row__` = ?) WHERE t3.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFilterConfigure10() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(bank -> {
                    bank.name().eq("");
                    bank.expression().exists(()->{
                        return bank.expression().subQueryable(SysBankCard.class)
                                .where(bank_card -> {
                                    bank_card.bankId().eq(bank.id());
                                    bank_card.type().eq("");
                                });
                    });
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testFilterConfigure11() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBank> list = easyEntityQuery.queryable(SysBank.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(bank -> {
                    bank.name().eq("");
                    bank.expression().exists(()->{
                        return bank.expression().subQueryable(SysBankCard.class)
                                .where(bank_card -> {
                                    bank_card.bankId().eq(bank.id());
                                    bank_card.type().eq("");
                                });
                    });
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_bank` t WHERE EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`bank_id` = t.`id` AND t1.`type` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
     public void testGroupConcat1(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<Draft3<String, String, String>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .groupBy(t_blog -> GroupKeys.of(t_blog.id()))
                    .select(group -> Select.DRAFT.of(
                            group.key1(),
                            group.groupTable().title().joining(",", true),
                            group.groupTable().content().joining(",", true)
                    )).toList();
        } catch (Exception e) {

        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,GROUP_CONCAT(DISTINCT t.`title` SEPARATOR ?) AS `value2`,GROUP_CONCAT(DISTINCT t.`content` SEPARATOR ?) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),,(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
     public void testGroupConcat2(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {
            List<Draft2<String, String>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                    .groupBy(t_blog -> GroupKeys.of(t_blog.id()))
                    .select(group -> Select.DRAFT.of(
                            group.key1(),
                            group.expression().sqlSegment("GROUP_CONCAT(DISTINCT {0} SEPARATOR {1})",c->{
                                c.expression(group.groupTable().content()).value(",");
                            }).asStr()
                    )).toList();
        } catch (Exception e) {

        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,GROUP_CONCAT(DISTINCT t.`content` SEPARATOR ?) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testAA(){

//        easyEntityQuery.queryable(SysBankCard.class)
//                        .where(bank_card -> {
//                            bank_card.user().appendOn();
//                        })
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s->s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .subQueryConfigure(s -> s.bankCards(), bk -> bk.where(x -> x.code().eq("1")))
                .where(user -> {
                    user.bankCards().any(b -> b.type().eq("11"));
                    user.bankCards().any(b -> b.type().eq("22"));
                }).toList();


    }

    @Test
    public  void cteViewTree1(){
        List<TreeC> list = easyEntityQuery.queryable(TreeC.class)
                .asTreeCTE()
                .toList();
    }

    @Test
    public  void cteViewTree2(){
        List<TreeC> list = easyEntityQuery.queryable(TreeA.class)
                .leftJoin(TreeB.class, (a, b) -> a.id().eq(b.aid()))
                .select((t1, t2) -> {

                    TreeCProxy treeCProxy = new TreeCProxy();
                    treeCProxy.selectAll(t1);
                    treeCProxy.pid().set(t2.aid());
                    return treeCProxy;
                }).asTreeCTECustom(s -> s.id(), s -> s.pid())
                .toList();


    }
}
