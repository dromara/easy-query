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
        Assert.assertEquals("WITH `ttt` AS (SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t WHERE t.`age` = ?) ,`yyy` AS (SELECT t2.`id`,t2.`name`,t2.`age`,t2.`create_time` FROM `m8_user` t2 WHERE t2.`age` = ?) SELECT t1.`name` AS `value1`,t3.`name` AS `value2` FROM `ttt` t1 LEFT JOIN `yyy` t3 ON t1.`age` = t3.`age` WHERE t1.`name` LIKE ? AND t3.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12(Integer),13(Integer),%123%(String),%456%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
