package com.easy.query.test.dameng;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.MathTest;
import com.easy.query.test.entity.MathTestDTO;
import com.easy.query.test.entity.proxy.MathTestDTOProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * create time 2025/8/29 08:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class MathQueryTest extends DamengBaseTest {


    @Test
    public void testPropertyMath1() {
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
//                                .testLog().set(
//                                        m.expression().caseWhen(()->{
//                                            m.testValue().gt(BigDecimal.ZERO);
//                                        }).then(m.testValue().log())
//                                                .elseEnd(null)
//                                )
//                                .testLog10().set(
//                                        m.expression().caseWhen(()->{
//                                                    m.testValue().gt(BigDecimal.ZERO);
//                                                }).then(m.testValue().log10())
//                                                .elseEnd(null)
//                                )
//                                .testPow().set(
//
//                                        m.expression().caseWhen(()->{
//                                                    m.testValue().ne(BigDecimal.ZERO);
//                                                }).then(m.testValue().pow(BigDecimal.valueOf(3)))
//                                                .elseEnd(null)
//
//                                )
                )
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"TEST_VALUE\" AS \"TEST_VALUE\",ABS(t.\"TEST_VALUE\") AS \"TEST_ABS\",SIGN(t.\"TEST_VALUE\") AS \"TEST_SIGNUM\",FLOOR(t.\"TEST_VALUE\") AS \"TEST_FLOOR\",CEIL(t.\"TEST_VALUE\") AS \"TEST_CEILING\",ROUND(t.\"TEST_VALUE\") AS \"TEST_ROUND1\",ROUND(t.\"TEST_VALUE\",?) AS \"TEST_ROUND2\" FROM \"t_math_test\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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

//            if (mathTestDTO.getTestValue().compareTo(BigDecimal.ZERO) > 0) {
//                Assert.assertEquals(0, compareTo0(BigDecimalMath.log(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestLog().setScale(16, RoundingMode.HALF_UP)));
//                Assert.assertEquals(0, compareTo0(BigDecimalMath.log10(mathTestDTO.getTestValue(), new MathContext(16)).setScale(16, RoundingMode.HALF_UP), mathTestDTO.getTestLog10().setScale(16, RoundingMode.HALF_UP)));
//            } else {
//                Assert.assertNull(mathTestDTO.getTestLog());
//                Assert.assertNull(mathTestDTO.getTestLog10());
//            }
//            BigDecimal pow = BigDecimalMath.pow(mathTestDTO.getTestValue(), 3, new MathContext(16));
//            Assert.assertEquals(0, compareTo0(pow.setScale(pow.scale() - 1, RoundingMode.HALF_UP), mathTestDTO.getTestPow().setScale(pow.scale() - 1, RoundingMode.HALF_UP)));

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
}
