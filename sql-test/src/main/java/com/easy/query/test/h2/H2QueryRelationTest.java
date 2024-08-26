package com.easy.query.test.h2;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.h2.domain.TbAccount;
import com.easy.query.test.h2.domain.TbOrder;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * create time 2024/8/26 15:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2QueryRelationTest extends H2BaseTest{
    @Test
    public void tbTest1(){

        easyEntityQuery.deletable(TbOrder.class).where(t -> {t.id().isNotNull();}).allowDeleteStatement(true).executeRows();
        easyEntityQuery.deletable(TbAccount.class).where(t -> {t.id().isNotNull();}).allowDeleteStatement(true).executeRows();
        ArrayList<TbOrder> a=new ArrayList<TbOrder>();
        {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setId("tbOrder1");
            tbOrder.setUid("小明");
            tbOrder.setUname("小明");
            tbOrder.setPrice(BigDecimal.valueOf(1));
            a.add(tbOrder);
        }
        {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setId("tbOrder2");
            tbOrder.setUid("小明");
            tbOrder.setUname("小明");
            tbOrder.setPrice(BigDecimal.valueOf(2));
            a.add(tbOrder);
        }
        {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setId("tbOrder3");
            tbOrder.setUid("小明");
            tbOrder.setUname("小明");
            tbOrder.setPrice(BigDecimal.valueOf(3));
            a.add(tbOrder);
        }
        {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setId("tbOrder4");
            tbOrder.setUid("小红");
            tbOrder.setUname("小红");
            tbOrder.setPrice(BigDecimal.valueOf(4));
            a.add(tbOrder);
        }
        {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setId("tbOrder5");
            tbOrder.setUid("小明");
            tbOrder.setUname("小明");
            tbOrder.setPrice(BigDecimal.valueOf(5));
            a.add(tbOrder);
        }
        {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setId("tbOrder6");
            tbOrder.setUid("小红");
            tbOrder.setUname("小红");
            tbOrder.setPrice(BigDecimal.valueOf(6));
            a.add(tbOrder);
        }
        {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setId("tbOrder7");
            tbOrder.setUid("小蓝");
            tbOrder.setUname("小蓝");
            tbOrder.setPrice(BigDecimal.valueOf(6));
            a.add(tbOrder);
        }
        {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setId("tbOrder8");
            tbOrder.setUid("小青");
            tbOrder.setUname("小青");
            tbOrder.setPrice(BigDecimal.valueOf(8));
            a.add(tbOrder);
        }
        ArrayList<TbAccount> tbAccounts = new ArrayList<>();
        {
            TbAccount tbAccount = new TbAccount();
            tbAccount.setId("tbAccount1");
            tbAccount.setUid("小明");
            tbAccount.setUname("小明");
            tbAccount.setAccount("tbAccount1");
            tbAccounts.add(tbAccount);
        }
        {
            TbAccount tbAccount = new TbAccount();
            tbAccount.setId("tbAccount2");
            tbAccount.setUid("小蓝");
            tbAccount.setUname("小蓝");
            tbAccount.setAccount("tbAccount2");
            tbAccounts.add(tbAccount);
        }
        {
            TbAccount tbAccount = new TbAccount();
            tbAccount.setId("tbAccount3");
            tbAccount.setUid("小红");
            tbAccount.setUname("小红");
            tbAccount.setAccount("tbAccount3");
            tbAccounts.add(tbAccount);
        }
        {
            TbAccount tbAccount = new TbAccount();
            tbAccount.setId("tbAccount4");
            tbAccount.setUid("小紫");
            tbAccount.setUname("小紫");
            tbAccount.setAccount("tbAccount4");
            tbAccounts.add(tbAccount);
        }
        {
            TbAccount tbAccount = new TbAccount();
            tbAccount.setId("tbAccount5");
            tbAccount.setUid("小明");
            tbAccount.setUname("小明");
            tbAccount.setAccount("tbAccount5");
            tbAccounts.add(tbAccount);
        }
        {
            TbAccount tbAccount = new TbAccount();
            tbAccount.setId("tbAccount6");
            tbAccount.setUid("小明");
            tbAccount.setUname("小明");
            tbAccount.setAccount("tbAccount6");
            tbAccounts.add(tbAccount);
        }
easyEntityQuery.insertable(a).executeRows();
easyEntityQuery.insertable(tbAccounts).executeRows();

        {
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);
            List<TbOrder> list = easyEntityQuery.queryable(TbOrder.class)
                    .includes(o -> o.accounts())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT id,uid,uname,price FROM t_tb_order", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT t.id,t.uid,t.uname,t.account FROM t_tb_account t WHERE t.uid IN (?,?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("小明(String),小红(String),小蓝(String),小青(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<TbOrder> list1 = easyEntityQuery.queryable(TbOrder.class)
                    .where(t -> {
                        t.accounts().any(s -> s.uname().like("小明"));
                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.id,t.uid,t.uname,t.price FROM t_tb_order t WHERE EXISTS (SELECT 1 FROM t_tb_account t1 WHERE t1.uid = t.uid AND t1.uname LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        easyEntityQuery.deletable(TbOrder.class).where(t -> {t.id().isNotNull();}).allowDeleteStatement(true).executeRows();
        easyEntityQuery.deletable(TbAccount.class).where(t -> {t.id().isNotNull();}).allowDeleteStatement(true).executeRows();
    }
}
