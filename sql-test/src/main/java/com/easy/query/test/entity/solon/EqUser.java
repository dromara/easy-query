package com.easy.query.test.entity.solon;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.ProxyProperty;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.solon.proxy.EqUserProxy;
import lombok.Data;

import java.util.Date;

/**
 * create time 2023/8/15 11:29
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("sys_user")
@Data
@EntityProxy
public class EqUser extends User implements ProxyEntityAvailable<EqUser, EqUserProxy> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2456376761688869439L;

    /**
     * ID
     */
    @Column(primaryKey = true)
    private Integer id;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 账号
     */
    @ProxyProperty("_accc")
    private String account;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户类型（SYSTEM 系统用户，REGISTE注册用户）
     */
    private String type;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别（M 男，F 女，U 未知）
     */
    private String gender;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 帐号状态（ON 正常，OFF 停用）
     */
    private String status;

    /**
     * 删除标志（true 是，false 否）
     */
    @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
    private Boolean del;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDatetime;

    /**
     * 创建者
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createdDatetime;

    /**
     * 更新者
     */
    private Integer editor;

    /**
     * 更新时间
     */
    private Date editedDatetime;

    /**
     * 备注
     */
    private String remark;

}
