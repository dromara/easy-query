package com.easy.query.test.mysql8.entity.many;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.many.proxy.M8AreaBuildLicenseProxy;
import com.easy.query.test.mysql8.entity.many.proxy.M8AreaBuildProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/7/17 22:24
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_build_license")
@FieldNameConstants
public class M8AreaBuildLicense implements ProxyEntityAvailable<M8AreaBuildLicense, M8AreaBuildLicenseProxy> {
    @Column(primaryKey = true)
    private String id;
    private String buildId;

    private String no;

}
