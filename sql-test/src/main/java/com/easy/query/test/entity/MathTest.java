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
@Table("t_math_test")
@EntityProxy
public class MathTest implements ProxyEntityAvailable<MathTest , MathTestProxy> {
    @Column(primaryKey = true)
    private String id;
    @Column(comment = "值")
    private BigDecimal testValue;
}
