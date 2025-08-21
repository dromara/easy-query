package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import org.junit.Assert;
import org.junit.Test;

/**
 * create time 2025/8/19 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQL8Test3 extends BaseTest {

    @Test
    public void testDynamicJoin() {

        String bankCardCode = "";
        String bankCardType = "";
        String bankName = "";
        String userName = "";
        String userPhone = "";
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


    @Test
    public void thisConfigureTest() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        EasyPageResult<Draft1<String>> pageResult = easyEntityQuery.queryable(SysUser.class)
                .thisConfigure(o->thisConfigure(o))
                .where(user -> {
                    user.name().eq("");
                }).orderBy(user -> {
                    user.bankCards().where(x -> x.type().eq("123")).max(s -> s.openTime()).asc(OrderByModeEnum.NULLS_LAST);
                }).select(user -> Select.DRAFT.of(
                        user.name()
                )).toPageResult(1, 20);
        listenerContextManager.clear();

        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT COUNT(*) FROM `t_sys_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("-1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`name` AS `value1` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,MAX(t1.`open_time`) AS `__max2__` FROM `t_bank_card` t1 WHERE t1.`type` = ? GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` ORDER BY CASE WHEN t2.`__max2__` IS NULL THEN 1 ELSE 0 END ASC,t2.`__max2__` ASC LIMIT 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }

    }

    public static  <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> EntityQueryable<T1Proxy, T1> thisConfigure(EntityQueryable<T1Proxy, T1> queryable) {
        return queryable.filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .configure(s->s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN));
    }
}
