package com.easy.query.test;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.MathTest;
import com.easy.query.test.entity.MathTestDTO;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.MathTestDTOProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

/**
 * create time 2025/8/28 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class MathQueryTest extends BaseTest {

    @Test
    public void testPropertyMath() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MathTestDTO> list = easyEntityQuery.queryable(MathTest.class)
                .select(m -> new MathTestDTOProxy()
                                .testValue().set(m.testValue())
                                .testAbs().set(m.testValue().abs())
                                .testSignum().set(m.testValue().signum())
                                .testFloor().set(m.testValue().floor())
                                .testCeiling().set(m.testValue().ceiling())
                                .testRound1().set(m.testValue().round())
                                .testRound2().set(m.testValue().round(2))
//                        .testExp().set(m.testValue().exp())
                                .testLog().set(m.testValue().log())
                                .testLog10().set(m.testValue().log10())
                                .testPow().set(m.testValue().pow(BigDecimal.valueOf(3)))
                                .testSqrt().set(m.testValue().sqrt())
                                .testCos().set(m.testValue().cos())
                                .testSin().set(m.testValue().sin())
                                .testTan().set(m.testValue().tan())
                                .testAcos().set(m.testValue().acos())
                                .testAsin().set(m.testValue().asin())
                                .testAtan().set(m.testValue().atan())
                                .testAtan2().set(m.testValue().atan2(BigDecimal.valueOf(1)))
                                .testTruncate().set(m.testValue().truncate())
                )
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`test_value` AS `test_value`,ABS(t.`test_value`) AS `test_abs`,SIGN(t.`test_value`) AS `test_signum`,FLOOR(t.`test_value`) AS `test_floor`,CEILING(t.`test_value`) AS `test_ceiling`,ROUND(t.`test_value`) AS `test_round1`,ROUND(t.`test_value`,?) AS `test_round2`,LOG(t.`test_value`) AS `test_log`,LOG10(t.`test_value`) AS `test_log10`,POW(t.`test_value`,?) AS `test_pow`,SQRT(t.`test_value`) AS `test_sqrt`,COS(t.`test_value`) AS `test_cos`,SIN(t.`test_value`) AS `test_sin`,TAN(t.`test_value`) AS `test_tan`,ACOS(t.`test_value`) AS `test_acos`,ASIN(t.`test_value`) AS `test_asin`,ATAN(t.`test_value`) AS `test_atan`,ATAN2(t.`test_value`,?) AS `test_atan2`,TRUNCATE(t.`test_value`,0) AS `test_truncate` FROM `t_math_test` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),3(BigDecimal),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        Assert.assertFalse(list.isEmpty());
        for (MathTestDTO mathTestDTO : list) {
            Assert.assertNotNull(mathTestDTO.getTestValue());
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().abs(), mathTestDTO.getTestAbs()));
            Assert.assertEquals(0, mathTestDTO.getTestSignum().compareTo(mathTestDTO.getTestValue().signum()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.FLOOR), mathTestDTO.getTestFloor()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.CEILING), mathTestDTO.getTestCeiling()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.HALF_UP), mathTestDTO.getTestRound1()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(2, RoundingMode.HALF_UP), mathTestDTO.getTestRound2()));
//            Assert.assertEquals(0, BigDecimalMath.exp(mathTestDTO.getTestValue(),new MathContext(16)).compareTo(mathTestDTO.getTestExp().setScale(15,RoundingMode.HALF_UP)));

            if (mathTestDTO.getTestValue().compareTo(BigDecimal.ZERO) > 0) {
                Assert.assertEquals(0, compareTo0(BigDecimalMath.log(mathTestDTO.getTestValue(), new MathContext(16)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestLog().setScale(9, RoundingMode.HALF_UP)));
                Assert.assertEquals(0, compareTo0(BigDecimalMath.log10(mathTestDTO.getTestValue(), new MathContext(16)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestLog10().setScale(9, RoundingMode.HALF_UP)));
            } else {
                Assert.assertNull(mathTestDTO.getTestLog());
                Assert.assertNull(mathTestDTO.getTestLog10());
            }
            BigDecimal pow = BigDecimalMath.pow(mathTestDTO.getTestValue(), 3, new MathContext(15));
            Assert.assertEquals(0, compareTo0(pow.setScale(pow.scale() - 1, RoundingMode.HALF_UP), mathTestDTO.getTestPow().setScale(pow.scale() - 1, RoundingMode.HALF_UP)));
            if (mathTestDTO.getTestSqrt()!=null) {

                Assert.assertEquals(0, compareTo0(BigDecimalMath.sqrt(mathTestDTO.getTestValue(), new MathContext(15)).setScale(9, RoundingMode.DOWN), mathTestDTO.getTestSqrt().setScale(9, RoundingMode.DOWN)));

            }
            Assert.assertEquals(0, compareTo0(BigDecimalMath.cos(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestCos().setScale(9, RoundingMode.HALF_UP)));
            Assert.assertEquals(0, compareTo0(BigDecimalMath.sin(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestSin().setScale(9, RoundingMode.HALF_UP)));
            Assert.assertEquals(0, compareTo0(BigDecimalMath.tan(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestTan().setScale(9, RoundingMode.HALF_UP),"0.000001"));

            if(mathTestDTO.getTestAsin()!=null){
                Assert.assertEquals(0, compareTo0(BigDecimalMath.asin(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestAsin().setScale(9, RoundingMode.HALF_UP)));
            }
            if(mathTestDTO.getTestAcos()!=null){
                Assert.assertEquals(0, compareTo0(BigDecimalMath.acos(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestAcos().setScale(9, RoundingMode.HALF_UP)));
            }
            Assert.assertEquals(0, compareTo0(BigDecimalMath.atan(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestAtan().setScale(9, RoundingMode.HALF_UP)));

            Assert.assertEquals(0, compareTo0(BigDecimalMath.atan2(mathTestDTO.getTestValue(), BigDecimal.valueOf(1), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestAtan2().setScale(9, RoundingMode.HALF_UP)));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.DOWN), mathTestDTO.getTestTruncate().setScale(9, RoundingMode.HALF_UP)));
        }
    }
    @Test
    public void testPropertyMath2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MathTestDTO> list = easyEntityQuery.queryable(MathTest.class)
                .select(m -> new MathTestDTOProxy()
                                .testValue().set(m.testValue())
                                .testAbs().set(SQLMathExpression.abs(m.testValue()))
                                .testSignum().set(SQLMathExpression.signum(m.testValue()))
                                .testFloor().set(SQLMathExpression.floor(m.testValue()))
                                .testCeiling().set(SQLMathExpression.ceiling(m.testValue()))
                                .testRound1().set(SQLMathExpression.round(m.testValue()))
                                .testRound2().set(SQLMathExpression.round(m.testValue(),2))
//                        .testExp().set(SQLMathExpression.exp())
                                .testLog().set(SQLMathExpression.log(m.testValue()))
                                .testLog10().set(SQLMathExpression.log10(m.testValue()))
                                .testPow().set(SQLMathExpression.pow(m.testValue(),BigDecimal.valueOf(3)))
                                .testSqrt().set(SQLMathExpression.sqrt(m.testValue()))
                                .testCos().set(SQLMathExpression.cos(m.testValue()))
                                .testSin().set(SQLMathExpression.sin(m.testValue()))
                                .testTan().set(SQLMathExpression.tan(m.testValue()))
                                .testAcos().set(SQLMathExpression.acos(m.testValue()))
                                .testAsin().set(SQLMathExpression.asin(m.testValue()))
                                .testAtan().set(SQLMathExpression.atan(m.testValue()))
                                .testAtan2().set(SQLMathExpression.atan2(m.testValue(),BigDecimal.valueOf(1)))
                                .testTruncate().set(SQLMathExpression.truncate(m.testValue()))
                )
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`test_value` AS `test_value`,ABS(t.`test_value`) AS `test_abs`,SIGN(t.`test_value`) AS `test_signum`,FLOOR(t.`test_value`) AS `test_floor`,CEILING(t.`test_value`) AS `test_ceiling`,ROUND(t.`test_value`) AS `test_round1`,ROUND(t.`test_value`,?) AS `test_round2`,LOG(t.`test_value`) AS `test_log`,LOG10(t.`test_value`) AS `test_log10`,POW(t.`test_value`,?) AS `test_pow`,SQRT(t.`test_value`) AS `test_sqrt`,COS(t.`test_value`) AS `test_cos`,SIN(t.`test_value`) AS `test_sin`,TAN(t.`test_value`) AS `test_tan`,ACOS(t.`test_value`) AS `test_acos`,ASIN(t.`test_value`) AS `test_asin`,ATAN(t.`test_value`) AS `test_atan`,ATAN2(t.`test_value`,?) AS `test_atan2`,TRUNCATE(t.`test_value`,0) AS `test_truncate` FROM `t_math_test` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),3(BigDecimal),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        Assert.assertFalse(list.isEmpty());
        for (MathTestDTO mathTestDTO : list) {
            Assert.assertNotNull(mathTestDTO.getTestValue());
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().abs(), mathTestDTO.getTestAbs()));
            Assert.assertEquals(0, mathTestDTO.getTestSignum().compareTo(mathTestDTO.getTestValue().signum()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.FLOOR), mathTestDTO.getTestFloor()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.CEILING), mathTestDTO.getTestCeiling()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.HALF_UP), mathTestDTO.getTestRound1()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(2, RoundingMode.HALF_UP), mathTestDTO.getTestRound2()));
//            Assert.assertEquals(0, BigDecimalMath.exp(mathTestDTO.getTestValue(),new MathContext(16)).compareTo(mathTestDTO.getTestExp().setScale(15,RoundingMode.HALF_UP)));

            if (mathTestDTO.getTestValue().compareTo(BigDecimal.ZERO) > 0) {
                Assert.assertEquals(0, compareTo0(BigDecimalMath.log(mathTestDTO.getTestValue(), new MathContext(16)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestLog().setScale(9, RoundingMode.HALF_UP)));
                Assert.assertEquals(0, compareTo0(BigDecimalMath.log10(mathTestDTO.getTestValue(), new MathContext(16)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestLog10().setScale(9, RoundingMode.HALF_UP)));
            } else {
                Assert.assertNull(mathTestDTO.getTestLog());
                Assert.assertNull(mathTestDTO.getTestLog10());
            }
            BigDecimal pow = BigDecimalMath.pow(mathTestDTO.getTestValue(), 3, new MathContext(15));
            Assert.assertEquals(0, compareTo0(pow.setScale(pow.scale() - 1, RoundingMode.HALF_UP), mathTestDTO.getTestPow().setScale(pow.scale() - 1, RoundingMode.HALF_UP)));
            if (mathTestDTO.getTestSqrt()!=null) {

                Assert.assertEquals(0, compareTo0(BigDecimalMath.sqrt(mathTestDTO.getTestValue(), new MathContext(15)).setScale(9, RoundingMode.DOWN), mathTestDTO.getTestSqrt().setScale(9, RoundingMode.DOWN)));

            }
            Assert.assertEquals(0, compareTo0(BigDecimalMath.cos(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestCos().setScale(9, RoundingMode.HALF_UP)));
            Assert.assertEquals(0, compareTo0(BigDecimalMath.sin(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestSin().setScale(9, RoundingMode.HALF_UP)));
            Assert.assertEquals(0, compareTo0(BigDecimalMath.tan(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestTan().setScale(9, RoundingMode.HALF_UP),"0.000001"));

            if(mathTestDTO.getTestAsin()!=null){
                Assert.assertEquals(0, compareTo0(BigDecimalMath.asin(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestAsin().setScale(9, RoundingMode.HALF_UP)));
            }
            if(mathTestDTO.getTestAcos()!=null){
                Assert.assertEquals(0, compareTo0(BigDecimalMath.acos(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestAcos().setScale(9, RoundingMode.HALF_UP)));
            }
            Assert.assertEquals(0, compareTo0(BigDecimalMath.atan(mathTestDTO.getTestValue(), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestAtan().setScale(9, RoundingMode.HALF_UP)));

            Assert.assertEquals(0, compareTo0(BigDecimalMath.atan2(mathTestDTO.getTestValue(), BigDecimal.valueOf(1), new MathContext(13)).setScale(9, RoundingMode.HALF_UP), mathTestDTO.getTestAtan2().setScale(9, RoundingMode.HALF_UP)));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.DOWN), mathTestDTO.getTestTruncate().setScale(0, RoundingMode.DOWN)));
        }
    }


    private int compareTo0(BigDecimal a, BigDecimal b) {
        System.out.println(a.toPlainString());
        System.out.println(b.toPlainString());
        int i = a.compareTo(b);
        if (i != 0) {
            if (a.setScale(a.scale() - 1, RoundingMode.DOWN).subtract(b.setScale(b.scale() - 1, RoundingMode.DOWN)).compareTo(new BigDecimal("0.000001")) <= 0) {
                return 0;
            }
        }
        return i;
    }
    private int compareTo0(BigDecimal a, BigDecimal b,String __01) {
        System.out.println(a.toPlainString());
        System.out.println(b.toPlainString());
        int i = a.compareTo(b);
        if (i != 0) {
            if (a.setScale(a.scale() - 1, RoundingMode.DOWN).subtract(b.setScale(b.scale() - 1, RoundingMode.DOWN)).compareTo(new BigDecimal(__01)) <= 0) {
                return 0;
            }
        }
        return i;
    }
}
