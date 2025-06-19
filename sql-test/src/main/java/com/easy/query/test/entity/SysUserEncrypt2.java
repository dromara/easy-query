package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.JavaEncryptionStrategy;
import com.easy.query.test.conversion.MySQLAesEncrypt2ColumnValueSQLConverter;
import com.easy.query.test.conversion.MySQLAesEncryptColumnValueSQLConverter;
import com.easy.query.test.entity.proxy.SysUserEncrypt2Proxy;
import com.easy.query.test.entity.proxy.SysUserEncryptProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2023/8/13 09:47
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("sys_user2")
@EntityProxy
public class SysUserEncrypt2 implements ProxyEntityAvailable<SysUserEncrypt2, SysUserEncrypt2Proxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Column(sqlConversion = MySQLAesEncrypt2ColumnValueSQLConverter.class)
    private String phone;
}