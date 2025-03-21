package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8UserExtraProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/3/20 23:50
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_ue")
@FieldNameConstants
public class M8UserExtra implements ProxyEntityAvailable<M8UserExtra , M8UserExtraProxy> {
    @Column(primaryKey = true)
    private String extraId;
    private String userId;
    private String certNo;
}
