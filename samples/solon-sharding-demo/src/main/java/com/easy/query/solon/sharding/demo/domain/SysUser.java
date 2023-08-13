package com.easy.query.solon.sharding.demo.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.solon.sharding.demo.encrypt.JavaEncryptionStrategy;
import com.easy.query.solon.sharding.demo.encrypt.MySQLAESColumnValueSQLConverter;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2023/8/12 20:40
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("sys_user")
public class SysUser {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Column(sqlConversion = MySQLAESColumnValueSQLConverter.class)
    private String phone;
    @Encryption(strategy = JavaEncryptionStrategy.class)
    private String Address;
    private LocalDateTime createTime;
    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty = "userId")
    private List<UserBook> books;
}
