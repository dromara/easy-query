package com.easy.query.test.entity.direct;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.direct.proxy.Direct2Proxy;
import com.easy.query.test.entity.direct.proxy.Direct3Proxy;
import lombok.Data;

/**
 * create time 2025/3/1 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("direct2")
@EntityProxy
public class Direct2 implements ProxyEntityAvailable<Direct2, Direct2Proxy> {
    @Column(primaryKey = true)
    private String c6;
    private String c7;
    private String c8;
    private String c9;
    private String c10;

    @Navigate(value = RelationTypeEnum.ManyToOne,
            selfProperty = {Direct2Proxy.Fields.c8, Direct2Proxy.Fields.c9},
            targetProperty = {Direct3Proxy.Fields.c13, Direct3Proxy.Fields.c14})
    private Direct3 direct3;
}
