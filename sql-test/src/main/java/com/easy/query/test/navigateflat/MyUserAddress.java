package com.easy.query.test.navigateflat;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.navigateflat.proxy.MyUserAddressProxy;
import lombok.Data;

/**
 * create time 2025/1/2 14:01
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("my_user_address")
@Data
@EntityProxy
public class MyUserAddress implements ProxyEntityAvailable<MyUserAddress , MyUserAddressProxy> {
    private String id;
    private String uid;
    private String address;
}
