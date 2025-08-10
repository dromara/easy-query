package com.easy.query.test.mysql8;

import com.easy.query.test.mysql8.dto.BankCardQueryDTO;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import org.junit.Test;

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
}
