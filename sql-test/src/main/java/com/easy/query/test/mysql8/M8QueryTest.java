package com.easy.query.test.mysql8;

import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.test.mysql8.dto.BankCardQueryDTO;
import com.easy.query.test.mysql8.dto.SysUserQueryDTO;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
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
        BankCardQueryDTO bankCardQueryDTO = new BankCardQueryDTO();
        bankCardQueryDTO.setBankName("工商银行");
        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .whereObject(bankCardQueryDTO)
                .toList();

    }
    @Test
    public void whereObject2(){
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

    }
}
