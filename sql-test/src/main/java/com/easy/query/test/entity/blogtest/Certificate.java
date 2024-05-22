package com.easy.query.test.entity.blogtest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.CertStatusColumnValueSQLConverter;
import com.easy.query.test.entity.blogtest.proxy.CertificateProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2024/4/30 17:27
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_certificate")
@Data
@EntityProxy
public class Certificate implements ProxyEntityAvailable<Certificate , CertificateProxy> {
    @Column(primaryKey = true)
    private String id;
    /**
     * 证书名称
     */
    private String name;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 过期时间
     */
    private String invalidTime;

    @Column(sqlConversion = CertStatusColumnValueSQLConverter.class)
    //因为不是真实列所以不需要插入
    @InsertIgnore
    //因为不是真实列所以不需要修改
    @UpdateIgnore
    private CertStatusEnum status;
}
