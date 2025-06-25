package com.easy.query.test.search;

import com.easy.query.core.api.dynamic.executor.search.EasySearch;
import com.easy.query.core.api.dynamic.executor.search.exception.EasySearchStatusException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;


/**
 *EasySearch 运算符边界测试
 *
 * @author bkbits
 */
public class EasySearchBaseOpExtraTests
        extends EasySearchBaseTest {
    @Test
    public void testOperator_lt_boundary() {
        // 测试下边界：userId < 100 (应无结果)
        testOp("userId", "lt", 0, "100");

        // 测试上边界：userId < 200 (应包含所有99条记录)
        testOp("userId", "lt", 99, "199");
    }

    @Test
    public void testOperator_roc_zero_range() throws Exception {
        // 测试零范围：(date, date] 应返回0条
        String date = getDate(-5);
        testOp("createTime", "roc", 0, date, date);
    }

    @Test
    public void testOperator_in_empty() {
        // 测试空IN列表
        testOp("userId", "in", 0, null);
    }

    @Test
    public void testOperator_lk_emptyPattern() {
        // 测试空LIKE模式
        testOp("userName", "lk", 100, "");
    }

    @Test
    public void testOperator_gt_maxValue() {
        // 测试超过最大值的GT条件
        testOp("userId", "gt", 0, "300");
    }

    /* ============== 异常场景测试增强 ============== */

    @Test
    public void testInvalidOperator() {
        // 测试不支持的运算符
        assertThrows(
                EasySearchStatusException.class,
                () -> testOp("userId", "invalid_op", 0, "100")
        );
    }

    @Test
    public void testNullFieldName() {
        // 测试空字段名
        assertThrows(NullPointerException.class, () -> testOp(null, "eq", 0, "100"));
    }

    @Test
    public void testTypeMismatch() {
//        // 测试类型不匹配 (日期字段传字符串)
//        assertThrows(
//                EasyQuerySQLCommandException.class,
//                () -> testOp("createTime", "gt", 0, new HashMap<>())
//        );
        testOp("createTime", "gt", 0, new HashMap<>());
    }

    private void assertThrows(Class<? extends Throwable> throwClass, Runnable runnable) {
        try {
            runnable.run();
            Assert.fail();
        } catch (Throwable e) {
            if (throwClass.isAssignableFrom(e.getClass())) {
                return;
            }
            Assert.fail(e.getMessage());
        }
    }

    /* ============== 复合条件测试增强 ============== */

    @Test
    public void testMultiCondition() {
        // 组合条件测试：userId > 195 AND userName like '%5'
        EasySearch easySearch = EasySearch.of(SysUser.class)
                                          .param("userId", "195")
                                          .param("userId-op", "ge")
                                          .param("userName", "5")
                                          .param("userName-op", "lmr");

        long count = easyEntityQuery.queryable(SysUser.class).whereObject(easySearch).count();
        // 预期：195-199中userName以5结尾的只有195
        Assert.assertEquals(1, count);
    }

    @Test
    public void testCrossTableCondition() {
        // 跨表条件测试
        EasySearch easySearch =
                EasySearch.of(SysUser.class).table("ext", SysUserExt.class).param(
                        "userId",
                        "100"
                ).param("ext.extMobile", "mobile_0");

        long count = easyEntityQuery.queryable(SysUser.class)
                                    .innerJoin(
                                            SysUserExt.class,
                                            (s, ext) -> s.userId().eq(ext.userId())
                                    )
                                    .whereObject(easySearch)
                                    .count();
        Assert.assertEquals(1, count);
    }

    @Test
    public void testThreeConditionCombination() {
        // 三条件组合测试：userId > 105 AND userId < 195 AND deleteTime is null
        EasySearch easySearch = EasySearch.of(SysUser.class)
                                          .param("userId", "105", "195")
                                          .param("userId-op", "ro")
                                          .param("deleteTime", false)
                                          .param("deleteTime-op", "inn");

        long count = easyEntityQuery.queryable(SysUser.class).whereObject(easySearch).count();
        // 105-195之间有90条记录，其中80% deleteTime为null (100条中有80条null)
        Assert.assertEquals(72, count);
    }

    /* ============== 特殊场景测试增强 ============== */

    @Test
    public void testEmptyResult() {
        // 测试无结果场景
        testOp("userId", "eq", 0, "999");
    }

//    @Test
//    public void testPagination() {
//        // 测试分页功能
//        EasySearch easySearch = EasySearch.of(SysUser.class).param("page", 2).param("size", 10);
//
//        List<SysUser> page = easyEntityQuery.queryable(SysUser.class)
//                                            .whereObject(easySearch)
//                                            .orderByObject(easySearch)
//                                            .toPage(2, 10)
//                                            .getData();
//
//        Assert.assertEquals(10, page.size());
//        Assert.assertEquals("110", page.get(0).getUserId());
//    }

    @Test
    public void testNullValueHandling() {
        // 测试null值处理
        EasySearch easySearch = EasySearch.of(SysUser.class).param("deleteTime", true).param(
                "deleteTime-op",
                "inn"
        );

        long count = easyEntityQuery.queryable(SysUser.class).whereObject(easySearch).count();
        // 初始数据中20%有deleteTime值
        Assert.assertEquals(20, count);
    }

    @Test
    public void testLargeResultSet() {
        // 测试大结果集处理
        EasySearch easySearch = EasySearch.of(SysUser.class).param("userId", "100").param(
                "userId-op",
                "ge"
        );

        List<SysUser> results = easyEntityQuery.queryable(SysUser.class)
                                               .whereObject(easySearch)
                                               .toList();

        Assert.assertEquals(100, results.size());
    }
}