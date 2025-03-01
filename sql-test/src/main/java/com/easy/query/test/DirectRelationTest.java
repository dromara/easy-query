package com.easy.query.test;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.MySignUp;
import com.easy.query.test.doc.dto.MySignUpDTO3;
import com.easy.query.test.entity.direct.Direct1;
import com.easy.query.test.entity.direct.Direct2;
import com.easy.query.test.entity.direct.Direct3;
import com.easy.query.test.entity.direct.Direct4;
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
public class DirectRelationTest extends BaseTest{

    @Before
    public void before(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        {

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(Direct1.class, Direct2.class, Direct3.class, Direct4.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
        }
        easyEntityQuery.deletable(Direct1.class).allowDeleteStatement(true).where(d -> d.isNotNull()).executeRows();
        easyEntityQuery.deletable(Direct2.class).allowDeleteStatement(true).where(d -> d.isNotNull()).executeRows();
        easyEntityQuery.deletable(Direct3.class).allowDeleteStatement(true).where(d -> d.isNotNull()).executeRows();
        easyEntityQuery.deletable(Direct4.class).allowDeleteStatement(true).where(d -> d.isNotNull()).executeRows();
        try(Transaction transaction = easyEntityQuery.beginTransaction()){

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
            transaction.commit();
        }
    }

    @Test
    public void test(){


        {
            System.out.println("44x");
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);


            List<Direct1> list = easyEntityQuery.queryable(Direct1.class)
                    .include(d -> d.direct4())
                    .toList();

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE t.`id` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `com_id` AS `com_id`,`user_id` AS `user_id` FROM `my_com_user` WHERE ((`com_id` =? AND `user_id` =?) OR (`com_id` =? AND `user_id` =?) OR (`com_id` =? AND `user_id` =?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("c1(String),u1(String),c1(String),u3(String),c2(String),u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `my_company_info` t WHERE t.`id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("c1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }

            System.out.println("33");
        }
    }
}
