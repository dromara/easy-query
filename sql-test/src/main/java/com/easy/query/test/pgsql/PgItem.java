package com.easy.query.test.pgsql;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.pgsql.proxy.PgItemProxy;
import lombok.Data;

import java.math.BigDecimal;

/**
 * create time 2026/2/3 16:51
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("pg_item")
public class PgItem implements ProxyEntityAvailable<PgItem , PgItemProxy> {
    private String type;
    private BigDecimal column2;
    private BigDecimal column3;
    private BigDecimal column4;
    private BigDecimal column5;
}
