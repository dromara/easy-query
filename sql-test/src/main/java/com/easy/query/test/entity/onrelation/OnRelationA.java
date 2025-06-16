package com.easy.query.test.entity.onrelation;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.onrelation.proxy.OnRelationAProxy;
import com.easy.query.test.entity.onrelation.proxy.OnRelationBProxy;
import lombok.Data;

/**
 * create time 2025/6/16 21:13
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("on_relation_a")
public class OnRelationA implements ProxyEntityAvailable<OnRelationA , OnRelationAProxy> {
    @Column(primaryKey = true)
    private String aid;
    private String aname;

    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {OnRelationAProxy.Fields.aid}, targetProperty = {OnRelationBProxy.Fields.aid})
    private OnRelationB onRelationB;
}
