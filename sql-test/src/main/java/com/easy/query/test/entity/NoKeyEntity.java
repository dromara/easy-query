package com.easy.query.test.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.NoKeyEntityProxy;
import lombok.Data;

/**
 * create time 2023/6/10 07:56
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("t_nokey")
public class NoKeyEntity implements ProxyEntityAvailable<NoKeyEntity , NoKeyEntityProxy> {
    private String id;
    private String name;
}
