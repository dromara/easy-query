package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.Interceptor2EntityProxy;
import lombok.Data;

/**
 * create time 2026/4/11 13:42
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("m8_interceptor2")
@EntityProxy
@Data
public class Interceptor2Entity implements ProxyEntityAvailable<Interceptor2Entity , Interceptor2EntityProxy> {

    private String id;
    private String aid;
    private String name;
}
