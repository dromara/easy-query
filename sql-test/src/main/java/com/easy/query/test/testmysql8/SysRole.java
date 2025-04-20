package com.easy.query.test.testmysql8;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table("t_sys_role")
public class SysRole extends BaseBean {
    private String name;
    private String desc;
    @Column(dbType = "text")
    private String auths;
    @Column(dbType = "text")
    private String menus;
}
