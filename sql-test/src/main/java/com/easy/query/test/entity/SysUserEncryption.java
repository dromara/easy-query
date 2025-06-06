package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.encryption.Base64EncryptionStrategy;
import com.easy.query.test.encryption.MyEncryptionStrategy;
import com.easy.query.test.entity.proxy.SysUserEncryptionProxy;
import lombok.Data;

/**
 * create time 2023/4/4 11:36
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_sys_user_encryption")
@EntityProxy
public class SysUserEncryption implements ProxyEntityAvailable<SysUserEncryption , SysUserEncryptionProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Encryption(strategy = Base64EncryptionStrategy.class)
    private String phoneNotSupportLike;
    @Encryption(strategy = Base64EncryptionStrategy.class)
    private String addressNotSupportLike;
    @Encryption(strategy = MyEncryptionStrategy.class, supportQueryLike = true)
    private String phoneSupportLike;
    @Encryption(strategy = MyEncryptionStrategy.class, supportQueryLike = true)
    private String addressSupportLike;
}
