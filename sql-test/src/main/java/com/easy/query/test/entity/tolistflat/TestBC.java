package com.easy.query.test.entity.tolistflat;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.tolistflat.proxy.TestBCProxy;
import lombok.Data;

/**
 * create time 2025/6/11 10:42
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("test_bc")
public class TestBC implements ProxyEntityAvailable<TestBC , TestBCProxy> {
    @Column(primaryKey = true)
    private String id;
    private String bid;
    private String cid;
}
