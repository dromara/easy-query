package com.easy.query.test.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.MyOrderProxy;
import lombok.Data;

/**
 * create time 2025/4/10 17:55
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_order")
@EntityProxy
@Data
public class MyOrder implements ProxyEntityAvailable<MyOrder , MyOrderProxy> {
    private String id;
    private String no;
    private Integer status;
}
