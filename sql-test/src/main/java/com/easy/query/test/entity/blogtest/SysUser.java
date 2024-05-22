package com.easy.query.test.entity.blogtest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.blogtest.proxy.SysUserProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2024/4/29 22:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user")
@Data
@EntityProxy
@FieldNameConstants
@EasyAlias("user")
public class SysUser implements ProxyEntityAvailable<SysUser , SysUserProxy> {
    @Column(primaryKey = true)
    private String id;
    private String companyId;
    private String name;
    private Integer age;
    private LocalDateTime createTime;

    @Navigate(value = RelationTypeEnum.OneToOne,targetProperty = "userId")
    private SysUserAddress address;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            mappingClass = UserRole.class,
            selfMappingProperty = "userId",
            targetMappingProperty = "roleId")
    private List<SysRole> roles;

    @Navigate(value = RelationTypeEnum.ManyToOne,selfProperty = Fields.companyId)
    private Company company;
}
