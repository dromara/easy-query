package com.easy.query.test.entity.direct;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.direct.proxy.Direct4Proxy;
import com.easy.query.test.entity.direct.proxy.Direct5Proxy;
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

    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {Direct4Proxy.Fields.c16, Direct4Proxy.Fields.c18}, targetProperty = {Direct5Proxy.Fields.c22, Direct5Proxy.Fields.c21})
    private Direct5 direct5;
}
