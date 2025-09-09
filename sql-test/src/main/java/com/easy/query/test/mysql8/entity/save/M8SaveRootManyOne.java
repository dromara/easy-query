package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootManyOneProxy;
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
@Table("m8_save_root_many_one")
public class M8SaveRootManyOne implements ProxyEntityAvailable<M8SaveRootManyOne, M8SaveRootManyOneProxy> {
    @Column(primaryKey = true)
    private String id;
    private String rootManyId;
    private String name;
}
