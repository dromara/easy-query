package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.proxy.core.draft.Draft6;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.m2m.Station;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.bank.proxy.SysBankCardProxy;
import com.easy.query.test.mysql8.entity.many.M8Province;
import com.easy.query.test.mysql8.vo.UserDTO2;
import com.easy.query.test.mysql8.vo.proxy.UserDTO2Proxy;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}
