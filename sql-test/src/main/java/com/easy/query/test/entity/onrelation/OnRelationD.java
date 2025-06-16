package com.easy.query.test.entity.onrelation;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.onrelation.proxy.OnRelationDProxy;
import lombok.Data;

/**
 * create time 2025/6/16 21:13
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("on_relation_d")
public class OnRelationD implements ProxyEntityAvailable<OnRelationD , OnRelationDProxy> {
    @Column(primaryKey = true)
    private String did;
    private String dname;
}
