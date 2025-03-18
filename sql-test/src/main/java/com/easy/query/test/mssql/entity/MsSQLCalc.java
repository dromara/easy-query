package com.easy.query.test.mssql.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mssql.entity.proxy.MsSQLCalcProxy;
import lombok.Data;

import java.math.BigDecimal;

/**
 * create time 2025/3/18 11:54
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_calc")
@EntityProxy
@Data
public class MsSQLCalc implements ProxyEntityAvailable<MsSQLCalc , MsSQLCalcProxy> {
    @Column(primaryKey = true)
    private String id;
    @Column(dbType = "decimal(18,2)")
    private BigDecimal column1;
    @Column(dbType = "decimal(18,4)")
    private BigDecimal column2;
    @Column(dbType = "decimal(18,4)")
    private BigDecimal column3;
}
