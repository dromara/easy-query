package com.easy.query.test.mysql8;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import org.junit.Test;

/**
 * create time 2025/8/19 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQL8Test3  extends BaseTest{

    @Test
    public void testDynamicJoin(){

        String bankCardCode="";
        String bankCardType="";
        String bankName="";
        String userName="";
        String userPhone="";
        EasyPageResult<SysBankCard> pageResult = easyEntityQuery.queryable(SysBankCard.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(bank_card -> {
                    bank_card.code().contains(bankCardCode);
                    bank_card.type().contains(bankCardType);
                    bank_card.bank().name().contains(bankName);
                    bank_card.user().name().contains(userName);
                    bank_card.user().phone().contains(userPhone);
                })
                .toPageResult(1, 2);
    }
}
