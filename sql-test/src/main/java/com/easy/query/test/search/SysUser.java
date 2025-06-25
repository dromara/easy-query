package com.easy.query.test.search;

import com.easy.query.search.annotation.EasyCond;
import com.easy.query.search.op.Equals;
import com.easy.query.test.search.proxy.SysUserProxy;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import lombok.Data;

import java.util.Date;

@Table("sys_user_search")
@EntityProxy
@Data
public class SysUser
        implements ProxyEntityAvailable<SysUser, SysUserProxy> {
    @Column(primaryKey = true, dbType = "BIGINT", comment = "用户id")
    @EasyCond(cond = Equals.class)
    private String userId;

    @EasyCond
    @Column(dbType = "VARCHAR(32)", comment = "用户名")
    private String userName;

    @EasyCond
    @Column(dbType = "DATETIME", comment = "创建时间")
    private Date createTime;

    @EasyCond
    @Column(dbType = "DATETIME", nullable = true, comment = "删除时间")
    private Date deleteTime;

    @EasyCond(false)
    @ColumnIgnore
    private Date noSearch;
}
