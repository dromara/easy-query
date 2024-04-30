package com.easy.query.test.entity.blogtest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.blogtest.proxy.SysUserAddressProxy;
import lombok.Data;

/**
 * create time 2024/4/30 07:02
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user_address")
@Data
@EntityProxy
public class SysUserAddress implements ProxyEntityAvailable<SysUserAddress , SysUserAddressProxy> {
    @Column(primaryKey = true)
    private String id;
    private String userId;
    private String province;
    private String city;
    private String area;
    private String addr;

    @Override
    public Class<SysUserAddressProxy> proxyTableClass() {
        return SysUserAddressProxy.class;
    }
}
