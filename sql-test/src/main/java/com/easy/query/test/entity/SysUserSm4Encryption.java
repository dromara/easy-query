package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.encryption.DefaultSafeSm4EasyEncryptionStrategy;
import com.easy.query.test.entity.proxy.SysUserSm4EncryptionProxy;
import lombok.Data;

/**
 * create time 2026/9/6 22:10
 * SM4加密(hex存储)用户表
 *
 * @author xuejiaming
 */
@Data
@Table("t_sys_user_sm4_encryption")
@EntityProxy
public class SysUserSm4Encryption implements ProxyEntityAvailable<SysUserSm4Encryption, SysUserSm4EncryptionProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Encryption(strategy = DefaultSafeSm4EasyEncryptionStrategy.class, supportQueryLike = true)
    @Column(dbType = "varchar(1024)")
    private String phone;
    @Encryption(strategy = DefaultSafeSm4EasyEncryptionStrategy.class, supportQueryLike = true)
    @Column(dbType = "varchar(2048)")
    private String address;
}
