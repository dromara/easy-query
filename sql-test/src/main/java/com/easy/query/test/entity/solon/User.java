package com.easy.query.test.entity.solon;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create time 2023/8/15 11:28
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class User implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3195549434753942610L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户类型（SYSTEM 系统用户，REGISTE注册用户）
     */
    private String type = "SYSTEM";

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
    private String gender = "M";

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
    @NotNull
    private Boolean del = Boolean.FALSE;

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
