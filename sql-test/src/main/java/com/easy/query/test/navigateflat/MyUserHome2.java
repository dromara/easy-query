package com.easy.query.test.navigateflat;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.navigateflat.proxy.MyTable;
import com.easy.query.test.navigateflat.proxy.MyUserHome2Proxy;
import lombok.Data;

/**
 * create time 2025/1/3 12:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Table(value = "my_user",schema = "a.dbo")
@Data
@EntityProxy
public class MyUserHome2 implements ProxyEntityAvailable<MyUserHome2 , MyUserHome2Proxy> {
    private String id;
    private String name;
    private Integer age;
}
