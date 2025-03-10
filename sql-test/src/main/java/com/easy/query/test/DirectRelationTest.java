package com.easy.query.test;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.direct.Direct1;
import com.easy.query.test.entity.direct.Direct2;
import com.easy.query.test.entity.direct.Direct3;
import com.easy.query.test.entity.direct.Direct4;
import com.easy.query.test.entity.direct.Direct5;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/3/1 22:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class DirectRelationTest extends BaseTest {

    @Before
    public void before() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        {

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(Direct1.class, Direct2.class, Direct3.class, Direct4.class, Direct5.class));
            codeFirstCommand.executeWithTransaction(s -> s.commit());
        }
        easyEntityQuery.deletable(Direct1.class).allowDeleteStatement(true).where(d -> d.isNotNull()).executeRows();
        easyEntityQuery.deletable(Direct2.class).allowDeleteStatement(true).where(d -> d.isNotNull()).executeRows();
        easyEntityQuery.deletable(Direct3.class).allowDeleteStatement(true).where(d -> d.isNotNull()).executeRows();
        easyEntityQuery.deletable(Direct4.class).allowDeleteStatement(true).where(d -> d.isNotNull()).executeRows();
        easyEntityQuery.deletable(Direct5.class).allowDeleteStatement(true).where(d -> d.isNotNull()).executeRows();
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {

            {
                Direct1 direct1 = new Direct1();
                direct1.setC1("1");
                direct1.setC2("2");
                direct1.setC3("3");
                direct1.setC4("4");
                direct1.setC5("5");
                easyEntityQuery.insertable(direct1).executeRows();
            }
            {
                Direct1 direct1 = new Direct1();
                direct1.setC1("2");
                direct1.setC2("3");
                direct1.setC3("4");
                direct1.setC4("5");
                direct1.setC5("6");
                easyEntityQuery.insertable(direct1).executeRows();
            }


            {
                Direct2 direct2 = new Direct2();
                direct2.setC6("6");
                direct2.setC7("1");
                direct2.setC8("8");
                direct2.setC9("9");
                direct2.setC10("10");
                easyEntityQuery.insertable(direct2).executeRows();
            }

            {
                Direct2 direct2 = new Direct2();
                direct2.setC6("7");
                direct2.setC7("8");
                direct2.setC8("9");
                direct2.setC9("10");
                direct2.setC10("11");
                easyEntityQuery.insertable(direct2).executeRows();
            }

            {
                Direct3 direct3 = new Direct3();
                direct3.setC11("11");
                direct3.setC12("12");
                direct3.setC13("8");
                direct3.setC14("9");
                direct3.setC15("15");
                easyEntityQuery.insertable(direct3).executeRows();
            }
            {
                Direct3 direct3 = new Direct3();
                direct3.setC11("13");
                direct3.setC12("14");
                direct3.setC13("15");
                direct3.setC14("16");
                direct3.setC15("17");
                easyEntityQuery.insertable(direct3).executeRows();
            }
            {
                Direct4 direct4 = new Direct4();
                direct4.setC16("20");
                direct4.setC17("21");
                direct4.setC18("22");
                direct4.setC19("23");
                direct4.setC20("15");
                easyEntityQuery.insertable(direct4).executeRows();
            }
            {
                Direct4 direct4 = new Direct4();
                direct4.setC16("29");
                direct4.setC17("30");
                direct4.setC18("32");
                direct4.setC19("33");
                direct4.setC20("35");
                easyEntityQuery.insertable(direct4).executeRows();
            }
            {
                Direct5 direct5 = new Direct5();
                direct5.setC21("22");
                direct5.setC22("20");
                direct5.setC23("44");
                direct5.setC24("55");
                direct5.setC25("66");
                easyEntityQuery.insertable(direct5).executeRows();
            }
            {
                Direct5 direct5 = new Direct5();
                direct5.setC21("77");
                direct5.setC22("88");
                direct5.setC23("99");
                direct5.setC24("00");
                direct5.setC25("11");
                easyEntityQuery.insertable(direct5).executeRows();
            }
            transaction.commit();
        }
    }

    @Test
    public void test() {


        {
            System.out.println("44x");
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);


            List<Direct1> list = easyEntityQuery.queryable(Direct1.class)
                    .include(d -> d.direct4())
                    .toList();

            for (Direct1 direct1 : list) {
                if ("2".equals(direct1.getC1())) {
                    Assert.assertNull(direct1.getDirect4());
                } else if ("1".equals(direct1.getC1())) {
                    Assert.assertNotNull(direct1.getDirect4());
                }
            }
            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `c1`,`c2`,`c3`,`c4`,`c5` FROM `direct1`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `c7`,`c8`,`c9` FROM `direct2` WHERE `c7` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT `c13`,`c14`,`c15` FROM `direct3` WHERE ((`c13` =? AND `c14` =?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("8(String),9(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("SELECT t.`c16`,t.`c17`,t.`c18`,t.`c19`,t.`c20` FROM `direct4` t WHERE t.`c20` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("15(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }

            System.out.println("33");
        }
    }

    @Test
    public void test2() {


        {
            System.out.println("44x");
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);


            List<Direct1> list = easyEntityQuery.queryable(Direct1.class)
                    .include(d -> d.direct5())
                    .toList();

            for (Direct1 direct1 : list) {
                if ("2".equals(direct1.getC1())) {
                    Assert.assertNull(direct1.getDirect4());
                } else if ("1".equals(direct1.getC1())) {
                    Assert.assertNotNull(direct1.getDirect5());
                }
            }
            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `c1`,`c2`,`c3`,`c4`,`c5` FROM `direct1`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `c7`,`c8`,`c9` FROM `direct2` WHERE `c7` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT `c13`,`c14`,`c15` FROM `direct3` WHERE ((`c13` =? AND `c14` =?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("8(String),9(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("SELECT `c20`,`c16`,`c18` FROM `direct4` WHERE `c20` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("15(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("SELECT t.`c21`,t.`c22`,t.`c23`,t.`c24`,t.`c25` FROM `direct5` t WHERE ((t.`c22` =? AND t.`c21` =?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("20(String),22(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }

            System.out.println("33");
        }
    }

    @Test
    public void test3() {

//        easyEntityQuery.queryable(Direct5.class)
//                .where(d -> {
//                    d.depIds().asAny().like("123");
//                    d.depIds().asAnyType(String.class).like("123");
//                    d.depIds().asAnyType(String.class).like(123);
//                    d.depIds().likeRaw("123");
//                })

        {
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);


            List<Direct1> list = easyEntityQuery.queryable(Direct1.class)
                    .where(d -> {
                        d.direct4().c16().eq("123");
                        d.direct5().c21().eq("1234");
                    })
                    .toList();

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT t.`c1`,t.`c2`,t.`c3`,t.`c4`,t.`c5` FROM `direct1` t LEFT JOIN `direct2` t1 ON t1.`c7` = t.`c1` LEFT JOIN `direct3` t2 ON (t2.`c13` = t1.`c8` AND t2.`c14` = t1.`c9`) LEFT JOIN `direct4` t3 ON t3.`c20` = t2.`c15` LEFT JOIN `direct5` t4 ON (t4.`c22` = t3.`c16` AND t4.`c21` = t3.`c18`) WHERE t3.`c16` = ? AND t4.`c21` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("123(String),1234(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }

        }
    }


//    @Test
//    public void test4() {
//
//        easyEntityQuery.queryable(SchoolStudent.class)
//                .includeMany(d -> {
//                    d.schoolClass().include();
//                    d.schoolClass().schoolStudents().include();
//                    d.schoolClass().schoolStudents().includeFilter(s->s.);
//                })
//        easyEntityQuery.queryable(Direct1.class)
//                .includeMany(d -> IncludeMany.of(
//                        d.direct2(),
//                        d.direct5(),
//                        d.direct4().direct5()
//                ))
//                .where(d -> d.direct5().c21().eq("1234"))
//    }
}
