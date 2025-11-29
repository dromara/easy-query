package com.easy.query.test.pgsql;

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
 * create time 2025/8/29 08:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class MathQueryTest extends PgSQLBaseTest{


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
        Assert.assertEquals("SELECT t.\"test_value\" AS \"test_value\",ABS(t.\"test_value\") AS \"test_abs\",SIGN(t.\"test_value\") AS \"test_signum\",FLOOR(t.\"test_value\") AS \"test_floor\",CEILING(t.\"test_value\") AS \"test_ceiling\",ROUND(t.\"test_value\") AS \"test_round1\",ROUND(t.\"test_value\",?) AS \"test_round2\",(CASE WHEN t.\"test_value\" > ? THEN LN(t.\"test_value\") ELSE NULL END) AS \"test_log\",(CASE WHEN t.\"test_value\" > ? THEN LOG10(t.\"test_value\") ELSE NULL END) AS \"test_log10\",(CASE WHEN t.\"test_value\" <> ? THEN POW(t.\"test_value\",?) ELSE NULL END) AS \"test_pow\",(CASE WHEN t.\"test_value\" >= ? THEN SQRT(t.\"test_value\") ELSE NULL END) AS \"test_sqrt\",COS(t.\"test_value\") AS \"test_cos\",SIN(t.\"test_value\") AS \"test_sin\",TAN(t.\"test_value\") AS \"test_tan\",(CASE WHEN (t.\"test_value\" >= ? AND t.\"test_value\" <= ?) THEN ACOS(t.\"test_value\") ELSE NULL END) AS \"test_acos\",(CASE WHEN (t.\"test_value\" >= ? AND t.\"test_value\" <= ?) THEN ASIN(t.\"test_value\") ELSE NULL END) AS \"test_asin\",ATAN(t.\"test_value\") AS \"test_atan\",ATAN2(t.\"test_value\",?) AS \"test_atan2\",TRUNC(t.\"test_value\",0) AS \"test_truncate\" FROM \"t_math_test\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),0(BigDecimal),0(BigDecimal),0(BigDecimal),3(BigDecimal),0(BigDecimal),-1(BigDecimal),1(BigDecimal),-1(BigDecimal),1(BigDecimal),1(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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
                Assert.assertEquals(0, compareTo0(pow.setScale(pow.scale() - 1, RoundingMode.HALF_UP), mathTestDTO.getTestPow().setScale(pow.scale() - 1, RoundingMode.HALF_UP)));
            }
            if (mathTestDTO.getTestValue().compareTo(BigDecimal.ZERO) > 0) {

                Assert.assertEquals(0, compareTo0(BigDecimalMath.sqrt(mathTestDTO.getTestValue(), new MathContext(16)).setScale(9, RoundingMode.DOWN), mathTestDTO.getTestSqrt().setScale(16, RoundingMode.DOWN)));

            } else {
//                Assert.assertNull(mathTestDTO.getTestSqrt());
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
