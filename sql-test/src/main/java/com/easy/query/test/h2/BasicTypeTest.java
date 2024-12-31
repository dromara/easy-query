package com.easy.query.test.h2;

import com.easy.query.api.proxy.base.BooleanProxy;
import com.easy.query.api.proxy.base.DoubleProxy;
import com.easy.query.api.proxy.base.FloatProxy;
import com.easy.query.api.proxy.base.IntegerProxy;
import com.easy.query.api.proxy.base.LocalDateProxy;
import com.easy.query.api.proxy.base.LocalDateTimeProxy;
import com.easy.query.api.proxy.base.LocalTimeProxy;
import com.easy.query.api.proxy.base.LongProxy;
import com.easy.query.api.proxy.base.ShortProxy;
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
    @Test
    public void intTest1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Integer b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new IntegerProxy(33)).firstNotNull();
        Assert.assertTrue(33== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("33(Integer),123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void intTest2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Integer b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new IntegerProxy(t.numberInteger())).firstNotNull();
        Assert.assertTrue(33== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_integer FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void intTest3(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Integer b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new IntegerProxy(t.numberIntegerBasic())).firstNotNull();
        Assert.assertTrue(33== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_integer_basic FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void longTest1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new LongProxy(12345678911L)).firstNotNull();
        Assert.assertTrue(12345678911L== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12345678911(Long),123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void longTest2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new LongProxy(t.numberLong())).firstNotNull();
        Assert.assertTrue(12345678911L== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_long FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void longTest3(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new LongProxy(t.numberLongBasic())).firstNotNull();
        Assert.assertTrue(12345678911L== b);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_long_basic FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void shortTest1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Short b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new ShortProxy(new Short("12"))).firstNotNull();
        Assert.assertTrue(new Short("12").compareTo(b)==0);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12(Short),123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void shortTest2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Short b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new ShortProxy(t.numberShort())).firstNotNull();
        Assert.assertTrue(new Short("12").compareTo(b)==0);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_short FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void shortTest3(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Short b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new ShortProxy(t.numberShortBasic())).firstNotNull();
        Assert.assertTrue(new Short("12").compareTo(b)==0);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.number_short_basic FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void localDateTimeTest1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        LocalDateTime b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new LocalDateTimeProxy(LocalDateTime.of(2021, 1, 1, 0, 0))).firstNotNull();
        Assert.assertTrue(LocalDateTime.of(2021, 1, 1, 0, 0).isEqual(b));
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-01-01T00:00(LocalDateTime),123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void localDateTimeTest2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        LocalDateTime b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new LocalDateTimeProxy(t.timeLocalDateTime())).firstNotNull();
        Assert.assertTrue(LocalDateTime.of(2021, 1, 1, 0, 0).isEqual(b));
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.time_local_date_time FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void localDateTest1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        LocalDate b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new LocalDateProxy(LocalDate.of(2121, 1, 2))).firstNotNull();
        Assert.assertTrue(LocalDate.of(2121, 1, 2).isEqual(b));
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2121-01-02(LocalDate),123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void localDateTest2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        LocalDate b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new LocalDateProxy(t.timeLocalDate())).firstNotNull();
        Assert.assertTrue(LocalDate.of(2121, 1, 2).isEqual(b));
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.time_local_date FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
//        alltype.setTimeLocalDate(LocalDate.of(2121, 1, 2));
//        alltype.setTimeLocalTime(LocalTime.of(21, 1, 9));
@Test
public void localTimeTest1(){

    ListenerContext listenerContext = new ListenerContext();
    listenerContextManager.startListen(listenerContext);

    LocalTime b = easyEntityQuery.queryable(ALLTYPE.class)
            .where(a -> a.id().eq("123xxxxxxx1qq"))
            .select(t -> new LocalTimeProxy(LocalTime.of(21, 1, 9))).firstNotNull();
    Assert.assertTrue(LocalTime.of(21, 1, 9).equals(b));
    Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
    Assert.assertEquals("SELECT ? FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
    Assert.assertEquals("21:01:09(LocalTime),123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    listenerContextManager.clear();
}

    @Test
    public void localTimeTest2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        LocalTime b = easyEntityQuery.queryable(ALLTYPE.class)
                .where(a -> a.id().eq("123xxxxxxx1qq"))
                .select(t -> new LocalTimeProxy(t.timeLocalTime())).firstNotNull();
        Assert.assertTrue(LocalTime.of(21, 1, 9).equals(b));
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.time_local_time FROM t_all_type t WHERE t.id = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxxxxxx1qq(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
