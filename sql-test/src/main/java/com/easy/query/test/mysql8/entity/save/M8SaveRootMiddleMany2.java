package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.SaveKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootMiddleMany2Proxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveRootMiddleManyProxy;
import lombok.Data;

/**
 * create time 2025/9/8 08:27
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_save_root_middle_many")
public class M8SaveRootMiddleMany2 implements ProxyEntityAvailable<M8SaveRootMiddleMany2, M8SaveRootMiddleMany2Proxy> {
    @Column(primaryKey = true)
    private String id;
    @SaveKey
    private String rootId;
    @SaveKey
    private String manyId;
}
