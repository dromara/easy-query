package com.easy.query.test;

import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.proxy.TopicTypeVOProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.testrelation.TestJoinEntity;
import com.easy.query.test.entity.testrelation.TestRoleEntity;
import com.easy.query.test.entity.testrelation.TestRouteEntity;
import com.easy.query.test.entity.testrelation.TestUserEntity;
import com.easy.query.test.entity.testrelation.vo.TestRoleDTO1;
import com.easy.query.test.entity.testrelation.vo.TestRouteDTO1;
import com.easy.query.test.entity.testrelation.vo.TestUserDTO;
import com.easy.query.test.entity.testrelation.vo.TestUserDTO1;
import com.easy.query.test.entity.testrelation.vo.proxy.TestRoleDTO1Proxy;
import com.easy.query.test.entity.testrelation.vo.proxy.TestUserDTO1Proxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

        {


            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            List<TestUserDTO> list = easyEntityQuery.queryable(TestUserEntity.class)
                    .selectAutoInclude(TestUserDTO.class)
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(5,listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT t.`id`,t.`name`,t.`password` FROM `t_test_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `first_id`,`second_id` FROM `t_test_join` WHERE `first_id` IN (?,?,?) AND `type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("u1(String),u2(String),u3(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT t.`id`,t.`name`,t.`remark` FROM `t_test_role` t WHERE t.`id` IN (?,?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("r1(String),r4(String),r2(String),r3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("SELECT `first_id`,`second_id` FROM `t_test_join` WHERE `first_id` IN (?,?,?,?) AND `type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("r1(String),r2(String),r3(String),r4(String),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("SELECT t.`id`,t.`name`,t.`request_path` FROM `t_test_route` t WHERE t.`id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("ru1(String),ru2(String),ru3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }

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
        }
        {


            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            List<TestUserDTO1> list = easyEntityQuery.queryable(TestUserEntity.class)
                    .includes(t->t.roles(),r->r.includes(x->x.routes()))
                    .select(t -> {
                        TestUserDTO1Proxy result = new TestUserDTO1Proxy();
                        result.selectAll(t);

                        result.roles().set(t.roles(), role -> {
                            TestRoleDTO1Proxy r = new TestRoleDTO1Proxy();
                            r.selectAll(role);
                            r.routes().set(role.routes());
                            return r;
                        });
                        return result;
                    })
                    .toList();


            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
            Assert.assertEquals(5,listenerContext.getJdbcExecuteAfterArgs().size());
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT t.`id`,t.`name`,t.`password` FROM `t_test_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                Assert.assertEquals("SELECT `first_id`,`second_id` FROM `t_test_join` WHERE `first_id` IN (?,?,?) AND `type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("u1(String),u2(String),u3(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                Assert.assertEquals("SELECT t.`id`,t.`name`,t.`remark` FROM `t_test_role` t WHERE t.`id` IN (?,?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("r1(String),r4(String),r2(String),r3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                Assert.assertEquals("SELECT `first_id`,`second_id` FROM `t_test_join` WHERE `first_id` IN (?,?,?,?) AND `type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("r1(String),r2(String),r3(String),r4(String),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
            {
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
                Assert.assertEquals("SELECT t.`id`,t.`name`,t.`request_path` FROM `t_test_route` t WHERE t.`id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("ru1(String),ru2(String),ru3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }

            {

                TestUserDTO1 u1 = list.stream().filter(o -> Objects.equals("u1", o.getId())).findFirst().orElse(null);
                Assert.assertNotNull(u1);
                List<TestRoleDTO1> roles = u1.getRoles();
                Assert.assertNotNull(roles);
                Assert.assertEquals(2,roles.size());
                TestRoleDTO1 testRoleDTO0 = roles.get(0);
                Assert.assertEquals("r1",testRoleDTO0.getId());
                TestRoleDTO1 testRoleDTO1 = roles.get(1);
                Assert.assertEquals("r4",testRoleDTO1.getId());
                Assert.assertNotNull(testRoleDTO0.getRoutes());
                Assert.assertEquals(2,testRoleDTO0.getRoutes().size());
                TestRouteDTO1 testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
                TestRouteDTO1 testRouteDTO1 = testRoleDTO0.getRoutes().get(1);
                Assert.assertEquals("ru1",testRouteDTO0.getId());
                Assert.assertEquals("ru2",testRouteDTO1.getId());
            }
            {

                TestUserDTO1 u2 = list.stream().filter(o -> Objects.equals("u2", o.getId())).findFirst().orElse(null);
                Assert.assertNotNull(u2);
                List<TestRoleDTO1> roles = u2.getRoles();
                Assert.assertNotNull(roles);
                Assert.assertEquals(2,roles.size());
                TestRoleDTO1 testRoleDTO0 = roles.get(0);
                Assert.assertEquals("r2",testRoleDTO0.getId());
                TestRoleDTO1 testRoleDTO1 = roles.get(1);
                Assert.assertEquals("r4",testRoleDTO1.getId());
                Assert.assertNotNull(testRoleDTO0.getRoutes());
                Assert.assertEquals(1,testRoleDTO0.getRoutes().size());
                TestRouteDTO1 testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
                Assert.assertEquals("ru2",testRouteDTO0.getId());
            }
            {

                TestUserDTO1 u2 = list.stream().filter(o -> Objects.equals("u3", o.getId())).findFirst().orElse(null);
                Assert.assertNotNull(u2);
                List<TestRoleDTO1> roles = u2.getRoles();
                Assert.assertNotNull(roles);
                Assert.assertEquals(1,roles.size());
                TestRoleDTO1 testRoleDTO0 = roles.get(0);
                Assert.assertEquals("r3",testRoleDTO0.getId());
                Assert.assertNotNull(testRoleDTO0.getRoutes());
                Assert.assertEquals(1,testRoleDTO0.getRoutes().size());
                TestRouteDTO1 testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
                Assert.assertEquals("ru3",testRouteDTO0.getId());
            }
        }
//        {
//
//            List<TestUserDTO2> list = easyEntityQuery.queryable(TestUserEntity.class)
//                    .includes(t->t.roles(),r->r.includes(x->x.routes()))
//                    .select(t -> {
//                        TestUserDTO2Proxy result = new TestUserDTO2Proxy();
//                        result.selectAll(t);
//                        SQLQueryable<StringProxy, String> select = t.roles().select(x -> new StringProxy(x.id()));
//                        result.roleIds().set(select);
//                        return result;
//                    })
//                    .toList();
//        }

    }

    @Test
    public void  testLikeColumn(){


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .where((b1, t2) -> {
                        b1.id().like(t2.id());

                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? AND t.`id` LIKE CONCAT('%',t1.`id`,'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .where((b1, t2) -> {
                        b1.id().notLike(t2.id());

                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? AND (NOT t.`id` LIKE CONCAT('%',t1.`id`,'%'))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .where((b1, t2) -> {
                        b1.id().notLike(t2.id());

                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? AND (NOT t.`id` LIKE CONCAT('%',t1.`id`,'%'))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);



            List<BlogEntity> list2 = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .where((b1, t2) -> {
                        b1.id().like(t2.id().subString(1, 2));

                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? AND t.`id` LIKE CONCAT('%',SUBSTR(t1.`id`,2,2),'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<BlogEntity> list23= easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .where((b1, t2) -> {
                        b1.id().nullOrDefault("123").like(t2.id().subString(1, 2));

                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? AND IFNULL(t.`id`,?) LIKE CONCAT('%',SUBSTR(t1.`id`,2,2),'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testLikeColumn2(){


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .where((b1, t2) -> {
                        b1.id().like(t2.stars().setPropertyType(String.class));

                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? AND t.`id` LIKE CONCAT('%',t1.`stars`,'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .where((b1, t2) -> {
                        b1.id().like(t2.stars().toStr());
                        b1.id().like("123");

                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? AND t.`id` LIKE CONCAT('%',CAST(t1.`stars` AS CHAR),'%') AND t.`id` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .where((b1, t2) -> {
                        b1.id().like(t2.stars().toStr());
                        b1.id().like("123");

                    }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? AND t.`id` LIKE CONCAT('%',CAST(t1.`stars` AS CHAR),'%') AND t.`id` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test1119(){
        List<Map<String, Object>> list = easyEntityQuery.queryable(SysUser.class)
                .leftJoin(Topic.class, (s, t2) -> s.id().eq(t2.id()))
                .where((s1, t2) -> s1.id().eq("1"))
                .select((s1, t2) -> new MapTypeProxy().selectAll(s1).selectExpression(t2.title().as("abc")))
                .toList();

        List<Draft1<String>> list1 = easyEntityQuery.queryable(SysUser.class)
                .leftJoin(Topic.class, (s, t2) -> s.id().eq(t2.id()))
                .where((s1, t2) -> {
                    s1.id().eq("1");
                    s1.expression().sql("{0} = IFNULL({1},1)", c -> c.expression(s1.id()).expression(t2.title()));
                })
                .select((s1, t2) -> Select.DRAFT.of(
                        s1.expression().sqlType("IFNULL({0},2)", c -> c.expression(s1.idCard())).setPropertyType(String.class)
                )).toList();

        List<TopicTypeVO> list2 = easyEntityQuery.queryable(SysUser.class)
                .leftJoin(Topic.class, (s, t2) -> s.id().eq(t2.id()))
                .where((s1, t2) -> {
                    s1.id().eq("1");
                    s1.expression().sql("{0} = IFNULL({1},1)", c -> c.expression(s1.id()).expression(t2.title()));
                })
                .select((s1, t2) -> new TopicTypeVOProxy().adapter(r->{
                    r.id().set(
                            s1.expression().sqlType("IFNULL({0},2)", c -> c.expression(s1.idCard())).setPropertyType(String.class)
                    );
                })).toList();

    }

//    @Test
//    public void test(){
//        List<BlogPartitionEntityVO> list = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> b.star().lt(12))
//                .select(b -> {
//                    Expression expression = b.expression();
//                    BlogPartitionEntityVOProxy r = new BlogPartitionEntityVOProxy();
//                    r.selectAll(b);
//                    r.num().set(
//                            expression.sqlType("ROW_NUMBER() OVER(PARTITION BY {0} ORDER BY {1} DESC)", c -> c.expression(b.title()).expression(b.score()))
//                                    .setPropertyType(Integer.class)
//                    );
//                    return r;
//                })
//                .where(b -> b.num().lt(1))
//                .toList();
//    }

    @Test
     public void test2(){
         EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                 .setDefaultDataSource(dataSource)
                 .optionConfigure(op -> {
                     op.setDeleteThrowError(false);
                     op.setExecutorCorePoolSize(1);
                     op.setExecutorMaximumPoolSize(2);
                     op.setMaxShardingQueryLimit(1);
                 })
                 .useDatabaseConfigure(new OracleDatabaseConfiguration())
                 .build();



            String sql = new DefaultEasyEntityQuery(easyQueryClient)
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                    .where((t1, b2) -> t1.id().like(b2.title()))
                    .toSQL();
            Assert.assertEquals("SELECT t.\"id\",t.\"stars\",t.\"title\",t.\"create_time\" FROM \"t_topic\" t LEFT JOIN \"t_blog\" t1 ON t1.\"deleted\" = ? AND t.\"id\" = t1.\"id\" WHERE t.\"id\" LIKE ('%'||TO_CHAR(t1.\"title\")||'%')",sql);
     }

     @Test
     public void testMapQuery(){
         {

             ListenerContext listenerContext = new ListenerContext();
             listenerContextManager.startListen(listenerContext);

             List<Map> list = easyQueryClient.queryable(Map.class)
                     .asTable("t_topic").where(m -> m.eq("id", 1))
                     .toList();
             Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
             JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
             Assert.assertEquals("SELECT * FROM `t_topic` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
             Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
             listenerContextManager.clear();
//             ArrayList<String> columns = new ArrayList<>();
//             columns.add("name");
//             columns.add("id");
//             easyQueryClient.queryable(Map.class)
//                     .asTable("t_topic").where(m -> m.eq("id", 1))
//                     .select(m ->{
//                         for (String column : columns) {
//                             m.column(column);
//                         }
//                     });
         }
         {

             ListenerContext listenerContext = new ListenerContext();
             listenerContextManager.startListen(listenerContext);

             List<Map<String, Object>> list = easyQueryClient.queryable("t_topic")
                     .where(m -> m.eq("id", 1))
                     .toList();
             Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
             JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
             Assert.assertEquals("SELECT * FROM `t_topic` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
             Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
             listenerContextManager.clear();
         }
     }

}
