package com.easy.query.test.entity.m2m;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.m2m.proxy.StationTenantProxy;
import lombok.Data;

/**
 * create time 2025/10/16 09:25
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_station_tenant")
@EntityProxy
public class StationTenant implements ProxyEntityAvailable<StationTenant , StationTenantProxy> {
    private String stationId;
    private String tenantId;
}
