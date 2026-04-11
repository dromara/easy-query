package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.Interceptor2EntityProxy;
import com.easy.query.test.mysql8.entity.proxy.InterceptorEntityProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2026/4/11 13:41
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("m8_interceptor")
@EntityProxy
@Data
public class InterceptorEntity implements ProxyEntityAvailable<InterceptorEntity, InterceptorEntityProxy> {

    private String id;
    private String name;

    /**
     * 1对多
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {InterceptorEntityProxy.Fields.id}, targetProperty = {Interceptor2EntityProxy.Fields.aid})
    private List<Interceptor2Entity> list;
}
