package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.blogtest.Company;
import com.easy.query.test.entity.blogtest.SysRole;
import com.easy.query.test.entity.blogtest.SysUser;
import com.easy.query.test.entity.blogtest.proxy.SysUserProxy;
import com.easy.query.test.entity.navf.UVO;
import com.easy.query.test.entity.navf.User;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * create time 2024/4/29 23:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest16 extends BaseTest {
//
//    @Test
//    public void test1() {
//        List<SysUser> 收货员 = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    //筛选条件为角色集合里面有角色名称叫做收货员的
//                    s.roles().where(role -> {
//                        role.name().eq("收货员");
//                    }).any();//any表示断言存在条件
//                }).toList();
//    }
//
//    @Test
//    public void test2() {
//        List<SysUser> 收货员 = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    //筛选条件为角色集合里面有角色名称叫做xx员的
//                    s.roles().where(role -> {
//                        role.name().likeMatchRight("员");
//                    }).count().gt(5L);
//                }).toList();
//    }
//
//    @Test
//    public void test3() {
//        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
//        List<SysUser> 收货员 = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    //筛选条件为角色集合里面有角色最大时间不能大于2022年的
//                    s.roles().max(role -> role.createTime()).lt(localDateTime);
//                }).toList();
//    }
//
//    @Test
//    public void test4() {
//        List<SysUser> 收货员 = easyEntityQuery.queryable(SysUser.class)
//                //前面的表达式表示要返回roles后面的表示如何返回返回按时间正序的3个
//                .includes(s -> s.roles(), x -> {
//                    x.orderBy(r -> r.createTime().asc()).limit(3);
//                })
//                .toList();
//    }
//
//    @Test
//    public void test5() {
//        List<SysMenu> menus = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    s.name().eq("小明");
//                })
//                .toList(x -> x.roles().flatElement().menus().flatElement());
//    }
//
//    @Test
//    public void test6() {
//        List<SysMenu> menus = easyEntityQuery.queryable(SysMenu.class)
//                .where(s -> {
//                    //判断菜单下的角色存在角色的用户叫做小明的
//                    s.roles().any(role -> {
//                        role.users().any(user -> {
//                            user.name().eq("小明");
//                        });
//                    });
//                }).toList();
//    }
//
//    @Test
//    public void test7() {
//        List<UserRoleMenuDTO> menus = easyEntityQuery.queryable(SysUser.class)
//                .where(u -> {
//                    u.name().like("小明");
//                    u.createTime().rangeClosed(LocalDateTime.now().plusDays(-100), LocalDateTime.now());
//                })
//                .selectAutoInclude(UserRoleMenuDTO.class)
//                .toList();
//    }
//
//    @Test
//    public void test8() {
//        List<SysUser> userInHz = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    //隐式子查询会自动join用户表和地址表
//                    s.or(() -> {
//                        s.address().city().eq("杭州市");
//                        s.address().city().eq("绍兴市");
//                    });
//                }).toList();
//    }
//
//    @Test
//    public void test9() {
//        List<Draft2<String, String>> userNameAndAddr = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    s.name().eq("小明");
//                }).select(s -> Select.DRAFT.of(
//                        s.name(),
//                        s.address().addr()//隐式join因为用户返回了地址标的地址信息
//                )).toList();
//    }
//
//    @Test
//    public void test10() {
//        List<Draft3<String, String, Long>> userNameAndAddrAndRoleCount = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    s.name().eq("小明");
//                }).select(s -> Select.DRAFT.of(
//                        s.name(),
//                        s.address().addr(),
//                        s.roles().count()//隐式子查询返回用户拥有的角色数量
//                )).toList();
//    }
//
//
//    @Test
//    public void testCert1() {
//        List<Certificate> list = easyEntityQuery.queryable(Certificate.class)
//                .toList();
//    }
//
//    @Test
//    public void testCert2() {
//        List<Certificate> list = easyEntityQuery.queryable(Certificate.class)
//                .where(c -> c.status().eq(CertStatusEnum.NORMAL))
//                .toList();
//    }
//
//    @Test
//    public void testCert3() {
//        List<Certificate> list = easyEntityQuery.queryable(Certificate.class)
//                .where(c -> c.status().eq(CertStatusEnum.NORMAL))
//                .orderBy(c -> c.status().asc())
//                .toList();
//    }
//
//    @Test
//    public void testCert4() {
//
////        List<Draft3<String, String, String>> 杭州 = easyEntityQuery.queryable(SysUser.class)
////                .leftJoin(Topic.class, (s, t2) -> s.id().eq(t2.id()))
////                .where(s -> {
////                    s.address().city().eq("杭州");
////                }).select((s1, t2) -> Select.DRAFT.of(
////                        s1.id(),
////                        s1.address().city(),
////                        t2.title()
////                )).toList();
//
//
//        List<Draft2<CertStatusEnum, Long>> list = easyEntityQuery.queryable(Certificate.class)
//                .where(c -> c.status().eq(CertStatusEnum.NORMAL))
//                .groupBy(c -> GroupKeys.TABLE1.of(c.status()))
//                .select(group -> Select.DRAFT.of(
//                        group.key1(),
//                        group.count()
//                )).toList();
//
//
//        List<SysRole> list4 = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> s.id().eq("1"))
//                .toList(s -> s.roles().flatElement());
//
//        List<SysRole> list5 = easyEntityQuery.queryable(SysRole.class)
//                .where(s -> {
//                    s.users().where(u -> {
//                                u.id().eq("1");
//                            })
//                            .any();
//                }).toList();
//
//        List<SysRole> list6 = easyEntityQuery.queryable(SysRole.class)
//                .where(s -> {
//                    s.users().flatElement().id().eq("1");
//                }).toList();
//
//
//        List<SysUser> list1 = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    s.roles().any(role -> {
//
//                        role.menus().any(menu -> {
//                            menu.name().like("/admin");
//                            menu.icon().eq("123");
//                        });
//
//                    });
//                }).toList();
//
//
//        List<SysUser> list7 = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    s.roles().flatElement().menus().any(c -> {
//
//                        c.name().like("/admin");
//                        c.icon().eq("123");
//
//                    });
//                }).toList();
//
//
//        List<SysUser> list2 = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> {
//                    s.roles().flatElement().menus().flatElement().name().like("/admin");
//                }).toList();
//
////        List<SysUser> list4 = easyEntityQuery.queryable(SysUser.class)
////                .where(s -> {
////                    s.roles().flatElement().menus().flatElement(c->{
////                        c.or(()->{
////                            c.name().like("/admin");
////                            c.name().like("/user");
////                        });
////                    });
////                }).toList();
//
//        List<SysUser> list3 = easyEntityQuery.queryable(SysMenu.class)
//                .where(s -> {
//                    s.name().like("/admin");
//                })
//                .toList(menu -> menu.roles().flatElement().users().flatElement());
//
//    }
//
//    @Test
//    public void xxx1() {
//        List<SysUser> list1 = easyEntityQuery.queryable(SysUser.class)
//                .where(user -> {
//                    user.expression().sql("{0} > {1}", c -> {
//                        c.value(LocalDateTime.now()).expression(user.createTime());
//                    });
//                }).toList();
//
//        List<Draft2<String, String>> list2 = easyEntityQuery.queryable(SysUser.class)
//                .where(user -> {
//                    user.createTime().gt(LocalDateTime.now());
//                }).select(user -> Select.DRAFT.of(
//                        user.id(),
//                        user.expression().sqlType("IFNULL({0},'')", c -> c.expression(user.name())).setPropertyType(String.class)
//                )).toList();
//
//        List<Draft2<String, Long>> userIdAndRoleCount1 = easyEntityQuery.queryable(SysUser.class)
//                .where(user -> user.name().like("小明"))
//                .select(user -> Select.DRAFT.of(
//                        user.id(),
//                        user.expression().subQuery(() -> {
//                            return easyEntityQuery.queryable(SysRole.class)
//                                    .where(r -> {
//                                        r.expression().exists(() -> {
//                                            return easyEntityQuery.queryable(UserRole.class)
//                                                    .where(u -> {
//                                                        u.roleId().eq(r.id());
//                                                        u.userId().eq(user.id());
//                                                    });
//                                        });
//                                    })
//                                    .selectCount();
//                        })
//                )).toList();
//
//        List<Draft2<String, Long>> userIdAndRoleCount = easyEntityQuery.queryable(SysUser.class)
//                .where(user -> user.name().like("小明"))
//                .select(user -> Select.DRAFT.of(
//                        user.id(),
//                        user.roles().count()
//                )).toList();
////        ==> Preparing: SELECT t.`id` AS `value1`,(SELECT COUNT(*) FROM `t_role` t1 WHERE EXISTS (SELECT 1 FROM `t_user` t2 WHERE EXISTS (SELECT 1 FROM `t_user_role` t3 WHERE t3.`user_id` = t2.`id` AND t3.`role_id` = t1.`id` LIMIT 1) AND t2.`id` = t.`id` LIMIT 1)) AS `value2` FROM `t_user` t WHERE t.`name` LIKE ?
////==> Parameters: %小明%(String)
//
//
//        System.out.println("------");
//        List<String> collect1 = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> s.id().eq("1"))
//                .select(x -> x.roles().flatElement().name())
//                .toList();
//        System.out.println("------");
//
//        List<String> collect2 = easyEntityQuery.queryable(SysUser.class)
//                .where(s -> s.id().eq("1"))
//                .toList(x -> x.roles().flatElement()).stream().map(o -> o.getId()).collect(Collectors.toList());
//
//        List<String> list = easyEntityQuery.queryable(SysRole.class)
//                .where(s -> {
//                    s.users().flatElement().id().eq("1");
//                })
//                .select(s -> s.name())
//                .toList();
////
////        List<SysUserDTO> users = easyEntityQuery.queryable(SysUser.class)
////                .where(s -> s.name().like("小明"))
////                .select(SysUserDTO.class, s -> Select.of(
////                        s.FETCHER.allFields(),
////                        s.address().addr().as(SysUserDTO.Fields.myAddress)
////                )).toList();
////        List<StructSysUserDTO> users = easyEntityQuery.queryable(SysUser.class)
////                .leftJoin(Topic.class,(s, t2) -> s.id().eq(t2.id()))
////                .where(s -> s.name().like("小明"))
////                .selectAutoInclude(StructSysUserDTO.class,(s, t2)->Select.of(
////                        //////s.FETCHER.allFields(),请注意不需要添加这一行因为selectAutoInclude会自动执行allFields
////                        t2.stars().nullOrDefault(1).as(StructSysUserDTO.Fields.topicStars)
////                )).toList();
////        List<SysUserFlatDTO> users = easyEntityQuery.queryable(SysUser.class)
////                .where(s -> s.name().like("小明"))
////                .selectAutoInclude(SysUserFlatDTO.class).toList();
////
////
////        List<String> menuIds = easyEntityQuery.queryable(SysUser.class)
////                .where(s -> s.name().like("小明"))
////                .toList(s -> s.roles().flatElement().menus().flatElement().id());
////
////        List<SysMenu> menuIdNames = easyEntityQuery.queryable(SysUser.class)
////                .where(s -> s.name().like("小明"))
////                .toList(s -> s.roles().flatElement().menus().flatElement(x->x.FETCHER.id().name()));
//
//        List<Draft3<String, LocalDateTime, String>> userInfo = easyEntityQuery.queryable(SysUser.class)
//                .leftJoin(SysUserAddress.class, (user, addr) -> user.id().eq(addr.userId()))
//                .where((user, addr) -> {
//                    user.name().like("小明");
//                    addr.city().eq("杭州");
//                })
//                .select((s1, s2) -> Select.DRAFT.of(
//                        s1.id(),
//                        s1.createTime(),
//                        s2.area()
//                )).toList();
//
//        List<SysUser> userIn = easyEntityQuery.queryable(SysUser.class)
//                .where(user -> {
//                    user.id().in(
//                            easyEntityQuery.queryable(SysRole.class)
//                                    .select(s -> s.id())
//                    );
//                }).toList();
//
//        List<SysUser> userExists = easyEntityQuery.queryable(SysUser.class)
//                .where(user -> {
//
//                    user.expression().exists(() -> {
//                        return easyEntityQuery.queryable(SysRole.class)
//                                .where(role -> {
//                                    role.name().eq("管理员");
//                                    role.id().eq(user.id());
//                                });
//                    });
//                }).toList();
//
//
//    }
//
//    @Test
//    public void test11() {
//
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<SysUser> list1 = easyEntityQuery.queryable(SysUser.class)
//                        .where(user -> {
//                            user.name().like("小明");
//                            user.company().name().like("JAVA企业");
//                        }).toList();
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
//                        .where(user -> {
//                            user.createTime()
//                                    .nullOrDefault(LocalDateTime.now())
//                                    .plus(1, TimeUnit.DAYS)
//                                    .format("yyyy-MM-dd HH:mm:ss")
//                                    .eq("2024-01-01 00:00:00");
//                        }).toList();
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<SysUser> userWithMenuContainsAdminPath = easyEntityQuery.queryable(SysUser.class)
//                        .where(user -> {
//                            user.roles().flatElement().menus().any(menu -> menu.route().like("/admin"));
//                        }).toList();
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<Company> companies = easyEntityQuery.queryable(SysUser.class)
//                        .leftJoin(Company.class, (user, com) -> user.companyId().eq(com.id()))
//                        .where((user, com) -> com.name().like("JAVA企业"))
//                        .select((user, com) -> com).toList();
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                EasyPageResult<SysUser> userAndRolePage = easyEntityQuery.queryable(SysUser.class)
//                        .includes(user -> user.roles())
//                        .where(user -> user.age().lt(18))
//                        .toPageResult(1, 20);
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<SysUser> userAndRoles = easyEntityQuery.queryable(SysUser.class)
//                        .includes(user -> user.roles())
//                        .where(user -> user.age().lt(18))
//                        .toList();
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//                List<SysMenu> menus = easyEntityQuery.queryable(SysUser.class)
//                        .where(user -> user.name().eq("小明"))
//                        .toList(user -> user.roles().flatElement().menus().flatElement());
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<String> menuIds = easyEntityQuery.queryable(SysUser.class)
//                        .where(user -> user.name().eq("小明"))
//                        .toList(user -> user.roles().flatElement().menus().flatElement().id());
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<Draft2<String, Integer>> list2 = easyEntityQuery.queryable(SysUser.class)
//                        .where(user -> {
//                            user.name().like("小明");
//                            user.company().name().like("JAVA企业");
//                        })
//                        .groupBy(user -> GroupKeys.TABLE1.of(user.name()))
//                        .select(group -> Select.DRAFT.of(
//                                group.key1(),//user.name
//                                group.sum(group.groupTable().age())//sum(user.age)
//                        )).toList();
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<Draft3<String, String, String>> userInfo = easyEntityQuery.queryable(SysUser.class)
//                        .where(user -> {
//                            user.or(() -> {
//                                user.name().like("小明");
//                                user.company().name().like("JAVA企业");
//                            });
//                        }).select(user -> Select.DRAFT.of(
//                                user.id(),
//                                user.name(),
//                                user.company().name()
//                        )).toList();
//                for (Draft3<String, String, String> userAdnCom : userInfo) {
//                    String userId = userAdnCom.getValue1();
//                    String userName = userAdnCom.getValue2();
//                    String companyName = userAdnCom.getValue3();
//                }
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<Company> companies = easyEntityQuery.queryable(Company.class)
//                        .where(com -> {
//                            com.users().any(u -> {
//                                u.name().like("小明");
//                            });
//                        }).toList();
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            try {
//
//                List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
//                        .where(user -> {
//                            user.or(() -> {
//                                user.name().like("小明");
//                                user.company().name().like("JAVA企业");
//                            });
//                        }).toList();
//            } catch (Exception ignore) {
//            }
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//
//
//    }

//    @Test
//    public void test13(){
//
//        EntityMetadata entityMetadata = easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(UserInfoPropUpper.class);
//        System.out.println(entityMetadata.getProperties());
//    }

//    @Test
//    public void test9_1() {
////        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
////                .where(user -> {
////                    user.books().where(book -> {
////                        book.author().eq("金庸");
////                    }).count().gt(2L);
////                }).toList();
//
////        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
////                .where(user -> {
////                    user.id().in(
////                            easyEntityQuery.queryable(UserBook.class)
////                                    .where(u_book -> {
////                                        u_book.name().like("java高级开发");
////                                    }).select(u_book -> u_book.userId())
////                    );
////                }).toList();
//
//
////        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
////                .where(user -> {
////                    user.expression().exists(()->{
////                        return easyEntityQuery.queryable(UserBook.class)
////                                .where(u_book -> {
////                                    u_book.userId().eq(user.id());
////                                    u_book.name().like("java高级开发");
////                                });
////                    });
////                }).toList();
////        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
////                .where(user -> {
////                    user.books().any(book -> {
////                        book.name().like("java高级开发");
////                    });
////                }).toList();
////        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
////                .where(user -> {
////                    user.books().flatElement().name().like("java高级开发");
////                }).toList();
////        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
////                .where(user -> {
////                    user.expression().subQuery(() -> {
////                        return easyEntityQuery.queryable(UserBook.class)
////                                .where(u_book -> {
////                                    u_book.author().eq("金庸");
////                                    u_book.userId().eq(user.id());
////                                }).selectCount();
////                    }).gt(2L);
////                }).toList();
//
////        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
////                .where(user -> {
////                    user.books().where(book -> {
////                        book.author().eq("金庸");
////                    }).count().gt(2L);
////                }).toList();
//    }

    @Test
    public void test14() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {
                List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                        .where(user -> {
                            user.or(() -> {
                                user.name().like("小明");
                                user.company().name().like("JAVA企业");
                            });
                        }).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE (t.`name` LIKE ? OR t1.`name` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%小明%(String),%JAVA企业%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test15() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                        .where(user -> {
                            user.or(() -> {
                                user.name().like("小明");
                                user.roles().flatElement().name().like("管理员");
                            });
                        }).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t WHERE (t.`name` LIKE ? OR EXISTS (SELECT 1 FROM `t_role` t1 WHERE EXISTS (SELECT 1 FROM `t_user_role` t2 WHERE t2.`role_id` = t1.`id` AND t2.`user_id` = t.`id` LIMIT 1) AND t1.`name` LIKE ? LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%小明%(String),%管理员%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test16() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                List<Map<String, Object>> list1 = easyQueryClient.mapQueryable().asTable("t_user")
                        .select(m -> {
                            ColumnAsSelector<?, ?> asSelector = m.getAsSelector(0);
                            asSelector.column("a");
                            asSelector.column("b");
                        })
                        .union(
                                easyQueryClient.mapQueryable().asTable("t_user1")
                                        .select(m -> {
                                            ColumnAsSelector<?, ?> asSelector = m.getAsSelector(0);
                                            asSelector.column("a");
                                            asSelector.column("b");
                                        })
                        ).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t4.`a` AS `a`,t4.`b` AS `b` FROM ( (SELECT t.`a` AS `a`,t.`b` AS `b` FROM `t_user` t)  UNION  (SELECT t2.`a` AS `a`,t2.`b` AS `b` FROM `t_user1` t2) ) t4", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%管理员%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                List<Map<String, Object>> list1 = easyQueryClient.mapQueryable().asTable("t_user")
                        .select(m -> {
                            ColumnAsSelector<?, ?> asSelector = m.getAsSelector(0);
                            asSelector.column("a");
                            asSelector.column("b");
                        })
                        .unionAll(
                                easyQueryClient.mapQueryable().asTable("t_user1")
                                        .select(m -> {
                                            ColumnAsSelector<?, ?> asSelector = m.getAsSelector(0);
                                            asSelector.column("a");
                                            asSelector.column("b");
                                        })
                        ).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t4.`a` AS `a`,t4.`b` AS `b` FROM ( (SELECT t.`a` AS `a`,t.`b` AS `b` FROM `t_user` t)  UNION ALL  (SELECT t2.`a` AS `a`,t2.`b` AS `b` FROM `t_user1` t2) ) t4", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%小明%(String),%管理员%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void docSub() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {

                List<Draft2<String, Boolean>> list = easyEntityQuery.queryable(Company.class)
                        .where(com -> com.name().like("xx公司"))
                        .select(com -> Select.DRAFT.of(
                                com.id(),
                                com.users().noneValue()
                        )).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,(NOT EXISTS((SELECT 1 FROM `t_user` t2 WHERE t2.`company_id` = t.`id` LIMIT 1))) AS `value2` FROM `t_company` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%xx公司%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {

                List<Draft2<String, Boolean>> list = easyEntityQuery.queryable(Company.class)
                        .where(com -> com.name().like("xx公司"))
                        .select(com -> Select.DRAFT.of(
                                com.id(),
                                com.users().anyValue()
                        )).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,EXISTS((SELECT 1 FROM `t_user` t2 WHERE t2.`company_id` = t.`id` LIMIT 1)) AS `value2` FROM `t_company` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%xx公司%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {

                List<Draft2<String, Integer>> list = easyEntityQuery.queryable(Company.class)
                        .where(com -> com.name().like("xx公司"))
                        .select(com -> Select.DRAFT.of(
                                com.id(),
                                com.users().where(user -> user.name().likeMatchLeft("李")).sum(x -> x.age())
                        )).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,IFNULL((SELECT SUM(t2.`age`) FROM `t_user` t2 WHERE t2.`company_id` = t.`id` AND t2.`name` LIKE ?),0) AS `value2` FROM `t_company` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("李%(String),%xx公司%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {

                List<Draft2<String, Long>> list = easyEntityQuery.queryable(Company.class)
                        .where(com -> com.name().like("xx公司"))
                        .select(com -> Select.DRAFT.of(
                                com.id(),
                                com.users().count()
                        )).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,(SELECT COUNT(*) FROM `t_user` t2 WHERE t2.`company_id` = t.`id`) AS `value2` FROM `t_company` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%xx公司%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {

                List<Draft2<String, Long>> list = easyEntityQuery.queryable(Company.class)
                        .where(com -> com.name().like("xx公司"))
                        .select(com -> Select.DRAFT.of(
                                com.id(),
                                com.users().count()
                        )).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,(SELECT COUNT(*) FROM `t_user` t2 WHERE t2.`company_id` = t.`id`) AS `value2` FROM `t_company` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%xx公司%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {

                List<Draft2<String, Long>> list = easyEntityQuery.queryable(Company.class)
                        .where(com -> com.name().like("xx公司"))
                        .select(com -> Select.DRAFT.of(
                                com.id(),
                                com.users().where(user -> user.name().likeMatchLeft("李")).count()
                        )).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `value1`,(SELECT COUNT(*) FROM `t_user` t2 WHERE t2.`company_id` = t.`id` AND t2.`name` LIKE ?) AS `value2` FROM `t_company` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("李%(String),%xx公司%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testDocSub() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                List<Company> list = easyEntityQuery.queryable(Company.class)
                        .where(com -> {
                            com.users().where(u -> {
                                u.age().gt(18);
                            }).any();
                        }).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_company` t WHERE EXISTS (SELECT 1 FROM `t_user` t1 WHERE t1.`company_id` = t.`id` AND t1.`age` > ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("18(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                List<Company> list = easyEntityQuery.queryable(Company.class)
                        .where(com -> {
                            com.users().flatElement().age().gt(18);
                        }).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_company` t WHERE EXISTS (SELECT 1 FROM `t_user` t1 WHERE t1.`company_id` = t.`id` AND t1.`age` > ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("18(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                List<Company> list = easyEntityQuery.queryable(Company.class)
                        .where(com -> {
                            com.users().avg(u -> u.age()).gt(BigDecimal.valueOf(18));
                        }).toList();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_company` t WHERE IFNULL((SELECT AVG(t1.`age`) FROM `t_user` t1 WHERE t1.`company_id` = t.`id`),0) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("18(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test111(){
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            boolean exception=false;

            try {
                List<UVO> list = easyEntityQuery.queryable(User.class)
                        .selectAutoInclude(UVO.class)
                        .toList();
            } catch (Exception ignore) {
                exception=true;
            }
            Assert.assertTrue(exception);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `__relation__id` FROM `sys_user` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testxxc(){
        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(b -> {
                    b.id().eq("123");
                }).orderBy(t -> {
                    t.expression().sqlSegment("RAND()").executeSQL();
                }).toList();


        List<Draft2<Double, Integer>> list1 = easyEntityQuery.queryable(Topic.class)
                .where(b -> {
                    b.id().eq("123");
                }).select(t -> Select.DRAFT.of(
                        t.expression().sqlSegment("RAND()").asAnyType(Double.class),
                        t.expression().sqlSegment("IFNULL({0},{1})", c -> {
                            c.expression(t.stars().nullOrDefault(1)).value(2);
                        },Integer.class)
                )).toList();

    }

    @Test
    public void test19(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
       try {
           LocalDateTime begin = LocalDateTime.of(2020,1,1,1,2);
           LocalDateTime end =LocalDateTime.of(2026,1,1,1,2);
           List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                   .where(user -> {
                       user.roles().configure(r->{
                           r.asAlias("myRole");
                       }).any(role -> {
                           role.name().like("查询的角色名");
                           role.createTime().rangeClosed(begin != null, begin, end != null, end);
                       });
                   }).toList();
       }catch (Exception ex){

       }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t WHERE EXISTS (SELECT 1 FROM `t_role` myRole WHERE EXISTS (SELECT 1 FROM `t_user_role` t2 WHERE t2.`role_id` = myRole.`id` AND t2.`user_id` = t.`id` LIMIT 1) AND myRole.`name` LIKE ? AND myRole.`create_time` >= ? AND myRole.`create_time` <= ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%查询的角色名%(String),2020-01-01T01:02(LocalDateTime),2026-01-01T01:02(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test20(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
       try {
           LocalDateTime begin = LocalDateTime.of(2020,1,1,1,2);
           LocalDateTime end =LocalDateTime.of(2026,1,1,1,2);
           List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                   .where(user -> {
                       user.company().name().like("123");

                       user.roles().configure(r->{
                           r.asAlias("myRole");
                       }).any(role -> {
                           role.name().like("查询的角色名");
                           role.createTime().rangeClosed(begin != null, begin, end != null, end);
                       });
                   }).toList();
       }catch (Exception ex){

       }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_company` t1 ON t1.`id` = t.`company_id` WHERE t1.`name` LIKE ? AND EXISTS (SELECT 1 FROM `t_role` myRole WHERE EXISTS (SELECT 1 FROM `t_user_role` t3 WHERE t3.`role_id` = myRole.`id` AND t3.`user_id` = t.`id` LIMIT 1) AND myRole.`name` LIKE ? AND myRole.`create_time` >= ? AND myRole.`create_time` <= ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),%查询的角色名%(String),2020-01-01T01:02(LocalDateTime),2026-01-01T01:02(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
//    @Test
//    public void testxxxvvb(){
//
//        List<SysUser> userExists = easyEntityQuery.queryable(SysUser.class)
//                .where(user -> {
//
//                    user.expression().exists(()->{
//                        return easyEntityQuery.queryable(SysRole.class)
//                                .where(role -> {
//                                    role.name().eq("管理员");
//                                    user.id().eq(role.id());
////                                    SysUserProxy u = user.create(user.getTable(), role.getEntitySQLContext());
////                                    u.id().eq(role.id());
//                                });
//                    });
//                }).toList();
//    }
}
