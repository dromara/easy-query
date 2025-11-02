package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.core.draft.Draft6;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.bank.proxy.SysBankCardProxy;
import com.easy.query.test.mysql8.vo.UserDTO2;
import com.easy.query.test.mysql8.vo.proxy.UserDTO2Proxy;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * create time 2025/10/19 22:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQL8Test4 extends BaseTest {

    @Test
    public void testPartition() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft6<String, String, String, String, LocalDateTime, String>> list = easyEntityQuery.queryable(SysUser.class)
                .select(user -> {
                    SysBankCardProxy firstBankCard = user.bankCards().orderBy(bankCard -> bankCard.openTime().asc()).first();
                    return Select.DRAFT.of(
                            user.id(),
                            user.name(),
                            firstBankCard.code(),
                            firstBankCard.type(),
                            firstBankCard.openTime(),
                            firstBankCard.bank().name()
                    );
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t.`name` AS `value2`,t3.`code` AS `value3`,t3.`type` AS `value4`,t3.`open_time` AS `value5`,t4.`name` AS `value6` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,t1.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t1.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t1) t3 ON (t3.`uid` = t.`id` AND t3.`__row__` = ?) INNER JOIN `t_bank` t4 ON t4.`id` = t3.`bank_id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testPartition2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<UserDTO2> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    //用户至少有三张储蓄卡
                    user.bankCards().where(c -> c.type().eq("储蓄卡")).count().gt(3L);
                    //用户没有信用卡
                    user.bankCards().where(c -> c.type().eq("信用卡")).none();
                })
                .select(user -> {
                    SysBankCardProxy thirdCard = user.bankCards().orderBy(bankCard -> bankCard.openTime().asc()).element(3);
                    return new UserDTO2Proxy()
                            .id().set(user.id())
                            .name().set(user.name())
                            .thirdCardType().set(thirdCard.type())
                            .thirdCardCode().set(thirdCard.code())
                            .thirdCardBankName().set(thirdCard.bank().name());
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,t.`name` AS `name`,t5.`type` AS `third_card_type`,t5.`code` AS `third_card_code`,t6.`name` AS `third_card_bank_name` FROM `t_sys_user` t LEFT JOIN (SELECT t3.`id`,t3.`uid`,t3.`code`,t3.`type`,t3.`bank_id`,t3.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t3.`uid` ORDER BY t3.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t3) t5 ON (t5.`uid` = t.`id` AND t5.`__row__` = ?) INNER JOIN `t_bank` t6 ON t6.`id` = t5.`bank_id` WHERE (SELECT COUNT(*) FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ?) > ? AND NOT ( EXISTS (SELECT 1 FROM `t_bank_card` t2 WHERE t2.`uid` = t.`id` AND t2.`type` = ? LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("4(Integer),储蓄卡(String),3(Long),信用卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testPartition3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<UserDTO2> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(user -> {
                    //用户至少有三张储蓄卡
                    user.bankCards().where(c -> c.type().eq("储蓄卡")).count().gt(4L);
                    //用户没有信用卡
                    user.bankCards().where(c -> c.type().eq("信用卡")).none();
                })
                .select(user -> {
                    SysBankCardProxy thirdCard = user.bankCards().orderBy(bankCard -> bankCard.openTime().asc()).element(3);
                    return new UserDTO2Proxy()
                            .id().set(user.id())
                            .name().set(user.name())
                            .thirdCardType().set(thirdCard.type())
                            .thirdCardCode().set(thirdCard.code())
                            .thirdCardBankName().set(thirdCard.bank().name());
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,t.`name` AS `name`,t5.`type` AS `third_card_type`,t5.`code` AS `third_card_code`,t6.`name` AS `third_card_bank_name` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE NULL END)) AS `__count2__`,(COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE NULL END)) <= 0) AS `__none3__` FROM `t_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` LEFT JOIN (SELECT t3.`id`,t3.`uid`,t3.`code`,t3.`type`,t3.`bank_id`,t3.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t3.`uid` ORDER BY t3.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t3) t5 ON (t5.`uid` = t.`id` AND t5.`__row__` = ?) INNER JOIN `t_bank` t6 ON t6.`id` = t5.`bank_id` WHERE IFNULL(t2.`__count2__`,0) > ? AND IFNULL(t2.`__none3__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),1(Integer),信用卡(String),1(Integer),4(Integer),4(Long),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testToResultSet() {
        {
            List<SysUser> list = easyEntityQuery.queryable(SysUser.class).toList();
            List<BlogEntity> resultSet = easyEntityQuery.queryable(SysUser.class)
                    .where(s -> {
                        s.id().isNotNull();
                    }).toResultSet(context -> {
                        ArrayList<BlogEntity> blogEntities = new ArrayList<>();
                        StreamResultSet streamResultSet = context.getStreamResultSet();
                        ResultSet resultSetResultSet = streamResultSet.getResultSet();
                        while (resultSetResultSet.next()) {
                            BlogEntity blogEntity = new BlogEntity();
                            blogEntity.setId(resultSetResultSet.getString(1));
                            blogEntities.add(blogEntity);
                        }
                        return blogEntities;
                    });
            System.out.println(resultSet);
        }
        {

            List<BlogEntity> resultSet = easyEntityQuery.queryable(SysUser.class)
                    .where(s -> {
                        s.id().isNotNull();
                    }).toResultSet(context -> {
                        ArrayList<BlogEntity> blogEntities = new ArrayList<>();
                        StreamResultSet streamResultSet = context.getStreamResultSet();
                        ResultSet resultSetResultSet = streamResultSet.getResultSet();
                        while (resultSetResultSet.next()) {
                            BlogEntity blogEntity = new BlogEntity();
                            blogEntity.setId(resultSetResultSet.getString(1));
                            blogEntities.add(blogEntity);
                        }
                        return blogEntities;
                    });
            System.out.println(resultSet);
        }
    }

    @Test
    public void testSubQueryCountCompareNull1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Long val = null;
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.bankCards().count().gt(val);
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE (SELECT COUNT(*) FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id`) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testSubQueryCountCompareNull2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Long val = null;
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(user -> {
                    user.bankCards().count().gt(val);
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name`,`phone`,`age`,`create_time` FROM `t_sys_user`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testTimeAfterNull1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime val = null;
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.createTime().isAfter(val);
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name`,`phone`,`age`,`create_time` FROM `t_sys_user` WHERE `create_time` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testTimeAfterNull2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime val = null;
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(user -> {
                    user.createTime().isAfter(val);
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name`,`phone`,`age`,`create_time` FROM `t_sys_user`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testTimeBeforeNull1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime val = null;
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.createTime().isBefore(val);
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name`,`phone`,`age`,`create_time` FROM `t_sys_user` WHERE `create_time` < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testTimeBeforeNull2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime val = null;
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(user -> {
                    user.createTime().isBefore(val);
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name`,`phone`,`age`,`create_time` FROM `t_sys_user`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testFilter() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime val = null;
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .leftJoin(SysUser.class, (user1, user2) -> {
                    user1.id().eq(user2.id());
                    user1.filter(u -> {
                        u.name().contains("123");
                    });
                    user2.filter(u -> {
                        u.name().contains("456");
                    });
                    user1.firstCard().filter(s -> {
                        s.type().eq("4567");
                    });
                })
                .where((user1, user2) -> {
                    user1.filter(u -> {
                        u.name().contains("789");
                    });
                    user2.filter(u -> {
                        u.name().contains("987");
                    });
                    user1.firstCard().type().eq("123123");
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN `t_sys_user` t1 ON t.`id` = t1.`id` AND t.`name` LIKE CONCAT('%',?,'%') AND t1.`name` LIKE CONCAT('%',?,'%') LEFT JOIN (SELECT t2.`id`,t2.`uid`,t2.`code`,t2.`type`,t2.`bank_id`,t2.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t2.`uid` ORDER BY t2.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t2) t4 ON (t4.`uid` = t.`id` AND t4.`__row__` = ?) AND t4.`type` = ? WHERE t.`name` LIKE CONCAT('%',?,'%') AND t1.`name` LIKE CONCAT('%',?,'%') AND t4.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),456(String),1(Integer),4567(String),789(String),987(String),123123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testFilterAndDependency() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime val = null;
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .leftJoin(SysUser.class, (user1, user2) -> {
                    user1.firstCard().uid().eq(user2.id());//会将firstCard提级到先join firstCard
                    user1.id().eq(user2.id());
                    user1.filter(u -> {
                        u.name().contains("123");
                    });
                    user2.filter(u -> {
                        u.name().contains("456");
                    });
                })
                .where((user1, user2) -> {
                    user1.filter(u -> {
                        u.name().contains("789");
                    });
                    user2.filter(u -> {
                        u.name().contains("987");
                    });
                    user1.firstCard().type().eq("123123");


                    //所有的卡都是储蓄卡
//                    user1.bankCards().all(bc->bc.type().eq("储蓄卡"));

                    //等价于 没有不是储蓄卡的
//                    user1.expression().not(()->{
//                        user1.bankCards().any(bc->bc.type().eq("储蓄卡"));
//                    });

                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t2.`id`,t2.`uid`,t2.`code`,t2.`type`,t2.`bank_id`,t2.`open_time`,(ROW_NUMBER() OVER (PARTITION BY t2.`uid` ORDER BY t2.`open_time` ASC)) AS `__row__` FROM `t_bank_card` t2) t4 ON (t4.`uid` = t.`id` AND t4.`__row__` = ?) LEFT JOIN `t_sys_user` t1 ON t4.`uid` = t1.`id` AND t.`id` = t1.`id` AND t.`name` LIKE CONCAT('%',?,'%') AND t1.`name` LIKE CONCAT('%',?,'%') WHERE t.`name` LIKE CONCAT('%',?,'%') AND t1.`name` LIKE CONCAT('%',?,'%') AND t4.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),123(String),456(String),789(String),987(String),123123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void testAll() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> bc.code().startsWith("33123"));
                }).toList();
        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡") && o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE NOT ( EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ? AND (NOT (t1.`code` LIKE CONCAT(?,'%'))) LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),33123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t WHERE t.`uid` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAll1() {
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> bc.code().nullOrDefault("").startsWith("33123"));
                }).toList();
        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡")).allMatch(o -> (o.getCode() == null ? "" : o.getCode()).startsWith("33123"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE NOT ( EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ? AND (NOT (IFNULL(t1.`code`,?) LIKE CONCAT(?,'%'))) LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),(String),33123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t WHERE t.`uid` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAll2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> {
                        bc.code().startsWith("33123");
                        bc.code().startsWith("45678");
                    });
                }).toList();
        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡") && o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123") && o.getCode().startsWith("45678"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE NOT ( EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ? AND (NOT (t1.`code` LIKE CONCAT(?,'%') AND t1.`code` LIKE CONCAT(?,'%'))) LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),33123(String),45678(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t WHERE t.`uid` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAllGroupJoin() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> bc.code().startsWith("33123"));
                }).toList();
        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡") && o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,(COUNT(?) <= 0) AS `__none2__` FROM `t_bank_card` t1 WHERE t1.`type` = ? AND (NOT (t1.`code` LIKE CONCAT(?,'%'))) GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE IFNULL(t2.`__none2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),储蓄卡(String),33123(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t WHERE t.`uid` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAllGroupJoin2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> {
                        bc.code().startsWith("33123");
                        bc.code().startsWith("45678");
                    });
                }).toList();

        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡") && o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123") && o.getCode().startsWith("45678"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,(COUNT(?) <= 0) AS `__none2__` FROM `t_bank_card` t1 WHERE t1.`type` = ? AND (NOT (t1.`code` LIKE CONCAT(?,'%') AND t1.`code` LIKE CONCAT(?,'%'))) GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE IFNULL(t2.`__none2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),储蓄卡(String),33123(String),45678(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t WHERE t.`uid` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAll4() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).notEmptyAll(bc -> bc.code().startsWith("33123"));
                }).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(1, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE (EXISTS (SELECT 1 FROM `t_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ? LIMIT 1) AND NOT ( EXISTS (SELECT 1 FROM `t_bank_card` t2 WHERE t2.`uid` = t.`id` AND t2.`type` = ? AND (NOT (t2.`code` LIKE CONCAT(?,'%'))) LIMIT 1)))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),储蓄卡(String),33123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAll5() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s->s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).notEmptyAll(bc -> bc.code().startsWith("33123"));
                }).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(1, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,(COUNT(?) > 0) AS `__any2__`,(COUNT((CASE WHEN (NOT (t1.`code` LIKE CONCAT(?,'%'))) THEN ? ELSE NULL END)) <= 0) AS `__none3__` FROM `t_bank_card` t1 WHERE t1.`type` = ? GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE (IFNULL(t2.`__any2__`,?) = ? AND IFNULL(t2.`__none3__`,?) = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),33123(String),1(Integer),储蓄卡(String),false(Boolean),true(Boolean),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }
//    @Test
//    public void testaaa() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime after15days = LocalDateTime.now().plusDays(15);
//        easyEntityQuery.queryable(Scheduling.class)
//                .where(s -> {
//                    s.schedulingDay().rangeClosed(now, after15days);
//                }).groupBy(s -> GroupKeys.of(s.process()))
//                .select(group -> new SchedulingVOProxy()
//                        .process().set(group.key1())
//                        .estmateCapacity().set(
//                                group.where(x -> x.origin().eq(1)).sumBigDecimal(x -> x.allocateCapacity())
//                        )
//                        .otherCapacity().set(
//                                group.where(x -> x.origin().eq(3)).sumBigDecimal(x -> x.allocateCapacity())
//                        )
//                ).toList();
//
//    }

}
