package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.UserExtra;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;


/**
 * create time 2026/4/25 14:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest29 extends BaseTest{



    @Test
    public void test1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<Integer, String, String>> list = easyEntityQuery.queryable(UserExtra.class)
                .where(u -> {
                    u.id().eq("test3");
                    u.fullName().like("悟");
                    u.fullName().isNotBlank();
                }).groupBy(u -> GroupKeys.of(u.age(), u.fullName()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.key2(),
                        group.groupTable().fullName().max()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT CEILING((timestampdiff(DAY, t.`birthday`, NOW()) / ?)) AS `value1`,CONCAT(t.`first_name`,t.`last_name`) AS `value2`,MAX(CONCAT(t.`first_name`,t.`last_name`)) AS `value3` FROM `t_user_extra` t WHERE t.`id` = ? AND CONCAT(t.`first_name`,t.`last_name`) LIKE ? GROUP BY CEILING((timestampdiff(DAY, t.`birthday`, NOW()) / ?)),CONCAT(t.`first_name`,t.`last_name`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("365(Integer),test3(String),%悟%(String),365(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        listenerContextManager.clear();

//
//
//        easyQueryClient.queryable(Map.class)
//                .asTable("t_a")
//                .leftJoin(Map.class, (t_a, t_b) -> {
//                    t_a.eq(t_b,"aid","bid");
//                })
//                .asTable("t_a")
//                .where((m1, m2) -> {
//                    m1.eq("name","123");
//                    m2.eq("name1","123");
//                })
    }
}
