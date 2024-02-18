package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.school.MySchoolClass;
import com.easy.query.test.entity.school.MySchoolStudent;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2024/2/9 11:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class Relation2Test extends BaseTest {
    @Test
    public void test() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MySchoolStudent> list = easyEntityQuery.queryable(MySchoolStudent.class)
                .where(m -> m.schoolStudentAddress().id().eq("1")).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `my_school_student` t LEFT JOIN `school_student_address` t1 ON t.`id` = t1.`student_id` WHERE t1.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MySchoolStudent> list = easyEntityQuery.queryable(MySchoolStudent.class)
                .where(m -> m.schoolStudentAddress().id().like("1"))
                .orderBy(m -> m.schoolStudentAddress().id().asc()).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `my_school_student` t LEFT JOIN `school_student_address` t1 ON t.`id` = t1.`student_id` WHERE t1.`id` LIKE ? ORDER BY t1.`id` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MySchoolStudent> list = easyEntityQuery.queryable(MySchoolStudent.class)
                .where(m -> {
                    m.schoolStudentAddress().id().like("1");
                    m.schoolClass().name().like("2");
                })
                .orderBy(m -> m.schoolStudentAddress().id().asc()).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `my_school_student` t LEFT JOIN `school_student_address` t1 ON t.`id` = t1.`student_id` LEFT JOIN `my_school_class` t2 ON t.`class_id` = t2.`id` WHERE t1.`id` LIKE ? AND t2.`name` LIKE ? ORDER BY t1.`id` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%1%(String),%2%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MySchoolStudent> list = easyEntityQuery.queryable(MySchoolStudent.class)
                .where(m -> {
                    m.name().like("小明");
                    m.schoolClass().name().eq("一班");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `my_school_student` t LEFT JOIN `my_school_class` t1 ON t.`class_id` = t1.`id` WHERE t.`name` LIKE ? AND t1.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),一班(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
//    @Test
//    public void test4() {
//
//        ListenerContext listenerContext = new ListenerContext();
//        listenerContextManager.startListen(listenerContext);
//
//        List<MySchoolClass> list = easyEntityQuery.queryable(MySchoolClass.class)
//                .where(m -> {
//                    m.SQLConstant().valueOf(1L).gt(m.schoolStudents().where(x->x.name().like("123")).selectCount());
//                }).toList();
//        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `my_school_class` t WHERE ? > (SELECT COUNT(*) FROM `my_school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Long),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//        listenerContextManager.clear();
//    }
    @Test
    public void test5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MySchoolClass> list = easyEntityQuery.queryable(MySchoolClass.class)
                .where(m -> {
                    m.schoolStudents().count(x->x.name().like("123")).gt(1L);
//                    m.id().setSubQuery();
//                    m.schoolStudents().where(x->x.name().like("123")).selectCount().
//                    m.SQLConstant().valueOf(1L).gt(m.schoolStudents().where(x->x.name().like("123")).selectCount());
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `my_school_class` t WHERE (SELECT COUNT(*) FROM `my_school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ?) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test6() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MySchoolClass> list = easyEntityQuery.queryable(MySchoolClass.class)
                .where(m -> {
                    m.schoolStudents().any(x->x.name().like("123"));
//                    m.id().setNavigate();
//                    m.schoolStudents().where(x->x.name().like("123")).selectCount().
//                    m.SQLConstant().valueOf(1L).gt(m.schoolStudents().where(x->x.name().like("123")).selectCount());
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `my_school_class` t WHERE EXISTS (SELECT 1 FROM `my_school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test7() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MySchoolClass> list = easyEntityQuery.queryable(MySchoolClass.class)
                .where(m -> {
                    m.schoolStudents().where(x->x.name().like("1234")).sum(x->x.name().asAny().setPropertyType(BigDecimal.class)).gt(new BigDecimal("1"));
//                    m.id().setSubQuery();
//                    m.schoolStudents().where(x->x.name().like("123")).selectCount().
//                    m.SQLConstant().valueOf(1L).gt(m.schoolStudents().where(x->x.name().like("123")).selectCount());
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `my_school_class` t WHERE IFNULL((SELECT SUM(t1.`name`) FROM `my_school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ?),0) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%1234%(String),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test8() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MySchoolClass> list = easyEntityQuery.queryable(MySchoolClass.class)
                .where(m -> {
                    m.schoolStudents().where(x->x.name().like("1234")).avg(x->x.name().asAny().setPropertyType(BigDecimal.class)).gt(new BigDecimal("1"));
//                    m.id().setSubQuery();
//                    m.schoolStudents().where(x->x.name().like("123")).selectCount().
//                    m.SQLConstant().valueOf(1L).gt(m.schoolStudents().where(x->x.name().like("123")).selectCount());
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `my_school_class` t WHERE IFNULL((SELECT AVG(t1.`name`) FROM `my_school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ?),0) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%1234%(String),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test9() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MySchoolClass> list = easyEntityQuery.queryable(MySchoolClass.class)
                .where(m -> {
                    m.schoolStudents().where(x->x.name().like("1234")).sumBigDecimal(x->x.name().asAny().setPropertyType(BigDecimal.class)).gt(new BigDecimal("1"));
//                    m.id().setSubQuery();
//                    m.schoolStudents().where(x->x.name().like("123")).selectCount().
//                    m.SQLConstant().valueOf(1L).gt(m.schoolStudents().where(x->x.name().like("123")).selectCount());
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `my_school_class` t WHERE IFNULL((SELECT SUM(t1.`name`) FROM `my_school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ?),0) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%1234%(String),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test10() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MySchoolClass> list = easyEntityQuery.queryable(MySchoolClass.class)
                .where(m -> {
                    m.schoolStudents().where(x->x.name().like("1234")).max(x->x.name().asAny().setPropertyType(BigDecimal.class)).gt(new BigDecimal("1"));
//                    m.id().setSubQuery();
//                    m.schoolStudents().where(x->x.name().like("123")).selectCount().
//                    m.SQLConstant().valueOf(1L).gt(m.schoolStudents().where(x->x.name().like("123")).selectCount());
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `my_school_class` t WHERE (SELECT MAX(t1.`name`) FROM `my_school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ?) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%1234%(String),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();



    }
}
