package com.easy.query.test;

import com.easy.query.test.entity.base.Area;
import com.easy.query.test.entity.base.City;
import com.easy.query.test.entity.base.Province;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.entity.school.SchoolClassTeacher;
import com.easy.query.test.entity.school.SchoolStudent;
import com.easy.query.test.entity.school.SchoolStudentAddress;
import com.easy.query.test.entity.school.SchoolTeacher;
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
                 .where(o->o.in(SchoolClass::getId,Arrays.asList("class1","class2","class3"))).executeRows();
         easyQuery.deletable(SchoolTeacher.class)
                 .where(o->o.in(SchoolTeacher::getId,Arrays.asList("teacher1","teacher2"))).executeRows();
         easyQuery.deletable(SchoolClassTeacher.class)
                 .where(o->o.in(SchoolClassTeacher::getClassId,Arrays.asList("class1","class2","class3"))).executeRows();
         easyQuery.deletable(SchoolClassTeacher.class)
                 .where(o->o.in(SchoolClassTeacher::getTeacherId,Arrays.asList("teacher1","teacher2"))).executeRows();
         ArrayList<SchoolStudent> schoolStudents = new ArrayList<>();
         ArrayList<SchoolClass> schoolClasses = new ArrayList<>();
         ArrayList<SchoolStudentAddress> schoolStudentAddresses = new ArrayList<>();
         ArrayList<SchoolTeacher> schoolTeachers = new ArrayList<>();
         ArrayList<SchoolClassTeacher> schoolClassTeachers = new ArrayList<>();
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
     public void relationRemove(List<String> ids){
         easyQuery.deletable(SchoolStudent.class)
                 .whereByIds(ids).executeRows();
         easyQuery.deletable(SchoolStudentAddress.class)
                 .where(o->o.in(SchoolStudentAddress::getStudentId,ids)).executeRows();
         easyQuery.deletable(SchoolClass.class)
                 .where(o->o.in(SchoolClass::getId,Arrays.asList("class1","class2","class3"))).executeRows();
         easyQuery.deletable(SchoolTeacher.class)
                 .where(o->o.in(SchoolTeacher::getId,Arrays.asList("teacher1","teacher2"))).executeRows();
         easyQuery.deletable(SchoolClassTeacher.class)
                 .where(o->o.in(SchoolClassTeacher::getClassId,Arrays.asList("class1","class2","class3"))).executeRows();
         easyQuery.deletable(SchoolClassTeacher.class)
                 .where(o->o.in(SchoolClassTeacher::getTeacherId,Arrays.asList("teacher1","teacher2"))).executeRows();
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

            {
                List<SchoolStudent> list1 = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolClass))
                        .toList();
                for (SchoolStudent schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(),schoolStudent.getSchoolClass().getId());
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
                List<SchoolClass> list1 = easyQuery.queryable(SchoolClass.class)
                        .include(o -> o.many(SchoolClass::getSchoolStudents))
                        .toList();
                for (SchoolClass schoolClass : list1) {
                    Assert.assertNotNull(schoolClass.getSchoolStudents());
                    Assert.assertTrue(schoolClass.getSchoolStudents().size()>=0);
                }
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
            {

                SchoolStudent schoolStudent = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolStudentAddress).include(x->x.one(SchoolStudentAddress::getSchoolStudent)))
                        .include(o -> o.one(SchoolStudent::getSchoolClass))
                        .where(o->o.eq(SchoolStudent::getId,"3")).firstOrNull();
                Assert.assertNotNull(schoolStudent);
                Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                Assert.assertNotNull(schoolStudent.getSchoolStudentAddress().getSchoolStudent());
                Assert.assertNotNull(schoolStudent.getSchoolClass());
            }
            {

                SchoolStudent schoolStudent = easyQuery.queryable(SchoolStudent.class)
                        .include(o -> o.one(SchoolStudent::getSchoolStudentAddress).where(x->x.eq(SchoolStudentAddress::getId,"x")))
                        .include(o -> o.one(SchoolStudent::getSchoolClass))
                        .where(o->o.eq(SchoolStudent::getId,"3")).firstOrNull();
                Assert.assertNotNull(schoolStudent);
                Assert.assertNull(schoolStudent.getSchoolStudentAddress());
            }
            {
                List<SchoolStudent> list2 = easyQuery.queryable(SchoolStudent.class)
                        .innerJoin(SchoolClass.class, (t, t1) -> t.eq(t1, SchoolStudent::getClassId, SchoolClass::getId))
                        .include((t, t1) -> t.one(SchoolStudent::getSchoolStudentAddress))
                        .toList();
                Assert.assertEquals(3,list2.size());
                for (SchoolStudent schoolStudent : list2) {
                    Assert.assertNotNull(schoolStudent.getSchoolStudentAddress());
                    Assert.assertEquals(schoolStudent.getId(),schoolStudent.getSchoolStudentAddress().getStudentId());
                }
            }

            {

                List<SchoolClass> list2 = easyQuery.queryable(SchoolClass.class)
                        .include(o -> o.many(SchoolClass::getSchoolTeachers))
                        .toList();
                for (SchoolClass schoolClass : list2) {
                    Assert.assertNotNull(schoolClass.getSchoolTeachers());
                }
            }

            List<SchoolClass> list2 = easyQuery.queryable(SchoolClass.class)
                    .include(o -> o.many(SchoolClass::getSchoolTeachers))
                    .include(o -> o.many(SchoolClass::getSchoolStudents))
                    .toList();
            Assert.assertEquals(3,list2.size());

            for (SchoolClass schoolClass : list2) {
                Assert.assertNotNull(schoolClass.getSchoolStudents());
                if("class1".equals(schoolClass.getId())){
                    Assert.assertNotNull(schoolClass.getSchoolTeachers());
                    Assert.assertEquals(2,schoolClass.getSchoolTeachers().size());
                }
                if("class2".equals(schoolClass.getId())){
                    Assert.assertNotNull(schoolClass.getSchoolTeachers());
                    Assert.assertEquals(1,schoolClass.getSchoolTeachers().size());
                }
                if("class3".equals(schoolClass.getId())){
                    Assert.assertNotNull(schoolClass.getSchoolTeachers());
                    Assert.assertEquals(0,schoolClass.getSchoolTeachers().size());
                }
            }

            List<SchoolTeacher> list3 = easyQuery.queryable(SchoolTeacher.class)
                    .include(o -> o.many(SchoolTeacher::getSchoolClasses))
                    .toList();
            Assert.assertEquals(2,list3.size());
            for (SchoolTeacher schoolTeacher : list3) {
                if("teacher1".equals(schoolTeacher.getId())){
                    Assert.assertNotNull(schoolTeacher.getSchoolClasses());
                    Assert.assertEquals(1,schoolTeacher.getSchoolClasses().size());
                }
                if("teacher2".equals(schoolTeacher.getId())){
                    Assert.assertNotNull(schoolTeacher.getSchoolClasses());
                    Assert.assertEquals(2,schoolTeacher.getSchoolClasses().size());
                }
            }

        }finally {
            relationRemove(ids);
        }
    }

    @Test
    public void provinceTest(){
        List<Province> list = easyQuery.queryable(Province.class)
                .include(o -> o.many(Province::getCities).include(x -> x.many(City::getAreas)))
                .toList();
        Assert.assertEquals(2,list.size());
        for (Province province : list) {
            Assert.assertNotNull(province.getCities());
            for (City city : province.getCities()) {
                Assert.assertEquals(province.getCode(),city.getProvinceCode());
                Assert.assertNotNull(city.getAreas());
                for (Area area : city.getAreas()) {
                    Assert.assertEquals(city.getCode(),area.getCityCode());
                    Assert.assertEquals(province.getCode(),area.getProvinceCode());
                }
            }
        }
    }

    @Test
    public void provinceTest1(){

        List<Province> list = easyQuery.queryable(Province.class)
                .include(o -> o.many(Province::getCities).where(x->x.eq(City::getCode,"3306"))
                        .include(x -> x.many(City::getAreas).where(y->y.eq(Area::getCode,"330602"))))
                .toList();

        Assert.assertEquals(2,list.size());
        for (Province province : list) {
            Assert.assertNotNull(province.getCities());
            for (City city : province.getCities()) {
                Assert.assertEquals("绍兴市",city.getName());
                Assert.assertEquals(province.getCode(),city.getProvinceCode());
                Assert.assertNotNull(city.getAreas());
                for (Area area : city.getAreas()) {
                    Assert.assertEquals("越城区",area.getName());
                    Assert.assertEquals(city.getCode(),area.getCityCode());
                    Assert.assertEquals(province.getCode(),area.getProvinceCode());
                }
            }
        }
        System.out.println(list);
    }
}