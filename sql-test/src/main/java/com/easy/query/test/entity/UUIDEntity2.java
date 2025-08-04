package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.UUIDEntity2Proxy;
import lombok.Data;

import java.sql.JDBCType;
import java.util.UUID;

/**
 * create time 2025/8/4 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_uuid2")
@EntityProxy
public class UUIDEntity2 implements ProxyEntityAvailable<UUIDEntity2 , UUIDEntity2Proxy> {

    @Column(dbType = "BINARY(36)",jdbcType = JDBCType.BINARY)
    private UUID id1;
    @Column(dbType = "varchar(36)")
    private UUID id2;
    @Column(dbType = "CHAR(36)")
    private UUID id3;
}
