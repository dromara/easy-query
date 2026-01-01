package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.MultiPrimaryEntityProxy;
import lombok.Data;

/**
 * create time 2026/1/1 18:54
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_multi_primary")
@Data
@EntityProxy
public class MultiPrimaryEntity implements ProxyEntityAvailable<MultiPrimaryEntity , MultiPrimaryEntityProxy> {
    @Column(primaryKey = true)
    private String id1;
    @Column(primaryKey = true)
    private String id2;
}
