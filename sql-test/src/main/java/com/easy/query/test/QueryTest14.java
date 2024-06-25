package com.easy.query.test;

import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLConstantExpression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.sqllite.config.SQLLiteDatabaseConfiguration;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.proxy.TopicTypeVOProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MultiColumnEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAuto;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.company.ValueCompanyAddress;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
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
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.vo.BlogEntityVO1;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * create time 2024/3/8 11:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest14 extends BaseTest {

    @Test
    public void test111() {

//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    Filter filter = t.getEntitySQLContext().getFilter();
//                    filter.eq(t.getTable(), "id", "123");
//                    filter.and(f -> {
//                        f.eq(t.getTable(), "id", "123")
//                                .or()
//                                .eq(t.getTable(), "title", "123");
//                    });
//                })
//                .toList();


        EntityQueryable<TopicProxy, Topic> queryable = easyEntityQuery.queryable(Topic.class);

        ClientQueryable<Topic> clientQueryable = queryable.getClientQueryable();
        clientQueryable.where(t->{
            t.eq("id","123");
        });

        List<Topic> list1 = queryable.orderBy(x -> {
            x.createTime().desc();
        }).toList();

    }

    @Test
    public void test1() {
        easyEntityQuery.deletable(TestUserEntity.class)
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("u1", "u2", "u3"))
                .executeRows();
        easyEntityQuery.deletable(TestRoleEntity.class)
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("r1", "r2", "r3", "r4"))
                .executeRows();
        easyEntityQuery.deletable(TestRouteEntity.class)
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("ru1", "ru2", "ru3"))
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
            Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
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
                Assert.assertEquals(2, roles.size());
                TestUserDTO.TestRoleDTO testRoleDTO0 = roles.get(0);
                Assert.assertEquals("r1", testRoleDTO0.getId());
                TestUserDTO.TestRoleDTO testRoleDTO1 = roles.get(1);
                Assert.assertEquals("r4", testRoleDTO1.getId());
                Assert.assertNotNull(testRoleDTO0.getRoutes());
                Assert.assertEquals(2, testRoleDTO0.getRoutes().size());
                TestUserDTO.TestRouteDTO testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
                TestUserDTO.TestRouteDTO testRouteDTO1 = testRoleDTO0.getRoutes().get(1);
                Assert.assertEquals("ru1", testRouteDTO0.getId());
                Assert.assertEquals("ru2", testRouteDTO1.getId());
            }
            {

                TestUserDTO u2 = list.stream().filter(o -> Objects.equals("u2", o.getId())).findFirst().orElse(null);
                Assert.assertNotNull(u2);
                List<TestUserDTO.TestRoleDTO> roles = u2.getRoles();
                Assert.assertNotNull(roles);
                Assert.assertEquals(2, roles.size());
                TestUserDTO.TestRoleDTO testRoleDTO0 = roles.get(0);
                Assert.assertEquals("r2", testRoleDTO0.getId());
                TestUserDTO.TestRoleDTO testRoleDTO1 = roles.get(1);
                Assert.assertEquals("r4", testRoleDTO1.getId());
                Assert.assertNotNull(testRoleDTO0.getRoutes());
                Assert.assertEquals(1, testRoleDTO0.getRoutes().size());
                TestUserDTO.TestRouteDTO testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
                Assert.assertEquals("ru2", testRouteDTO0.getId());
            }
            {

                TestUserDTO u2 = list.stream().filter(o -> Objects.equals("u3", o.getId())).findFirst().orElse(null);
                Assert.assertNotNull(u2);
                List<TestUserDTO.TestRoleDTO> roles = u2.getRoles();
                Assert.assertNotNull(roles);
                Assert.assertEquals(1, roles.size());
                TestUserDTO.TestRoleDTO testRoleDTO0 = roles.get(0);
                Assert.assertEquals("r3", testRoleDTO0.getId());
                Assert.assertNotNull(testRoleDTO0.getRoutes());
                Assert.assertEquals(1, testRoleDTO0.getRoutes().size());
                TestUserDTO.TestRouteDTO testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
                Assert.assertEquals("ru3", testRouteDTO0.getId());
            }
        }
        {


            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            List<TestUserDTO1> list = easyEntityQuery.queryable(TestUserEntity.class)
                    .includes(t -> t.roles(), r -> r.includes(x -> x.routes()))
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
            Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
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
                Assert.assertEquals(2, roles.size());
                TestRoleDTO1 testRoleDTO0 = roles.get(0);
                Assert.assertEquals("r1", testRoleDTO0.getId());
                TestRoleDTO1 testRoleDTO1 = roles.get(1);
                Assert.assertEquals("r4", testRoleDTO1.getId());
                Assert.assertNotNull(testRoleDTO0.getRoutes());
                Assert.assertEquals(2, testRoleDTO0.getRoutes().size());
                TestRouteDTO1 testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
                TestRouteDTO1 testRouteDTO1 = testRoleDTO0.getRoutes().get(1);
                Assert.assertEquals("ru1", testRouteDTO0.getId());
                Assert.assertEquals("ru2", testRouteDTO1.getId());
            }
            {

                TestUserDTO1 u2 = list.stream().filter(o -> Objects.equals("u2", o.getId())).findFirst().orElse(null);
                Assert.assertNotNull(u2);
                List<TestRoleDTO1> roles = u2.getRoles();
                Assert.assertNotNull(roles);
                Assert.assertEquals(2, roles.size());
                TestRoleDTO1 testRoleDTO0 = roles.get(0);
                Assert.assertEquals("r2", testRoleDTO0.getId());
                TestRoleDTO1 testRoleDTO1 = roles.get(1);
                Assert.assertEquals("r4", testRoleDTO1.getId());
                Assert.assertNotNull(testRoleDTO0.getRoutes());
                Assert.assertEquals(1, testRoleDTO0.getRoutes().size());
                TestRouteDTO1 testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
                Assert.assertEquals("ru2", testRouteDTO0.getId());
            }
            {

                TestUserDTO1 u2 = list.stream().filter(o -> Objects.equals("u3", o.getId())).findFirst().orElse(null);
                Assert.assertNotNull(u2);
                List<TestRoleDTO1> roles = u2.getRoles();
                Assert.assertNotNull(roles);
                Assert.assertEquals(1, roles.size());
                TestRoleDTO1 testRoleDTO0 = roles.get(0);
                Assert.assertEquals("r3", testRoleDTO0.getId());
                Assert.assertNotNull(testRoleDTO0.getRoutes());
                Assert.assertEquals(1, testRoleDTO0.getRoutes().size());
                TestRouteDTO1 testRouteDTO0 = testRoleDTO0.getRoutes().get(0);
                Assert.assertEquals("ru3", testRouteDTO0.getId());
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
    public void testLikeColumn() {


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


            List<BlogEntity> list23 = easyEntityQuery.queryable(BlogEntity.class)
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
    public void testLikeColumn2() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                    .where((b1, t2) -> {
                        b1.id().like(t2.stars().asAnyType(String.class));

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
    public void test1119() {
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
                        s1.expression().sqlType("IFNULL({0},2)", c -> c.expression(s1.idCard())).asAnyType(String.class)
                )).toList();

        List<TopicTypeVO> list2 = easyEntityQuery.queryable(SysUser.class)
                .leftJoin(Topic.class, (s, t2) -> s.id().eq(t2.id()))
                .where((s1, t2) -> {
                    s1.id().eq("1");
                    s1.expression().sql("{0} = IFNULL({1},1)", c -> c.expression(s1.id()).expression(t2.title()));
                })
                .select((s1, t2) -> new TopicTypeVOProxy().adapter(r -> {
                    r.id().set(
                            s1.expression().sqlType("IFNULL({0},2)", c -> c.expression(s1.idCard())).asAnyType(String.class)
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
//
    @Test
    public void testxxxxx(){
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(2);
                    op.setMaxShardingQueryLimit(1);
                })
                .useDatabaseConfigure(new OracleDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                defaultEasyEntityQuery.insertable(new Topic())
                        .setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS)
                        .onConflictThen(o->o.FETCHER.stars(),o->o.FETCHER.allFields())
                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("MERGE INTO \"t_topic\" t1 USING (SELECT ? AS \"id\",? AS \"stars\",? AS \"title\",? AS \"create_time\" FROM DUAL ) t2 ON (t1.\"create_time\" = t2.\"create_time\" AND t1.\"id\" = t2.\"id\" AND t1.\"stars\" = t2.\"stars\" AND t1.\"title\" = t2.\"title\") WHEN NOT MATCHED THEN INSERT (\"id\",\"stars\",\"title\",\"create_time\") VALUES (t2.\"id\",t2.\"stars\",t2.\"title\",t2.\"create_time\")", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("null(null),null(null),null(null),null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }


    @Test
    public void test2() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(2);
                    op.setMaxShardingQueryLimit(1);
                })
                .useDatabaseConfigure(new OracleDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        {

            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                try {

                    List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                            .where(b -> b.id().eq("123"))
                            .groupBy(b -> GroupKeys.TABLE1.of(b.title()))
                            .select(group -> {
                                BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                                blogEntityProxy.title().set(group.key1());
                                blogEntityProxy.star().set(group.groupTable().id().intCount());
                                return blogEntityProxy;
                            }).limit(10).toList();
                } catch (Exception ignore) {
                }
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT rt.* FROM (SELECT t.\"title\" AS \"title\",COUNT(t.\"id\") AS \"star\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ? GROUP BY t.\"title\") rt WHERE ROWNUM < 11", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }


            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                try {

                    List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                            .where(b -> b.id().eq("123"))
                            .groupBy(b -> GroupKeys.TABLE1.of(b.title()))
                            .select(group -> {
                                BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                                blogEntityProxy.title().set(group.key1());
                                blogEntityProxy.star().set(group.groupTable().id().intCount());
                                return blogEntityProxy;
                            }).limit(20, 10).toList();
                } catch (Exception ignore) {
                }
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \"__rownum__\" FROM (SELECT t.\"title\" AS \"title\",COUNT(t.\"id\") AS \"star\", ROWNUM AS \"__rownum__\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ? GROUP BY t.\"title\") rt WHERE ROWNUM < 31) rt1 WHERE rt1.\"__rownum__\" > 20", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                        .where(b -> b.id().eq("123"))
                        .groupBy(b -> GroupKeys.TABLE1.of(b.title()))
                        .select(group -> {
                            BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                            blogEntityProxy.title().set(group.key1());
                            blogEntityProxy.star().set(group.groupTable().id().intCount());
                            return blogEntityProxy;
                        }).orderBy(b -> b.star().asc()).limit(20, 10).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \"__rownum__\" FROM (SELECT t1.\"title\" AS \"title\",t1.\"star\" AS \"star\" FROM (SELECT t.\"title\" AS \"title\",COUNT(t.\"id\") AS \"star\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ? GROUP BY t.\"title\") t1 ORDER BY t1.\"star\" ASC) rt WHERE ROWNUM < 31) rt1 WHERE rt1.\"__rownum__\" > 20", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                        .where(b -> b.id().eq("123"))
                        .orderBy(b -> b.star().asc())
                        .groupBy(b -> GroupKeys.TABLE1.of(b.title()))
                        .select(group -> {
                            BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                            blogEntityProxy.title().set(group.key1());
                            blogEntityProxy.star().set(group.groupTable().id().intCount());
                            return blogEntityProxy;
                        }).limit(20, 10).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \"__rownum__\" FROM (SELECT t.\"title\" AS \"title\",COUNT(t.\"id\") AS \"star\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ? GROUP BY t.\"title\" ORDER BY t.\"star\" ASC) rt WHERE ROWNUM < 31) rt1 WHERE rt1.\"__rownum__\" > 20", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {


            String sql = defaultEasyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                    .where((t1, b2) -> t1.id().like(b2.title()))
                    .toSQL();
            Assert.assertEquals("SELECT t.\"id\",t.\"stars\",t.\"title\",t.\"create_time\" FROM \"t_topic\" t LEFT JOIN \"t_blog\" t1 ON t1.\"deleted\" = ? AND t.\"id\" = t1.\"id\" WHERE t.\"id\" LIKE ('%'||TO_CHAR(t1.\"title\")||'%')", sql);

        }
        {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            String sql = defaultEasyEntityQuery.insertable(topicAuto)
                    .asTableLink(o -> o + "1")
                    .onConflictThen(o -> o.FETCHER.stars().title(), x -> x.title())
                    .toSQL(topicAuto);


            Assert.assertEquals("MERGE INTO 1\"t_topic_auto\" t1 USING (SELECT ? AS \"stars\",? AS \"title\",? AS \"create_time\" FROM DUAL ) t2 ON (t1.\"title\" = t2.\"title\") WHEN MATCHED THEN UPDATE SET t1.\"stars\" = t2.\"stars\" WHEN NOT MATCHED THEN INSERT (\"stars\",\"title\",\"create_time\") VALUES (t2.\"stars\",t2.\"title\",t2.\"create_time\")", sql);

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                Topic topicAuto = new Topic();
                topicAuto.setId("111xxa");
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(null, o -> o.FETCHER.stars().id())
                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("MERGE INTO \"t_topic\" t1 USING (SELECT ? AS \"id\",? AS \"stars\",? AS \"title\",? AS \"create_time\" FROM DUAL ) t2 ON (t1.\"stars\" = t2.\"stars\" AND t1.\"id\" = t2.\"id\") WHEN NOT MATCHED THEN INSERT (\"id\",\"stars\",\"title\",\"create_time\") VALUES (t2.\"id\",t2.\"stars\",t2.\"title\",t2.\"create_time\")", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("111xxa(String),999(Integer),title999(String),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                Topic topicAuto = new Topic();
                topicAuto.setId("111xxa");
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(null, o -> o.FETCHER.stars())
                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("MERGE INTO \"t_topic\" t1 USING (SELECT ? AS \"id\",? AS \"stars\",? AS \"title\",? AS \"create_time\" FROM DUAL ) t2 ON (t1.\"stars\" = t2.\"stars\") WHEN NOT MATCHED THEN INSERT (\"id\",\"stars\",\"title\",\"create_time\") VALUES (t2.\"id\",t2.\"stars\",t2.\"title\",t2.\"create_time\")", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("111xxa(String),999(Integer),title999(String),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(null, o -> o.FETCHER.stars().id())
                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("MERGE INTO \"t_topic_auto\" t1 USING (SELECT ? AS \"stars\",? AS \"title\",? AS \"create_time\" FROM DUAL ) t2 ON (t1.\"stars\" = t2.\"stars\") WHEN NOT MATCHED THEN INSERT (\"stars\",\"title\",\"create_time\") VALUES (t2.\"stars\",t2.\"title\",t2.\"create_time\")", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("999(Integer),title999(String),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                Assert.assertNull(topicAuto.getId());
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(o -> o.FETCHER.stars().title())
                        .executeRows();
            } catch (Exception ignore) {
                Assert.assertTrue(ignore instanceof EasyQueryInvalidOperationException);
                Assert.assertEquals("TopicAuto no constraint property", ignore.getMessage());
            }
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("MERGE INTO \"t_topic_auto\" t1 USING (SELECT ? AS \"stars\",? AS \"title\",? AS \"create_time\" FROM DUAL ) t2 ON (t1.\"id\" = t2.\"id\") WHEN MATCHED THEN UPDATE SET t1.\"stars\" = t2.\"stars\",t1.\"title\" = t2.\"title\" WHEN NOT MATCHED THEN INSERT (\"stars\",\"title\",\"create_time\") VALUES (t2.\"stars\",t2.\"title\",t2.\"create_time\")", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("999(Integer),title999(String),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                Assert.assertNull(topicAuto.getId());
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(o -> o.FETCHER.stars().title(), x -> x.id())
                        .executeRows();
            } catch (Exception ignore) {
                Assert.assertTrue(ignore instanceof EasyQueryInvalidOperationException);
                Assert.assertEquals("TopicAuto no constraint property", ignore.getMessage());
            }
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("MERGE INTO \"t_topic_auto\" t1 USING (SELECT ? AS \"stars\",? AS \"title\",? AS \"create_time\" FROM DUAL ) t2 ON (t1.\"id\" = t2.\"id\") WHEN MATCHED THEN UPDATE SET t1.\"stars\" = t2.\"stars\",t1.\"title\" = t2.\"title\" WHEN NOT MATCHED THEN INSERT (\"stars\",\"title\",\"create_time\") VALUES (t2.\"stars\",t2.\"title\",t2.\"create_time\")", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("999(Integer),title999(String),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                Topic topicAuto = new Topic();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                Assert.assertNull(topicAuto.getId());
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(o -> o.FETCHER.allFields())
                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("MERGE INTO \"t_topic\" t1 USING (SELECT ? AS \"stars\",? AS \"title\",? AS \"create_time\" FROM DUAL ) t2 ON (t1.\"id\" = t2.\"id\") WHEN MATCHED THEN UPDATE SET t1.\"stars\" = t2.\"stars\",t1.\"title\" = t2.\"title\",t1.\"create_time\" = t2.\"create_time\" WHEN NOT MATCHED THEN INSERT (\"stars\",\"title\",\"create_time\") VALUES (t2.\"stars\",t2.\"title\",t2.\"create_time\")", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("999(Integer),title999(String),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                Assert.assertNull(topicAuto.getId());
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(null)
                        .executeRows();
            } catch (Exception ignore) {
                Assert.assertTrue(ignore instanceof EasyQueryInvalidOperationException);
                Assert.assertEquals("TopicAuto no constraint property", ignore.getMessage());
            }
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
            listenerContextManager.clear();
        }

    }

    @Test
    public void test3() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(2);
                    op.setMaxShardingQueryLimit(1);
                })
                .useDatabaseConfigure(new SQLLiteDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        {

            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                try {

                    List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                            .where(b -> b.id().eq("123"))
                            .groupBy(b -> GroupKeys.TABLE1.of(b.title()))
                            .select(group -> {
                                BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                                blogEntityProxy.title().set(group.key1());
                                blogEntityProxy.star().set(group.groupTable().id().intCount());
                                return blogEntityProxy;
                            }).limit(10).toList();
                } catch (Exception ignore) {
                }
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t.\"title\" AS \"title\",COUNT(t.\"id\") AS \"star\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ? GROUP BY t.\"title\" LIMIT 10", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }


            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                try {

                    List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                            .where(b -> b.id().eq("123"))
                            .groupBy(b -> GroupKeys.TABLE1.of(b.title()))
                            .select(group -> {
                                BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                                blogEntityProxy.title().set(group.key1());
                                blogEntityProxy.star().set(group.groupTable().id().intCount());
                                return blogEntityProxy;
                            }).limit(20, 10).toList();
                } catch (Exception ignore) {
                }
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT t.\"title\" AS \"title\",COUNT(t.\"id\") AS \"star\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ? GROUP BY t.\"title\" LIMIT 10 OFFSET 20", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                        .where(b -> b.id().eq("123"))
                        .groupBy(b -> GroupKeys.TABLE1.of(b.title()))
                        .select(group -> {
                            BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                            blogEntityProxy.title().set(group.key1());
                            blogEntityProxy.star().set(group.groupTable().id().intCount());
                            return blogEntityProxy;
                        }).orderBy(b -> b.star().asc()).limit(20, 10).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t1.\"title\" AS \"title\",t1.\"star\" AS \"star\" FROM (SELECT t.\"title\" AS \"title\",COUNT(t.\"id\") AS \"star\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ? GROUP BY t.\"title\") t1 ORDER BY t1.\"star\" ASC LIMIT 10 OFFSET 20", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                        .where(b -> b.id().eq("123"))
                        .orderBy(b -> b.star().asc())
                        .groupBy(b -> GroupKeys.TABLE1.of(b.title()))
                        .select(group -> {
                            BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                            blogEntityProxy.title().set(group.key1());
                            blogEntityProxy.star().set(group.groupTable().id().intCount());
                            return blogEntityProxy;
                        }).limit(20, 10).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.\"title\" AS \"title\",COUNT(t.\"id\") AS \"star\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ? GROUP BY t.\"title\" ORDER BY t.\"star\" ASC LIMIT 10 OFFSET 20", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {


            String sql = defaultEasyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                    .where((t1, b2) -> t1.id().like(b2.title()))
                    .toSQL();
            Assert.assertEquals("SELECT t.\"id\",t.\"stars\",t.\"title\",t.\"create_time\" FROM \"t_topic\" t LEFT JOIN \"t_blog\" t1 ON t1.\"deleted\" = ? AND t.\"id\" = t1.\"id\" WHERE t.\"id\" LIKE CONCAT('%',t1.\"title\",'%')", sql);

        }
        {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            String sql = defaultEasyEntityQuery.insertable(topicAuto)
                    .asTableLink(o -> o + "1")
                    .onConflictThen(o -> o.FETCHER.stars().title(), x -> x.title())
                    .toSQL(topicAuto);


            Assert.assertEquals("REPLACE INTO 1\"t_topic_auto\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?)", sql);

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                Topic topicAuto = new Topic();
                topicAuto.setId("111xxa");
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(null, o -> o.FETCHER.stars().id())
                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("INSERT INTO \"t_topic\" (\"id\",\"stars\",\"title\",\"create_time\") SELECT ?,?,?,? WHERE NOT EXISTS(SELECT 1 FROM \"t_topic\" t  WHERE (t.\"stars\" = ? AND t.\"id\" = ?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("111xxa(String),999(Integer),title999(String),2020-01-01T01:01(LocalDateTime),999(Integer),111xxa(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                Topic topicAuto = new Topic();
                topicAuto.setId("111xxa");
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(null, o -> o.FETCHER.stars())
                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("INSERT INTO \"t_topic\" (\"id\",\"stars\",\"title\",\"create_time\") SELECT ?,?,?,? WHERE NOT EXISTS(SELECT 1 FROM \"t_topic\" t  WHERE (t.\"stars\" = ?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("111xxa(String),999(Integer),title999(String),2020-01-01T01:01(LocalDateTime),999(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(null, o -> o.FETCHER.stars().id())
                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("INSERT INTO \"t_topic_auto\" (\"stars\",\"title\",\"create_time\") SELECT ?,?,? WHERE NOT EXISTS(SELECT 1 FROM \"t_topic_auto\" t  WHERE (t.\"stars\" = ?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("999(Integer),title999(String),2020-01-01T01:01(LocalDateTime),999(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                Assert.assertNull(topicAuto.getId());
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(o -> o.FETCHER.stars().title())
                        .executeRows();
            } catch (Exception ignore) {
                Assert.assertTrue(ignore instanceof EasyQuerySQLCommandException);
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("REPLACE INTO \"t_topic_auto\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("999(Integer),title999(String),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                Assert.assertNull(topicAuto.getId());
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(o -> o.FETCHER.stars().title(), x -> x.id())
                        .executeRows();
            } catch (Exception ignore) {
                Assert.assertTrue(ignore instanceof EasyQuerySQLCommandException);
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("REPLACE INTO \"t_topic_auto\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("999(Integer),title999(String),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                Topic topicAuto = new Topic();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                Assert.assertNull(topicAuto.getId());
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(o -> o.FETCHER.allFields())
                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("REPLACE INTO \"t_topic\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("999(Integer),title999(String),2020-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(999);
                topicAuto.setTitle("title" + 999);
                topicAuto.setCreateTime(LocalDateTime.of(2020, 1, 1, 1, 1));
                Assert.assertNull(topicAuto.getId());
                long l = defaultEasyEntityQuery.insertable(topicAuto)
                        .onConflictThen(null)
                        .executeRows();
            } catch (Exception ignore) {
                Assert.assertTrue(ignore instanceof EasyQueryInvalidOperationException);
                Assert.assertEquals("TopicAuto no constraint property", ignore.getMessage());
            }
            Assert.assertNull(listenerContext.getJdbcExecuteAfterArg());
            listenerContextManager.clear();
        }

    }

    @Test
    public void testMapQuery() {

        String sql = easyQueryClient.queryable("t_topic")
                .select(Map.class, t -> {
                    t.columnAs("col001", "姓名");
                    t.columnAs("col002", "年龄");
                    t.sqlFuncAs(
                            t.fx().dateTimeFormat("create_time", "yyyy-MM-dd"),
                            "创建时间"
                    );
                    t.sqlFuncAs(
                            t.fx().count(x -> x.format(1)),
                            "c1"
                    );
                    t.columnCountAs("id", "c2");
                }).toSQL();
        System.out.println(sql);

        String sql1 = easyQueryClient.queryable("t_topic")
                .groupBy(t -> t.column("col001"))
                .select(Map.class, t -> {
                    t.columnAs("col001", "姓名");
                    t.sqlFuncAs(
                            t.fx().join("col002", ","),
                            "年龄集合"
                    );
                }).toSQL();
        System.out.println(sql1);

        List<Draft2<String, String>> list1 = easyEntityQuery.queryable(Topic.class)
                .groupBy(t -> GroupKeys.TABLE1.of(t.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.groupTable().id().join(",")
                )).toList();

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
//                     .select(String.class,m ->{
//                         for (String column : columns) {
//                             m.column(column);
//                         }
//                         m.sqlNativeSegment("id as 'a123'");
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

//    @Test
//    public void testxx(){
//
//
//        ListenerContext listenerContext = new ListenerContext();
//        listenerContextManager.startListen(listenerContext);
//
//
//        List<TopicTypeVO> list2 = easyEntityQuery.queryable(SysUser.class)
//                .leftJoin(Topic.class, (s, t2) -> s.id().eq(t2.id()))
//                .where((s1, t2) -> {
//                    s1.id().eq("1");
//                    s1.expression().sqlExecutor("{0} = IFNULL({1},1)")
//                                    .expression(s1.id())
//                                            .expression(t2.title())
//                            .executeSQL();
//                })
//                .select((s1, t2) -> new TopicTypeVOProxy().adapter(r -> {
//                    r.id().set(
//                            s1.expression().sqlType("IFNULL({0},2)", c -> c.expression(s1.idCard())).setPropertyType(String.class)
//                    );
//                })).toList();
//        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//        Assert.assertEquals("SELECT * FROM `t_topic` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//        listenerContextManager.clear();
//    }

//    @Test
//    public void test1x(){
////        ConnectionManager connectionManager = easyEntityQuery.getRuntimeContext().getConnectionManager();
////        List<EasyConnection> easyConnections = connectionManager.getEasyConnections(1, "ds0", ConnectionStrategyEnum.ShareConnection);
////
////        EasyConnection easyConnection = easyConnections.get(0);
////        try {
////            Connection connection = easyConnection.getConnection();
////        }finally {
////            connectionManager.closeEasyConnection(easyConnection);
////        }
//
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .toList();
//        list.forEach(o->o.setStatus(1));
//        easyEntityQuery.updatable(list)
//                .setColumns(b -> b.FETCHER.status())
//                .executeRows();
//    }
//    @Test
//    @EasyQueryTrack//如果默认配置defaultTrack=true那么springboot下只需要添加这个注解
//    public void test1x2(){
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .toList();
//        list.forEach(o->o.setStatus(1));
//        easyEntityQuery.updatable(list)
////                .setColumns(b -> b.FETCHER.status())
//                .executeRows();
//    }
//    @Test
//    @EasyQueryTrack//springboot下只需要添加这个注解
//    public void test1x23(){
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .asTracking()//如果默认配置defaultTrack=false那么可以手动使用tracking来实现数据追踪
//                .toList();
//        list.forEach(o->o.setStatus(1));
//        easyEntityQuery.updatable(list)
////                .setColumns(b -> b.FETCHER.status())
//                .executeRows();
//
//
//        easyEntityQuery.updatable(BlogEntity.class)
//                .setColumns(b -> b.status().set(1))
//                .where(b -> b.id().in(Collections.emptyList()))
//                .executeRows();
//        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> b.id().in(Collections.emptyList()))
//                .toList();
//
//
//        List<BlogEntity> blogEntities = updateAndReturn(BlogEntity.class, b -> b.status().set(1), b ->b.id().eq("1"));
//    }
//
//    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> List<T>
//    updateAndReturn(Class<T> entityClass, SQLExpression1<TProxy> columnSetExpression,SQLExpression1<TProxy> whereExpression){
//        easyEntityQuery.updatable(entityClass)
//                .setColumns(columnSetExpression)
//                .where(whereExpression)
//                .executeRows();
//        return easyEntityQuery.queryable(entityClass)
//                .where(whereExpression)
//                .toList();
//    }

    @Test
    public void testClose() {
        List<Map<String, Object>> maps = easyEntityQuery.sqlQueryMap("select * from t_topic");
        System.out.println(maps);

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntityVO1> list = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
                .orderByObject(new QueryTest3.UISort(new HashMap<>()))
                .select(BlogEntityVO1.class, (b1, t2) -> Select.of(
                        t2.FETCHER.allFields(),
                        b1.FETCHER.order().as(BlogEntityVO1::getTop)
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`order` AS `top` FROM `t_blog` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void testDynamicTable() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            LocalDateTime star = LocalDateTime.of(2022, 1, 1, 1, 1);
            LocalDateTime end = LocalDateTime.of(2023, 1, 1, 1, 1);
            String tableTail = false ? "_cold" : "_hot";
            List<MultiColumnEntity> list = easyEntityQuery.queryable(MultiColumnEntity.class)
                    .asTable(o -> o + tableTail)
                    .where(m -> {
                        if (star != null) {
                            m.col8().toDateTime(LocalDateTime.class).gt(star);
                        }
                        if (end != null) {
                            m.col9().toDateTime(LocalDateTime.class).lt(end);
                        }
                    })
                    .select("*").toList();
        } catch (Exception ignored) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT * FROM `multi_hot` WHERE CAST(`col8` AS DATETIME) > ? AND CAST(`col9` AS DATETIME) < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2022-01-01T01:01(LocalDateTime),2023-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testDynamicTable2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {


            MapQueryable bbb = easyQueryClient.mapQueryable().asTable("bbb");
            List<Map<String, Object>> list = easyQueryClient.mapQueryable()
                    .asTable("aaa")
                    .select(o -> {
                        ColumnAsSelector<?, ?> asSelector = o.getAsSelector(0);
                        asSelector.column("id");
                    }).join(MultiTableTypeEnum.LEFT_JOIN, bbb, f -> {
                        WherePredicate<?> wherePredicate1 = f.getWherePredicate(0);
                        WherePredicate<?> wherePredicate2 = f.getWherePredicate(1);
                        wherePredicate1.eq(wherePredicate2, "id", "id");

                    }).toList();
        } catch (Exception ignored) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id` AS `id` FROM (SELECT t.`id` AS `id` FROM `aaa` t) t1 LEFT JOIN (SELECT * FROM `bbb` t2) t3 ON t1.`id` = t3.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("2022-01-01T01:01(LocalDateTime),2023-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
//        List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
//                .asAlias("topic")
//                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
//                .asAlias("blog")
//                .where((t1, b2) -> {
//                    t1.id().eq("123");
//                    b2.title().eq("123");
//                })
//                .toList();

//        List<Topic> list = easyQueryClient.queryable(Topic.class)
//                .where(t -> {
//                    t.sqlNativeSegment("STR_TO_DATE({0},'%Y-%m-%d-%H-%i-%s.%f') > STR_TO_DATE({1},'%Y-%m-%d-%H-%i-%s.%f')", c -> {
//                        c.keepStyle();
//                        c.expression("createTime").expression("createTime");
//                    });
//                }).toList();
//        Class<Map<String,Object>> result= EasyObjectUtil.typeCastNullable(Map.class);
//        EasyPageResult<Map<String,Object>> pageResult = easyQueryClient.queryable(Topic.class)
////                .where(t -> {
////                    t.sqlNativeSegment("STR_TO_DATE({0},'%Y-%m-%d-%H-%i-%s.%f') > STR_TO_DATE({1},'%Y-%m-%d-%H-%i-%s.%f')", c -> {
////                        c.keepStyle();
////                        c.expression("createTime").expression("createTime");
////                    });
////                })
//                .select(result)
//                .toPageResult(1, 2);
//        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    Expression expression = t.expression();
//                    expression.sql("STR_TO_DATE({0},'%Y-%m-%d-%H-%i-%s.%f') > STR_TO_DATE({1},'%Y-%m-%d-%H-%i-%s.%f')",c->{
//                        c.keepStyle();
//                        SQLNativeExpressionContext propContext = c.getSQLNativeExpressionContext();
//                        propContext.expression(t.getTable(),"createTime");
//                        propContext.expression(t.getTable(),"createTime");
//                    });
//                }).toList();


//        QueryRuntimeContext runtimeContext = easyQueryClient.getRuntimeContext();
//        ClientQueryable<Map<String, Object>> tTopic1 = easyQueryClient.queryable("t_topic1");
//        EntityTableExpressionBuilder mainTable = tTopic1.getSQLEntityExpressionBuilder().getTable(0);
//        SQLExpressionProviderImpl<Object> mainTableProvider = new SQLExpressionProviderImpl<>(0, tTopic1.getSQLEntityExpressionBuilder());
//        {
//
//            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(Map.class);
//            EntityTableExpressionBuilder sqlTable = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.LEFT_JOIN, runtimeContext);
//            tTopic1.getSQLEntityExpressionBuilder().addSQLEntityTableExpression(sqlTable);
//            sqlTable.setTableNameAs(o->"t_topic2");
//            WherePredicateImpl<Object> objectWherePredicate = new WherePredicateImpl<>(mainTable.getEntityTable(), mainTableProvider.getOnWhereFilterContext());
//            objectWherePredicate.eq(new SimpleEntitySQLTableOwner<>(sqlTable.getEntityTable()),"id","id");
//        }
//        {
//            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(Map.class);
//            EntityTableExpressionBuilder sqlTable = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.LEFT_JOIN, runtimeContext);
//            tTopic1.getSQLEntityExpressionBuilder().addSQLEntityTableExpression(sqlTable);
//            sqlTable.setTableNameAs(o->"t_topic3");
//            WherePredicateImpl<Object> objectWherePredicate = new WherePredicateImpl<>(mainTable.getEntityTable(), mainTableProvider.getOnWhereFilterContext());
//            objectWherePredicate.eq(new SimpleEntitySQLTableOwner<>(sqlTable.getEntityTable()),"name","age");
//        }
//        EntityTableExpressionBuilder table = tTopic1.getSQLEntityExpressionBuilder().getTable(1);
//        WherePredicateImpl<?> t1WherePredicate = new WherePredicateImpl<>(table.getEntityTable(), mainTableProvider.getOnWhereFilterContext());
//        t1WherePredicate.eq("id",123);
//        List<Map<String, Object>> list = tTopic1.toList();
//easyQueryClient.queryable(Topic.class)
//        .leftJoin(BlogEntity.class,(t, b2) -> {
//            WherePredicate<Topic> t1 = t;
//            WherePredicate<BlogEntity> b21 = b2;
//            t1.eq(b21,"id","id");
//        })
//        .where((t1, b2) -> {
//            WherePredicate<BlogEntity> b21 = b2;
//            b21.eq("name","456");
//        });
//        MapQueryable tTopic1 = easyQueryClient.mapQueryable()
//                .asTable("t_topic1");
//        List<Map<String, Object>> list = easyQueryClient.mapQueryable()
//                .asTable("t_topic1")
//                .join(MultiTableTypeEnum.LEFT_JOIN,tTopic1, f -> {
//                    WherePredicate<?> t1 = f.getWherePredicate(0);
//                    WherePredicate<?> t2 = f.getWherePredicate(1);
//                    t1.eq(t2, "id", "id");
//                })
//                .asTable("t_topic2")
//                .where(f->{
//                    WherePredicate<?> wherePredicate = f.getWherePredicate(1);
//                    wherePredicate.eq("name","456");
//                })
//                .select(f->{
//                    ColumnSelector<?> selector = f.getSelector(0);
//                    ColumnAsSelector<?, ?> asSelector = f.getAsSelector(1);
//                    selector.column("id");
//                    asSelector.columnAs("name","name123");
//                })
//                .toList();
//
//easyQueryClient.queryable(Topic.class)
//        .leftJoin(BlogEntity.class,(t1, t2) -> {})
//        .where((t1, b2) -> {
//
//        })

//        easyQueryClient.queryable(Topic.class)
//                .leftJoin(BlogEntity.class,(t, b2) -> {
//                    WherePredicate<Topic> t1 = t;
//                    EntitySQLTableOwner<BlogEntity> b21 = b2;
//                    t1.eq(b21,"id","id");
//                });
//        easyQueryClient.mapQueryable()
//                .asTable("t_topic1")
//                .join(MultiTableTypeEnum.LEFT_JOIN, f -> {
//                    WherePredicate<?> wherePredicate = f.getWherePredicate(0);
//                    EntitySQLTableOwner<?> table1 = f.getTableOwner(1);
//                    wherePredicate.eq(table1, "id", "id");
//                });
//        List<Map<String, Object>> list1 = easyQueryClient.mapQueryable()
//                .asTable("t_topic1")
//                .join(MultiTableTypeEnum.LEFT_JOIN,
//                        easyQueryClient.mapQueryable()
//                                .asTable("t_topic1")
//                                .join(MultiTableTypeEnum.LEFT_JOIN, f -> {
//                                    EntitySQLTableOwner<?> table1 = f.getTableOwner(1);
//                                    WherePredicate<?> wherePredicate = f.getWherePredicate(0);
//                                    wherePredicate.eq(table1, "id", "id");
//                                })
//                                .asTable("t_topic2")
//                                .where(f->{
//                                    WherePredicate<?> wherePredicate = f.getWherePredicate(1);
//                                    wherePredicate.eq("name","456");
//                                })
//                                .select(f->{
//                                    ColumnSelector<?> selector = f.getSelector(0);
//                                    ColumnAsSelector<?, ?> asSelector = f.getAsSelector(1);
//                                    selector.column("id");
//                                    asSelector.columnAs("name","name123");
//                                })
//                        , f -> {
//                    EntitySQLTableOwner<?> table1 = f.getTableOwner(1);
//                    WherePredicate<?> wherePredicate = f.getWherePredicate(0);
//                    wherePredicate.eq(table1, "id", "id");
//                })
//                .asTable("t_topic2")
//                .where(f->{
//                    WherePredicate<?> wherePredicate = f.getWherePredicate(1);
//                    wherePredicate.eq("name","456");
//                })
//                .select(f->{
//                    ColumnSelector<?> selector = f.getSelector(0);
//                    ColumnAsSelector<?, ?> asSelector = f.getAsSelector(1);
//                    selector.column("id");
//                    asSelector.columnAs("name","name123");
//                })
//                .toList();


    }

    @Test

    public void testOracle() {

        ListenerContextManager listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(2);
                    op.setMaxShardingQueryLimit(1);
                })
                .useDatabaseConfigure(new OracleDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        {

            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                try {

                    List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                            .where(b -> b.createTime().format("yyyy年MM月dd日 HH时mm分ss秒").eq("2022年01月01日 01时01分01秒"))
                            .toList();
                } catch (Exception ignore) {
                }
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND (TO_CHAR(\"create_time\",'YYYY年MM月DD日 HH24时MI分SS秒')) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("false(Boolean),2022年01月01日 01时01分01秒(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }

            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                try {

                    List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                            .where(b -> b.id().eq("123"))
                            .groupBy(b -> GroupKeys.TABLE1.of(b.title()))
                            .select(group -> {
                                BlogEntityProxy blogEntityProxy = new BlogEntityProxy();
                                blogEntityProxy.title().set(group.key1());
                                blogEntityProxy.star().set(group.groupTable().id().intCount());
                                return blogEntityProxy;
                            }).limit(20, 10).toList();
                } catch (Exception ignore) {
                }
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \"__rownum__\" FROM (SELECT t.\"title\" AS \"title\",COUNT(t.\"id\") AS \"star\", ROWNUM AS \"__rownum__\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ? GROUP BY t.\"title\") rt WHERE ROWNUM < 31) rt1 WHERE rt1.\"__rownum__\" > 20", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }

            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                try {

                    List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                            .where(b -> b.id().eq("123")).orderBy(b -> b.star().asc()).limit(20, 10).toList();
                } catch (Exception ignore) {
                }
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \"__rownum__\" FROM (SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ? ORDER BY \"star\" ASC) rt WHERE ROWNUM < 31) rt1 WHERE rt1.\"__rownum__\" > 20", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }
            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                try {

                    List<BlogEntity> list = defaultEasyEntityQuery.queryable(BlogEntity.class)
                            .where(b -> b.id().eq("123")).orderBy(b -> b.star().asc()).limit(10).toList();
                } catch (Exception ignore) {
                }
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT rt.* FROM (SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ? ORDER BY \"star\" ASC) rt WHERE ROWNUM < 11", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }

            {

                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);
                try {
                    ValueCompany valueCompany = new ValueCompany();
                    valueCompany.setId("1");
                    valueCompany.setName("2");
                    ValueCompanyAddress valueCompanyAddress = new ValueCompanyAddress();
                    valueCompanyAddress.setProvince("123");
                    valueCompanyAddress.setArea("456");
                    valueCompany.setAddress(valueCompanyAddress);
                    long l = defaultEasyEntityQuery.insertable(valueCompany)
                            .onConflictThen(o -> Select.of(
                                    o.FETCHER.name(),
                                    o.address().area()
                            ), o -> Select.of(
                                    o.FETCHER.id(),
                                    o.address().province()
                            )).executeRows();
                } catch (Exception ignore) {

                }
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("MERGE INTO \"my_company\" t1 USING (SELECT ? AS \"id\",? AS \"name\",? AS \"province\",? AS \"area\" FROM DUAL ) t2 ON (t1.\"id\" = t2.\"id\" AND t1.\"province\" = t2.\"province\") WHEN MATCHED THEN UPDATE SET t1.\"name\" = t2.\"name\",t1.\"area\" = t2.\"area\" WHEN NOT MATCHED THEN INSERT (\"id\",\"name\",\"province\",\"area\") VALUES (t2.\"id\",t2.\"name\",t2.\"province\",t2.\"area\")", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("1(String),2(String),123(String),456(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                listenerContextManager.clear();
            }
        }
    }

    @Test
    public void testPlus() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.of(2020, 1, 1, 1, 1));
        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    SQLConstantExpression constant = t.expression().constant();
                    t.createTime().lt(
                            constant.valueOf(format).toDateTime(LocalDateTime.class).plus(1, TimeUnit.DAYS)
                    );
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` < date_add(CAST(? AS DATETIME), interval (?) microsecond)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-01-01 01:01:00(String),86400000000(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testPropertyFunc() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> list = easyQueryClient.queryable(Topic.class)

                .where(t -> {
                    String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.of(2020, 1, 1, 1, 1));
                    SQLFunction createTime = t.fx().cast(x -> x.value(format), LocalDateTime.class);
                    t.lt("createTime", createTime);
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE  `create_time` < CAST(? AS DATETIME)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-01-01 01:01:00(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        String sql = easyQueryClient.queryable(Map.class)
                .asTable("my_table")
                .where(t -> {
                    String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.of(2020, 1, 1, 1, 1));
                    SQLFunction createTime = t.fx().cast(x -> x.format("'" + format + "'"), LocalDateTime.class);
                    t.lt("create_time", createTime);
                }).toSQL();
        Assert.assertEquals("SELECT * FROM `my_table` WHERE  `create_time` < CAST('2020-01-01 01:01:00' AS DATETIME)", sql);
    }

}
