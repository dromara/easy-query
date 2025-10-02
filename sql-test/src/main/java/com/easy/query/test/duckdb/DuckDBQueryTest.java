package com.easy.query.test.duckdb;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.draft.Draft7;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * create time 2025/10/2 15:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBQueryTest  extends DuckDBBaseTest{


    @Test
    public void testDraft9() {
        String id = "123456zz9";
        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z" );
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setUpdateBy("z" );
        blog.setUpdateTime(LocalDateTime.of(2022, 2, 3, 4, 5, 6));
        blog.setTitle("titlez" );
        blog.setContent("contentz" );
        blog.setUrl("http://blog.easy-query.com/z" );
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2" ));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2" ).multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        easyEntityQuery.insertable(blog)
                .executeRows();
        Draft3<LocalDateTime, LocalDateTime, LocalDateTime> draft31 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plus(1, TimeUnitEnum.DAYS),
                        o.createTime().plus(2, TimeUnitEnum.SECONDS),
                        o.createTime().plus(3, TimeUnitEnum.MINUTES)
                )).firstOrNull();

        Duration between = Duration.between(blog.getUpdateTime(), blog.getCreateTime());
        long days = between.toDays();
        long hours = between.toHours();

        long minutes = between.toMinutes();


        Duration between1 = Duration.between(blog.getCreateTime(), blog.getUpdateTime());
        long days1 = between1.toDays();
        long hours1 = between1.toHours();

        long minutes1 = between1.toMinutes();

        Draft7<Long, Long, Long, Long, Long, Long, Long> draft3 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.updateTime().duration(o.createTime()).toDays(),
                        o.updateTime().duration(o.createTime()).toHours(),
                        o.updateTime().duration(o.createTime()).toMinutes(),
                        o.updateTime().duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).duration(o.createTime()).toDays(),
                        o.createTime().plus(2,TimeUnitEnum.SECONDS).duration(o.createTime()).toSeconds(),
                        o.createTime().plus(3,TimeUnitEnum.MINUTES).duration(o.createTime()).toMinutes()
                )).firstOrNull();

        Assert.assertNotNull(draft3);
        Long value1 = draft3.getValue1();
        Assert.assertEquals(-32, (long) value1);
        Long value2 = draft3.getValue2();
        Assert.assertEquals(-769, (long) value2);
        Long value3 = draft3.getValue3();
        Assert.assertEquals(-46141, (long) value3);
        Long value4 = draft3.getValue4();
        Assert.assertEquals(-2768461, (long) value4);
        Long value5 = draft3.getValue5();
        Assert.assertEquals(-1, (long) value5);
        Long value6 = draft3.getValue6();
        Assert.assertEquals(-2, (long) value6);
        Long value7 = draft3.getValue7();
        Assert.assertEquals(-3, (long) value7);
        Draft7<Long, Long, Long, Long, Long, Long, Long> draft4 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        SQLMathExpression.abs(o.updateTime().duration(o.createTime()).toDays()),
                        o.updateTime().duration(o.createTime()).toHours(),
                        o.updateTime().duration(o.createTime()).toMinutes(),
                        o.updateTime().duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).duration(o.createTime()).toDays(),
                        o.createTime().plus(2,TimeUnitEnum.SECONDS).duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).plus(3,TimeUnitEnum.MINUTES).duration(o.createTime()).toMinutes()
                )).firstNotNull();
        Assert.assertEquals(-1443,(long)draft4.getValue7());

        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }



    @Test
    public void testDraft9_1() {
        String id = "123456zz91";
        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z" );
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setUpdateBy("z" );
        blog.setUpdateTime(LocalDateTime.of(2022, 2, 3, 4, 5, 6));
        blog.setTitle("titlez" );
        blog.setContent("contentz" );
        blog.setUrl("http://blog.easy-query.com/z" );
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2" ));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2" ).multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        easyEntityQuery.insertable(blog)
                .executeRows();
        Draft3<LocalDateTime, LocalDateTime, LocalDateTime> draft31 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plus(1, TimeUnitEnum.DAYS),
                        o.createTime().plus(2, TimeUnitEnum.SECONDS),
                        o.createTime().plus(3, TimeUnitEnum.MINUTES)
                )).firstOrNull();

        Draft7<Long, Long, Long, Long, Long, Long, Long> draft3 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.updateTime().duration(o.createTime()).toDays(),
                        o.updateTime().duration(o.createTime()).toHours(),
                        o.updateTime().duration(o.createTime()).toMinutes(),
                        o.updateTime().duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).duration(o.createTime()).toDays(),
                        o.createTime().plus(2,TimeUnitEnum.SECONDS).duration(o.createTime()).toSeconds(),
                        o.createTime().plus(3,TimeUnitEnum.MINUTES).duration(o.createTime()).toMinutes()
                ))
//                .select(o -> Select.DRAFT.of(
//                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Days),
//                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Hours),
//                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Minutes),
//                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Seconds),
//                        o.createTime().duration(o.createTime().plus(1,TimeUnitEnum.DAYS), DateTimeDurationEnum.Days),
//                        o.createTime().duration(o.createTime().plus(2,TimeUnitEnum.SECONDS),DateTimeDurationEnum.Seconds),
//                        o.createTime().duration(o.createTime().plus(3,TimeUnitEnum.MINUTES),DateTimeDurationEnum.Minutes)
//                ))
                .firstOrNull();

        Assert.assertNotNull(draft3);
        Long value1 = draft3.getValue1();
        Assert.assertEquals(-32, (long) value1);
        Long value2 = draft3.getValue2();
        Assert.assertEquals(-769, (long) value2);
        Long value3 = draft3.getValue3();
        Assert.assertEquals(-46141, (long) value3);
        Long value4 = draft3.getValue4();
        Assert.assertEquals(-2768461, (long) value4);
        Long value5 = draft3.getValue5();
        Assert.assertEquals(-1, (long) value5);
        Long value6 = draft3.getValue6();
        Assert.assertEquals(-2, (long) value6);
        Long value7 = draft3.getValue7();
        Assert.assertEquals(-3, (long) value7);
        Draft7<Long, Long, Long, Long, Long, Long, Long> draft4 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        SQLMathExpression.abs(o.updateTime().duration(o.createTime()).toDays()),
                        o.updateTime().duration(o.createTime()).toHours(),
                        o.updateTime().duration(o.createTime()).toMinutes(),
                        o.updateTime().duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).duration(o.createTime()).toDays(),
                        o.createTime().plus(2,TimeUnitEnum.SECONDS).duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).plus(3,TimeUnitEnum.MINUTES).duration(o.createTime()).toMinutes()
                )).firstOrNull();
        Assert.assertEquals(-1443,(long)draft4.getValue7());

        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }



    @Test
    public void datetimeformat1() {

        String formater = "yyyy年MM-01 HH:mm分ss秒" ;
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<LocalDateTime, String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(d -> Select.DRAFT.of(
                        d.createTime(),
                        d.createTime().format(formater)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft2<LocalDateTime, String> timeAndFormat : list) {
            LocalDateTime value1 = timeAndFormat.getValue1();
            String format = value1.format(DateTimeFormatter.ofPattern(formater));
            Assert.assertEquals(format, timeAndFormat.getValue2());
        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"create_time\" AS \"value1\",strftime('%Y年%m-01 %H:%M分%S秒', t.\"create_time\") AS \"value2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }



    @Test
    public void testConcat() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.expression().stringFormat("你好:{0}我叫{1}你好吗?我今年{2}岁了,{0}你呢", t_blog.title(), t_blog.star(), 12)
                            .eq("比较一下");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND CONCAT(?,\"title\",?,\"star\",?,?,?,\"title\",?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),你好:(String),我叫(String),你好吗?我今年(String),12(Integer),岁了,(String),你呢(String),比较一下(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testConcat2() {
        Exception ex = null;
        try {

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.expression().stringFormat("你好:{0}我叫{1}你好吗?我今年{2}岁了,{3}你呢", t_blog.title(), t_blog.star(), 12)
                                .eq("比较一下");
                    }).toList();
        } catch (Exception e) {
            ex = e;
        }
        Assert.assertNotNull(ex);
        Assert.assertTrue(ex instanceof EasyQueryInvalidOperationException);
        Assert.assertEquals("Mismatch: provided 3 arguments, but the format string expects a different number.", ex.getMessage());
    }

    @Test
    public void testConcat3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.expression().stringFormat("你好:{0}我叫{1}你好吗?我今年{1}岁了", t_blog.title(), t_blog.star())
                            .eq("比较一下");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND CONCAT(?,\"title\",?,\"star\",?,\"star\",?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),你好:(String),我叫(String),你好吗?我今年(String),岁了(String),比较一下(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void datetimeformat() {

        String formater = "yyyy年MM-01 HH:mm分ss秒";
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<LocalDateTime, String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(d -> Select.DRAFT.of(
                        d.createTime(),
                        d.createTime().format(formater)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft2<LocalDateTime, String> timeAndFormat : list) {
            LocalDateTime value1 = timeAndFormat.getValue1();
            String format = value1.format(DateTimeFormatter.ofPattern(formater));
            Assert.assertEquals(format, timeAndFormat.getValue2());
        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"create_time\" AS \"value1\",strftime('%Y年%m-01 %H:%M分%S秒', t.\"create_time\") AS \"value2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


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
        Assert.assertEquals("SELECT t.\"create_time\" AS \"value1\",strftime('%Y年%m-01 %H:%M分%S秒', t.\"create_time\") AS \"value2\",strftime('%Y-%m-%d %H:%M:%S', t.\"create_time\") AS \"value3\",strftime('%Y/%m-/01 %H时%M分%S秒', t.\"create_time\") AS \"value4\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

}
