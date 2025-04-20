package com.easy.query.test.testmysql8;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table("t_sys_module")
public class SysModule extends BaseBean {
    private String name;
    private String url;
    private String pre;
    private String iconClass;
    private int isShowMenu;
    private int parentModuleId;
    @Column(dbType = "text")
    private String amisJSON;
    private String authList;
    private int sortNo;
}
