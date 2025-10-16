package com.easy.query.test.entity.m2m;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.m2m.proxy.TenantOperatorProxy;
import lombok.Data;

/**
 * create time 2025/10/16 09:25
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("t_tenant_operator")
public class TenantOperator implements ProxyEntityAvailable<TenantOperator , TenantOperatorProxy> {
    private String tenantOperatorId;
    private String tenantOperatorName;
}
