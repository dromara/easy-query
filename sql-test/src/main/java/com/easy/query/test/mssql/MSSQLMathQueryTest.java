package com.easy.query.test.mssql;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.MathTest;
import com.easy.query.test.entity.MathTestDTO;
import com.easy.query.test.entity.proxy.MathTestDTOProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

/**
 * create time 2025/8/29 12:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class MSSQLMathQueryTest extends MsSQLBaseTest{


    @Test
    public void testPropertyMath() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MathTestDTO> list = entityQuery.queryable(MathTest.class)
                .select(m -> new MathTestDTOProxy()
                                .testValue().set(m.testValue())
                                .testAbs().set(m.testValue().abs())
                                .testSignum().set(m.testValue().signum())
                                .testFloor().set(m.testValue().floor())
                                .testCeiling().set(m.testValue().ceiling())
                                .testRound1().set(m.testValue().round())
                                .testRound2().set(m.testValue().round(2))
////                        .testExp().set(m.testValue().exp())
                                .testLog().set(
                                        m.expression().caseWhen(()->{
                                                    m.testValue().gt(BigDecimal.ZERO);
                                                }).then(m.testValue().log())
                                                .elseEnd(null)
                                )
                                .testLog10().set(
                                        m.expression().caseWhen(()->{
                                                    m.testValue().gt(BigDecimal.ZERO);
                                                }).then(m.testValue().log10())
                                                .elseEnd(null)
                                )
                                .testPow().set(

                                        m.expression().caseWhen(()->{
                                                    m.testValue().ne(BigDecimal.ZERO);
                                                }).then(m.testValue().pow(BigDecimal.valueOf(3)))
                                                .elseEnd(null)

                                )
                                .testSqrt().set(
                                        m.expression().caseWhen(()->{
                                                    m.testValue().ge(BigDecimal.ZERO);
                                                }).then(m.testValue().sqrt())
                                                .elseEnd(null)
                                )
                                .testCos().set(m.testValue().cos())
                                .testSin().set(m.testValue().sin())
                                .testTan().set(m.testValue().tan())
                                .testAcos().set(

                                        m.expression().caseWhen(()->{
                                                    m.testValue().rangeClosed(new BigDecimal("-1"),new BigDecimal("1"));
                                                }).then(m.testValue().acos())
                                                .elseEnd(null)
                                )
                                .testAsin().set(
                                        m.expression().caseWhen(()->{
                                                    m.testValue().rangeClosed(new BigDecimal("-1"),new BigDecimal("1"));
                                                }).then(m.testValue().asin())
                                                .elseEnd(null)
                                )
                                .testAtan().set(m.testValue().atan())
                                .testAtan2().set(m.testValue().atan2(BigDecimal.valueOf(1)))
                                .testTruncate().set(m.testValue().truncate())
                )
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.[TestValue] AS [TestValue],ABS(t.[TestValue]) AS [TestAbs],SIGN(t.[TestValue]) AS [TestSignum],(CASE WHEN t.[TestValue] >= 0 THEN FLOOR(t.[TestValue]) ELSE CEILING(t.[TestValue]) END) AS [TestFloor],CEILING(t.[TestValue]) AS [TestCeiling],ROUND(t.[TestValue],0) AS [TestRound1],ROUND(t.[TestValue],?) AS [TestRound2],(CASE WHEN t.[TestValue] > ? THEN LOG(t.[TestValue]) ELSE NULL END) AS [TestLog],(CASE WHEN t.[TestValue] > ? THEN LOG10(t.[TestValue]) ELSE NULL END) AS [TestLog10],(CASE WHEN t.[TestValue] <> ? THEN POWER(t.[TestValue],?) ELSE NULL END) AS [TestPow],(CASE WHEN t.[TestValue] >= ? THEN SQRT(t.[TestValue]) ELSE NULL END) AS [TestSqrt],COS(t.[TestValue]) AS [TestCos],SIN(t.[TestValue]) AS [TestSin],TAN(t.[TestValue]) AS [TestTan],(CASE WHEN (t.[TestValue] >= ? AND t.[TestValue] <= ?) THEN ACOS(t.[TestValue]) ELSE NULL END) AS [TestAcos],(CASE WHEN (t.[TestValue] >= ? AND t.[TestValue] <= ?) THEN ASIN(t.[TestValue]) ELSE NULL END) AS [TestAsin],ATAN(t.[TestValue]) AS [TestAtan],(CASE WHEN ?<>0 THEN ATAN(t.[TestValue]/?) + CASE WHEN ?<0 AND t.[TestValue]>=0 THEN PI() WHEN ?<0 AND t.[TestValue]<0 THEN -PI() ELSE 0 END WHEN ?=0 AND t.[TestValue]>0 THEN PI()/2 WHEN ?=0 AND t.[TestValue]<0 THEN -PI()/2 ELSE NULL END) AS [TestAtan2],(CASE WHEN t.[TestValue] >= 0 THEN FLOOR(t.[TestValue]) ELSE CEILING(t.[TestValue]) END) AS [TestTruncate] FROM [t_math_test] t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),0(BigDecimal),0(BigDecimal),0(BigDecimal),3(BigDecimal),0(BigDecimal),-1(BigDecimal),1(BigDecimal),-1(BigDecimal),1(BigDecimal),1(BigDecimal),1(BigDecimal),1(BigDecimal),1(BigDecimal),1(BigDecimal),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

        Assert.assertFalse(list.isEmpty());
        for (MathTestDTO mathTestDTO : list) {
            Assert.assertNotNull(mathTestDTO.getTestValue());
            System.out.println("mathTestDTO.getTestValue():"+mathTestDTO.getTestValue().toPlainString());
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().abs(), mathTestDTO.getTestAbs()));
            Assert.assertEquals(0, mathTestDTO.getTestSignum().compareTo(mathTestDTO.getTestValue().signum()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.FLOOR), mathTestDTO.getTestFloor()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.CEILING), mathTestDTO.getTestCeiling()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(0, RoundingMode.HALF_UP), mathTestDTO.getTestRound1()));
            Assert.assertEquals(0, compareTo0(mathTestDTO.getTestValue().setScale(2, RoundingMode.HALF_UP), mathTestDTO.getTestRound2()));
//            Assert.assertEquals(0, BigDecimalMath.exp(mathTestDTO.getTestValue(),new MathContext(16)).compareTo(mathTestDTO.getTestExp().setScale(15,RoundingMode.HALF_UP)));

            if (mathTestDTO.getTestValue().compareTo(BigDecimal.ZERO) > 0) {
                Assert.assertEquals(0, compareTo0(BigDecimalMath.log(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestLog().setScale(16, RoundingMode.HALF_UP)));
                Assert.assertEquals(0, compareTo0(BigDecimalMath.log10(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestLog10().setScale(16, RoundingMode.HALF_UP)));
            } else {
                Assert.assertNull(mathTestDTO.getTestLog());
                Assert.assertNull(mathTestDTO.getTestLog10());
            }
            BigDecimal pow = BigDecimalMath.pow(mathTestDTO.getTestValue(), 3, new MathContext(16));
            if(mathTestDTO.getTestPow()!=null){
                Assert.assertEquals(0, compareTo0(pow.setScale(pow.scale(), RoundingMode.HALF_UP), mathTestDTO.getTestPow().setScale(pow.scale(), RoundingMode.HALF_UP),"0.01"));
            }
            if (mathTestDTO.getTestValue().compareTo(BigDecimal.ZERO) > 0) {

                Assert.assertEquals(0, compareTo0(BigDecimalMath.sqrt(mathTestDTO.getTestValue(), new MathContext(16)).setScale(9, RoundingMode.DOWN), mathTestDTO.getTestSqrt().setScale(16, RoundingMode.DOWN)));

            } else {
                Assert.assertNull(mathTestDTO.getTestSqrt());
            }
            Assert.assertEquals(0, compareTo0(BigDecimalMath.cos(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestCos().setScale(16, RoundingMode.HALF_UP)));
            Assert.assertEquals(0, compareTo0(BigDecimalMath.sin(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestSin().setScale(16, RoundingMode.HALF_UP)));
            Assert.assertEquals(0, compareTo0(BigDecimalMath.tan(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestTan().setScale(16, RoundingMode.HALF_UP),"0.0001"));

            if(mathTestDTO.getTestAsin()!=null){
                Assert.assertEquals(0, compareTo0(BigDecimalMath.asin(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestAsin().setScale(16, RoundingMode.HALF_UP)));
            }
            if(mathTestDTO.getTestAcos()!=null){
                Assert.assertEquals(0, compareTo0(BigDecimalMath.acos(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestAcos().setScale(16, RoundingMode.HALF_UP)));
            }
            Assert.assertEquals(0, compareTo0(BigDecimalMath.atan(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestAtan().setScale(16, RoundingMode.HALF_UP)));

            Assert.assertEquals(0, compareTo0(BigDecimalMath.atan2(mathTestDTO.getTestValue(), BigDecimal.valueOf(1), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestAtan2().setScale(16, RoundingMode.HALF_UP)));
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
