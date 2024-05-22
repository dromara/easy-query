package com.easy.query.test.dto.autotest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.dto.autotest.proxy.UserProxy;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2024/5/15 16:34
 * 文件说明
 *
 * @author xuejiaming
 */

@Data
@Accessors(chain = true)
@EntityProxy
@Table("sys_user")
@EasyAlias("tUser")
@FieldNameConstants
public class User implements ProxyEntityAvailable<User, UserProxy> {

    @Column(primaryKey = true)
    private Long id;

    /** 登录账号 */
    @Column("username")
    private String username;

    /** 账户状态 {1:有效,2:过期,3:禁用} */
    @Column("status")
    private Integer status;

    /** 用户名 */
    @Column("real_name")
    private String realName;

    /** 登录密码 */
    @Column("password")
    private String password;

    /** 电子邮件 */
    @Column("email")
    private String email;

    /** 手机号码 */
    @Column("mobile")
    private String mobile;

    /** 创建时间 */
    @Column("create_time")
    private LocalDateTime createTime;

    /** 最后登录时间 */
    @Column("login_time")
    private LocalDateTime loginTime;

    @Column("deleted")
    @LogicDelete(strategy = LogicDeleteStrategyEnum.DELETE_LONG_TIMESTAMP)
    private Long deleted;

    @Navigate(value = RelationTypeEnum.ManyToMany
            , mappingClass = RoleJoin.class
            , selfMappingProperty = "bizId"
            , targetMappingProperty = "roleId"
            , extraFilter = RoleJoin.RoleJoinType.class)
    private List<Role> roles;

}