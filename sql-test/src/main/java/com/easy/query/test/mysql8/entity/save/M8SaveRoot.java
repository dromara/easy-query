package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRoot2ManyProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootManyProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootMiddleMany2Proxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootMiddleManyProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootOne2Proxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootOneProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/9/7 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_save_root")
public class M8SaveRoot implements ProxyEntityAvailable<M8SaveRoot, M8SaveRootProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private String code;
    /**
     * toOne 关系
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {M8SaveRootProxy.Fields.id}, targetProperty = {M8SaveRootOneProxy.Fields.rootId}, cascade = CascadeTypeEnum.DELETE)
    private M8SaveRootOne m8SaveRootOne;
    /**
     * toOne2 关系
     **/
    @Navigate(value = RelationTypeEnum.OneToOne,
            selfProperty = {M8SaveRootProxy.Fields.id},
            targetProperty = {M8SaveRootOne2Proxy.Fields.rootId},cascade = CascadeTypeEnum.DELETE)
    private M8SaveRootOne2 m8SaveRootOne2;

    /**
     * toMany关系
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8SaveRootProxy.Fields.id}, targetProperty = {M8SaveRootManyProxy.Fields.rootId}, cascade = CascadeTypeEnum.DELETE)
    private List<M8SaveRootMany> m8SaveRootManyList;

    /**
     * 多对多
     **/
    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = {M8SaveRootProxy.Fields.id},
            selfMappingProperty = {M8SaveRootMiddleManyProxy.Fields.rootId},
            mappingClass = M8SaveRootMiddleMany.class,
            targetProperty = {M8SaveRoot2ManyProxy.Fields.id},
            targetMappingProperty = {M8SaveRootMiddleManyProxy.Fields.manyId}, cascade = CascadeTypeEnum.DELETE)
    private List<M8SaveRoot2Many> m8SaveRoot2ManyList;


    /**
     *
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8SaveRootProxy.Fields.id}, targetProperty = {M8SaveRootMiddleManyProxy.Fields.rootId}, cascade = CascadeTypeEnum.DELETE)
    private List<M8SaveRootMiddleMany> m8SaveRootMiddleManyList;
    /**
     *
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8SaveRootProxy.Fields.id}, targetProperty = {M8SaveRootMiddleMany2Proxy.Fields.rootId}, cascade = CascadeTypeEnum.DELETE)
    private List<M8SaveRootMiddleMany2> m8SaveRootMiddleManyList2;
}
