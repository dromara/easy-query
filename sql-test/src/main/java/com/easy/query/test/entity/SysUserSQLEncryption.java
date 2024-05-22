package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.MySQLAesEncryptColumnValueSQLConverter;
import com.easy.query.test.entity.proxy.SysUserSQLEncryptionProxy;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * create time 2023/4/4 11:36
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_sys_user_sql_encryption")
@ToString
@EntityProxy
public class SysUserSQLEncryption implements ProxyEntityAvailable<SysUserSQLEncryption , SysUserSQLEncryptionProxy> {
    @Column(primaryKey = true)
    private String id;
    private String username;
    @Column(sqlConversion = MySQLAesEncryptColumnValueSQLConverter.class)
    private String phone;
    private String idCard;
    private String address;
    private LocalDateTime createTime;
}
