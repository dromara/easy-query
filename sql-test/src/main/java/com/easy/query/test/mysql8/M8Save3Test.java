package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.entity.save.SaveBehaviorEnum;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8Role3;
import com.easy.query.test.mysql8.entity.M8User3;
import com.easy.query.test.mysql8.entity.M8UserRole3;
import com.easy.query.test.mysql8.entity.save.M8SaveA;
import com.easy.query.test.mysql8.entity.save.M8SaveB;
import com.easy.query.test.mysql8.entity.save.M8SaveC;
import com.easy.query.test.mysql8.entity.save.M8SaveD;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * create time 2025/9/15 15:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8Save3Test extends BaseTest {

    public void deleteAll() {

        easyEntityQuery.deletable(M8SaveA.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveB.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveC.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8SaveD.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
    }

    public M8SaveA create(String a, String b, String c, String d) {
        M8SaveA a1 = new M8SaveA();
        a1.setId(a);
        a1.setName(a);

        M8SaveB b2 = new M8SaveB();
        b2.setId(b);
        b2.setName(b);
        a1.setM8SaveB(b2);

        M8SaveC c3 = new M8SaveC();
        c3.setId(c);
        c3.setName(c);
        b2.setM8SaveC(c3);

        M8SaveD d4 = new M8SaveD();
        d4.setId(d);
        d4.setName(d);
        c3.setM8SaveD(d4);
        return a1;
    }

    public void insertOne() {

        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();
            ArrayList<M8SaveA> list = new ArrayList<>();
            {
                M8SaveA item = create("1", "2", "3", "4");
                list.add(item);
            }
            {
                M8SaveA item = create("5", "6", "7", "8");
                list.add(item);
            }
            {
                M8SaveA item = create("9", "10", "11", "12");
                list.add(item);
            }

            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);

                try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                    easyEntityQuery.savable(list).executeCommand();
                    transaction.commit();
                }

            }

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
    public void testOwnershipChange1() {
        deleteAll();
        insertOne();


        invoke(listenerContext -> {
            M8SaveA a1 = easyEntityQuery.queryable(M8SaveA.class).whereById("1")
                    .include((c,s)->{
                        c.query(s.m8SaveB().m8SaveC().m8SaveD());
                    }).singleNotNull();
            M8SaveA a5 = easyEntityQuery.queryable(M8SaveA.class).whereById("5")
                    .include((c,s)->{
                        c.query(s.m8SaveB().m8SaveC().m8SaveD());
                    }).singleNotNull();
            M8SaveA a9 = easyEntityQuery.queryable(M8SaveA.class).whereById("9")
                    .include((c,s)->{
                        c.query(s.m8SaveB().m8SaveC().m8SaveD());
                    }).singleNotNull();

            List<M8SaveA> list = new ArrayList<>();
            list.add(a1);
            list.add(a5);
            list.add(a9);

            M8SaveB b2 = a1.getM8SaveB();
            M8SaveB b6 = a5.getM8SaveB();
            a1.setM8SaveB(b6);
            a5.setM8SaveB(b2);
            M8SaveC c7 = b6.getM8SaveC();
            M8SaveB b10 = a9.getM8SaveB();
            M8SaveC c11 = b10.getM8SaveC();
            b6.setM8SaveC(c11);
            b10.setM8SaveC(c7);

            M8SaveC c3 = b2.getM8SaveC();
            M8SaveD d4 = c3.getM8SaveD();
            d4.setName("myd4");

            M8SaveD d12 = c11.getM8SaveD();
            c3.setM8SaveD(d12);
            c11.setM8SaveD(d4);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(list).configure(s -> s.getSaveBehavior().add(SaveBehaviorEnum.ALLOW_OWNERSHIP_CHANGE)).executeCommand();
                transaction.commit();
            }


            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(18, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(12);
                Assert.assertEquals("UPDATE `m8_save_b` SET `pid` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("1(String),6(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(13);
                Assert.assertEquals("UPDATE `m8_save_b` SET `pid` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("5(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(14);
                Assert.assertEquals("UPDATE `m8_save_c` SET `pid` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("6(String),11(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(15);
                Assert.assertEquals("UPDATE `m8_save_c` SET `pid` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("10(String),7(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(16);
                Assert.assertEquals("UPDATE `m8_save_d` SET `pid` = ?,`name` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("11(String),myd4(String),4(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(17);
                Assert.assertEquals("UPDATE `m8_save_d` SET `pid` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("3(String),12(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }

    @Test
    public void testOwnershipChange2() {
        deleteAll();
        insertOne();


        invoke(listenerContext -> {
            M8SaveA a1 = easyEntityQuery.queryable(M8SaveA.class).whereById("1")
                    .include((c,s)->{
                        c.query(s.m8SaveB().m8SaveC().m8SaveD());
                    }).singleNotNull();

            a1.setM8SaveB(null);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(a1).configure(s -> s.getSaveBehavior().add(SaveBehaviorEnum.ALLOW_OWNERSHIP_CHANGE)).executeCommand();
                transaction.commit();
            }


            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(7, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("DELETE FROM `m8_save_d` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("4(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                Assert.assertEquals("DELETE FROM `m8_save_c` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(6);
                Assert.assertEquals("DELETE FROM `m8_save_b` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }

    @Test
    public void testOwnershipChange3() {
        deleteAll();
        insertOne();


        invoke(listenerContext -> {
            List<M8SaveA> list = easyEntityQuery.queryable(M8SaveA.class)
                    .include((c,s)->{
                        c.query(s.m8SaveB().m8SaveC().m8SaveD());
                    })
//                    .includeBy(m -> Include.of(
//                            m.m8SaveB().m8SaveC().m8SaveD().asIncludeQueryable()
//                    ))
                    .toList();
            M8SaveA a1 = list.stream().filter(o -> Objects.equals("1", o.getId())).findFirst().orElseThrow(() -> new RuntimeException("a1 not found"));
            M8SaveA a5 = list.stream().filter(o -> Objects.equals("5", o.getId())).findFirst().orElseThrow(() -> new RuntimeException("a5 not found"));
            M8SaveB b2 = a1.getM8SaveB();
            a1.setM8SaveB(null);
            M8SaveC c3 = b2.getM8SaveC();
            c3.setM8SaveD(null);

            M8SaveB b6 = a5.getM8SaveB();
            b6.setM8SaveC(c3);


            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(list).configure(s -> s.getSaveBehavior().add(SaveBehaviorEnum.ALLOW_OWNERSHIP_CHANGE)).executeCommand();
                transaction.commit();
            }


            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(9, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("DELETE FROM `m8_save_d` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("4(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                Assert.assertEquals("DELETE FROM `m8_save_d` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("8(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(6);
                Assert.assertEquals("DELETE FROM `m8_save_c` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("7(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(7);
                Assert.assertEquals("DELETE FROM `m8_save_b` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(8);
                Assert.assertEquals("UPDATE `m8_save_c` SET `pid` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("6(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }


    @Test
    public void many2manySetNullInsert() {
        easyEntityQuery.deletable(M8User3.class).allowDeleteStatement(true).where(m -> m.userId().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8Role3.class).allowDeleteStatement(true).where(m -> m.roleId().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8UserRole3.class).allowDeleteStatement(true).where(m -> m.userRoleId().isNotNull()).executeRows();

        invoke(listenerContext -> {

            M8Role3 m8Role3 = new M8Role3();
            m8Role3.setRoleId("r1");
            m8Role3.setRoleName("r1");

            M8User3 m8User3 = new M8User3();
            m8User3.setUserId("u1");
            m8User3.setUserAge(20);
            m8User3.setUserName("u1");
            m8User3.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
            m8User3.setRoles(Arrays.asList(m8Role3));

            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8Role3).executeCommand();
                easyEntityQuery.savable(m8User3).executeCommand();
                transaction.commit();
            }


            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("INSERT INTO `m8_role3` (`role_id`,`role_name`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("r1(String),r1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("INSERT INTO `m8_user3` (`user_id`,`user_name`,`user_age`,`create_time`) VALUES (?,?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("u1(String),u1(String),20(Integer),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }

    @Test
    public void testForeach() {
        deleteAll();
        insertOne();


        invoke(listenerContext -> {
            Exception e = null;
            try {

                M8SaveA a1 = easyEntityQuery.queryable(M8SaveA.class).whereById("1")
                        .include((c,s)->{
                            c.query(s.m8SaveB().m8SaveC().m8SaveD());
                        })
                        .forEach(item -> {
                            if (item.getId().equals("1")) {
                                throw new RuntimeException("123123");
                            }
                        }).singleNotNull();
            } catch (Exception ex) {
                ex.printStackTrace();
                e = ex;
            }
            Assert.assertNotNull(e);
            Assert.assertEquals("123123",e.getMessage());
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(1, listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT `id`,`name` FROM `m8_save_a` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
        });
    }

}
