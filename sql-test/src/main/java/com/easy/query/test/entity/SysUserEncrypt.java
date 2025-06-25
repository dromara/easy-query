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
@Table("sys_user_encrypt")
@EntityProxy
public class SysUserEncrypt implements ProxyEntityAvailable<SysUserEncrypt , SysUserEncryptProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Column(sqlConversion = MySQLAesEncryptColumnValueSQLConverter.class)
    private String phone;
    @Column(sqlConversion = MySQLAesEncrypt2ColumnValueSQLConverter.class)
    private String phone2;
    @Encryption(strategy = JavaEncryptionStrategy.class,supportQueryLike = true)
    @Column(dbType = "varchar(4000)")
    private String Address;
    private LocalDateTime createTime;
    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty = "userId")
    private List<UserBookEncrypt> books;
}