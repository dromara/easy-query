package com.easy.query.test;

import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.entity.school.SchoolStudent;
import com.easy.query.test.entity.school.SchoolStudentAddress;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/7/16 21:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationTest extends BaseTest{

     public void relationInit(List<String> ids){

         easyQuery.deletable(SchoolStudent.class)
                 .whereByIds(ids).executeRows();
         easyQuery.deletable(SchoolStudentAddress.class)
                 .where(o->o.in(SchoolStudentAddress::getStudentId,ids)).executeRows();
         easyQuery.deletable(SchoolClass.class)
                 .where(o->o.in(SchoolClass::getId,Arrays.asList("class1","class2"))).executeRows();
         ArrayList<SchoolStudent> schoolStudents = new ArrayList<>();
         ArrayList<SchoolClass> schoolClasses = new ArrayList<>();
         ArrayList<SchoolStudentAddress> schoolStudentAddresses = new ArrayList<>();
         int i=0;
         for (String id : ids) {
             int i1 = i % 2;
             SchoolStudent schoolStudent = new SchoolStudent();
             schoolStudent.setId(id);
             schoolStudent.setClassId("class"+(i1+1));
             schoolStudent.setName("学生"+id);
             schoolStudents.add(schoolStudent);
             SchoolStudentAddress schoolStudentAddress = new SchoolStudentAddress();
             schoolStudentAddress.setId("address"+id);
             schoolStudentAddress.setStudentId(id);
             schoolStudentAddress.setAddress("地址"+id);
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


         easyQuery.insertable(schoolStudents).executeRows();
         easyQuery.insertable(schoolClasses).executeRows();
         easyQuery.insertable(schoolStudentAddresses).executeRows();
     }
     public void relationRemove(List<String> ids){
         easyQuery.deletable(SchoolStudent.class)
                 .whereByIds(ids).executeRows();
         easyQuery.deletable(SchoolStudentAddress.class)
                 .where(o->o.in(SchoolStudentAddress::getStudentId,ids)).executeRows();
         easyQuery.deletable(SchoolClass.class)
                 .where(o->o.in(SchoolClass::getId,Arrays.asList("class1","class2"))).executeRows();
     }
    @Test
    public void testOneToOne(){
        List<String> ids = Arrays.asList("1", "2", "3");
        try{
            relationInit(ids);

            List<SchoolStudent> list = easyQuery.queryable(SchoolStudent.class).toList();
            Assert.assertEquals(3,list.size());
            for (SchoolStudent schoolStudent : list) {
                Assert.assertNull(schoolStudent.getSchoolStudentAddress());
            }
            List<SchoolStudent> list1 = easyQuery.queryable(SchoolStudent.class)
                    .include(o -> o.one(SchoolStudent::getSchoolStudentAddress))
                    .include(o -> o.one(SchoolStudent::getSchoolClass)).toList();
            Assert.assertEquals(3,list1.size());
            for (SchoolStudent schoolStudent : list1) {
                Assert.assertNotNull(schoolStudent.getSchoolClass());
                Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                Assert.assertEquals(schoolStudent.getId(),schoolStudent.getSchoolStudentAddress().getStudentId());
            }

        }finally {
            relationRemove(ids);
        }
    }
}
