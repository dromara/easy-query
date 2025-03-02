package com.easy.query.test.entity.direct;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.direct.proxy.Direct5Proxy;
import lombok.Data;

/**
 * create time 2025/3/1 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("direct5")
@EntityProxy
public class Direct5 implements ProxyEntityAvailable<Direct5, Direct5Proxy> {
    @Column(primaryKey = true)
    private String c21;
    private String c22;
    private String c23;
    private String c24;
    private String c25;
}
