package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.UUIDEntityProxy;
import lombok.Data;

import java.sql.JDBCType;
import java.util.UUID;

/**
 * create time 2025/8/4 08:20
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_uuid")
@EntityProxy
public class UUIDEntity implements ProxyEntityAvailable<UUIDEntity , UUIDEntityProxy> {
    @Column(dbType = "UUID")
    private UUID id1;
    @Column(dbType = "varchar(36)",jdbcType = JDBCType.VARCHAR)
    private UUID id2;
}
