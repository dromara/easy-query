package com.easy.query.test.h2;

import com.easy.query.api.proxy.base.BooleanProxy;
import com.easy.query.api.proxy.base.DoubleProxy;
import com.easy.query.api.proxy.base.FloatProxy;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.h2.domain.ALLTYPE;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

/**
 * create time 2024/12/31 15:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class BasicTypeTest extends H2BaseTest {

    
    @Test
    public void booleanTest1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Boolean b = easyEntityQuery.queryable(ALLTYPE.class)
                .select(t -> new BooleanProxy(true)).firstNotNull();
        Assert.assertTrue(b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? FROM t_all_type t LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void booleanTest2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Boolean b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> {
                    a.enable().eq(true);
                })
                .select(t -> new BooleanProxy(t.enable())).firstNotNull();
        Assert.assertTrue(b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.enable FROM t_all_type t WHERE t.enable = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void booleanTest3(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Boolean b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> {
                    a.enableBasic().eq(true);
                })
                .select(t -> new BooleanProxy(t.enableBasic())).firstNotNull();
        Assert.assertTrue(b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.enable_basic FROM t_all_type t WHERE t.enable_basic = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void doubleTest1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Double b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new DoubleProxy(1.1d)).firstNotNull();
        Assert.assertTrue(1.1d== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1.1(Double),123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void doubleTest2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Double b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new DoubleProxy(t.numberDouble())).firstNotNull();
        Assert.assertTrue(22.1d== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_double FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void doubleTest3(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Double b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new DoubleProxy(t.numberDoubleBasic())).firstNotNull();
        Assert.assertTrue(22.1d== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_double_basic FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void floatTest1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Float b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new FloatProxy(12.3f)).firstNotNull();
        Assert.assertTrue(12.3f== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12.3(Float),123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void floatTest2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Float b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new FloatProxy(t.numberFloat())).firstNotNull();
        Assert.assertTrue(12.3f== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_float FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void floatTest3(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Float b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new FloatProxy(t.numberFloatBasic())).firstNotNull();
        Assert.assertTrue(12.3f== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_float_basic FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
