package com.easy.query.test;

import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.autodto.SchoolClassAO;
import com.easy.query.test.dto.autodto.SchoolClassAOProp;
import com.easy.query.test.dto.autodto.SchoolClassAOProp10;
import com.easy.query.test.dto.autodto.SchoolClassAOProp11;
import com.easy.query.test.dto.autodto.SchoolClassAOProp12;
import com.easy.query.test.dto.autodto.SchoolClassAOProp2;
import com.easy.query.test.dto.autodto.SchoolClassAOProp3;
import com.easy.query.test.dto.autodto.SchoolClassAOProp4;
import com.easy.query.test.dto.autodto.SchoolClassAOProp5;
import com.easy.query.test.dto.autodto.SchoolClassAOProp6;
import com.easy.query.test.dto.autodto.SchoolClassAOProp8;
import com.easy.query.test.dto.autodto.SchoolClassAOProp9;
import com.easy.query.test.dto.autodto.SchoolStudentDTOAO111;
import com.easy.query.test.dto.autodto.SchoolStudentDTOxxx;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.base.Area;
import com.easy.query.test.entity.base.City;
import com.easy.query.test.entity.base.Province;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.entity.school.SchoolClassAggregate;
import com.easy.query.test.entity.school.SchoolClassTeacher;
import com.easy.query.test.entity.school.SchoolStudent;
import com.easy.query.test.entity.school.SchoolStudentAddress;
import com.easy.query.test.entity.school.SchoolStudentExtendsVO;
import com.easy.query.test.entity.school.SchoolTeacher;
import com.easy.query.test.entity.school.dto.SchoolClass1VO;
import com.easy.query.test.entity.school.dto.SchoolClassExtendsVO;
import com.easy.query.test.entity.school.dto.SchoolClassOnlyVO;
import com.easy.query.test.entity.school.dto.SchoolClassVO;
import com.easy.query.test.entity.school.dto.SchoolStudentOnlyVO;
import com.easy.query.test.entity.school.dto.SchoolStudentVO;
import com.easy.query.test.entity.school.dto.SchoolTeacherVO;
import com.easy.query.test.entity.school.dto.proxy.SchoolClassVOProxy;
import com.easy.query.test.entity.school.dto.proxy.SchoolStudentVOProxy;
import com.easy.query.test.entity.school.proxy.SchoolStudentProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/7/16 21:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationTest extends BaseTest {

    public void relationInit(List<String> ids) {

        easyQuery.deletable(SchoolStudent.class)
                .whereByIds(ids).executeRows();
        easyQuery.deletable(SchoolStudentAddress.class)
                .where(o -> o.in(SchoolStudentAddress::getStudentId, ids)).executeRows();
        easyQuery.deletable(SchoolClass.class)
                .where(o -> o.in(SchoolClass::getId, Arrays.asList("class1", "class2", "class3"))).executeRows();
        easyQuery.deletable(SchoolTeacher.class)
                .where(o -> o.in(SchoolTeacher::getId, Arrays.asList("teacher1", "teacher2"))).executeRows();
        easyQuery.deletable(SchoolClassTeacher.class)
                .where(o -> o.in(SchoolClassTeacher::getClassId, Arrays.asList("class1", "class2", "class3"))).executeRows();
        easyQuery.deletable(SchoolClassTeacher.class)
                .where(o -> o.in(SchoolClassTeacher::getTeacherId, Arrays.asList("teacher1", "teacher2"))).executeRows();
        ArrayList<SchoolStudent> schoolStudents = new ArrayList<>();
        ArrayList<SchoolClass> schoolClasses = new ArrayList<>();
        ArrayList<SchoolStudentAddress> schoolStudentAddresses = new ArrayList<>();
        ArrayList<SchoolTeacher> schoolTeachers = new ArrayList<>();
        ArrayList<SchoolClassTeacher> schoolClassTeachers = new ArrayList<>();
        int i = 0;
        for (String id : ids) {
            int i1 = i % 2;
            SchoolStudent schoolStudent = new SchoolStudent();
            schoolStudent.setId(id);
            schoolStudent.setClassId("class" + (i1 + 1));
            schoolStudent.setName("学生" + id);
            schoolStudents.add(schoolStudent);
            SchoolStudentAddress schoolStudentAddress = new SchoolStudentAddress();
            schoolStudentAddress.setId("address" + id);
            schoolStudentAddress.setStudentId(id);
            schoolStudentAddress.setAddress("地址" + id);
            schoolStudentAddresses.add(schoolStudentAddress);
            i++;
        }
        {

            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setId("class1");
            schoolClass.setName("班级1");
            schoolClasses.add(schoolClass);
        }
        {

            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setId("class2");
            schoolClass.setName("班级2");
            schoolClasses.add(schoolClass);
        }
        {

            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setId("class3");
            schoolClass.setName("班级3");
            schoolClasses.add(schoolClass);
        }
        {

            SchoolTeacher schoolTeacher = new SchoolTeacher();
            schoolTeacher.setId("teacher1");
            schoolTeacher.setName("老师1");
            schoolTeachers.add(schoolTeacher);
        }
        {

            SchoolTeacher schoolTeacher = new SchoolTeacher();
            schoolTeacher.setId("teacher2");
            schoolTeacher.setName("老师2");
            schoolTeachers.add(schoolTeacher);
        }
        {
            SchoolClassTeacher schoolClassTeacher = new SchoolClassTeacher();
            schoolClassTeacher.setClassId("class1");
            schoolClassTeacher.setTeacherId("teacher1");
            schoolClassTeachers.add(schoolClassTeacher);
        }
        {
            SchoolClassTeacher schoolClassTeacher = new SchoolClassTeacher();
            schoolClassTeacher.setClassId("class1");
            schoolClassTeacher.setTeacherId("teacher2");
            schoolClassTeachers.add(schoolClassTeacher);
        }
        {
            SchoolClassTeacher schoolClassTeacher = new SchoolClassTeacher();
            schoolClassTeacher.setClassId("class2");
            schoolClassTeacher.setTeacherId("teacher2");
            schoolClassTeachers.add(schoolClassTeacher);
        }


        easyQuery.insertable(schoolStudents).executeRows();
        easyQuery.insertable(schoolClasses).executeRows();
        easyQuery.insertable(schoolStudentAddresses).executeRows();
        easyQuery.insertable(schoolTeachers).executeRows();
        easyQuery.insertable(schoolClassTeachers).executeRows();
    }

    public void relationRemove(List<String> ids) {
        easyQuery.deletable(SchoolStudent.class)
                .whereByIds(ids).executeRows();
        easyQuery.deletable(SchoolStudentAddress.class)
                .where(o -> o.in(SchoolStudentAddress::getStudentId, ids)).executeRows();
        easyQuery.deletable(SchoolClass.class)
                .where(o -> o.in(SchoolClass::getId, Arrays.asList("class1", "class2", "class3"))).executeRows();
        easyQuery.deletable(SchoolTeacher.class)
                .where(o -> o.in(SchoolTeacher::getId, Arrays.asList("teacher1", "teacher2"))).executeRows();
        easyQuery.deletable(SchoolClassTeacher.class)
                .where(o -> o.in(SchoolClassTeacher::getClassId, Arrays.asList("class1", "class2", "class3"))).executeRows();
        easyQuery.deletable(SchoolClassTeacher.class)
                .where(o -> o.in(SchoolClassTeacher::getTeacherId, Arrays.asList("teacher1", "teacher2"))).executeRows();
    }

    @Test
    public void testOneToOne() {
        List<String> ids = Arrays.asList("1", "2", "3");
        try {
            relationInit(ids);
            {
                System.out.println("4");
                    ListenerContext listenerContext = new ListenerContext(true);
                    listenerContextManager.startListen(listenerContext);
                    List<SchoolStudentDTOAO111> list = easyEntityQuery.queryable(SchoolStudent.class)
                            .selectAutoInclude(SchoolStudentDTOAO111.class)
                            .toList();

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `address`,`student_id` AS `__relation__studentId` FROM `school_student_address` WHERE `student_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

            }

            {
                List<String> list = easyEntityQuery.queryable(SchoolClass.class)
                        .toList(x -> x.schoolTeachers().flatElement().name());
            }
            {
                System.out.println("4");
                boolean exception = true;
                try {
                    List<SchoolClassAOProp12> list = easyEntityQuery.queryable(SchoolClass.class)
                            .selectAutoInclude(SchoolClassAOProp12.class)
                            .toList();
                    exception = false;

                } catch (Exception ex) {
                    Assert.assertTrue(ex instanceof EasyQueryInvalidOperationException);
                    Assert.assertEquals("@NavigateFlat cannot simultaneously include non-database related objects: [SchoolStudentAO] and its object properties.", ex.getMessage());
                }
                Assert.assertTrue(exception);
                System.out.println("1");

            }

            {
                System.out.println("4");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                int i = 0;
                List<SchoolClassAOProp5> list = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassAOProp5.class)
                        .toList();
                for (SchoolClassAOProp5 schoolClassAOProp5 : list) {
                    for (SchoolClassAOProp5.SchoolStudentAO schoolStudentAO : schoolClassAOProp5.getSchoolTeachersClassList()) {
                        i++;
                    }
                }
                Assert.assertTrue(i>0);

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `id` AS `__relation__id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT t.`id` FROM `school_class` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("1");
            }
            {
                System.out.println("4");
                boolean exception = true;
                try {
                    List<SchoolStudentDTOxxx> list = easyEntityQuery.queryable(SchoolStudent.class)
                            .selectAutoInclude(SchoolStudentDTOxxx.class)
                            .toList();
                    exception = false;

                } catch (Exception ex) {
                    Assert.assertTrue(ex instanceof EasyQueryInvalidOperationException);
                    Assert.assertEquals("In the selectAutoInclude query, the relational propoerty [schoolClass] of the class [SchoolStudentDTOxxx] should appear in both @Navigate and @NavigateFlat.", ex.getMessage());
                }
                Assert.assertTrue(exception);
                System.out.println("1");

            }
            {
                System.out.println("6");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassAOProp9> list = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassAOProp9.class)
                        .toList();
                for (SchoolClassAOProp9 schoolClassAOProp2 : list) {
                    List<String> schoolStudentsIds = schoolClassAOProp2.getSchoolStudentsIds();
                    List<String> schoolTeachersClassId1s = schoolClassAOProp2.getSchoolTeachersClassId1s();
                    List<SchoolClass> schoolTeachersClassList = schoolClassAOProp2.getSchoolTeachersClassList();
                    Assert.assertNull(schoolClassAOProp2.getName1());
                    if (schoolClassAOProp2.getName().equals("班级1")) {
                        Assert.assertTrue(schoolStudentsIds.contains("1"));
                        Assert.assertTrue(schoolStudentsIds.contains("3"));
                        Assert.assertEquals(2, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassId1s.size());
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class1", o.getId()) && Objects.equals("班级1", o.getName())));
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class2", o.getId()) && Objects.equals("班级2", o.getName())));
                        Assert.assertEquals(2, schoolTeachersClassList.size());
                    } else if (schoolClassAOProp2.getName().equals("班级2")) {
                        Assert.assertTrue(schoolStudentsIds.contains("2"));
                        Assert.assertEquals(1, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassId1s.size());
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class1", o.getId()) && Objects.equals("班级1", o.getName())));
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class2", o.getId()) && Objects.equals("班级2", o.getName())));
                        Assert.assertEquals(2, schoolTeachersClassList.size());
                    } else {
                        Assert.assertTrue(schoolStudentsIds.isEmpty());
                    }

                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(6, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t1.`stars` AS `name1`,t.`name`,t.`id` AS `__relation__id` FROM `school_class` t LEFT JOIN `t_topic` t1 ON t1.`id` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id` AS `__relation__classId` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `id` AS `__relation__id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                System.out.println("1");

            }
            {
                System.out.println("2");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassAOProp11> list = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassAOProp11.class)
                        .toList();
                for (SchoolClassAOProp11 schoolClassAOProp2 : list) {
                    List<String> schoolStudentsIds = schoolClassAOProp2.getSchoolStudentsIds();
                    List<String> schoolTeachersClassIds = schoolClassAOProp2.getSchoolTeachersClassIds();
                    List<String> schoolTeachersClassId1s = schoolClassAOProp2.getSchoolTeachersClassId1s();
                    if (schoolClassAOProp2.getName().equals("班级1")) {
                        Assert.assertTrue(schoolStudentsIds.contains("1"));
                        Assert.assertTrue(schoolStudentsIds.contains("3"));
                        Assert.assertEquals(2, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassIds.contains("class1"));
                        Assert.assertTrue(schoolTeachersClassIds.contains("class2"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassIds.size());
                    } else if (schoolClassAOProp2.getName().equals("班级2")) {
                        Assert.assertTrue(schoolStudentsIds.contains("2"));
                        Assert.assertEquals(1, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassIds.contains("class1"));
                        Assert.assertTrue(schoolTeachersClassIds.contains("class2"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassIds.size());
                    } else {
                        Assert.assertTrue(schoolStudentsIds.isEmpty());
                    }

                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(6, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id` AS `__relation__classId` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `id` AS `__relation__id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                    Assert.assertEquals("SELECT `id`,`name` FROM `school_class` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                System.out.println("1");

            }
            {
                System.out.println("4");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassAOProp10> list = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassAOProp10.class)
                        .toList();

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`name`,t1.`address` AS `stu_address`,t.`class_id` AS `__relation__classId` FROM `school_student` t LEFT JOIN `school_student_address` t1 ON t1.`student_id` = t.`id` WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `name`,`id` AS `__relation__id` FROM `school_class` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

            }

            {
                List<SchoolClassVO> list = easyEntityQuery.queryable(SchoolStudent.class)
                        .where(s -> s.name().like("123"))
                        .select(SchoolClassVO.class, x -> x.schoolStudentAddress().FETCHER.allFields())
                        .toList();
            }
            {
                System.out.println("4");
                boolean exception = true;
                try {
                    List<SchoolClassAOProp8> list = easyEntityQuery.queryable(SchoolClass.class)
                            .selectAutoInclude(SchoolClassAOProp8.class)
                            .toList();
                    boolean anyMatch1 = list.stream().anyMatch(o -> o.getSchoolStudents().size() > 0);
                    Assert.assertTrue(anyMatch1);
                    for (SchoolClassAOProp8 schoolClassAOProp8 : list) {
                        if (!schoolClassAOProp8.getSchoolStudents().isEmpty()) {
                            boolean anyMatch = schoolClassAOProp8.getSchoolStudents().stream().anyMatch(o -> o.getClassNames().size() > 0);
                            Assert.assertTrue(anyMatch);
                        }
                        for (SchoolClassAOProp8.SchoolStudentAO schoolStudentAO : schoolClassAOProp8.getSchoolStudents()) {
                            for (String className : schoolStudentAO.getClassNames()) {

                            }
                        }
                    }
                    exception = false;

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    exception = true;
                }
                Assert.assertFalse(exception);
                System.out.println("1");

            }
            {
                boolean exception = true;
                try {

                    List<String> list = easyEntityQuery.queryable(SchoolStudent.class)
                            .toList(x -> x.schoolStudentAddress().address());
                    exception = false;
                } catch (Exception ex) {
                    Assert.assertTrue(ex instanceof EasyQueryInvalidOperationException);
                    Assert.assertEquals(ex.getMessage(), "flatElement result only allowed use in toList");
                }
                Assert.assertTrue(exception);
                System.out.println(1);
            }
            {
                System.out.println("6");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassAOProp6> list = easyEntityQuery.queryable(SchoolClass.class)
                        .leftJoin(Topic.class, (s, t2) -> s.id().eq(t2.id()))
                        .selectAutoInclude(SchoolClassAOProp6.class, (s, t2) -> Select.of(
                                t2.stars().nullOrDefault(1).as(SchoolClassAOProp6::getName1)
                        ))
                        .toList();
                for (SchoolClassAOProp6 schoolClassAOProp2 : list) {
                    List<String> schoolStudentsIds = schoolClassAOProp2.getSchoolStudentsIds();
                    List<String> schoolTeachersClassId1s = schoolClassAOProp2.getSchoolTeachersClassId1s();
                    List<SchoolClass> schoolTeachersClassList = schoolClassAOProp2.getSchoolTeachersClassList();
                    Assert.assertEquals("1", schoolClassAOProp2.getName1());
                    if (schoolClassAOProp2.getName().equals("班级1")) {
                        Assert.assertTrue(schoolStudentsIds.contains("1"));
                        Assert.assertTrue(schoolStudentsIds.contains("3"));
                        Assert.assertEquals(2, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassId1s.size());
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class1", o.getId()) && Objects.equals("班级1", o.getName())));
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class2", o.getId()) && Objects.equals("班级2", o.getName())));
                        Assert.assertEquals(2, schoolTeachersClassList.size());
                    } else if (schoolClassAOProp2.getName().equals("班级2")) {
                        Assert.assertTrue(schoolStudentsIds.contains("2"));
                        Assert.assertEquals(1, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassId1s.size());
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class1", o.getId()) && Objects.equals("班级1", o.getName())));
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class2", o.getId()) && Objects.equals("班级2", o.getName())));
                        Assert.assertEquals(2, schoolTeachersClassList.size());
                    } else {
                        Assert.assertTrue(schoolStudentsIds.isEmpty());
                    }

                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(6, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT IFNULL(t1.`stars`,?) AS `name1`,t.`name`,t.`id` AS `__relation__id` FROM `school_class` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id` AS `__relation__classId` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `id` AS `__relation__id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                System.out.println("1");

            }
            {
                System.out.println("3");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassAOProp4> list = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassAOProp4.class)
                        .toList();
                for (SchoolClassAOProp4 schoolClassAOProp2 : list) {
                    List<String> schoolStudentsIds = schoolClassAOProp2.getSchoolStudentsIds();
                    List<String> schoolTeachersClassIds = schoolClassAOProp2.getSchoolTeachersClassIds();
                    List<String> schoolTeachersClassId1s = schoolClassAOProp2.getSchoolTeachersClassId1s();
                    List<SchoolClass> schoolTeachersClassList = schoolClassAOProp2.getSchoolTeachersClassList();
                    if (schoolClassAOProp2.getName().equals("班级1")) {
                        Assert.assertTrue(schoolStudentsIds.contains("1"));
                        Assert.assertTrue(schoolStudentsIds.contains("3"));
                        Assert.assertEquals(2, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassIds.contains("class1"));
                        Assert.assertTrue(schoolTeachersClassIds.contains("class2"));
                        Assert.assertEquals(2, schoolTeachersClassIds.size());
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassId1s.size());
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class1", o.getId()) && Objects.equals("班级1", o.getName())));
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class2", o.getId()) && Objects.equals("班级2", o.getName())));
                        Assert.assertEquals(2, schoolTeachersClassList.size());
                    } else if (schoolClassAOProp2.getName().equals("班级2")) {
                        Assert.assertTrue(schoolStudentsIds.contains("2"));
                        Assert.assertEquals(1, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassIds.contains("class1"));
                        Assert.assertTrue(schoolTeachersClassIds.contains("class2"));
                        Assert.assertEquals(2, schoolTeachersClassIds.size());
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassId1s.size());
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class1", o.getId()) && Objects.equals("班级1", o.getName())));
                        Assert.assertTrue(schoolTeachersClassList.stream().anyMatch(o -> Objects.equals("class2", o.getId()) && Objects.equals("班级2", o.getName())));
                        Assert.assertEquals(2, schoolTeachersClassList.size());
                    } else {
                        Assert.assertTrue(schoolStudentsIds.isEmpty());
                    }

                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(6, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id` AS `__relation__classId` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `id` AS `__relation__id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                System.out.println("1");

            }
            {
                System.out.println("2");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassAOProp3> list = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassAOProp3.class)
                        .toList();
                for (SchoolClassAOProp3 schoolClassAOProp2 : list) {
                    List<String> schoolStudentsIds = schoolClassAOProp2.getSchoolStudentsIds();
                    List<String> schoolTeachersClassIds = schoolClassAOProp2.getSchoolTeachersClassIds();
                    List<String> schoolTeachersClassId1s = schoolClassAOProp2.getSchoolTeachersClassId1s();
                    if (schoolClassAOProp2.getName().equals("班级1")) {
                        Assert.assertTrue(schoolStudentsIds.contains("1"));
                        Assert.assertTrue(schoolStudentsIds.contains("3"));
                        Assert.assertEquals(2, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassIds.contains("class1"));
                        Assert.assertTrue(schoolTeachersClassIds.contains("class2"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassIds.size());
                    } else if (schoolClassAOProp2.getName().equals("班级2")) {
                        Assert.assertTrue(schoolStudentsIds.contains("2"));
                        Assert.assertEquals(1, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassIds.contains("class1"));
                        Assert.assertTrue(schoolTeachersClassIds.contains("class2"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级1"));
                        Assert.assertTrue(schoolTeachersClassId1s.contains("班级2"));
                        Assert.assertEquals(2, schoolTeachersClassIds.size());
                    } else {
                        Assert.assertTrue(schoolStudentsIds.isEmpty());
                    }

                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(6, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id` AS `__relation__classId` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `id` AS `__relation__id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                    Assert.assertEquals("SELECT `id`,`name` FROM `school_class` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                System.out.println("1");

            }
            {
                System.out.println("1");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassAOProp2> list = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassAOProp2.class)
                        .toList();
                for (SchoolClassAOProp2 schoolClassAOProp2 : list) {
                    List<String> schoolStudentsIds = schoolClassAOProp2.getSchoolStudentsIds();
                    List<String> schoolTeachersClassIds = schoolClassAOProp2.getSchoolTeachersClassIds();
                    if (schoolClassAOProp2.getName().equals("班级1")) {
                        Assert.assertTrue(schoolStudentsIds.contains("1"));
                        Assert.assertTrue(schoolStudentsIds.contains("3"));
                        Assert.assertEquals(2, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassIds.contains("class1"));
                        Assert.assertTrue(schoolTeachersClassIds.contains("class2"));
                        Assert.assertEquals(2, schoolTeachersClassIds.size());
                    } else if (schoolClassAOProp2.getName().equals("班级2")) {
                        Assert.assertTrue(schoolStudentsIds.contains("2"));
                        Assert.assertEquals(1, schoolStudentsIds.size());
                        Assert.assertTrue(schoolTeachersClassIds.contains("class1"));
                        Assert.assertTrue(schoolTeachersClassIds.contains("class2"));
                        Assert.assertEquals(2, schoolTeachersClassIds.size());
                    } else {
                        Assert.assertTrue(schoolStudentsIds.isEmpty());
                    }

                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(6, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id` AS `__relation__classId` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `id` AS `__relation__id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                    Assert.assertEquals("SELECT `id` FROM `school_class` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                System.out.println("1");

            }
            {
                System.out.println("1");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassAOProp> list = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassAOProp.class)
                        .toList();
                for (SchoolClassAOProp schoolClassAOProp : list) {
                    List<String> schoolStudentsIds = schoolClassAOProp.getSchoolStudentsIds();
                    if (schoolClassAOProp.getName().equals("班级1")) {
                        Assert.assertTrue(schoolStudentsIds.contains("1"));
                        Assert.assertTrue(schoolStudentsIds.contains("3"));
                        Assert.assertEquals(2, schoolStudentsIds.size());
                    } else if (schoolClassAOProp.getName().equals("班级2")) {
                        Assert.assertTrue(schoolStudentsIds.contains("2"));
                        Assert.assertEquals(1, schoolStudentsIds.size());
                    } else {
                        Assert.assertTrue(schoolStudentsIds.isEmpty());
                    }
                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id` AS `__relation__classId` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                System.out.println("1");

            }
            {


                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())

                List<SchoolStudentAddress> list = easyEntityQuery.queryable(SchoolClass.class)
                        .toList(x -> x.schoolStudents().flatElement().schoolStudentAddress().schoolStudent().schoolStudentAddress());
                for (SchoolStudentAddress schoolStudentAddress : list) {

                }
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT `id` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id` AS `__relation__classId` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `id`,`student_id` AS `__relation__studentId` FROM `school_student_address` WHERE `student_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `id` AS `__relation__id` FROM `school_student` WHERE `id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `id`,`student_id`,`address` FROM `school_student_address` WHERE `student_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
            }
            {


                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())

                List<SchoolStudentAddress> list = easyEntityQuery.queryable(SchoolClass.class)
                        .toList(x -> x.schoolStudents().flatElement().schoolStudentAddress());
                for (SchoolStudentAddress schoolStudentAddress : list) {

                }
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT `id` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id` AS `__relation__classId` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `id`,`student_id`,`address` FROM `school_student_address` WHERE `student_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
            }
//            {
//
//
//                ListenerContext listenerContext = new ListenerContext(true);
//                listenerContextManager.startListen(listenerContext);
//                System.out.println("------------------");
////                easyQueryClient.queryable(SchoolClass.class)
////                        .where(s -> s.sqlNativeSegment())
//
//                List<SchoolClass> list1 = easyEntityQuery.queryable(SchoolClass.class)
//                        .toList(x -> x.schoolTeachers().flatElement().schoolClasses().flatElement(c->c.FETCHER.name()));
//                System.out.println("------------------");
//                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
//                Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
//
//                {
//
//                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT `id` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                }
//                {
//                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//                }
//                {
//                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
//                    Assert.assertEquals("SELECT `id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//                }
//                {
//                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
//                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//                }
//                {
//                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
//                    Assert.assertEquals("SELECT `id`,`name` FROM `school_class` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//                }
//            }
            System.out.println("||||||||||||||||||||||||||");

            {


                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())

                List<SchoolClass> list1 = easyEntityQuery.queryable(SchoolClass.class)
                        .toList(x -> x.schoolTeachers().flatElement().schoolClasses().flatElement(z -> z.FETCHER.name().id().name()));
                for (SchoolClass s : list1) {
                    System.out.println(s);
                }
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT `id` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `name`,`id`,`name` FROM `school_class` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
            }
            {


                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())

                List<String> list1 = easyEntityQuery.queryable(SchoolClass.class)
                        .toList(x -> x.schoolTeachers().flatElement().schoolClasses().flatElement().name());
                for (String s : list1) {
                    System.out.println(s);
                }
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT `id` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `name`,`id` AS `__relation__id` FROM `school_class` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
            }
            {


                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())

                List<SchoolClass> list1 = easyEntityQuery.queryable(SchoolClass.class)
                        .toList(x -> x.schoolTeachers().flatElement().schoolClasses().flatElement());
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT `id` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `id` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `teacher_id`,`class_id` FROM `school_class_teacher` WHERE `teacher_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `id`,`name` FROM `school_class` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
            }
            {


                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())

                List<SchoolTeacher> list = easyEntityQuery.queryable(SchoolClass.class).toList(x -> x.schoolTeachers().flatElement());
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT `id` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT `id`,`name` FROM `school_teacher` WHERE `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
//                System.out.println("123");
//                List<String> list1 = easyEntityQuery.queryable(SchoolClass.class).toList(x -> x.schoolTeachers().flatElement().id());
//                System.out.println("123");
            }

            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);

                List<Draft1<Long>> list5 = easyEntityQuery.queryable(SchoolClass.class)
                        .where(s -> s.name().like("123"))
                        .select(s -> Select.DRAFT.of(
                                s.schoolStudents().where(st -> st.name().like("456")).count()
                        )).toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(1, listenerContext.getJdbcExecuteAfterArgs().size());

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT (SELECT COUNT(*) FROM `school_student` t2 WHERE t2.`class_id` = t.`id` AND t2.`name` LIKE ?) AS `value1` FROM `school_class` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("%456%(String),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

            }

            List<SchoolStudent> list4 = easyEntityQuery.queryable(SchoolStudent.class).toList();
            easyEntityQuery.loadInclude(list4, x -> x.schoolClass());
            for (SchoolStudent schoolStudent : list4) {
                Assert.assertNotNull(schoolStudent.getSchoolClass());
            }
//            {
//
//                List<String> list1 = easyEntityQuery.queryable(SchoolStudent.class)
//                        .select(s -> s.name2()).toList();
//
//                ListenerContext listenerContext = new ListenerContext(true);
//                listenerContextManager.startListen(listenerContext);
//
//                List<SchoolTeacher> list = easyEntityQuery.queryable(SchoolStudent.class)
//                        .select(s ->  s.schoolClass().schoolTeachers().toList())
//                        .firstNotNull();
//
////                List<SchoolTeacher> list1 = easyEntityQuery.queryable(SchoolStudent.class)
////                        .select(s -> s.schoolClass().schoolTeachers().toList2()).toList();
//                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
//                Assert.assertEquals(3,listenerContext.getJdbcExecuteAfterArgs().size());
//            }
            {
//                easyQueryClient.queryable(SchoolClass.class)
//                        .include(o-> o.with("schoolStudents"))

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())
                List<SchoolClass> schoolStudents = easyEntityQuery.queryable(SchoolClass.class).toList();

                easyEntityQuery.loadInclude(schoolStudents, x -> x.schoolStudents());
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT `id`,`name` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());


                }
                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id`,`name` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                }
                for (SchoolClass schoolStudent : schoolStudents) {
                    if (Objects.equals(schoolStudent.getId(), "class1")) {
                        Assert.assertEquals(2, schoolStudent.getSchoolStudents().size());
                    }
                    if (Objects.equals(schoolStudent.getId(), "class2")) {
                        Assert.assertEquals(1, schoolStudent.getSchoolStudents().size());
                    }
                    if (Objects.equals(schoolStudent.getId(), "class3")) {
                        Assert.assertEquals(0, schoolStudent.getSchoolStudents().size());
                    }
                }


//                Assert.assertEquals("1","2");
                List<SchoolClass> list1 = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents())
                        .toList();
                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents(), y -> y.where(x -> x.name().like("123")))
                        .toList();
                for (SchoolClass schoolClass : list1) {
                    Assert.assertNotNull(schoolClass.getSchoolStudents());
                    Assert.assertTrue(schoolClass.getSchoolStudents().size() >= 0);
                }
            }
            {
//                easyQueryClient.queryable(SchoolClass.class)
//                        .include(o-> o.with("schoolStudents"))

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())

                List<SchoolClass> schoolStudents = easyQueryClient.queryable(SchoolClass.class)
                        .toList();
                easyQueryClient.loadInclude(schoolStudents, "schoolStudents");
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT `id`,`name` FROM `school_class`", jdbcExecuteAfterArg.getBeforeArg().getSql());


                }
                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT `id`,`class_id`,`name` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                }


//                Assert.assertEquals("1","2");
                List<SchoolClass> list1 = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents())
                        .toList();
                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents(), y -> y.where(x -> x.name().like("123")))
                        .toList();
                for (SchoolClass schoolClass : list1) {
                    Assert.assertNotNull(schoolClass.getSchoolStudents());
                    Assert.assertTrue(schoolClass.getSchoolStudents().size() >= 0);
                }
            }

            {
//                easyQueryClient.queryable(SchoolClass.class)
//                        .include(o-> o.with("schoolStudents"))

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())

                List<SchoolClassAO> list = easyQueryClient.queryable(SchoolClass.class)
                        .include(s -> s.with("schoolStudents"))
                        .selectAutoInclude(SchoolClassAO.class)
                        .toList();
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());


                }
                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`name`,t.`class_id` AS `__relation__classId` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                }


//                Assert.assertEquals("1","2");
                List<SchoolClass> list1 = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents())
                        .toList();
                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents(), y -> y.where(x -> x.name().like("123")))
                        .toList();
                for (SchoolClass schoolClass : list1) {
                    Assert.assertNotNull(schoolClass.getSchoolStudents());
                    Assert.assertTrue(schoolClass.getSchoolStudents().size() >= 0);
                }
            }

            {
//                easyQueryClient.queryable(SchoolClass.class)
//                        .include(o-> o.with("schoolStudents"))

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())

                List<SchoolClassAO> list = easyQueryClient.queryable(SchoolClass.class)
                        .include(s -> s.with("schoolStudents"))
                        .selectAutoInclude(SchoolClassAO.class)
                        .toList();
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());


                }
                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`name`,t.`class_id` AS `__relation__classId` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                }


//                Assert.assertEquals("1","2");
                List<SchoolClass> list1 = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents())
                        .toList();
                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents(), y -> y.where(x -> x.name().like("123")))
                        .toList();
                for (SchoolClass schoolClass : list1) {
                    Assert.assertNotNull(schoolClass.getSchoolStudents());
                    Assert.assertTrue(schoolClass.getSchoolStudents().size() >= 0);
                }
            }
//            {
//
//                ListenerContext listenerContext = new ListenerContext(true);
//                listenerContextManager.startListen(listenerContext);
//                System.out.println("------------------");
////                easyQueryClient.queryable(SchoolClass.class)
////                        .where(s -> s.sqlNativeSegment())
//                List<SchoolClassAggregate> list = easyEntityQuery.queryable(SchoolClassAggregate.class)
//                        .where(s -> s.studentSize().gt(0L))
//                        .orderBy(s -> s.studentSize().asc())
//                        .toList();
//                System.out.println("------------------");
//                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                Assert.assertEquals("SELECT `id`,`name`,(SELECT COUNT(t1.`id`) AS `id` FROM `school_student` t1 WHERE t1.`class_id` = `id`) AS `student_size` FROM `school_class` WHERE (SELECT COUNT(t3.`id`) AS `id` FROM `school_student` t3 WHERE t3.`class_id` = `id`) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            }
            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                easyQueryClient.queryable(SchoolClass.class)
//                        .where(s -> s.sqlNativeSegment())
                List<SchoolClassAggregate> list = easyEntityQuery.queryable(SchoolClassAggregate.class)
                        .where(s -> s.studentSize().gt(0L))
                        .toList();
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT t.`id`,t.`name`,(SELECT COUNT(t1.`id`) AS `id` FROM `school_student` t1 WHERE t1.`class_id` = t.`id`) AS `student_size` FROM `school_class` t WHERE (SELECT COUNT(t3.`id`) AS `id` FROM `school_student` t3 WHERE t3.`class_id` = t.`id`) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            }
            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
                List<SchoolClassAggregate> list = easyEntityQuery.queryable(SchoolClassAggregate.class)
                        .toList();
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT t.`id`,t.`name`,(SELECT COUNT(t1.`id`) AS `id` FROM `school_student` t1 WHERE t1.`class_id` = t.`id`) AS `student_size` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            }
            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
//                List<SchoolClass1VO> listx= easyEntityQuery.queryable(SchoolClass.class)
//                        .includes(s -> s.schoolStudents(),schoolStudentQuery->{
//                            schoolStudentQuery.where(st->{
//                                st.schoolStudentAddress().address().like("北京");
//                            }).any();
//                        })
//                        .selectAutoInclude(SchoolClass1VO.class,false)
//                        .toList();
                List<SchoolClass1VO> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(s -> s.schoolStudents(), schoolStudentQuery -> {
                            schoolStudentQuery.where(st -> st.name().like("123")).limit(2);
                        })
                        .selectAutoInclude(SchoolClass1VO.class)
                        .toList();
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(listenerContext.getJdbcExecuteAfterArgs().size(), 2);
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t1.`id`,t1.`class_id`,t1.`name` FROM ( (SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`name` LIKE ? AND t.`class_id` = ? LIMIT 2)  UNION ALL  (SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`name` LIKE ? AND t.`class_id` = ? LIMIT 2)  UNION ALL  (SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`name` LIKE ? AND t.`class_id` = ? LIMIT 2) ) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("%123%(String),class1(String),%123%(String),class2(String),%123%(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                for (SchoolClass1VO schoolClassVO : listx) {
                    Assert.assertNotNull(schoolClassVO.getSchoolStudents());
                    for (SchoolStudentVO schoolStudent : schoolClassVO.getSchoolStudents()) {
                        Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                    }
                }
            }
            {

//                ListenerContext listenerContext = new ListenerContext(true);
//                listenerContextManager.startListen(listenerContext);
//
//                List<SchoolClassTeachIdsVO> list = easyEntityQuery.queryable(SchoolClass.class)
////                        .includes(s -> s.schoolTeachers(),x->x.selectColumn(y->y.id()))
//                        .selectAutoInclude(SchoolClassTeachIdsVO.class)
//                        .toList();
//                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
//                Assert.assertEquals(3,listenerContext.getJdbcExecuteAfterArgs().size());
            }
            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
                List<SchoolClassVO> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassVO.class)
                        .toList();
                System.out.println("------------------");
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(listenerContext.getJdbcExecuteAfterArgs().size(), 9);
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_teacher` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                    Assert.assertEquals("SELECT t.`id`,t.`student_id`,t.`address` FROM `school_student_address` t WHERE t.`student_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(6);
                    Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(7);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(8);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_teacher` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                for (SchoolClassVO schoolClassVO : listx) {
                    Assert.assertNotNull(schoolClassVO.getSchoolStudents());
                    Assert.assertNotNull(schoolClassVO.getSchoolTeachers());
                    for (SchoolStudentVO schoolStudent : schoolClassVO.getSchoolStudents()) {
                        Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                    }
                }
            }
            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassVO> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(s -> s.schoolStudents())
                        .select(s -> new SchoolClassVOProxy().adapter(r -> {
                            r.schoolStudents().set(s.schoolStudents()
                                    , x -> {
                                        SchoolStudentVOProxy schoolStudentVOProxy = new SchoolStudentVOProxy();
                                        schoolStudentVOProxy.selectAll(x);
                                        return schoolStudentVOProxy;
                                    }
                            );
                        }))
                        .toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }
            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassVO> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(s -> s.schoolStudents())
                        .select(SchoolClassVO.class)
                        .toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
                for (SchoolClassVO schoolClassVO : listx) {
                    Assert.assertNotNull(schoolClassVO.getSchoolStudents());
                }
            }
            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassVO> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(s -> s.schoolStudents())
                        .select(SchoolClassVO.class)
                        .toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
                for (SchoolClassVO schoolClassVO : listx) {
                    Assert.assertNotNull(schoolClassVO.getSchoolStudents());
                }
            }
            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
                List<SchoolClassVO> listx = easyEntityQuery.queryable(SchoolClass.class)
                        //返回班级下的所有学生 班级和学生是一对多
                        .includes(s -> s.schoolStudents(), x -> {
                            //返回学生下的所有学生地址 学生和学生地址是一对一
                            x.include(y -> y.schoolStudentAddress());
                        })
                        //返回班级下面的所有老师 老师和班级多对多
                        .includes(s -> s.schoolTeachers())
                        .select(SchoolClassVO.class)
                        .toList();
                System.out.println("------------------");


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(listenerContext.getJdbcExecuteAfterArgs().size(), 5);
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT t.`id`,t.`student_id`,t.`address` FROM `school_student_address` t WHERE t.`student_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_teacher` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());


//==> Preparing: SELECT t.`id`,t.`name` FROM `school_class` t
//                    <== Time Elapsed: 4(ms)
//                    <== Total: 3
//                    ==> Preparing: SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t LEFT JOIN `school_student_address` t1 ON t.`id` = t1.`student_id` WHERE t.`class_id` IN (?,?,?)
//==> Parameters: class1(String),class2(String),class3(String)
//                    <== Time Elapsed: 7(ms)
//                    <== Total: 3
//                    ==> Preparing: SELECT t.`id`,t.`student_id`,t.`address` FROM `school_student_address` t WHERE t.`student_id` IN (?,?,?)
//==> Parameters: 1(String),2(String),3(String)
//                    <== Time Elapsed: 5(ms)
//                    <== Total: 3
//                    ==> Preparing: SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)
//==> Parameters: class1(String),class2(String),class3(String)
//                    <== Time Elapsed: 6(ms)
//                    <== Total: 3
//                    ==> Preparing: SELECT t.`id`,t.`name` FROM `school_teacher` t WHERE t.`id` IN (?,?)
//==> Parameters: teacher1(String),teacher2(String)
//                    <== Time Elapsed: 14(ms)
//                    <== Total: 2
                listenerContextManager.clear();
                for (SchoolClassVO schoolClassVO : listx) {
                    Assert.assertNotNull(schoolClassVO.getSchoolStudents());
                    Assert.assertNotNull(schoolClassVO.getSchoolTeachers());
                    for (SchoolStudentVO schoolStudent : schoolClassVO.getSchoolStudents()) {
                        Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                    }
                }
            }
            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
                List<SchoolClassOnlyVO> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .selectAutoInclude(SchoolClassOnlyVO.class)
                        .toList();
                System.out.println("------------------");


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(listenerContext.getJdbcExecuteAfterArgs().size(), 6);
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT t.`id`,t.`student_id`,t.`address` FROM `school_student_address` t WHERE t.`student_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(5);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_teacher` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());

                listenerContextManager.clear();
                for (SchoolClassOnlyVO schoolClassVO : listx) {
                    Assert.assertNotNull(schoolClassVO.getSchoolStudents());
                    Assert.assertNotNull(schoolClassVO.getSchoolTeachers());
                    for (SchoolStudentOnlyVO schoolStudent : schoolClassVO.getSchoolStudents()) {
                        Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                    }
                }
            }

            {

                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                System.out.println("------------------");
                List<SchoolClassOnlyVO> listx = easyEntityQuery.queryable(SchoolClass.class)
                        //返回班级下的所有学生 班级和学生是一对多
                        .includes(s -> s.schoolStudents(), x -> {
                            //返回学生下的所有学生地址 学生和学生地址是一对一
                            x.include(y -> y.schoolStudentAddress());
                        })
                        //返回班级下面的所有老师 老师和班级多对多
                        .includes(s -> s.schoolTeachers())
                        .select(SchoolClassOnlyVO.class)
                        .toList();
                System.out.println("------------------");


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(listenerContext.getJdbcExecuteAfterArgs().size(), 5);
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT t.`id`,t.`student_id`,t.`address` FROM `school_student_address` t WHERE t.`student_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT `class_id`,`teacher_id` FROM `school_class_teacher` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_teacher` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("teacher1(String),teacher2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
                listenerContextManager.clear();
                for (SchoolClassOnlyVO schoolClassVO : listx) {
                    Assert.assertNotNull(schoolClassVO.getSchoolStudents());
                    Assert.assertNotNull(schoolClassVO.getSchoolTeachers());
                    for (SchoolStudentOnlyVO schoolStudent : schoolClassVO.getSchoolStudents()) {
                        Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                    }
                }
            }
            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);
                List<SchoolClassVO> listx = easyEntityQuery.queryable(SchoolClass.class).includes(s -> s.schoolStudents())
                        .select(s -> new SchoolClassVOProxy().adapter(r -> {
                            r.schoolStudents().set(s.schoolStudents()

                                    , x -> {
                                        SchoolStudentVOProxy schoolStudentVOProxy = new SchoolStudentVOProxy();
                                        schoolStudentVOProxy.selectAll(x).selectIgnores(x.name());
                                        return schoolStudentVOProxy;
                                    }
                            );
                        }))
                        .toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t.`id`,t.`class_id` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }

            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);
                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolStudent.class)
                        .select(s -> s.schoolClass())
                        .toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t1.`id`,t1.`name` FROM `school_student` t LEFT JOIN `school_class` t1 ON t1.`id` = t.`class_id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }
            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);
                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(s -> s.schoolStudents())
                        .fetchBy(s -> s.FETCHER.name())
                        .toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT `id`,`class_id`,`name` FROM `school_student` WHERE `class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
                String sql1 = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(s -> s.schoolStudents())
                        .fetchBy(s -> s.FETCHER.name()).toSQL();
                Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `school_class` t", sql1);
            }
            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents(), x -> x.limit(2))
                        .toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t1.`id`,t1.`class_id`,t1.`name` FROM ( (SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` = ? LIMIT 2)  UNION ALL  (SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` = ? LIMIT 2)  UNION ALL  (SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` = ? LIMIT 2) ) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }
            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents(), x -> x.where(y -> y.name().like("123")))
                        .toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`name` LIKE ? AND t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("%123%(String),class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }
            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents(), x -> x.limit(2), 1)
                        .toList();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t WHERE t.`class_id` IN (?) LIMIT 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }
            {
                List<SchoolStudent> list = easyQuery.queryable(SchoolStudent.class).toList();
                Assert.assertEquals(3, list.size());
                for (SchoolStudent schoolStudent : list) {
                    Assert.assertNull(schoolStudent.getSchoolStudentAddress());
                }
            }
            {
                List<SchoolStudent> list = easyEntityQuery.queryable(SchoolStudent.class).toList();
                Assert.assertEquals(3, list.size());
                for (SchoolStudent schoolStudent : list) {
                    Assert.assertNull(schoolStudent.getSchoolStudentAddress());
                }
            }
            {

                SchoolStudentProxy table = SchoolStudentProxy.createTable();
                List<SchoolStudent> listp = easyProxyQuery.queryable(table).toList();
                Assert.assertEquals(3, listp.size());
                for (SchoolStudent schoolStudent : listp) {
                    Assert.assertNull(schoolStudent.getSchoolStudentAddress());
                }
            }


            {
                List<SchoolStudent> list1 = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolClass, 1))
                        .toList();
                for (SchoolStudent schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(), schoolStudent.getSchoolClass().getId());
                }
            }
            {
                List<SchoolStudent> list1 = easyEntityQuery.queryable(SchoolStudent.class)
                        .include(t -> t.schoolClass())
                        .toList();
                for (SchoolStudent schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(), schoolStudent.getSchoolClass().getId());
                }
//                easyEntityQuery.queryable(SchoolStudent.class)
//                        .where(o->{
//                            o.schoolClass().id().eq()
//                            o.schoolClasses(x->x.id().eq("xx"))

//                        })
            }
            {
                //todo alias
                List<SchoolStudentVO> list1 = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolClass))
                        .select(SchoolStudentVO.class, o -> o
                                .columnAll()
                                .columnInclude(SchoolStudent::getSchoolClass, SchoolStudentVO::getSchoolClass)
                        )
                        .toList();
                for (SchoolStudentVO schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(), schoolStudent.getSchoolClass().getId());
                    Assert.assertNotNull(schoolStudent.getSchoolClass().getName());
                }
            }
            {
                //todo alias
                List<SchoolStudentExtendsVO> list1 = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolClass))
                        .select(SchoolStudentExtendsVO.class, o -> o
                                .columnAll()
                                .columnInclude(SchoolStudent::getSchoolClass, SchoolStudentExtendsVO::getSchoolClass)
                        )
                        .toList();
                for (SchoolStudentExtendsVO schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(), schoolStudent.getSchoolClass().getId());
                    Assert.assertNotNull(schoolStudent.getSchoolClass().getName());
                }
            }
            {
                //todo alias
                List<SchoolStudentVO> list1 = easyEntityQuery.queryable(SchoolStudent.class)
                        .include(o -> o.schoolClass())
                        .select(o -> new SchoolStudentVOProxy().adapter(r -> {
                            r.selectAll(o);
                            r.schoolClass().set(o.schoolClass());
                        }))
                        .toList();
                for (SchoolStudentVO schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(), schoolStudent.getSchoolClass().getId());
                    Assert.assertNotNull(schoolStudent.getSchoolClass().getName());
                }
            }
            {
                List<SchoolStudent> list2 = easyEntityQuery.queryable(SchoolStudent.class)
                        .include(o -> o.schoolClass(), q -> q.asNoTracking().disableLogicDelete())
                        .toList();
                List<SchoolStudent> list3 = easyEntityQuery.queryable(SchoolStudent.class)
                        .include(o -> o.schoolClass(), 20)
                        .toList();
                //todo alias
                List<SchoolStudentVO> list1 = easyEntityQuery.queryable(SchoolStudent.class)
                        .include(o -> o.schoolClass())
                        .select(o -> new SchoolStudentVOProxy().adapter(r -> {
                            r.selectAll(o);
//                            r.schoolClass().setNavigate(o.schoolClass());
                            r.schoolClass().set(o.schoolClass());
                        }))
                        .toList();
                for (SchoolStudentVO schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(), schoolStudent.getSchoolClass().getId());
                    Assert.assertNotNull(schoolStudent.getSchoolClass().getName());
                }
            }
            {
                //todo alias
                List<SchoolStudentVO> list1 = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolClass, 1))
                        .select(SchoolStudentVO.class, o -> o
                                .columnAll()
                                .columnInclude(SchoolStudent::getSchoolClass, SchoolStudentVO::getSchoolClass)
                        )
                        .toList();
                for (SchoolStudentVO schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(), schoolStudent.getSchoolClass().getId());
                    Assert.assertNotNull(schoolStudent.getSchoolClass().getName());
                }
            }
            {
                //todo alias
                List<SchoolStudentVO> list1 = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolClass))
                        .select(SchoolStudentVO.class, o -> o
                                .columnAll()
                                .columnInclude(SchoolStudent::getSchoolClass, SchoolStudentVO::getSchoolClass, s -> s.column(SchoolClassVO::getId))
                        )
                        .toList();
                for (SchoolStudentVO schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(), schoolStudent.getSchoolClass().getId());
                    Assert.assertNull(schoolStudent.getSchoolClass().getName());
                }
            }
            {
                List<SchoolStudent> list1 = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolStudentAddress).asTracking().disableLogicDelete())
                        .toList();
                for (SchoolStudent schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                }
            }
            {
                //todo alias
                List<SchoolStudentVO> list1 = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolStudentAddress).asTracking().disableLogicDelete())
                        .select(SchoolStudentVO.class, o -> o.columnAll()
                                .columnInclude(SchoolStudent::getSchoolStudentAddress, SchoolStudentVO::getSchoolStudentAddress))
                        .toList();
                for (SchoolStudentVO schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                }
            }
            {
                //todo alias
                List<SchoolStudentVO> list1 = easyEntityQuery.queryable(SchoolStudent.class)
                        .include(s -> s.schoolStudentAddress(), x -> x.asNoTracking().disableLogicDelete())
                        .select(s -> new SchoolStudentVOProxy().adapter(r -> {
                            r.selectAll(s);
                            r.schoolStudentAddress().set(s.schoolStudentAddress());
                        })).toList();
                for (SchoolStudentVO schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                }
            }
            {
//                easyQueryClient.queryable(SchoolClass.class)
//                        .include(o-> o.with("schoolStudents"))
                List<SchoolClass> list1 = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents())
                        .toList();
                List<SchoolClass> listx = easyEntityQuery.queryable(SchoolClass.class)
                        .includes(o -> o.schoolStudents(), y -> y.where(x -> x.name().like("123")))
                        .toList();
                for (SchoolClass schoolClass : list1) {
                    Assert.assertNotNull(schoolClass.getSchoolStudents());
                    Assert.assertTrue(schoolClass.getSchoolStudents().size() >= 0);
                }
            }

            {
//                easyQueryClient.queryable(SchoolClass.class)
//                        .include(o-> o.with("schoolStudents"))
                List<SchoolClass> list1 = easyQuery.queryable(SchoolClass.class)
                        .include(o -> o.many(SchoolClass::getSchoolStudents, 1))
                        .toList();
                for (SchoolClass schoolClass : list1) {
                    Assert.assertNotNull(schoolClass.getSchoolStudents());
                    Assert.assertTrue(schoolClass.getSchoolStudents().size() >= 0);
                }
            }
            {
                //todo alias
                List<SchoolClassVO> list1 = easyQuery.queryable(SchoolClass.class)
                        .include(o -> o.many(SchoolClass::getSchoolStudents))
                        .select(SchoolClassVO.class, o -> o.columnAll()
                                .columnIncludeMany(SchoolClass::getSchoolStudents, SchoolClassVO::getSchoolStudents))
                        .toList();
                for (SchoolClassVO schoolClass : list1) {
                    Assert.assertNotNull(schoolClass.getSchoolStudents());
                    Assert.assertTrue(schoolClass.getSchoolStudents().size() >= 0);
                }
            }
            {
                //todo alias
                List<SchoolClassExtendsVO> list1 = easyQuery.queryable(SchoolClass.class)
                        .include(o -> o.many(SchoolClass::getSchoolStudents).where(x -> x.isNotNull(SchoolStudent::getId)))
                        .select(SchoolClassExtendsVO.class, o -> o.columnAll()
                                .columnIncludeMany(SchoolClass::getSchoolStudents, SchoolClassExtendsVO::getSchoolStudents))
                        .toList();
                for (SchoolClassExtendsVO schoolClass : list1) {
                    Assert.assertNotNull(schoolClass.getSchoolStudents());
                    Assert.assertTrue(schoolClass.getSchoolStudents().size() >= 0);
                }
            }

            List<SchoolStudent> list1 = easyQuery.queryable(SchoolStudent.class)
                    .include(o -> o.one(SchoolStudent::getSchoolStudentAddress))
                    .include(o -> o.one(SchoolStudent::getSchoolClass)).toList();
            Assert.assertEquals(3, list1.size());
            for (SchoolStudent schoolStudent : list1) {
                Assert.assertNotNull(schoolStudent.getSchoolClass());
                Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                Assert.assertEquals(schoolStudent.getId(), schoolStudent.getSchoolStudentAddress().getStudentId());
            }
            {

                SchoolStudent schoolStudent = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolStudentAddress).include(x -> x.one(SchoolStudentAddress::getSchoolStudent)))
                        .include(o -> o.one(SchoolStudent::getSchoolClass))
                        .where(o -> o.eq(SchoolStudent::getId, "3")).firstOrNull();
                Assert.assertNotNull(schoolStudent);
                Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                Assert.assertNotNull(schoolStudent.getSchoolStudentAddress().getSchoolStudent());
                Assert.assertNotNull(schoolStudent.getSchoolClass());
            }
            {

                SchoolStudent schoolStudent = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolStudentAddress).where(x -> x.eq(SchoolStudentAddress::getId, "x")))
                        .include(o -> o.one(SchoolStudent::getSchoolClass))
                        .where(o -> o.eq(SchoolStudent::getId, "3")).firstOrNull();
                Assert.assertNotNull(schoolStudent);
                Assert.assertNull(schoolStudent.getSchoolStudentAddress());
            }
            {
                List<SchoolStudent> list2 = easyQuery.queryable(SchoolStudent.class)
                        .innerJoin(SchoolClass.class, (t1, t2) -> t1.eq(t2, SchoolStudent::getClassId, SchoolClass::getId))
                        .include(t -> t.one(SchoolStudent::getSchoolStudentAddress))
                        .toList();
                Assert.assertEquals(3, list2.size());
                for (SchoolStudent schoolStudent : list2) {
                    Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                    Assert.assertEquals(schoolStudent.getId(), schoolStudent.getSchoolStudentAddress().getStudentId());
                }
            }

            {

                List<SchoolClass> list2 = easyQuery.queryable(SchoolClass.class)
                        .include(o -> o.many(SchoolClass::getSchoolTeachers, 1))
                        .toList();
                for (SchoolClass schoolClass : list2) {
                    Assert.assertNotNull(schoolClass.getSchoolTeachers());
                }
            }

            {
                //todo alias
                List<SchoolClassVO> list2 = easyQuery.queryable(SchoolClass.class)
                        .include(o -> o.many(SchoolClass::getSchoolTeachers))
                        .select(SchoolClassVO.class, o -> o.columnAll()
                                .columnIncludeMany(SchoolClass::getSchoolTeachers, SchoolClassVO::getSchoolTeachers))
                        .toList();
                for (SchoolClassVO schoolClass : list2) {
                    Assert.assertNotNull(schoolClass.getSchoolTeachers());
                    if ("class1".equals(schoolClass.getId())) {
                        Assert.assertEquals(2, schoolClass.getSchoolTeachers().size());
                        for (SchoolTeacherVO schoolTeacher : schoolClass.getSchoolTeachers()) {
                            Assert.assertTrue("teacher1".equals(schoolTeacher.getId()) || "teacher2".equals(schoolTeacher.getId()));
                        }
                    }
                    if ("class2".equals(schoolClass.getId())) {
                        Assert.assertEquals(1, schoolClass.getSchoolTeachers().size());
                        for (SchoolTeacherVO schoolTeacher : schoolClass.getSchoolTeachers()) {
                            Assert.assertEquals("teacher2", schoolTeacher.getId());
                        }
                    }
                }
            }

            List<SchoolClass> list2 = easyQuery.queryable(SchoolClass.class)
                    .include(o -> o.many(SchoolClass::getSchoolTeachers))
                    .include(o -> o.many(SchoolClass::getSchoolStudents))
                    .toList();
            Assert.assertEquals(3, list2.size());

            for (SchoolClass schoolClass : list2) {
                Assert.assertNotNull(schoolClass.getSchoolStudents());
                if ("class1".equals(schoolClass.getId())) {
                    Assert.assertNotNull(schoolClass.getSchoolTeachers());
                    Assert.assertEquals(2, schoolClass.getSchoolTeachers().size());
                }
                if ("class2".equals(schoolClass.getId())) {
                    Assert.assertNotNull(schoolClass.getSchoolTeachers());
                    Assert.assertEquals(1, schoolClass.getSchoolTeachers().size());
                }
                if ("class3".equals(schoolClass.getId())) {
                    Assert.assertNotNull(schoolClass.getSchoolTeachers());
                    Assert.assertEquals(0, schoolClass.getSchoolTeachers().size());
                }
            }

            List<SchoolTeacher> list3 = easyQuery.queryable(SchoolTeacher.class)
                    .include(o -> o.many(SchoolTeacher::getSchoolClasses))
                    .toList();
            Assert.assertEquals(2, list3.size());
            for (SchoolTeacher schoolTeacher : list3) {
                if ("teacher1".equals(schoolTeacher.getId())) {
                    Assert.assertNotNull(schoolTeacher.getSchoolClasses());
                    Assert.assertEquals(1, schoolTeacher.getSchoolClasses().size());
                }
                if ("teacher2".equals(schoolTeacher.getId())) {
                    Assert.assertNotNull(schoolTeacher.getSchoolClasses());
                    Assert.assertEquals(2, schoolTeacher.getSchoolClasses().size());
                }
            }

        } finally {
            relationRemove(ids);
        }
    }

    @Test
    public void provinceTest() {
        List<Province> list = easyQuery.queryable(Province.class)
                .include(o -> o.many(Province::getCities).include(x -> x.many(City::getAreas)))
                .toList();
        Assert.assertEquals(2, list.size());
        for (Province province : list) {
            Assert.assertNotNull(province.getCities());
            for (City city : province.getCities()) {
                Assert.assertEquals(province.getCode(), city.getProvinceCode());
                Assert.assertNotNull(city.getAreas());
                for (Area area : city.getAreas()) {
                    Assert.assertEquals(city.getCode(), area.getCityCode());
                    Assert.assertEquals(province.getCode(), area.getProvinceCode());
                }
            }
        }
    }

    @Test
    public void provinceTest6() {
        EasyPageResult<Province> page = easyQuery.queryable(Province.class)
                .include(o -> o.many(Province::getCities).include(x -> x.many(City::getAreas)))
                .toPageResult(1, 100);
        List<Province> list = page.getData();
        Assert.assertEquals(2, list.size());
        for (Province province : list) {
            Assert.assertNotNull(province.getCities());
            for (City city : province.getCities()) {
                Assert.assertEquals(province.getCode(), city.getProvinceCode());
                Assert.assertNotNull(city.getAreas());
                for (Area area : city.getAreas()) {
                    Assert.assertEquals(city.getCode(), area.getCityCode());
                    Assert.assertEquals(province.getCode(), area.getProvinceCode());
                }
            }
        }
    }

    @Test
    public void provinceTest7() {
        EasyPageResult<Province> page = easyQuery.queryable(Province.class)
                .include(o -> o.many(Province::getCities).include(x -> x.many(City::getAreas)))
                .toPageResult(1, 1);
        List<Province> list = page.getData();
        Assert.assertEquals(1, list.size());
        for (Province province : list) {
            Assert.assertNotNull(province.getCities());
            for (City city : province.getCities()) {
                Assert.assertEquals(province.getCode(), city.getProvinceCode());
                Assert.assertNotNull(city.getAreas());
                for (Area area : city.getAreas()) {
                    Assert.assertEquals(city.getCode(), area.getCityCode());
                    Assert.assertEquals(province.getCode(), area.getProvinceCode());
                }
            }
        }
    }

    @Test
    public void provinceTest1() {

        List<Province> list = easyQuery.queryable(Province.class)
                .include(o -> o.many(Province::getCities).where(x -> x.eq(City::getCode, "3306"))
                        .include(x -> x.many(City::getAreas).where(y -> y.eq(Area::getCode, "330602"))))
                .toList();

        Assert.assertEquals(2, list.size());
        for (Province province : list) {
            Assert.assertNotNull(province.getCities());
            for (City city : province.getCities()) {
                Assert.assertEquals("绍兴市", city.getName());
                Assert.assertEquals(province.getCode(), city.getProvinceCode());
                Assert.assertNotNull(city.getAreas());
                for (Area area : city.getAreas()) {
                    Assert.assertEquals("越城区", area.getName());
                    Assert.assertEquals(city.getCode(), area.getCityCode());
                    Assert.assertEquals(province.getCode(), area.getProvinceCode());
                }
            }
        }
        System.out.println(list);
    }

    @Test
    public void provinceTest5() {

        List<Province> list = easyQuery.queryable(Province.class)
                .whereById("33")
                .include(o -> o.many(Province::getCities))
                .toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void provinceTest61() {

        List<Province> list = easyQuery.queryable(Province.class)
                .whereById("33")
                .include(o -> o.many(Province::getCities).leftJoin(Topic.class, (x, y) -> x.eq(y, City::getCode, Topic::getId)).where((x, y) -> y.like(Topic::getTitle, "title")))
                .toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void schoolTest1() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<SchoolClass> hasXiaoMingClass = easyEntityQuery.queryable(SchoolClass.class)
                    .where(s -> s.schoolStudents().any(x -> x.name().like("小明")))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<SchoolClass> hasXiaoMingClass = easyEntityQuery.queryable(SchoolClass.class)
                    .where(s -> s.schoolStudents().any(x -> {
                        x.name().like("小明");
                        x.classId().like("13");
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ? AND t1.`class_id` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%小明%(String),%13%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void schoolTest2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolStudent> hasXiaoMingClass = easyEntityQuery.queryable(SchoolStudent.class)
//                    .include(x->x.schoolClass())
                .where(s -> s.schoolClass().name().like("一班"))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`class_id`,t.`name` FROM `school_student` t LEFT JOIN `school_class` t1 ON t1.`id` = t.`class_id` WHERE t1.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%一班%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void schoolTest3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<SchoolClass> studentAddressInXXRoadClasses = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> s.schoolStudents().any(
                        x -> x.schoolStudentAddress().address().like("xx路")
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_student` t1 LEFT JOIN `school_student_address` t2 ON t2.`student_id` = t1.`id` WHERE t1.`class_id` = t.`id` AND t2.`address` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%xx路%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void schoolTest4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<SchoolClass> studentAddressInXXRoadClasses = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> s.schoolStudents().any(
                        x -> {
                            x.schoolStudentAddress().address().like("xx路");
                            x.name().like("小明");
                        }
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_student` t1 LEFT JOIN `school_student_address` t2 ON t2.`student_id` = t1.`id` WHERE t1.`class_id` = t.`id` AND t2.`address` LIKE ? AND t1.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%xx路%(String),%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void schoolTest5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<SchoolClass> studentAddressInXXRoadClasses = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> s.schoolStudents()
                        .where(x -> x.schoolStudentAddress().address().like("xx路"))
                        .where(x -> x.name().like("小明")).any()
                ).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_student` t1 LEFT JOIN `school_student_address` t2 ON t2.`student_id` = t1.`id` WHERE t1.`class_id` = t.`id` AND t2.`address` LIKE ? AND t1.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%xx路%(String),%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void schoolTest6() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<SchoolClass> x1 = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> s.schoolTeachers()
                        .any(x -> x.name().like("x"))).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE EXISTS (SELECT 1 FROM `school_teacher` t1 WHERE EXISTS (SELECT 1 FROM `school_class_teacher` t2 WHERE t2.`teacher_id` = t1.`id` AND t2.`class_id` = t.`id` LIMIT 1) AND t1.`name` LIKE ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%x%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void schoolTest7() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<SchoolClass> nameStartZhang = easyEntityQuery.queryable(SchoolClass.class)
                .where(s -> s.schoolStudents().where(x -> x.name().likeMatchLeft("张")).count().eq(5L))
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE (SELECT COUNT(*) FROM `school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("张%(String),5(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void schoolTest8() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<SchoolClass> hasXiaoMingClass = easyEntityQuery.queryable(SchoolClass.class)
                    .where(s -> s.schoolStudents().where(x -> {
                        x.name().like("小明");
                        x.classId().like("13");
                    }).none())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE NOT ( EXISTS (SELECT 1 FROM `school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ? AND t1.`class_id` LIKE ? LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%小明%(String),%13%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<SchoolClass> hasXiaoMingClass = easyEntityQuery.queryable(SchoolClass.class)
                    .where(s -> s.schoolStudents().none(x -> {
                        x.name().like("小明");
                        x.classId().like("13");
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t WHERE NOT ( EXISTS (SELECT 1 FROM `school_student` t1 WHERE t1.`class_id` = t.`id` AND t1.`name` LIKE ? AND t1.`class_id` LIKE ? LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%小明%(String),%13%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
//        SQLFunc fx = easyQueryClient.getRuntimeContext().fx();
//        SQLFunction subString = fx.subString("title", 1, 10);
//        SQLFunction concat = fx.concat(x -> x.sqlFunc(subString).value("123"));
//        easyQueryClient.updatable(Topic.class)
//                .setSQLFunction("title",concat)
//                .asTable("a123123")
//                .whereById("123zzzxxx")
//                .executeRows();

    }

    @Test
    public void testSub() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolStudent.class)
                .where(s -> s.name().like("123"))
                .select(s -> s.schoolClass()).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`name` FROM `school_student` t LEFT JOIN `school_class` t1 ON t1.`id` = t.`class_id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSub1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(SchoolStudent.class)
                .where(s -> s.name().like("123"))
                .select(s -> new StringProxy(s.schoolClass().name())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`name` FROM `school_student` t LEFT JOIN `school_class` t1 ON t1.`id` = t.`class_id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testSub2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolStudent> list = easyEntityQuery.queryable(SchoolStudent.class)
                .where(s -> s.name().like("123"))
                .select(s -> s.schoolStudentAddress().schoolStudent()).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`id`,t2.`class_id`,t2.`name` FROM `school_student` t LEFT JOIN `school_student_address` t1 ON t1.`student_id` = t.`id` LEFT JOIN `school_student` t2 ON t2.`id` = t1.`student_id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSub3() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(SchoolStudent.class)
                .where(s -> s.name().like("123"))
                .select(s -> new StringProxy(s.schoolClass().name().toLower())).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT LOWER(t1.`name`) FROM `school_student` t LEFT JOIN `school_class` t1 ON t1.`id` = t.`class_id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testSub4() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(SchoolStudent.class)
                .where(s -> {
                    s.name().like("123");
                })
                .selectColumn(s -> s.schoolClass().name().toLower()).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT LOWER(t1.`name`) FROM `school_student` t LEFT JOIN `school_class` t1 ON t1.`id` = t.`class_id` WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
