package com.easy.query.test;

import com.easy.query.api.proxy.base.BigDecimalProxy;
import com.easy.query.api.proxy.base.LongProxy;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.draft.Draft7;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.grouping.Grouping1;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.TopicSubQueryBlog;
import com.easy.query.test.dto.proxy.TopicSubQueryBlogProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.BlogGroupIdAndName;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.BlogGroupIdAndNameProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
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

        List<Draft2<String, Integer>> list2 = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.title().length().eq(123);
//                    o.createTime().
//                    LocalDateTime.now().plus(1, TimeUnit.MILLISECONDS)
                })
                .groupBy(o -> GroupKeys.of(o.content()))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.groupTable().content().length()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`content` AS `value1`,CHAR_LENGTH(t.`content`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND CHAR_LENGTH(t.`title`) = ? GROUP BY t.`content`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft1_1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, Integer>> list2 = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.title().length().eq(123);
//                    o.createTime().
//                    LocalDateTime.now().plus(1, TimeUnit.MILLISECONDS)
                })
                .groupBy(o -> GroupKeys.of(o.content()))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.groupTable().content().length()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`content` AS `value1`,CHAR_LENGTH(t.`content`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND CHAR_LENGTH(t.`title`) = ? GROUP BY t.`content`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, Integer>> list2 = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.title().toNumber(Integer.class).asAny().eq(123);
//                    o.createTime().
//                    LocalDateTime.now().plus(1, TimeUnit.MILLISECONDS)
                })
                .groupBy(o -> GroupKeys.of(o.content()))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.groupTable().content().length()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`content` AS `value1`,CHAR_LENGTH(t.`content`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND CAST(t.`title` AS SIGNED) = ? GROUP BY t.`content`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft3() {
        {
            Draft1<LocalDateTime> localDateTimeDraft1 = easyEntityQuery.queryable(BlogEntity.class)
                    .select(o -> Select.DRAFT.of(
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

        Draft1<LocalDateTime> localDateTimeDraft1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plus(2, TimeUnit.DAYS)
                )).firstOrNull();
        Assert.assertNotNull(localDateTimeDraft1);
        LocalDateTime value1 = localDateTimeDraft1.getValue1();
        Assert.assertEquals(2020, value1.getYear());
        Assert.assertEquals(1, value1.getMonth().getValue());
        Assert.assertEquals(3, value1.getDayOfMonth());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(t.`create_time`, interval (?) microsecond) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("172800000000(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft4() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft1<LocalDateTime> localDateTimeDraft1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plusMonths(2)
                )).firstOrNull();
        Assert.assertNotNull(localDateTimeDraft1);
        LocalDateTime value1 = localDateTimeDraft1.getValue1();
        Assert.assertEquals(2020, value1.getYear());
        Assert.assertEquals(3, value1.getMonth().getValue());
        Assert.assertEquals(1, value1.getDayOfMonth());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(t.`create_time`, interval (?) month) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft5() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft1<LocalDateTime> localDateTimeDraft1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plusMonths(12).plus(1, TimeUnit.DAYS)
                )).firstOrNull();
        Assert.assertNotNull(localDateTimeDraft1);
        LocalDateTime value1 = localDateTimeDraft1.getValue1();
        Assert.assertEquals(2021, value1.getYear());
        Assert.assertEquals(1, value1.getMonth().getValue());
        Assert.assertEquals(2, value1.getDayOfMonth());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(date_add(t.`create_time`, interval (?) month), interval (?) microsecond) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12(Long),86400000000(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testDraft6() {
        String id = "123456zz";
        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z");
        blog.setCreateTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        blog.setUpdateBy("z");
        blog.setUpdateTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        blog.setTitle("titlez");
        blog.setContent("contentz");
        blog.setUrl("http://blog.easy-query.com/z");
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2"));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        easyEntityQuery.insertable(blog)
                .executeRows();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft3<LocalDateTime, LocalDateTime, LocalDateTime> draft3 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .where(o -> {
                    o.createTime().plusYears(1).le(LocalDateTime.of(2023, 1, 1, 0, 0));
                })
                .select(o -> Select.DRAFT.of(
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
        Assert.assertEquals("SELECT date_add(date_add(t.`create_time`, interval (?) month), interval (?) microsecond) AS `value1`,date_add(t.`create_time`, interval (?) year) AS `value2`,date_add(t.`create_time`, interval (?) microsecond) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND date_add(t.`create_time`, interval (?) year) <= ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12(Long),86400000000(Long),1(Long),3000000(Long),false(Boolean),123456zz(String),1(Long),2023-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }

    @Test
    public void testDraft7() {
        String id = "123456zz7";
        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z");
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5, 6));
        blog.setUpdateBy("z");
        blog.setUpdateTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        blog.setTitle("titlez");
        blog.setContent("contentz");
        blog.setUrl("http://blog.easy-query.com/z");
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2"));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        easyEntityQuery.insertable(blog)
                .executeRows();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> draft3 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .where(o -> {
                    o.createTime().plusYears(1).le(LocalDateTime.of(2023, 2, 1, 0, 0));
                })
                .select(o -> Select.DRAFT.of(
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
        Assert.assertEquals("SELECT YEAR(t.`create_time`) AS `value1`,MONTH(t.`create_time`) AS `value2`,DAYOFMONTH(t.`create_time`) AS `value3`,HOUR(t.`create_time`) AS `value4`,MINUTE(t.`create_time`) AS `value5`,SECOND(t.`create_time`) AS `value6`,(DAYOFWEEK(t.`create_time`)-1) AS `value7` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND date_add(t.`create_time`, interval (?) year) <= ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123456zz7(String),1(Long),2023-02-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }

    @Test
    public void testDraft8() {
        String id = "123456zz8";
        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z");
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 13, 4, 5, 6));
        blog.setUpdateBy("z");
        blog.setUpdateTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        blog.setTitle("titlez");
        blog.setContent("contentz");
        blog.setUrl("http://blog.easy-query.com/z");
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2"));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        easyEntityQuery.insertable(blog)
                .executeRows();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> draft3 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .where(o -> {
                    o.createTime().plusYears(1).le(LocalDateTime.of(2023, 2, 1, 0, 0));
                })
                .select(o -> Select.DRAFT.of(
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
        Assert.assertEquals("SELECT YEAR(t.`create_time`) AS `value1`,MONTH(t.`create_time`) AS `value2`,DAYOFMONTH(t.`create_time`) AS `value3`,HOUR(t.`create_time`) AS `value4`,MINUTE(t.`create_time`) AS `value5`,SECOND(t.`create_time`) AS `value6`,(DAYOFWEEK(t.`create_time`)-1) AS `value7` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND date_add(t.`create_time`, interval (?) year) <= ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123456zz8(String),1(Long),2023-02-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }

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
        blog.setCreateBy("z");
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setUpdateBy("z");
        blog.setUpdateTime(LocalDateTime.of(2022, 2, 3, 4, 5, 6));
        blog.setTitle("titlez");
        blog.setContent("contentz");
        blog.setUrl("http://blog.easy-query.com/z");
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2"));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        easyEntityQuery.insertable(blog)
                .executeRows();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft7<Long, Long, Long, Long, Long, Long, Long> draft3 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Hours),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Minutes),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(1, TimeUnit.DAYS), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.createTime().plus(2, TimeUnit.SECONDS), DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(3, TimeUnit.MINUTES), DateTimeDurationEnum.Minutes)
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
        Assert.assertEquals("SELECT timestampdiff(DAY, t.`update_time`, t.`create_time`) AS `value1`,timestampdiff(HOUR, t.`update_time`, t.`create_time`) AS `value2`,timestampdiff(MINUTE, t.`update_time`, t.`create_time`) AS `value3`,timestampdiff(SECOND, t.`update_time`, t.`create_time`) AS `value4`,timestampdiff(DAY, date_add(t.`create_time`, interval (?) microsecond), t.`create_time`) AS `value5`,timestampdiff(SECOND, date_add(t.`create_time`, interval (?) microsecond), t.`create_time`) AS `value6`,timestampdiff(MINUTE, date_add(t.`create_time`, interval (?) microsecond), t.`create_time`) AS `value7` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("86400000000(Long),2000000(Long),180000000(Long),false(Boolean),123456zz9(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }

    @Test
    public void testExists() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft4<String, String, LocalDateTime, String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().like("123");
                    o.expression().exists(() -> {
                        return easyEntityQuery.queryable(BlogEntity.class)
                                .where(x -> x.id().eq(o.id()));
                    });
                    //创建时间和现在的相差天数现在小于0
                    o.createTime().duration(LocalDateTime.of(2021, 1, 1, 1, 1)).toDays().le(0L);
                })
                .select(o -> Select.DRAFT.of(
                        o.id(),
                        o.title(),
                        o.createTime(),
                        o.expression().subQuery(() -> {
                            return easyEntityQuery.queryable(BlogEntity.class)
                                    .where(x -> x.id().eq(o.id()))
                                    .select(x -> new StringProxy(x.title()));
                        })
                )).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t.`title` AS `value2`,t.`create_time` AS `value3`,(SELECT t2.`title` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`id` = t.`id`) AS `value4` FROM `t_topic` t WHERE t.`title` LIKE ? AND EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AND timestampdiff(DAY, t.`create_time`, ?) <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String),false(Boolean),2021-01-01T01:01(LocalDateTime),0(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }
    @Test
    public void testSelect1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().isNotNull())
                .select(o -> new BlogEntityProxy() {
                    {
                        title().set("1");
                        id().set(o.title());
                    }
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? AS `title`,t.`title` AS `id` FROM `t_topic` t WHERE t.`title` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelect2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().isNotNull())
                .select(o -> new BlogEntityProxy() {
                    {
                        id().set(o.title());
                        title().setSQL("1+1+{0}", c -> c.expression(o.stars()));
                    }
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`title` AS `id`,1+1+t.`stars` AS `title` FROM `t_topic` t WHERE t.`title` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelect3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select(o -> new BlogEntityProxy() {
                    {
                        id().set(o.title());
                        id().set(o.createTime().format("yyyy-MM-dd"));
                        title().set(o.title().subString(1, 2));
                    }
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`title` AS `id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d') AS `id`,SUBSTR(t.`title`,2,2) AS `title` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelect4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().isNotNull())
                .select(o -> new BlogEntityProxy() {
                    {
                        selectExpression(o.id(), o.title(), o.createTime().format("yyyy-MM-dd").as(title()));
                    }
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`title`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d') AS `title` FROM `t_topic` t WHERE t.`title` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelect5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().isNotNull())
                .select(o -> new BlogEntityProxy() {
                    {
                        id().set(o.title());
                        title().set(o.title().subString(1, 2));
                    }
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`title` AS `id`,SUBSTR(t.`title`,2,2) AS `title` FROM `t_topic` t WHERE t.`title` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelect6() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogGroupIdAndName> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select(o -> new BlogEntityProxy() {
                    {
                        id().set(o.title());
                        title().set(o.title().subString(1, 2));
//                        content().set(o.title().subString(1, 2).asAny().toStr());
                    }
                })
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(o -> new BlogGroupIdAndNameProxy() {
                    {
                        id().set(o.key1());
                        idCount().set(o.count());
                    }
                })
                .orderBy(o -> {
                    o.idCount().desc();
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`id` AS `id`,t2.`id_count` AS `id_count` FROM (SELECT t1.`id` AS `id`,COUNT(*) AS `id_count` FROM (SELECT t.`title` AS `id`,SUBSTR(t.`title`,2,2) AS `title` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?) t1 GROUP BY t1.`id`) t2 ORDER BY t2.`id_count` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelect6_1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogGroupIdAndName> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select(o -> new BlogEntityProxy() {
                    {
                        id().set(o.title());
                        title().set(o.title().subString(1, 2));
//                        content().set(o.title().subString(1, 2).asAny().toStr());
                    }
                })
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(BlogGroupIdAndName.class, group -> Select.of(
                        group.key1().as(BlogGroupIdAndName::getId),
                        group.count().as(BlogGroupIdAndName::getIdCount)
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id` AS `id`,COUNT(*) AS `id_count` FROM (SELECT t.`title` AS `id`,SUBSTR(t.`title`,2,2) AS `title` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?) t1 GROUP BY t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testSelect6_2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogGroupIdAndName> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select(o -> new BlogEntityProxy() {
                    {
                        id().set(o.title());
                        title().set(o.title().subString(1, 2));
//                        content().set(o.title().subString(1, 2).asAny().toStr());
                    }
                })
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(BlogGroupIdAndName.class, group -> Select.of(
                        group.key1().as(BlogGroupIdAndName::getId),
                        group.groupTable().id().min().as(BlogGroupIdAndName::getIdCount)
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id` AS `id`,MIN(t1.`id`) AS `id_count` FROM (SELECT t.`title` AS `id`,SUBSTR(t.`title`,2,2) AS `title` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?) t1 GROUP BY t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testSelect6_3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogGroupIdAndName> list = easyEntityQuery.queryable(Topic.class)
                .asTracking()
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                .where((t, b2) -> {
                    t.title().isNotNull();
                    t.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .groupBy((t1, b2) -> GroupKeys.of(t1.id(), b2.star()))
                .select(group -> new BlogGroupIdAndNameProxy().adapter(r -> {
                    r.id().set(group.key1());
                    r.idCount().set(group.groupTable().t2.id().count());
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t1.`id`) AS `id_count` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`title` IS NOT NULL AND t.`create_time` <= ? GROUP BY t.`id`,t1.`star`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    private <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>, TProperty> void set(TResult val) {

    }

    @Test
    public void testSelect7() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, Long>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select(o -> Select.DRAFT.of(o.title(), o.title().subString(1, 2)))
                .groupBy(o -> GroupKeys.of(o.value1()))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.count()
                ))
                .orderBy(o -> {
                    o.value1().desc();
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`value1` AS `value1`,t2.`value2` AS `value2` FROM (SELECT t1.`value1` AS `value1`,COUNT(*) AS `value2` FROM (SELECT t.`title` AS `value1`,SUBSTR(t.`title`,2,2) AS `value2` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?) t1 GROUP BY t1.`value1`) t2 ORDER BY t2.`value1` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSelect8() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<String> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                }).select(o -> new StringProxy(o.title())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`title` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


        List<BigDecimal> list1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                }).select(o -> new BigDecimalProxy(o.title().toNumber(BigDecimal.class))).toList();
    }

    @Test
    public void testSelect9() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, Long>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select(o -> Select.DRAFT.of(o.title(), o.title().subString(1, 2)))
                .groupBy(o -> {
                    return GroupKeys.of(o.value1());
                })
                .orderBy(o -> {
                    o.key1().desc();
                })
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.count()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`value1` AS `value1`,COUNT(*) AS `value2` FROM (SELECT t.`title` AS `value1`,SUBSTR(t.`title`,2,2) AS `value2` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?) t1 GROUP BY t1.`value1` ORDER BY t1.`value1` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


    }

    @Test
    public void testSelect10() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<TopicSubQueryBlog> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().isNotNull())
                .select(o -> new TopicSubQueryBlogProxy().adapter(r -> {

                    r.selectAll(o);
                    r.blogCount().setSubQuery(easyEntityQuery.queryable(BlogEntity.class).where(x -> x.id().eq(o.id())).selectCount());
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,(SELECT COUNT(*) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test

    public void testGroup11() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .groupBy(o -> GroupKeys.of(o.title().subString(1, 2)))
                .select(g -> new BlogEntityProxy().adapter(r -> {

                    r.id().set(g.key1());
                    r.star().set(g.groupTable().title().subString(1, 2).intCount());
                })).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.`title`,2,2) AS `id`,COUNT(SUBSTR(t.`title`,2,2)) AS `star` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ? GROUP BY SUBSTR(t.`title`,2,2)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test

    public void testGroup12() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select(o -> Select.DRAFT.of(o.title(), o.title().subString(1, 2)))
                .groupBy(o -> {
                    return GroupKeys.of(o.value1());
                })
                .select(g -> new BlogEntityProxy().adapter(r -> {
                    r.id().set(g.key1());
                    r.star().set(g.groupTable().value2().intCount());
                })).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`value1` AS `id`,COUNT(t1.`value2`) AS `star` FROM (SELECT t.`title` AS `value1`,SUBSTR(t.`title`,2,2) AS `value2` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?) t1 GROUP BY t1.`value1`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test

    public void testGroup13() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select(o -> Select.DRAFT.of(o.title(), o.title().subString(1, 2)))
                .groupBy(o -> {
                    return GroupKeys.of(o.value1());
                })
                .orderBy(o -> o.key1().asc())
                .select(g -> new BlogEntityProxy().adapter(r -> {
                    r.id().set(g.key1());
                    r.star().set(g.groupTable().value2().intCount());
                })).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`value1` AS `id`,COUNT(t1.`value2`) AS `star` FROM (SELECT t.`title` AS `value1`,SUBSTR(t.`title`,2,2) AS `value2` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?) t1 GROUP BY t1.`value1` ORDER BY t1.`value1` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test

    public void testGroup14() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select(o -> Select.DRAFT.of(o.title(), o.title().subString(1, 2)))
                .groupBy(o -> {
                    return GroupKeys.of(o.value1());
                })
                .select(g -> new BlogEntityProxy().adapter(r -> {
                    r.selectExpression();
                    r.id().set(g.key1());
                    r.star().set(g.groupTable().value2().intCount());
                }))
                .orderBy(o -> o.star().asc()).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`id` AS `id`,t2.`star` AS `star` FROM (SELECT t1.`value1` AS `id`,COUNT(t1.`value2`) AS `star` FROM (SELECT t.`title` AS `value1`,SUBSTR(t.`title`,2,2) AS `value2` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?) t1 GROUP BY t1.`value1`) t2 ORDER BY t2.`star` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test

    public void testGroup15() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                    o.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .groupBy(o -> GroupKeys.of(o.title().subString(1, 2)))
                .select(g -> new BlogEntityProxy().adapter(r -> {

                    r.id().set(g.key1());
                    r.title().set(
                            g.groupTable().title().max()
                    );
                    r.score().set(
                            g.groupTable().stars().toNumber(BigDecimal.class).min()
                    );
                    r.content().set(
                            g.groupTable().id().join(",")
                    );
                })).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.`title`,2,2) AS `id`,MAX(t.`title`) AS `title`,MIN(CAST(t.`stars` AS DECIMAL(36,18))) AS `score`,GROUP_CONCAT(t.`id` SEPARATOR ?) AS `content` FROM `t_topic` t WHERE t.`title` IS NOT NULL AND t.`create_time` <= ? GROUP BY SUBSTR(t.`title`,2,2)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test

    public void testGroup16() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Grouping1<String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                })
                .groupBy(o -> GroupKeys.of(o.title().subString(1, 2))).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(`title`,2,2) FROM `t_topic` WHERE `title` IS NOT NULL GROUP BY SUBSTR(`title`,2,2)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("2023-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test

    public void testGroup17() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Grouping1<String>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                })
                .groupBy(o -> GroupKeys.of(o.title().subString(1, 2)))
                .having(o -> {
                    o.count().ge(1L);
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(`title`,2,2) FROM `t_topic` WHERE `title` IS NOT NULL GROUP BY SUBSTR(`title`,2,2) HAVING COUNT(*) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test

    public void testGroup18() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, Long>> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.title().isNotNull();
                })
                .groupBy(o -> GroupKeys.of(o.title().subString(1, 2)))
                .having(o -> {
                    o.count().ge(1L);
                }).select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.count()
                )).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.`title`,2,2) AS `value1`,COUNT(*) AS `value2` FROM `t_topic` t WHERE t.`title` IS NOT NULL GROUP BY SUBSTR(t.`title`,2,2) HAVING COUNT(*) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        for (Draft2<String, Long> stringLongDraft2 : list) {

            if ("12".equals(stringLongDraft2.getValue1())) {
                Assert.assertEquals(97L, (long) stringLongDraft2.getValue2());
            }
        }
    }

    @Test

    public void testGroup19() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<String, BigDecimal, Long>> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> {
                    t.id().isNotNull();
                    t1.score().isNotNull();
                })
                .groupBy((t, t1) -> GroupKeys.of(
                        t.title().subString(1, 2),
                        t1.score().nullOrDefault(BigDecimal.ZERO)
                ))
                .having(t -> {
                    t.count().ge(1L);
                }).select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.key2(),
                        o.count()
                )).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.`title`,2,2) AS `value1`,IFNULL(t1.`score`,?) AS `value2`,COUNT(*) AS `value3` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IS NOT NULL AND t1.`score` IS NOT NULL GROUP BY SUBSTR(t.`title`,2,2),IFNULL(t1.`score`,?) HAVING COUNT(*) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("0(BigDecimal),false(Boolean),0(BigDecimal),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test

    public void testGroup20() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> {
                    t.id().isNotNull();
                    t1.score().isNotNull();
                })
                .groupBy((t, t1) -> GroupKeys.of(
                        t.expression().sqlType("SUBSTR({0},2,2)", c -> c.expression(t.title())),
                        t1.score().nullOrDefault(BigDecimal.ZERO)
                )).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.`title`,2,2),IFNULL(t1.`score`,?) FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IS NOT NULL AND t1.`score` IS NOT NULL GROUP BY SUBSTR(t.`title`,2,2),IFNULL(t1.`score`,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("0(BigDecimal),false(Boolean),0(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test

    public void testGroup21() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, Long>> list1 = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> {
                    t.id().isNotNull();
                    t1.score().isNotNull();
                })
                .groupBy((t, t1) -> GroupKeys.of(
                        t.expression().sqlType("SUBSTR({0},2,2)", c -> c.expression(t.title())).asAnyType(String.class),
                        t1.score().nullOrDefault(BigDecimal.ZERO)
                ))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.groupTable().t1.title().count()
                )).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.`title`,2,2) AS `value1`,COUNT(t.`title`) AS `value2` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IS NOT NULL AND t1.`score` IS NOT NULL GROUP BY SUBSTR(t.`title`,2,2),IFNULL(t1.`score`,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),0(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<String, Long> stringLongDraft2 : list1) {

            String value1 = stringLongDraft2.getValue1();
            Long value2 = stringLongDraft2.getValue2();
        }

    }

    @Test

    public void testGroup22() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<LocalDateTime, Long>> list1 = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> {
                    t.id().isNotNull();
                    t1.score().isNotNull();
                })
                .groupBy((t, t1) -> GroupKeys.of(
                        t.expression().sqlType("IFNULL({0},{1})", c -> c.expression(t.createTime()).value(LocalDateTime.of(2022, 1, 1, 1, 2))).asAnyType(LocalDateTime.class),
                        t1.score().nullOrDefault(BigDecimal.ZERO)
                ))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.groupTable().t1.title().count()
                )).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL(t.`create_time`,?) AS `value1`,COUNT(t.`title`) AS `value2` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IS NOT NULL AND t1.`score` IS NOT NULL GROUP BY IFNULL(t.`create_time`,?),IFNULL(t1.`score`,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2022-01-01T01:02(LocalDateTime),false(Boolean),2022-01-01T01:02(LocalDateTime),0(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<LocalDateTime, Long> stringLongDraft2 : list1) {

            LocalDateTime value1 = stringLongDraft2.getValue1();
            Long value2 = stringLongDraft2.getValue2();
        }

    }


    @Test
    public void testDraft11() {
        String id = "123456zz9xx1";
        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z");
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setUpdateBy("z");
        blog.setUpdateTime(LocalDateTime.of(2022, 2, 3, 4, 5, 6));
        blog.setTitle("titlez");
        blog.setContent("contentz");
        blog.setUrl("http://blog.easy-query.com/z");
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2"));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        easyEntityQuery.insertable(blog)
                .executeRows();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft7<Long, Long, Long, Long, Long, Long, Long> draft3 = easyEntityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().duration(o.updateTime()).toDays(),
                        o.createTime().duration(o.updateTime()).toHours(),
                        o.createTime().duration(o.updateTime()).toMinutes(),
                        o.createTime().duration(o.updateTime()).toSeconds(),
                        o.createTime().duration(o.createTime().plus(1, TimeUnit.DAYS)).toDays(),
                        o.createTime().duration(o.createTime().plus(2, TimeUnit.SECONDS)).toSeconds(),
                        o.createTime().duration(o.createTime().plus(3, TimeUnit.MINUTES)).toMinutes()
                )).firstOrNull();

        Assert.assertNotNull(draft3);
        Long value1 = draft3.getValue1();
        Assert.assertEquals(32, (long) value1);
        Long value2 = draft3.getValue2();
        Assert.assertEquals(769, (long) value2);
        Long value3 = draft3.getValue3();
        Assert.assertEquals(46141, (long) value3);
        Long value4 = draft3.getValue4();
        Assert.assertEquals(2768461, (long) value4);
        Long value5 = draft3.getValue5();
        Assert.assertEquals(1, (long) value5);
        Long value6 = draft3.getValue6();
        Assert.assertEquals(2, (long) value6);
        Long value7 = draft3.getValue7();
        Assert.assertEquals(3, (long) value7);

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT timestampdiff(DAY, t.`create_time`, t.`update_time`) AS `value1`,timestampdiff(HOUR, t.`create_time`, t.`update_time`) AS `value2`,timestampdiff(MINUTE, t.`create_time`, t.`update_time`) AS `value3`,timestampdiff(SECOND, t.`create_time`, t.`update_time`) AS `value4`,timestampdiff(DAY, t.`create_time`, date_add(t.`create_time`, interval (?) microsecond)) AS `value5`,timestampdiff(SECOND, t.`create_time`, date_add(t.`create_time`, interval (?) microsecond)) AS `value6`,timestampdiff(MINUTE, t.`create_time`, date_add(t.`create_time`, interval (?) microsecond)) AS `value7` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("86400000000(Long),2000000(Long),180000000(Long),false(Boolean),123456zz9xx1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        easyEntityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }


}
