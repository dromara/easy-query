package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.dto.BankCardQueryDTO;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/11/12 12:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class CrossJoinTest extends BaseTest {
    @Test
    public void crossJoin1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysBankCard> list = easyEntityQuery.queryable(SysBankCard.class)
                .crossJoin(SysBankCard.class, (bank_card1, bank_card2) -> bank_card1.id().eq(bank_card2.id()))
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id`,t.`open_time` FROM `t_bank_card` t CROSS JOIN `t_bank_card` t1 ON t.`id` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("%工商银行%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
