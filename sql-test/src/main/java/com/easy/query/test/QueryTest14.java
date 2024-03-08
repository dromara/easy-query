package com.easy.query.test;

import com.easy.query.test.entity.testrelation.TestJoinEntity;
import com.easy.query.test.entity.testrelation.TestRoleEntity;
import com.easy.query.test.entity.testrelation.TestRouteEntity;
import com.easy.query.test.entity.testrelation.TestUserEntity;
import com.easy.query.test.entity.testrelation.vo.TestUserDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * create time 2024/3/8 11:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest14 extends BaseTest{

    @Test
    public void test1(){

        easyEntityQuery.deletable(TestUserEntity.class)
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("u1","u2","u3"))
                .executeRows();
        easyEntityQuery.deletable(TestRoleEntity.class)
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("r1","r2","r3","r4"))
                .executeRows();
        easyEntityQuery.deletable(TestRouteEntity.class)
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("ru1","ru2","ru3"))
                .executeRows();
        easyEntityQuery.deletable(TestJoinEntity.class)
                .allowDeleteStatement(true)
                .where(t -> t.expression().sql("1=1"))
                .executeRows();
        ArrayList<TestUserEntity> testUserEntities = new ArrayList<>();
        {
            TestUserEntity testUserEntity = new TestUserEntity();
            testUserEntity.setId("u1");
            testUserEntity.setName("uname1");
            testUserEntity.setPassword("upwd1");
            testUserEntities.add(testUserEntity);
        }
        {
            TestUserEntity testUserEntity = new TestUserEntity();
            testUserEntity.setId("u2");
            testUserEntity.setName("uname2");
            testUserEntity.setPassword("upwd2");
            testUserEntities.add(testUserEntity);
        }
        {
            TestUserEntity testUserEntity = new TestUserEntity();
            testUserEntity.setId("u3");
            testUserEntity.setName("uname3");
            testUserEntity.setPassword("upwd3");
            testUserEntities.add(testUserEntity);
        }
        ArrayList<TestRoleEntity> testRoleEntities = new ArrayList<>();
        {
            TestRoleEntity testRoleEntity = new TestRoleEntity();
            testRoleEntity.setId("r1");
            testRoleEntity.setName("rname1");
            testRoleEntity.setRemark("rremark1");
            testRoleEntities.add(testRoleEntity);
        }
        {
            TestRoleEntity testRoleEntity = new TestRoleEntity();
            testRoleEntity.setId("r2");
            testRoleEntity.setName("rname2");
            testRoleEntity.setRemark("rremark2");
            testRoleEntities.add(testRoleEntity);
        }
        {
            TestRoleEntity testRoleEntity = new TestRoleEntity();
            testRoleEntity.setId("r3");
            testRoleEntity.setName("rname3");
            testRoleEntity.setRemark("rremark3");
            testRoleEntities.add(testRoleEntity);
        }
        {
            TestRoleEntity testRoleEntity = new TestRoleEntity();
            testRoleEntity.setId("r4");
            testRoleEntity.setName("rname4");
            testRoleEntity.setRemark("rremark4");
            testRoleEntities.add(testRoleEntity);
        }
        ArrayList<TestRouteEntity> testRouteEntities = new ArrayList<>();
        {
            TestRouteEntity testRouteEntity = new TestRouteEntity();
            testRouteEntity.setId("ru1");
            testRouteEntity.setName("runame1");
            testRouteEntity.setRequestPath("/api/1");
            testRouteEntities.add(testRouteEntity);
        }
        {
            TestRouteEntity testRouteEntity = new TestRouteEntity();
            testRouteEntity.setId("ru2");
            testRouteEntity.setName("runame2");
            testRouteEntity.setRequestPath("/api/2");
            testRouteEntities.add(testRouteEntity);
        }
        {
            TestRouteEntity testRouteEntity = new TestRouteEntity();
            testRouteEntity.setId("ru3");
            testRouteEntity.setName("runame3");
            testRouteEntity.setRequestPath("/api/3");
            testRouteEntities.add(testRouteEntity);
        }


//        easyEntityQuery.deletable(TestUserEntity.class)
//                .allowDeleteStatement(true)
//                .whereByIds(Arrays.asList("u1","u2","u3"))
//                .executeRows();
//        easyEntityQuery.deletable(TestRoleEntity.class)
//                .allowDeleteStatement(true)
//                .whereByIds(Arrays.asList("r1","r2","r3","r4"))
//                .executeRows();
//        easyEntityQuery.deletable(TestRouteEntity.class)
//                .allowDeleteStatement(true)
//                .whereByIds(Arrays.asList("ru1","ru2","ru3"))
//                .executeRows();
//        easyEntityQuery.deletable(TestJoinEntity.class)
//                .allowDeleteStatement(true)
//                .where(t -> t.expression().sql("1=1"))
//                .executeRows();

        ArrayList<TestJoinEntity> testJoinEntities = new ArrayList<>();
        {
            TestJoinEntity testJoinEntity = new TestJoinEntity();
            testJoinEntity.setFirstId("u1");
            testJoinEntity.setSecondId("r1");
            testJoinEntity.setType(1);
            testJoinEntities.add(testJoinEntity);
        }
        {
            TestJoinEntity testJoinEntity = new TestJoinEntity();
            testJoinEntity.setFirstId("u2");
            testJoinEntity.setSecondId("r2");
            testJoinEntity.setType(1);
            testJoinEntities.add(testJoinEntity);
        }
        {
            TestJoinEntity testJoinEntity = new TestJoinEntity();
            testJoinEntity.setFirstId("u3");
            testJoinEntity.setSecondId("r3");
            testJoinEntity.setType(1);
            testJoinEntities.add(testJoinEntity);
        }
        {
            TestJoinEntity testJoinEntity = new TestJoinEntity();
            testJoinEntity.setFirstId("u1");
            testJoinEntity.setSecondId("r4");
            testJoinEntity.setType(1);
            testJoinEntities.add(testJoinEntity);
        }
        {
            TestJoinEntity testJoinEntity = new TestJoinEntity();
            testJoinEntity.setFirstId("u2");
            testJoinEntity.setSecondId("r4");
            testJoinEntity.setType(1);
            testJoinEntities.add(testJoinEntity);
        }
        //角色和路由
        {
            TestJoinEntity testJoinEntity = new TestJoinEntity();
            testJoinEntity.setFirstId("r1");
            testJoinEntity.setSecondId("ru1");
            testJoinEntity.setType(2);
            testJoinEntities.add(testJoinEntity);
        }
        {
            TestJoinEntity testJoinEntity = new TestJoinEntity();
            testJoinEntity.setFirstId("r1");
            testJoinEntity.setSecondId("ru2");
            testJoinEntity.setType(2);
            testJoinEntities.add(testJoinEntity);
        }
        {
            TestJoinEntity testJoinEntity = new TestJoinEntity();
            testJoinEntity.setFirstId("r2");
            testJoinEntity.setSecondId("ru2");
            testJoinEntity.setType(2);
            testJoinEntities.add(testJoinEntity);
        }
        {
            TestJoinEntity testJoinEntity = new TestJoinEntity();
            testJoinEntity.setFirstId("r3");
            testJoinEntity.setSecondId("ru3");
            testJoinEntity.setType(2);
            testJoinEntities.add(testJoinEntity);
        }

        easyEntityQuery.insertable(testUserEntities).executeRows();
        easyEntityQuery.insertable(testRoleEntities).executeRows();
        easyEntityQuery.insertable(testRouteEntities).executeRows();
        easyEntityQuery.insertable(testJoinEntities).executeRows();

        List<TestUserDTO> list = easyEntityQuery.queryable(TestUserEntity.class)
                .selectAutoInclude(TestUserDTO.class)
                .toList();

        {

            TestUserDTO u1 = list.stream().filter(o -> Objects.equals("u1", o.getId())).findFirst().orElse(null);
            Assert.assertNotNull(u1);
            List<TestUserDTO.TestRoleDTO> roles = u1.getRoles();
            Assert.assertNotNull(roles);
            Assert.assertEquals(2,roles.size());
            TestUserDTO.TestRoleDTO testRoleDTO0 = roles.get(0);
            Assert.assertEquals("r1",testRoleDTO0.getId());
            TestUserDTO.TestRoleDTO testRoleDTO1 = roles.get(1);
            Assert.assertEquals("r4",testRoleDTO1.getId());
            Assert.assertNotNull(testRoleDTO0.getRoutes());
            Assert.assertEquals(2,testRoleDTO0.getRoutes().size());
            TestUserDTO.TestRouteDTO testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
            TestUserDTO.TestRouteDTO testRouteDTO1 = testRoleDTO0.getRoutes().get(1);
            Assert.assertEquals("ru1",testRouteDTO0.getId());
            Assert.assertEquals("ru2",testRouteDTO1.getId());
        }
        {

            TestUserDTO u2 = list.stream().filter(o -> Objects.equals("u2", o.getId())).findFirst().orElse(null);
            Assert.assertNotNull(u2);
            List<TestUserDTO.TestRoleDTO> roles = u2.getRoles();
            Assert.assertNotNull(roles);
            Assert.assertEquals(2,roles.size());
            TestUserDTO.TestRoleDTO testRoleDTO0 = roles.get(0);
            Assert.assertEquals("r2",testRoleDTO0.getId());
            TestUserDTO.TestRoleDTO testRoleDTO1 = roles.get(1);
            Assert.assertEquals("r4",testRoleDTO1.getId());
            Assert.assertNotNull(testRoleDTO0.getRoutes());
            Assert.assertEquals(1,testRoleDTO0.getRoutes().size());
            TestUserDTO.TestRouteDTO testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
            Assert.assertEquals("ru2",testRouteDTO0.getId());
        }
        {

            TestUserDTO u2 = list.stream().filter(o -> Objects.equals("u3", o.getId())).findFirst().orElse(null);
            Assert.assertNotNull(u2);
            List<TestUserDTO.TestRoleDTO> roles = u2.getRoles();
            Assert.assertNotNull(roles);
            Assert.assertEquals(1,roles.size());
            TestUserDTO.TestRoleDTO testRoleDTO0 = roles.get(0);
            Assert.assertEquals("r3",testRoleDTO0.getId());
            Assert.assertNotNull(testRoleDTO0.getRoutes());
            Assert.assertEquals(1,testRoleDTO0.getRoutes().size());
            TestUserDTO.TestRouteDTO testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
            Assert.assertEquals("ru3",testRouteDTO0.getId());
        }

        System.out.println(list);

    }
}
