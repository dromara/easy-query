package com.easy.query.test.mysql8;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8Order;
import com.easy.query.test.mysql8.entity.M8OrderItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/4/12 10:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8OrderTest extends BaseTest{
    @Before
    public void before(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(M8Order.class, M8OrderItem.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
    }

    @Test
    public void testSubQuery(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Order> list = easyEntityQuery.queryable(M8Order.class)
                .subQueryToGroupJoin(s->s.orderItems())
                .where(m -> {
                    m.orderItems().flatElement().price().gt(BigDecimal.ONE);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`no`,t.`price`,t.`create_time` FROM `t_order` t INNER JOIN (SELECT t1.`order_id` AS `orderId`,(CASE WHEN COUNT((CASE WHEN t1.`price` > ? THEN ? ELSE ? END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `t_order_item` t1 GROUP BY t1.`order_id`) t2 ON t2.`orderId` = t.`id` WHERE t2.`__any2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(BigDecimal),1(Integer),null(null),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testSubQuery3(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Order> list = easyEntityQuery.queryable(M8Order.class)
                .subQueryToGroupJoin(s->s.orderItems())
                .where(m -> {
                    m.orderItems().flatElement().price().isNull();
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`no`,t.`price`,t.`create_time` FROM `t_order` t INNER JOIN (SELECT t1.`order_id` AS `orderId`,(CASE WHEN COUNT((CASE WHEN t1.`price` IS NULL THEN ? ELSE ? END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `t_order_item` t1 GROUP BY t1.`order_id`) t2 ON t2.`orderId` = t.`id` WHERE t2.`__any2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),null(null),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testSubQuery2(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Order> list = easyEntityQuery.queryable(M8Order.class)
                .subQueryToGroupJoin(s->s.orderItems())
                .where(m -> {
                    m.orderItems().firstElement().price().gt(BigDecimal.ONE);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`no`,t.`price`,t.`create_time` FROM `t_order` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`order_id` AS `order_id`,t2.`price` AS `price`,t2.`create_time` AS `create_time` FROM (SELECT t1.`id`,t1.`order_id`,t1.`price`,t1.`create_time`,(ROW_NUMBER() OVER (PARTITION BY t1.`order_id`)) AS `__row__` FROM `t_order_item` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`order_id` = t.`id` WHERE t4.`price` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
