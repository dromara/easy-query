package com.easy.query.test.mysql8.entity.many;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.many.proxy.M8AreaProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2025/7/17 22:22
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_area")
@FieldNameConstants
public class M8Area implements ProxyEntityAvailable<M8Area , M8AreaProxy> {
    @Column(primaryKey = true)
    private String id;

    private String cid;

    private String name;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8Area.Fields.id}, targetProperty = {M8AreaBuild.Fields.aid})
    private List<M8AreaBuild> builds;
}
