package com.easy.query.test.mysql8;

import com.alibaba.fastjson2.JSON;
import com.easy.query.api.proxy.entity.save.OwnershipPolicyEnum;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.proxy.sql.Include;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.blogtest.SysRole;
import com.easy.query.test.entity.blogtest.SysUser;
import com.easy.query.test.entity.blogtest.UserRole;
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
import java.util.Arrays;
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
public class M8Save2Test extends BaseTest {


    public void deleteAll() {

        easyEntityQuery.deletable(M8SaveRoot.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRoot2Many.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootMany.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootMiddleMany.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootOne.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveRootManyOne.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
    }

    public String insertOne() {

        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();

            M8SaveRoot root = new M8SaveRoot();
            root.setName("rootname");
            root.setCode("rootcode");
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

            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);

                try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                    easyEntityQuery.savable(root).executeCommand();
                    transaction.commit();
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
        deleteAll();


        invoke(listenerContext -> {
            ArrayList<M8SaveRoot> roots = new ArrayList<>();
            {
                M8SaveRoot m8SaveRoot = new M8SaveRoot();
                m8SaveRoot.setId("1");
                m8SaveRoot.setM8SaveRootManyList(new ArrayList<>());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("2").build());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("3").build());
                roots.add(m8SaveRoot);
            }
            {
                M8SaveRoot m8SaveRoot = new M8SaveRoot();
                m8SaveRoot.setId("4");
                m8SaveRoot.setM8SaveRootManyList(new ArrayList<>());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("5").build());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("6").build());
                roots.add(m8SaveRoot);
            }


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(roots).executeCommand();
                transaction.commit();
            }
        });


        invoke(listenerContext -> {
            List<M8SaveRoot> list = easyEntityQuery.queryable(M8SaveRoot.class).whereByIds(Arrays.asList("1", "4"))
                    .includes(m -> m.m8SaveRootManyList())
                    .toList();
            String jsonString = JSON.toJSONString(list);
            System.out.println(jsonString);
            M8SaveRoot rootId1 = list.stream().filter(o -> Objects.equals("1", o.getId())).findFirst().orElse(null);
            M8SaveRoot rootId4 = list.stream().filter(o -> Objects.equals("4", o.getId())).findFirst().orElse(null);
            Assert.assertNotNull(rootId1);
            Assert.assertNotNull(rootId4);
            //id4里面移除id5的
            M8SaveRootMany id5 = rootId4.getM8SaveRootManyList().remove(0);

            //放到id1里面
            rootId1.getM8SaveRootManyList().add(id5);

            Exception exception = null;

            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(list).executeCommand();
                transaction.commit();
            } catch (Exception e) {
                exception = e;
            }
            Assert.assertNotNull(exception);
            Assert.assertEquals("relation value not equals,entity:[M8SaveRootMany],property:[rootId],value:[4],should:[1]. Current OwnershipPolicy does not allow reassignment.", exception.getMessage());

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
        deleteAll();


        invoke(listenerContext -> {
            ArrayList<M8SaveRoot> roots = new ArrayList<>();
            {
                M8SaveRoot m8SaveRoot = new M8SaveRoot();
                m8SaveRoot.setId("1");
                m8SaveRoot.setM8SaveRootManyList(new ArrayList<>());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("2").build());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("3").build());
                roots.add(m8SaveRoot);
            }
            {
                M8SaveRoot m8SaveRoot = new M8SaveRoot();
                m8SaveRoot.setId("4");
                m8SaveRoot.setM8SaveRootManyList(new ArrayList<>());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("5").build());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("6").build());
                roots.add(m8SaveRoot);
            }


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(roots).executeCommand();
                transaction.commit();
            }
        });


        invoke(listenerContext -> {
            List<M8SaveRoot> list = easyEntityQuery.queryable(M8SaveRoot.class).whereByIds(Arrays.asList("1", "4"))
                    .includes(m -> m.m8SaveRootManyList())
                    .toList();
            String jsonString = JSON.toJSONString(list);
            System.out.println(jsonString);
            M8SaveRoot rootId1 = list.stream().filter(o -> Objects.equals("1", o.getId())).findFirst().orElse(null);
            M8SaveRoot rootId4 = list.stream().filter(o -> Objects.equals("4", o.getId())).findFirst().orElse(null);
            Assert.assertNotNull(rootId1);
            Assert.assertNotNull(rootId4);
            //id4里面移除id5的
            M8SaveRootMany id5 = rootId4.getM8SaveRootManyList().remove(0);

            //放到id1里面
            rootId1.getM8SaveRootManyList().add(id5);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(list).ownershipPolicy(OwnershipPolicyEnum.AllowOwnershipChange).executeCommand();
                transaction.commit();
            }

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name`,`code` FROM `m8_save_root` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("1(String),4(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT t.`id`,t.`root_id`,t.`name` FROM `m8_save_root_many` t WHERE t.`root_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("1(String),4(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("UPDATE `m8_save_root_many` SET `root_id` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("1(String),5(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });

    }

    /**
     * 测试同一个值对象存在不同的聚合根中
     */
    @Test
    public void testOne2Many3() {
        deleteAll();


        invoke(listenerContext -> {
            ArrayList<M8SaveRoot> roots = new ArrayList<>();
            {
                M8SaveRoot m8SaveRoot = new M8SaveRoot();
                m8SaveRoot.setId("1");
                m8SaveRoot.setM8SaveRootManyList(new ArrayList<>());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("2").build());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("3").build());
                roots.add(m8SaveRoot);
            }
            {
                M8SaveRoot m8SaveRoot = new M8SaveRoot();
                m8SaveRoot.setId("4");
                m8SaveRoot.setM8SaveRootManyList(new ArrayList<>());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("5").build());
                m8SaveRoot.getM8SaveRootManyList().add(M8SaveRootMany.builder().id("6").build());
                roots.add(m8SaveRoot);
            }


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(roots).executeCommand();
                transaction.commit();
            }
        });


        invoke(listenerContext -> {
            List<M8SaveRoot> list = easyEntityQuery.queryable(M8SaveRoot.class).whereByIds(Arrays.asList("1", "4"))
                    .includes(m -> m.m8SaveRootManyList())
                    .toList();
            String jsonString = JSON.toJSONString(list);
            System.out.println(jsonString);
            M8SaveRoot rootId1 = list.stream().filter(o -> Objects.equals("1", o.getId())).findFirst().orElse(null);
            M8SaveRoot rootId4 = list.stream().filter(o -> Objects.equals("4", o.getId())).findFirst().orElse(null);
            Assert.assertNotNull(rootId1);
            Assert.assertNotNull(rootId4);
            //id4里面移除id5的
            M8SaveRootMany id5 = rootId4.getM8SaveRootManyList().get(0);

            //放到id1里面
            rootId1.getM8SaveRootManyList().add(id5);

            Exception exception = null;
            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(list).ownershipPolicy(OwnershipPolicyEnum.AllowOwnershipChange).executeCommand();
                transaction.commit();
            } catch (Exception e) {
                exception = e;
            }

            Assert.assertNotNull(exception);
            Assert.assertEquals("The current object:[M8SaveRootMany] has a conflicting save state and cannot be changed from [M8SaveRoot.INSERT] to [M8SaveRoot.UPDATE_IGNORE].", exception.getMessage());
        });

    }


    @Test
     public void USerRoleTest(){
        SysRole sysRole = new SysRole();
        SysUser sysUser = new SysUser();
        UserRole userRole = new UserRole();
    }
}
