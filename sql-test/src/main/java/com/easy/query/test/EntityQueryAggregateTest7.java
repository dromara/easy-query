package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/10/31 08:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityQueryAggregateTest7 extends BaseEntityQueryAggregateTest1 {
    @Test
    public void test1() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join1().select((t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> Select.DRAFT.of(
                    t1.id(),
                    t1.stars(),
                    t1.createTime()
            )).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

    }
    @Test
    public void test2() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join1().selectMerge(o -> Select.DRAFT.of(
                    o.t1.id(),
                    o.t1.stars(),
                    o.t1.createTime()
            )).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

    }
    @Test
    public void test3() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join1()
                    .whereById("123")
                    .whereById(false,"123")
                    .whereByIds(Arrays.asList("123"))
                    .whereByIds(false,Arrays.asList("123"))
                    .where(t -> t.stars().eq(1))
                    .where(false,t -> t.stars().eq(1))
                    .orderBy(false,t1 -> t1.id().asc())
                    .orderBy(t1 -> t1.id().asc())
                    .limit(2,3)
                    .limit(false,2,3)
                    .select((t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> Select.DRAFT.of(
                    t1.id(),
                    t1.stars(),
                    t1.createTime()
            )).distinct().distinct(false).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT DISTINCT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` IN (?) AND t.`stars` = ? ORDER BY t.`id` ASC LIMIT 3 OFFSET 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test4() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join2()
                    .whereById("123")
                    .whereById(false,"123")
                    .whereByIds(Arrays.asList("123"))
                    .whereByIds(false,Arrays.asList("123"))
                    .where(t -> t.stars().eq(1))
                    .where(false,t -> t.stars().eq(1))
                    .orderBy(false,t1 -> t1.id().asc())
                    .orderBy(t1 -> t1.id().asc())
                    .limit(2,3)
                    .limit(false,2,3)
                    .select((t1, t2, t3, t4, t5, t6, t7, t8, t9) -> Select.DRAFT.of(
                    t1.id(),
                    t1.stars(),
                    t1.createTime()
            )).distinct().distinct(false).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT DISTINCT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` WHERE t.`id` = ? AND t.`id` IN (?) AND t.`stars` = ? ORDER BY t.`id` ASC LIMIT 3 OFFSET 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test5() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join3()
                    .whereById("123")
                    .whereById(false,"123")
                    .whereByIds(Arrays.asList("123"))
                    .whereByIds(false,Arrays.asList("123"))
                    .where(t -> t.stars().eq(1))
                    .where(false,t -> t.stars().eq(1))
                    .orderBy(false,t1 -> t1.id().asc())
                    .orderBy(t1 -> t1.id().asc())
                    .limit(2,3)
                    .limit(false,2,3)
                    .select((t1, t2, t3, t4, t5, t6, t7, t8) -> Select.DRAFT.of(
                    t1.id(),
                    t1.stars(),
                    t1.createTime()
            )).distinct().distinct(false).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT DISTINCT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` WHERE t.`id` = ? AND t.`id` IN (?) AND t.`stars` = ? ORDER BY t.`id` ASC LIMIT 3 OFFSET 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test6() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join4()
                    .whereById("123")
                    .whereById(false,"123")
                    .whereByIds(Arrays.asList("123"))
                    .whereByIds(false,Arrays.asList("123"))
                    .where(t -> t.stars().eq(1))
                    .where(false,t -> t.stars().eq(1))
                    .orderBy(false,t1 -> t1.id().asc())
                    .orderBy(t1 -> t1.id().asc())
                    .limit(2,3)
                    .limit(false,2,3)
                    .select((t1, t2, t3, t4, t5, t6, t7) -> Select.DRAFT.of(
                    t1.id(),
                    t1.stars(),
                    t1.createTime()
            )).distinct().distinct(false).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT DISTINCT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` WHERE t.`id` = ? AND t.`id` IN (?) AND t.`stars` = ? ORDER BY t.`id` ASC LIMIT 3 OFFSET 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test7() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join5()
                    .whereById("123")
                    .whereById(false,"123")
                    .whereByIds(Arrays.asList("123"))
                    .whereByIds(false,Arrays.asList("123"))
                    .where(t -> t.stars().eq(1))
                    .where(false,t -> t.stars().eq(1))
                    .orderBy(false,t1 -> t1.id().asc())
                    .orderBy(t1 -> t1.id().asc())
                    .limit(2,3)
                    .limit(false,2,3)
                    .select((t1, t2, t3, t4, t5, t6) -> Select.DRAFT.of(
                    t1.id(),
                    t1.stars(),
                    t1.createTime()
            )).distinct().distinct(false).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT DISTINCT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` WHERE t.`id` = ? AND t.`id` IN (?) AND t.`stars` = ? ORDER BY t.`id` ASC LIMIT 3 OFFSET 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test8() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join6()
                    .whereById("123")
                    .whereById(false,"123")
                    .whereByIds(Arrays.asList("123"))
                    .whereByIds(false,Arrays.asList("123"))
                    .where(t -> t.stars().eq(1))
                    .where(false,t -> t.stars().eq(1))
                    .orderBy(false,t1 -> t1.id().asc())
                    .orderBy(t1 -> t1.id().asc())
                    .limit(2,3)
                    .limit(false,2,3)
                    .select((t1, t2, t3, t4, t5) -> Select.DRAFT.of(
                    t1.id(),
                    t1.stars(),
                    t1.createTime()
            )).distinct().distinct(false).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT DISTINCT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` WHERE t.`id` = ? AND t.`id` IN (?) AND t.`stars` = ? ORDER BY t.`id` ASC LIMIT 3 OFFSET 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test9() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join7()
                    .whereById("123")
                    .whereById(false,"123")
                    .whereByIds(Arrays.asList("123"))
                    .whereByIds(false,Arrays.asList("123"))
                    .where(t -> t.stars().eq(1))
                    .where(false,t -> t.stars().eq(1))
                    .orderBy(false,t1 -> t1.id().asc())
                    .orderBy(t1 -> t1.id().asc())
                    .limit(2,3)
                    .limit(false,2,3)
                    .select((t1, t2, t3, t4) -> Select.DRAFT.of(
                    t1.id(),
                    t1.stars(),
                    t1.createTime()
            )).distinct().distinct(false).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT DISTINCT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` WHERE t.`id` = ? AND t.`id` IN (?) AND t.`stars` = ? ORDER BY t.`id` ASC LIMIT 3 OFFSET 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test10() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Draft3<String, Integer, LocalDateTime>> list = join8()
                    .whereById("123")
                    .whereById(false,"123")
                    .whereByIds(Arrays.asList("123"))
                    .whereByIds(false,Arrays.asList("123"))
                    .where(t -> t.stars().eq(1))
                    .where(false,t -> t.stars().eq(1))
                    .orderBy(false,t1 -> t1.id().asc())
                    .orderBy(t1 -> t1.id().asc())
                    .limit(2,3)
                    .limit(false,2,3)
                    .select((t1, t2, t3) -> Select.DRAFT.of(
                    t1.id(),
                    t1.stars(),
                    t1.createTime()
            )).distinct().distinct(false).toList();
            for (Draft3<String, Integer, LocalDateTime> item : list) {

                String value1 = item.getValue1();
                Integer value2 = item.getValue2();
                LocalDateTime value3 = item.getValue3();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT DISTINCT t.`id` AS `value1`,t.`stars` AS `value2`,t.`create_time` AS `value3` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` WHERE t.`id` = ? AND t.`id` IN (?) AND t.`stars` = ? ORDER BY t.`id` ASC LIMIT 3 OFFSET 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
}
