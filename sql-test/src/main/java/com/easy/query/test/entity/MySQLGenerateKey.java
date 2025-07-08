package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.MySQLGenerateKeyProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2025/7/8 10:41
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("my_sql_generate_key")
@EntityProxy
public class MySQLGenerateKey implements ProxyEntityAvailable<MySQLGenerateKey , MySQLGenerateKeyProxy> {
    @Column(primaryKey = true, generatedKey = true)
    public Integer id;

    @Column(dbDefault = "''")
    private String name;

    @Column(dbDefault = "CURRENT_TIMESTAMP(3)",nullable = false)
    private LocalDateTime now;
}
