package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.dto.BankCardQueryDTO;
import com.easy.query.test.mysql8.dto.SysUserQueryDTO;
import com.easy.query.test.mysql8.dto.SysUserQueryDTO2;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/8/10 21:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8QueryTest extends BaseTest{
    @Test
    public void whereObject1(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BankCardQueryDTO bankCardQueryDTO = new BankCardQueryDTO();
        bankCardQueryDTO.setBankName("工商银行");
        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .whereObject(bankCardQueryDTO)
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t INNER JOIN `t_bank` t1 ON t1.`id` = t.`bank_id` WHERE t1.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%工商银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }
    @Test
    public void whereObject2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        SysUserQueryDTO queryDTO = new SysUserQueryDTO();
        queryDTO.setBankCardCode("123");
        queryDTO.setBankCardType("储蓄卡");
        queryDTO.setBankCardBankNames(Arrays.asList("工商银行","建设银行"));
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s->s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .whereObject(queryDTO)
                .where(user -> {
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `__group_key1__`,(COUNT(?) > 0) AS `__any2__` FROM `t_bank_card` t1 INNER JOIN `t_bank` t3 ON t3.`id` = t1.`bank_id` WHERE t1.`code` LIKE ? AND t1.`type` LIKE ? AND t3.`name` IN (?,?) GROUP BY t1.`uid`) t2 ON t2.`__group_key1__` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),%123%(String),%储蓄卡%(String),工商银行(String),建设银行(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void whereObject3(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        SysUserQueryDTO queryDTO = new SysUserQueryDTO();
        queryDTO.setBankCardCode("123");
        queryDTO.setBankCardType("储蓄卡");
        queryDTO.setBankCardBankNames(Arrays.asList("工商银行","建设银行"));
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .whereObject(queryDTO)
                .where(user -> {
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t WHERE EXISTS (SELECT 1 FROM `t_bank_card` t1 INNER JOIN `t_bank` t2 ON t2.`id` = t1.`bank_id` WHERE t1.`uid` = t.`id` AND t1.`code` LIKE ? AND t1.`type` LIKE ? AND t2.`name` IN (?,?) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),%储蓄卡%(String),工商银行(String),建设银行(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void whereObject4(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        SysUserQueryDTO queryDTO = new SysUserQueryDTO();
        queryDTO.setBankCardCode("123");
        queryDTO.setBankCardType("储蓄卡");
        queryDTO.setBankCardBankNames(Arrays.asList("工商银行","建设银行"));
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s->s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .whereObject(queryDTO)
                .where(user -> {
                    user.bankCards().any(x->x.code().contains("123"));
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `__group_key1__`,(COUNT((CASE WHEN t1.`code` LIKE ? AND t1.`type` LIKE ? AND t3.`name` IN (?,?) THEN ? ELSE NULL END)) > 0) AS `__any2__`,(COUNT((CASE WHEN t1.`code` LIKE CONCAT('%',?,'%') THEN ? ELSE NULL END)) > 0) AS `__any3__` FROM `t_bank_card` t1 INNER JOIN `t_bank` t3 ON t3.`id` = t1.`bank_id` GROUP BY t1.`uid`) t2 ON t2.`__group_key1__` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ? AND IFNULL(t2.`__any3__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),%储蓄卡%(String),工商银行(String),建设银行(String),1(Integer),123(String),1(Integer),false(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void whereObject5(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        SysUserQueryDTO queryDTO = new SysUserQueryDTO();
        queryDTO.setBankCardCode("123");
        queryDTO.setBankCardType("储蓄卡");
        queryDTO.setBankCardBankNames(Arrays.asList("工商银行","建设银行"));
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s->s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(user -> {
                    user.bankCards().any(s->{
                        s.code().like(queryDTO.getBankCardCode());
                        s.type().like(queryDTO.getBankCardType());
                        s.bank().name().in(queryDTO.getBankCardBankNames());
                    });
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `__group_key1__`,(COUNT(?) > 0) AS `__any2__` FROM `t_bank_card` t1 INNER JOIN `t_bank` t3 ON t3.`id` = t1.`bank_id` WHERE t1.`code` LIKE ? AND t1.`type` LIKE ? AND t3.`name` IN (?,?) GROUP BY t1.`uid`) t2 ON t2.`__group_key1__` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),%123%(String),%储蓄卡%(String),工商银行(String),建设银行(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void whereObject6(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        SysUserQueryDTO2 queryDTO = new SysUserQueryDTO2();
        queryDTO.setBankCardCode("123%");
        queryDTO.setBankCardType("储蓄_卡");
        queryDTO.setBankCardType2("储蓄卡");
        queryDTO.setBankCardType3("储蓄卡");
        queryDTO.setBankCardBankNames(Arrays.asList("工商银行","建设银行"));
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s->s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .whereObject(queryDTO)
                .where(user -> {
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age`,t.`create_time` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `__group_key1__`,(COUNT(?) > 0) AS `__any2__` FROM `t_bank_card` t1 INNER JOIN `t_bank` t3 ON t3.`id` = t1.`bank_id` WHERE LOCATE(?,t1.`code`) = 1 AND t3.`name` IN (?,?) AND LOCATE(?,t1.`type`) > 0 AND t1.`type` LIKE CONCAT('%',?) AND t1.`type` LIKE CONCAT('%',?,'%') GROUP BY t1.`uid`) t2 ON t2.`__group_key1__` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),123%(String),工商银行(String),建设银行(String),储蓄_卡(String),储蓄卡(String),储蓄卡(String),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
}
