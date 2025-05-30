package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8User;
import com.easy.query.test.mysql8.entity.proxy.M8UserProxy;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/5/31 00:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class CTETest extends BaseTest{
    @Test
    public void cteTest1(){


        EntityQueryable<M8UserProxy, M8User> cteTTTAs = easyEntityQuery.queryable(M8User.class)
                .where(m -> {
                    m.age().eq(12);
                }).toCteAs("ttt");


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, Integer>> list1 = cteTTTAs.where(s -> {
            s.name().like("123");
        }).select(m -> Select.DRAFT.of(
                m.name(),
                m.age()
        )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `ttt` AS (SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t WHERE t.`age` = ?) SELECT t1.`name` AS `value1`,t1.`age` AS `value2` FROM `ttt` t1 WHERE t1.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12(Integer),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void cteJoinTest2(){


        EntityQueryable<M8UserProxy, M8User> cteTTTAs = easyEntityQuery.queryable(M8User.class)
                .where(m -> {
                    m.age().eq(12);
                }).toCteAs("ttt");
        EntityQueryable<M8UserProxy, M8User> cteYYYAs = easyEntityQuery.queryable(M8User.class)
                .where(m -> {
                    m.age().eq(13);
                }).toCteAs("yyy");


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = cteTTTAs.leftJoin(cteYYYAs, (m, m2) -> m.age().eq(m2.age()))
                .where((m1, m2) -> {
                    m1.name().like("123");
                    m2.name().like("456");
                }).select((m1, m2) -> Select.DRAFT.of(
                        m1.name(),
                        m2.name()
                )).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,(CASE WHEN COUNT((CASE WHEN IFNULL(t8.`__any2__`,?) = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__`,(CASE WHEN COUNT((CASE WHEN IFNULL(t8.`__any3__`,?) = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any3__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` LEFT JOIN (SELECT t6.`role_id` AS `role_id`,(CASE WHEN COUNT((CASE WHEN t5.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__`,(CASE WHEN COUNT((CASE WHEN t5.`path` LIKE ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any3__` FROM `m8_menu` t5 INNER JOIN `m8_role_menu` t6 ON t5.`id` = t6.`menu_id` GROUP BY t6.`role_id`) t8 ON t8.`role_id` = t1.`id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE IFNULL(t4.`__any2__`,?) = ? AND IFNULL(t4.`__any3__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),true(Boolean),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean),1(Integer),true(Boolean),false(Boolean),admin(String),1(Integer),true(Boolean),false(Boolean),/admin%(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
