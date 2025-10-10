package com.easy.query.test.duckdb;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.duckdb.proxy.TestJdjgProxy;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * create time 2025/10/8 18:56
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_jdjg")
@Data
@ToString
@EntityProxy
public class TestJdjg implements ProxyEntityAvailable<TestJdjg , TestJdjgProxy> {
    private String name;
    private BigDecimal month4;
    private BigDecimal month5;
    private BigDecimal month6;
    private BigDecimal month7;
    private BigDecimal month8;
    private BigDecimal month9;
}
