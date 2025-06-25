package com.easy.query.test.search;


import com.easy.query.search.EasySortType;
import com.easy.query.search.annotation.EasyCond;
import com.easy.query.search.op.Equals;

import java.util.Date;

@EasyCond(table = SysUser.class)
public class SysUserQueryDTO {
    @EasyCond(cond = Equals.class,
            table = SysUser.class
    )
    private String userId;

    @EasyCond(tableAlias = "u")
    private String userName;

    @EasyCond(tableIndex = 0)
    private Date createTime;

    @EasyCond
    private Date deleteTime;

    @EasyCond(
            name = "phone",
            property = "mobile",
            table = SysUserExt.class,
            sort = EasySortType.Desc
    )
    private String phone;
}
