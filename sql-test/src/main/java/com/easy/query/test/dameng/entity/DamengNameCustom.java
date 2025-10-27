package com.easy.query.test.dameng.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.dameng.entity.proxy.DamengNameCustomProxy;
import lombok.Data;

/**
 * create time 2025/10/27 14:38
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("MY_CUSTOM")
@EntityProxy
public class DamengNameCustom implements ProxyEntityAvailable<DamengNameCustom , DamengNameCustomProxy> {
    private String id;
    private String name;
    @Column("ROWNUMBER")
    private Integer rowNumber;
    @Column("ISDELETE")
    private Integer isDelete;
}
