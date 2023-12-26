package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft7;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/12/23 23:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyTest1 extends BaseTest {

    @Test
    public void testDraft1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, Integer>> list2 = entityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.title().length().eq(123);
//                    o.createTime().
//                    LocalDateTime.now().plus(1, TimeUnit.MILLISECONDS)
                })
                .groupBy(o -> o.content())
                .selectDraft(o -> Select.draft(
                        o.groupKeys(0).toDraft(String.class),
                        o.content().length()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`content` AS `value1`,CHAR_LENGTH(t.`content`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND CHAR_LENGTH(t.`title`) = ? GROUP BY t.`content`" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, Integer>> list2 = entityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.title().toNumber(Integer.class).asAny().eq(123);
//                    o.createTime().
//                    LocalDateTime.now().plus(1, TimeUnit.MILLISECONDS)
                })
                .groupBy(o -> o.content())
                .selectDraft(o -> Select.draft(
                        o.groupKeys(0).toDraft(String.class),
                        o.content().length()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`content` AS `value1`,CHAR_LENGTH(t.`content`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND CAST(t.`title` AS SIGNED) = ? GROUP BY t.`content`" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft3() {
        {
            Draft1<LocalDateTime> localDateTimeDraft1 = entityQuery.queryable(BlogEntity.class)
                    .selectDraft(o -> Select.draft(
                            o.createTime()
                    )).firstOrNull();
            Assert.assertNotNull(localDateTimeDraft1);
            LocalDateTime value1 = localDateTimeDraft1.getValue1();
            Assert.assertEquals(2020, value1.getYear());
            Assert.assertEquals(1, value1.getMonth().getValue());
            Assert.assertEquals(1, value1.getDayOfMonth());
        }
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft1<LocalDateTime> localDateTimeDraft1 = entityQuery.queryable(BlogEntity.class)
                .selectDraft(o -> Select.draft(
                        o.createTime().plus(2, TimeUnit.DAYS)
                )).firstOrNull();
        Assert.assertNotNull(localDateTimeDraft1);
        LocalDateTime value1 = localDateTimeDraft1.getValue1();
        Assert.assertEquals(2020, value1.getYear());
        Assert.assertEquals(1, value1.getMonth().getValue());
        Assert.assertEquals(3, value1.getDayOfMonth());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(t.`create_time`, interval (?) microsecond) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("172800000000(Long),false(Boolean)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft4() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft1<LocalDateTime> localDateTimeDraft1 = entityQuery.queryable(BlogEntity.class)
                .selectDraft(o -> Select.draft(
                        o.createTime().plusMonths(2)
                )).firstOrNull();
        Assert.assertNotNull(localDateTimeDraft1);
        LocalDateTime value1 = localDateTimeDraft1.getValue1();
        Assert.assertEquals(2020, value1.getYear());
        Assert.assertEquals(3, value1.getMonth().getValue());
        Assert.assertEquals(1, value1.getDayOfMonth());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(t.`create_time`, interval (?) month) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft5() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft1<LocalDateTime> localDateTimeDraft1 = entityQuery.queryable(BlogEntity.class)
                .selectDraft(o -> Select.draft(
                        o.createTime().plusMonths(12).plus(1, TimeUnit.DAYS)
                )).firstOrNull();
        Assert.assertNotNull(localDateTimeDraft1);
        LocalDateTime value1 = localDateTimeDraft1.getValue1();
        Assert.assertEquals(2021, value1.getYear());
        Assert.assertEquals(1, value1.getMonth().getValue());
        Assert.assertEquals(2, value1.getDayOfMonth());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(date_add(t.`create_time`, interval (?) month), interval (?) microsecond) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12(Integer),86400000000(Long),false(Boolean)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft6() {
        String id = "123456zz";
        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z" );
        blog.setCreateTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        blog.setUpdateBy("z" );
        blog.setUpdateTime(LocalDateTime.of(2022, 1, 1, 0, 0));
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
        entityQuery.insertable(blog)
                .executeRows();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft3<LocalDateTime, LocalDateTime, LocalDateTime> draft3 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .where(o -> {
                    o.createTime().plusYears(1).le(LocalDateTime.of(2023, 1, 1, 0, 0));
                })
                .selectDraft(o -> Select.draft(
                        o.createTime().plusMonths(12).plus(1, TimeUnit.DAYS),
                        o.createTime().plusYears(1),
                        o.createTime().plus(3, TimeUnit.SECONDS)
                )).firstOrNull();
        Assert.assertNotNull(draft3);
        LocalDateTime value1 = draft3.getValue1();
        Assert.assertEquals(2023, value1.getYear());
        Assert.assertEquals(1, value1.getMonth().getValue());
        Assert.assertEquals(2, value1.getDayOfMonth());
        LocalDateTime value2 = draft3.getValue2();
        Assert.assertEquals(2023, value2.getYear());
        Assert.assertEquals(1, value2.getMonth().getValue());
        Assert.assertEquals(1, value2.getDayOfMonth());
        LocalDateTime value3 = draft3.getValue3();
        Assert.assertEquals(2022, value3.getYear());
        Assert.assertEquals(1, value3.getMonth().getValue());
        Assert.assertEquals(1, value3.getDayOfMonth());
        Assert.assertEquals(3, value3.getSecond());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(date_add(t.`create_time`, interval (?) month), interval (?) microsecond) AS `value1`,date_add(t.`create_time`, interval (?) year) AS `value2`,date_add(t.`create_time`, interval (?) microsecond) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND date_add(t.`create_time`, interval (?) year) <= ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12(Integer),86400000000(Long),1(Integer),3000000(Long),false(Boolean),123456zz(String),1(Integer),2023-01-01T00:00(LocalDateTime)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }

    @Test
    public void testDraft7() {
        String id = "123456zz7";
        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z" );
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5, 6));
        blog.setUpdateBy("z" );
        blog.setUpdateTime(LocalDateTime.of(2022, 1, 1, 0, 0));
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
        entityQuery.insertable(blog)
                .executeRows();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> draft3 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .where(o -> {
                    o.createTime().plusYears(1).le(LocalDateTime.of(2023, 2, 1, 0, 0));
                })
                .selectDraft(o -> Select.draft(
                        o.createTime().year(),
                        o.createTime().month(),
                        o.createTime().day(),
                        o.createTime().hour(),
                        o.createTime().minute(),
                        o.createTime().second(),
                        o.createTime().dayOfWeek()
                )).firstOrNull();

        Assert.assertNotNull(draft3);
        Integer value1 = draft3.getValue1();
        Assert.assertEquals(2022, (int) value1);
        Integer value2 = draft3.getValue2();
        Assert.assertEquals(1, (int) value2);
        Integer value3 = draft3.getValue3();
        Assert.assertEquals(2, (int) value3);
        Integer value4 = draft3.getValue4();
        Assert.assertEquals(3, (int) value4);
        Integer value5 = draft3.getValue5();
        Assert.assertEquals(4, (int) value5);
        Integer value6 = draft3.getValue6();
        Assert.assertEquals(5, (int) value6);
        Integer value7 = draft3.getValue7();
        Assert.assertEquals(0, (int) value7);
        DayOfWeek dayOfWeek = LocalDateTime.of(2022, 1, 2, 3, 4, 5, 6).getDayOfWeek();

        Assert.assertEquals(DayOfWeek.SUNDAY, dayOfWeek);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT year(t.`create_time`) AS `value1`,month(t.`create_time`) AS `value2`,dayofmonth(t.`create_time`) AS `value3`,hour(t.`create_time`) AS `value4`,minute(t.`create_time`) AS `value5`,second(t.`create_time`) AS `value6`,(dayofweek(t.`create_time`)-1) AS `value7` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND date_add(t.`create_time`, interval (?) year) <= ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123456zz7(String),1(Integer),2023-02-01T00:00(LocalDateTime)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }

    @Test
    public void testDraft8() {
        String id = "123456zz8";
        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z" );
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 13, 4, 5, 6));
        blog.setUpdateBy("z" );
        blog.setUpdateTime(LocalDateTime.of(2022, 1, 1, 0, 0));
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
        entityQuery.insertable(blog)
                .executeRows();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> draft3 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .where(o -> {
                    o.createTime().plusYears(1).le(LocalDateTime.of(2023, 2, 1, 0, 0));
                })
                .selectDraft(o -> Select.draft(
                        o.createTime().year(),
                        o.createTime().month(),
                        o.createTime().day(),
                        o.createTime().hour(),
                        o.createTime().minute(),
                        o.createTime().second(),
                        o.createTime().dayOfWeek()
                )).firstOrNull();

        Assert.assertNotNull(draft3);
        Integer value1 = draft3.getValue1();
        Assert.assertEquals(2022, (int) value1);
        Integer value2 = draft3.getValue2();
        Assert.assertEquals(1, (int) value2);
        Integer value3 = draft3.getValue3();
        Assert.assertEquals(2, (int) value3);
        Integer value4 = draft3.getValue4();
        Assert.assertEquals(13, (int) value4);
        Integer value5 = draft3.getValue5();
        Assert.assertEquals(4, (int) value5);
        Integer value6 = draft3.getValue6();
        Assert.assertEquals(5, (int) value6);
        Integer value7 = draft3.getValue7();
        Assert.assertEquals(0, (int) value7);
        DayOfWeek dayOfWeek = LocalDateTime.of(2022, 1, 2, 3, 4, 5, 6).getDayOfWeek();

        Assert.assertEquals(DayOfWeek.SUNDAY, dayOfWeek);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT year(t.`create_time`) AS `value1`,month(t.`create_time`) AS `value2`,dayofmonth(t.`create_time`) AS `value3`,hour(t.`create_time`) AS `value4`,minute(t.`create_time`) AS `value5`,second(t.`create_time`) AS `value6`,(dayofweek(t.`create_time`)-1) AS `value7` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND date_add(t.`create_time`, interval (?) year) <= ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123456zz8(String),1(Integer),2023-02-01T00:00(LocalDateTime)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }

    @Test
    public void testDraft9() {
        String id = "123456zz9";
        entityQuery.deletable(BlogEntity.class)
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
        entityQuery.insertable(blog)
                .executeRows();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft7<Long, Long, Long, Long, Long, Long, Long> draft3 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .selectDraft(o -> Select.draft(
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Hours),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Minutes),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(1,TimeUnit.DAYS), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.createTime().plus(2,TimeUnit.SECONDS),DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(3,TimeUnit.MINUTES),DateTimeDurationEnum.Minutes)
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

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT timestampdiff(day, t.`update_time`, t.`create_time`) AS `value1`,timestampdiff(hour, t.`update_time`, t.`create_time`) AS `value2`,timestampdiff(minute, t.`update_time`, t.`create_time`) AS `value3`,timestampdiff(second, t.`update_time`, t.`create_time`) AS `value4`,timestampdiff(day, date_add(t.`create_time`, interval (?) microsecond), t.`create_time`) AS `value5`,timestampdiff(second, date_add(t.`create_time`, interval (?) microsecond), t.`create_time`) AS `value6`,timestampdiff(minute, date_add(t.`create_time`, interval (?) microsecond), t.`create_time`) AS `value7` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1" , jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("86400000000(Long),2000000(Long),180000000(Long),false(Boolean),123456zz9(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }
}
