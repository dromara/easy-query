package com.easy.query.test.entity.tolistflat;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.tolistflat.proxy.TestcProxy;
import lombok.Data;

/**
 * create time 2025/6/11 10:42
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("test_c")
public class Testc implements ProxyEntityAvailable<Testc , TestcProxy> {
    @Column(primaryKey = true)
    private String cid;
    private String cname;
}
