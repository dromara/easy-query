package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.MathTestProxy;
import lombok.Data;

import java.math.BigDecimal;

/**
 * create time 2025/8/28 21:14
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class MathTestDTO {
    private BigDecimal testValue;
    private BigDecimal testAbs;
    private Integer testSignum;
    private BigDecimal testFloor;
    private BigDecimal testCeiling;
    private BigDecimal testRound1;
    private BigDecimal testRound2;
    private BigDecimal testExp;
    private BigDecimal testLog;
    private BigDecimal testLog10;
    private BigDecimal testPow;
    private BigDecimal testSqrt;
    private BigDecimal testCos;
    private BigDecimal testSin;
    private BigDecimal testTan;
    private BigDecimal testAcos;
    private BigDecimal testAsin;
    private BigDecimal testAtan;
    private BigDecimal testAtan2;
    private BigDecimal testTruncate;
}
