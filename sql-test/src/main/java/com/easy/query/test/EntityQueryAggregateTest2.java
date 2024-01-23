package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2024/1/20 23:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityQueryAggregateTest2 extends BaseEntityQueryAggregateTest1{

    @Test
    public void test1() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = join1().whereById("1")
                    .asSchema(o->o)
                    .asTable(o->o)
                    .asTableLink(x->x)
                    .whereMerge(o -> {
                        o.t1.id().eq("1");
                        o.t2.id().eq(false, "1");
                    })
                    .where((t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
                        t2.title().like("xx");
                    })
                    .where(false, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
                        t2.title().like("xx");
                    })
                    .where(o->Assert.assertTrue(true))
                    .where(false,o->o.id().eq("xx"))
                    .whereMerge(false, o -> {
                        o.t1.id().eq("xx");
                    })
                    .orderBy((t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(false, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(o->Assert.assertTrue(true))
                    .orderBy(false,o->o.id().asc())
                    .orderByMerge(o -> {
                        o.t4.createTime().desc();
                    })
                    .orderByMerge(false, o -> {
                        o.t4.createTime().asc();
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? ORDER BY t.`create_time` ASC,t3.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(String),%xx%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test2() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = join2().whereById("1")
                    .asSchema(o->o)
                    .asTable(o->o)
                    .asTableLink(x->x)
                    .whereMerge(o -> {
                        o.t1.id().eq("1");
                        o.t2.id().eq(false, "1");
                    })
                    .where((t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                        t2.title().like("xx");
                    })
                    .where(false, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                        t2.title().like("xx");
                    })
                    .where(o->Assert.assertTrue(true))
                    .where(false,o->o.id().eq("xx"))
                    .whereMerge(false, o -> {
                        o.t1.id().eq("xx");
                    })
                    .orderBy((t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(false, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(o->Assert.assertTrue(true))
                    .orderBy(false,o->o.id().asc())
                    .orderByMerge(o -> {
                        o.t4.createTime().desc();
                    })
                    .orderByMerge(false, o -> {
                        o.t4.createTime().asc();
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? ORDER BY t.`create_time` ASC,t3.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(String),%xx%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test3() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = join3().whereById("1")
                    .asSchema(o->o)
                    .asTable(o->o)
                    .asTableLink(x->x)
                    .whereMerge(o -> {
                        o.t1.id().eq("1");
                        o.t2.id().eq(false, "1");
                    })
                    .where((t1, t2, t3, t4, t5, t6, t7, t8) -> {
                        t2.title().like("xx");
                    })
                    .where(false, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
                        t2.title().like("xx");
                    })
                    .where(o->Assert.assertTrue(true))
                    .where(false,o->o.id().eq("xx"))
                    .whereMerge(false, o -> {
                        o.t1.id().eq("xx");
                    })
                    .orderBy((t1, t2, t3, t4, t5, t6, t7, t8) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(false, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(o->Assert.assertTrue(true))
                    .orderBy(false,o->o.id().asc())
                    .orderByMerge(o -> {
                        o.t4.createTime().desc();
                    })
                    .orderByMerge(false, o -> {
                        o.t4.createTime().asc();
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? ORDER BY t.`create_time` ASC,t3.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(String),%xx%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test4() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = join4().whereById("1")
                    .asSchema(o->o)
                    .asTable(o->o)
                    .asTableLink(x->x)
                    .whereMerge(o -> {
                        o.t1.id().eq("1");
                        o.t2.id().eq(false, "1");
                    })
                    .where((t1, t2, t3, t4, t5, t6, t7) -> {
                        t2.title().like("xx");
                    })
                    .where(false, (t1, t2, t3, t4, t5, t6, t7) -> {
                        t2.title().like("xx");
                    })
                    .where(o->Assert.assertTrue(true))
                    .where(false,o->o.id().eq("xx"))
                    .whereMerge(false, o -> {
                        o.t1.id().eq("xx");
                    })
                    .orderBy((t1, t2, t3, t4, t5, t6, t7) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(false, (t1, t2, t3, t4, t5, t6, t7) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(o->Assert.assertTrue(true))
                    .orderBy(false,o->o.id().asc())
                    .orderByMerge(o -> {
                        o.t4.createTime().desc();
                    })
                    .orderByMerge(false, o -> {
                        o.t4.createTime().asc();
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? ORDER BY t.`create_time` ASC,t3.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(String),%xx%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test5() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = join5().whereById("1")
                    .asSchema(o->o)
                    .asTable(o->o)
                    .asTableLink(x->x)
                    .whereMerge(o -> {
                        o.t1.id().eq("1");
                        o.t2.id().eq(false, "1");
                    })
                    .where((t1, t2, t3, t4, t5, t6) -> {
                        t2.title().like("xx");
                    })
                    .where(false, (t1, t2, t3, t4, t5, t6) -> {
                        t2.title().like("xx");
                    })
                    .where(o->Assert.assertTrue(true))
                    .where(false,o->o.id().eq("xx"))
                    .whereMerge(false, o -> {
                        o.t1.id().eq("xx");
                    })
                    .orderBy((t1, t2, t3, t4, t5, t6) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(false, (t1, t2, t3, t4, t5, t6) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(o->Assert.assertTrue(true))
                    .orderBy(false,o->o.id().asc())
                    .orderByMerge(o -> {
                        o.t4.createTime().desc();
                    })
                    .orderByMerge(false, o -> {
                        o.t4.createTime().asc();
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? ORDER BY t.`create_time` ASC,t3.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(String),%xx%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test6() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = join6().whereById("1")
                    .asSchema(o->o)
                    .asTable(o->o)
                    .asTableLink(x->x)
                    .whereMerge(o -> {
                        o.t1.id().eq("1");
                        o.t2.id().eq(false, "1");
                    })
                    .where((t1, t2, t3, t4, t5) -> {
                        t2.title().like("xx");
                    })
                    .where(false, (t1, t2, t3, t4, t5) -> {
                        t2.title().like("xx");
                    })
                    .where(o->Assert.assertTrue(true))
                    .where(false,o->o.id().eq("xx"))
                    .whereMerge(false, o -> {
                        o.t1.id().eq("xx");
                    })
                    .orderBy((t1, t2, t3, t4, t5) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(false, (t1, t2, t3, t4, t5) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(o->Assert.assertTrue(true))
                    .orderBy(false,o->o.id().asc())
                    .orderByMerge(o -> {
                        o.t4.createTime().desc();
                    })
                    .orderByMerge(false, o -> {
                        o.t4.createTime().asc();
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? ORDER BY t.`create_time` ASC,t3.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(String),%xx%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test7() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = join7().whereById("1")
                    .asSchema(o->o)
                    .asTable(o->o)
                    .asTableLink(x->x)
                    .whereMerge(o -> {
                        o.t1.id().eq("1");
                        o.t2.id().eq(false, "1");
                    })
                    .where((t1, t2, t3, t4) -> {
                        t2.title().like("xx");
                    })
                    .where(false, (t1, t2, t3, t4) -> {
                        t2.title().like("xx");
                    })
                    .where(o->Assert.assertTrue(true))
                    .where(false,o->o.id().eq("xx"))
                    .whereMerge(false, o -> {
                        o.t1.id().eq("xx");
                    })
                    .orderBy((t1, t2, t3, t4) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(false, (t1, t2, t3, t4) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(o->Assert.assertTrue(true))
                    .orderBy(false,o->o.id().asc())
                    .orderByMerge(o -> {
                        o.t4.createTime().desc();
                    })
                    .orderByMerge(false, o -> {
                        o.t4.createTime().asc();
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? ORDER BY t.`create_time` ASC,t3.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(String),%xx%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test8() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = join8().whereById("1")
                    .asSchema(o->o)
                    .asTable(o->o)
                    .asTableLink(x->x)
                    .whereMerge(o -> {
                        o.t1.id().eq("1");
                        o.t2.id().eq(false, "1");
                    })
                    .where((t1, t2, t3) -> {
                        t2.title().like("xx");
                    })
                    .where(false, (t1, t2, t3) -> {
                        t2.title().like("xx");
                    })
                    .where(o->Assert.assertTrue(true))
                    .where(false,o->o.id().eq("xx"))
                    .whereMerge(false, o -> {
                        o.t1.id().eq("xx");
                    })
                    .orderBy((t1, t2, t3) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(false, (t1, t2, t3) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(o->Assert.assertTrue(true))
                    .orderBy(false,o->o.id().asc())
                    .orderByMerge(o -> {
                        o.t3.createTime().desc();
                    })
                    .orderByMerge(false, o -> {
                        o.t3.createTime().asc();
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? ORDER BY t.`create_time` ASC,t2.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(String),%xx%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
    @Test
    public void test9() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list = join9().whereById("1")
                    .asSchema(o->o)
                    .asTable(o->o)
                    .asTableLink(x->x)
                    .whereMerge(o -> {
                        o.t1.id().eq("1");
                        o.t2.id().eq(false, "1");
                    })
                    .where((t1, t2) -> {
                        t2.title().like("xx");
                    })
                    .where(false, (t1, t2) -> {
                        t2.title().like("xx");
                    })
                    .where(o->Assert.assertTrue(true))
                    .where(false,o->o.id().eq("xx"))
                    .whereMerge(false, o -> {
                        o.t1.id().eq("xx");
                    })
                    .orderBy((t1, t2) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(false, (t1, t2) -> {
                        t1.createTime().asc();
                        t2.createTime().desc(false);
                    })
                    .orderBy(o->Assert.assertTrue(true))
                    .orderBy(false,o->o.id().asc())
                    .orderByMerge(o -> {
                        o.t2.createTime().desc();
                    })
                    .orderByMerge(false, o -> {
                        o.t2.createTime().asc();
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`id` = ? AND t.`id` = ? AND t1.`title` LIKE ? ORDER BY t.`create_time` ASC,t1.`create_time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(String),%xx%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
}
