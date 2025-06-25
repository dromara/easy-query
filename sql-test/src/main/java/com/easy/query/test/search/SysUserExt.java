package com.easy.query.test.search;

import com.easy.query.search.annotation.EasyCond;
import com.easy.query.search.op.Equals;
import com.easy.query.test.search.proxy.SysUserExtProxy;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import lombok.Data;

@Table("sys_user_search_ext")
@EntityProxy
@Data
public class SysUserExt
        implements ProxyEntityAvailable<SysUserExt, SysUserExtProxy> {
    @EasyCond(cond = Equals.class)
    @Column(primaryKey = true, dbType = "BIGINT", comment = "用户id")
    private String userId;

    @EasyCond(name = "extMobile")
    @Column(dbType = "VARCHAR(24)", comment = "手机号码")
    private String mobile;
}
