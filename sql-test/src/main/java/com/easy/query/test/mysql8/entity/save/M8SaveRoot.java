package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootManyProxy;
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
    private String id;
    private String name;
    private String code;
    private String tenantId;
    /**
     * toOne 关系
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {M8SaveRootProxy.Fields.id}, targetProperty = {M8SaveRootOneProxy.Fields.rootId})
    private M8SaveRootOne m8SaveRootOne;

    /**
     * toMany关系
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8SaveRootProxy.Fields.id}, targetProperty = {M8SaveRootManyProxy.Fields.rootId})
    private List<M8SaveRootMany> m8SaveRootManyList;
}
