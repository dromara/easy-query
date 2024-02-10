package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.school.MySchoolClass;
import com.easy.query.test.entity.school.MySchoolStudent;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

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

        List<MySchoolClass> list = easyEntityQuery.queryable(MySchoolClass.class)
                .where(m -> {
                    m.SQLConstant().valueOf(1L).gt(m.schoolStudents().where(x->x.name().like("123")).selectCount());
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `my_school_class` t WHERE ? > (SELECT COUNT(*) FROM `my_school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Long),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
