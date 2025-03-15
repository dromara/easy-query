package com.easy.query.test.mysql8;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.doc.entity.DocUserBook;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.entity.school.SchoolClassTeacher;
import com.easy.query.test.entity.school.SchoolTeacher;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8Role;
import com.easy.query.test.mysql8.entity.M8Role2;
import com.easy.query.test.mysql8.entity.M8User;
import com.easy.query.test.mysql8.entity.M8User2;
import com.easy.query.test.mysql8.entity.M8UserRole;
import com.easy.query.test.mysql8.entity.M8UserRole2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/3/6 11:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class ManyJoinTest extends BaseTest{
    @Before
    public void before(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBankCard.class, DocBank.class, DocUser.class, DocUserBook.class,SchoolClass.class, SchoolTeacher.class, SchoolClassTeacher.class, M8User.class, M8Role.class, M8UserRole.class, M8User2.class, M8Role2.class, M8UserRole2.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
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
        }
    }

    @Test
    public void manyGroupTest1(){
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().any();
                }).toList();
    }


    @Test
    public void testElement4(){


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
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY 1 = 1)) AS `__row__` FROM `doc_bank_card` t1 WHERE t1.`type` = ?) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` LEFT JOIN (SELECT t6.`id` AS `id`,t6.`uid` AS `uid`,t6.`code` AS `code`,t6.`type` AS `type`,t6.`bank_id` AS `bank_id` FROM (SELECT t5.`id`,t5.`uid`,t5.`code`,t5.`type`,t5.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t5.`uid` ORDER BY 1 = 1)) AS `__row__` FROM `doc_bank_card` t5) t6 WHERE t6.`__row__` = ?) t8 ON t8.`uid` = t.`id` WHERE t4.`bank_id` = ? AND t4.`type` = ? AND t8.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),3(Integer),123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testElement5(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().orderBy(x->{
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().eq("123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY IFNULL(t1.`code`,?) DESC)) AS `__row__` FROM `doc_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` WHERE t4.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("x(String),3(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testElement6(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().orderBy(x->{
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().eq("123");
                }).orderBy(user -> {
                    user.bankCards().orderBy(x->{
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().asc();
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY IFNULL(t1.`code`,?) DESC)) AS `__row__` FROM `doc_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` WHERE t4.`type` = ? ORDER BY t4.`type` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("x(String),3(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testElement7(){


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
        Assert.assertEquals("SELECT t4.`type` AS `value1`,t4.`code` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY IFNULL(t1.`code`,?) DESC)) AS `__row__` FROM `doc_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` WHERE t4.`type` = ? ORDER BY t4.`type` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("x(String),3(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testElement3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                    .manyJoin(x -> x.bankCards())
                    .select(user -> Select.DRAFT.of(
                            user.bankCards().where(o -> o.type().eq("123")).max(x -> x.code()),
                            user.bankCards().where(o -> o.type().eq("123")).element(0).type()
                    )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`__max2__` AS `value1`,t6.`type` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,MAX((CASE WHEN t1.`type` = ? THEN t1.`code` ELSE ? END)) AS `__max2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` LEFT JOIN (SELECT t4.`id` AS `id`,t4.`uid` AS `uid`,t4.`code` AS `code`,t4.`type` AS `type`,t4.`bank_id` AS `bank_id` FROM (SELECT t3.`id`,t3.`uid`,t3.`code`,t3.`type`,t3.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t3.`uid` ORDER BY 1 = 1)) AS `__row__` FROM `doc_bank_card` t3 WHERE t3.`type` = ?) t4 WHERE t4.`__row__` = ?) t6 ON t6.`uid` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),null(null),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testElement8() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                    .manyJoin(x -> x.bankCards())
                    .select(user -> Select.DRAFT.of(
                            user.bankCards().where(o -> o.type().eq("123")).elements(3,5).max(x -> x.code()),
                            user.bankCards().where(o -> o.type().eq("123")).element(0).type()
                    )).toList();
        } catch (Exception ex) {

        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT MAX(t5.`code`) FROM `doc_bank_card` t5 WHERE t5.`uid` = t.`id` AND t5.`type` = ? LIMIT 3 OFFSET 3) AS `value1`,t4.`type` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY 1 = 1)) AS `__row__` FROM `doc_bank_card` t1 WHERE t1.`type` = ?) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void manyJoinMany2Many2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .manyJoin(x -> x.schoolTeachers())
                .where(s -> {
                    s.schoolTeachers().where(x -> x.name().like("小明")).orderBy(x->{
                        x.name().asc();
                    }).first().name().like("123123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t LEFT JOIN (SELECT t4.`id` AS `id`,t4.`name` AS `name`,t4.`class_id` AS `class_id` FROM (SELECT t2.`class_id` AS `class_id`,t1.`id`,t1.`name`,(ROW_NUMBER() OVER (PARTITION BY t1.`id` ORDER BY t1.`name` ASC)) AS `__row__` FROM `school_teacher` t1 INNER JOIN `school_class_teacher` t2 ON t1.`id` = t2.`teacher_id` WHERE t1.`name` LIKE ?) t4 WHERE t4.`__row__` = ?) t6 ON t6.`class_id` = t.`id` WHERE t6.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),1(Integer),%123123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void manyJoinMany2Many3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User> list = easyEntityQuery.queryable(M8User.class)
                .manyJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.name().like("小明角色")).count().eq(123L);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,COUNT((CASE WHEN t1.`name` LIKE ? THEN ? ELSE ? END)) AS `__count2__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`id` WHERE t4.`__count2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),1(Integer),null(null),123(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void manyJoinMany2Many3_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .manyJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.roleName().like("小明角色")).count().eq(123L);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`user_id`,t.`user_name`,t.`user_age`,t.`create_time` FROM `m8_user2` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,COUNT((CASE WHEN t1.`role_name` LIKE ? THEN ? ELSE ? END)) AS `__count2__` FROM `m8_role2` t1 INNER JOIN `m8_user_role2` t2 ON t1.`role_id` = t2.`role_id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`user_id` WHERE t4.`__count2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),1(Integer),null(null),123(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void manyJoinMany2Many3_2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .manyJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.roleName().like("小明角色")).sum(x->x.roleName().toNumber(Long.class)).eq(123L);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`user_id`,t.`user_name`,t.`user_age`,t.`create_time` FROM `m8_user2` t LEFT JOIN (SELECT t2.`user_id` AS `user_id`,SUM((CASE WHEN t1.`role_name` LIKE ? THEN CAST(t1.`role_name` AS SIGNED) ELSE ? END)) AS `__sum2__` FROM `m8_role2` t1 INNER JOIN `m8_user_role2` t2 ON t1.`role_id` = t2.`role_id` GROUP BY t2.`user_id`) t4 ON t4.`user_id` = t.`user_id` WHERE t4.`__sum2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),0(Integer),123(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void manyJoinMany2Many4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User> list = easyEntityQuery.queryable(M8User.class)
                .manyJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.name().like("小明角色")).orderBy(x->{
                        x.name().asc();
                    }).first().name().eq("123123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age`,t.`create_time` FROM `m8_user` t LEFT JOIN (SELECT t4.`id` AS `id`,t4.`name` AS `name`,t4.`user_id` AS `user_id` FROM (SELECT t2.`user_id` AS `user_id`,t1.`id`,t1.`name`,(ROW_NUMBER() OVER (PARTITION BY t1.`id` ORDER BY t1.`name` ASC)) AS `__row__` FROM `m8_role` t1 INNER JOIN `m8_user_role` t2 ON t1.`id` = t2.`role_id` WHERE t1.`name` LIKE ?) t4 WHERE t4.`__row__` = ?) t6 ON t6.`user_id` = t.`id` WHERE t6.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),1(Integer),123123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void manyJoinMany2Many4_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .manyJoin(x -> x.roles())
                .where(s -> {
                    s.roles().where(x -> x.roleName().like("小明角色")).orderBy(x->{
                        x.roleName().asc();
                    }).first().roleName().eq("123123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`user_id`,t.`user_name`,t.`user_age`,t.`create_time` FROM `m8_user2` t LEFT JOIN (SELECT t4.`role_id` AS `role_id`,t4.`role_name` AS `role_name`,t4.`user_id` AS `user_id` FROM (SELECT t2.`user_id` AS `user_id`,t1.`role_id`,t1.`role_name`,(ROW_NUMBER() OVER (PARTITION BY t1.`role_id` ORDER BY t1.`role_name` ASC)) AS `__row__` FROM `m8_role2` t1 INNER JOIN `m8_user_role2` t2 ON t1.`role_id` = t2.`role_id` WHERE t1.`role_name` LIKE ?) t4 WHERE t4.`__row__` = ?) t6 ON t6.`user_id` = t.`user_id` WHERE t6.`role_name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明角色%(String),1(Integer),123123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
