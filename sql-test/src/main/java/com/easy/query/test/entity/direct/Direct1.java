package com.easy.query.test.entity.direct;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.direct.proxy.Direct1Proxy;
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
@Table("direct1")
@EntityProxy
public class Direct1 implements ProxyEntityAvailable<Direct1 , Direct1Proxy> {
    @Column(primaryKey = true)
    private String c1;
    private String c2;
    private String c3;
    private String c4;
    private String c5;

    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {Direct1Proxy.Fields.c1}, targetProperty = {Direct2Proxy.Fields.c7})
    private Direct2 direct2;


    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {Direct1Proxy.Fields.c1},
            mappingClass = Direct2.class,
            selfMappingProperty = {Direct2Proxy.Fields.c7},
            targetMappingProperty = {Direct2Proxy.Fields.c8, Direct2Proxy.Fields.c9},
            targetProperty = {Direct3Proxy.Fields.c13, Direct3Proxy.Fields.c14})
    private Direct3 direct3;


    @Navigate(value = RelationTypeEnum.ManyToOne,directMapping = {"direct2","direct3","direct4"})
    private Direct4 direct4;

    @Navigate(value = RelationTypeEnum.ManyToOne,directMapping = {"direct2","direct3","direct4","direct5"})
    private Direct5 direct5;
}
