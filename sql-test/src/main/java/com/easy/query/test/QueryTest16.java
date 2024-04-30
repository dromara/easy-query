//package com.easy.query.test;
//
//import com.easy.query.core.proxy.core.draft.Draft2;
//import com.easy.query.core.proxy.sql.Select;
//import com.easy.query.test.dto.UserRoleMenuDTO;
//import com.easy.query.test.entity.blogtest.SysMenu;
//import com.easy.query.test.entity.blogtest.SysUser;
//import org.junit.Test;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
///**
// * create time 2024/4/29 23:02
// * 文件说明
// *
// * @author xuejiaming
// */
//public class QueryTest16 extends BaseTest {
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
//}
