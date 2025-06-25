package com.easy.query.test.search;

import com.easy.query.core.api.dynamic.executor.search.EasySearch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;


/**
 * EasySearch基础测试
 *
 * @author bkbits
 */
@Slf4j
public class EasySearchBaseCommonTests
        extends EasySearchBaseTest {
    @Test
    public void testEmpty() {
        EasySearch easySearch = EasySearch.of(SysUser.class)
                                          .table("ext", SysUserExt.class);

        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                                             .innerJoin(
                                                     SysUserExt.class,
                                                     (s, s2) -> s.userId().eq(s2.userId())
                                             )
                                             .whereObject(easySearch)
                                             .orderByObject(easySearch)
                                             .toList();

        Assert.assertEquals(users.size(), 100);
    }

    @Test
    public void testCommon() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        EasySearch easySearch = EasySearch.of(SysUser.class)
                                          .table("ext", SysUserExt.class)
                                          .addDefaultSort("createTime:desc")
                                          .param("userId", "101", "102", "103", "104", "105")
                                          .param("userId-1", "120", "121")
                                          .param("userId-op", "in")
                                          .param("userName", "user_")
                                          .param("createTime-2", getDate(6))
                                          .param("createTime-1", getDate(-6))
                                          .param("ext.extMobile", "mobile_")
                                          .param(
                                                  "sort", "userId:ascend", "userName:ascend",
                                                  "ext.extMobile:ascend", "createTime:desc"
                                          );

        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                                             .innerJoin(
                                                     SysUserExt.class,
                                                     (s, s2) -> s.userId().eq(s2.userId())
                                             )
                                             .whereObject(easySearch)
                                             .orderByObject(easySearch)
                                             .toList();

        Assert.assertFalse(users.isEmpty());
        Assert.assertEquals("101", users.get(0).getUserId());
    }

    @Test
    public void testCommon2() {
        EasySearch easySearch = EasySearch.of(SysUserQueryDTO.class)
                                          .addDefaultSort("createTime:desc")
                                          .param("userId", "101", "102", "103", "104", "105")
                                          .param("userId-1", "120", "121")
                                          .param("userId-op", "in")
                                          .param("userName", "user_")
                                          .param("createTime-2", getDate(6))
                                          .param("createTime-1", getDate(-6))
                                          .param("phone", "mobile_")
                                          .param(
                                                  "sort",
                                                  "userId:ascend",
                                                  "userName:ascend",
                                                  "phone:ascend",
                                                  "createTime:desc"
                                          );

        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                                             .asAlias("u")
                                             .innerJoin(
                                                     SysUserExt.class,
                                                     (s, s2) -> s.userId().eq(s2.userId())
                                             )
                                             .asAlias("e")
                                             .whereObject(easySearch)
                                             .orderByObject(easySearch)
                                             .toList();
        Assert.assertFalse(users.isEmpty());
        Assert.assertEquals("101", users.get(0).getUserId());
    }

    @Test
    public void testDefaultSort() {
        EasySearch easySearch = EasySearch.of(SysUserQueryDTO.class)
                                          .addDefaultSort("userId:asc", "createTime:desc", "phone")
                                          .param("userId", "101", "102", "103", "104", "105")
                                          .param("userId-1", "120", "121")
                                          .param("userId-op", "in")
                                          .param("userName", "user_")
                                          .param("deleteTime", 0)
                                          .param("deleteTime-op", "inn")
                                          .param("createTime-2", getDate(6))
                                          .param("createTime-1", getDate(-6))
                                          .param("phone", "mobile_");

        List<SysUser> users = easyEntityQuery.queryable(SysUser.class)
                                             .asAlias("u")
                                             .innerJoin(
                                                     SysUserExt.class,
                                                     (s, s2) -> s.userId().eq(s2.userId())
                                             )
                                             .asAlias("e")
                                             .whereObject(easySearch)
                                             .orderByObject(easySearch)
                                             .toList();

        Assert.assertFalse(users.isEmpty());
        Assert.assertEquals("101", users.get(0).getUserId());
    }

    @Test
    public void testComplexJoinConditions() {
        EasySearch easySearch = EasySearch.of(SysUser.class)
                                          .table("ext", SysUserExt.class)
                                          .param("userId", 100)
                                          .param("ext.extMobile", "mobile_")
                                          .param("createTime-1", getDate(-6))
                                          .param("createTime-2", getDate(6))
                                          .param("createTime-op", "rc")
                                          .param("sort", "userName:descend");

        List<SysUser> results = easyEntityQuery.queryable(SysUser.class)
                                               .innerJoin(
                                                       SysUserExt.class,
                                                       (s, ext) -> s.userId().eq(ext.userId())
                                               )
                                               .whereObject(easySearch)
                                               .orderByObject(easySearch)
                                               .toList();

        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals("user_100", results.get(0).getUserName());
    }

    @Test
    public void testMultiFieldSortOverride() {
        EasySearch easySearch = EasySearch.of(SysUserQueryDTO.class)
                                          .addDefaultSort("createTime:desc", "phone:asc")
                                          .param("sort", "createTime:ascend")
                                          .param("phone", "mobile_9");

        List<SysUser> results = easyEntityQuery.queryable(SysUser.class)
                                               .asAlias("u")
                                               .innerJoin(
                                                       SysUserExt.class,
                                                       (s, ext) -> s.userId().eq(ext.userId())
                                               )
                                               .asAlias("e")
                                               .whereObject(easySearch)
                                               .orderByObject(easySearch)
                                               .toList();

        Assert.assertTrue(results.size() > 1);
        Assert.assertTrue(results.get(0).getCreateTime().before(results.get(1).getCreateTime()));
    }

    @Test
    public void testNullValueHandling() {
        EasySearch easySearch = EasySearch.of(SysUserQueryDTO.class)
                                          .param("deleteTime", true)
                                          .param("deleteTime-op", "inn")
                                          .param("userName", "user_150");

        long count = easyEntityQuery.queryable(SysUser.class)
                                    .asAlias("u")
                                    .whereObject(easySearch)
                                    .count();

        Assert.assertEquals(1, count);
    }

    @Test
    public void testMultiSourceCondition() {
        EasySearch easySearch = EasySearch.of(SysUserQueryDTO.class)
                                          .param("userName", "user_110")
                                          .param("userName-op", "eq")
                                          .param("phone", "mobile_10")
                                          .param("createTime-op", "gt")
                                          .param("createTime", getDate(-7));

        List<SysUser> results = easyEntityQuery.queryable(SysUser.class)
                                               .asAlias("u")
                                               .innerJoin(
                                                       SysUserExt.class,
                                                       (s, ext) -> s.userId().eq(ext.userId())
                                               )
                                               .asAlias("e")
                                               .whereObject(easySearch)
                                               .toList();

        Assert.assertEquals(1, results.size());
        Assert.assertEquals("user_110", results.get(0).getUserName());
    }
}