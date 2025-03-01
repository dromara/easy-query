package com.easy.query.test.entity.direct;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.direct.proxy.Direct4Proxy;
import lombok.Data;

/**
 * create time 2025/3/1 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("direct4")
@EntityProxy
public class Direct4 implements ProxyEntityAvailable<Direct4, Direct4Proxy> {
    @Column(primaryKey = true)
    private String c16;
    private String c17;
    private String c18;
    private String c19;
    private String c20;
}
