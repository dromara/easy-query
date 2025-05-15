package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.test.entity.proxy.SysUserProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2023/3/25 10:55
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_sys_user",schema = "easy-query-test",ignoreProperties = {"updateTime","createBy","updateBy","deleted"})
@EntityProxy
public class SysUser extends BaseEntity implements ProxyEntityAvailable<SysUser , SysUserProxy> {
    private String username;
    @Encryption(strategy = DefaultAesEasyEncryptionStrategy.class, supportQueryLike = true)
    private String phone;
    @Encryption(strategy = DefaultAesEasyEncryptionStrategy.class, supportQueryLike = true)
    private String idCard;
    @Encryption(strategy = DefaultAesEasyEncryptionStrategy.class, supportQueryLike = true)
    private String address;

    @Navigate(value = RelationTypeEnum.OneToMany, targetProperty = "title")
    private List<BlogEntity> blogs;

    @Navigate(value = RelationTypeEnum.OneToOne)
    private BlogEntity myBlog;
}

//    id varchar(32) not null comment '主键ID'primary key,
//        username varchar(50) null comment '姓名',
//        phone varchar(250) null comment '手机号加密',
//        id_card varchar(500) null comment '身份证编号',
//        address text null comment '地址',
//        create_time datetime not null comment '创建时间'