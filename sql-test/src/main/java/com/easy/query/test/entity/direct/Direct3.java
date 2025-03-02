package com.easy.query.test.entity.direct;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.direct.proxy.Direct3Proxy;
import com.easy.query.test.entity.direct.proxy.Direct4Proxy;
import lombok.Data;

/**
 * create time 2025/3/1 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("direct3")
@EntityProxy
public class Direct3 implements ProxyEntityAvailable<Direct3, Direct3Proxy> {
    @Column(primaryKey = true)
    private String c11;
    private String c12;
    private String c13;
    private String c14;
    private String c15;

    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = {Direct3Proxy.Fields.c15}, targetProperty = {Direct4Proxy.Fields.c20})
    private Direct4 direct4;
}
