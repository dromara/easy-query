package com.easy.query.test.search;


import org.junit.Test;


/**
 * EasySearch运算符测试
 *
 * @author bkbits
 */
public class EasySearchBaseOpTests
        extends EasySearchBaseTest {
    @Test
    public void testOperator_lml() {
        // LikeMatchLeft: user_10%
        testOp("userName", "lml", 10, "user_10");
    }

    @Test
    public void testOperator_in() {
        // in: userId in (101,102,103)
        testOp("userId", "in", 3, "101", "102", "103");
    }

    @Test
    public void testOperator_lmr() {
        // LikeMatchRight: %1
        testOp("userName", "lmr", 10, "1");
    }

    @Test
    public void testOperator_roc() throws Exception {
        // rangeOpenClosed: (createTime1, createTime2]
        testOp("createTime", "roc", 20, getDate(-6), getDate(-4));
    }

    @Test
    public void testOperator_inn() {
        // IsNotNull: deleteTime is not null
        testOp("deleteTime", "inn", 80, false); // 初始数据中deleteTime都为null
        testOp("deleteTime", "inn", 20, true); // 初始数据中deleteTime都为null
    }

    @Test
    public void testOperator_lt() {
        // LessThan: userId < 105
        testOp("userId", "lt", 5, "105");
    }

    @Test
    public void testOperator_eq() {
        // Equals: userId = 100
        testOp("userId", "eq", 1, "100");
    }

    @Test
    public void testOperator_gt() {
        // GreaterThan: userId > 195
        testOp("userId", "gt", 4, "195");
    }

    @Test
    public void testOperator_nlml() {
        // notLikeMatchLeft: NOT user_10%
        testOp("userName", "nlml", 90, "user_10");
    }

    @Test
    public void testOperator_rc() throws Exception {
        // rangeClosed: [createTime1, createTime2]
        testOp("createTime", "rc", 20, getDate(-6), getDate(-4));
    }

    @Test
    public void testOperator_nlmr() {
        // notLikeMatchRight: NOT %00
        testOp("userName", "nlmr", 99, "00");
    }

    @Test
    public void testOperator_ne() {
        // notEquals: userId != 100
        testOp("userId", "ne", 99, "100");
    }

    @Test
    public void testOperator_le() {
        // LessEquals: userId <= 105
        testOp("userId", "le", 6, "105");
    }

    @Test
    public void testOperator_ni() {
        // notIn: userId not in (101,102,103)
        testOp("userId", "ni", 97, "101", "102", "103");
    }

    @Test
    public void testOperator_ro() throws Exception {
        // rangeOpen: (createTime1, createTime2)
        testOp("createTime", "ro", 10, getDate(-6), getDate(-4));
    }

    @Test
    public void testOperator_ge() {
        // GreaterEquals: userId >= 195
        testOp("userId", "ge", 5, "195");
    }

    @Test
    public void testOperator_nl() {
        // notLike: NOT %0%
        testOp("userName", "nl", 81, "0");
    }

    @Test
    public void testOperator_lk() {
        // like: %0%
        testOp("userName", "lk", 19, "0");
    }

    @Test
    public void testOperator_rco() throws Exception {
        // rangeClosedOpen: [createTime1, createTime2)
        testOp("createTime", "rco", 10, getDate(-6), getDate(-4));
    }

    // 新增测试方法
    @Test
    public void testOperator_nrco() throws Exception {
        // NotRangeClosedOpen: NOT [createTime1, createTime2)
        testOp("createTime", "nrco", 90, getDate(-6), getDate(-4));
    }

    @Test
    public void testOperator_nro() throws Exception {
        // NotRangeOpen: NOT (createTime1, createTime2)
        testOp("createTime", "nro", 90, getDate(-6), getDate(-4));
    }

    @Test
    public void testOperator_nroc() throws Exception {
        // NotRangeOpenClosed: NOT (createTime1, createTime2]
        testOp("createTime", "nroc", 80, getDate(-6), getDate(-4));
    }

    @Test
    public void testOperator_nrc() throws Exception {
        // NotRangeClosed: NOT [createTime1, createTime2]
        testOp("createTime", "nrc", 80, getDate(-6), getDate(-4));
    }
}