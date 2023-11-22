package com.easy.query.test;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.test.dto.CityVO;
import com.easy.query.test.entity.base.Area;
import com.easy.query.test.entity.base.City;
import com.easy.query.test.entity.base.Province;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.entity.school.SchoolClassTeacher;
import com.easy.query.test.entity.school.SchoolStudent;
import com.easy.query.test.entity.school.SchoolStudentAddress;
import com.easy.query.test.entity.school.SchoolTeacher;
import com.easy.query.test.entity.school.dto.SchoolClassVO;
import com.easy.query.test.entity.school.dto.SchoolStudentVO;
import com.easy.query.test.entity.school.dto.SchoolTeacherVO;
import com.easy.query.test.entity.school.proxy.SchoolClassProxy;
import com.easy.query.test.entity.school.proxy.SchoolStudentProxy;
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

            List<SchoolStudent> list = easyQuery.queryable(SchoolStudent.class).toList();
            Assert.assertEquals(3, list.size());
            for (SchoolStudent schoolStudent : list) {
                Assert.assertNull(schoolStudent.getSchoolStudentAddress());
            }

            SchoolStudentProxy table = SchoolStudentProxy.createTable();
            List<SchoolStudent> listp = easyProxyQuery.queryable(table).toList();
            Assert.assertEquals(3, listp.size());
            for (SchoolStudent schoolStudent : listp) {
                Assert.assertNull(schoolStudent.getSchoolStudentAddress());
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
                SchoolStudentProxy table1 = SchoolStudentProxy.createTable();
                SchoolClassProxy table2 = SchoolClassProxy.createTable();
                List<SchoolStudent> list1 = easyProxyQuery.queryable(table1)
                        .include(i -> i.one(table1.schoolClass(), table2))
                        .toList();
                for (SchoolStudent schoolStudent : list1) {
                    Assert.assertNotNull(schoolStudent.getSchoolClass());
                    Assert.assertEquals(schoolStudent.getClassId(), schoolStudent.getSchoolClass().getId());
                }
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
    public void provinceTest2() {

        List<Province> list = easyQuery.queryable(Province.class)
                .fillMany(x -> x.with(City.class).where(y -> y.eq(City::getCode, "3306"))
                        , City::getProvinceCode
                        , Province::getCode
                        , (x, y) -> {
                            x.setCities(new ArrayList<>(y));
                        })
                .toList();
        List<City> list1 = easyQuery.queryable(City.class)
                .fillOne(x -> x.with(Province.class), Province::getCode, City::getProvinceCode, (x, y) -> {
                    x.setProvince(y);
                })
                .toList();

        Assert.assertEquals(2, list.size());
        for (Province province : list) {
            if ("浙江省".equals(province.getName())) {

                Assert.assertNotNull(province.getCities());
                for (City city : province.getCities()) {
                    Assert.assertEquals("绍兴市", city.getName());
                    Assert.assertEquals(province.getCode(), city.getProvinceCode());
                    Assert.assertNull(city.getAreas());
                }
            }
        }
    }

    @Test
    public void provinceTest3() {

        EasyPageResult<City> pageResult = easyQuery.queryable(City.class)
                .fillOne(x -> x.consumeNull(false).with(Province.class), Province::getCode, City::getProvinceCode, (x, y) -> {
                    x.setProvince(y);
                })
                .toPageResult(1, 2);

        Assert.assertEquals(24L, pageResult.getTotal());
        Assert.assertEquals(2, pageResult.getData().size());
        for (City city : pageResult.getData()) {
            Assert.assertNotNull(city.getProvince());
        }
        {

            EasyPageResult<Province> pageResult1 = easyQuery.queryable(Province.class)
                    .fillMany(x -> x.with(City.class).where(y -> y.eq(City::getCode, "3306"))
                            , City::getProvinceCode
                            , Province::getCode
                            , (x, y) -> {
                                x.setCities(new ArrayList<>(y));
                            })
                    .toPageResult(1, 1);
            Assert.assertEquals(2L, pageResult1.getTotal());
            Assert.assertEquals(1, pageResult1.getData().size());
            for (Province province : pageResult1.getData()) {
                if (!"33".equals(province.getCode())) {
                    Assert.assertNull(province.getCities());
                } else {
                    Assert.assertNotNull(province.getCities());
                    Assert.assertTrue(province.getCities().size() > 0);
                }
            }
        }
        {

            EasyPageResult<Province> pageResult1 = easyQuery.queryable(Province.class)
                    .fillMany(x -> x.consumeNull(true).with(City.class).where(y -> y.eq(City::getCode, "3306"))
                            , City::getProvinceCode
                            , Province::getCode
                            , (x, y) -> {
                                x.setCities(y == null ? new ArrayList<>() : new ArrayList<>(y));
                            })
                    .toPageResult(1, 1);
            Assert.assertEquals(2L, pageResult1.getTotal());
            Assert.assertEquals(1, pageResult1.getData().size());
            for (Province province : pageResult1.getData()) {
                Assert.assertNotNull(province.getCities());
                if (!"33".equals(province.getCode())) {
                    Assert.assertEquals(0, province.getCities().size());
                } else {
                    Assert.assertTrue(province.getCities().size() > 0);
                }
            }
        }
        {

            EasyPageResult<Province> pageResult1 = easyQuery.queryable(Province.class)
                    .fillMany(x -> x.consumeNull(true).with(City.class).where(y -> y.eq(City::getCode, "3306")).select(CityVO.class)
                            , CityVO::getProvinceCode
                            , Province::getCode
                            , (x, y) -> {
                                if (EasyCollectionUtil.isNotEmpty(y)) {
                                    CityVO first = EasyCollectionUtil.first(y);
                                    x.setFirstCityName(first.getName());
                                }
                            })
                    .toPageResult(1, 10);
            Assert.assertEquals(2L, pageResult1.getTotal());
            Assert.assertEquals(2, pageResult1.getData().size());
            for (Province province : pageResult1.getData()) {
                if (!"33".equals(province.getCode())) {
                    Assert.assertNull(province.getFirstCityName());
                } else {
                    Assert.assertNotNull(province.getFirstCityName());
                }
            }
        }
    }

    @Test
    public void provinceTest5() {

        List<Province> list = easyQuery.queryable(Province.class)
                .whereById("33")
                .include(o -> o.many(Province::getCities))
                .toList();
        Assert.assertEquals(1, list.size());
    }
}
