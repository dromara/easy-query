package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootManyOneProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootManyProxy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create time 2025/9/7 17:00
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_save_root_many")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class M8SaveRootMany implements ProxyEntityAvailable<M8SaveRootMany, M8SaveRootManyProxy> {
    @Column(primaryKey = true)
    private String id;
    private String rootId;
    private String name;
    /**
     *
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {M8SaveRootManyProxy.Fields.id}, targetProperty = {M8SaveRootManyOneProxy.Fields.rootManyId},cascade = CascadeTypeEnum.DELETE)
    private M8SaveRootManyOne m8SaveRootManyOne;
}
