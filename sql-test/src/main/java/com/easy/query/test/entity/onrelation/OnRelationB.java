package com.easy.query.test.entity.onrelation;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.onrelation.proxy.OnRelationBProxy;
import com.easy.query.test.entity.onrelation.proxy.OnRelationCProxy;
import lombok.Data;

/**
 * create time 2025/6/16 21:13
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("on_relation_b")
public class OnRelationB implements ProxyEntityAvailable<OnRelationB , OnRelationBProxy> {
    @Column(primaryKey = true)
    private String bid;
    private String aid;
    private String bname;

    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {OnRelationBProxy.Fields.bid}, targetProperty = {OnRelationCProxy.Fields.bid})
    private OnRelationC onRelationC;
}
