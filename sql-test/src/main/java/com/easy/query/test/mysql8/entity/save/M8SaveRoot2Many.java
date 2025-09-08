package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRoot2ManyProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootManyProxy;
import lombok.Data;

/**
 * create time 2025/9/7 17:00
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_save_root_2many")
public class M8SaveRoot2Many implements ProxyEntityAvailable<M8SaveRoot2Many, M8SaveRoot2ManyProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
}
