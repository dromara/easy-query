package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8User;
import com.easy.query.test.mysql8.entity.M8UserTemp;
import com.easy.query.test.mysql8.entity.M8UserTemp2;
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
public class CTETest extends BaseTest {
    @Test
    public void cteTest1() {


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
    public void cteJoinTest2() {


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
        Assert.assertEquals("WITH `ttt` AS (SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t WHERE t.`age` = ?) ,`yyy` AS (SELECT t2.`id`,t2.`name`,t2.`age`,t2.`create_time` FROM `m8_user` t2 WHERE t2.`age` = ?) SELECT t1.`name` AS `value1`,t3.`name` AS `value2` FROM `ttt` t1 LEFT JOIN `yyy` t3 ON t1.`age` = t3.`age` WHERE t1.`name` LIKE ? AND t3.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12(Integer),13(Integer),%123%(String),%456%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void cteTest3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<M8UserTemp> list = easyEntityQuery.queryable(M8UserTemp.class)
                .where(m -> {
                    m.name().contains("123");
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `m8_user_temp` AS (SELECT t.`id`,t.`name` FROM `m8_user` t WHERE t.`age` IS NULL) SELECT t2.`id`,t2.`name` FROM `m8_user_temp` t2 WHERE t2.`name` LIKE CONCAT('%',?,'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void cteTest4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<M8UserTemp> list = easyEntityQuery.queryable(M8UserTemp.class)
                .where(m -> {
                    m.roles().flatElement().name().contains("123");
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `m8_user_temp` AS (SELECT t.`id`,t.`name` FROM `m8_user` t WHERE t.`age` IS NULL) SELECT t2.`id`,t2.`name` FROM `m8_user_temp` t2 WHERE EXISTS (SELECT 1 FROM `m8_role` t3 WHERE EXISTS (SELECT 1 FROM `m8_user_role` t4 WHERE t4.`role_id` = t3.`id` AND t4.`user_id` = t2.`id` LIMIT 1) AND t3.`name` LIKE CONCAT('%',?,'%') LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void cteTest5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, String>> list = easyEntityQuery.queryable(M8UserTemp.class)
                .leftJoin(M8UserTemp.class, (m, m2) -> m.name().eq(m2.name()))
                .where((m1, m2) -> {
                    m2.roles().first().name().like("123");
                }).select((m1, m2) -> Select.DRAFT.of(
                        m1.name(),
                        m2.roles().first().name()
                )).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `m8_user_temp` AS (SELECT t.`id`,t.`name` FROM `m8_user` t WHERE t.`age` IS NULL) SELECT t2.`name` AS `value1`,t11.`name` AS `value2` FROM `m8_user_temp` t2 LEFT JOIN `m8_user_temp` t5 ON t2.`name` = t5.`name` LEFT JOIN (SELECT t9.`id` AS `id`,t9.`name` AS `name`,t9.`user_id` AS `user_id` FROM (SELECT t7.`user_id` AS `user_id`,t6.`id`,t6.`name`,(ROW_NUMBER() OVER (PARTITION BY t6.`id`)) AS `__row__` FROM `m8_role` t6 INNER JOIN `m8_user_role` t7 ON t6.`id` = t7.`role_id`) t9 WHERE t9.`__row__` = ?) t11 ON t11.`user_id` = t5.`id` WHERE t11.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void cteTest6() {


        List<M8User> list1 = easyEntityQuery.queryable(M8User.class)
                .configure(op->op.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(m -> {
                    m.roles().flatElement().menus().flatElement().path().contains("/admin");

                }).toList();


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<M8UserTemp2> list = easyEntityQuery.queryable(M8UserTemp2.class)
                .where(m -> {
                    m.rowNumber().eq(2L);
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH `m8_user_temp2` AS (SELECT t.`id`,t.`name`,t.`age`,(ROW_NUMBER() OVER (PARTITION BY t.`age` ORDER BY t.`create_time` DESC)) AS `row_number` FROM `m8_user` t WHERE t.`age` IS NULL) SELECT t2.`id`,t2.`name`,t2.`age`,t2.`row_number` FROM `m8_user_temp2` t2 WHERE t2.`row_number` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
