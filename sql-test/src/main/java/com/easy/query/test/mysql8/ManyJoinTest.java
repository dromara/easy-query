package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.proxy.Draft3Proxy;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.PartitionByTypeExpression;
import com.easy.query.core.proxy.part.Part1;
import com.easy.query.core.proxy.part.proxy.Part1Proxy;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.doc.entity.DocUserBook;
import com.easy.query.test.entity.blogtest.RoleMenu;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.entity.school.SchoolClassTeacher;
import com.easy.query.test.entity.school.SchoolTeacher;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.dto.M8RoleDTO;
import com.easy.query.test.mysql8.dto.M8RoleDTO2;
import com.easy.query.test.mysql8.entity.M8Menu;
import com.easy.query.test.mysql8.entity.M8MenuOwner;
import com.easy.query.test.mysql8.entity.M8Role;
import com.easy.query.test.mysql8.entity.M8Role2;
import com.easy.query.test.mysql8.entity.M8RoleMenu;
import com.easy.query.test.mysql8.entity.M8User;
import com.easy.query.test.mysql8.entity.M8User2;
import com.easy.query.test.mysql8.entity.M8UserBook;
import com.easy.query.test.mysql8.entity.M8UserRole;
import com.easy.query.test.mysql8.entity.M8UserRole2;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.proxy.M8MenuProxy;
import com.easy.query.test.mysql8.entity.proxy.M8UserProxy;
import com.easy.query.test.mysql8.entity.proxy.M8UserRoleProxy;
import com.easy.query.test.mysql8.vo.UserAndBankCount;
import com.easy.query.test.mysql8.vo.proxy.UserAndBankCountProxy;
import lombok.var;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * create time 2025/3/6 11:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class ManyJoinTest extends BaseTest {
    @Before
    public void before() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBankCard.class, DocBank.class, DocUser.class, DocUserBook.class, SchoolClass.class, SchoolTeacher.class, SchoolClassTeacher.class, M8User.class, M8Role.class, M8UserRole.class, M8User2.class, M8Role2.class, M8UserRole2.class, M8UserBook.class, M8RoleMenu.class, M8Menu.class, M8MenuOwner.class));
        codeFirstCommand.executeWithTransaction(s -> s.commit());
        {
            easyEntityQuery.deletable(DocBankCard.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(DocBank.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(DocUser.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(DocUserBook.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(SchoolClass.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(SchoolTeacher.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(SchoolClassTeacher.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8User.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8Role.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8UserRole.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8User2.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8Role2.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8UserRole2.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8UserBook.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8RoleMenu.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8Menu.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(M8MenuOwner.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
        }

        {
            M8User2 m8User2 = new M8User2();
            m8User2.setUserId("123");
            m8User2.setUserAge(123);
            m8User2.setUserName("123");
            m8User2.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
            M8UserRole2 m8UserRole2 = new M8UserRole2();
            m8UserRole2.setUserRoleId("1");
            m8UserRole2.setUserId("123");
            m8UserRole2.setRoleId("456");

            M8Role2 m8Role2 = new M8Role2();
            m8Role2.setRoleId("456");
            m8Role2.setRoleName("456role");

            ArrayList<M8Role> m8Roles = new ArrayList<>();
            ArrayList<M8RoleMenu> m8RoleMenus = new ArrayList<>();
            ArrayList<M8Menu> m8Menus = new ArrayList<>();
            ArrayList<M8MenuOwner> m8MenuOwners = new ArrayList<>();
            M8Role m8Role = new M8Role();
            m8Role.setId("r123");
            m8Role.setName("测试角色");
            m8Role.setCreateTime(LocalDateTime.of(2020, 1, 1, 0, 0));
            m8Roles.add(m8Role);
            M8RoleMenu roleMenu = new M8RoleMenu();
            roleMenu.setId("rm123");
            roleMenu.setRoleId("r123");
            roleMenu.setMenuId("m123");
            m8RoleMenus.add(roleMenu);
            M8Menu m8Menu = new M8Menu();
            m8Menu.setId("m123");
            m8Menu.setName("测试菜单");
            m8Menu.setPath("/admin");
            m8Menu.setOwnerId("o123");
            m8Menus.add(m8Menu);
            M8MenuOwner m8MenuOwner = new M8MenuOwner();
            m8MenuOwner.setId("o123");
            m8MenuOwner.setName("测试所属菜单");
            m8MenuOwners.add(m8MenuOwner);

            easyEntityQuery.insertable(m8User2).executeRows();
            easyEntityQuery.insertable(m8UserRole2).executeRows();
            easyEntityQuery.insertable(m8Role2).executeRows();
            easyEntityQuery.insertable(m8Roles).executeRows();
            easyEntityQuery.insertable(m8RoleMenus).executeRows();
            easyEntityQuery.insertable(m8Menus).executeRows();
            easyEntityQuery.insertable(m8MenuOwners).executeRows();
        }
    }

    @Test
    public void manyGroupTest1() {
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().any();
                }).toList();
    }


    @Test
    public void testElement4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().where(o -> o.type().eq("123")).element(0).bankId().eq("123");
                    user.bankCards().where(o -> o.type().eq("123")).element(0).type().eq("123");
                    user.bankCards().element(2).type().eq("123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY t1.`id` ASC)) AS `__row__` FROM `doc_bank_card` t1 WHERE t1.`type` = ?) t3 ON (t3.`uid` = t.`id` AND t3.`__row__` = ?) LEFT JOIN (SELECT t4.`id`,t4.`uid`,t4.`code`,t4.`type`,t4.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t4.`uid` ORDER BY t4.`id` ASC)) AS `__row__` FROM `doc_bank_card` t4) t6 ON (t6.`uid` = t.`id` AND t6.`__row__` = ?) WHERE t3.`bank_id` = ? AND t3.`type` = ? AND t6.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),3(Integer),123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testElement5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().orderBy(x -> {
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().eq("123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY IFNULL(t1.`code`,?) DESC)) AS `__row__` FROM `doc_bank_card` t1) t3 ON (t3.`uid` = t.`id` AND t3.`__row__` = ?) WHERE t3.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("x(String),3(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testElement6() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().orderBy(x -> {
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().eq("123");
                }).orderBy(user -> {
                    user.bankCards().orderBy(x -> {
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().asc();
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY IFNULL(t1.`code`,?) DESC)) AS `__row__` FROM `doc_bank_card` t1) t3 ON (t3.`uid` = t.`id` AND t3.`__row__` = ?) WHERE t3.`type` = ? ORDER BY t3.`type` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("x(String),3(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testElement7() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().orderBy(x -> {
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().eq("123");
                }).orderBy(user -> {
                    user.bankCards().orderBy(x -> {
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().asc();
                }).select(user -> Select.DRAFT.of(
                        user.bankCards().orderBy(x -> {
                            x.code().nullOrDefault("x").desc();
                        }).element(2).type(),
                        user.bankCards().orderBy(x -> {
                            x.code().nullOrDefault("x").desc();
                        }).element(2).code()
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t3.`type` AS `value1`,t3.`code` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY IFNULL(t1.`code`,?) DESC)) AS `__row__` FROM `doc_bank_card` t1) t3 ON (t3.`uid` = t.`id` AND t3.`__row__` = ?) WHERE t3.`type` = ? ORDER BY t3.`type` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("x(String),3(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testElement3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                .subQueryToGroupJoin(x -> x.bankCards())
                .select(user -> Select.DRAFT.of(
                        user.bankCards().where(o -> o.type().eq("123")).max(x -> x.code()),
                        user.bankCards().where(o -> o.type().eq("123")).element(0).type()
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`__max2__` AS `value1`,t5.`type` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,MAX((CASE WHEN t1.`type` = ? THEN t1.`code` ELSE NULL END)) AS `__max2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` LEFT JOIN (SELECT t3.`id`,t3.`uid`,t3.`code`,t3.`type`,t3.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t3.`uid` ORDER BY t3.`id` ASC)) AS `__row__` FROM `doc_bank_card` t3 WHERE t3.`type` = ?) t5 ON (t5.`uid` = t.`id` AND t5.`__row__` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testElement8() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                    .subQueryToGroupJoin(x -> x.bankCards())
                    .select(user -> Select.DRAFT.of(
                            user.bankCards().where(o -> o.type().eq("123")).elements(3, 5).max(x -> x.code()),
                            user.bankCards().where(o -> o.type().eq("123")).element(0).type()
                    )).toList();
        } catch (Exception ex) {

        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t3.`__max2__` AS `value1`,t6.`type` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t2.`uid` AS `uid`,MAX(t2.`code`) AS `__max2__` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id` FROM `doc_bank_card` t1 WHERE t1.`type` = ? LIMIT 3 OFFSET 3) t2 GROUP BY t2.`uid`) t3 ON t3.`uid` = t.`id` LEFT JOIN (SELECT t4.`id`,t4.`uid`,t4.`code`,t4.`type`,t4.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t4.`uid` ORDER BY t4.`id` ASC)) AS `__row__` FROM `doc_bank_card` t4 WHERE t4.`type` = ?) t6 ON (t6.`uid` = t.`id` AND t6.`__row__` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .subQueryToGroupJoin(x -> x.schoolTeachers())
                .where(s -> {
                    s.schoolTeachers().where(x -> x.name().like("小明")).orderBy(x -> {
                        x.name().asc();
                    }).firstElement().name().like("123123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t LEFT JOIN (SELECT t2.`class_id` AS `__class_id__`,t1.`id`,t1.`name`,(ROW_NUMBER() OVER (PARTITION BY t2.`class_id` ORDER BY t1.`name` ASC)) AS `__row__` FROM `school_teacher` t1 INNER JOIN `school_class_teacher` t2 ON t1.`id` = t2.`teacher_id` WHERE t1.`name` LIKE ?) t5 ON (t5.`__class_id__` = t.`id` AND t5.`__row__` = ?) WHERE t5.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),1(Integer),%123123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User> list = easyEntityQuery.queryable(M8User.class)
                .subQueryToGroupJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.name().like("小明角色")).count().eq(123L);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,COUNT((CASE WHEN t1.`name` LIKE ? THEN ? ELSE NULL END)) AS `__count2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE IFNULL(t4.`__count2__`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),1(Integer),123(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many3_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .subQueryToGroupJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.roleName().like("小明角色")).count().eq(123L);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`user_id`,t.`user_name`,t.`user_age`,t.`create_time` FROM `m8_user2` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,COUNT((CASE WHEN t1.`role_name` LIKE ? THEN ? ELSE NULL END)) AS `__count2__` FROM `m8_role2` t1 INNER JOIN `m8_user_role2` t2 ON t1.`role_id` = t2.`role_id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`user_id` WHERE IFNULL(t4.`__count2__`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),1(Integer),123(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many3_2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .subQueryToGroupJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.roleName().like("小明角色")).sum(x -> x.roleName().toNumber(Long.class)).eq(123L);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`user_id`,t.`user_name`,t.`user_age`,t.`create_time` FROM `m8_user2` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,SUM((CASE WHEN t1.`role_name` LIKE ? THEN CAST(t1.`role_name` AS SIGNED) ELSE ? END)) AS `__sum2__` FROM `m8_role2` t1 INNER JOIN `m8_user_role2` t2 ON t1.`role_id` = t2.`role_id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`user_id` WHERE IFNULL(t4.`__sum2__`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),0(Integer),123(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User> list = easyEntityQuery.queryable(M8User.class)
                .subQueryToGroupJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.name().like("小明角色")).orderBy(x -> {
                        x.name().asc();
                    }).firstElement().name().eq("123123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `__user_id__`,t1.`id`,t1.`name`,t1.`create_time`,(ROW_NUMBER() OVER (PARTITION BY t2.`user_id` ORDER BY t1.`name` ASC)) AS `__row__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` WHERE t1.`name` LIKE ?) t5 ON (t5.`__user_id__` = t.`id` AND t5.`__row__` = ?) WHERE t5.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),1(Integer),123123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many4_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .subQueryToGroupJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.roleName().like("小明角色")).orderBy(x -> {
                        x.roleName().asc();
                    }).firstElement().roleName().eq("123123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`user_id`,t.`user_name`,t.`user_age`,t.`create_time` FROM `m8_user2` t LEFT JOIN (SELECT t2.`user_id` AS `__user_id__`,t1.`role_id`,t1.`role_name`,(ROW_NUMBER() OVER (PARTITION BY t2.`user_id` ORDER BY t1.`role_name` ASC)) AS `__row__` FROM `m8_role2` t1 INNER JOIN `m8_user_role2` t2 ON t1.`role_id` = t2.`role_id` WHERE t1.`role_name` LIKE ?) t5 ON (t5.`__user_id__` = t.`user_id` AND t5.`__row__` = ?) WHERE t5.`role_name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),1(Integer),123123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many9() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .where(s -> {
                    s.books().where(x -> x.bookPrice().gt(BigDecimal.valueOf(100))).orderBy(x -> {
                        x.bookPrice().desc(OrderByModeEnum.NULLS_LAST);
                    }).firstElement().bookPrice().eq(BigDecimal.ONE);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`user_id`,t.`user_name`,t.`user_age`,t.`create_time` FROM `m8_user2` t LEFT JOIN (SELECT t1.`book_id`,t1.`user_id`,t1.`book_name`,t1.`book_price`,(ROW_NUMBER() OVER (PARTITION BY t1.`user_id` ORDER BY CASE WHEN t1.`book_price` IS NULL THEN 1 ELSE 0 END ASC,t1.`book_price` DESC)) AS `__row__` FROM `m8_user_book` t1 WHERE t1.`book_price` > ?) t3 ON (t3.`user_id` = t.`user_id` AND t3.`__row__` = ?) WHERE t3.`book_price` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("100(BigDecimal),1(Integer),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void manyJoinMany2Many10() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .subQueryToGroupJoin(x -> x.books())
                .where(s -> {
                    s.books().where(x -> x.bookPrice().gt(BigDecimal.valueOf(100))).sum(x -> x.bookPrice()).eq(BigDecimal.ONE);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`user_id`,t.`user_name`,t.`user_age`,t.`create_time` FROM `m8_user2` t LEFT JOIN (SELECT t1.`user_id` AS `userId`,SUM((CASE WHEN t1.`book_price` > ? THEN t1.`book_price` ELSE ? END)) AS `__sum2__` FROM `m8_user_book` t1 GROUP BY t1.`user_id`) t2 ON t2.`userId` = t.`user_id` WHERE IFNULL(t2.`__sum2__`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("100(BigDecimal),0(Integer),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyJoinGroup() {
        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .subQueryToGroupJoin(x -> x.books())
                .where(m -> {
                    m.books().joining(x -> x.bookName(), ",").like("123");
                }).toList();
//        easyEntityQuery.queryable(M8UserBook.class)
//                .groupBy(m -> GroupKeys.of(m.userId()))
//                .select(group -> {
//                    group.where().element(1).bookPrice();
//                })

    }

    @Test
    public void testManyJoinGroup1() {


        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();


            List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                    .includes(x -> x.roles()).toList();
            Assert.assertNotEquals(0, list.size());
            Assert.assertNotEquals(0, list.get(0).getRoles().size());

        } finally {
            trackManager.release();
        }

    }

    @Test
    public void testMany2Many2GroupJoin() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<M8User> admin = easyEntityQuery.queryable(M8User.class)
                .subQueryToGroupJoin(s -> s.roles())
                .subQueryConfigure(s -> s.roles(), s -> s.subQueryToGroupJoin(r -> r.menus()))
                .where(m -> {
                    m.roles().any(r -> {
                        r.menus().any(menu -> menu.name().eq("admin"));
                    });
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,(CASE WHEN COUNT((CASE WHEN IFNULL(t8.`__any2__`,?) = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` LEFT JOIN (SELECT t6.`role_id` AS `role_id`,(CASE WHEN COUNT((CASE WHEN t5.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `m8_menu` t5 INNER JOIN `m8_role_menu` t6 ON t5.`id` = t6.`menu_id` GROUP BY t6.`role_id`) t8 ON t8.`role_id` = t1.`id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE IFNULL(t4.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),true(Boolean),1(Integer),true(Boolean),false(Boolean),admin(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    private <TMember extends Number> NumberTypeExpression<BigDecimal> createColumn(M8UserProxy u, SQLFuncExpression1<M8MenuProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        return u.roles().sumBigDecimal(role -> {
            return role.menus().where(menu -> menu.name().like("admin")).sum(columnSelector);
        });
    }

    @Test
    public void testMany2Many2GroupJoin2() {


//        List<Draft1<Integer>> list1 = easyEntityQuery.queryable(M8User.class)
//                .subQueryToGroupJoin(s -> s.roles())
//                .subQueryConfigure(s -> s.roles(), s -> s.subQueryToGroupJoin(r -> r.menus()))
//                .select(m -> Select.DRAFT.of(
//                        m.roles().sum(role -> {
//                            return role.menus().where(menu -> menu.name().like("admin")).sum(menu -> menu.path().asInteger());
//                        })
//                )).toList();
//
//        List<Draft2<BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(M8User.class)
//                .select(m -> Select.DRAFT.of(
//                        createColumn(m, menu -> menu.path().asInteger()),
//                        createColumn(m, menu -> menu.name().asInteger())
//                )).toList();


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<M8User> admin = easyEntityQuery.queryable(M8User.class)
                .subQueryToGroupJoin(s -> s.roles())
                .subQueryConfigure(s -> s.roles(), s -> s.subQueryToGroupJoin(r -> r.menus()))
                .where(m -> {

                    m.roles().flatElement().menus().any(menu -> menu.name().eq("admin"));
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,(CASE WHEN COUNT((CASE WHEN IFNULL(t8.`__any2__`,?) = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` LEFT JOIN (SELECT t6.`role_id` AS `role_id`,(CASE WHEN COUNT((CASE WHEN t5.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `m8_menu` t5 INNER JOIN `m8_role_menu` t6 ON t5.`id` = t6.`menu_id` GROUP BY t6.`role_id`) t8 ON t8.`role_id` = t1.`id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE IFNULL(t4.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),true(Boolean),1(Integer),true(Boolean),false(Boolean),admin(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testMany2Many2GroupJoin24() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<M8User> admin = easyEntityQuery.queryable(M8User.class)
                .subQueryToGroupJoin(s -> s.roles())
                .subQueryConfigure(s -> s.roles(), s -> s.subQueryToGroupJoin(r -> r.menus()))
                .where(m -> {

                    m.roles().flatElement().id().eq("123");
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,(CASE WHEN COUNT((CASE WHEN t1.`id` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE IFNULL(t4.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testMany2Many2GroupJoin3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<M8User> admin = easyEntityQuery.queryable(M8User.class)
                .configure(o -> {
                    o.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN);
                })
                .where(m -> {

                    m.roles().any(r -> {
                        r.menus().any(menu -> menu.name().eq("admin"));
                    });
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,(CASE WHEN COUNT((CASE WHEN IFNULL(t8.`__any2__`,?) = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` LEFT JOIN (SELECT t6.`role_id` AS `role_id`,(CASE WHEN COUNT((CASE WHEN t5.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `m8_menu` t5 INNER JOIN `m8_role_menu` t6 ON t5.`id` = t6.`menu_id` GROUP BY t6.`role_id`) t8 ON t8.`role_id` = t1.`id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE IFNULL(t4.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),true(Boolean),1(Integer),true(Boolean),false(Boolean),admin(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testMany2Many2GroupJoin4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<M8User> admin = easyEntityQuery.queryable(M8User.class)
                .configure(o -> {
                    o.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN);
                })
                .where(m -> {
                    m.roles().flatElement().menus().flatElement().name().eq("admin");
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,(CASE WHEN COUNT((CASE WHEN IFNULL(t8.`__any2__`,?) = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` LEFT JOIN (SELECT t6.`role_id` AS `role_id`,(CASE WHEN COUNT((CASE WHEN t5.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__` FROM `m8_menu` t5 INNER JOIN `m8_role_menu` t6 ON t5.`id` = t6.`menu_id` GROUP BY t6.`role_id`) t8 ON t8.`role_id` = t1.`id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE IFNULL(t4.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),true(Boolean),1(Integer),true(Boolean),false(Boolean),admin(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testMany2Many2GroupJoin5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<M8User> admin = easyEntityQuery.queryable(M8User.class)
                .configure(o -> {
                    o.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN);
                })
                .where(m -> {
                    m.roles().flatElement().menus().flatElement().name().eq("admin");
                    m.roles().flatElement().menus().flatElement().path().likeMatchLeft("/admin");
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,(CASE WHEN COUNT((CASE WHEN IFNULL(t8.`__any2__`,?) = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__`,(CASE WHEN COUNT((CASE WHEN IFNULL(t8.`__any3__`,?) = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any3__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` LEFT JOIN (SELECT t6.`role_id` AS `role_id`,(CASE WHEN COUNT((CASE WHEN t5.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__`,(CASE WHEN COUNT((CASE WHEN t5.`path` LIKE ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any3__` FROM `m8_menu` t5 INNER JOIN `m8_role_menu` t6 ON t5.`id` = t6.`menu_id` GROUP BY t6.`role_id`) t8 ON t8.`role_id` = t1.`id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE IFNULL(t4.`__any2__`,?) = ? AND IFNULL(t4.`__any3__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),true(Boolean),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean),1(Integer),true(Boolean),false(Boolean),admin(String),1(Integer),true(Boolean),false(Boolean),/admin%(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void partitionByFirst() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<M8User> admin = easyEntityQuery.queryable(M8User.class)
                .where(m -> {
                    m.roles().orderBy(s -> s.name().asc()).first().name().eq("管理员");
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `__user_id__`,t1.`id`,t1.`name`,t1.`create_time`,(ROW_NUMBER() OVER (PARTITION BY t2.`user_id` ORDER BY t1.`name` ASC)) AS `__row__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id`) t5 ON (t5.`__user_id__` = t.`id` AND t5.`__row__` = ?) WHERE t5.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),管理员(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void partitionByFirst2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<M8User> admin = easyEntityQuery.queryable(M8User.class)
                .where(m -> {
                    m.roles().orderBy(s -> s.name().asc()).first().createTime().le(LocalDateTime.of(2020, 1, 1, 1, 1));
                }).toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `__user_id__`,t1.`id`,t1.`name`,t1.`create_time`,(ROW_NUMBER() OVER (PARTITION BY t2.`user_id` ORDER BY t1.`name` ASC)) AS `__row__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id`) t5 ON (t5.`__user_id__` = t.`id` AND t5.`__row__` = ?) WHERE t5.`create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void test111() {

        var userIdQuery = easyEntityQuery.queryable(SysBankCard.class)
                .groupBy(bank_card -> GroupKeys.of(bank_card.bankId(), bank_card.uid()))
                .select(group -> new UserAndBankCountProxy()
                        .bankId().set(group.key1())
                        .uid().set(group.key2())
                        .bankCount().set(group.count())
                ).select(o -> Select.PART.of(
                        o,
                        o.expression().rowNumberOver().partitionBy(o.bankId()).orderByDescending(o.bankCount())
                )).where(userBankCount -> {
                    userBankCount.partColumn1().eq(3L);
                }).selectColumn(userBankCount -> userBankCount.entityTable().uid());


        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(user -> {
                    user.id().in(userIdQuery);
                })
                .orderBy(user -> {
                    user.createTime().desc();
                }).toList();


    }


    @Test
    public void testFlatMenuOwner(){
        List<M8RoleDTO> list = easyEntityQuery.queryable(M8Role.class)
                .selectAutoInclude(M8RoleDTO.class)
                .toList();
        System.out.println("123");
    }

    @Test
    public void testFlatMenuOwner1(){
        List<M8RoleDTO2> list = easyEntityQuery.queryable(M8Role.class)
                .selectAutoInclude(M8RoleDTO2.class)
                .toList();
        System.out.println("123");
    }
}
