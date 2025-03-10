package com.easy.query.test.entity.one;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.one.proxy.One2Proxy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * create time 2025/3/10 15:07
 * 文件说明
 *
 * @author xuejiaming
 */
@Getter
@Setter
@Table("one2")
@EntityProxy
@EasyAlias("one2")
@ToString
public class One2 implements ProxyEntityAvailable<One2, One2Proxy> {
    @Column(primaryKey = true)
    private String id;
    private String oid;
    private String oname;
}
