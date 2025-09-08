package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.save.M8SaveRoot;
import com.easy.query.test.mysql8.entity.save.M8SaveRoot2Many;
import com.easy.query.test.mysql8.entity.save.M8SaveRootMany;
import com.easy.query.test.mysql8.entity.save.M8SaveRootMiddleMany;
import com.easy.query.test.mysql8.entity.save.M8SaveRootOne;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/9/8 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8SaveTest extends BaseTest {

    public String before() {

        easyEntityQuery.deletable(M8SaveRoot.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRoot2Many.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootMany.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootMiddleMany.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootOne.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();


        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();

            M8SaveRoot root = new M8SaveRoot();
            root.setName("rootname");
            root.setCode("rootcode");
            M8SaveRootOne rootOne = new M8SaveRootOne();
            root.setM8SaveRootOne(rootOne);
            List<M8SaveRootMany> many = new ArrayList<>();
            root.setM8SaveRootManyList(many);

            {

                M8SaveRootMany rootMany = new M8SaveRootMany();
                rootMany.setName("rootMany1");
                many.add(rootMany);
            }
            {

                M8SaveRootMany rootMany = new M8SaveRootMany();
                rootMany.setName("rootMany2");
                many.add(rootMany);
            }
            List<M8SaveRoot2Many> many2many = new ArrayList<>();
            root.setM8SaveRoot2ManyList(many2many);
            {

                M8SaveRoot2Many root2Many = new M8SaveRoot2Many();
                root2Many.setName("root2Many1");
                many2many.add(root2Many);
            }
            {

                M8SaveRoot2Many root2Many = new M8SaveRoot2Many();
                root2Many.setName("root2Many2");
                many2many.add(root2Many);
            }

            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);

                try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                    easyEntityQuery.savable(many2many).executeCommand();
                    easyEntityQuery.savable(root).executeCommand();
                    transaction.commit();
                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(8, listenerContext.getJdbcExecuteAfterArgs().size());
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("INSERT INTO `m8_save_root_2many` (`id`,`name`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals(many2many.get(0).getId() + "(String),root2Many1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("INSERT INTO `m8_save_root_2many` (`id`,`name`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals(many2many.get(1).getId() + "(String),root2Many2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("INSERT INTO `m8_save_root` (`id`,`name`,`code`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals(root.getId() + "(String),rootname(String),rootcode(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("INSERT INTO `m8_save_root_one` (`id`,`root_id`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals(rootOne.getId() + "(String)," + root.getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("INSERT INTO `m8_save_root_many` (`id`,`root_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals(many.get(0).getId() + "(String)," + root.getId() + "(String),rootMany1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                    Assert.assertEquals("INSERT INTO `m8_save_root_many` (`id`,`root_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals(many.get(1).getId() + "(String)," + root.getId() + "(String),rootMany2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(6);
                    Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    String s = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
                    Assert.assertTrue(s.endsWith("(String)," + root.getId() + "(String)," + many2many.get(0).getId() + "(String)"));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(7);
                    Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    String s = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
                    Assert.assertTrue(s.endsWith("(String)," + root.getId() + "(String)," + many2many.get(1).getId() + "(String)"));
                }
            }

            return root.getId();
        } finally {
            trackManager.release();
        }
    }

    public void invoke(Consumer<ListenerContext> action) {

        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();


            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            action.accept(listenerContext);



        } finally {
            trackManager.release();
        }
    }

    @Test
    public void testInsert() {
        String rootId = before();

    }
    @Test
    public void testOne2Many1() {
        String rootId = before();

        invoke(listenerContext->{
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRootManyList())
                    .singleNotNull();
            m8SaveRoot.setName("namenew2");
            List<M8SaveRootMany> m8SaveRootManyList = m8SaveRoot.getM8SaveRootManyList();
            M8SaveRootMany m8SaveRootMany = m8SaveRootManyList.get(0);

            List<M8SaveRootMany> one2many = new ArrayList<>();
            one2many.add(m8SaveRootMany);
            M8SaveRootMany m8SaveRootMany2 = new M8SaveRootMany();
            m8SaveRootMany2.setName("newOneToMany2");
            one2many.add(m8SaveRootMany2);
            m8SaveRoot.setM8SaveRootManyList(one2many);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
//            Assert.assertEquals(8, listenerContext.getJdbcExecuteAfterArgs().size());
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                Assert.assertEquals("INSERT INTO `m8_save_root_2many` (`id`,`name`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(many2many.get(0).getId() + "(String),root2Many1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                Assert.assertEquals("INSERT INTO `m8_save_root_2many` (`id`,`name`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(many2many.get(1).getId() + "(String),root2Many2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
//                Assert.assertEquals("INSERT INTO `m8_save_root` (`id`,`name`,`code`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(root.getId() + "(String),rootname(String),rootcode(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
//                Assert.assertEquals("INSERT INTO `m8_save_root_one` (`id`,`root_id`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(rootOne.getId() + "(String)," + root.getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
//                Assert.assertEquals("INSERT INTO `m8_save_root_many` (`id`,`root_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(many.get(0).getId() + "(String)," + root.getId() + "(String),rootMany1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
//                Assert.assertEquals("INSERT INTO `m8_save_root_many` (`id`,`root_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(many.get(1).getId() + "(String)," + root.getId() + "(String),rootMany2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(6);
//                Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                String s = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
//                Assert.assertTrue(s.endsWith("(String)," + root.getId() + "(String)," + many2many.get(0).getId() + "(String)"));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(7);
//                Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                String s = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
//                Assert.assertTrue(s.endsWith("(String)," + root.getId() + "(String)," + many2many.get(1).getId() + "(String)"));
//            }
        });
    }
    @Test
    public void testOne2Many2() {
        String rootId = before();

        invoke(listenerContext->{
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRootManyList())
                    .singleNotNull();

            List<M8SaveRootMany> m8SaveRootManyList = m8SaveRoot.getM8SaveRootManyList();
            m8SaveRootManyList.remove(1);//移除index=1的元素因为原先有两个
            M8SaveRootMany m8SaveRootMany2 = new M8SaveRootMany();
            m8SaveRootMany2.setName("newOneToMany2");
            m8SaveRootManyList.add(m8SaveRootMany2);//新加一个


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
//            Assert.assertEquals(8, listenerContext.getJdbcExecuteAfterArgs().size());
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                Assert.assertEquals("INSERT INTO `m8_save_root_2many` (`id`,`name`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(many2many.get(0).getId() + "(String),root2Many1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                Assert.assertEquals("INSERT INTO `m8_save_root_2many` (`id`,`name`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(many2many.get(1).getId() + "(String),root2Many2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
//                Assert.assertEquals("INSERT INTO `m8_save_root` (`id`,`name`,`code`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(root.getId() + "(String),rootname(String),rootcode(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
//                Assert.assertEquals("INSERT INTO `m8_save_root_one` (`id`,`root_id`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(rootOne.getId() + "(String)," + root.getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
//                Assert.assertEquals("INSERT INTO `m8_save_root_many` (`id`,`root_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(many.get(0).getId() + "(String)," + root.getId() + "(String),rootMany1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
//                Assert.assertEquals("INSERT INTO `m8_save_root_many` (`id`,`root_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(many.get(1).getId() + "(String)," + root.getId() + "(String),rootMany2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(6);
//                Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                String s = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
//                Assert.assertTrue(s.endsWith("(String)," + root.getId() + "(String)," + many2many.get(0).getId() + "(String)"));
//            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(7);
//                Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                String s = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
//                Assert.assertTrue(s.endsWith("(String)," + root.getId() + "(String)," + many2many.get(1).getId() + "(String)"));
//            }
        });
    }
}
