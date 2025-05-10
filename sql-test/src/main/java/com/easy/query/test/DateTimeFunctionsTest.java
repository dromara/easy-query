package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.draft.Draft6;
import com.easy.query.core.proxy.core.draft.Draft7;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * create time 2025/4/13 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class DateTimeFunctionsTest extends BaseTest {

    @Test
    public void formatMySQL() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        String format1 = "yyyy年MM-01 HH:mm分ss秒";
        String format2 = "yyyy-MM-dd HH:mm:ss";
        String format3 = "yyyy/MM-/01 HH时mm分ss秒";
        List<Draft4<LocalDateTime, String, String, String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.createTime(),
                        t_blog.createTime().format(format1),
                        t_blog.createTime().format(format2),
                        t_blog.createTime().format(format3)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft4<LocalDateTime, String, String, String> timeAndFormat : list) {
            LocalDateTime value1 = timeAndFormat.getValue1();
            String formatv1 = value1.format(DateTimeFormatter.ofPattern(format1));
            Assert.assertEquals(formatv1, timeAndFormat.getValue2());
            String formatv2 = value1.format(DateTimeFormatter.ofPattern(format2));
            Assert.assertEquals(formatv2, timeAndFormat.getValue3());
            String formatv3 = value1.format(DateTimeFormatter.ofPattern(format3));
            Assert.assertEquals(formatv3, timeAndFormat.getValue4());
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` AS `value1`,DATE_FORMAT(t.`create_time`,'%Y年%m-01 %H:%i分%s秒') AS `value2`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') AS `value3`,DATE_FORMAT(t.`create_time`,'%Y/%m-/01 %H时%i分%s秒') AS `value4` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void dayOfYearMySQL() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<LocalDateTime, Integer>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.createTime(),
                        t_blog.createTime().dayOfYear()
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft2<LocalDateTime, Integer> item : list) {
            int dayOfYear = item.getValue1().getDayOfYear();
            Assert.assertEquals(item.getValue2().intValue(), dayOfYear);
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` AS `value1`,DAYOFYEAR(t.`create_time`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void dayOfWeekSunDayIsEndDayMySQL() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<LocalDateTime, Integer>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.createTime(),
                        t_blog.createTime().dayOfWeekSunDayIsLastDay()
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft2<LocalDateTime, Integer> item : list) {
            DayOfWeek dayOfWeek = item.getValue1().getDayOfWeek();
            Assert.assertEquals(item.getValue2().intValue(), dayOfWeek.getValue());
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` AS `value1`,(WEEKDAY(t.`create_time`)+1) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void dateTimePropMySQL() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft7<LocalDateTime, Integer, Integer, Integer, Integer, Integer, Integer>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.createTime(),
                        t_blog.createTime().year(),
                        t_blog.createTime().month(),
                        t_blog.createTime().day(),
                        t_blog.createTime().hour(),
                        t_blog.createTime().minute(),
                        t_blog.createTime().second()
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft7<LocalDateTime, Integer, Integer, Integer, Integer, Integer, Integer> item : list) {
            LocalDateTime value1 = item.getValue1();
            Assert.assertEquals(value1.getYear(), item.getValue2().intValue());
            Assert.assertEquals(value1.getMonth().ordinal()+1, item.getValue3().intValue());
            Assert.assertEquals(value1.getDayOfMonth(), item.getValue4().intValue());
            Assert.assertEquals(value1.getHour(), item.getValue5().intValue());
            Assert.assertEquals(value1.getMinute(), item.getValue6().intValue());
            Assert.assertEquals(value1.getSecond(), item.getValue7().intValue());
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` AS `value1`,YEAR(t.`create_time`) AS `value2`,MONTH(t.`create_time`) AS `value3`,DAYOFMONTH(t.`create_time`) AS `value4`,HOUR(t.`create_time`) AS `value5`,MINUTE(t.`create_time`) AS `value6`,SECOND(t.`create_time`) AS `value7` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void dateTimeProp2MySQL() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft7<LocalDateTime, Integer, Integer, Integer, Integer, Integer, Integer>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.createTime(),
                        t_blog.createTime().dateTimeProp(DateTimeUnitEnum.Year),
                        t_blog.createTime().dateTimeProp(DateTimeUnitEnum.Month),
                        t_blog.createTime().dateTimeProp(DateTimeUnitEnum.Day),
                        t_blog.createTime().dateTimeProp(DateTimeUnitEnum.Hour),
                        t_blog.createTime().dateTimeProp(DateTimeUnitEnum.Minute),
                        t_blog.createTime().dateTimeProp(DateTimeUnitEnum.Second)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft7<LocalDateTime, Integer, Integer, Integer, Integer, Integer, Integer> item : list) {
            LocalDateTime value1 = item.getValue1();
            Assert.assertEquals(value1.getYear(), item.getValue2().intValue());
            Assert.assertEquals(value1.getMonth().ordinal()+1, item.getValue3().intValue());
            Assert.assertEquals(value1.getDayOfMonth(), item.getValue4().intValue());
            Assert.assertEquals(value1.getHour(), item.getValue5().intValue());
            Assert.assertEquals(value1.getMinute(), item.getValue6().intValue());
            Assert.assertEquals(value1.getSecond(), item.getValue7().intValue());
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` AS `value1`,YEAR(t.`create_time`) AS `value2`,MONTH(t.`create_time`) AS `value3`,DAYOFMONTH(t.`create_time`) AS `value4`,HOUR(t.`create_time`) AS `value5`,MINUTE(t.`create_time`) AS `value6`,SECOND(t.`create_time`) AS `value7` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void durationMySQL() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        LocalDateTime time2025 = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        List<Draft6<LocalDateTime, Long, Long, Long, Long, Long>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.createTime(),
                        t_blog.createTime().duration(time2025).toDays(),
                        t_blog.createTime().duration(time2025).toHours(),
                        t_blog.createTime().duration(time2025).toMinutes(),
                        t_blog.createTime().duration(time2025).toSeconds(),
                        t_blog.createTime().duration(time2025).toValues(DateTimeDurationEnum.Days)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft6<LocalDateTime, Long, Long, Long, Long, Long> item : list) {
            LocalDateTime value1 = item.getValue1();
            Duration between = Duration.between(value1, time2025);

            Assert.assertEquals(between.toDays(), item.getValue2().longValue());
            Assert.assertEquals(between.toHours(), item.getValue3().longValue());
            Assert.assertEquals(between.toMinutes(), item.getValue4().longValue());
            Assert.assertEquals(between.getSeconds(), item.getValue5().longValue());
            Assert.assertEquals(between.toDays(), item.getValue6().longValue());
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` AS `value1`,timestampdiff(DAY, t.`create_time`, ?) AS `value2`,timestampdiff(HOUR, t.`create_time`, ?) AS `value3`,timestampdiff(MINUTE, t.`create_time`, ?) AS `value4`,timestampdiff(SECOND, t.`create_time`, ?) AS `value5`,timestampdiff(DAY, t.`create_time`, ?) AS `value6` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2025-01-01T00:00(LocalDateTime),2025-01-01T00:00(LocalDateTime),2025-01-01T00:00(LocalDateTime),2025-01-01T00:00(LocalDateTime),2025-01-01T00:00(LocalDateTime),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void booleanMySQL() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Boolean, Boolean>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.deleted(),
                        t_blog.deleted().not()
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft2<Boolean, Boolean> item : list) {
            Assert.assertEquals(item.getValue1(), !item.getValue2());
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`deleted` AS `value1`,(NOT (t.`deleted`)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testConstant(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<LocalDateTime, LocalDateTime>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.expression().now(),
                        t_blog.expression().utcNow()
                )).toList();
        Assert.assertFalse(list.isEmpty());
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT NOW() AS `value1`,UTC_TIMESTAMP() AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
