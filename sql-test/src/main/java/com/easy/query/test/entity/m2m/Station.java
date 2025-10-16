package com.easy.query.test.entity.m2m;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.m2m.proxy.StationProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/10/16 09:24
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_station")
@EntityProxy
public class Station implements ProxyEntityAvailable<Station, StationProxy> {
    private String stationId;
    private String stationName;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = "stationId",
            selfMappingProperty = "stationId",
            mappingClass = StationTenant.class,
            targetProperty = "tenantOperatorId",
            targetMappingProperty = "tenantId",
            subQueryToGroupJoin = true)
    private List<TenantOperator> operators;
}
