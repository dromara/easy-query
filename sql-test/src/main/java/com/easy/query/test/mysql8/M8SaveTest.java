package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.proxy.sql.Include;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.save.M8SaveRoot;
import com.easy.query.test.mysql8.entity.save.M8SaveRoot2Many;
import com.easy.query.test.mysql8.entity.save.M8SaveRootMany;
import com.easy.query.test.mysql8.entity.save.M8SaveRootManyOne;
import com.easy.query.test.mysql8.entity.save.M8SaveRootMiddleMany;
import com.easy.query.test.mysql8.entity.save.M8SaveRootOne;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * create time 2025/9/8 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8SaveTest extends BaseTest {


    public void deleteAll(){

        easyEntityQuery.deletable(M8SaveRoot.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRoot2Many.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootMany.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootMiddleMany.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootOne.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootManyOne.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
    }
    public String insertOne(){
//        ArrayList<String> ids = new ArrayList<>();
//        List<List<String>> partition = EasyCollectionUtil.partition(ids, 1000);
//        easyEntityQuery.deletable(M8SaveRoot.class)
//                .where(m -> {
//                        m.or(()->{
//                            for (List<String> strings : partition) {
//                                m.id().in(strings);
//                            }
//                        });
//                }).executeRows();

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
                M8SaveRootManyOne m8SaveRootManyOne = new M8SaveRootManyOne();
                m8SaveRootManyOne.setName("rootMany2One");
                rootMany.setM8SaveRootManyOne(m8SaveRootManyOne);
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
                Assert.assertEquals(9, listenerContext.getJdbcExecuteAfterArgs().size());
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
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(8);
                    Assert.assertEquals("INSERT INTO `m8_save_root_many_one` (`id`,`root_many_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    String s = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
                    System.out.println(s);
                    System.out.println(many.get(1).getM8SaveRootManyOne().getId()+"(String),"+many.get(1).getId()+"(String),rootMany2One(String)");
                    Assert.assertEquals(many.get(1).getM8SaveRootManyOne().getId()+"(String),"+many.get(1).getId()+"(String),rootMany2One(String)",s);
                }
            }

            return root.getId();
        } finally {
            trackManager.release();
        }

    }
    public String before() {
        deleteAll();
        return insertOne();
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

        invoke(listenerContext -> {
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
    public void testPathAggregateRoot() {
        String rootId = before();

        invoke(listenerContext -> {
            SysBankCard sysBankCard = new SysBankCard();
            Exception exception=null;
            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(sysBankCard).savePath(o->Include.path(
                        o.user()
                )).executeCommand();
                transaction.commit();
            } catch (Exception e) {
                exception=e;
            }
            Assert.assertNotNull(exception);
            Assert.assertEquals("entity:[SysBankCard.user] value type is:[AGGREGATE_ROOT] save path limit only support value object",exception.getMessage());
        });
    }

    @Test
    public void testOne2Many2() {
        String rootId = before();

        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRootManyList())
                    .singleNotNull();

            List<M8SaveRootMany> m8SaveRootManyList = m8SaveRoot.getM8SaveRootManyList();
            M8SaveRootMany remove = m8SaveRootManyList.remove(1);//移除index=1的元素因为原先有两个
            M8SaveRootMany m8SaveRootMany2 = new M8SaveRootMany();
            m8SaveRootMany2.setName("newOneToMany2");
            m8SaveRootManyList.add(m8SaveRootMany2);//新加一个


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(4, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT t.`id`,t.`root_id`,t.`name` FROM `m8_save_root_many` t WHERE t.`root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("DELETE FROM `m8_save_root_many` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(remove.getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("INSERT INTO `m8_save_root_many` (`id`,`root_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(m8SaveRootMany2.getId() + "(String)," + rootId + "(String)," + m8SaveRootMany2.getName() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }

    @Test
    public void testOne2Many3() {
        String rootId = before();

        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRootManyList(),rq->rq.include(s->s.m8SaveRootManyOne()))
                    .singleNotNull();

            List<M8SaveRootMany> m8SaveRootManyList = m8SaveRoot.getM8SaveRootManyList();
            M8SaveRootMany remove = m8SaveRootManyList.remove(1);//移除index=1的元素因为原先有两个
            M8SaveRootMany m8SaveRootMany2 = new M8SaveRootMany();
            m8SaveRootMany2.setName("newOneToMany2");
            M8SaveRootManyOne m8SaveRootManyOne = new M8SaveRootManyOne();
            m8SaveRootManyOne.setName("m8SaveRootManyOne2");
            m8SaveRootMany2.setM8SaveRootManyOne(m8SaveRootManyOne);
            m8SaveRootManyList.add(m8SaveRootMany2);//新加一个


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(6, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT t.`id`,t.`root_id`,t.`name` FROM `m8_save_root_many` t WHERE t.`root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT t.`id`,t.`root_many_id`,t.`name` FROM `m8_save_root_many_one` t WHERE t.`root_many_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("DELETE FROM `m8_save_root_many` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(remove.getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("INSERT INTO `m8_save_root_many` (`id`,`root_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(m8SaveRootMany2.getId() + "(String)," + rootId + "(String)," + m8SaveRootMany2.getName() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                Assert.assertEquals("INSERT INTO `m8_save_root_many_one` (`id`,`root_many_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(m8SaveRootManyList.get(1).getM8SaveRootManyOne().getId() + "(String)," + m8SaveRootManyList.get(1).getId() + "(String)," + m8SaveRootManyList.get(1).getM8SaveRootManyOne().getName() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }
    @Test
    public void testOne2Many4() {
        String rootId = before();

        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRootManyList())
                    .singleNotNull();
            List<M8SaveRootMany> m8SaveRootManyList = m8SaveRoot.getM8SaveRootManyList();
            M8SaveRootMany m8SaveRootMany2 = new M8SaveRootMany();
            m8SaveRootMany2.setName("newOneToMany2");
            M8SaveRootManyOne m8SaveRootManyOne = new M8SaveRootManyOne();
            m8SaveRootManyOne.setName("m8SaveRootManyOne2");
            m8SaveRootMany2.setM8SaveRootManyOne(m8SaveRootManyOne);
            m8SaveRootManyList.add(m8SaveRootMany2);//新加一个


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(4, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT t.`id`,t.`root_id`,t.`name` FROM `m8_save_root_many` t WHERE t.`root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("INSERT INTO `m8_save_root_many` (`id`,`root_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(m8SaveRootManyList.get(2).getId()+"(String),"+rootId+"(String),newOneToMany2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("INSERT INTO `m8_save_root_many_one` (`id`,`root_many_id`,`name`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(m8SaveRootManyList.get(2).getM8SaveRootManyOne().getId() + "(String)," + m8SaveRootManyList.get(2).getId() + "(String)," + m8SaveRootManyList.get(2).getM8SaveRootManyOne().getName() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }
    @Test
    public void testOne2Many5() {
        String rootId = before();

        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRootManyList())
                    .singleNotNull();
            List<M8SaveRootMany> m8SaveRootManyList = m8SaveRoot.getM8SaveRootManyList();
            M8SaveRootManyOne m8SaveRootManyOne = new M8SaveRootManyOne();
            m8SaveRootManyOne.setName("m8SaveRootManyOne2");
            m8SaveRootManyList.get(1).setM8SaveRootManyOne(m8SaveRootManyOne);

            Exception exception=null;

                try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                    easyEntityQuery.savable(m8SaveRoot).executeCommand();
                    transaction.commit();
                }catch (Exception ex){
                    exception=ex;
                }

            Assert.assertNotNull(exception);
            Assert.assertEquals("The current navigation property [M8SaveRootMany.m8SaveRootManyOne] is not being tracked.",exception.getMessage());

        });
    }
    @Test
    public void testOne2Many6() {
        String rootId = before();

        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRootManyList())
                    .singleNotNull();
            List<M8SaveRootMany> m8SaveRootManyList = m8SaveRoot.getM8SaveRootManyList();
            M8SaveRootManyOne m8SaveRootManyOne = new M8SaveRootManyOne();
            m8SaveRootManyOne.setName("m8SaveRootManyOne2");
            m8SaveRootManyList.get(1).setM8SaveRootManyOne(m8SaveRootManyOne);


                try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                    easyEntityQuery.savable(m8SaveRoot).savePath(o->Include.path(
                            o.m8SaveRootManyList().flatElement().m8SaveRootManyOne()
                    )).executeCommand();
                    transaction.commit();
                }


        });
    }

    @Test
    public void testMany2Many1() {
        String rootId = before();
        ArrayList<M8SaveRoot2Many> m8SaveRoot2Manies = new ArrayList<>();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRoot2ManyList())
                    .singleNotNull();
            List<M8SaveRoot2Many> m8SaveRoot2ManyList = m8SaveRoot.getM8SaveRoot2ManyList();
            List<String> m8SaveRoot2Manies0 = new ArrayList<>();
            for (M8SaveRoot2Many m8SaveRoot2Many : m8SaveRoot2ManyList) {
                m8SaveRoot2Manies0.add(m8SaveRoot2Many.getId() + "(String)");
            }
            m8SaveRoot2Manies.addAll(m8SaveRoot2ManyList);
            m8SaveRoot.getM8SaveRoot2ManyList().clear();


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `root_id`,`many_id` FROM `m8_save_root_middle_many` WHERE `root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_save_root_2many` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                String sqlParameterToString = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
                String join1 = String.join(",", m8SaveRoot2Manies0);
                Collections.reverse(m8SaveRoot2Manies0);
                String join2 = String.join(",", m8SaveRoot2Manies0);

                Assert.assertTrue(Objects.equals(join1, sqlParameterToString) || Objects.equals(join2, sqlParameterToString));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(0).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(1).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRoot2ManyList())
                    .singleNotNull();
            m8SaveRoot.getM8SaveRoot2ManyList().addAll(m8SaveRoot2Manies);
            List<M8SaveRoot2Many> m8SaveRoot2ManyList = m8SaveRoot.getM8SaveRoot2ManyList();


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(4, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `root_id`,`many_id` FROM `m8_save_root_middle_many` WHERE `root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertTrue(EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)).endsWith("(String)," + rootId + "(String)," + m8SaveRoot2ManyList.get(0).getId() + "(String)"));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertTrue(EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)).endsWith("(String)," + rootId + "(String)," + m8SaveRoot2ManyList.get(1).getId() + "(String)"));
            }
        });
    }


    @Test
    public void testMany2Many2() {
        String rootId = before();
        ArrayList<M8SaveRoot2Many> m8SaveRoot2Manies = new ArrayList<>();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRoot2ManyList())
                    .singleNotNull();
            List<M8SaveRoot2Many> m8SaveRoot2ManyList = m8SaveRoot.getM8SaveRoot2ManyList();
            List<String> m8SaveRoot2Manies0 = new ArrayList<>();
            for (M8SaveRoot2Many m8SaveRoot2Many : m8SaveRoot2ManyList) {
                m8SaveRoot2Manies0.add(m8SaveRoot2Many.getId() + "(String)");
            }
            m8SaveRoot2Manies.addAll(m8SaveRoot2ManyList);
            m8SaveRoot.getM8SaveRoot2ManyList().clear();


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).savePath(o->Include.path(
                        o.m8SaveRoot2ManyList()
                )).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `root_id`,`many_id` FROM `m8_save_root_middle_many` WHERE `root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_save_root_2many` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                String sqlParameterToString = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
                String join1 = String.join(",", m8SaveRoot2Manies0);
                Collections.reverse(m8SaveRoot2Manies0);
                String join2 = String.join(",", m8SaveRoot2Manies0);

                Assert.assertTrue(Objects.equals(join1, sqlParameterToString) || Objects.equals(join2, sqlParameterToString));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(0).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(1).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRoot2ManyList())
                    .singleNotNull();
            m8SaveRoot.getM8SaveRoot2ManyList().addAll(m8SaveRoot2Manies);
            List<M8SaveRoot2Many> m8SaveRoot2ManyList = m8SaveRoot.getM8SaveRoot2ManyList();


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).savePath(o->Include.path(
                        o.m8SaveRoot2ManyList()
                )).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(4, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `root_id`,`many_id` FROM `m8_save_root_middle_many` WHERE `root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertTrue(EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)).endsWith("(String)," + rootId + "(String)," + m8SaveRoot2ManyList.get(0).getId() + "(String)"));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("INSERT INTO `m8_save_root_middle_many` (`id`,`root_id`,`many_id`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertTrue(EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)).endsWith("(String)," + rootId + "(String)," + m8SaveRoot2ManyList.get(1).getId() + "(String)"));
            }
        });
    }

    @Test
    public void test2One1() {
        String rootId = before();
        ValueHolder<M8SaveRootOne> valueHolder = new ValueHolder<>();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .include(m -> m.m8SaveRootOne())
                    .singleNotNull();
            M8SaveRootOne m8SaveRootOne = m8SaveRoot.getM8SaveRootOne();
            valueHolder.setValue(m8SaveRootOne);
            m8SaveRoot.setM8SaveRootOne(null);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT t.`id`,t.`root_id`,t.`name` FROM `m8_save_root_one` t WHERE t.`root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("DELETE FROM `m8_save_root_one` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(valueHolder.getValue().getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .include(m -> m.m8SaveRootOne())
                    .singleNotNull();
            M8SaveRootOne m8SaveRootOne = m8SaveRoot.getM8SaveRootOne();
            Assert.assertNull(m8SaveRootOne);
            m8SaveRoot.setM8SaveRootOne(valueHolder.getValue());


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT t.`id`,t.`root_id`,t.`name` FROM `m8_save_root_one` t WHERE t.`root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("INSERT INTO `m8_save_root_one` (`id`,`root_id`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(valueHolder.getValue().getId() + "(String)," + rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }

    @Test
    public void test2One2() {
        String rootId = before();
        ValueHolder<M8SaveRootOne> valueHolder = new ValueHolder<>();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .include(m -> m.m8SaveRootOne())
                    .singleNotNull();
            M8SaveRootOne m8SaveRootOne = m8SaveRoot.getM8SaveRootOne();
            valueHolder.setValue(m8SaveRootOne);
            m8SaveRoot.setM8SaveRootOne(null);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT t.`id`,t.`root_id`,t.`name` FROM `m8_save_root_one` t WHERE t.`root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("DELETE FROM `m8_save_root_one` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(valueHolder.getValue().getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .singleNotNull();
            M8SaveRootOne m8SaveRootOne = m8SaveRoot.getM8SaveRootOne();
            Assert.assertNull(m8SaveRootOne);
            m8SaveRoot.setM8SaveRootOne(valueHolder.getValue());

            Exception exception = null;

            try {
                try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                    easyEntityQuery.savable(m8SaveRoot).executeCommand();
                    transaction.commit();
                }
            } catch (Exception ex) {
                exception = ex;
            }
            Assert.assertNotNull(exception);
            Assert.assertEquals("The current navigation property [M8SaveRoot.m8SaveRootOne] is not being tracked.", exception.getMessage());


        });
    }


    @Test
    public void test2One3() {
        String rootId = before();
        ValueHolder<M8SaveRootOne> valueHolder = new ValueHolder<>();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .singleNotNull();
            easyEntityQuery.loadInclude(m8SaveRoot, m -> m.m8SaveRootOne());
            M8SaveRootOne m8SaveRootOne = m8SaveRoot.getM8SaveRootOne();
            valueHolder.setValue(m8SaveRootOne);
            m8SaveRoot.setM8SaveRootOne(null);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `id`,`root_id`,`name` FROM `m8_save_root_one` WHERE `root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("DELETE FROM `m8_save_root_one` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(valueHolder.getValue().getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .singleNotNull();
            easyEntityQuery.loadInclude(m8SaveRoot, m -> m.m8SaveRootOne());
            M8SaveRootOne m8SaveRootOne = m8SaveRoot.getM8SaveRootOne();
            Assert.assertNull(m8SaveRootOne);
            m8SaveRoot.setM8SaveRootOne(valueHolder.getValue());


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `id`,`root_id`,`name` FROM `m8_save_root_one` WHERE `root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("INSERT INTO `m8_save_root_one` (`id`,`root_id`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(valueHolder.getValue().getId() + "(String)," + rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }

    @Test
    public void test2One4() {
        deleteAll();
        String rootId = insertOne();
        String rootId2 = insertOne();
        ValueHolder<M8SaveRootOne> valueHolder = new ValueHolder<>();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .include(m -> m.m8SaveRootOne())
                    .singleNotNull();
            M8SaveRootOne m8SaveRootOne = m8SaveRoot.getM8SaveRootOne();
            valueHolder.setValue(m8SaveRootOne);
            m8SaveRoot.setM8SaveRootOne(null);
            M8SaveRoot m8SaveRoot1 = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId2)
                    .singleNotNull();

            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(4, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT t.`id`,t.`root_id`,t.`name` FROM `m8_save_root_one` t WHERE t.`root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId2 + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("DELETE FROM `m8_save_root_one` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(valueHolder.getValue().getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }
    @Test
    public void testMany2Many3() {
        String rootId = before();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .singleNotNull();
            List<M8SaveRoot2Many> many2many = new ArrayList<>();
            M8SaveRoot2Many m8SaveRoot2Many = new M8SaveRoot2Many();
            m8SaveRoot2Many.setName("many2manynottrack");
            many2many.add(m8SaveRoot2Many);
            m8SaveRoot.setM8SaveRoot2ManyList(many2many);

            Exception exception = null;
            try {

                try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                    easyEntityQuery.savable(m8SaveRoot).executeCommand();
                    transaction.commit();
                }
            } catch (Exception ex) {
                exception = ex;
            }
            Assert.assertNotNull(exception);
            Assert.assertEquals("The current navigation property [M8SaveRoot.m8SaveRoot2ManyList] is not being tracked.", exception.getMessage());

        });
    }

    @Test
    public void testMany2Many4() {
        String rootId = before();
        ArrayList<M8SaveRoot2Many> m8SaveRoot2Manies = new ArrayList<>();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRoot2ManyList())
                    .singleNotNull();

            List<M8SaveRoot2Many> m8SaveRoot2ManyList = m8SaveRoot.getM8SaveRoot2ManyList();
            List<String> m8SaveRoot2Manies0 = new ArrayList<>();
            for (M8SaveRoot2Many m8SaveRoot2Many : m8SaveRoot2ManyList) {
                m8SaveRoot2Manies0.add(m8SaveRoot2Many.getId() + "(String)");
            }
            m8SaveRoot2Manies.addAll(m8SaveRoot2ManyList);
            m8SaveRoot.setM8SaveRoot2ManyList(null);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `root_id`,`many_id` FROM `m8_save_root_middle_many` WHERE `root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_save_root_2many` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                String sqlParameterToString = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
                String join1 = String.join(",", m8SaveRoot2Manies0);
                Collections.reverse(m8SaveRoot2Manies0);
                String join2 = String.join(",", m8SaveRoot2Manies0);

                Assert.assertTrue(Objects.equals(join1, sqlParameterToString) || Objects.equals(join2, sqlParameterToString));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(0).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(1).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }

    @Test
    public void testMany2Many7() {
        String rootId = before();
        ArrayList<M8SaveRoot2Many> m8SaveRoot2Manies = new ArrayList<>();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .includes(m -> m.m8SaveRoot2ManyList())
                    .include(m->m.m8SaveRootOne())
                    .singleNotNull();

            m8SaveRoot.setM8SaveRootOne(null);
            List<M8SaveRoot2Many> m8SaveRoot2ManyList = m8SaveRoot.getM8SaveRoot2ManyList();
            List<String> m8SaveRoot2Manies0 = new ArrayList<>();
            for (M8SaveRoot2Many m8SaveRoot2Many : m8SaveRoot2ManyList) {
                m8SaveRoot2Manies0.add(m8SaveRoot2Many.getId() + "(String)");
            }
            m8SaveRoot2Manies.addAll(m8SaveRoot2ManyList);
            m8SaveRoot.setM8SaveRoot2ManyList(null);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot)
                        .savePath(o -> Include.path(
                                o.m8SaveRoot2ManyList()
                        )).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(6, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `root_id`,`many_id` FROM `m8_save_root_middle_many` WHERE `root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_save_root_2many` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                String sqlParameterToString = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
                String join1 = String.join(",", m8SaveRoot2Manies0);
                Collections.reverse(m8SaveRoot2Manies0);
                String join2 = String.join(",", m8SaveRoot2Manies0);

                Assert.assertTrue(Objects.equals(join1, sqlParameterToString) || Objects.equals(join2, sqlParameterToString));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("SELECT t.`id`,t.`root_id`,t.`name` FROM `m8_save_root_one` t WHERE t.`root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(0).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(1).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }

    @Test
    public void testMany2Many5() {
        String rootId = before();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .singleNotNull();
            List<M8SaveRoot2Many> many2many = new ArrayList<>();
            M8SaveRoot2Many m8SaveRoot2Many = new M8SaveRoot2Many();
            m8SaveRoot2Many.setName("many2manynottrack");
            many2many.add(m8SaveRoot2Many);
            m8SaveRoot.setM8SaveRoot2ManyList(many2many);

            Exception exception=null;
            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }catch (Exception ex) {
                exception=ex;
            }
            Assert.assertNotNull(exception);
            Assert.assertEquals("The current navigation property [M8SaveRoot.m8SaveRoot2ManyList] is not being tracked.",exception.getMessage());

        });
    }


    @Test
    public void testMany2Many6() {
        String rootId = before();
        ArrayList<M8SaveRoot2Many> m8SaveRoot2Manies = new ArrayList<>();
        invoke(listenerContext -> {
            M8SaveRoot m8SaveRoot = easyEntityQuery.queryable(M8SaveRoot.class).whereById(rootId)
                    .singleNotNull();

            easyEntityQuery.loadInclude(m8SaveRoot, m -> m.m8SaveRoot2ManyList());

            List<M8SaveRoot2Many> m8SaveRoot2ManyList = m8SaveRoot.getM8SaveRoot2ManyList();
            List<String> m8SaveRoot2Manies0 = new ArrayList<>();
            for (M8SaveRoot2Many m8SaveRoot2Many : m8SaveRoot2ManyList) {
                m8SaveRoot2Manies0.add(m8SaveRoot2Many.getId() + "(String)");
            }
            m8SaveRoot2Manies.addAll(m8SaveRoot2ManyList);
            m8SaveRoot.setM8SaveRoot2ManyList(null);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8SaveRoot).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `root_id`,`many_id` FROM `m8_save_root_middle_many` WHERE `root_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT `id`,`name` FROM `m8_save_root_2many` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                String sqlParameterToString = EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0));
                String join1 = String.join(",", m8SaveRoot2Manies0);
                Collections.reverse(m8SaveRoot2Manies0);
                String join2 = String.join(",", m8SaveRoot2Manies0);

                Assert.assertTrue(Objects.equals(join1, sqlParameterToString) || Objects.equals(join2, sqlParameterToString));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(0).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("DELETE FROM `m8_save_root_middle_many` WHERE `root_id` = ? AND `many_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals(rootId + "(String)," + m8SaveRoot2Manies.get(1).getId() + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }
}
