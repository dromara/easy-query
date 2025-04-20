package com.easy.query.test.testmysql8;

import com.easy.query.core.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("t_sys_user")
public class SysUser extends BaseBean {
    private String userName;
    private String password;
    private String realName;
    private int roleId;
    private int allowLogin;
}
